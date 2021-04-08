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
        cut.setLog(new LogImpl());

        project = new MavenProject();
        project.setFile(new File(System.getProperty("user.dir"), "dummyPom.xml"));

        cut.setProject(project);
        File targetDir = new File(project.getBasedir(), TARGET_DIR);
        if (!deleteAll(targetDir)) {
            fail("could not delete target dir first");
        }
    }

    private boolean deleteAll(File file) {
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        boolean result = true;
        for (File subFile : file.listFiles()) {
            if (!deleteAll(subFile)) {
                result = false;
            }
        }
        return  file.delete() && result;
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
