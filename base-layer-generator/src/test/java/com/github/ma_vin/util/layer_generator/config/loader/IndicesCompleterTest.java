package com.github.ma_vin.util.layer_generator.config.loader;

import com.github.ma_vin.util.layer_generator.config.elements.Index;
import com.github.ma_vin.util.layer_generator.config.elements.fields.Field;
import com.github.ma_vin.util.layer_generator.config.elements.fields.FieldSorting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * {@link IndicesCompleter} is the class under test
 */
public class IndicesCompleterTest extends AbstractCompleterTest {
    private static final String FIELD_NAME = "FieldName";
    private static final String SECOND_FIELD_NAME = "SecondFieldName";
    private static final String FIELD_LIST = FIELD_NAME;
    private IndicesCompleter cut;

    @Mock
    private Index index;
    @Mock
    private Field field;
    @Mock
    private Field secondField;


    private final List<FieldSorting> fieldSortingList = new ArrayList<>();

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();

        cut = new IndicesCompleter(logger);
        fieldSortingList.clear();

        when(field.getFieldName()).thenReturn(FIELD_NAME);
        when(secondField.getFieldName()).thenReturn(SECOND_FIELD_NAME);

        when(index.getFieldList()).thenReturn(FIELD_LIST);
        when(index.getFields()).thenReturn(fieldSortingList);

        when(entity.getFields()).thenReturn(Arrays.asList(field, secondField));
        when(entity.getIndices()).thenReturn(Collections.singletonList(index));
    }


    @DisplayName("complete index without order")
    @Test
    public void testComplete() {
        assertTrue(cut.complete(config), "The result of completion should be true");
        assertEquals(1, fieldSortingList.size(), "Wrong number of index fields");
        assertTrue(fieldSortingList.get(0).isAscending(), "The field at index should be ascending");
        assertEquals(field, fieldSortingList.get(0).getField(), "Wrong field at index");


        verify(index).setFields(any());
    }

    @DisplayName("complete index with explicit ascending order")
    @Test
    public void testCompleteIndexAsc() {
        when(index.getFieldList()).thenReturn(FIELD_NAME + " ASC");

        assertTrue( cut.complete(config), "The result of completion should be true");
        assertEquals(1, fieldSortingList.size(), "Wrong number of index fields");
        assertTrue(fieldSortingList.get(0).isAscending(), "The field at index should be ascending");
        assertEquals(field, fieldSortingList.get(0).getField(), "Wrong field at index");

        verify(index).setFields(any());
    }

    @DisplayName("complete index with explicit descending order")
    @Test
    public void testCompleteIndexDesc() {
        when(index.getFieldList()).thenReturn(FIELD_NAME + " DESC");

        assertTrue(cut.complete(config), "The result of completion should be true");
        assertEquals(1, fieldSortingList.size(), "Wrong number of index fields");
        assertFalse(fieldSortingList.get(0).isAscending(), "The field at index should be descending");
        assertEquals(field, fieldSortingList.get(0).getField(), "Wrong field at index");

        verify(index).setFields(any());
    }

    @DisplayName("complete index with id column")
    @Test
    public void testCompleteIndexId() {
        when(index.getFieldList()).thenReturn("Id");

        assertTrue(cut.complete(config), "The result of completion should be true");
        assertEquals(1, fieldSortingList.size(), "Wrong number of index fields");
        assertTrue(fieldSortingList.get(0).isAscending(), "The field at index should be ascending");
        assertNotNull(fieldSortingList.get(0).getField(), "The field should not be null");
        assertEquals("Id", fieldSortingList.get(0).getField().getFieldName(), "Wrong field at index");

        verify(index).setFields(any());
    }

    @DisplayName("complete index with non existing target field")
    @Test
    public void testCompleteNonExistingField() {
        when(index.getFieldList()).thenReturn("AnyRandomField");

        assertFalse(cut.complete(config), "The result of completion should be false");
        assertEquals(0, fieldSortingList.size(), "Wrong number of index fields");
    }

    @DisplayName("complete index with two fields")
    @Test
    public void testCompleteTwoFields() {
        when(index.getFieldList()).thenReturn(FIELD_NAME + ", " + SECOND_FIELD_NAME);

        assertTrue(cut.complete(config), "The result of completion should be true");
        assertEquals(2, fieldSortingList.size(), "Wrong number of index fields");
        assertTrue(fieldSortingList.get(0).isAscending(), "The field at index should be ascending");
        assertEquals(field, fieldSortingList.get(0).getField(), "Wrong field at index");
        assertTrue(fieldSortingList.get(1).isAscending(), "The second field at index should be ascending");
        assertEquals(secondField, fieldSortingList.get(1).getField(), "Wrong second field at index");

        verify(index).setFields(any());
    }
}
