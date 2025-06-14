package com.github.ma_vin.util.layer_generator.config.loader;

import com.github.ma_vin.util.layer_generator.config.elements.Config;
import com.github.ma_vin.util.layer_generator.config.elements.Entity;
import com.github.ma_vin.util.layer_generator.config.elements.Version;
import com.github.ma_vin.util.layer_generator.logging.ILogWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Completes list properties of a {@link Config} by removing {@code null} lists
 */
public class ListCompleter extends AbstractCompleter {

    public ListCompleter(ILogWrapper logger) {
        super("null list removing", 1, logger);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean complete() {
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

        return completeEntityIterator(this::removeNullList);
    }

    /**
     * Removes {@code null} lists at a given list of entities
     *
     * @param entities the list of entities whose {@code null} lists should be removed
     * @return always {@code true} to fulfill {@link AbstractCompleter.ICompleterIterator}
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
            if (e.getVersions() == null) {
                e.setVersions(Collections.emptyList());
            } else {
                removeVersionsNullList(e.getVersions());
            }
            e.setParentRefs(new ArrayList<>());
        });
        return true;
    }

    /**
     * Removes {@code null} lists at a given list of versions
     *
     * @param versions the list of versions whose {@code null} lists should be removed
     */
    private void removeVersionsNullList(List<Version> versions) {
        versions.forEach(v -> {
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
        });
    }
}
