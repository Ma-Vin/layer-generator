package com.github.ma_vin.util.layer_generator.config.loader;

import com.github.ma_vin.util.layer_generator.config.elements.Entity;
import com.github.ma_vin.util.layer_generator.config.elements.Index;
import com.github.ma_vin.util.layer_generator.config.elements.fields.Field;
import com.github.ma_vin.util.layer_generator.config.elements.fields.FieldSorting;
import com.github.ma_vin.util.layer_generator.logging.ILogWrapper;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Completes the indices of a given configuration
 */
public class IndicesCompleter extends AbstractCompleter {

    public IndicesCompleter(ILogWrapper logger) {
        super("indices", 6, logger);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean complete() {
        return completeEntityIterator(this::completeIndices);
    }

    /**
     * Completes the indices of a entity list
     *
     * @param entities entities to complete
     * @return {@code true} if completion was successful
     */
    private boolean completeIndices(List<Entity> entities) {
        boolean result = true;
        for (Entity entity : entities) {
            for (Index i : entity.getIndices()) {
                i.setFields(new ArrayList<>());
                for (String s : i.getFieldList().split(",")) {
                    result = addFieldAtIndex(s, entity, i) && result;
                }
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
}
