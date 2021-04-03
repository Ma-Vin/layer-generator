package de.ma_vin.util.layer.generator.config;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static boolean validateRequired(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean validateNonRequired(String value) {
        return value == null || !value.trim().isEmpty();
    }

    public static boolean validate(String value, boolean required) {
        return required ? validateRequired(value) : validateNonRequired(value);
    }
}
