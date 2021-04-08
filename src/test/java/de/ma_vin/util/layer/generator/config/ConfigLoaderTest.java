package de.ma_vin.util.layer.generator.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import de.ma_vin.util.layer.generator.config.elements.*;
import de.ma_vin.util.layer.generator.log.LogImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@link ConfigLoader} is the class under test
 */
public class ConfigLoaderTest {

    private static final String ENTITY_NAME = "EntityName";
    private static final String GROUPING_ENTITY_NAME = "GroupingEntityName";
    private static final String REFERENCE_NAME = "ReferenceName";
    private static final String FIELD_NAME = "FieldName";
    private static final String GROUPING_FIELD_NAME = "GroupingFieldName";

    @Mock
    private File configFile;
    @Mock
    private Config config;
    @Mock
    private Entity entity;
    @Mock
    private Entity groupingEntity;
    @Mock
    private Grouping grouping;
    @Mock
    private Reference reference;
    @Mock
    private Field entityField;
    @Mock
    private Field groupingEntityField;


    private ConfigLoader cut;

    private List<Reference> entityParentReferences = new ArrayList<>();
    private List<Reference> groupingEntityParentReferences = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        initMocks(this);
        cut = new ConfigLoader(configFile, new LogImpl());
        initDefaultNonMocks();
        initDefaultMocks();
    }

    private void initDefaultNonMocks() {
        cut.setConfig(config);
        entityParentReferences.clear();
        groupingEntityParentReferences.clear();
    }

    private void initDefaultMocks() {
        when(config.getEntities()).thenReturn(Arrays.asList(entity));
        when(config.getGroupings()).thenReturn(Arrays.asList(grouping));

        when(grouping.getEntities()).thenReturn(Arrays.asList(groupingEntity));

        defaultMockEntity(entity, ENTITY_NAME, Arrays.asList(entityField), Arrays.asList(reference)
                , entityParentReferences);

        defaultMockEntity(groupingEntity, GROUPING_ENTITY_NAME, Arrays.asList(groupingEntityField), null
                , groupingEntityParentReferences);

        when(reference.getReferenceName()).thenReturn(REFERENCE_NAME);
        when(reference.getTargetEntity()).thenReturn(GROUPING_ENTITY_NAME);
        when(reference.isList()).thenReturn(Boolean.TRUE);
        when(reference.isOwner()).thenReturn(Boolean.TRUE);
        doAnswer(a -> when(reference.getRealTargetEntity()).thenReturn(a.getArgument(0))).when(reference).setRealTargetEntity(any());

        when(entityField.getFieldName()).thenReturn(FIELD_NAME);
        when(groupingEntityField.getFieldName()).thenReturn(GROUPING_FIELD_NAME);
    }

    private void defaultMockEntity(Entity entity, String entityName, List<Field> fields, List<Reference> references
            , List<Reference> parentReferences) {

        when(entity.getBaseName()).thenReturn(entityName);
        when(entity.getFields()).thenReturn(fields);
        doAnswer(a -> when(entity.getFields()).thenReturn(a.getArgument(0)))
                .when(entity).setFields(any());
        doAnswer(a -> {
            parentReferences.clear();
            parentReferences.addAll(a.getArgument(0));
            return null;
        }).when(entity).setParentRefs(any());
        when(entity.getReferences()).thenReturn(references);
        doAnswer(a -> when(entity.getReferences()).thenReturn(a.getArgument(0)))
                .when(entity).setReferences(any());
        when(entity.getParentRefs()).thenReturn(parentReferences);
    }

    @Test
    public void testCompleteDefault() {
        boolean result = cut.complete();
        assertTrue(result, "The result of completion should be true");

        verify(entity, never()).setGrouping(any());
        verify(entity, never()).setFields(any());
        verify(entity).setParentRefs(any());
        verify(entity, never()).setReferences(any());
        verify(entity, never()).setRealParent(any());
        assertEquals(0, entityParentReferences.size(), "Wrong number of set parent references at entity");

        verify(groupingEntity).setGrouping(eq(grouping));
        verify(groupingEntity, never()).setFields(any());
        verify(groupingEntity).setParentRefs(any());
        verify(groupingEntity).setReferences(any());
        verify(groupingEntity, never()).setRealParent(any());
        assertEquals(1, groupingEntityParentReferences.size(), "Wrong number of set parent references at grouping entity");
        assertEquals(REFERENCE_NAME, groupingEntityParentReferences.get(0).getReferenceName(), "Wrong name at parent reference");
        assertEquals(ENTITY_NAME, groupingEntityParentReferences.get(0).getTargetEntity(), "Wrong name of target at parent reference");
        assertEquals(groupingEntity, groupingEntityParentReferences.get(0).getParent(), "Wrong parent at parent reference");
        assertEquals(entity, groupingEntityParentReferences.get(0).getRealTargetEntity(), "Wrong parent at parent reference");
        assertTrue(groupingEntityParentReferences.get(0).isList(), "Wrong list indicator at parent reference");
        assertTrue(groupingEntityParentReferences.get(0).isOwner(), "Wrong owner indicator at parent reference");

        verify(reference).setParent(eq(entity));
        verify(reference).setRealTargetEntity(eq(groupingEntity));
        verify(reference, never()).setRealFilterField(any());
    }

    @Test
    public void testCompleteNoEntities() {
        when(config.getEntities()).thenReturn(null);
        doAnswer(a -> when(config.getEntities()).thenReturn(a.getArgument(0)))
                .when(config).setEntities(any());

        boolean result = cut.complete();
        assertTrue(result, "The result of completion should be true");

        verify(entity, never()).setGrouping(any());
        verify(entity, never()).setFields(any());
        verify(entity, never()).setParentRefs(any());
        verify(entity, never()).setReferences(any());
        verify(entity, never()).setRealParent(any());
        assertEquals(0, entityParentReferences.size(), "Wrong number of set parent references at entity");

        verify(groupingEntity).setGrouping(eq(grouping));
        verify(groupingEntity, never()).setFields(any());
        verify(groupingEntity).setParentRefs(any());
        verify(groupingEntity).setReferences(any());
        verify(groupingEntity, never()).setRealParent(any());
        assertEquals(0, groupingEntityParentReferences.size(), "Wrong number of set parent references at grouping entity");

        verify(reference, never()).setParent(eq(entity));
        verify(reference, never()).setRealTargetEntity(eq(groupingEntity));
        verify(reference, never()).setRealFilterField(any());
    }

    @Test
    public void testCompleteNoGroupings() {
        when(config.getGroupings()).thenReturn(null);
        doAnswer(a -> when(config.getGroupings()).thenReturn(a.getArgument(0)))
                .when(config).setGroupings(any());
        when(entity.getReferences()).thenReturn(null);
        doAnswer(a -> when(entity.getReferences()).thenReturn(a.getArgument(0)))
                .when(entity).setReferences(any());

        boolean result = cut.complete();
        assertTrue(result, "The result of completion should be true");

        verify(config).setGroupings(any());
    }

    @Test
    public void testCompleteNoGroupingEntities() {
        when(grouping.getEntities()).thenReturn(null);
        doAnswer(a -> when(grouping.getEntities()).thenReturn(a.getArgument(0)))
                .when(grouping).setEntities(any());
        when(entity.getReferences()).thenReturn(null);
        doAnswer(a -> when(entity.getReferences()).thenReturn(a.getArgument(0)))
                .when(entity).setReferences(any());

        boolean result = cut.complete();
        assertTrue(result, "The result of completion should be true");

        verify(grouping).setEntities(any());
    }


    @Test
    public void testCompleteInvalidRefTarget() {
        when(reference.getTargetEntity()).thenReturn(GROUPING_ENTITY_NAME + "1");

        boolean result = cut.complete();
        assertFalse(result, "The result of completion should be false");

        assertEquals(0, entityParentReferences.size(), "Wrong number of set parent references at entity");

        assertEquals(0, groupingEntityParentReferences.size(), "Wrong number of set parent references at grouping entity");

        verify(reference, never()).setParent(eq(entity));
        verify(reference, never()).setRealTargetEntity(eq(groupingEntity));
        verify(reference, never()).setRealFilterField(any());
    }

    @Test
    public void testCompleteSuperClass() {
        Entity parentEntity = mock(Entity.class);
        defaultMockEntity(parentEntity, "ParentEntity", null, null, new ArrayList<>());
        when(parentEntity.getIsAbstract()).thenReturn(Boolean.TRUE);
        when(entity.getParent()).thenReturn("ParentEntity");
        when(config.getEntities()).thenReturn(Arrays.asList(entity, parentEntity));

        boolean result = cut.complete();
        assertTrue(result, "The result of completion should be true");

        verify(entity).setRealParent(eq(parentEntity));
    }

    @Test
    public void testCompleteSuperClassNotAbstract() {
        Entity parentEntity = mock(Entity.class);
        defaultMockEntity(parentEntity, "ParentEntity", null, null, new ArrayList<>());
        when(parentEntity.getIsAbstract()).thenReturn(Boolean.FALSE);
        when(entity.getParent()).thenReturn("ParentEntity");
        when(config.getEntities()).thenReturn(Arrays.asList(entity, parentEntity));

        boolean result = cut.complete();
        assertFalse(result, "The result of completion should be false");

        verify(entity, never()).setRealParent(eq(parentEntity));
    }

    @Test
    public void testCompleteSuperClassNotExisting() {
        Entity parentEntity = mock(Entity.class);
        defaultMockEntity(parentEntity, "ParentEntity", null, null, new ArrayList<>());
        when(parentEntity.getIsAbstract()).thenReturn(Boolean.TRUE);
        when(entity.getParent()).thenReturn("OtherParentEntity");
        when(config.getEntities()).thenReturn(Arrays.asList(entity, parentEntity));

        boolean result = cut.complete();
        assertFalse(result, "The result of completion should be false");

        verify(entity, never()).setRealParent(eq(parentEntity));
    }

    @Test
    public void testCompleteFilterField() {
        when(reference.getFilterField()).thenReturn(GROUPING_FIELD_NAME);
        when(groupingEntityField.getIsTypeEnum()).thenReturn(Boolean.TRUE);

        boolean result = cut.complete();
        assertTrue(result, "The result of completion should be true");

        verify(reference).setRealFilterField(eq(groupingEntityField));
        verify(groupingEntityField).setParentEntity(eq(groupingEntity));
    }

    @Test
    public void testCompleteFilterFieldNotEnum() {
        when(reference.getFilterField()).thenReturn(GROUPING_FIELD_NAME);

        boolean result = cut.complete();
        assertFalse(result, "The result of completion should be false");

        verify(reference, never()).setRealFilterField(any());
    }

    @Test
    public void testCompleteFilterFieldNotExisting() {
        when(reference.getFilterField()).thenReturn(GROUPING_FIELD_NAME + "1");
        when(groupingEntityField.getIsTypeEnum()).thenReturn(Boolean.TRUE);

        boolean result = cut.complete();
        assertFalse(result, "The result of completion should be false");

        verify(reference, never()).setRealFilterField(any());
    }

    @Test
    public void testCompleteFilterFieldNullable() {
        when(reference.getFilterField()).thenReturn(GROUPING_FIELD_NAME);
        when(groupingEntityField.getIsTypeEnum()).thenReturn(Boolean.TRUE);
        DaoInfo daoInfo = mock(DaoInfo.class);
        when(groupingEntityField.getDaoInfo()).thenReturn(daoInfo);
        when(daoInfo.getNullable()).thenReturn(Boolean.TRUE);

        boolean result = cut.complete();
        assertTrue(result, "The result of completion should be true");

        verify(reference).setRealFilterField(eq(groupingEntityField));
        verify(daoInfo).setNullable(eq(Boolean.FALSE));
    }

    @Test
    public void testCompleteFilterFieldNotNullable() {
        when(reference.getFilterField()).thenReturn(GROUPING_FIELD_NAME);
        when(groupingEntityField.getIsTypeEnum()).thenReturn(Boolean.TRUE);
        DaoInfo daoInfo = mock(DaoInfo.class);
        when(groupingEntityField.getDaoInfo()).thenReturn(daoInfo);
        when(daoInfo.getNullable()).thenReturn(Boolean.FALSE);

        boolean result = cut.complete();
        assertTrue(result, "The result of completion should be true");

        verify(reference).setRealFilterField(eq(groupingEntityField));
        verify(daoInfo, never()).setNullable(any());
    }
}
