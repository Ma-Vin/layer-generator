package com.github.ma_vin.util.layer_generator.sample.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.content.domain.Root;
import com.github.ma_vin.util.layer_generator.sample.content.domain.RootExt;
import com.github.ma_vin.util.layer_generator.sample.content.domain.SingleRefOne;
import com.github.ma_vin.util.layer_generator.sample.content.domain.SingleRefTwo;
import com.github.ma_vin.util.layer_generator.sample.content.dto.*;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link com.github.ma_vin.util.layer_generator.sample.content.mapper.CommonTransportMapper} is the class under test
 */
public class CommonTransportMapperTest {

    public static final String DEFAULT_NAME = "Name";
    public static final String DEFAULT_DESCRIPTION = "Description";
    public static final String DEFAULT_EXTENDED_INFO = "ExtendedInfo";
    public static final String DEFAULT_ADDED_FIELD = "Added";
    public static final Integer DEFAULT_INTEGER_VALUE = 1;

    public static final String ROOT_IDENTIFICATION = IdGenerator.generateIdentification(1L, Root.ID_PREFIX);
    public static final String ROOT_EXT_IDENTIFICATION = IdGenerator.generateIdentification(2L, RootExt.ID_PREFIX);
    public static final String SINGLE_REF_ONE_IDENTIFICATION = IdGenerator.generateIdentification(3L, SingleRefOne.ID_PREFIX);
    public static final String SINGLE_REF_TWO_IDENTIFICATION = IdGenerator.generateIdentification(4L, SingleRefTwo.ID_PREFIX);

    private Root root;
    private RootExt rootExt;
    private SingleRefOne singleRefOne;
    private SingleRefTwo singleRefTwo;

    private RootDto rootDto;
    private RootV1Dto rootV1Dto;
    private RootV2Dto rootV2Dto;
    private RootExtDto rootExtDto;
    private RootExtV1Dto rootExtV1Dto;
    private SingleRefOneDto singleRefOneDto;
    private SingleRefTwoDto singleRefTwoDto;


    @BeforeEach
    public void setUp() {
        createDomainObjects();
        setDomainReferences();

        createDtoObjects();
        setDtoReferences();
    }


    private void createDomainObjects() {
        root = new Root();
        root.setRootName(DEFAULT_NAME);
        root.setDescription(DEFAULT_DESCRIPTION);
        root.setIdentification(ROOT_IDENTIFICATION);

        rootExt = new RootExt();
        rootExt.setExtendedInfo(DEFAULT_EXTENDED_INFO);
        rootExt.setSomeInteger(DEFAULT_INTEGER_VALUE);
        rootExt.setIdentification(ROOT_EXT_IDENTIFICATION);

        singleRefOne = new SingleRefOne();
        singleRefOne.setIdentification(SINGLE_REF_ONE_IDENTIFICATION);

        singleRefTwo = new SingleRefTwo();
        singleRefTwo.setIdentification(SINGLE_REF_TWO_IDENTIFICATION);
    }

    private void setDomainReferences() {
        root.setExt(rootExt);
        root.setSingleRef(singleRefOne);
        root.setAnotherSingleRef(singleRefTwo);
    }


    private void createDtoObjects() {
        rootDto = new RootDto();
        rootDto.setRootName(DEFAULT_NAME);
        rootDto.setDescription(DEFAULT_DESCRIPTION);
        rootDto.setIdentification(ROOT_IDENTIFICATION);

        rootV1Dto = new RootV1Dto();
        rootV1Dto.setRootName(DEFAULT_NAME);
        rootV1Dto.setAddedField(DEFAULT_ADDED_FIELD);
        rootV1Dto.setIdentification(ROOT_IDENTIFICATION);

        rootV2Dto = new RootV2Dto();
        rootV2Dto.setRootName(DEFAULT_NAME);
        rootV2Dto.setIdentification(ROOT_IDENTIFICATION);

        rootExtDto = new RootExtDto();
        rootExtDto.setExtendedInfo(DEFAULT_EXTENDED_INFO);
        rootExtDto.setSomeInteger(DEFAULT_INTEGER_VALUE);
        rootExtDto.setIdentification(ROOT_EXT_IDENTIFICATION);

        rootExtV1Dto = new RootExtV1Dto();
        rootExtV1Dto.setExtendedInfo(DEFAULT_EXTENDED_INFO);
        rootExtV1Dto.setIdentification(ROOT_EXT_IDENTIFICATION);

        singleRefOneDto = new SingleRefOneDto();
        singleRefOneDto.setIdentification(SINGLE_REF_ONE_IDENTIFICATION);

        singleRefTwoDto = new SingleRefTwoDto();
        singleRefTwoDto.setIdentification(SINGLE_REF_TWO_IDENTIFICATION);
    }

    private void setDtoReferences() {
        rootDto.setExt(rootExtDto);
        rootDto.setSingleRef(singleRefOneDto);
        rootDto.setAnotherSingleRef(singleRefTwoDto);

        rootV1Dto.setExtNew(rootExtV1Dto);
        rootV1Dto.setAnotherSingleRef(singleRefTwoDto);

        rootV2Dto.setExtNew(rootExtV1Dto);
        rootV2Dto.setAnotherSingleRef(singleRefTwoDto);
    }


    @Test
    public void testConvertToRoot() {
        Root result = CommonTransportMapper.convertToRoot(rootDto);

        assertNotNull(result, "There should be a result");
        assertEquals(DEFAULT_NAME, result.getRootName(), "Wrong name");
        assertEquals(DEFAULT_DESCRIPTION, result.getDescription(), "Wrong description");
        assertEquals(ROOT_IDENTIFICATION, result.getIdentification(), "Wrong identification");

        assertNotNull(result.getSingleRef(), "There should be a single reg");
        assertNotNull(result.getAnotherSingleRef(), "There should be a second single reg");
        assertNotNull(result.getExt(), "There should be an ext");
    }


    @Test
    public void testConvertToRootFromV1() {
        Root result = CommonTransportMapper.convertToRoot(rootV1Dto);

        assertNotNull(result, "There should be a result");
        assertEquals(DEFAULT_NAME, result.getRootName(), "Wrong name");
        assertNull(result.getDescription(), "Wrong description");
        assertEquals(ROOT_IDENTIFICATION, result.getIdentification(), "Wrong identification");

        assertNull(result.getSingleRef(), "There should not be any single reg");
        assertNotNull(result.getAnotherSingleRef(), "There should be any second single reg");

        assertNull(result.getExt(), "There should not be an ext, because of a different property name at RootV1Dto");
    }


    @Test
    public void testConvertToRootFromV2() {
        Root result = CommonTransportMapper.convertToRoot(rootV2Dto);

        assertNotNull(result, "There should be a result");
        assertEquals(DEFAULT_NAME, result.getRootName(), "Wrong name");
        assertNull(result.getDescription(), "Wrong description");
        assertEquals(ROOT_IDENTIFICATION, result.getIdentification(), "Wrong identification");

        assertNull(result.getSingleRef(), "There should not be any single reg");
        assertNotNull(result.getAnotherSingleRef(), "There should be any second single reg");

        assertNull(result.getExt(), "There should not be an ext, because of a different property name at RootV2Dto");
    }


    @Test
    public void testConvertToRootDto() {
        RootDto result = CommonTransportMapper.convertToRootDto(root);

        assertNotNull(result, "There should be a result");
        assertEquals(DEFAULT_NAME, result.getRootName(), "Wrong name");
        assertEquals(DEFAULT_DESCRIPTION, result.getDescription(), "Wrong description");
        assertEquals(ROOT_IDENTIFICATION, result.getIdentification(), "Wrong identification");

        assertNotNull(result.getSingleRef(), "There should be a single reg");
        assertNotNull(result.getAnotherSingleRef(), "There should be a second single reg");
        assertNotNull(result.getExt(), "There should be an ext");
    }


    @Test
    public void testConvertToRootV1Dto() {
        RootV1Dto result = CommonTransportMapper.convertToRootV1Dto(root);

        assertNotNull(result, "There should be a result");
        assertEquals(DEFAULT_NAME, result.getRootName(), "Wrong name");
        assertEquals(ROOT_IDENTIFICATION, result.getIdentification(), "Wrong identification");

        assertNotNull(result.getAnotherSingleRef(), "There should be any second single reg");

        assertNull(result.getExtNew(), "There should be an ext, because of a different property name at Root");
    }


    @Test
    public void testConvertToRootV2Dto() {
        RootV2Dto result = CommonTransportMapper.convertToRootV2Dto(root);

        assertNotNull(result, "There should be a result");
        assertEquals(DEFAULT_NAME, result.getRootName(), "Wrong name");
        assertEquals(ROOT_IDENTIFICATION, result.getIdentification(), "Wrong identification");

        assertNotNull(result.getAnotherSingleRef(), "There should be any second single reg");

        assertNull(result.getExtNew(), "There should be an ext, because of a different property name at Root");
    }

    @Test
    public void testConvertToRootExt() {
        RootExt result = CommonTransportMapper.convertToRootExt(rootExtDto);

        assertNotNull(result, "There should be a result");
        assertEquals(DEFAULT_EXTENDED_INFO, result.getExtendedInfo(), "Wrong extended info");
        assertEquals(DEFAULT_INTEGER_VALUE, result.getSomeInteger(), "Wrong integer value");
        assertEquals(ROOT_EXT_IDENTIFICATION, result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToRootExtFromV1() {
        RootExt result = CommonTransportMapper.convertToRootExt(rootExtV1Dto);

        assertNotNull(result, "There should be a result");
        assertEquals(DEFAULT_EXTENDED_INFO, result.getExtendedInfo(), "Wrong extended info");
        assertNull(result.getSomeInteger(), "integer value should be null");
        assertEquals(ROOT_EXT_IDENTIFICATION, result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToRootExtWithParent() {
        root.setExt(null);
        RootExt result = CommonTransportMapper.convertToRootExt(rootExtDto, root);

        assertNotNull(result, "There should be a result");
        assertEquals(DEFAULT_EXTENDED_INFO, result.getExtendedInfo(), "Wrong extended info");
        assertEquals(DEFAULT_INTEGER_VALUE, result.getSomeInteger(), "Wrong integer value");
        assertEquals(ROOT_EXT_IDENTIFICATION, result.getIdentification(), "Wrong identification");

        assertNotNull(root.getExt(), "the reference to root ext should not be null");
        assertEquals(result, root.getExt(), "Wrong root ext");
    }

    @Test
    public void testConvertToRootExtDto() {
        RootExtDto result = CommonTransportMapper.convertToRootExtDto(rootExt);

        assertNotNull(result, "There should be a result");
        assertEquals(DEFAULT_EXTENDED_INFO, result.getExtendedInfo(), "Wrong extended info");
        assertEquals(DEFAULT_INTEGER_VALUE, result.getSomeInteger(), "Wrong integer value");
        assertEquals(ROOT_EXT_IDENTIFICATION, result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToRootExtV1Dto() {
        RootExtV1Dto result = CommonTransportMapper.convertToRootExtV1Dto(rootExt);

        assertNotNull(result, "There should be a result");
        assertEquals(DEFAULT_EXTENDED_INFO, result.getExtendedInfo(), "Wrong extended info");
        assertEquals(ROOT_EXT_IDENTIFICATION, result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToRootExtDtoWithParent() {
        rootDto.setExt(null);
        RootExtDto result = CommonTransportMapper.convertToRootExtDto(rootExt, rootDto);

        assertNotNull(result, "There should be a result");
        assertEquals(DEFAULT_EXTENDED_INFO, result.getExtendedInfo(), "Wrong extended info");
        assertEquals(DEFAULT_INTEGER_VALUE, result.getSomeInteger(), "Wrong integer value");
        assertEquals(ROOT_EXT_IDENTIFICATION, result.getIdentification(), "Wrong identification");

        assertNotNull(rootDto.getExt(), "the reference to root ext should not be null");
        assertEquals(result, rootDto.getExt(), "Wrong root ext");
    }


    @Test
    public void testConvertToRootExtV1DtoWithV1Parent() {
        rootV1Dto.setExtNew(null);
        RootExtV1Dto result = CommonTransportMapper.convertToRootExtV1Dto(rootExt, rootV1Dto);

        assertNotNull(result, "There should be a result");
        assertEquals(DEFAULT_EXTENDED_INFO, result.getExtendedInfo(), "Wrong extended info");
        assertEquals(ROOT_EXT_IDENTIFICATION, result.getIdentification(), "Wrong identification");

        assertNotNull(rootV1Dto.getExtNew(), "the reference to root ext should not be null");
        assertEquals(result, rootV1Dto.getExtNew(), "Wrong root ext");

    }

    @Test
    public void testConvertToRootExtV1DtoWithV2Parent() {
        rootV2Dto.setExtNew(null);
        RootExtV1Dto result = CommonTransportMapper.convertToRootExtV1Dto(rootExt, rootV2Dto);

        assertNotNull(result, "There should be a result");
        assertEquals(DEFAULT_EXTENDED_INFO, result.getExtendedInfo(), "Wrong extended info");
        assertEquals(ROOT_EXT_IDENTIFICATION, result.getIdentification(), "Wrong identification");

        assertNotNull(rootV2Dto.getExtNew(), "the reference to root ext should not be null");
        assertEquals(result, rootV2Dto.getExtNew(), "Wrong root ext");
    }

    @Test
    public void testConvertToSingleRefOne() {
        SingleRefOne result = CommonTransportMapper.convertToSingleRefOne(singleRefOneDto);

        assertNotNull(result, "There should be a result");
        assertEquals(SINGLE_REF_ONE_IDENTIFICATION, result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToSingleRefOneWithParent() {
        root.setSingleRef(null);
        SingleRefOne result = CommonTransportMapper.convertToSingleRefOne(singleRefOneDto, root);

        assertNotNull(result, "There should be a result");
        assertEquals(SINGLE_REF_ONE_IDENTIFICATION, result.getIdentification(), "Wrong identification");

        assertNotNull(root.getSingleRef(), "the reference to single ref one should not be null");
        assertEquals(result, root.getSingleRef(), "Wrong single ref one");
    }

    @Test
    public void testConvertToSingleRefOneDto() {
        SingleRefOneDto result = CommonTransportMapper.convertToSingleRefOneDto(singleRefOne);

        assertNotNull(result, "There should be a result");
        assertEquals(SINGLE_REF_ONE_IDENTIFICATION, result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToSingleRefOneWithDtoParent() {
        rootDto.setSingleRef(null);
        SingleRefOneDto result = CommonTransportMapper.convertToSingleRefOneDto(singleRefOne, rootDto);

        assertNotNull(result, "There should be a result");
        assertEquals(SINGLE_REF_ONE_IDENTIFICATION, result.getIdentification(), "Wrong identification");

        assertNotNull(rootDto.getSingleRef(), "the reference to single ref one should not be null");
        assertEquals(result, rootDto.getSingleRef(), "Wrong single ref one");
    }

    @Test
    public void testConvertToSingleRefTwo() {
        SingleRefTwo result = CommonTransportMapper.convertToSingleRefTwo(singleRefTwoDto);

        assertNotNull(result, "There should be a result");
        assertEquals(SINGLE_REF_TWO_IDENTIFICATION, result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToSingleRefTwoWithParent() {
        root.setAnotherSingleRef(null);
        SingleRefTwo result = CommonTransportMapper.convertToSingleRefTwo(singleRefTwoDto, root);

        assertNotNull(result, "There should be a result");
        assertEquals(SINGLE_REF_TWO_IDENTIFICATION, result.getIdentification(), "Wrong identification");

        assertNotNull(root.getAnotherSingleRef(), "the reference to single ref one should not be null");
        assertEquals(result, root.getAnotherSingleRef(), "Wrong single ref one");
    }

    @Test
    public void testConvertToSingleRefTwoDto() {
        SingleRefTwoDto result = CommonTransportMapper.convertToSingleRefTwoDto(singleRefTwo);

        assertNotNull(result, "There should be a result");
        assertEquals(SINGLE_REF_TWO_IDENTIFICATION, result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToSingleRefTwoWithDtoParent() {
        rootDto.setAnotherSingleRef(null);
        SingleRefTwoDto result = CommonTransportMapper.convertToSingleRefTwoDto(singleRefTwo, rootDto);

        assertNotNull(result, "There should be a result");
        assertEquals(SINGLE_REF_TWO_IDENTIFICATION, result.getIdentification(), "Wrong identification");

        assertNotNull(rootDto.getAnotherSingleRef(), "the reference to single ref one should not be null");
        assertEquals(result, rootDto.getAnotherSingleRef(), "Wrong single ref one");
    }

    @Test
    public void testConvertToSingleRefTwoWithDtoV1Parent() {
        rootV1Dto.setAnotherSingleRef(null);
        SingleRefTwoDto result = CommonTransportMapper.convertToSingleRefTwoDto(singleRefTwo, rootV1Dto);

        assertNotNull(result, "There should be a result");
        assertEquals(SINGLE_REF_TWO_IDENTIFICATION, result.getIdentification(), "Wrong identification");

        assertNotNull(rootV1Dto.getAnotherSingleRef(), "the reference to single ref one should not be null");
        assertEquals(result, rootV1Dto.getAnotherSingleRef(), "Wrong single ref one");
    }

    @Test
    public void testConvertToSingleRefTwoWithDtoV2Parent() {
        rootV2Dto.setAnotherSingleRef(null);
        SingleRefTwoDto result = CommonTransportMapper.convertToSingleRefTwoDto(singleRefTwo, rootV2Dto);

        assertNotNull(result, "There should be a result");
        assertEquals(SINGLE_REF_TWO_IDENTIFICATION, result.getIdentification(), "Wrong identification");

        assertNotNull(rootV2Dto.getAnotherSingleRef(), "the reference to single ref one should not be null");
        assertEquals(result, rootV2Dto.getAnotherSingleRef(), "Wrong single ref one");
    }
}
