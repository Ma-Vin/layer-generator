package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.single.SingleRefOneParent;
import de.ma_vin.util.sample.content.domain.single.SingleRefTwoParents;
import de.ma_vin.util.sample.content.dto.RootDto;
import de.ma_vin.util.sample.content.dto.single.SingleRefOneParentDto;
import de.ma_vin.util.sample.content.dto.single.SingleRefTwoParentsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.ma_vin.util.sample.content.ObjectFactory.*;
import static org.junit.jupiter.api.Assertions.*;

public class SingleTransportMapperTest {
    private Root root;
    private SingleRefOneParent singleRefOneParent;
    private SingleRefTwoParents singleRefTwoParents;

    private RootDto rootDto;
    private SingleRefOneParentDto singleRefOneParentDto;
    private SingleRefTwoParentsDto singleRefTwoParentsDto;

    @BeforeEach
    public void setUp() {
        initObjectFactory();
        singleRefOneParent = createSingleRefOneParentWithChildren(getNextId());
        singleRefTwoParents = createSingleRefTwoParents(getNextId());
        root = createRoot(getNextId());

        initObjectFactory();
        singleRefOneParentDto = createSingleRefOneParentDtoWithChildren(getNextId());
        singleRefTwoParentsDto = createSingleRefTwoParentsDto(getNextId());
        rootDto = createRootDto(getNextId());
    }

    @Test
    public void testConvertToSingleRefOneParent() {
        SingleRefOneParent result = SingleTransportMapper.convertToSingleRefOneParent(singleRefOneParentDto);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefOneParentDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefOneParentDto.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToSingleRefOneParentWithParent() {
        SingleRefOneParent result = SingleTransportMapper.convertToSingleRefOneParent(singleRefOneParentDto, root);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefOneParentDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefOneParentDto.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, root.getSingleRef(), "Wrong single ref at root");
    }

    @Test
    public void testConvertToSingleRefOneParentNull() {
        assertNull(SingleTransportMapper.convertToSingleRefOneParent(null), "The result should be null");
    }

    @Test
    public void testConvertToSingleRefTwoParents() {
        SingleRefTwoParents result = SingleTransportMapper.convertToSingleRefTwoParents(singleRefTwoParentsDto);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefTwoParentsDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefTwoParentsDto.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToSingleRefTwoParentsWithRootParent() {
        SingleRefTwoParents result = SingleTransportMapper.convertToSingleRefTwoParents(singleRefTwoParentsDto, root);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefTwoParentsDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefTwoParentsDto.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, root.getAnotherSingleRef(), "Wrong another single ref at root");
    }

    @Test
    public void testConvertToSingleRefTwoParentsWithOtherParent() {
        SingleRefTwoParents result = SingleTransportMapper.convertToSingleRefTwoParents(singleRefTwoParentsDto, singleRefOneParent);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefTwoParentsDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefTwoParentsDto.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, singleRefOneParent.getSingleRef(), "Wrong single ref at root");
    }

    @Test
    public void testConvertToSingleRefTwoParentsNull() {
        assertNull(SingleTransportMapper.convertToSingleRefTwoParents(null), "The result should be null");
    }

    @Test
    public void testConvertToSingleRefOneParentDto() {
        SingleRefOneParentDto result = SingleTransportMapper.convertToSingleRefOneParentDto(singleRefOneParent);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefOneParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefOneParent.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToSingleRefOneParentDtoWithParent() {
        SingleRefOneParentDto result = SingleTransportMapper.convertToSingleRefOneParentDto(singleRefOneParent, rootDto);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefOneParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefOneParent.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, rootDto.getSingleRef(), "Wrong single ref at root");
    }

    @Test
    public void testConvertToSingleRefOneParentDtoNull() {
        assertNull(SingleTransportMapper.convertToSingleRefOneParentDto(null), "The result should be null");
    }

    @Test
    public void testConvertToSingleRefTwoParentsDto() {
        SingleRefTwoParentsDto result = SingleTransportMapper.convertToSingleRefTwoParentsDto(singleRefTwoParents);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefTwoParentsDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefTwoParentsDto.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToSingleRefTwoParentsDtoWithRootParent() {
        SingleRefTwoParentsDto result = SingleTransportMapper.convertToSingleRefTwoParentsDto(singleRefTwoParents, rootDto);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefTwoParents.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefTwoParents.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, rootDto.getAnotherSingleRef(), "Wrong another single ref at root");
    }

    @Test
    public void testConvertToSingleRefTwoParentsDtoWithOtherParent() {
        SingleRefTwoParentsDto result = SingleTransportMapper.convertToSingleRefTwoParentsDto(singleRefTwoParents, singleRefOneParentDto);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefTwoParents.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefTwoParents.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, singleRefOneParentDto.getSingleRef(), "Wrong single ref at root");
    }

    @Test
    public void testConvertToSingleRefTwoParentsDtoNull() {
        assertNull(SingleTransportMapper.convertToSingleRefTwoParentsDto(null), "The result should be null");
    }

    @Test
    public void testGetInstance() {
        SingleTransportMapper firstInstance = SingleTransportMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, SingleTransportMapper.getInstance(), "Any other instance should be the same");
    }
}
