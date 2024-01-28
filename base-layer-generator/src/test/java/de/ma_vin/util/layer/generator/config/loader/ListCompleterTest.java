package de.ma_vin.util.layer.generator.config.loader;

import de.ma_vin.util.layer.generator.config.elements.Config;
import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.config.elements.Grouping;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * {@link ListCompleter} is the class under test
 */
public class ListCompleterTest {

    @InjectMocks
    private ListCompleter cut;

    private AutoCloseable openMocks;

    @Mock
    private Config config;
    @Mock
    private Entity entity;
    @Mock
    private Entity groupingEntity;
    @Mock
    private Grouping grouping;

    private final List<Entity> entities = new ArrayList<>();
    private final List<Entity> groupingEntities = new ArrayList<>();
    private final List<Grouping> groupings = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        openMocks = openMocks(this);

        entities.clear();
        groupings.clear();

        mockEntityDefault();
        mockGroupDefault();
    }

    private void mockEntityDefault() {
        when(config.getEntities()).thenReturn(entities);
        doAnswer(a -> when(config.getEntities()).thenReturn(a.getArgument(0))).when(config).setEntities(anyList());

        entities.add(entity);
        groupingEntities.add(groupingEntity);

        when(entity.getReferences()).thenReturn(Collections.emptyList());
        when(entity.getFields()).thenReturn(Collections.emptyList());
        when(entity.getIndices()).thenReturn(Collections.emptyList());

        when(groupingEntity.getReferences()).thenReturn(Collections.emptyList());
        when(groupingEntity.getFields()).thenReturn(Collections.emptyList());
        when(groupingEntity.getIndices()).thenReturn(Collections.emptyList());
    }

    private void mockGroupDefault() {
        when(config.getGroupings()).thenReturn(groupings);
        doAnswer(a -> when(config.getGroupings()).thenReturn(a.getArgument(0))).when(config).setGroupings(anyList());

        groupings.add(grouping);

        when(grouping.getEntities()).thenReturn(groupingEntities);
        doAnswer(a -> when(grouping.getEntities()).thenReturn(a.getArgument(0))).when(grouping).setEntities(anyList());
    }


    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
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
