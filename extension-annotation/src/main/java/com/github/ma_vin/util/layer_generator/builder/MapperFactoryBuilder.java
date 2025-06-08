package com.github.ma_vin.util.layer_generator.builder;

import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseAccessMapper;
import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseTransportMapper;
import com.github.ma_vin.util.layer_generator.annotations.mapper.ExtendingAccessMapper;
import com.github.ma_vin.util.layer_generator.annotations.mapper.ExtendingTransportMapper;
import lombok.extern.log4j.Log4j2;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.*;


/**
 * Class which generates the mapper factories
 */
@SupportedAnnotationTypes(
        "com.github.ma_vin.util.layer_generator.annotations.mapper.*")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@Log4j2
public class MapperFactoryBuilder extends AbstractFactoryBuilder {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<Class<?>, Set<TypeElement>> annotatedClasses = createAnnotatedClassesMap(annotations, roundEnv);

        try {
            Set<GenerateInformation> accessMapperClassesToGenerate = determineClasses(annotatedClasses, MapperType.ACCESS);
            Set<GenerateInformation> transportMapperClassesToGenerate = determineClasses(annotatedClasses, MapperType.TRANSPORT);
            log.info("{} access mapper class to add at object factory", accessMapperClassesToGenerate.size());
            log.info("{} transport mapper class to add at object factory", transportMapperClassesToGenerate.size());
            generateFactory(accessMapperClassesToGenerate, MapperType.ACCESS.getFactoryClassName());
            generateFactory(transportMapperClassesToGenerate, MapperType.TRANSPORT.getFactoryClassName());
        } catch (NoSuchElementException | IOException e) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<Class<?>, Set<TypeElement>> createDefaultAnnotatedClassesMap() {
        Map<Class<?>, Set<TypeElement>> annotatedClasses = new HashMap<>();

        annotatedClasses.put(BaseAccessMapper.class, new HashSet<>());
        annotatedClasses.put(BaseTransportMapper.class, new HashSet<>());

        annotatedClasses.put(ExtendingAccessMapper.class, new HashSet<>());
        annotatedClasses.put(ExtendingTransportMapper.class, new HashSet<>());

        return annotatedClasses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<String, Class<?>> getNameToClassMap() {
        Map<String, Class<?>> nameToClass = new HashMap<>();

        nameToClass.put(BaseAccessMapper.class.getSimpleName(), BaseAccessMapper.class);
        nameToClass.put(BaseTransportMapper.class.getSimpleName(), BaseTransportMapper.class);

        nameToClass.put(ExtendingAccessMapper.class.getSimpleName(), ExtendingAccessMapper.class);
        nameToClass.put(ExtendingTransportMapper.class.getSimpleName(), ExtendingTransportMapper.class);

        return nameToClass;
    }

    /**
     * Determines the base and the extending classes which should be provided by factory
     *
     * @param annotatedClasses the map which contains the annotation and their set of annotated classes
     * @param mapperType       the type of mapper wish should be determined
     * @return A set of information for generating the given extending type
     */
    private Set<GenerateInformation> determineClasses(Map<Class<?>, Set<TypeElement>> annotatedClasses, MapperType mapperType) {
        Set<GenerateInformation> classesToGenerate =
                switch (mapperType) {
                    case ACCESS -> determineExtendingClasses(annotatedClasses, ExtendingAccessMapper.class);
                    case TRANSPORT -> determineExtendingClasses(annotatedClasses, ExtendingTransportMapper.class);
                };

        Set<GenerateInformation> baseClasses =
                switch (mapperType) {
                    case ACCESS -> determineBaseClasses(annotatedClasses, BaseAccessMapper.class,
                            e -> getPackageNameFromQualified(e.getQualifiedName().toString()));
                    case TRANSPORT -> determineBaseClasses(annotatedClasses, BaseTransportMapper.class,
                            e -> getPackageNameFromQualified(e.getQualifiedName().toString()));
                };

        return aggregateBaseAndExtendingInformation(classesToGenerate, baseClasses);
    }
}
