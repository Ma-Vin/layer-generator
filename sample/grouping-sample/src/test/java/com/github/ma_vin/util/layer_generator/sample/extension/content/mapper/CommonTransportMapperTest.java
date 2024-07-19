package com.github.ma_vin.util.layer_generator.sample.extension.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.CommonEntity;
import com.github.ma_vin.util.layer_generator.sample.extension.content.dto.CommonEntityDto;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommonTransportMapperTest {
    private CommonEntity commonEntity;
    private CommonEntityDto commonEntityDto;

    @BeforeEach
    public void setUp() {
        commonEntity = new CommonEntity();
        commonEntityDto = new CommonEntityDto();

        commonEntity.setIdentification(IdGenerator.generateIdentification(1L, CommonEntity.ID_PREFIX));
        commonEntityDto.setIdentification(commonEntity.getIdentification());
    }

    @Test
    public void testConvertToSubEntity() {
        CommonEntity result = CommonTransportMapper.convertToCommonEntity(commonEntityDto);

        assertNotNull(result, "There should be any result");
        assertEquals(commonEntity, result, "Wrong result");
    }

    @Test
    public void testConvertToSubEntityDto() {
        CommonEntityDto result = CommonTransportMapper.convertToCommonEntityDto(commonEntity);

        assertNotNull(result, "There should be any result");
        assertEquals(commonEntityDto, result, "Wrong result");
    }
}
