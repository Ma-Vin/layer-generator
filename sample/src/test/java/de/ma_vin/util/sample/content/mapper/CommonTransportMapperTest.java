package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.RootExt;
import de.ma_vin.util.sample.content.dto.RootDto;
import de.ma_vin.util.sample.content.dto.RootExtDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.ma_vin.util.sample.content.ObjectFactory.*;
import static de.ma_vin.util.sample.content.ObjectFactory.getNextId;
import static org.junit.jupiter.api.Assertions.*;

public class CommonTransportMapperTest {

    private Root root;
    private RootExt rootExt;

    private RootDto rootDto;
    private RootExtDto rootExtDto;

    @BeforeEach
    public void setUp() {
        initObjectFactory();
        root = createRootWithChildren(getNextId());
        rootExt = createRootExt(getNextId());

        initObjectFactory();
        rootDto = createRootDtoWithChildren(getNextId());
        rootExtDto = createRootExtDto(getNextId());
    }

    @Test
    public void testConvertToRoot() {
        Root result = CommonTransportMapper.convertToRoot(rootDto);

        assertNotNull(result, "There should be any result");
        assertEquals(rootDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(rootDto.getDescription(), result.getDescription(), "Wrong description");

        assertNotNull(result.getSingleRef(), "There should be any SingleRef");
        assertEquals(rootDto.getSingleRef().getIdentification(), result.getSingleRef().getIdentification(), "Wrong identification at SingleRef");
        assertNotNull(result.getAnotherSingleRef(), "There should be any AnotherSingleRef");
        assertEquals(rootDto.getAnotherSingleRef().getIdentification(), result.getAnotherSingleRef().getIdentification(), "Wrong identification at AnotherSingleRef");
        assertNotNull(result.getSingleRefIndirectParent(), "There should be any SingleRefIndirectParent");
        assertEquals(rootDto.getSingleRefIndirectParent().getIdentification(), result.getSingleRefIndirectParent().getIdentification(), "Wrong identification at SingleRefIndirectParent");
        assertNotNull(result.getSingleRefIndirectOtherParent(), "There should be any SingleRefIndirectOtherParent");
        assertEquals(rootDto.getSingleRefIndirectOtherParent().getIdentification(), result.getSingleRefIndirectOtherParent().getIdentification(), "Wrong identification at SingleRefIndirectOtherParent");
        assertNotNull(result.getFiltering(), "There should be any Filtering");
        assertEquals(rootDto.getFiltering().getIdentification(), result.getFiltering().getIdentification(), "Wrong identification at Filtering");
        assertNotNull(result.getExt(), "There should be any RootExt");
        assertEquals(rootDto.getExt().getIdentification(), result.getExt().getIdentification(), "Wrong identification at RootExt");

        assertNotNull(result.getMultiRefs(), "There should be any MultiRefs list");
        assertEquals(0, result.getMultiRefs().size());
        assertNotNull(result.getAnotherMultiRefs(), "There should be any AnotherMultiRefs list");
        assertEquals(0, result.getAnotherMultiRefs().size());
        assertNotNull(result.getMultiRefIndirectParents(), "There should be any MultiRefIndirectParents list");
        assertEquals(0, result.getMultiRefIndirectParents().size());
        assertNotNull(result.getMultiRefIndirectOtherParents(), "There should be any MultiRefIndirectOtherParents list");
        assertEquals(0, result.getMultiRefIndirectOtherParents().size());
        assertNotNull(result.getExtendings(), "There should be any extending list");
        assertEquals(0, result.getExtendings().size());
    }

    @Test
    public void testConvertToRootNull() {
        assertNull(CommonTransportMapper.convertToRoot(null), "The result should be null");
    }

    @Test
    public void testCreateRootExt() {
        RootExt result = CommonTransportMapper.convertToRootExt(rootExtDto);

        assertNotNull(result, "There should be any result");
        assertEquals(rootExtDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(rootExtDto.getExtendedInfo(), result.getExtendedInfo(), "Wrong ExtendedInfo");
        assertEquals(rootExtDto.getSomeInteger(), result.getSomeInteger(), "Wrong SomeInteger");
        assertEquals(rootExtDto.getSomeCustom(), result.getSomeCustom(), "Wrong SomeCustom");
        assertEquals(rootExtDto.getDtoAndDomain(), result.getDtoAndDomain(), "Wrong DtoAndDomain");
        assertEquals(rootExtDto.getTextWithDaoInfo(), result.getTextWithDaoInfo(), "Wrong TextWithDaoInfo");
        assertEquals(rootExtDto.getNumberWithDaoInfo(), result.getNumberWithDaoInfo(), "Wrong NumberWithDaoInfo");
        assertEquals(rootExtDto.getDaoEnum(), result.getDaoEnum(), "Wrong DaoEnum");
        assertEquals(rootExtDto.getDaoEnumWithText(), result.getDaoEnumWithText(), "Wrong DaoEnumWithText");
        assertEquals(rootExtDto.getExtendedInfo(), result.getExtendedInfo(), "Wrong ExtendedInfo");
    }

    @Test
    public void testCreateRootExtWithParent() {
        RootExt result = CommonTransportMapper.convertToRootExt(rootExtDto, root);

        assertNotNull(result, "There should be any result");
        assertEquals(rootExtDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(rootExtDto.getExtendedInfo(), result.getExtendedInfo(), "Wrong ExtendedInfo");
        assertEquals(rootExtDto.getSomeInteger(), result.getSomeInteger(), "Wrong SomeInteger");
        assertEquals(rootExtDto.getSomeCustom(), result.getSomeCustom(), "Wrong SomeCustom");
        assertEquals(rootExtDto.getDtoAndDomain(), result.getDtoAndDomain(), "Wrong DtoAndDomain");
        assertEquals(rootExtDto.getTextWithDaoInfo(), result.getTextWithDaoInfo(), "Wrong TextWithDaoInfo");
        assertEquals(rootExtDto.getNumberWithDaoInfo(), result.getNumberWithDaoInfo(), "Wrong NumberWithDaoInfo");
        assertEquals(rootExtDto.getDaoEnum(), result.getDaoEnum(), "Wrong DaoEnum");
        assertEquals(rootExtDto.getDaoEnumWithText(), result.getDaoEnumWithText(), "Wrong DaoEnumWithText");
        assertEquals(rootExtDto.getExtendedInfo(), result.getExtendedInfo(), "Wrong ExtendedInfo");

        assertEquals(root.getExt(), result, "Wrong ext at root");
    }

    @Test
    public void testConvertToRootExtNull() {
        assertNull(CommonTransportMapper.convertToRootExt(null), "The result should be null");
    }

    @Test
    public void testConvertToRootDto() {
        RootDto result = CommonTransportMapper.convertToRootDto(root);

        assertNotNull(result, "There should be any result");
        assertEquals(root.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(root.getDescription(), result.getDescription(), "Wrong description");

        assertNotNull(result.getSingleRef(), "There should be any SingleRef");
        assertEquals(root.getSingleRef().getIdentification(), result.getSingleRef().getIdentification(), "Wrong identification at SingleRef");
        assertNotNull(result.getAnotherSingleRef(), "There should be any AnotherSingleRef");
        assertEquals(root.getAnotherSingleRef().getIdentification(), result.getAnotherSingleRef().getIdentification(), "Wrong identification at AnotherSingleRef");
        assertNotNull(result.getSingleRefIndirectParent(), "There should be any SingleRefIndirectParent");
        assertEquals(root.getSingleRefIndirectParent().getIdentification(), result.getSingleRefIndirectParent().getIdentification(), "Wrong identification at SingleRefIndirectParent");
        assertNotNull(result.getSingleRefIndirectOtherParent(), "There should be any SingleRefIndirectOtherParent");
        assertEquals(root.getSingleRefIndirectOtherParent().getIdentification(), result.getSingleRefIndirectOtherParent().getIdentification(), "Wrong identification at SingleRefIndirectOtherParent");
        assertNotNull(result.getFiltering(), "There should be any Filtering");
        assertEquals(root.getFiltering().getIdentification(), result.getFiltering().getIdentification(), "Wrong identification at Filtering");
        assertNotNull(result.getExt(), "There should be any RootExt");
        assertEquals(root.getExt().getIdentification(), result.getExt().getIdentification(), "Wrong identification at RootExt");
    }

    @Test
    public void testConvertToRootDtoNull() {
        assertNull(CommonTransportMapper.convertToRootDto(null), "The result should be null");
    }

    @Test
    public void testCreateRootExtDto() {
        RootExtDto result = CommonTransportMapper.convertToRootExtDto(rootExt);

        assertNotNull(result, "There should be any result");
        assertEquals(rootExt.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(rootExt.getExtendedInfo(), result.getExtendedInfo(), "Wrong ExtendedInfo");
        assertEquals(rootExt.getSomeInteger(), result.getSomeInteger(), "Wrong SomeInteger");
        assertEquals(rootExt.getSomeCustom(), result.getSomeCustom(), "Wrong SomeCustom");
        assertEquals(rootExt.getDtoAndDomain(), result.getDtoAndDomain(), "Wrong DtoAndDomain");
        assertEquals(rootExt.getTextWithDaoInfo(), result.getTextWithDaoInfo(), "Wrong TextWithDaoInfo");
        assertEquals(rootExt.getNumberWithDaoInfo(), result.getNumberWithDaoInfo(), "Wrong NumberWithDaoInfo");
        assertEquals(rootExt.getDaoEnum(), result.getDaoEnum(), "Wrong DaoEnum");
        assertEquals(rootExt.getDaoEnumWithText(), result.getDaoEnumWithText(), "Wrong DaoEnumWithText");
        assertEquals(rootExt.getExtendedInfo(), result.getExtendedInfo(), "Wrong ExtendedInfo");
    }

    @Test
    public void testConvertToRootDtoExtNull() {
        assertNull(CommonTransportMapper.convertToRootExtDto(null), "The result should be null");
    }

    @Test
    public void testGetInstance() {
        CommonTransportMapper firstInstance = CommonTransportMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, CommonTransportMapper.getInstance(), "Any other instance should be the same");
    }
}
