package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.domain.domain.dto.DomainAndDto;
import de.ma_vin.util.sample.content.dto.domain.dto.DomainAndDtoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.ma_vin.util.sample.content.ObjectFactory.*;
import static de.ma_vin.util.sample.content.ObjectFactory.getNextId;
import static org.junit.jupiter.api.Assertions.*;

public class DomainDtoTransportMapperTest {
    
    private DomainAndDto domainAndDto;
    private DomainAndDtoDto domainAndDtoDto;
    
    @BeforeEach
    public void setUp() {
        initObjectFactory();
        domainAndDto = createDomainAndDto(getNextId());
        initObjectFactory();
        domainAndDtoDto = createDomainAndDtoDto(getNextId());
    }

    @Test
    public void testConvertToDomainAndDto() {
        DomainAndDto result = DomainDtoTransportMapper.convertToDomainAndDto(domainAndDtoDto);
        assertNotNull(result, "There should be any result");
        assertEquals(domainAndDtoDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(domainAndDtoDto.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToDomainAndDtoNull() {
        assertNull(DomainDtoTransportMapper.convertToDomainAndDto(null), "The result should be null");
    }

    @Test
    public void testConvertToDomainAndDtoDto() {
        DomainAndDtoDto result = DomainDtoTransportMapper.convertToDomainAndDtoDto(domainAndDto);
        assertNotNull(result, "There should be any result");
        assertEquals(domainAndDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(domainAndDto.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToDomainAndDtoDtoNull() {
        assertNull(DomainDtoTransportMapper.convertToDomainAndDto(null), "The result should be null");
    }

    @Test
    public void testGetInstance() {
        DomainDtoTransportMapper firstInstance = DomainDtoTransportMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, DomainDtoTransportMapper.getInstance(), "Any other instance should be the same");
    }
}
