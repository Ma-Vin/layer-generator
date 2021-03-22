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
    public static final String INCLUDE_CHILDREN_PARAMETER = "includeChildren";
    public static final String DAO_POSTFIX = "Dao";

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
        createConvertMethodWithParentWithoutMap(mapperClass, entity, referenceToParent, daoPackageName, DAO_POSTFIX);

        Method convertMethodWithMap = createConvertMethodWithParentBase(mapperClass, entity, referenceToParent, daoPackageName, DAO_POSTFIX);
        convertMethodWithMap.addParameter(String.format("%s<String, %s>", Map.class.getSimpleName(), DaoCreator.DAO_INTERFACE), "mappedObjects");
        convertMethodWithMap.addLine("%sDao result = %s(%s,%s mappedObjects)"
                , entity.getBaseName()
                , getConvertMethodNameDao(entity)
                , getLowerFirst(entity.getBaseName())
                , hasIncludeChildrenParameter(entity) ? String.format(" %s,", INCLUDE_CHILDREN_PARAMETER) : ""
        );
        convertMethodWithMap.addLine("if (result != null) {");
        convertMethodWithMap.addLine("%sresult.setParent%s(parent);", TAB, referenceToParent.getTargetEntity());
        if (referenceToParent.isList()) {
            convertMethodWithMap.addLine("%sparent.get%ss().add(result);", TAB, getUpperFirst(referenceToParent.getReferenceName()));
        } else {
            convertMethodWithMap.addLine("%sparent.set%s(result);", TAB, getUpperFirst(referenceToParent.getReferenceName()));
        }
        convertMethodWithMap.addLine("}");
        convertMethodWithMap.addLine("return result;");

        mapperClass.addMethod(convertMethodWithMap);
        mapperClass.addImport(Map.class.getName());
    }

    /**
     * Creates mapping methods with a given parent but without a map parameter
     *
     * @param mapperClass           class where to add methods at
     * @param entity                entity whose properties are to map
     * @param referenceToParent     reference to parent which should be used for the parent parameter
     * @param packageName           name of base package
     * @param classParameterPostFix postfix for classes and parameters
     */
    private void createConvertMethodWithParentWithoutMap(Clazz mapperClass, Entity entity, Reference referenceToParent, String packageName, String classParameterPostFix) {
        Method convertMethod = createConvertMethodWithParentBase(mapperClass, entity, referenceToParent, packageName, classParameterPostFix);
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
     * @param mapperClass           class where to add the method
     * @param entity                entity which is to map
     * @param referenceToParent     reference to parent which should be used for the parent parameter
     * @param packageName           name of base  package
     * @param classParameterPostFix postfix for classes and parameters
     * @return the created Method
     */
    private Method createConvertMethodWithParentBase(Clazz mapperClass, Entity entity, Reference referenceToParent
            , String packageName, String classParameterPostFix) {

        Method convertMethod = createConvertMethodBase(entity, classParameterPostFix);
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
        createConvertMethodWithoutMap(mapperClass, entity, DAO_POSTFIX);

        Method convertMethodWithMap = createConvertMethodBase(entity, DAO_POSTFIX);
        convertMethodWithMap.addParameter(String.format("%s<String, %s>", Map.class.getSimpleName(), DaoCreator.DAO_INTERFACE), "mappedObjects");

        createConvertToDaoMappings(mapperClass, convertMethodWithMap, entity, daoPackageName);

        mapperClass.addMethod(convertMethodWithMap);
        mapperClass.addImport(Map.class.getName());
    }

    /**
     * Creates mapping methods without a map parameter
     *
     * @param mapperClass           class where to add methods at
     * @param entity                entity whose properties are to map
     * @param classParameterPostFix postfix for classes and parameters
     */
    private void createConvertMethodWithoutMap(Clazz mapperClass, Entity entity, String classParameterPostFix) {
        Method convertMethod = createConvertMethodBase(entity, classParameterPostFix);
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
        convertMethod.addLine("mappedObjects.put(identification, result);");
        convertMethod.addLine("return result;");
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
        convertMethod.addLine("String identification = %s;", getterIdentificationForMap(entity));
        convertMethod.addLine("if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {", getterIdentificationForMap(entity));
        convertMethod.addLine("return (%sDao) mappedObjects.get(identification);", 1, getUpperFirst(entity.getBaseName()));
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
            convertMethod.addLine("%s.%s(%s.get%s(), includeChildren, result, mappedObjects);"
                    , mapperName, mapperMethodName, variableName, getterSubName);
        } else if (!reference.isOwner() && hasIncludeChildrenParameter) {
            convertMethod.addLine("result.set%s(%s.%s(%s.get%s(), includeChildren, mappedObjects));"
                    , getterSubName, mapperName, mapperMethodName, variableName, getterSubName);
        } else if (reference.isOwner() && !hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.%s(%s.get%s(), result, mappedObjects);"
                    , mapperName, mapperMethodName, variableName, getterSubName);
        } else {
            convertMethod.addLine("result.set%s(%s.%s(%s.get%s(), mappedObjects));"
                    , getterSubName, mapperName, mapperMethodName, variableName, getterSubName);
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
            convertMethod.addLine("%s.%s(arg, includeChildren, result, mappedObjects)"
                    , numTabs, mapperName, mapperMethodName);
        } else if (!reference.isOwner() && hasIncludeChildrenParameter) {
            convertMethod.addLine("%1$s connectionTable = new %1$s();", numTabs, connectionTableName);
            convertMethod.addLine("connectionTable.set%s(result);", numTabs, sourceConnectionName);
            convertMethod.addLine("connectionTable.set%s(%s.%s(arg, includeChildren, mappedObjects));"
                    , numTabs, targetConnectionName, mapperName, mapperMethodName);
            convertMethod.addLine("result.get%s().add(connectionTable);", numTabs, getterSubName);
            mapperClass.addImport(String.format("%s.%s", getPackage(entity, daoPackageName), connectionTableName));
        } else if (reference.isOwner() && !hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.%s(arg, result, mappedObjects)"
                    , numTabs, mapperName, mapperMethodName);
        } else {
            convertMethod.addLine("%1$s connectionTable = new %1$s();", numTabs, connectionTableName);
            convertMethod.addLine("connectionTable.set%s(result);", numTabs, sourceConnectionName);
            convertMethod.addLine("connectionTable.set%s(%s.%s(arg, mappedObjects));"
                    , numTabs, targetConnectionName, mapperName, mapperMethodName);
            convertMethod.addLine("result.get%s().add(connectionTable);", numTabs, getterSubName);
            mapperClass.addImport(String.format("%s.%s", getPackage(entity, daoPackageName), connectionTableName));
        }

        convertMethod.addLine("%s);", reference.isOwner() ? "" : "}");
    }

    /**
     * Creates a basic convert method
     *
     * @param entity                entity which is to map
     * @param classParameterPostFix postfix for classes and parameters
     * @return the created Method
     */
    private Method createConvertMethodBase(Entity entity, String classParameterPostFix) {
        Method convertMethod = new Method(getConvertMethodName(entity, classParameterPostFix));
        convertMethod.setMethodType(String.format("%s%s", entity.getBaseName(), classParameterPostFix));
        convertMethod.setQualifier(Qualifier.PUBLIC);
        convertMethod.setStatic(true);

        String objParamName = getLowerFirst(entity.getBaseName());
        convertMethod.addParameter(entity.getBaseName(), objParamName);

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
    private String getterIdentificationForMap(Entity entity) {
        if (config.isUseIdGenerator()) {
            return String.format("%s.getIdentification()", getLowerFirst(entity.getBaseName()));
        }
        return String.format("\"%sDao\" + %s.getId().longValue()", getUpperFirst(entity.getBaseName()), getLowerFirst(entity.getBaseName()));
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
    }
}