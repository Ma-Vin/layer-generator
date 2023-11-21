package de.ma_vin.util.layer.generator.generator;

import de.ma_vin.util.layer.generator.annotations.model.BaseDomain;
import de.ma_vin.util.layer.generator.config.elements.Models;
import de.ma_vin.util.layer.generator.logging.ILogWrapper;
import de.ma_vin.util.layer.generator.sources.*;
import de.ma_vin.util.layer.generator.config.elements.Config;
import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.config.elements.references.Reference;
import lombok.*;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * Class to create sources of domain objects
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DomainCreator extends AbstractObjectCreator {

    public static final String DOMAIN_INTERFACE = "IIdentifiable";

    public DomainCreator(Config config, ILogWrapper logger) {
        super(config, logger);
    }

    public boolean createDomainObjectInterface(String basePackageName, Optional<File> basePackageDir) {
        Interface daoInterface = new Interface(basePackageName, DOMAIN_INTERFACE);

        if (config.isUseIdGenerator()) {
            daoInterface.addMethodDeclarationWithDescription(String.class.getSimpleName(), "getIdentification"
                    , "@return the identification of the domain object");
            daoInterface.addMethodDeclarationWithDescription("void", "setIdentification"
                    , "@param identification the identification of the domain object", String.class.getSimpleName(), "identification");
        } else {
            daoInterface.addMethodDeclarationWithDescription("Long", "getId", "@return the id of the domain object");
            daoInterface.addMethodDeclarationWithDescription("void", "setId", "@param id the id of the domain object", "Long", "id");
        }

        return writeClassFile(basePackageDir, daoInterface);
    }

    /**
     * Creates the domain objects
     *
     * @param entity      Entity to generate
     * @param packageName name of the package to use
     * @param packageDir  directory where to write at
     * @return {@code true} if creating was successful. Otherwise {@code false}
     */
    public boolean createDomainObject(Entity entity, String packageName, Optional<File> packageDir) {
        if (!entity.getModels().isDomain()) {
            logger.debug(String.format("Entity %s is not relevant for domain", entity.getBaseName()));
            return true;
        }

        Clazz domainClazz = new Clazz(getPackage(entity, packageName), entity.getBaseName());
        if (entity.hasNoParent()) {
            domainClazz.addInterface(DOMAIN_INTERFACE);
            domainClazz.addImport(String.format("%s.%s", packageName, DOMAIN_INTERFACE));
        }
        checkAndAddParent(domainClazz, entity, packageName, "");

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
        Annotation unusedParameterSuppressing = domainClazz.getAnnotation(SuppressWarnings.class.getSimpleName())
                .orElse(new Annotation(SuppressWarnings.class.getSimpleName()));
        unusedParameterSuppressing.appendValue("\"java:S1068\"");
        domainClazz.addAnnotation(unusedParameterSuppressing);

        if (Boolean.FALSE.equals(entity.getIsAbstract())) {
            domainClazz.addImport(BaseDomain.class.getName());
            domainClazz.addAnnotation(new Annotation(BaseDomain.class, null, "\"" + packageName + "\""));
        }

        addIdentificationAttribute(domainClazz, entity);
        addAttributes(entity, domainClazz, Models.DOMAIN);
        addReferences(entity, domainClazz, packageName, Models::isDomain);

        return writeClassFile(getPackageDir(entity, packageDir), domainClazz);
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

            Attribute identificationAttribute = new Attribute("identification", String.class.getSimpleName());
            identificationAttribute.setJavaDoc(new JavaDoc(String.format("Identification of %s", domainClazz.getClassName())));

            Attribute prefixAttribute = new Attribute("ID_PREFIX", String.class.getSimpleName());
            prefixAttribute.setQualifier(Qualifier.PUBLIC);
            prefixAttribute.setStatic(true);
            prefixAttribute.setFinal(true);
            prefixAttribute.setInitValue(String.format("\"%s\"", entity.getIdentificationPrefix()));

            domainClazz.addAttribute(identificationAttribute);
            domainClazz.addAttribute(prefixAttribute);

            return;
        }
        if (entity.hasNoParent()) {
            logger.debug("Id will be created for " + domainClazz.getClassName());

            Attribute idAttribute = new Attribute("id", "Long");
            idAttribute.setJavaDoc(new JavaDoc(String.format("Id of %s", domainClazz.getClassName())));
            domainClazz.addAttribute(idAttribute);
        }
    }

    @Override
    protected void addReference(Clazz clazz, String packageName, Reference reference, List<String> attributeNames) {
        String childClassName = reference.getTargetEntity();
        String propertyBaseName = getLowerFirst(reference.getReferenceName());
        String parameterName = getLowerFirst(childClassName);

        Attribute child;

        if (reference.isList()) {
            boolean addThis = propertyBaseName.equals(parameterName);

            clazz.addImport(Collection.class.getName());
            clazz.addImport(Setter.class.getName());
            clazz.addImport(AccessLevel.class.getName());
            clazz.addImport(HashSet.class.getName());

            child = new Attribute(propertyBaseName, String.format("%s<%s>", Collection.class.getSimpleName(), childClassName));
            child.addAnnotation(Setter.class.getSimpleName(), null, String.format("%s.%s", AccessLevel.class.getSimpleName(), AccessLevel.PROTECTED.name()));
            child.setInitValue("new HashSet<>()");

            JavaDoc addMethodDescription = new JavaDoc();
            addMethodDescription.addLine(String.format("Adds %s %s to %s", startsWithVowel(childClassName) ? "an" : "a", childClassName, propertyBaseName));
            addMethodDescription.addLine("");
            addMethodDescription.addLine(String.format("@param %s %s to add", parameterName, childClassName));

            Method addMethod = new Method(String.format("add%s", getUpperFirst(propertyBaseName)));
            addMethod.addParameter(childClassName, parameterName);
            addMethod.setMethodType("boolean");
            addMethod.setQualifier(Qualifier.PUBLIC);
            addMethod.addLine(String.format("return %s%s.add(%s);", addThis ? "this." : "", propertyBaseName, parameterName));
            addMethod.setJavaDoc(addMethodDescription);

            JavaDoc removeMethodDescription = new JavaDoc();
            removeMethodDescription.addLine(String.format("Removes %s %s from %s", startsWithVowel(childClassName) ? "an" : "a", childClassName, propertyBaseName));
            removeMethodDescription.addLine("");
            removeMethodDescription.addLine(String.format("@param %s %s to remove", parameterName, childClassName));

            Method removeMethod = new Method(String.format("remove%s", getUpperFirst(propertyBaseName)));
            removeMethod.addParameter(childClassName, parameterName);
            removeMethod.setMethodType("boolean");
            removeMethod.setQualifier(Qualifier.PUBLIC);
            removeMethod.addLine(String.format("return %s%s.remove(%s);", addThis ? "this." : "", propertyBaseName, parameterName));
            removeMethod.setJavaDoc(removeMethodDescription);

            clazz.addMethod(addMethod);
            clazz.addMethod(removeMethod);
        } else {
            child = new Attribute(propertyBaseName, childClassName);
        }
        if (reference.getShortDescription() != null) {
            child.setJavaDoc(new JavaDoc(reference.getShortDescription()));
        }
        clazz.addAttribute(child);
        clazz.addImport(getPackageAndClass(reference, packageName));

        attributeNames.add(child.getAttributeName());
    }
}
