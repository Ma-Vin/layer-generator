package de.ma_vin.util.layer.generator.config.elements.fields;

import de.ma_vin.util.layer.generator.config.elements.Models;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link VersionField} is the class under test
 */
public class VersionFieldTest {

    private VersionField cut;

    @BeforeEach
    public void setUp() {
        cut = new VersionField();
        cut.setFieldName("FieldName");
        cut.setType("Type");
        cut.setTypePackage("TypePackage");
        cut.setIsTypeEnum(Boolean.TRUE);
        cut.setShortDescription("ShortDescription");
        cut.setDescription("Description");
    }

    @Test
    public void testGetAsField() {
        Field result = cut.getAsField();

        assertNotNull(result, "There should be a result");
        assertEquals(cut.getFieldName(), result.getFieldName(), "Wrong fieldName");
        assertEquals(cut.getType(), result.getType(), "Wrong type");
        assertEquals(cut.getTypePackage(), result.getTypePackage(), "Wrong typePackage");
        assertEquals(cut.getIsTypeEnum(), result.getIsTypeEnum(), "Wrong isTypeEnum");
        assertEquals(cut.getShortDescription(), result.getShortDescription(), "Wrong shortDescription");
        assertEquals(cut.getDescription(), result.getDescription(), "Wrong description");
        assertEquals(Models.DTO, result.getModels(), "Wrong model");
        assertNull(result.getDaoInfo(), "The daoInfo should be null");
        assertNull(result.getParentEntity(), "The parentEntity should be null");
    }
}
