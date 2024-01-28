package de.ma_vin.util.layer.generator.config.loader;

import de.ma_vin.util.layer.generator.config.elements.*;
import de.ma_vin.util.layer.generator.config.elements.references.Reference;
import de.ma_vin.util.layer.generator.logging.ILogWrapper;
import lombok.Data;


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
        completer.add(new IdGeneratorCompleter(logger));
        completer.add(new FieldCompleter(logger));
        completer.add(new ReferenceCompleter(logger));
        completer.add(new EntityCompleter(logger));
        completer.add(new IndicesCompleter(logger));
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
        if (!completeVersions()) {
            logger.error("Completion of versions could not be completed");
            return false;
        }
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
