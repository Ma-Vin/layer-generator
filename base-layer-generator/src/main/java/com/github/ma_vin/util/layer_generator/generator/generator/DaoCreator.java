package com.github.ma_vin.util.layer_generator.generator.generator;

import com.github.ma_vin.util.layer_generator.config.elements.Config;
import com.github.ma_vin.util.layer_generator.config.elements.Models;
import com.github.ma_vin.util.layer_generator.sources.*;
import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.config.elements.Entity;
import com.github.ma_vin.util.layer_generator.config.elements.Index;
import com.github.ma_vin.util.layer_generator.config.elements.fields.Field;
import com.github.ma_vin.util.layer_generator.config.elements.references.Reference;
import com.github.ma_vin.util.layer_generator.logging.ILogWrapper;
import com.github.ma_vin.util.layer_generator.exceptions.exceptions.NotSupportedMethodException;

import jakarta.persistence.*;

import lombok.*;

import java.io.File;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class to create sources of data access objects
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DaoCreator extends AbstractObjectCreator {

    public static final String TARGET_ENTITY = "targetEntity";
    public static final String PLACEHOLDER_ID = "\"%sId\"";
    public static final String CLASS_ENDING = ".class";
    public static final String DAO_INTERFACE = "IIdentifiableDao";
    public static final String DAO_POSTFIX = "Dao";

    public DaoCreator(Config config, ILogWrapper logger) {
        super(config, logger);
    }


    public boolean createDataAccessObjectInterface(String basePackageName, Optional<File> basePackageDir) {
        Interface daoInterface = new Interface(basePackageName, DAO_INTERFACE);

        daoInterface.addMethodDeclarationWithDescription("Long", "getId", "@return the id of the dao");
        daoInterface.addMethodDeclarationWithDescription("void", "setId", "@param id the id of the dao", "Long", "id");
        if (config.isUseIdGenerator()) {
            daoInterface.addMethodDeclarationWithDescription(String.class.getSimpleName(), "getIdentification"
                    , "@return the calculated identification from id of the dao");
            daoInterface.addMethodDeclarationWithDescription("void", "setIdentification"
                    , "@param identification the identification where to determine the id from", String.class.getSimpleName(), "identification");
        }

        return writeClassFile(basePackageDir, daoInterface);
    }

    /**
     * Creates the data access objects
     *
     * @param entity      Entity to generate
     * @param packageName name of the package to use
     * @param packageDir  directory where to write at
     * @return {@code true} if creating was successful. Otherwise {@code false}
     */
    public boolean createDataAccessObject(Entity entity, String packageName, Optional<File> packageDir) {
        if (!entity.getModels().isDao()) {
            logger.debug(String.format("Entity %s is not relevant for dao", entity.getBaseName()));
            return true;
        }
        Clazz daoClazz = new Clazz(getPackage(entity, packageName), entity.getBaseName() + DAO_POSTFIX);
        if (entity.hasNoParent()) {
            daoClazz.addInterface(DAO_INTERFACE);
            daoClazz.addImport(String.format("%s.%s", packageName, DAO_INTERFACE));
        }
        checkAndAddParent(daoClazz, entity, packageName, DAO_POSTFIX);

        JavaDoc javaDoc = new JavaDoc(String.format("Generated dao class of %s", entity.getBaseName()));
        if (entity.getDescription() != null && !entity.getDescription().trim().isEmpty()) {
            javaDoc.addLine("<br>");
            javaDoc.addLine(entity.getDescription().trim());
        }
        daoClazz.setDescription(javaDoc);

        daoClazz.addImport("jakarta.persistence.*");
        daoClazz.addImport(Data.class.getName());

        daoClazz.addAnnotation(Data.class);
        if (Boolean.TRUE.equals(entity.getIsAbstract())) {
            daoClazz.addAnnotation(MappedSuperclass.class);
        } else {
            daoClazz.addImport(BaseDao.class.getName());
            daoClazz.addAnnotation(new Annotation(BaseDao.class, null, "\"" + packageName + "\""));
            daoClazz.addAnnotation(jakarta.persistence.Entity.class);
            Annotation tableAnnotation = new Annotation(Table.class, "name", String.format("\"%ss\"", entity.getTableName()));
            addIndices(entity, tableAnnotation);
            daoClazz.addAnnotation(tableAnnotation);

        }

        addAttributes(entity, daoClazz, Models.DAO);
        addReferences(entity, daoClazz, packageName, packageDir);

        addDaoIdentificationMethods(daoClazz, entity);

        return writeClassFile(getPackageDir(entity, packageDir), daoClazz);
    }

    /**
     * Adds Indices to the {@link Table} annotation
     *
     * @param entity          entity which is owner of the indices
     * @param tableAnnotation annotation of the table
     */
    private void addIndices(Entity entity, Annotation tableAnnotation) {
        if (entity.getIndices().isEmpty()) {
            return;
        }
        List<String> indexAnnotations = new ArrayList<>();
        for (Index i : entity.getIndices()) {
            Annotation indexAnnotation = new Annotation(jakarta.persistence.Index.class, "name", "\"" + i.getIndexName() + "\"");
            indexAnnotation.addParameter("columnList", "\"" + getIndexColumnList(i) + "\"");
            if (Boolean.TRUE.equals(i.getIsUnique())) {
                indexAnnotation.addParameter("unique", "true");
            }
            indexAnnotations.addAll(indexAnnotation.generate());
        }
        tableAnnotation.addParameterArray("indexes", indexAnnotations);
    }

    /**
     * Creates the columnList value for an index
     *
     * @param index index whose list is asked for
     * @return the columnList
     */
    private String getIndexColumnList(Index index) {
        StringBuilder columnList = new StringBuilder();
        index.getFields().forEach(fs -> {
            if (fs.getField().getDaoInfo() != null && fs.getField().getDaoInfo().getColumnName() != null) {
                columnList.append(fs.getField().getDaoInfo().getColumnName());
            } else {
                columnList.append(fs.getField().getFieldName());
            }
            if (!fs.isAscending()) {
                columnList.append(" DESC");
            }
            columnList.append(", ");
        });
        return columnList.substring(0, columnList.length() - 2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addAttributes(Entity entity, Clazz daoClazz, Models actualModel) {
        if (entity.hasNoParent()) {
            Attribute idAttribute = new Attribute("id", Long.class.getSimpleName());
            idAttribute.addAnnotation(Id.class);
            idAttribute.addAnnotation(GeneratedValue.class, "strategy", "GenerationType.IDENTITY");
            idAttribute.addAnnotation(Column.class, "name", "\"Id\"");
            if (Boolean.TRUE.equals(entity.getIsAbstract())) {
                idAttribute.setQualifier(Qualifier.PROTECTED);
            }
            daoClazz.addAttribute(idAttribute);
        }

        addAttributes(entity, daoClazz, actualModel, "Column");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Attribute createAttribute(Field field, String... annotations) {
        Attribute attribute = super.createAttribute(field, annotations);
        if (Boolean.TRUE.equals(field.getIsTypeEnum()) && (field.getDaoInfo() == null || Boolean.TRUE.equals(field.getDaoInfo().getUseEnumText()))) {
            attribute.addAnnotation("Enumerated", null, "EnumType.STRING");
        }
        Annotation columnAnnotation = getOrCreateAndAddAnnotation(attribute, Column.class.getSimpleName());
        if (isFieldNotNullable(field)) {
            columnAnnotation.addParameter("nullable", "false");
        }
        if (field.getDaoInfo() != null) {
            addParamIfExists(columnAnnotation, "name", field.getDaoInfo().getColumnName());
            addParamIfExists(columnAnnotation, "length", field.getDaoInfo().getLength());
            addParamIfExists(columnAnnotation, "precision", field.getDaoInfo().getPrecision());
            addParamIfExists(columnAnnotation, "scale", field.getDaoInfo().getScale());
            addParamIfExists(columnAnnotation, "columnDefinition", field.getDaoInfo().getColumnDefinition());
            if ((Boolean.TRUE.equals(field.getDaoInfo().getIsLobType()))) {
                attribute.addAnnotation(Lob.class);
            }
        }
        return attribute;
    }

    private void addParamIfExists(Annotation annotation, String annotationPropertyName, String annotationPropertyValue) {
        if (annotationPropertyValue != null && !annotationPropertyValue.trim().isEmpty()) {
            annotation.addParameter(annotationPropertyName, "\"" + annotationPropertyValue + "\"");
        }
    }

    private void addParamIfExists(Annotation annotation, String annotationPropertyName, Integer annotationPropertyValue) {
        if (annotationPropertyValue != null) {
            annotation.addParameter(annotationPropertyName, annotationPropertyValue.toString());
        }
    }

    /**
     * Checks whether a field is nullable or not
     *
     * @param field field to check
     * @return {@code true} if the field is not nullable
     */
    private boolean isFieldNotNullable(Field field) {
        return (field.getIsTypeEnum() && field.getParentEntity().getReferences().stream().anyMatch(ref -> field.equals(ref.getRealFilterField()))
                || (field.getDaoInfo() != null && Boolean.FALSE.equals(field.getDaoInfo().getNullable())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addReferences(Entity entity, Clazz clazz, String packageName, ModelValidator modelValidator) {
        throw new NotSupportedMethodException("Method addReferences(Entity entity, Clazz clazz, String packageName) should not be used in context data access object");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addReference(Clazz clazz, String packageName, Reference reference, List<String> attributeNames) {
        throw new NotSupportedMethodException("Method addReferenceClazz clazz, String packageName, Reference reference, List<String> attributeNames) should not be used in context data access object");
    }

    /**
     * Adds all necessary references to the class
     *
     * @param entity      entity whose references should be added
     * @param daoClazz    Class where to add attributes
     * @param packageName base package name where other referenced class are found
     * @param packageDir  base package directory where other referenced class sources are found
     */
    private void addReferences(Entity entity, Clazz daoClazz, String packageName, Optional<File> packageDir) {

        List<String> attributes = new ArrayList<>();

        List<Reference> treatedParentReferences = getTreatedReferences(entity.getNonVersionedParentRefs());
        boolean isSingle = treatedParentReferences.stream().filter(Reference::getIsOwner).count() == 1;
        treatedParentReferences.stream()
                .filter(Reference::getIsOwner)
                .filter(ref -> ref.getRealTargetEntity().getModels().isDao())
                .forEach(ref -> addParentRef(daoClazz, packageName, ref, isSingle, attributes));
        getTreatedReferences(entity.getReferences()).stream()
                .filter(ref -> ref.getRealTargetEntity().getModels().isDao())
                .forEach(ref -> addChildRef(daoClazz, packageName, ref, attributes, packageDir));

        addExcludeAttributes(daoClazz, attributes);
    }

    /**
     * Returns an aggregated list of references. Multi references to the same target will be reduced to a single one.
     * Single reference will lose their ownership.
     * The model should provide some filter {@link Field} to be able to distinguish them at mapper
     *
     * @param references The list to treated
     * @return the treated list of references
     */
    public static List<Reference> getTreatedReferences(List<Reference> references) {
        List<Reference> tempList = getAggregatedReferences(references);
        return getMovedOwnershipReferences(tempList);
    }

    /**
     * Sets the ownership at single reference to {@link Boolean#FALSE} if there exists some other reference to the same target
     *
     * @param references The list to check ownership
     * @return List of modified references
     */
    public static List<Reference> getMovedOwnershipReferences(List<Reference> references) {
        Set<Reference> referencesToModify = new HashSet<>();
        references.stream().filter(ref ->
                references.stream().anyMatch(ref2 -> ref.isOwner() && !ref.equals(ref2) && !ref.isList() && ref.getTargetEntity().equals(ref2.getTargetEntity()))
        ).forEach(referencesToModify::add);

        List<Reference> result = references.stream().filter(ref -> !referencesToModify.contains(ref)).collect(Collectors.toList());

        referencesToModify.forEach(ref -> {
            Reference modifiedReference = ref.copy();
            modifiedReference.setIsOwner(false);
            result.add(modifiedReference);
        });

        return result;
    }

    /**
     * Returns an aggregated list of references. Multi references to the same target will be reduced to a single one.
     * The model should provide some filter {@link Field} to be able to distinguish them at mapper
     *
     * @param references The list to aggregate
     * @return the aggregated list of references
     */
    public static List<Reference> getAggregatedReferences(List<Reference> references) {
        Map<AggregationKey, List<Reference>> sameTargetReferenceMap = new HashMap<>();

        references.forEach(ref -> {
            if (isToAggregate(ref, references)) {
                AggregationKey key = new AggregationKey(ref);
                if (!sameTargetReferenceMap.containsKey(key)) {
                    sameTargetReferenceMap.put(key, new ArrayList<>());
                }
                sameTargetReferenceMap.get(key).add(ref);
            }
        });

        List<Reference> result = references.stream().filter(ref -> !ref.isList() || !sameTargetReferenceMap.containsKey(new AggregationKey(ref))).collect(Collectors.toList());

        sameTargetReferenceMap.forEach((key, refList) -> {
            Reference aggReference = refList.get(0).copy();
            aggReference.setReferenceName("agg" + getUpperFirst(aggReference.isReverse() ? aggReference.getParent().getBaseName() : aggReference.getTargetEntity()));
            aggReference.setAggregated(true);
            result.add(aggReference);
        });

        return result;
    }

    /**
     * Checks whether a reference need to be aggregate or not
     *
     * @param reference     reference to check
     * @param allReferences list of available references
     * @return {@code true} if the reference need to be aggregated
     */
    public static boolean isToAggregate(Reference reference, List<Reference> allReferences) {
        return allReferences.stream().anyMatch(ref2 -> !reference.equals(ref2) && reference.isList() && ref2.isList() && AggregationKey.isEqualKey(reference, ref2));
    }

    /**
     * Adds getter and setter of an identification which is provided by {@code de.ma_vin.ape.utils.generators.IdGenerator}
     *
     * @param daoClazz class where to add methods
     * @param entity   Entity which is used for generating
     */
    private void addDaoIdentificationMethods(Clazz daoClazz, Entity entity) {
        if (!config.isUseIdGenerator() || Boolean.TRUE.equals(entity.getIsAbstract())) {
            return;
        }
        logger.debug("Identification methods will be created for " + daoClazz.getClassName());

        daoClazz.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, config.getIdGeneratorPackage(), config.getIdGeneratorClass()));
        if (entity.getModels().isDomain()) {
            daoClazz.addImport(getPackageAndClass(entity, config.getBasePackage() + "." + config.getDomainPackage(), ""));
        }

        Method getIdentificationMethod = new Method("getIdentification");
        getIdentificationMethod.addAnnotation(Override.class.getSimpleName());
        getIdentificationMethod.setQualifier(Qualifier.PUBLIC);
        getIdentificationMethod.setMethodType(String.class.getSimpleName());
        if (entity.getModels().isDomain()) {
            getIdentificationMethod.addLine("return %s.generateIdentification(id, %s.ID_PREFIX);", config.getIdGeneratorClass(), entity.getBaseName());
        } else {
            getIdentificationMethod.addLine("return %s.generateIdentification(id, \"\");", config.getIdGeneratorClass());
        }
        daoClazz.addMethod(getIdentificationMethod);

        Method setIdentificationMethod = new Method("setIdentification");
        setIdentificationMethod.addAnnotation(Override.class.getSimpleName());
        setIdentificationMethod.setQualifier(Qualifier.PUBLIC);
        setIdentificationMethod.addParameter(String.class.getSimpleName(), "identification");
        if (entity.getModels().isDomain()) {
            setIdentificationMethod.addLine("id = %s.generateId(identification, %s.ID_PREFIX);", config.getIdGeneratorClass(), entity.getBaseName());
        } else {
            setIdentificationMethod.addLine("id = %s.generateId(identification, \"\");", config.getIdGeneratorClass());
        }
        daoClazz.addMethod(setIdentificationMethod);
    }

    /**
     * Adds a field of reference to the parent entity
     *
     * @param daoClazz       clazz where to add attribute
     * @param basePackage    base package name
     * @param reference      reference which should be add
     * @param isSingle       {@code true} if its unique reference
     * @param attributeNames List where to add the name of created attribute
     */
    private void addParentRef(Clazz daoClazz, String basePackage, Reference reference, boolean isSingle, List<String> attributeNames) {
        String parentClassName = reference.getTargetEntity() + DAO_POSTFIX;
        String refTypeName = reference.isList() ? ManyToOne.class.getSimpleName() : OneToOne.class.getSimpleName();

        Attribute parent = new Attribute("parent" + reference.getTargetEntity(), parentClassName);
        parent.addAnnotation(refTypeName, TARGET_ENTITY, String.format("%s.class", parentClassName));
        Annotation joinColAnnotation = new Annotation(JoinColumn.class);
        joinColAnnotation.addParameter("name", String.format("\"Parent%sId\"", getUpperFirst(reference.getTargetEntity())));
        if (isSingle) {
            joinColAnnotation.addParameter("nullable", "false");
        }
        parent.addAnnotation(joinColAnnotation);

        attributeNames.add(parent.getAttributeName());

        daoClazz.addImport(getPackageAndClass(reference, basePackage, DAO_POSTFIX));

        daoClazz.addAttribute(parent);
    }

    /**
     * Adds a field or list of reference to the child entity
     *
     * @param daoClazz       clazz where to add attribute
     * @param packageName    base package name
     * @param reference      reference which should be add
     * @param attributeNames list of attributes which are used as reference property at class
     * @param packageDir     base package directory where other referenced class sources are found
     */
    private void addChildRef(Clazz daoClazz, String packageName, Reference reference, List<String> attributeNames, Optional<File> packageDir) {
        if (reference.isOwner()) {
            addDirectChildRef(daoClazz, packageName, reference, attributeNames);
        } else {
            addIndirectChildRef(daoClazz, packageName, reference, attributeNames, packageDir);
        }
    }

    /**
     * Adds a field or list of reference to the child entity, but only if the source of reference is also the owner
     *
     * @param daoClazz       clazz where to add attribute
     * @param packageName    base package name
     * @param reference      reference which should be add
     * @param attributeNames list of attributes which are used as reference property at class
     */
    private void addDirectChildRef(Clazz daoClazz, String packageName, Reference reference, List<String> attributeNames) {
        String childClassName = reference.getTargetEntity() + DAO_POSTFIX;
        String propertyBaseName = getLowerFirst(reference.getReferenceName());
        String refTypeName = reference.isList() ? OneToMany.class.getSimpleName() : OneToOne.class.getSimpleName();
        Attribute child;

        if (reference.isList()) {
            daoClazz.addImport(Collection.class.getName());
            child = new Attribute(propertyBaseName, String.format("%s<%s>", Collection.class.getSimpleName(), childClassName));
        } else {
            child = new Attribute(propertyBaseName, childClassName);
        }
        if (reference.getShortDescription() != null) {
            child.setJavaDoc(new JavaDoc(reference.getShortDescription()));
        }

        Annotation refAnnotation = new Annotation(refTypeName);
        refAnnotation.addParameter(TARGET_ENTITY, childClassName + CLASS_ENDING);
        refAnnotation.addParameter("mappedBy", String.format("\"parent%s\"", reference.getParent().getBaseName()));

        child.addAnnotation(refAnnotation);
        daoClazz.addAttribute(child);
        daoClazz.addImport(getPackageAndClass(reference, packageName, DAO_POSTFIX));

        attributeNames.add(child.getAttributeName());
    }

    /**
     * Adds a field or list of reference to the child entity, but only if the source of reference is not the owner
     *
     * @param daoClazz       clazz where to add attribute
     * @param packageName    base package name
     * @param reference      reference which should be add
     * @param attributeNames list of attributes which are used as reference property at class
     * @param packageDir     base package directory where other referenced class sources are found.
     *                       Is used to generate a connection table
     */
    private void addIndirectChildRef(Clazz daoClazz, String packageName, Reference reference, List<String> attributeNames, Optional<File> packageDir) {
        String childClassName;
        String propertyBaseName = getLowerFirst(reference.getReferenceName());
        Attribute child;

        if (reference.isList()) {
            childClassName = getConnectionTableName(reference);
            daoClazz.addImport(Collection.class.getName());

            child = new Attribute(propertyBaseName, String.format("%s<%s>", Collection.class.getSimpleName(), childClassName));

            Annotation refAnnotation = new Annotation(OneToMany.class.getSimpleName());
            refAnnotation.addParameter(TARGET_ENTITY, childClassName + CLASS_ENDING);
            refAnnotation.addParameter("mappedBy", String.format("\"%s\"", getLowerFirst(reference.getParent().getBaseName())));
            child.addAnnotation(refAnnotation);

            Clazz connectionTableClazz = createConnectionTable(daoClazz.getPackageName(), packageName
                    , reference, getPackageDir(reference.getParent(), packageDir));
            daoClazz.addImport(String.format("%s.%s", connectionTableClazz.getPackageName(), connectionTableClazz.getClassName()));
        } else {
            childClassName = String.format("%sDao", reference.getTargetEntity());

            child = new Attribute(propertyBaseName, childClassName);
            child.addAnnotation(ManyToOne.class, TARGET_ENTITY, childClassName + CLASS_ENDING);
            child.addAnnotation(JoinColumn.class, "name", String.format(PLACEHOLDER_ID, propertyBaseName));
            daoClazz.addImport(getPackageAndClass(reference, packageName, DAO_POSTFIX));
        }
        if (reference.getShortDescription() != null) {
            child.setJavaDoc(new JavaDoc(reference.getShortDescription()));
        }
        daoClazz.addAttribute(child);

        attributeNames.add(child.getAttributeName());
    }

    public static String getConnectionTableName(Reference reference) {
        return String.format("%sTo%sDao", reference.getParent().getBaseName(), reference.getTargetEntity());
    }

    public static String getConnectionTableNameParentRef(Reference parentReference) {
        return String.format("%sTo%sDao", parentReference.getTargetEntity(), parentReference.getParent().getBaseName());
    }

    /**
     * Creates a connection table for reference where the source entity is not the owner
     *
     * @param packageName     package name to use
     * @param basePackageName base package name
     * @param reference       reference which should be presented by the connection table
     * @param packageDir      directory of the package sources
     */
    private Clazz createConnectionTable(String packageName, String basePackageName, Reference reference, Optional<File> packageDir) {
        String clazzName = getConnectionTableName(reference);
        String baseClassName = clazzName.substring(0, clazzName.length() - 3);
        String sourcePropertyName = getLowerFirst(reference.getParent().getBaseName());
        String targetPropertyName = getLowerFirst(reference.getTargetEntity());
        if (sourcePropertyName.equals(targetPropertyName)) {
            targetPropertyName = "sub" + getUpperFirst(targetPropertyName);
        }
        String sourceJoinColumnName = String.format(PLACEHOLDER_ID, getUpperFirst(sourcePropertyName));
        String targetJoinColumnName = String.format(PLACEHOLDER_ID, getUpperFirst(targetPropertyName));

        Clazz connectionClazz = new Clazz(packageName, clazzName);

        connectionClazz.addImport(BaseDao.class.getName());
        connectionClazz.addImport(AllArgsConstructor.class.getName());
        connectionClazz.addImport(Data.class.getName());
        connectionClazz.addImport(NoArgsConstructor.class.getName());
        connectionClazz.addImport("jakarta.persistence.*");
        connectionClazz.addImport("java.io.Serializable");
        connectionClazz.addImport(getPackageAndClass(reference, basePackageName, DAO_POSTFIX));
        connectionClazz.addImport(getPackageAndClass(reference.getParent(), basePackageName, DAO_POSTFIX));

        connectionClazz.addAnnotation(new Annotation(BaseDao.class, null, "\"" + basePackageName + "\""));
        connectionClazz.addAnnotation(jakarta.persistence.Entity.class.getSimpleName());
        connectionClazz.addAnnotation(AllArgsConstructor.class.getSimpleName());
        connectionClazz.addAnnotation(Data.class.getSimpleName());
        connectionClazz.addAnnotation(NoArgsConstructor.class.getSimpleName());
        connectionClazz.addAnnotation(new Annotation(Table.class, "name", String.format("\"%ss\"", baseClassName)));
        connectionClazz.addAnnotation(new Annotation(IdClass.class, null, String.format("%s.%sId.class", clazzName, baseClassName)));
        connectionClazz.addAnnotation(new Annotation(SuppressWarnings.class.getSimpleName(), null, "\"java:S1948\""));

        Attribute sourceAttribute = new Attribute(sourcePropertyName, reference.getParent().getBaseName() + DAO_POSTFIX);
        sourceAttribute.addAnnotation(ManyToOne.class, TARGET_ENTITY, reference.getParent().getBaseName() + "Dao.class");
        sourceAttribute.addAnnotation(JoinColumn.class, "name", sourceJoinColumnName);
        sourceAttribute.addAnnotation(Id.class);
        connectionClazz.addAttribute(sourceAttribute);

        Attribute targetAttribute = new Attribute(targetPropertyName, reference.getTargetEntity() + DAO_POSTFIX);
        targetAttribute.addAnnotation(OneToOne.class, TARGET_ENTITY, reference.getTargetEntity() + DAO_POSTFIX + CLASS_ENDING);
        targetAttribute.addAnnotation(JoinColumn.class, "name", targetJoinColumnName);
        targetAttribute.addAnnotation(Id.class);
        connectionClazz.addAttribute(targetAttribute);

        if (reference.isConnectionFiltering()) {
            Attribute filterAttribute = new Attribute(reference.getNonOwnerFilterField().getFilterFieldName(), reference.getNonOwnerFilterField().getFilterFieldType());
            connectionClazz.addImport(String.format(PACKAGE_AND_CLASS_NAME_FORMAT, reference.getNonOwnerFilterField().getFilterFieldPackage()
                    , reference.getNonOwnerFilterField().getFilterFieldType()));
            connectionClazz.addAttribute(filterAttribute);
        }

        connectionClazz.addInnerClazz(getInnerIdClass(baseClassName + "Id", sourcePropertyName, targetPropertyName));

        writeClassFile(packageDir, connectionClazz);

        return connectionClazz;
    }

    /**
     * Creates an inner class which represent the ID of a connection table
     *
     * @param className    Name of the id class
     * @param sourceIdName name of id attribute which points to the source
     * @param targetIdName name of id attribute which points to the target
     * @return created inner class
     */
    private Clazz getInnerIdClass(String className, String sourceIdName, String targetIdName) {
        Clazz innerIdClazz = new Clazz(className);
        innerIdClazz.setStatic(true);
        innerIdClazz.addInterface(Serializable.class.getSimpleName());

        innerIdClazz.addAnnotation(AllArgsConstructor.class.getSimpleName());
        innerIdClazz.addAnnotation(Data.class.getSimpleName());
        innerIdClazz.addAnnotation(NoArgsConstructor.class.getSimpleName());
        innerIdClazz.addAnnotation(new Annotation(SuppressWarnings.class.getSimpleName(), null, "\"java:S1068\""));

        innerIdClazz.addAttribute(new Attribute(sourceIdName, Long.class.getSimpleName()));
        innerIdClazz.addAttribute(new Attribute(targetIdName, Long.class.getSimpleName()));

        return innerIdClazz;
    }

    @Data
    private static class AggregationKey {
        String targetName;
        boolean isOwner;

        AggregationKey(Reference reference) {
            targetName = reference.getTargetEntity();
            isOwner = reference.isOwner();
        }

        private static boolean isEqualKey(Reference ref1, Reference ref2) {
            return ref1.getTargetEntity().equals(ref2.getTargetEntity())
                    && ref1.isOwner() == ref2.isOwner();
        }
    }
}
