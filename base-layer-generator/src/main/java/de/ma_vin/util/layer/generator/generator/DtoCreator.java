package de.ma_vin.util.layer.generator.generator;

import de.ma_vin.util.layer.generator.annotations.model.BaseDto;
import de.ma_vin.util.layer.generator.config.elements.Models;
import de.ma_vin.util.layer.generator.sources.*;
import de.ma_vin.util.layer.generator.config.elements.Config;
import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.config.elements.Reference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Class to create sources of data transport objects
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DtoCreator extends AbstractObjectCreator {

    public static final String DTO_BASIC_INTERFACE = "IBasicTransportable";
    public static final String DTO_INTERFACE = "ITransportable";
    public static final String DTO_POSTFIX = "Dto";

    public DtoCreator(Config config, Log logger) {
        super(config, logger);
    }

    public boolean createDataTransportObjectInterface(String basePackageName, Optional<File> basePackageDir) {
        Interface dtoInterfaceBasic = new Interface(basePackageName, DTO_BASIC_INTERFACE);

        Interface dtoInterface = new Interface(basePackageName, DTO_INTERFACE);
        dtoInterface.setExtension(DTO_BASIC_INTERFACE);

        if (config.isUseIdGenerator()) {
            dtoInterface.addMethodDeclarationWithDescription(String.class.getSimpleName(), "getIdentification"
                    , "@return the identification of the dto");
            dtoInterface.addMethodDeclarationWithDescription("void", "setIdentification"
                    , "@param identification the identification of the dto", String.class.getSimpleName(), "identification");
        } else {
            dtoInterface.addMethodDeclarationWithDescription("Long", "getId", "@return the id of the dto");
            dtoInterface.addMethodDeclarationWithDescription("void", "setId", "@param id the id of the dto", "Long", "id");
        }

        return writeClassFile(basePackageDir, dtoInterfaceBasic)
                & writeClassFile(basePackageDir, dtoInterface);
    }

    /**
     * Creates the data transport objects
     *
     * @param entity      Entity to generate
     * @param packageName name of the package to use
     * @param packageDir  directory where to write at
     * @return {@code true} if creating was successful. Otherwise {@code false}
     */
    public boolean createDataTransportObject(Entity entity, String packageName, Optional<File> packageDir) {
        if (!entity.getModels().isDto()) {
            logger.debug(String.format("Entity %s is not relevant for dto", entity.getBaseName()));
            return true;
        }

        Clazz dtoClazz = new Clazz(getPackage(entity, packageName), entity.getBaseName() + DTO_POSTFIX);
        if (entity.hasNoParent()) {
            dtoClazz.addInterface(isBasicTransportable(entity) ? DTO_BASIC_INTERFACE : DTO_INTERFACE);
            dtoClazz.addImport(String.format("%s.%s", packageName, isBasicTransportable(entity) ? DTO_BASIC_INTERFACE : DTO_INTERFACE));
        }
        checkAndAddParent(dtoClazz, entity, packageName, DTO_POSTFIX);

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
        Annotation unusedParameterSuppressing = dtoClazz.getAnnotation(SuppressWarnings.class.getSimpleName())
                .orElse(new Annotation(SuppressWarnings.class.getSimpleName()));
        unusedParameterSuppressing.appendValue("\"java:S1068\"");
        dtoClazz.addAnnotation(unusedParameterSuppressing);

        if (Boolean.FALSE.equals(entity.getIsAbstract())) {
            dtoClazz.addImport(BaseDto.class.getName());
            dtoClazz.addAnnotation(new Annotation(BaseDto.class, null, "\"" + packageName + "\""));
        }

        addIdentificationAttribute(dtoClazz, entity);
        addAttributes(entity, dtoClazz, Models.DTO);
        addReferences(entity, dtoClazz, packageName, Models::isDto);

        return writeClassFile(getPackageDir(entity, packageDir), dtoClazz);
    }

    /**
     * Adds an identification or id to the class depending {@link Config#isUseIdGenerator()}
     *
     * @param dtoClazz class where to add attribute
     * @param entity   Entity which is used for generating
     */
    private void addIdentificationAttribute(Clazz dtoClazz, Entity entity) {
        if (isBasicTransportable(entity)) {
            logger.debug(String.format("Skip adding identification for %s, because its an only dto entity", dtoClazz.getClassName()));
            return;
        }
        String ofPostFix = dtoClazz.getClassName().substring(0, dtoClazz.getClassName().length() - 3);
        if (config.isUseIdGenerator()) {
            logger.debug("Identification will be created for " + dtoClazz.getClassName());

            Attribute identificationAttribute = new Attribute("identification", "String");
            identificationAttribute.setJavaDoc(new JavaDoc(String.format("Identification of %s", ofPostFix)));

            dtoClazz.addAttribute(identificationAttribute);

            return;
        }
        if (entity.hasNoParent()) {
            logger.debug("Id will be created for " + dtoClazz.getClassName());

            Attribute idAttribute = new Attribute("id", "Long");
            idAttribute.setJavaDoc(new JavaDoc(String.format("Id of %s", ofPostFix)));
            dtoClazz.addAttribute(idAttribute);
        }
    }

    @Override
    protected void addReference(Clazz clazz, String packageName, Reference reference, List<String> attributeNames) {
        if (reference.isList()) {
            logger.debug(String.format("Collection to %s is skipped at %s", reference.getTargetEntity(), clazz.getClassName()));
            return;
        }

        Attribute child = new Attribute(getLowerFirst(reference.getReferenceName()), reference.getTargetEntity() + DTO_POSTFIX);
        if (reference.getShortDescription() != null) {
            child.setJavaDoc(new JavaDoc(reference.getShortDescription()));
        }
        clazz.addAttribute(child);
        clazz.addImport(getPackageAndClass(reference, packageName, DTO_POSTFIX));

        attributeNames.add(child.getAttributeName());
    }

    private boolean isBasicTransportable(Entity entity) {
        return Boolean.FALSE.equals(entity.getGenIdIfDto()) && Models.DTO.equals(entity.getModels());
    }
}
