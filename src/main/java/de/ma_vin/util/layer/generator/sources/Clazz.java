package de.ma_vin.util.layer.generator.sources;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

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
    private Set<Constructor> constructors = new TreeSet<>();
    private Set<Attribute> attributes = new TreeSet<>();
    private Set<Method> methods = new TreeSet<>();
    private Set<Clazz> innerClasses = new TreeSet<>();
    private Set<String> interfaces = new TreeSet<>();
    private String extension = null;
    private Qualifier qualifier = Qualifier.PUBLIC;
    private boolean isStatic;
    private boolean isInner;
    private boolean isAbstract;

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
        result.add(String.format("%s%s%s class %s%s%s {", qualifier.getText(), getStaticText(), getAbstractText()
                , className, getExtensionText(), getInterfaceText()));
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

    @Override
    @SuppressWarnings("java:S1210")
    public int compareTo(Clazz o) {
        return className.compareTo(o.className);
    }

    public void addConstructor(Constructor constructor) {
        constructors.add(constructor);
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
