package com.github.ma_vin.util.layer_generator.sources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;

/**
 * Class to generate sources of annotations
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Annotation extends AbstractGenerateLines implements Comparable<Annotation> {

    private String annotationName;
    private List<AttributeValue> parameters = new ArrayList<>();

    /**
     * Constructor
     *
     * @param annotationName name of the annotation
     */
    public Annotation(String annotationName) {
        this.annotationName = annotationName;
    }

    /**
     * Constructor
     *
     * @param annotationName name of the annotation
     * @param paraName       name of a single parameter
     * @param paraValue      the value of the single parameter
     */
    public Annotation(String annotationName, String paraName, String paraValue) {
        this(annotationName);
        addParameter(paraName, paraValue);
    }

    /**
     * Constructor
     *
     * @param annotationClass classes of an annotation, whose simple name ist used for {@code annotationName}
     */
    public Annotation(Class<?> annotationClass) {
        this(annotationClass.getSimpleName());
    }

    /**
     * Constructor
     *
     * @param annotationClass classes of an annotation, whose simple name ist used for {@code annotationName}
     * @param paraName        name of a single parameter
     * @param paraValue       the value of the single parameter
     */
    public Annotation(Class<?> annotationClass, String paraName, String paraValue) {
        this(annotationClass.getSimpleName(), paraName, paraValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> generate() {
        Collections.sort(parameters);
        List<String> result = new ArrayList<>();
        if (parameters.isEmpty()) {
            result.add(String.format("@%s", annotationName));
        } else {
            result.add(String.format("@%s(%s)", annotationName, getParametersText(parameters)));
        }
        return result;
    }

    /**
     * Adds a value without reference to a name to the annotation
     *
     * @param paraValue the value to add
     */
    public void addValue(String paraValue) {
        parameters.add(new AttributeValue(null, paraValue));
    }

    /**
     * Adds a name and value of a parameter to the annotation
     *
     * @param paraName  name to add
     * @param paraValue value to add
     */
    public void addParameter(String paraName, String paraValue) {
        parameters.add(new AttributeValue(paraName, paraValue));
    }

    /**
     * Adds a name and an array of values of a parameter to the annotation
     *
     * @param paraName  name to add
     * @param paraValue values to add
     */
    public void addParameterArray(String paraName, String... paraValue) {
        parameters.add(new AttributeValue(paraName, paraValue));
    }

    /**
     * Adds a name and a list of values of a parameter to the annotation
     *
     * @param paraName   name to add
     * @param paraValues values to add
     */
    public void addParameterArray(String paraName, List<String> paraValues) {
        addParameterArray(paraName, paraValues.toArray(String[]::new));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("java:S1210")
    public int compareTo(Annotation o) {
        int res = annotationName.compareTo(o.annotationName);
        if (res == 0) {
            res = getParametersText(parameters).compareTo(getParametersText(o.parameters));
        }
        return res;
    }

    /**
     * Class which represents a parameter name and its values
     */
    @Data
    @AllArgsConstructor
    public class AttributeValue implements IComparableWithText<AttributeValue> {
        private boolean isArray;
        private String attributeName;
        private String[] values;

        /**
         * Constructor
         *
         * @param attributeName name of the annotation attribute
         * @param value         value of the annotation attribute
         */
        public AttributeValue(String attributeName, String value) {
            this(false, attributeName, new String[]{value});
        }

        /**
         * Constructor
         *
         * @param attributeName name of the annotation attribute
         * @param values        values of the annotation attribute
         */
        public AttributeValue(String attributeName, String[] values) {
            this(true, attributeName, values);
        }

        /**
         * Determines the source code representation of this object
         *
         * @return the textual representation
         */
        public String getText() {
            if (!isArray) {
                return attributeName == null ? values[0] : String.format("%s = %s", attributeName, values[0]);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(attributeName == null ? "{" : String.format("%s = {", attributeName));
            sb.append(values[0]);
            for (int i = 1; i < values.length; i++) {
                sb.append(", ");
                sb.append(values[i]);
            }
            sb.append("}");
            return sb.toString();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("java:S1210")
        public int compareTo(AttributeValue o) {
            return attributeName.compareTo(o.attributeName);
        }
    }

    /**
     * Appends a value to existing values of the parameter without name. If the parameter without name does not exists a new one will be added
     *
     * @param paraValue The value to add
     */
    public void appendValue(String paraValue) {
        Optional<AttributeValue> attributeValue = parameters.stream().filter(av -> av.getAttributeName() == null).findFirst();
        if (attributeValue.isEmpty()) {
            addValue(paraValue);
            return;
        }
        addValue(attributeValue.get(), paraValue);
    }

    /**
     * Appends a value to existing values of the parameter. If the parameter without name does not exists a new one will be added
     *
     * @param paraName  name of the parameter where to add value
     * @param paraValue The value to add
     */
    public void appendParameter(String paraName, String paraValue) {
        Optional<AttributeValue> attributeValue = parameters.stream().filter(av -> av.getAttributeName() != null && av.getAttributeName().equals(paraName)).findFirst();
        if (attributeValue.isEmpty()) {
            addParameter(paraName, paraValue);
            return;
        }
        addValue(attributeValue.get(), paraValue);
    }

    /**
     * Adds a value to the value array at an {@link AttributeValue}
     *
     * @param attributeValue where to add value
     * @param value          value to add
     */
    private void addValue(AttributeValue attributeValue, String value) {
        List<String> values = new ArrayList<>();
        values.addAll(Arrays.asList(attributeValue.values));
        if (!values.contains(value)) {
            values.add(value);
            attributeValue.isArray = true;
        }
        attributeValue.values = values.toArray(String[]::new);
    }
}
