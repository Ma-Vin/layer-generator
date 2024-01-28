package de.ma_vin.util.layer.generator.config.loader;

import de.ma_vin.util.layer.generator.config.elements.Config;
import de.ma_vin.util.layer.generator.config.elements.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Interface which describes a completer of a {@link de.ma_vin.util.layer.generator.config.elements.Config}
 */
@Data
@AllArgsConstructor
public abstract class AbstractCompleter {

    private final String failMessageTextPart;
    private final int executionPriority;

    /**
     * Completes a given configuration for a concrete aspect
     *
     * @param config the configuration to complete
     * @return {@code true} if completion was successful. {@code false} otherwise
     */
    public abstract boolean complete(Config config);

    /**
     * @return a fail message text
     */
    public String getFailMessage() {
        return String.format("Completion of %s could not be finalized successfully", failMessageTextPart);
    }

    protected boolean completeEntityIterator(ICompleterIterator<Entity> entitiesCompleter, Config config) {
        return entitiesCompleter.complete(config.getEntities())
                && config.getGroupings().stream().allMatch(g -> entitiesCompleter.complete(g.getEntities()));
    }

    /**
     * Functional interface to provide an iterator, which completes all elements of a given list
     *
     * @param <T> The type of elements
     */
    @FunctionalInterface
    public interface ICompleterIterator<T> {
        /**
         * Completes all elements of a given list
         *
         * @param elements The list of elements to complete
         * @return {@code true} if all elements could be completed. {@code false} at any failure.
         */
        boolean complete(List<T> elements);
    }
}
