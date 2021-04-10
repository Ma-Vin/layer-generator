package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.domain.parent.ExtendingClass;
import de.ma_vin.util.sample.content.dto.parent.ExtendingClassDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.ma_vin.util.sample.content.ObjectFactory.*;
import static org.junit.jupiter.api.Assertions.*;

public class ParentTransportMapperTest {
    private ExtendingClassDto extendingClassDto;

    private ExtendingClass extendingClass;

    @BeforeEach
    public void setUp() {
        initObjectFactory();
        extendingClassDto = createExtendingClassDto(getNextId());

        initObjectFactory();
        extendingClass = createExtendingClass(getNextId());
    }

    @Test
    public void testConvertToExtendingClass() {
        ExtendingClass result = ParentTransportMapper.convertToExtendingClass(extendingClassDto);
        assertNotNull(result, "There should be any result");
        assertEquals(extendingClassDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(extendingClassDto.getDescription(), result.getDescription(), "Wrong description");
        assertEquals(extendingClassDto.getAdditionalDescription(), result.getAdditionalDescription(), "Wrong additional description");
    }

    @Test
    public void testConvertToMultiRefOneParentNull() {
        assertNull(ParentTransportMapper.convertToExtendingClass(null), "The result should be null");
    }

    @Test
    public void testConvertToExtendingClassDto() {
        ExtendingClassDto result = ParentTransportMapper.convertToExtendingClassDto(extendingClass);
        assertNotNull(result, "There should be any result");
        assertEquals(extendingClass.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(extendingClass.getDescription(), result.getDescription(), "Wrong description");
        assertEquals(extendingClass.getAdditionalDescription(), result.getAdditionalDescription(), "Wrong additional description");
    }

    @Test
    public void testConvertToMultiRefOneParentDtoNull() {
        assertNull(ParentTransportMapper.convertToExtendingClassDto(null), "The result should be null");
    }

    @Test
    public void testGetInstance() {
        ParentTransportMapper firstInstance = ParentTransportMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, ParentTransportMapper.getInstance(), "Any other instance should be the same");
    }
}
