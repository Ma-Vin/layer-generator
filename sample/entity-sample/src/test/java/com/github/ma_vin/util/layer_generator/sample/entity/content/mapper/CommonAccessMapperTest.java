package com.github.ma_vin.util.layer_generator.sample.entity.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.entity.content.dao.ExtendingEntityDao;
import com.github.ma_vin.util.layer_generator.sample.entity.content.dao.RootEntityDao;
import com.github.ma_vin.util.layer_generator.sample.entity.content.domain.ExtendingEntity;
import com.github.ma_vin.util.layer_generator.sample.entity.content.domain.RootEntity;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommonAccessMapperTest {

    private RootEntityDao rootEntityDao;
    private RootEntity rootEntity;
    private ExtendingEntityDao extendingEntityDao;
    private ExtendingEntity extendingEntity;

    @BeforeEach
    public void setUp() {
        rootEntity = new RootEntity();
        rootEntityDao = new RootEntityDao();

        rootEntity.setRootName("RootName");
        rootEntity.setDescription("anything");
        rootEntity.setIdentification(IdGenerator.generateIdentification(1L, RootEntity.ID_PREFIX));

        rootEntityDao.setRootName(rootEntity.getRootName());
        rootEntityDao.setDescription(rootEntity.getDescription());
        rootEntityDao.setId(1L);

        extendingEntity = new ExtendingEntity();
        extendingEntityDao = new ExtendingEntityDao();

        extendingEntity.setSuperName("ExtendingName");
        extendingEntity.setAddedField(2);
        extendingEntity.setIdentification(IdGenerator.generateIdentification(3L, ExtendingEntity.ID_PREFIX));

        extendingEntityDao.setSuperName(extendingEntity.getSuperName());
        extendingEntityDao.setAddedField(extendingEntity.getAddedField());
        extendingEntityDao.setId(3L);
    }

    @Test
    public void testConvertToRoot() {
        RootEntity result = CommonAccessMapper.convertToRootEntity(rootEntityDao);

        assertNotNull(result, "There should be any result");
        assertEquals(rootEntityDao.getRootName(), result.getRootName(), "Wrong name");
        assertEquals(rootEntityDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(rootEntityDao.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToExtending() {
        ExtendingEntity result = CommonAccessMapper.convertToExtendingEntity(extendingEntityDao);

        assertNotNull(result, "There should be any result");
        assertEquals(extendingEntityDao.getSuperName(), result.getSuperName(), "Wrong name");
        assertEquals(extendingEntityDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(extendingEntityDao.getAddedField(), result.getAddedField(), "Wrong added field");
    }

    @Test
    public void testConvertToRootDao() {
        RootEntityDao result = CommonAccessMapper.convertToRootEntityDao(rootEntity);

        assertEquals(rootEntity.getRootName(), result.getRootName(), "Wrong name");
        assertEquals(rootEntity.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(rootEntity.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToExtendingDao() {
        ExtendingEntityDao result = CommonAccessMapper.convertToExtendingEntityDao(extendingEntity);

        assertNotNull(result, "There should be any result");
        assertEquals(extendingEntity.getSuperName(), result.getSuperName(), "Wrong name");
        assertEquals(extendingEntity.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(extendingEntity.getAddedField(), result.getAddedField(), "Wrong added field");
    }
}
