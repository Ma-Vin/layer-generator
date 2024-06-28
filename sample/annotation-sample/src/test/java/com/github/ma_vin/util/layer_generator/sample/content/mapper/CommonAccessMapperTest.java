package com.github.ma_vin.util.layer_generator.sample.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.content.dao.RootEntityDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.SubEntityDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.RootEntity;
import com.github.ma_vin.util.layer_generator.sample.content.domain.SubEntity;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Generated {@link CommonAccessMapper} is under test
 */
public class CommonAccessMapperTest {

    private RootEntityDao rootEntityDao;
    private SubEntityDao subEntityDao;

    private RootEntity rootEntity;
    private SubEntity subEntity;

    @BeforeEach
    public void setUp() {
        rootEntityDao = new RootEntityDao();
        rootEntityDao.setRootName("DummyName");
        rootEntityDao.setDescription("DummyDescription");
        rootEntityDao.setId(1L);

        rootEntity = new RootEntity();
        rootEntity.setRootName("DummyName");
        rootEntity.setDescription("DummyDescription");
        rootEntity.setIdentification(IdGenerator.generateIdentification(1L, RootEntity.ID_PREFIX));

        subEntityDao = new SubEntityDao();
        subEntityDao.setSubName("SubDummyName");
        subEntityDao.setDescription("SubDummyDescription");
        subEntityDao.setId(2L);
        subEntityDao.setParentRootEntity(rootEntityDao);
        rootEntityDao.setSubEntities(Collections.singleton(subEntityDao));

        subEntity = new SubEntity();
        subEntity.setSubName("SubDummyName");
        subEntity.setDescription("SubDummyDescription");
        subEntity.setIdentification(IdGenerator.generateIdentification(2L, SubEntity.ID_PREFIX));
        rootEntity.addSubEntities(subEntity);
    }

    @Test
    public void testConvertToRootEntity() {
        RootEntity result = CommonAccessMapper.convertToRootEntity(rootEntityDao, false);

        assertEquals(rootEntityDao.getRootName(), result.getRootName(), "wrong name");
        assertEquals(rootEntityDao.getDescription(), result.getDescription(), "wrong description");
        assertNotNull(result.getSubEntities(), "There should be a list of sub entities");
        assertEquals(0, result.getSubEntities().size(), "Wrong number of sub entities");
    }

    @Test
    public void testConvertToRootDaoEntity() {
        RootEntityDao result = CommonAccessMapper.convertToRootEntityDao(rootEntity, false);

        assertEquals(rootEntity.getRootName(), result.getRootName(), "wrong name");
        assertEquals(rootEntity.getDescription(), result.getDescription(), "wrong description");
        assertNotNull(result.getSubEntities(), "There should be a list of sub entities");
        assertEquals(0, result.getSubEntities().size(), "Wrong number of sub entities");
    }

    @Test
    public void testConvertToRootEntityWithChildren() {
        RootEntity result = CommonAccessMapper.convertToRootEntity(rootEntityDao, true);

        assertEquals(rootEntityDao.getRootName(), result.getRootName(), "wrong name");
        assertEquals(rootEntityDao.getDescription(), result.getDescription(), "wrong description");
        assertNotNull(result.getSubEntities(), "There should be a list of sub entities");
        assertEquals(1, result.getSubEntities().size(), "Wrong number of sub entities");
        SubEntity subResult = result.getSubEntities().iterator().next();
        assertEquals(subEntityDao.getSubName(), subResult.getSubName(), "wrong name at sub entity");
        assertEquals(subEntityDao.getDescription(), subResult.getDescription(), "wrong description at sub entity");
    }

    @Test
    public void testConvertToRootDaoEntityWithChildren() {
        RootEntityDao result = CommonAccessMapper.convertToRootEntityDao(rootEntity, true);

        assertEquals(rootEntity.getRootName(), result.getRootName(), "wrong name");
        assertEquals(rootEntity.getDescription(), result.getDescription(), "wrong description");
        assertNotNull(result.getSubEntities(), "There should be a list of sub entities");
        assertEquals(1, result.getSubEntities().size(), "Wrong number of sub entities");
        SubEntityDao subResult = result.getSubEntities().iterator().next();
        assertEquals(subEntity.getSubName(), subResult.getSubName(), "wrong name at sub entity");
        assertEquals(subEntity.getDescription(), subResult.getDescription(), "wrong description at sub entity");
    }

    @Test
    public void testConvertToSubEntity() {
        SubEntity result = CommonAccessMapper.convertToSubEntity(subEntityDao);

        assertEquals(subEntityDao.getSubName(), result.getSubName(), "wrong name");
        assertEquals(subEntityDao.getDescription(), result.getDescription(), "wrong description");
    }

    @Test
    public void testConvertToSubEntityWithParent() {
        rootEntity.getSubEntities().clear();
        SubEntity result = CommonAccessMapper.convertToSubEntity(subEntityDao, rootEntity);

        assertEquals(subEntityDao.getSubName(), result.getSubName(), "wrong name");
        assertEquals(subEntityDao.getDescription(), result.getDescription(), "wrong description");
        assertEquals(1, rootEntity.getSubEntities().size(), "Wrong number of entities");
        assertEquals(result, rootEntity.getSubEntities().iterator().next(), "Wrong sub entity at parent");
    }

    @Test
    public void testConvertToSubEntityDao() {
        SubEntityDao result = CommonAccessMapper.convertToSubEntityDao(subEntity);

        assertEquals(subEntity.getSubName(), result.getSubName(), "wrong name");
        assertEquals(subEntity.getDescription(), result.getDescription(), "wrong description");
    }

    @Test
    public void testConvertToSubEntityDaoWithParent() {
        rootEntityDao.setSubEntities(new ArrayList<>());
        SubEntityDao result = CommonAccessMapper.convertToSubEntityDao(subEntity, rootEntityDao);

        assertEquals(subEntity.getSubName(), result.getSubName(), "wrong name");
        assertEquals(subEntity.getDescription(), result.getDescription(), "wrong description");
        assertNotNull(result.getParentRootEntity(), "There should be a parent");
        assertEquals(1, result.getParentRootEntity().getSubEntities().size(), "Wrong number of sub entities");
        assertEquals(result, result.getParentRootEntity().getSubEntities().iterator().next(), "Wrong sub entity at parent");
    }
}
