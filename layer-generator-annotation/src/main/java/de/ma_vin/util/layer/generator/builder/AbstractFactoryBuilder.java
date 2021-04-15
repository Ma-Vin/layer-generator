package de.ma_vin.util.layer.generator.builder;

import de.ma_vin.util.layer.generator.sources.Clazz;
import de.ma_vin.util.layer.generator.sources.Constructor;
import de.ma_vin.util.layer.generator.sources.Method;
import de.ma_vin.util.layer.generator.sources.Qualifier;
import lombok.extern.log4j.Log4j2;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
public abstract class AbstractFactoryBuilder extends AbstractProcessor {

    protected Map<Class<?>, Set<TypeElement>> createAnnotatedClassesMap(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<Class<?>, Set<TypeElement>> annotatedClasses = createDefaultAnnotatedClassesMap();
        Map<String, Class<?>> nameToClass = getNameToClassMap();

        for (TypeElement annotation : annotations) {
            Class<?> annotationClass = nameToClass.get(annotation.getSimpleName().toString());
            if (!annotatedClasses.containsKey(annotationClass)) {
                annotatedClasses.put(annotationClass, new HashSet<>());
            }
            roundEnv.getElementsAnnotatedWith(annotation).forEach(e -> {
                if (e instanceof TypeElement) {
                    annotatedClasses.get(annotationClass).add((TypeElement) e);
                }
            });
        }
        return annotatedClasses;
    }

    /**
     * Creates a map which contains already the relevant keys of typ base and extending annotations
     *
     * @return the created map
     */
    protected abstract Map<Class<?>, Set<TypeElement>> createDefaultAnnotatedClassesMap();

    /**
     * Determines a map of simple class name and class of the relevant annotations
     *
     * @return The map
     */
    protected abstract Map<String, Class<?>> getNameToClassMap();

    /**
     * Aggregates the extending classes and the base classes. All extending classes are insert.
     * Only those base ones are inserted which are not replaced by some extending one
     *
     * @param classesToGenerate set of extending class information
     * @param baseClasses       set of base class information
     * @return a new aggregated set
     */
    protected Set<GenerateInformation> aggregateBaseAndExtendingInformation(Set<GenerateInformation> classesToGenerate
            , Set<GenerateInformation> baseClasses) {
        classesToGenerate.forEach(extendingClass ->
                extendingClass.setModelPackage(baseClasses.stream()
                        .filter(baseClass -> baseClass.getClassName().equals(extendingClass.getBaseClassName()) && baseClass.getPackageName().equals(extendingClass.getBasePackageName()))
                        .findFirst().map(GenerateInformation::getModelPackage)
                        .orElseThrow())
        );

        Set<GenerateInformation> result = new HashSet<>();
        result.addAll(classesToGenerate);
        result.addAll(
                baseClasses.stream()
                        .filter(baseClass -> classesToGenerate.stream()
                                .noneMatch(extendingClass -> baseClass.getClassName().equals(extendingClass.getBaseClassName()) && baseClass.getPackageName().equals(extendingClass.getBasePackageName())))
                        .collect(Collectors.toSet())
        );

        return result;
    }

    /**
     * Generates sources of the factory for data access, domain model and data transport objects
     *
     * @param generateInformationSet relevant set an information for generation collected from annotated classes
     * @param factoryClassName       name of the Factory
     * @throws IOException Thrown if JavaFileObject or BufferedWriter creation fails
     */
    protected void generateFactory(Set<GenerateInformation> generateInformationSet, String factoryClassName) throws IOException {
        Optional<GenerateInformation> first = generateInformationSet.stream().findFirst();
        if (first.isEmpty()) {
            return;
        }
        Clazz clazz = new Clazz(first.get().getModelPackage(), factoryClassName);

        Constructor constructor = new Constructor(clazz.getClassName());
        constructor.setQualifier(Qualifier.PRIVATE);
        clazz.addConstructor(constructor);

        generateInformationSet.forEach(generateInformation -> {
            clazz.addImport(String.format("%s.%s", generateInformation.getBasePackageName(), generateInformation.getBaseClassName()));
            clazz.addImport(String.format("%s.%s", generateInformation.getPackageName(), generateInformation.getClassName()));

            Method createMethod = new Method(String.format("create%s", generateInformation.getBaseClassName()));
            createMethod.setStatic(true);
            createMethod.setQualifier(Qualifier.PUBLIC);
            createMethod.setMethodType(generateInformation.getBaseClassName());
            createMethod.addLine("return new %s();", generateInformation.getClassName());

            clazz.addMethod(createMethod);
        });

        JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(clazz.getPackageName() + "." + clazz.getClassName());
        BufferedWriter bw = createBufferedWriter(javaFileObject);

        clazz.generate().forEach(s -> {
            try {
                bw.write(s);
                bw.newLine();
            } catch (IOException e) {
                log.error("Exception while writing factories: " + e.getMessage());
            }
        });
        bw.flush();
        bw.close();
    }

    /**
     * Creator Method to be make mocking easier at unit test
     *
     * @param javaFileObject file to write at
     * @return created buffered writer
     */
    protected BufferedWriter createBufferedWriter(JavaFileObject javaFileObject) throws IOException {
        return new BufferedWriter(javaFileObject.openWriter());
    }
}
