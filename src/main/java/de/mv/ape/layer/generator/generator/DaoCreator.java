package de.mv.ape.layer.generator.generator;

import de.mv.ape.layer.generator.config.elements.Config;
import de.mv.ape.layer.generator.config.elements.Reference;
import de.mv.ape.layer.generator.sources.*;

import javax.persistence.*;

import lombok.*;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to create sources of data access objects
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DaoCreator extends AbstractCreator {

    public static final String TARGET_ENTITY = "targetEntity";
    public static final String PLACEHOLDER_ID = "\"%sId\"";
    public static final String CLASS_ENDING = ".class";

    public DaoCreator(Config config, Log logger) {
        super(config, logger);
    }

    /**
     * Creates the data access objects
     *
     * @param entity      Entity to generate
     * @param packageName name of the package to use
     * @param packageDir  directory where to write at
     * @return {@code true} if creating was successful. Otherwise {@code false}
     */
    public boolean createDataAccessObject(de.mv.ape.layer.generator.config.elements.Entity entity, String packageName, File packageDir) {
        if (!entity.getModels().isDao()) {
            logger.debug(String.format("Entity %s is not relevant for dao", entity.getBaseName()));
            return true;
        }
        Clazz daoClazz = new Clazz(getPackage(entity, packageName), entity.getBaseName() + "Dao");

        JavaDoc javaDoc = new JavaDoc(String.format("Generated dao class of %s", entity.getBaseName()));
        if (entity.getDescription() != null && !entity.getDescription().trim().isEmpty()) {
            javaDoc.addLine("<br>");
            javaDoc.addLine(entity.getDescription().trim());
        }
        daoClazz.setDescription(javaDoc);

        daoClazz.addImport("javax.persistence.*");
        daoClazz.addImport(Data.class.getName());

        daoClazz.addAnnotation(Data.class);
        daoClazz.addAnnotation(javax.persistence.Entity.class);
        daoClazz.addAnnotation(new Annotation(Table.class, "name", String.format("\"%ss\"", entity.getBaseName())));

        addAttributes(entity, daoClazz);
        addReferences(entity, daoClazz, packageName, packageDir);

        addDaoIdentificationMethods(daoClazz, entity);

        return writeClassFile(getPackageDir(entity, packageDir), daoClazz.getClassName(), daoClazz);
    }

    /**
     * Adds all necessary attributes to the class
     *
     * @param entity   entity whose fields should be added as attribute
     * @param daoClazz Class where to add attributes
     */
    private void addAttributes(de.mv.ape.layer.generator.config.elements.Entity entity, Clazz daoClazz) {
        Attribute idAttribute = new Attribute("id", Long.class.getSimpleName());
        idAttribute.addAnnotation(Id.class);
        idAttribute.addAnnotation(GeneratedValue.class, "strategy", "GenerationType.IDENTITY");
        idAttribute.addAnnotation(Column.class, "name", "\"Id\"");
        daoClazz.addAttribute(idAttribute);

        entity.getFields().stream()
                .filter(f -> f.getModels() == null || f.getModels().isDao())
                .forEach(f -> {
                    if (f.getTypePackage() != null && !f.getTypePackage().isEmpty()) {
                        daoClazz.addImport(String.format("%s.%s", f.getTypePackage(), f.getType()));
                    }
                    daoClazz.addAttribute(createAttribute(f, "Column"));
                });
    }

    /**
     * Adds all necessary references to the class
     *
     * @param entity      entity whose references should be added
     * @param daoClazz    Class where to add attributes
     * @param packageName base package name where other referenced class are found
     * @param packageDir  base package directory where other referenced class sources are found
     */
    private void addReferences(de.mv.ape.layer.generator.config.elements.Entity entity, Clazz daoClazz, String packageName, File packageDir) {

        List<String> attributes = new ArrayList<>();

        boolean isSingle = entity.getParentRefs().size() == 1;
        entity.getParentRefs().stream()
                .filter(Reference::isOwner)
                .forEach(ref -> addParentRef(daoClazz, packageName, ref, isSingle, attributes));
        entity.getReferences().forEach(ref -> addChildRef(daoClazz, packageName, ref, attributes, packageDir));

        if (!attributes.isEmpty()) {
            List<String> excludeAttributes = attributes.stream().map(a -> "\"" + a + "\"").collect(Collectors.toList());

            Annotation toStringAnnotation = new Annotation(ToString.class);
            Annotation equalsAndHashCodeAnnotation = new Annotation(EqualsAndHashCode.class);

            toStringAnnotation.addParameterArray("exclude", excludeAttributes);
            equalsAndHashCodeAnnotation.addParameterArray("exclude", excludeAttributes);

            daoClazz.addImport(EqualsAndHashCode.class.getName());
            daoClazz.addImport(ToString.class.getName());
            daoClazz.addAnnotation(toStringAnnotation);
            daoClazz.addAnnotation(equalsAndHashCodeAnnotation);
        }
    }

    /**
     * Adds getter and setter of an identification which is provided by {@code de.mv.ape.utils.generators.IdGenerator}
     *
     * @param daoClazz class where to add methods
     * @param entity   Entity which is used for generating
     */
    private void addDaoIdentificationMethods(Clazz daoClazz, de.mv.ape.layer.generator.config.elements.Entity entity) {
        if (!config.isUseIdGenerator()) {
            return;
        }
        logger.debug("Identification methods will be created for " + daoClazz.getClassName());

        daoClazz.addImport("de.mv.ape.utils.generators.IdGenerator");
        daoClazz.addImport(config.getBasePackage() + "." + config.getDomainPackage() + "." + entity.getBaseName());

        Method getIdentificationMethod = new Method("getIdentification");
        getIdentificationMethod.setQualifier("public");
        getIdentificationMethod.setMethodType("String");
        getIdentificationMethod.addLine(String.format("return IdGenerator.generateIdentification(id, %s.ID_PREFIX);", entity.getBaseName()));
        daoClazz.addMethod(getIdentificationMethod);

        Method setIdentificationMethod = new Method("setIdentification");
        setIdentificationMethod.setQualifier("public");
        setIdentificationMethod.addParameter("String", "identification");
        setIdentificationMethod.addLine(String.format("id = IdGenerator.generateId(identification, %s.ID_PREFIX);", entity.getBaseName()));
        daoClazz.addMethod(setIdentificationMethod);
    }

    /**
     * Adds a field of reference to the parent entity
     *
     * @param daoClazz       clazz where to add attribute
     * @param reference      reference which should be add
     * @param isSingle       {@code true} if its unique reference
     * @param attributeNames List where to add the name of created attribute
     */
    private void addParentRef(Clazz daoClazz, String basePackage, Reference reference, boolean isSingle, List<String> attributeNames) {
        String parentClassName = reference.getTargetEntity() + "Dao";
        String refTypeName = reference.isList() ? ManyToOne.class.getSimpleName() : OneToOne.class.getSimpleName();

        Attribute parent = new Attribute("parent" + reference.getTargetEntity(), parentClassName);
        parent.addAnnotation(refTypeName, TARGET_ENTITY, String.format("%s.class", parentClassName));
        Annotation joinColAnnotation = new Annotation(JoinColumn.class);
        joinColAnnotation.addParameter("name", String.format(PLACEHOLDER_ID, reference.getTargetEntity()));
        if (isSingle) {
            joinColAnnotation.addParameter("nullable", "false");
        }
        parent.addAnnotation(joinColAnnotation);

        attributeNames.add(parent.getAttributeName());

        daoClazz.addImport(getPackageAndClass(reference, basePackage, "Dao"));

        daoClazz.addAttribute(parent);
    }

    /**
     * Adds a field or list of reference to the child entity
     *
     * @param daoClazz       clazz where to add attribute
     * @param reference      reference which should be add
     * @param attributeNames list of attributes which are used as reference property at class
     * @param packageDir     base package directory where other referenced class sources are found
     */
    private void addChildRef(Clazz daoClazz, String packageName, Reference reference, List<String> attributeNames, File packageDir) {
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
     * @param reference      reference which should be add
     * @param attributeNames list of attributes which are used as reference property at class
     */
    private void addDirectChildRef(Clazz daoClazz, String packageName, Reference reference, List<String> attributeNames) {
        String childClassName = reference.getTargetEntity() + "Dao";
        String propertyBaseName = getLowerFirst(reference.getReferenceName());
        String refTypeName = reference.isList() ? OneToMany.class.getSimpleName() : OneToOne.class.getSimpleName();
        Attribute child;

        if (reference.isList()) {
            daoClazz.addImport(Collection.class.getName());
            child = new Attribute(propertyBaseName + "s", String.format("%s<%s>", Collection.class.getSimpleName(), childClassName));
        } else {
            child = new Attribute(propertyBaseName, childClassName);
        }

        Annotation refAnnotation = new Annotation(refTypeName);
        refAnnotation.addParameter(TARGET_ENTITY, childClassName + CLASS_ENDING);
        refAnnotation.addParameter("mappedBy", String.format("\"parent%s\"", reference.getParent().getBaseName()));

        child.addAnnotation(refAnnotation);
        daoClazz.addAttribute(child);
        daoClazz.addImport(getPackageAndClass(reference, packageName, "Dao"));

        attributeNames.add(child.getAttributeName());
    }

    /**
     * Adds a field or list of reference to the child entity, but only if the source of reference is not the owner
     *
     * @param daoClazz       clazz where to add attribute
     * @param reference      reference which should be add
     * @param attributeNames list of attributes which are used as reference property at class
     * @param packageDir     base package directory where other referenced class sources are found.
     *                       Is used to generate a connection table
     */
    private void addIndirectChildRef(Clazz daoClazz, String packageName, Reference reference, List<String> attributeNames, File packageDir) {
        String childClassName;
        String propertyBaseName = getLowerFirst(reference.getReferenceName());
        Attribute child;

        if (reference.isList()) {
            childClassName = getConnectionTableName(reference);
            daoClazz.addImport(Collection.class.getName());

            child = new Attribute(propertyBaseName + "s", String.format("%s<%s>", Collection.class.getSimpleName(), childClassName));

            Annotation refAnnotation = new Annotation("OneToMany");
            refAnnotation.addParameter(TARGET_ENTITY, childClassName + CLASS_ENDING);
            refAnnotation.addParameter("mappedBy", String.format("\"%s\"", getLowerFirst(reference.getTargetEntity())));
            child.addAnnotation(refAnnotation);

            Clazz connectionTableClazz = createConnectionTable(daoClazz.getPackageName(), packageName
                    , reference, getPackageDir(reference.getParent(), packageDir));
            daoClazz.addImport(String.format("%s.%s", connectionTableClazz.getPackageName(), connectionTableClazz.getClassName()));
        } else {
            childClassName = String.format("%sDao", reference.getTargetEntity());

            child = new Attribute(propertyBaseName, childClassName);
            child.addAnnotation(ManyToOne.class, TARGET_ENTITY, childClassName + CLASS_ENDING);
            child.addAnnotation(JoinColumn.class, "name", String.format(PLACEHOLDER_ID, propertyBaseName));
            daoClazz.addImport(getPackageAndClass(reference, packageName, "Dao"));
        }

        daoClazz.addAttribute(child);

        attributeNames.add(child.getAttributeName());
    }

    private String getConnectionTableName(Reference reference) {
        return String.format("%sTo%sDao", reference.getParent().getBaseName(), reference.getTargetEntity());
    }

    /**
     * Creates a connection table for reference where the source entity is not the owner
     *
     * @param packageName package name to use
     * @param reference   reference which should be presented by the connection table
     * @param packageDir  directory of the package sources
     */
    private Clazz createConnectionTable(String packageName, String basePackageName, Reference reference, File packageDir) {
        String clazzName = getConnectionTableName(reference);
        String baseClassName = clazzName.substring(0, clazzName.length() - 3);
        Clazz connectionClazz = new Clazz(packageName, clazzName);

        connectionClazz.addImport(AllArgsConstructor.class.getName());
        connectionClazz.addImport(Data.class.getName());
        connectionClazz.addImport(NoArgsConstructor.class.getName());
        connectionClazz.addImport("javax.persistence.*");
        connectionClazz.addImport("java.io.Serializable");
        connectionClazz.addImport(getPackageAndClass(reference, basePackageName, "Dao"));
        connectionClazz.addImport(getPackageAndClass(reference.getParent(), basePackageName, "Dao"));

        connectionClazz.addAnnotation("Entity");
        connectionClazz.addAnnotation(AllArgsConstructor.class.getSimpleName());
        connectionClazz.addAnnotation(Data.class.getSimpleName());
        connectionClazz.addAnnotation(NoArgsConstructor.class.getSimpleName());
        connectionClazz.addAnnotation(new Annotation(Table.class, "name", String.format("\"%ss\"", baseClassName)));
        connectionClazz.addAnnotation(new Annotation(IdClass.class, null, String.format("%s.%sId.class", clazzName, baseClassName)));
        connectionClazz.addAnnotation(new Annotation(SuppressWarnings.class.getSimpleName(), null, "\"java:S1948\""));

        Attribute sourceAttribute = new Attribute(getLowerFirst(reference.getParent().getBaseName()), reference.getParent().getBaseName() + "Dao");
        sourceAttribute.addAnnotation(ManyToOne.class, TARGET_ENTITY, reference.getParent().getBaseName() + "Dao.class");
        sourceAttribute.addAnnotation(JoinColumn.class, "name", String.format(PLACEHOLDER_ID, reference.getParent().getBaseName()));
        sourceAttribute.addAnnotation(Id.class);
        connectionClazz.addAttribute(sourceAttribute);

        Attribute targetAttribute = new Attribute(getLowerFirst(reference.getTargetEntity()), reference.getTargetEntity() + "Dao");
        targetAttribute.addAnnotation(OneToOne.class, TARGET_ENTITY, reference.getTargetEntity() + "Dao.class");
        targetAttribute.addAnnotation(JoinColumn.class, "name", String.format(PLACEHOLDER_ID, reference.getTargetEntity()));
        targetAttribute.addAnnotation(Id.class);
        connectionClazz.addAttribute(targetAttribute);

        connectionClazz.addInnerClazz(getInnerIdClass(baseClassName + "Id"
                , getLowerFirst(reference.getParent().getBaseName()) + "Id"
                , getLowerFirst(reference.getTargetEntity()) + "Id"));

        writeClassFile(packageDir, clazzName, connectionClazz);

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
}
