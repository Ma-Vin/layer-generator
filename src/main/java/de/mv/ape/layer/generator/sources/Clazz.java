package de.mv.ape.layer.generator.sources;


import lombok.Data;

import java.util.*;


/**
 * Class to generate sources for classes
 */
@Data
public class Clazz extends AbstractGenerateLines implements Comparable<Clazz> {
    private String packageName;
    private String className;

    private Set<Annotation> annotations = new TreeSet<>();
    private Set<Import> imports = new TreeSet<>();
    private Set<Import> staticImports = new TreeSet<>();
    private JavaDoc description = null;
    private Set<Attribute> attributes = new TreeSet<>();
    private Set<Method> methods = new TreeSet<>();
    private Set<Clazz> innerClasses = new TreeSet<>();
    private Set<String> interfaces = new TreeSet<>();
    private String extension = null;
    private String qualifier = "public";
    private boolean isStatic;
    private boolean isInner;

    public Clazz(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }

    public Clazz(String className) {
        this(null, className);
        isInner = true;
    }

    @Override
    public List<String> generate() {
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
        result.add(String.format("%s%s class %s%s%s {", qualifier, getStaticText(), className, getExtensionText(), getInterfaceText()));
        result.add("");
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
        result.add("}");
        return result;
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
        return sb.toString().substring(0, sb.length() - 2);
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

    @Override
    @SuppressWarnings("java:S1210")
    public int compareTo(Clazz o) {
        return className.compareTo(o.className);
    }

    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }

    public void addMethod(Method method) {
        methods.add(method);
    }

    public void addAnnotation(Annotation annotation) {
        annotations.add(annotation);
    }

    public void addAnnotation(String annotation) {
        annotations.add(new Annotation(annotation));
    }

    public void addAnnotation(Class<?> annotation) {
        addAnnotation(annotation.getSimpleName());
    }

    public void addImport(String importedClass) {
        imports.add(new Import(importedClass, false));
    }

    public void addStaticImport(String importedClass) {
        staticImports.add(new Import(importedClass, true));
    }

    public void addInnerClazz(Clazz clazz) {
        innerClasses.add(clazz);
    }

    public void addInterface(String interfaceName) {
        interfaces.add(interfaceName);
    }
}
