package com.github.ma_vin.util.layer_generator.config.loader;

import com.github.ma_vin.util.layer_generator.config.elements.Entity;
import com.github.ma_vin.util.layer_generator.config.elements.Version;
import com.github.ma_vin.util.layer_generator.config.elements.references.Reference;
import com.github.ma_vin.util.layer_generator.logging.ILogWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Completes all versions and transforms them to an entity with an actual version (whose references considering version entities also)
 */
public class VersionCompleter extends AbstractCompleter {

    public VersionCompleter(ILogWrapper logger) {
        super("versions", 7, logger);
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

    /**
     * Completes the references of a given version
     *
     * @param version the version whose references should be completed
     * @return {@code true} if completion was successful. {@code false} at any failure.
     */
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
        versionEntities.forEach(this::determineVersionParentReferences);
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
                    updatedReference.setParent(entity);
                    entity.getActualVersion()
                            .determineReferenceTargetVersion(r)
                            .ifPresentOrElse(
                                    version -> updatedReference.setRealTargetEntity(version.getVersionEntity())
                                    , () -> addParentReferenceToNonVersionedEntity(updatedReference)
                            );
                    return updatedReference;
                }).toList();

        entity.setReferences(references);
    }

    /**
     * Adds a parent reference to a non versioned target of a versioned reference
     *
     * @param versionedReference reference whose source is a versioned entity, but not its target
     */
    private void addParentReferenceToNonVersionedEntity(Reference versionedReference) {
        if (versionedReference.getParent().getActualVersion() == null || versionedReference.getRealTargetEntity().getActualVersion() != null) {
            return;
        }

        Reference parentReference = versionedReference.copy();

        parentReference.setTargetEntity(versionedReference.getParent().getBaseName());
        parentReference.setRealTargetEntity(versionedReference.getParent());
        parentReference.setParent(versionedReference.getRealTargetEntity());
        parentReference.setReverse(true);

        versionedReference.getRealTargetEntity().getParentRefs().add(parentReference);
    }

    /**
     * Determines and set the parent references of a versioned entity
     *
     * @param entity the version entity whose parent references should be determined
     */
    private void determineVersionParentReferences(Entity entity) {
        List<Reference> parentReferences = determineReferenceToVersionEntity(entity).stream()
                .map(this::createParentReference).collect(Collectors.toList());

        entity.setParentRefs(parentReferences);
    }

    private Reference createParentReference(Reference referenceToInvert) {
        Reference parentReference = referenceToInvert.copy();

        parentReference.setTargetEntity(referenceToInvert.getParent().getBaseName());
        parentReference.setRealTargetEntity(referenceToInvert.getParent());
        parentReference.setParent(referenceToInvert.getRealTargetEntity());
        parentReference.setReverse(true);

        return parentReference;
    }

    /**
     * Determines all references pointing to a versioned entity
     *
     * @param entity the target of the references
     * @return A list containing all references which pointing to {@code entity}
     */
    private List<Reference> determineReferenceToVersionEntity(Entity entity) {
        List<Reference> relevantReverences = new ArrayList<>();
        completeEntityIterator(entities -> {
                    entities.forEach(e -> e.getVersions().forEach(
                            v -> v.getVersionEntity().getReferences().stream()
                                    .filter(r -> r.getRealTargetEntity().getBaseName().equals(entity.getBaseName()))
                                    .filter(r -> r.getRealTargetEntity().getActualVersion() != null)
                                    .filter(r -> v.determineReferenceTargetVersion(r).stream()
                                            .anyMatch(v2 -> v2.getVersionId().equals(entity.getActualVersion().getVersionId()))
                                    )
                                    .forEach(relevantReverences::add)
                    ));
                    return true;
                }
        );
        return relevantReverences;
    }
}
