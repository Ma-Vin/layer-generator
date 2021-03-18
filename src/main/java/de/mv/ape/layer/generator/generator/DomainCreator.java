package de.mv.ape.layer.generator.generator;

import de.mv.ape.layer.generator.config.elements.Config;
import de.mv.ape.layer.generator.config.elements.Entity;
import de.mv.ape.layer.generator.config.elements.Reference;
import de.mv.ape.layer.generator.sources.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class to create sources of domain objects
 */
@Data
public class DomainCreator extends AbstractCreator {

    public DomainCreator(Config config, Log logger) {
        super(config, logger);
    }

    /**
     * Creates the domain objects
     *
     * @param entity      Entity to generate
     * @param packageName name of the package to use
     * @param packageDir  directory where to write at
     * @return {@code true} if creating was successful. Otherwise {@code false}
     */
    public boolean createDomainObject(Entity entity, String packageName, File packageDir) {
        if (!entity.getModels().isDomain()) {
            logger.debug(String.format("Entity %s is not relevant for domain", entity.getBaseName()));
            return true;
        }

        Clazz domainClazz = new Clazz(getPackage(entity, packageName), entity.getBaseName());

        JavaDoc javaDoc = new JavaDoc(String.format("Generated domain class of %s", entity.getBaseName()));
        if (entity.getDescription() != null && !entity.getDescription().trim().isEmpty()) {
            javaDoc.addLine("<br>");
            javaDoc.addLine(entity.getDescription().trim());
        }
        domainClazz.setDescription(javaDoc);

        domainClazz.addImport(Data.class.getName());
        domainClazz.addImport(NoArgsConstructor.class.getName());

        domainClazz.addAnnotation(Data.class);
        domainClazz.addAnnotation(NoArgsConstructor.class);
        domainClazz.addAnnotation(new Annotation("SuppressWarnings", null, "\"java:S1068\""));

        addIdentificationAttribute(domainClazz, entity);
        addAttributes(entity, domainClazz);
        addReferences(entity, domainClazz, packageName);

        return writeClassFile(getPackageDir(entity, packageDir), domainClazz.getClassName(), domainClazz);
    }

    /**
     * Adds an identification or id to the class depending {@link Config#isUseIdGenerator()}
     *
     * @param domainClazz class where to add attribute
     * @param entity      Entity which is used for generating
     */
    private void addIdentificationAttribute(Clazz domainClazz, Entity entity) {
        if (config.isUseIdGenerator()) {
            logger.debug("Identification will be created for " + domainClazz.getClassName());

            Attribute identificationAttribute = new Attribute("identification", "String");
            identificationAttribute.setJavaDoc(new JavaDoc(String.format("Identification of %s", domainClazz.getClassName())));

            Attribute prefixAttribute = new Attribute("ID_PREFIX", "String");
            prefixAttribute.setQualifier(Qualifier.PUBLIC);
            prefixAttribute.setStatic(true);
            prefixAttribute.setFinal(true);
            prefixAttribute.setInitValue(String.format("\"%s\"", entity.getIdentificationPrefix()));

            domainClazz.addAttribute(identificationAttribute);
            domainClazz.addAttribute(prefixAttribute);

            return;
        }
        logger.debug("Id will be created for " + domainClazz.getClassName());

        Attribute idAttribute = new Attribute("id", "Long");
        idAttribute.setJavaDoc(new JavaDoc(String.format("Id of %s", domainClazz.getClassName())));
        domainClazz.addAttribute(idAttribute);
    }

    /**
     * Adds all necessary attributes to the class
     *
     * @param entity      entity whose fields should be added as attribute
     * @param domainClazz Class where to add attributes
     */
    private void addAttributes(Entity entity, Clazz domainClazz) {
        entity.getFields().stream()
                .filter(f -> f.getModels() == null || f.getModels().isDomain())
                .forEach(f -> {
                    if (f.getTypePackage() != null && !f.getTypePackage().isEmpty()) {
                        domainClazz.addImport(String.format("%s.%s", f.getTypePackage(), f.getType()));
                    }
                    domainClazz.addAttribute(createAttribute(f, false));
                });
    }

    /**
     * Adds all necessary references to the class
     *
     * @param entity      entity whose references should be added
     * @param domainClazz Class where to add attributes
     * @param packageName base package name where other referenced class are found
     */
    private void addReferences(Entity entity, Clazz domainClazz, String packageName) {
        List<String> attributes = new ArrayList<>();

        entity.getReferences().forEach(ref -> addReference(domainClazz, packageName, ref, attributes));

        logger.debug(String.format("%d references added to %s", attributes.size(), domainClazz.getClassName()));
        addExcludeAttributes(domainClazz, attributes);
    }

    /**
     * Adds a reference to the class
     *
     * @param domainClazz    clazz where to add attribute
     * @param packageName    base package name
     * @param reference      reference which should be add
     * @param attributeNames list of attributes which are used as reference property at class
     */
    private void addReference(Clazz domainClazz, String packageName, Reference reference, List<String> attributeNames) {
        String childClassName = reference.getTargetEntity();
        String propertyBaseName = getLowerFirst(reference.getReferenceName());

        Attribute child;

        if (reference.isList()) {
            domainClazz.addImport(Collection.class.getName());
            domainClazz.addImport(Setter.class.getName());

            child = new Attribute(propertyBaseName + "s", String.format("%s<%s>", Collection.class.getSimpleName(), childClassName));
            child.addAnnotation(Setter.class.getSimpleName(), null, String.format("%s.%s", AccessLevel.class.getSimpleName(), AccessLevel.PROTECTED.name()));
            child.setInitValue("new HashSet<>()");

            JavaDoc addMethodDescription = new JavaDoc();
            addMethodDescription.addLine(String.format("Adds %s %s", startsWithVowel(childClassName) ? "an" : "a", childClassName));
            addMethodDescription.addLine("");
            addMethodDescription.addLine(String.format("@param %s %s to add", propertyBaseName, childClassName));

            Method addMethod = new Method(String.format("add%s", childClassName));
            addMethod.addParameter(childClassName, propertyBaseName);
            addMethod.setMethodType("boolean");
            addMethod.setQualifier(Qualifier.PUBLIC);
            addMethod.addLine(String.format("return %s.add(%s);", child.getAttributeName(), propertyBaseName));
            addMethod.setJavaDoc(addMethodDescription);

            JavaDoc removeMethodDescription = new JavaDoc();
            removeMethodDescription.addLine(String.format("Removes %s %s", startsWithVowel(childClassName) ? "an" : "a", childClassName));
            removeMethodDescription.addLine("");
            removeMethodDescription.addLine(String.format("@param %s %s to remove", propertyBaseName, childClassName));

            Method removeMethod = new Method(String.format("remove%s", childClassName));
            removeMethod.addParameter(childClassName, propertyBaseName);
            removeMethod.setMethodType("boolean");
            removeMethod.setQualifier(Qualifier.PUBLIC);
            removeMethod.addLine(String.format("return %s.remove(%s);", child.getAttributeName(), propertyBaseName));
            removeMethod.setJavaDoc(removeMethodDescription);

            domainClazz.addMethod(addMethod);
            domainClazz.addMethod(removeMethod);
        } else {
            child = new Attribute(propertyBaseName, childClassName);
        }

        domainClazz.addAttribute(child);
        domainClazz.addImport(getPackageAndClass(reference, packageName));

        attributeNames.add(child.getAttributeName());
    }
}
