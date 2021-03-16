package de.mv.ape.layer.generator.sources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JavaDocTest {
    private JavaDoc cut;

    @BeforeEach
    public void setUp() {
        cut = new JavaDoc();
    }

    @Test
    public void testGenerateDefault() {
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(0, result.size(), "Wrong number of lines");
    }

    @Test
    public void testGenerateAddLine() {
        cut.addLine("abc");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(3, result.size(), "Wrong number of lines");
        assertEquals("/**", result.get(0), "Wrong first element");
        assertEquals(" * abc", result.get(1), "Wrong second element");
        assertEquals(" */", result.get(2), "Wrong third element");
    }
}
