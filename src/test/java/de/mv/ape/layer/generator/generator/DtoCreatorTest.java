package de.mv.ape.layer.generator.generator;

import de.mv.ape.layer.generator.config.elements.Models;
import de.mv.ape.layer.generator.log.LogImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class DtoCreatorTest extends AbstractCreatorTest {
    private DtoCreator cut;

    @BeforeEach
    public void setUp() {
        super.setUp();

        cut = new DtoCreator(config, new LogImpl()) {
            @Override
            protected BufferedWriter createBufferedWriter(File classFile) {
                List<String> fileContent = new ArrayList<>();
                writtenFileContents.put(classFile.getName(), fileContent);
                try {
                    // Assumption: after write is als a newLine statement
                    doAnswer(a -> fileContent.add(a.getArgument(0))).when(bufferedWriter).write(anyString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bufferedWriter;
            }

            @Override
            protected File createFile(File dir, String fileName) {
                File createdFile = mock(File.class);
                when(createdFile.getName()).thenReturn(fileName);
                when(createdFile.getParentFile()).thenReturn(dir);
                return createdFile;
            }
        };
    }

    @Test
    public void testCreateDataTransportObjectDefault() {
        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto.group;");
        expected.add("");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dto class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@NoArgsConstructor()");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class DummyDto {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

        checkSingleFile("DummyDto.java", expected);
    }


    @Test
    public void testCreateDataTransportObjectNoDomain() {
        when(entity.getModels()).thenReturn(Models.DAO);

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

        assertEquals(0, writtenFileContents.size(), "Wrong number of files");
    }

    @Test
    public void testCreateDataTransportObjectNoGrouping() {
        when(entity.getGrouping()).thenReturn(null);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto;");
        expected.add("");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dto class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@NoArgsConstructor()");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class DummyDto {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

        checkSingleFile("DummyDto.java", expected);
    }

    @Test
    public void testCreateDataTransportObjectUniqueRelation() {
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto.group;");
        expected.add("");
        expected.add("import de.test.package.dto.group.TargetDto;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dto class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@EqualsAndHashCode(exclude = {\"targetRef\"})");
        expected.add("@NoArgsConstructor()");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("@ToString(exclude = {\"targetRef\"})");
        expected.add("public class DummyDto {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	private TargetDto targetRef;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

        checkSingleFile("DummyDto.java", expected);
    }

    @Test
    public void testCreateDataTransportObjectRelation() {
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto.group;");
        expected.add("");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dto class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@NoArgsConstructor()");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class DummyDto {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

        checkSingleFile("DummyDto.java", expected);
    }

    @Test
    public void testCreateDataTransportObjectField() {
        when(entity.getFields()).thenReturn(Arrays.asList(field));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto.group;");
        expected.add("");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dto class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@NoArgsConstructor()");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class DummyDto {");
        expected.add("");
        expected.add("	private String anyField;");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

        checkSingleFile("DummyDto.java", expected);
    }

    @Test
    public void testCreateDataTransportObjectFieldEnum() {
        when(entity.getFields()).thenReturn(Arrays.asList(field));
        when(field.isTypeEnum()).thenReturn(Boolean.TRUE);
        when(field.getType()).thenReturn("AnyEnum");
        when(field.getTypePackage()).thenReturn("the.enum.package");

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto.group;");
        expected.add("");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("import the.enum.package.AnyEnum;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dto class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@NoArgsConstructor()");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class DummyDto {");
        expected.add("");
        expected.add("	private AnyEnum anyField;");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");


        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

        checkSingleFile("DummyDto.java", expected);
    }

    @Test
    public void testCreateDataTransportObjectUseIdGenerator() {
        when(config.isUseIdGenerator()).thenReturn(Boolean.TRUE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto.group;");
        expected.add("");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dto class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@NoArgsConstructor()");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class DummyDto {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Identification of Dummy");
        expected.add("	 */");
        expected.add("	private String identification;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

        checkSingleFile("DummyDto.java", expected);
    }
}
