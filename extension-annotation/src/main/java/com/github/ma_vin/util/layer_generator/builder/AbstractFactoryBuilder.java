package com.github.ma_vin.util.layer_generator.builder;

import com.github.ma_vin.util.layer_generator.sources.Clazz;
import com.github.ma_vin.util.layer_generator.sources.Constructor;
import com.github.ma_vin.util.layer_generator.sources.Method;
import com.github.ma_vin.util.layer_generator.sources.Qualifier;
import lombok.extern.log4j.Log4j2;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.tools.JavaFileObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * class with common methods to generate an object factory.
 */
@Log4j2
public abstract class AbstractFactoryBuilder extends AbstractProcessor {

    /**
     * Creates the basic map with annotations as key and the elements, which used them, as value
     *
     * @param annotations Annotations which are considered by the processor
     * @param roundEnv    environment for information about the current and prior round
     * @return the created and filled map
     */
    protected Map<Class<?>, Set<TypeElement>> createAnnotatedClassesMap(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<Class<?>, Set<TypeElement>> annotatedClasses = createDefaultAnnotatedClassesMap();
        Map<String, Class<?>> nameToClass = getNameToClassMap();

        for (TypeElement annotation : annotations) {
            Class<?> annotationClass = nameToClass.get(annotation.getSimpleName().toString());
            annotatedClasses.computeIfAbsent(annotationClass, k -> new HashSet<>());
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
     * Determines the set of information for generating for a given annotation of extending type
     *
     * @param annotatedClasses    the map which contains the annotation and their set of annotated classes
     * @param extendingAnnotation The extending annotation
     * @param <A>                 Class of the extending annotation
     * @return A set of information for generating the given extending type
     */
    protected <A extends Annotation> Set<GenerateInformation> determineExtendingClasses(Map<Class<?>, Set<TypeElement>> annotatedClasses
            , Class<A> extendingAnnotation) {

        return annotatedClasses.get(extendingAnnotation).stream()
                .map(e -> {
                    if (e.getSuperclass().getKind() != TypeKind.DECLARED) {
                        return null;
                    }
                    TypeElement extendedClass = (TypeElement) processingEnv.getTypeUtils().asElement(e.getSuperclass());
                    GenerateInformation generateInformation = createCommonGenerateInformation(e);
                    generateInformation.setBaseClassName(extendedClass.getSimpleName().toString());
                    generateInformation.setBasePackageName(getPackageNameFromQualified(extendedClass.getQualifiedName().toString()));
                    generateInformation.setModelPackage(null);
                    return generateInformation;
                }).collect(Collectors.toSet());
    }

    /**
     * Determines the set of information for generating for a given annotation of base type
     *
     * @param annotatedClasses   the map which contains the annotation and their set of annotated classes
     * @param baseAnnotation     The base annotation
     * @param modelPackageGetter FunctionalInterface to get annotation value with the model package.
     * @param <A>                Class of the base annotation
     * @return A set of information for generating the given base type
     */
    protected <A extends Annotation> Set<GenerateInformation> determineBaseClasses(Map<Class<?>, Set<TypeElement>> annotatedClasses
            , Class<A> baseAnnotation, ModelPackageGetter modelPackageGetter) {

        return annotatedClasses.get(baseAnnotation).stream()
                .map(e -> {
                    GenerateInformation generateInformation = createCommonGenerateInformation(e);
                    generateInformation.setBaseClassName(generateInformation.getClassName());
                    generateInformation.setBasePackageName(generateInformation.getPackageName());
                    generateInformation.setModelPackage(modelPackageGetter.getModelPackage(e));
                    return generateInformation;
                }).collect(Collectors.toSet());
    }

    /**
     * Creates a common information for generation, which contains class and package name
     *
     * @param classTypeElement The element to determine the names
     * @return a new information
     */
    protected static GenerateInformation createCommonGenerateInformation(TypeElement classTypeElement) {
        GenerateInformation generateInformation = new GenerateInformation();
        generateInformation.setClassName(classTypeElement.getSimpleName().toString());
        generateInformation.setPackageName(getPackageNameFromQualified(classTypeElement.getQualifiedName().toString()));
        return generateInformation;
    }

    /**
     * Determines the package name of a given full qualified name
     *
     * @param qualifiedName the full qualified name which contains the package name
     * @return the package name
     */
    protected static String getPackageNameFromQualified(String qualifiedName) {
        return qualifiedName.substring(0, qualifiedName.lastIndexOf("."));
    }

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
     * @throws IOException if an I/O error occurred
     */
    protected BufferedWriter createBufferedWriter(JavaFileObject javaFileObject) throws IOException {
        return new BufferedWriter(javaFileObject.openWriter());
    }

    /**
     * FunctionalInterface to get annotation value with the model package
     */
    @FunctionalInterface
    protected interface ModelPackageGetter {
        /**
         * Getter of the model package
         * @param extendedClassTypeElement the type element of an extending class
         * @return the model package
         */
        String getModelPackage(TypeElement extendedClassTypeElement);
    }
}
