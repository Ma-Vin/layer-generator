package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.domain.dto.DomainAndDto;
import de.ma_vin.util.sample.content.dto.ITransportable;
import de.ma_vin.util.sample.content.dto.domain.dto.DomainAndDtoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static de.ma_vin.util.sample.content.ObjectFactory.*;
import static de.ma_vin.util.sample.content.ObjectFactory.getNextId;
import static org.junit.jupiter.api.Assertions.*;

public class DomainDtoTransportMapperTest {

    private DomainAndDto domainAndDto;
    private DomainAndDtoDto domainAndDtoDto;

    Map<String, IIdentifiable> mappedObjects = new HashMap<>();
    Map<String, ITransportable> mappedDtoObjects = new HashMap<>();

    @BeforeEach
    public void setUp() {
        mappedObjects.clear();
        mappedDtoObjects.clear();

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
    public void testConvertToDomainAndDtoAgain() {
        DomainAndDto result = DomainDtoTransportMapper.convertToDomainAndDto(domainAndDtoDto, mappedObjects);
        DomainAndDto convertAgainResult = DomainDtoTransportMapper.convertToDomainAndDto(domainAndDtoDto, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
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
    public void testConvertToDomainAndDtoDtoAgain() {
        DomainAndDtoDto result = DomainDtoTransportMapper.convertToDomainAndDtoDto(domainAndDto, mappedDtoObjects);
        DomainAndDtoDto convertAgainResult = DomainDtoTransportMapper.convertToDomainAndDtoDto(domainAndDto, mappedDtoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testGetInstance() {
        DomainDtoTransportMapper firstInstance = DomainDtoTransportMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, DomainDtoTransportMapper.getInstance(), "Any other instance should be the same");
    }
}
