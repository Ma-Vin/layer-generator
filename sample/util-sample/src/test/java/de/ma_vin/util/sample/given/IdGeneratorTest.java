package de.ma_vin.util.sample.given;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link IdGenerator} is the class under test
 */
public class IdGeneratorTest {

    @Test
    public void testGenerateIdentification() {
        assertEquals("ABC1", IdGenerator.generateIdentification(1L, "ABC"), "Wrong identification");
    }

    @Test
    public void testGenerateId() {
        assertEquals(1L, IdGenerator.generateId("ABC1", "ABC"), "Wrong id");
    }

    @Test
    public void testGenerateIdWrongPrefix() {
        assertEquals(0L, IdGenerator.generateId("ABC1", "ABCD"), "Wrong id");
    }
}
