package de.mv.ape.layer.generator.sources;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to generate sources for methods at classes
 */
@AllArgsConstructor
@Data
public class Method extends AbstractGenerateLines implements Comparable<Method> {
    protected JavaDoc javaDoc = null;
    protected List<Annotation> annotations = new ArrayList<>();
    protected boolean isStatic;
    protected Qualifier qualifier = Qualifier.PRIVATE;
    protected String methodType = "void";
    protected String methodName;
    protected List<Parameter> parameters = new ArrayList<>();
    protected List<String> methodBody = new ArrayList<>();


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

    public void addLine(String methodLine, int numTabs) {
        methodBody.add(getTabs(numTabs) + methodLine);
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
        result.add(getDeclaration());
        methodBody.forEach(b -> result.add(TAB + b));
        result.add("}");
        return result;
    }

    protected String getDeclaration() {
        return String.format("%s%s %s %s(%s) {", qualifier.getText(), getStaticText(), methodType, methodName, getParametersText(parameters));
    }

    protected String getStaticText() {
        return isStatic ? " static" : "";
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
                res = parameters.get(i).getParameterName().compareTo(o.getParameters().get(i).getParameterName());
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

}
