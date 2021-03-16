package de.mv.ape.layer.generator.sources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ImportTest {
    private Import cut;

    @BeforeEach
    public void setUp() {
        cut = new Import("de.abc.ImportClass", false);
    }

    @Test
    public void testGenerateDefault() {
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(1, result.size(), "Wrong number of lines");
        assertEquals("import de.abc.ImportClass;", result.get(0));
    }

    @Test
    public void testGenerateStatic() {
        cut.setStatic(true);
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(1, result.size(), "Wrong number of lines");
        assertEquals("import static de.abc.ImportClass;", result.get(0));
    }

    @Test
    public void testCompare() {
        List<Import> imports = new ArrayList<>();
        imports.add(new Import("aa.abc.ImportClass", true));
        imports.add(new Import("de.abc.ImportClass", true));
        imports.add(new Import("aa.abc.ImportClass", false));
        imports.add(cut);

        TestUtil.checkComparisonOfSortedList(imports);
        TestUtil.checkSortingOfSortedList(imports);
    }
}
