package com.github.ma_vin.util.layer_generator.sample.reference.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.given.AnyEnumType;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dao.*;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.*;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class CommonAccessMapperTest {

    private SourceEntityOneToOne sourceEntityOneToOne;
    private SourceEntityOneToOneDao sourceEntityOneToOneDao;
    private TargetEntityOneToOne targetEntityOneToOne;
    private TargetEntityOneToOneDao targetEntityOneToOneDao;

    private SourceEntityOneToMany sourceEntityOneToMany;
    private SourceEntityOneToManyDao sourceEntityOneToManyDao;
    private TargetEntityOneToMany targetEntityOneToMany;
    private TargetEntityOneToManyDao targetEntityOneToManyDao;

    private SourceEntityManyToMany sourceEntityManyToMany;
    private SourceEntityManyToManyDao sourceEntityManyToManyDao;
    private SourceEntityManyToOne sourceEntityManyToOne;
    private SourceEntityManyToOneDao sourceEntityManyToOneDao;
    private TargetEntityManyToMany targetEntityManyToMany;
    private TargetEntityManyToManyDao targetEntityManyToManyDao;

    private SourceEntityFilter sourceEntityFilter;
    private SourceEntityFilterDao sourceEntityFilterDao;
    private TargetEntityFilter targetEntityFilterA;
    private TargetEntityFilter targetEntityFilterB;
    private TargetEntityFilterDao targetEntityFilterDaoA;
    private TargetEntityFilterDao targetEntityFilterDaoB;

    private SourceEntityFilterNotAtTarget sourceEntityFilterNotAtTarget;
    private SourceEntityFilterNotAtTargetDao sourceEntityFilterNotAtTargetDao;
    private TargetEntityFilterNotAtTarget targetEntityFilterNotAtTargetA;
    private TargetEntityFilterNotAtTarget targetEntityFilterNotAtTargetB;
    private SourceEntityFilterNotAtTargetToTargetEntityFilterNotAtTargetDao sourceTargetConnectionFilterDaoA;
    private SourceEntityFilterNotAtTargetToTargetEntityFilterNotAtTargetDao sourceTargetConnectionFilterDaoB;

    private void setUpOneToOne() {
        sourceEntityOneToOne = new SourceEntityOneToOne();
        sourceEntityOneToOneDao = new SourceEntityOneToOneDao();

        targetEntityOneToOne = new TargetEntityOneToOne();
        targetEntityOneToOneDao = new TargetEntityOneToOneDao();

        sourceEntityOneToOne.setOneToOneRef(targetEntityOneToOne);
        sourceEntityOneToOneDao.setOneToOneRef(targetEntityOneToOneDao);

        sourceEntityOneToOne.setIdentification(IdGenerator.generateIdentification(1L, SourceEntityOneToOne.ID_PREFIX));
        sourceEntityOneToOneDao.setId(1L);
        targetEntityOneToOne.setIdentification(IdGenerator.generateIdentification(2L, TargetEntityOneToOne.ID_PREFIX));
        targetEntityOneToOneDao.setId(2L);
    }

    private void setUpOneToMany() {
        sourceEntityOneToMany = new SourceEntityOneToMany();
        sourceEntityOneToManyDao = new SourceEntityOneToManyDao();

        targetEntityOneToMany = new TargetEntityOneToMany();
        targetEntityOneToManyDao = new TargetEntityOneToManyDao();

        sourceEntityOneToMany.addOneToManyRef(targetEntityOneToMany);
        sourceEntityOneToManyDao.setOneToManyRef(new ArrayList<>());
        sourceEntityOneToManyDao.getOneToManyRef().add(targetEntityOneToManyDao);

        targetEntityOneToManyDao.setParentSourceEntityOneToMany(sourceEntityOneToManyDao);

        sourceEntityOneToMany.setIdentification(IdGenerator.generateIdentification(3L, SourceEntityOneToMany.ID_PREFIX));
        sourceEntityOneToManyDao.setId(3L);
        targetEntityOneToMany.setIdentification(IdGenerator.generateIdentification(4L, TargetEntityOneToMany.ID_PREFIX));
        targetEntityOneToManyDao.setId(4L);
    }

    private void setUpManyToMany() {
        sourceEntityManyToMany = new SourceEntityManyToMany();
        sourceEntityManyToManyDao = new SourceEntityManyToManyDao();

        sourceEntityManyToOne = new SourceEntityManyToOne();
        sourceEntityManyToOneDao = new SourceEntityManyToOneDao();

        targetEntityManyToMany = new TargetEntityManyToMany();
        targetEntityManyToManyDao = new TargetEntityManyToManyDao();
        SourceEntityManyToManyToTargetEntityManyToManyDao sourceTargetConnectionDao = new SourceEntityManyToManyToTargetEntityManyToManyDao();

        sourceEntityManyToMany.addManyToManyRef(targetEntityManyToMany);
        sourceEntityManyToManyDao.setManyToManyRef(new ArrayList<>());
        sourceEntityManyToManyDao.getManyToManyRef().add(sourceTargetConnectionDao);
        sourceTargetConnectionDao.setSourceEntityManyToMany(sourceEntityManyToManyDao);
        sourceTargetConnectionDao.setTargetEntityManyToMany(targetEntityManyToManyDao);

        sourceEntityManyToOne.setManyToOneRef(targetEntityManyToMany);
        sourceEntityManyToOneDao.setManyToOneRef(targetEntityManyToManyDao);

        sourceEntityManyToMany.setIdentification(IdGenerator.generateIdentification(5L, SourceEntityManyToMany.ID_PREFIX));
        sourceEntityManyToManyDao.setId(5L);
        sourceEntityManyToOne.setIdentification(IdGenerator.generateIdentification(6L, SourceEntityManyToOne.ID_PREFIX));
        sourceEntityManyToOneDao.setId(6L);
        targetEntityManyToMany.setIdentification(IdGenerator.generateIdentification(7L, TargetEntityManyToMany.ID_PREFIX));
        targetEntityManyToManyDao.setId(7L);
    }

    private void setUpFilter() {
        sourceEntityFilter = new SourceEntityFilter();
        sourceEntityFilterDao = new SourceEntityFilterDao();

        targetEntityFilterA = new TargetEntityFilter();
        targetEntityFilterDaoA = new TargetEntityFilterDao();

        targetEntityFilterB = new TargetEntityFilter();
        targetEntityFilterDaoB = new TargetEntityFilterDao();

        sourceEntityFilter.addOneToManyFilterA(targetEntityFilterA);
        sourceEntityFilter.addOneToManyFilterB(targetEntityFilterB);
        sourceEntityFilterDao.setAggTargetEntityFilter(new ArrayList<>());
        sourceEntityFilterDao.getAggTargetEntityFilter().add(targetEntityFilterDaoA);
        sourceEntityFilterDao.getAggTargetEntityFilter().add(targetEntityFilterDaoB);


        targetEntityFilterA.setEnumFieldForFiltering(AnyEnumType.ENUM_VALUE_A);
        targetEntityFilterDaoA.setEnumFieldForFiltering(AnyEnumType.ENUM_VALUE_A);
        targetEntityFilterB.setEnumFieldForFiltering(AnyEnumType.ENUM_VALUE_B);
        targetEntityFilterDaoB.setEnumFieldForFiltering(AnyEnumType.ENUM_VALUE_B);

        sourceEntityFilter.setIdentification(IdGenerator.generateIdentification(8L, SourceEntityFilter.ID_PREFIX));
        sourceEntityFilterDao.setId(8L);
        targetEntityFilterA.setIdentification(IdGenerator.generateIdentification(9L, TargetEntityFilter.ID_PREFIX));
        targetEntityFilterDaoA.setId(9L);
        targetEntityFilterB.setIdentification(IdGenerator.generateIdentification(10L, TargetEntityFilter.ID_PREFIX));
        targetEntityFilterDaoB.setId(10L);
    }

    private void setUpFilterNotAtTarget() {
        sourceEntityFilterNotAtTarget = new SourceEntityFilterNotAtTarget();
        sourceEntityFilterNotAtTargetDao = new SourceEntityFilterNotAtTargetDao();

        targetEntityFilterNotAtTargetA = new TargetEntityFilterNotAtTarget();
        TargetEntityFilterNotAtTargetDao targetEntityFilterNotAtTargetDaoA = new TargetEntityFilterNotAtTargetDao();
        sourceTargetConnectionFilterDaoA = new SourceEntityFilterNotAtTargetToTargetEntityFilterNotAtTargetDao();

        targetEntityFilterNotAtTargetB = new TargetEntityFilterNotAtTarget();
        TargetEntityFilterNotAtTargetDao targetEntityFilterNotAtTargetDaoB = new TargetEntityFilterNotAtTargetDao();
        sourceTargetConnectionFilterDaoB = new SourceEntityFilterNotAtTargetToTargetEntityFilterNotAtTargetDao();

        sourceEntityFilterNotAtTarget.addOneToManyFilterA(targetEntityFilterNotAtTargetA);
        sourceEntityFilterNotAtTarget.addOneToManyFilterB(targetEntityFilterNotAtTargetB);

        sourceEntityFilterNotAtTargetDao.setAggTargetEntityFilterNotAtTarget(new ArrayList<>());
        sourceEntityFilterNotAtTargetDao.getAggTargetEntityFilterNotAtTarget().add(sourceTargetConnectionFilterDaoA);
        sourceEntityFilterNotAtTargetDao.getAggTargetEntityFilterNotAtTarget().add(sourceTargetConnectionFilterDaoB);

        sourceTargetConnectionFilterDaoA.setFilterAnyEnumType(AnyEnumType.ENUM_VALUE_A);
        sourceTargetConnectionFilterDaoA.setSourceEntityFilterNotAtTarget(sourceEntityFilterNotAtTargetDao);
        sourceTargetConnectionFilterDaoA.setTargetEntityFilterNotAtTarget(targetEntityFilterNotAtTargetDaoA);

        sourceTargetConnectionFilterDaoB.setFilterAnyEnumType(AnyEnumType.ENUM_VALUE_B);
        sourceTargetConnectionFilterDaoB.setSourceEntityFilterNotAtTarget(sourceEntityFilterNotAtTargetDao);
        sourceTargetConnectionFilterDaoB.setTargetEntityFilterNotAtTarget(targetEntityFilterNotAtTargetDaoB);

        sourceEntityFilterNotAtTarget.setIdentification(IdGenerator.generateIdentification(11L, SourceEntityFilterNotAtTarget.ID_PREFIX));
        sourceEntityFilterNotAtTargetDao.setId(11L);
        targetEntityFilterNotAtTargetA.setIdentification(IdGenerator.generateIdentification(12L, TargetEntityFilterNotAtTarget.ID_PREFIX));
        targetEntityFilterNotAtTargetDaoA.setId(12L);
        targetEntityFilterNotAtTargetB.setIdentification(IdGenerator.generateIdentification(13L, TargetEntityFilterNotAtTarget.ID_PREFIX));
        targetEntityFilterNotAtTargetDaoB.setId(13L);
    }

    @Test
    public void testConvertToSourceEntityOneToOne() {
        setUpOneToOne();
        SourceEntityOneToOne result = CommonAccessMapper.convertToSourceEntityOneToOne(sourceEntityOneToOneDao);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityOneToOneDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(targetEntityOneToOne, result.getOneToOneRef(), "Wrong OneToOneRef");
    }

    @Test
    public void testConvertToSourceEntityOneToOneDao() {
        setUpOneToOne();
        SourceEntityOneToOneDao result = CommonAccessMapper.convertToSourceEntityOneToOneDao(sourceEntityOneToOne);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityOneToOneDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(targetEntityOneToOneDao, result.getOneToOneRef(), "Wrong OneToOneRef");
    }

    @Test
    public void testConvertToSourceEntityOneToMany() {
        setUpOneToMany();
        SourceEntityOneToMany result = CommonAccessMapper.convertToSourceEntityOneToMany(sourceEntityOneToManyDao, true);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityOneToManyDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(sourceEntityOneToManyDao.getOneToManyRef().size(), result.getOneToManyRef().size(), "Wrong number of OneToManyRef");
        assertEquals(targetEntityOneToMany, getFirstEntry(result.getOneToManyRef()), "Wrong first entry of OneToManyRef");
    }

    @Test
    public void testConvertToSourceEntityOneToManyDao() {
        setUpOneToMany();
        SourceEntityOneToManyDao result = CommonAccessMapper.convertToSourceEntityOneToManyDao(sourceEntityOneToMany, true);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityOneToMany.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(sourceEntityOneToMany.getOneToManyRef().size(), result.getOneToManyRef().size(), "Wrong number of OneToManyRef");
        assertEquals(targetEntityOneToManyDao, getFirstEntry(result.getOneToManyRef()), "Wrong first entry of OneToManyRef");
    }

    @Test
    public void testConvertToSourceEntityManyToMany() {
        setUpManyToMany();
        SourceEntityManyToMany result = CommonAccessMapper.convertToSourceEntityManyToMany(sourceEntityManyToManyDao, true);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityManyToManyDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(sourceEntityManyToManyDao.getManyToManyRef().size(), result.getManyToManyRef().size(), "Wrong number of ManyToManyRef");
        assertEquals(targetEntityManyToMany, getFirstEntry(result.getManyToManyRef()), "Wrong first entry of ManyToManyRef");
    }

    @Test
    public void testConvertToSourceEntityManyToManyDao() {
        setUpManyToMany();
        SourceEntityManyToManyDao result = CommonAccessMapper.convertToSourceEntityManyToManyDao(sourceEntityManyToMany, true);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityManyToMany.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(sourceEntityManyToMany.getManyToManyRef().size(), result.getManyToManyRef().size(), "Wrong number of ManyToManyRef");
        assertEquals(targetEntityManyToManyDao, getFirstEntry(result.getManyToManyRef()).getTargetEntityManyToMany(), "Wrong first entry of  ManyToManyRef");
    }

    @Test
    public void testConvertToSourceEntityManyToOne() {
        setUpManyToMany();
        SourceEntityManyToOne result = CommonAccessMapper.convertToSourceEntityManyToOne(sourceEntityManyToOneDao);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityManyToOneDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(targetEntityManyToMany, result.getManyToOneRef(), "Wrong ManyToOneRef");
    }

    @Test
    public void testConvertToSourceEntityManyToOneDao() {
        setUpManyToMany();
        SourceEntityManyToOneDao result = CommonAccessMapper.convertToSourceEntityManyToOneDao(sourceEntityManyToOne);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityManyToOne.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(targetEntityManyToManyDao, result.getManyToOneRef(), "Wrong first entry of ManyToOneRef");
    }

    @Test
    public void testConvertToSourceEntityFilter() {
        setUpFilter();
        SourceEntityFilter result = CommonAccessMapper.convertToSourceEntityFilter(sourceEntityFilterDao, true);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityFilterDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(sourceEntityFilter.getOneToManyFilterA().size(), result.getOneToManyFilterA().size(), "Wrong number of OneToManyFilterA");
        assertEquals(sourceEntityFilter.getOneToManyFilterB().size(), result.getOneToManyFilterB().size(), "Wrong number of OneToManyFilterB");
        assertEquals(targetEntityFilterA, getFirstEntry(result.getOneToManyFilterA()), "Wrong first entry of OneToManyFilterA");
        assertEquals(targetEntityFilterB, getFirstEntry(result.getOneToManyFilterB()), "Wrong first entry of OneToManyFilterB");
    }

    @Test
    public void testConvertToSourceEntityFilterDao() {
        setUpFilter();
        SourceEntityFilterDao result = CommonAccessMapper.convertToSourceEntityFilterDao(sourceEntityFilter, true);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityFilter.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(sourceEntityFilterDao.getAggTargetEntityFilter().size(), result.getAggTargetEntityFilter().size(), "Wrong number of AggTargetEntityFilter");
        assertTrue(result.getAggTargetEntityFilter().contains(targetEntityFilterDaoA), "AggTargetEntityFilter must contains targetEntityFilterDaoA");
        assertTrue(result.getAggTargetEntityFilter().contains(targetEntityFilterDaoB), "AggTargetEntityFilter must contains targetEntityFilterDaoB");
    }

    @Test
    public void testConvertToSourceEntityFilterNotAtTarget() {
        setUpFilterNotAtTarget();
        SourceEntityFilterNotAtTarget result = CommonAccessMapper.convertToSourceEntityFilterNotAtTarget(sourceEntityFilterNotAtTargetDao, true);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityFilterNotAtTargetDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(sourceEntityFilterNotAtTarget.getOneToManyFilterA().size(), result.getOneToManyFilterA().size(), "Wrong number of OneToManyFilterA");
        assertEquals(sourceEntityFilterNotAtTarget.getOneToManyFilterB().size(), result.getOneToManyFilterB().size(), "Wrong number of OneToManyFilterB");
        assertEquals(targetEntityFilterNotAtTargetA, getFirstEntry(result.getOneToManyFilterA()), "Wrong first entry of OneToManyFilterA");
        assertEquals(targetEntityFilterNotAtTargetB, getFirstEntry(result.getOneToManyFilterB()), "Wrong first entry of OneToManyFilterB");
    }

    @Test
    public void testConvertToSourceEntityFilterNotAtTargetDao() {
        setUpFilterNotAtTarget();
        SourceEntityFilterNotAtTargetDao result = CommonAccessMapper.convertToSourceEntityFilterNotAtTargetDao(sourceEntityFilterNotAtTarget, true);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityFilterNotAtTarget.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(sourceEntityFilterNotAtTargetDao.getAggTargetEntityFilterNotAtTarget().size(), result.getAggTargetEntityFilterNotAtTarget().size(), "Wrong number of AggTargetEntityFilterNotAtTarget");
        assertTrue(result.getAggTargetEntityFilterNotAtTarget().contains(sourceTargetConnectionFilterDaoA), "AggTargetEntityFilterNotAtTarget must contains sourceTargetConnectionFilterDaoA");
        assertTrue(result.getAggTargetEntityFilterNotAtTarget().contains(sourceTargetConnectionFilterDaoB), "AggTargetEntityFilterNotAtTarget must contains sourceTargetConnectionFilterDaoB");
    }

    private <T> T getFirstEntry(Collection<T> collection) {
        return collection.stream().findFirst().orElseThrow(() -> new AssertionFailedError("Missing first element"));
    }
}
