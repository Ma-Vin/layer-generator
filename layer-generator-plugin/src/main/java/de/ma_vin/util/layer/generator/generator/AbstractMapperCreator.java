package de.ma_vin.util.layer.generator.generator;

import static de.ma_vin.util.layer.generator.generator.CommonMapperCreator.*;

import de.ma_vin.util.layer.generator.builder.MapperType;
import de.ma_vin.util.layer.generator.sources.*;
import de.ma_vin.util.layer.generator.config.elements.Config;
import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.config.elements.Field;
import de.ma_vin.util.layer.generator.config.elements.Reference;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.maven.plugin.logging.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractMapperCreator extends AbstractCreator {

    public static final String INCLUDE_CHILDREN_PARAMETER = "includeChildren";
    public static final String CONVERT_TO_TEXT = "convertTo%s%s";
    public static final String MAPPED_OBJECTS_PARAMETER_TEXT = "mappedObjects";
    public static final String MAP_DECLARATION_TEXT = "%s<String, %s>";
    public static final String RETURN_RESULT_TEXT = "return result;";

    AbstractMapperCreator(Config config, Log logger) {
        super(config, logger);
    }

    /**
     * Determines the name of the mapper class
     *
     * @param mapperTypeName Name of Type to use for the name
     * @param groupingName   grouping which is used for the first part of name
     * @return class name of the mapper
     */
    protected String getMapperName(String mapperTypeName, String groupingName) {
        if (groupingName != null && groupingName.contains(".")) {
            String[] split = groupingName.split("\\.");
            StringBuilder sb = new StringBuilder();
            for (String s : split) {
                sb.append(getUpperFirst(s));
            }
            groupingName = sb.toString();
        }
        return String.format("%s%sMapper", groupingName != null && !groupingName.trim().isEmpty() ? getUpperFirst(groupingName) : "Common"
                , getUpperFirst(mapperTypeName));
    }

    /**
     * Determines the name of the mapper class
     *
     * @param groupingName grouping which is used for the first part of name
     * @return class name of the mapper
     */
    protected abstract String getMapperName(String groupingName);

    /**
     * Determines the name of the mapper class
     *
     * @param entity entity whose mapper is asked for
     * @return class name of the mapper
     */
    protected String getMapperName(Entity entity) {
        return getMapperName(entity.getGrouping() == null ? null : entity.getGrouping().getGroupingPackage());
    }

    /**
     * Creates the singleton and get instance method
     *
     * @param mapperClass class which should get the getter
     * @param mapperType  type of mapper to determine the correct object factory
     */
    protected void createGetInstance(Clazz mapperClass, MapperType mapperType) {
        Attribute instanceAttribute = new Attribute("instance", mapperClass.getClassName());
        instanceAttribute.setStatic(true);
        mapperClass.addAttribute(instanceAttribute);

        Method getInstanceMethod = new Method("getInstance");
        getInstanceMethod.setMethodType(mapperClass.getClassName());
        getInstanceMethod.setStatic(true);
        getInstanceMethod.setQualifier(Qualifier.PUBLIC);
        getInstanceMethod.addLine("if (instance == null) {");
        getInstanceMethod.addLine("instance = %s.create%s();", 1, mapperType.getFactoryClassName(), mapperClass.getClassName());
        getInstanceMethod.addLine("}");
        getInstanceMethod.addLine("return instance;");
        mapperClass.addMethod(getInstanceMethod);

        instanceAttribute.setJavaDoc(new JavaDoc("singleton"));
        getInstanceMethod.setJavaDoc(new JavaDoc());
        getInstanceMethod.getJavaDoc().setReturnDescription("the singleton");
    }

    /**
     * Creates the convertTo method which will be used for value and reference mapping
     *
     * @param mapperClass         class where to add the created method
     * @param methodName          the name of the method
     * @param sourceInterfaceName the interface of the object which is the input at the convert method
     * @param targetInterfaceName the interface of the object which is the result of the convert method
     */
    protected void createAndAddConvertToGenericMethod(Clazz mapperClass, String methodName, String sourceInterfaceName, String targetInterfaceName) {
        mapperClass.addImport(Map.class.getName());

        Method convertToMethod = new Method(methodName);
        convertToMethod.setQualifier(Qualifier.PROTECTED);
        convertToMethod.setStatic(true);
        convertToMethod.addGeneric(new Generic(SOURCE_GENERIC, sourceInterfaceName));
        convertToMethod.addGeneric(new Generic(TARGET_GENERIC, targetInterfaceName));
        convertToMethod.setMethodType(TARGET_GENERIC);
        convertToMethod.addParameter(SOURCE_GENERIC, SOURCE_VARIABLE);
        convertToMethod.addParameter(String.format(CLASS_TWO_GENERICS, Map.class.getSimpleName(), String.class.getSimpleName(), targetInterfaceName), MAPPED_OBJECTS_PARAMETER_TEXT);
        convertToMethod.addParameter(String.format(CLASS_ONE_GENERIC, OBJECT_CREATOR_INTERFACE, TARGET_GENERIC), OBJECT_CREATOR_PARAMETER);
        convertToMethod.addParameter(String.format(CLASS_TWO_GENERICS, VALUE_MAPPER_INTERFACE, SOURCE_GENERIC, TARGET_GENERIC), VALUE_MAPPER_PARAMETER);
        convertToMethod.addParameter(String.format(CLASS_TWO_GENERICS, REFERENCE_MAPPER_INTERFACE, SOURCE_GENERIC, TARGET_GENERIC), SINGLE_REFERENCE_MAPPER_PARAMETER);
        convertToMethod.addParameter(String.format(CLASS_TWO_GENERICS, REFERENCE_MAPPER_INTERFACE, SOURCE_GENERIC, TARGET_GENERIC), MULTI_REFERENCE_MAPPER_PARAMETER);

        convertToMethod.addLine("return %s(%s, %s, %s, %s, %s, %s", CONVERT_TO_METHOD_NAME, SOURCE_VARIABLE, MAPPED_OBJECTS_PARAMETER_TEXT
                , OBJECT_CREATOR_PARAMETER, VALUE_MAPPER_PARAMETER, SINGLE_REFERENCE_MAPPER_PARAMETER, MULTI_REFERENCE_MAPPER_PARAMETER);
        if (config.isUseIdGenerator()) {
            convertToMethod.addLine(", %s::getIdentification, (s, t) -> t.setIdentification(s.getIdentification()));", 2, SOURCE_GENERIC);
        } else {
            convertToMethod.addLine(", s -> s.getClass().getSimpleName() + s.getId().longValue(), (s, t) -> t.setId(s.getId()));", 2, SOURCE_GENERIC);
        }

        mapperClass.addMethod(convertToMethod);

        createAndAddConvertToGenericJavaDocs(convertToMethod, sourceInterfaceName, targetInterfaceName);
    }

    /**
     * Adds the JavaDoc at the convertTo method which will be used for value and reference mapping
     *
     * @param convertToMethod     method where to add doc
     * @param sourceInterfaceName the interface of the object which is the input at the convert method
     * @param targetInterfaceName the interface of the object which is the result of the convert method
     */
    private void createAndAddConvertToGenericJavaDocs(Method convertToMethod, String sourceInterfaceName, String targetInterfaceName) {
        JavaDoc javaDoc = new JavaDoc();
        javaDoc.addLine("Converts an {@link %s} to an {@link %s} object", sourceInterfaceName, targetInterfaceName);

        javaDoc.addParams(SOURCE_VARIABLE, "object which is to converted");
        javaDoc.addParams(MAPPED_OBJECTS_PARAMETER_TEXT
                , "map which contains already mapped objects. If an identification of {@code %s} is contained, the found {@link %s} will be returned"
                , SOURCE_VARIABLE, targetInterfaceName);
        javaDoc.addParams(OBJECT_CREATOR_PARAMETER, "functional interface which is called to create a new instance of {@link %s} as result"
                , targetInterfaceName);
        javaDoc.addParams(VALUE_MAPPER_PARAMETER, "functional interface which is called to set the values of {@code %s} at result"
                , SOURCE_VARIABLE);
        javaDoc.addParams(SINGLE_REFERENCE_MAPPER_PARAMETER, "functional interface which is called to add the single references of {@code %s} at result"
                , SOURCE_VARIABLE);
        javaDoc.addParams(MULTI_REFERENCE_MAPPER_PARAMETER, "functional interface which is called to add the multi references of {@code %s} at result"
                , SOURCE_VARIABLE);

        javaDoc.addParams(String.format("<%s>", SOURCE_GENERIC), "the type of the source object");
        javaDoc.addParams(String.format("<%s>", TARGET_GENERIC), "the type of the target object");

        javaDoc.setReturnDescription("an equivalent new created object or the found one from the given map");

        convertToMethod.setJavaDoc(javaDoc);
    }

    /**
     * Creates the method which converts the values
     * <br>
     * SuppressWarnings("java:S1186"): do not blame empty methods, otherwise they wont be extendable
     *
     * @param mapperClass                 class where to add the method
     * @param entity                      entity whose properties are to map
     * @param fieldChecker                checks whether a field of entity is relevant or not
     * @param sourceClassParameterPostFix postfix for classes and parameters of the source
     * @param targetClassParameterPostFix postfix for classes and parameters of the target
     * @param sourceParameter             the method parameter for the source object
     * @param targetParameter             the method parameter for the target object
     * @return the created method
     */
    protected Method createSetValueMethod(Clazz mapperClass, Entity entity, FieldRelevantChecker fieldChecker
            , String sourceClassParameterPostFix, String targetClassParameterPostFix
            , String sourceParameter, String targetParameter) {

        Method setValueMethod = new Method(String.format("set%s%sValues", getUpperFirst(entity.getBaseName()), targetClassParameterPostFix));
        setValueMethod.setQualifier(Qualifier.PROTECTED);
        setValueMethod.addParameter(String.format("%s%s", getUpperFirst(entity.getBaseName()), sourceClassParameterPostFix), sourceParameter);
        setValueMethod.addParameter(String.format("%s%s", getUpperFirst(entity.getBaseName()), targetClassParameterPostFix), targetParameter);

        determineAlFields(entity).stream()
                .filter(fieldChecker::isRelevant)
                .forEach(f ->
                        setValueMethod.addLine("%1$s.set%3$s(%2$s.get%3$s());"
                                , targetParameter
                                , sourceParameter
                                , getUpperFirst(f.getFieldName()))
                );

        if (setValueMethod.getMethodBody().isEmpty()) {
            setValueMethod.addAnnotation(SuppressWarnings.class.getSimpleName(), null, "\"java:S1186\"");
        }
        addSetValueMethodJavadDoc(setValueMethod, sourceParameter, targetParameter);
        mapperClass.addMethod(setValueMethod);

        return setValueMethod;
    }

    /**
     * Adds the java doc to the set value method
     *
     * @param setValueMethod  Method where to add java doc
     * @param sourceParameter the method parameter for the source object
     * @param targetParameter the method parameter for the target object
     */
    private void addSetValueMethodJavadDoc(Method setValueMethod, String sourceParameter, String targetParameter) {
        JavaDoc javaDoc = new JavaDoc();
        javaDoc.addLine("Takes over values from {@code %s} to {@code %s} which are not of reference type", sourceParameter, targetParameter);
        javaDoc.addParams(sourceParameter, "source of the given values");
        javaDoc.addParams(targetParameter, "object where to set the values");
        setValueMethod.setJavaDoc(javaDoc);
    }

    /**
     * Creates the method which converts the single references
     * <br>
     * SuppressWarnings("java:S1186"): do not blame empty methods, otherwise they wont be extendable
     *
     * @param mapperClass         class where to add the method
     * @param parameterContainer  container for default parameter to create convert method
     * @param sourceParameter     the method parameter for the source object
     * @param targetParameter     the method parameter for the target object
     * @param targetInterfaceName the interface which the target object implements
     * @param referenceFilter     Functional reference to filter the relevant references
     */
    protected void createSetSingleReferenceMethod(Clazz mapperClass, CreateMethodParameterContainer parameterContainer
            , String sourceParameter, String targetParameter, String targetInterfaceName, ReferenceFilter referenceFilter) {

        List<Reference> relevantReferences = getSingleReferences(parameterContainer.entity, parameterContainer.entityChecker, referenceFilter);

        boolean hasIncludeChildrenParameter = hasSingleRefWithChildren(relevantReferences, parameterContainer.entityChecker);

        Method setSingleRefMethod = new Method(String.format("set%s%sSingleReferences", getUpperFirst(parameterContainer.entity.getBaseName()), parameterContainer.classParameterPostFix));
        setSingleRefMethod.setQualifier(Qualifier.PROTECTED);
        setSingleRefMethod.addParameter(String.format("%s%s", getUpperFirst(parameterContainer.entity.getBaseName()), parameterContainer.sourceClassParameterPostFix), sourceParameter);
        setSingleRefMethod.addParameter(String.format("%s%s", getUpperFirst(parameterContainer.entity.getBaseName()), parameterContainer.classParameterPostFix), targetParameter);
        if (hasIncludeChildrenParameter) {
            setSingleRefMethod.addParameter("boolean", INCLUDE_CHILDREN_PARAMETER);
        }
        setSingleRefMethod.addParameter(String.format(MAP_DECLARATION_TEXT, Map.class.getSimpleName(), targetInterfaceName), MAPPED_OBJECTS_PARAMETER_TEXT);

        relevantReferences.forEach(ref -> addSingleRefConvert(setSingleRefMethod, ref, parameterContainer.classParameterPostFix
                , sourceParameter, targetParameter, parameterContainer.entityChecker));

        if (setSingleRefMethod.getMethodBody().isEmpty()) {
            setSingleRefMethod.addAnnotation(SuppressWarnings.class.getSimpleName(), null, "\"java:S1186\"");
        }

        addSetSingleReferenceMethodJavaDoc(setSingleRefMethod, hasIncludeChildrenParameter, sourceParameter, targetParameter);
        mapperClass.addMethod(setSingleRefMethod);
    }

    /**
     * Adds the java doc to an single reference method
     *
     * @param setSingleRefMethod          Method where to add java doc
     * @param hasIncludeChildrenParameter Indicator if an include children must be described
     * @param sourceParameter             the method parameter for the source object
     * @param targetParameter             the method parameter for the target object
     */
    private void addSetSingleReferenceMethodJavaDoc(Method setSingleRefMethod, boolean hasIncludeChildrenParameter, String sourceParameter, String targetParameter) {
        JavaDoc javaDoc = new JavaDoc();
        javaDoc.addLine("Adds the references at {@code %s} which are not of type {@link java.util.Collection}", targetParameter);
        javaDoc.addParams(sourceParameter, "source of the given references");
        javaDoc.addParams(targetParameter, "object where to add the references");
        if (hasIncludeChildrenParameter) {
            javaDoc.addParams(INCLUDE_CHILDREN_PARAMETER, "{@code true} if all references at sub entities of the single reference should also be mapped. "
                    + "{@code false} if only those references should be mapped which are not of type {@link java.util.Collection}");
        }
        javaDoc.addParams(MAPPED_OBJECTS_PARAMETER_TEXT, "map which contains already mapped objects. It will be used while mapping sub entities of {@code %s} to {@code %s}"
                , sourceParameter, targetParameter);
        setSingleRefMethod.setJavaDoc(javaDoc);
    }

    protected List<Reference> getSingleReferences(Entity entity, EntityRelevantChecker entityChecker, ReferenceFilter referenceFilter) {
        return referenceFilter.filter(determineAllReferences(entity)).stream()
                .filter(ref -> !ref.isList() && entityChecker.isRelevant(ref.getRealTargetEntity()))
                .collect(Collectors.toList());
    }

    protected boolean hasSingleRefWithChildren(List<Reference> relevantReferences, EntityRelevantChecker entityChecker) {
        return relevantReferences.stream().anyMatch(reference -> hasIncludeChildrenParameter(reference.getRealTargetEntity(), entityChecker));
    }

    protected boolean hasSingleRefWithChildren(Entity entity, EntityRelevantChecker entityChecker, ReferenceFilter referenceFilter) {
        List<Reference> relevantReferences = getSingleReferences(entity, entityChecker, referenceFilter);

        return hasSingleRefWithChildren(relevantReferences, entityChecker);
    }

    protected List<Reference> getMultiReferences(Entity entity, EntityRelevantChecker entityChecker, ReferenceFilter referenceFilter) {
        return referenceFilter.filter(determineAllReferences(entity)).stream()
                .filter(ref -> ref.isList() && entityChecker.isRelevant(ref.getRealTargetEntity()))
                .collect(Collectors.toList());
    }

    /**
     * Creates a basic convert method
     *
     * @param parameterContainer container for default parameter to create convert method
     * @return the created Method
     */
    protected Method createConvertMethodBase(CreateMethodParameterContainer parameterContainer) {
        Method convertMethod = new Method(getConvertMethodName(parameterContainer.entity, parameterContainer.classParameterPostFix));
        convertMethod.setMethodType(String.format("%s%s", parameterContainer.entity.getBaseName(), parameterContainer.classParameterPostFix));
        convertMethod.setQualifier(Qualifier.PUBLIC);
        convertMethod.setStatic(true);
        convertMethod.setJavaDoc(new JavaDoc());

        String objParamName = getLowerFirst(parameterContainer.entity.getBaseName());
        convertMethod.addParameter(String.format("%s%s", getUpperFirst(parameterContainer.entity.getBaseName()), parameterContainer.sourceClassParameterPostFix), objParamName);
        convertMethod.getJavaDoc().addParams(objParamName, "the source object which should be converted");

        if (hasIncludeChildrenParameter(parameterContainer.entity, parameterContainer.entityChecker)) {
            convertMethod.addParameter("boolean", INCLUDE_CHILDREN_PARAMETER);
            convertMethod.getJavaDoc().addParams(INCLUDE_CHILDREN_PARAMETER, "{@code true} if all references should also be mapped. "
                    + "{@code false} if only those references should be mapped which are not of type {@link java.util.Collection}");
        }

        convertMethod.getJavaDoc().setReturnDescription("an equivalent new created {@link %s%s}"
                , getUpperFirst(parameterContainer.getEntity().getBaseName()), parameterContainer.getClassParameterPostFix());

        return convertMethod;
    }

    /**
     * Creates mapping methods without a map parameter
     *
     * @param mapperClass        class where to add methods at
     * @param parameterContainer container for default parameter to create convert method
     * @return the created method
     */
    protected Method createConvertMethodWithoutMap(Clazz mapperClass, CreateMethodParameterContainer parameterContainer) {

        return createConvertMethodWithoutMap(mapperClass, parameterContainer, false);
    }


    /**
     * Creates mapping methods without a map parameter
     *
     * @param mapperClass              class where to add methods at
     * @param parameterContainer       container for default parameter to create convert method
     * @param singleValueModelRelevant {@code true} if values, which are provided by only one model, should be passed as parameter.
     * @return the created method
     */
    protected Method createConvertMethodWithoutMap(Clazz mapperClass, CreateMethodParameterContainer parameterContainer, boolean singleValueModelRelevant) {

        Method convertMethod = createConvertMethodBase(parameterContainer);
        convertMethod.addLine("return %s(%s,%s%s new %s<>());"
                , getConvertMethodName(parameterContainer.entity, parameterContainer.classParameterPostFix)
                , getLowerFirst(parameterContainer.entity.getBaseName())
                , hasIncludeChildrenParameter(parameterContainer.entity, parameterContainer.entityChecker) ? String.format(" %s,", INCLUDE_CHILDREN_PARAMETER) : ""
                , singleValueModelRelevant ? getParameterOfRelevantSingleModelValuesText(parameterContainer.entity) : ""
                , HashMap.class.getSimpleName());
        mapperClass.addMethod(convertMethod);
        mapperClass.addImport(HashMap.class.getName());

        addConvertMethodDescription(convertMethod, parameterContainer.entity, parameterContainer.sourceClassParameterPostFix, parameterContainer.classParameterPostFix);

        return convertMethod;
    }

    /**
     * Creates mapping methods with a given parent but without a map parameter
     *
     * @param mapperClass        class where to add methods at
     * @param parameterContainer container for default parameter to create convert method
     * @param referenceToParent  reference to parent which should be used for the parent parameter
     * @param packageName        name of base package
     * @return the created method
     */
    protected Method createConvertMethodWithParentWithoutMap(Clazz mapperClass, CreateMethodParameterContainer parameterContainer
            , Reference referenceToParent, String packageName) {
        return createConvertMethodWithParentWithoutMap(mapperClass, parameterContainer, referenceToParent, packageName, false, false);
    }

    /**
     * Creates mapping methods with a given parent but without a map parameter
     *
     * @param mapperClass               class where to add methods at
     * @param parameterContainer        container for default parameter to create convert method
     * @param referenceToParent         reference to parent which should be used for the parent parameter
     * @param packageName               name of base package
     * @param singleValueModelRelevant  {@code true} if values, which are provided by only one model, should be passed as parameter.
     * @param referenceToParentRelevant {@code true} if values, which are provided by parent reference, should be passed as parameter.
     * @return the created method
     */
    protected Method createConvertMethodWithParentWithoutMap(Clazz mapperClass, CreateMethodParameterContainer parameterContainer
            , Reference referenceToParent, String packageName, boolean singleValueModelRelevant, boolean referenceToParentRelevant) {

        Method convertMethod = createConvertMethodWithParentBase(mapperClass, parameterContainer, referenceToParent, packageName);

        convertMethod.addLine("return %s(%s,%s parent,%s%s new %s<>());"
                , getConvertMethodName(parameterContainer.entity, parameterContainer.classParameterPostFix)
                , getLowerFirst(parameterContainer.entity.getBaseName())
                , hasIncludeChildrenParameter(parameterContainer.entity, parameterContainer.entityChecker) ? String.format(" %s,", INCLUDE_CHILDREN_PARAMETER) : ""
                , singleValueModelRelevant ? getParameterOfRelevantSingleModelValuesText(parameterContainer.entity) : ""
                , referenceToParentRelevant ? getParameterOfParentReferencesText(referenceToParent) : ""
                , HashMap.class.getSimpleName()
        );
        mapperClass.addMethod(convertMethod);
        mapperClass.addImport(HashMap.class.getName());

        addConvertMethodDescriptionWithParent(convertMethod, parameterContainer.entity, parameterContainer.sourceClassParameterPostFix, parameterContainer.classParameterPostFix);

        return convertMethod;
    }

    /**
     * Determines the values of parameters which are provided by only one model and should be set at some convert method call
     *
     * @param entity entity which is filtered
     * @return Text which can be added as parameter
     */
    protected abstract String getParameterOfRelevantSingleModelValuesText(Entity entity);

    /**
     * Determines the values of parameters which are provided by only one model and should be set at some convert method call
     *
     * @param referenceToParent reference to parent which should be used for the parent parameter
     * @return Text which can be added as parameter
     */
    protected abstract String getParameterOfParentReferencesText(Reference referenceToParent);

    /**
     * Creates a basic convert method with parent parameter
     *
     * @param mapperClass        class where to add the method
     * @param parameterContainer container for default parameter to create convert method
     * @param referenceToParent  reference to parent which should be used for the parent parameter
     * @param packageName        name of base  package
     * @return the created Method
     */
    protected Method createConvertMethodWithParentBase(Clazz mapperClass, CreateMethodParameterContainer parameterContainer
            , Reference referenceToParent, String packageName) {

        Method convertMethod = createConvertMethodBase(parameterContainer);
        mapperClass.addImport(getPackageAndClass(referenceToParent, packageName, parameterContainer.classParameterPostFix));
        convertMethod.addParameter(referenceToParent.getTargetEntity() + parameterContainer.classParameterPostFix, "parent");
        convertMethod.getJavaDoc().addParams("parent", "the parent of converted result");

        return convertMethod;
    }

    /**
     * Adds mappings for single reference to other dao
     *
     * @param convertMethod         the method where to add body
     * @param reference             reference which is actual to map
     * @param classParameterPostFix postfix for classes and parameters
     * @param entityChecker         checker for relevance verification
     * @param sourceParameter       the method parameter for the source object
     * @param targetParameter       the method parameter for the target object
     */
    protected void addSingleRefConvert(Method convertMethod, Reference reference, String classParameterPostFix
            , String sourceParameter, String targetParameter, EntityRelevantChecker entityChecker) {

        boolean hasIncludeChildrenParameter = hasIncludeChildrenParameter(reference.getRealTargetEntity(), entityChecker);

        String mapperName = getMapperName(reference.getRealTargetEntity());
        String mapperMethodName = getConvertMethodName(reference.getRealTargetEntity(), classParameterPostFix);
        String getterSubName = getUpperFirst(reference.getReferenceName());

        if (reference.isOwner() && hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.%s(%s.get%s(), includeChildren, %s, %s);"
                    , mapperName, mapperMethodName, sourceParameter, getterSubName, targetParameter, MAPPED_OBJECTS_PARAMETER_TEXT);
        } else if (!reference.isOwner() && hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.set%s(%s.%s(%s.get%s(), includeChildren, %s));"
                    , targetParameter, getterSubName, mapperName, mapperMethodName, sourceParameter, getterSubName, MAPPED_OBJECTS_PARAMETER_TEXT);
        } else if (reference.isOwner() && !hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.%s(%s.get%s(), %s, %s);"
                    , mapperName, mapperMethodName, sourceParameter, getterSubName, targetParameter, MAPPED_OBJECTS_PARAMETER_TEXT);
        } else {
            convertMethod.addLine("%s.set%s(%s.%s(%s.get%s(), %s));"
                    , targetParameter, getterSubName, mapperName, mapperMethodName, sourceParameter, getterSubName, MAPPED_OBJECTS_PARAMETER_TEXT);
        }
    }

    /**
     * Checks whether a converting method of an entity needs to provide a mapping indicator for child entities
     *
     * @param entity entity which is to check
     * @return {@code true} if an indicator is to provide
     */
    protected boolean hasIncludeChildrenParameter(Entity entity, EntityRelevantChecker entityChecker) {
        return entityChecker.isRelevant(entity) && determineAllReferences(entity).stream()
                .anyMatch(ref -> (ref.isList() && entityChecker.isRelevant(ref.getRealTargetEntity()))
                        || hasIncludeChildrenParameter(ref.getRealTargetEntity(), entityChecker));
    }

    /**
     * Determines the name of the converting method
     *
     * @param entity entity which is to convert
     * @return Name of the dao converting method
     */
    protected String getConvertMethodName(Entity entity, String classParameterPostFix) {
        return String.format(CONVERT_TO_TEXT, entity.getBaseName(), classParameterPostFix);
    }

    /**
     * Determines all fields which are need by mapping
     *
     * @param entity Entity whose fields should be determined
     * @return List of direct fields and those provided by super class of {@code entity}
     */
    protected List<Field> determineAlFields(Entity entity) {
        if (entity.hasNoParent()) {
            return entity.getFields();
        }
        List<Field> result = new ArrayList<>();
        result.addAll(entity.getFields());
        result.addAll(determineAlFields(entity.getRealParent()));

        return result;
    }

    /**
     * Determines all references which are need by mapping
     *
     * @param entity Entity whose References should be determined
     * @return List of direct references and those provided by super class of {@code entity}
     */
    protected List<Reference> determineAllReferences(Entity entity) {
        if (entity.hasNoParent()) {
            return entity.getReferences();
        }
        List<Reference> result = new ArrayList<>();
        result.addAll(entity.getReferences());
        result.addAll(determineAllReferences(entity.getRealParent()));

        return result;
    }

    /**
     * Adds a line of description at the java doc of an method with a parent parameter
     *
     * @param convertMethod      Method where to add description
     * @param entity             Entity which is converted
     * @param sourceClassPostFix postfix for classes which is to map
     * @param targetClassPostFix postfix for classes which will be the result
     */
    protected void addConvertMethodDescriptionWithParent(Method convertMethod, Entity entity, String sourceClassPostFix, String targetClassPostFix) {
        convertMethod.getJavaDoc().addLine("Converts a(n) {@link %1$s%2$s} to a(n) {@link %1$s%3$s} and sets the result to the corresponding reference property at the parent"
                , getUpperFirst(entity.getBaseName()), sourceClassPostFix, targetClassPostFix);
    }

    /**
     * Adds a line of description at the java doc of an method without a parent parameter
     *
     * @param convertMethod      Method where to add description
     * @param entity             Entity which is converted
     * @param sourceClassPostFix postfix for classes which is to map
     * @param targetClassPostFix postfix for classes which will be the result
     */
    protected void addConvertMethodDescription(Method convertMethod, Entity entity, String sourceClassPostFix, String targetClassPostFix) {
        convertMethod.getJavaDoc().addLine("Converts a(n) {@link %1$s%2$s} to a(n) {@link %1$s%3$s}"
                , getUpperFirst(entity.getBaseName()), sourceClassPostFix, targetClassPostFix);
    }

    /**
     * Adds the mappedObjects parameter and javadoc entry to a method
     *
     * @param convertMethod      Method where to add parameter end javadoc entry
     * @param entity             Entity which is converted
     * @param targetInterface    Interface which is implemented by the result
     * @param targetClassPostfix postfix for classes which will be the result
     */
    protected void addMappedObjectsParam(Method convertMethod, Entity entity, String targetInterface, String targetClassPostfix) {
        convertMethod.addParameter(String.format(MAP_DECLARATION_TEXT, Map.class.getSimpleName(), targetInterface), MAPPED_OBJECTS_PARAMETER_TEXT);
        convertMethod.getJavaDoc().addParams(MAPPED_OBJECTS_PARAMETER_TEXT
                , "map which contains already mapped objects. If an identification of {@code %s} is contained, the found {@link %s%s} will be returned"
                , getLowerFirst(entity.getBaseName()), getUpperFirst(entity.getBaseName()), targetClassPostfix);
        convertMethod.getJavaDoc().setReturnDescription(convertMethod.getJavaDoc().getReturnDescription() + " or the found one from the given map");
    }

    @FunctionalInterface
    public interface FieldRelevantChecker {
        /**
         * Checks whether field elements are to generate or not
         *
         * @param field field to check
         * @return {@code true} if the field is relevant for the mapper
         */
        boolean isRelevant(Field field);
    }

    @FunctionalInterface
    public interface EntityRelevantChecker {
        /**
         * Checks whether entity elements are to generate or not
         *
         * @param entity entity to check
         * @return {@code true} if the entity is relevant for the mapper
         */
        boolean isRelevant(Entity entity);
    }

    @FunctionalInterface
    public interface ReferenceFilter {
        /**
         * Filter a given list
         *
         * @param references list to filter
         * @return filtered list
         */
        List<Reference> filter(List<Reference> references);
    }

    /**
     * Container for default parameters which are need to create a convert method
     */
    @Data
    @AllArgsConstructor
    public static class CreateMethodParameterContainer {
        /**
         * entity which is to map
         */
        Entity entity;
        /**
         * postfix for classes and parameters
         */
        String classParameterPostFix;
        /**
         * postfix for classes and parameters which is to map
         */
        String sourceClassParameterPostFix;
        /**
         * checker for relevance verification
         */
        EntityRelevantChecker entityChecker;
    }
}
