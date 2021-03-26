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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class AccessMapperCreatorTest extends AbstractCreatorTest {

    public static final String GROUPING_NAME = "grouping";
    public static final String MAPPER_PACKAGE_NAME = BASE_PACKAGE + ".mapper";
    public static final String DAO_PACKAGE_NAME = BASE_PACKAGE + ".dao";
    public static final String DOMAIN_PACKAGE_NAME = BASE_PACKAGE + ".domain";
    private AccessMapperCreator cut;
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
        cut = new AccessMapperCreator(config, new LogImpl()) {
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
    public void tesCreateAccessMapper() {
        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.group.DummyDao;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingAccessMapper {");
        expected.add("");
        expected.add("	private GroupingAccessMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingAccessMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy) {");
        expected.add("		return convertToDummy(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, Map<String, IIdentifiable> mappedObjects) {");
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
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy) {");
        expected.add("		return convertToDummyDao(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, Map<String, IIdentifiableDao> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDao\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDao) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDao result = new DummyDao();");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingAccessMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingAccessMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sAccessMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void tesCreateAccessMapperField() {
        when(entity.getFields()).thenReturn(Arrays.asList(field));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.group.DummyDao;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingAccessMapper {");
        expected.add("");
        expected.add("	private GroupingAccessMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingAccessMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy) {");
        expected.add("		return convertToDummy(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, Map<String, IIdentifiable> mappedObjects) {");
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
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy) {");
        expected.add("		return convertToDummyDao(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, Map<String, IIdentifiableDao> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDao\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDao) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDao result = new DummyDao();");
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
        expected.add("	public static GroupingAccessMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingAccessMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");


        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sAccessMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void tesCreateAccessMapperSingleRef() {
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.group.DummyDao;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingAccessMapper {");
        expected.add("");
        expected.add("	private GroupingAccessMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingAccessMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy) {");
        expected.add("		return convertToDummy(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, Map<String, IIdentifiable> mappedObjects) {");
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
        expected.add("		GroupAccessMapper.convertToTarget(dummy.getTargetRef(), result, mappedObjects);");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy) {");
        expected.add("		return convertToDummyDao(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, Map<String, IIdentifiableDao> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDao\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDao) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDao result = new DummyDao();");
        expected.add("");
        expected.add("		GroupAccessMapper.convertToTargetDao(dummy.getTargetRef(), result, mappedObjects);");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingAccessMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingAccessMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sAccessMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void tesCreateAccessMapperSingleRefNotOwner() {
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(targetReference.isOwner()).thenReturn(Boolean.FALSE);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.group.DummyDao;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingAccessMapper {");
        expected.add("");
        expected.add("	private GroupingAccessMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingAccessMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy) {");
        expected.add("		return convertToDummy(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, Map<String, IIdentifiable> mappedObjects) {");
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
        expected.add("		result.setTargetRef(GroupAccessMapper.convertToTarget(dummy.getTargetRef(), mappedObjects));");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy) {");
        expected.add("		return convertToDummyDao(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, Map<String, IIdentifiableDao> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDao\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDao) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDao result = new DummyDao();");
        expected.add("");
        expected.add("		result.setTargetRef(GroupAccessMapper.convertToTargetDao(dummy.getTargetRef(), mappedObjects));");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingAccessMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingAccessMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sAccessMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void tesCreateAccessMapperMultiRef() {
        when(targetReference.getParent()).thenReturn(entity);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.group.DummyDao;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import java.util.ArrayList;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingAccessMapper {");
        expected.add("");
        expected.add("	private GroupingAccessMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingAccessMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, boolean includeChildren) {");
        expected.add("		return convertToDummy(dummy, includeChildren, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {");
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
        expected.add("		if (includeChildren) {");
        expected.add("			dummy.getTargetRefs().forEach(arg ->");
        expected.add("					GroupAccessMapper.convertToTarget(arg, result, mappedObjects)");
        expected.add("			);");
        expected.add("		}");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, boolean includeChildren) {");
        expected.add("		return convertToDummyDao(dummy, includeChildren, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDao\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDao) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDao result = new DummyDao();");
        expected.add("");
        expected.add("		result.setTargetRefs(new ArrayList<>());");
        expected.add("		if (includeChildren) {");
        expected.add("			dummy.getTargetRefs().forEach(arg ->");
        expected.add("					GroupAccessMapper.convertToTargetDao(arg, result, mappedObjects)");
        expected.add("			);");
        expected.add("		}");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingAccessMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingAccessMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sAccessMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void tesCreateAccessMapperMultiRefNotOwner() {
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isOwner()).thenReturn(Boolean.FALSE);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.group.DummyDao;");
        expected.add("import de.test.package.dao.group.DummyToTargetDao;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import java.util.ArrayList;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingAccessMapper {");
        expected.add("");
        expected.add("	private GroupingAccessMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingAccessMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, boolean includeChildren) {");
        expected.add("		return convertToDummy(dummy, includeChildren, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {");
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
        expected.add("		if (includeChildren) {");
        expected.add("			dummy.getTargetRefs().forEach(arg ->");
        expected.add("					GroupAccessMapper.convertToTarget(arg.getDummy(), result, mappedObjects)");
        expected.add("			);");
        expected.add("		}");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, boolean includeChildren) {");
        expected.add("		return convertToDummyDao(dummy, includeChildren, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDao\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDao) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDao result = new DummyDao();");
        expected.add("");
        expected.add("		result.setTargetRefs(new ArrayList<>());");
        expected.add("		if (includeChildren) {");
        expected.add("			dummy.getTargetRefs().forEach(arg -> {");
        expected.add("				DummyToTargetDao connectionTable = new DummyToTargetDao();");
        expected.add("				connectionTable.setDummy(result);");
        expected.add("				connectionTable.setDummy(GroupAccessMapper.convertToTargetDao(arg, mappedObjects));");
        expected.add("				result.getTargetRefs().add(connectionTable);");
        expected.add("			});");
        expected.add("		}");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingAccessMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingAccessMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sAccessMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void tesCreateAccessMapperParentSingleRef() {
        when(entity.getParentRefs()).thenReturn(Arrays.asList(parentReference));
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.OwnerDao;");
        expected.add("import de.test.package.dao.group.DummyDao;");
        expected.add("import de.test.package.domain.Owner;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingAccessMapper {");
        expected.add("");
        expected.add("	private GroupingAccessMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingAccessMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy) {");
        expected.add("		return convertToDummy(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, Map<String, IIdentifiable> mappedObjects) {");
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
        expected.add("		GroupAccessMapper.convertToTarget(dummy.getTargetRef(), result, mappedObjects);");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, Owner parent) {");
        expected.add("		return convertToDummy(dummy, parent, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, Owner parent, Map<String, IIdentifiable> mappedObjects) {");
        expected.add("		Dummy result = convertToDummy(dummy, mappedObjects)");
        expected.add("		if (result != null) {");
        expected.add("			parent.getDummys().add(result);");
        expected.add("		}");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy) {");
        expected.add("		return convertToDummyDao(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, Map<String, IIdentifiableDao> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDao\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDao) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDao result = new DummyDao();");
        expected.add("");
        expected.add("		GroupAccessMapper.convertToTargetDao(dummy.getTargetRef(), result, mappedObjects);");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, OwnerDao parent) {");
        expected.add("		return convertToDummyDao(dummy, parent, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, OwnerDao parent, Map<String, IIdentifiableDao> mappedObjects) {");
        expected.add("		DummyDao result = convertToDummyDao(dummy, mappedObjects)");
        expected.add("		if (result != null) {");
        expected.add("			result.setParentOwner(parent);");
        expected.add("			parent.getDummys().add(result);");
        expected.add("		}");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingAccessMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingAccessMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");
        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sAccessMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void tesCreateAccessMapperParentMultiRef() {
        when(entity.getParentRefs()).thenReturn(Arrays.asList(parentReference));
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.OwnerDao;");
        expected.add("import de.test.package.dao.group.DummyDao;");
        expected.add("import de.test.package.domain.Owner;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import java.util.ArrayList;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingAccessMapper {");
        expected.add("");
        expected.add("	private GroupingAccessMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingAccessMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, boolean includeChildren) {");
        expected.add("		return convertToDummy(dummy, includeChildren, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {");
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
        expected.add("		if (includeChildren) {");
        expected.add("			dummy.getTargetRefs().forEach(arg ->");
        expected.add("					GroupAccessMapper.convertToTarget(arg, result, mappedObjects)");
        expected.add("			);");
        expected.add("		}");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, boolean includeChildren, Owner parent) {");
        expected.add("		return convertToDummy(dummy, includeChildren, parent, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, boolean includeChildren, Owner parent, Map<String, IIdentifiable> mappedObjects) {");
        expected.add("		Dummy result = convertToDummy(dummy, includeChildren, mappedObjects)");
        expected.add("		if (result != null) {");
        expected.add("			parent.getDummys().add(result);");
        expected.add("		}");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, boolean includeChildren) {");
        expected.add("		return convertToDummyDao(dummy, includeChildren, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDao\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDao) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDao result = new DummyDao();");
        expected.add("");
        expected.add("		result.setTargetRefs(new ArrayList<>());");
        expected.add("		if (includeChildren) {");
        expected.add("			dummy.getTargetRefs().forEach(arg ->");
        expected.add("					GroupAccessMapper.convertToTargetDao(arg, result, mappedObjects)");
        expected.add("			);");
        expected.add("		}");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, boolean includeChildren, OwnerDao parent) {");
        expected.add("		return convertToDummyDao(dummy, includeChildren, parent, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, boolean includeChildren, OwnerDao parent, Map<String, IIdentifiableDao> mappedObjects) {");
        expected.add("		DummyDao result = convertToDummyDao(dummy, includeChildren, mappedObjects)");
        expected.add("		if (result != null) {");
        expected.add("			result.setParentOwner(parent);");
        expected.add("			parent.getDummys().add(result);");
        expected.add("		}");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingAccessMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingAccessMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sAccessMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }


    @Test
    public void tesCreateAccessMapperUseIdGenerator() {
        when(config.isUseIdGenerator()).thenReturn(Boolean.TRUE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.group.DummyDao;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingAccessMapper {");
        expected.add("");
        expected.add("	private GroupingAccessMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingAccessMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy) {");
        expected.add("		return convertToDummy(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, Map<String, IIdentifiable> mappedObjects) {");
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
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy) {");
        expected.add("		return convertToDummyDao(dummy, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, Map<String, IIdentifiableDao> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = dummy.getIdentification();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDao) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDao result = new DummyDao();");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingAccessMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingAccessMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sAccessMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void tesCreateAccessMapperSingleRefWithChildren() {
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetEntity.getReferences()).thenReturn(Arrays.asList(subReference));
        when(subReference.getParent()).thenReturn(targetEntity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.group.DummyDao;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingAccessMapper {");
        expected.add("");
        expected.add("	private GroupingAccessMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingAccessMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, boolean includeChildren) {");
        expected.add("		return convertToDummy(dummy, includeChildren, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {");
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
        expected.add("		GroupAccessMapper.convertToTarget(dummy.getTargetRef(), includeChildren, result, mappedObjects);");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, boolean includeChildren) {");
        expected.add("		return convertToDummyDao(dummy, includeChildren, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDao\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDao) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDao result = new DummyDao();");
        expected.add("");
        expected.add("		GroupAccessMapper.convertToTargetDao(dummy.getTargetRef(), includeChildren, result, mappedObjects);");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingAccessMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingAccessMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sAccessMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }


    @Test
    public void tesCreateAccessMapperSingleRefNotOwnerWithChildren() {
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(targetReference.isOwner()).thenReturn(Boolean.FALSE);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetEntity.getReferences()).thenReturn(Arrays.asList(subReference));
        when(subReference.getParent()).thenReturn(targetEntity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.group.DummyDao;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingAccessMapper {");
        expected.add("");
        expected.add("	private GroupingAccessMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingAccessMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, boolean includeChildren) {");
        expected.add("		return convertToDummy(dummy, includeChildren, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {");
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
        expected.add("		result.setTargetRef(GroupAccessMapper.convertToTarget(dummy.getTargetRef(), includeChildren, mappedObjects));");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, boolean includeChildren) {");
        expected.add("		return convertToDummyDao(dummy, includeChildren, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDao\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDao) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDao result = new DummyDao();");
        expected.add("");
        expected.add("		result.setTargetRef(GroupAccessMapper.convertToTargetDao(dummy.getTargetRef(), includeChildren, mappedObjects));");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingAccessMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingAccessMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sAccessMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void tesCreateAccessMapperMultiRefWithChildren() {
        when(targetReference.getParent()).thenReturn(entity);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetEntity.getReferences()).thenReturn(Arrays.asList(subReference));
        when(subReference.getParent()).thenReturn(targetEntity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.group.DummyDao;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import java.util.ArrayList;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingAccessMapper {");
        expected.add("");
        expected.add("	private GroupingAccessMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingAccessMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, boolean includeChildren) {");
        expected.add("		return convertToDummy(dummy, includeChildren, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {");
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
        expected.add("		if (includeChildren) {");
        expected.add("			dummy.getTargetRefs().forEach(arg ->");
        expected.add("					GroupAccessMapper.convertToTarget(arg, true, result, mappedObjects)");
        expected.add("			);");
        expected.add("		}");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, boolean includeChildren) {");
        expected.add("		return convertToDummyDao(dummy, includeChildren, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDao\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDao) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDao result = new DummyDao();");
        expected.add("");
        expected.add("		result.setTargetRefs(new ArrayList<>());");
        expected.add("		if (includeChildren) {");
        expected.add("			dummy.getTargetRefs().forEach(arg ->");
        expected.add("					GroupAccessMapper.convertToTargetDao(arg, true, result, mappedObjects)");
        expected.add("			);");
        expected.add("		}");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingAccessMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingAccessMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sAccessMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }


    @Test
    public void tesCreateAccessMapperMultiRefNotOwnerWithChildren() {
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isOwner()).thenReturn(Boolean.FALSE);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetEntity.getReferences()).thenReturn(Arrays.asList(subReference));
        when(subReference.getParent()).thenReturn(targetEntity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.group.DummyDao;");
        expected.add("import de.test.package.dao.group.DummyToTargetDao;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import java.util.ArrayList;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupingAccessMapper {");
        expected.add("");
        expected.add("	private GroupingAccessMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupingAccessMapper instance;");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, boolean includeChildren) {");
        expected.add("		return convertToDummy(dummy, includeChildren, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {");
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
        expected.add("		if (includeChildren) {");
        expected.add("			dummy.getTargetRefs().forEach(arg ->");
        expected.add("					GroupAccessMapper.convertToTarget(arg.getDummy(), true, result, mappedObjects)");
        expected.add("			);");
        expected.add("		}");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, boolean includeChildren) {");
        expected.add("		return convertToDummyDao(dummy, includeChildren, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {");
        expected.add("		if (dummy == null) {");
        expected.add("			return null;");
        expected.add("		}");
        expected.add("");
        expected.add("		String identification = \"DummyDao\" + dummy.getId().longValue();");
        expected.add("		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {");
        expected.add("			return (DummyDao) mappedObjects.get(identification);");
        expected.add("		}");
        expected.add("");
        expected.add("		DummyDao result = new DummyDao();");
        expected.add("");
        expected.add("		result.setTargetRefs(new ArrayList<>());");
        expected.add("		if (includeChildren) {");
        expected.add("			dummy.getTargetRefs().forEach(arg -> {");
        expected.add("				DummyToTargetDao connectionTable = new DummyToTargetDao();");
        expected.add("				connectionTable.setDummy(result);");
        expected.add("				connectionTable.setDummy(GroupAccessMapper.convertToTargetDao(arg, true, mappedObjects));");
        expected.add("				result.getTargetRefs().add(connectionTable);");
        expected.add("			});");
        expected.add("		}");
        expected.add("");
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the singleton");
        expected.add("	 */");
        expected.add("	public static GroupingAccessMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupingAccessMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sAccessMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }
}
