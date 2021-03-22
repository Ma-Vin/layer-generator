package de.mv.ape.layer.generator.generator;

import static de.mv.ape.layer.generator.sources.AbstractGenerateLines.TAB;

import de.mv.ape.layer.generator.config.elements.Config;
import de.mv.ape.layer.generator.config.elements.Entity;
import de.mv.ape.layer.generator.config.elements.Reference;
import de.mv.ape.layer.generator.sources.*;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessMapperCreator extends AbstractMapperCreator {

    public static final String CONVERT_TO_TEXT = "convertTo%s%s";
    public static final String MAP_DECLARATION_TEXT = "%s<String, %s>";
    public static final String MAPPED_OBJECTS_PARAMETER_TEXT = "mappedObjects";
    public static final String RETURN_RESULT_TEXT = "return result;";
    public static final String INCLUDE_CHILDREN_PARAMETER = "includeChildren";
    public static final String DAO_POSTFIX = "Dao";
    public static final String DOMAIN_POSTFIX = "";

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
        Clazz mapperClass = new Clazz(mapperPackageName, getMapperName(groupingName));
        logger.debug("Create access mapper " + mapperClass.getClassName());

        createGetInstance(mapperClass);

        entities.forEach(e -> {
            createConvertToDaoMethods(mapperClass, e, daoPackageName, domainPackageName);
            createConvertToDomainMethods(mapperClass, e, daoPackageName, domainPackageName);
        });

        return writeClassFile(mapperPackageDir, mapperClass.getClassName() + ".java", mapperClass);
    }

    /**
     * Creates the singleton and get instance method
     *
     * @param mapperClass
     */
    private void createGetInstance(Clazz mapperClass) {
        Attribute instanceAttribute = new Attribute("instance", mapperClass.getClassName());
        instanceAttribute.setStatic(true);
        instanceAttribute.setJavaDoc(new JavaDoc("singleton"));
        mapperClass.addAttribute(instanceAttribute);

        Constructor constructor = new Constructor(mapperClass.getClassName());
        constructor.setQualifier(Qualifier.PRIVATE);
        mapperClass.addConstructor(constructor);

        Method getInstanceMethod = new Method("getInstance");
        getInstanceMethod.setMethodType(mapperClass.getClassName());
        getInstanceMethod.setStatic(true);
        getInstanceMethod.setQualifier(Qualifier.PUBLIC);
        getInstanceMethod.setJavaDoc(new JavaDoc("@return the singleton"));
        getInstanceMethod.addLine("if (instance == null) {");
        getInstanceMethod.addLine("instance = new %s();", 1, mapperClass.getClassName());
        getInstanceMethod.addLine("}");
        getInstanceMethod.addLine("return instance;");
        mapperClass.addMethod(getInstanceMethod);
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
        mapperClass.addImport(getPackageAndClass(entity, daoPackageName, "Dao"));
        mapperClass.addImport(getPackageAndClass(entity, domainPackageName, ""));

        entity.getParentRefs().stream()
                .filter(Reference::isOwner)
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
        createConvertMethodWithParentWithoutMap(mapperClass, entity, referenceToParent, daoPackageName, DAO_POSTFIX, DOMAIN_POSTFIX);

        Method convertMethodWithMap = createConvertMethodWithParentBase(mapperClass, entity, referenceToParent, daoPackageName, DAO_POSTFIX, DOMAIN_POSTFIX);
        convertMethodWithMap.addParameter(String.format(MAP_DECLARATION_TEXT, Map.class.getSimpleName(), DaoCreator.DAO_INTERFACE), MAPPED_OBJECTS_PARAMETER_TEXT);
        convertMethodWithMap.addLine("%sDao result = %s(%s,%s %s)"
                , entity.getBaseName()
                , getConvertMethodNameDao(entity)
                , getLowerFirst(entity.getBaseName())
                , hasIncludeChildrenParameter(entity) ? String.format(" %s,", INCLUDE_CHILDREN_PARAMETER) : ""
                , MAPPED_OBJECTS_PARAMETER_TEXT
        );
        convertMethodWithMap.addLine("if (result != null) {");
        convertMethodWithMap.addLine("%sresult.setParent%s(parent);", TAB, referenceToParent.getTargetEntity());
        if (referenceToParent.isList()) {
            convertMethodWithMap.addLine("%sparent.get%ss().add(result);", TAB, getUpperFirst(referenceToParent.getReferenceName()));
        } else {
            convertMethodWithMap.addLine("%sparent.set%s(result);", TAB, getUpperFirst(referenceToParent.getReferenceName()));
        }
        convertMethodWithMap.addLine("}");
        convertMethodWithMap.addLine(RETURN_RESULT_TEXT);

        mapperClass.addMethod(convertMethodWithMap);
        mapperClass.addImport(Map.class.getName());
    }

    /**
     * Creates mapping methods with a given parent but without a map parameter
     *
     * @param mapperClass                 class where to add methods at
     * @param entity                      entity whose properties are to map
     * @param referenceToParent           reference to parent which should be used for the parent parameter
     * @param packageName                 name of base package
     * @param classParameterPostFix       postfix for classes and parameters
     * @param sourceClassParameterPostFix postfix for classes and parameters which is to map
     */
    private void createConvertMethodWithParentWithoutMap(Clazz mapperClass, Entity entity, Reference referenceToParent, String packageName
            , String classParameterPostFix, String sourceClassParameterPostFix) {
        Method convertMethod = createConvertMethodWithParentBase(mapperClass, entity, referenceToParent, packageName
                , classParameterPostFix, sourceClassParameterPostFix);

        convertMethod.addLine("return %s(%s,%s parent, new %s<>());"
                , getConvertMethodName(entity, classParameterPostFix)
                , getLowerFirst(entity.getBaseName())
                , hasIncludeChildrenParameter(entity) ? String.format(" %s,", INCLUDE_CHILDREN_PARAMETER) : ""
                , HashMap.class.getSimpleName()
        );
        mapperClass.addMethod(convertMethod);
        mapperClass.addImport(HashMap.class.getName());
    }

    /**
     * Creates a basic convert method with parent parameter
     *
     * @param mapperClass                 class where to add the method
     * @param entity                      entity which is to map
     * @param referenceToParent           reference to parent which should be used for the parent parameter
     * @param packageName                 name of base  package
     * @param classParameterPostFix       postfix for classes and parameters
     * @param sourceClassParameterPostFix postfix for classes and parameters which is to map
     * @return the created Method
     */
    private Method createConvertMethodWithParentBase(Clazz mapperClass, Entity entity, Reference referenceToParent
            , String packageName, String classParameterPostFix, String sourceClassParameterPostFix) {

        Method convertMethod = createConvertMethodBase(entity, classParameterPostFix, sourceClassParameterPostFix);
        mapperClass.addImport(getPackageAndClass(referenceToParent, packageName, classParameterPostFix));
        convertMethod.addParameter(referenceToParent.getTargetEntity() + classParameterPostFix, "parent");

        return convertMethod;
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
     * Determines the name of the converting method
     *
     * @param entity entity which is to convert
     * @return Name of the dao converting method
     */
    private String getConvertMethodName(Entity entity, String classParameterPostFix) {
        return String.format(CONVERT_TO_TEXT, entity.getBaseName(), classParameterPostFix);
    }

    /**
     * Creates mapping methods from domain to dao without a given parent as parameter
     *
     * @param mapperClass    class where to add methods at
     * @param entity         entity whose properties are to map
     * @param daoPackageName name of base dao package
     */
    private void createConvertToDaoMethod(Clazz mapperClass, Entity entity, String daoPackageName) {
        createConvertMethodWithoutMap(mapperClass, entity, DAO_POSTFIX, DOMAIN_POSTFIX);

        Method convertMethodWithMap = createConvertMethodBase(entity, DAO_POSTFIX, DOMAIN_POSTFIX);
        convertMethodWithMap.addParameter(String.format(MAP_DECLARATION_TEXT, Map.class.getSimpleName(), DaoCreator.DAO_INTERFACE), MAPPED_OBJECTS_PARAMETER_TEXT);

        createConvertToDaoMappings(mapperClass, convertMethodWithMap, entity, daoPackageName);

        mapperClass.addMethod(convertMethodWithMap);
        mapperClass.addImport(Map.class.getName());
    }

    /**
     * Creates mapping methods without a map parameter
     *
     * @param mapperClass                 class where to add methods at
     * @param entity                      entity whose properties are to map
     * @param classParameterPostFix       postfix for classes and parameters
     * @param sourceClassParameterPostFix postfix for classes and parameters which is to map
     */
    private void createConvertMethodWithoutMap(Clazz mapperClass, Entity entity, String classParameterPostFix, String sourceClassParameterPostFix) {
        Method convertMethod = createConvertMethodBase(entity, classParameterPostFix, sourceClassParameterPostFix);
        convertMethod.addLine("return %s(%s,%s new %s<>());"
                , getConvertMethodName(entity, classParameterPostFix)
                , getLowerFirst(entity.getBaseName())
                , hasIncludeChildrenParameter(entity) ? String.format(" %s,", INCLUDE_CHILDREN_PARAMETER) : ""
                , HashMap.class.getSimpleName());
        mapperClass.addMethod(convertMethod);
        mapperClass.addImport(HashMap.class.getName());
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
        addConvertDefaultMappings(convertMethod, entity, DAO_POSTFIX);

        entity.getReferences().stream()
                .filter(ref -> !ref.isList())
                .forEach(ref -> addSingleRefConvert(convertMethod, entity, ref, DAO_POSTFIX));
        convertMethod.addEmptyLine();

        entity.getReferences().stream()
                .filter(Reference::isList)
                .forEach(ref -> addMultiRefConvertToDao(mapperClass, convertMethod, entity, ref, daoPackageName));
        convertMethod.addEmptyLine();
        convertMethod.addLine("%s.put(identification, result);", MAPPED_OBJECTS_PARAMETER_TEXT);
        convertMethod.addLine(RETURN_RESULT_TEXT);
    }

    /**
     * Adds the default mappings to a convert method
     *
     * @param convertMethod         method where to add lines for default mapping instrcutions
     * @param entity                entity whose properties are to map
     * @param classParameterPostFix postfix for classes and parameters
     */
    private void addConvertDefaultMappings(Method convertMethod, Entity entity, String classParameterPostFix) {
        convertMethod.addLine("if (%s == null) {", getLowerFirst(entity.getBaseName()));
        convertMethod.addLine("return null;", 1);
        convertMethod.addLine("}");
        convertMethod.addEmptyLine();
        convertMethod.addLine("String identification = %s;", getterIdentificationForMap(entity, classParameterPostFix));
        convertMethod.addLine("if (!%1$s.isEmpty() && %1$s.containsKey(identification)) {", MAPPED_OBJECTS_PARAMETER_TEXT);
        convertMethod.addLine("return (%s%s) %s.get(identification);", 1, getUpperFirst(entity.getBaseName())
                , classParameterPostFix, MAPPED_OBJECTS_PARAMETER_TEXT);
        convertMethod.addLine("}");
        convertMethod.addEmptyLine();
        convertMethod.addLine("%1$s%2$s result = new %1$s%2$s();", getUpperFirst(entity.getBaseName()), classParameterPostFix);
        convertMethod.addEmptyLine();

        entity.getFields().stream()
                .filter(f -> f.getModels() == null || (f.getModels().isDomain() && f.getModels().isDao()))
                .forEach(f ->
                        convertMethod.addLine("result.set%1$s(%2$s.get%1$s());"
                                , getUpperFirst(f.getFieldName())
                                , getLowerFirst(entity.getBaseName()))
                );
        convertMethod.addEmptyLine();
    }

    /**
     * Adds mappings for single reference to other dao
     *
     * @param convertMethod         the method where to add body
     * @param entity                entity whose properties are to map
     * @param reference             reference which is actual to map
     * @param classParameterPostFix postfix for classes and parameters
     */
    private void addSingleRefConvert(Method convertMethod, Entity entity, Reference reference, String classParameterPostFix) {
        boolean hasIncludeChildrenParameter = hasIncludeChildrenParameter(reference.getRealTargetEntity());

        String mapperName = getMapperName(reference.getRealTargetEntity());
        String mapperMethodName = getConvertMethodName(reference.getRealTargetEntity(), classParameterPostFix);
        String variableName = getLowerFirst(entity.getBaseName());
        String getterSubName = getUpperFirst(reference.getReferenceName());

        if (reference.isOwner() && hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.%s(%s.get%s(), includeChildren, result, %s);"
                    , mapperName, mapperMethodName, variableName, getterSubName, MAPPED_OBJECTS_PARAMETER_TEXT);
        } else if (!reference.isOwner() && hasIncludeChildrenParameter) {
            convertMethod.addLine("result.set%s(%s.%s(%s.get%s(), includeChildren, %s));"
                    , getterSubName, mapperName, mapperMethodName, variableName, getterSubName, MAPPED_OBJECTS_PARAMETER_TEXT);
        } else if (reference.isOwner() && !hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.%s(%s.get%s(), result, %s);"
                    , mapperName, mapperMethodName, variableName, getterSubName, MAPPED_OBJECTS_PARAMETER_TEXT);
        } else {
            convertMethod.addLine("result.set%s(%s.%s(%s.get%s(), %s));"
                    , getterSubName, mapperName, mapperMethodName, variableName, getterSubName, MAPPED_OBJECTS_PARAMETER_TEXT);
        }
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
        boolean hasIncludeChildrenParameter = hasIncludeChildrenParameter(reference.getRealTargetEntity());

        String mapperName = getMapperName(reference.getRealTargetEntity());
        String mapperMethodName = getConvertMethodNameDao(reference.getRealTargetEntity());
        String variableName = getLowerFirst(entity.getBaseName());
        String getterSubName = getUpperFirst(reference.getReferenceName()) + "s";
        String connectionTableName = DaoCreator.getConnectionTableName(reference);
        String sourceConnectionName = getUpperFirst(reference.getParent().getBaseName());
        String targetConnectionName = getUpperFirst(entity.getBaseName());

        mapperClass.addImport(ArrayList.class.getName());
        convertMethod.addLine("result.set%s(new %s<>());", getterSubName, ArrayList.class.getSimpleName());
        convertMethod.addLine("%s.get%s().forEach(arg ->%s", variableName, getterSubName, reference.isOwner() ? "" : " {");

        int numTabs = reference.isOwner() ? 2 : 1;

        if (reference.isOwner() && hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.%s(arg, includeChildren, result, %s)"
                    , numTabs, mapperName, mapperMethodName, MAPPED_OBJECTS_PARAMETER_TEXT);
        } else if (!reference.isOwner() && hasIncludeChildrenParameter) {
            convertMethod.addLine("%1$s connectionTable = new %1$s();", numTabs, connectionTableName);
            convertMethod.addLine("connectionTable.set%s(result);", numTabs, sourceConnectionName);
            convertMethod.addLine("connectionTable.set%s(%s.%s(arg, includeChildren, %s));"
                    , numTabs, targetConnectionName, mapperName, mapperMethodName, MAPPED_OBJECTS_PARAMETER_TEXT);
            convertMethod.addLine("result.get%s().add(connectionTable);", numTabs, getterSubName);
            mapperClass.addImport(String.format("%s.%s", getPackage(entity, daoPackageName), connectionTableName));
        } else if (reference.isOwner() && !hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.%s(arg, result, %s)"
                    , numTabs, mapperName, mapperMethodName, MAPPED_OBJECTS_PARAMETER_TEXT);
        } else {
            convertMethod.addLine("%1$s connectionTable = new %1$s();", numTabs, connectionTableName);
            convertMethod.addLine("connectionTable.set%s(result);", numTabs, sourceConnectionName);
            convertMethod.addLine("connectionTable.set%s(%s.%s(arg, %s));"
                    , numTabs, targetConnectionName, mapperName, mapperMethodName, MAPPED_OBJECTS_PARAMETER_TEXT);
            convertMethod.addLine("result.get%s().add(connectionTable);", numTabs, getterSubName);
            mapperClass.addImport(String.format("%s.%s", getPackage(entity, daoPackageName), connectionTableName));
        }

        convertMethod.addLine("%s);", reference.isOwner() ? "" : "}");
    }

    /**
     * Creates a basic convert method
     *
     * @param entity                      entity which is to map
     * @param classParameterPostFix       postfix for classes and parameters
     * @param sourceClassParameterPostFix postfix for classes and parameters which is to map
     * @return the created Method
     */
    private Method createConvertMethodBase(Entity entity, String classParameterPostFix, String sourceClassParameterPostFix) {
        Method convertMethod = new Method(getConvertMethodName(entity, classParameterPostFix));
        convertMethod.setMethodType(String.format("%s%s", entity.getBaseName(), classParameterPostFix));
        convertMethod.setQualifier(Qualifier.PUBLIC);
        convertMethod.setStatic(true);

        String objParamName = getLowerFirst(entity.getBaseName());
        convertMethod.addParameter(String.format("%s%s", getUpperFirst(entity.getBaseName()), sourceClassParameterPostFix), objParamName);

        if (hasIncludeChildrenParameter(entity)) {
            convertMethod.addParameter("boolean", INCLUDE_CHILDREN_PARAMETER);
        }

        return convertMethod;
    }

    /**
     * Determines the name of the mapper class
     *
     * @param entity entity whose mapper is asked for
     * @return class name of the mapper
     */
    private String getMapperName(Entity entity) {
        return getMapperName(entity.getGrouping() == null ? null : entity.getGrouping().getGroupingPackage());
    }

    /**
     * Determines the name of the mapper class
     *
     * @param groupingName grouping which is used for the first part of name
     * @return class name of the mapper
     */
    private String getMapperName(String groupingName) {
        return String.format("%sAccessMapper", groupingName != null && !groupingName.trim().isEmpty() ? getUpperFirst(groupingName) : "Common");
    }

    /**
     * Determines how to get the key for the {@code mappedObjects} map
     *
     * @param entity entity whose key is asked for
     * @return the key to use
     */
    private String getterIdentificationForMap(Entity entity, String classParameterPostFix) {
        if (config.isUseIdGenerator()) {
            return String.format("%s.getIdentification()", getLowerFirst(entity.getBaseName()));
        }
        return String.format("\"%s%s\" + %s.getId().longValue()", getUpperFirst(entity.getBaseName()), classParameterPostFix, getLowerFirst(entity.getBaseName()));
    }

    /**
     * Checks whether a converting method of an entity needs to provide a mapping indicator for child entities
     *
     * @param entity entity which is to check
     * @return {@code true} if an indicator is to provide
     */
    private boolean hasIncludeChildrenParameter(Entity entity) {
        return entity.getReferences().stream().anyMatch(Reference::isList);
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
        mapperClass.addImport(getPackageAndClass(entity, daoPackageName, DAO_POSTFIX));
        mapperClass.addImport(getPackageAndClass(entity, domainPackageName, DOMAIN_POSTFIX));

        entity.getParentRefs().stream()
                .filter(Reference::isOwner)
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
        createConvertMethodWithParentWithoutMap(mapperClass, entity, referenceToParent, domainPackageName, DOMAIN_POSTFIX, DAO_POSTFIX);

        Method convertMethodWithMap = createConvertMethodWithParentBase(mapperClass, entity, referenceToParent, domainPackageName, DOMAIN_POSTFIX, DAO_POSTFIX);
        convertMethodWithMap.addParameter(String.format(MAP_DECLARATION_TEXT, Map.class.getSimpleName(), DomainCreator.DOMAIN_INTERFACE), MAPPED_OBJECTS_PARAMETER_TEXT);
        convertMethodWithMap.addLine("%s result = %s(%s,%s %s)"
                , entity.getBaseName()
                , getConvertMethodName(entity, DOMAIN_POSTFIX)
                , getLowerFirst(entity.getBaseName())
                , hasIncludeChildrenParameter(entity) ? String.format(" %s,", INCLUDE_CHILDREN_PARAMETER) : ""
                , MAPPED_OBJECTS_PARAMETER_TEXT
        );
        convertMethodWithMap.addLine("if (result != null) {");
        if (referenceToParent.isList()) {
            convertMethodWithMap.addLine("%sparent.get%ss().add(result);", TAB, getUpperFirst(referenceToParent.getReferenceName()));
        } else {
            convertMethodWithMap.addLine("%sparent.set%s(result);", TAB, getUpperFirst(referenceToParent.getReferenceName()));
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
        createConvertMethodWithoutMap(mapperClass, entity, DOMAIN_POSTFIX, DAO_POSTFIX);

        Method convertMethodWithMap = createConvertMethodBase(entity, DOMAIN_POSTFIX, DAO_POSTFIX);
        convertMethodWithMap.addParameter(String.format(MAP_DECLARATION_TEXT, Map.class.getSimpleName(), DomainCreator.DOMAIN_INTERFACE), MAPPED_OBJECTS_PARAMETER_TEXT);

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
        addConvertDefaultMappings(convertMethod, entity, DOMAIN_POSTFIX);

        entity.getReferences().stream()
                .filter(ref -> !ref.isList())
                .forEach(ref -> addSingleRefConvert(convertMethod, entity, ref, DOMAIN_POSTFIX));
        convertMethod.addEmptyLine();

        entity.getReferences().stream()
                .filter(Reference::isList)
                .forEach(ref -> addMultiRefConvertToDomain(mapperClass, convertMethod, entity, ref));
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
        boolean hasIncludeChildrenParameter = hasIncludeChildrenParameter(reference.getRealTargetEntity());

        String mapperName = getMapperName(reference.getRealTargetEntity());
        String mapperMethodName = getConvertMethodName(reference.getRealTargetEntity(), DOMAIN_POSTFIX);
        String variableName = getLowerFirst(entity.getBaseName());
        String getterSubName = getUpperFirst(reference.getReferenceName()) + "s";
        String targetConnectionName = getUpperFirst(entity.getBaseName());

        mapperClass.addImport(ArrayList.class.getName());
        convertMethod.addLine("%s.get%s().forEach(arg ->", variableName, getterSubName);

        if (reference.isOwner() && hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.%s(arg, includeChildren, result, %s)"
                    , 2, mapperName, mapperMethodName, MAPPED_OBJECTS_PARAMETER_TEXT);
        } else if (!reference.isOwner() && hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.%s(arg.get%s(), includeChildren, result, %s)"
                    , 2, mapperName, mapperMethodName, targetConnectionName, MAPPED_OBJECTS_PARAMETER_TEXT);
        } else if (reference.isOwner() && !hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.%s(arg, result, %s)"
                    , 2, mapperName, mapperMethodName, MAPPED_OBJECTS_PARAMETER_TEXT);
        } else {
            convertMethod.addLine("%s.%s(arg.get%s(), result, %s)"
                    , 2, mapperName, mapperMethodName, targetConnectionName, MAPPED_OBJECTS_PARAMETER_TEXT);
        }

        convertMethod.addLine(");");
    }
}