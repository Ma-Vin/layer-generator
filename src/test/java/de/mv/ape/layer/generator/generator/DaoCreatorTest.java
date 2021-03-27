package de.mv.ape.layer.generator.generator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import de.mv.ape.layer.generator.config.elements.*;
import de.mv.ape.layer.generator.log.LogImpl;
import de.mv.ape.layer.generator.sources.TestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
public class DaoCreatorTest extends AbstractCreatorTest {

    @Mock
    private Entity parentEntity;
    @Mock
    private Reference parentReference;

    private DaoCreator cut;

    private List<String> directoriesWhereRequestedToWrite = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        super.setUp();

        cut = new DaoCreator(config, new LogImpl()) {
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

        directoriesWhereRequestedToWrite.clear();
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
        when(parentReference.isList()).thenReturn(Boolean.TRUE);
        when(parentReference.isOwner()).thenReturn(Boolean.TRUE);
    }

    @Test
    public void testCreateDataAccessObjectDefault() {
        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import javax.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@Entity()");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id()");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", basePackageDir));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectNoDao() {
        when(entity.getModels()).thenReturn(Models.DOMAIN);

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", basePackageDir));

        assertEquals(0, writtenFileContents.size(), "Wrong number of files");
    }

    @Test
    public void testCreateDataAccessObjectNoGrouping() {
        when(entity.getGrouping()).thenReturn(null);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao;");
        expected.add("");
        expected.add("import javax.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@Entity()");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id()");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", basePackageDir));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectUniqueRelation() {
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import javax.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@Entity()");
        expected.add("@EqualsAndHashCode(exclude = {\"targetRef\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"targetRef\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id()");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@OneToOne(targetEntity = TargetDao.class, mappedBy = \"parentDummy\")");
        expected.add("	private TargetDao targetRef;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", basePackageDir));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectRelation() {
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import java.util.Collection;");
        expected.add("import javax.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@Entity()");
        expected.add("@EqualsAndHashCode(exclude = {\"targetRefs\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"targetRefs\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id()");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@OneToMany(targetEntity = TargetDao.class, mappedBy = \"parentDummy\")");
        expected.add("	private Collection<TargetDao> targetRefs;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", basePackageDir));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectUniqueParentRelation() {
        when(entity.getParentRefs()).thenReturn(Arrays.asList(parentReference));
        when(parentReference.getParent()).thenReturn(entity);
        when(parentReference.isList()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.OwnerDao;");
        expected.add("import javax.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@Entity()");
        expected.add("@EqualsAndHashCode(exclude = {\"parentOwner\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"parentOwner\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id()");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@JoinColumn(name = \"OwnerId\", nullable = false)");
        expected.add("	@OneToOne(targetEntity = OwnerDao.class)");
        expected.add("	private OwnerDao parentOwner;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", basePackageDir));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectParentRelation() {
        when(entity.getParentRefs()).thenReturn(Arrays.asList(parentReference));
        when(parentReference.getParent()).thenReturn(entity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.OwnerDao;");
        expected.add("import javax.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@Entity()");
        expected.add("@EqualsAndHashCode(exclude = {\"parentOwner\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"parentOwner\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id()");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@JoinColumn(name = \"OwnerId\", nullable = false)");
        expected.add("	@ManyToOne(targetEntity = OwnerDao.class)");
        expected.add("	private OwnerDao parentOwner;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", basePackageDir));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectMultiDifferentParentRelation() {
        Reference anotherParentReference = mock(Reference.class);
        Entity anotherParentEntity = mock(Entity.class);

        when(entity.getParentRefs()).thenReturn(Arrays.asList(parentReference, anotherParentReference));
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

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import de.test.package.dao.AnotherOwnerDao;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.dao.OwnerDao;");
        expected.add("import javax.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@Entity()");
        expected.add("@EqualsAndHashCode(exclude = {\"parentOwner\", \"parentAnotherOwner\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"parentOwner\", \"parentAnotherOwner\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id()");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@JoinColumn(name = \"AnotherOwnerId\")");
        expected.add("	@ManyToOne(targetEntity = AnotherOwnerDao.class)");
        expected.add("	private AnotherOwnerDao parentAnotherOwner;");
        expected.add("");
        expected.add("	@JoinColumn(name = \"OwnerId\")");
        expected.add("	@ManyToOne(targetEntity = OwnerDao.class)");
        expected.add("	private OwnerDao parentOwner;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", basePackageDir));

        checkSingleFile("DummyDao.java", expected);
    }


    @Test
    public void testCreateDataAccessObjectUniqueRelationNotOwner() {
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);
        when(targetReference.isOwner()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import javax.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@Entity()");
        expected.add("@EqualsAndHashCode(exclude = {\"targetRef\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"targetRef\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id()");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@JoinColumn(name = \"targetRefId\")");
        expected.add("	@ManyToOne(targetEntity = TargetDao.class)");
        expected.add("	private TargetDao targetRef;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", basePackageDir));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectRelationNotOwner() {
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isOwner()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import java.util.Collection;");
        expected.add("import javax.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@Entity()");
        expected.add("@EqualsAndHashCode(exclude = {\"targetRefs\"})");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("@ToString(exclude = {\"targetRefs\"})");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id()");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@OneToMany(targetEntity = DummyToTargetDao.class, mappedBy = \"target\")");
        expected.add("	private Collection<DummyToTargetDao> targetRefs;");
        expected.add("");
        expected.add("}");

        List<String> expectedConnection = new ArrayList<>();
        expectedConnection.add("package de.test.package.dao.group;");
        expectedConnection.add("");
        expectedConnection.add("import java.io.Serializable;");
        expectedConnection.add("import javax.persistence.*;");
        expectedConnection.add("import lombok.AllArgsConstructor;");
        expectedConnection.add("import lombok.Data;");
        expectedConnection.add("import lombok.NoArgsConstructor;");
        expectedConnection.add("");
        expectedConnection.add("@AllArgsConstructor()");
        expectedConnection.add("@Data()");
        expectedConnection.add("@Entity()");
        expectedConnection.add("@IdClass(DummyToTargetDao.DummyToTargetId.class)");
        expectedConnection.add("@NoArgsConstructor()");
        expectedConnection.add("@SuppressWarnings(\"java:S1948\")");
        expectedConnection.add("@Table(name = \"DummyToTargets\")");
        expectedConnection.add("public class DummyToTargetDao {");
        expectedConnection.add("");
        expectedConnection.add("	@Id()");
        expectedConnection.add("	@JoinColumn(name = \"DummyId\")");
        expectedConnection.add("	@ManyToOne(targetEntity = DummyDao.class)");
        expectedConnection.add("	private DummyDao dummy;");
        expectedConnection.add("");
        expectedConnection.add("	@Id()");
        expectedConnection.add("	@JoinColumn(name = \"TargetId\")");
        expectedConnection.add("	@OneToOne(targetEntity = TargetDao.class)");
        expectedConnection.add("	private TargetDao target;");
        expectedConnection.add("");
        expectedConnection.add("	@AllArgsConstructor()");
        expectedConnection.add("	@Data()");
        expectedConnection.add("	@NoArgsConstructor()");
        expectedConnection.add("	@SuppressWarnings(\"java:S1068\")");
        expectedConnection.add("	public static class DummyToTargetId implements Serializable {");
        expectedConnection.add("");
        expectedConnection.add("		private Long dummyId;");
        expectedConnection.add("");
        expectedConnection.add("		private Long targetId;");
        expectedConnection.add("");
        expectedConnection.add("	}");
        expectedConnection.add("");
        expectedConnection.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", basePackageDir));

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
    public void testCreateDataAccessObjectUniqueParentRelationNotOwner() {
        when(entity.getParentRefs()).thenReturn(Arrays.asList(parentReference));
        when(parentReference.getParent()).thenReturn(entity);
        when(parentReference.isList()).thenReturn(Boolean.FALSE);
        when(parentReference.isOwner()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import javax.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@Entity()");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id()");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", basePackageDir));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectParentRelationNotOwner() {
        when(entity.getParentRefs()).thenReturn(Arrays.asList(parentReference));
        when(parentReference.getParent()).thenReturn(entity);
        when(parentReference.isOwner()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import javax.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@Entity()");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id()");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", basePackageDir));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectField() {
        when(entity.getFields()).thenReturn(Arrays.asList(field));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import javax.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@Entity()");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column()");
        expected.add("	private String anyField;");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id()");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", basePackageDir));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectFieldEnum() {
        when(entity.getFields()).thenReturn(Arrays.asList(field));
        when(field.isTypeEnum()).thenReturn(Boolean.TRUE);
        when(field.getType()).thenReturn("AnyEnum");
        when(field.getTypePackage()).thenReturn("the.enum.package");

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import javax.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("import the.enum.package.AnyEnum;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@Entity()");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column()");
        expected.add("	@Enumerated(EnumType.STRING)");
        expected.add("	private AnyEnum anyField;");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id()");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", basePackageDir));

        checkSingleFile("DummyDao.java", expected);
    }

    @Test
    public void testCreateDataAccessObjectUseIdGenerator() {
        when(config.isUseIdGenerator()).thenReturn(Boolean.TRUE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group;");
        expected.add("");
        expected.add("import de.mv.ape.utils.generators.IdGenerator;");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import de.test.package.domain.group.Dummy;");
        expected.add("import javax.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@Entity()");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id()");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@Override()");
        expected.add("	public String getIdentification() {");
        expected.add("		return IdGenerator.generateIdentification(id, Dummy.ID_PREFIX);");
        expected.add("	}");
        expected.add("");
        expected.add("	@Override()");
        expected.add("	public void setIdentification(String identification) {");
        expected.add("		id = IdGenerator.generateId(identification, Dummy.ID_PREFIX);");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", basePackageDir));

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

        assertTrue(cut.createDataAccessObjectInterface(BASE_PACKAGE + ".dao", basePackageDir));

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

        assertTrue(cut.createDataAccessObjectInterface(BASE_PACKAGE + ".dao", basePackageDir));

        checkSingleFile(DaoCreator.DAO_INTERFACE + ".java", expected);
    }

    @Test
    public void testCreateDataAccessObjectGroupingWithDots() {
        when(grouping.getGroupingPackage()).thenReturn("group.subgroup");

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.dao.group.subgroup;");
        expected.add("");
        expected.add("import de.test.package.dao.IIdentifiableDao;");
        expected.add("import javax.persistence.*;");
        expected.add("import lombok.Data;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated dao class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@Entity()");
        expected.add("@Table(name = \"Dummys\")");
        expected.add("public class DummyDao implements IIdentifiableDao {");
        expected.add("");
        expected.add("	@Column(name = \"Id\")");
        expected.add("	@GeneratedValue(strategy = GenerationType.IDENTITY)");
        expected.add("	@Id()");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDataAccessObject(entity, BASE_PACKAGE + ".dao", basePackageDir));

        assertFalse(directoriesWhereRequestedToWrite.contains("group.subgroup"), "Not any directories with dots should be used");
        assertTrue(directoriesWhereRequestedToWrite.contains("group\\subgroup"), "Dot should be replaced by backslash");

        checkSingleFile("DummyDao.java", expected);
    }
}
