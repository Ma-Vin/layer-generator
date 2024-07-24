package com.github.ma_vin.util.layer_generator.sources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class to generate sources for methods at classes
 */
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Method extends AbstractGenerateLines implements Comparable<Method> {
    /**
     * Java doc with method description
     */
    protected JavaDoc javaDoc = null;
    /**
     * List of annotations
     */
    protected List<Annotation> annotations = new ArrayList<>();
    /**
     * Indicator if this is a static method or nor
     */
    protected boolean isStatic;
    /**
     * Visibility qualifier
     */
    protected Qualifier qualifier = Qualifier.PRIVATE;
    /**
     * Type of the return
     */
    protected String methodType = "void";
    /**
     * Name of the method
     */
    protected String methodName;
    /**
     * Generics used at this method
     */
    protected Set<Generic> generics = new TreeSet<>();
    /**
     * Parameters at this method
     */
    protected List<Parameter> parameters = new ArrayList<>();
    /**
     * Lines of the method body
     */
    protected List<String> methodBody = new ArrayList<>();

    /**
     * Constructor
     *
     * @param methodName name of this method
     */
    public Method(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Constructor with a single line at method body
     *
     * @param methodName name of this method
     * @param methodLine line to add
     */
    public Method(String methodName, String methodLine) {
        this(methodName);
        addLine(methodLine);
    }

    /**
     * Adds a line to method body
     *
     * @param methodLine line to add
     */
    public void addLine(String methodLine) {
        methodBody.add(methodLine);
    }

    /**
     * Adds a line to method body
     *
     * @param methodLine template of the method line
     * @param args       arguments to set at template
     */
    public void addLine(String methodLine, Object... args) {
        methodBody.add(String.format(methodLine, args));
    }

    /**
     * Adds a tabbed line to method body
     *
     * @param methodLine line to add
     * @param numTabs    number of tabs to add in front
     */
    public void addTabbedLine(String methodLine, int numTabs) {
        addLine(getTabs(numTabs) + methodLine);
    }

    /**
     * Adds a tabbed line to method body
     *
     * @param methodLine template of  line to add
     * @param numTabs    number of tabs to add in front
     * @param args       arguments to set at template
     */
    public void addTabbedLine(String methodLine, int numTabs, Object... args) {
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

    /**
     * adds a parameter to method signature
     *
     * @param parameterType type of the parameter
     * @param parameterName parameter name
     */
    public void addParameter(String parameterType, String parameterName) {
        parameters.add(new Parameter(parameterType, parameterName));
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
        result.addAll(getDeclarations());
        methodBody.forEach(b -> result.add(b.trim().isEmpty() ? "" : TAB + b));
        result.add("}");
        return result;
    }

    /**
     * Determine the textual representation of a defined generic at this method
     *
     * @return textual representation of a defined generic. Empty string if there is no generic set
     */
    protected String getGenericText() {
        if (generics.isEmpty()) {
            return "";
        }
        return " " + Generic.getText(generics);
    }

    /**
     * determine the textual representation of this method without body, parameters and javadoc
     *
     * @return basic textual representation of this method
     */
    protected String getBaseDeclaration() {
        return String.format("%s%s%s %s %s(", qualifier.getText(), getStaticText(), getGenericText(), methodType, methodName);
    }

    /**
     * determine the textual representation of this method without  body and javadoc
     *
     * @return textual representation of this method
     */
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

    /**
     * Text of static declaration
     *
     * @return the static qualifier. Empty string if it is not static
     */
    protected String getStaticText() {
        return isStatic ? " static" : "";
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * Adds an annotation to this Method
     *
     * @param annotationName name of the annotation to add
     */
    public void addAnnotation(String annotationName) {
        annotations.add(new Annotation(annotationName));
    }

    /**
     * Adds an annotation to this Method
     *
     * @param annotationName name of the annotation to add
     * @param parameterName  name of the annotations parameter
     * @param parameterValue value of the annotations parameter
     */
    public void addAnnotation(String annotationName, String parameterName, String parameterValue) {
        Annotation annotation = new Annotation(annotationName);
        annotation.addParameter(parameterName, parameterValue);
        annotations.add(annotation);
    }

    /**
     * Adds a generic to this method
     *
     * @param genericName name of the generic
     */
    public void addGeneric(String genericName) {
        addGeneric(new Generic(genericName));
    }

    /**
     * Adds a generic to this method
     *
     * @param generic teh generic to add
     */
    public void addGeneric(Generic generic) {
        generics.add(generic);
    }
}
