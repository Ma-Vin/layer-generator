package com.github.ma_vin.util.layer_generator.sample.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.content.domain.single.SingleRefOneParent;
import com.github.ma_vin.util.layer_generator.sample.content.dto.ITransportable;
import com.github.ma_vin.util.layer_generator.sample.content.dto.domain.DerivedFromDomainDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.github.ma_vin.util.layer_generator.sample.content.ObjectFactory.*;
import static com.github.ma_vin.util.layer_generator.sample.content.ObjectFactory.getNextId;
import static org.junit.jupiter.api.Assertions.*;

public class DomainTransportMapperTest {

    private SingleRefOneParent singleRefOneParent;
    Map<String, IIdentifiable> mappedObjects = new HashMap<>();
    Map<String, ITransportable> mappedDtoObjects = new HashMap<>();

    @BeforeEach
    public void setUp() {
        mappedObjects.clear();
        mappedDtoObjects.clear();

        initObjectFactory();
        singleRefOneParent = createSingleRefOneParent(getNextId());
    }

    @Test
    public void testConvertToDerivedFromDomainDto() {
        DerivedFromDomainDto result = DomainTransportMapper.convertToDerivedFromDomainDto(singleRefOneParent);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefOneParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefOneParent.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToDerivedFromDomainDtoNull() {
        assertNull(DomainDtoTransportMapper.convertToDomainAndDto(null), "The result should be null");
    }

    @Test
    public void testConvertToDerivedFromDomainDtoAgain() {
        DerivedFromDomainDto result = DomainTransportMapper.convertToDerivedFromDomainDto(singleRefOneParent, mappedDtoObjects);
        DerivedFromDomainDto convertAgainResult = DomainTransportMapper.convertToDerivedFromDomainDto(singleRefOneParent, mappedDtoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testGetInstance() {
        DomainTransportMapper firstInstance = DomainTransportMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, DomainTransportMapper.getInstance(), "Any other instance should be the same");
    }
}
