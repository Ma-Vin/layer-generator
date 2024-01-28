package de.ma_vin.util.layer.generator.config.loader;

import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.logging.ILogWrapper;

import java.util.List;

/**
 * Sets the owner entity at fields of a {@link de.ma_vin.util.layer.generator.config.elements.Config}
 */
public class FieldCompleter extends AbstractCompleter {

    public FieldCompleter(ILogWrapper logger) {
        super("field owner", 2, logger);
    }

    @Override
    protected boolean complete() {
        return completeEntityIterator(this::completeFieldOwner);
    }

    private boolean completeFieldOwner(List<Entity> entityList) {
        entityList.forEach(e -> e.getFields().forEach(f -> f.setParentEntity(e)));
        return true;
    }

}
