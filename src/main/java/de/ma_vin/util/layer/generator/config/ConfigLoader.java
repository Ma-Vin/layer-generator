package de.ma_vin.util.layer.generator.config;

import de.ma_vin.util.layer.generator.config.elements.*;
import lombok.Data;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.maven.plugin.logging.Log;
import org.xml.sax.SAXException;

@Data
public class ConfigLoader {
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

    public boolean load() {
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
            return validate() && complete();
        } catch (JAXBException | FileNotFoundException | SAXException e) {
            logger.error("Could not load config file:");
            logger.error(e);
            return false;
        }
    }

    private boolean validate() {
        return config.isValid();
    }

    public boolean complete() {
        removeNullLists();
        completeOwner();
        if (!completeReferences()) {
            logger.error("Completion of references could not be completed");
            return false;
        }
        if (!completeParentEntities()) {
            logger.error("Completion of parents could not be completed");
            return false;
        }
        if (!completeFilterFields()) {
            logger.error("Completion of filter fields at references could not be completed");
            return false;
        }
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
            e.setParentRefs(new ArrayList<>());
        });
    }

    private void completeOwner() {
        config.getGroupings().forEach(g -> g.getEntities().forEach(e -> e.setGrouping(g)));
        completeEntities(this::completeFieldOwner);
    }

    private boolean completeFieldOwner(List<Entity> entityList) {
        entityList.forEach(e -> e.getFields().forEach(f -> f.setParentEntity(e)));
        return true;
    }

    private boolean completeReferences() {
        return completeEntities(this::completeReferencesOfEntities);
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
        Reference parentRef = new Reference();
        parentRef.setTargetEntity(actualEntity.getBaseName());
        parentRef.setRealTargetEntity(actualEntity);
        parentRef.setParent(targetEntity);
        parentRef.setIsOwner(actualReference.isOwner());
        parentRef.setIsList(actualReference.isList());
        parentRef.setReferenceName(actualReference.getReferenceName());
        parentRef.setReverse(true);

        targetEntity.getParentRefs().add(parentRef);


        actualReference.setParent(actualEntity);
        actualReference.setRealTargetEntity(targetEntity);
    }

    private boolean completeParentEntities() {
        return completeEntities(this::completeParentEntities);
    }

    private boolean completeParentEntities(List<Entity> entities) {
        boolean result = true;
        for (Entity e : entities) {
            if (e.getParent() != null && !e.getParent().trim().isEmpty()) {
                result = result && completeParentEntities(e, e.getParent().trim());
            }
        }
        return result;
    }

    private boolean completeParentEntities(Entity actualEntity, String parentName) {
        Optional<Entity> entity = getEntity(parentName);
        if (entity.isPresent() && Boolean.TRUE.equals(entity.get().getIsAbstract())) {
            actualEntity.setRealParent(entity.get());
            return true;
        }
        logger.error(String.format("The parent %s of entity %s could not be found", parentName, actualEntity.getBaseName()));
        return false;
    }

    Optional<Entity> getEntity(String entityName) {
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
        return completeEntities(this::completeFilterFields);
    }

    private boolean completeFilterFields(List<Entity> entities) {
        boolean result = true;
        for (Entity e : entities) {
            for (Reference ref : e.getReferences()) {
                if (ref.getFilterField() == null) {
                    continue;
                }
                result = completeFilterFields(ref) && result;
            }
        }
        return result;
    }

    private boolean completeFilterFields(Reference reference) {
        Optional<Field> filterField = reference.getRealTargetEntity().getFields().stream()
                .filter(f -> f.getFieldName().equals(reference.getFilterField()))
                .findFirst();

        if (filterField.isEmpty() || Boolean.FALSE.equals(filterField.get().getIsTypeEnum())) {
            logger.error(String.format("The filter field %s of reference %s could not be found at entity %s or is not an enum type"
                    , reference.getFilterField(), reference.getReferenceName(), reference.getTargetEntity()));
            return false;
        }
        reference.setRealFilterField(filterField.get());

        if (filterField.get().getDaoInfo() != null && Boolean.TRUE.equals(filterField.get().getDaoInfo().getNullable())) {
            logger.warn(String.format("The filter field %s at %s is marked as nullable. This is not allowed and will be set to not nullable."
                    , filterField.get(), reference.getTargetEntity()));
            filterField.get().getDaoInfo().setNullable(Boolean.FALSE);
        }

        return true;
    }

    private boolean completeEntities(EntitiesCompleter entitiesCompleter) {
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
