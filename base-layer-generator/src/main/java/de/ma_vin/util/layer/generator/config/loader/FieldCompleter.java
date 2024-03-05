package de.ma_vin.util.layer.generator.config.loader;

import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.config.elements.fields.Field;
import de.ma_vin.util.layer.generator.config.elements.references.Reference;
import de.ma_vin.util.layer.generator.logging.ILogWrapper;

import java.util.List;
import java.util.Optional;

/**
 * Completes the fields of a given {@link de.ma_vin.util.layer.generator.config.elements.Config}
 */
public class FieldCompleter extends AbstractCompleter {

    public FieldCompleter(ILogWrapper logger) {
        super("field owner and filter", 5, logger);
    }

    @Override
    protected boolean complete() {
        return completeEntityIterator(this::completeFieldOwner) && completeEntityIterator(this::completeFilterFields);
    }

    /**
     * Sets the parent entity at all fields
     *
     * @param entityList entities whose fields should get an owner
     * @return always {@code true}
     */
    private boolean completeFieldOwner(List<Entity> entityList) {
        entityList.forEach(e -> e.getFields().forEach(f -> f.setParentEntity(e)));
        return true;
    }

    /**
     * Completes the filtering fields at all references of given entities
     *
     * @param entities the entities whose references should be completed for field filtering
     * @return {@code true} if all filter fields could be completed. {@code false} at any failure.
     */
    private boolean completeFilterFields(List<Entity> entities) {
        return entities.stream().allMatch(e -> completeFilterFieldsAtRef(e.getReferences()) && completeFilterFieldsAtRef(e.getParentRefs()));
    }

    /**
     * Completes the filtering fields at given references
     *
     * @param references references whose filtering fields should be completed
     * @return {@code true} if all filter fields could be completed. {@code false} at any failure.
     */
    private boolean completeFilterFieldsAtRef(List<Reference> references) {
        return references.stream().allMatch(this::completeFilterFields);
    }

    /**
     * Completes the filtering fields a given reference
     *
     * @param reference reference whose filtering fields should be completed
     * @return {@code true} if the filter field could be completed. {@code false} at any failure.
     */
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

}
