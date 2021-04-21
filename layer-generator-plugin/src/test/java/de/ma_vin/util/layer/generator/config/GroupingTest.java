package de.ma_vin.util.layer.generator.config;

import de.ma_vin.util.layer.generator.config.elements.Field;
import de.ma_vin.util.layer.generator.config.elements.Grouping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroupingTest {

    private Grouping cut;
    private final List<String> messages = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        cut = new Grouping();
        cut.setGroupingPackage("groupingPackage");
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
        cut.setEntities(null);
        assertTrue(cut.isValid(messages), "Entity should be valid");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidGroupingPackage() {
        cut.setGroupingPackage(null);
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }
}
