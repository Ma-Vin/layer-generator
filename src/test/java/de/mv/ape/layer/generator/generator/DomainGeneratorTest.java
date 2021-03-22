package de.mv.ape.layer.generator.generator;

import de.mv.ape.layer.generator.config.elements.*;
import de.mv.ape.layer.generator.log.LogImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DomainGeneratorTest extends AbstractCreatorTest {

    private DomainCreator cut;

    @BeforeEach
    public void setUp() {
        super.setUp();

        cut = new DomainCreator(config, new LogImpl()) {
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
    public void testCreateDomainObjectDefault() {
        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.domain.group;");
        expected.add("");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated domain class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@NoArgsConstructor()");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class Dummy implements IIdentifiable {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDomainObject(entity, BASE_PACKAGE + ".domain", basePackageDir));

        checkSingleFile("Dummy.java", expected);
    }


    @Test
    public void testCreateDomainObjectNoDomain() {
        when(entity.getModels()).thenReturn(Models.DAO);

        assertTrue(cut.createDomainObject(entity, BASE_PACKAGE + ".domain", basePackageDir));

        assertEquals(0, writtenFileContents.size(), "Wrong number of files");
    }

    @Test
    public void testCreateDomainObjectNoGrouping() {
        when(entity.getGrouping()).thenReturn(null);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.domain;");
        expected.add("");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated domain class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@NoArgsConstructor()");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class Dummy implements IIdentifiable {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDomainObject(entity, BASE_PACKAGE + ".domain", basePackageDir));

        checkSingleFile("Dummy.java", expected);
    }

    @Test
    public void testCreateDomainObjectUniqueRelation() {
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);
        when(targetReference.isList()).thenReturn(Boolean.FALSE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.domain.group;");
        expected.add("");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.group.Target;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated domain class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@EqualsAndHashCode(exclude = {\"targetRef\"})");
        expected.add("@NoArgsConstructor()");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("@ToString(exclude = {\"targetRef\"})");
        expected.add("public class Dummy implements IIdentifiable {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	private Target targetRef;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDomainObject(entity, BASE_PACKAGE + ".domain", basePackageDir));

        checkSingleFile("Dummy.java", expected);
    }

    @Test
    public void testCreateDomainObjectRelation() {
        when(entity.getReferences()).thenReturn(Arrays.asList(targetReference));
        when(targetReference.getParent()).thenReturn(entity);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.domain.group;");
        expected.add("");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import de.test.package.domain.group.Target;");
        expected.add("import java.util.Collection;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.EqualsAndHashCode;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("import lombok.Setter;");
        expected.add("import lombok.ToString;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated domain class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@EqualsAndHashCode(exclude = {\"targetRefs\"})");
        expected.add("@NoArgsConstructor()");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("@ToString(exclude = {\"targetRefs\"})");
        expected.add("public class Dummy implements IIdentifiable {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("	@Setter(AccessLevel.PROTECTED)");
        expected.add("	private Collection<Target> targetRefs = new HashSet<>();");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Adds a Target");
        expected.add("	 * ");
        expected.add("	 * @param targetRef Target to add");
        expected.add("	 */");
        expected.add("	public boolean addTarget(Target targetRef) {");
        expected.add("		return targetRefs.add(targetRef);");
        expected.add("	}");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Removes a Target");
        expected.add("	 * ");
        expected.add("	 * @param targetRef Target to remove");
        expected.add("	 */");
        expected.add("	public boolean removeTarget(Target targetRef) {");
        expected.add("		return targetRefs.remove(targetRef);");
        expected.add("	}");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDomainObject(entity, BASE_PACKAGE + ".domain", basePackageDir));

        checkSingleFile("Dummy.java", expected);
    }

    @Test
    public void testCreateDomainObjectField() {
        when(entity.getFields()).thenReturn(Arrays.asList(field));

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.domain.group;");
        expected.add("");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated domain class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@NoArgsConstructor()");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class Dummy implements IIdentifiable {");
        expected.add("");
        expected.add("	private String anyField;");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDomainObject(entity, BASE_PACKAGE + ".domain", basePackageDir));

        checkSingleFile("Dummy.java", expected);
    }

    @Test
    public void testCreateDomainObjectFieldEnum() {
        when(entity.getFields()).thenReturn(Arrays.asList(field));
        when(field.isTypeEnum()).thenReturn(Boolean.TRUE);
        when(field.getType()).thenReturn("AnyEnum");
        when(field.getTypePackage()).thenReturn("the.enum.package");

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.domain.group;");
        expected.add("");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("import the.enum.package.AnyEnum;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated domain class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@NoArgsConstructor()");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class Dummy implements IIdentifiable {");
        expected.add("");
        expected.add("	private AnyEnum anyField;");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Id of Dummy");
        expected.add("	 */");
        expected.add("	private Long id;");
        expected.add("");
        expected.add("}");


        assertTrue(cut.createDomainObject(entity, BASE_PACKAGE + ".domain", basePackageDir));

        checkSingleFile("Dummy.java", expected);
    }

    @Test
    public void testCreateDomainObjectUseIdGenerator() {
        when(config.isUseIdGenerator()).thenReturn(Boolean.TRUE);

        List<String> expected = new ArrayList<>();
        expected.add("package de.test.package.domain.group;");
        expected.add("");
        expected.add("import de.test.package.domain.IIdentifiable;");
        expected.add("import lombok.Data;");
        expected.add("import lombok.NoArgsConstructor;");
        expected.add("");
        expected.add("/**");
        expected.add(" * Generated domain class of Dummy");
        expected.add(" * <br>");
        expected.add(" * Dummy description");
        expected.add(" */");
        expected.add("@Data()");
        expected.add("@NoArgsConstructor()");
        expected.add("@SuppressWarnings(\"java:S1068\")");
        expected.add("public class Dummy implements IIdentifiable {");
        expected.add("");
        expected.add("	public static final String ID_PREFIX = \"DU\";");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * Identification of Dummy");
        expected.add("	 */");
        expected.add("	private String identification;");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDomainObject(entity, BASE_PACKAGE + ".domain", basePackageDir));

        checkSingleFile("Dummy.java", expected);
    }

    @Test
    public void testCreateDomainObjectInterface() {
        List<String> expected = new ArrayList<>();

        expected.add("package de.test.package.domain;");
        expected.add("");
        expected.add("public interface IIdentifiable {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the id of the domain object");
        expected.add("	 */");
        expected.add("	Long getId();");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @param id the id of the domain object");
        expected.add("	 */");
        expected.add("	void setId(Long id);");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDomainObjectInterface(BASE_PACKAGE + ".domain", basePackageDir));

        checkSingleFile(DomainCreator.DOMAIN_INTERFACE + ".java", expected);
    }

    @Test
    public void testCreateDomainObjectInterfaceUseIdGenerator() {
        when(config.isUseIdGenerator()).thenReturn(Boolean.TRUE);
        List<String> expected = new ArrayList<>();

        expected.add("package de.test.package.domain;");
        expected.add("");
        expected.add("public interface IIdentifiable {");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @return the identification of the domain object");
        expected.add("	 */");
        expected.add("	String getIdentification();");
        expected.add("");
        expected.add("	/**");
        expected.add("	 * @param identification the identification of the domain object");
        expected.add("	 */");
        expected.add("	void setIdentification(String identification);");
        expected.add("");
        expected.add("}");

        assertTrue(cut.createDomainObjectInterface(BASE_PACKAGE + ".domain", basePackageDir));

        checkSingleFile(DomainCreator.DOMAIN_INTERFACE + ".java", expected);
    }
}
