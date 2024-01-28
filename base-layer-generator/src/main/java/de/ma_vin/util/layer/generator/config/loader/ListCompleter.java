package de.ma_vin.util.layer.generator.config.loader;

import de.ma_vin.util.layer.generator.config.elements.Config;
import de.ma_vin.util.layer.generator.config.elements.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Completes list properties of a {@link Config} by removing {@code null} lists
 */
public class ListCompleter extends AbstractCompleter {
    @Override
    public boolean complete(Config config) {
        if (config.getEntities() == null) {
            config.setEntities(new ArrayList<>());
        }

        if (config.getGroupings() == null) {
            config.setGroupings(new ArrayList<>());
        } else {
            config.getGroupings().stream()
                    .filter(g -> g.getEntities() == null)
                    .forEach(g -> g.setEntities(new ArrayList<>()));
        }

        return completeEntityIterator(this::removeNullList, config);
    }

    /**
     * Removes {@code null} lists at a given list of entities
     *
     * @param entities the list of entities whose {@code null} lists should be removed
     * @return always {@code true} to fulfill {@link de.ma_vin.util.layer.generator.config.loader.AbstractCompleter.ICompleterIterator}
     */
    private boolean removeNullList(List<Entity> entities) {
        entities.forEach(e -> {
            if (e.getReferences() == null) {
                e.setReferences(new ArrayList<>());
            }
            if (e.getFields() == null) {
                e.setFields(new ArrayList<>());
            }
            if (e.getIndices() == null) {
                e.setIndices(new ArrayList<>());
            }
            e.setParentRefs(new ArrayList<>());
        });
        return true;
    }
}
