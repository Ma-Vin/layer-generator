package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.multi.indirect.*;
import de.ma_vin.util.sample.content.dto.ITransportable;
import de.ma_vin.util.sample.content.dto.multi.indirect.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static de.ma_vin.util.sample.content.ObjectFactory.*;
import static de.ma_vin.util.sample.content.ObjectFactory.getNextId;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;

public class MultiIndirectTransportMapperTest {

    private MultiRefIndirectParent multiRefIndirectParent;
    private MultiRefOtherIndirectParent multiRefOtherIndirectParent;

    private MultiRefIndirectParentDto multiRefIndirectParentDto;
    private MultiRefOtherIndirectParentDto multiRefOtherIndirectParentDto;

    Map<String, IIdentifiable> mappedObjects = new HashMap<>();
    Map<String, ITransportable> mappedDtoObjects = new HashMap<>();

    @BeforeEach
    public void setUp() {
        mappedObjects.clear();
        mappedDtoObjects.clear();

        initObjectFactory();
        multiRefIndirectParentDto = createMultiRefIndirectParentDto(getNextId());
        addToCreatedMap(multiRefIndirectParentDto);
        multiRefOtherIndirectParentDto = createMultiRefOtherIndirectParentDto(getNextId());

        initObjectFactory();
        multiRefIndirectParent = createMultiRefIndirectParent(getNextId());
        addToCreatedMap(multiRefIndirectParent);
        multiRefOtherIndirectParent = createMultiRefOtherIndirectParentWithChildren(getNextId(), multiRefIndirectParent.getIdentification());
    }

    @Test
    public void testConvertToMultiRefIndirectParent() {
        MultiRefIndirectParent result = MultiIndirectTransportMapper.convertToMultiRefIndirectParent(multiRefIndirectParentDto);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefIndirectParentDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefIndirectParentDto.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToMultiRefIndirectParentNull() {
        assertNull(MultiIndirectTransportMapper.convertToMultiRefIndirectParent(null), "The result should be null");
    }

    @Test
    public void testConvertToMultiRefIndirectParentAgain() {
        MultiRefIndirectParent result = MultiIndirectTransportMapper.convertToMultiRefIndirectParent(multiRefIndirectParentDto, mappedObjects);
        MultiRefIndirectParent convertAgainResult = MultiIndirectTransportMapper.convertToMultiRefIndirectParent(multiRefIndirectParentDto, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToMultiRefOtherIndirectParent() {
        MultiRefOtherIndirectParent result = MultiIndirectTransportMapper.convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParentDto);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOtherIndirectParentDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOtherIndirectParentDto.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(0, result.getMultiIndirectRefs().size(), "Wrong number of MultiRefs");
    }

    @Test
    public void testConvertToMultiRefOtherIndirectParentNull() {
        assertNull(MultiIndirectTransportMapper.convertToMultiRefOtherIndirectParent(null), "The result should be null");
    }

    @Test
    public void testConvertToMultiRefOtherIndirectParentAgain() {
        MultiRefOtherIndirectParent result = MultiIndirectTransportMapper.convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParentDto, mappedObjects);
        MultiRefOtherIndirectParent convertAgainResult = MultiIndirectTransportMapper.convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParentDto, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToMultiRefIndirectParentDto() {
        MultiRefIndirectParentDto result = MultiIndirectTransportMapper.convertToMultiRefIndirectParentDto(multiRefIndirectParent);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefIndirectParent.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToMultiRefIndirectParentDtoNull() {
        assertNull(MultiIndirectTransportMapper.convertToMultiRefIndirectParentDto(null), "The result should be null");
    }

    @Test
    public void testConvertToMultiRefIndirectParentDtoAgain() {
        MultiRefIndirectParentDto result = MultiIndirectTransportMapper.convertToMultiRefIndirectParentDto(multiRefIndirectParent, mappedDtoObjects);
        MultiRefIndirectParentDto convertAgainResult = MultiIndirectTransportMapper.convertToMultiRefIndirectParentDto(multiRefIndirectParent, mappedDtoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToMultiRefOtherIndirectParentDto() {
        MultiRefOtherIndirectParentDto result = MultiIndirectTransportMapper.convertToMultiRefOtherIndirectParentDto(multiRefOtherIndirectParent);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOtherIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOtherIndirectParent.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToMultiRefOtherIndirectParentDtoNull() {
        assertNull(MultiIndirectTransportMapper.convertToMultiRefOtherIndirectParentDto(null), "The result should be null");
    }

    @Test
    public void testConvertToMultiRefOtherIndirectParentDtoAgain() {
        MultiRefOtherIndirectParentDto result = MultiIndirectTransportMapper.convertToMultiRefOtherIndirectParentDto(multiRefOtherIndirectParent, mappedDtoObjects);
        MultiRefOtherIndirectParentDto convertAgainResult = MultiIndirectTransportMapper.convertToMultiRefOtherIndirectParentDto(multiRefOtherIndirectParent, mappedDtoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testGetInstance() {
        MultiIndirectTransportMapper firstInstance = MultiIndirectTransportMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, MultiIndirectTransportMapper.getInstance(), "Any other instance should be the same");
    }
}