package de.ma_vin.util.layer.generator.generator;

import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.config.elements.Models;
import de.ma_vin.util.layer.generator.log.LogImpl;
import de.ma_vin.util.layer.generator.sources.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class DtoCreatorTest extends AbstractCreatorTest {

    @Mock
    private Entity parentEntity;

    private DtoCreator cut;

    private List<String> directoriesWhereRequestedToWrite = new ArrayList<>();

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
                directoriesWhereRequestedToWrite.add(dir.getName());
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

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

        checkSingleFile("DummyDto.java", expected);
    }


    @Test
    public void testCreateDataTransportObjectNoDto() {
        when(entity.getModels()).thenReturn(Models.DAO);

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

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

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

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

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

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

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

        checkSingleFile("DummyDto.java", expected);
    }


    @DisplayName("Create a data transport object with an one to one relation, but the target does not support the transport model")
    @Test
    public void testCreateDataTransportObjectUniqueRelationButNonDto() {
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
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

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

        checkSingleFile("DummyDto.java", expected);
    }


    @DisplayName("Create a data transport object with an one to many relation, but the target does not support the transport model")
    @Test
    public void testCreateDataTransportObjectRelationButNotDto() {
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
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

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

        checkSingleFile("DummyDto.java", expected);
    }

    @Test
    public void testCreateDataTransportObjectField() {
        when(entity.getFields()).thenReturn(Arrays.asList(field));

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

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

        checkSingleFile("DummyDto.java", expected);
    }

    @Test
    public void testCreateDataTransportObjectFieldEnum() {
        when(entity.getFields()).thenReturn(Arrays.asList(field));
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


        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

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

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

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

        assertTrue(cut.createDataTransportObjectInterface(BASE_PACKAGE + ".dto", basePackageDir));

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

        assertTrue(cut.createDataTransportObjectInterface(BASE_PACKAGE + ".dto", basePackageDir));

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

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

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

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

        checkSingleFile("DummyDto.java", expected);
    }

    @Test
    public void testCreateDataTransportObjectHasSuperClass() {
        when(entity.getParent()).thenReturn("AnotherDummy");
        when(entity.getRealParent()).thenReturn(parentEntity);
        when(entity.getFields()).thenReturn(Arrays.asList(field));
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

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

        checkSingleFile("DummyDto.java", expected);
    }

    @Test
    public void testCreateDataTransportObjectUniqueRelationShortDescription() {
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
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

        assertTrue(cut.createDataTransportObject(entity, BASE_PACKAGE + ".dto", basePackageDir));

        checkSingleFile("DummyDto.java", expected);
    }
}
