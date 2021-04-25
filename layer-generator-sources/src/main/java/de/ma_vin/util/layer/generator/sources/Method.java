package de.ma_vin.util.layer.generator.sources;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
    protected Set<Generic> generics = new TreeSet<>();
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

    public void addLine(String methodLine, String... args) {
        methodBody.add(String.format(methodLine, args));
    }

    public void addLine(String methodLine, int numTabs) {
        addLine(getTabs(numTabs) + methodLine);
    }

    public void addLine(String methodLine, int numTabs, String... args) {
        addLine(getTabs(numTabs) + methodLine, args);
    }

    /**
     * Adds an empty line if the last one is not already empty
     */
    public void addEmptyLine() {
        if (!methodBody.isEmpty() && !methodBody.get(methodBody.size() - 1).trim().isEmpty()) {
            methodBody.add("");
        }
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
        result.addAll(getDeclarations());
        methodBody.forEach(b -> result.add(b.trim().isEmpty() ? "" : TAB + b));
        result.add("}");
        return result;
    }

    protected String getGenericText() {
        if (generics.isEmpty()) {
            return "";
        }
        return " " + Generic.getText(generics);
    }

    protected String getBaseDeclaration() {
        return String.format("%s%s%s %s %s(", qualifier.getText(), getStaticText(), getGenericText(), methodType, methodName);
    }

    protected List<String> getDeclarations() {
        String firstEntry = getBaseDeclaration();
        List<String> result = new ArrayList<>();
        if (parameters.isEmpty()) {
            result.add(firstEntry + ") {");
            return result;
        }
        result.addAll(getParameterTexts(firstEntry.length(), parameters));
        result.set(0, firstEntry + result.get(0));
        result.set(result.size() - 1, result.get(result.size() - 1) + ") {");
        return result;
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
                res = parameters.get(i).compareTo(o.getParameters().get(i));
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

    public void addGeneric(String genericName) {
        addGeneric(new Generic(genericName));
    }

    public void addGeneric(Generic generic) {
        generics.add(generic);
    }
}
