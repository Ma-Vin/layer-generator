package de.ma_vin.util.layer.generator.config.loader;

import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.config.elements.Version;
import de.ma_vin.util.layer.generator.config.elements.references.Reference;
import de.ma_vin.util.layer.generator.logging.ILogWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Completes all versions and transforms them to an entity with an actual version (whose references considering version entities also)
 */
public class VersionCompleter extends AbstractCompleter {

    public VersionCompleter(ILogWrapper logger) {
        super("", 7, logger);
    }

    @Override
    protected boolean complete() {
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
        boolean result = true;
        for (Version v : entity.getVersions()) {
            v.setParentEntity(entity);
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
                            .ifPresent(version -> updatedReference.setRealTargetEntity(version.getVersionEntity()));
                    return updatedReference;
                }).toList();

        entity.setReferences(references);

        // TODO: We need the parent references from version. The added are missing and removed are to much.
        List<Reference> parentReferences = entity.getActualVersion().getParentEntity().getParentRefs().stream()
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
                })
                .filter(r -> r.getRealTargetEntity() != null)
                .toList();

        entity.setParentRefs(parentReferences);
    }
}
