package de.ma_vin.util.layer.generator.config;

import static org.junit.jupiter.api.Assertions.*;

import de.ma_vin.util.layer.generator.config.elements.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntityTest {
    private Entity cut;
    private final List<String> messages = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        cut = new Entity();
        cut.setBaseName("baseName");
        cut.setDescription("description");
        cut.setIdentificationPrefix("identificationPrefix");
        cut.setParent("parent");
        cut.setFields(Collections.EMPTY_LIST);
        cut.setReferences(Collections.EMPTY_LIST);

        messages.clear();
    }

    @Test
    public void testHasNoParent() {
        cut.setParent(null);
        assertTrue(cut.hasNoParent(), "Null should be interpreted as has no parent");
        cut.setParent(" ");
        assertTrue(cut.hasNoParent(), "Trimmed empty string should be interpreted as has no parent");
        cut.setParent("a");
        assertFalse(cut.hasNoParent(), "Non empty string should not be interpreted as has no parent");
    }

    @Test
    public void testHasParent() {
        cut.setParent(null);
        assertFalse(cut.hasParent(), "Null should not be interpreted as has parent");
        cut.setParent(" ");
        assertFalse(cut.hasParent(), "Trimmed empty string should be interpreted as has parent");
        cut.setParent("a");
        assertTrue(cut.hasParent(), "Non empty string should be interpreted as has parent");
    }

    @Test
    public void testIsValid() {
        assertTrue(cut.isValid(messages), "Entity should be valid");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidNulls() {
        cut.setDescription(null);
        cut.setIdentificationPrefix(null);
        cut.setParent(null);
        cut.setFields(null);
        cut.setReferences(null);
        assertTrue(cut.isValid(messages), "Entity should be valid");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidBaseName() {
        cut.setBaseName(null);
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidDescription() {
        cut.setDescription("");
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidIdentificationPrefix() {
        cut.setIdentificationPrefix("");
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidParent() {
        cut.setParent("");
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }
}
