package de.ma_vin.util.layer.generator.generator;

import de.ma_vin.util.layer.generator.config.elements.Config;
import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.config.elements.Reference;
import de.ma_vin.util.layer.generator.sources.Annotation;
import de.ma_vin.util.layer.generator.sources.Attribute;
import de.ma_vin.util.layer.generator.sources.IFileRepresentation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.maven.plugin.logging.Log;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;
import java.io.*;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class AbstractCreator {
    public static final String PACKAGE_AND_CLASS_NAME_FORMAT = "%s.%s";

    protected Config config;
    protected Log logger;
    protected boolean generateJavaFileObject;
    private Optional<ProcessingEnvironment> processingEnv;

    protected AbstractCreator(Config config, Log logger) {
        this.config = config;
        this.logger = logger;
        generateJavaFileObject = false;
        processingEnv = Optional.empty();
    }

    /**
     * Generates and write the content to a file
     *
     * @param packageDir directory where to create the file
     * @param toGenerate object where to call the generate method and getting file content from
     * @return {@code true} if writing was successful. Otherwise {@code false}
     */
    protected boolean writeClassFile(Optional<File> packageDir, IFileRepresentation toGenerate) {
        List<String> linesToWrite = toGenerate.generate();
        logger.debug(String.format("Start writing %s with %d lines", toGenerate.getFilename(), linesToWrite.size()));
        try (BufferedWriter bw = createBufferedWriter(packageDir, toGenerate)) {
            for (String line : linesToWrite) {
                bw.write(line);
                bw.newLine();
            }
            bw.flush();
            logger.debug(String.format("Finish writing %s with %d lines", toGenerate.getFilename(), linesToWrite.size()));
            return true;
        } catch (IOException e) {
            logger.error("Exception while writing " + toGenerate.getFilename());
            logger.error(e);
            return false;
        }
    }

    protected static String getLowerFirst(String text) {
        return text.substring(0, 1).toLowerCase() + text.substring(1);
    }


    public static String getUpperFirst(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    /**
     * Creator Method to be make mocking easier at unit test
     *
     * @param packageDir directory where to create the file
     * @param toGenerate object where to call the generate method and getting file content from
     * @return created buffered writer
     */
    private BufferedWriter createBufferedWriter(Optional<File> packageDir, IFileRepresentation toGenerate) throws IOException {
        if (generateJavaFileObject && processingEnv.isPresent()) {
            JavaFileObject javaFileObject = processingEnv.get().getFiler().createSourceFile(toGenerate.getPackageName() + "." + toGenerate.getObjectName());
            return createBufferedWriter(javaFileObject);
        }
        if (!generateJavaFileObject && packageDir.isPresent()) {
            File classFile = createFile(packageDir.get(), toGenerate.getFilename());
            return createBufferedWriter(classFile);
        }
        throw new IOException(String.format("Ambiguous definition of buffered writer. generateJavaFileObject=%b , packageDir.isPresent()=%b, processingEnv.isPresent()=%b"
                , generateJavaFileObject, packageDir.isPresent(), processingEnv.isPresent()));
    }

    protected BufferedWriter createBufferedWriter(JavaFileObject javaFileObject) throws IOException {
        return createBufferedWriter(javaFileObject.openWriter());
    }

    protected BufferedWriter createBufferedWriter(File classFile) throws IOException {
        return createBufferedWriter(new FileWriter(classFile));
    }

    protected BufferedWriter createBufferedWriter(Writer writer) {
        return new BufferedWriter(writer);
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

    /**
     * Checks whether a text starts with an vowel
     *
     * @param text text to check
     * @return {@code true} if the first letter of text is a vowel
     */
    protected boolean startsWithVowel(String text) {
        String vowels = "aeiou";
        return vowels.contains(text.toLowerCase().substring(0, 1));
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
            return getPackage(entity.getGrouping().getGroupingPackage(), basePackage);
        }
        return basePackage;
    }

    /**
     * Creates a string with package of subPackage
     *
     * @param subPackage  sub package
     * @param basePackage package which is definitely used for package name
     * @return package
     */
    protected String getPackage(String subPackage, String basePackage) {
        if (subPackage != null) {
            return String.format(PACKAGE_AND_CLASS_NAME_FORMAT
                    , basePackage
                    , subPackage);
        }
        return basePackage;
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

    /**
     * Creates a string with package and class name of reference
     *
     * @param ref         reference to check if a sub package is needed
     * @param basePackage package which is definitely used for package name
     * @return package and class name
     */
    protected String getPackageAndClass(Reference ref, String basePackage) {
        return getPackageAndClass(ref.getRealTargetEntity(), basePackage, "");
    }

    /**
     * Returns a {@link Annotation} at a given {@link Attribute}. If there is not any at attribute, a new will be created and added to the attribute
     *
     * @param attribute      Attribute where to search at
     * @param annotationName Name of annotation
     * @return The found annotation or new created one
     */
    protected Annotation getOrCreateAndAddAnnotation(Attribute attribute, String annotationName) {
        Optional<Annotation> columnAnnotationOpt = attribute.getAnnotations().stream()
                .filter(a -> annotationName.equals(a.getAnnotationName()))
                .findFirst();
        if (columnAnnotationOpt.isPresent()) {
            return columnAnnotationOpt.get();
        }
        Annotation annotation = new Annotation(annotationName);
        attribute.addAnnotation(annotationName);
        return annotation;
    }
}
