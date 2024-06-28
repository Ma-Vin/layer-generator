package de.ma_vin.util.layer.generator.processor;

import de.ma_vin.util.layer.generator.annotations.LayerGenerator;
import com.github.ma_vin.util.layer_generator.generator.generator.CommonGenerator;
import com.github.ma_vin.util.layer_generator.logging.MessagerLogImpl;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

/**
 * Processor which loads the model file defined by {@link LayerGenerator} and generates the corresponding sources
 */
@SupportedAnnotationTypes("de.ma_vin.util.layer.generator.annotations.LayerGenerator")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class LayerGeneratorProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        boolean result = true;
        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                if (element instanceof TypeElement) {
                    LayerGenerator layerGenerator = element.getAnnotation(LayerGenerator.class);
                    Optional<File> modelFile = getModelFile(layerGenerator);
                    Optional<CommonGenerator> commonGenerator = getCommonGenerator(layerGenerator, modelFile);
                    result &= loadAndGenerate(commonGenerator);
                }
            }
        }
        return result;
    }

    /**
     * Loads the configuration file from resources
     *
     * @param layerGenerator Annotation with data of file name and relative path
     * @return Optional of file. {@link Optional#empty()} if no file object was created
     */
    private Optional<File> getModelFile(LayerGenerator layerGenerator) {
        try {
            FileObject fo = processingEnv.getFiler().getResource(StandardLocation.CLASS_OUTPUT, "", getRelativePath(layerGenerator));
            return Optional.of(new File(fo.toUri().getPath()));
        } catch (IOException ex) {
            String msg = String.format("Could not find file \"%s\" at \"%s\"", layerGenerator.modelDefinitionFilename(), layerGenerator.modelDefinitionDirectory());
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
            return Optional.empty();
        }
    }

    /**
     * Creates a {@link CommonGenerator} from a given model file and annotation
     *
     * @param layerGenerator Annotation with data to set at {@link CommonGenerator}
     * @param modelFile      Optional of the model file
     * @return Optional of {@link CommonGenerator}. {@link Optional#empty()} if it was not created
     */
    private Optional<CommonGenerator> getCommonGenerator(LayerGenerator layerGenerator, Optional<File> modelFile) {
        if (modelFile.isEmpty()) {
            return Optional.empty();
        }
        CommonGenerator commonGenerator = createCommonGenerator();
        commonGenerator.setLog(new MessagerLogImpl(processingEnv, layerGenerator.logLevel()));
        commonGenerator.setModelFile(modelFile.get());
        commonGenerator.setGenerateDto(layerGenerator.generateDto());
        commonGenerator.setGenerateDomain(layerGenerator.generateDomain());
        commonGenerator.setGenerateDao(layerGenerator.generateDao());

        return Optional.of(commonGenerator);
    }

    /**
     * Executes the load of the configuration and the generation of the code
     *
     * @param commonGenerator Optional of the {@link CommonGenerator} to use for loading and generating
     * @return {@code true} if  loading and generating was successful. {@code false} at any failure.
     */
    private boolean loadAndGenerate(Optional<CommonGenerator> commonGenerator) {
        if (commonGenerator.isEmpty()) {
            return false;
        }
        if (!commonGenerator.get().loadConfig()) {
            String msg = String.format("Failed to load configuration from model file \"%s\"", commonGenerator.get().getModelFile().getName());
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
            return false;
        }
        if (!commonGenerator.get().generate(processingEnv)) {
            String msg = String.format("Failed to generate model of file \"%s\"", commonGenerator.get().getModelFile().getName());
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
            return false;
        }
        return true;
    }

    /**
     * Determines the relative path of the model file
     *
     * @param layerGenerator Annotation with data to concat to a path
     * @return the relative path of the model file
     */
    private String getRelativePath(LayerGenerator layerGenerator) {
        if (layerGenerator.modelDefinitionDirectory() != null && !layerGenerator.modelDefinitionDirectory().isEmpty()) {
            if (layerGenerator.modelDefinitionDirectory().endsWith("/")) {
                return layerGenerator.modelDefinitionDirectory() + layerGenerator.modelDefinitionFilename();
            }
            return layerGenerator.modelDefinitionDirectory() + "/" + layerGenerator.modelDefinitionFilename();
        }
        return layerGenerator.modelDefinitionFilename();
    }

    /**
     * Method for mocking reasons
     *
     * @return a new {@link CommonGenerator}
     */
    protected CommonGenerator createCommonGenerator() {
        return new CommonGenerator();
    }
}
