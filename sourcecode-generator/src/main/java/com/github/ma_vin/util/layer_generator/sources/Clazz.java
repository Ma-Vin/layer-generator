package com.github.ma_vin.util.layer_generator.sources;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class to generate sources for classes
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Clazz extends AbstractGenerateLines implements IFileRepresentation, Comparable<Clazz> {
    private String packageName;
    private String className;

    private Set<Annotation> annotations = new TreeSet<>();
    private Set<Import> imports = new TreeSet<>();
    private Set<Import> staticImports = new TreeSet<>();
    private JavaDoc description = null;
    private Set<Generic> generics = new TreeSet<>();
    private Set<Constructor> constructors = new TreeSet<>();
    private Set<Attribute> attributes = new TreeSet<>();
    private Set<Method> methods = new TreeSet<>();
    private Set<Clazz> innerClasses = new TreeSet<>();
    private Set<String> interfaces = new TreeSet<>();
    private Set<Interface> innerInterfaces = new TreeSet<>();
    private String extension = null;
    private Qualifier qualifier = Qualifier.PUBLIC;
    private boolean isStatic;
    private boolean isInner;
    private boolean isAbstract;

    /**
     * Constructor
     *
     * @param packageName package of this class
     * @param className   the simple name of the class
     */
    public Clazz(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }

    /**
     * Constructor for an inner class
     *
     * @param className the simple name of the class
     */
    public Clazz(String className) {
        this(null, className);
        isInner = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> generate() {
        removeImportsAtSamePackage();
        List<String> result = new ArrayList<>();
        if (!isInner) {
            result.add(String.format("package %s;", packageName));
            result.add("");
            if (!staticImports.isEmpty()) {
                staticImports.stream().sorted().forEach(i -> result.addAll(i.generate()));
                result.add("");
            }
            if (!imports.isEmpty()) {
                imports.stream().sorted().forEach(i -> result.addAll(i.generate()));
                result.add("");
            }
        }
        if (description != null) {
            result.addAll(description.generate());
        }
        annotations.stream().sorted().forEach(a -> result.addAll(a.generate()));
        result.add(String.format("%s%s%s class %s%s%s%s {", qualifier.getText(), getStaticText(), getAbstractText()
                , className, getGenericText(), getExtensionText(), getInterfaceText()));
        result.add("");
        constructors.stream().sorted().forEach(a -> {
            result.addAll(a.generate(1));
            result.add("");
        });
        attributes.stream().sorted().forEach(a -> {
            result.addAll(a.generate(1));
            result.add("");
        });
        methods.stream().sorted().forEach(m -> {
            result.addAll(m.generate(1));
            result.add("");
        });
        innerClasses.stream().sorted().forEach(ic -> {
            result.addAll(ic.generate(1));
            result.add("");
        });
        innerInterfaces.stream().sorted().forEach(ic -> {
            result.addAll(ic.generate(1));
            result.add("");
        });
        result.add("}");
        return result;
    }

    private String getGenericText() {
        return Generic.getText(generics);
    }

    private String getInterfaceText() {
        if (interfaces.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" implements ");
        interfaces.forEach(i -> {
            sb.append(i.trim());
            sb.append(", ");
        });
        return sb.substring(0, sb.length() - 2);
    }

    private String getExtensionText() {
        if (extension == null || extension.trim().isEmpty()) {
            return "";
        }
        return String.format(" extends %s", extension.trim());
    }

    private String getStaticText() {
        return isStatic ? " static" : "";
    }

    private String getAbstractText() {
        return isAbstract ? " abstract" : "";
    }

    private void removeImportsAtSamePackage() {
        if (isInner) {
            imports.clear();
            staticImports.clear();
        }
        Set<Import> toRemove = imports.stream()
                .filter(i -> i.importedClass.startsWith(packageName))
                .filter(i -> !i.importedClass.substring(packageName.length() + 1).contains("."))
                .collect(Collectors.toSet());
        imports.removeAll(toRemove);

        Set<Import> staticToRemove = staticImports.stream()
                .filter(i -> i.importedClass.startsWith(packageName))
                .filter(i -> !i.importedClass.substring(packageName.length() + 1).contains("."))
                .collect(Collectors.toSet());
        staticImports.removeAll(staticToRemove);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("java:S1210")
    public int compareTo(Clazz o) {
        return className.compareTo(o.className);
    }

    /**
     * Adds a constructor to this class
     *
     * @param constructor the constructor to add
     */
    public void addConstructor(Constructor constructor) {
        constructors.add(constructor);
    }

    /**
     * Adds an attribute to this class
     *
     * @param attribute the attribute to add
     */
    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }

    /**
     * Adds a method to this class
     *
     * @param method the method to add
     */
    public void addMethod(Method method) {
        methods.add(method);
    }

    /**
     * Adds an annotation to this class
     *
     * @param annotation the annotation to add
     */
    public void addAnnotation(Annotation annotation) {
        annotations.add(annotation);
    }

    /**
     * Adds an annotation to this class
     *
     * @param annotation the annotation name to add
     */
    public void addAnnotation(String annotation) {
        annotations.add(new Annotation(annotation));
    }

    /**
     * Adds an annotation to this class
     *
     * @param annotation the class of annotation to add by its simple name
     */
    public void addAnnotation(Class<?> annotation) {
        addAnnotation(annotation.getSimpleName());
    }

    /**
     * Adds a class to import to this class
     *
     * @param importedClass the class to add to imports
     */
    public void addImport(String importedClass) {
        imports.add(new Import(importedClass, false));
    }

    /**
     * Adds a class to import static to this class
     *
     * @param importedClass the class to add to imports
     */
    public void addStaticImport(String importedClass) {
        staticImports.add(new Import(importedClass, true));
    }

    /**
     * Adds a class as inner one to this class
     *
     * @param clazz the class to add
     */
    public void addInnerClazz(Clazz clazz) {
        innerClasses.add(clazz);
    }

    /**
     * Adds an interface which is implemented by this class
     *
     * @param interfaceName the attribute to add
     */
    public void addInterface(String interfaceName) {
        interfaces.add(interfaceName);
    }

    /**
     * Adds an inner interface to this class
     *
     * @param inter the interface to add
     */
    public void addInnerInterface(Interface inter) {
        innerInterfaces.add(inter);
    }

    /**
     * Adds a generic to this class
     *
     * @param genericName the name of generic to add
     */
    public void addGeneric(String genericName) {
        addGeneric(new Generic(genericName));
    }

    /**
     * Adds a generic to this class
     *
     * @param generic the generic to add
     */
    public void addGeneric(Generic generic) {
        generics.add(generic);
    }

    /**
     * Getter of a defined annotation by name
     *
     * @param annotationName name of the annotation to get
     * @return an optional the found annotation. {@link Optional#empty()} if not present
     */
    public Optional<Annotation> getAnnotation(String annotationName) {
        return annotations.stream().filter(a -> a.getAnnotationName().equals(annotationName)).findFirst();
    }

    /**
     * Sets a description to this class
     *
     * @param description the description to set
     */
    public void setDescription(JavaDoc description) {
        this.description = description;
    }

    /**
     * Sets a description to this class
     *
     * @param description template of description to set
     * @param args        arguments to set at template
     */
    public void setDescription(String description, Object... args) {
        setDescription(new JavaDoc(String.format(description, args)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFilename() {
        return className + ".java";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getObjectName() {
        return className;
    }
}
