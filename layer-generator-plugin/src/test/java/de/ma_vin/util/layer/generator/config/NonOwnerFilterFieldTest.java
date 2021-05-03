package de.ma_vin.util.layer.generator.config;

import de.ma_vin.util.layer.generator.config.elements.NonOwnerFilterField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NonOwnerFilterFieldTest {
    private NonOwnerFilterField cut;
    private final List<String> messages = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        cut = new NonOwnerFilterField();
        cut.setFilterFieldType("FilterType");
        cut.setFilterFieldPackage("FilterPackage");
        cut.setFilterFieldValue("FilterValue");

        messages.clear();
    }

    @Test
    public void testIsValid(){
        assertTrue(cut.isValid(messages), "Index should be valid");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidFilterFieldType(){
        cut.setFilterFieldType(null);
        assertFalse(cut.isValid(messages), "Index should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidFilterFieldPackage(){
        cut.setFilterFieldPackage(null);
        assertFalse(cut.isValid(messages), "Index should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidFilterFieldValue(){
        cut.setFilterFieldValue(null);
        assertFalse(cut.isValid(messages), "Index should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }
}
