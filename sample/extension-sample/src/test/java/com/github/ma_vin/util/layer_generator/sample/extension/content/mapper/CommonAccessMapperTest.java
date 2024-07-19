package com.github.ma_vin.util.layer_generator.sample.extension.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.extension.ExtendedEntity;
import com.github.ma_vin.util.layer_generator.sample.extension.ExtendedEntityDao;
import com.github.ma_vin.util.layer_generator.sample.extension.content.dao.ToExtendEntityDao;
import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.ToExtendEntity;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommonAccessMapperTest {
    private ExtendedEntity extendedEntity;
    private ExtendedEntityDao extendedEntityDao;

    @BeforeEach
    public void setUp() {
        extendedEntity = new ExtendedEntity();
        extendedEntityDao = new ExtendedEntityDao();

        extendedEntity.setIdentification(IdGenerator.generateIdentification(1L, ToExtendEntity.ID_PREFIX));
        extendedEntity.setExistingAttribute("Existing");
        extendedEntity.setAddedToDomainBigDecimal(BigDecimal.ONE);

        extendedEntityDao.setIdentification(IdGenerator.generateIdentification(1L, ToExtendEntity.ID_PREFIX));
        extendedEntityDao.setExistingAttribute("Existing");
        extendedEntityDao.setAddedToDaoBigDecimal(BigDecimal.ONE);
    }

    @Test
    public void testConvertToToExtendEntity() {
        ToExtendEntity result = CommonAccessMapper.convertToToExtendEntity(extendedEntityDao);

        assertNotNull(result, "There should be any result");
        assertEquals(extendedEntity, result, "Wrong result");
    }

    @Test
    public void testConvertToToExtendEntityDao() {
        ToExtendEntityDao result = CommonAccessMapper.convertToToExtendEntityDao(extendedEntity);

        assertNotNull(result, "There should be any result");
        assertEquals(extendedEntityDao, result, "Wrong result");
    }
}
