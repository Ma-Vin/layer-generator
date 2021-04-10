package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.multi.MultiRefOneParent;
import de.ma_vin.util.sample.content.domain.multi.MultiRefTwoParents;
import de.ma_vin.util.sample.content.dto.RootDto;
import de.ma_vin.util.sample.content.dto.multi.MultiRefOneParentDto;
import de.ma_vin.util.sample.content.dto.multi.MultiRefTwoParentsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.ma_vin.util.sample.content.ObjectFactory.*;
import static org.junit.jupiter.api.Assertions.*;

public class MultiTransportMapperTest {
    private MultiRefOneParentDto multiRefOneParentDto;
    private MultiRefTwoParentsDto multiRefTwoParentsDto;

    private MultiRefOneParent multiRefOneParent;
    private MultiRefTwoParents multiRefTwoParents;

    @BeforeEach
    public void setUp() {
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
    public void testConvertToMultiRefTwoParents() {
        MultiRefTwoParents result = MultiTransportMapper.convertToMultiRefTwoParents(multiRefTwoParentsDto);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefTwoParentsDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefTwoParentsDto.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToMultiRefTwoParentsNull() {
        assertNull(MultiTransportMapper.convertToMultiRefTwoParents(null), "The result should be null");
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
    public void testGetInstance() {
        MultiTransportMapper firstInstance = MultiTransportMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, MultiTransportMapper.getInstance(), "Any other instance should be the same");
    }
}
