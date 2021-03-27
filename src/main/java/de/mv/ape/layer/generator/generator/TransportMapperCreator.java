package de.mv.ape.layer.generator.generator;

import de.mv.ape.layer.generator.config.elements.Config;
import de.mv.ape.layer.generator.config.elements.Entity;
import de.mv.ape.layer.generator.config.elements.Reference;
import de.mv.ape.layer.generator.sources.Clazz;
import de.mv.ape.layer.generator.sources.Method;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.util.List;
import java.util.Map;

import static de.mv.ape.layer.generator.sources.AbstractGenerateLines.TAB;

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
        Clazz mapperClass = new Clazz(mapperPackageName, getMapperName(groupingName));
        logger.debug("Create transport mapper " + mapperClass.getClassName());

        createGetInstance(mapperClass);

        entities.forEach(e -> {
            createConvertToDtoMethods(mapperClass, e, dtoPackageName, domainPackageName);
            createConvertToDomainMethods(mapperClass, e, dtoPackageName, domainPackageName);
        });

        return writeClassFile(mapperPackageDir, mapperClass.getClassName() + ".java", mapperClass);
    }

    @Override
    protected String getMapperName(String groupingName) {
        return getMapperName(MAPPER_TYPE_NAME, groupingName);
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
    protected boolean hasIncludeChildrenParameter(Entity entity) {
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
        mapperClass.addImport(getPackageAndClass(entity, dtoPackageName, DTO_POSTFIX));
        mapperClass.addImport(getPackageAndClass(entity, domainPackageName, DOMAIN_POSTFIX));

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
        createConvertMethodWithParentWithoutMap(mapperClass, entity, referenceToParent, domainPackageName, DOMAIN_POSTFIX, DTO_POSTFIX);

        Method convertMethodWithMap = createConvertMethodWithParentBase(mapperClass, entity, referenceToParent, domainPackageName, DOMAIN_POSTFIX, DTO_POSTFIX);
        convertMethodWithMap.addParameter(String.format(MAP_DECLARATION_TEXT, Map.class.getSimpleName(), DomainCreator.DOMAIN_INTERFACE), MAPPED_OBJECTS_PARAMETER_TEXT);
        convertMethodWithMap.addLine("%s result = %s(%s,%s %s);"
                , entity.getBaseName()
                , getConvertMethodName(entity, DOMAIN_POSTFIX)
                , getLowerFirst(entity.getBaseName())
                , hasIncludeChildrenParameter(entity) ? String.format(" %s,", INCLUDE_CHILDREN_PARAMETER) : ""
                , MAPPED_OBJECTS_PARAMETER_TEXT
        );
        convertMethodWithMap.addLine("if (result != null) {");
        convertMethodWithMap.addLine("%sparent.set%s(result);", TAB, getUpperFirst(referenceToParent.getReferenceName()));
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
        createConvertMethodWithoutMap(mapperClass, entity, DOMAIN_POSTFIX, DTO_POSTFIX);

        Method convertMethodWithMap = createConvertMethodBase(entity, DOMAIN_POSTFIX, DTO_POSTFIX);
        convertMethodWithMap.addParameter(String.format(MAP_DECLARATION_TEXT, Map.class.getSimpleName(), DomainCreator.DOMAIN_INTERFACE), MAPPED_OBJECTS_PARAMETER_TEXT);

        addConvertDefaultMappings(convertMethodWithMap, entity, DOMAIN_POSTFIX);

        entity.getReferences().stream()
                .filter(ref -> !ref.isList())
                .forEach(ref -> addSingleRefConvert(convertMethodWithMap, entity, ref, DOMAIN_POSTFIX));
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
     * @param daoPackageName    name of base dao package
     * @param domainPackageName name of base domain package
     */
    private void createConvertToDtoMethods(Clazz mapperClass, Entity entity, String daoPackageName, String domainPackageName) {
        mapperClass.addImport(getPackageAndClass(entity, daoPackageName, DTO_POSTFIX));
        mapperClass.addImport(getPackageAndClass(entity, domainPackageName, DOMAIN_POSTFIX));

        entity.getParentRefs().stream()
                .filter(ref -> ref.isOwner() && !ref.isList())
                .forEach(ref ->
                        createConvertToDtoMethodWithParent(mapperClass, entity, ref, daoPackageName)
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
        createConvertMethodWithParentWithoutMap(mapperClass, entity, referenceToParent, daoPackageName, DTO_POSTFIX, DOMAIN_POSTFIX);

        Method convertMethodWithMap = createConvertMethodWithParentBase(mapperClass, entity, referenceToParent, daoPackageName, DTO_POSTFIX, DOMAIN_POSTFIX);
        convertMethodWithMap.addParameter(String.format(MAP_DECLARATION_TEXT, Map.class.getSimpleName(), DtoCreator.DTO_INTERFACE), MAPPED_OBJECTS_PARAMETER_TEXT);
        convertMethodWithMap.addLine("%sDao result = %s(%s,%s %s);"
                , entity.getBaseName()
                , getConvertMethodNameDto(entity)
                , getLowerFirst(entity.getBaseName())
                , hasIncludeChildrenParameter(entity) ? String.format(" %s,", INCLUDE_CHILDREN_PARAMETER) : ""
                , MAPPED_OBJECTS_PARAMETER_TEXT
        );
        convertMethodWithMap.addLine("if (result != null) {");
        convertMethodWithMap.addLine("%sparent.set%s(result);", TAB, getUpperFirst(referenceToParent.getReferenceName()));
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
        createConvertMethodWithoutMap(mapperClass, entity, DTO_POSTFIX, DOMAIN_POSTFIX);

        Method convertMethodWithMap = createConvertMethodBase(entity, DTO_POSTFIX, DOMAIN_POSTFIX);
        convertMethodWithMap.addParameter(String.format(MAP_DECLARATION_TEXT, Map.class.getSimpleName(), DtoCreator.DTO_INTERFACE), MAPPED_OBJECTS_PARAMETER_TEXT);

        addConvertDefaultMappings(convertMethodWithMap, entity, DTO_POSTFIX);

        entity.getReferences().stream()
                .filter(ref -> !ref.isList())
                .forEach(ref -> addSingleRefConvert(convertMethodWithMap, entity, ref, DTO_POSTFIX));
        convertMethodWithMap.addEmptyLine();


        convertMethodWithMap.addEmptyLine();
        convertMethodWithMap.addLine("%s.put(identification, result);", MAPPED_OBJECTS_PARAMETER_TEXT);
        convertMethodWithMap.addLine(RETURN_RESULT_TEXT);

        mapperClass.addMethod(convertMethodWithMap);
        mapperClass.addImport(Map.class.getName());
    }
}
