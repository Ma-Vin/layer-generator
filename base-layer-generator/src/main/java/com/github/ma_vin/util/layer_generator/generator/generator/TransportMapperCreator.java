package com.github.ma_vin.util.layer_generator.generator.generator;

import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseTransportMapper;
import com.github.ma_vin.util.layer_generator.builder.MapperType;
import com.github.ma_vin.util.layer_generator.builder.ModelType;
import com.github.ma_vin.util.layer_generator.config.elements.Config;
import com.github.ma_vin.util.layer_generator.config.elements.Entity;
import com.github.ma_vin.util.layer_generator.config.elements.Version;
import com.github.ma_vin.util.layer_generator.config.elements.fields.Field;
import com.github.ma_vin.util.layer_generator.config.elements.references.Reference;
import com.github.ma_vin.util.layer_generator.logging.ILogWrapper;
import com.github.ma_vin.util.layer_generator.sources.AbstractGenerateLines;
import com.github.ma_vin.util.layer_generator.sources.Clazz;
import com.github.ma_vin.util.layer_generator.sources.Method;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TransportMapperCreator extends AbstractMapperCreator {

    public static final String ABSTRACT_TRANSPORT_MAPPER_CLASS_NAME = "AbstractTransportMapper";
    public static final String MAPPER_TYPE_NAME = "Transport";
    public static final String CONVERT_TO_DTO_NAME = "convertToDto";
    public static final String CONVERT_TO_DOMAIN_NAME = "convertToDomain";
    public static final String DTO_PARAMETER = "dto";
    public static final String DOMAIN_PARAMETER = "domain";

    public TransportMapperCreator(Config config, ILogWrapper logger) {
        super(config, logger);
    }

    /**
     * Creates the abstract mapper which is used by all transport mapper
     *
     * @param mapperPackageName package of the mapper to use
     * @param mapperPackageDir  directory of the package
     * @param dtoPackageName    name of base dto package
     * @param domainPackageName name of base domain package
     * @return {@code true} if generation was successful
     */
    public boolean createAbstractTransportMapper(String mapperPackageName, Optional<File> mapperPackageDir, String dtoPackageName, String domainPackageName) {
        Clazz mapperClass = new Clazz(mapperPackageName, ABSTRACT_TRANSPORT_MAPPER_CLASS_NAME);
        logger.debug("Create abstract access mapper " + mapperClass.getClassName());
        mapperClass.setAbstract(true);
        mapperClass.setExtension(CommonMapperCreator.ABSTRACT_MAPPER_CLASS_NAME);

        mapperClass.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, dtoPackageName, DtoCreator.DTO_INTERFACE));
        mapperClass.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, domainPackageName, DomainCreator.DOMAIN_INTERFACE));

        mapperClass.setDescription("Generated abstract class which provides generic methods to convert a data transport to a domain object and the other way around");

        createAndAddConvertToGenericMethod(mapperClass, CONVERT_TO_DTO_NAME, DomainCreator.DOMAIN_INTERFACE, DtoCreator.DTO_INTERFACE);
        createAndAddConvertToGenericMethod(mapperClass, CONVERT_TO_DOMAIN_NAME, DtoCreator.DTO_INTERFACE, DomainCreator.DOMAIN_INTERFACE);

        return writeClassFile(mapperPackageDir, mapperClass);
    }

    /**
     * Creates the transport mapper which converts dto to domain and the other way around.
     *
     * @param entities          List of Entities to generate
     * @param groupingName      grouping which is used for class name
     * @param mapperPackageName package name which is to use for mapper
     * @param dtoPackageName    name of the package to use for dto
     * @param domainPackageName name of the package to use for domain
     * @param mapperPackageDir  directory where to write sources of created mapper
     * @return {@code true} if creating was successful. Otherwise {@code false}
     */
    public boolean createTransportMapper(List<Entity> entities, String groupingName, String mapperPackageName, String dtoPackageName
            , String domainPackageName, Optional<File> mapperPackageDir) {

        if (entities.stream().noneMatch(e -> isEntityRelevant(e) && !e.getIsAbstract())) {
            logger.debug("No access mapper need for " + mapperPackageName);
            return true;
        }
        Clazz mapperClass = new Clazz(mapperPackageName, getMapperName(groupingName));
        logger.debug("Create transport mapper " + mapperClass.getClassName());

        mapperClass.setExtension(ABSTRACT_TRANSPORT_MAPPER_CLASS_NAME);

        mapperClass.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, dtoPackageName, ModelType.DTO.getFactoryClassName()));
        mapperClass.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, domainPackageName, ModelType.DOMAIN.getFactoryClassName()));
        mapperClass.addImport(BaseTransportMapper.class.getName());

        mapperClass.addAnnotation(BaseTransportMapper.class.getSimpleName());

        mapperClass.setDescription("Generated class which provides methods to convert a data transport to a domain object of sub package <i>%s</i> and the other way around"
                , groupingName);

        createGetInstance(mapperClass, MapperType.TRANSPORT);

        createConvertMethods(mapperClass, entities, dtoPackageName, domainPackageName);
        createVersionConvertMethods(mapperClass, entities, dtoPackageName, domainPackageName);

        return writeClassFile(mapperPackageDir, mapperClass);
    }

    /**
     * Creates the methods which converts entities between data transport and domain objects
     *
     * @param mapperClass       the class where to add methods
     * @param entities          entities which should be used
     * @param dtoPackageName    name of the package to use for dto
     * @param domainPackageName name of the package to use for domain
     */
    private void createConvertMethods(Clazz mapperClass, List<Entity> entities, String dtoPackageName, String domainPackageName) {
        entities.stream().filter(e -> !e.getIsAbstract()).forEach(e -> {
            createConvertToDtoMethods(mapperClass, e, dtoPackageName, domainPackageName);
            createConvertToDomainMethods(mapperClass, e, dtoPackageName, domainPackageName);
        });
    }

    /**
     * Creates the methods which converts version entities between data transport and domain objects
     *
     * @param mapperClass       the class where to add methods
     * @param entities          entities whose versions should be used
     * @param dtoPackageName    name of the package to use for dto
     * @param domainPackageName name of the package to use for domain
     */
    private void createVersionConvertMethods(Clazz mapperClass, List<Entity> entities, String dtoPackageName, String domainPackageName) {
        entities.stream().flatMap(e -> e.getVersions().stream()).map(Version::getVersionEntity).forEach(e -> {
            createConvertToDtoMethods(mapperClass, e, dtoPackageName, domainPackageName);
            createConvertToDomainMethods(mapperClass, e, dtoPackageName, domainPackageName);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getMapperName(String groupingName) {
        return getMapperName(MAPPER_TYPE_NAME, groupingName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getParameterOfRelevantSingleModelValuesText(Entity entity) {
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getParameterOfParentReferencesText(Reference referenceToParent) {
        return "";
    }

    /**
     * Determines the name of the converting method
     *
     * @param entity entity which is to convert
     * @return Name of the dto converting method
     */
    private String getConvertMethodNameDto(Entity entity) {
        return getConvertMethodName(entity, DTO_POSTFIX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean hasIncludeChildrenParameter(Entity entity, EntityRelevantChecker relevantChecker) {
        return false;
    }

    /**
     * Creates mapping methods from dto to domain
     *
     * @param mapperClass       class where to add methods at
     * @param entity            entity whose properties are to map
     * @param dtoPackageName    name of base dto package
     * @param domainPackageName name of base domain package
     */
    private void createConvertToDomainMethods(Clazz mapperClass, Entity entity, String dtoPackageName, String domainPackageName) {
        if (!isEntityRelevant(entity) || entity.getRealDerivedFrom() != null) {
            logger.debug(String.format("Entity %s is not need to converted by %s", entity.getBaseName(), mapperClass.getClassName()));
            return;
        }
        mapperClass.addImport(getPackageAndClass(entity, dtoPackageName, DTO_POSTFIX));
        mapperClass.addImport(getPackageAndClass(getNonVersionParentEntity(entity), domainPackageName, DOMAIN_POSTFIX));
        mapperClass.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, domainPackageName, DomainCreator.DOMAIN_INTERFACE));

        entity.getParentRefs().stream()
                .filter(ref -> ref.isOwner() && !ref.isList())
                .filter(ref -> ref.getRealTargetEntity().getRealDerivedFrom() == null)
                .forEach(ref ->
                        createConvertToDomainMethodWithParent(mapperClass, entity, ref, domainPackageName)
                );
        createConvertToDomainMethod(mapperClass, entity);
    }

    /**
     * Creates mapping methods from dto to domain with a given parent
     *
     * @param mapperClass       class where to add methods at
     * @param entity            entity whose properties are to map
     * @param referenceToParent reference to parent which should be used for the parent parameter
     * @param domainPackageName name of base domain package
     */
    private void createConvertToDomainMethodWithParent(Clazz mapperClass, Entity entity, Reference referenceToParent, String domainPackageName) {
        if (!isEntityRelevant(referenceToParent.getRealTargetEntity()) || !isParentRefRelevant(referenceToParent)) {
            logger.debug(String.format("No connection is to generate to parent %s from entity %s"
                    , referenceToParent.getTargetEntity(), entity.getBaseName()));
            return;
        }

        CreateMethodParameterContainer createMethodParams = getDomainCreateMethodParameterContainer(entity);

        createConvertMethodWithParentWithoutMap(mapperClass, createMethodParams, referenceToParent, domainPackageName);

        Method convertMethodWithMap = createConvertMethodWithParentBase(mapperClass, createMethodParams, referenceToParent, domainPackageName);
        addMappedObjectsParam(convertMethodWithMap, entity, DomainCreator.DOMAIN_INTERFACE, DTO_POSTFIX, DOMAIN_POSTFIX);

        convertMethodWithMap.addLine("%s result = %s(%s,%s %s);"
                , entity.getBaseName()
                , getConvertMethodName(entity, DOMAIN_POSTFIX)
                , getLowerFirst(entity.getBaseName())
                , hasIncludeChildrenParameter(entity, TransportMapperCreator::isEntityRelevant) ? String.format(" %s,", INCLUDE_CHILDREN_PARAMETER) : ""
                , MAPPED_OBJECTS_PARAMETER_TEXT
        );
        convertMethodWithMap.addLine("if (result != null) {");
        convertMethodWithMap.addLine("%sparent.set%s(result);", AbstractGenerateLines.TAB, getUpperFirst(referenceToParent.getReferenceName()));
        convertMethodWithMap.addLine("}");
        convertMethodWithMap.addLine(RETURN_RESULT_TEXT);

        addConvertMethodDescriptionWithParent(convertMethodWithMap, entity, DTO_POSTFIX, DOMAIN_POSTFIX);

        mapperClass.addMethod(convertMethodWithMap);
        mapperClass.addImport(Map.class.getName());
    }

    /**
     * Creates mapping methods from dto to domain without a given parent as parameter
     *
     * @param mapperClass class where to add methods at
     * @param entity      entity whose properties are to map
     */
    private void createConvertToDomainMethod(Clazz mapperClass, Entity entity) {
        CreateMethodParameterContainer createMethodParams = getDomainCreateMethodParameterContainer(entity);

        createConvertMethodWithoutMap(mapperClass, createMethodParams);

        Method convertMethodWithMap = createConvertMethodBase(createMethodParams);
        addMappedObjectsParam(convertMethodWithMap, entity, DomainCreator.DOMAIN_INTERFACE, DTO_POSTFIX, DOMAIN_POSTFIX);

        convertMethodWithMap.addLine("return convertToDomain(%1$s, %2$s, DomainObjectFactory::create%3$s, (%4$s, %5$s) -> getInstance().set%3$sValues(%4$s, %5$s)"
                , getLowerFirst(entity.getBaseName()), MAPPED_OBJECTS_PARAMETER_TEXT, getUpperFirst(createMethodParams.getAdjustedEntity().getBaseName())
                , DTO_PARAMETER, DOMAIN_PARAMETER);
        convertMethodWithMap.addTabbedLine(", (%1$s, %2$s) -> getInstance().set%3$sSingleReferences(%1$s, %2$s, %5$s%4$s)", 2
                , DTO_PARAMETER, DOMAIN_PARAMETER, getUpperFirst(createMethodParams.getAdjustedEntity().getBaseName()), MAPPED_OBJECTS_PARAMETER_TEXT
                , hasSingleRefWithChildren(entity, TransportMapperCreator::isEntityRelevant, l -> l) ? "includeChildren, " : "");
        convertMethodWithMap.addTabbedLine(", (%1$s, %2$s) -> {", 2, DTO_PARAMETER, DOMAIN_PARAMETER);
        convertMethodWithMap.addLine("});");

        addConvertMethodDescription(convertMethodWithMap, entity, DTO_POSTFIX, DOMAIN_POSTFIX);

        mapperClass.addMethod(convertMethodWithMap);
        mapperClass.addImport(Map.class.getName());

        createSetValueMethod(mapperClass, entity, TransportMapperCreator::isFieldRelevant, DTO_POSTFIX, DOMAIN_POSTFIX, DTO_PARAMETER, DOMAIN_PARAMETER);
        createSetSingleReferenceMethod(mapperClass, createMethodParams, DTO_PARAMETER, DOMAIN_PARAMETER, DomainCreator.DOMAIN_INTERFACE, l -> l);
    }

    /**
     * Creates mapping methods from domain to dto
     *
     * @param mapperClass       class where to add methods at
     * @param entity            entity whose properties are to map
     * @param dtoPackageName    name of base dto package
     * @param domainPackageName name of base domain package
     */
    private void createConvertToDtoMethods(Clazz mapperClass, Entity entity, String dtoPackageName, String domainPackageName) {
        if (!isEntityRelevant(entity)) {
            logger.debug(String.format("Entity %s is not need to converted by %s", entity.getBaseName(), mapperClass.getClassName()));
            return;
        }
        mapperClass.addImport(getPackageAndClass(entity, dtoPackageName, DTO_POSTFIX));
        mapperClass.addImport(getPackageAndClass(getDomainEntityForImport(entity), domainPackageName, DOMAIN_POSTFIX));
        mapperClass.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, dtoPackageName, DtoCreator.DTO_INTERFACE));

        entity.getParentRefs().stream()
                .filter(ref -> ref.isOwner() && !ref.isList())
                .forEach(ref ->
                        createConvertToDtoMethodWithParent(mapperClass, entity, ref, dtoPackageName)
                );
        createConvertToDtoMethod(mapperClass, entity);
    }

    /**
     * Determines the {@link Entity} which is to use for the import statement of a domain object
     *
     * @param entity the entity whose class should be imported
     * @return the non versioned source from which it was derived. If it is not derived the non versioned entity
     */
    private Entity getDomainEntityForImport(Entity entity) {
        Entity versionParentEntity = getNonVersionParentEntity(entity);
        return versionParentEntity.getRealDerivedFrom() == null ? versionParentEntity : versionParentEntity.getRealDerivedFrom();
    }

    /**
     * Creates mapping methods from domain to dto with a given parent
     *
     * @param mapperClass       class where to add methods at
     * @param entity            entity whose properties are to map
     * @param referenceToParent reference to parent which should be used for the parent parameter
     * @param dtoPackageName    name of base dto package
     */
    private void createConvertToDtoMethodWithParent(Clazz mapperClass, Entity entity, Reference referenceToParent, String dtoPackageName) {
        if (!isEntityRelevant(referenceToParent.getRealTargetEntity())) {
            logger.debug(String.format("No connection is to generate to parent %s from entity %s"
                    , referenceToParent.getTargetEntity(), entity.getBaseName()));
            return;
        }

        CreateMethodParameterContainer createMethodParams = getDtoCreateMethodParameterContainer(entity);

        createConvertMethodWithParentWithoutMap(mapperClass, createMethodParams, referenceToParent, dtoPackageName);

        Method convertMethodWithMap = createConvertMethodWithParentBase(mapperClass, createMethodParams, referenceToParent, dtoPackageName);
        addMappedObjectsParam(convertMethodWithMap, entity, DtoCreator.DTO_INTERFACE, DOMAIN_POSTFIX, DTO_POSTFIX);

        convertMethodWithMap.addLine("%sDto result = %s(%s,%s %s);"
                , entity.getBaseName()
                , getConvertMethodNameDto(entity)
                , getLowerFirst(createMethodParams.getSourceAdjustedEntity().getBaseName())
                , hasIncludeChildrenParameter(entity, TransportMapperCreator::isEntityRelevant) ? String.format(" %s,", INCLUDE_CHILDREN_PARAMETER) : ""
                , MAPPED_OBJECTS_PARAMETER_TEXT
        );
        convertMethodWithMap.addLine("if (result != null) {");
        convertMethodWithMap.addLine("%sparent.set%s(result);", AbstractGenerateLines.TAB, getUpperFirst(referenceToParent.getReferenceName()));
        convertMethodWithMap.addLine("}");
        convertMethodWithMap.addLine(RETURN_RESULT_TEXT);

        addConvertMethodDescriptionWithParent(convertMethodWithMap, entity, DOMAIN_POSTFIX, DTO_POSTFIX);

        mapperClass.addMethod(convertMethodWithMap);
        mapperClass.addImport(Map.class.getName());
    }

    /**
     * Creates mapping methods from domain to dto without a given parent as parameter
     *
     * @param mapperClass class where to add methods at
     * @param entity      entity whose properties are to map
     */
    private void createConvertToDtoMethod(Clazz mapperClass, Entity entity) {
        CreateMethodParameterContainer createMethodParams = getDtoCreateMethodParameterContainer(entity);

        createConvertMethodWithoutMap(mapperClass, createMethodParams);

        Method convertMethodWithMap = createConvertMethodBase(createMethodParams);
        addMappedObjectsParam(convertMethodWithMap, entity, DtoCreator.DTO_INTERFACE, DOMAIN_POSTFIX, DTO_POSTFIX);

        convertMethodWithMap.addLine("return convertToDto(%1$s, %2$s, DtoObjectFactory::create%3$sDto, (%4$s, %5$s) -> getInstance().set%3$sDtoValues(%4$s, %5$s)"
                , getLowerFirst(getSourceEntityBaseName(entity, DOMAIN_POSTFIX)), MAPPED_OBJECTS_PARAMETER_TEXT, getUpperFirst(entity.getBaseName())
                , DOMAIN_PARAMETER, DTO_PARAMETER);
        convertMethodWithMap.addTabbedLine(", (%1$s, %2$s) -> getInstance().set%3$sDtoSingleReferences(%1$s, %2$s, %5$s%4$s)", 2
                , DOMAIN_PARAMETER, DTO_PARAMETER, getUpperFirst(entity.getBaseName()), MAPPED_OBJECTS_PARAMETER_TEXT
                , hasSingleRefWithChildren(entity, TransportMapperCreator::isEntityRelevant, l -> l) ? "includeChildren, " : "");
        convertMethodWithMap.addTabbedLine(", (%1$s, %2$s) -> {", 2, DOMAIN_PARAMETER, DTO_PARAMETER);
        convertMethodWithMap.addLine("});");

        addConvertMethodDescription(convertMethodWithMap, entity, DOMAIN_POSTFIX, DTO_POSTFIX);

        mapperClass.addMethod(convertMethodWithMap);
        mapperClass.addImport(Map.class.getName());

        createSetValueMethod(mapperClass, entity, TransportMapperCreator::isFieldRelevant, DOMAIN_POSTFIX, DTO_POSTFIX, DOMAIN_PARAMETER, DTO_PARAMETER);
        createSetSingleReferenceMethod(mapperClass, getDtoCreateMethodParameterContainer(entity), DOMAIN_PARAMETER, DTO_PARAMETER, DtoCreator.DTO_INTERFACE, l -> l);
    }

    /**
     * Checks whether elements are to generate or not
     *
     * @param entity Entity to check for relevance
     * @return {@code true} if the entity is relevant for the mapper
     */
    private static boolean isEntityRelevant(Entity entity) {
        return entity.getModels().isDto() && (
                entity.getModels().isDomain() || (entity.getRealDerivedFrom() != null && entity.getRealDerivedFrom().getModels().isDomain())
        );
    }

    private static boolean isParentRefRelevant(Reference referenceToParent) {
        return referenceToParent.getRealTargetEntity().getActualVersion() == null
                || referenceToParent.getRealTargetEntity().getActualVersion().getParentEntity().getReferences().stream()
                .anyMatch(r -> r.getReferenceName().equals(referenceToParent.getReferenceName()));
    }

    /**
     * Checks whether elements are to generate or not
     *
     * @param field Field to check for relevance
     * @return {@code true} if the field is relevant for the mapper
     */
    private static boolean isFieldRelevant(Field field) {
        return field.getModels().isDto() && field.getModels().isDomain();
    }

    /**
     * Creates the parameter container for dto convert methods
     *
     * @param entity the entity which should be converted
     * @return the parameter container
     */
    private CreateMethodParameterContainer getDtoCreateMethodParameterContainer(Entity entity) {
        return new CreateMethodParameterContainer(entity, DTO_POSTFIX, DOMAIN_POSTFIX, TransportMapperCreator::isEntityRelevant);
    }

    /**
     * Creates the parameter container for domain convert methods
     *
     * @param entity the entity which should be converted
     * @return the parameter container
     */
    private CreateMethodParameterContainer getDomainCreateMethodParameterContainer(Entity entity) {
        return new CreateMethodParameterContainer(entity, DOMAIN_POSTFIX, DTO_POSTFIX, TransportMapperCreator::isEntityRelevant);
    }
}
