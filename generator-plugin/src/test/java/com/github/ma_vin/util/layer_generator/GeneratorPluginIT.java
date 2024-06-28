package com.github.ma_vin.util.layer_generator;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.fail;

public class GeneratorPluginIT {
    private static final String TARGET_DIR = String.format("target%sgen-temp", File.separator);
    private static final String MODEL_DIR = String.format("src%1$stest%1$sresources%1$sreferences%1$sconfig", File.separator);

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
        cut.setLog(new MavenLog4jLogImpl());

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
