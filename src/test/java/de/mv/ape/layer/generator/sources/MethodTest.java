package de.mv.ape.layer.generator.sources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class MethodTest {
    @Mock
    private JavaDoc javaDoc;

    private Method cut;

    @BeforeEach
    public void setUp() {
        cut = new Method("methodName");
        initMocks(this);
    }

    @Test
    public void testGenerateDefault() {
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(2, result.size(), "Wrong number of lines");
        assertEquals("private void methodName() {", result.get(0));
        assertEquals("}", result.get(1));
    }

    @Test
    public void testGenerateBody() {
        cut.addLine("abc");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(3, result.size(), "Wrong number of lines");
        assertEquals("private void methodName() {", result.get(0));
        assertEquals(AbstractGenerateLines.TAB + "abc", result.get(1));
        assertEquals("}", result.get(2));
    }

    @Test
    public void testGenerateBodyViaConstructor() {
        cut = new Method("methodName", "abc");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(3, result.size(), "Wrong number of lines");
        assertEquals("private void methodName() {", result.get(0));
        assertEquals(AbstractGenerateLines.TAB + "abc", result.get(1));
        assertEquals("}", result.get(2));
    }

    @Test
    public void testGenerateParameter() {
        cut.addParameter("parameterType", "parameterName");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(2, result.size(), "Wrong number of lines");
        assertEquals("private void methodName(parameterType parameterName) {", result.get(0));
        assertEquals("}", result.get(1));
    }

    @Test
    public void testGenerateMultiParameter() {
        cut.addParameter("p1Type", "p1Name");
        cut.addParameter("p2Type", "p2Name");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(2, result.size(), "Wrong number of lines");
        assertEquals("private void methodName(p1Type p1Name, p2Type p2Name) {", result.get(0));
        assertEquals("}", result.get(1));
    }

    @Test
    public void testGenerateQualifier() {
        cut.setQualifier("public");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(2, result.size(), "Wrong number of lines");
        assertEquals("public void methodName() {", result.get(0));
        assertEquals("}", result.get(1));
    }


    @Test
    public void testGenerateStatic() {
        cut.setStatic(true);
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(2, result.size(), "Wrong number of lines");
        assertEquals("private static void methodName() {", result.get(0));
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
        assertEquals("private void methodName() {", result.get(1));
        assertEquals("}", result.get(2));
    }

    @Test
    public void testGenerateAndAddAnnotation() {
        cut.addAnnotation("TestAnnotation");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(3, result.size(), "Wrong number of lines");
        assertEquals("@TestAnnotation()", result.get(0));
        assertEquals("private void methodName() {", result.get(1));
        assertEquals("}", result.get(2));
    }

    @Test
    public void testGenerateAndAddAnnotationWithParameter() {
        cut.addAnnotation("TestAnnotation", "parameterName", "parameterValue");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(3, result.size(), "Wrong number of lines");
        assertEquals("@TestAnnotation(parameterName = parameterValue)", result.get(0));
        assertEquals("private void methodName() {", result.get(1));
        assertEquals("}", result.get(2));
    }

    @Test
    void testCompare() {
        List<Method> methods = new ArrayList<>();
        methods.add(getMethod("A", true));
        methods.add(getMethod("A", true, "p1Type", "p1Name"));
        methods.add(getMethod("A", true, "p2Type", "p2Name"));
        methods.add(getMethod("B", true));
        methods.add(getMethod("A", false));

        TestUtil.checkComparisonOfSortedList(methods);
        TestUtil.checkSortingOfSortedList(methods);
    }

    private Method getMethod(String methodName, boolean isStatic, String... parameters) {
        Method result = new Method(methodName);
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
