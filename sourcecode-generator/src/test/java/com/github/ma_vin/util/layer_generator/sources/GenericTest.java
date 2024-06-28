package com.github.ma_vin.util.layer_generator.sources;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GenericTest {
    private Generic cut;

    @BeforeEach
    public void setUp() {
        cut = new Generic("T");
    }

    @Test
    public void testGetText() {
        String result = cut.getText();
        assertNotNull(result, "There should be some result");
        assertEquals("T", result, "Wrong text");
    }

    @Test
    public void testGetTextWithExtension() {
        cut.setExtension("SomeClass");
        String result = cut.getText();
        assertNotNull(result, "There should be some result");
        assertEquals("T extends SomeClass", result, "Wrong text");
    }

    @Test
    public void testCompare() {
        Generic otherGeneric = new Generic("S");
        assertTrue(cut.compareTo(otherGeneric) > 0, "Wrong compare value");
    }
}
