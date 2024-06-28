package com.github.ma_vin.util.layer_generator.sample.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.content.domain.parent.ExtendingClass;
import com.github.ma_vin.util.layer_generator.sample.content.dto.ITransportable;
import com.github.ma_vin.util.layer_generator.sample.content.dto.parent.ExtendingClassDto;
import com.github.ma_vin.util.layer_generator.sample.extending.ExtendedExtendingClassDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.github.ma_vin.util.layer_generator.sample.content.ObjectFactory.*;
import static org.junit.jupiter.api.Assertions.*;

public class ParentTransportMapperTest {
    private ExtendingClassDto extendingClassDto;

    private ExtendingClass extendingClass;

    Map<String, IIdentifiable> mappedObjects = new HashMap<>();
    Map<String, ITransportable> mappedDtoObjects = new HashMap<>();

    @BeforeEach
    public void setUp() {
        mappedObjects.clear();
        mappedDtoObjects.clear();

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
    public void testConvertToMultiRefOneParentAgain() {
        ExtendingClass result = ParentTransportMapper.convertToExtendingClass(extendingClassDto, mappedObjects);
        ExtendingClass convertAgainResult = ParentTransportMapper.convertToExtendingClass(extendingClassDto, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToExtendingClassDto() {
        ExtendingClassDto result = ParentTransportMapper.convertToExtendingClassDto(extendingClass);
        assertNotNull(result, "There should be any result");
        assertEquals(extendingClass.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(extendingClass.getDescription(), result.getDescription(), "Wrong description");
        assertEquals(extendingClass.getAdditionalDescription(), result.getAdditionalDescription(), "Wrong additional description");
        assertTrue(result instanceof ExtendedExtendingClassDto, "The result should be an extended one");
    }

    @Test
    public void testConvertToMultiRefOneParentDtoNull() {
        assertNull(ParentTransportMapper.convertToExtendingClassDto(null), "The result should be null");
    }

    @Test
    public void testConvertToMultiRefOneParentDtoAgain() {
        ExtendingClassDto result = ParentTransportMapper.convertToExtendingClassDto(extendingClass, mappedDtoObjects);
        ExtendingClassDto convertAgainResult = ParentTransportMapper.convertToExtendingClassDto(extendingClass, mappedDtoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testGetInstance() {
        ParentTransportMapper firstInstance = ParentTransportMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, ParentTransportMapper.getInstance(), "Any other instance should be the same");
    }
}
