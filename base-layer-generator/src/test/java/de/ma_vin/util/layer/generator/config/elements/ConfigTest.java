package de.ma_vin.util.layer.generator.config.elements;

import de.ma_vin.util.layer.generator.config.elements.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigTest {
    private Config cut;
    private final List<String> messages = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        cut = new Config();
        cut.setBasePackage("basePackage");
        cut.setDtoPackage("dtoPackage");
        cut.setDomainPackage("domainPackage");
        cut.setDaoPackage("daoPackage");
        cut.setIdGeneratorPackage("idGeneratorPackage");
        cut.setIdGeneratorClass("idGeneratorClass");
        cut.setGroupings(Collections.EMPTY_LIST);
        cut.setEntities(Collections.EMPTY_LIST);

        messages.clear();
    }

    @Test
    public void testIsValid() {
        assertTrue(cut.isValid(messages), "Entity should be valid");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidNulls() {
        cut.setIdGeneratorPackage(null);
        cut.setIdGeneratorClass(null);
        cut.setGroupings(null);
        cut.setEntities(null);
        assertTrue(cut.isValid(messages), "Entity should be valid");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidBasePackage() {
        cut.setBasePackage(null);
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidDtoPackage() {
        cut.setDtoPackage(null);
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidDomainPackage() {
        cut.setDomainPackage(null);
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidDaoPackage() {
        cut.setDaoPackage(null);
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidIdGeneratorPackage() {
        cut.setIdGeneratorPackage("");
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidIdGeneratorClass() {
        cut.setIdGeneratorClass("");
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }
}
