package com.github.ma_vin.util.layer_generator.config.elements.fields;

import com.github.ma_vin.util.layer_generator.config.elements.DaoInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class FieldTest {
    private Field cut;
    private final List<String> messages = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        cut = new Field();
        cut.setFieldName("fieldName");
        cut.setType("type");
        cut.setTypePackage("typePackage");
        cut.setShortDescription("shortDescription");
        cut.setDescription("description");
        cut.setDaoInfo(new DaoInfo());

        messages.clear();
    }

    @Test
    public void testIsValid() {
        assertTrue(cut.isValid(messages), "Entity should be valid");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidNulls() {
        cut.setTypePackage(null);
        cut.setShortDescription(null);
        cut.setDescription(null);
        cut.setDaoInfo(null);
        assertTrue(cut.isValid(messages), "Entity should be valid");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidFieldName() {
        cut.setFieldName(null);
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidType() {
        cut.setType(null);
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidTypePackage() {
        cut.setTypePackage("");
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
    public void testIsValidShortDescription() {
        cut.setShortDescription("");
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }
}
