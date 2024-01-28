package de.ma_vin.util.layer.generator.config.loader;

import de.ma_vin.util.layer.generator.config.elements.*;
import de.ma_vin.util.layer.generator.config.elements.fields.Field;
import de.ma_vin.util.layer.generator.config.elements.fields.FieldSorting;
import de.ma_vin.util.layer.generator.config.elements.references.Reference;
import de.ma_vin.util.layer.generator.logging.ILogWrapper;
import lombok.Data;

import jakarta.persistence.Id;

import java.io.*;
import java.util.*;

@Data
public class ConfigLoader {

    private ILogWrapper logger;
    private ConfigFileLoader fileLoader;

    private SortedSet<AbstractCompleter> completer = new TreeSet<>(Comparator.comparingInt(AbstractCompleter::getExecutionPriority));

    private Config config;

    public ConfigLoader(File configFile, ILogWrapper logger) {
        this(new ConfigFileLoader(configFile, logger), logger);
    }

    public ConfigLoader(File configFile, ILogWrapper logger, File schemaFile) {
        this(new ConfigFileLoader(configFile, schemaFile, logger), logger);
    }

    public ConfigLoader(ConfigFileLoader configLoader, ILogWrapper logger) {
        this.logger = logger;
        this.fileLoader = configLoader;

        completer.add(new ListCompleter(logger));
        completer.add(new FieldCompleter(logger));
        completer.add(new ReferenceCompleter(logger));
    }

    /**
     * Loads the configuration from {@code configFile} with formats {@link ConfigFileLoader#XML_FILE_ENDING}
     * , {@link ConfigFileLoader#YAML_FILE_ENDING}, {@link ConfigFileLoader#YML_FILE_ENDING} or {@link ConfigFileLoader#JSON_FILE_ENDING}
     *
     * @return {@code true} the file could be parsed to {@link Config}, validated and completed. Otherwise {@code false}
     */
    public boolean load() {
        return loadFile() && validate() && complete();
    }

    private boolean loadFile() {
        Optional<Config> loadedConfig = fileLoader.load();
        if (loadedConfig.isEmpty()) {
            return false;
        }
        config = loadedConfig.get();
        return true;
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
        for (AbstractCompleter c : completer) {
            if (!c.complete(config)) {
                logger.error(c.getFailMessage());
                return false;
            }
        }
        if (!completeEntities()) {
            logger.error("Completion of parents could not be completed");
            return false;
        }
        if (!completeFilterFields()) {
            logger.error("Completion of filter fields at references could not be completed");
            return false;
        }
        if (!completeVersions()) {
            logger.error("Completion of versions could not be completed");
            return false;
        }
        config.setUseIdGenerator(config.getIdGeneratorPackage() != null && config.getIdGeneratorClass() != null);
        return true;
    }

    private boolean completeEntities() {
        config.getGroupings().forEach(g -> g.getEntities().forEach(e -> e.setGrouping(g)));
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
     * Completes all versions and transforms them to an entity with an actual version (whose references considering version entities also)
     *
     * @return {@code true} if completion was successful
     */
    private boolean completeVersions() {
        if (!completeEntityIterator(this::completeVersions)) {
            return false;
        }
        transformVersionsToEntities();
        return true;
    }

    /**
     * Completes the versions of an entity list
     *
     * @param entities list of entities to complete
     * @return {@code true} if completion was successful
     */
    private boolean completeVersions(List<Entity> entities) {
        return entities.stream().allMatch(this::completeVersions);
    }

    /**
     * Completes the versions of an entity
     *
     * @param entity entity to complete
     * @return {@code true} if completion was successful
     */
    private boolean completeVersions(Entity entity) {
        if (entity.getVersions() == null) {
            entity.setVersions(Collections.emptyList());
            return true;
        }
        boolean result = true;
        for (Version v : entity.getVersions()) {
            v.setParentEntity(entity);
            if (v.getAddedFields() == null) {
                v.setAddedFields(Collections.emptyList());
            }
            if (v.getRemovedFieldNames() == null) {
                v.setRemovedFieldNames(Collections.emptyList());
            }
            if (v.getAddedReferences() == null) {
                v.setAddedReferences(Collections.emptyList());
            }
            if (v.getRemovedReferenceNames() == null) {
                v.setRemovedReferenceNames(Collections.emptyList());
            }
            v.setBaseVersion(v.determineBaseVersion(entity));
            v.setFields(v.determineFields(entity));
            v.setReferences(v.determineReferences(entity));
            v.generateVersionName();

            result = completeVersionReferences(v) && result;
        }
        return result;
    }

    private boolean completeVersionReferences(Version version) {
        return version.getReferences().stream()
                .filter(r -> r.getRealTargetEntity() == null)
                .allMatch(r -> {
                            Optional<Entity> targetEntity = getEntity(r.getTargetEntity());
                            if (targetEntity.isEmpty()) {
                                logger.error(String.format("could not find target entity %s of version reference %s at version %s"
                                        , r.getTargetEntity(), r.getReferenceName(), version.getVersionName()));
                                return false;
                            }
                            r.setRealTargetEntity(targetEntity.get());
                            return true;
                        }
                );
    }

    /**
     * Transform the existing versions at base entities to an entity with an actual version.
     * All references and parent references will be created in a way that they point to a version entity (The references at {@link Version} do not so)
     */
    private void transformVersionsToEntities() {
        List<Entity> versionEntities = new ArrayList<>();
        completeEntityIterator(entities -> {
            entities.forEach(e ->
                    e.getVersions().forEach(v -> {
                        Entity versionEntity = e.copyForVersion(v);
                        versionEntities.add(versionEntity);
                        v.setVersionEntity(versionEntity);
                    })
            );
            return true;
        });

        versionEntities.forEach(this::transformVersionReferences);
    }

    /**
     * Transforms the given references (which are pointing to non version entities) of a version entity to a new reference which points to a version entity also.
     *
     * @param entity the version entity whose references should be replaced
     */
    private void transformVersionReferences(Entity entity) {
        List<Reference> references = entity.getReferences().stream()
                .map(r -> {
                    Reference updatedReference = r.copy();
                    r.setParent(entity);
                    entity.getActualVersion()
                            .determineReferenceTargetVersion(r)
                            .ifPresent(version -> r.setRealTargetEntity(version.getVersionEntity()));
                    return updatedReference;
                }).toList();

        entity.setReferences(references);

        List<Reference> parentReferences = entity.getParentRefs().stream()
                .map(r -> {
                    Reference updatedReference = r.copy();
                    updatedReference.setParent(entity);
                    r.getRealTargetEntity().getReferences().stream()
                            .filter(r2 -> r2.getReferenceName().equals(r.getReferenceName()))
                            .findFirst()
                            .flatMap(r2 -> r.getRealTargetEntity().getVersions().stream()
                                    .filter(v -> v.determineReferenceTargetVersion(r2)
                                            .filter(v2 -> v2.getVersionEntity().equals(entity))
                                            .isPresent())
                                    .findFirst())
                            .ifPresent(v -> updatedReference.setRealTargetEntity(v.getVersionEntity()));

                    return updatedReference;
                }).toList();

        entity.setParentRefs(parentReferences);
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
