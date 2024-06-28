package com.github.ma_vin.util.layer_generator.builder;

import com.github.ma_vin.util.layer_generator.annotations.model.*;
import lombok.extern.log4j.Log4j2;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.*;

@SupportedAnnotationTypes(
        "com.github.ma_vin.util.layer_generator.annotations.model.*")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@Log4j2
public class ObjectFactoryBuilder extends AbstractFactoryBuilder {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<Class<?>, Set<TypeElement>> annotatedClasses = createAnnotatedClassesMap(annotations, roundEnv);

        try {
            Set<GenerateInformation> daoClassesToGenerate = determineClasses(annotatedClasses, ModelType.DAO);
            Set<GenerateInformation> domainClassesToGenerate = determineClasses(annotatedClasses, ModelType.DOMAIN);
            Set<GenerateInformation> dtoClassesToGenerate = determineClasses(annotatedClasses, ModelType.DTO);
            log.info("{} dao class to add at object factory", daoClassesToGenerate.size());
            log.info("{} domain class to add at object factory", domainClassesToGenerate.size());
            log.info("{} dto class to add at object factory", dtoClassesToGenerate.size());
            generateFactory(daoClassesToGenerate, ModelType.DAO.getFactoryClassName());
            generateFactory(domainClassesToGenerate, ModelType.DOMAIN.getFactoryClassName());
            generateFactory(dtoClassesToGenerate, ModelType.DTO.getFactoryClassName());
        } catch (NoSuchElementException | IOException e) {
            return false;
        }
        return true;
    }

    @Override
    protected Map<Class<?>, Set<TypeElement>> createDefaultAnnotatedClassesMap() {
        Map<Class<?>, Set<TypeElement>> annotatedClasses = new HashMap<>();

        annotatedClasses.put(BaseDao.class, new HashSet<>());
        annotatedClasses.put(BaseDomain.class, new HashSet<>());
        annotatedClasses.put(BaseDto.class, new HashSet<>());

        annotatedClasses.put(ExtendingDao.class, new HashSet<>());
        annotatedClasses.put(ExtendingDomain.class, new HashSet<>());
        annotatedClasses.put(ExtendingDto.class, new HashSet<>());

        return annotatedClasses;
    }

    @Override
    protected Map<String, Class<?>> getNameToClassMap() {
        Map<String, Class<?>> nameToClass = new HashMap<>();

        nameToClass.put(BaseDao.class.getSimpleName(), BaseDao.class);
        nameToClass.put(BaseDomain.class.getSimpleName(), BaseDomain.class);
        nameToClass.put(BaseDto.class.getSimpleName(), BaseDto.class);

        nameToClass.put(ExtendingDao.class.getSimpleName(), ExtendingDao.class);
        nameToClass.put(ExtendingDomain.class.getSimpleName(), ExtendingDomain.class);
        nameToClass.put(ExtendingDto.class.getSimpleName(), ExtendingDto.class);

        return nameToClass;
    }

    /**
     * Determines the base and the extending classes which should be provided by factory
     *
     * @param annotatedClasses the map which contains the annotation and their set of annotated classes
     * @param modelType        the type of model wish should be determined
     * @return A set of information for generating the given extending type
     */
    private Set<GenerateInformation> determineClasses(Map<Class<?>, Set<TypeElement>> annotatedClasses, ModelType modelType) {
        Set<GenerateInformation> classesToGenerate =
                switch (modelType) {
                    case DAO -> determineExtendingClasses(annotatedClasses, ExtendingDao.class);
                    case DOMAIN -> determineExtendingClasses(annotatedClasses, ExtendingDomain.class);
                    case DTO -> determineExtendingClasses(annotatedClasses, ExtendingDto.class);
                };

        Set<GenerateInformation> baseClasses =
                switch (modelType) {
                    case DAO -> determineBaseClasses(annotatedClasses, BaseDao.class, e -> e.getAnnotation(BaseDao.class).value());
                    case DOMAIN -> determineBaseClasses(annotatedClasses, BaseDomain.class, e -> e.getAnnotation(BaseDomain.class).value());
                    case DTO -> determineBaseClasses(annotatedClasses, BaseDto.class, e -> e.getAnnotation(BaseDto.class).value());
                };

        return aggregateBaseAndExtendingInformation(classesToGenerate, baseClasses);
    }
}
