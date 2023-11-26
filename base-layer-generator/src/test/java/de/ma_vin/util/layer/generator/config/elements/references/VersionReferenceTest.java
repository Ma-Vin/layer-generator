package de.ma_vin.util.layer.generator.config.elements.references;

import de.ma_vin.util.layer.generator.config.elements.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link VersionReference} is the class under test
 */
public class VersionReferenceTest {

    private VersionReference cut;

    @Mock
    private Entity entity;

    private final List<String> messages = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        cut = new VersionReference();

        cut.setReferenceName("ReferenceName");
        cut.setTargetEntity("TargetEntity");
        cut.setShortDescription("ShortDescription");
        cut.setRealTargetEntity(entity);
        cut.setIsOwner(Boolean.TRUE);

        messages.clear();
    }

    @Test
    public void testGetAsReference() {
        Reference result = cut.getAsReference();

        assertNotNull(result, "There should be a result");
        assertEquals(cut.getReferenceName(), result.getReferenceName(), "Wrong referenceName");
        assertEquals(cut.getTargetEntity(), result.getTargetEntity(), "Wrong targetEntity");
        assertEquals(cut.getShortDescription(), result.getShortDescription(), "Wrong shortDescription");
        assertEquals(cut.getRealTargetEntity(), result.getRealTargetEntity(), "Wrong realTargetEntity");
        assertEquals(cut.isOwner(), result.isOwner(), "Wrong isOwner");
        assertNull(result.getFilterField(), "filterField should be null");
        assertNull(result.getRealFilterField(), "realFilterField should be null");
        assertNull(result.getFilterFieldValue(), "filterFieldValue should be null");
        assertNull(result.getParent(), "parent should be null");
        assertNull(result.getNonOwnerFilterField(), "nonOwnerFilterField should be null");
        assertEquals(Boolean.FALSE, result.getIsList(), "Wrong isList");
        assertFalse(result.isAggregated(), "Wrong isAggregated");
        assertFalse( result.isReverse(), "Wrong isReverse");
    }

    @Test
    public void testIsValidEmptyDivergentTargetVersion(){
        cut.setDivergentTargetVersion("");
        assertFalse(cut.isValid(messages), "Entity should not be valid");
        assertEquals(1, messages.size(), "Wrong number of messages");
    }
}
