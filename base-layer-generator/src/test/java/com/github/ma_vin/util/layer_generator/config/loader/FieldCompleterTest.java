package com.github.ma_vin.util.layer_generator.config.loader;

import com.github.ma_vin.util.layer_generator.config.elements.DaoInfo;
import com.github.ma_vin.util.layer_generator.config.elements.fields.Field;
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
 * {@link FieldCompleter} is the class under test
 */
public class FieldCompleterTest extends AbstractCompleterTest {
    private static final String REFERENCE_NAME = "ReferenceName";
    private static final String ENTITY_NAME = "EntityName";
    private static final String GROUPING_ENTITY_NAME = "GroupingEntityName";
    private static final String GROUPING_FIELD_NAME = "GroupingFieldName";

    private FieldCompleter cut;

    @Mock
    private Field field;
    @Mock
    private Field groupingField;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();

        cut = new FieldCompleter(logger);

        when(entity.getBaseName()).thenReturn(ENTITY_NAME);
        when(entity.getFields()).thenReturn(Collections.singletonList(field));

        when(groupingEntity.getBaseName()).thenReturn(GROUPING_ENTITY_NAME);
        when(groupingEntity.getFields()).thenReturn(Collections.singletonList(groupingField));

        when(groupingField.getFieldName()).thenReturn(GROUPING_FIELD_NAME);

        when(reference.getReferenceName()).thenReturn(REFERENCE_NAME);
        when(reference.getTargetEntity()).thenReturn(GROUPING_ENTITY_NAME);
        when(reference.getRealTargetEntity()).thenReturn(groupingEntity);
        when(reference.isOwner()).thenReturn(Boolean.TRUE);
    }

    @DisplayName("Set owner at fields of entities")
    @Test
    public void testComplete() {
        assertTrue(cut.complete(config), "The result should be true");

        verify(field).setParentEntity(eq(entity));
        verify(groupingField).setParentEntity(eq(groupingEntity));
    }

    @DisplayName("Not existing fields at entities")
    @Test
    public void testCompleteEmptyFieldList() {
        when(entity.getFields()).thenReturn(Collections.emptyList());
        when(groupingEntity.getFields()).thenReturn(Collections.emptyList());

        assertTrue(cut.complete(config), "The result should be true");
    }

    @DisplayName("Filter with field of enum type")
    @Test
    public void testCompleteFilterField() {
        references.add(reference);
        when(reference.getFilterField()).thenReturn(GROUPING_FIELD_NAME);
        when(groupingField.getIsTypeEnum()).thenReturn(Boolean.TRUE);

        assertTrue(cut.complete(config), "The result of completion should be true");

        verify(reference).setRealFilterField(eq(groupingField));
        verify(groupingField).setParentEntity(eq(groupingEntity));
    }

    @DisplayName("Filter with field of non enum type")
    @Test
    public void testCompleteFilterFieldNotEnum() {
        references.add(reference);
        when(reference.getFilterField()).thenReturn(GROUPING_FIELD_NAME);

        assertFalse(cut.complete(config), "The result of completion should be false");

        verify(reference, never()).setRealFilterField(any());
    }

    @DisplayName("Filter with non existing field")
    @Test
    public void testCompleteFilterFieldNotExisting() {
        references.add(reference);
        when(reference.getFilterField()).thenReturn(GROUPING_FIELD_NAME + "1");
        when(groupingField.getIsTypeEnum()).thenReturn(Boolean.TRUE);

        assertFalse(cut.complete(config), "The result of completion should be false");

        verify(reference, never()).setRealFilterField(any());
    }

    @DisplayName("Filter with warning for nullable database field")
    @Test
    public void testCompleteFilterFieldNullable() {
        references.add(reference);
        when(reference.getFilterField()).thenReturn(GROUPING_FIELD_NAME);
        when(groupingField.getIsTypeEnum()).thenReturn(Boolean.TRUE);
        DaoInfo daoInfo = mock(DaoInfo.class);
        when(groupingField.getDaoInfo()).thenReturn(daoInfo);
        when(daoInfo.getNullable()).thenReturn(Boolean.TRUE);

        assertTrue(cut.complete(config), "The result of completion should be true");

        verify(reference).setRealFilterField(eq(groupingField));
        verify(daoInfo).setNullable(eq(Boolean.FALSE));
    }

    @DisplayName("Filter with non nullable database field")
    @Test
    public void testCompleteFilterFieldNotNullable() {
        references.add(reference);
        when(reference.getFilterField()).thenReturn(GROUPING_FIELD_NAME);
        when(groupingField.getIsTypeEnum()).thenReturn(Boolean.TRUE);
        DaoInfo daoInfo = mock(DaoInfo.class);
        when(groupingField.getDaoInfo()).thenReturn(daoInfo);
        when(daoInfo.getNullable()).thenReturn(Boolean.FALSE);

        assertTrue(cut.complete(config), "The result of completion should be true");

        verify(reference).setRealFilterField(eq(groupingField));
        verify(daoInfo, never()).setNullable(any());
    }
}
