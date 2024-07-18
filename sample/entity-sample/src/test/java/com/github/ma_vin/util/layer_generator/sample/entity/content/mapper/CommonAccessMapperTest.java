package com.github.ma_vin.util.layer_generator.sample.entity.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.entity.content.dao.ExtendingEntityDao;
import com.github.ma_vin.util.layer_generator.sample.entity.content.dao.IndexEntityDao;
import com.github.ma_vin.util.layer_generator.sample.entity.content.dao.RootEntityDao;
import com.github.ma_vin.util.layer_generator.sample.entity.content.domain.ExtendingEntity;
import com.github.ma_vin.util.layer_generator.sample.entity.content.domain.IndexEntity;
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
    private IndexEntity indexEntity;
    private IndexEntityDao indexEntityDao;

    @BeforeEach
    public void setUp() {
        setUpRootEntity();
        setUpExtendingEntity();
        setUpIndexEntity();
    }

    private void setUpRootEntity() {
        rootEntity = new RootEntity();
        rootEntityDao = new RootEntityDao();

        rootEntity.setRootName("RootName");
        rootEntity.setAnyAttribute(1.3);
        rootEntity.setIdentification(IdGenerator.generateIdentification(1L, RootEntity.ID_PREFIX));

        rootEntityDao.setRootName(rootEntity.getRootName());
        rootEntityDao.setAnyAttribute(rootEntity.getAnyAttribute());
        rootEntityDao.setId(1L);
    }

    private void setUpExtendingEntity() {
        extendingEntity = new ExtendingEntity();
        extendingEntityDao = new ExtendingEntityDao();

        extendingEntity.setSuperName("ExtendingName");
        extendingEntity.setAddedField(2);
        extendingEntity.setIdentification(IdGenerator.generateIdentification(3L, ExtendingEntity.ID_PREFIX));

        extendingEntityDao.setSuperName(extendingEntity.getSuperName());
        extendingEntityDao.setAddedField(extendingEntity.getAddedField());
        extendingEntityDao.setId(3L);
    }

    private void setUpIndexEntity() {
        indexEntity = new IndexEntity();
        indexEntityDao = new IndexEntityDao();

        indexEntity.setPrimaryKeyPartOne(1);
        indexEntity.setPrimaryKeyPartTwo(2);
        indexEntity.setIndexPart(3);
        indexEntity.setIdentification(IdGenerator.generateIdentification(4L, IndexEntity.ID_PREFIX));

        indexEntityDao.setPrimaryKeyPartOne(1);
        indexEntityDao.setPrimaryKeyPartTwo(2);
        indexEntityDao.setIndexPart(3);
        indexEntityDao.setId(4L);
    }

    @Test
    public void testConvertToRoot() {
        RootEntity result = CommonAccessMapper.convertToRootEntity(rootEntityDao);

        assertNotNull(result, "There should be any result");
        assertEquals(rootEntityDao.getRootName(), result.getRootName(), "Wrong name");
        assertEquals(rootEntityDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(rootEntityDao.getAnyAttribute(), result.getAnyAttribute(), "Wrong attribute");
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
    public void testConvertToIndex() {
        IndexEntity result = CommonAccessMapper.convertToIndexEntity(indexEntityDao);

        assertNotNull(result, "There should be any result");
        assertEquals(indexEntityDao.getPrimaryKeyPartOne(), result.getPrimaryKeyPartOne(), "Wrong pk1");
        assertEquals(indexEntityDao.getPrimaryKeyPartTwo(), result.getPrimaryKeyPartTwo(), "Wrong pk2");
        assertEquals(indexEntityDao.getIndexPart(), result.getIndexPart(), "Wrong index");
        assertEquals(indexEntityDao.getIdentification(), result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToRootDao() {
        RootEntityDao result = CommonAccessMapper.convertToRootEntityDao(rootEntity);

        assertEquals(rootEntity.getRootName(), result.getRootName(), "Wrong name");
        assertEquals(rootEntity.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(rootEntity.getAnyAttribute(), result.getAnyAttribute(), "Wrong attribute");
    }

    @Test
    public void testConvertToExtendingDao() {
        ExtendingEntityDao result = CommonAccessMapper.convertToExtendingEntityDao(extendingEntity);

        assertNotNull(result, "There should be any result");
        assertEquals(extendingEntity.getSuperName(), result.getSuperName(), "Wrong name");
        assertEquals(extendingEntity.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(extendingEntity.getAddedField(), result.getAddedField(), "Wrong added field");
    }

    @Test
    public void testConvertToIndexDao() {
        IndexEntityDao result = CommonAccessMapper.convertToIndexEntityDao(indexEntity);

        assertNotNull(result, "There should be any result");
        assertEquals(indexEntityDao.getPrimaryKeyPartOne(), result.getPrimaryKeyPartOne(), "Wrong pk1");
        assertEquals(indexEntityDao.getPrimaryKeyPartTwo(), result.getPrimaryKeyPartTwo(), "Wrong pk2");
        assertEquals(indexEntityDao.getIndexPart(), result.getIndexPart(), "Wrong index");
        assertEquals(indexEntityDao.getIdentification(), result.getIdentification(), "Wrong identification");
    }

}
