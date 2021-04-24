package de.ma_vin.util.layer.generator.sources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.ma_vin.util.layer.generator.sources.JavaDoc.JAVA_DOC_MAX_LENGTH;
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

    @Test
    public void testGenerateAddLineFormat() {
        cut.addLine("%sb%s", "a", "c");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(3, result.size(), "Wrong number of lines");
        assertEquals("/**", result.get(0), "Wrong first element");
        assertEquals(" * abc", result.get(1), "Wrong second element");
        assertEquals(" */", result.get(2), "Wrong third element");
    }

    @Test
    public void testGenerateAddLineBreak() {
        String line = getStringRepeating("a ", (JAVA_DOC_MAX_LENGTH + 3) / 2);
        cut.addLine(line);
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(4, result.size(), "Wrong number of lines");
        assertEquals("/**", result.get(0), "Wrong first element");
        assertEquals(" * " + line.substring(0, JAVA_DOC_MAX_LENGTH).trim(), result.get(1), "Wrong second element");
        assertEquals(" * a", result.get(2), "Wrong third element");
        assertEquals(" */", result.get(3), "Wrong forth element");
    }

    @Test
    public void testGenerateAddLineBreakDoNotSplitCurledBrackets() {
        String line = getStringRepeating("a ", (JAVA_DOC_MAX_LENGTH) / 2 - 6);
        line += "{some bracket}";
        cut.addLine(line);
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(4, result.size(), "Wrong number of lines");
        assertEquals("/**", result.get(0), "Wrong first element");
        assertEquals(" * " + line.substring(0, JAVA_DOC_MAX_LENGTH - "{some bracket}".length()).trim(), result.get(1), "Wrong second element");
        assertEquals(" * {some bracket}", result.get(2), "Wrong third element");
        assertEquals(" */", result.get(3), "Wrong forth element");
    }

    @Test
    public void testGenerateParam() {
        cut.addParams("first", "description");
        cut.addParams("second", "another description");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(4, result.size(), "Wrong number of lines");
        assertEquals("/**", result.get(0), "Wrong first element");
        assertEquals(" * @param first  description", result.get(1), "Wrong second element");
        assertEquals(" * @param second another description", result.get(2), "Wrong third element");
        assertEquals(" */", result.get(3), "Wrong forth element");
    }

    @Test
    public void testGenerateParamFormatted() {
        cut.addParams("first", "description %s is formatted", "which");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(3, result.size(), "Wrong number of lines");
        assertEquals("/**", result.get(0), "Wrong first element");
        assertEquals(" * @param first description which is formatted", result.get(1), "Wrong second element");
        assertEquals(" */", result.get(2), "Wrong third element");
    }

    @Test
    public void testGenerateParamLineBreak() {
        String firstParam = "first";
        String firstDescription = getStringRepeating("a "
                , (JAVA_DOC_MAX_LENGTH - JavaDoc.PARAM.length() - firstParam.length()) / 2);

        cut.addParams(firstParam, firstDescription);
        cut.addParams("second", "another description");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(5, result.size(), "Wrong number of lines");
        assertEquals("/**", result.get(0), "Wrong first element");
        assertEquals(" * @param first  " + firstDescription.substring(0, firstDescription.length() - 2).trim(), result.get(1), "Wrong second element");
        assertEquals(" *               a", result.get(2), "Wrong third element");
        assertEquals(" * @param second another description", result.get(3), "Wrong forth element");
        assertEquals(" */", result.get(4), "Wrong fifth element");
    }

    @Test
    public void testGenerateThrows() {
        cut.addThrows("first", "description");
        cut.addThrows("second", "another description");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(4, result.size(), "Wrong number of lines");
        assertEquals("/**", result.get(0), "Wrong first element");
        assertEquals(" * @throws first  description", result.get(1), "Wrong second element");
        assertEquals(" * @throws second another description", result.get(2), "Wrong third element");
        assertEquals(" */", result.get(3), "Wrong forth element");
    }

    @Test
    public void testGenerateThrowsLineBreak() {
        String firstThrow = "first";
        String secondThrow = "second";
        String firstDescription = getStringRepeating("a "
                , (JAVA_DOC_MAX_LENGTH - JavaDoc.THROWS.length() - secondThrow.length()) / 2);

        cut.addThrows(firstThrow, firstDescription);
        cut.addThrows(secondThrow, "another description");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(5, result.size(), "Wrong number of lines");
        assertEquals("/**", result.get(0), "Wrong first element");
        assertEquals(" * @throws first  " + firstDescription.substring(0, firstDescription.length() - 2).trim(), result.get(1), "Wrong second element");
        assertEquals(" *                a", result.get(2), "Wrong third element");
        assertEquals(" * @throws second another description", result.get(3), "Wrong forth element");
        assertEquals(" */", result.get(4), "Wrong fifth element");
    }

    @Test
    public void testGenerateReturn() {
        cut.setReturnDescription("description");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(3, result.size(), "Wrong number of lines");
        assertEquals("/**", result.get(0), "Wrong first element");
        assertEquals(" * @return description", result.get(1), "Wrong second element");
        assertEquals(" */", result.get(2), "Wrong third element");
    }

    @Test
    public void testGenerateReturnFormatted() {
        cut.setReturnDescription("description %s is formatted", "which");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(3, result.size(), "Wrong number of lines");
        assertEquals("/**", result.get(0), "Wrong first element");
        assertEquals(" * @return description which is formatted", result.get(1), "Wrong second element");
        assertEquals(" */", result.get(2), "Wrong third element");
    }

    @Test
    public void testGenerateReturnLineBreak() {
        String description = getStringRepeating("a "
                , (JAVA_DOC_MAX_LENGTH - JavaDoc.RETURN.length() + 3) / 2);
        cut.setReturnDescription(description);
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(4, result.size(), "Wrong number of lines");
        assertEquals("/**", result.get(0), "Wrong first element");
        assertEquals(" * @return " + description.substring(0, description.length() - 2).trim(), result.get(1), "Wrong second element");
        assertEquals(" *         a", result.get(2), "Wrong third element");
        assertEquals(" */", result.get(3), "Wrong forth element");
    }

    @Test
    public void testGenerateAddLineAndEmptyLine() {
        cut.addLine("abc");
        cut.addParams("first", "description");
        List<String> result = cut.generate();
        assertNotNull(result, "There should be any result");
        assertEquals(5, result.size(), "Wrong number of lines");
        assertEquals("/**", result.get(0), "Wrong first element");
        assertEquals(" * abc", result.get(1), "Wrong second element");
        assertEquals(" *", result.get(2), "Wrong third element");
        assertEquals(" * @param first description", result.get(3), "Wrong forth element");
        assertEquals(" */", result.get(4), "Wrong fifth element");
    }

    private static String getStringRepeating(String toRepeat, int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append(toRepeat);
        }
        return sb.toString();
    }
}
