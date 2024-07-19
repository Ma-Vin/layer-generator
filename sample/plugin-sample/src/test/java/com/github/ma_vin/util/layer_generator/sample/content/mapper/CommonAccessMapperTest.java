package com.github.ma_vin.util.layer_generator.sample.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.content.domain.PluginEntity;
import com.github.ma_vin.util.layer_generator.sample.content.dto.PluginEntityDto;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommonAccessMapperTest {
    private PluginEntity pluginEntity;
    private PluginEntityDto pluginEntityDto;

    @BeforeEach
    public void setUp() {
        pluginEntity = new PluginEntity();
        pluginEntityDto = new PluginEntityDto();

        pluginEntity.setIdentification(IdGenerator.generateIdentification(1L, PluginEntity.ID_PREFIX));
        pluginEntityDto.setIdentification(pluginEntity.getIdentification());

        pluginEntity.setExampleAttribute((short) 5);
        pluginEntityDto.setExampleAttribute(pluginEntity.getExampleAttribute());
    }

    @Test
    public void testConvertToPluginEntity() {
        PluginEntity result = CommonTransportMapper.convertToPluginEntity(pluginEntityDto);

        assertNotNull(result, "There should be any result");
        assertEquals(pluginEntity, result, "Wrong result");
    }

    @Test
    public void testConvertToPluginEntityDto() {
        PluginEntityDto result = CommonTransportMapper.convertToPluginEntityDto(pluginEntity);

        assertNotNull(result, "There should be any result");
        assertEquals(pluginEntityDto, result, "Wrong result");
    }
}
