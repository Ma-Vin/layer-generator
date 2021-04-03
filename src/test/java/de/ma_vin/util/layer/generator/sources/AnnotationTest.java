package de.ma_vin.util.layer.generator.sources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AnnotationTest {
    private Annotation cut;

    @BeforeEach
    public void setUp() {
        cut = new Annotation("AnnotationName");
    }

    @Test
    public void testGenerateDefault() {
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(1, result.size(), "Wrong number of lines");
        assertEquals("@AnnotationName()", result.get(0));
    }

    @Test
    public void testGenerateParameter() {
        cut.addParameter("parameterName", "parameterValue");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(1, result.size(), "Wrong number of lines");
        assertEquals("@AnnotationName(parameterName = parameterValue)", result.get(0));
    }

    @Test
    public void testGenerateNoParameterName() {
        cut.addParameter(null, "parameterValue");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(1, result.size(), "Wrong number of lines");
        assertEquals("@AnnotationName(parameterValue)", result.get(0));
    }

    @Test
    public void testGenerateAddValue() {
        cut.addValue("parameterValue");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(1, result.size(), "Wrong number of lines");
        assertEquals("@AnnotationName(parameterValue)", result.get(0));
    }

    @Test
    public void testGenerateParameters() {
        cut.addParameter("p1Name", "p1Value");
        cut.addParameter("p2Name", "p2Value");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(1, result.size(), "Wrong number of lines");
        assertEquals("@AnnotationName(p1Name = p1Value, p2Name = p2Value)", result.get(0));
    }

    @Test
    public void testGenerateMultiParameter() {
        cut.addParameterArray("parameterName", "parameterValue");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(1, result.size(), "Wrong number of lines");
        assertEquals("@AnnotationName(parameterName = {parameterValue})", result.get(0));
    }

    @Test
    public void testGenerateMultiParameters() {
        cut.addParameterArray("parameterName", "parameterValue1", "parameterValue2");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(1, result.size(), "Wrong number of lines");
        assertEquals("@AnnotationName(parameterName = {parameterValue1, parameterValue2})", result.get(0));
    }

    @Test
    public void testCompare() {
        List<Annotation> annotations = new ArrayList<>();
        annotations.add(createAnnotation("A", "p1", "v1"));
        annotations.add(createAnnotation("A", "p1", "v2"));
        annotations.add(createAnnotation("A", "p2", "v1"));
        annotations.add(createAnnotation("A", "p2", "v2"));
        annotations.add(createAnnotation("B", "p1", "v1"));
        annotations.add(createAnnotation("B", "p1", "v2"));
        annotations.add(createAnnotation("B", "p2", "v1"));
        annotations.add(createAnnotation("B", "p2", "v2"));

        TestUtil.checkComparisonOfSortedList(annotations);
        TestUtil.checkSortingOfSortedList(annotations);
    }

    private Annotation createAnnotation(String annotationName, String parameterName, String parameterValue) {
        Annotation result = new Annotation(annotationName);
        result.addParameter(parameterName, parameterValue);
        return result;
    }
}
