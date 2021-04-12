package de.ma_vin.util.layer.generator.config;

import static org.junit.jupiter.api.Assertions.*;

import de.ma_vin.util.layer.generator.config.elements.Reference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ReferenceTest {
    private Reference cut;

    @BeforeEach
    public void setUp() {
        cut = new Reference();
        cut.setReferenceName("Ref");
        cut.setTargetEntity("Target");
        cut.setIsOwner(true);
        cut.setIsList(true);
        cut.setFilterField("filterField");
        cut.setFilterFieldValue("filterValue");
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

        assertTrue(Reference.isFilterFieldValid(Arrays.asList(ref1, ref2)), "The validation should be successful");
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

        assertFalse(Reference.isFilterFieldValid(Arrays.asList(ref1, ref2)), "The validation should not be successful");
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

        assertTrue(Reference.isFilterFieldValid(Arrays.asList(ref1, ref2)), "The validation should be successful");
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

        assertTrue(Reference.isFilterFieldValid(Arrays.asList(ref1, ref2)), "The validation should be successful");
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

        assertTrue(Reference.isFilterFieldValid(Arrays.asList(ref1, ref2)), "The validation should be successful");
    }

    @Test
    public void testCopy() {
        Reference result = cut.copy();
        assertNotNull(result, "There should be any result");
        assertEquals(cut, result, "The copy should be equal to the original");
    }
}
