package de.ma_vin.util.layer.generator.config.loader;

import de.ma_vin.util.layer.generator.config.elements.Entity;
import de.ma_vin.util.layer.generator.config.elements.Models;
import de.ma_vin.util.layer.generator.config.elements.fields.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * {@link EntityCompleter} is the class under test
 */
public class EntityCompleterTest extends AbstractCompleterTest {
    private static final String ENTITY_NAME = "EntityName";
    private static final String GROUPING_ENTITY_NAME = "GroupingEntityName";
    private static final String PARENT_ENTITY_NAME = "ParentEntityName";
    private static final String DERIVED_ENTITY_NAME = "DerivedEntityName";
    private static final String FIELD_NAME = "FieldName";
    private static final String FIELD_TYPE = "CustomType";
    private static final String FIELD_TYPE_PACKAGE = "de.ma_vin.util.layer.generator";
    private static final String FIELD_DESCRIPTION = "Description";
    private static final String FIELD_SHORT_DESCRIPTION = "ShortDescription";


    private EntityCompleter cut;

    @Mock
    private Entity derivedEntity;
    @Mock
    private Entity parentEntity;
    @Mock
    private Field entityField;
    @Mock
    private Field derivedEntityField;
    @Mock
    private Field groupingEntityField;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();

        cut = new EntityCompleter(logger);

        when(entity.getBaseName()).thenReturn(ENTITY_NAME);
        when(entity.getIsAbstract()).thenReturn(Boolean.FALSE);
        when(entity.getFields()).thenReturn(Collections.singletonList(entityField));
        when(entity.getModels()).thenReturn(Models.DOMAIN_DAO_DTO);
        when(entity.hasParent()).thenReturn(Boolean.FALSE);
        when(entity.hasNoParent()).thenReturn(Boolean.TRUE);
        doAnswer(a -> when(entity.getRealParent()).thenReturn(a.getArgument(0))).when(entity).setRealParent(any());

        when(groupingEntity.getBaseName()).thenReturn(GROUPING_ENTITY_NAME);
        when(groupingEntity.getIsAbstract()).thenReturn(Boolean.FALSE);
        when(groupingEntity.getFields()).thenReturn(Collections.singletonList(groupingEntityField));
        when(groupingEntity.getModels()).thenReturn(Models.DOMAIN_DAO_DTO);
        when(groupingEntity.hasParent()).thenReturn(Boolean.FALSE);
        when(groupingEntity.hasNoParent()).thenReturn(Boolean.TRUE);

        when(parentEntity.getBaseName()).thenReturn(PARENT_ENTITY_NAME);
        when(parentEntity.getIsAbstract()).thenReturn(Boolean.TRUE);
        when(parentEntity.getModels()).thenReturn(Models.DOMAIN_DAO_DTO);
        when(parentEntity.hasParent()).thenReturn(Boolean.FALSE);
        when(parentEntity.hasNoParent()).thenReturn(Boolean.TRUE);

        when(derivedEntity.getBaseName()).thenReturn(DERIVED_ENTITY_NAME);
        when(derivedEntity.getIsAbstract()).thenReturn(Boolean.FALSE);
        when(derivedEntity.getFields()).thenReturn(Collections.singletonList(derivedEntityField));
        when(derivedEntity.getDerivedFrom()).thenReturn(ENTITY_NAME);
        when(derivedEntity.getModels()).thenReturn(Models.DOMAIN_DTO);
        when(derivedEntity.hasParent()).thenReturn(Boolean.FALSE);
        when(derivedEntity.hasNoParent()).thenReturn(Boolean.TRUE);

        when(entityField.getFieldName()).thenReturn(FIELD_NAME);
        when(entityField.getType()).thenReturn(FIELD_TYPE);
        when(entityField.getTypePackage()).thenReturn(FIELD_TYPE_PACKAGE);
        when(entityField.getIsTypeEnum()).thenReturn(Boolean.FALSE);
        when(entityField.getDescription()).thenReturn(FIELD_DESCRIPTION);
        when(entityField.getShortDescription()).thenReturn(FIELD_SHORT_DESCRIPTION);

        when(derivedEntityField.getFieldName()).thenReturn(FIELD_NAME);

    }


    @DisplayName("complete entity default")
    @Test
    public void testComplete() {
        assertTrue(cut.complete(config), "The result of completion should be true");

        verify(entity).setTableName(eq(ENTITY_NAME));
        verify(entity, never()).setRealParent(any());
        verify(entity, never()).setRealDerivedFrom(any());

        verify(groupingEntity).setTableName(eq(GROUPING_ENTITY_NAME));
        verify(groupingEntity, never()).setRealParent(any());
        verify(groupingEntity, never()).setRealDerivedFrom(any());
    }

    @DisplayName("complete entity with table name")
    @Test
    public void testCompleteTableName() {
        when(entity.getTableName()).thenReturn(ENTITY_NAME + "abc");
        assertTrue(cut.complete(config), "The result of completion should be true");

        verify(entity, never()).setTableName(any());
        verify(entity, never()).setRealParent(any());
        verify(entity, never()).setRealDerivedFrom(any());

        verify(groupingEntity).setTableName(eq(GROUPING_ENTITY_NAME));
        verify(groupingEntity, never()).setRealParent(any());
        verify(groupingEntity, never()).setRealDerivedFrom(any());
    }

    @DisplayName("complete entity with abstract parent")
    @Test
    public void testCompleteWithParent() {
        when(entity.getParent()).thenReturn(PARENT_ENTITY_NAME);
        when(entity.hasParent()).thenReturn(Boolean.TRUE);
        when(entity.hasNoParent()).thenReturn(Boolean.FALSE);
        entities.add(parentEntity);

        assertTrue(cut.complete(config), "The result of completion should be true");

        verify(entity).setTableName(eq(ENTITY_NAME));
        verify(entity).setRealParent(eq(parentEntity));
        verify(entity, never()).setRealDerivedFrom(any());

        verify(groupingEntity).setTableName(eq(GROUPING_ENTITY_NAME));
        verify(groupingEntity, never()).setRealParent(any());
        verify(groupingEntity, never()).setRealDerivedFrom(any());

        verify(parentEntity).setTableName(eq(PARENT_ENTITY_NAME));
        verify(parentEntity, never()).setRealParent(any());
        verify(parentEntity, never()).setRealDerivedFrom(any());
    }

    @DisplayName("complete entity with non abstract parent")
    @Test
    public void testCompleteWithNonAbstractParent() {
        when(entity.getParent()).thenReturn(PARENT_ENTITY_NAME);
        when(entity.hasParent()).thenReturn(Boolean.TRUE);
        when(entity.hasNoParent()).thenReturn(Boolean.FALSE);
        when(parentEntity.getIsAbstract()).thenReturn(Boolean.FALSE);
        entities.add(parentEntity);

        assertFalse(cut.complete(config), "The result of completion should be false");

        verify(entity).setTableName(eq(ENTITY_NAME));
        verify(entity, never()).setRealParent(any());
        verify(entity, never()).setRealDerivedFrom(any());
    }

    @DisplayName("complete entity with non existing parent")
    @Test
    public void testCompleteWithNonExistingParent() {
        when(entity.getParent()).thenReturn(PARENT_ENTITY_NAME + "abc");
        when(entity.hasParent()).thenReturn(Boolean.TRUE);
        when(entity.hasNoParent()).thenReturn(Boolean.FALSE);
        entities.add(parentEntity);

        assertFalse(cut.complete(config), "The result of completion should be false");

        verify(entity).setTableName(eq(ENTITY_NAME));
        verify(entity, never()).setRealParent(any());
        verify(entity, never()).setRealDerivedFrom(any());
    }

    @DisplayName("complete entity derived from other")
    @Test
    public void testCompleteDerived() {
        entities.add(derivedEntity);

        assertTrue(cut.complete(config), "The result of completion should be true");

        verify(entity).setTableName(eq(ENTITY_NAME));
        verify(entity, never()).setRealParent(any());
        verify(entity, never()).setRealDerivedFrom(any());

        verify(groupingEntity).setTableName(eq(GROUPING_ENTITY_NAME));
        verify(groupingEntity, never()).setRealParent(any());
        verify(groupingEntity, never()).setRealDerivedFrom(any());

        verify(derivedEntity).setTableName(eq(DERIVED_ENTITY_NAME));
        verify(derivedEntity, never()).setRealParent(any());
        verify(derivedEntity).setRealDerivedFrom(eq(entity));

        verify(derivedEntityField).setType(eq(FIELD_TYPE));
        verify(derivedEntityField).setTypePackage(eq(FIELD_TYPE_PACKAGE));
        verify(derivedEntityField).setIsTypeEnum(eq(Boolean.FALSE));
        verify(derivedEntityField).setDescription(eq(FIELD_DESCRIPTION));
        verify(derivedEntityField).setShortDescription(eq(FIELD_SHORT_DESCRIPTION));
        verify(derivedEntityField).setModels(eq(Models.DOMAIN_DTO));
    }

    @DisplayName("complete entity derived from other with parent field")
    @Test
    public void testCompleteDerivedWithParentField() {
        entities.add(derivedEntity);
        entities.add(parentEntity);

        when(entity.getFields()).thenReturn(Collections.emptyList());
        when(entity.getParent()).thenReturn(PARENT_ENTITY_NAME);
        when(entity.hasParent()).thenReturn(Boolean.TRUE);
        when(entity.hasNoParent()).thenReturn(Boolean.FALSE);
        when(parentEntity.getFields()).thenReturn(Collections.singletonList(entityField));

        assertTrue(cut.complete(config), "The result of completion should be true");

        verify(entity).setTableName(eq(ENTITY_NAME));
        verify(entity).setRealParent(eq(parentEntity));
        verify(entity, never()).setRealDerivedFrom(any());

        verify(groupingEntity).setTableName(eq(GROUPING_ENTITY_NAME));
        verify(groupingEntity, never()).setRealParent(any());
        verify(groupingEntity, never()).setRealDerivedFrom(any());

        verify(derivedEntity).setTableName(eq(DERIVED_ENTITY_NAME));
        verify(derivedEntity, never()).setRealParent(any());
        verify(derivedEntity).setRealDerivedFrom(eq(entity));

        verify(derivedEntityField).setType(eq(FIELD_TYPE));
        verify(derivedEntityField).setTypePackage(eq(FIELD_TYPE_PACKAGE));
        verify(derivedEntityField).setIsTypeEnum(eq(Boolean.FALSE));
        verify(derivedEntityField).setDescription(eq(FIELD_DESCRIPTION));
        verify(derivedEntityField).setShortDescription(eq(FIELD_SHORT_DESCRIPTION));
        verify(derivedEntityField).setModels(eq(Models.DOMAIN_DTO));
    }

    @DisplayName("complete entity derived from abstract one")
    @Test
    public void testCompleteDerivedFromAbstract() {
        entities.add(derivedEntity);
        when(entity.getIsAbstract()).thenReturn(Boolean.TRUE);

        checkDerivedEntityFailed();
    }

    @DisplayName("complete entity derived from non existing one")
    @Test
    public void testCompleteDerivedFromNonExisting() {
        entities.add(derivedEntity);
        when(derivedEntity.getDerivedFrom()).thenReturn(ENTITY_NAME + "abc");

        checkDerivedEntityFailed();
    }

    @DisplayName("complete entity derived from non domain model one")
    @Test
    public void testCompleteDerivedFromNonDomain() {
        entities.add(derivedEntity);
        when(entity.getModels()).thenReturn(Models.DAO_DTO);

        checkDerivedEntityFailed();
    }

    @DisplayName("complete entity derived from other with different fields")
    @Test
    public void testCompleteDerivedFromWithDifferentFields() {
        entities.add(derivedEntity);
        when(derivedEntityField.getFieldName()).thenReturn(FIELD_NAME + "abc");

        checkDerivedEntityFailed();
    }


    private void checkDerivedEntityFailed() {
        assertFalse(cut.complete(config), "The result of completion should be false");

        verify(derivedEntity).setTableName(eq(DERIVED_ENTITY_NAME));
        verify(derivedEntity, never()).setRealParent(any());
        verify(derivedEntity, never()).setRealDerivedFrom(any());

        verify(derivedEntityField, never()).setType(any());
        verify(derivedEntityField, never()).setTypePackage(any());
        verify(derivedEntityField, never()).setIsTypeEnum(any());
        verify(derivedEntityField, never()).setDescription(any());
        verify(derivedEntityField, never()).setShortDescription(any());
        verify(derivedEntityField, never()).setModels(any());
    }

}
