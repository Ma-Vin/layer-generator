package de.ma_vin.util.layer.generator.config;

import static org.junit.jupiter.api.Assertions.*;

import de.ma_vin.util.layer.generator.config.elements.Config;
import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.logging.ILogWrapper;
import de.ma_vin.util.layer.generator.logging.Log4jLogImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Optional;

public class ConfigLoaderIT {
    private String resourcesDirectoryTemplate;
    private ILogWrapper logger;


    @BeforeEach
    public void setUp() {
        logger = new Log4jLogImpl();
        resourcesDirectoryTemplate = "references/config/%s";
    }

    @DisplayName("load valid xml config file")
    @Test()
    public void testLoadXmlValid() {
        File exampleModelFile = getConfigFile("exampleModel.xml");
        ConfigLoader configLoader = new ConfigLoader(exampleModelFile, logger);

        checkValidModel(configLoader);
    }

    private void checkValidModel(ConfigLoader configLoader) {
        assertTrue(configLoader.load(), "result should be true");

        assertNotNull(configLoader.getConfig(), "There should be some config");

        Config config = configLoader.getConfig();

        assertNotNull(config.getBasePackage(), "There should be some base package");
        assertEquals("de.ma_vin.util.test.content", config.getBasePackage(), "Wrong base package");
        assertNotNull(config.getDtoPackage(), "There should be some dto package");
        assertEquals("dto", config.getDtoPackage(), "Wrong dto package");
        assertNotNull(config.getDomainPackage(), "There should be some dto package");
        assertEquals("domain", config.getDomainPackage(), "Wrong dto package");
        assertNotNull(config.getDaoPackage(), "There should be some dao package");
        assertEquals("dao", config.getDaoPackage(), "Wrong dao package");

        assertTrue(config.isUseIdGenerator(), "the id generator should be used");

        assertNotNull(config.getEntities(), "There should be some entities");
        assertEquals(2, config.getEntities().size(), "Wrong number of entities");
        Optional<Entity> root = config.getEntities().stream().filter(e -> e.getBaseName().equals("Root")).findFirst();
        Optional<Entity> rootExt = config.getEntities().stream().filter(e -> e.getBaseName().equals("RootExt")).findFirst();
        assertTrue(root.isPresent(), "Root should be an entity without grouping");
        assertTrue(rootExt.isPresent(), "RootExt should be an entity without grouping");

        assertEquals(13, root.get().getReferences().size(), "Wrong number of references at root");
        assertEquals(15, rootExt.get().getFields().size(), "Wrong number of fields at rootExt");

        assertNotNull(config.getGroupings(), "There should be some groupings");
        assertEquals(11, config.getGroupings().size(), "Wrong number of groupings");

        assertEquals(2, root.get().getVersions().size(), "Wrong number of entity versions");
        assertEquals(2, root.get().getVersions().stream().filter(v -> "v2".equals(v.getVersionId())).mapToLong(v -> v.getFields().size()).sum());
        assertEquals(1, root.get().getVersions().stream().filter(v -> "v3".equals(v.getVersionId())).mapToLong(v -> v.getFields().size()).sum());
        assertEquals(13, root.get().getVersions().stream().filter(v -> "v2".equals(v.getVersionId())).mapToLong(v -> v.getReferences().size()).sum());
        assertEquals(12, root.get().getVersions().stream().filter(v -> "v3".equals(v.getVersionId())).mapToLong(v -> v.getReferences().size()).sum());
    }

    private void checkNotLoaded(ConfigLoader configLoader) {
        assertFalse(configLoader.load(), "result should be false");
        assertNull(configLoader.getConfig(), "The config should be empty");
    }

    @DisplayName("load invalid xml config file with respect to program validation")
    @Test()
    public void testLoadXmlProgramInvalid() {
        File exampleModelFile = getConfigFile("invalidModel.xml");
        ConfigLoader configLoader = new ConfigLoader(exampleModelFile, logger);

        assertFalse(configLoader.load(), "result should be false");
        assertNotNull(configLoader.getConfig(), "The config should be not empty");
    }

    @DisplayName("load not existing xml config file")
    @Test()
    public void testLoadXmlNotExisting() {
        File exampleModelFile = getNonExistingConfigFile("exampleModelNotExisting.xml");
        ConfigLoader configLoader = new ConfigLoader(exampleModelFile, logger);

        checkNotLoaded(configLoader);
    }

    @DisplayName("load invalid xml config file with respect to schema validation")
    @Test()
    public void testLoadXmlSchemaInvalid() {
        File exampleModelFile = getConfigFile("schemaInvalidModel.xml");
        File schemaFile = getFileFromResource("xsd/config.xsd");
        ConfigLoader configLoader = new ConfigLoader(exampleModelFile, logger, schemaFile);

        checkNotLoaded(configLoader);
    }

    @DisplayName("load valid yaml config file")
    @Test()
    public void testLoadYamlValid() {
        File exampleModelFile = getConfigFile("exampleModel.yaml");
        ConfigLoader configLoader = new ConfigLoader(exampleModelFile, logger);

        checkValidModel(configLoader);
    }

    @DisplayName("xml and yaml valid models are equal")
    @Test
    public void testComparisonXmlAndYamlModel() {
        File xmlModelFile = getConfigFile("exampleModel.xml");
        ConfigLoader xmlConfigLoader = new ConfigLoader(xmlModelFile, logger);

        File yamlModelFile = getConfigFile("exampleModel.yaml");
        ConfigLoader yamlConfigLoader = new ConfigLoader(yamlModelFile, logger);

        File ymlModelFile = getConfigFile("exampleModel.yml");
        ConfigLoader ymlConfigLoader = new ConfigLoader(ymlModelFile, logger);

        assertTrue(xmlConfigLoader.load(), "xml load should be true");
        assertTrue(yamlConfigLoader.load(), "yaml load should be true");
        assertTrue(ymlConfigLoader.load(), "yaml load should be true");

        assertEquals(xmlConfigLoader.getConfig(), yamlConfigLoader.getConfig(), "xml and yaml should be equal");
        assertEquals(xmlConfigLoader.getConfig(), ymlConfigLoader.getConfig(), "xml and yml should be equal");
    }

    @DisplayName("load not existing yaml config file")
    @Test()
    public void testLoadYamlNotExisting() {
        File exampleModelFile = getNonExistingConfigFile("exampleModelNotExisting.yaml");
        ConfigLoader configLoader = new ConfigLoader(exampleModelFile, logger);

        checkNotLoaded(configLoader);
    }

    @DisplayName("load valid json config file")
    @Test()
    public void testLoadJsonValid() {
        File exampleModelFile = getConfigFile("exampleModel.json");
        ConfigLoader configLoader = new ConfigLoader(exampleModelFile, logger);

        checkValidModel(configLoader);
    }

    @DisplayName("xml and json valid models are equal")
    @Test
    public void testCompareJsonXmlAndJsonModel() {
        File xmlModelFile = getConfigFile("exampleModel.xml");
        ConfigLoader xmlConfigLoader = new ConfigLoader(xmlModelFile, logger);

        File jsonModelFile = getConfigFile("exampleModel.json");
        ConfigLoader jsonConfigLoader = new ConfigLoader(jsonModelFile, logger);

        assertTrue(xmlConfigLoader.load(), "xml load should be true");
        assertTrue(jsonConfigLoader.load(), "json load should be true");

        assertEquals(xmlConfigLoader.getConfig(), jsonConfigLoader.getConfig(), "xml and yml should be equal");
    }

    @DisplayName("load not existing json config file")
    @Test()
    public void testLoadJsonNotExisting() {
        File exampleModelFile = getNonExistingConfigFile("exampleModelNotExisting.json");
        ConfigLoader configLoader = new ConfigLoader(exampleModelFile, logger);

        checkNotLoaded(configLoader);
    }

    private File getConfigFile(String fileName) {
        return getFileFromResource(String.format(resourcesDirectoryTemplate, fileName));
    }

    private File getNonExistingConfigFile(String fileName) {
        return new File(getFileFromResource("references/config/"), fileName);
    }

    private File getFileFromResource(String resourceName) {
        return new File(getClass().getClassLoader().getResource(resourceName).getFile());
    }
}
