package de.ma_vin.util.layer.generator.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

import de.ma_vin.util.layer.generator.config.elements.*;
import de.ma_vin.util.layer.generator.log.LogImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * {@link ConfigLoader} is the class under test
 */
public class ConfigLoaderTest {

    private static final String ENTITY_NAME = "EntityName";
    private static final String DERIVED_ENTITY_NAME = "DerivedEntityName";
    private static final String PARENT_ENTITY_NAME = "ParentEntityName";
    private static final String GROUPING_ENTITY_NAME = "GroupingEntityName";
    private static final String REFERENCE_NAME = "ReferenceName";
    private static final String FIELD_NAME = "FieldName";
    private static final String OTHER_FIELD_NAME = "OtherFieldName";
    private static final String GROUPING_FIELD_NAME = "GroupingFieldName";
    private static final String FIELD_LIST = FIELD_NAME;

    AutoCloseable openMocks;

    @Mock
    private File configFile;
    @Mock
    private Config config;
    @Mock
    private Entity entity;
    @Mock
    private Entity groupingEntity;
    @Mock
    private Entity derivedEntity;
    @Mock
    private Entity parentEntity;
    @Mock
    private Grouping grouping;
    @Mock
    private Reference reference;
    @Mock
    private Field entityField;
    @Mock
    private Field parentEntityField;
    @Mock
    private Field groupingEntityField;
    @Mock
    private Index index;
    @Mock
    private NonOwnerFilterField nonOwnerFilterField;


    private ConfigLoader cut;

    private final List<Reference> entityParentReferences = new ArrayList<>();
    private final List<Reference> groupingEntityParentReferences = new ArrayList<>();
    private final List<FieldSorting> fieldSortings = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        openMocks = openMocks(this);
        cut = new ConfigLoader(configFile, new LogImpl());
        initDefaultNonMocks();
        initDefaultMocks();
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    private void initDefaultNonMocks() {
        cut.setConfig(config);
        entityParentReferences.clear();
        groupingEntityParentReferences.clear();
        fieldSortings.clear();
    }

    private void initDefaultMocks() {
        when(config.getEntities()).thenReturn(Arrays.asList(entity, derivedEntity, parentEntity));
        when(config.getGroupings()).thenReturn(Collections.singletonList(grouping));

        when(grouping.getEntities()).thenReturn(Collections.singletonList(groupingEntity));

        defaultMockEntity(entity, ENTITY_NAME, Collections.singletonList(entityField), Collections.singletonList(index)
                , Collections.singletonList(reference), entityParentReferences);
        defaultMockEntity(derivedEntity, DERIVED_ENTITY_NAME, Collections.singletonList(entityField), null
                , null, new ArrayList<>());
        when(derivedEntity.getDerivedFrom()).thenReturn(ENTITY_NAME);
        defaultMockEntity(parentEntity, PARENT_ENTITY_NAME, Collections.singletonList(parentEntityField), null
                , null, new ArrayList<>());
        when(parentEntity.getIsAbstract()).thenReturn(Boolean.TRUE);

        defaultMockEntity(groupingEntity, GROUPING_ENTITY_NAME, Collections.singletonList(groupingEntityField), null, null
                , groupingEntityParentReferences);

        when(reference.getReferenceName()).thenReturn(REFERENCE_NAME);
        when(reference.getTargetEntity()).thenReturn(GROUPING_ENTITY_NAME);
        when(reference.isList()).thenReturn(Boolean.TRUE);
        when(reference.isOwner()).thenReturn(Boolean.TRUE);
        doAnswer(a -> when(reference.getRealTargetEntity()).thenReturn(a.getArgument(0))).when(reference).setRealTargetEntity(any());

        when(entityField.getFieldName()).thenReturn(FIELD_NAME);
        when(parentEntityField.getFieldName()).thenReturn(OTHER_FIELD_NAME);
        when(groupingEntityField.getFieldName()).thenReturn(GROUPING_FIELD_NAME);

        when(index.getFieldList()).thenReturn(FIELD_LIST);
        when(index.getFields()).thenReturn(fieldSortings);

        doCallRealMethod().when(reference).copy();
    }

    private void defaultMockEntity(Entity entity, String entityName, List<Field> fields, List<Index> indices, List<Reference> references
            , List<Reference> parentReferences) {

        when(entity.getBaseName()).thenReturn(entityName);
        when(entity.getTableName()).thenReturn(entityName);
        when(entity.getModels()).thenReturn(Models.DOMAIN_DAO_DTO);
        when(entity.getFields()).thenReturn(fields);
        doAnswer(a -> when(entity.getFields()).thenReturn(a.getArgument(0)))
                .when(entity).setFields(any());
        when(entity.getIndices()).thenReturn(indices);
        doAnswer(a -> when(entity.getIndices()).thenReturn(a.getArgument(0)))
                .when(entity).setIndices(any());
        doAnswer(a -> {
            parentReferences.clear();
            parentReferences.addAll(a.getArgument(0));
            return null;
        }).when(entity).setParentRefs(any());
        when(entity.getReferences()).thenReturn(references);
        doAnswer(a -> when(entity.getReferences()).thenReturn(a.getArgument(0)))
                .when(entity).setReferences(any());
        when(entity.getParentRefs()).thenReturn(parentReferences);
        doAnswer(a -> when(entity.getRealDerivedFrom()).thenReturn(a.getArgument(0)))
                .when(entity).setRealDerivedFrom(any());
        doAnswer(a -> when(entity.getRealParent()).thenReturn(a.getArgument(0))).when(entity).setRealParent(any());
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
        verify(entity, never()).setRealDerivedFrom(any());

        verify(derivedEntity, never()).setGrouping(any());
        verify(derivedEntity, never()).setFields(any());
        verify(derivedEntity).setParentRefs(any());
        verify(derivedEntity).setReferences(any());
        verify(derivedEntity, never()).setRealParent(any());
        verify(derivedEntity).setRealDerivedFrom(any());

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

        verify(index).setFields(any());
        assertEquals(1, fieldSortings.size(), "Wrong number of index fields");
        assertTrue(fieldSortings.get(0).isAscending(), "The field at index should be ascending");
        assertEquals(entityField, fieldSortings.get(0).getField(), "Wrong field at index");

        verify(config).setUseIdGenerator(eq(Boolean.FALSE));
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
        defaultMockEntity(parentEntity, "ParentEntity", null, null, null, new ArrayList<>());
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
        defaultMockEntity(parentEntity, "ParentEntity", null, null, null, new ArrayList<>());
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
        defaultMockEntity(parentEntity, "ParentEntity", null, null, null, new ArrayList<>());
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

    @Test
    public void testCompleteFilterFieldNotExistingAndNotPackageButFiltering() {
        when(reference.getFilterField()).thenReturn(GROUPING_FIELD_NAME + "1");
        when(reference.isOwner()).thenReturn(Boolean.FALSE);
        when(groupingEntityField.getIsTypeEnum()).thenReturn(Boolean.TRUE);

        boolean result = cut.complete();
        assertFalse(result, "The result of completion should be false");

        verify(reference, never()).setRealFilterField(any());
    }

    @Test
    public void testCompleteIdGenerator() {
        when(config.getIdGeneratorPackage()).thenReturn("de.ma_vin.test");
        when(config.getIdGeneratorClass()).thenReturn("IdGenerator");

        boolean result = cut.complete();
        assertTrue(result, "The result of completion should be true");

        verify(config).setUseIdGenerator(eq(Boolean.TRUE));
    }

    @Test
    public void testCompleteInvalidIdGenerator() {
        when(config.getIdGeneratorPackage()).thenReturn("de.ma_vin.test");

        boolean result = cut.complete();
        assertTrue(result, "The result of completion should be true");

        verify(config).setUseIdGenerator(eq(Boolean.FALSE));

        clearInvocations(config);
        when(config.getIdGeneratorPackage()).thenReturn(null);
        when(config.getIdGeneratorClass()).thenReturn("IdGenerator");

        result = cut.complete();
        assertTrue(result, "The result of completion should be true");

        verify(config).setUseIdGenerator(eq(Boolean.FALSE));
    }

    @Test
    public void testCompleteTableName() {
        when(entity.getTableName()).thenReturn(null);


        boolean result = cut.complete();
        assertTrue(result, "The result of completion should be true");

        verify(entity).setTableName(eq(ENTITY_NAME));
    }

    @Test
    public void testCompleteIndexAsc() {
        when(index.getFieldList()).thenReturn(FIELD_NAME + " ASC");
        boolean result = cut.complete();
        assertTrue(result, "The result of completion should be true");
        verify(index).setFields(any());
        assertEquals(1, fieldSortings.size(), "Wrong number of index fields");
        assertTrue(fieldSortings.get(0).isAscending(), "The field at index should be ascending");
        assertEquals(entityField, fieldSortings.get(0).getField(), "Wrong field at index");
    }

    @Test
    public void testCompleteIndexDesc() {
        when(index.getFieldList()).thenReturn(FIELD_NAME + " DESC");
        boolean result = cut.complete();
        assertTrue(result, "The result of completion should be true");
        verify(index).setFields(any());
        assertEquals(1, fieldSortings.size(), "Wrong number of index fields");
        assertFalse(fieldSortings.get(0).isAscending(), "The field at index should be descending");
        assertEquals(entityField, fieldSortings.get(0).getField(), "Wrong field at index");
    }

    @Test
    public void testCompleteIndexId() {
        when(index.getFieldList()).thenReturn("Id");
        boolean result = cut.complete();
        assertTrue(result, "The result of completion should be true");
        verify(index).setFields(any());
        assertEquals(1, fieldSortings.size(), "Wrong number of index fields");
        assertTrue(fieldSortings.get(0).isAscending(), "The field at index should be ascending");
        assertNotNull(fieldSortings.get(0).getField(), "The field should not be null");
        assertEquals("Id", fieldSortings.get(0).getField().getFieldName(), "Wrong field at index");
    }


    @Test
    public void testCompleteNonExisting() {
        when(index.getFieldList()).thenReturn("AnyRandomField");
        boolean result = cut.complete();
        assertFalse(result, "The result of completion should be false");
        assertEquals(0, fieldSortings.size(), "Wrong number of index fields");
    }

    @DisplayName("The config cannot completed because the derived from entity does not exists")
    @Test
    public void testCompleteRealDerivedFromNonExisting() {
        when(derivedEntity.getDerivedFrom()).thenReturn("AnyRandomEntityName");
        boolean result = cut.complete();
        assertFalse(result, "The result of completion should be false");
        verify(derivedEntity, never()).setRealDerivedFrom(any());
    }

    @DisplayName("The config cannot completed because the derived from entity is abstract")
    @Test
    public void testCompleteRealDerivedFromIsAbstract() {
        when(entity.getIsAbstract()).thenReturn(Boolean.TRUE);
        boolean result = cut.complete();
        assertFalse(result, "The result of completion should be false");
        verify(derivedEntity, never()).setRealDerivedFrom(any());
    }

    @DisplayName("The config cannot completed because the derived from entity does not support domain model")
    @Test
    public void testCompleteRealDerivedFromIsNotDomain() {
        when(entity.getModels()).thenReturn(Models.DAO);
        boolean result = cut.complete();
        assertFalse(result, "The result of completion should be false");
        verify(derivedEntity, never()).setRealDerivedFrom(any());
    }

    @DisplayName("The config cannot completed because the derived from entity does not contain all required fields")
    @Test
    public void testCompleteRealDerivedFromMissingFields() {
        when(derivedEntity.getFields()).thenReturn(Collections.singletonList(parentEntityField));
        boolean result = cut.complete();
        assertFalse(result, "The result of completion should be false");
        verify(derivedEntity, never()).setRealDerivedFrom(any());
    }

    @DisplayName("The config completed with an field from the parent of the derived from entity")
    @Test
    public void testCompleteRealDerivedFromParent() {
        when(entity.getParent()).thenReturn(PARENT_ENTITY_NAME);
        when(entity.hasParent()).thenReturn(Boolean.TRUE);
        when(derivedEntity.getFields()).thenReturn(Collections.singletonList(parentEntityField));
        boolean result = cut.complete();
        assertTrue(result, "The result of completion should be true");
        verify(derivedEntity).setRealDerivedFrom(any());
    }
}
