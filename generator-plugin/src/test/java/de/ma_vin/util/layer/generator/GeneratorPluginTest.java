package de.ma_vin.util.layer.generator;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

import com.github.ma_vin.util.layer_generator.generator.generator.CommonGenerator;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.File;

public class GeneratorPluginTest {

    AutoCloseable openMocks;

    @Mock
    private MavenProject project;
    @Mock
    private File baseDir;
    @Mock
    private File modelFile;
    @Mock
    private CommonGenerator commonGenerator;

    @InjectMocks
    private GeneratorPlugin cut;

    @BeforeEach
    public void setUp() {
        openMocks = openMocks(this);

        cut.setLog(new MavenLog4jLogImpl());
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

        when(modelFile.exists()).thenReturn(Boolean.TRUE);
        when(modelFile.getAbsolutePath()).thenReturn("AnyModelFilePath");

        when(commonGenerator.validateInput(eq(baseDir))).thenReturn(Boolean.TRUE);
        when(commonGenerator.loadConfig()).thenReturn(Boolean.TRUE);
        when(commonGenerator.cleanDirectories()).thenReturn(Boolean.TRUE);
        when(commonGenerator.generate()).thenReturn(Boolean.TRUE);
        when(commonGenerator.getModelFile()).thenReturn(modelFile);
    }


    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @DisplayName("Execution is successful")
    @Test
    public void testExecute() {
        try {
            cut.execute();

            verify(commonGenerator).validateInput(eq(baseDir));
            verify(commonGenerator).loadConfig();
            verify(commonGenerator).cleanDirectories();
            verify(commonGenerator).generate();
        } catch (MojoExecutionException e) {
            fail("Exception occurs but not expected: " + e.getMessage());
        }
    }

    @DisplayName("Execution is skipped")
    @Test
    public void testExecuteSkip() {
        cut.setSkip(true);
        try {
            cut.execute();

            verify(commonGenerator, never()).validateInput(any());
            verify(commonGenerator, never()).loadConfig();
            verify(commonGenerator, never()).cleanDirectories();
            verify(commonGenerator, never()).generate();
        } catch (MojoExecutionException e) {
            fail("Exception occurs but not expected: " + e.getMessage());
        }
    }

    @DisplayName("Execution, but validation fails")
    @Test
    public void testExecuteValidationFails() {
        when(commonGenerator.validateInput(eq(baseDir))).thenReturn(Boolean.FALSE);
        try {
            cut.execute();
            fail("There should be a MojoExecutionException");
        } catch (MojoExecutionException e) {
            verify(commonGenerator).validateInput(eq(baseDir));
            verify(commonGenerator, never()).loadConfig();
            verify(commonGenerator, never()).cleanDirectories();
            verify(commonGenerator, never()).generate();
        }
    }

    @DisplayName("Execution, but configuration load fails")
    @Test
    public void testExecuteConfigurationFails() {
        when(commonGenerator.loadConfig()).thenReturn(Boolean.FALSE);
        try {
            cut.execute();
            fail("There should be a MojoExecutionException");
        } catch (MojoExecutionException e) {
            verify(commonGenerator).validateInput(eq(baseDir));
            verify(commonGenerator).loadConfig();
            verify(commonGenerator, never()).cleanDirectories();
            verify(commonGenerator, never()).generate();
        }
    }

    @DisplayName("Execution, but clean fails")
    @Test
    public void testExecuteCleanFails() {
        when(commonGenerator.cleanDirectories()).thenReturn(Boolean.FALSE);
        try {
            cut.execute();
            fail("There should be a MojoExecutionException");
        } catch (MojoExecutionException e) {
            verify(commonGenerator).validateInput(eq(baseDir));
            verify(commonGenerator).loadConfig();
            verify(commonGenerator).cleanDirectories();
            verify(commonGenerator, never()).generate();
        }
    }

    @DisplayName("Execution, but generate fails")
    @Test
    public void testExecuteGenerateFails() {
        when(commonGenerator.generate()).thenReturn(Boolean.FALSE);
        try {
            cut.execute();
            fail("There should be a MojoExecutionException");
        } catch (MojoExecutionException e) {
            verify(commonGenerator).validateInput(eq(baseDir));
            verify(commonGenerator).loadConfig();
            verify(commonGenerator).cleanDirectories();
            verify(commonGenerator).generate();
        }
    }
}
