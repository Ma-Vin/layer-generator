package de.mv.ape.layer.generator.config;

import static org.junit.jupiter.api.Assertions.*;

import de.mv.ape.layer.generator.config.elements.Entity;
import de.mv.ape.layer.generator.log.LogImpl;
import org.apache.maven.plugin.logging.Log;
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
        resourcesDirectory = new File("src/test/resources/references/config");
    }

    @Test()
    public void testLoadValid() {
        File exampleModelFile = new File(resourcesDirectory, "exampleModel.xml");
        ConfigLoader configLoader = new ConfigLoader(exampleModelFile, logger);

        assertTrue(configLoader.load(), "result should be true");

        assertNotNull(configLoader.getConfig(), "There should be some config");

        assertNotNull(configLoader.getConfig().getBasePackage(), "There should be some base package");
        assertEquals("de.mv.ape.test.content", configLoader.getConfig().getBasePackage(), "Wrong base package");
        assertNotNull(configLoader.getConfig().getDtoPackage(), "There should be some dto package");
        assertEquals("dto", configLoader.getConfig().getDtoPackage(), "Wrong dto package");
        assertNotNull(configLoader.getConfig().getDomainPackage(), "There should be some dto package");
        assertEquals("domain", configLoader.getConfig().getDomainPackage(), "Wrong dto package");
        assertNotNull(configLoader.getConfig().getDaoPackage(), "There should be some dao package");
        assertEquals("dao", configLoader.getConfig().getDaoPackage(), "Wrong dao package");

        assertTrue(configLoader.getConfig().isUseIdGenerator(), "the id generator should be used");

        assertNotNull(configLoader.getConfig().getEntities(), "There should be some entities");
        assertEquals(2, configLoader.getConfig().getEntities().size(), "Wrong number of entities");
        Optional<Entity> root = configLoader.getConfig().getEntities().stream().filter(e -> e.getBaseName().equals("Root")).findFirst();
        Optional<Entity> rootExt = configLoader.getConfig().getEntities().stream().filter(e -> e.getBaseName().equals("RootExt")).findFirst();
        assertTrue(root.isPresent(), "Root should be an entity without grouping");
        assertTrue(rootExt.isPresent(), "RootExt should be an entity without grouping");

        assertEquals(9, root.get().getReferences().size(), "Wrong number of references at root");
        assertEquals(14, rootExt.get().getFields().size(), "Wrong number of fields at rootExt");

        assertNotNull(configLoader.getConfig().getGroupings(), "There should be some groupings");
        assertEquals(9, configLoader.getConfig().getGroupings().size(), "Wrong number of groupings");
    }

    @Test()
    public void testLoadValueInvalid() {
        File exampleModelFile = new File(resourcesDirectory, "invalidModel.xml");
        ConfigLoader configLoader = new ConfigLoader(exampleModelFile, logger);

        assertFalse(configLoader.load(), "result should be false");
        assertNotNull(configLoader.getConfig(), "The config should be not empty");
    }

    @Test()
    public void testLoadSchemaInvalid() {
        File exampleModelFile = new File(resourcesDirectory, "schemaInvalidModel.xml");
        File schemaFile = new File("target\\classes\\xsd\\config.xsd");
        ConfigLoader configLoader = new ConfigLoader(exampleModelFile, logger, schemaFile);

        assertFalse(configLoader.load(), "result should be false");
        assertNull(configLoader.getConfig(), "The config should be empty");
    }
}
