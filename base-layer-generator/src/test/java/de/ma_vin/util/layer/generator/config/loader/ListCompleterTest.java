package de.ma_vin.util.layer.generator.config.loader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * {@link ListCompleter} is the class under test
 */
public class ListCompleterTest extends AbstractCompleterTest {

    @InjectMocks
    private ListCompleter cut;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();

        mockEntityDefault();
        mockGroupDefault();
    }

    private void mockEntityDefault() {
        doAnswer(a -> when(config.getEntities()).thenReturn(a.getArgument(0))).when(config).setEntities(anyList());

        when(entity.getReferences()).thenReturn(Collections.emptyList());
        when(entity.getFields()).thenReturn(Collections.emptyList());
        when(entity.getIndices()).thenReturn(Collections.emptyList());

        when(groupingEntity.getReferences()).thenReturn(Collections.emptyList());
        when(groupingEntity.getFields()).thenReturn(Collections.emptyList());
        when(groupingEntity.getIndices()).thenReturn(Collections.emptyList());
    }

    private void mockGroupDefault() {
        doAnswer(a -> when(config.getGroupings()).thenReturn(a.getArgument(0))).when(config).setGroupings(anyList());
        doAnswer(a -> when(grouping.getEntities()).thenReturn(a.getArgument(0))).when(grouping).setEntities(anyList());
    }

    @DisplayName("Complete config without null lists")
    @Test
    public void testComplete() {
        assertTrue(cut.complete(config), "The result should be true");

        verify(config, never()).setEntities(any());
        verify(config, never()).setGroupings(any());

        verify(entity, never()).setReferences(any());
        verify(entity, never()).setFields(any());
        verify(entity, never()).setIndices(any());
        verify(entity).setParentRefs(any());

        verify(groupingEntity, never()).setReferences(any());
        verify(groupingEntity, never()).setFields(any());
        verify(groupingEntity, never()).setIndices(any());
        verify(groupingEntity).setParentRefs(any());

        verify(grouping, never()).setEntities(any());
    }

    @DisplayName("Complete missing entities at config and grouping")
    @Test
    public void testCompleteNullEntitiesList() {
        when(config.getEntities()).thenReturn(null);
        when(grouping.getEntities()).thenReturn(null);

        assertTrue(cut.complete(config), "The result should be true");

        verify(config).setEntities(any());
        verify(config, never()).setGroupings(any());

        verify(grouping).setEntities(any());
    }

    @DisplayName("Complete missing groupings at config ")
    @Test
    public void testCompleteNullGroupingsList() {
        when(config.getGroupings()).thenReturn(null);

        assertTrue(cut.complete(config), "The result should be true");

        verify(config, never()).setEntities(any());
        verify(config).setGroupings(any());
    }

    @DisplayName("Complete entity without properties")
    @Test
    public void testCompleteNullPropertyListsAtEntity() {
        when(entity.getReferences()).thenReturn(null);
        when(entity.getFields()).thenReturn(null);
        when(entity.getIndices()).thenReturn(null);

        assertTrue(cut.complete(config), "The result should be true");

        verify(config, never()).setEntities(any());
        verify(config, never()).setGroupings(any());

        verify(entity).setReferences(any());
        verify(entity).setFields(any());
        verify(entity).setIndices(any());
        verify(entity).setParentRefs(any());

        verify(groupingEntity, never()).setReferences(any());
        verify(groupingEntity, never()).setFields(any());
        verify(groupingEntity, never()).setIndices(any());
        verify(groupingEntity).setParentRefs(any());

        verify(grouping, never()).setEntities(any());
    }

    @DisplayName("Complete entity without properties")
    @Test
    public void testCompleteNullPropertyListsAtGroupingEntity() {
        when(groupingEntity.getReferences()).thenReturn(null);
        when(groupingEntity.getFields()).thenReturn(null);
        when(groupingEntity.getIndices()).thenReturn(null);

        assertTrue(cut.complete(config), "The result should be true");

        verify(config, never()).setEntities(any());
        verify(config, never()).setGroupings(any());

        verify(entity, never()).setReferences(any());
        verify(entity, never()).setFields(any());
        verify(entity, never()).setIndices(any());
        verify(entity).setParentRefs(any());

        verify(groupingEntity).setReferences(any());
        verify(groupingEntity).setFields(any());
        verify(groupingEntity).setIndices(any());
        verify(groupingEntity).setParentRefs(any());

        verify(grouping, never()).setEntities(any());
    }
}
