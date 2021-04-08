package de.ma_vin.util.layer.generator;

import de.ma_vin.util.layer.generator.log.LogImpl;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.fail;

//@Disabled
public class GeneratorPluginIT {
    private static final String TARGET_DIR = "target/gen-temp";
    private static final String MODEL_DIR = "src/test/resources/references/config";

    private GeneratorPlugin cut;
    private MavenProject project;

    @BeforeEach
    public void setUp() {
        cut = new GeneratorPlugin();
        cut.setGenerateDao(true);
        cut.setGenerateDto(true);
        cut.setGenerateDomain(true);
        cut.setGenerateTargetDirectory(TARGET_DIR);
        cut.setModelDefinitionDirectory(MODEL_DIR);
        cut.setModelDefinitionFilename("exampleModel.xml");
        cut.setCleanTargetDirectory(true);
        cut.setCleanBasePackage(false);
        cut.setLog(new LogImpl());

        project = new MavenProject();
        project.setFile(new File(System.getProperty("user.dir"), "dummyPom.xml"));

        cut.setProject(project);
    }

    @Test
    public void testExecute() {
        try {
            cut.execute();
        } catch (MojoExecutionException e) {
            fail(e);
        }
    }
}
