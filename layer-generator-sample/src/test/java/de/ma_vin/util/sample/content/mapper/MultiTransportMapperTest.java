package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.multi.MultiRefOneParent;
import de.ma_vin.util.sample.content.domain.multi.MultiRefTwoParents;
import de.ma_vin.util.sample.content.dto.ITransportable;
import de.ma_vin.util.sample.content.dto.multi.MultiRefOneParentDto;
import de.ma_vin.util.sample.content.dto.multi.MultiRefTwoParentsDto;
import de.ma_vin.util.sample.extending.ExtendedMultiRefTwoParents;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static de.ma_vin.util.sample.content.ObjectFactory.*;
import static org.junit.jupiter.api.Assertions.*;

public class MultiTransportMapperTest {
    private MultiRefOneParentDto multiRefOneParentDto;
    private MultiRefTwoParentsDto multiRefTwoParentsDto;

    private MultiRefOneParent multiRefOneParent;
    private MultiRefTwoParents multiRefTwoParents;

    Map<String, IIdentifiable> mappedObjects = new HashMap<>();
    Map<String, ITransportable> mappedDtoObjects = new HashMap<>();

    @BeforeEach
    public void setUp() {
        mappedObjects.clear();
        mappedDtoObjects.clear();

        initObjectFactory();
        multiRefOneParentDto = createMultiRefOneParentDto(getNextId());
        multiRefTwoParentsDto = createMultiRefTwoParentsDto(getNextId());

        initObjectFactory();
        multiRefOneParent = createMultiRefOneParentWithChildren(getNextId());
        multiRefTwoParents = createMultiRefTwoParents(getNextId());
    }

    @Test
    public void testConvertToMultiRefOneParent() {
        MultiRefOneParent result = MultiTransportMapper.convertToMultiRefOneParent(multiRefOneParentDto);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOneParentDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOneParentDto.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(0, result.getMultiRefs().size(), "Wrong number of MultiRefs");
    }

    @Test
    public void testConvertToMultiRefOneParentWithChildren() {
        MultiRefOneParent result = MultiTransportMapper.convertToMultiRefOneParent(multiRefOneParentDto);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOneParentDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOneParentDto.getDescription(), result.getDescription(), "Wrong description");

    }

    @Test
    public void testConvertToMultiRefOneParentWithParent() {
        MultiRefOneParent result = MultiTransportMapper.convertToMultiRefOneParent(multiRefOneParentDto);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOneParentDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOneParentDto.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToMultiRefOneParentNull() {
        assertNull(MultiTransportMapper.convertToMultiRefOneParent(null), "The result should be null");
    }

    @Test
    public void testConvertToMultiRefOneParentAgain() {
        MultiRefOneParent result = MultiTransportMapper.convertToMultiRefOneParent(multiRefOneParentDto, mappedObjects);
        MultiRefOneParent convertAgainResult = MultiTransportMapper.convertToMultiRefOneParent(multiRefOneParentDto, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToMultiRefTwoParents() {
        MultiRefTwoParents result = MultiTransportMapper.convertToMultiRefTwoParents(multiRefTwoParentsDto);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefTwoParentsDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefTwoParentsDto.getDescription(), result.getDescription(), "Wrong description");
        assertTrue(result instanceof ExtendedMultiRefTwoParents, "The result should be an extended one");
    }

    @Test
    public void testConvertToMultiRefTwoParentsNull() {
        assertNull(MultiTransportMapper.convertToMultiRefTwoParents(null), "The result should be null");
    }

    @Test
    public void testConvertToMultiRefTwoParentsAgain() {
        MultiRefTwoParents result = MultiTransportMapper.convertToMultiRefTwoParents(multiRefTwoParentsDto, mappedObjects);
        MultiRefTwoParents convertAgainResult = MultiTransportMapper.convertToMultiRefTwoParents(multiRefTwoParentsDto, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToMultiRefOneParentDto() {
        MultiRefOneParentDto result = MultiTransportMapper.convertToMultiRefOneParentDto(multiRefOneParent);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOneParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOneParent.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToMultiRefOneParentDtoWithChildren() {
        MultiRefOneParentDto result = MultiTransportMapper.convertToMultiRefOneParentDto(multiRefOneParent);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOneParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOneParent.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToMultiRefOneParentDtoWithParent() {
        MultiRefOneParentDto result = MultiTransportMapper.convertToMultiRefOneParentDto(multiRefOneParent);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOneParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOneParent.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToMultiRefOneParentDtoNull() {
        assertNull(MultiTransportMapper.convertToMultiRefOneParentDto(null), "The result should be null");
    }

    @Test
    public void testConvertToMultiRefOneParentDtoAgain() {
        MultiRefOneParentDto result = MultiTransportMapper.convertToMultiRefOneParentDto(multiRefOneParent, mappedDtoObjects);
        MultiRefOneParentDto convertAgainResult = MultiTransportMapper.convertToMultiRefOneParentDto(multiRefOneParent, mappedDtoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToMultiRefTwoParentsDto() {
        MultiRefTwoParentsDto result = MultiTransportMapper.convertToMultiRefTwoParentsDto(multiRefTwoParents);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefTwoParents.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefTwoParents.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToMultiRefTwoParentsDtoNull() {
        assertNull(MultiTransportMapper.convertToMultiRefTwoParentsDto(null), "The result should be null");
    }

    @Test
    public void testConvertToMultiRefTwoParentsDtoAgain() {
        MultiRefTwoParentsDto result = MultiTransportMapper.convertToMultiRefTwoParentsDto(multiRefTwoParents, mappedDtoObjects);
        MultiRefTwoParentsDto convertAgainResult = MultiTransportMapper.convertToMultiRefTwoParentsDto(multiRefTwoParents, mappedDtoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testGetInstance() {
        MultiTransportMapper firstInstance = MultiTransportMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, MultiTransportMapper.getInstance(), "Any other instance should be the same");
    }
}
