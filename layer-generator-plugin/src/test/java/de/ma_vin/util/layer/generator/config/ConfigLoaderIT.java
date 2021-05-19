package de.ma_vin.util.layer.generator.config;

import static org.junit.jupiter.api.Assertions.*;

import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.log.LogImpl;
import org.apache.maven.plugin.logging.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Optional;

public class ConfigLoaderIT {
    File resourcesDirectory;
    ClassLoader classLoader;
    Log logger;


    @BeforeEach
    public void setUp() {
        logger = new LogImpl();
        classLoader = getClass().getClassLoader();
        resourcesDirectory = new File(String.format("src%1$stest%1$sresources%1$sreferences%1$sconfig", File.separator));
    }

    @Test()
    public void testLoadValid() {
        File exampleModelFile = new File(resourcesDirectory, "exampleModel.xml");
        ConfigLoader configLoader = new ConfigLoader(exampleModelFile, logger);

        assertTrue(configLoader.load(), "result should be true");

        Assertions.assertNotNull(configLoader.getConfig(), "There should be some config");

        Assertions.assertNotNull(configLoader.getConfig().getBasePackage(), "There should be some base package");
        Assertions.assertEquals("de.ma_vin.util.test.content", configLoader.getConfig().getBasePackage(), "Wrong base package");
        Assertions.assertNotNull(configLoader.getConfig().getDtoPackage(), "There should be some dto package");
        Assertions.assertEquals("dto", configLoader.getConfig().getDtoPackage(), "Wrong dto package");
        Assertions.assertNotNull(configLoader.getConfig().getDomainPackage(), "There should be some dto package");
        Assertions.assertEquals("domain", configLoader.getConfig().getDomainPackage(), "Wrong dto package");
        Assertions.assertNotNull(configLoader.getConfig().getDaoPackage(), "There should be some dao package");
        Assertions.assertEquals("dao", configLoader.getConfig().getDaoPackage(), "Wrong dao package");

        Assertions.assertTrue(configLoader.getConfig().isUseIdGenerator(), "the id generator should be used");

        Assertions.assertNotNull(configLoader.getConfig().getEntities(), "There should be some entities");
        Assertions.assertEquals(2, configLoader.getConfig().getEntities().size(), "Wrong number of entities");
        Optional<Entity> root = configLoader.getConfig().getEntities().stream().filter(e -> e.getBaseName().equals("Root")).findFirst();
        Optional<Entity> rootExt = configLoader.getConfig().getEntities().stream().filter(e -> e.getBaseName().equals("RootExt")).findFirst();
        assertTrue(root.isPresent(), "Root should be an entity without grouping");
        assertTrue(rootExt.isPresent(), "RootExt should be an entity without grouping");

        assertEquals(13, root.get().getReferences().size(), "Wrong number of references at root");
        assertEquals(15, rootExt.get().getFields().size(), "Wrong number of fields at rootExt");

        Assertions.assertNotNull(configLoader.getConfig().getGroupings(), "There should be some groupings");
        Assertions.assertEquals(11, configLoader.getConfig().getGroupings().size(), "Wrong number of groupings");
    }

    @Test()
    public void testLoadValueInvalid() {
        File exampleModelFile = new File(resourcesDirectory, "invalidModel.xml");
        ConfigLoader configLoader = new ConfigLoader(exampleModelFile, logger);

        assertFalse(configLoader.load(), "result should be false");
        Assertions.assertNotNull(configLoader.getConfig(), "The config should be not empty");
    }

    @Test()
    public void testLoadSchemaInvalid() {
        File exampleModelFile = new File(resourcesDirectory, "schemaInvalidModel.xml");
        File schemaFile = new File(String.format("target%1$sclasses%1$sxsd%1$sconfig.xsd", File.separator));
        ConfigLoader configLoader = new ConfigLoader(exampleModelFile, logger, schemaFile);

        assertFalse(configLoader.load(), "result should be false");
        Assertions.assertNull(configLoader.getConfig(), "The config should be empty");
    }
}
