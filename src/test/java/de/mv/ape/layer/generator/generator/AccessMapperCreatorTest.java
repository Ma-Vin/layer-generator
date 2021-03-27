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
    private Entity anotherParentEntity;
    @Mock
    private Reference anotherParentReference;

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

        when(grouping.getGroupingPackage()).thenReturn(GROUPING_NAME);
        
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

        when(anotherParentEntity.getBaseName()).thenReturn("AnotherOwner");
        when(anotherParentEntity.getDescription()).thenReturn("Another owner description");
        when(anotherParentEntity.getIdentificationPrefix()).thenReturn("AOW");
        when(anotherParentEntity.getModels()).thenReturn(Models.DOMAIN_DAO_DTO);
        when(anotherParentEntity.getGrouping()).thenReturn(null);

        when(anotherParentReference.getTargetEntity()).thenReturn("AnotherOwner");
        when(anotherParentReference.getRealTargetEntity()).thenReturn(anotherParentEntity);
        when(anotherParentReference.getReferenceName()).thenReturn("anotherDummy");
        when(anotherParentReference.isList()).thenReturn(Boolean.TRUE);
        when(anotherParentReference.isOwner()).thenReturn(Boolean.TRUE);

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
    public void testCreateAccessMapper() {
        List<String> expected = getDefaultExpected();

        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sAccessMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    private List<String> getDefaultExpected(){
        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.grouping.DummyDao;");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.grouping.Dummy;");
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

        return expected;
    }

    @Test
    public void testCreateAccessMapperField() {
        when(entity.getFields()).thenReturn(Arrays.asList(field));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.grouping.DummyDao;");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.grouping.Dummy;");
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
    public void testCreateAccessMapperSingleRef() {
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.grouping.DummyDao;");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.grouping.Dummy;");
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
        expected.add("		GroupingAccessMapper.convertToTarget(dummy.getTargetRef(), result, mappedObjects);");
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
        expected.add("		GroupingAccessMapper.convertToTargetDao(dummy.getTargetRef(), result, mappedObjects);");
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
    public void testCreateAccessMapperSingleRefNotOwner() {
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(targetReference.isOwner()).thenReturn(Boolean.FALSE);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.grouping.DummyDao;");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.grouping.Dummy;");
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
        expected.add("		result.setTargetRef(GroupingAccessMapper.convertToTarget(dummy.getTargetRef(), mappedObjects));");
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
        expected.add("		result.setTargetRef(GroupingAccessMapper.convertToTargetDao(dummy.getTargetRef(), mappedObjects));");
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
    public void testCreateAccessMapperMultiRef() {
        when(targetReference.getParent()).thenReturn(entity);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.grouping.DummyDao;");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.grouping.Dummy;");
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
        expected.add("					GroupingAccessMapper.convertToTarget(arg, result, mappedObjects)");
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
        expected.add("					GroupingAccessMapper.convertToTargetDao(arg, result, mappedObjects)");
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
    public void testCreateAccessMapperMultiRefNotOwner() {
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isOwner()).thenReturn(Boolean.FALSE);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.grouping.DummyDao;");
        expected.add("import de.test.package.dao.grouping.DummyToTargetDao;");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.grouping.Dummy;");
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
        expected.add("					GroupingAccessMapper.convertToTarget(arg.getDummy(), result, mappedObjects)");
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
        expected.add("				connectionTable.setDummy(GroupingAccessMapper.convertToTargetDao(arg, mappedObjects));");
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
    public void testCreateAccessMapperParentSingleRef() {
        when(entity.getParentRefs()).thenReturn(Arrays.asList(parentReference));
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.OwnerDao;");
        expected.add("import de.test.package.dao.grouping.DummyDao;");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.Owner;");
        expected.add("import de.test.package.domain.grouping.Dummy;");
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
        expected.add("		GroupingAccessMapper.convertToTarget(dummy.getTargetRef(), result, mappedObjects);");
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
        expected.add("		Dummy result = convertToDummy(dummy, mappedObjects);");
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
        expected.add("		GroupingAccessMapper.convertToTargetDao(dummy.getTargetRef(), result, mappedObjects);");
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
        expected.add("		DummyDao result = convertToDummyDao(dummy, mappedObjects);");
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
    public void testCreateAccessMapperMultiParentSingleRef() {
        when(entity.getParentRefs()).thenReturn(Arrays.asList(parentReference, anotherParentReference));
        when(parentReference.isList()).thenReturn(Boolean.FALSE);
        when(anotherParentReference.isList()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.AnotherOwnerDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.OwnerDao;");
        expected.add("import de.test.package.dao.grouping.DummyDao;");
        expected.add("import de.test.package.domain.AnotherOwner;");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.Owner;");
        expected.add("import de.test.package.domain.grouping.Dummy;");
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
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, AnotherOwner parent) {");
        expected.add("		return convertToDummy(dummy, parent, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, AnotherOwner parent, Map<String, IIdentifiable> mappedObjects) {");
        expected.add("		Dummy result = convertToDummy(dummy, mappedObjects);");
        expected.add("		if (result != null) {");
        expected.add("			parent.setAnotherDummy(result);");
        expected.add("		}");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, Owner parent) {");
        expected.add("		return convertToDummy(dummy, parent, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, Owner parent, Map<String, IIdentifiable> mappedObjects) {");
        expected.add("		Dummy result = convertToDummy(dummy, mappedObjects);");
        expected.add("		if (result != null) {");
        expected.add("			parent.setDummy(result);");
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
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, AnotherOwnerDao parent) {");
        expected.add("		return convertToDummyDao(dummy, parent, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, AnotherOwnerDao parent, Map<String, IIdentifiableDao> mappedObjects) {");
        expected.add("		DummyDao result = convertToDummyDao(dummy, mappedObjects);");
        expected.add("		if (result != null) {");
        expected.add("			result.setParentAnotherOwner(parent);");
        expected.add("			parent.setAnotherDummy(result);");
        expected.add("		}");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, OwnerDao parent) {");
        expected.add("		return convertToDummyDao(dummy, parent, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, OwnerDao parent, Map<String, IIdentifiableDao> mappedObjects) {");
        expected.add("		DummyDao result = convertToDummyDao(dummy, mappedObjects);");
        expected.add("		if (result != null) {");
        expected.add("			result.setParentOwner(parent);");
        expected.add("			parent.setDummy(result);");
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
    public void testCreateAccessMapperParentMultiRef() {
        when(entity.getParentRefs()).thenReturn(Arrays.asList(parentReference));
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.OwnerDao;");
        expected.add("import de.test.package.dao.grouping.DummyDao;");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.Owner;");
        expected.add("import de.test.package.domain.grouping.Dummy;");
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
        expected.add("					GroupingAccessMapper.convertToTarget(arg, result, mappedObjects)");
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
        expected.add("		Dummy result = convertToDummy(dummy, includeChildren, mappedObjects);");
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
        expected.add("					GroupingAccessMapper.convertToTargetDao(arg, result, mappedObjects)");
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
        expected.add("		DummyDao result = convertToDummyDao(dummy, includeChildren, mappedObjects);");
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
    public void testCreateAccessMapperMultiParentMultiRef() {
        when(entity.getParentRefs()).thenReturn(Arrays.asList(parentReference, anotherParentReference));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.AnotherOwnerDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.OwnerDao;");
        expected.add("import de.test.package.dao.grouping.DummyDao;");
        expected.add("import de.test.package.domain.AnotherOwner;");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.Owner;");
        expected.add("import de.test.package.domain.grouping.Dummy;");
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
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, AnotherOwner parent) {");
        expected.add("		return convertToDummy(dummy, parent, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, AnotherOwner parent, Map<String, IIdentifiable> mappedObjects) {");
        expected.add("		Dummy result = convertToDummy(dummy, mappedObjects);");
        expected.add("		if (result != null) {");
        expected.add("			parent.getAnotherDummys().add(result);");
        expected.add("		}");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, Owner parent) {");
        expected.add("		return convertToDummy(dummy, parent, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static Dummy convertToDummy(DummyDao dummy, Owner parent, Map<String, IIdentifiable> mappedObjects) {");
        expected.add("		Dummy result = convertToDummy(dummy, mappedObjects);");
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
        expected.add("		mappedObjects.put(identification, result);");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, AnotherOwnerDao parent) {");
        expected.add("		return convertToDummyDao(dummy, parent, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, AnotherOwnerDao parent, Map<String, IIdentifiableDao> mappedObjects) {");
        expected.add("		DummyDao result = convertToDummyDao(dummy, mappedObjects);");
        expected.add("		if (result != null) {");
        expected.add("			result.setParentAnotherOwner(parent);");
        expected.add("			parent.getAnotherDummys().add(result);");
        expected.add("		}");
        expected.add("		return result;");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, OwnerDao parent) {");
        expected.add("		return convertToDummyDao(dummy, parent, new HashMap<>());");
        expected.add("	}");
        expected.add("");
        expected.add("	public static DummyDao convertToDummyDao(Dummy dummy, OwnerDao parent, Map<String, IIdentifiableDao> mappedObjects) {");
        expected.add("		DummyDao result = convertToDummyDao(dummy, mappedObjects);");
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
    public void testCreateAccessMapperUseIdGenerator() {
        when(config.isUseIdGenerator()).thenReturn(Boolean.TRUE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.grouping.DummyDao;");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.grouping.Dummy;");
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
    public void testCreateAccessMapperSingleRefWithChildren() {
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetEntity.getReferences()).thenReturn(Arrays.asList(subReference));
        when(subReference.getParent()).thenReturn(targetEntity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.grouping.DummyDao;");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.grouping.Dummy;");
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
        expected.add("		GroupingAccessMapper.convertToTarget(dummy.getTargetRef(), includeChildren, result, mappedObjects);");
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
        expected.add("		GroupingAccessMapper.convertToTargetDao(dummy.getTargetRef(), includeChildren, result, mappedObjects);");
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
    public void testCreateAccessMapperSingleRefNotOwnerWithChildren() {
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(targetReference.isOwner()).thenReturn(Boolean.FALSE);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetEntity.getReferences()).thenReturn(Arrays.asList(subReference));
        when(subReference.getParent()).thenReturn(targetEntity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.grouping.DummyDao;");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.grouping.Dummy;");
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
        expected.add("		result.setTargetRef(GroupingAccessMapper.convertToTarget(dummy.getTargetRef(), includeChildren, mappedObjects));");
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
        expected.add("		result.setTargetRef(GroupingAccessMapper.convertToTargetDao(dummy.getTargetRef(), includeChildren, mappedObjects));");
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
    public void testCreateAccessMapperMultiRefWithChildren() {
        when(targetReference.getParent()).thenReturn(entity);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetEntity.getReferences()).thenReturn(Arrays.asList(subReference));
        when(subReference.getParent()).thenReturn(targetEntity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.grouping.DummyDao;");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.grouping.Dummy;");
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
        expected.add("					GroupingAccessMapper.convertToTarget(arg, true, result, mappedObjects)");
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
        expected.add("					GroupingAccessMapper.convertToTargetDao(arg, true, result, mappedObjects)");
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
    public void testCreateAccessMapperMultiRefNotOwnerWithChildren() {
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isOwner()).thenReturn(Boolean.FALSE);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetEntity.getReferences()).thenReturn(Arrays.asList(subReference));
        when(subReference.getParent()).thenReturn(targetEntity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.grouping.DummyDao;");
        expected.add("import de.test.package.dao.grouping.DummyToTargetDao;");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.grouping.Dummy;");
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
        expected.add("					GroupingAccessMapper.convertToTarget(arg.getDummy(), true, result, mappedObjects)");
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
        expected.add("				connectionTable.setDummy(GroupingAccessMapper.convertToTargetDao(arg, true, mappedObjects));");
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
    public void testCreateAccessMapperGroupingWithDot() {
        when(grouping.getGroupingPackage()).thenReturn("group.subgroup");
        
        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.mapper;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.group.subgroup.DummyDao;");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.group.subgroup.Dummy;");
        expected.add("import java.util.HashMap;");
        expected.add("import java.util.Map;");
        expected.add("");
        expected.add("public class GroupSubgroupAccessMapper {");
        expected.add("");
        expected.add("	private GroupSubgroupAccessMapper() {");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * singleton");
        expected.add("	 */");
        expected.add("	private static GroupSubgroupAccessMapper instance;");
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
        expected.add("	public static GroupSubgroupAccessMapper getInstance() {");
        expected.add("		if (instance == null) {");
        expected.add("			instance = new GroupSubgroupAccessMapper();");
        expected.add("		}");
        expected.add("		return instance;");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createAccessMapper(entities, "group.subgroup", MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sAccessMapper.java", "GroupSubgroup"), expected);
    }

    @Test
    public void testCreateAccessMapperNothingToMap(){
        when(entity.getModels()).thenReturn(Models.DTO);
        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));
        assertEquals(0, writtenFileContents.size(), "No Mapper should be generated for only dto");

        when(entity.getModels()).thenReturn(Models.DOMAIN);
        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));
        assertEquals(0, writtenFileContents.size(), "No Mapper should be generated for only domain");

        when(entity.getModels()).thenReturn(Models.DAO);
        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));
        assertEquals(0, writtenFileContents.size(), "No Mapper should be generated for only dao");
    }

    @Test
    public void testCreateAccessMapperNoAllToMap(){
        when(parentEntity.getModels()).thenReturn(Models.DOMAIN_DTO);
        when(subEntity.getModels()).thenReturn(Models.DAO);
        entities.add(parentEntity);
        entities.add(subEntity);

        List<String> expected = getDefaultExpected();

        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sAccessMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void testCreateAccessMapperMultiRefParentNotRelevant(){
        when(parentEntity.getModels()).thenReturn(Models.DOMAIN_DTO);
        when(entity.getParentRefs()).thenReturn(Arrays.asList(parentReference));

        List<String> expected = getDefaultExpected();

        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sAccessMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void testCreateAccessMapperSingleRefParentNotRelevant(){
        when(entity.getParentRefs()).thenReturn(Arrays.asList(parentReference));
        when(parentReference.isList()).thenReturn(Boolean.FALSE);
        when(parentEntity.getModels()).thenReturn(Models.DOMAIN_DTO);

        List<String> expected = getDefaultExpected();

        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sAccessMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void testCreateAccessMapperMultiRefChildNotRelevant(){
        when(targetEntity.getModels()).thenReturn(Models.DOMAIN_DTO);
        when(targetReference.getParent()).thenReturn(entity);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));

        List<String> expected = getDefaultExpected();

        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sAccessMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }

    @Test
    public void testCreateAccessMapperSingleRefChildNotRelevant(){
        when(targetEntity.getModels()).thenReturn(Models.DOMAIN_DTO);
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));

        List<String> expected = getDefaultExpected();

        assertTrue(cut.createAccessMapper(entities, GROUPING_NAME, MAPPER_PACKAGE_NAME, DAO_PACKAGE_NAME, DOMAIN_PACKAGE_NAME, basePackageDir));

        checkSingleFile(String.format("%sAccessMapper.java", AbstractCreator.getUpperFirst(GROUPING_NAME)), expected);
    }
}
