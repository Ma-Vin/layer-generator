package de.ma_vin.util.layer.generator.config;

import static org.junit.jupiter.api.Assertions.*;

import de.ma_vin.util.layer.generator.config.elements.Reference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReferenceTest {
    private Reference cut;
    private final List<String> messages = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        cut = new Reference();
        cut.setReferenceName("Ref");
        cut.setTargetEntity("Target");
        cut.setIsOwner(true);
        cut.setIsList(true);
        cut.setShortDescription("shortDescription");
        cut.setFilterField("filterField");
        cut.setFilterFieldValue("filterValue");

        messages.clear();
    }

    @Test
    public void testIsFilterFieldValidDifferentTargets() {
        Reference ref1 = new Reference();
        ref1.setTargetEntity("t1");
        ref1.setIsList(true);
        ref1.setFilterField(null);

        Reference ref2 = new Reference();
        ref2.setTargetEntity("t2");
        ref2.setIsList(true);
        ref2.setFilterField(null);

        assertTrue(Reference.isFilterFieldValid("entity", Arrays.asList(ref1, ref2), messages), "The validation should be successful");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsFilterFieldValidEqualTargets() {
        Reference ref1 = new Reference();
        ref1.setTargetEntity("t1");
        ref1.setIsList(true);
        ref1.setFilterField(null);

        Reference ref2 = new Reference();
        ref2.setTargetEntity("t1");
        ref2.setIsList(true);
        ref2.setFilterField(null);

        assertFalse(Reference.isFilterFieldValid("entity", Arrays.asList(ref1, ref2), messages), "The validation should not be successful");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsFilterFieldValidEqualTargetsWithOneFilter() {
        Reference ref1 = new Reference();
        ref1.setTargetEntity("t1");
        ref1.setIsList(true);
        ref1.setFilterField("f1");

        Reference ref2 = new Reference();
        ref2.setTargetEntity("t1");
        ref2.setIsList(true);
        ref2.setFilterField(null);

        assertTrue(Reference.isFilterFieldValid("entity", Arrays.asList(ref1, ref2), messages), "The validation should be successful");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsFilterFieldValidEqualTargetsWithAllFilter() {
        Reference ref1 = new Reference();
        ref1.setTargetEntity("t1");
        ref1.setIsList(true);
        ref1.setFilterField("f1");

        Reference ref2 = new Reference();
        ref2.setTargetEntity("t1");
        ref2.setIsList(true);
        ref2.setFilterField("f2");

        assertTrue(Reference.isFilterFieldValid("entity", Arrays.asList(ref1, ref2), messages), "The validation should be successful");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsFilterFieldValidEqualTargetsButOnlyOneIsList() {
        Reference ref1 = new Reference();
        ref1.setTargetEntity("t1");
        ref1.setIsList(true);
        ref1.setFilterField(null);

        Reference ref2 = new Reference();
        ref2.setTargetEntity("t1");
        ref2.setIsList(false);
        ref2.setFilterField(null);

        assertTrue(Reference.isFilterFieldValid("entity", Arrays.asList(ref1, ref2), messages), "The validation should be successful");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testCopy() {
        Reference result = cut.copy();
        assertNotNull(result, "There should be any result");
        assertEquals(cut, result, "The copy should be equal to the original");
    }

    @Test
    public void testIsValid() {
        assertTrue(cut.isValid(messages), "Entity should be valid");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidNulls() {
        cut.setShortDescription(null);
        cut.setFilterField(null);
        cut.setFilterFieldValue(null);
        assertTrue(cut.isValid(messages), "Entity should be valid");
        assertEquals(0, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidTargetEntity() {
        cut.setTargetEntity(null);
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidReferenceName() {
        cut.setReferenceName(null);
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidShortDescription() {
        cut.setShortDescription("");
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidFilterField() {
        cut.setFilterField("");
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }

    @Test
    public void testIsValidFilterFieldValue() {
        cut.setFilterFieldValue("");
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }
}
