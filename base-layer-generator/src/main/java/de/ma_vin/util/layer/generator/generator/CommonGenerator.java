package de.ma_vin.util.layer.generator.generator;

import de.ma_vin.util.layer.generator.config.ConfigLoader;
import de.ma_vin.util.layer.generator.config.elements.Config;
import lombok.Data;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Data
public class CommonGenerator {
    
    private Log log;

    private String generateTargetDirectory;
    private boolean generateDto;
    private boolean generateDomain;
    private boolean generateDao;
    private String modelDefinitionDirectory;
    private String modelDefinitionFilename;
    private boolean cleanTargetDirectory;
    private boolean cleanBasePackage;

    private File targetDir;
    private File modelFile;
    private Config config;

    /**
     * Checks whether the parameter are valid
     *
     * @return {@code true} if the process could proceed
     */
    public boolean validateInput(File baseDir) {

        targetDir = createFile(baseDir, generateTargetDirectory);
        if (!targetDir.exists()) {
            log.debug("Target dir does not exists -> create target dir " + targetDir.getAbsolutePath());
            if (!targetDir.mkdirs()) {
                log.error(String.format("target dir \"%s\" could not be created", targetDir.getAbsoluteFile()));
                return false;
            }
        } else {
            log.debug("Use existing target dir " + targetDir.getAbsolutePath());
        }


        File modelDir = createFile(baseDir, modelDefinitionDirectory);
        modelFile = createFile(modelDir, modelDefinitionFilename);
        if (!modelDir.exists()) {
            log.debug("Model dir does not exists -> create model dir " + modelDir.getAbsolutePath());
            if (!modelDir.mkdirs()) {
                log.error(String.format("model dir \"%s\" could not be created", modelDir.getAbsoluteFile()));
                return false;
            }
            createEmptyModelFile();
            return false;
        } else {
            log.debug("Use existing model dir " + modelDir.getAbsolutePath());
        }

        if (!modelFile.exists()) {
            createEmptyModelFile();
            return false;
        }
        log.debug("Use existing model file " + modelFile.getAbsolutePath());
        return true;
    }

    /**
     * creates a new empty model file
     */
    private void createEmptyModelFile() {
        log.error("Model file does not exists");
        try {
            if (!modelFile.createNewFile()) {
                log.error("New Model file to fill with some content could not be generated at " + modelFile.getAbsolutePath());
                return;
            }
            log.error("Model file to fill with some content is generated at " + modelFile.getAbsolutePath());
        } catch (IOException e) {
            log.error("Dummy model file could not be created: ");
            log.error(e.getMessage());
        }
    }

    /**
     * Loads the generation configuration from model file
     *
     * @return {@code true} if loading was successful. Otherwise {@code false}
     */
    public boolean loadConfig() {
        log.debug("Load config from model file " + modelFile.getAbsolutePath());
        ConfigLoader configLoader = createConfigLoader(modelFile);
        if (!configLoader.load()) {
            log.error("Failed to load configuration from model file");
            return false;
        }
        config = configLoader.getConfig();
        log.debug(String.format("Config from model file \"%s\" was loaded", modelFile.getAbsolutePath()));
        return true;
    }

    /**
     * Generates the model java sources based on the existing configuration
     *
     * @return {@code true} if generating was successful. Otherwise {@code false}
     */
    public boolean generate() {
        log.debug("Start generating java sources");
        ModelGenerator modelGenerator = createModelGenerator();
        if (!modelGenerator.generate()) {
            log.error("Failed to generate model");
            return false;
        }
        log.debug("Generation of java sources completed");
        return true;
    }

    public boolean cleanDirectories() {
        if (cleanTargetDirectory) {
            log.debug("Start cleaning target directory");
            if (!deleteAllWithin(targetDir)) {
                log.error("Failed to clean target directory");
                return false;
            }
            log.debug("Cleaning target directory completed");
        }
        if (!cleanTargetDirectory && cleanBasePackage) {
            log.debug("Start cleaning target directory");
            File basePackageDir = createFile(targetDir, config.getBasePackage().replace(".", File.separator));
            if (!deleteAllWithin(basePackageDir)) {
                log.error("Failed to clean target directory");
                return false;
            }
            log.debug("Cleaning target directory completed");
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
            log.debug(String.format("The directory \"%s\" does not exists and could not be deleted", file.getAbsolutePath()));
            return true;
        }
        if (file.isFile()) {
            log.debug(String.format("The directory \"%s\" is a file and will not be deleted", file.getAbsolutePath()));
            return false;
        }
        boolean result = true;
        log.debug(String.format("Start deleting files within directory \"%s\"", file.getAbsolutePath()));
        for (File subFile : file.listFiles()) {
            try {
                result = deleteAll(subFile) && result;
            } catch (IOException e) {
                result = false;
                log.debug(String.format("Exception occur while deleting within directory \"%s\": %s", file.getAbsolutePath(), e.getMessage()));
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
            log.debug(String.format("delete file \"%s\"", file.getAbsolutePath()));
            return Files.deleteIfExists(file.toPath());
        }
        boolean result = true;
        for (File subFile : file.listFiles()) {
            if (!deleteAll(subFile)) {
                result = false;
            }
        }
        log.debug(String.format("delete directory \"%s\"", file.getAbsolutePath()));
        return Files.deleteIfExists(file.toPath()) && result;
    }

    /**
     * Creator Method to be make mocking easier at unit test
     *
     * @return Model creator to use
     */
    protected ModelGenerator createModelGenerator() {
        return new ModelGenerator(config, log, targetDir, generateDto, generateDomain, generateDao);
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
        return new ConfigLoader(modelFile, log);
    }
}
