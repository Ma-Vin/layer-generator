package de.ma_vin.util.layer.generator.config;

import de.ma_vin.util.layer.generator.logging.ILogWrapper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class to validate configuration elements {@link de.ma_vin.util.layer.generator.config.elements}
 */
public class ConfigElementsUtil {

    public static final String INDENT = "  ";
    public static final List<String> CLASS_TYPE_NAMES_TO_LOG = List.of(String.class.getTypeName(), Boolean.class.getTypeName()
            , Byte.class.getTypeName(), Character.class.getTypeName(), Short.class.getTypeName(), Integer.class.getTypeName()
            , Double.class.getTypeName(), Long.class.getTypeName(), Float.class.getTypeName(), BigDecimal.class.getTypeName());

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
     * Checks if a given list is {@code null} or empty and adds a message to hint list
     *
     * @param listToCheck  list to check
     * @param messages     hint list where to a new messages at
     * @param propertyName name of list property which is checked
     * @param reason       reason why this property has to be {@code null} or zero size
     * @param <T>          type of the config element at list
     * @return {@code true} if the list of elements is {@code null} or empty.
     * Otherwise {@code false}
     */
    public static <T> boolean checkNullOrEmpty(List<T> listToCheck, List<String> messages, String propertyName, String reason) {
        if (listToCheck == null || listToCheck.isEmpty()) {
            return true;
        }
        messages.add(String.format("There are entries at %s which is not allowed because of %s", propertyName, reason));
        return false;
    }

    /**
     * Creates an indented string from a template and its arguments
     *
     * @param template the template with placeholders for {@code args}
     * @param indent   number of {@link ConfigElementsUtil#INDENT} to use as prefix
     * @param args     arguments to use with {@code template}
     * @return the indented string
     */
    public static String format(String template, int indent, Object... args) {
        return INDENT.repeat(Math.max(0, indent)) + String.format(template, args);
    }

    /**
     * Logs fields of primitive, enum {@link ConfigElementsUtil#CLASS_TYPE_NAMES_TO_LOG} or {@link IConfigLog} type
     * (or {@link List}s of them)
     *
     * @param configElement the element which is to log
     * @param logger        the logger to use
     * @param indent        number of {@link ConfigElementsUtil#INDENT} to use as prefix
     */
    public static void logConfigElement(Object configElement, ILogWrapper logger, int indent) {
        logger.debug(format("%s:", indent, configElement.getClass().getSimpleName()));
        logConfigFields(configElement, logger, indent + 1);
        logConfigListFields(configElement, logger, indent + 1);
    }

    /**
     * Logs fields of primitive, enum, {@link ConfigElementsUtil#CLASS_TYPE_NAMES_TO_LOG} or {@link IConfigLog} type
     *
     * @param configElement the element which is to log
     * @param logger        the logger to use
     * @param indent        number of {@link ConfigElementsUtil#INDENT} to use as prefix
     */
    private static void logConfigFields(Object configElement, ILogWrapper logger, int indent) {
        List<Field> fieldsToLog = determinePrimitiveFieldsToPrint(configElement.getClass());
        fieldsToLog.addAll(determineComplexFieldsToPrint(configElement.getClass()));

        logConfigFields(configElement, fieldsToLog, logger, indent);
    }

    /**
     * Logs {@link List} fields of primitive, enum or {@link ConfigElementsUtil#CLASS_TYPE_NAMES_TO_LOG} or {@link IConfigLog} type
     *
     * @param configElement the element which is to log
     * @param logger        the logger to use
     * @param indent        number of {@link ConfigElementsUtil#INDENT} to use as prefix
     */
    private static void logConfigListFields(Object configElement, ILogWrapper logger, int indent) {
        List<Field> fieldsToLog = determinePrimitiveListFieldsToPrint(configElement.getClass());
        fieldsToLog.addAll(determineComplexListFieldsToPrint(configElement.getClass()));

        logConfigListFields(configElement, fieldsToLog, logger, indent);
    }

    /**
     * Determines fields of primitive, enum or {@link ConfigElementsUtil#CLASS_TYPE_NAMES_TO_LOG} type
     *
     * @param classToPrint class definition whose fields are asked for
     * @return a list of determined fields
     */
    private static List<Field> determinePrimitiveFieldsToPrint(Class<?> classToPrint) {
        if (areFieldsOrMethodsOfClassIrrelevant(classToPrint)) {
            return Collections.emptyList();
        }
        List<Field> result = Arrays.stream(classToPrint.getDeclaredFields())
                .filter(f -> f.getType().isPrimitive() || f.getType().isEnum()
                        || CLASS_TYPE_NAMES_TO_LOG.contains(f.getType().getTypeName()))
                .collect(Collectors.toList());
        result.addAll(determinePrimitiveFieldsToPrint(classToPrint.getSuperclass()));
        return result;
    }

    /**
     * Determines {@link List} fields of {@link ConfigElementsUtil#CLASS_TYPE_NAMES_TO_LOG} type
     *
     * @param classToPrint class definition whose fields are asked for
     * @return a list of determined fields
     */
    private static List<Field> determinePrimitiveListFieldsToPrint(Class<?> classToPrint) {
        if (areFieldsOrMethodsOfClassIrrelevant(classToPrint)) {
            return Collections.emptyList();
        }
        List<Field> result = Arrays.stream(classToPrint.getDeclaredFields())
                .filter(f -> f.getType().getTypeName().equals(List.class.getTypeName()))
                .filter(f -> CLASS_TYPE_NAMES_TO_LOG.stream()
                        .anyMatch(s -> Arrays.stream(((ParameterizedType) f.getGenericType()).getActualTypeArguments()).anyMatch(a -> a.getTypeName().equals(s))))
                .collect(Collectors.toList());
        result.addAll(determinePrimitiveListFieldsToPrint(classToPrint.getSuperclass()));
        return result;
    }

    /**
     * Determines fields of {@link IConfigLog} type
     *
     * @param classToPrint class definition whose fields are asked for
     * @return a list of determined fields
     */
    private static List<Field> determineComplexFieldsToPrint(Class<?> classToPrint) {
        if (areFieldsOrMethodsOfClassIrrelevant(classToPrint)) {
            return Collections.emptyList();
        }
        List<Field> result = Arrays.stream(classToPrint.getDeclaredFields())
                .filter(f -> IConfigLog.class.isAssignableFrom(f.getType()))
                .collect(Collectors.toList());
        result.addAll(determineComplexFieldsToPrint(classToPrint.getSuperclass()));
        return result;
    }

    /**
     * Determines {@link List} fields of {@link IConfigLog} type
     *
     * @param classToPrint class definition whose fields are asked for
     * @return a list of determined fields
     */
    private static List<Field> determineComplexListFieldsToPrint(Class<?> classToPrint) {
        if (areFieldsOrMethodsOfClassIrrelevant(classToPrint)) {
            return Collections.emptyList();
        }
        List<Field> result = Arrays.stream(classToPrint.getDeclaredFields())
                .filter(f -> f.getType().getTypeName().equals(List.class.getTypeName()))
                .filter(f -> Arrays.stream(((ParameterizedType) f.getGenericType()).getActualTypeArguments()).anyMatch(a -> IConfigLog.class.isAssignableFrom((Class<?>) a)))
                .collect(Collectors.toList());
        result.addAll(determineComplexListFieldsToPrint(classToPrint.getSuperclass()));
        return result;
    }

    /**
     * Checks whether a class or its super class definition is relevant or not
     *
     * @param classToPrint class definition whose fields are asked for
     * @return {@code true} if the class is null or of wrong package. {@code false} otherwise
     */
    private static boolean areFieldsOrMethodsOfClassIrrelevant(Class<?> classToPrint) {
        return classToPrint == null || !classToPrint.getPackage().getName().contains(IConfigLog.class.getPackageName());
    }

    /**
     * Logs a given list of fields
     *
     * @param configElement the element which is to log
     * @param fields        fields of {@code configElement} to log
     * @param logger        the logger to use
     * @param indent        number of {@link ConfigElementsUtil#INDENT} to use as prefix
     */
    private static void logConfigFields(Object configElement, List<Field> fields, ILogWrapper logger, int indent) {
        fields.stream()
                .sorted(Comparator.comparing(Field::getName))
                .forEach(f -> logConfigExecuteAccessible(configElement, f, logger, indent, ConfigElementsUtil::logConfigField));
    }

    /**
     * Logs a given field
     *
     * @param configElement the element which is to log
     * @param field         field of {@code configElement} to log
     * @param logger        the logger to use
     * @param indent        number of {@link ConfigElementsUtil#INDENT} to use as prefix
     * @throws IllegalAccessException if this Field object is enforcing Java language access control and the underlying field is inaccessible.
     */
    private static void logConfigField(Object configElement, Field field, ILogWrapper logger, int indent) throws IllegalAccessException {
        if (IConfigLog.class.isAssignableFrom(field.getType()) && field.get(configElement) != null) {
            logConfigInterfaceField(configElement, field, logger, indent);
        } else {
            logger.debug(format("%s = %s", indent, field.getName(), field.get(configElement)));
        }
    }

    /**
     * Logs a given field of {@link IConfigLog} type
     *
     * @param configElement the element which is to log
     * @param field         field of {@code configElement} to log
     * @param logger        the logger to use
     * @param indent        number of {@link ConfigElementsUtil#INDENT} to use as prefix
     * @throws IllegalAccessException if this Field object is enforcing Java language access control and the underlying field is inaccessible.
     */
    private static void logConfigInterfaceField(Object configElement, Field field, ILogWrapper logger, int indent) throws IllegalAccessException {
        IConfigLog configLog = (IConfigLog) field.get(configElement);
        if (configElement instanceof IConfigLog && ((IConfigLog) configElement).getFieldNamesToLogComplete().contains(field.getName())) {
            logger.debug(format("%s =", indent, field.getName()));
            logConfigElement(configLog, logger, indent + 1);
        } else {
            logger.debug(format("%s = %s", indent, field.getName(), configLog.getSimpleLogName()));
        }
    }

    /**
     * Logs a given list of {@link List} fields
     *
     * @param configElement the element which is to log
     * @param fields        fields of {@code configElement} to log
     * @param logger        the logger to use
     * @param indent        number of {@link ConfigElementsUtil#INDENT} to use as prefix
     */
    private static void logConfigListFields(Object configElement, List<Field> fields, ILogWrapper logger, int indent) {
        fields.stream()
                .sorted(Comparator.comparing(Field::getName))
                .forEach(f -> logConfigExecuteAccessible(configElement, f, logger, indent, ConfigElementsUtil::logConfigListFieldSize));

        fields.stream()
                .filter(f -> logConfigExecuteAccessible(configElement, f, logger, indent, ConfigElementsUtil::filterEmptyLogConfigList))
                .sorted(Comparator.comparing(Field::getName))
                .forEach(f -> logConfigExecuteAccessible(configElement, f, logger, indent, ConfigElementsUtil::logConfigListField));
    }

    /**
     * Logs the size of ad {@link List} field
     *
     * @param configElement the element which is to log
     * @param field         field of {@code configElement} to log
     * @param logger        the logger to use
     * @param indent        number of {@link ConfigElementsUtil#INDENT} to use as prefix
     * @throws IllegalAccessException if this Field object is enforcing Java language access control and the underlying field is inaccessible.
     */
    private static void logConfigListFieldSize(Object configElement, Field field, ILogWrapper logger, int indent) throws IllegalAccessException {
        if (field.get(configElement) != null) {
            List<?> list = (List<?>) field.get(configElement);
            logger.debug(format("#%s = %d", indent, field.getName(), list.size()));
        } else {
            logger.debug(format("#%s = 0", indent, field.getName()));
        }
    }

    /**
     * Checks whether a field is {@code null} or empty and can be filtered from log
     *
     * @param configElement the element which is to log
     * @param field         field of {@code configElement} to log
     * @param logger        he logger to use
     * @param indent        number of {@link ConfigElementsUtil#INDENT} to use as prefix
     * @return {@code true} if the field is not  {@code null} and not empty
     * @throws IllegalAccessException if this Field object is enforcing Java language access control and the underlying field is inaccessible.
     */
    private static boolean filterEmptyLogConfigList(Object configElement, Field field, ILogWrapper logger, int indent) throws IllegalAccessException {
        return field.get(configElement) != null && !((List<?>) field.get(configElement)).isEmpty();
    }

    /**
     * Logs a given list of {@link List} fields
     *
     * @param configElement the element which is to log
     * @param field         field of {@code configElement} to log
     * @param logger        the logger to use
     * @param indent        number of {@link ConfigElementsUtil#INDENT} to use as prefix
     * @throws IllegalAccessException if this Field object is enforcing Java language access control and the underlying field is inaccessible.
     */
    @SuppressWarnings("unchecked")
    private static void logConfigListField(Object configElement, Field field, ILogWrapper logger, int indent) throws IllegalAccessException {
        logger.debug(format("%s:", indent, field.getName()));
        if (Arrays.stream(((ParameterizedType) field.getGenericType()).getActualTypeArguments()).anyMatch(a -> IConfigLog.class.isAssignableFrom((Class<?>) a))) {
            List<? extends IConfigLog> list = (List<? extends IConfigLog>) field.get(configElement);
            list.forEach(c -> logConfigElement(c, logger, indent + 1));
        } else {
            List<?> list = (List<?>) field.get(configElement);
            list.forEach(c -> logger.debug(format("%s", indent + 1, c)));
        }
    }


    /**
     * @return a simple name to represent this class at config log
     */
    @SuppressWarnings("java:S3011")
    public static String getSimpleLogName(Object configElement) {
        return determinePrimitiveFieldsToPrint(configElement.getClass()).stream()
                .filter(f -> f.getName().endsWith("Name") && f.getType().getTypeName().equals(String.class.getTypeName()))
                .map(f -> {
                    try {
                        boolean accessible = f.canAccess(configElement);
                        f.setAccessible(true);
                        String result = (String) f.get(configElement);
                        f.setAccessible(accessible);
                        return result == null ? configElement.toString() : result;
                    } catch (IllegalAccessException e) {
                        return configElement.toString();
                    }
                })
                .findFirst()
                .orElseGet(configElement::toString);
    }

    /**
     * Makes a field accessible for a given action
     *
     * @param configElement the element which is to log
     * @param field         field of {@code configElement} to log
     * @param logger        the logger to use
     * @param indent        number of {@link ConfigElementsUtil#INDENT} to use as prefix
     * @param action        the logging action to execute
     */
    @SuppressWarnings("java:S3011")
    private static void logConfigExecuteAccessible(Object configElement, Field field, ILogWrapper logger, int indent, LogConfigFieldAction action) {
        boolean accessible = field.canAccess(configElement);
        field.setAccessible(true);
        try {
            action.execute(configElement, field, logger, indent);
        } catch (IllegalAccessException e) {
            logger.debug(format("%s access failure %s", indent, field.getName(), e.getMessage()));
        }
        field.setAccessible(accessible);
    }

    /**
     * Makes a field accessible for a given action
     *
     * @param configElement the element which is to log
     * @param field         field of {@code configElement} to log
     * @param logger        the logger to use
     * @param indent        number of {@link ConfigElementsUtil#INDENT} to use as prefix
     * @param action        the logging action to execute
     * @return the boolean result of {@code action} or {@code false} at any {@link IllegalAccessException}
     */
    @SuppressWarnings({"java:S3011", "java:S108"})
    private static boolean logConfigExecuteAccessible(Object configElement, Field field, ILogWrapper logger, int indent, LogConfigFieldBooleanAction action) {
        boolean result = false;
        boolean accessible = field.canAccess(configElement);
        field.setAccessible(true);
        try {
            result = action.execute(configElement, field, logger, indent);
        } catch (IllegalAccessException ignored) {
        }
        field.setAccessible(accessible);
        return result;
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

    /**
     * Functional interface to describe logging actions
     */
    @FunctionalInterface
    public interface LogConfigFieldAction {
        /**
         * executes a log action for given field
         *
         * @param configElement the element which is to log
         * @param field         field of {@code configElement} to log
         * @param logger        the logger to use
         * @param indent        number of {@link ConfigElementsUtil#INDENT} to use as prefix
         * @throws IllegalAccessException if this Field object is enforcing Java language access control and the underlying field is inaccessible.
         */
        void execute(Object configElement, Field field, ILogWrapper logger, int indent) throws IllegalAccessException;
    }

    /**
     * Functional interface to describe logging actions with a boolean result
     */
    @FunctionalInterface
    public interface LogConfigFieldBooleanAction {
        /**
         * executes a log action for given field
         *
         * @param configElement the element which is to log
         * @param field         field of {@code configElement} to log
         * @param logger        the logger to use
         * @param indent        number of {@link ConfigElementsUtil#INDENT} to use as prefix
         * @return the boolean result of the action
         * @throws IllegalAccessException if this Field object is enforcing Java language access control and the underlying field is inaccessible.
         */
        boolean execute(Object configElement, Field field, ILogWrapper logger, int indent) throws IllegalAccessException;
    }
}
