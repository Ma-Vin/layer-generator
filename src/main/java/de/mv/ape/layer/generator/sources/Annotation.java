package de.mv.ape.layer.generator.sources;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        List<String> result = new ArrayList<>();
        result.add(String.format("@%s(%s)", annotationName, getParametersText()));
        return result;
    }

    private String getParametersText() {
        if (parameters.isEmpty()) {
            return "";
        }
        Collections.sort(parameters);
        StringBuilder sb = new StringBuilder();
        sb.append(parameters.get(0).getText());
        for (int i = 1; i < parameters.size(); i++) {
            sb.append(", ");
            sb.append(parameters.get(i).getText());
        }
        return sb.toString();
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
            res = getParametersText().compareTo(o.getParametersText());
        }
        return res;
    }

    @Data
    @AllArgsConstructor
    public class AttributeValue implements Comparable<AttributeValue> {
        private boolean isArray;
        private String attributeName;
        private String[] values;

        public AttributeValue(String attributeName, String value) {
            this(false, attributeName, new String[]{value});
        }

        public AttributeValue(String attributeName, String[] values) {
            this(true, attributeName, values);
        }

        private String getText() {
            if (!isArray) {
                return attributeName == null ? values[0] : String.format("%s = %s", attributeName, values[0]);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%s = {", attributeName));
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
}
