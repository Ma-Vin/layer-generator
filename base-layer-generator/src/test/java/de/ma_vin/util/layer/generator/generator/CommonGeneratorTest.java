package de.ma_vin.util.layer.generator.generator;

import de.ma_vin.util.layer.generator.config.loader.ConfigLoader;
import de.ma_vin.util.layer.generator.config.elements.Config;
import com.github.ma_vin.util.layer_generator.logging.Log4jLogImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.annotation.processing.ProcessingEnvironment;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class CommonGeneratorTest {

    AutoCloseable openMocks;

    @Mock
    private File baseDir;
    @Mock
    private File targetDir;
    @Mock
    private File modelDir;
    @Mock
    private File modelFile;
    @Mock
    private File packageBaseDir;
    @Mock
    private File packageBaseSubDir;
    @Mock
    private File packageBaseExistingFile;
    @Mock
    private FileSystemProvider provider;
    @Mock
    private FileSystem fileSystem;
    @Mock
    private Path packageBaseDirPath;
    @Mock
    private Path packageBaseSubDirPath;
    @Mock
    private Path packageBaseExistingFilePath;
    @Mock
    private ModelGenerator modelGenerator;
    @Mock
    private ConfigLoader configLoader;
    @Mock
    private Config config;
    @Mock
    protected ProcessingEnvironment processingEnv;

    private CommonGenerator cut;

    private boolean isProcessingEnvModelGenerator;
    private boolean isTargetDirModelGenerator;

    @BeforeEach
    public void setUp() throws IOException {
        cut = new CommonGenerator() {
            int numFiles = 0;

            @Override
            protected ConfigLoader createConfigLoader(File modelFile) {
                return configLoader;
            }

            @Override
            protected ModelGenerator createProcessingEnvModelGenerator() {
                isProcessingEnvModelGenerator = true;
                return modelGenerator;
            }

            @Override
            protected ModelGenerator createTargetDirModelGenerator() {
                isTargetDirModelGenerator = true;
                return modelGenerator;
            }

            @Override
            protected File createFile(File dir, String fileName) {
                return switch (fileName) {
                    case "target/temp/generated" -> targetDir;
                    case "src/test/resources/references/config" -> modelDir;
                    case "exampleModel.xml" -> modelFile;
                    default -> packageBaseDir;
                };
            }
        };
        cut.setLog(new Log4jLogImpl());

        openMocks = openMocks(this);
        cut.setGenerateTargetDirectory("target/temp/generated");
        cut.setGenerateDto(true);
        cut.setGenerateDomain(true);
        cut.setGenerateDao(true);
        cut.setModelDefinitionDirectory("src/test/resources/references/config");
        cut.setModelDefinitionFilename("exampleModel.xml");
        cut.setCleanTargetDirectory(false);
        cut.setCleanBasePackage(false);

        when(baseDir.getAbsolutePath()).thenReturn("AnyBaseDir");

        when(targetDir.exists()).thenReturn(Boolean.TRUE);
        when(targetDir.listFiles()).thenReturn(new File[]{packageBaseDir});
        when(modelDir.exists()).thenReturn(Boolean.TRUE);
        when(modelFile.exists()).thenReturn(Boolean.TRUE);
        when(modelFile.getAbsolutePath()).thenReturn("AnyModelFilePath");
        when(modelFile.createNewFile()).thenReturn(Boolean.TRUE);
        when(packageBaseDir.exists()).thenReturn(Boolean.TRUE);
        when(packageBaseDir.getAbsolutePath()).thenReturn("BasePackagePath");
        when(packageBaseDir.listFiles()).thenReturn(new File[]{packageBaseSubDir});
        when(packageBaseSubDir.exists()).thenReturn(Boolean.TRUE);
        when(packageBaseSubDir.getAbsolutePath()).thenReturn("SubBasePackagePath");
        when(packageBaseSubDir.listFiles()).thenReturn(new File[]{packageBaseExistingFile});
        when(packageBaseExistingFile.exists()).thenReturn(Boolean.TRUE);
        when(packageBaseExistingFile.isFile()).thenReturn(Boolean.TRUE);
        when(packageBaseExistingFile.getAbsolutePath()).thenReturn("FileBasePackagePath");
        when(packageBaseExistingFile.listFiles()).thenReturn(new File[0]);

        mockDeletion(packageBaseDir, packageBaseDirPath);
        mockDeletion(packageBaseSubDir, packageBaseSubDirPath);
        mockDeletion(packageBaseExistingFile, packageBaseExistingFilePath);

        when(configLoader.load()).thenReturn(Boolean.TRUE);
        when(configLoader.getConfig()).thenReturn(config);
        when(config.getBasePackage()).thenReturn("de.ma_vin.test");

        when(modelGenerator.generate()).thenReturn(Boolean.TRUE);

        isProcessingEnvModelGenerator = false;
        isTargetDirModelGenerator = false;
    }

    private void mockDeletion(File fileToDelete, Path path) {
        when(fileToDelete.delete()).thenReturn(Boolean.TRUE);
        when(fileToDelete.toPath()).thenReturn(path);
        when(path.getFileSystem()).thenReturn(fileSystem);
        when(fileSystem.provider()).thenReturn(provider);
        try {
            when(provider.deleteIfExists(any())).thenReturn(Boolean.TRUE);
        } catch (IOException e) {
            fail("Mocking deleteIfExists failed");
        }
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @DisplayName("input validation, but target directory does not exist")
    @Test
    public void testValidateInputNotExistingTargetDir() throws IOException {
        when(targetDir.exists()).thenReturn(Boolean.FALSE);
        when(targetDir.mkdirs()).thenReturn(Boolean.TRUE);

        assertTrue(cut.validateInput(baseDir), "validation should be successful");

        verify(targetDir).exists();
        verify(targetDir).mkdirs();
        verify(modelDir).exists();
        verify(modelDir, never()).mkdirs();
        verify(modelFile).exists();
        verify(modelFile, never()).createNewFile();
    }

    @DisplayName("input validation, but target directory creation fails")
    @Test
    public void testValidateInputTargetDirCreationFails() throws IOException {
        when(targetDir.exists()).thenReturn(Boolean.FALSE);
        when(targetDir.mkdirs()).thenReturn(Boolean.FALSE);

        assertFalse(cut.validateInput(baseDir), "validation should fail");

        verify(targetDir).exists();
        verify(targetDir).mkdirs();
        verify(modelDir, never()).exists();
        verify(modelDir, never()).mkdirs();
        verify(modelFile, never()).exists();
        verify(modelFile, never()).createNewFile();
    }

    @DisplayName("input validation, but model directory does not exist")
    @Test
    public void testValidateInputNotExistingModelDir() throws IOException {
        when(modelDir.exists()).thenReturn(Boolean.FALSE);
        when(modelDir.mkdirs()).thenReturn(Boolean.TRUE);

        assertFalse(cut.validateInput(baseDir), "validation should fail");

        verify(targetDir).exists();
        verify(targetDir, never()).mkdirs();
        verify(modelDir).exists();
        verify(modelDir).mkdirs();
        verify(modelFile, never()).exists();
        verify(modelFile).createNewFile();
    }

    @DisplayName("input validation, but model directory creation fails")
    @Test
    public void testValidateInputModelDirCreationFails() throws IOException {
        when(modelDir.exists()).thenReturn(Boolean.FALSE);
        when(modelDir.mkdirs()).thenReturn(Boolean.FALSE);

        assertFalse(cut.validateInput(baseDir), "validation should fail");

        verify(targetDir).exists();
        verify(targetDir, never()).mkdirs();
        verify(modelDir).exists();
        verify(modelDir).mkdirs();
        verify(modelFile, never()).exists();
        verify(modelFile, never()).createNewFile();
    }

    @DisplayName("input validation, but model file does not exist")
    @Test
    public void testValidateInputNotExistingModelFile() throws IOException {
        when(modelFile.exists()).thenReturn(Boolean.FALSE);
        when(modelFile.createNewFile()).thenReturn(Boolean.TRUE);

        assertFalse(cut.validateInput(baseDir), "validation should fail");

        verify(targetDir).exists();
        verify(targetDir, never()).mkdirs();
        verify(modelDir).exists();
        verify(modelDir, never()).mkdirs();
        verify(modelFile).exists();
        verify(modelFile).createNewFile();
    }

    @DisplayName("input validation, but empty model file creations fails")
    @Test
    public void testValidateInputModelFileCreationFails() throws IOException {
        when(modelFile.exists()).thenReturn(Boolean.FALSE);
        when(modelFile.createNewFile()).thenReturn(Boolean.FALSE);

        assertFalse(cut.validateInput(baseDir), "validation should fail");

        verify(targetDir).exists();
        verify(targetDir, never()).mkdirs();
        verify(modelDir).exists();
        verify(modelDir, never()).mkdirs();
        verify(modelFile).exists();
        verify(modelFile).createNewFile();
    }

    @DisplayName("input validation, but empty model file creations throws IOException")
    @Test
    public void testValidateInputModelFileCreationIOException() throws IOException {
        when(modelFile.exists()).thenReturn(Boolean.FALSE);
        when(modelFile.createNewFile()).thenThrow(new IOException("TestException"));

        assertFalse(cut.validateInput(baseDir), "validation should fail");

        verify(targetDir).exists();
        verify(targetDir, never()).mkdirs();
        verify(modelDir).exists();
        verify(modelDir, never()).mkdirs();
        verify(modelFile).exists();
        verify(modelFile).createNewFile();
    }

    @DisplayName("input validation of existing model file")
    @Test
    public void testValidateInputExistingModelFile() throws IOException {
        assertTrue(cut.validateInput(baseDir), "validation should be successful");

        verify(targetDir).exists();
        verify(targetDir, never()).mkdirs();
        verify(modelDir).exists();
        verify(modelDir, never()).mkdirs();
        verify(modelFile).exists();
        verify(modelFile, never()).createNewFile();
    }

    @DisplayName("load configuration")
    @Test
    public void testLoadConfig() {
        cut.setModelFile(modelFile);
        assertTrue(cut.loadConfig(), "loading should be successful");

        verify(configLoader).load();
        verify(configLoader).getConfig();
    }

    @DisplayName("load configuration fails")
    @Test
    public void testLoadConfigFails() {
        cut.setModelFile(modelFile);
        when(configLoader.load()).thenReturn(Boolean.FALSE);

        assertFalse(cut.loadConfig(), "loading should fail");

        verify(configLoader).load();
        verify(configLoader, never()).getConfig();
    }

    @DisplayName("clean target directory")
    @Test
    public void testCleanDirectoriesTarget() throws IOException {
        cut.setTargetDir(targetDir);
        cut.setConfig(config);
        cut.setCleanTargetDirectory(true);

        assertTrue(cut.cleanDirectories(), "cleaning of directories should be successful");

        verify(targetDir).exists();
        verify(targetDir).isFile();
        verify(targetDir).listFiles();
        verify(targetDir, never()).delete();

        verify(packageBaseDir).exists();
        verify(packageBaseDir).isFile();
        verify(packageBaseDir).listFiles();
        verify(provider).deleteIfExists(eq(packageBaseDirPath));

        verify(packageBaseSubDir).exists();
        verify(packageBaseSubDir).isFile();
        verify(packageBaseSubDir).listFiles();
        verify(provider).deleteIfExists(eq(packageBaseSubDirPath));

        verify(packageBaseExistingFile).exists();
        verify(packageBaseExistingFile).isFile();
        verify(packageBaseExistingFile, never()).listFiles();
        verify(provider).deleteIfExists(eq(packageBaseExistingFilePath));
    }

    @DisplayName("clean target directory, but not existing")
    @Test
    public void testCleanDirectoriesTargetNotExisting() throws IOException {
        when(targetDir.exists()).thenReturn(Boolean.FALSE);
        cut.setTargetDir(targetDir);
        cut.setConfig(config);
        cut.setCleanTargetDirectory(true);

        assertTrue(cut.cleanDirectories(), "cleaning of directories should be successful");

        verify(targetDir).exists();
        verify(targetDir, never()).isFile();
        verify(targetDir, never()).listFiles();
        verify(targetDir, never()).delete();

        verify(packageBaseDir, never()).exists();
        verify(packageBaseDir, never()).isFile();
        verify(packageBaseDir, never()).listFiles();
        verify(provider, never()).deleteIfExists(eq(packageBaseDirPath));

        verify(packageBaseSubDir, never()).exists();
        verify(packageBaseSubDir, never()).isFile();
        verify(packageBaseSubDir, never()).listFiles();
        verify(provider, never()).deleteIfExists(eq(packageBaseSubDirPath));

        verify(packageBaseExistingFile, never()).exists();
        verify(packageBaseExistingFile, never()).isFile();
        verify(packageBaseExistingFile, never()).listFiles();
        verify(provider, never()).deleteIfExists(eq(packageBaseExistingFilePath));
    }

    @DisplayName("clean target directory, but it is a file")
    @Test
    public void testCleanDirectoriesTargetFile() throws IOException {
        when(targetDir.isFile()).thenReturn(Boolean.TRUE);
        cut.setTargetDir(targetDir);
        cut.setConfig(config);
        cut.setCleanTargetDirectory(true);

        assertFalse(cut.cleanDirectories(), "cleaning of directories should fail");

        verify(targetDir).exists();
        verify(targetDir).isFile();
        verify(targetDir, never()).listFiles();
        verify(targetDir, never()).delete();

        verify(packageBaseDir, never()).exists();
        verify(packageBaseDir, never()).isFile();
        verify(packageBaseDir, never()).listFiles();
        verify(provider, never()).deleteIfExists(eq(packageBaseDirPath));

        verify(packageBaseSubDir, never()).exists();
        verify(packageBaseSubDir, never()).isFile();
        verify(packageBaseSubDir, never()).listFiles();
        verify(provider, never()).deleteIfExists(eq(packageBaseSubDirPath));

        verify(packageBaseExistingFile, never()).exists();
        verify(packageBaseExistingFile, never()).isFile();
        verify(packageBaseExistingFile, never()).listFiles();
        verify(provider, never()).deleteIfExists(eq(packageBaseExistingFilePath));
    }

    @DisplayName("clean target directory, but fail to delete sub dir")
    @Test
    public void testCleanDirectoriesTargetFailToDeleteSubDir() throws IOException {
        when(provider.deleteIfExists(eq(packageBaseDirPath))).thenReturn(Boolean.FALSE);

        cut.setTargetDir(targetDir);
        cut.setConfig(config);
        cut.setCleanTargetDirectory(true);

        assertFalse(cut.cleanDirectories(), "cleaning of directories should fail");

        verify(targetDir).exists();
        verify(targetDir).isFile();
        verify(targetDir).listFiles();
        verify(targetDir, never()).delete();

        verify(packageBaseDir).exists();
        verify(packageBaseDir).isFile();
        verify(packageBaseDir).listFiles();
        verify(provider).deleteIfExists(eq(packageBaseDirPath));

        verify(packageBaseSubDir).exists();
        verify(packageBaseSubDir).isFile();
        verify(packageBaseSubDir).listFiles();
        verify(provider).deleteIfExists(eq(packageBaseSubDirPath));

        verify(packageBaseExistingFile).exists();
        verify(packageBaseExistingFile).isFile();
        verify(packageBaseExistingFile, never()).listFiles();
        verify(provider).deleteIfExists(eq(packageBaseExistingFilePath));
    }

    @DisplayName("clean target directory, but fail to delete sub dir")
    @Test
    public void testCleanDirectoriesTargetFailToDeleteNotExistingSubDir() throws IOException {
        when(packageBaseSubDir.exists()).thenReturn(Boolean.FALSE);

        cut.setTargetDir(targetDir);
        cut.setConfig(config);
        cut.setCleanTargetDirectory(true);

        assertTrue(cut.cleanDirectories(), "cleaning of directories should be successful");

        verify(targetDir).exists();
        verify(targetDir).isFile();
        verify(targetDir).listFiles();
        verify(targetDir, never()).delete();

        verify(packageBaseDir).exists();
        verify(packageBaseDir).isFile();
        verify(packageBaseDir).listFiles();
        verify(provider).deleteIfExists(eq(packageBaseDirPath));

        verify(packageBaseSubDir).exists();
        verify(packageBaseSubDir, never()).isFile();
        verify(packageBaseSubDir, never()).listFiles();
        verify(provider, never()).deleteIfExists(eq(packageBaseSubDirPath));

        verify(packageBaseExistingFile, never()).exists();
        verify(packageBaseExistingFile, never()).isFile();
        verify(packageBaseExistingFile, never()).listFiles();
        verify(provider, never()).deleteIfExists(eq(packageBaseExistingFilePath));
    }

    @DisplayName("clean target directory, but fail to delete sub file")
    @Test
    public void testCleanDirectoriesTargetFailToDeleteSubFile() throws IOException {
        when(provider.deleteIfExists(eq(packageBaseExistingFilePath))).thenReturn(Boolean.FALSE);

        cut.setTargetDir(targetDir);
        cut.setConfig(config);
        cut.setCleanTargetDirectory(true);

        assertFalse(cut.cleanDirectories(), "cleaning of directories should fail");

        verify(targetDir).exists();
        verify(targetDir).isFile();
        verify(targetDir).listFiles();
        verify(targetDir, never()).delete();

        verify(packageBaseDir).exists();
        verify(packageBaseDir).isFile();
        verify(packageBaseDir).listFiles();
        verify(provider).deleteIfExists(eq(packageBaseDirPath));

        verify(packageBaseSubDir).exists();
        verify(packageBaseSubDir).isFile();
        verify(packageBaseSubDir).listFiles();
        verify(provider).deleteIfExists(eq(packageBaseSubDirPath));

        verify(packageBaseExistingFile).exists();
        verify(packageBaseExistingFile).isFile();
        verify(packageBaseExistingFile, never()).listFiles();
        verify(provider).deleteIfExists(eq(packageBaseExistingFilePath));
    }

    @DisplayName("clean target directory, but fail to delete sub file")
    @Test
    public void testCleanDirectoriesTargetFailToDeleteNotExistingSubFile() throws IOException {
        when(packageBaseExistingFile.exists()).thenReturn(Boolean.FALSE);

        cut.setTargetDir(targetDir);
        cut.setConfig(config);
        cut.setCleanTargetDirectory(true);

        assertTrue(cut.cleanDirectories(), "cleaning of directories should be successful");

        verify(targetDir).exists();
        verify(targetDir).isFile();
        verify(targetDir).listFiles();
        verify(targetDir, never()).delete();

        verify(packageBaseDir).exists();
        verify(packageBaseDir).isFile();
        verify(packageBaseDir).listFiles();
        verify(provider).deleteIfExists(eq(packageBaseDirPath));

        verify(packageBaseSubDir).exists();
        verify(packageBaseSubDir).isFile();
        verify(packageBaseSubDir).listFiles();
        verify(provider).deleteIfExists(eq(packageBaseSubDirPath));

        verify(packageBaseExistingFile).exists();
        verify(packageBaseExistingFile, never()).isFile();
        verify(packageBaseExistingFile, never()).listFiles();
        verify(provider, never()).deleteIfExists(eq(packageBaseExistingFilePath));
    }

    @DisplayName("clean base package directory")
    @Test
    public void testCleanDirectoriesBasePackage() throws IOException {
        cut.setTargetDir(targetDir);
        cut.setConfig(config);
        cut.setCleanBasePackage(true);

        assertTrue(cut.cleanDirectories(), "cleaning of directories should be successful");

        verify(targetDir, never()).exists();
        verify(targetDir, never()).isFile();
        verify(targetDir, never()).listFiles();
        verify(targetDir, never()).delete();

        verify(packageBaseDir).exists();
        verify(packageBaseDir).isFile();
        verify(packageBaseDir).listFiles();
        verify(provider, never()).deleteIfExists(eq(packageBaseDirPath));

        verify(packageBaseSubDir).exists();
        verify(packageBaseSubDir).isFile();
        verify(packageBaseSubDir).listFiles();
        verify(provider).deleteIfExists(eq(packageBaseSubDirPath));

        verify(packageBaseExistingFile).exists();
        verify(packageBaseExistingFile).isFile();
        verify(packageBaseExistingFile, never()).listFiles();
        verify(provider).deleteIfExists(eq(packageBaseExistingFilePath));
    }

    @DisplayName("clean base package directory, but not existing")
    @Test
    public void testCleanDirectoriesBasePackageNotExisting() throws IOException {
        when(packageBaseDir.exists()).thenReturn(Boolean.FALSE);

        cut.setTargetDir(targetDir);
        cut.setConfig(config);
        cut.setCleanBasePackage(true);

        assertTrue(cut.cleanDirectories(), "cleaning of directories should be successful");

        verify(targetDir, never()).exists();
        verify(targetDir, never()).isFile();
        verify(targetDir, never()).listFiles();
        verify(targetDir, never()).delete();

        verify(packageBaseDir).exists();
        verify(packageBaseDir, never()).isFile();
        verify(packageBaseDir, never()).listFiles();
        verify(provider, never()).deleteIfExists(eq(packageBaseDirPath));

        verify(packageBaseSubDir, never()).exists();
        verify(packageBaseSubDir, never()).isFile();
        verify(packageBaseSubDir, never()).listFiles();
        verify(provider, never()).deleteIfExists(eq(packageBaseSubDirPath));

        verify(packageBaseExistingFile, never()).exists();
        verify(packageBaseExistingFile, never()).isFile();
        verify(packageBaseExistingFile, never()).listFiles();
        verify(provider, never()).deleteIfExists(eq(packageBaseExistingFilePath));
    }

    @DisplayName("generation successful")
    @Test
    public void testGenerate() {
        cut.setTargetDir(targetDir);
        cut.setConfig(config);

        assertTrue(cut.generate(), "Generation should be successful");
        assertTrue(isTargetDirModelGenerator, "A model generator with target dir is expected");
        assertFalse(isProcessingEnvModelGenerator, "A model generator with processing env is not expected");

        verify(modelGenerator).generate();
    }

    @DisplayName("generation successful with processing env")
    @Test
    public void testGenerateWithProcessingEnv() {
        cut.setConfig(config);

        assertTrue(cut.generate(processingEnv), "Generation should be successful");
        assertFalse(isTargetDirModelGenerator, "A model generator with target dir is not expected");
        assertTrue(isProcessingEnvModelGenerator, "A model generator with processing env is expected");

        verify(modelGenerator).generate();
    }

    @DisplayName("generation fails")
    @Test
    public void testGenerateFails() {
        when(modelGenerator.generate()).thenReturn(Boolean.FALSE);

        cut.setTargetDir(targetDir);
        cut.setConfig(config);

        assertFalse(cut.generate(), "Generation should fail");

        verify(modelGenerator).generate();
    }
}
