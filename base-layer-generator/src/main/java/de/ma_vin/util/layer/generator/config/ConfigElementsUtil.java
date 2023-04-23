package de.ma_vin.util.layer.generator.config;

import java.util.List;

public class ConfigElementsUtil {

    private ConfigElementsUtil() {
    }

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

    public static boolean validateNonRequired(String value, List<String> messages, String messageName) {
        if (value == null || !value.trim().isEmpty()) {
            return true;
        }
        messages.add(String.format("The value %s is not required, but was an empty string", messageName));
        return false;
    }

    public static boolean validate(String value, boolean required, List<String> messages, String messageName) {
        return required ? validateRequired(value, messages, messageName) : validateNonRequired(value, messages, messageName);
    }
}
