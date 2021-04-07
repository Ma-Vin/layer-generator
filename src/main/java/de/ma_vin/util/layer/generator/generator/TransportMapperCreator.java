package de.ma_vin.util.layer.generator.generator;

import de.ma_vin.util.layer.generator.sources.AbstractGenerateLines;
import de.ma_vin.util.layer.generator.sources.Clazz;
import de.ma_vin.util.layer.generator.sources.Method;
import de.ma_vin.util.layer.generator.config.elements.Config;
import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.config.elements.Field;
import de.ma_vin.util.layer.generator.config.elements.Reference;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.util.List;
import java.util.Map;

public class TransportMapperCreator extends AbstractMapperCreator {

    public static final String MAPPER_TYPE_NAME = "Transport";
    public static final String DTO_POSTFIX = "Dto";
    public static final String DOMAIN_POSTFIX = "";

    public TransportMapperCreator(Config config, Log logger) {
        super(config, logger);
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
            , String domainPackageName, File mapperPackageDir) {

        if (entities.stream().noneMatch(e -> isEntityRelevant(e) && !e.isAbstract())) {
            logger.debug("No access mapper need for " + mapperPackageName);
            return true;
        }
        Clazz mapperClass = new Clazz(mapperPackageName, getMapperName(groupingName));
        logger.debug("Create transport mapper " + mapperClass.getClassName());

        createGetInstance(mapperClass);

        entities.stream().filter(e -> !e.isAbstract()).forEach(e -> {
            createConvertToDtoMethods(mapperClass, e, dtoPackageName, domainPackageName);
            createConvertToDomainMethods(mapperClass, e, dtoPackageName, domainPackageName);
        });

        return writeClassFile(mapperPackageDir, mapperClass.getClassName() + ".java", mapperClass);
    }

    @Override
    protected String getMapperName(String groupingName) {
        return getMapperName(MAPPER_TYPE_NAME, groupingName);
    }

    @Override
    protected String getParameterOfRelevantSingleModelValuesText(Entity entity) {
        return "";
    }

    /**
     * Determines the name of the converting method
     *
     * @param entity entity which is to convert
     * @return Name of the dao converting method
     */
    private String getConvertMethodNameDto(Entity entity) {
        return getConvertMethodName(entity, DTO_POSTFIX);
    }

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
        if (!isEntityRelevant(entity)) {
            logger.debug(String.format("Entity %s is not need to converted by %s", entity.getBaseName(), mapperClass.getClassName()));
            return;
        }
        mapperClass.addImport(getPackageAndClass(entity, dtoPackageName, DTO_POSTFIX));
        mapperClass.addImport(getPackageAndClass(entity, domainPackageName, DOMAIN_POSTFIX));
        mapperClass.addImport(String.format("%s.%s", domainPackageName, DomainCreator.DOMAIN_INTERFACE));

        entity.getParentRefs().stream()
                .filter(ref -> ref.isOwner() && !ref.isList())
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
        if (!isEntityRelevant(referenceToParent.getRealTargetEntity())) {
            logger.debug(String.format("No connection is to generate to parent %s from entity %s"
                    , referenceToParent.getTargetEntity(), entity.getBaseName()));
            return;
        }

        CreateMethodParameterContainer createMethodParams = getDomainCreateMethodParameterContainer(entity);

        createConvertMethodWithParentWithoutMap(mapperClass, createMethodParams, referenceToParent, domainPackageName);

        Method convertMethodWithMap = createConvertMethodWithParentBase(mapperClass, createMethodParams, referenceToParent, domainPackageName);
        convertMethodWithMap.addParameter(String.format(MAP_DECLARATION_TEXT, Map.class.getSimpleName(), DomainCreator.DOMAIN_INTERFACE)
                , MAPPED_OBJECTS_PARAMETER_TEXT);
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
        convertMethodWithMap.addParameter(String.format(MAP_DECLARATION_TEXT, Map.class.getSimpleName(), DomainCreator.DOMAIN_INTERFACE)
                , MAPPED_OBJECTS_PARAMETER_TEXT);

        addConvertDefaultMappings(convertMethodWithMap, entity, DOMAIN_POSTFIX, TransportMapperCreator::isFieldRelevant);

        determineAllReferences(entity).stream()
                .filter(ref -> !ref.isList() && isEntityRelevant(ref.getRealTargetEntity()))
                .forEach(ref -> addSingleRefConvert(convertMethodWithMap, entity, ref, DOMAIN_POSTFIX, TransportMapperCreator::isEntityRelevant));
        convertMethodWithMap.addEmptyLine();

        convertMethodWithMap.addEmptyLine();
        convertMethodWithMap.addLine("%s.put(identification, result);", MAPPED_OBJECTS_PARAMETER_TEXT);
        convertMethodWithMap.addLine(RETURN_RESULT_TEXT);

        mapperClass.addMethod(convertMethodWithMap);
        mapperClass.addImport(Map.class.getName());
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
        mapperClass.addImport(getPackageAndClass(entity, domainPackageName, DOMAIN_POSTFIX));
        mapperClass.addImport(String.format("%s.%s", dtoPackageName, DtoCreator.DTO_INTERFACE));

        entity.getParentRefs().stream()
                .filter(ref -> ref.isOwner() && !ref.isList())
                .forEach(ref ->
                        createConvertToDtoMethodWithParent(mapperClass, entity, ref, dtoPackageName)
                );
        createConvertToDtoMethod(mapperClass, entity);
    }

    /**
     * Creates mapping methods from domain to dao with a given parent
     *
     * @param mapperClass       class where to add methods at
     * @param entity            entity whose properties are to map
     * @param referenceToParent reference to parent which should be used for the parent parameter
     * @param daoPackageName    name of base dao package
     */
    private void createConvertToDtoMethodWithParent(Clazz mapperClass, Entity entity, Reference referenceToParent, String daoPackageName) {
        if (!isEntityRelevant(referenceToParent.getRealTargetEntity())) {
            logger.debug(String.format("No connection is to generate to parent %s from entity %s"
                    , referenceToParent.getTargetEntity(), entity.getBaseName()));
            return;
        }

        CreateMethodParameterContainer createMethodParams = getDtoCreateMethodParameterContainer(entity);

        createConvertMethodWithParentWithoutMap(mapperClass, createMethodParams, referenceToParent, daoPackageName);

        Method convertMethodWithMap = createConvertMethodWithParentBase(mapperClass, createMethodParams, referenceToParent, daoPackageName);
        convertMethodWithMap.addParameter(String.format(MAP_DECLARATION_TEXT, Map.class.getSimpleName(), DtoCreator.DTO_INTERFACE)
                , MAPPED_OBJECTS_PARAMETER_TEXT);
        convertMethodWithMap.addLine("%sDto result = %s(%s,%s %s);"
                , entity.getBaseName()
                , getConvertMethodNameDto(entity)
                , getLowerFirst(entity.getBaseName())
                , hasIncludeChildrenParameter(entity, TransportMapperCreator::isEntityRelevant) ? String.format(" %s,", INCLUDE_CHILDREN_PARAMETER) : ""
                , MAPPED_OBJECTS_PARAMETER_TEXT
        );
        convertMethodWithMap.addLine("if (result != null) {");
        convertMethodWithMap.addLine("%sparent.set%s(result);", AbstractGenerateLines.TAB, getUpperFirst(referenceToParent.getReferenceName()));
        convertMethodWithMap.addLine("}");
        convertMethodWithMap.addLine(RETURN_RESULT_TEXT);

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
        convertMethodWithMap.addParameter(String.format(MAP_DECLARATION_TEXT, Map.class.getSimpleName(), DtoCreator.DTO_INTERFACE), MAPPED_OBJECTS_PARAMETER_TEXT);

        addConvertDefaultMappings(convertMethodWithMap, entity, DTO_POSTFIX, TransportMapperCreator::isFieldRelevant);

        determineAllReferences(entity).stream()
                .filter(ref -> !ref.isList() && isEntityRelevant(ref.getRealTargetEntity()))
                .forEach(ref -> addSingleRefConvert(convertMethodWithMap, entity, ref, DTO_POSTFIX, TransportMapperCreator::isEntityRelevant));
        convertMethodWithMap.addEmptyLine();


        convertMethodWithMap.addEmptyLine();
        convertMethodWithMap.addLine("%s.put(identification, result);", MAPPED_OBJECTS_PARAMETER_TEXT);
        convertMethodWithMap.addLine(RETURN_RESULT_TEXT);

        mapperClass.addMethod(convertMethodWithMap);
        mapperClass.addImport(Map.class.getName());
    }

    /**
     * Checks whether elements are to generate or not
     *
     * @param entity Entity to check for relevance
     * @return {@code true} if the entity is relevant for the mapper
     */
    private static boolean isEntityRelevant(Entity entity) {
        return entity.getModels() == null || (entity.getModels().isDto() && entity.getModels().isDomain());
    }

    /**
     * Checks whether elements are to generate or not
     *
     * @param field Field to check for relevance
     * @return {@code true} if the field is relevant for the mapper
     */
    private static boolean isFieldRelevant(Field field) {
        return field.getModels() == null || (field.getModels().isDto() && field.getModels().isDomain());
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
