package de.ma_vin.util.layer.generator.config.loader;

import de.ma_vin.util.layer.generator.config.elements.Config;
import de.ma_vin.util.layer.generator.config.elements.Entity;
import com.github.ma_vin.util.layer_generator.logging.ILogWrapper;
import lombok.Data;

import java.util.List;
import java.util.Optional;

/**
 * Interface which describes a completer of a {@link de.ma_vin.util.layer.generator.config.elements.Config}
 */
@Data
public abstract class AbstractCompleter {

    private final String failMessageTextPart;
    private final int executionPriority;
    protected final ILogWrapper logger;

    protected Config config;

    /**
     * Completes a given configuration for a concrete aspect
     *
     * @return {@code true} if completion was successful. {@code false} otherwise
     */
    protected abstract boolean complete();

    public boolean complete(Config config) {
        this.config = config;
        return complete();
    }

    /**
     * @return a fail message text
     */
    public String getFailMessage() {
        return String.format("Completion of %s could not be finalized successfully", failMessageTextPart);
    }

    protected boolean completeEntityIterator(ICompleterIterator<Entity> entitiesCompleter) {
        return entitiesCompleter.complete(config.getEntities())
                && config.getGroupings().stream().allMatch(g -> entitiesCompleter.complete(g.getEntities()));
    }

    /**
     * Determines an {@link Entity} by its name from the config
     *
     * @param entityName the name of the entity
     * @return {@link Optional} of the entity.
     * {@link Optional#empty()} if there does not exist an entity with the given name
     */
    protected Optional<Entity> getEntity(String entityName) {
        Optional<Entity> result = config.getEntities().stream()
                .filter(e -> e.getBaseName().equals(entityName))
                .findFirst();
        if (result.isEmpty()) {
            result = config.getGroupings().stream()
                    .flatMap(g -> g.getEntities().stream())
                    .filter(e -> e.getBaseName().equals(entityName))
                    .findFirst();
        }
        return result;
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
