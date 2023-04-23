package de.ma_vin.util.layer.generator;

import de.ma_vin.util.layer.generator.config.ConfigLoader;
import de.ma_vin.util.layer.generator.config.elements.Config;
import de.ma_vin.util.layer.generator.generator.ModelGenerator;
import lombok.Data;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Mojo(name = "generate-model", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
@Data
public class GeneratorPlugin extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Parameter(property = "generate-target", defaultValue = "src/main/generated/")
    private String generateTargetDirectory;

    @Parameter(property = "generate-dto", defaultValue = "true")
    private boolean generateDto;

    @Parameter(property = "generate-domain", defaultValue = "true")
    private boolean generateDomain;

    @Parameter(property = "generate-dao", defaultValue = "true")
    private boolean generateDao;

    @Parameter(property = "model-dir", defaultValue = "src/main/resources/")
    private String modelDefinitionDirectory;

    @Parameter(property = "model-file", defaultValue = "model.xml")
    private String modelDefinitionFilename;

    @Parameter(property = "clean-target-dir", defaultValue = "false")
    private boolean cleanTargetDirectory;

    @Parameter(property = "clean-base-package", defaultValue = "true")
    private boolean cleanBasePackage;

    @Parameter(property = "skip", defaultValue = "false")
    private boolean skip;

    private File targetDir;
    private File modelFile;
    private Config config;

    @Override
    public void execute() throws MojoExecutionException {
        if (skip) {
            getLog().info("Skip generating");
            return;
        }

        getLog().info("Start to generating model objects");

        printProperties();

        if (!validateInput()) {
            throw new MojoExecutionException(modelFile, "Non valid input", "The parameter leads to some invalid configuration");
        }
        getLog().info("Parameter validation completed");

        if (!loadConfig()) {
            throw new MojoExecutionException("The model file could not be used to configure the generator");
        }
        getLog().info("Model file loaded");

        if (!cleanDirectories()) {
            throw new MojoExecutionException("The cleaning of the target directory or base package could not be completed");
        }
        getLog().info("Directories cleaned");

        if (!generate()) {
            throw new MojoExecutionException("The generation of the java sources could not be completed");
        }
        getLog().info("Java sources generated");
    }

    /**
     * Prints the set parameters
     */
    private void printProperties() {
        getLog().info("Working directory     = " + project.getBasedir().getAbsolutePath());
        getLog().info("Following properties are set");
        getLog().info("generate-target-dir   = " + generateTargetDirectory);
        getLog().info("generate-dto          = " + generateDto);
        getLog().info("generate-domain       = " + generateDomain);
        getLog().info("generate-dao          = " + generateDao);
        getLog().info("model-dir             = " + modelDefinitionDirectory);
        getLog().info("model-file            = " + modelDefinitionFilename);
        getLog().info("clean-target-dir      = " + cleanTargetDirectory);
        getLog().info("clean-base-package    = " + cleanBasePackage);
    }

    /**
     * Checks whether the parameter are valid
     *
     * @return {@code true} if the process could proceed
     */
    private boolean validateInput() {
        targetDir = createFile(project.getBasedir(), generateTargetDirectory);
        if (!targetDir.exists()) {
            getLog().debug("Target dir does not exists -> create target dir " + targetDir.getAbsolutePath());
            if (!targetDir.mkdirs()) {
                getLog().error(String.format("target dir \"%s\" could not be created", targetDir.getAbsoluteFile()));
                return false;
            }
        } else {
            getLog().debug("Use existing target dir " + targetDir.getAbsolutePath());
        }


        File modelDir = createFile(project.getBasedir(), modelDefinitionDirectory);
        modelFile = createFile(modelDir, modelDefinitionFilename);
        if (!modelDir.exists()) {
            getLog().debug("Model dir does not exists -> create model dir " + modelDir.getAbsolutePath());
            if (!modelDir.mkdirs()) {
                getLog().error(String.format("model dir \"%s\" could not be created", modelDir.getAbsoluteFile()));
                return false;
            }
            createEmptyModelFile();
            return false;
        } else {
            getLog().debug("Use existing model dir " + modelDir.getAbsolutePath());
        }

        if (!modelFile.exists()) {
            createEmptyModelFile();
            return false;
        }
        getLog().debug("Use existing model file " + modelFile.getAbsolutePath());
        return true;
    }

    /**
     * creates a new empty model file
     */
    private void createEmptyModelFile() {
        getLog().error("Model file does not exists");
        try {
            if (!modelFile.createNewFile()) {
                getLog().error("New Model file to fill with some content could not be generated at " + modelFile.getAbsolutePath());
                return;
            }
            getLog().error("Model file to fill with some content is generated at " + modelFile.getAbsolutePath());
        } catch (IOException e) {
            getLog().error("Dummy model file could not be created: ");
            getLog().error(e.getMessage());
        }
    }

    /**
     * Loads the generation configuration from model file
     *
     * @return {@code true} if loading was successful. Otherwise {@code false}
     */
    private boolean loadConfig() {
        getLog().debug("Load config from model file " + modelFile.getAbsolutePath());
        ConfigLoader configLoader = createConfigLoader(modelFile);
        if (!configLoader.load()) {
            getLog().error("Failed to load configuration from model file");
            return false;
        }
        config = configLoader.getConfig();
        getLog().debug(String.format("Config from model file \"%s\" was loaded", modelFile.getAbsolutePath()));
        return true;
    }

    /**
     * Generates the model java sources based on the existing configuration
     *
     * @return {@code true} if generating was successful. Otherwise {@code false}
     */
    private boolean generate() {
        getLog().debug("Start generating java sources");
        ModelGenerator modelGenerator = createModelGenerator();
        if (!modelGenerator.generate()) {
            getLog().error("Failed to generate model");
            return false;
        }
        getLog().debug("Generation of java sources completed");
        return true;
    }

    private boolean cleanDirectories() {
        if (cleanTargetDirectory) {
            getLog().debug("Start cleaning target directory");
            if (!deleteAllWithin(targetDir)) {
                getLog().error("Failed to clean target directory");
                return false;
            }
            getLog().debug("Cleaning target directory completed");
        }
        if (!cleanTargetDirectory && cleanBasePackage) {
            getLog().debug("Start cleaning target directory");
            File basePackageDir = createFile(targetDir, config.getBasePackage().replace(".", File.separator));
            if (!deleteAllWithin(basePackageDir)) {
                getLog().error("Failed to clean target directory");
                return false;
            }
            getLog().debug("Cleaning target directory completed");
        }
        return true;
    }

    /**
     * Deletes all files and directories within a directory but not itself
     *
     * @param file file or directory whose content is to delete
     * @return {@code true} if removing was successful
     */
    private boolean deleteAllWithin(File file) {
        if (!file.exists()) {
            getLog().debug(String.format("The directory \"%s\" does not exists and could not be deleted", file.getAbsolutePath()));
            return false;
        }
        if (file.isFile()) {
            getLog().debug(String.format("The directory \"%s\" is a file and will not be deleted", file.getAbsolutePath()));
            return false;
        }
        boolean result = true;
        getLog().debug(String.format("Start deleting files within directory \"%s\"", file.getAbsolutePath()));
        for (File subFile : file.listFiles()) {
            try {
                result = deleteAll(subFile) && result;
            } catch (IOException e) {
                result = false;
                getLog().debug(String.format("Exception occur while deleting within directory \"%s\": %s", file.getAbsolutePath(), e.getMessage()));
            }
        }
        return result;
    }

    /**
     * Deletes all files and directories within a directory and itself
     *
     * @param file file or directory to delete
     * @return {@code true} if removing was successful
     */
    private boolean deleteAll(File file) throws IOException {
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            getLog().debug(String.format("delete file \"%s\"", file.getAbsolutePath()));
            return Files.deleteIfExists(file.toPath());
        }
        boolean result = true;
        for (File subFile : file.listFiles()) {
            if (!deleteAll(subFile)) {
                result = false;
            }
        }
        getLog().debug(String.format("delete directory \"%s\"", file.getAbsolutePath()));
        return Files.deleteIfExists(file.toPath()) && result;
    }

    /**
     * Creator Method to be make mocking easier at unit test
     *
     * @return Model creator to use
     */
    protected ModelGenerator createModelGenerator() {
        return new ModelGenerator(config, getLog(), targetDir, generateDto, generateDomain, generateDao);
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
     * Creator Method to be make mocking easier at unit test
     *
     * @param modelFile model file
     * @return created loader
     */
    protected ConfigLoader createConfigLoader(File modelFile) {
        return new ConfigLoader(modelFile, getLog());
    }
}
