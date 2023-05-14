package de.ma_vin.util.layer.generator.config;

import de.ma_vin.util.layer.generator.config.elements.DaoInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DaoInfoTest {
    private DaoInfo cut;
    private final List<String> messages = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        cut = new DaoInfo();
        cut.setColumnName("ColumnName");
        cut.setColumnDefinition("BLOB");
    }

    @Test
    public void testIsValid() {
        assertTrue(cut.isValid(messages), "Index should be valid");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidNulls() {
        cut.setColumnName(null);
        cut.setColumnDefinition(null);
        assertTrue(cut.isValid(messages), "Index should be valid");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidColumnName() {
        cut.setColumnName("");
        assertFalse(cut.isValid(messages), "Index should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidColumnDefinition() {
        cut.setColumnDefinition("");
        assertFalse(cut.isValid(messages), "Index should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }
}
