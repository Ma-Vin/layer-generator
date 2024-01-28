package de.ma_vin.util.layer.generator.config.loader;

import de.ma_vin.util.layer.generator.config.elements.Config;
import de.ma_vin.util.layer.generator.config.elements.Entity;

import java.util.List;

/**
 * Sets the owner entity at fields of a {@link Config}
 */
public class FieldCompleter extends AbstractCompleter {

    public FieldCompleter() {
        super("field owner", 2);
    }

    @Override
    public boolean complete(Config config) {
        return completeEntityIterator(this::completeFieldOwner, config);
    }

    private boolean completeFieldOwner(List<Entity> entityList) {
        entityList.forEach(e -> e.getFields().forEach(f -> f.setParentEntity(e)));
        return true;
    }

}
