package de.ma_vin.util.layer.generator.sources;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Data
public class Interface extends AbstractGenerateLines implements Comparable<Interface> {

    private String packageName;
    private String interfaceName;

    private Set<Import> imports = new TreeSet<>();
    private JavaDoc description = null;
    private Set<Annotation> annotations = new TreeSet<>();
    private Set<Generic> generics = new TreeSet<>();
    private String extension = null;
    private Set<MethodDeclaration> methodDeclarations = new TreeSet<>();
    private boolean isInner;

    public Interface(String packageName, String interfaceName) {
        this.packageName = packageName;
        this.interfaceName = interfaceName;
    }

    public Interface(String interfaceName) {
        this(null, interfaceName);
        isInner = true;
    }

    public void addMethodDeclaration(MethodDeclaration methodDeclaration) {
        methodDeclarations.add(methodDeclaration);
    }

    public void addMethodDeclaration(String methodType, String methodName, String... parameterPairs) {
        addMethodDeclarationWithDescription(methodType, methodName, null, parameterPairs);
    }

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

    public void addAnnotation(Annotation annotation) {
        annotations.add(annotation);
    }

    public void addAnnotation(String annotationName) {
        addAnnotation(new Annotation(annotationName));
    }

    public void addImport(String importedClass) {
        imports.add(new Import(importedClass, false));
    }

    public void addGeneric(String genericName) {
        addGeneric(new Generic(genericName));
    }

    public void addGeneric(Generic generic) {
        generics.add(generic);
    }

    public void setDescription(JavaDoc description) {
        this.description = description;
    }

    public void setDescription(String description, String... args) {
        setDescription(new JavaDoc(String.format(description, args)));
    }

    @Override
    @SuppressWarnings("java:S1210")
    public int compareTo(Interface o) {
        return interfaceName.compareTo(o.interfaceName);
    }

    @Data
    public static class MethodDeclaration implements Comparable<MethodDeclaration> {
        private JavaDoc javaDoc = null;
        private List<Annotation> annotations = new ArrayList<>();
        private String methodType = "void";
        private String methodName;
        private List<Parameter> parameters = new ArrayList<>();

        public MethodDeclaration(String methodName) {
            this.methodName = methodName;
        }

        public MethodDeclaration(String methodType, String methodName) {
            this.methodName = methodName;
            this.methodType = methodType;
        }

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
            return generate().stream().map(s -> tabsToAdd + s).collect(Collectors.toList());
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
