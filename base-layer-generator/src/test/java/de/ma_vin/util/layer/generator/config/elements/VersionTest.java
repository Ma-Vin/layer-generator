package de.ma_vin.util.layer.generator.config.elements;

import de.ma_vin.util.layer.generator.config.elements.fields.Field;
import de.ma_vin.util.layer.generator.config.elements.fields.VersionField;
import de.ma_vin.util.layer.generator.config.elements.references.Reference;
import de.ma_vin.util.layer.generator.config.elements.references.VersionReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * {@link Version} is the class under test
 */
public class VersionTest {

    public static final String DEFAULT_VERSION_ID = "v2";
    public static final String DEFAULT_BASE_VERSION_ID = "v11";
    public static final String DEFAULT_ENTITY_NAME = "Entity";
    public static final String DEFAULT_REMOVED_FIELD_NAME = "field1";
    public static final String DEFAULT_BASE_VERSION_FIELD_NAME = "field2";
    public static final String DEFAULT_ENTITY_FIELD_NAME = "field3";
    public static final String DEFAULT_ADDED_FIELD_NAME = "field4";
    public static final String DEFAULT_REMOVED_REFERENCE_NAME = "reference1";
    public static final String DEFAULT_BASE_VERSION_REFERENCE_NAME = "reference2";
    public static final String DEFAULT_ENTITY_REFERENCE_NAME = "reference3";
    public static final String DEFAULT_ADDED_REFERENCE_NAME = "reference4";

    private AutoCloseable openMocks;

    @InjectMocks
    private Version cut;

    @Mock
    private VersionField addedField;
    @Mock
    private Field baseVersionField;
    @Mock
    private Field entityField;
    @Mock
    private Field removedField;
    @Mock
    private VersionReference addedReference;
    @Mock
    private Reference baseVersionReference;
    @Mock
    private Reference entityReference;
    @Mock
    private Reference removedReference;
    @Mock
    private Version baseVersion;
    @Mock
    private Version referenceTargetVersion;
    @Mock
    private Entity parentEntity;
    @Mock
    private Entity referenceTargetEntity;

    private final List<String> messages = new ArrayList<>();


    @BeforeEach
    public void setUp() {
        openMocks = openMocks(this);

        mockEntityMocks();
        mockReferenceMocks();
        mockFieldMocks();
        mockVersionMocks();

        cut.setAddedFields(Collections.singletonList(addedField));
        cut.setAddedReferences(Collections.singletonList(addedReference));
        cut.setRemovedFieldNames(Collections.singletonList(DEFAULT_REMOVED_FIELD_NAME));
        cut.setRemovedReferenceNames(Collections.singletonList(DEFAULT_REMOVED_REFERENCE_NAME));
        cut.setVersionId(DEFAULT_VERSION_ID);
        cut.setBaseVersionId(DEFAULT_BASE_VERSION_ID);

        messages.clear();
    }

    private void mockEntityMocks() {
        when(parentEntity.getVersions()).thenReturn(Collections.singletonList(baseVersion));
        when(parentEntity.getFields()).thenReturn(Arrays.asList(entityField, removedField));
        when(parentEntity.getReferences()).thenReturn(Arrays.asList(entityReference, removedReference));
        when(parentEntity.getBaseName()).thenReturn(DEFAULT_ENTITY_NAME);

        when(referenceTargetEntity.getVersions()).thenReturn(Collections.singletonList(referenceTargetVersion));
        when(referenceTargetEntity.getFields()).thenReturn(Collections.emptyList());
        when(referenceTargetEntity.getReferences()).thenReturn(Collections.emptyList());
        when(referenceTargetEntity.getBaseName()).thenReturn(DEFAULT_ENTITY_NAME + "2");
    }

    private void mockReferenceMocks() {
        when(addedReference.getReferenceName()).thenReturn(DEFAULT_ADDED_REFERENCE_NAME);
        when(addedReference.isValid(anyList())).thenReturn(Boolean.TRUE);
        doCallRealMethod().when(addedReference).getAsReference();

        when(baseVersionReference.getReferenceName()).thenReturn(DEFAULT_BASE_VERSION_REFERENCE_NAME);
        when(baseVersionReference.isValid(anyList())).thenReturn(Boolean.TRUE);

        when(entityReference.getReferenceName()).thenReturn(DEFAULT_ENTITY_REFERENCE_NAME);
        when(entityReference.isValid(anyList())).thenReturn(Boolean.TRUE);
        when(entityReference.getRealTargetEntity()).thenReturn(referenceTargetEntity);

        when(removedReference.getReferenceName()).thenReturn(DEFAULT_REMOVED_REFERENCE_NAME);
        when(removedReference.isValid(anyList())).thenReturn(Boolean.TRUE);
    }

    private void mockFieldMocks() {
        when(addedField.getFieldName()).thenReturn(DEFAULT_ADDED_FIELD_NAME);
        when(addedField.isValid(anyList())).thenReturn(Boolean.TRUE);
        doCallRealMethod().when(addedField).getAsField();

        when(baseVersionField.getFieldName()).thenReturn(DEFAULT_BASE_VERSION_FIELD_NAME);
        when(baseVersionField.isValid(anyList())).thenReturn(Boolean.TRUE);

        when(entityField.getFieldName()).thenReturn(DEFAULT_ENTITY_FIELD_NAME);
        when(entityField.isValid(anyList())).thenReturn(Boolean.TRUE);

        when(removedField.getFieldName()).thenReturn(DEFAULT_REMOVED_FIELD_NAME);
        when(removedField.isValid(anyList())).thenReturn(Boolean.TRUE);
    }

    private void mockVersionMocks() {
        when(baseVersion.determineFields(eq(parentEntity))).thenReturn(Arrays.asList(entityField, removedField, baseVersionField));
        when(baseVersion.determineReferences(eq(parentEntity))).thenReturn(Arrays.asList(entityReference, removedReference, baseVersionReference));
        doCallRealMethod().when(baseVersion).setVersionId(anyString());
        doCallRealMethod().when(baseVersion).getVersionId();

        baseVersion.setVersionId(DEFAULT_BASE_VERSION_ID);

        when(referenceTargetVersion.determineFields(any())).thenReturn(Collections.emptyList());
        when(referenceTargetVersion.determineReferences(any())).thenReturn(Collections.emptyList());
        doCallRealMethod().when(referenceTargetVersion).setVersionId(anyString());
        doCallRealMethod().when(referenceTargetVersion).getVersionId();

        referenceTargetVersion.setVersionId(DEFAULT_VERSION_ID);
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    public void testIsValid() {
        assertTrue(cut.isValid(messages, parentEntity), "Entity should be valid");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidNulls() {
        cut.setAddedFields(null);
        cut.setAddedReferences(null);
        cut.setBaseVersionId(null);

        assertTrue(cut.isValid(messages, parentEntity), "Entity should be valid");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidMissingVersion() {
        cut.setVersionId(null);

        assertFalse(cut.isValid(messages, parentEntity), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidEmptyVersionName() {
        cut.setVersionName("");

        assertFalse(cut.isValid(messages, parentEntity), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidMissingFieldToRemove() {
        when(parentEntity.getFields()).thenReturn(Collections.singletonList(entityField));
        when(baseVersion.determineFields(eq(parentEntity))).thenReturn(Arrays.asList(entityField, baseVersionField));

        assertFalse(cut.isValid(messages, parentEntity), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidMissingReferenceToRemove() {
        when(parentEntity.getReferences()).thenReturn(Collections.singletonList(entityReference));
        when(baseVersion.determineReferences(eq(parentEntity))).thenReturn(Arrays.asList(entityReference, baseVersionReference));

        assertFalse(cut.isValid(messages, parentEntity), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidInvalidFieldToAdd() {
        when(addedField.isValid(anyList())).then(a -> {
            ((List<String>) a.getArgument(0)).add("dummy");
            return Boolean.FALSE;
        });

        assertFalse(cut.isValid(messages, parentEntity), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidEInvalidReferenceToAdd() {
        when(addedReference.isValid(anyList())).then(a -> {
            ((List<String>) a.getArgument(0)).add("dummy");
            return Boolean.FALSE;
        });

        assertFalse(cut.isValid(messages, parentEntity), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidMissingBaseVersionName() {
        cut.setBaseVersionId(DEFAULT_BASE_VERSION_ID + "1");

        assertFalse(cut.isValid(messages, parentEntity), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testDetermineFields() {
        List<Field> result = cut.determineFields(parentEntity);

        assertNotNull(result, "There should be a result");
        assertEquals(3, result.size(), "Wrong number of results");
        assertTrue(result.stream().anyMatch(f -> DEFAULT_ADDED_FIELD_NAME.equals(f.getFieldName())), "addedField should be contained");
        assertTrue(result.contains(baseVersionField), "baseVersionField should be contained");
        assertTrue(result.contains(entityField), "entityField should be contained");
        assertFalse(result.contains(removedField), "removedField should not be contained");
    }

    @Test
    public void testDetermineFieldsNullLists() {
        cut.setAddedFields(null);
        cut.setRemovedFieldNames(null);

        List<Field> result = cut.determineFields(parentEntity);

        assertNotNull(result, "There should be a result");
        assertEquals(3, result.size(), "Wrong number of results");
        assertFalse(result.stream().anyMatch(f -> DEFAULT_ADDED_FIELD_NAME.equals(f.getFieldName())), "addedField should not be contained");
        assertTrue(result.contains(baseVersionField), "baseVersionField should be contained");
        assertTrue(result.contains(entityField), "entityField should be contained");
        assertTrue(result.contains(removedField), "removedField should be contained");
    }

    @Test
    public void testDetermineFieldsOverrideExisting() {
        when(addedField.getFieldName()).thenReturn(DEFAULT_ENTITY_FIELD_NAME);

        List<Field> result = cut.determineFields(parentEntity);

        assertNotNull(result, "There should be a result");
        assertEquals(2, result.size(), "Wrong number of results");
        assertTrue(result.stream().anyMatch(f -> DEFAULT_ENTITY_FIELD_NAME.equals(f.getFieldName())), "field name should be contained");
        assertFalse(result.stream().anyMatch(f -> DEFAULT_ADDED_FIELD_NAME.equals(f.getFieldName())), "addedField name should not be contained");
        assertTrue(result.contains(baseVersionField), "baseVersionField should be contained");
        assertFalse(result.contains(entityField), "entityField should not be contained");
        assertFalse(result.contains(removedField), "removedField should not be contained");
    }

    @Test
    public void testDetermineReferences() {
        List<Reference> result = cut.determineReferences(parentEntity);

        assertNotNull(result, "There should be a result");
        assertEquals(3, result.size(), "Wrong number of results");
        assertTrue(result.stream().anyMatch(r -> DEFAULT_ADDED_REFERENCE_NAME.equals(r.getReferenceName())), "addedReference should be contained");
        assertTrue(result.contains(baseVersionReference), "baseVersionReference should be contained");
        assertTrue(result.contains(entityReference), "entityReference should be contained");
        assertFalse(result.contains(removedReference), "removedReference should not be contained");
    }

    @Test
    public void testDetermineReferencesNullLists() {
        cut.setAddedReferences(null);
        cut.setRemovedReferenceNames(null);

        List<Reference> result = cut.determineReferences(parentEntity);

        assertNotNull(result, "There should be a result");
        assertEquals(3, result.size(), "Wrong number of results");
        assertFalse(result.stream().anyMatch(r -> DEFAULT_ADDED_REFERENCE_NAME.equals(r.getReferenceName())), "addedReference should not be contained");
        assertTrue(result.contains(baseVersionReference), "baseVersionReference should be contained");
        assertTrue(result.contains(entityReference), "entityReference should be contained");
        assertTrue(result.contains(removedReference), "removedReference should be contained");
    }

    @Test
    public void testDetermineReferencesOverrideExisting() {
        when(addedReference.getReferenceName()).thenReturn(DEFAULT_ENTITY_REFERENCE_NAME);

        List<Reference> result = cut.determineReferences(parentEntity);

        assertNotNull(result, "There should be a result");
        assertEquals(2, result.size(), "Wrong number of results");
        assertTrue(result.stream().anyMatch(r -> DEFAULT_ENTITY_REFERENCE_NAME.equals(r.getReferenceName())), "reference name should be contained");
        assertFalse(result.stream().anyMatch(r -> DEFAULT_ADDED_REFERENCE_NAME.equals(r.getReferenceName())), "addedReference name should not be contained");
        assertTrue(result.contains(baseVersionReference), "baseVersionReference should be contained");
        assertFalse(result.contains(entityReference), "entityReference should not be contained");
        assertFalse(result.contains(removedReference), "removedReference should not be contained");
    }


    @Test
    public void testGenerateVersionName() {
        cut.setParentEntity(parentEntity);

        cut.generateVersionName();

        assertNotNull(cut.getVersionName(), "there should be a version name");
        assertEquals(DEFAULT_ENTITY_NAME + "V2", cut.getVersionName(), "Wrong version name");
    }

    @Test
    public void testGenerateVersionNameSingleSign() {
        cut.setVersionId("2");
        cut.setParentEntity(parentEntity);

        cut.generateVersionName();

        assertNotNull(cut.getVersionName(), "there should be a version name");
        assertEquals(DEFAULT_ENTITY_NAME + "2", cut.getVersionName(), "Wrong version name");
    }

    @Test
    public void testGenerateVersionNameAlreadySet() {
        cut.setVersionName("abc");
        cut.setParentEntity(parentEntity);

        cut.generateVersionName();

        assertNotNull(cut.getVersionName(), "there should be a version name");
        assertEquals("abc", cut.getVersionName(), "Wrong version name");
    }

    @Test
    public void testDetermineReferenceTargetVersionEqualVersionId() {
        Optional<Version> result = cut.determineReferenceTargetVersion(entityReference);

        assertNotNull(result, "There should be a result");
        assertTrue(result.isPresent(), "the result should be present");
        assertEquals(referenceTargetVersion, result.get(), "Wrong version");
    }

    @Test
    public void testDetermineReferenceTargetVersionDifferentVersionId() {
        when(entityReference.getReferenceName()).thenReturn(DEFAULT_ADDED_REFERENCE_NAME);
        when(addedReference.getDivergentTargetVersion()).thenReturn(DEFAULT_BASE_VERSION_ID);
        referenceTargetVersion.setVersionId(DEFAULT_BASE_VERSION_ID);

        Optional<Version> result = cut.determineReferenceTargetVersion(entityReference);

        assertNotNull(result, "There should be a result");
        assertTrue(result.isPresent(), "the result should be present");
        assertEquals(referenceTargetVersion, result.get(), "Wrong version");

    }

    @Test
    public void testDetermineReferenceTargetVersionNon() {
        referenceTargetVersion.setVersionId(DEFAULT_BASE_VERSION_ID);

        Optional<Version> result = cut.determineReferenceTargetVersion(entityReference);

        assertNotNull(result, "There should be a result");
        assertTrue(result.isEmpty(), "the result should be empty");

    }
}
