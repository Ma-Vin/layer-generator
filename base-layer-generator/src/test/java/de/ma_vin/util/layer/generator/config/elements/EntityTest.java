package de.ma_vin.util.layer.generator.config.elements;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

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
}
