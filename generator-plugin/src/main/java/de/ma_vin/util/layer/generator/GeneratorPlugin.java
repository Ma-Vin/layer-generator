package de.ma_vin.util.layer.generator;

import de.ma_vin.util.layer.generator.generator.CommonGenerator;
import de.ma_vin.util.layer.generator.logging.MavenLogImpl;
import lombok.Data;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;


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

    private CommonGenerator commonGenerator = new CommonGenerator();


    @Override
    public void execute() throws MojoExecutionException {
        if (skip) {
            getLog().info("Skip generating");
            return;
        }
        commonGenerator.setLog(new MavenLogImpl(getLog()));

        getLog().info("Start to generating model objects");

        printProperties();

        adoptProperties();

        if (!commonGenerator.validateInput(project.getBasedir())) {
            throw new MojoExecutionException(commonGenerator.getModelFile(), "Non valid input", "The parameter leads to some invalid configuration");
        }
        getLog().info("Parameter validation completed");

        if (!commonGenerator.loadConfig()) {
            throw new MojoExecutionException("The model file could not be used to configure the generator");
        }
        getLog().info("Model file loaded");

        if (!commonGenerator.cleanDirectories()) {
            throw new MojoExecutionException("The cleaning of the target directory or base package could not be completed");
        }
        getLog().info("Directories cleaned");

        if (!commonGenerator.generate()) {
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
     * Adopts properties to {@code generatorCommon}
     */
    private void adoptProperties() {
        commonGenerator.setGenerateTargetDirectory(generateTargetDirectory);
        commonGenerator.setGenerateDto(generateDto);
        commonGenerator.setGenerateDomain(generateDomain);
        commonGenerator.setGenerateDao(generateDao);
        commonGenerator.setModelDefinitionDirectory(modelDefinitionDirectory);
        commonGenerator.setModelDefinitionFilename(modelDefinitionFilename);
        commonGenerator.setCleanTargetDirectory(cleanTargetDirectory);
        commonGenerator.setCleanBasePackage(cleanBasePackage);
    }
}
