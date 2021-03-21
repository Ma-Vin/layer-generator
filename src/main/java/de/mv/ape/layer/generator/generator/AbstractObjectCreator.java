package de.mv.ape.layer.generator.generator;

import de.mv.ape.layer.generator.config.elements.*;
import de.mv.ape.layer.generator.sources.*;
import lombok.*;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuppressWarnings("java:S2160")
public abstract class AbstractObjectCreator extends AbstractCreator {

    protected boolean isDao;

    AbstractObjectCreator(Config config, Log logger) {
        this(config, logger, false);
    }

    AbstractObjectCreator(Config config, Log logger, boolean isDao) {
        super(config, logger);
        this.isDao = isDao;
    }

    /**
     * Adds all necessary attributes to the class
     *
     * @param entity entity whose fields should be added as attribute
     * @param clazz  Class where to add attributes
     */
    @SuppressWarnings("java:S3878")
    protected void addAttributes(Entity entity, Clazz clazz) {
        addAttributes(entity, clazz, new String[]{});
    }

    /**
     * Adds all necessary attributes to the class
     *
     * @param entity      entity whose fields should be added as attribute
     * @param clazz       Class where to add attributes
     * @param annotations annotations which should be added
     */
    protected void addAttributes(Entity entity, Clazz clazz, String... annotations) {
        entity.getFields().stream()
                .filter(f -> f.getModels() == null || f.getModels().isDomain())
                .forEach(f -> {
                    if (f.getTypePackage() != null && !f.getTypePackage().isEmpty()) {
                        clazz.addImport(String.format("%s.%s", f.getTypePackage(), f.getType()));
                    }
                    clazz.addAttribute(createAttribute(f, isDao, annotations));
                });
    }

    /**
     * Adds all necessary references to the class
     *
     * @param entity      entity whose references should be added
     * @param clazz       Class where to add attributes
     * @param packageName base package name where other referenced class are found
     */
    protected void addReferences(Entity entity, Clazz clazz, String packageName) {
        List<String> attributes = new ArrayList<>();

        entity.getReferences().forEach(ref -> addReference(clazz, packageName, ref, attributes));

        logger.debug(String.format("%d references added to %s", attributes.size(), clazz.getClassName()));
        addExcludeAttributes(clazz, attributes);
    }

    protected abstract void addReference(Clazz clazz, String packageName, Reference reference, List<String> attributeNames);

    /**
     * Create a field
     *
     * @param field       field whose source lines should be generated
     * @param isDao       {@code true} if an attribute of a dao is created
     * @param annotations annotations which should be added
     * @return attribute
     */
    protected Attribute createAttribute(Field field, boolean isDao, String... annotations) {
        Attribute attribute = new Attribute(field.getFieldName(), field.getType());
        if (field.getDescription() != null) {
            attribute.setJavaDoc(new JavaDoc(field.getDescription()));
        }
        if (isDao && field.isTypeEnum()) {
            attribute.addAnnotation("Enumerated", null, "EnumType.STRING");
        }
        for (String annotation : annotations) {
            attribute.addAnnotation(annotation);
        }
        logger.debug(String.format("Attribute %s of type %s was created", attribute.getAttributeName(), attribute.getAttributeType()));
        return attribute;
    }

    /**
     * Adds exclusions to {@link ToString} and {@link EqualsAndHashCode} for a given list of names of attribute.
     * If the list is empty, not any annotation will be added.
     *
     * @param daoClazz   class where to add the Annotation with exclusion
     * @param attributes attributes to exclude
     */
    protected void addExcludeAttributes(Clazz daoClazz, List<String> attributes) {
        if (attributes.isEmpty()) {
            return;
        }

        List<String> excludeAttributes = attributes.stream()
                .map(a -> a.startsWith("\"") && a.endsWith("\"") ? a : "\"" + a + "\"")
                .collect(Collectors.toList());

        Annotation toStringAnnotation = new Annotation(ToString.class);
        Annotation equalsAndHashCodeAnnotation = new Annotation(EqualsAndHashCode.class);

        toStringAnnotation.addParameterArray("exclude", excludeAttributes);
        equalsAndHashCodeAnnotation.addParameterArray("exclude", excludeAttributes);

        daoClazz.addImport(EqualsAndHashCode.class.getName());
        daoClazz.addImport(ToString.class.getName());
        daoClazz.addAnnotation(toStringAnnotation);
        daoClazz.addAnnotation(equalsAndHashCodeAnnotation);
    }

    /**
     * Creates a File for package of entity
     *
     * @param entity         entity to check if a sub package is needed
     * @param basePackageDir package which is definitely used for package dir
     * @return package directory
     */
    protected File getPackageDir(Entity entity, File basePackageDir) {
        if (entity.getGrouping() != null) {
            return createFile(basePackageDir, entity.getGrouping().getGroupingPackage());
        }
        return basePackageDir;
    }

}
