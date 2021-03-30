package de.mv.ape.layer.generator.config;

import static org.junit.jupiter.api.Assertions.*;

import de.mv.ape.layer.generator.config.elements.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EntityTest {
    Entity cut;

    @BeforeEach
    public void setUp(){
        cut = new Entity();
    }

    @Test
    public void testHasNoParent(){
        cut.setParent(null);
        assertTrue(cut.hasNoParent(), "Null should be interpreted as has no parent");
        cut.setParent(" ");
        assertTrue(cut.hasNoParent(), "Trimmed empty string should be interpreted as has no parent");
        cut.setParent("a");
        assertFalse(cut.hasNoParent(), "Non empty string should not be interpreted as has no parent");
    }

    @Test
    public void testHasParent(){
        cut.setParent(null);
        assertFalse(cut.hasParent(), "Null should not be interpreted as has parent");
        cut.setParent(" ");
        assertFalse(cut.hasParent(), "Trimmed empty string should be interpreted as has parent");
        cut.setParent("a");
        assertTrue(cut.hasParent(), "Non empty string should be interpreted as has parent");
    }
}
