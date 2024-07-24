package com.github.ma_vin.util.layer_generator.sources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to generate sources for attributes at classes
 */
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Attribute extends AbstractGenerateLines implements Comparable<Attribute> {
    private JavaDoc javaDoc = null;
    private List<Annotation> annotations = new ArrayList<>();
    private boolean isStatic;
    private boolean isFinal;
    private Qualifier qualifier = Qualifier.PRIVATE;
    private String attributeName;
    private String attributeType;
    private String initValue = null;

    /**
     * Constructor
     * @param attributeName name of the attribute
     * @param attributeType type of the attribute
     */
    public Attribute(String attributeName, String attributeType) {
        this.attributeName = attributeName;
        this.attributeType = attributeType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> generate() {
        List<String> result = new ArrayList<>();
        if (javaDoc != null) {
            result.addAll(javaDoc.generate());
        }
        annotations.stream().sorted().forEach(a -> result.addAll(a.generate()));
        result.add(String.format("%s%s%s %s %s%s;", qualifier.getText(), getStaticText(), getFinalText(), attributeType, attributeName, getInitValueText()));
        return result;
    }

    private String getStaticText() {
        return isStatic ? " static" : "";
    }

    private String getFinalText() {
        return isFinal ? " final" : "";
    }

    private String getInitValueText() {
        return initValue != null ? String.format(" = %s", initValue) : "";
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("java:S1210")
    public int compareTo(Attribute o) {
        if (isStatic && !o.isStatic) {
            return -1;
        }
        if (!isStatic && o.isStatic) {
            return 1;
        }
        if (isFinal && !o.isFinal) {
            return -1;
        }
        if (!isFinal && o.isFinal) {
            return 1;
        }
        return attributeName.compareTo(o.attributeName);
    }

    /**
     * Adds an annotation without parameters
     *
     * @param annotationName name of the annotation
     */
    public void addAnnotation(String annotationName) {
        annotations.add(new Annotation(annotationName));
    }

    /**
     * Adds an annotation
     *
     * @param annotationName name of the annotation
     * @param parameterName  name of the annotation parameter
     * @param parameterValue value of the annotation parameter
     */
    public void addAnnotation(String annotationName, String parameterName, String parameterValue) {
        Annotation annotation = new Annotation(annotationName);
        annotation.addParameter(parameterName, parameterValue);
        annotations.add(annotation);
    }

    /**
     * Adds an annotation without parameters
     *
     * @param annotationClass classes of an annotation, whose simple name ist used for {@code annotationName}
     */
    public void addAnnotation(Class<?> annotationClass) {
        addAnnotation(annotationClass.getSimpleName());
    }

    /**
     * Adds an annotation
     *
     * @param annotationClass classes of an annotation, whose simple name ist used for {@code annotationName}
     * @param parameterName   name of the annotation parameter
     * @param parameterValue  value of the annotation parameter
     */
    public void addAnnotation(Class<?> annotationClass, String parameterName, String parameterValue) {
        addAnnotation(annotationClass.getSimpleName(), parameterName, parameterValue);
    }

    /**
     * Adds an annotation
     *
     * @param annotation the annotation to add
     */
    public void addAnnotation(Annotation annotation) {
        annotations.add(annotation);
    }
}
