package de.ma_vin.util.layer.generator.generator;

import de.ma_vin.util.layer.generator.sources.AbstractGenerateLines;
import de.ma_vin.util.layer.generator.sources.Clazz;
import de.ma_vin.util.layer.generator.config.elements.Config;
import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.config.elements.Field;
import de.ma_vin.util.layer.generator.config.elements.Reference;
import de.ma_vin.util.layer.generator.sources.Method;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccessMapperCreator extends AbstractMapperCreator {

    public static final String MAPPER_TYPE_NAME = "Access";
    public static final String DAO_POSTFIX = "Dao";
    public static final String DOMAIN_POSTFIX = "";
    public static final String PACKAGE_AND_CLASS_NAME_FORMAT = "%s.%s";

    public AccessMapperCreator(Config config, Log logger) {
        super(config, logger);
    }

    /**
     * Creates the access mapper which converts dao to domain and the other way around.
     *
     * @param entities          List of Entities to generate
     * @param groupingName      grouping which is used for class name
     * @param mapperPackageName package name which is to use for mapper
     * @param daoPackageName    name of the package to use for dao
     * @param domainPackageName name of the package to use for domain
     * @param mapperPackageDir  directory where to write sources of created mapper
     * @return {@code true} if creating was successful. Otherwise {@code false}
     */
    public boolean createAccessMapper(List<Entity> entities, String groupingName, String mapperPackageName, String daoPackageName
            , String domainPackageName, File mapperPackageDir) {

        if (entities.stream().noneMatch(e -> isEntityRelevant(e) && !e.isAbstract())) {
            logger.debug("No access mapper need for " + mapperPackageName);
            return true;
        }
        Clazz mapperClass = new Clazz(mapperPackageName, getMapperName(groupingName));
        logger.debug("Create access mapper " + mapperClass.getClassName());

        createGetInstance(mapperClass);

        entities.stream().filter(e -> !e.isAbstract()).forEach(e -> {
            createConvertToDaoMethods(mapperClass, e, daoPackageName, domainPackageName);
            createConvertToDomainMethods(mapperClass, e, daoPackageName, domainPackageName);
        });

        return writeClassFile(mapperPackageDir, mapperClass.getClassName() + ".java", mapperClass);
    }

    /**
     * Creates mapping methods from domain to dao
     *
     * @param mapperClass       class where to add methods at
     * @param entity            entity whose properties are to map
     * @param daoPackageName    name of base dao package
     * @param domainPackageName name of base domain package
     */
    private void createConvertToDaoMethods(Clazz mapperClass, Entity entity, String daoPackageName, String domainPackageName) {
        if (!isEntityRelevant(entity)) {
            logger.debug(String.format("Entity %s is not need to converted by %s", entity.getBaseName(), mapperClass.getClassName()));
            return;
        }
        mapperClass.addImport(getPackageAndClass(entity, daoPackageName, DAO_POSTFIX));
        mapperClass.addImport(getPackageAndClass(entity, domainPackageName, DOMAIN_POSTFIX));
        mapperClass.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, daoPackageName, DaoCreator.DAO_INTERFACE));

        entity.getParentRefs().stream()
                .forEach(ref ->
                        createConvertToDaoMethodWithParent(mapperClass, entity, ref, daoPackageName)
                );
        createConvertToDaoMethod(mapperClass, entity, daoPackageName);
    }

    /**
     * Creates mapping methods from domain to dao with a given parent
     *
     * @param mapperClass       class where to add methods at
     * @param entity            entity whose properties are to map
     * @param referenceToParent reference to parent which should be used for the parent parameter
     * @param daoPackageName    name of base dao package
     */
    private void createConvertToDaoMethodWithParent(Clazz mapperClass, Entity entity, Reference referenceToParent, String daoPackageName) {
        if (!isEntityRelevant(referenceToParent.getRealTargetEntity())) {
            logger.debug(String.format("No connection is to generate to parent %s from entity %s"
                    , referenceToParent.getTargetEntity(), entity.getBaseName()));
            return;
        }
        createConvertMethodWithParentWithoutMap(mapperClass, entity, referenceToParent, daoPackageName, DAO_POSTFIX
                , DOMAIN_POSTFIX, AccessMapperCreator::isEntityRelevant);

        Method convertMethodWithMap = createConvertMethodWithParentBase(mapperClass, entity, referenceToParent
                , daoPackageName, DAO_POSTFIX, DOMAIN_POSTFIX, AccessMapperCreator::isEntityRelevant);
        convertMethodWithMap.addParameter(String.format(MAP_DECLARATION_TEXT, Map.class.getSimpleName(), DaoCreator.DAO_INTERFACE)
                , MAPPED_OBJECTS_PARAMETER_TEXT);
        convertMethodWithMap.addLine("%sDao result = %s(%s,%s %s);"
                , entity.getBaseName()
                , getConvertMethodNameDao(entity)
                , getLowerFirst(entity.getBaseName())
                , hasIncludeChildrenParameter(entity, AccessMapperCreator::isEntityRelevant) ? String.format(" %s,", INCLUDE_CHILDREN_PARAMETER) : ""
                , MAPPED_OBJECTS_PARAMETER_TEXT
        );
        convertMethodWithMap.addLine("if (result != null) {");
        addSettingOfDaoParent(convertMethodWithMap, entity, referenceToParent);
        convertMethodWithMap.addLine("}");
        convertMethodWithMap.addLine(RETURN_RESULT_TEXT);

        mapperClass.addMethod(convertMethodWithMap);
        mapperClass.addImport(Map.class.getName());
    }

    /**
     * Adds lines for setting the parent direct or with some connection table at dao converting
     *
     * @param convertMethod     Method where to add lines
     * @param entity            Entity which is converted
     * @param referenceToParent Reference to parent
     */
    private void addSettingOfDaoParent(Method convertMethod, Entity entity, Reference referenceToParent) {
        if (referenceToParent.isOwner()) {
            convertMethod.addLine("%sresult.setParent%s(parent);", AbstractGenerateLines.TAB, referenceToParent.getTargetEntity());
        }
        if (referenceToParent.isList()) {
            if (referenceToParent.isOwner()) {
                convertMethod.addLine("%sparent.get%ss().add(result);", AbstractGenerateLines.TAB, getUpperFirst(referenceToParent.getReferenceName()));
            } else {
                convertMethod.addLine("%s%2$s connectionTable = new %2$s();", AbstractGenerateLines.TAB, DaoCreator.getConnectionTableNameParentRef(referenceToParent));
                convertMethod.addLine("%sconnectionTable.set%s(result);", AbstractGenerateLines.TAB, entity.getBaseName());
                convertMethod.addLine("%sconnectionTable.set%s(parent);", AbstractGenerateLines.TAB, referenceToParent.getTargetEntity());
                convertMethod.addLine("%sparent.get%ss().add(connectionTable);", AbstractGenerateLines.TAB, getUpperFirst(referenceToParent.getReferenceName()));
            }
        } else {
            convertMethod.addLine("%sparent.set%s(result);", AbstractGenerateLines.TAB, getUpperFirst(referenceToParent.getReferenceName()));
        }
    }

    /**
     * Determines the name of the converting method
     *
     * @param entity entity which is to convert
     * @return Name of the dao converting method
     */
    private String getConvertMethodNameDao(Entity entity) {
        return getConvertMethodName(entity, DAO_POSTFIX);
    }

    /**
     * Creates mapping methods from domain to dao without a given parent as parameter
     *
     * @param mapperClass    class where to add methods at
     * @param entity         entity whose properties are to map
     * @param daoPackageName name of base dao package
     */
    private void createConvertToDaoMethod(Clazz mapperClass, Entity entity, String daoPackageName) {
        createConvertMethodWithoutMap(mapperClass, entity, DAO_POSTFIX, DOMAIN_POSTFIX, AccessMapperCreator::isEntityRelevant);

        Method convertMethodWithMap = createConvertMethodBase(entity, DAO_POSTFIX, DOMAIN_POSTFIX, AccessMapperCreator::isEntityRelevant);
        convertMethodWithMap.addParameter(String.format(MAP_DECLARATION_TEXT, Map.class.getSimpleName(), DaoCreator.DAO_INTERFACE)
                , MAPPED_OBJECTS_PARAMETER_TEXT);

        createConvertToDaoMappings(mapperClass, convertMethodWithMap, entity, daoPackageName);

        mapperClass.addMethod(convertMethodWithMap);
        mapperClass.addImport(Map.class.getName());
    }

    /**
     * Creates the method content which contains the concrete mapping
     *
     * @param mapperClass    class where to add methods at
     * @param convertMethod  the method where to add body
     * @param entity         entity whose properties are to map
     * @param daoPackageName name of base dao package
     */
    private void createConvertToDaoMappings(Clazz mapperClass, Method convertMethod, Entity entity, String daoPackageName) {
        addConvertDefaultMappings(convertMethod, entity, DAO_POSTFIX, AccessMapperCreator::isFieldRelevant);

        List<Reference> allReferences = determineAllReferences(entity);

        allReferences.stream()
                .filter(ref -> !ref.isList())
                .filter(ref -> isEntityRelevant(ref.getRealTargetEntity()))
                .forEach(ref -> addSingleRefConvert(convertMethod, entity, ref, DAO_POSTFIX, AccessMapperCreator::isEntityRelevant));
        convertMethod.addEmptyLine();

        boolean hasIncludeChildrenParameter = hasIncludeChildrenParameter(entity, AccessMapperCreator::isEntityRelevant);

        if (hasIncludeChildrenParameter && allReferences.stream()
                .anyMatch(ref -> ref.isList() && isEntityRelevant(ref.getRealTargetEntity()))) {

            allReferences.stream()
                    .filter(Reference::isList)
                    .filter(ref -> isEntityRelevant(ref.getRealTargetEntity()))
                    .forEach(ref -> {
                        String getterSubName = getUpperFirst(ref.getReferenceName()) + "s";
                        convertMethod.addLine("result.set%s(new %s<>());", getterSubName, ArrayList.class.getSimpleName());
                    });

            convertMethod.addLine("if (includeChildren) {");

            allReferences.stream()
                    .filter(Reference::isList)
                    .filter(ref -> isEntityRelevant(ref.getRealTargetEntity()))
                    .forEach(ref -> addMultiRefConvertToDao(mapperClass, convertMethod, entity, ref, daoPackageName));

            convertMethod.addLine("}");
        }

        convertMethod.addEmptyLine();
        convertMethod.addLine("%s.put(identification, result);", MAPPED_OBJECTS_PARAMETER_TEXT);
        convertMethod.addLine(RETURN_RESULT_TEXT);
    }

    /**
     * Adds mappings for multi reference to other dao
     *
     * @param mapperClass    class where to add imports
     * @param convertMethod  the method where to add body
     * @param entity         entity whose properties are to map
     * @param reference      reference which is actual to map
     * @param daoPackageName name of base dao package
     */
    private void addMultiRefConvertToDao(Clazz mapperClass, Method convertMethod, Entity entity, Reference reference, String daoPackageName) {
        boolean hasIncludeChildrenParameter = hasIncludeChildrenParameter(reference.getRealTargetEntity(), AccessMapperCreator::isEntityRelevant);

        String mapperName = getMapperName(reference.getRealTargetEntity());
        String mapperMethodName = getConvertMethodNameDao(reference.getRealTargetEntity());
        String variableName = getLowerFirst(entity.getBaseName());
        String getterSubName = getUpperFirst(reference.getReferenceName()) + "s";
        String connectionTableName = DaoCreator.getConnectionTableName(reference);
        String sourceConnectionName = getUpperFirst(reference.getParent().getBaseName());
        String targetConnectionName = getUpperFirst(reference.getRealTargetEntity().getBaseName());

        mapperClass.addImport(ArrayList.class.getName());

        convertMethod.addLine("%s.get%s().forEach(arg ->%s", 1, variableName, getterSubName, reference.isOwner() ? "" : " {");

        int refNumTabs = reference.isOwner() ? 3 : 2;

        if (reference.isOwner() && hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.%s(arg, true, result, %s)"
                    , refNumTabs, mapperName, mapperMethodName, MAPPED_OBJECTS_PARAMETER_TEXT);
        } else if (!reference.isOwner() && hasIncludeChildrenParameter) {
            convertMethod.addLine("%1$s connectionTable = new %1$s();", refNumTabs, connectionTableName);
            convertMethod.addLine("connectionTable.set%s(result);", refNumTabs, sourceConnectionName);
            convertMethod.addLine("connectionTable.set%s(%s.%s(arg, true, %s));"
                    , refNumTabs, targetConnectionName, mapperName, mapperMethodName, MAPPED_OBJECTS_PARAMETER_TEXT);
            convertMethod.addLine("result.get%s().add(connectionTable);", refNumTabs, getterSubName);
            mapperClass.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, getPackage(entity, daoPackageName), connectionTableName));
        } else if (reference.isOwner() && !hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.%s(arg, result, %s)"
                    , refNumTabs, mapperName, mapperMethodName, MAPPED_OBJECTS_PARAMETER_TEXT);
        } else {
            convertMethod.addLine("%1$s connectionTable = new %1$s();", refNumTabs, connectionTableName);
            convertMethod.addLine("connectionTable.set%s(result);", refNumTabs, sourceConnectionName);
            convertMethod.addLine("connectionTable.set%s(%s.%s(arg, %s));"
                    , refNumTabs, targetConnectionName, mapperName, mapperMethodName, MAPPED_OBJECTS_PARAMETER_TEXT);
            convertMethod.addLine("result.get%s().add(connectionTable);", refNumTabs, getterSubName);
            mapperClass.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, getPackage(entity, daoPackageName), connectionTableName));
        }

        convertMethod.addLine("%s);", 1, reference.isOwner() ? "" : "}");
    }

    @Override
    protected String getMapperName(String groupingName) {
        return getMapperName(MAPPER_TYPE_NAME, groupingName);
    }

    /**
     * Creates mapping methods from dao to domain
     *
     * @param mapperClass       class where to add methods at
     * @param entity            entity whose properties are to map
     * @param daoPackageName    name of base dao package
     * @param domainPackageName name of base domain package
     */
    private void createConvertToDomainMethods(Clazz mapperClass, Entity entity, String daoPackageName, String domainPackageName) {
        if (!isEntityRelevant(entity)) {
            logger.debug(String.format("Entity %s is not need to converted by %s", entity.getBaseName(), mapperClass.getClassName()));
            return;
        }
        mapperClass.addImport(getPackageAndClass(entity, daoPackageName, DAO_POSTFIX));
        mapperClass.addImport(getPackageAndClass(entity, domainPackageName, DOMAIN_POSTFIX));
        mapperClass.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, domainPackageName, DomainCreator.DOMAIN_INTERFACE));

        entity.getParentRefs().stream()
                .forEach(ref ->
                        createConvertToDomainMethodWithParent(mapperClass, entity, ref, domainPackageName)
                );
        createConvertToDomainMethod(mapperClass, entity);
    }

    /**
     * Creates mapping methods from dao to domain with a given parent
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
        createConvertMethodWithParentWithoutMap(mapperClass, entity, referenceToParent, domainPackageName, DOMAIN_POSTFIX
                , DAO_POSTFIX, AccessMapperCreator::isEntityRelevant);

        Method convertMethodWithMap = createConvertMethodWithParentBase(mapperClass, entity, referenceToParent, domainPackageName
                , DOMAIN_POSTFIX, DAO_POSTFIX, AccessMapperCreator::isEntityRelevant);
        convertMethodWithMap.addParameter(String.format(MAP_DECLARATION_TEXT, Map.class.getSimpleName(), DomainCreator.DOMAIN_INTERFACE), MAPPED_OBJECTS_PARAMETER_TEXT);
        convertMethodWithMap.addLine("%s result = %s(%s,%s %s);"
                , entity.getBaseName()
                , getConvertMethodName(entity, DOMAIN_POSTFIX)
                , getLowerFirst(entity.getBaseName())
                , hasIncludeChildrenParameter(entity, AccessMapperCreator::isEntityRelevant) ? String.format(" %s,", INCLUDE_CHILDREN_PARAMETER) : ""
                , MAPPED_OBJECTS_PARAMETER_TEXT
        );
        convertMethodWithMap.addLine("if (result != null) {");
        if (referenceToParent.isList()) {
            convertMethodWithMap.addLine("%sparent.get%ss().add(result);", AbstractGenerateLines.TAB, getUpperFirst(referenceToParent.getReferenceName()));
        } else {
            convertMethodWithMap.addLine("%sparent.set%s(result);", AbstractGenerateLines.TAB, getUpperFirst(referenceToParent.getReferenceName()));
        }
        convertMethodWithMap.addLine("}");
        convertMethodWithMap.addLine(RETURN_RESULT_TEXT);

        mapperClass.addMethod(convertMethodWithMap);
        mapperClass.addImport(Map.class.getName());
    }

    /**
     * Creates mapping methods from dao to domain without a given parent as parameter
     *
     * @param mapperClass class where to add methods at
     * @param entity      entity whose properties are to map
     */
    private void createConvertToDomainMethod(Clazz mapperClass, Entity entity) {
        createConvertMethodWithoutMap(mapperClass, entity, DOMAIN_POSTFIX, DAO_POSTFIX, AccessMapperCreator::isEntityRelevant);

        Method convertMethodWithMap = createConvertMethodBase(entity, DOMAIN_POSTFIX, DAO_POSTFIX, AccessMapperCreator::isEntityRelevant);
        convertMethodWithMap.addParameter(String.format(MAP_DECLARATION_TEXT, Map.class.getSimpleName(), DomainCreator.DOMAIN_INTERFACE)
                , MAPPED_OBJECTS_PARAMETER_TEXT);

        createConvertToDomainMappings(mapperClass, convertMethodWithMap, entity);

        mapperClass.addMethod(convertMethodWithMap);
        mapperClass.addImport(Map.class.getName());
    }

    /**
     * Creates the method content which contains the concrete mapping
     *
     * @param mapperClass   class where to add methods at
     * @param convertMethod the method where to add body
     * @param entity        entity whose properties are to map
     */
    private void createConvertToDomainMappings(Clazz mapperClass, Method convertMethod, Entity entity) {
        addConvertDefaultMappings(convertMethod, entity, DOMAIN_POSTFIX, AccessMapperCreator::isFieldRelevant);

        List<Reference> allReferences = determineAllReferences(entity);

        allReferences.stream()
                .filter(ref -> !ref.isList())
                .filter(ref -> isEntityRelevant(ref.getRealTargetEntity()))
                .forEach(ref -> addSingleRefConvert(convertMethod, entity, ref, DOMAIN_POSTFIX, AccessMapperCreator::isEntityRelevant));
        convertMethod.addEmptyLine();

        boolean hasIncludeChildrenParameter = hasIncludeChildrenParameter(entity, AccessMapperCreator::isEntityRelevant);

        if (hasIncludeChildrenParameter && allReferences.stream()
                .anyMatch(ref -> ref.isList() && isEntityRelevant(ref.getRealTargetEntity()))) {

            convertMethod.addLine("if (includeChildren) {");

            allReferences.stream()
                    .filter(Reference::isList)
                    .filter(ref -> isEntityRelevant(ref.getRealTargetEntity()))
                    .forEach(ref -> addMultiRefConvertToDomain(mapperClass, convertMethod, entity, ref));

            convertMethod.addLine("}");
        }

        convertMethod.addEmptyLine();
        convertMethod.addLine("%s.put(identification, result);", MAPPED_OBJECTS_PARAMETER_TEXT);
        convertMethod.addLine(RETURN_RESULT_TEXT);
    }

    /**
     * Adds mappings for multi reference to other domain
     *
     * @param mapperClass   class where to add imports
     * @param convertMethod the method where to add body
     * @param entity        entity whose properties are to map
     * @param reference     reference which is actual to map
     */
    private void addMultiRefConvertToDomain(Clazz mapperClass, Method convertMethod, Entity entity, Reference reference) {
        boolean hasIncludeChildrenParameter = hasIncludeChildrenParameter(reference.getRealTargetEntity(), AccessMapperCreator::isEntityRelevant);

        String mapperName = getMapperName(reference.getRealTargetEntity());
        String mapperMethodName = getConvertMethodName(reference.getRealTargetEntity(), DOMAIN_POSTFIX);
        String variableName = getLowerFirst(entity.getBaseName());
        String getterSubName = getUpperFirst(reference.getReferenceName()) + "s";
        String targetConnectionName = getUpperFirst(reference.getRealTargetEntity().getBaseName());

        mapperClass.addImport(ArrayList.class.getName());
        convertMethod.addLine("%s.get%s().forEach(arg ->", 1, variableName, getterSubName);

        if (reference.isOwner() && hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.%s(arg, true, result, %s)"
                    , 3, mapperName, mapperMethodName, MAPPED_OBJECTS_PARAMETER_TEXT);
        } else if (!reference.isOwner() && hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.%s(arg.get%s(), true, result, %s)"
                    , 3, mapperName, mapperMethodName, targetConnectionName, MAPPED_OBJECTS_PARAMETER_TEXT);
        } else if (reference.isOwner() && !hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.%s(arg, result, %s)"
                    , 3, mapperName, mapperMethodName, MAPPED_OBJECTS_PARAMETER_TEXT);
        } else {
            convertMethod.addLine("%s.%s(arg.get%s(), result, %s)"
                    , 3, mapperName, mapperMethodName, targetConnectionName, MAPPED_OBJECTS_PARAMETER_TEXT);
        }

        convertMethod.addLine(");", 1);
    }

    /**
     * Checks whether elements are to generate or not
     *
     * @param entity Entity to check for relevance
     * @return {@code true} if the entity is relevant for the mapper
     */
    private static boolean isEntityRelevant(Entity entity) {
        return entity.getModels() == null || (entity.getModels().isDao() && entity.getModels().isDomain());
    }

    /**
     * Checks whether elements are to generate or not
     *
     * @param field Field to check for relevance
     * @return {@code true} if the field is relevant for the mapper
     */
    private static boolean isFieldRelevant(Field field) {
        return field.getModels() == null || (field.getModels().isDao() && field.getModels().isDomain());
    }
}