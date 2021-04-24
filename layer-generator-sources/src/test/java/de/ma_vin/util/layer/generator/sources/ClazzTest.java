package de.ma_vin.util.layer.generator.sources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static de.ma_vin.util.layer.generator.sources.AbstractGenerateLines.TAB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ClazzTest {
    @Mock
    private Annotation annotation;
    @Mock
    private Attribute attribute;
    @Mock
    private JavaDoc javaDoc;
    @Mock
    private Method method;
    @Mock
    private Constructor constructor;
    @Mock
    private Clazz innerClazz;
    @Mock
    private Interface innerInterface;

    private Clazz cut;

    @BeforeEach
    public void setUp() {
        cut = new Clazz("de.abc", "Dummy");
        initMocks(this);
    }

    @Test
    public void testGenerateDefault() {
        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("public class Dummy {");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testGenerateStatic() {
        cut.setStatic(true);

        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("public static class Dummy {");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testGenerateAbstract() {
        cut.setAbstract(true);

        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("public abstract class Dummy {");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }


    @Test
    public void testGenerateInner() {
        cut = new Clazz("Dummy");
        cut.addImport("de.abc.Something");
        cut.addStaticImport("de.abc.Anything.*");

        List<String> expected = new ArrayList<>();
        expected.add("public class Dummy {");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testGenerateImports() {
        cut.addImport("de.abc.sub.Something");
        cut.addStaticImport("de.abc.sub.Anything.*");

        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("import static de.abc.sub.Anything.*;");
        expected.add("");
        expected.add("import de.abc.sub.Something;");
        expected.add("");
        expected.add("public class Dummy {");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testGenerateImportsSamePackage() {
        cut.addImport("de.abc.Something");
        cut.addStaticImport("de.abc.Anything");

        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("public class Dummy {");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testGenerateImportsSamePackageButStaticMethods() {
        cut.addImport("de.abc.Something");
        cut.addStaticImport("de.abc.Anything.*");

        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("import static de.abc.Anything.*;");
        expected.add("");
        expected.add("public class Dummy {");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testGenerateJavaDoc() {
        when(javaDoc.generate()).thenReturn(Arrays.asList("commentPlaceHolder"));
        cut.setDescription(javaDoc);

        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("commentPlaceHolder");
        expected.add("public class Dummy {");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testGenerateJavaDocFormatted() {
        cut.setDescription("Some %s description", "value");

        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Some value description");
        expected.add(" */");
        expected.add("public class Dummy {");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testGenerateAddAnnotationText() {
        cut.addAnnotation("Annotation");

        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("@Annotation");
        expected.add("public class Dummy {");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testGenerateAddAnnotation() {
        when(annotation.generate()).thenReturn(Arrays.asList("annotationPlaceHolder"));
        cut.addAnnotation(annotation);

        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("annotationPlaceHolder");
        expected.add("public class Dummy {");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testGenerateAttributes() {
        when(attribute.generate()).thenReturn(Arrays.asList("attributePlaceHolder"));
        doCallRealMethod().when(attribute).generate(anyInt());
        doCallRealMethod().when(attribute).getTabs(anyInt());
        cut.addAttribute(attribute);

        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("public class Dummy {");
        expected.add("");
        expected.add(TAB + "attributePlaceHolder");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testGenerateConstructors() {
        when(method.generate()).thenReturn(Arrays.asList("constructorPlaceHolder"));
        doCallRealMethod().when(method).generate(anyInt());
        doCallRealMethod().when(method).getTabs(anyInt());
        cut.addMethod(method);

        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("public class Dummy {");
        expected.add("");
        expected.add(TAB + "constructorPlaceHolder");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testGenerateMethods() {
        when(constructor.generate()).thenReturn(Arrays.asList("methodPlaceHolder"));
        doCallRealMethod().when(constructor).generate(anyInt());
        doCallRealMethod().when(constructor).getTabs(anyInt());
        cut.addMethod(constructor);

        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("public class Dummy {");
        expected.add("");
        expected.add(TAB + "methodPlaceHolder");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testGenerateInnerClazz() {
        when(innerClazz.generate()).thenReturn(Arrays.asList("innerClazzPlaceHolder"));
        doCallRealMethod().when(innerClazz).generate(anyInt());
        doCallRealMethod().when(innerClazz).getTabs(anyInt());
        cut.addInnerClazz(innerClazz);

        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("public class Dummy {");
        expected.add("");
        expected.add(TAB + "innerClazzPlaceHolder");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testGenerateInterface() {
        cut.addInterface("Bam");
        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("public class Dummy implements Bam {");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testGenerateInnerInterface() {
        when(innerInterface.generate()).thenReturn(Arrays.asList("innerInterfacePlaceHolder"));
        doCallRealMethod().when(innerInterface).generate(anyInt());
        doCallRealMethod().when(innerInterface).getTabs(anyInt());
        cut.addInnerInterface(innerInterface);

        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("public class Dummy {");
        expected.add("");
        expected.add(TAB + "innerInterfacePlaceHolder");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testGenerateMultiInterface() {
        cut.addInterface("Bam");
        cut.addInterface("Boom");
        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("public class Dummy implements Bam, Boom {");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testGenerateExtension() {
        cut.setExtension("Pow");
        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("public class Dummy extends Pow {");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testGenerateGenerics() {
        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("public class Dummy<S, T extends U> {");
        expected.add("");
        expected.add("}");

        cut.addGeneric("S");
        Generic secondGeneric = new Generic("T");
        secondGeneric.setExtension("U");
        cut.addGeneric(secondGeneric);

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testGetAnnotation() {
        cut.addAnnotation(annotation);
        when(annotation.getAnnotationName()).thenReturn("AnyName");

        Optional<Annotation> result = cut.getAnnotation("AnyName");
        assertNotNull(result, "There should be some result");
        assertTrue(result.isPresent(), "The result should contain some element");
        assertEquals("AnyName", result.get().getAnnotationName(), "The annotation has the wrong name");

        result = cut.getAnnotation("AnyOtherName");
        assertNotNull(result, "There should be some result");
        assertTrue(result.isEmpty(), "The result should not contain any element");
    }

    @Test
    public void testCompare() {
        List<Clazz> clazzes = new ArrayList<>();
        clazzes.add(cut);
        clazzes.add(new Clazz("de.abc", "Emmy"));

        TestUtil.checkComparisonOfSortedList(clazzes);
        TestUtil.checkSortingOfSortedList(clazzes);
    }
}
