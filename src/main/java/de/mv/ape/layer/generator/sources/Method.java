package de.mv.ape.layer.generator.sources;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to generate sources for methods at classes
 */
@AllArgsConstructor
@Data
public class Method extends AbstractGenerateLines implements Comparable<Method> {
    private JavaDoc javaDoc = null;
    private List<Annotation> annotations = new ArrayList<>();
    private boolean isStatic;
    private String qualifier = "private";
    private String methodType = "void";
    private String methodName;
    private List<Parameter> parameters = new ArrayList<>();
    private List<String> methodBody = new ArrayList<>();


    public Method(String methodName) {
        this.methodName = methodName;
    }

    public Method(String methodName, String methodLine) {
        this(methodName);
        addLine(methodLine);
    }

    public void addLine(String methodLine) {
        methodBody.add(methodLine);
    }

    public void addParameter(String parameterType, String parameterName) {
        parameters.add(new Parameter(parameterType, parameterName));
    }

    @Override
    public List<String> generate() {
        List<String> result = new ArrayList<>();
        if (javaDoc != null) {
            result.addAll(javaDoc.generate());
        }
        annotations.stream().sorted().forEach(a -> result.addAll(a.generate()));
        result.add(String.format("%s%s %s %s(%s) {", qualifier, getStaticText(), methodType, methodName, getParametersText()));
        methodBody.forEach(b -> result.add(TAB + b));
        result.add("}");
        return result;
    }

    private String getStaticText() {
        return isStatic ? " static" : "";
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

    @Override
    @SuppressWarnings("java:S1210")
    public int compareTo(Method o) {
        if (isStatic && !o.isStatic) {
            return -1;
        }
        if (!isStatic && o.isStatic) {
            return 1;
        }
        int res = methodName.compareTo(o.methodName);
        if (res == 0) {
            for (int i = 0; i < parameters.size() && i < o.parameters.size() && res == 0; i++) {
                res = parameters.get(i).parameterName.compareTo(o.getParameters().get(i).parameterName);
            }
        }
        if (res == 0) {
            res = parameters.size() - o.parameters.size();
        }
        return res;
    }

    public void addAnnotation(String annotationName) {
        annotations.add(new Annotation(annotationName));
    }

    public void addAnnotation(String annotationName, String parameterName, String parameterValue) {
        Annotation annotation = new Annotation(annotationName);
        annotation.addParameter(parameterName, parameterValue);
        annotations.add(annotation);
    }

    @Data
    @AllArgsConstructor
    public class Parameter implements Comparable<Parameter> {
        private String parameterType;
        private String parameterName;

        private String getText() {
            return String.format("%s %s", parameterType, parameterName);
        }

        @Override
        @SuppressWarnings("java:S1210")
        public int compareTo(Parameter o) {
            return parameterName.compareTo(o.parameterName);
        }
    }
}
