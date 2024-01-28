package de.ma_vin.util.layer.generator.config.loader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link ReferenceCompleter} is the class under test
 */
public class ReferenceCompleterTest extends AbstractCompleterTest {
    private static final String REFERENCE_NAME = "ReferenceName";
    private static final String ENTITY_NAME = "EntityName";
    private static final String GROUPING_ENTITY_NAME = "GroupingEntityName";

    private ReferenceCompleter cut;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();

        cut = new ReferenceCompleter(logger);

        when(reference.getReferenceName()).thenReturn(REFERENCE_NAME);
        when(reference.getTargetEntity()).thenReturn(GROUPING_ENTITY_NAME);
        doCallRealMethod().when(reference).copy();
        references.add(reference);

        when(entity.getBaseName()).thenReturn(ENTITY_NAME);
        when(entity.getTableName()).thenReturn(ENTITY_NAME);

        when(groupingEntity.getBaseName()).thenReturn(GROUPING_ENTITY_NAME);
        when(groupingEntity.getTableName()).thenReturn(GROUPING_ENTITY_NAME);
    }

    @DisplayName("Complete reference with target at grouped entity")
    @Test
    public void testCompleteGroupedEntityTarget() {
        assertTrue(cut.complete(config), "The result should be true");

        assertEquals(0, parentReferences.size(), "There should not be any reference to a parent at entity");
        assertEquals(1, parentGroupingReferences.size(), "There should be a reference to a parent at grouped entity");
        assertEquals(ENTITY_NAME, parentGroupingReferences.get(0).getTargetEntity(), "Wrong target entity at parent reference");
        assertEquals(entity, parentGroupingReferences.get(0).getRealTargetEntity(), "Wrong real target entity at parent reference");
        assertEquals(groupingEntity, parentGroupingReferences.get(0).getParent(), "Wrong parent entity at parent reference");
        assertTrue(parentGroupingReferences.get(0).isReverse(), "the parent reference should be indicated a reverse");

        verify(reference).setRealTargetEntity(eq(groupingEntity));
        verify(reference).setParent(eq(entity));
    }

    @DisplayName("Complete reference with target at entity")
    @Test
    public void testCompleteEntityTarget() {
        when(reference.getReferenceName()).thenReturn(REFERENCE_NAME);
        when(reference.getTargetEntity()).thenReturn(ENTITY_NAME);
        groupingReferences.add(reference);
        references.clear();

        assertTrue(cut.complete(config), "The result should be true");

        assertEquals(1, parentReferences.size(), "There should be a reference to a parent at entity");
        assertEquals(0, parentGroupingReferences.size(), "There should not be any reference to a parent at grouped entity");
        assertEquals(GROUPING_ENTITY_NAME, parentReferences.get(0).getTargetEntity(), "Wrong target entity at parent reference");
        assertEquals(groupingEntity, parentReferences.get(0).getRealTargetEntity(), "Wrong real target entity at parent reference");
        assertEquals(entity, parentReferences.get(0).getParent(), "Wrong parent entity at parent reference");
        assertTrue(parentReferences.get(0).isReverse(), "the parent reference should be indicated a reverse");

        verify(reference).setRealTargetEntity(eq(entity));
        verify(reference).setParent(eq(groupingEntity));
    }


    @DisplayName("Complete reference with unknown target entity")
    @Test
    public void testCompleteUnknownEntityTarget() {
        when(reference.getTargetEntity()).thenReturn(ENTITY_NAME + "abc");

        assertFalse(cut.complete(config), "The result should be true");

        assertEquals(0, parentReferences.size(), "There should not be any reference to a parent at entity");
        assertEquals(0, parentGroupingReferences.size(), "There should not be any reference to a parent at grouped entity");

        verify(reference, never()).setRealTargetEntity(eq(groupingEntity));
        verify(reference, never()).setParent(eq(entity));
    }
}
