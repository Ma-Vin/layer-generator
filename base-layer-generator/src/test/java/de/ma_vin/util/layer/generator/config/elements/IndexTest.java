package de.ma_vin.util.layer.generator.config.elements;

import de.ma_vin.util.layer.generator.config.elements.Index;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IndexTest {
    private Index cut;
    private final List<String> messages = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        cut = new Index();
        cut.setIndexName("IndexName");
        cut.setIsUnique(true);
        cut.setFieldList("Field DESC");
    }

    @Test
    public void testIsValid() {
        assertTrue(cut.isValid(messages), "Index should be valid");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidNulls() {
        cut.setIsUnique(null);
        assertTrue(cut.isValid(messages), "Index should be valid");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidIndexName() {
        cut.setIndexName(null);
        assertFalse(cut.isValid(messages), "Index should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidFieldList() {
        cut.setFieldList(null);
        assertFalse(cut.isValid(messages), "Index should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }
}
