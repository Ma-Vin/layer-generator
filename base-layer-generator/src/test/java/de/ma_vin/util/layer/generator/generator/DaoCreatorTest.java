package de.ma_vin.util.layer.generator.generator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import de.ma_vin.util.layer.generator.config.elements.*;
import de.ma_vin.util.layer.generator.config.elements.fields.FieldSorting;
import de.ma_vin.util.layer.generator.config.elements.references.Reference;
import com.github.ma_vin.util.layer_generator.logging.Log4jLogImpl;
import com.github.ma_vin.util.layer_generator.sources.TestUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.tools.JavaFileObject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Log4j2
public class DaoCreatorTest extends AbstractCreatorTest {

    @Mock
    private Entity parentEntity;
    @Mock
    private Reference parentReference;
    @Mock
    private DaoInfo daoInfo;

    private DaoCreator cut;

    private final List<String> directoriesWhereRequestedToWrite = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        super.setUp();

        cut = new DaoCreator(config, new Log4jLogImpl()) {
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

        directoriesWhereRequestedToWrite.clear();
    }

    @Override
    protected void initDefaultMock() {
        super.initDefaultMock();

        when(parentEntity.getBaseName()).thenReturn("Owner");
        when(parentEntity.getTableName()).thenReturn("Owner");
        when(parentEntity.getDescription()).thenReturn("Owner description");
        when(parentEntity.getIdentificationPrefix()).thenReturn("OW");
        when(parentEntity.getModels()).thenReturn(Models.DOMAIN_DAO_DTO);
        when(parentEntity.getGrouping()).thenReturn(null);

        setMockReturnsReference(parentReference, "owner", "Owner", null, null, Boolean.TRUE, Boolean.TRUE);
        setMockReturnsReference(parentReference, null, parentEntity, null);

        doAnswer(a -> when(parentReference.getReferenceName()).thenReturn(a.getArgument(0)))
                .when(parentReference).setReferenceName(anyString());
    }

    @DisplayName("create data access object with common file")
    @Test
    public void testCreateDataAccessObjectCommonFile() {
        List<String> expected = getDefaultExpected();

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);

        verify(processingEnv, never()).getFiler();
    }

    @DisplayName("create data access object with java file object")
    @Test
    public void testCreateDataAccessObjectJavaFileObject() throws IOException {
        List<String> expected = getDefaultExpected();
        cut.setGenerateJavaFileObject(true);
        cut.setProcessingEnv(Optional.of(processingEnv));

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.empty()));

        checkSingleFile(String.format("%s.dao.group.DummyDao", BASE_PACKAGE), expected);

        verify(processingEnv).getFiler();
        verify(filer).createSourceFile(eq(String.format("%s.dao.group.DummyDao", BASE_PACKAGE)));
    }

    private List<String> getDefaultExpected() {
        List<String> expected = new ArrayList<>();

        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        return expected;
    }

    @Test
    public void testCreateDataAccessObjectNoDao() {
        when(entity.getModels()).thenReturn(Models.DOMAIN);

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        assertEquals(0, writtenFileContents.size(), "Wrong number of files");
    }

    @Test
    public void testCreateDataAccessObjectNoGrouping() {
        when(entity.getGrouping()).thenReturn(null);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectUniqueRelation() {
        when(entity.getReferences()).thenReturn(Collections.singletonList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@EqualsAndHashCode(exclude = {\"targetRef\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"targetRef\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@OneToOne(mappedBy = \"parentDummy\", targetEntity = TargetDao.class)");
        expected.add("	private TargetDao targetRef;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }


    @DisplayName("Create a data object with an one to one relation, but the target does not support the dao model")
    @Test
    public void testCreateDataAccessObjectUniqueRelationButNonDao() {
        when(entity.getReferences()).thenReturn(Collections.singletonList(targetReference));
        when(targetEntity.getModels()).thenReturn(Models.DOMAIN_DTO);
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectRelation() {
        when(entity.getReferences()).thenReturn(Collections.singletonList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import java.util.Collection;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@EqualsAndHashCode(exclude = {\"targetRef\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"targetRef\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@OneToMany(mappedBy = \"parentDummy\", targetEntity = TargetDao.class)");
        expected.add("	private Collection<TargetDao> targetRef;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }


    @DisplayName("Create a data object with an one to many relation, but the target does not support the dao model")
    @Test
    public void testCreateDataAccessObjectRelationButNonDao() {
        when(entity.getReferences()).thenReturn(Collections.singletonList(targetReference));
        when(targetEntity.getModels()).thenReturn(Models.DOMAIN_DTO);
        when(targetReference.getParent()).thenReturn(entity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectUniqueParentRelation() {
        when(entity.getNonVersionedParentRefs()).thenReturn(Collections.singletonList(parentReference));
        when(parentReference.getParent()).thenReturn(entity);
        when(parentReference.isList()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.OwnerDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@EqualsAndHashCode(exclude = {\"parentOwner\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"parentOwner\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@JoinColumn(name = \"ParentOwnerId\", nullable = false)");
        expected.add("	@OneToOne(targetEntity = OwnerDao.class)");
        expected.add("	private OwnerDao parentOwner;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }


    @DisplayName("Create a data object with an one to one relation from parent, but the parent does not support the dao model")
    @Test
    public void testCreateDataAccessObjectUniqueParentRelationButNonDao() {
        when(entity.getNonVersionedParentRefs()).thenReturn(Collections.singletonList(parentReference));
        when(parentReference.getParent()).thenReturn(entity);
        when(parentReference.isList()).thenReturn(Boolean.FALSE);
        when(parentEntity.getModels()).thenReturn(Models.DOMAIN_DTO);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }


    @Test
    public void testCreateDataAccessObjectParentRelation() {
        when(entity.getNonVersionedParentRefs()).thenReturn(Collections.singletonList(parentReference));
        when(parentReference.getParent()).thenReturn(entity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.OwnerDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@EqualsAndHashCode(exclude = {\"parentOwner\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"parentOwner\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@JoinColumn(name = \"ParentOwnerId\", nullable = false)");
        expected.add("	@ManyToOne(targetEntity = OwnerDao.class)");
        expected.add("	private OwnerDao parentOwner;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @DisplayName("Create a data object with an many to one relation from parent, but the parent does not support the dao model")
    @Test
    public void testCreateDataAccessObjectParentRelationButNonDao() {
        when(entity.getNonVersionedParentRefs()).thenReturn(Collections.singletonList(parentReference));
        when(parentReference.getParent()).thenReturn(entity);
        when(parentEntity.getModels()).thenReturn(Models.DOMAIN_DTO);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }


    @Test
    public void testCreateDataAccessObjectMultiDifferentParentRelation() {
        Reference anotherParentReference = mock(Reference.class);
        Entity anotherParentEntity = mock(Entity.class);

        when(entity.getNonVersionedParentRefs()).thenReturn(Arrays.asList(parentReference, anotherParentReference));
        when(parentReference.getParent()).thenReturn(entity);
        when(anotherParentReference.getParent()).thenReturn(entity);

        when(anotherParentEntity.getBaseName()).thenReturn("AnotherOwner");
        when(anotherParentEntity.getDescription()).thenReturn("Another Owner description");
        when(anotherParentEntity.getIdentificationPrefix()).thenReturn("AOW");
        when(anotherParentEntity.getModels()).thenReturn(Models.DOMAIN_DAO_DTO);
        when(anotherParentEntity.getGrouping()).thenReturn(null);

        when(anotherParentReference.getTargetEntity()).thenReturn("AnotherOwner");
        when(anotherParentReference.getRealTargetEntity()).thenReturn(anotherParentEntity);
        when(anotherParentReference.isList()).thenReturn(Boolean.TRUE);
        when(anotherParentReference.isOwner()).thenReturn(Boolean.TRUE);
        when(anotherParentReference.getIsOwner()).thenReturn(Boolean.TRUE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.AnotherOwnerDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.OwnerDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@EqualsAndHashCode(exclude = {\"parentOwner\", \"parentAnotherOwner\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"parentOwner\", \"parentAnotherOwner\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@JoinColumn(name = \"ParentAnotherOwnerId\")");
        expected.add("	@ManyToOne(targetEntity = AnotherOwnerDao.class)");
        expected.add("	private AnotherOwnerDao parentAnotherOwner;");
        expected.add("");
        expected.add("	@JoinColumn(name = \"ParentOwnerId\")");
        expected.add("	@ManyToOne(targetEntity = OwnerDao.class)");
        expected.add("	private OwnerDao parentOwner;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }


    @Test
    public void testCreateDataAccessObjectUniqueRelationNotOwner() {
        when(entity.getReferences()).thenReturn(Collections.singletonList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(targetReference.isOwner()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@EqualsAndHashCode(exclude = {\"targetRef\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"targetRef\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@JoinColumn(name = \"targetRefId\")");
        expected.add("	@ManyToOne(targetEntity = TargetDao.class)");
        expected.add("	private TargetDao targetRef;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectRelationNotOwner() {
        when(entity.getReferences()).thenReturn(Collections.singletonList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isOwner()).thenReturn(Boolean.FALSE);
        when(targetReference.getIsOwner()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import java.util.Collection;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@EqualsAndHashCode(exclude = {\"targetRef\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"targetRef\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@OneToMany(mappedBy = \"dummy\", targetEntity = DummyToTargetDao.class)");
        expected.add("	private Collection<DummyToTargetDao> targetRef;");
        expected.add("");
        expected.add("}");

        List<String> expectedConnection = new ArrayList<>();
        expectedConnection.add("package de.test.package.dao.group;");
        expectedConnection.add("");
        expectedConnection.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expectedConnection.add("import jakarta.persistence.*;");
        expectedConnection.add("import java.io.Serializable;");
        expectedConnection.add("import lombok.AllArgsConstructor;");
        expectedConnection.add("import lombok.Data;");
        expectedConnection.add("import lombok.NoArgsConstructor;");
        expectedConnection.add("");
        expectedConnection.add("@AllArgsConstructor");
        expectedConnection.add("@BaseDao(\"de.test.package.dao\")");
        expectedConnection.add("@Data");
        expectedConnection.add("@Entity");
        expectedConnection.add("@IdClass(DummyToTargetDao.DummyToTargetId.class)");
        expectedConnection.add("@NoArgsConstructor");
        expectedConnection.add("@SuppressWarnings(\"java:S1948\")");
        expectedConnection.add("@Table(name = \"DummyToTargets\")");
        expectedConnection.add("public class DummyToTargetDao {");
        expectedConnection.add("");
        expectedConnection.add("	@Id");
        expectedConnection.add("	@JoinColumn(name = \"DummyId\")");
        expectedConnection.add("	@ManyToOne(targetEntity = DummyDao.class)");
        expectedConnection.add("	private DummyDao dummy;");
        expectedConnection.add("");
        expectedConnection.add("	@Id");
        expectedConnection.add("	@JoinColumn(name = \"TargetId\")");
        expectedConnection.add("	@OneToOne(targetEntity = TargetDao.class)");
        expectedConnection.add("	private TargetDao target;");
        expectedConnection.add("");
        expectedConnection.add("	@AllArgsConstructor");
        expectedConnection.add("	@Data");
        expectedConnection.add("	@NoArgsConstructor");
        expectedConnection.add("	@SuppressWarnings(\"java:S1068\")");
        expectedConnection.add("	public static class DummyToTargetId implements Serializable {");
        expectedConnection.add("");
        expectedConnection.add("		private Long dummy;");
        expectedConnection.add("");
        expectedConnection.add("		private Long target;");
        expectedConnection.add("");
        expectedConnection.add("	}");
        expectedConnection.add("");
        expectedConnection.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        assertEquals(2, writtenFileContents.size(), "Wrong number of files");
        assertTrue(writtenFileContents.containsKey("DummyDao.java"));
        assertTrue(writtenFileContents.containsKey("DummyToTargetDao.java"));

        if (expected.size() != writtenFileContents.get("DummyDao.java").size()
                || expectedConnection.size() != writtenFileContents.get("DummyToTargetDao.java").size()) {
            logFileContents();
        }
        TestUtil.checkList(expected, writtenFileContents.get("DummyDao.java"));
        TestUtil.checkList(expectedConnection, writtenFileContents.get("DummyToTargetDao.java"));
    }

    @Test
    public void testCreateDataAccessObjectRelationNotOwnerSameTarget() {
        when(entity.getReferences()).thenReturn(Collections.singletonList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isOwner()).thenReturn(Boolean.FALSE);
        when(targetReference.getIsOwner()).thenReturn(Boolean.FALSE);
        when(targetEntity.getBaseName()).thenReturn(ENTITY_NAME);
        when(targetEntity.getTableName()).thenReturn(ENTITY_NAME);
        setMockReturnsReference(targetReference, "Sub" + ENTITY_NAME, ENTITY_NAME, null, null, Boolean.TRUE, Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import java.util.Collection;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@EqualsAndHashCode(exclude = {\"subDummy\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"subDummy\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@OneToMany(mappedBy = \"dummy\", targetEntity = DummyToDummyDao.class)");
        expected.add("	private Collection<DummyToDummyDao> subDummy;");
        expected.add("");
        expected.add("}");

        List<String> expectedConnection = new ArrayList<>();
        expectedConnection.add("package de.test.package.dao.group;");
        expectedConnection.add("");
        expectedConnection.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expectedConnection.add("import jakarta.persistence.*;");
        expectedConnection.add("import java.io.Serializable;");
        expectedConnection.add("import lombok.AllArgsConstructor;");
        expectedConnection.add("import lombok.Data;");
        expectedConnection.add("import lombok.NoArgsConstructor;");
        expectedConnection.add("");
        expectedConnection.add("@AllArgsConstructor");
        expectedConnection.add("@BaseDao(\"de.test.package.dao\")");
        expectedConnection.add("@Data");
        expectedConnection.add("@Entity");
        expectedConnection.add("@IdClass(DummyToDummyDao.DummyToDummyId.class)");
        expectedConnection.add("@NoArgsConstructor");
        expectedConnection.add("@SuppressWarnings(\"java:S1948\")");
        expectedConnection.add("@Table(name = \"DummyToDummys\")");
        expectedConnection.add("public class DummyToDummyDao {");
        expectedConnection.add("");
        expectedConnection.add("	@Id");
        expectedConnection.add("	@JoinColumn(name = \"DummyId\")");
        expectedConnection.add("	@ManyToOne(targetEntity = DummyDao.class)");
        expectedConnection.add("	private DummyDao dummy;");
        expectedConnection.add("");
        expectedConnection.add("	@Id");
        expectedConnection.add("	@JoinColumn(name = \"SubDummyId\")");
        expectedConnection.add("	@OneToOne(targetEntity = DummyDao.class)");
        expectedConnection.add("	private DummyDao subDummy;");
        expectedConnection.add("");
        expectedConnection.add("	@AllArgsConstructor");
        expectedConnection.add("	@Data");
        expectedConnection.add("	@NoArgsConstructor");
        expectedConnection.add("	@SuppressWarnings(\"java:S1068\")");
        expectedConnection.add("	public static class DummyToDummyId implements Serializable {");
        expectedConnection.add("");
        expectedConnection.add("		private Long dummy;");
        expectedConnection.add("");
        expectedConnection.add("		private Long subDummy;");
        expectedConnection.add("");
        expectedConnection.add("	}");
        expectedConnection.add("");
        expectedConnection.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        assertEquals(2, writtenFileContents.size(), "Wrong number of files");
        assertTrue(writtenFileContents.containsKey("DummyDao.java"));
        assertTrue(writtenFileContents.containsKey("DummyToDummyDao.java"));

        if (expected.size() != writtenFileContents.get("DummyDao.java").size()
                || expectedConnection.size() != writtenFileContents.get("DummyToDummyDao.java").size()) {
            logFileContents();
        }
        TestUtil.checkList(expected, writtenFileContents.get("DummyDao.java"));
        TestUtil.checkList(expectedConnection, writtenFileContents.get("DummyToDummyDao.java"));
    }

    @Test
    public void testCreateDataAccessObjectUniqueParentRelationNotOwner() {
        when(entity.getNonVersionedParentRefs()).thenReturn(Collections.singletonList(parentReference));
        when(parentReference.getParent()).thenReturn(entity);
        when(parentReference.isList()).thenReturn(Boolean.FALSE);
        when(parentReference.isOwner()).thenReturn(Boolean.FALSE);
        when(parentReference.getIsOwner()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectParentRelationNotOwner() {
        when(entity.getNonVersionedParentRefs()).thenReturn(Collections.singletonList(parentReference));
        when(parentReference.getParent()).thenReturn(entity);
        when(parentReference.isOwner()).thenReturn(Boolean.FALSE);
        when(parentReference.getIsOwner()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectField() {
        when(entity.getFields()).thenReturn(Collections.singletonList(field));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column");
        expected.add("	private String anyField;");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectFieldWithDaoInfo() {
        when(entity.getFields()).thenReturn(Collections.singletonList(field));
        when(field.getDaoInfo()).thenReturn(daoInfo);
        when(field.getType()).thenReturn("CustomEnum");
        when(field.getTypePackage()).thenReturn("de.test.custom");
        when(field.getIsTypeEnum()).thenReturn(Boolean.TRUE);

        when(daoInfo.getColumnName()).thenReturn("differentName");
        when(daoInfo.getNullable()).thenReturn(Boolean.FALSE);
        when(daoInfo.getLength()).thenReturn(5);
        when(daoInfo.getPrecision()).thenReturn(4);
        when(daoInfo.getScale()).thenReturn(2);
        when(daoInfo.getUseEnumText()).thenReturn(Boolean.TRUE);
        when(daoInfo.getColumnDefinition()).thenReturn("BLOB");
        when(daoInfo.getIsLobType()).thenReturn(Boolean.TRUE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.custom.CustomEnum;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(columnDefinition = \"BLOB\", length = 5, name = \"differentName\", nullable = false, precision = 4, scale = 2)");
        expected.add("	@Enumerated(EnumType.STRING)");
        expected.add("	@Lob");
        expected.add("	private CustomEnum anyField;");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectFieldWithDaoInfoDoNotUseEnumText() {
        when(entity.getFields()).thenReturn(Collections.singletonList(field));
        when(field.getDaoInfo()).thenReturn(daoInfo);
        when(field.getType()).thenReturn("CustomEnum");
        when(field.getTypePackage()).thenReturn("de.test.custom");
        when(field.getIsTypeEnum()).thenReturn(Boolean.TRUE);

        when(daoInfo.getNullable()).thenReturn(null);
        when(daoInfo.getLength()).thenReturn(null);
        when(daoInfo.getPrecision()).thenReturn(null);
        when(daoInfo.getScale()).thenReturn(null);
        when(daoInfo.getUseEnumText()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.custom.CustomEnum;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column");
        expected.add("	private CustomEnum anyField;");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectFieldEnum() {
        when(entity.getFields()).thenReturn(Collections.singletonList(field));
        when(field.getIsTypeEnum()).thenReturn(Boolean.TRUE);
        when(field.getType()).thenReturn("AnyEnum");
        when(field.getTypePackage()).thenReturn("the.enum.package");

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("import the.enum.package.AnyEnum;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column");
        expected.add("	@Enumerated(EnumType.STRING)");
        expected.add("	private AnyEnum anyField;");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectUseIdGenerator() {
        when(config.isUseIdGenerator()).thenReturn(Boolean.TRUE);
        when(config.getIdGeneratorPackage()).thenReturn("de.ma_vin.ape.utils.generators");
        when(config.getIdGeneratorClass()).thenReturn("IdGenerator");

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import de.ma_vin.ape.utils.generators.IdGenerator;");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@Override");
        expected.add("	public String getIdentification() {");
        expected.add("		return IdGenerator.generateIdentification(id, Dummy.ID_PREFIX);");
        expected.add("	}");
        expected.add("");
        expected.add("	@Override");
        expected.add("	public void setIdentification(String identification) {");
        expected.add("		id = IdGenerator.generateId(identification, Dummy.ID_PREFIX);");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectUseIdGeneratorHasSuperClass() {
        when(config.isUseIdGenerator()).thenReturn(Boolean.TRUE);
        when(config.getIdGeneratorPackage()).thenReturn("de.ma_vin.ape.utils.generators");
        when(config.getIdGeneratorClass()).thenReturn("IdGenerator");
        when(entity.getParent()).thenReturn("AnotherDummy");
        when(entity.hasParent()).thenReturn(Boolean.TRUE);
        when(entity.hasNoParent()).thenReturn(Boolean.FALSE);
        when(entity.getRealParent()).thenReturn(parentEntity);
        when(parentEntity.getBaseName()).thenReturn("AnotherDummy");

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import de.ma_vin.ape.utils.generators.IdGenerator;");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.AnotherDummyDao;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@EqualsAndHashCode(callSuper = true)");
        expected.add("@SuppressWarnings(\"java:S2160\")");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(callSuper = true)");
        expected.add("public class DummyDao extends AnotherDummyDao {");
        expected.add("");
        expected.add("	@Override");
        expected.add("	public String getIdentification() {");
        expected.add("		return IdGenerator.generateIdentification(id, Dummy.ID_PREFIX);");
        expected.add("	}");
        expected.add("");
        expected.add("	@Override");
        expected.add("	public void setIdentification(String identification) {");
        expected.add("		id = IdGenerator.generateId(identification, Dummy.ID_PREFIX);");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectUseIdGeneratorWithoutDomain() {
        when(config.isUseIdGenerator()).thenReturn(Boolean.TRUE);
        when(config.getIdGeneratorPackage()).thenReturn("de.ma_vin.ape.utils.generators");
        when(config.getIdGeneratorClass()).thenReturn("IdGenerator");
        when(entity.getModels()).thenReturn(Models.DAO);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import de.ma_vin.ape.utils.generators.IdGenerator;");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@Override");
        expected.add("	public String getIdentification() {");
        expected.add("		return IdGenerator.generateIdentification(id, \"\");");
        expected.add("	}");
        expected.add("");
        expected.add("	@Override");
        expected.add("	public void setIdentification(String identification) {");
        expected.add("		id = IdGenerator.generateId(identification, \"\");");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectInterface() {
        List<String> expected = new ArrayList<>();

        expected.add("package de.test.package.dao;");
        expected.add("");
        expected.add("public interface IIdentifiableDao {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the id of the dao");
        expected.add("	 */");
        expected.add("	Long getId();");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @param id the id of the dao");
        expected.add("	 */");
        expected.add("	void setId(Long id);");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObjectInterface(BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile(DaoCreator.DAO_INTERFACE + ".java", expected);
    }

    @Test
    public void testCreateDataAccessObjectInterfaceUseIdGenerator() {
        when(config.isUseIdGenerator()).thenReturn(Boolean.TRUE);
        List<String> expected = new ArrayList<>();

        expected.add("package de.test.package.dao;");
        expected.add("");
        expected.add("public interface IIdentifiableDao {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the id of the dao");
        expected.add("	 */");
        expected.add("	Long getId();");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the calculated identification from id of the dao");
        expected.add("	 */");
        expected.add("	String getIdentification();");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @param id the id of the dao");
        expected.add("	 */");
        expected.add("	void setId(Long id);");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @param identification the identification where to determine the id from");
        expected.add("	 */");
        expected.add("	void setIdentification(String identification);");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObjectInterface(BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile(DaoCreator.DAO_INTERFACE + ".java", expected);
    }

    @Test
    public void testCreateDataAccessObjectGroupingWithDots() {
        when(grouping.getGroupingPackage()).thenReturn("group.subgroup");

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group.subgroup;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        assertFalse(directoriesWhereRequestedToWrite.contains("group.subgroup"), "Not any directories with dots should be used");
        assertTrue(directoriesWhereRequestedToWrite.contains(String.format("group%ssubgroup", File.separator)), "Dot should be replaced by backslash");

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectIsAbstract() {
        when(entity.getIsAbstract()).thenReturn(Boolean.TRUE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data");
        expected.add("@MappedSuperclass");
        expected.add("public abstract class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	protected Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectHasSuperClass() {
        when(entity.getParent()).thenReturn("AnotherDummy");
        when(entity.hasParent()).thenReturn(Boolean.TRUE);
        when(entity.hasNoParent()).thenReturn(Boolean.FALSE);
        when(entity.getRealParent()).thenReturn(parentEntity);
        when(entity.getFields()).thenReturn(Collections.singletonList(field));
        when(parentEntity.getBaseName()).thenReturn("AnotherDummy");

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.AnotherDummyDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@EqualsAndHashCode(callSuper = true)");
        expected.add("@SuppressWarnings(\"java:S2160\")");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(callSuper = true)");
        expected.add("public class DummyDao extends AnotherDummyDao {");
        expected.add("");
        expected.add("	@Column");
        expected.add("	private String anyField;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectAggregatedChildRef() {
        Reference sameTargetReference = mock(Reference.class);

        setMockReturnsReference(sameTargetReference, "AnotherTargetRef", "Target", null, null, Boolean.TRUE, Boolean.TRUE);
        setMockReturnsReference(sameTargetReference, entity, targetEntity, null);

        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference, sameTargetReference));
        when(targetReference.getParent()).thenReturn(entity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import java.util.Collection;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@EqualsAndHashCode(exclude = {\"aggTarget\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"aggTarget\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@OneToMany(mappedBy = \"parentDummy\", targetEntity = TargetDao.class)");
        expected.add("	private Collection<TargetDao> aggTarget;");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectNotAggregatedChildRef() {
        Reference sameTargetReference = mock(Reference.class);

        setMockReturnsReference(sameTargetReference, "AnotherTargetRef", "Target", null, null, Boolean.FALSE, Boolean.FALSE);
        setMockReturnsReference(sameTargetReference, entity, targetEntity, null);

        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference, sameTargetReference));
        when(targetReference.getParent()).thenReturn(entity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import java.util.Collection;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@EqualsAndHashCode(exclude = {\"targetRef\", \"anotherTargetRef\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"targetRef\", \"anotherTargetRef\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@JoinColumn(name = \"anotherTargetRefId\")");
        expected.add("	@ManyToOne(targetEntity = TargetDao.class)");
        expected.add("	private TargetDao anotherTargetRef;");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@OneToMany(mappedBy = \"parentDummy\", targetEntity = TargetDao.class)");
        expected.add("	private Collection<TargetDao> targetRef;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectAggregatedParentRef() {
        Reference sameParentReference = mock(Reference.class);
        setMockReturnsReference(sameParentReference, "AnotherReferenceToParent", "Owner", null, null, Boolean.TRUE, Boolean.TRUE);
        setMockReturnsReference(sameParentReference, entity, parentEntity, null);

        when(entity.getNonVersionedParentRefs()).thenReturn(Arrays.asList(parentReference, sameParentReference));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.OwnerDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@EqualsAndHashCode(exclude = {\"parentOwner\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"parentOwner\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@JoinColumn(name = \"ParentOwnerId\", nullable = false)");
        expected.add("	@ManyToOne(targetEntity = OwnerDao.class)");
        expected.add("	private OwnerDao parentOwner;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectNotAggregatedParentRef() {
        Reference sameParentReference = mock(Reference.class);

        setMockReturnsReference(sameParentReference, "AnotherReferenceToParent", "Owner", null, null, Boolean.FALSE, Boolean.FALSE);
        setMockReturnsReference(sameParentReference, entity, parentEntity, null);

        when(entity.getNonVersionedParentRefs()).thenReturn(Arrays.asList(parentReference, sameParentReference));
        when(parentReference.getParent()).thenReturn(entity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.OwnerDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@EqualsAndHashCode(exclude = {\"parentOwner\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"parentOwner\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@JoinColumn(name = \"ParentOwnerId\", nullable = false)");
        expected.add("	@ManyToOne(targetEntity = OwnerDao.class)");
        expected.add("	private OwnerDao parentOwner;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectMoveOwnershipChildRef() {
        Reference sameTargetReference = mock(Reference.class);

        setMockReturnsReference(sameTargetReference, "AnotherTargetRef", "Target", null, null, Boolean.FALSE, Boolean.TRUE);
        setMockReturnsReference(sameTargetReference, entity, targetEntity, null);

        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference, sameTargetReference));
        when(targetReference.getParent()).thenReturn(entity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import java.util.Collection;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@EqualsAndHashCode(exclude = {\"targetRef\", \"anotherTargetRef\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"targetRef\", \"anotherTargetRef\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@JoinColumn(name = \"anotherTargetRefId\")");
        expected.add("	@ManyToOne(targetEntity = TargetDao.class)");
        expected.add("	private TargetDao anotherTargetRef;");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@OneToMany(mappedBy = \"parentDummy\", targetEntity = TargetDao.class)");
        expected.add("	private Collection<TargetDao> targetRef;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectWithIndex() {
        when(entity.getFields()).thenReturn(Collections.singletonList(field));
        Index index = mock(Index.class);
        when(entity.getIndices()).thenReturn(Collections.singletonList(index));
        FieldSorting fieldSorting = mock(FieldSorting.class);
        when(index.getFields()).thenReturn(Collections.singletonList(fieldSorting));
        when(index.getIndexName()).thenReturn("IX_NAME");
        when(index.getIsUnique()).thenReturn(Boolean.TRUE);
        when(fieldSorting.getField()).thenReturn(field);
        when(fieldSorting.isAscending()).thenReturn(Boolean.TRUE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@Table(indexes = {@Index(columnList = \"anyField\", name = \"IX_NAME\", unique = true)}, name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column");
        expected.add("	private String anyField;");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectWithIndexDesc() {
        when(entity.getFields()).thenReturn(Collections.singletonList(field));
        Index index = mock(Index.class);
        when(entity.getIndices()).thenReturn(Collections.singletonList(index));
        FieldSorting fieldSorting = mock(FieldSorting.class);
        when(index.getFields()).thenReturn(Collections.singletonList(fieldSorting));
        when(index.getIndexName()).thenReturn("IX_NAME");
        when(fieldSorting.getField()).thenReturn(field);
        when(fieldSorting.isAscending()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@Table(indexes = {@Index(columnList = \"anyField DESC\", name = \"IX_NAME\")}, name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column");
        expected.add("	private String anyField;");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectWithIndexDaoColumnName() {
        when(entity.getFields()).thenReturn(Collections.singletonList(field));
        Index index = mock(Index.class);
        when(entity.getIndices()).thenReturn(Collections.singletonList(index));
        FieldSorting fieldSorting = mock(FieldSorting.class);
        when(index.getFields()).thenReturn(Collections.singletonList(fieldSorting));
        when(index.getIndexName()).thenReturn("IX_NAME");
        when(fieldSorting.getField()).thenReturn(field);
        when(fieldSorting.isAscending()).thenReturn(Boolean.TRUE);

        when(field.getDaoInfo()).thenReturn(daoInfo);
        when(daoInfo.getColumnName()).thenReturn("differentName");
        when(daoInfo.getNullable()).thenReturn(null);
        when(daoInfo.getLength()).thenReturn(null);
        when(daoInfo.getPrecision()).thenReturn(null);
        when(daoInfo.getScale()).thenReturn(null);
        when(daoInfo.getUseEnumText()).thenReturn(null);


        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@Table(indexes = {@Index(columnList = \"differentName\", name = \"IX_NAME\")}, name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"differentName\")");
        expected.add("	private String anyField;");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectRelationDescription() {
        when(entity.getReferences()).thenReturn(Collections.singletonList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.getShortDescription()).thenReturn("Some description");

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import java.util.Collection;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@EqualsAndHashCode(exclude = {\"targetRef\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"targetRef\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Some description");
        expected.add("	 */");
        expected.add("	@OneToMany(mappedBy = \"parentDummy\", targetEntity = TargetDao.class)");
        expected.add("	private Collection<TargetDao> targetRef;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectUniqueRelationDescription() {
        when(entity.getReferences()).thenReturn(Collections.singletonList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(targetReference.getShortDescription()).thenReturn("Some description");

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import com.github.ma_vin.util.layer_generator.model.annotations.BaseDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import jakarta.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@BaseDao(\"de.test.package.dao\")");
        expected.add("@Data");
        expected.add("@Entity");
        expected.add("@EqualsAndHashCode(exclude = {\"targetRef\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"targetRef\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Some description");
        expected.add("	 */");
        expected.add("	@OneToOne(mappedBy = \"parentDummy\", targetEntity = TargetDao.class)");
        expected.add("	private TargetDao targetRef;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", Optional.of(basePackageDir)));

        checkSingleFile("DummyDao.java", expected);
    }
}
