package de.mv.ape.layer.generator.sources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static de.mv.ape.layer.generator.sources.AbstractGenerateLines.TAB;

public class InterfaceTest {

    @Mock
    private JavaDoc javaDoc;
    @Mock
    private Annotation annotation;

    private Interface cut;

    @BeforeEach
    public void setUp() {
        cut = new Interface("de.abc", "Dummy");
        initMocks(this);
    }

    @Test
    public void testGenerateDefault() {
        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("public interface Dummy {");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testGenerateImports() {
        cut.addImport("de.abc.Something");

        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("import de.abc.Something;");
        expected.add("");
        expected.add("public interface Dummy {");
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
        expected.add("public interface Dummy {");
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
        expected.add("public interface Dummy {");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testAddDeclaration() {
        when(annotation.generate()).thenReturn(Arrays.asList("annotationPlaceHolder"));
        when(javaDoc.generate()).thenReturn(Arrays.asList("commentPlaceHolder"));

        Interface.MethodDeclaration declaration = new Interface.MethodDeclaration("anyMethod");
        declaration.getAnnotations().add(annotation);
        declaration.setJavaDoc(javaDoc);
        cut.addMethodDeclaration(declaration);

        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("public interface Dummy {");
        expected.add("");
        expected.add(TAB + "commentPlaceHolder");
        expected.add(TAB + "annotationPlaceHolder");
        expected.add(TAB + "void anyMethod();");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testAddDeclarationParams() {
        cut.addMethodDeclaration("BigDecimal","anyMethod", "BigDecimal", "value");

        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("public interface Dummy {");
        expected.add("");
        expected.add(TAB + "BigDecimal anyMethod(BigDecimal value);");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testAddDeclarationParamsWrongNumber() {
        cut.addMethodDeclaration("BigDecimal","anyMethod", "BigDecimal");

        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("public interface Dummy {");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testAddMethodDeclarationWithDescription() {
        cut.addMethodDeclarationWithDescription("BigDecimal","anyMethod","anything", "BigDecimal", "value");

        List<String> expected = new ArrayList<>();
        expected.add("package de.abc;");
        expected.add("");
        expected.add("public interface Dummy {");
        expected.add("");
        expected.add(TAB +"/**");
        expected.add(TAB +" * anything");
        expected.add(TAB +" */");
        expected.add(TAB + "BigDecimal anyMethod(BigDecimal value);");
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
        expected.add("public interface Dummy extends Pow {");
        expected.add("");
        expected.add("}");

        List<String> result = cut.generate();

        TestUtil.checkList(expected, result);
    }

    @Test
    public void testCompare() {
        List<Interface> interfaces = new ArrayList<>();
        interfaces.add(cut);
        interfaces.add(new Interface("de.abc", "Emmy"));

        TestUtil.checkComparisonOfSortedList(interfaces);
        TestUtil.checkSortingOfSortedList(interfaces);
    }
}
