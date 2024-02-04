package de.ma_vin.util.layer.generator.config.loader;

import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.config.elements.Version;
import de.ma_vin.util.layer.generator.config.elements.fields.Field;
import de.ma_vin.util.layer.generator.config.elements.references.Reference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link VersionCompleter} is the class under test
 */
public class VersionCompleterTest extends AbstractCompleterTest {
    private static final String REFERENCE_NAME = "ReferenceName";
    private static final String ENTITY_NAME = "EntityName";
    private static final String GROUPING_ENTITY_NAME = "GroupingEntityName";
    private static final String FIELD_NAME = "FieldName";
    private static final String VERSION_ID = "V1";

    private VersionCompleter cut;

    @Mock
    private Version version;
    @Mock
    private Version groupingVersion;
    @Mock
    private Field field;
    @Mock
    private Reference firstCopyReference;
    @Mock
    private Reference secondCopyReference;
    @Mock
    private Entity versionEntity;
    @Mock
    private Entity versionGroupingEntity;


    protected final List<Reference> versionReferences = new ArrayList<>();
    protected final List<Reference> versionEntityReferences = new ArrayList<>();
    protected final List<Reference> versionGroupingEntityReferences = new ArrayList<>();

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();

        cut = new VersionCompleter(logger);

        versionReferences.clear();
        versionEntityReferences.clear();
        versionGroupingEntityReferences.clear();

        mockDefaultReferences();
        mockDefaultEntities();
        mockDefaultVersions();

        when(field.getFieldName()).thenReturn(FIELD_NAME);
    }

    private void mockDefaultReferences() {
        mockDefaultReference(reference, entity, groupingEntity, GROUPING_ENTITY_NAME, REFERENCE_NAME);
        mockDefaultReference(firstCopyReference, entity, groupingEntity, GROUPING_ENTITY_NAME, REFERENCE_NAME);
        mockDefaultReference(secondCopyReference, entity, groupingEntity, GROUPING_ENTITY_NAME, REFERENCE_NAME);

        when(reference.copy()).thenReturn(firstCopyReference, secondCopyReference);
        when(firstCopyReference.copy()).thenReturn(secondCopyReference);
        references.add(reference);
    }

    private void mockDefaultReference(Reference versionReference, Entity source, Entity target, String targetName, String refName) {
        when(versionReference.getReferenceName()).thenReturn(refName);
        when(versionReference.getTargetEntity()).thenReturn(targetName);
        doAnswer(a -> when(versionReference.getTargetEntity()).thenReturn(a.getArgument(0))).when(versionReference).setTargetEntity(any());
        when(versionReference.getRealTargetEntity()).thenReturn(target);
        doAnswer(a -> when(versionReference.getRealTargetEntity()).thenReturn(a.getArgument(0))).when(versionReference).setRealTargetEntity(any());
        when(versionReference.getParent()).thenReturn(source);
        doAnswer(a -> when(versionReference.getParent()).thenReturn(a.getArgument(0))).when(versionReference).setParent(any());
        when(versionReference.isReverse()).thenReturn(Boolean.FALSE);
        doAnswer(a -> when(versionReference.isReverse()).thenReturn(a.getArgument(0))).when(versionReference).setReverse(anyBoolean());
    }

    private void mockDefaultEntities() {
        mockDefaultEntity(entity, ENTITY_NAME, version, versionEntity, versionReferences, versionEntityReferences);
        mockDefaultEntity(groupingEntity, GROUPING_ENTITY_NAME, groupingVersion, versionGroupingEntity, Collections.emptyList(), versionGroupingEntityReferences);
    }

    private void mockDefaultEntity(Entity entity, String entityName, Version version, Entity versionEntity, List<Reference> versionReferences, List<Reference> versionEntityReferences) {
        when(entity.getBaseName()).thenReturn(entityName);
        when(entity.getTableName()).thenReturn(entityName);
        when(entity.getVersions()).thenReturn(Collections.singletonList(version));
        when(entity.copyForVersion(eq(version))).then(a -> {
            versionEntityReferences.addAll(versionReferences);
            return versionEntity;
        });

        mockDefaultVersionEntity(versionEntity, entityName, version, versionEntityReferences);
    }

    private void mockDefaultVersionEntity(Entity versionEntity, String entityName, Version version, List<Reference> versionEntityReferences) {
        when(versionEntity.getBaseName()).thenReturn(entityName);
        when(versionEntity.getTableName()).thenReturn(entityName);
        when(versionEntity.getActualVersion()).thenReturn(version);
        when(versionEntity.getReferences()).thenReturn(versionEntityReferences);
        doAnswer(a -> {
            versionEntityReferences.clear();
            versionEntityReferences.addAll(a.getArgument(0));
            return null;
        }).when(versionEntity).setReferences(anyList());
    }

    private void mockDefaultVersions() {
        versionReferences.add(reference);
        when(version.getReferences()).thenReturn(versionReferences);
        when(version.determineReferences(eq(entity))).thenReturn(versionReferences);
        when(version.getFields()).thenReturn(Collections.singletonList(field));
        when(version.determineFields(eq(entity))).thenReturn(Collections.singletonList(field));
        when(version.determineBaseVersion(any())).thenReturn(Optional.empty());
        when(version.determineReferenceTargetVersion(any())).thenReturn(Optional.of(groupingVersion));
        when(version.getVersionId()).thenReturn(VERSION_ID);
        when(version.getVersionEntity()).thenReturn(versionEntity);
        when(version.getParentEntity()).thenReturn(entity);


        when(groupingVersion.getReferences()).thenReturn(Collections.emptyList());
        when(groupingVersion.determineReferences(any())).thenReturn(Collections.emptyList());
        when(groupingVersion.getFields()).thenReturn(Collections.emptyList());
        when(groupingVersion.determineFields(any())).thenReturn(Collections.emptyList());
        when(groupingVersion.determineBaseVersion(any())).thenReturn(Optional.empty());
        when(groupingVersion.determineReferenceTargetVersion(any())).thenReturn(Optional.empty());
        when(groupingVersion.getVersionId()).thenReturn(VERSION_ID);
        when(groupingVersion.getVersionEntity()).thenReturn(versionGroupingEntity);
        when(groupingVersion.getParentEntity()).thenReturn(groupingEntity);
    }

    @DisplayName("Complete entity with version, but target of reference not")
    @Test
    public void testCompleteReferenceNonVersionedTarget() {
        when(groupingEntity.getVersions()).thenReturn(Collections.emptyList());
        when(version.determineReferenceTargetVersion(eq(reference))).thenReturn(Optional.empty());

        assertTrue(cut.complete(config), "The result should be true");

        verify(entity).copyForVersion(version);
        verify(versionEntity).setReferences(eq(Collections.singletonList(firstCopyReference)));
        verify(versionEntity).setParentRefs(eq(Collections.emptyList()));

        verify(reference).copy();
        verify(reference, never()).setRealTargetEntity(any());
        verify(firstCopyReference, never()).setRealTargetEntity(any());
        verify(firstCopyReference, never()).copy();

        verify(version).setParentEntity(eq(entity));
        verify(version).setBaseVersion(eq(Optional.empty()));
        verify(version).setFields(eq(Collections.singletonList(field)));
        verify(version).setReferences(eq(Collections.singletonList(reference)));
        verify(version).generateVersionName();
    }

    @DisplayName("Complete entity with version added reference, but target of reference not")
    @Test
    public void testCompleteAddedReference() {
        references.clear();
        when(reference.getRealTargetEntity()).thenReturn(null);
        when(groupingEntity.getVersions()).thenReturn(Collections.emptyList());
        when(version.determineReferenceTargetVersion(eq(reference))).thenReturn(Optional.empty());

        assertTrue(cut.complete(config), "The result should be true");

        verify(entity).copyForVersion(version);
        verify(versionEntity).setReferences(eq(Collections.singletonList(firstCopyReference)));
        verify(versionEntity).setParentRefs(eq(Collections.emptyList()));

        verify(reference).copy();
        verify(reference).setRealTargetEntity(eq(groupingEntity));
        verify(firstCopyReference, never()).setRealTargetEntity(any());
        verify(firstCopyReference, never()).copy();

        verify(version).setParentEntity(eq(entity));
        verify(version).setBaseVersion(eq(Optional.empty()));
        verify(version).setFields(eq(Collections.singletonList(field)));
        verify(version).setReferences(eq(Collections.singletonList(reference)));
        verify(version).generateVersionName();
    }

    @DisplayName("Complete entity with version added reference but missing target")
    @Test
    public void testCompleteAddedReferenceMissingTarget() {
        references.clear();
        when(reference.getRealTargetEntity()).thenReturn(null);
        when(groupingEntity.getBaseName()).thenReturn(GROUPING_ENTITY_NAME + "abc");

        assertFalse(cut.complete(config), "The result should be false");
        assertEquals(0, versionEntityReferences.size(), "There should not be any versioned reference");

        verify(entity, never()).copyForVersion(version);
        verify(reference, never()).copy();
        verify(firstCopyReference, never()).copy();
    }

    @DisplayName("Complete entity with reference to versioned target")
    @Test
    public void testCompleteReferenceWithVersion() {
        assertTrue(cut.complete(config), "The result should be true");
        assertEquals(1, versionEntityReferences.size(), "There should not be any versioned reference");

        verify(firstCopyReference).setRealTargetEntity(eq(versionGroupingEntity));
        verify(secondCopyReference).setRealTargetEntity(eq(versionEntity));
        verify(versionGroupingEntity).setParentRefs(eq(Collections.singletonList(secondCopyReference)));
    }

    @DisplayName("Complete entity with reference to versioned target, but itself not versioned")
    @Test
    public void testCompleteReferenceWithVersionButSourceNotVersioned() {
        when(groupingEntity.getVersions()).thenReturn(Collections.singletonList(groupingVersion));

        when(version.determineReferenceTargetVersion(reference)).thenReturn(Optional.of(groupingVersion));
        when(entity.getVersions()).thenReturn(Collections.emptyList());
        when(secondCopyReference.getRealTargetEntity()).thenReturn(null);

        assertTrue(cut.complete(config), "The result should be true");
        assertEquals(0, versionEntityReferences.size(), "There should not be any versioned reference");

        verify(reference, never()).copy();
        verify(secondCopyReference, never()).setRealTargetEntity(any());
        verify(versionGroupingEntity).setParentRefs(eq(Collections.emptyList()));
    }
}
