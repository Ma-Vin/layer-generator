package de.mv.ape.layer.generator.generator;

import de.mv.ape.layer.generator.config.elements.Entity;
import de.mv.ape.layer.generator.config.elements.Models;
import de.mv.ape.layer.generator.config.elements.Reference;
import de.mv.ape.layer.generator.log.LogImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

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

public class TransportMapperCreatorTest extends AbstractCreatorTest {

    public static final String GROUPING_NAME = "grouping";
    public static final String MAPPER_PACKAGE_NAME = BASE_PACKAGE + ".mapper";
    public static final String DTO_PACKAGE_NAME = BASE_PACKAGE + ".dto";
    public static final String DOMAIN_PACKAGE_NAME = BASE_PACKAGE + ".domain";
    private TransportMapperCreator cut;
    private List<Entity> entities = new ArrayList<>();

    @Mock
    private Entity parentEntity;
    @Mock
    private Reference parentReference;

    @Mock
    private Entity subEntity;
    @Mock
    private Reference subReference;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        cut = new TransportMapperCreator(config, new LogImpl()) {
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

        entities.clear();
        entities.add(entity);
    }

    @Override
    protected void initDefaultMock() {
        super.initDefaultMock();

        when(parentEntity.getBaseName()).thenReturn("Owner");
        when(parentEntity.getDescription()).thenReturn("Owner description");
        when(parentEntity.getIdentificationPrefix()).thenReturn("OW");
        when(parentEntity.getModels()).thenReturn(Models.DOMAIN_DAO_DTO);
        when(parentEntity.getGrouping()).thenReturn(null);

        when(parentReference.getTargetEntity()).thenReturn("Owner");
        when(parentReference.getRealTargetEntity()).thenReturn(parentEntity);
        when(parentReference.getReferenceName()).thenReturn("dummy");
        when(parentReference.isList()).thenReturn(Boolean.TRUE);
        when(parentReference.isOwner()).thenReturn(Boolean.TRUE);

        when(subEntity.getBaseName()).thenReturn("Child");
        when(subEntity.getDescription()).thenReturn("child description");
        when(subEntity.getIdentificationPrefix()).thenReturn("CH");
        when(subEntity.getModels()).thenReturn(Models.DOMAIN_DAO_DTO);
        when(subEntity.getGrouping()).thenReturn(grouping);

        when(subReference.getTargetEntity()).thenReturn("Child");
        when(subReference.getRealTargetEntity()).thenReturn(subEntity);
        when(subReference.getReferenceName()).thenReturn("child");
        when(subReference.isList()).thenReturn(Boolean.TRUE);
        when(subReference.isOwner()).thenReturn(Boolean.TRUE);
    }

    @Test
    public void testCreateTransportMapper() {
        List<String> expected = getDefaultExpected();

        assertTrue(cut.createTransportMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DTO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sTransportMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    private List<String> getDefaultExpected(){
        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import de.test.package.dto.group.DummyDto;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingTransportMapper {");
        expected.add("");
        expected.add("	private GroupingTransportMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingTransportMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy) {");
        expected.add("		return convertToDummy(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy, Map<String, IIdentifiable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"Dummy\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (Dummy) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		Dummy result = new Dummy();");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy) {");
        expected.add("		return convertToDummyDto(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy, Map<String, ITransportable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDto\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDto) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDto result = new DummyDto();");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingTransportMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingTransportMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        return expected;
    }

    @Test
    public void testCreateTransportMapperField() {
        when(entity.getFields()).thenReturn(Arrays.asList(field));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import de.test.package.dto.group.DummyDto;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingTransportMapper {");
        expected.add("");
        expected.add("	private GroupingTransportMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingTransportMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy) {");
        expected.add("		return convertToDummy(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy, Map<String, IIdentifiable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"Dummy\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (Dummy) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		Dummy result = new Dummy();");
        expected.add("");
        expected.add("		result.setAnyField(dummy.getAnyField());");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy) {");
        expected.add("		return convertToDummyDto(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy, Map<String, ITransportable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDto\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDto) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDto result = new DummyDto();");
        expected.add("");
        expected.add("		result.setAnyField(dummy.getAnyField());");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingTransportMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingTransportMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");


        assertTrue(cut.createTransportMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DTO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sTransportMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void testCreateTransportMapperSingleRef() {
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import de.test.package.dto.group.DummyDto;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingTransportMapper {");
        expected.add("");
        expected.add("	private GroupingTransportMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingTransportMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy) {");
        expected.add("		return convertToDummy(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy, Map<String, IIdentifiable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"Dummy\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (Dummy) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		Dummy result = new Dummy();");
        expected.add("");
        expected.add("		GroupTransportMapper.convertToTarget(dummy.getTargetRef(), result, mappedObjects);");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy) {");
        expected.add("		return convertToDummyDto(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy, Map<String, ITransportable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDto\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDto) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDto result = new DummyDto();");
        expected.add("");
        expected.add("		GroupTransportMapper.convertToTargetDto(dummy.getTargetRef(), result, mappedObjects);");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingTransportMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingTransportMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createTransportMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DTO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sTransportMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void testCreateTransportMapperSingleRefNotOwner() {
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(targetReference.isOwner()).thenReturn(Boolean.FALSE);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import de.test.package.dto.group.DummyDto;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingTransportMapper {");
        expected.add("");
        expected.add("	private GroupingTransportMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingTransportMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy) {");
        expected.add("		return convertToDummy(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy, Map<String, IIdentifiable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"Dummy\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (Dummy) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		Dummy result = new Dummy();");
        expected.add("");
        expected.add("		result.setTargetRef(GroupTransportMapper.convertToTarget(dummy.getTargetRef(), mappedObjects));");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy) {");
        expected.add("		return convertToDummyDto(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy, Map<String, ITransportable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDto\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDto) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDto result = new DummyDto();");
        expected.add("");
        expected.add("		result.setTargetRef(GroupTransportMapper.convertToTargetDto(dummy.getTargetRef(), mappedObjects));");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingTransportMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingTransportMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createTransportMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DTO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sTransportMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void testCreateTransportMapperMultiRef() {
        when(targetReference.getParent()).thenReturn(entity);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import de.test.package.dto.group.DummyDto;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingTransportMapper {");
        expected.add("");
        expected.add("	private GroupingTransportMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingTransportMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy) {");
        expected.add("		return convertToDummy(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy, Map<String, IIdentifiable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"Dummy\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (Dummy) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		Dummy result = new Dummy();");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy) {");
        expected.add("		return convertToDummyDto(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy, Map<String, ITransportable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDto\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDto) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDto result = new DummyDto();");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingTransportMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingTransportMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createTransportMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DTO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sTransportMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void testCreateTransportMapperMultiRefNotOwner() {
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isOwner()).thenReturn(Boolean.FALSE);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import de.test.package.dto.group.DummyDto;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingTransportMapper {");
        expected.add("");
        expected.add("	private GroupingTransportMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingTransportMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy) {");
        expected.add("		return convertToDummy(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy, Map<String, IIdentifiable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"Dummy\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (Dummy) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		Dummy result = new Dummy();");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy) {");
        expected.add("		return convertToDummyDto(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy, Map<String, ITransportable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDto\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDto) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDto result = new DummyDto();");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingTransportMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingTransportMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createTransportMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DTO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sTransportMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void testCreateTransportMapperParentSingleRef() {
        when(entity.getParentRefs()).thenReturn(Arrays.asList(parentReference));
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(parentReference.isList()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.Owner;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import de.test.package.dto.OwnerDto;");
        expected.add("import de.test.package.dto.group.DummyDto;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingTransportMapper {");
        expected.add("");
        expected.add("	private GroupingTransportMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingTransportMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy) {");
        expected.add("		return convertToDummy(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy, Map<String, IIdentifiable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"Dummy\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (Dummy) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		Dummy result = new Dummy();");
        expected.add("");
        expected.add("		GroupTransportMapper.convertToTarget(dummy.getTargetRef(), result, mappedObjects);");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy, Owner parent) {");
        expected.add("		return convertToDummy(dummy, parent, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy, Owner parent, Map<String, IIdentifiable> mappedObjects) {");
        expected.add("		Dummy result = convertToDummy(dummy, mappedObjects);");
        expected.add("		if (result != null) {");
        expected.add("			parent.setDummy(result);");
        expected.add("		}");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy) {");
        expected.add("		return convertToDummyDto(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy, Map<String, ITransportable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDto\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDto) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDto result = new DummyDto();");
        expected.add("");
        expected.add("		GroupTransportMapper.convertToTargetDto(dummy.getTargetRef(), result, mappedObjects);");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy, OwnerDto parent) {");
        expected.add("		return convertToDummyDto(dummy, parent, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy, OwnerDto parent, Map<String, ITransportable> mappedObjects) {");
        expected.add("		DummyDto result = convertToDummyDto(dummy, mappedObjects);");
        expected.add("		if (result != null) {");
        expected.add("			parent.setDummy(result);");
        expected.add("		}");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingTransportMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingTransportMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");
        assertTrue(cut.createTransportMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DTO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sTransportMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void testCreateTransportMapperParentMultiRef() {
        when(entity.getParentRefs()).thenReturn(Arrays.asList(parentReference));
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import de.test.package.dto.group.DummyDto;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingTransportMapper {");
        expected.add("");
        expected.add("	private GroupingTransportMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingTransportMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy) {");
        expected.add("		return convertToDummy(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy, Map<String, IIdentifiable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"Dummy\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (Dummy) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		Dummy result = new Dummy();");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy) {");
        expected.add("		return convertToDummyDto(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy, Map<String, ITransportable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDto\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDto) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDto result = new DummyDto();");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingTransportMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingTransportMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createTransportMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DTO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sTransportMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }


    @Test
    public void testCreateTransportMapperUseIdGenerator() {
        when(config.isUseIdGenerator()).thenReturn(Boolean.TRUE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import de.test.package.dto.group.DummyDto;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingTransportMapper {");
        expected.add("");
        expected.add("	private GroupingTransportMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingTransportMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy) {");
        expected.add("		return convertToDummy(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy, Map<String, IIdentifiable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = dummy.getIdentification();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (Dummy) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		Dummy result = new Dummy();");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy) {");
        expected.add("		return convertToDummyDto(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy, Map<String, ITransportable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = dummy.getIdentification();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDto) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDto result = new DummyDto();");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingTransportMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingTransportMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createTransportMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DTO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sTransportMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void testCreateTransportMapperSingleRefWithChildren() {
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetEntity.getReferences()).thenReturn(Arrays.asList(subReference));
        when(subReference.getParent()).thenReturn(targetEntity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import de.test.package.dto.group.DummyDto;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingTransportMapper {");
        expected.add("");
        expected.add("	private GroupingTransportMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingTransportMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy) {");
        expected.add("		return convertToDummy(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy, Map<String, IIdentifiable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"Dummy\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (Dummy) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		Dummy result = new Dummy();");
        expected.add("");
        expected.add("		GroupTransportMapper.convertToTarget(dummy.getTargetRef(), result, mappedObjects);");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy) {");
        expected.add("		return convertToDummyDto(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy, Map<String, ITransportable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDto\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDto) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDto result = new DummyDto();");
        expected.add("");
        expected.add("		GroupTransportMapper.convertToTargetDto(dummy.getTargetRef(), result, mappedObjects);");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingTransportMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingTransportMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createTransportMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DTO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sTransportMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }


    @Test
    public void testCreateTransportMapperSingleRefNotOwnerWithChildren() {
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(targetReference.isOwner()).thenReturn(Boolean.FALSE);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetEntity.getReferences()).thenReturn(Arrays.asList(subReference));
        when(subReference.getParent()).thenReturn(targetEntity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import de.test.package.dto.group.DummyDto;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingTransportMapper {");
        expected.add("");
        expected.add("	private GroupingTransportMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingTransportMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy) {");
        expected.add("		return convertToDummy(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy, Map<String, IIdentifiable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"Dummy\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (Dummy) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		Dummy result = new Dummy();");
        expected.add("");
        expected.add("		result.setTargetRef(GroupTransportMapper.convertToTarget(dummy.getTargetRef(), mappedObjects));");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy) {");
        expected.add("		return convertToDummyDto(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy, Map<String, ITransportable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDto\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDto) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDto result = new DummyDto();");
        expected.add("");
        expected.add("		result.setTargetRef(GroupTransportMapper.convertToTargetDto(dummy.getTargetRef(), mappedObjects));");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingTransportMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingTransportMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createTransportMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DTO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sTransportMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void testCreateTransportMapperMultiRefWithChildren() {
        when(targetReference.getParent()).thenReturn(entity);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetEntity.getReferences()).thenReturn(Arrays.asList(subReference));
        when(subReference.getParent()).thenReturn(targetEntity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import de.test.package.dto.group.DummyDto;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingTransportMapper {");
        expected.add("");
        expected.add("	private GroupingTransportMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingTransportMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy) {");
        expected.add("		return convertToDummy(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy, Map<String, IIdentifiable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"Dummy\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (Dummy) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		Dummy result = new Dummy();");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy) {");
        expected.add("		return convertToDummyDto(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy, Map<String, ITransportable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDto\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDto) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDto result = new DummyDto();");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingTransportMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingTransportMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createTransportMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DTO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sTransportMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }


    @Test
    public void testCreateTransportMapperMultiRefNotOwnerWithChildren() {
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isOwner()).thenReturn(Boolean.FALSE);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetEntity.getReferences()).thenReturn(Arrays.asList(subReference));
        when(subReference.getParent()).thenReturn(targetEntity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import de.test.package.dto.ITransportable;");
        expected.add("import de.test.package.dto.group.DummyDto;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingTransportMapper {");
        expected.add("");
        expected.add("	private GroupingTransportMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingTransportMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy) {");
        expected.add("		return convertToDummy(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDto dummy, Map<String, IIdentifiable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"Dummy\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (Dummy) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		Dummy result = new Dummy();");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy) {");
        expected.add("		return convertToDummyDto(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDto convertToDummyDto(Dummy dummy, Map<String, ITransportable> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDto\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDto) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDto result = new DummyDto();");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingTransportMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingTransportMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createTransportMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DTO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sTransportMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void testCreateTransportMapperNothingToMap(){
        when(entity.getModels()).thenReturn(Models.DTO);
        assertTrue(cut.createTransportMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DTO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));
        assertEquals(0, writtenFileContents.size(), "No Mapper should be generated for only dto");

        when(entity.getModels()).thenReturn(Models.DOMAIN);
        assertTrue(cut.createTransportMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DTO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));
        assertEquals(0, writtenFileContents.size(), "No Mapper should be generated for only domain");

        when(entity.getModels()).thenReturn(Models.DAO);
        assertTrue(cut.createTransportMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DTO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));
        assertEquals(0, writtenFileContents.size(), "No Mapper should be generated for only dao");
    }

    @Test
    public void testCreateTransportMapperNoAllToMap(){
        when(parentEntity.getModels()).thenReturn(Models.DOMAIN_DAO);
        when(subEntity.getModels()).thenReturn(Models.DTO);
        entities.add(parentEntity);
        entities.add(subEntity);

        List<String> expected = getDefaultExpected();

        assertTrue(cut.createTransportMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DTO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sTransportMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void testCreateAccessMapperMultiRefParentNotRelevant(){
        when(parentEntity.getModels()).thenReturn(Models.DOMAIN_DAO);
        when(entity.getParentRefs()).thenReturn(Arrays.asList(parentReference));

        List<String> expected = getDefaultExpected();

        assertTrue(cut.createTransportMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DTO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sTransportMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void testCreateAccessMapperSingleRefParentNotRelevant(){
        when(entity.getParentRefs()).thenReturn(Arrays.asList(parentReference));
        when(parentReference.isList()).thenReturn(Boolean.FALSE);
        when(parentEntity.getModels()).thenReturn(Models.DOMAIN_DAO);

        List<String> expected = getDefaultExpected();

        assertTrue(cut.createTransportMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DTO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sTransportMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void testCreateAccessMapperMultiRefChildNotRelevant(){
        when(targetEntity.getModels()).thenReturn(Models.DOMAIN_DAO);
        when(targetReference.getParent()).thenReturn(entity);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));

        List<String> expected = getDefaultExpected();

        assertTrue(cut.createTransportMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DTO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sTransportMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void testCreateAccessMapperSingleRefChildNotRelevant(){
        when(targetEntity.getModels()).thenReturn(Models.DOMAIN_DAO);
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));

        List<String> expected = getDefaultExpected();

        assertTrue(cut.createTransportMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DTO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sTransportMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }
}
