package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.single.indirect.*;
import de.ma_vin.util.sample.content.dto.ITransportable;
import de.ma_vin.util.sample.content.dto.RootDto;
import de.ma_vin.util.sample.content.dto.single.indirect.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static de.ma_vin.util.sample.content.ObjectFactory.*;
import static de.ma_vin.util.sample.content.ObjectFactory.getNextId;
import static org.junit.jupiter.api.Assertions.*;

public class SingleIndirectTransportMapperTest {

    private Root root;
    private SingleRefIndirectParent singleRefIndirectParent;
    private SingleRefOtherIndirectParent singleRefOtherIndirectParent;

    private RootDto rootDto;
    private SingleRefIndirectParentDto singleRefIndirectParentDto;
    private SingleRefOtherIndirectParentDto singleRefOtherIndirectParentDto;

    Map<String, IIdentifiable> mappedObjects = new HashMap<>();
    Map<String, ITransportable> mappedDtoObjects = new HashMap<>();

    @BeforeEach
    public void setUp() {
        mappedObjects.clear();
        mappedDtoObjects.clear();

        initObjectFactory();
        singleRefIndirectParent = createSingleRefIndirectParent(getNextId());
        singleRefOtherIndirectParent = createSingleRefOtherIndirectParent(getNextId());
        root = createRoot(getNextId());

        initObjectFactory();
        singleRefIndirectParentDto = createSingleRefIndirectParentDto(getNextId());
        singleRefOtherIndirectParentDto = createSingleRefOtherIndirectParentDto(getNextId());
        rootDto = createRootDto(getNextId());
    }

    @Test
    public void testConvertToSingleRefIndirectParent() {
        SingleRefIndirectParent result = SingleIndirectTransportMapper.convertToSingleRefIndirectParent(singleRefIndirectParentDto);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefIndirectParentDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefIndirectParentDto.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToSingleRefIndirectParentWithParent() {
        SingleRefIndirectParent result = SingleIndirectTransportMapper.convertToSingleRefIndirectParent(singleRefIndirectParentDto, root);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefIndirectParentDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefIndirectParentDto.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, root.getSingleRefIndirectParent(), "Wrong single ref indirect at root");
    }

    @Test
    public void testConvertToSingleRefIndirectParentNull() {
        assertNull(SingleIndirectTransportMapper.convertToSingleRefIndirectParent(null), "The result should be null");
    }

    @Test
    public void estConvertToSingleRefIndirectParentAgain() {
        SingleRefIndirectParent result = SingleIndirectTransportMapper.convertToSingleRefIndirectParent(singleRefIndirectParentDto, mappedObjects);
        SingleRefIndirectParent convertAgainResult = SingleIndirectTransportMapper.convertToSingleRefIndirectParent(singleRefIndirectParentDto, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToSingleRefOtherIndirectParent() {
        SingleRefOtherIndirectParent result = SingleIndirectTransportMapper.convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParentDto);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefOtherIndirectParentDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefOtherIndirectParentDto.getDescription(), result.getDescription(), "Wrong description");
        assertNull(result.getSingleIndirectRef(), "There should be any reference");
    }

    @Test
    public void testConvertToSingleRefOtherIndirectParentWithChildren() {
        addToCreatedMap(singleRefIndirectParentDto);
        singleRefOtherIndirectParentDto = createSingleRefOtherIndirectParentDtoWithChildren(getNextId(), singleRefIndirectParentDto.getIdentification());

        SingleRefOtherIndirectParent result = SingleIndirectTransportMapper.convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParentDto);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefOtherIndirectParentDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefOtherIndirectParentDto.getDescription(), result.getDescription(), "Wrong description");
        assertNotNull(result.getSingleIndirectRef(), "There should be some reference");
        assertEquals(singleRefIndirectParentDto.getIdentification(), result.getSingleIndirectRef().getIdentification(), "Wrong identification at reference");
    }

    @Test
    public void testConvertToSingleRefOtherIndirectParentWithParent() {
        SingleRefOtherIndirectParent result = SingleIndirectTransportMapper.convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParentDto, root);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefOtherIndirectParentDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefOtherIndirectParentDto.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, root.getSingleRefIndirectOtherParent(), "Wrong single ref indirect at root");
    }

    @Test
    public void testConvertToSingleRefOtherIndirectParentNull() {
        assertNull(SingleIndirectTransportMapper.convertToSingleRefOtherIndirectParent(null), "The result should be null");
    }

    @Test
    public void testConvertToSingleRefOtherIndirectParentAgain() {
        SingleRefOtherIndirectParent result = SingleIndirectTransportMapper.convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParentDto, mappedObjects);
        SingleRefOtherIndirectParent convertAgainResult = SingleIndirectTransportMapper.convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParentDto, mappedObjects);
        SingleIndirectTransportMapper.convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParentDto, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToSingleRefIndirectParentDto() {
        SingleRefIndirectParentDto result = SingleIndirectTransportMapper.convertToSingleRefIndirectParentDto(singleRefIndirectParent);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefIndirectParent.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToSingleRefIndirectParentDtoWithParent() {
        SingleRefIndirectParentDto result = SingleIndirectTransportMapper.convertToSingleRefIndirectParentDto(singleRefIndirectParent, rootDto);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefIndirectParent.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, rootDto.getSingleRefIndirectParent(), "Wrong single ref indirect at root");
    }

    @Test
    public void testConvertToSingleRefIndirectParentDtoNull() {
        assertNull(SingleIndirectTransportMapper.convertToSingleRefIndirectParentDto(null), "The result should be null");
    }

    @Test
    public void testConvertToSingleRefIndirectParentDtoAgain() {
        SingleRefIndirectParentDto result = SingleIndirectTransportMapper.convertToSingleRefIndirectParentDto(singleRefIndirectParent, mappedDtoObjects);
        SingleRefIndirectParentDto convertAgainResult = SingleIndirectTransportMapper.convertToSingleRefIndirectParentDto(singleRefIndirectParent, mappedDtoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToSingleRefOtherIndirectParentDto() {
        SingleRefOtherIndirectParentDto result = SingleIndirectTransportMapper.convertToSingleRefOtherIndirectParentDto(singleRefOtherIndirectParent);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefOtherIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefOtherIndirectParent.getDescription(), result.getDescription(), "Wrong description");
        assertNull(result.getSingleIndirectRef(), "There should be any reference");
    }

    @Test
    public void testConvertToSingleRefOtherIndirectParentDtoWithChildren() {
        addToCreatedMap(singleRefIndirectParent);
        singleRefOtherIndirectParent = createSingleRefOtherIndirectParentWithChildren(getNextId(), singleRefIndirectParentDto.getIdentification());

        SingleRefOtherIndirectParentDto result = SingleIndirectTransportMapper.convertToSingleRefOtherIndirectParentDto(singleRefOtherIndirectParent);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefOtherIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefOtherIndirectParent.getDescription(), result.getDescription(), "Wrong description");
        assertNotNull(result.getSingleIndirectRef(), "There should be some reference");
        assertEquals(singleRefIndirectParent.getIdentification(), result.getSingleIndirectRef().getIdentification(), "Wrong identification at reference");
    }

    @Test
    public void testConvertToSingleRefOtherIndirectParentDtoWithParent() {
        SingleRefOtherIndirectParentDto result = SingleIndirectTransportMapper.convertToSingleRefOtherIndirectParentDto(singleRefOtherIndirectParent, rootDto);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefOtherIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefOtherIndirectParent.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, rootDto.getSingleRefIndirectOtherParent(), "Wrong single ref indirect at root");
    }

    @Test
    public void testConvertToSingleRefOtherIndirectParentDtoNull() {
        assertNull(SingleIndirectTransportMapper.convertToSingleRefOtherIndirectParentDto(null), "The result should be null");
    }

    @Test
    public void testConvertToSingleRefOtherIndirectParentDtoAgain() {
        SingleRefOtherIndirectParentDto result = SingleIndirectTransportMapper.convertToSingleRefOtherIndirectParentDto(singleRefOtherIndirectParent, mappedDtoObjects);
        SingleRefOtherIndirectParentDto convertAgainResult = SingleIndirectTransportMapper.convertToSingleRefOtherIndirectParentDto(singleRefOtherIndirectParent, mappedDtoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testGetInstance() {
        SingleIndirectTransportMapper firstInstance = SingleIndirectTransportMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, SingleIndirectTransportMapper.getInstance(), "Any other instance should be the same");
    }

}
