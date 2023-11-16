package de.ma_vin.util.layer.generator.config;

import java.util.List;

/**
 * Utility class to validate configuration elements {@link de.ma_vin.util.layer.generator.config.elements}
 */
public class ConfigElementsUtil {

    /**
     * private constructor since this class provides static methods only
     */
    private ConfigElementsUtil() {
    }

    /**
     * Checks if a required value is given or not
     *
     * @param value       value to check
     * @param messages    list where to a new messages at
     * @param messageName the name of the value to add at the message
     * @return {@code true} if the value is not {@code null} and not empty.
     * Otherwise {@code false}
     */
    public static boolean validateRequired(String value, List<String> messages, String messageName) {
        if (value == null) {
            messages.add(String.format("The value %s is required, but was null", messageName));
            return false;
        }
        if (value.trim().isEmpty()) {
            messages.add(String.format("The value %s is required, but was an empty string", messageName));
            return false;
        }
        return true;
    }

    /**
     * Checks if a not required not {@code null} value has a correct format
     *
     * @param value       value to check
     * @param messages    list where to a new messages at
     * @param messageName the name of the value to add at the message
     * @return {@code true} if the value is {@code null} or not empty.
     * Otherwise {@code false}
     */
    public static boolean validateNonRequired(String value, List<String> messages, String messageName) {
        if (value == null || !value.trim().isEmpty()) {
            return true;
        }
        messages.add(String.format("The value %s is not required, but was an empty string", messageName));
        return false;
    }

    /**
     * Checks if a value valid with respect if it is required or not
     *
     * @param value       value to check
     * @param required    indicator to check the value in a way that the value is required or not
     * @param messages    list where to a new messages at
     * @param messageName the name of the value to add at the message
     * @return {@code true} if the value is required and fulfills {@link ConfigElementsUtil#validateRequired(String, List, String)}
     * or if not required it fulfills {@link ConfigElementsUtil#validateNonRequired(String, List, String)}.
     * Otherwise {@code false}
     */
    public static boolean validate(String value, boolean required, List<String> messages, String messageName) {
        return required ? validateRequired(value, messages, messageName) : validateNonRequired(value, messages, messageName);
    }

    /**
     * Validates a list of config items
     *
     * @param listToCheck the list with items to check
     * @param messages    list where to a new messages at
     * @param validator   validator to check an element of the list
     * @param <T>         type of the config element
     * @return {@code true} if the list of elements is {@code null}, empty or all elements are valid with respect to {@code validator}
     * Otherwise {@code false}
     */
    public static <T> boolean validateList(List<T> listToCheck, List<String> messages, ElementValidator<T> validator) {
        return listToCheck == null || listToCheck.stream().allMatch(a -> validator.validate(a, messages));
    }

    /**
     * Checks if all entry of a name list are contained in a reference list (with respect to their names)
     *
     * @param namesToCheck  list of names to check
     * @param messages      list where to a new messages at
     * @param listName      the property name of the name list
     * @param referenceList list of elements whose names should be equal to entries of {@code namesToCheck}
     * @param nameProvider  provides the name of an element
     * @param <T>           type of the config element
     * @return {@code true} if namesToCheck is {@code null} or all names are contained at {@code referenceList}
     * Otherwise {@code false}
     */
    public static <T> boolean validateNamesCompareToElementList(List<String> namesToCheck, List<String> messages, String listName, List<T> referenceList
            , ElementNameProvider<T> nameProvider) {
        return namesToCheck == null || namesToCheck.stream().allMatch(n -> {
            if (n.trim().isEmpty()) {
                messages.add(String.format("An element at %s is empty", listName));
                return false;
            }
            if (referenceList.stream().noneMatch(e -> n.equals(nameProvider.getName(e)))) {
                messages.add(String.format("The element %s at list %s was not found at the reference list", n, listName));
                return false;
            }
            return true;
        });
    }

    /**
     * Functional interface to validate an element from a list
     *
     * @param <T> type of the config element
     */
    @FunctionalInterface
    public interface ElementValidator<T> {
        /**
         * checks if an element is valid or not
         *
         * @param element  the element to check
         * @param messages list where to a new messages at
         * @return {@code true} if the element is valid. Otherwise {@code false}
         */
        boolean validate(T element, List<String> messages);
    }

    /**
     * Functional interface to provide a name of an element
     *
     * @param <T> type of the config element
     */
    @FunctionalInterface
    public interface ElementNameProvider<T> {
        /**
         * Determines the name of the element
         *
         * @param element the element whose name is asked for
         * @return the name of the element
         */
        String getName(T element);
    }
}
