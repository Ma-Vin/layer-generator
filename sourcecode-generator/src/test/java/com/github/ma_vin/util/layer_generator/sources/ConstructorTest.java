package com.github.ma_vin.util.layer_generator.sources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ConstructorTest {
    @Mock
    private JavaDoc javaDoc;

    private Constructor cut;

    @BeforeEach
    public void setUp() {
        cut = new Constructor("ConstructorName");
        initMocks(this);
    }

    @Test
    public void testGenerateDefault() {
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(2, result.size(), "Wrong number of lines");
        assertEquals("private ConstructorName() {", result.get(0));
        assertEquals("}", result.get(1));
    }

    @Test
    public void testGenerateBody() {
        cut.addLine("abc");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(3, result.size(), "Wrong number of lines");
        assertEquals("private ConstructorName() {", result.get(0));
        assertEquals(AbstractGenerateLines.TAB + "abc", result.get(1));
        assertEquals("}", result.get(2));
    }

    @Test
    public void testGenerateBodyViaConstructor() {
        cut = new Constructor("constructorName", "abc");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(3, result.size(), "Wrong number of lines");
        assertEquals("private ConstructorName() {", result.get(0));
        assertEquals(AbstractGenerateLines.TAB + "abc", result.get(1));
        assertEquals("}", result.get(2));
    }

    @Test
    public void testGenerateParameter() {
        cut.addParameter("parameterType", "parameterName");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(2, result.size(), "Wrong number of lines");
        assertEquals("private ConstructorName(parameterType parameterName) {", result.get(0));
        assertEquals("}", result.get(1));
    }

    @Test
    public void testGenerateMultiParameter() {
        cut.addParameter("p1Type", "p1Name");
        cut.addParameter("p2Type", "p2Name");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(2, result.size(), "Wrong number of lines");
        assertEquals("private ConstructorName(p1Type p1Name, p2Type p2Name) {", result.get(0));
        assertEquals("}", result.get(1));
    }

    @Test
    public void testGenerateMultiParameterLineBreak() {
        cut.addParameter("p1Type", "p1Name");
        cut.addParameter("p2Type", "p2Name");
        cut.addParameter("p3Type", "p3Name");
        cut.addParameter("p4Type", "p4Name");
        cut.addParameter("p5Type", "p5Name");
        cut.addParameter("p6Type", "p6Name");
        cut.addParameter("p7Type", "p7Name");
        cut.addParameter("p8Type", "p8Name");
        cut.addParameter("p9Type", "p9Name");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(3, result.size(), "Wrong number of lines");
        assertEquals("private ConstructorName(p1Type p1Name, p2Type p2Name, p3Type p3Name, p4Type p4Name, p5Type p5Name, p6Type p6Name, p7Type p7Name, p8Type p8Name", result.get(0));
        assertEquals("		, p9Type p9Name) {", result.get(1));
        assertEquals("}", result.get(2));
    }


    @Test
    public void testGenerateQualifier() {
        cut.setQualifier(Qualifier.PUBLIC);
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(2, result.size(), "Wrong number of lines");
        assertEquals("public ConstructorName() {", result.get(0));
        assertEquals("}", result.get(1));
    }


    @Test
    public void testGenerateJavaDoc() {
        when(javaDoc.generate()).thenReturn(Arrays.asList("commentPlaceHolder"));
        cut.setJavaDoc(javaDoc);
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(3, result.size(), "Wrong number of lines");
        assertEquals("commentPlaceHolder", result.get(0));
        assertEquals("private ConstructorName() {", result.get(1));
        assertEquals("}", result.get(2));
    }

    @Test
    public void testGenerateAndAddAnnotation() {
        cut.addAnnotation("TestAnnotation");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(3, result.size(), "Wrong number of lines");
        assertEquals("@TestAnnotation", result.get(0));
        assertEquals("private ConstructorName() {", result.get(1));
        assertEquals("}", result.get(2));
    }

    @Test
    public void testGenerateAndAddAnnotationWithParameter() {
        cut.addAnnotation("TestAnnotation", "parameterName", "parameterValue");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(3, result.size(), "Wrong number of lines");
        assertEquals("@TestAnnotation(parameterName = parameterValue)", result.get(0));
        assertEquals("private ConstructorName() {", result.get(1));
        assertEquals("}", result.get(2));
    }

    @Test
    void testCompare() {
        List<Method> methods = new ArrayList<>();
        methods.add(getConstructor("A", true));
        methods.add(getConstructor("A", true, "p1Type", "p1Name"));
        methods.add(getConstructor("A", true, "p2Type", "p2Name"));
        methods.add(getConstructor("B", true));
        methods.add(getConstructor("A", false));

        TestUtil.checkComparisonOfSortedList(methods);
        TestUtil.checkSortingOfSortedList(methods);
    }

    private Constructor getConstructor(String methodName, boolean isStatic, String... parameters) {
        Constructor result = new Constructor(methodName);
        result.setStatic(isStatic);
        if (parameters.length % 2 == 1) {
            fail("number of parameters are not even");
        }
        for (int i = 0; i < parameters.length; i += 2) {
            result.addParameter(parameters[i], parameters[i + 1]);
        }
        return result;
    }
}
