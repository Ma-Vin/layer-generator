package com.github.ma_vin.util.layer_generator.sample.reference.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.given.AnyEnumType;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dto.*;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommonTransportMapperTest {

    private SourceEntityOneToOne sourceEntityOneToOne;
    private SourceEntityOneToOneDto sourceEntityOneToOneDto;
    private TargetEntityOneToOne targetEntityOneToOne;
    private TargetEntityOneToOneDto targetEntityOneToOneDto;

    private SourceEntityOneToMany sourceEntityOneToMany;
    private SourceEntityOneToManyDto sourceEntityOneToManyDto;
    private TargetEntityOneToMany targetEntityOneToMany;
    private TargetEntityOneToManyDto targetEntityOneToManyDto;

    private SourceEntityManyToMany sourceEntityManyToMany;
    private SourceEntityManyToManyDto sourceEntityManyToManyDto;
    private SourceEntityManyToOne sourceEntityManyToOne;
    private SourceEntityManyToOneDto sourceEntityManyToOneDto;
    private TargetEntityManyToMany targetEntityManyToMany;
    private TargetEntityManyToManyDto targetEntityManyToManyDto;

    private SourceEntityFilter sourceEntityFilter;
    private SourceEntityFilterDto sourceEntityFilterDto;
    private TargetEntityFilter targetEntityFilterA;
    private TargetEntityFilterDto targetEntityFilterDtoA;

    private SourceEntityFilterNotAtTarget sourceEntityFilterNotAtTarget;
    private SourceEntityFilterNotAtTargetDto sourceEntityFilterNotAtTargetDto;
    private TargetEntityFilterNotAtTarget targetEntityFilterNotAtTargetA;
    private TargetEntityFilterNotAtTargetDto targetEntityFilterNotAtTargetDtoA;


    private void setUpOneToOne() {
        sourceEntityOneToOne = new SourceEntityOneToOne();
        sourceEntityOneToOneDto = new SourceEntityOneToOneDto();

        targetEntityOneToOne = new TargetEntityOneToOne();
        targetEntityOneToOneDto = new TargetEntityOneToOneDto();

        sourceEntityOneToOne.setOneToOneRef(targetEntityOneToOne);
        sourceEntityOneToOneDto.setOneToOneRef(targetEntityOneToOneDto);

        sourceEntityOneToOne.setIdentification(IdGenerator.generateIdentification(1L, SourceEntityOneToOne.ID_PREFIX));
        sourceEntityOneToOneDto.setIdentification(sourceEntityOneToOne.getIdentification());
        targetEntityOneToOne.setIdentification(IdGenerator.generateIdentification(2L, TargetEntityOneToOne.ID_PREFIX));
        targetEntityOneToOneDto.setIdentification(targetEntityOneToOne.getIdentification());
    }

    private void setUpOneToMany() {
        sourceEntityOneToMany = new SourceEntityOneToMany();
        sourceEntityOneToManyDto = new SourceEntityOneToManyDto();

        targetEntityOneToMany = new TargetEntityOneToMany();
        targetEntityOneToManyDto = new TargetEntityOneToManyDto();

        sourceEntityOneToMany.addOneToManyRef(targetEntityOneToMany);

        sourceEntityOneToMany.setIdentification(IdGenerator.generateIdentification(3L, SourceEntityOneToMany.ID_PREFIX));
        sourceEntityOneToManyDto.setIdentification(sourceEntityOneToMany.getIdentification());
        targetEntityOneToMany.setIdentification(IdGenerator.generateIdentification(4L, TargetEntityOneToMany.ID_PREFIX));
        targetEntityOneToManyDto.setIdentification(targetEntityOneToMany.getIdentification());
    }

    private void setUpManyToMany() {
        sourceEntityManyToMany = new SourceEntityManyToMany();
        sourceEntityManyToManyDto = new SourceEntityManyToManyDto();

        sourceEntityManyToOne = new SourceEntityManyToOne();
        sourceEntityManyToOneDto = new SourceEntityManyToOneDto();

        targetEntityManyToMany = new TargetEntityManyToMany();
        targetEntityManyToManyDto = new TargetEntityManyToManyDto();

        sourceEntityManyToMany.addManyToManyRef(targetEntityManyToMany);

        sourceEntityManyToOne.setManyToOneRef(targetEntityManyToMany);
        sourceEntityManyToOneDto.setManyToOneRef(targetEntityManyToManyDto);

        sourceEntityManyToMany.setIdentification(IdGenerator.generateIdentification(5L, SourceEntityManyToMany.ID_PREFIX));
        sourceEntityManyToManyDto.setIdentification(sourceEntityManyToMany.getIdentification());
        sourceEntityManyToOne.setIdentification(IdGenerator.generateIdentification(6L, SourceEntityManyToOne.ID_PREFIX));
        sourceEntityManyToOneDto.setIdentification(sourceEntityManyToOne.getIdentification());
        targetEntityManyToMany.setIdentification(IdGenerator.generateIdentification(7L, TargetEntityManyToMany.ID_PREFIX));
        targetEntityManyToManyDto.setIdentification(targetEntityManyToMany.getIdentification());
    }

    private void setUpFilter() {
        sourceEntityFilter = new SourceEntityFilter();
        sourceEntityFilterDto = new SourceEntityFilterDto();

        targetEntityFilterA = new TargetEntityFilter();
        targetEntityFilterDtoA = new TargetEntityFilterDto();

        TargetEntityFilter targetEntityFilterB = new TargetEntityFilter();
        TargetEntityFilterDto targetEntityFilterDtoB = new TargetEntityFilterDto();

        sourceEntityFilter.addOneToManyFilterA(targetEntityFilterA);
        sourceEntityFilter.addOneToManyFilterB(targetEntityFilterB);


        targetEntityFilterA.setEnumFieldForFiltering(AnyEnumType.ENUM_VALUE_A);
        targetEntityFilterDtoA.setEnumFieldForFiltering(AnyEnumType.ENUM_VALUE_A);
        targetEntityFilterB.setEnumFieldForFiltering(AnyEnumType.ENUM_VALUE_B);
        targetEntityFilterDtoB.setEnumFieldForFiltering(AnyEnumType.ENUM_VALUE_B);

        sourceEntityFilter.setIdentification(IdGenerator.generateIdentification(8L, SourceEntityFilter.ID_PREFIX));
        sourceEntityFilterDto.setIdentification(sourceEntityFilter.getIdentification());
        targetEntityFilterA.setIdentification(IdGenerator.generateIdentification(9L, TargetEntityFilter.ID_PREFIX));
        targetEntityFilterDtoA.setIdentification(targetEntityFilterA.getIdentification());
        targetEntityFilterB.setIdentification(IdGenerator.generateIdentification(10L, TargetEntityFilter.ID_PREFIX));
        targetEntityFilterDtoB.setIdentification(targetEntityFilterB.getIdentification());
    }

    private void setUpFilterNotAtTarget() {
        sourceEntityFilterNotAtTarget = new SourceEntityFilterNotAtTarget();
        sourceEntityFilterNotAtTargetDto = new SourceEntityFilterNotAtTargetDto();

        targetEntityFilterNotAtTargetA = new TargetEntityFilterNotAtTarget();
        targetEntityFilterNotAtTargetDtoA = new TargetEntityFilterNotAtTargetDto();

        TargetEntityFilterNotAtTarget targetEntityFilterNotAtTargetB = new TargetEntityFilterNotAtTarget();
        TargetEntityFilterNotAtTargetDto targetEntityFilterNotAtTargetDtoB = new TargetEntityFilterNotAtTargetDto();

        sourceEntityFilterNotAtTarget.addOneToManyFilterA(targetEntityFilterNotAtTargetA);
        sourceEntityFilterNotAtTarget.addOneToManyFilterB(targetEntityFilterNotAtTargetB);


        sourceEntityFilterNotAtTarget.setIdentification(IdGenerator.generateIdentification(11L, SourceEntityFilterNotAtTarget.ID_PREFIX));
        sourceEntityFilterNotAtTargetDto.setIdentification(sourceEntityFilterNotAtTarget.getIdentification());
        targetEntityFilterNotAtTargetA.setIdentification(IdGenerator.generateIdentification(12L, TargetEntityFilterNotAtTarget.ID_PREFIX));
        targetEntityFilterNotAtTargetDtoA.setIdentification(targetEntityFilterNotAtTargetA.getIdentification());
        targetEntityFilterNotAtTargetB.setIdentification(IdGenerator.generateIdentification(13L, TargetEntityFilterNotAtTarget.ID_PREFIX));
        targetEntityFilterNotAtTargetDtoB.setIdentification(targetEntityFilterNotAtTargetB.getIdentification());
    }

    @Test
    public void testConvertToSourceEntityOneToOne() {
        setUpOneToOne();
        SourceEntityOneToOne result = CommonTransportMapper.convertToSourceEntityOneToOne(sourceEntityOneToOneDto);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityOneToOneDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(targetEntityOneToOne, result.getOneToOneRef(), "Wrong OneToOneRef");
    }

    @Test
    public void testConvertToSourceEntityOneToOneDto() {
        setUpOneToOne();
        SourceEntityOneToOneDto result = CommonTransportMapper.convertToSourceEntityOneToOneDto(sourceEntityOneToOne);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityOneToOneDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(targetEntityOneToOneDto, result.getOneToOneRef(), "Wrong OneToOneRef");
    }

    @Test
    public void testConvertToSourceEntityOneToMany() {
        setUpOneToMany();
        SourceEntityOneToMany result = CommonTransportMapper.convertToSourceEntityOneToMany(sourceEntityOneToManyDto);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityOneToManyDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(0, result.getOneToManyRef().size(), "Wrong number of OneToManyRef");
    }

    @Test
    public void testConvertToTargetEntityOneToMany() {
        setUpOneToMany();
        TargetEntityOneToMany result = CommonTransportMapper.convertToTargetEntityOneToMany(targetEntityOneToManyDto);

        assertNotNull(result, "There should be any result");
        assertEquals(targetEntityOneToManyDto.getIdentification(), result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToSourceEntityOneToManyDto() {
        setUpOneToMany();
        SourceEntityOneToManyDto result = CommonTransportMapper.convertToSourceEntityOneToManyDto(sourceEntityOneToMany);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityOneToMany.getIdentification(), result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToTargetEntityOneToManyDto() {
        setUpOneToMany();
        TargetEntityOneToManyDto result = CommonTransportMapper.convertToTargetEntityOneToManyDto(targetEntityOneToMany);

        assertNotNull(result, "There should be any result");
        assertEquals(targetEntityOneToMany.getIdentification(), result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToSourceEntityManyToMany() {
        setUpManyToMany();
        SourceEntityManyToMany result = CommonTransportMapper.convertToSourceEntityManyToMany(sourceEntityManyToManyDto);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityManyToManyDto.getIdentification(), result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToTargetEntityManyToMany() {
        setUpManyToMany();
        TargetEntityManyToMany result = CommonTransportMapper.convertToTargetEntityManyToMany(targetEntityManyToManyDto);

        assertNotNull(result, "There should be any result");
        assertEquals(targetEntityManyToManyDto.getIdentification(), result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToSourceEntityManyToManyDto() {
        setUpManyToMany();
        SourceEntityManyToManyDto result = CommonTransportMapper.convertToSourceEntityManyToManyDto(sourceEntityManyToMany);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityManyToMany.getIdentification(), result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToTargetEntityManyToManyDto() {
        setUpManyToMany();
        TargetEntityManyToManyDto result = CommonTransportMapper.convertToTargetEntityManyToManyDto(targetEntityManyToMany);

        assertNotNull(result, "There should be any result");
        assertEquals(targetEntityManyToMany.getIdentification(), result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToSourceEntityManyToOne() {
        setUpManyToMany();
        SourceEntityManyToOne result = CommonTransportMapper.convertToSourceEntityManyToOne(sourceEntityManyToOneDto);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityManyToOneDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(targetEntityManyToMany, result.getManyToOneRef(), "Wrong ManyToOneRef");
    }

    @Test
    public void testConvertToSourceEntityManyToOneDto() {
        setUpManyToMany();
        SourceEntityManyToOneDto result = CommonTransportMapper.convertToSourceEntityManyToOneDto(sourceEntityManyToOne);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityManyToOne.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(targetEntityManyToManyDto, result.getManyToOneRef(), "Wrong first entry of ManyToOneRef");
    }

    @Test
    public void testConvertToSourceEntityFilter() {
        setUpFilter();
        SourceEntityFilter result = CommonTransportMapper.convertToSourceEntityFilter(sourceEntityFilterDto);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityFilterDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(0, result.getOneToManyFilterA().size(), "Wrong number of OneToManyFilterA");
        assertEquals(0, result.getOneToManyFilterB().size(), "Wrong number of OneToManyFilterB");
    }

    @Test
    public void testConvertToTargetEntityFilter() {
        setUpFilter();
        TargetEntityFilter result = CommonTransportMapper.convertToTargetEntityFilter(targetEntityFilterDtoA);

        assertNotNull(result, "There should be any result");
        assertEquals(targetEntityFilterDtoA.getIdentification(), result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToSourceEntityFilterDto() {
        setUpFilter();
        SourceEntityFilterDto result = CommonTransportMapper.convertToSourceEntityFilterDto(sourceEntityFilter);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityFilter.getIdentification(), result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToTargetEntityFilterDto() {
        setUpFilter();
        TargetEntityFilterDto result = CommonTransportMapper.convertToTargetEntityFilterDto(targetEntityFilterA);

        assertNotNull(result, "There should be any result");
        assertEquals(targetEntityFilterA.getIdentification(), result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToSourceEntityFilterNotAtTarget() {
        setUpFilterNotAtTarget();
        SourceEntityFilterNotAtTarget result = CommonTransportMapper.convertToSourceEntityFilterNotAtTarget(sourceEntityFilterNotAtTargetDto);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityFilterNotAtTargetDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(0, result.getOneToManyFilterA().size(), "Wrong number of OneToManyFilterA");
        assertEquals(0, result.getOneToManyFilterB().size(), "Wrong number of OneToManyFilterB");
    }

    @Test
    public void testConvertToTargetEntityFilterNotAtTarget() {
        setUpFilterNotAtTarget();
        TargetEntityFilterNotAtTarget result = CommonTransportMapper.convertToTargetEntityFilterNotAtTarget(targetEntityFilterNotAtTargetDtoA);

        assertNotNull(result, "There should be any result");
        assertEquals(targetEntityFilterNotAtTargetDtoA.getIdentification(), result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToSourceEntityFilterNotAtTargetDto() {
        setUpFilterNotAtTarget();
        SourceEntityFilterNotAtTargetDto result = CommonTransportMapper.convertToSourceEntityFilterNotAtTargetDto(sourceEntityFilterNotAtTarget);

        assertNotNull(result, "There should be any result");
        assertEquals(sourceEntityFilterNotAtTarget.getIdentification(), result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToTargetEntityFilterNotAtTargetDto() {
        setUpFilterNotAtTarget();
        TargetEntityFilterNotAtTargetDto result = CommonTransportMapper.convertToTargetEntityFilterNotAtTargetDto(targetEntityFilterNotAtTargetA);

        assertNotNull(result, "There should be any result");
        assertEquals(targetEntityFilterNotAtTargetA.getIdentification(), result.getIdentification(), "Wrong identification");
    }
}
