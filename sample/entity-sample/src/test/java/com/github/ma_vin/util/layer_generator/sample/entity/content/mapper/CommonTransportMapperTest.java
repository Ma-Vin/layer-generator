package com.github.ma_vin.util.layer_generator.sample.entity.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.entity.content.domain.ExtendingEntity;
import com.github.ma_vin.util.layer_generator.sample.entity.content.domain.RootEntity;
import com.github.ma_vin.util.layer_generator.sample.entity.content.dto.DerivedEntityDto;
import com.github.ma_vin.util.layer_generator.sample.entity.content.dto.ExtendingEntityDto;
import com.github.ma_vin.util.layer_generator.sample.entity.content.dto.RootEntityDto;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommonTransportMapperTest {

    private RootEntity rootEntity;
    private RootEntityDto rootEntityDto;
    private ExtendingEntity extendingEntity;
    private ExtendingEntityDto extendingEntityDto;
    private DerivedEntityDto derivedEntityDto;

    @BeforeEach
    public void setUp() {
        rootEntity = new RootEntity();
        rootEntityDto = new RootEntityDto();

        rootEntity.setRootName("RootName");
        rootEntity.setAnyAttribute(1.2);
        rootEntity.setIdentification(IdGenerator.generateIdentification(1L, RootEntity.ID_PREFIX));

        rootEntityDto.setRootName(rootEntity.getRootName());
        rootEntityDto.setAnyAttribute(rootEntity.getAnyAttribute());
        rootEntityDto.setIdentification(rootEntity.getIdentification());

        extendingEntity = new ExtendingEntity();
        extendingEntityDto = new ExtendingEntityDto();

        extendingEntity.setSuperName("ExtendingName");
        extendingEntity.setAddedField(2);
        extendingEntity.setIdentification(IdGenerator.generateIdentification(3L, ExtendingEntity.ID_PREFIX));

        extendingEntityDto.setSuperName(extendingEntity.getSuperName());
        extendingEntityDto.setAddedField(extendingEntity.getAddedField());
        extendingEntityDto.setIdentification(extendingEntity.getIdentification());

        derivedEntityDto = new DerivedEntityDto();
        derivedEntityDto.setRootName(rootEntity.getRootName());
        derivedEntityDto.setIdentification(rootEntity.getIdentification());
    }

    @Test
    public void testConvertToRoot() {
        RootEntity result = CommonTransportMapper.convertToRootEntity(rootEntityDto);

        assertNotNull(result, "There should be any result");
        assertEquals(rootEntityDto.getRootName(), result.getRootName(), "Wrong name");
        assertEquals(rootEntityDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(rootEntityDto.getAnyAttribute(), result.getAnyAttribute(), "Wrong attribute");
    }

    @Test
    public void testConvertToExtending() {
        ExtendingEntity result = CommonTransportMapper.convertToExtendingEntity(extendingEntityDto);

        assertNotNull(result, "There should be any result");
        assertEquals(extendingEntityDto.getSuperName(), result.getSuperName(), "Wrong name");
        assertEquals(extendingEntityDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(extendingEntityDto.getAddedField(), result.getAddedField(), "Wrong added field");
    }

    @Test
    public void testConvertToRootDto() {
        RootEntityDto result = CommonTransportMapper.convertToRootEntityDto(rootEntity);

        assertEquals(rootEntity.getRootName(), result.getRootName(), "Wrong name");
        assertEquals(rootEntity.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(rootEntity.getAnyAttribute(), result.getAnyAttribute(), "Wrong attribute");
    }

    @Test
    public void testConvertToExtendingDto() {
        ExtendingEntityDto result = CommonTransportMapper.convertToExtendingEntityDto(extendingEntity);

        assertNotNull(result, "There should be any result");
        assertEquals(extendingEntity.getSuperName(), result.getSuperName(), "Wrong name");
        assertEquals(extendingEntity.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(extendingEntity.getAddedField(), result.getAddedField(), "Wrong added field");
    }

    @Test
    public void testConvertToDerivedDto() {
        DerivedEntityDto result = CommonTransportMapper.convertToDerivedEntityDto(rootEntity);

        assertEquals(rootEntity.getRootName(), result.getRootName(), "Wrong name");
        assertEquals(rootEntity.getIdentification(), result.getIdentification(), "Wrong identification");
    }

}
