package de.ma_vin.util.layer.generator.config.loader;

import de.ma_vin.util.layer.generator.config.elements.fields.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * {@link FieldCompleter} is the class under test
 */
public class FieldCompleterTest extends AbstractCompleterTest {

    @InjectMocks
    private FieldCompleter cut;

    @Mock
    private Field field;
    @Mock
    private Field groupingField;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();

        when(entity.getFields()).thenReturn(Collections.singletonList(field));
        when(groupingEntity.getFields()).thenReturn(Collections.singletonList(groupingField));
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
}
