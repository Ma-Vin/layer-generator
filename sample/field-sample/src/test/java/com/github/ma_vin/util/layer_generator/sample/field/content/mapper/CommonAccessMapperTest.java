package com.github.ma_vin.util.layer_generator.sample.field.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.field.content.dao.FieldEntityDao;
import com.github.ma_vin.util.layer_generator.sample.field.content.domain.FieldEntity;
import com.github.ma_vin.util.layer_generator.sample.given.AnyEnumType;
import com.github.ma_vin.util.layer_generator.sample.given.CustomType;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommonAccessMapperTest {

    private FieldEntity fieldEntity;
    private FieldEntityDao fieldEntityDao;

    @BeforeEach
    public void setUp() {
        fieldEntity = new FieldEntity();
        fieldEntity.setIdentification(IdGenerator.generateIdentification(1L, FieldEntity.ID_PREFIX));
        fieldEntity.setDaoAndDomain("dao domain");
        fieldEntity.setDaoEnum(AnyEnumType.ENUM_VALUE_A);
        fieldEntity.setDaoEnumWithText(AnyEnumType.ENUM_VALUE_B);
        fieldEntity.setDocument("some document text");
        fieldEntity.setDtoAndDomain("not a dao field");
        fieldEntity.setOnlyDomain("only domain");
        fieldEntity.setNumberWithDaoInfo(42.1);
        fieldEntity.setSomeCustom(new CustomType("anyThing"));
        fieldEntity.setSomeEnum(AnyEnumType.ENUM_VALUE_C);
        fieldEntity.setSomeInteger(4711);
        fieldEntity.setSomeName("otherColumnName");
        fieldEntity.setSomeString("anything");
        fieldEntity.setTextWithDaoInfo("a length limited column");

        fieldEntityDao = new FieldEntityDao();
        fieldEntityDao.setId(1L);
        fieldEntityDao.setDaoAndDomain(fieldEntity.getDaoAndDomain());
        fieldEntityDao.setDaoEnum(fieldEntity.getDaoEnum());
        fieldEntityDao.setDaoEnumWithText(fieldEntity.getDaoEnumWithText());
        fieldEntityDao.setDocument(fieldEntity.getDocument());
        fieldEntityDao.setNumberWithDaoInfo(fieldEntity.getNumberWithDaoInfo());
        fieldEntityDao.setOnlyDao("only dao");
        fieldEntityDao.setSomeCustom(fieldEntity.getSomeCustom());
        fieldEntityDao.setSomeEnum(fieldEntity.getSomeEnum());
        fieldEntityDao.setSomeInteger(fieldEntity.getSomeInteger());
        fieldEntityDao.setSomeName(fieldEntity.getSomeName());
        fieldEntityDao.setSomeString(fieldEntity.getSomeString());
        fieldEntityDao.setTextWithDaoInfo(fieldEntity.getTextWithDaoInfo());
    }

    @Test
    public void testConvertToFieldEntity() {
        FieldEntity result = CommonAccessMapper.convertToFieldEntity(fieldEntityDao);

        assertNotNull(result, "There should be any result");
        assertNull(result.getOnlyDomain(), "onlyDomain should be null");
        assertNull(result.getDtoAndDomain(), "dtoAndDomain should be null");
        assertEquals(fieldEntityDao.getDaoAndDomain(), result.getDaoAndDomain(), "Wrong daoAndDomain");
        assertEquals(fieldEntityDao.getDaoEnum(), result.getDaoEnum(), "Wrong daoEnum");
        assertEquals(fieldEntityDao.getDaoEnumWithText(), result.getDaoEnumWithText(), "Wrong DaoEnumWithText");
        assertEquals(fieldEntityDao.getDocument(), result.getDocument(), "Wrong Document");
        assertEquals(fieldEntityDao.getNumberWithDaoInfo(), result.getNumberWithDaoInfo(), "Wrong NumberWithDaoInfo");
        assertEquals(fieldEntityDao.getSomeCustom(), result.getSomeCustom(), "Wrong SomeCustom");
        assertEquals(fieldEntityDao.getSomeEnum(), result.getSomeEnum(), "Wrong SomeEnum");
        assertEquals(fieldEntityDao.getSomeInteger(), result.getSomeInteger(), "Wrong SomeInteger");
        assertEquals(fieldEntityDao.getSomeName(), result.getSomeName(), "Wrong SomeName");
        assertEquals(fieldEntityDao.getSomeString(), result.getSomeString(), "Wrong SomeString");
        assertEquals(fieldEntityDao.getTextWithDaoInfo(), result.getTextWithDaoInfo(), "Wrong TextWithDaoInfo");
    }

    @Test
    public void testConvertToFieldEntityDao() {
        FieldEntityDao result = CommonAccessMapper.convertToFieldEntityDao(fieldEntity);

        assertNotNull(result, "There should be any result");
        assertNull(result.getOnlyDao(), "onlyDAon should be null");
        assertEquals(fieldEntity.getDaoAndDomain(), result.getDaoAndDomain(), "Wrong daoAndDomain");
        assertEquals(fieldEntity.getDaoEnum(), result.getDaoEnum(), "Wrong daoEnum");
        assertEquals(fieldEntity.getDaoEnumWithText(), result.getDaoEnumWithText(), "Wrong DaoEnumWithText");
        assertEquals(fieldEntity.getDocument(), result.getDocument(), "Wrong Document");
        assertEquals(fieldEntity.getNumberWithDaoInfo(), result.getNumberWithDaoInfo(), "Wrong NumberWithDaoInfo");
        assertEquals(fieldEntity.getSomeCustom(), result.getSomeCustom(), "Wrong SomeCustom");
        assertEquals(fieldEntity.getSomeEnum(), result.getSomeEnum(), "Wrong SomeEnum");
        assertEquals(fieldEntity.getSomeInteger(), result.getSomeInteger(), "Wrong SomeInteger");
        assertEquals(fieldEntity.getSomeName(), result.getSomeName(), "Wrong SomeName");
        assertEquals(fieldEntity.getSomeString(), result.getSomeString(), "Wrong SomeString");
        assertEquals(fieldEntity.getTextWithDaoInfo(), result.getTextWithDaoInfo(), "Wrong TextWithDaoInfo");
    }
}
