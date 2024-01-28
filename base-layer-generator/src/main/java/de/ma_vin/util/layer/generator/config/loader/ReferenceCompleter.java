package de.ma_vin.util.layer.generator.config.loader;

import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.config.elements.references.Reference;
import de.ma_vin.util.layer.generator.logging.ILogWrapper;

import java.util.List;
import java.util.Optional;

/**
 * Completes the {@link Reference}s at a given {@link de.ma_vin.util.layer.generator.config.elements.Config}
 */
public class ReferenceCompleter extends AbstractCompleter {

    public ReferenceCompleter(ILogWrapper logger) {
        super("references", 2, logger);
    }

    @Override
    protected boolean complete() {
        return completeEntityIterator(this::completeReferencesOfEntities);
    }

    /**
     * Iterates through the given entities and completes them  by determining the real target. The real target gets a reverse orientated parent reference also.
     *
     * @param entityList the list of entities
     * @return {@code true} if the completion was successful. {@code false} if any target entity does not exist.
     */
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


    /**
     * Completes a given reference by determining the real target. The real target gets a reverse orientated parent reference also.
     *
     * @param targetEntityName the name of the reference target entity
     * @param actualEntity     the entity where the reference is starting from
     * @param actualReference  the reference
     * @return {@code true} if the completion was successful. {@code false} if the target entity does not exist.
     */
    private boolean completeReferences(String targetEntityName, Entity actualEntity, Reference actualReference) {
        Optional<Entity> entity = getEntity(targetEntityName);
        if (entity.isPresent()) {
            addParentReferenceAndTarget(actualEntity, actualReference, entity.get());
            return true;
        }
        logger.error(String.format("The target of reference %s could not be found", actualReference.getTargetEntity()));
        return false;
    }

    /**
     * Creates a reverse reference from target to actual entity and set parent and real target entities at the original reference.
     *
     * @param actualEntity    the entity where the reference is starting from
     * @param actualReference the reference
     * @param targetEntity    the target entity of the reference
     */
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
}
