package de.mv.ape.layer.generator.generator;

import de.mv.ape.layer.generator.config.elements.Config;
import de.mv.ape.layer.generator.config.elements.Entity;
import de.mv.ape.layer.generator.config.elements.Reference;
import de.mv.ape.layer.generator.sources.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.util.List;

/**
 * Class to create sources of data transport objects
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DtoCreator extends AbstractObjectCreator {

    public DtoCreator(Config config, Log logger) {
        super(config, logger);
    }

    /**
     * Creates the data transport objects
     *
     * @param entity      Entity to generate
     * @param packageName name of the package to use
     * @param packageDir  directory where to write at
     * @return {@code true} if creating was successful. Otherwise {@code false}
     */
    public boolean createDataTransportObject(Entity entity, String packageName, File packageDir) {
        if (!entity.getModels().isDto()) {
            logger.debug(String.format("Entity %s is not relevant for dto", entity.getBaseName()));
            return true;
        }

        Clazz dtoClazz = new Clazz(getPackage(entity, packageName), entity.getBaseName() + "Dto");

        JavaDoc javaDoc = new JavaDoc(String.format("Generated dto class of %s", entity.getBaseName()));
        if (entity.getDescription() != null && !entity.getDescription().trim().isEmpty()) {
            javaDoc.addLine("<br>");
            javaDoc.addLine(entity.getDescription().trim());
        }
        dtoClazz.setDescription(javaDoc);

        dtoClazz.addImport(Data.class.getName());
        dtoClazz.addImport(NoArgsConstructor.class.getName());

        dtoClazz.addAnnotation(Data.class);
        dtoClazz.addAnnotation(NoArgsConstructor.class);
        dtoClazz.addAnnotation(new Annotation("SuppressWarnings", null, "\"java:S1068\""));

        addIdentificationAttribute(dtoClazz);
        addAttributes(entity, dtoClazz);
        addReferences(entity, dtoClazz, packageName);

        return writeClassFile(getPackageDir(entity, packageDir), dtoClazz.getClassName(), dtoClazz);
    }

    /**
     * Adds an identification or id to the class depending {@link Config#isUseIdGenerator()}
     *
     * @param dtoClazz class where to add attribute
     */
    private void addIdentificationAttribute(Clazz dtoClazz) {
        String ofPostFix = dtoClazz.getClassName().substring(0, dtoClazz.getClassName().length() - 3);
        if (config.isUseIdGenerator()) {
            logger.debug("Identification will be created for " + dtoClazz.getClassName());

            Attribute identificationAttribute = new Attribute("identification", "String");
            identificationAttribute.setJavaDoc(new JavaDoc(String.format("Identification of %s", ofPostFix)));

            dtoClazz.addAttribute(identificationAttribute);

            return;
        }
        logger.debug("Id will be created for " + dtoClazz.getClassName());

        Attribute idAttribute = new Attribute("id", "Long");
        idAttribute.setJavaDoc(new JavaDoc(String.format("Id of %s", ofPostFix)));
        dtoClazz.addAttribute(idAttribute);
    }

    @Override
    protected void addReference(Clazz clazz, String packageName, Reference reference, List<String> attributeNames) {
        if (reference.isList()) {
            logger.debug(String.format("Collection to %s is skipped at %s", reference.getTargetEntity(), clazz.getClassName()));
            return;
        }

        Attribute child = new Attribute(getLowerFirst(reference.getReferenceName()), reference.getTargetEntity() + "Dto");

        clazz.addAttribute(child);
        clazz.addImport(getPackageAndClass(reference, packageName, "Dto"));

        attributeNames.add(child.getAttributeName());
    }
}
