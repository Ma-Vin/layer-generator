package de.mv.ape.layer.generator.generator;

import de.mv.ape.layer.generator.config.elements.Config;
import de.mv.ape.layer.generator.config.elements.Entity;
import de.mv.ape.layer.generator.config.elements.Field;
import de.mv.ape.layer.generator.config.elements.Reference;
import de.mv.ape.layer.generator.sources.AbstractGenerateLines;
import de.mv.ape.layer.generator.sources.Attribute;
import de.mv.ape.layer.generator.sources.JavaDoc;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.maven.plugin.logging.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class AbstractCreator {

    protected Config config;
    protected Log logger;

    /**
     * Generates and write the content to a file
     *
     * @param packageDir directory where to create the file
     * @param className  name of the class. If suffix is not equal to {@code .java}, it will be append
     * @param toGenerate object where to call the generate method and getting file content from
     * @return {@code true} if writing was successful. Otherwise {@code false}
     */
    protected boolean writeClassFile(File packageDir, String className, AbstractGenerateLines toGenerate) {
        File classFile = createFile(packageDir, className.endsWith(".java") ? className : className + ".java");
        List<String> linesToWrite = toGenerate.generate();
        logger.debug(String.format("Start writing class %s with %d lines", className, linesToWrite.size()));
        try (BufferedWriter bw = createBufferedWriter(classFile)) {
            for (String line : linesToWrite) {
                bw.write(line);
                bw.newLine();
            }
            bw.flush();
            logger.debug(String.format("Finish writing class %s with %d lines", className, linesToWrite.size()));
            return true;
        } catch (IOException e) {
            logger.error("Exception while writing class " + className);
            logger.error(e);
            return false;
        }
    }

    /**
     * Create a field
     *
     * @param field       field whose source lines should be generated
     * @param annotations annotations which should be added
     * @return attribute
     */
    protected Attribute createAttribute(Field field, String... annotations) {
        Attribute attribute = new Attribute(field.getFieldName(), field.getType());
        if (field.getDescription() != null) {
            attribute.setJavaDoc(new JavaDoc(field.getDescription()));
        }
        if(field.isTypeEnum()){
            attribute.addAnnotation("Enumerated", null, "EnumType.STRING");
        }
        for (String annotation : annotations) {
            attribute.addAnnotation(annotation);
        }
        return attribute;
    }


    /**
     * Creates a string with package of entity
     *
     * @param entity      entity to check if a sub package is needed
     * @param basePackage package which is definitely used for package name
     * @return package
     */
    protected String getPackage(Entity entity, String basePackage) {
        if (entity.getGrouping() != null) {
            return String.format("%s.%s"
                    , basePackage
                    , entity.getGrouping().getGroupingPackage());
        }
        return basePackage;
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

    /**
     * Creates a string with package and class name of entity
     *
     * @param entity       entity to check if a sub package is needed
     * @param basePackage  package which is definitely used for package name
     * @param classPostfix Postfix which will be append to class name
     * @return package and class name
     */
    protected String getPackageAndClass(Entity entity, String basePackage, String classPostfix) {
        return String.format("%s.%s%s"
                , getPackage(entity, basePackage)
                , entity.getBaseName()
                , classPostfix);
    }

    /**
     * Creates a string with package and class name of reference
     *
     * @param ref          reference to check if a sub package is needed
     * @param basePackage  package which is definitely used for package name
     * @param classPostfix Postfix which will be append to class name
     * @return package and class name
     */
    protected String getPackageAndClass(Reference ref, String basePackage, String classPostfix) {
        return getPackageAndClass(ref.getRealTargetEntity(), basePackage, classPostfix);
    }

    protected static String getLowerFirst(String text) {
        return text.substring(0, 1).toLowerCase() + text.substring(1);
    }

    /**
     * Creator Method to be make mocking easier at unit test
     *
     * @param classFile file to write at
     * @return created buffered writer
     */
    protected BufferedWriter createBufferedWriter(File classFile) throws IOException {
        return new BufferedWriter(new FileWriter(classFile));
    }

    /**
     * Creator Method to be make mocking easier at unit test
     *
     * @param dir      directory of the file
     * @param fileName name of the file
     * @return created file
     */
    protected File createFile(File dir, String fileName) {
        return new File(dir, fileName);
    }
}
