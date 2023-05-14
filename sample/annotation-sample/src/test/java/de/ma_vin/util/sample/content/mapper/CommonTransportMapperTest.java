package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.domain.RootEntity;
import de.ma_vin.util.sample.content.domain.SubEntity;
import de.ma_vin.util.sample.content.dto.RootEntityDto;
import de.ma_vin.util.sample.content.dto.SubEntityDto;
import de.ma_vin.util.sample.given.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Generated {@link CommonTransportMapper} is the class under test
 */
public class CommonTransportMapperTest {
    private RootEntityDto rootEntityDto;
    private SubEntityDto subEntityDto;

    private RootEntity rootEntity;
    private SubEntity subEntity;

    @BeforeEach
    public void setUp() {
        rootEntityDto = new RootEntityDto();
        rootEntityDto.setRootName("DummyName");
        rootEntityDto.setDescription("DummyDescription");
        rootEntityDto.setIdentification(IdGenerator.generateIdentification(1L, RootEntity.ID_PREFIX));

        rootEntity = new RootEntity();
        rootEntity.setRootName("DummyName");
        rootEntity.setDescription("DummyDescription");
        rootEntity.setIdentification(IdGenerator.generateIdentification(1L, RootEntity.ID_PREFIX));

        subEntityDto = new SubEntityDto();
        subEntityDto.setSubName("SubDummyName");
        subEntityDto.setDescription("SubDummyDescription");
        subEntityDto.setIdentification(IdGenerator.generateIdentification(2L, SubEntity.ID_PREFIX));

        subEntity = new SubEntity();
        subEntity.setSubName("SubDummyName");
        subEntity.setDescription("SubDummyDescription");
        subEntity.setIdentification(IdGenerator.generateIdentification(2L, SubEntity.ID_PREFIX));
        rootEntity.addSubEntities(subEntity);
    }

    @Test
    public void testConvertToRootEntity() {
        RootEntity result = CommonTransportMapper.convertToRootEntity(rootEntityDto);

        assertEquals(rootEntityDto.getRootName(), result.getRootName(), "wrong name");
        assertEquals(rootEntityDto.getDescription(), result.getDescription(), "wrong description");
        assertNotNull(result.getSubEntities(), "There should be a list of sub entities");
        assertEquals(0, result.getSubEntities().size(), "Wrong number of sub entities");
    }

    @Test
    public void testConvertToRootEntityDto() {
        RootEntityDto result = CommonTransportMapper.convertToRootEntityDto(rootEntity);

        assertEquals(rootEntityDto.getRootName(), result.getRootName(), "wrong name");
        assertEquals(rootEntityDto.getDescription(), result.getDescription(), "wrong description");
    }

    @Test
    public void testConvertToSubEntity() {
        SubEntity result = CommonTransportMapper.convertToSubEntity(subEntityDto);

        assertEquals(subEntityDto.getSubName(), result.getSubName(), "wrong name");
        assertEquals(subEntityDto.getDescription(), result.getDescription(), "wrong description");
    }

    @Test
    public void testConvertToSubEntityDto() {
        SubEntityDto result = CommonTransportMapper.convertToSubEntityDto(subEntity);

        assertEquals(subEntity.getSubName(), result.getSubName(), "wrong name");
        assertEquals(subEntity.getDescription(), result.getDescription(), "wrong description");
    }

}
