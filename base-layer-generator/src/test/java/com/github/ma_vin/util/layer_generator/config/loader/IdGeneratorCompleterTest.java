package com.github.ma_vin.util.layer_generator.config.loader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * {@link IdGeneratorCompleter} is the class under test
 */
public class IdGeneratorCompleterTest extends AbstractCompleterTest {
    private IdGeneratorCompleter cut;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();

        cut = new IdGeneratorCompleter(logger);
    }

    @DisplayName("without id generator")
    @Test
    public void testCompleteWithoutIdGenerator() {
        assertTrue(cut.complete(config), "The result of completion should be true");

        verify(config).setUseIdGenerator(eq(Boolean.FALSE));
    }

    @DisplayName("with valid id generator")
    @Test
    public void testCompleteIdGenerator() {
        when(config.getIdGeneratorPackage()).thenReturn("de.ma_vin.test");
        when(config.getIdGeneratorClass()).thenReturn("IdGenerator");

        assertTrue(cut.complete(config), "The result of completion should be true");

        verify(config).setUseIdGenerator(eq(Boolean.TRUE));
    }

    @DisplayName("with invalid id generator missing class")
    @Test
    public void testCompleteInvalidIdGeneratorWithoutClass() {
        when(config.getIdGeneratorPackage()).thenReturn("de.ma_vin.test");

        assertFalse(cut.complete(config), "The result of completion should be true");

        verify(config).setUseIdGenerator(eq(Boolean.FALSE));
    }

    @DisplayName("with invalid id generator missing package")
    @Test
    public void testCompleteInvalidIdGeneratorWithoutPackage() {
        when(config.getIdGeneratorClass()).thenReturn("IdGenerator");

        assertFalse(cut.complete(config), "The result of completion should be true");

        verify(config).setUseIdGenerator(eq(Boolean.FALSE));
    }
}
