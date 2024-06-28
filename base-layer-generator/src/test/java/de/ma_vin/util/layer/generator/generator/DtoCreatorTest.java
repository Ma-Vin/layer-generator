package de.ma_vin.util.layer.generator.generator;

import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.config.elements.Models;
import de.ma_vin.util.layer.generator.config.elements.Version;
import de.ma_vin.util.layer.generator.config.elements.references.Reference;
import com.github.ma_vin.util.layer_generator.logging.Log4jLogImpl;
import com.github.ma_vin.util.layer_generator.sources.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.tools.JavaFileObject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class DtoCreatorTest extends AbstractCreatorTest {

    @Mock
    private Entity parentEntity;
    @Mock
    private Entity copyEntity;
    @Mock
    private Version version;
    @Mock
    private Version targetVersion;

    private DtoCreator cut;

    private final List<String> directoriesWhereRequestedToWrite = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        super.setUp();

        cut = new DtoCreator(config, new Log4jLogImpl()) {
            @Override
            protected BufferedWriter createBufferedWriter(File classFile) {
                return mockBufferedWriter(classFile.getName());
            }

            @Override
            protected BufferedWriter createBufferedWriter(JavaFileObject javaFileObject) {
                return mockBufferedWriter(javaFileObject.getName());
            }

            @Override
            protected File createFile(File dir, String fileName) {
                directoriesWhereRequestedToWrite.add(dir.getName());
                File createdFile = mock(File.class);
                when(createdFile.getName()).thenReturn(fileName);
                when(createdFile.getParentFile()).thenReturn(dir);
                return createdFile;
            }
        };
    }

    @DisplayName("create data transport object with common file")
    @Test
    public void testCreateDataTransportObjectCommonFile() {
        List<String> expected = getDefaultExpected();

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", Optional.of(basePackageDir)));

        checkSingleFile("DummyDto.java", expected);

        verify(processingEnv, never()).getFiler();
    }

    @DisplayName("create data transport object with java file object")
    @Test
    public void testCreateDataTransportObjectJavaFileObject() throws IOException {
        List<String> expected = getDefaultExpected();
        cut.setGenerateJavaFileObject(true);
        cut.setProcessingEnv(Optional.of(processingEnv));

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", Optional.empty()));

        checkSingleFile(String.format("%s.dto.group.DummyDto", BASE_PACKAGE), expected);

        verify(processingEnv).getFiler();
        verify(filer).createSourceFile(eq(String.format("%s.dto.group.DummyDto", BASE_PACKAGE)));
    }

    private List<String> getDefaultExpected() {
        List<String> expected = new ArrayList<>();

        expected.add("package de.test.package.dto.group;");
        expected.add("");
        expected.add("import de.ma_vin.util.layer.generator.annotations.model.BaseDto;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dto class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDto(\"de.test.package.dto\")");
        expected.add("@Data");
        expected.add("@NoArgsConstructor");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class DummyDto implements ITransportable {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        return expected;
    }


    @Test
    public void testCreateDataTransportObjectNoDto() {
        when(entity.getModels()).thenReturn(Models.DAO);

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", Optional.of(basePackageDir)));

        assertEquals(0, writtenFileContents.size(), "Wrong number of files");
    }

    @DisplayName("Create object with id for only dto entity")
    @Test
    public void testCreateDataTransportObjectOnlyDto() {
        when(entity.getModels()).thenReturn(Models.DTO);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto.group;");
        expected.add("");
        expected.add("import de.ma_vin.util.layer.generator.annotations.model.BaseDto;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dto class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDto(\"de.test.package.dto\")");
        expected.add("@Data");
        expected.add("@NoArgsConstructor");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class DummyDto implements ITransportable {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", Optional.of(basePackageDir)));

        checkSingleFile("DummyDto.java", expected);
    }

    @DisplayName("Create object without id for only dto entity")
    @Test
    public void testCreateDataTransportObjectOnlyDtoWithoutId() {
        when(entity.getModels()).thenReturn(Models.DTO);
        when(entity.getGenIdIfDto()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto.group;");
        expected.add("");
        expected.add("import de.ma_vin.util.layer.generator.annotations.model.BaseDto;");
        expected.add("import de.test.package.dto.IBasicTransportable;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dto class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDto(\"de.test.package.dto\")");
        expected.add("@Data");
        expected.add("@NoArgsConstructor");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class DummyDto implements IBasicTransportable {");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", Optional.of(basePackageDir)));

        checkSingleFile("DummyDto.java", expected);
    }

    @Test
    public void testCreateDataTransportObjectNoGrouping() {
        when(entity.getGrouping()).thenReturn(null);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto;");
        expected.add("");
        expected.add("import de.ma_vin.util.layer.generator.annotations.model.BaseDto;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dto class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDto(\"de.test.package.dto\")");
        expected.add("@Data");
        expected.add("@NoArgsConstructor");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class DummyDto implements ITransportable {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", Optional.of(basePackageDir)));

        checkSingleFile("DummyDto.java", expected);
    }

    @Test
    public void testCreateDataTransportObjectUniqueRelation() {
        when(entity.getReferences()).thenReturn(Collections.singletonList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto.group;");
        expected.add("");
        expected.add("import de.ma_vin.util.layer.generator.annotations.model.BaseDto;");
        expected.add("import de.test.package.dto.ITransportable;");
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
        expected.add("@BaseDto(\"de.test.package.dto\")");
        expected.add("@Data");
        expected.add("@EqualsAndHashCode(exclude = {\"targetRef\"})");
        expected.add("@NoArgsConstructor");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("@ToString(exclude = {\"targetRef\"})");
        expected.add("public class DummyDto implements ITransportable {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	private TargetDto targetRef;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", Optional.of(basePackageDir)));

        checkSingleFile("DummyDto.java", expected);
    }


    @DisplayName("Create a data transport object with an one to one relation, but the target does not support the transport model")
    @Test
    public void testCreateDataTransportObjectUniqueRelationButNonDto() {
        when(entity.getReferences()).thenReturn(Collections.singletonList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(targetEntity.getModels()).thenReturn(Models.DOMAIN_DAO);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto.group;");
        expected.add("");
        expected.add("import de.ma_vin.util.layer.generator.annotations.model.BaseDto;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dto class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDto(\"de.test.package.dto\")");
        expected.add("@Data");
        expected.add("@NoArgsConstructor");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class DummyDto implements ITransportable {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", Optional.of(basePackageDir)));

        checkSingleFile("DummyDto.java", expected);
    }


    @Test
    public void testCreateDataTransportObjectRelation() {
        when(entity.getReferences()).thenReturn(Collections.singletonList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto.group;");
        expected.add("");
        expected.add("import de.ma_vin.util.layer.generator.annotations.model.BaseDto;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dto class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDto(\"de.test.package.dto\")");
        expected.add("@Data");
        expected.add("@NoArgsConstructor");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class DummyDto implements ITransportable {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", Optional.of(basePackageDir)));

        checkSingleFile("DummyDto.java", expected);
    }


    @DisplayName("Create a data transport object with an one to many relation, but the target does not support the transport model")
    @Test
    public void testCreateDataTransportObjectRelationButNotDto() {
        when(entity.getReferences()).thenReturn(Collections.singletonList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);
        when(targetEntity.getModels()).thenReturn(Models.DOMAIN_DAO);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto.group;");
        expected.add("");
        expected.add("import de.ma_vin.util.layer.generator.annotations.model.BaseDto;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dto class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDto(\"de.test.package.dto\")");
        expected.add("@Data");
        expected.add("@NoArgsConstructor");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class DummyDto implements ITransportable {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", Optional.of(basePackageDir)));

        checkSingleFile("DummyDto.java", expected);
    }

    @Test
    public void testCreateDataTransportObjectField() {
        when(entity.getFields()).thenReturn(Collections.singletonList(field));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto.group;");
        expected.add("");
        expected.add("import de.ma_vin.util.layer.generator.annotations.model.BaseDto;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dto class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDto(\"de.test.package.dto\")");
        expected.add("@Data");
        expected.add("@NoArgsConstructor");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class DummyDto implements ITransportable {");
        expected.add("");
        expected.add("	private String anyField;");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", Optional.of(basePackageDir)));

        checkSingleFile("DummyDto.java", expected);
    }

    @Test
    public void testCreateDataTransportObjectFieldEnum() {
        when(entity.getFields()).thenReturn(Collections.singletonList(field));
        when(field.getIsTypeEnum()).thenReturn(Boolean.TRUE);
        when(field.getType()).thenReturn("AnyEnum");
        when(field.getTypePackage()).thenReturn("the.enum.package");

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto.group;");
        expected.add("");
        expected.add("import de.ma_vin.util.layer.generator.annotations.model.BaseDto;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("import the.enum.package.AnyEnum;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dto class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDto(\"de.test.package.dto\")");
        expected.add("@Data");
        expected.add("@NoArgsConstructor");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class DummyDto implements ITransportable {");
        expected.add("");
        expected.add("	private AnyEnum anyField;");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");


        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", Optional.of(basePackageDir)));

        checkSingleFile("DummyDto.java", expected);
    }

    @Test
    public void testCreateDataTransportObjectUseIdGenerator() {
        when(config.isUseIdGenerator()).thenReturn(Boolean.TRUE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto.group;");
        expected.add("");
        expected.add("import de.ma_vin.util.layer.generator.annotations.model.BaseDto;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dto class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDto(\"de.test.package.dto\")");
        expected.add("@Data");
        expected.add("@NoArgsConstructor");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class DummyDto implements ITransportable {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Identification of Dummy");
        expected.add("	 */");
        expected.add("	private String identification;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", Optional.of(basePackageDir)));

        checkSingleFile("DummyDto.java", expected);
    }

    @Test
    public void testCreateDataTransportObjectInterface() {
        List<String> expected = new ArrayList<>();

        expected.add("package de.test.package.dto;");
        expected.add("");
        expected.add("public interface ITransportable extends IBasicTransportable {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the id of the dto");
        expected.add("	 */");
        expected.add("	Long getId();");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @param id the id of the dto");
        expected.add("	 */");
        expected.add("	void setId(Long id);");
        expected.add("");
        expected.add("}");

        List<String> basicExpected = new ArrayList<>();

        basicExpected.add("package de.test.package.dto;");
        basicExpected.add("");
        basicExpected.add("public interface IBasicTransportable {");
        basicExpected.add("");
        basicExpected.add("}");

        assertTrue(cut.createDataTransportObjectInterface(BASE_PACKAGE + ".dto", Optional.of(basePackageDir)));

        assertEquals(2, writtenFileContents.size(), "Wrong number of files");
        assertTrue(writtenFileContents.containsKey(DtoCreator.DTO_INTERFACE + ".java"));
        assertTrue(writtenFileContents.containsKey(DtoCreator.DTO_BASIC_INTERFACE + ".java"));

        if (expected.size() != writtenFileContents.get(DtoCreator.DTO_INTERFACE + ".java").size()
                || basicExpected.size() != writtenFileContents.get(DtoCreator.DTO_BASIC_INTERFACE + ".java").size()) {
            logFileContents();
        }
        TestUtil.checkList(expected, writtenFileContents.get(DtoCreator.DTO_INTERFACE + ".java"));
        TestUtil.checkList(basicExpected, writtenFileContents.get(DtoCreator.DTO_BASIC_INTERFACE + ".java"));
    }

    @Test
    public void testCreateDataTransportObjectInterfaceUseIdGenerator() {
        when(config.isUseIdGenerator()).thenReturn(Boolean.TRUE);
        List<String> expected = new ArrayList<>();

        expected.add("package de.test.package.dto;");
        expected.add("");
        expected.add("public interface ITransportable extends IBasicTransportable {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the identification of the dto");
        expected.add("	 */");
        expected.add("	String getIdentification();");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @param identification the identification of the dto");
        expected.add("	 */");
        expected.add("	void setIdentification(String identification);");
        expected.add("");
        expected.add("}");

        List<String> basicExpected = new ArrayList<>();

        basicExpected.add("package de.test.package.dto;");
        basicExpected.add("");
        basicExpected.add("public interface IBasicTransportable {");
        basicExpected.add("");
        basicExpected.add("}");

        assertTrue(cut.createDataTransportObjectInterface(BASE_PACKAGE + ".dto", Optional.of(basePackageDir)));

        assertEquals(2, writtenFileContents.size(), "Wrong number of files");
        assertTrue(writtenFileContents.containsKey(DtoCreator.DTO_INTERFACE + ".java"));
        assertTrue(writtenFileContents.containsKey(DtoCreator.DTO_BASIC_INTERFACE + ".java"));

        if (expected.size() != writtenFileContents.get(DtoCreator.DTO_INTERFACE + ".java").size()
                || basicExpected.size() != writtenFileContents.get(DtoCreator.DTO_BASIC_INTERFACE + ".java").size()) {
            logFileContents();
        }
        TestUtil.checkList(expected, writtenFileContents.get(DtoCreator.DTO_INTERFACE + ".java"));
        TestUtil.checkList(basicExpected, writtenFileContents.get(DtoCreator.DTO_BASIC_INTERFACE + ".java"));
    }

    @Test
    public void testCreateDataTransportObjectGroupingWithDots() {
        when(grouping.getGroupingPackage()).thenReturn("group.subgroup");

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto.group.subgroup;");
        expected.add("");
        expected.add("import de.ma_vin.util.layer.generator.annotations.model.BaseDto;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dto class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDto(\"de.test.package.dto\")");
        expected.add("@Data");
        expected.add("@NoArgsConstructor");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class DummyDto implements ITransportable {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", Optional.of(basePackageDir)));

        assertFalse(directoriesWhereRequestedToWrite.contains("group.subgroup"), "Not any directories with dots should be used");
        assertTrue(directoriesWhereRequestedToWrite.contains(String.format("group%ssubgroup", File.separator)), "Dot should be replaced by backslash");

        checkSingleFile("DummyDto.java", expected);
    }

    @Test
    public void testCreateDataTransportObjectIsAbstract() {
        when(entity.getIsAbstract()).thenReturn(Boolean.TRUE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto.group;");
        expected.add("");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dto class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data");
        expected.add("@NoArgsConstructor");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public abstract class DummyDto implements ITransportable {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", Optional.of(basePackageDir)));

        checkSingleFile("DummyDto.java", expected);
    }

    @Test
    public void testCreateDataTransportObjectHasSuperClass() {
        when(entity.getParent()).thenReturn("AnotherDummy");
        when(entity.getRealParent()).thenReturn(parentEntity);
        when(entity.getFields()).thenReturn(Collections.singletonList(field));
        when(entity.hasParent()).thenReturn(Boolean.TRUE);
        when(entity.hasNoParent()).thenReturn(Boolean.FALSE);
        when(parentEntity.getBaseName()).thenReturn("AnotherDummy");
        when(parentEntity.getModels()).thenReturn(Models.DOMAIN_DAO_DTO);
        when(parentEntity.getGrouping()).thenReturn(null);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto.group;");
        expected.add("");
        expected.add("import de.ma_vin.util.layer.generator.annotations.model.BaseDto;");
        expected.add("import de.test.package.dto.AnotherDummyDto;");
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
        expected.add("@BaseDto(\"de.test.package.dto\")");
        expected.add("@Data");
        expected.add("@EqualsAndHashCode(callSuper = true)");
        expected.add("@NoArgsConstructor");
        expected.add("@SuppressWarnings({\"java:S2160\", \"java:S1068\"})");
        expected.add("@ToString(callSuper = true)");
        expected.add("public class DummyDto extends AnotherDummyDto {");
        expected.add("");
        expected.add("	private String anyField;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", Optional.of(basePackageDir)));

        checkSingleFile("DummyDto.java", expected);
    }

    @Test
    public void testCreateDataTransportObjectUniqueRelationShortDescription() {
        when(entity.getReferences()).thenReturn(Collections.singletonList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(targetReference.getShortDescription()).thenReturn("Some description");

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto.group;");
        expected.add("");
        expected.add("import de.ma_vin.util.layer.generator.annotations.model.BaseDto;");
        expected.add("import de.test.package.dto.ITransportable;");
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
        expected.add("@BaseDto(\"de.test.package.dto\")");
        expected.add("@Data");
        expected.add("@EqualsAndHashCode(exclude = {\"targetRef\"})");
        expected.add("@NoArgsConstructor");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("@ToString(exclude = {\"targetRef\"})");
        expected.add("public class DummyDto implements ITransportable {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Some description");
        expected.add("	 */");
        expected.add("	private TargetDto targetRef;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", Optional.of(basePackageDir)));

        checkSingleFile("DummyDto.java", expected);
    }

    @Test
    public void testCreateDataTransportObjectWithVersion() {
        List<String> expected = getDefaultExpected();

        List<String> basicExpected = new ArrayList<>();

        basicExpected.add("package de.test.package.dto.group;");
        basicExpected.add("");
        basicExpected.add("import de.ma_vin.util.layer.generator.annotations.model.BaseDto;");
        basicExpected.add("import de.test.package.dto.ITransportable;");
        basicExpected.add("import lombok.Data;");
        basicExpected.add("import lombok.NoArgsConstructor;");
        basicExpected.add("");
        basicExpected.add("/**");
        basicExpected.add(" * Generated dto class of DummyV1");
        basicExpected.add(" * <br>");
        basicExpected.add(" * Dummy description");
        basicExpected.add(" */");
        basicExpected.add("@BaseDto(\"de.test.package.dto\")");
        basicExpected.add("@Data");
        basicExpected.add("@NoArgsConstructor");
        basicExpected.add("@SuppressWarnings(\"java:S1068\")");
        basicExpected.add("public class DummyV1Dto implements ITransportable {");
        basicExpected.add("");
        basicExpected.add("	/**");
        basicExpected.add("	 * Id of DummyV1");
        basicExpected.add("	 */");
        basicExpected.add("	private Long id;");
        basicExpected.add("");
        basicExpected.add("}");

        when(entity.getVersions()).thenReturn(Collections.singletonList(version));
        when(entity.copyForVersion(eq(version))).thenReturn(copyEntity);
        mockEntityDefault(copyEntity);
        when(copyEntity.getBaseName()).thenReturn(ENTITY_NAME + "V1");
        when(version.getVersionName()).thenReturn(ENTITY_NAME + "V1");
        when(version.getParentEntity()).thenReturn(entity);

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", Optional.of(basePackageDir)));

        assertEquals(2, writtenFileContents.size(), "Wrong number of files");
        assertTrue(writtenFileContents.containsKey("DummyDto.java"));
        assertTrue(writtenFileContents.containsKey("DummyV1Dto.java"));

        if (expected.size() != writtenFileContents.get("DummyDto.java").size()
                || basicExpected.size() != writtenFileContents.get("DummyV1Dto.java").size()) {
            logFileContents();
        }
        TestUtil.checkList(expected, writtenFileContents.get("DummyDto.java"));
        TestUtil.checkList(basicExpected, writtenFileContents.get("DummyV1Dto.java"));
    }

    @Test
    public void testCreateDataTransportObjectWithVersionAndTargetWithVersion() {
        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto.group;");
        expected.add("");
        expected.add("import de.ma_vin.util.layer.generator.annotations.model.BaseDto;");
        expected.add("import de.test.package.dto.ITransportable;");
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
        expected.add("@BaseDto(\"de.test.package.dto\")");
        expected.add("@Data");
        expected.add("@EqualsAndHashCode(exclude = {\"targetRef\"})");
        expected.add("@NoArgsConstructor");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("@ToString(exclude = {\"targetRef\"})");
        expected.add("public class DummyDto implements ITransportable {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Some description");
        expected.add("	 */");
        expected.add("	private TargetDto targetRef;");
        expected.add("");
        expected.add("}");

        List<String> basicExpected = new ArrayList<>();
        basicExpected.add("package de.test.package.dto.group;");
        basicExpected.add("");
        basicExpected.add("import de.ma_vin.util.layer.generator.annotations.model.BaseDto;");
        basicExpected.add("import de.test.package.dto.ITransportable;");
        basicExpected.add("import lombok.Data;");
        basicExpected.add("import lombok.EqualsAndHashCode;");
        basicExpected.add("import lombok.NoArgsConstructor;");
        basicExpected.add("import lombok.ToString;");
        basicExpected.add("");
        basicExpected.add("/**");
        basicExpected.add(" * Generated dto class of DummyV1");
        basicExpected.add(" * <br>");
        basicExpected.add(" * Dummy description");
        basicExpected.add(" */");
        basicExpected.add("@BaseDto(\"de.test.package.dto\")");
        basicExpected.add("@Data");
        basicExpected.add("@EqualsAndHashCode(exclude = {\"targetRef\"})");
        basicExpected.add("@NoArgsConstructor");
        basicExpected.add("@SuppressWarnings(\"java:S1068\")");
        basicExpected.add("@ToString(exclude = {\"targetRef\"})");
        basicExpected.add("public class DummyV1Dto implements ITransportable {");
        basicExpected.add("");
        basicExpected.add("	/**");
        basicExpected.add("	 * Id of DummyV1");
        basicExpected.add("	 */");
        basicExpected.add("	private Long id;");
        basicExpected.add("");
        basicExpected.add("	/**");
        basicExpected.add("	 * Some description");
        basicExpected.add("	 */");
        basicExpected.add("	private TargetV2Dto targetRef;");
        basicExpected.add("");
        basicExpected.add("}");

        when(entity.getReferences()).thenReturn(Collections.singletonList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(targetReference.getShortDescription()).thenReturn("Some description");

        when(entity.getVersions()).thenReturn(Collections.singletonList(version));
        when(entity.copyForVersion(eq(version))).thenReturn(copyEntity);


        mockEntityDefault(copyEntity);
        when(copyEntity.getBaseName()).thenReturn(ENTITY_NAME + "V1");
        List<Reference> modifiableList = new ArrayList<>(Collections.singletonList(targetReference));
        when(copyEntity.getReferences()).thenReturn(modifiableList);

        when(version.getVersionName()).thenReturn(ENTITY_NAME + "V1");
        when(version.getParentEntity()).thenReturn(entity);
        when(version.determineReferenceTargetVersion(eq(targetReference))).thenReturn(Optional.of(targetVersion));

        when(targetVersion.getVersionName()).thenReturn(TARGET_ENTITY_NAME + "V2");
        when(targetEntity.getVersions()).thenReturn(Collections.singletonList(targetVersion));


        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", Optional.of(basePackageDir)));

        assertEquals(2, writtenFileContents.size(), "Wrong number of files");
        assertTrue(writtenFileContents.containsKey("DummyDto.java"));
        assertTrue(writtenFileContents.containsKey("DummyV1Dto.java"));

        if (expected.size() != writtenFileContents.get("DummyDto.java").size()
                || basicExpected.size() != writtenFileContents.get("DummyV1Dto.java").size()) {
            logFileContents();
        }
        TestUtil.checkList(expected, writtenFileContents.get("DummyDto.java"));
        TestUtil.checkList(basicExpected, writtenFileContents.get("DummyV1Dto.java"));
    }

    @Test
    public void testCreateDataTransportObjectWithVersionAndEqualTargetVersion() {
        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dto.group;");
        expected.add("");
        expected.add("import de.ma_vin.util.layer.generator.annotations.model.BaseDto;");
        expected.add("import de.test.package.dto.ITransportable;");
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
        expected.add("@BaseDto(\"de.test.package.dto\")");
        expected.add("@Data");
        expected.add("@EqualsAndHashCode(exclude = {\"targetRef\"})");
        expected.add("@NoArgsConstructor");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("@ToString(exclude = {\"targetRef\"})");
        expected.add("public class DummyDto implements ITransportable {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Some description");
        expected.add("	 */");
        expected.add("	private TargetDto targetRef;");
        expected.add("");
        expected.add("}");

        List<String> basicExpected = new ArrayList<>();
        basicExpected.add("package de.test.package.dto.group;");
        basicExpected.add("");
        basicExpected.add("import de.ma_vin.util.layer.generator.annotations.model.BaseDto;");
        basicExpected.add("import de.test.package.dto.ITransportable;");
        basicExpected.add("import lombok.Data;");
        basicExpected.add("import lombok.EqualsAndHashCode;");
        basicExpected.add("import lombok.NoArgsConstructor;");
        basicExpected.add("import lombok.ToString;");
        basicExpected.add("");
        basicExpected.add("/**");
        basicExpected.add(" * Generated dto class of DummyV1");
        basicExpected.add(" * <br>");
        basicExpected.add(" * Dummy description");
        basicExpected.add(" */");
        basicExpected.add("@BaseDto(\"de.test.package.dto\")");
        basicExpected.add("@Data");
        basicExpected.add("@EqualsAndHashCode(exclude = {\"targetRef\"})");
        basicExpected.add("@NoArgsConstructor");
        basicExpected.add("@SuppressWarnings(\"java:S1068\")");
        basicExpected.add("@ToString(exclude = {\"targetRef\"})");
        basicExpected.add("public class DummyV1Dto implements ITransportable {");
        basicExpected.add("");
        basicExpected.add("	/**");
        basicExpected.add("	 * Id of DummyV1");
        basicExpected.add("	 */");
        basicExpected.add("	private Long id;");
        basicExpected.add("");
        basicExpected.add("	/**");
        basicExpected.add("	 * Some description");
        basicExpected.add("	 */");
        basicExpected.add("	private TargetDto targetRef;");
        basicExpected.add("");
        basicExpected.add("}");

        when(entity.getReferences()).thenReturn(Collections.singletonList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(targetReference.getShortDescription()).thenReturn("Some description");

        when(entity.getVersions()).thenReturn(Collections.singletonList(version));
        when(entity.copyForVersion(eq(version))).thenReturn(copyEntity);


        mockEntityDefault(copyEntity);
        when(copyEntity.getBaseName()).thenReturn(ENTITY_NAME + "V1");
        List<Reference> modifiableList = new ArrayList<>(Collections.singletonList(targetReference));
        when(copyEntity.getReferences()).thenReturn(modifiableList);

        when(version.getVersionName()).thenReturn(ENTITY_NAME + "V1");
        when(version.getParentEntity()).thenReturn(entity);
        when(version.determineReferenceTargetVersion(eq(targetReference))).thenReturn(Optional.empty());


        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", Optional.of(basePackageDir)));

        assertEquals(2, writtenFileContents.size(), "Wrong number of files");
        assertTrue(writtenFileContents.containsKey("DummyDto.java"));
        assertTrue(writtenFileContents.containsKey("DummyV1Dto.java"));

        if (expected.size() != writtenFileContents.get("DummyDto.java").size()
                || basicExpected.size() != writtenFileContents.get("DummyV1Dto.java").size()) {
            logFileContents();
        }
        TestUtil.checkList(expected, writtenFileContents.get("DummyDto.java"));
        TestUtil.checkList(basicExpected, writtenFileContents.get("DummyV1Dto.java"));
    }
}
