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
}
