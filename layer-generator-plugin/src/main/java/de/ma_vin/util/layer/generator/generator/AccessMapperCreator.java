package de.ma_vin.util.layer.generator.generator;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseAccessMapper;
import de.ma_vin.util.layer.generator.builder.MapperType;
import de.ma_vin.util.layer.generator.builder.ModelType;
import de.ma_vin.util.layer.generator.sources.*;
import de.ma_vin.util.layer.generator.config.elements.Config;
import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.config.elements.Field;
import de.ma_vin.util.layer.generator.config.elements.Reference;
import lombok.extern.log4j.Log4j2;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class AccessMapperCreator extends AbstractMapperCreator {

    public static final String ABSTRACT_ACCESS_MAPPER_CLASS_NAME = "AbstractAccessMapper";
    public static final String MAPPER_TYPE_NAME = "Access";
    public static final String DAO_POSTFIX = "Dao";
    public static final String DOMAIN_POSTFIX = "";
    public static final String CONVERT_TO_DAO_NAME = "convertToDao";
    public static final String CONVERT_TO_DOMAIN_NAME = "convertToDomain";
    public static final String DAO_PARAMETER = "dao";
    public static final String DOMAIN_PARAMETER = "domain";
    public static final String INCLUDE_CHILDREN_PARAMETER_WITH_COMMA = "includeChildren, ";
    public static final String EMPTY_METHOD_RULE_ID = "\"java:S1186\"";

    public static final String FILTER_PARAM_JAVA_DOC = "value to map between domain multiple {@link java.util.Collection}s and dao aggregated {@link java.util.Collection}";

    public AccessMapperCreator(Config config, Log logger) {
        super(config, logger);
    }

    /**
     * Creates the abstract mapper which is used by all access mapper
     *
     * @param mapperPackageName package of the mapper to use
     * @param mapperPackageDir  directory of the package
     * @param daoPackageName    name of base dao package
     * @param domainPackageName name of base domain package
     * @return {@code true} if generation was successful
     */
    public boolean createAbstractAccessMapper(String mapperPackageName, File mapperPackageDir, String daoPackageName, String domainPackageName) {
        Clazz mapperClass = new Clazz(mapperPackageName, ABSTRACT_ACCESS_MAPPER_CLASS_NAME);
        logger.debug("Create abstract access mapper " + mapperClass.getClassName());
        mapperClass.setAbstract(true);
        mapperClass.setExtension(CommonMapperCreator.ABSTRACT_MAPPER_CLASS_NAME);

        mapperClass.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, daoPackageName, DaoCreator.DAO_INTERFACE));
        mapperClass.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, domainPackageName, DomainCreator.DOMAIN_INTERFACE));

        mapperClass.setDescription("Generated abstract class which provides generic methods to convert a data access to a domain object and the other way around");

        createAndAddConvertToGenericMethod(mapperClass, CONVERT_TO_DAO_NAME, DomainCreator.DOMAIN_INTERFACE, DaoCreator.DAO_INTERFACE);
        createAndAddConvertToGenericMethod(mapperClass, CONVERT_TO_DOMAIN_NAME, DaoCreator.DAO_INTERFACE, DomainCreator.DOMAIN_INTERFACE);

        return writeClassFile(mapperPackageDir, mapperClass.getClassName(), mapperClass);
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

        if (entities.stream().noneMatch(e -> isEntityRelevant(e) && !e.getIsAbstract())) {
            logger.debug("No access mapper need for " + mapperPackageName);
            return true;
        }
        Clazz mapperClass = new Clazz(mapperPackageName, getMapperName(groupingName));
        logger.debug("Create access mapper " + mapperClass.getClassName());

        mapperClass.setExtension(ABSTRACT_ACCESS_MAPPER_CLASS_NAME);

        mapperClass.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, daoPackageName, ModelType.DAO.getFactoryClassName()));
        mapperClass.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, domainPackageName, ModelType.DOMAIN.getFactoryClassName()));
        mapperClass.addImport(BaseAccessMapper.class.getName());

        mapperClass.addAnnotation(BaseAccessMapper.class.getSimpleName());

        mapperClass.setDescription("Generated class which provides methods to convert a data access to a domain object of sub package <i>%s<i> and the other way around"
                , groupingName);

        createGetInstance(mapperClass, MapperType.ACCESS);

        entities.stream().filter(e -> !e.getIsAbstract()).forEach(e -> {
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

        DaoCreator.getAggregatedReferences(entity.getParentRefs()).forEach(ref ->
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

        CreateMethodParameterContainer createMethodParams = getDaoCreateMethodParameterContainer(entity);

        Method convertMethod = createConvertMethodWithParentWithoutMap(mapperClass, createMethodParams
                , referenceToParent, daoPackageName, true);

        Method convertMethodWithMap = createConvertMethodWithParentBase(mapperClass, createMethodParams, referenceToParent, daoPackageName);

        addFilterValueParameter(mapperClass, referenceToParent, convertMethod, convertMethodWithMap);

        addMappedObjectsParam(convertMethodWithMap, entity, DaoCreator.DAO_INTERFACE, DAO_POSTFIX);

        convertMethodWithMap.addLine("%sDao result = %s(%s,%s%s %s);"
                , entity.getBaseName()
                , getConvertMethodNameDao(entity)
                , getLowerFirst(entity.getBaseName())
                , hasIncludeChildrenParameter(entity, AccessMapperCreator::isEntityRelevant) ? String.format(" %s,", INCLUDE_CHILDREN_PARAMETER) : ""
                , getParameterOfRelevantSingleModelValuesText(entity)
                , MAPPED_OBJECTS_PARAMETER_TEXT
        );
        convertMethodWithMap.addLine("if (result != null) {");
        addSettingOfDaoParent(convertMethodWithMap, entity, referenceToParent);
        convertMethodWithMap.addLine("}");
        convertMethodWithMap.addLine(RETURN_RESULT_TEXT);

        addConvertMethodDescriptionWithParent(convertMethodWithMap, entity, DOMAIN_POSTFIX, DAO_POSTFIX);

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
            String getterMethodName = getUpperFirst(referenceToParent.getReferenceName());

            if (referenceToParent.isOwner()) {
                convertMethod.addLine("%sparent.get%ss().add(result);", AbstractGenerateLines.TAB, getterMethodName);
            } else {
                convertMethod.addLine("%1$s%2$s connectionTable = %3$s.create%2$s();", AbstractGenerateLines.TAB
                        , DaoCreator.getConnectionTableNameParentRef(referenceToParent), ModelType.DAO.getFactoryClassName());
                convertMethod.addLine("%sconnectionTable.set%s(result);", AbstractGenerateLines.TAB, entity.getBaseName());
                convertMethod.addLine("%sconnectionTable.set%s(parent);", AbstractGenerateLines.TAB, referenceToParent.getTargetEntity());
                convertMethod.addLine("%sparent.get%ss().add(connectionTable);", AbstractGenerateLines.TAB, getterMethodName);
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
        CreateMethodParameterContainer createMethodParams = getDaoCreateMethodParameterContainer(entity);

        Method convertMethod = createConvertMethodWithoutMap(mapperClass, createMethodParams, true);
        Method convertMethodWithMap = createConvertMethodBase(createMethodParams);

        addFilterValueParameter(mapperClass, entity, convertMethod, convertMethodWithMap);

        Method setValueMethod = createSetValueMethod(mapperClass, entity, AccessMapperCreator::isFieldRelevant, DOMAIN_POSTFIX, DAO_POSTFIX, DOMAIN_PARAMETER, DAO_PARAMETER);
        String filterParameter = appendAndGetFilterParameterToSetValue(mapperClass, entity, setValueMethod);

        addMappedObjectsParam(convertMethodWithMap, entity, DaoCreator.DAO_INTERFACE, DAO_POSTFIX);

        boolean hasIncludeChildrenParameter = !getMultiReferences(entity, AccessMapperCreator::isEntityRelevant, l -> l).isEmpty();

        convertMethodWithMap.addLine("return convertToDao(%1$s, %2$s, DaoObjectFactory::create%3$sDao, (%4$s, %5$s) -> getInstance().set%3$sDaoValues(%4$s, %5$s%6$s)"
                , getLowerFirst(entity.getBaseName()), MAPPED_OBJECTS_PARAMETER_TEXT, getUpperFirst(entity.getBaseName())
                , DOMAIN_PARAMETER, DAO_PARAMETER, filterParameter);
        convertMethodWithMap.addLine(", (%1$s, %2$s) -> getInstance().set%3$sDaoSingleReferences(%1$s, %2$s, %5$s%4$s)", 2
                , DOMAIN_PARAMETER, DAO_PARAMETER, getUpperFirst(entity.getBaseName()), MAPPED_OBJECTS_PARAMETER_TEXT
                , hasSingleRefWithChildren(entity, AccessMapperCreator::isEntityRelevant, l -> l) ? INCLUDE_CHILDREN_PARAMETER_WITH_COMMA : "");
        convertMethodWithMap.addLine(", (%1$s, %2$s) -> getInstance().set%3$sDaoMultiReferences(%1$s, %2$s, %5$s%4$s));", 2, DOMAIN_PARAMETER, DAO_PARAMETER
                , getUpperFirst(entity.getBaseName()), MAPPED_OBJECTS_PARAMETER_TEXT, hasIncludeChildrenParameter ? INCLUDE_CHILDREN_PARAMETER_WITH_COMMA : "");

        mapperClass.addMethod(convertMethodWithMap);
        mapperClass.addImport(Map.class.getName());

        addConvertMethodDescription(convertMethodWithMap, entity, DOMAIN_POSTFIX, DAO_POSTFIX);

        createSetSingleReferenceMethod(mapperClass, getDaoCreateMethodParameterContainer(entity), DOMAIN_PARAMETER, DAO_PARAMETER, DaoCreator.DAO_INTERFACE
                , l -> l);
        createSetDaoMultiReferenceMethod(mapperClass, entity, daoPackageName);
    }

    /**
     * Appends to the setValueMethod filter parameter if needed
     *
     * @param mapperClass    class where to add methods at
     * @param entity         entity whose properties are to map
     * @param setValueMethod Method which sets values
     * @return a comma separated list of parameters which are needed for filtering. First entry is a comma, if there are any parameters
     */
    String appendAndGetFilterParameterToSetValue(Clazz mapperClass, Entity entity, Method setValueMethod) {
        StringBuilder sb = new StringBuilder();
        getFilterAttributes(entity).forEach(f -> {
            mapperClass.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, f.getTypePackage(), f.getType()));
            setValueMethod.addParameter(f.getType(), getLowerFirst(f.getFieldName()));
            setValueMethod.getJavaDoc().addParams(getLowerFirst(f.getFieldName()), FILTER_PARAM_JAVA_DOC);
            setValueMethod.addLine("dao.set%s(%s);", getUpperFirst(f.getFieldName()), getLowerFirst(f.getFieldName()));
            sb.append(", ");
            sb.append(getLowerFirst(f.getFieldName()));
        });
        String result = sb.toString();
        if (!result.isEmpty()) {
            Optional<Annotation> suppressWarning = setValueMethod.getAnnotations().stream()
                    .filter(a -> a.getAnnotationName().equals(SuppressWarnings.class.getSimpleName())
                            && a.getParameters().stream().anyMatch(p -> p.getAttributeName() == null && p.getValues().length == 1 && EMPTY_METHOD_RULE_ID.equals(p.getValues()[0])))
                    .findFirst();
            suppressWarning.ifPresent(a -> setValueMethod.getAnnotations().remove(a));
        }
        return result;
    }

    /**
     * Creates the method which converts the multi references for dao
     * <br>
     * SuppressWarnings("java:S1186"): do not blame empty methods, otherwise they wont be extendable
     *
     * @param mapperClass    class where to add the method
     * @param entity         entity whose properties are to map
     * @param daoPackageName name of base dao package
     */
    protected void createSetDaoMultiReferenceMethod(Clazz mapperClass, Entity entity, String daoPackageName) {
        List<Reference> relevantReferences = getMultiReferences(entity, AccessMapperCreator::isEntityRelevant, l -> l);

        Method setMultiRefMethod = new Method(String.format("set%sDaoMultiReferences", getUpperFirst(entity.getBaseName())));
        setMultiRefMethod.setQualifier(Qualifier.PROTECTED);
        setMultiRefMethod.addParameter(String.format("%s%s", getUpperFirst(entity.getBaseName()), DOMAIN_POSTFIX), DOMAIN_PARAMETER);
        setMultiRefMethod.addParameter(String.format("%s%s", getUpperFirst(entity.getBaseName()), DAO_POSTFIX), DAO_PARAMETER);
        if (!relevantReferences.isEmpty()) {
            setMultiRefMethod.addParameter("boolean", INCLUDE_CHILDREN_PARAMETER);
        }
        setMultiRefMethod.addParameter(String.format(MAP_DECLARATION_TEXT, Map.class.getSimpleName(), DaoCreator.DAO_INTERFACE), MAPPED_OBJECTS_PARAMETER_TEXT);

        if (!relevantReferences.isEmpty()) {

            getMultiReferences(entity, AccessMapperCreator::isEntityRelevant, DaoCreator::getAggregatedReferences)
                    .forEach(ref -> {
                        String getterSubName = getUpperFirst(ref.getReferenceName()) + "s";
                        setMultiRefMethod.addLine("dao.set%s(new %s<>());", getterSubName, ArrayList.class.getSimpleName());
                    });
            setMultiRefMethod.addLine("if (includeChildren) {");
            relevantReferences.forEach(ref -> addMultiRefConvertToDao(mapperClass, setMultiRefMethod, entity, ref, daoPackageName));
            setMultiRefMethod.addLine("}");
        }

        if (setMultiRefMethod.getMethodBody().isEmpty()) {
            setMultiRefMethod.addAnnotation(SuppressWarnings.class.getSimpleName(), null, EMPTY_METHOD_RULE_ID);
        }
        addSetMultiReferenceMethodJavaDoc(setMultiRefMethod, !relevantReferences.isEmpty(), DOMAIN_PARAMETER, DAO_PARAMETER);

        mapperClass.addMethod(setMultiRefMethod);
    }

    /**
     * Adds the java doc to an multi reference method
     *
     * @param setMultiRefMethod           Method where to add java doc
     * @param hasIncludeChildrenParameter Indicator if an include children must be described
     * @param sourceParameter             the method parameter for the source object
     * @param targetParameter             the method parameter for the target object
     */
    private void addSetMultiReferenceMethodJavaDoc(Method setMultiRefMethod, boolean hasIncludeChildrenParameter, String sourceParameter, String targetParameter) {
        JavaDoc javaDoc = new JavaDoc();
        javaDoc.addLine("Adds the references at {@code %s} which are of type {@link java.util.Collection}", targetParameter);
        javaDoc.addParams(sourceParameter, "source of the given references");
        javaDoc.addParams(targetParameter, "object where to add the references");
        if (hasIncludeChildrenParameter) {
            javaDoc.addParams(INCLUDE_CHILDREN_PARAMETER, "{@code true} if references should be mapped. Otherwise {@code false}");
        }
        javaDoc.addParams(MAPPED_OBJECTS_PARAMETER_TEXT, "map which contains already mapped objects. It will be used while mapping sub entities of {@code %s} to {@code %s}"
                , sourceParameter, targetParameter);
        setMultiRefMethod.setJavaDoc(javaDoc);
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

        UtilTextContainer textContainer = new UtilTextContainer();
        textContainer.mapperName = getMapperName(reference.getRealTargetEntity());
        textContainer.mapperMethodName = getConvertMethodNameDao(reference.getRealTargetEntity());
        textContainer.getterSubName = getUpperFirst(reference.getReferenceName()) + "s";
        textContainer.connectionTableName = DaoCreator.getConnectionTableName(reference);
        textContainer.sourceConnectionName = getUpperFirst(reference.getParent().getBaseName());
        textContainer.targetConnectionName = getUpperFirst(reference.getRealTargetEntity().getBaseName());

        mapperClass.addImport(ArrayList.class.getName());

        convertMethod.addLine("domain.get%s().forEach(arg ->%s", 1, textContainer.getterSubName, reference.isOwner() ? "" : " {");

        addOwnerWithChildrenDaoMultiRef(mapperClass, convertMethod, reference, hasIncludeChildrenParameter, textContainer);
        addNotOwnerButWithChildrenDaoMultiRef(mapperClass, convertMethod, entity, reference, daoPackageName
                , hasIncludeChildrenParameter, textContainer);
        addOwnerWithoutChildrenDaoMultiRef(mapperClass, convertMethod, reference, hasIncludeChildrenParameter, textContainer);
        addNotOwnerWithoutChildrenDaoMultiRef(mapperClass, convertMethod, entity, reference, daoPackageName
                , hasIncludeChildrenParameter, textContainer);

        convertMethod.addLine("%s);", 1, reference.isOwner() ? "" : "}");
    }

    /**
     * Adds the connection table for references with children, but without ownership
     *
     * @param mapperClass                 class where to add imports
     * @param convertMethod               the method where to add body
     * @param entity                      entity whose properties are to map
     * @param reference                   reference which is actual to map
     * @param daoPackageName              name of base dao package
     * @param hasIncludeChildrenParameter indicator if the target has any children
     * @param textContainer               container for texts
     */
    private void addNotOwnerButWithChildrenDaoMultiRef(Clazz mapperClass, Method convertMethod, Entity entity, Reference reference
            , String daoPackageName, boolean hasIncludeChildrenParameter, UtilTextContainer textContainer) {

        if (!reference.isOwner() && hasIncludeChildrenParameter) {
            convertMethod.addLine("%1$s connectionTable = new %1$s();", 2, textContainer.connectionTableName);
            convertMethod.addLine("connectionTable.set%s(dao);", 2, textContainer.sourceConnectionName);
            convertMethod.addLine("connectionTable.set%s(%s.%s(arg, true, %s));", 2, textContainer.targetConnectionName
                    , textContainer.mapperName, textContainer.mapperMethodName, MAPPED_OBJECTS_PARAMETER_TEXT);
            convertMethod.addLine("dao.get%s().add(connectionTable);", 2, textContainer.getterSubName);
            mapperClass.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, getPackage(entity, daoPackageName), textContainer.connectionTableName));
        }
    }

    /**
     * Adds the connection table for references without children and ownership
     *
     * @param mapperClass                 class where to add imports
     * @param convertMethod               the method where to add body
     * @param entity                      entity whose properties are to map
     * @param reference                   reference which is actual to map
     * @param daoPackageName              name of base dao package
     * @param hasIncludeChildrenParameter indicator if the target has any children
     * @param textContainer               container for texts
     */
    private void addNotOwnerWithoutChildrenDaoMultiRef(Clazz mapperClass, Method convertMethod, Entity entity, Reference reference
            , String daoPackageName, boolean hasIncludeChildrenParameter, UtilTextContainer textContainer) {

        if (!reference.isOwner() && !hasIncludeChildrenParameter) {
            convertMethod.addLine("%1$s connectionTable = new %1$s();", 2, textContainer.connectionTableName);
            convertMethod.addLine("connectionTable.set%s(dao);", 2, textContainer.sourceConnectionName);
            convertMethod.addLine("connectionTable.set%s(%s.%s(arg, %s));", 2, textContainer.targetConnectionName
                    , textContainer.mapperName, textContainer.mapperMethodName, MAPPED_OBJECTS_PARAMETER_TEXT);
            convertMethod.addLine("dao.get%s().add(connectionTable);", 2, textContainer.getterSubName);
            mapperClass.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, getPackage(entity, daoPackageName), textContainer.connectionTableName));
        }
    }

    /**
     * Adds the setting for references with children and ownership
     *
     * @param mapperClass                 class where to add imports
     * @param convertMethod               the method where to add body
     * @param reference                   reference which is actual to map
     * @param hasIncludeChildrenParameter indicator if the target has any children
     * @param textContainer               container for texts
     */
    private void addOwnerWithChildrenDaoMultiRef(Clazz mapperClass, Method convertMethod, Reference reference
            , boolean hasIncludeChildrenParameter, UtilTextContainer textContainer) {

        if (reference.isOwner() && hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.%s(arg, true, dao,%s %s)"
                    , 3, textContainer.mapperName, textContainer.mapperMethodName, getFilterValueText(mapperClass, reference), MAPPED_OBJECTS_PARAMETER_TEXT);
        }
    }

    /**
     * Adds the setting for references without children, but with ownership
     *
     * @param mapperClass                 class where to add imports
     * @param convertMethod               the method where to add body
     * @param reference                   reference which is actual to map
     * @param hasIncludeChildrenParameter indicator if the target has any children
     * @param textContainer               container for texts
     */
    private void addOwnerWithoutChildrenDaoMultiRef(Clazz mapperClass, Method convertMethod, Reference reference, boolean hasIncludeChildrenParameter
            , UtilTextContainer textContainer) {

        if (reference.isOwner() && !hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.%s(arg, dao,%s %s)"
                    , 3, textContainer.mapperName, textContainer.mapperMethodName, getFilterValueText(mapperClass, reference), MAPPED_OBJECTS_PARAMETER_TEXT);
        }
    }

    /**
     * Determines the text for the filter value of dao multi ref
     *
     * @param mapperClass class where to add imports
     * @param reference   reference which is actual to map
     * @return If the reference is to aggregate and the filter field is not a domain one, a filter value parameter will be returned
     */
    private String getFilterValueText(Clazz mapperClass, Reference reference) {
        if (!DaoCreator.isToAggregate(reference, determineAllReferences(reference.getParent())) || reference.getRealFilterField().getModels().isDomain()) {
            return "";
        }
        mapperClass.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, reference.getRealFilterField().getTypePackage()
                , reference.getRealFilterField().getType()));
        return String.format(" %s.%s,", reference.getRealFilterField().getType(), reference.getFilterFieldValue());
    }

    @Override
    protected String getMapperName(String groupingName) {
        return getMapperName(MAPPER_TYPE_NAME, groupingName);
    }

    @Override
    protected String getParameterOfRelevantSingleModelValuesText(Entity entity) {
        StringBuilder sb = new StringBuilder();
        getFilterAttributes(entity).forEach(f -> {
            sb.append(" ");
            sb.append(f.getFieldName());
            sb.append(",");
        });
        return sb.toString();
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

        DaoCreator.getAggregatedReferences(entity.getParentRefs()).forEach(ref ->
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

        CreateMethodParameterContainer createMethodParams = getDomainCreateMethodParameterContainer(entity);

        createConvertMethodWithParentWithoutMap(mapperClass, createMethodParams, referenceToParent, domainPackageName);

        Method convertMethodWithMap = createConvertMethodWithParentBase(mapperClass, createMethodParams, referenceToParent, domainPackageName);

        addMappedObjectsParam(convertMethodWithMap, entity, DomainCreator.DOMAIN_INTERFACE, DOMAIN_POSTFIX);

        convertMethodWithMap.addLine("%s result = %s(%s,%s %s);"
                , entity.getBaseName()
                , getConvertMethodName(entity, DOMAIN_POSTFIX)
                , getLowerFirst(entity.getBaseName())
                , hasIncludeChildrenParameter(entity, AccessMapperCreator::isEntityRelevant) ? String.format(" %s,", INCLUDE_CHILDREN_PARAMETER) : ""
                , MAPPED_OBJECTS_PARAMETER_TEXT
        );
        convertMethodWithMap.addLine("if (result != null) {");
        addSettingOfDomainParent(mapperClass, convertMethodWithMap, referenceToParent);
        convertMethodWithMap.addLine("}");
        convertMethodWithMap.addLine(RETURN_RESULT_TEXT);

        addConvertMethodDescriptionWithParent(convertMethodWithMap, entity, DAO_POSTFIX, DOMAIN_POSTFIX);

        mapperClass.addMethod(convertMethodWithMap);
        mapperClass.addImport(Map.class.getName());
    }

    /**
     * Adds lines for setting the values at the parent
     *
     * @param convertMethod     Method where to add lines
     * @param referenceToParent Reference to parent
     */
    private void addSettingOfDomainParent(Clazz mapperClass, Method convertMethod, Reference referenceToParent) {
        if (!referenceToParent.isList()) {
            convertMethod.addLine("%sparent.set%s(result);", AbstractGenerateLines.TAB, getUpperFirst(referenceToParent.getReferenceName()));
            return;
        }
        if (!referenceToParent.isAggregated()) {
            convertMethod.addLine("%sparent.get%ss().add(result);", AbstractGenerateLines.TAB, getUpperFirst(referenceToParent.getReferenceName()));
            return;
        }
        List<Reference> references = referenceToParent.getRealTargetEntity().getReferences().stream()
                .filter(ref -> ref.getRealTargetEntity().equals(referenceToParent.getParent()) && ref.isList() == referenceToParent.isList() && ref.isOwner() == referenceToParent.isOwner())
                .collect(Collectors.toList());

        convertMethod.addLine("switch (%s.get%s()) {", 1, getLowerFirst(referenceToParent.getParent().getBaseName()), getUpperFirst(referenceToParent.getFilterField()));

        references.forEach(ref -> {
            convertMethod.addLine("case %s:", 2, ref.getFilterFieldValue());
            convertMethod.addLine("parent.add%ss(result);", 3, getUpperFirst(ref.getReferenceName()));
            convertMethod.addLine("break;", 3);
        });

        convertMethod.addLine("default:", 2);
        convertMethod.addLine("log.error(\"There is not any mapping rule for dummy of type {}\", %s.get%s());", 3
                , getLowerFirst(referenceToParent.getParent().getBaseName()), getUpperFirst(referenceToParent.getFilterField()));
        convertMethod.addLine("}", 1);

        mapperClass.addImport(Log4j2.class.getName());
        mapperClass.addAnnotation(Log4j2.class.getSimpleName());
    }

    /**
     * Adds the filter parameter to the convert methods if necessary
     *
     * @param mapperClass          class where to add import at
     * @param referenceToParent    reference to parent which should be used for the parent parameter
     * @param convertMethod        convert method where to add parameter
     * @param convertMethodWithMap convert method where to add parameter
     */
    private void addFilterValueParameter(Clazz mapperClass, Reference referenceToParent, Method convertMethod, Method convertMethodWithMap) {
        List<Reference> allReferences = determineAllReferences(referenceToParent.getRealTargetEntity());
        Reference reference = allReferences.stream().filter(ref -> ref.getRealTargetEntity().equals(referenceToParent.getParent())).findFirst().orElseThrow();

        if (DaoCreator.isToAggregate(reference, allReferences)
                && !referenceToParent.getRealFilterField().getModels().isDomain()) {

            mapperClass.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, referenceToParent.getRealFilterField().getTypePackage()
                    , referenceToParent.getRealFilterField().getType()));
            convertMethod.addParameter(referenceToParent.getRealFilterField().getType(), getLowerFirst(referenceToParent.getFilterField()));
            convertMethod.getJavaDoc().addParams(getLowerFirst(referenceToParent.getFilterField()), FILTER_PARAM_JAVA_DOC);
            convertMethodWithMap.addParameter(referenceToParent.getRealFilterField().getType(), getLowerFirst(referenceToParent.getFilterField()));
            convertMethodWithMap.getJavaDoc().addParams(getLowerFirst(referenceToParent.getFilterField()), FILTER_PARAM_JAVA_DOC);
        }
    }

    /**
     * Adds the filter parameter to the convert methods if necessary
     *
     * @param mapperClass          class where to add import at
     * @param entity               entity which is converted
     * @param convertMethod        convert method where to add parameter
     * @param convertMethodWithMap convert method where to add parameter
     */
    private void addFilterValueParameter(Clazz mapperClass, Entity entity, Method convertMethod, Method convertMethodWithMap) {
        Set<Field> filterAttributes = getFilterAttributes(entity);

        filterAttributes.forEach(f -> {
            mapperClass.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, f.getTypePackage(), f.getType()));
            convertMethod.addParameter(f.getType(), getLowerFirst(f.getFieldName()));
            convertMethod.getJavaDoc().addParams(getLowerFirst(f.getFieldName()), FILTER_PARAM_JAVA_DOC);
            convertMethodWithMap.addParameter(f.getType(), getLowerFirst(f.getFieldName()));
            convertMethodWithMap.getJavaDoc().addParams(getLowerFirst(f.getFieldName()), FILTER_PARAM_JAVA_DOC);
        });
    }

    /**
     * Determines all fields witch are used by references to filter the entity
     *
     * @param entity Entity which is the target of the filtering references
     * @return Set of Fields which are used as filter
     */
    private Set<Field> getFilterAttributes(Entity entity) {
        return entity.getParentRefs().stream()
                .map(Reference::getRealTargetEntity)
                .flatMap(e -> DaoCreator.getTreatedReferences(e.getReferences()).stream())
                .filter(ref -> ref.isAggregated() && ref.getTargetEntity().equals(entity.getBaseName()) && !ref.getRealFilterField().getModels().isDomain())
                .map(Reference::getRealFilterField)
                .collect(Collectors.toSet());
    }

    /**
     * Creates mapping methods from dao to domain without a given parent as parameter
     *
     * @param mapperClass class where to add methods at
     * @param entity      entity whose properties are to map
     */
    private void createConvertToDomainMethod(Clazz mapperClass, Entity entity) {
        CreateMethodParameterContainer createMethodParams = getDomainCreateMethodParameterContainer(entity);

        createConvertMethodWithoutMap(mapperClass, createMethodParams);

        Method convertMethodWithMap = createConvertMethodBase(createMethodParams);
        addMappedObjectsParam(convertMethodWithMap, entity, DomainCreator.DOMAIN_INTERFACE, DOMAIN_POSTFIX);

        boolean hasIncludeChildrenParameter = !getMultiReferences(entity, AccessMapperCreator::isEntityRelevant, DaoCreator::getAggregatedReferences).isEmpty();

        convertMethodWithMap.addLine("return convertToDomain(%1$s, %2$s, DomainObjectFactory::create%3$s, (%4$s, %5$s) -> getInstance().set%3$sValues(%4$s, %5$s)"
                , getLowerFirst(entity.getBaseName()), MAPPED_OBJECTS_PARAMETER_TEXT, getUpperFirst(entity.getBaseName())
                , DAO_PARAMETER, DOMAIN_PARAMETER);
        convertMethodWithMap.addLine(", (%1$s, %2$s) -> getInstance().set%3$sSingleReferences(%1$s, %2$s, %5$s%4$s)", 2
                , DAO_PARAMETER, DOMAIN_PARAMETER, getUpperFirst(entity.getBaseName()), MAPPED_OBJECTS_PARAMETER_TEXT
                , hasSingleRefWithChildren(entity, AccessMapperCreator::isEntityRelevant, DaoCreator::getAggregatedReferences) ? INCLUDE_CHILDREN_PARAMETER_WITH_COMMA : "");
        convertMethodWithMap.addLine(", (%1$s, %2$s) -> getInstance().set%3$sMultiReferences(%1$s, %2$s, %5$s%4$s));", 2, DAO_PARAMETER, DOMAIN_PARAMETER
                , getUpperFirst(entity.getBaseName()), MAPPED_OBJECTS_PARAMETER_TEXT, hasIncludeChildrenParameter ? INCLUDE_CHILDREN_PARAMETER_WITH_COMMA : "");

        mapperClass.addMethod(convertMethodWithMap);
        mapperClass.addImport(Map.class.getName());

        addConvertMethodDescription(convertMethodWithMap, entity, DAO_POSTFIX, DOMAIN_POSTFIX);

        createSetValueMethod(mapperClass, entity, AccessMapperCreator::isFieldRelevant, DAO_POSTFIX, DOMAIN_POSTFIX, DAO_PARAMETER, DOMAIN_PARAMETER);
        createSetSingleReferenceMethod(mapperClass, getDomainCreateMethodParameterContainer(entity), DAO_PARAMETER, DOMAIN_PARAMETER, DomainCreator.DOMAIN_INTERFACE
                , DaoCreator::getAggregatedReferences);
        createSetDomainMultiReferenceMethod(mapperClass, entity);
    }

    /**
     * Creates the method which converts the multi references for domain
     * <br>
     * SuppressWarnings("java:S1186"): do not blame empty methods, otherwise they wont be extendable
     *
     * @param mapperClass class where to add the method
     * @param entity      entity whose properties are to map
     */
    protected void createSetDomainMultiReferenceMethod(Clazz mapperClass, Entity entity) {
        List<Reference> relevantReferences = getMultiReferences(entity, AccessMapperCreator::isEntityRelevant, DaoCreator::getAggregatedReferences);

        Method setMultiRefMethod = new Method(String.format("set%sMultiReferences", getUpperFirst(entity.getBaseName())));
        setMultiRefMethod.setQualifier(Qualifier.PROTECTED);
        setMultiRefMethod.addParameter(String.format("%s%s", getUpperFirst(entity.getBaseName()), DAO_POSTFIX), DAO_PARAMETER);
        setMultiRefMethod.addParameter(String.format("%s%s", getUpperFirst(entity.getBaseName()), DOMAIN_POSTFIX), DOMAIN_PARAMETER);
        if (!relevantReferences.isEmpty()) {
            setMultiRefMethod.addParameter("boolean", INCLUDE_CHILDREN_PARAMETER);
        }
        setMultiRefMethod.addParameter(String.format(MAP_DECLARATION_TEXT, Map.class.getSimpleName(), DomainCreator.DOMAIN_INTERFACE), MAPPED_OBJECTS_PARAMETER_TEXT);


        if (!relevantReferences.isEmpty()) {
            setMultiRefMethod.addLine("if (includeChildren) {");
            relevantReferences.forEach(ref -> addMultiRefConvertToDomain(mapperClass, setMultiRefMethod, ref));
            setMultiRefMethod.addLine("}");
        }

        if (setMultiRefMethod.getMethodBody().isEmpty()) {
            setMultiRefMethod.addAnnotation(SuppressWarnings.class.getSimpleName(), null, EMPTY_METHOD_RULE_ID);
        }
        addSetMultiReferenceMethodJavaDoc(setMultiRefMethod, !relevantReferences.isEmpty(), DAO_PARAMETER, DOMAIN_PARAMETER);

        mapperClass.addMethod(setMultiRefMethod);
    }

    /**
     * Adds mappings for multi reference to other domain
     *
     * @param mapperClass   class where to add imports
     * @param convertMethod the method where to add body
     * @param reference     reference which is actual to map
     */
    private void addMultiRefConvertToDomain(Clazz mapperClass, Method convertMethod, Reference reference) {
        boolean hasIncludeChildrenParameter = hasIncludeChildrenParameter(reference.getRealTargetEntity(), AccessMapperCreator::isEntityRelevant);

        String mapperName = getMapperName(reference.getRealTargetEntity());
        String mapperMethodName = getConvertMethodName(reference.getRealTargetEntity(), DOMAIN_POSTFIX);
        String getterSubName = getUpperFirst(reference.getReferenceName()) + "s";
        String targetConnectionName = getUpperFirst(reference.getRealTargetEntity().getBaseName());

        mapperClass.addImport(ArrayList.class.getName());
        convertMethod.addLine("dao.get%s().forEach(arg ->", 1, getterSubName);

        if (reference.isOwner() && hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.%s(arg, true, domain, %s)"
                    , 3, mapperName, mapperMethodName, MAPPED_OBJECTS_PARAMETER_TEXT);
        } else if (!reference.isOwner() && hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.%s(arg.get%s(), true, domain, %s)"
                    , 3, mapperName, mapperMethodName, targetConnectionName, MAPPED_OBJECTS_PARAMETER_TEXT);
        } else if (reference.isOwner() && !hasIncludeChildrenParameter) {
            convertMethod.addLine("%s.%s(arg, domain, %s)"
                    , 3, mapperName, mapperMethodName, MAPPED_OBJECTS_PARAMETER_TEXT);
        } else {
            convertMethod.addLine("%s.%s(arg.get%s(), domain, %s)"
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
        return entity.getModels().isDao() && entity.getModels().isDomain();
    }

    /**
     * Checks whether elements are to generate or not
     *
     * @param field Field to check for relevance
     * @return {@code true} if the field is relevant for the mapper
     */
    private static boolean isFieldRelevant(Field field) {
        return field.getModels().isDao() && field.getModels().isDomain();
    }

    /**
     * Determines all references which are need by mapping.
     * In addition the ownership is set to {@code false} if necessary.
     * The aggregation does not takes place since the mapping has to handle these differences
     *
     * @param entity Entity whose References should be determined
     * @return List of direct references and those provided by super class of {@code entity}
     */
    @Override
    protected List<Reference> determineAllReferences(Entity entity) {
        return DaoCreator.getMovedOwnershipReferences(super.determineAllReferences(entity));
    }

    private static class UtilTextContainer {
        private String mapperName = null;
        private String mapperMethodName = null;
        private String getterSubName = null;
        private String connectionTableName = null;
        private String sourceConnectionName = null;
        private String targetConnectionName = null;
    }

    /**
     * Creates the parameter container for dao convert methods
     *
     * @param entity the entity which should be converted
     * @return the parameter container
     */
    private CreateMethodParameterContainer getDaoCreateMethodParameterContainer(Entity entity) {
        return new CreateMethodParameterContainer(entity, DAO_POSTFIX, DOMAIN_POSTFIX, AccessMapperCreator::isEntityRelevant);
    }

    /**
     * Creates the parameter container for domain convert methods
     *
     * @param entity the entity which should be converted
     * @return the parameter container
     */
    private CreateMethodParameterContainer getDomainCreateMethodParameterContainer(Entity entity) {
        return new CreateMethodParameterContainer(entity, DOMAIN_POSTFIX, DAO_POSTFIX, AccessMapperCreator::isEntityRelevant);
    }
}