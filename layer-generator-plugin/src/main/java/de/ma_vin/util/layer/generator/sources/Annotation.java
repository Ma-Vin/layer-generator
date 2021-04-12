package de.ma_vin.util.layer.generator.sources;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

/**
 * Class to generate sources of annotations
 */
@Data
@AllArgsConstructor
public class Annotation extends AbstractGenerateLines implements Comparable<Annotation> {

    private String annotationName;
    private List<AttributeValue> parameters = new ArrayList<>();

    public Annotation(String annotationName) {
        this.annotationName = annotationName;
    }

    public Annotation(String annotationName, String paraName, String paraValue) {
        this(annotationName);
        addParameter(paraName, paraValue);
    }

    public Annotation(Class<?> annotationClass) {
        this(annotationClass.getSimpleName());
    }

    public Annotation(Class<?> annotationClass, String paraName, String paraValue) {
        this(annotationClass.getSimpleName(), paraName, paraValue);
    }

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

    public void addValue(String paraValue) {
        parameters.add(new AttributeValue(null, paraValue));
    }

    public void addParameter(String paraName, String paraValue) {
        parameters.add(new AttributeValue(paraName, paraValue));
    }

    public void addParameterArray(String paraName, String... paraValue) {
        parameters.add(new AttributeValue(paraName, paraValue));
    }

    public void addParameterArray(String paraName, List<String> paraValues) {
        addParameterArray(paraName, paraValues.toArray(String[]::new));
    }

    @Override
    @SuppressWarnings("java:S1210")
    public int compareTo(Annotation o) {
        int res = annotationName.compareTo(o.annotationName);
        if (res == 0) {
            res = getParametersText(parameters).compareTo(getParametersText(o.parameters));
        }
        return res;
    }

    @Data
    @AllArgsConstructor
    public class AttributeValue implements IComparableWithText<AttributeValue> {
        private boolean isArray;
        private String attributeName;
        private String[] values;

        public AttributeValue(String attributeName, String value) {
            this(false, attributeName, new String[]{value});
        }

        public AttributeValue(String attributeName, String[] values) {
            this(true, attributeName, values);
        }

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
