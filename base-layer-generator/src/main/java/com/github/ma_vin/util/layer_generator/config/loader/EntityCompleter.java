package com.github.ma_vin.util.layer_generator.config.loader;

import com.github.ma_vin.util.layer_generator.config.elements.Config;
import com.github.ma_vin.util.layer_generator.config.elements.Entity;
import com.github.ma_vin.util.layer_generator.config.elements.Models;
import com.github.ma_vin.util.layer_generator.config.elements.fields.Field;
import com.github.ma_vin.util.layer_generator.logging.ILogWrapper;

import java.util.List;
import java.util.Optional;

/**
 * Completes the {@link Entity}s at a given {@link Config}
 */
public class EntityCompleter extends AbstractCompleter {

    public EntityCompleter(ILogWrapper logger) {
        super("entities", 4, logger);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean complete() {
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

}
