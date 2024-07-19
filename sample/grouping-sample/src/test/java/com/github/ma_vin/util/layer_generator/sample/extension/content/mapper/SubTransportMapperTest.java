package com.github.ma_vin.util.layer_generator.sample.extension.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.sub.SubEntity;
import com.github.ma_vin.util.layer_generator.sample.extension.content.dto.sub.SubEntityDto;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SubTransportMapperTest {

    private SubEntity subEntity;
    private SubEntityDto subEntityDto;

    @BeforeEach
    public void setUp() {
        subEntity = new SubEntity();
        subEntityDto = new SubEntityDto();

        subEntity.setIdentification(IdGenerator.generateIdentification(1L, SubEntity.ID_PREFIX));
        subEntityDto.setIdentification(subEntity.getIdentification());
    }

    @Test
    public void testConvertToSubEntity() {
        SubEntity result = SubTransportMapper.convertToSubEntity(subEntityDto);

        assertNotNull(result, "There should be any result");
        assertEquals(subEntity, result, "Wrong result");
    }

    @Test
    public void testConvertToSubEntityDto() {
        SubEntityDto result = SubTransportMapper.convertToSubEntityDto(subEntity);

        assertNotNull(result, "There should be any result");
        assertEquals(subEntityDto, result, "Wrong result");
    }
}
