package com.github.ma_vin.util.layer_generator.sources;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A representation of an interface
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Interface extends AbstractGenerateLines implements IFileRepresentation, Comparable<Interface> {

    private String packageName;
    private String interfaceName;

    private Set<Import> imports = new TreeSet<>();
    private JavaDoc description = null;
    private Set<Annotation> annotations = new TreeSet<>();
    private Set<Generic> generics = new TreeSet<>();
    private String extension = null;
    private Set<MethodDeclaration> methodDeclarations = new TreeSet<>();
    private boolean isInner;

    /**
     * Constructor
     *
     * @param packageName   name of the package of this interface
     * @param interfaceName simple name of the interface
     */
    public Interface(String packageName, String interfaceName) {
        this.packageName = packageName;
        this.interfaceName = interfaceName;
    }

    /**
     * Constructor of an inner interface
     *
     * @param interfaceName simple name of the interface
     */
    public Interface(String interfaceName) {
        this(null, interfaceName);
        isInner = true;
    }

    /**
     * Adds a declaration of a method to this interface
     *
     * @param methodDeclaration the declaration to add
     */
    public void addMethodDeclaration(MethodDeclaration methodDeclaration) {
        methodDeclarations.add(methodDeclaration);
    }

    /**
     * Adds a declaration of a method to this interface
     *
     * @param methodType     the return type of the method
     * @param methodName     the name of the method
     * @param parameterPairs pairs of type and name for parameters
     */
    public void addMethodDeclaration(String methodType, String methodName, String... parameterPairs) {
        addMethodDeclarationWithDescription(methodType, methodName, null, parameterPairs);
    }

    /**
     * Adds a declaration of a method to this interface
     *
     * @param methodType     the return type of the method
     * @param methodName     the name of the method
     * @param description    the java doc description of the method
     * @param parameterPairs pairs of type and name for parameters
     */
    public void addMethodDeclarationWithDescription(String methodType, String methodName, String description, String... parameterPairs) {
        MethodDeclaration toAdd = new MethodDeclaration(methodType, methodName);

        if (description != null) {
            toAdd.javaDoc = new JavaDoc(description);
        }
        if (parameterPairs.length % 2 != 0) {
            return;
        }
        for (int i = 0; i < parameterPairs.length; i += 2) {
            toAdd.parameters.add(new Parameter(parameterPairs[i], parameterPairs[i + 1]));
        }
        addMethodDeclaration(toAdd);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> generate() {
        List<String> result = new ArrayList<>();

        if (!isInner) {
            result.add(String.format("package %s;", packageName));
            result.add("");
            if (!imports.isEmpty()) {
                imports.stream().sorted().forEach(i -> result.addAll(i.generate()));
                result.add("");
            }
        }
        if (description != null) {
            result.addAll(description.generate());
        }
        annotations.stream().sorted().forEach(a -> result.addAll(a.generate()));
        result.add(String.format("%s interface %s%s%s {", Qualifier.PUBLIC.getText(), interfaceName, getGenericText(), getExtensionText()));
        result.add("");
        methodDeclarations.stream().sorted().forEach(m -> {
            result.addAll(m.generate(1));
            result.add("");
        });
        result.add("}");

        return result;
    }

    private String getExtensionText() {
        if (extension == null || extension.trim().isEmpty()) {
            return "";
        }
        return String.format(" extends %s", extension.trim());
    }

    private String getGenericText() {
        return Generic.getText(generics);
    }

    /**
     * Adds an annotation to this interface
     *
     * @param annotation annotation to add
     */
    public void addAnnotation(Annotation annotation) {
        annotations.add(annotation);
    }

    /**
     * Adds an annotation to this interface
     *
     * @param annotationName name of the annotation to add
     */
    public void addAnnotation(String annotationName) {
        addAnnotation(new Annotation(annotationName));
    }

    /**
     * Adds a class import to this interface
     *
     * @param importedClass full qualified name of the class
     */
    public void addImport(String importedClass) {
        imports.add(new Import(importedClass, false));
    }

    /**
     * Adds a generic to this interface
     *
     * @param genericName name of the generic
     */
    public void addGeneric(String genericName) {
        addGeneric(new Generic(genericName));
    }

    /**
     * Adds a generic to this interface
     *
     * @param generic generic to add
     */
    public void addGeneric(Generic generic) {
        generics.add(generic);
    }

    /**
     * Sets the description of this interface
     *
     * @param description the description to set
     */
    public void setDescription(JavaDoc description) {
        this.description = description;
    }

    /**
     * Sets the description of this interface
     *
     * @param description a description template
     * @param args        arguments to set at template
     */
    public void setDescription(String description, Object... args) {
        setDescription(new JavaDoc(String.format(description, args)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("java:S1210")
    public int compareTo(Interface o) {
        return interfaceName.compareTo(o.interfaceName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFilename() {
        return interfaceName + ".java";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getObjectName() {
        return interfaceName;
    }

    /**
     * Representation of a method declaration
     */
    @Data
    public static class MethodDeclaration implements Comparable<MethodDeclaration> {
        private JavaDoc javaDoc = null;
        private List<Annotation> annotations = new ArrayList<>();
        private String methodType = "void";
        private String methodName;
        private List<Parameter> parameters = new ArrayList<>();

        /**
         * Constructor
         *
         * @param methodName name of the method
         */
        public MethodDeclaration(String methodName) {
            this.methodName = methodName;
        }

        /**
         * Constructor
         *
         * @param methodType return type of the method
         * @param methodName name of the method
         */
        public MethodDeclaration(String methodType, String methodName) {
            this.methodName = methodName;
            this.methodType = methodType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("java:S1210")
        public int compareTo(MethodDeclaration o) {
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

        private List<String> generate(int numOfTabs) {
            String tabsToAdd = getTabs(numOfTabs);
            return generate().stream().map(s -> tabsToAdd + s).toList();
        }

        private List<String> generate() {
            List<String> result = new ArrayList<>();

            if (javaDoc != null) {
                result.addAll(javaDoc.generate());
            }
            annotations.stream().sorted().forEach(a -> result.addAll(a.generate()));
            result.add(String.format("%s %s(%s);", methodType, methodName, getParametersText(parameters)));

            return result;
        }
    }
}
