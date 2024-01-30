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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    private Reference parentReference;
    @Mock
    private Reference copyReference;
    @Mock
    private Reference copyParentReference;
    @Mock
    private Entity copyEntity;
    @Mock
    private Entity copyGroupingEntity;


    protected final List<Reference> copyReferences = new ArrayList<>();
    protected final List<Reference> versionReferences = new ArrayList<>();

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();

        cut = new VersionCompleter(logger);

        copyReferences.clear();
        versionReferences.clear();

        mockDefaultReferences();
        mockDefaultEntities();
        mockDefaultVersions();

        when(field.getFieldName()).thenReturn(FIELD_NAME);
    }

    private void mockDefaultReferences() {
        when(reference.getReferenceName()).thenReturn(REFERENCE_NAME);
        when(reference.getTargetEntity()).thenReturn(GROUPING_ENTITY_NAME);
        when(reference.getRealTargetEntity()).thenReturn(groupingEntity);
        when(reference.getParent()).thenReturn(entity);
        when(reference.copy()).thenReturn(copyReference);
        references.add(reference);

        when(copyReference.getReferenceName()).thenReturn(REFERENCE_NAME);
        when(copyReference.getTargetEntity()).thenReturn(GROUPING_ENTITY_NAME);
        when(copyReference.getRealTargetEntity()).thenReturn(groupingEntity);

        when(parentReference.getReferenceName()).thenReturn(REFERENCE_NAME);
        when(parentReference.getTargetEntity()).thenReturn(ENTITY_NAME);
        when(parentReference.getRealTargetEntity()).thenReturn(entity);
        when(parentReference.getParent()).thenReturn(groupingEntity);
        when(parentReference.isReverse()).thenReturn(Boolean.TRUE);
        when(parentReference.copy()).thenReturn(copyParentReference);

        when(copyParentReference.getReferenceName()).thenReturn(REFERENCE_NAME);
        when(copyParentReference.getTargetEntity()).thenReturn(ENTITY_NAME);
        when(copyParentReference.getRealTargetEntity()).thenReturn(entity);
        when(copyParentReference.isReverse()).thenReturn(Boolean.TRUE);
    }

    private void mockDefaultEntities() {
        when(entity.getBaseName()).thenReturn(ENTITY_NAME);
        when(entity.getTableName()).thenReturn(ENTITY_NAME);
        when(entity.getVersions()).thenReturn(Collections.singletonList(version));
        when(entity.copyForVersion(eq(version))).then(a -> {
            copyReferences.addAll(versionReferences);
            return copyEntity;
        });

        when(copyEntity.getBaseName()).thenReturn(ENTITY_NAME);
        when(copyEntity.getTableName()).thenReturn(ENTITY_NAME);
        when(copyEntity.getActualVersion()).thenReturn(version);
        when(copyEntity.getReferences()).thenReturn(copyReferences);

        when(groupingEntity.getBaseName()).thenReturn(GROUPING_ENTITY_NAME);
        when(groupingEntity.getTableName()).thenReturn(GROUPING_ENTITY_NAME);
        when(groupingEntity.copyForVersion(eq(groupingVersion))).thenReturn(copyGroupingEntity);

        when(copyGroupingEntity.getBaseName()).thenReturn(GROUPING_ENTITY_NAME);
        when(copyGroupingEntity.getTableName()).thenReturn(GROUPING_ENTITY_NAME);
        when(copyGroupingEntity.getActualVersion()).thenReturn(groupingVersion);
    }

    private void mockDefaultVersions(){
        versionReferences.add(reference);
        when(version.getReferences()).thenReturn(versionReferences);
        when(version.determineReferences(eq(entity))).thenReturn(versionReferences);
        when(version.getFields()).thenReturn(Collections.singletonList(field));
        when(version.determineFields(eq(entity))).thenReturn(Collections.singletonList(field));
        when(version.determineBaseVersion(any())).thenReturn(Optional.empty());
        when(version.determineReferenceTargetVersion(any())).thenReturn(Optional.empty());
        when(version.getVersionId()).thenReturn(VERSION_ID);
        when(version.getVersionEntity()).thenReturn(copyEntity);
        when(version.getParentEntity()).thenReturn(entity);

        when(groupingVersion.determineBaseVersion(any())).thenReturn(Optional.empty());
        when(groupingVersion.determineReferenceTargetVersion(any())).thenReturn(Optional.empty());
        when(groupingVersion.getVersionId()).thenReturn(VERSION_ID);
        when(groupingVersion.getVersionEntity()).thenReturn(copyGroupingEntity);
        when(groupingVersion.getParentEntity()).thenReturn(groupingEntity);
    }

    @DisplayName("Complete entity with version")
    @Test
    public void testComplete() {
        assertTrue(cut.complete(config), "The result should be true");

        verify(entity).copyForVersion(version);
        verify(copyEntity).setReferences(eq(Collections.singletonList(copyReference)));
        verify(copyEntity).setParentRefs(eq(Collections.emptyList()));

        verify(reference).copy();
        verify(reference, never()).setRealTargetEntity(any());
        verify(copyReference, never()).setRealTargetEntity(any());

        verify(version).setParentEntity(eq(entity));
        verify(version).setBaseVersion(eq(Optional.empty()));
        verify(version).setFields(eq(Collections.singletonList(field)));
        verify(version).setReferences(eq(Collections.singletonList(reference)));
        verify(version).generateVersionName();
    }

    @DisplayName("Complete entity with version added reference")
    @Test
    public void testCompleteAddedReference() {
        references.clear();
        when(reference.getRealTargetEntity()).thenReturn(null);

        assertTrue(cut.complete(config), "The result should be true");

        verify(entity).copyForVersion(version);
        verify(copyEntity).setReferences(eq(Collections.singletonList(copyReference)));
        verify(copyEntity).setParentRefs(eq(Collections.emptyList()));

        verify(reference).copy();
        verify(reference).setRealTargetEntity(eq(groupingEntity));
        verify(copyReference, never()).setRealTargetEntity(any());

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

        verify(entity, never()).copyForVersion(version);
        verify(reference, never()).copy();
    }

    @DisplayName("Complete entity with reference to versioned target")
    @Test
    public void testCompleteReferenceWithVersion() {
        parentGroupingReferences.add(parentReference);
        when(groupingEntity.getVersions()).thenReturn(Collections.singletonList(groupingVersion));

        when(version.determineReferenceTargetVersion(reference)).thenReturn(Optional.of(groupingVersion));

        assertTrue(cut.complete(config), "The result should be true");

        verify(parentReference).copy();
        verify(copyReference).setRealTargetEntity(eq(copyGroupingEntity));
        verify(copyParentReference).setRealTargetEntity(eq(copyEntity));
        verify(copyGroupingEntity).setParentRefs(eq(Collections.singletonList(copyParentReference)));
    }

    @DisplayName("Complete entity with reference to versioned target, but itself not versioned")
    @Test
    public void testCompleteReferenceWithVersionButSourceNotVersioned() {
        parentGroupingReferences.add(parentReference);
        when(groupingEntity.getVersions()).thenReturn(Collections.singletonList(groupingVersion));

        when(version.determineReferenceTargetVersion(reference)).thenReturn(Optional.of(groupingVersion));
        when(entity.getVersions()).thenReturn(Collections.emptyList());
        when(copyParentReference.getRealTargetEntity()).thenReturn(null);

        assertTrue(cut.complete(config), "The result should be true");

        verify(parentReference).copy();
        verify(copyParentReference,never()).setRealTargetEntity(any());
        verify(copyGroupingEntity).setParentRefs(eq(Collections.emptyList()));
    }
}
