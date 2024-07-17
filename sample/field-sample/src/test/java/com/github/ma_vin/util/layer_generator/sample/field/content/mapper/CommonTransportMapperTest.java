package com.github.ma_vin.util.layer_generator.sample.field.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.field.content.domain.FieldEntity;
import com.github.ma_vin.util.layer_generator.sample.field.content.dto.FieldEntityDto;
import com.github.ma_vin.util.layer_generator.sample.given.AnyEnumType;
import com.github.ma_vin.util.layer_generator.sample.given.CustomType;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommonTransportMapperTest {
    private FieldEntity fieldEntity;
    private FieldEntityDto fieldEntityDto;

    @BeforeEach
    public void setUp() {
        fieldEntity = new FieldEntity();
        fieldEntity.setIdentification(IdGenerator.generateIdentification(1L, FieldEntity.ID_PREFIX));
        fieldEntity.setDaoAndDomain("not a dto field");
        fieldEntity.setDaoEnum(AnyEnumType.ENUM_VALUE_A);
        fieldEntity.setDaoEnumWithText(AnyEnumType.ENUM_VALUE_B);
        fieldEntity.setDocument("some document text");
        fieldEntity.setDtoAndDomain("dto domain");
        fieldEntity.setOnlyDomain("only domain");
        fieldEntity.setNumberWithDaoInfo(42.1);
        fieldEntity.setSomeCustom(new CustomType("anyThing"));
        fieldEntity.setSomeEnum(AnyEnumType.ENUM_VALUE_C);
        fieldEntity.setSomeInteger(4711);
        fieldEntity.setSomeName("otherColumnName");
        fieldEntity.setSomeString("anything");
        fieldEntity.setTextWithDaoInfo("a length limited column");

        fieldEntityDto = new FieldEntityDto();
        fieldEntityDto.setIdentification(fieldEntity.getIdentification());
        fieldEntityDto.setDaoEnum(fieldEntity.getDaoEnum());
        fieldEntityDto.setDaoEnumWithText(fieldEntity.getDaoEnumWithText());
        fieldEntityDto.setDocument(fieldEntity.getDocument());
        fieldEntityDto.setDtoAndDomain(fieldEntity.getDaoAndDomain());
        fieldEntityDto.setNumberWithDaoInfo(fieldEntity.getNumberWithDaoInfo());
        fieldEntityDto.setOnlyDto("only dto");
        fieldEntityDto.setSomeCustom(fieldEntity.getSomeCustom());
        fieldEntityDto.setSomeEnum(fieldEntity.getSomeEnum());
        fieldEntityDto.setSomeInteger(fieldEntity.getSomeInteger());
        fieldEntityDto.setSomeName(fieldEntity.getSomeName());
        fieldEntityDto.setSomeString(fieldEntity.getSomeString());
        fieldEntityDto.setTextWithDaoInfo(fieldEntity.getTextWithDaoInfo());
    }

    @Test
    public void testConvertToFieldEntity() {
        FieldEntity result = CommonTransportMapper.convertToFieldEntity(fieldEntityDto);

        assertNotNull(result, "There should be any result");
        assertNull(result.getOnlyDomain(), "onlyDomain should be null");
        assertNull(result.getDaoAndDomain(), "dtoAndDomain should be null");
        assertEquals(fieldEntityDto.getDaoEnum(), result.getDaoEnum(), "Wrong daoEnum");
        assertEquals(fieldEntityDto.getDaoEnumWithText(), result.getDaoEnumWithText(), "Wrong DaoEnumWithText");
        assertEquals(fieldEntityDto.getDocument(), result.getDocument(), "Wrong Document");
        assertEquals(fieldEntityDto.getDtoAndDomain(), result.getDtoAndDomain(), "Wrong DtoAndDomain");
        assertEquals(fieldEntityDto.getNumberWithDaoInfo(), result.getNumberWithDaoInfo(), "Wrong NumberWithDaoInfo");
        assertEquals(fieldEntityDto.getSomeCustom(), result.getSomeCustom(), "Wrong SomeCustom");
        assertEquals(fieldEntityDto.getSomeEnum(), result.getSomeEnum(), "Wrong SomeEnum");
        assertEquals(fieldEntityDto.getSomeInteger(), result.getSomeInteger(), "Wrong SomeInteger");
        assertEquals(fieldEntityDto.getSomeName(), result.getSomeName(), "Wrong SomeName");
        assertEquals(fieldEntityDto.getSomeString(), result.getSomeString(), "Wrong SomeString");
        assertEquals(fieldEntityDto.getTextWithDaoInfo(), result.getTextWithDaoInfo(), "Wrong TextWithDaoInfo");
    }

    @Test
    public void testConvertToFieldEntityDao() {
        FieldEntityDto result = CommonTransportMapper.convertToFieldEntityDto(fieldEntity);

        assertNotNull(result, "There should be any result");
        assertNull(result.getOnlyDto(), "onlyDto should be null");
        assertEquals(fieldEntity.getDaoEnum(), result.getDaoEnum(), "Wrong DaoEnum");
        assertEquals(fieldEntity.getDaoEnumWithText(), result.getDaoEnumWithText(), "Wrong DaoEnumWithText");
        assertEquals(fieldEntity.getDocument(), result.getDocument(), "Wrong Document");
        assertEquals(fieldEntity.getDtoAndDomain(), result.getDtoAndDomain(), "Wrong DtoAndDomain");
        assertEquals(fieldEntity.getNumberWithDaoInfo(), result.getNumberWithDaoInfo(), "Wrong NumberWithDaoInfo");
        assertEquals(fieldEntity.getSomeCustom(), result.getSomeCustom(), "Wrong SomeCustom");
        assertEquals(fieldEntity.getSomeEnum(), result.getSomeEnum(), "Wrong SomeEnum");
        assertEquals(fieldEntity.getSomeInteger(), result.getSomeInteger(), "Wrong SomeInteger");
        assertEquals(fieldEntity.getSomeName(), result.getSomeName(), "Wrong SomeName");
        assertEquals(fieldEntity.getSomeString(), result.getSomeString(), "Wrong SomeString");
        assertEquals(fieldEntity.getTextWithDaoInfo(), result.getTextWithDaoInfo(), "Wrong TextWithDaoInfo");
    }
}
