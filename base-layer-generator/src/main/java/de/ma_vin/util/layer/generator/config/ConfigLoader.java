package de.ma_vin.util.layer.generator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ma_vin.util.layer.generator.config.elements.*;
import lombok.Data;

import jakarta.persistence.Id;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.maven.plugin.logging.Log;
import org.xml.sax.SAXException;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

@Data
public class ConfigLoader {

    public static final String XML_FILE_ENDING = ".xml";
    public static final String YAML_FILE_ENDING = ".yaml";
    public static final String YML_FILE_ENDING = ".yml";
    public static final String JSON_FILE_ENDING = ".json";

    private File configFile;
    private File schemaFile = null;
    private Config config;
    private Log logger;

    public ConfigLoader(File configFile, Log logger) {
        this.logger = logger;
        this.configFile = configFile;
    }

    public ConfigLoader(File configFile, Log logger, File schemaFile) {
        this(configFile, logger);
        this.schemaFile = schemaFile;
    }

    /**
     * Loads the configuration from {@code configFile} with formats {@link ConfigLoader#XML_FILE_ENDING}
     * , {@link ConfigLoader#YAML_FILE_ENDING}, {@link ConfigLoader#YML_FILE_ENDING} or {@link ConfigLoader#JSON_FILE_ENDING}
     *
     * @return {@code true} the file could be parsed to {@link Config}, validated and completed. Otherwise {@code false}
     */
    public boolean load() {
        if (!configFile.getName().contains(".")) {
            logger.error(String.format("Could not identify file ending of config file \"%s\"", configFile.getName()));
            return false;
        }

        String fileEnding = configFile.getName().substring(configFile.getName().lastIndexOf("."));

        boolean loaded = switch (fileEnding) {
            case JSON_FILE_ENDING -> loadJson();
            case XML_FILE_ENDING -> loadXml();
            case YAML_FILE_ENDING, YML_FILE_ENDING -> loadYaml();
            default -> handleUnknownConfigFileFormat(fileEnding);
        };

        return loaded && validate() && complete();
    }

    /**
     * Parse the config file with a yaml format to {@link Config} property {@code configFile}
     *
     * @return {@code true} if parsing was successful. {@code false} at any failure
     */
    private boolean loadYaml() {
        LoaderOptions loaderOptions = new LoaderOptions();
        Yaml yaml = new Yaml(new Constructor(Config.class, loaderOptions));
        try {
            config = yaml.load(new FileInputStream(configFile));
            return true;
        } catch (FileNotFoundException e) {
            return handleConfigReadException(e);
        }
    }

    /**
     * Parse the config file with a json format to {@link Config} property {@code configFile}
     *
     * @return {@code true} if parsing was successful. {@code false} at any failure
     */
    private boolean loadJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            config = objectMapper.readValue(configFile, Config.class);
            return true;
        } catch (IOException e) {
            return handleConfigReadException(e);
        }
    }

    /**
     * Parse the config file with a xml format to the {@link Config} property {@code configFile}
     *
     * @return {@code true} if parsing was successful. {@code false} at any failure
     */
    private boolean loadXml() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            if (schemaFile != null) {
                SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Schema configSchema = sf.newSchema(schemaFile);
                unmarshaller.setSchema(configSchema);
            }

            FileReader reader = new FileReader(configFile);
            config = (Config) unmarshaller.unmarshal(reader);
            return true;
        } catch (JAXBException | FileNotFoundException | SAXException e) {
            return handleConfigReadException(e);
        }
    }

    /**
     * Handles an unknown file
     *
     * @param fileEnding the unknown file ending
     * @return {@code false}
     */
    private boolean handleUnknownConfigFileFormat(String fileEnding) {
        logger.error(String.format("Not supported file format \"%s\". Only \"%s\", \"%s\", \"%s\" and \"%s\" are provided"
                , fileEnding, XML_FILE_ENDING, YAML_FILE_ENDING, YML_FILE_ENDING, JSON_FILE_ENDING));
        return false;
    }

    /**
     * Handles an exception while reading a config file
     *
     * @param exception the exception which was thrown
     * @return {@code false}
     */
    private boolean handleConfigReadException(Exception exception) {
        logger.error("Could not load config file:");
        logger.error(exception);
        return false;
    }

    private boolean validate() {
        List<String> messages = new ArrayList<>();
        if (config.isValid(messages)) {
            messages.forEach(logger::warn);
            return true;
        }
        messages.forEach(logger::error);
        return false;
    }

    public boolean complete() {
        removeNullLists();
        completeOwner();
        if (!completeReferences()) {
            logger.error("Completion of references could not be completed");
            return false;
        }
        if (!completeEntities()) {
            logger.error("Completion of parents could not be completed");
            return false;
        }
        if (!completeFilterFields()) {
            logger.error("Completion of filter fields at references could not be completed");
            return false;
        }
        config.setUseIdGenerator(config.getIdGeneratorPackage() != null && config.getIdGeneratorClass() != null);
        return true;
    }

    private void removeNullLists() {
        if (config.getEntities() == null) {
            config.setEntities(new ArrayList<>());
        } else {
            removeNullList(config.getEntities());
        }
        if (config.getGroupings() == null) {
            config.setGroupings(new ArrayList<>());
            return;
        }
        config.getGroupings().forEach(g -> {
            if (g.getEntities() == null) {
                g.setEntities(new ArrayList<>());
            }
            removeNullList(g.getEntities());
        });
    }

    private void removeNullList(List<Entity> entities) {
        entities.forEach(e -> {
            if (e.getReferences() == null) {
                e.setReferences(new ArrayList<>());
            }
            if (e.getFields() == null) {
                e.setFields(new ArrayList<>());
            }
            if (e.getIndices() == null) {
                e.setIndices(new ArrayList<>());
            }
            e.setParentRefs(new ArrayList<>());
        });
    }

    private void completeOwner() {
        config.getGroupings().forEach(g -> g.getEntities().forEach(e -> e.setGrouping(g)));
        completeEntityIterator(this::completeFieldOwner);
    }

    private boolean completeFieldOwner(List<Entity> entityList) {
        entityList.forEach(e -> e.getFields().forEach(f -> f.setParentEntity(e)));
        return true;
    }

    private boolean completeReferences() {
        return completeEntityIterator(this::completeReferencesOfEntities);
    }

    private boolean completeReferencesOfEntities(List<Entity> entityList) {
        for (Entity e : entityList) {
            for (Reference r : e.getReferences()) {
                if (!completeReferences(r.getTargetEntity(), e, r)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean completeReferences(String targetEntityName, Entity actualEntity, Reference actualReference) {
        Optional<Entity> entity = getEntity(targetEntityName);
        if (entity.isPresent()) {
            addParentReferenceAndTarget(actualEntity, actualReference, entity.get());
            return true;
        }
        logger.error(String.format("The target of reference %s could not be found", actualReference.getTargetEntity()));
        return false;
    }

    private void addParentReferenceAndTarget(Entity actualEntity, Reference actualReference, Entity targetEntity) {
        Reference parentRef = actualReference.copy();
        parentRef.setTargetEntity(actualEntity.getBaseName());
        parentRef.setRealTargetEntity(actualEntity);
        parentRef.setParent(targetEntity);
        parentRef.setReverse(true);
        targetEntity.getParentRefs().add(parentRef);


        actualReference.setParent(actualEntity);
        actualReference.setRealTargetEntity(targetEntity);
    }

    private boolean completeEntities() {
        return completeEntityIterator(this::completeEntities);
    }

    private boolean completeEntities(List<Entity> entities) {
        boolean result = true;
        for (Entity e : entities) {
            if (e.getParent() != null && !e.getParent().trim().isEmpty()) {
                result = result && completeEntitiesWithParent(e, e.getParent().trim());
            }
            if (e.getDerivedFrom() != null && !e.getDerivedFrom().trim().isEmpty()) {
                result = result && completeEntitiesDerived(e);
            }
            result = result && completeIndices(e);
            if (e.getTableName() == null) {
                e.setTableName(e.getBaseName());
            }
        }
        return result;
    }

    private boolean completeEntitiesWithParent(Entity actualEntity, String parentName) {
        Optional<Entity> entity = getEntity(parentName);
        if (entity.isPresent() && Boolean.TRUE.equals(entity.get().getIsAbstract())) {
            actualEntity.setRealParent(entity.get());
            return true;
        }
        logger.error(String.format("The parent %s of entity %s could not be found", parentName, actualEntity.getBaseName()));
        return false;
    }

    /**
     * Completes an {@link Entity} which is based on another one and sets the {@link Entity#setRealDerivedFrom(Entity)}
     *
     * @param actualEntity entity which is to complete
     * @return {@code true} if the derived from entity could be found, is not abstract, provides all necessary fields
     * and is at least a domain one. Otherwise {@code false}.
     */
    private boolean completeEntitiesDerived(Entity actualEntity) {
        String derivedFromName = actualEntity.getDerivedFrom().trim();
        Optional<Entity> entity = getEntity(derivedFromName);
        if (entity.isEmpty() || Boolean.TRUE.equals(entity.get().getIsAbstract())) {
            logger.error(String.format("The entity %s, from which %s is to be derived, could not be found"
                    , derivedFromName, actualEntity.getBaseName()));
            return false;
        }
        if (!entity.get().getModels().isDomain()) {
            logger.error(String.format("The entity %s, from which %s is to be derived, has to be model which include domain"
                    , derivedFromName, actualEntity.getBaseName()));
            return false;
        }
        if (!actualEntity.getFields().stream().allMatch(f -> existsDerivedFromFieldAtEntity(f, entity.get()))) {
            logger.error(String.format("The entity %s, from which %s is to be derived, does not have all required fields"
                    , derivedFromName, actualEntity.getBaseName()));
            return false;
        }
        actualEntity.setRealDerivedFrom(entity.get());
        return true;
    }

    /**
     * Checks whether there exists an equal {@link Field} at a given {@link Entity} or not.
     *
     * @param field  field to check
     * @param entity entity where to search at
     * @return {@code true} if there is an equal field at the entity or at its parent. Otherwise {@code false}
     */
    private boolean existsDerivedFromFieldAtEntity(Field field, Entity entity) {
        if (entity.getFields().stream().anyMatch(f -> checkAndDerive(field, f))
                || (entity.hasParent() && existsDerivedFromFieldAtEntity(field, entity.getRealParent()))) {
            return true;
        }
        logger.error(String.format("The field %s does not exists at %s or its parent", field.getFieldName(), entity.getBaseName()));
        return false;
    }

    /**
     * if the fields equals by name the other properties will be taken over
     *
     * @param field            field where to set the properties
     * @param derivedFromField field where to get from properties
     * @return {@code true} if the field names equals. Otherwise {@code false}
     */
    private boolean checkAndDerive(Field field, Field derivedFromField) {
        if (!field.getFieldName().equals(derivedFromField.getFieldName())) {
            return false;
        }
        field.setType(derivedFromField.getType());
        field.setTypePackage(derivedFromField.getTypePackage());
        field.setIsTypeEnum(derivedFromField.getIsTypeEnum());
        field.setShortDescription(derivedFromField.getShortDescription());
        field.setDescription(derivedFromField.getDescription());
        field.setModels(Models.DOMAIN_DTO);
        return true;
    }

    private Optional<Entity> getEntity(String entityName) {
        Optional<Entity> result = config.getEntities().stream()
                .filter(e -> e.getBaseName().equals(entityName))
                .findFirst();
        if (result.isEmpty()) {
            result = config.getGroupings().stream()
                    .flatMap(g -> g.getEntities().stream())
                    .filter(e -> e.getBaseName().equals(entityName))
                    .findFirst();
        }
        return result;
    }

    private boolean completeFilterFields() {
        return completeEntityIterator(this::completeFilterFields);
    }

    private boolean completeFilterFields(List<Entity> entities) {
        return entities.stream().allMatch(e -> completeFilterFieldsAtRef(e.getReferences()) && completeFilterFieldsAtRef(e.getParentRefs()));
    }

    private boolean completeFilterFieldsAtRef(List<Reference> references) {
        return references.stream().allMatch(this::completeFilterFields);
    }

    private boolean completeFilterFields(Reference reference) {
        if (reference.getFilterField() == null) {
            return true;
        }
        Entity filterOwnerEntity = reference.isReverse() ? reference.getParent() : reference.getRealTargetEntity();
        Optional<Field> filterField = filterOwnerEntity.getFields().stream()
                .filter(f -> f.getFieldName().equals(reference.getFilterField()))
                .findFirst();

        if (filterField.isEmpty() || Boolean.FALSE.equals(filterField.get().getIsTypeEnum())) {
            if (!reference.isReverse()) {
                logger.error(String.format("The filter field %s of reference %s could not be found at entity %s or is not an enum type"
                        , reference.getFilterField(), reference.getReferenceName(), reference.getTargetEntity()));
            }
            return false;
        }

        filterField.ifPresent(reference::setRealFilterField);

        if (!reference.isReverse() && filterField.get().getDaoInfo() != null && Boolean.TRUE.equals(filterField.get().getDaoInfo().getNullable())) {
            logger.warn(String.format("The filter field %s at %s is marked as nullable. This is not allowed and will be set to not nullable."
                    , filterField.get(), reference.getTargetEntity()));
            filterField.get().getDaoInfo().setNullable(Boolean.FALSE);
        }

        return true;
    }

    /**
     * Completes the indices of an entity
     *
     * @param entity entity to complete
     * @return {@code true} if completion was successful
     */
    private boolean completeIndices(Entity entity) {
        boolean result = true;
        for (Index i : entity.getIndices()) {
            i.setFields(new ArrayList<>());
            for (String s : i.getFieldList().split(",")) {
                result = addFieldAtIndex(s, entity, i) && result;
            }
        }
        return result;
    }

    /**
     * Adds a field to an index
     *
     * @param fieldListPart field from the field list
     * @param entity        owner of the index and the referenced fields
     * @param index         owner of the field list
     * @return {@code true} if completion was successful
     */
    private boolean addFieldAtIndex(String fieldListPart, Entity entity, Index index) {
        FieldSorting fieldSorting = new FieldSorting();
        fieldSorting.setField(null);
        String fieldName = fieldListPart.trim();
        if (fieldName.endsWith(" ASC")) {
            fieldName = fieldName.substring(0, fieldName.length() - 3).trim();
        }
        if (fieldName.endsWith(" DESC")) {
            fieldSorting.setAscending(false);
            fieldName = fieldName.substring(0, fieldName.length() - 4).trim();
        }
        if (fieldName.equalsIgnoreCase(Id.class.getSimpleName())) {
            fieldSorting.setField(new Field());
            fieldSorting.getField().setFieldName(Id.class.getSimpleName());
            fieldSorting.getField().setType(Long.class.getSimpleName());
        } else {
            for (Field f : entity.getFields()) {
                if (f.getFieldName().equalsIgnoreCase(fieldName)) {
                    fieldSorting.setField(f);
                    break;
                }
            }
        }
        if (fieldSorting.getField() == null) {
            logger.error(String.format("The field %s at index %s could not be found at entity %s."
                    , fieldName, index.getIndexName(), entity.getBaseName()));
            return false;
        }
        index.getFields().add(fieldSorting);
        return true;
    }

    private boolean completeEntityIterator(EntitiesCompleter entitiesCompleter) {
        if (!entitiesCompleter.complete(config.getEntities())) {
            return false;
        }
        for (Grouping g : config.getGroupings()) {
            if (!entitiesCompleter.complete(g.getEntities())) {
                return false;
            }
        }
        return true;
    }

    @FunctionalInterface
    private interface EntitiesCompleter {
        boolean complete(List<Entity> entities);
    }
}
