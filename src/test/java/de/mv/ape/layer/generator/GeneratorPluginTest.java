package de.mv.ape.layer.generator;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.*;

import de.mv.ape.layer.generator.config.ConfigLoader;
import de.mv.ape.layer.generator.config.elements.Config;
import de.mv.ape.layer.generator.generator.ModelGenerator;
import de.mv.ape.layer.generator.log.LogImpl;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.File;
import java.io.IOException;

public class GeneratorPluginTest {

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
                    default:
                        return modelFile;
                }
            }
        };
        cut.setLog(new LogImpl());

        initMocks(this);
        project = new MavenProject();
        cut.setProject(project);
        cut.setGenerateTargetDirectory("target/temp/generated");
        cut.setGenerateDto(true);
        cut.setGenerateDomain(true);
        cut.setGenerateDao(true);
        cut.setModelDefinitionDirectory("src/test/resources/references/config");
        cut.setModelDefinitionFilename("exampleModel.xml");

        project.setBasedir(baseDir);
        when(baseDir.getAbsolutePath()).thenReturn("AnyBaseDir");

        when(targetDir.exists()).thenReturn(Boolean.TRUE);
        when(modelDir.exists()).thenReturn(Boolean.TRUE);
        when(modelFile.exists()).thenReturn(Boolean.TRUE);
        when(modelFile.getAbsolutePath()).thenReturn("AnyModelFilePath");

        when(configLoader.load()).thenReturn(Boolean.TRUE);
        when(configLoader.getConfig()).thenReturn(config);

        when(modelGenerator.generate()).thenReturn(Boolean.TRUE);
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
}
