package de.ma_vin.util.layer.generator.sources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AttributeTest {
    @Mock
    private JavaDoc javaDoc;

    private Attribute cut;

    @BeforeEach
    public void setUp() {
        cut = new Attribute("attributeName", "String");
        initMocks(this);
    }

    @Test
    public void testGenerateDefault() {
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(1, result.size(), "Wrong number of lines");
        assertEquals("private String attributeName;", result.get(0));
    }

    @Test
    public void testGenerateInitValue() {
        cut.setInitValue("abc");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(1, result.size(), "Wrong number of lines");
        assertEquals("private String attributeName = abc;", result.get(0));
    }

    @Test
    public void testGenerateQualifier() {
        cut.setQualifier(Qualifier.PUBLIC);
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(1, result.size(), "Wrong number of lines");
        assertEquals("public String attributeName;", result.get(0));
    }

    @Test
    public void testGenerateFinal() {
        cut.setFinal(true);
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(1, result.size(), "Wrong number of lines");
        assertEquals("private final String attributeName;", result.get(0));
    }

    @Test
    public void testGenerateStatic() {
        cut.setStatic(true);
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(1, result.size(), "Wrong number of lines");
        assertEquals("private static String attributeName;", result.get(0));
    }

    @Test
    public void testGenerateJavaDoc() {
        when(javaDoc.generate()).thenReturn(Arrays.asList("commentPlaceHolder"));
        cut.setJavaDoc(javaDoc);
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(2, result.size(), "Wrong number of lines");
        assertEquals("commentPlaceHolder", result.get(0));
        assertEquals("private String attributeName;", result.get(1));
    }

    @Test
    public void testGenerateAndAddAnnotation() {
        cut.addAnnotation("TestAnnotation");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(2, result.size(), "Wrong number of lines");
        assertEquals("@TestAnnotation", result.get(0));
        assertEquals("private String attributeName;", result.get(1));
    }

    @Test
    public void testGenerateAndAddAnnotationWithParameter() {
        cut.addAnnotation("TestAnnotation", "parameterName", "parameterValue");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(2, result.size(), "Wrong number of lines");
        assertEquals("@TestAnnotation(parameterName = parameterValue)", result.get(0));
        assertEquals("private String attributeName;", result.get(1));
    }

    @Test
    void testCompare() {
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(getStringAttribute("A", true, true));
        attributes.add(getStringAttribute("B", true, true));
        attributes.add(getStringAttribute("A", true, false));
        attributes.add(getStringAttribute("B", true, false));
        attributes.add(getStringAttribute("A", false, true));
        attributes.add(getStringAttribute("B", false, true));
        attributes.add(getStringAttribute("A", false, false));
        attributes.add(getStringAttribute("B", false, false));

        TestUtil.checkComparisonOfSortedList(attributes);
        TestUtil.checkSortingOfSortedList(attributes);
    }

    private Attribute getStringAttribute(String attributeName, boolean isStatic, boolean isFinal) {
        Attribute result = new Attribute(attributeName, "String");
        result.setStatic(isStatic);
        result.setFinal(isFinal);
        return result;
    }
}
