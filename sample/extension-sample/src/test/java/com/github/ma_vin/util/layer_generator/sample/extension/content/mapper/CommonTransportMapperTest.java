package com.github.ma_vin.util.layer_generator.sample.extension.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.extension.ExtendedEntity;
import com.github.ma_vin.util.layer_generator.sample.extension.ExtendedEntityDto;
import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.ToExtendEntity;
import com.github.ma_vin.util.layer_generator.sample.extension.content.dto.ToExtendEntityDto;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommonTransportMapperTest {

    private ExtendedEntity extendedEntity;
    private ExtendedEntityDto extendedEntityDto;

    @BeforeEach
    public void setUp() {
        extendedEntity = new ExtendedEntity();
        extendedEntityDto = new ExtendedEntityDto();

        extendedEntity.setIdentification(IdGenerator.generateIdentification(1L, ToExtendEntity.ID_PREFIX));
        extendedEntity.setExistingAttribute("Existing");
        extendedEntity.setAddedToDomainBigDecimal(BigDecimal.ONE);

        extendedEntityDto.setIdentification(IdGenerator.generateIdentification(1L, ToExtendEntity.ID_PREFIX));
        extendedEntityDto.setExistingAttribute("Existing");
        extendedEntityDto.setAddedToDtoBigDecimal(BigDecimal.ONE);
    }

    @Test
    public void testConvertToToExtendEntity() {
        ToExtendEntity result = CommonTransportMapper.convertToToExtendEntity(extendedEntityDto);

        assertNotNull(result, "There should be any result");
        assertEquals(extendedEntity, result, "Wrong result");
    }

    @Test
    public void testConvertToToExtendEntityDto() {
        ToExtendEntityDto result = CommonTransportMapper.convertToToExtendEntityDto(extendedEntity);

        assertNotNull(result, "There should be any result");
        assertEquals(extendedEntityDto, result, "Wrong result");
    }
}
