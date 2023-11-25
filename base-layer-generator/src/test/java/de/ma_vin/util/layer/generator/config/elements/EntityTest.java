package de.ma_vin.util.layer.generator.config.elements;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import de.ma_vin.util.layer.generator.config.elements.fields.Field;
import de.ma_vin.util.layer.generator.config.elements.references.Reference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntityTest {

    private AutoCloseable openMocks;

    @InjectMocks
    private Entity cut;
    private final List<String> messages = new ArrayList<>();

    @Mock
    private Version version;
    @Mock
    private Reference reference;
    @Mock
    private Field field;

    @BeforeEach
    public void setUp() {
        openMocks = openMocks(this);

        cut.setBaseName("baseName");
        cut.setTableName("tableName");
        cut.setDescription("description");
        cut.setIdentificationPrefix("identificationPrefix");
        cut.setParent("parent");
        cut.setDerivedFrom("derivedFrom");
        cut.setFields(Collections.emptyList());
        cut.setIndices(Collections.emptyList());
        cut.setReferences(Collections.emptyList());
        cut.setVersions(Collections.emptyList());

        messages.clear();

        when(version.isValid(anyList(), eq(cut))).thenReturn(Boolean.TRUE);
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    public void testHasNoParent() {
        cut.setParent(null);
        assertTrue(cut.hasNoParent(), "Null should be interpreted as has no parent");
        cut.setParent(" ");
        assertTrue(cut.hasNoParent(), "Trimmed empty string should be interpreted as has no parent");
        cut.setParent("a");
        assertFalse(cut.hasNoParent(), "Non empty string should not be interpreted as has no parent");
    }

    @Test
    public void testHasParent() {
        cut.setParent(null);
        assertFalse(cut.hasParent(), "Null should not be interpreted as has parent");
        cut.setParent(" ");
        assertFalse(cut.hasParent(), "Trimmed empty string should be interpreted as has parent");
        cut.setParent("a");
        assertTrue(cut.hasParent(), "Non empty string should be interpreted as has parent");
    }

    @Test
    public void testIsValid() {
        assertTrue(cut.isValid(messages), "Entity should be valid");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidNulls() {
        cut.setTableName(null);
        cut.setDescription(null);
        cut.setIdentificationPrefix(null);
        cut.setParent(null);
        cut.setFields(null);
        cut.setIndices(null);
        cut.setReferences(null);
        cut.setVersions(null);
        assertTrue(cut.isValid(messages), "Entity should be valid");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidBaseNameNull() {
        cut.setBaseName(null);
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidTableNameEmptyString() {
        cut.setTableName("");
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidDescriptionEmptyString() {
        cut.setDescription("");
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidIdentificationPrefixEmptyString() {
        cut.setIdentificationPrefix("");
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidParentEmptyString() {
        cut.setParent("");
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidDerivedFromEmptyString() {
        cut.setDerivedFrom("");
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidVersion() {
        cut.setDerivedFrom(null);
        cut.setVersions(Collections.singletonList(version));

        assertTrue(cut.isValid(messages), "Entity should not be valid");
    }

    @Test
    public void testIsValidVersionInvalid() {
        when(version.isValid(anyList(), eq(cut))).thenReturn(Boolean.FALSE);
        cut.setVersions(Collections.singletonList(version));

        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidVersionAndDerivedFrom() {
        cut.setVersions(Collections.singletonList(version));

        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidVersionAndIsAbstract() {
        cut.setVersions(Collections.singletonList(version));
        cut.setDerivedFrom(null);
        cut.setIsAbstract(Boolean.TRUE);

        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidVersionAndButNotDtoModel() {
        cut.setVersions(Collections.singletonList(version));
        cut.setDerivedFrom(null);
        cut.setModels(Models.DOMAIN_DAO);

        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testCopyForVersion() {
        cut.setVersions(Collections.singletonList(version));
        when(version.getFields()).thenReturn(Collections.singletonList(field));
        when(version.getReferences()).thenReturn(Collections.singletonList(reference));
        when(version.getVersionName()).thenReturn("baseNameV1");

        Entity result = cut.copyForVersion(version);
        assertNotNull(result, "There should be a result");
        assertEquals("baseNameV1", result.getBaseName(), "wrong base name");
        assertEquals(cut.getTableName(), result.getTableName(),"Wrong table name");
        assertEquals(cut.getModels(),result.getModels(), "Wrong model");
        assertEquals(cut.getDescription(), result.getDescription(),"Wrong description");
        assertEquals(cut.getIdentificationPrefix(), result.getIdentificationPrefix(), "Wrong id prefix");
        assertEquals(cut.getIsAbstract(), result.getIsAbstract(), "Wrong is abstract");
        assertEquals(cut.getGenIdIfDto(), result.getGenIdIfDto(), "Wrong gen id info");
        assertEquals(cut.getGrouping(), result.getGrouping(), "Wrong grouping");
        assertNull(result.getParent(), "Parent should be null");
        assertNull(result.getRealParent(), "realParent should be null");
        assertNull(result.getDerivedFrom(), "derivedFrom should be null");
        assertNull(result.getRealDerivedFrom(), "realDerivedFrom should be null");
        assertTrue(result.getIndices().isEmpty(), "indices should be empty");
        assertTrue(result.getVersions().isEmpty(), "versions should be empty");
        assertTrue(result.getParentRefs().isEmpty(), "parentRefs should be empty");

        assertEquals(1, result.getFields().size(), "Wrong number of fields");
        assertTrue(result.getFields().contains(field), "the field should be contained");
        assertEquals(1, result.getReferences().size(), "Wrong number of references");
        assertTrue(result.getReferences().contains(reference), "the reference should be contained");
    }
}
