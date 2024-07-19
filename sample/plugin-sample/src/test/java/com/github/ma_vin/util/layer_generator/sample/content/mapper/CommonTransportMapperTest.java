package com.github.ma_vin.util.layer_generator.sample.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.content.dao.PluginEntityDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.PluginEntity;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommonTransportMapperTest {
    private PluginEntity pluginEntity;
    private PluginEntityDao pluginEntityDao;

    @BeforeEach
    public void setUp() {
        pluginEntity = new PluginEntity();
        pluginEntityDao = new PluginEntityDao();

        pluginEntity.setIdentification(IdGenerator.generateIdentification(1L, PluginEntity.ID_PREFIX));
        pluginEntityDao.setIdentification(pluginEntity.getIdentification());

        pluginEntity.setExampleAttribute((short) 5);
        pluginEntityDao.setExampleAttribute(pluginEntity.getExampleAttribute());
    }

    @Test
    public void testConvertToPluginEntity() {
        PluginEntity result = CommonAccessMapper.convertToPluginEntity(pluginEntityDao);

        assertNotNull(result, "There should be any result");
        assertEquals(pluginEntity, result, "Wrong result");
    }

    @Test
    public void testConvertToPluginEntityDao() {
        PluginEntityDao result = CommonAccessMapper.convertToPluginEntityDao(pluginEntity);

        assertNotNull(result, "There should be any result");
        assertEquals(pluginEntityDao, result, "Wrong result");
    }
}
