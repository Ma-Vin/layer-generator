package de.ma_vin.util.layer.generator;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

import de.ma_vin.util.layer.generator.config.ConfigLoader;
import de.ma_vin.util.layer.generator.config.elements.Config;
import de.ma_vin.util.layer.generator.generator.ModelGenerator;
import de.ma_vin.util.layer.generator.log.LogImpl;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;

public class GeneratorPluginTest {

    AutoCloseable openMocks;

    @Mock
    private MavenProject project;
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

    private GeneratorPlugin cut;

    @BeforeEach
    public void setUp() {
        cut = new GeneratorPlugin() {
            int numFiles = 0;

            @Override
            protected ConfigLoader createConfigLoader(File modelFile) {
                return configLoader;
            }

            @Override
            protected ModelGenerator createModelGenerator() {
                return modelGenerator;
            }

            @Override
            protected File createFile(File dir, String fileName) {
                numFiles++;
                switch (numFiles) {
                    case 1:
                        return targetDir;
                    case 2:
                        return modelDir;
                    case 3:
                        return modelFile;
                    default:
                        return packageBaseDir;
                }
            }
        };
        cut.setLog(new LogImpl());

        openMocks = openMocks(this);
        cut.setProject(project);
        cut.setGenerateTargetDirectory("target/temp/generated");
        cut.setGenerateDto(true);
        cut.setGenerateDomain(true);
        cut.setGenerateDao(true);
        cut.setModelDefinitionDirectory("src/test/resources/references/config");
        cut.setModelDefinitionFilename("exampleModel.xml");
        cut.setCleanTargetDirectory(false);
        cut.setCleanBasePackage(false);

        when(project.getBasedir()).thenReturn(baseDir);
        when(baseDir.getAbsolutePath()).thenReturn("AnyBaseDir");

        when(targetDir.exists()).thenReturn(Boolean.TRUE);
        when(targetDir.listFiles()).thenReturn(new File[]{packageBaseDir});
        when(modelDir.exists()).thenReturn(Boolean.TRUE);
        when(modelFile.exists()).thenReturn(Boolean.TRUE);
        when(modelFile.getAbsolutePath()).thenReturn("AnyModelFilePath");
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

    @Test
    public void testDefault() {
        try {
            cut.execute();
        } catch (MojoExecutionException e) {
            fail("Exception occurs but not expected: " + e.getMessage());
        }
    }

    @Test
    public void testDefaultSkip() {
        cut.setSkip(true);
        try {
            cut.execute();

            verify(baseDir, never()).exists();
            verify(targetDir, never()).exists();
            verify(modelDir, never()).exists();
            verify(packageBaseDir, never()).exists();
            verify(packageBaseSubDir, never()).exists();
        } catch (MojoExecutionException e) {
            fail("Exception occurs but not expected: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidNoTargetDir() {
        when(targetDir.exists()).thenReturn(Boolean.FALSE);
        when(targetDir.mkdirs()).thenReturn(Boolean.TRUE);

        try {
            cut.execute();
        } catch (MojoExecutionException e) {
            fail("Exception occurs but not expected: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidNoTargetDirCreation() {
        when(targetDir.exists()).thenReturn(Boolean.FALSE);
        when(targetDir.mkdirs()).thenReturn(Boolean.FALSE);

        try {
            cut.execute();
            fail("Exception not occurs but expected");
        } catch (MojoExecutionException e) {
            assertTrue(e.getMessage().contains("Non valid input"), "Missing exception text");
        }
    }

    @Test
    public void testInvalidNoModelDir() throws IOException {
        when(modelDir.exists()).thenReturn(Boolean.FALSE);
        when(modelDir.mkdirs()).thenReturn(Boolean.TRUE);
        when(modelFile.createNewFile()).thenReturn(Boolean.TRUE);

        try {
            cut.execute();
            fail("Exception not occurs but expected");
        } catch (MojoExecutionException e) {
            assertTrue(e.getMessage().contains("Non valid input"), "Missing exception text");
        }
        verify(modelFile).createNewFile();
    }

    @Test
    public void testInvalidNoModelDirCreation() throws IOException {
        when(modelDir.exists()).thenReturn(Boolean.FALSE);
        when(modelDir.mkdirs()).thenReturn(Boolean.FALSE);
        when(modelFile.createNewFile()).thenReturn(Boolean.TRUE);

        try {
            cut.execute();
            fail("Exception not occurs but expected");
        } catch (MojoExecutionException e) {
            assertTrue(e.getMessage().contains("Non valid input"), "Missing exception text");
        }
        verify(modelFile, never()).createNewFile();
    }

    @Test
    public void testInvalidNoModelFileCreation() throws IOException {
        when(modelDir.exists()).thenReturn(Boolean.FALSE);
        when(modelDir.mkdirs()).thenReturn(Boolean.TRUE);
        when(modelFile.createNewFile()).thenReturn(Boolean.FALSE);

        try {
            cut.execute();
            fail("Exception not occurs but expected");
        } catch (MojoExecutionException e) {
            assertTrue(e.getMessage().contains("Non valid input"), "Missing exception text");
        }
        verify(modelFile).createNewFile();
    }

    @Test
    public void testInvalidNoModelFile() throws IOException {
        when(modelFile.exists()).thenReturn(Boolean.FALSE);
        when(modelFile.createNewFile()).thenReturn(Boolean.TRUE);
        try {
            cut.execute();
            fail("Exception not occurs but expected");
        } catch (MojoExecutionException e) {
            assertTrue(e.getMessage().contains("Non valid input"), "Missing exception text");
        }
        verify(modelFile).createNewFile();
    }

    @Test
    public void testInvalidExceptionAtModelFileCreation() throws IOException {
        when(modelFile.exists()).thenReturn(Boolean.FALSE);
        when(modelFile.createNewFile()).thenThrow(new IOException("createNewFile"));
        try {
            cut.execute();
            fail("Exception not occurs but expected");
        } catch (MojoExecutionException e) {
            assertTrue(e.getMessage().contains("Non valid input"), "Missing exception text");
        }
        verify(modelFile).createNewFile();
    }

    @Test
    public void testLoadConfigFailed() {
        when(configLoader.load()).thenReturn(Boolean.FALSE);
        try {
            cut.execute();
            fail("Exception not occurs but expected");
        } catch (MojoExecutionException e) {
            assertTrue(e.getMessage().contains("The model file could not be used to configure the generator"), "Missing exception text");
        }
    }

    @Test
    public void testGenerateFailed() {
        when(modelGenerator.generate()).thenReturn(Boolean.FALSE);
        try {
            cut.execute();
            fail("Exception not occurs but expected");
        } catch (MojoExecutionException e) {
            assertTrue(e.getMessage().contains("The generation of the java sources could not be completed"), "Missing exception text");
        }
    }

    @Test
    public void testGenerateDeleteTargetDir() {
        cut.setCleanTargetDirectory(true);
        try {
            cut.execute();

            verify(targetDir, times(2)).exists();
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
        } catch (MojoExecutionException | IOException e) {
            fail("Exception occurs but not expected: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateDeleteTargetDirSubDeletionFailure() throws IOException {
        cut.setCleanTargetDirectory(true);
        when(provider.deleteIfExists(eq(packageBaseDirPath))).thenReturn(Boolean.FALSE);
        try {
            cut.execute();
            fail("Exception not occurs but expected");
        } catch (MojoExecutionException e) {
            assertTrue(e.getMessage().contains("The cleaning of the target directory or base package could not be completed"), "Missing exception text");
        }
    }

    @Test
    public void testGenerateDeleteOnlyOnce() {
        cut.setCleanTargetDirectory(true);
        cut.setCleanBasePackage(true);
        try {
            cut.execute();

            verify(targetDir, times(2)).exists();
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
        } catch (MojoExecutionException | IOException e) {
            fail("Exception occurs but not expected: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateDeletePackageBase() {
        cut.setCleanBasePackage(true);
        try {
            cut.execute();

            verify(targetDir).exists();
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
        } catch (MojoExecutionException | IOException e) {
            fail("Exception occurs but not expected: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateDeletePackageBaseNotExisting() throws IOException {
        cut.setCleanBasePackage(true);
        when(packageBaseDir.exists()).thenReturn(Boolean.FALSE);
        try {
            cut.execute();
            fail("Exception not occurs but expected");
        } catch (MojoExecutionException e) {
            assertTrue(e.getMessage().contains("The cleaning of the target directory or base package could not be completed"), "Missing exception text");
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
    }

    @Test
    public void testGenerateDeletePackageBaseIsFile() throws IOException {
        cut.setCleanBasePackage(true);
        when(packageBaseDir.isFile()).thenReturn(Boolean.TRUE);
        try {
            cut.execute();
            fail("Exception not occurs but expected");
        } catch (MojoExecutionException e) {
            assertTrue(e.getMessage().contains("The cleaning of the target directory or base package could not be completed"), "Missing exception text");
            verify(packageBaseDir, never()).listFiles();
            verify(packageBaseDir, never()).delete();
            verify(provider, never()).deleteIfExists(eq(packageBaseDirPath));

            verify(packageBaseSubDir, never()).exists();
            verify(packageBaseSubDir, never()).isFile();
            verify(packageBaseSubDir, never()).listFiles();
            verify(packageBaseSubDir, never()).delete();
            verify(provider, never()).deleteIfExists(eq(packageBaseSubDirPath));

            verify(packageBaseExistingFile, never()).exists();
            verify(packageBaseExistingFile, never()).isFile();
            verify(packageBaseExistingFile, never()).listFiles();
            verify(packageBaseExistingFile, never()).delete();
            verify(provider, never()).deleteIfExists(eq(packageBaseExistingFilePath));
        }
    }

    @Test
    public void testGenerateDeletePackageBaseFileNotExisting() throws IOException {
        cut.setCleanBasePackage(true);
        when(packageBaseExistingFile.exists()).thenReturn(Boolean.FALSE);
        try {
            cut.execute();

            verify(packageBaseExistingFile).exists();
            verify(packageBaseExistingFile, never()).isFile();
            verify(packageBaseExistingFile, never()).listFiles();
            verify(provider, never()).deleteIfExists(eq(packageBaseExistingFilePath));
        } catch (MojoExecutionException e) {
            fail("Exception occurs but not expected: " + e.getMessage());
        }
    }

    @Test
    public void testGenerateDeletePackageBaseFileDeletionFailure() throws IOException {
        cut.setCleanBasePackage(true);
        when(packageBaseExistingFile.delete()).thenReturn(Boolean.FALSE);
        when(provider.deleteIfExists(eq(packageBaseExistingFilePath))).thenReturn(Boolean.FALSE);
        try {
            cut.execute();
            fail("Exception not occurs but expected");
        } catch (MojoExecutionException e) {
            assertTrue(e.getMessage().contains("The cleaning of the target directory or base package could not be completed"), "Missing exception text");


            verify(packageBaseExistingFile).exists();
            verify(packageBaseExistingFile).isFile();
            verify(packageBaseExistingFile, never()).listFiles();
            verify(provider).deleteIfExists(eq(packageBaseExistingFilePath));
        }
    }

    @Test
    public void testGenerateDeletePackageBaseSubDeletionFailure() throws IOException {
        cut.setCleanBasePackage(true);
        when(provider.deleteIfExists(eq(packageBaseSubDirPath))).thenReturn(Boolean.FALSE);
        try {
            cut.execute();
            fail("Exception not occurs but expected");
        } catch (MojoExecutionException e) {
            assertTrue(e.getMessage().contains("The cleaning of the target directory or base package could not be completed"), "Missing exception text");


            verify(packageBaseSubDir).exists();
            verify(packageBaseSubDir).isFile();
            verify(packageBaseSubDir).listFiles();
            verify(provider).deleteIfExists(eq(packageBaseSubDirPath));
        }
    }
}
