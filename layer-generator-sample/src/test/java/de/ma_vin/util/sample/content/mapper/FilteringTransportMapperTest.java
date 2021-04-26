package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.filtering.*;
import de.ma_vin.util.sample.content.dto.ITransportable;
import de.ma_vin.util.sample.content.dto.RootDto;
import de.ma_vin.util.sample.content.dto.filtering.*;
import de.ma_vin.util.sample.given.AnyEnumType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static de.ma_vin.util.sample.content.ObjectFactory.*;
import static org.junit.jupiter.api.Assertions.*;

public class FilteringTransportMapperTest {

    private Root root;
    private Filtered filtered;
    private SomeFilteringOwner someFilteringOwner;
    private FilteredOnlyDaoField filteredOnlyDaoField;

    private RootDto rootDto;
    private SomeFilteringOwnerDto someFilteringOwnerDto;
    private FilteredDto filteredDto;
    private FilteredOnlyDaoFieldDto filteredOnlyDaoFieldDto;

    Map<String, IIdentifiable> mappedObjects = new HashMap<>();
    Map<String, ITransportable> mappedDtoObjects = new HashMap<>();

    @BeforeEach
    public void setUp() {
        mappedObjects.clear();
        mappedDtoObjects.clear();

        initObjectFactory();
        filteredDto = createFilteredDto(getNextId(), AnyEnumType.ENUM_VALUE_A);
        filteredOnlyDaoFieldDto = createFilteredOnlyDaoFieldDto(getNextId(), AnyEnumType.ENUM_VALUE_B);
        someFilteringOwnerDto = createSomeFilteringOwnerDto(getNextId());
        rootDto = createRootDto(getNextId());

        initObjectFactory();
        filtered = createFiltered(getNextId(), AnyEnumType.ENUM_VALUE_C);
        filteredOnlyDaoField = createFilteredOnlyDaoField(getNextId());
        someFilteringOwner = createSomeFilteringOwnerWithChildren(getNextId());
        root = createRoot(getNextId());
    }

    @Test
    public void testConvertToSomeFilteringOwner() {
        SomeFilteringOwner result = FilteringTransportMapper.convertToSomeFilteringOwner(someFilteringOwnerDto);
        assertNotNull(result, "There should be any result");
        assertEquals(someFilteringOwnerDto.getIdentification(), result.getIdentification(), "Wrong identification");

        assertEquals(0, result.getFilterA().size(), "Wrong number of FilterA");
        assertEquals(0, result.getFilterB().size(), "Wrong number of FilterB");
        assertEquals(0, result.getFilterC().size(), "Wrong number of FilterC");
        assertEquals(0, result.getFilterDaoA().size(), "Wrong number of FilterDaoA");
        assertEquals(0, result.getFilterDaoB().size(), "Wrong number of FilterDaoB");
        assertEquals(0, result.getFilterDaoC().size(), "Wrong number of FilterDaoC");
    }

    @Test
    public void testConvertToSomeFilteringOwnerWithParent() {
        SomeFilteringOwner result = FilteringTransportMapper.convertToSomeFilteringOwner(someFilteringOwnerDto, root);
        assertNotNull(result, "There should be any result");
        assertEquals(someFilteringOwnerDto.getIdentification(), result.getIdentification(), "Wrong identification");

        assertEquals(root.getFiltering(), result, "Wrong ref at root");
    }

    @Test
    public void testConvertToSomeFilteringOwnerNull() {
        assertNull(FilteringTransportMapper.convertToSomeFilteringOwner(null), "The result should be null");
    }

    @Test
    public void testConvertToSomeFilteringOwnerAgain() {
        SomeFilteringOwner result = FilteringTransportMapper.convertToSomeFilteringOwner(someFilteringOwnerDto, mappedObjects);
        SomeFilteringOwner convertAgainResult = FilteringTransportMapper.convertToSomeFilteringOwner(someFilteringOwnerDto, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToFiltered() {
        Filtered result = FilteringTransportMapper.convertToFiltered(filteredDto);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredDto.getDescription(), result.getDescription(), "Wrong description");
        assertEquals(filteredDto.getSomeEnum(), result.getSomeEnum(), "Wrong some enum");
    }

    @Test
    public void testConvertToFilteredNull() {
        assertNull(FilteringTransportMapper.convertToFiltered(null), "The result should be null");
    }

    @Test
    public void testConvertToFilteredAgain() {
        Filtered result = FilteringTransportMapper.convertToFiltered(filteredDto, mappedObjects);
        Filtered convertAgainResult = FilteringTransportMapper.convertToFiltered(filteredDto, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToFilteredOnlyDaoField() {
        FilteredOnlyDaoField result = FilteringTransportMapper.convertToFilteredOnlyDaoField(filteredOnlyDaoFieldDto);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredOnlyDaoFieldDto.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredOnlyDaoFieldDto.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldNull() {
        assertNull(FilteringTransportMapper.convertToFilteredOnlyDaoField(null), "The result should be null");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldAgain() {
        FilteredOnlyDaoField result = FilteringTransportMapper.convertToFilteredOnlyDaoField(filteredOnlyDaoFieldDto, mappedObjects);
        FilteredOnlyDaoField convertAgainResult = FilteringTransportMapper.convertToFilteredOnlyDaoField(filteredOnlyDaoFieldDto, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToSomeFilteringOwnerDto() {
        SomeFilteringOwnerDto result = FilteringTransportMapper.convertToSomeFilteringOwnerDto(someFilteringOwner);
        assertNotNull(result, "There should be any result");
        assertEquals(someFilteringOwner.getIdentification(), result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToSomeFilteringOwnerDtoWithParent() {
        SomeFilteringOwnerDto result = FilteringTransportMapper.convertToSomeFilteringOwnerDto(someFilteringOwner, rootDto);
        assertNotNull(result, "There should be any result");
        assertEquals(someFilteringOwner.getIdentification(), result.getIdentification(), "Wrong identification");

        assertEquals(rootDto.getFiltering(), result, "Wrong ref at root");
    }

    @Test
    public void testConvertToSomeFilteringOwnerDtoNull() {
        assertNull(FilteringTransportMapper.convertToSomeFilteringOwnerDto(null), "The result should be null");
    }

    @Test
    public void testConvertToSomeFilteringOwnerDtoAgain() {
        SomeFilteringOwnerDto result = FilteringTransportMapper.convertToSomeFilteringOwnerDto(someFilteringOwner, mappedDtoObjects);
        SomeFilteringOwnerDto convertAgainResult = FilteringTransportMapper.convertToSomeFilteringOwnerDto(someFilteringOwner, mappedDtoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToFilteredDto() {
        FilteredDto result = FilteringTransportMapper.convertToFilteredDto(filtered);
        assertNotNull(result, "There should be any result");
        assertEquals(filtered.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filtered.getDescription(), result.getDescription(), "Wrong description");
        assertEquals(filtered.getSomeEnum(), result.getSomeEnum(), "Wrong some enum");
    }

    @Test
    public void testConvertToFilteredDtoNull() {
        assertNull(FilteringTransportMapper.convertToFilteredDto(null), "The result should be null");
    }

    @Test
    public void ttestConvertToFilteredDtoAgain() {
        FilteredDto result = FilteringTransportMapper.convertToFilteredDto(filtered, mappedDtoObjects);
        FilteredDto convertAgainResult = FilteringTransportMapper.convertToFilteredDto(filtered, mappedDtoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldDto() {
        FilteredOnlyDaoFieldDto result = FilteringTransportMapper.convertToFilteredOnlyDaoFieldDto(filteredOnlyDaoField);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredOnlyDaoField.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredOnlyDaoField.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldDtoNull() {
        assertNull(FilteringTransportMapper.convertToFilteredOnlyDaoFieldDto(null), "The result should be null");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldDtoAgain() {
        FilteredOnlyDaoFieldDto result = FilteringTransportMapper.convertToFilteredOnlyDaoFieldDto(filteredOnlyDaoField, mappedDtoObjects);
        FilteredOnlyDaoFieldDto convertAgainResult = FilteringTransportMapper.convertToFilteredOnlyDaoFieldDto(filteredOnlyDaoField, mappedDtoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testGetInstance() {
        FilteringTransportMapper firstInstance = FilteringTransportMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, FilteringTransportMapper.getInstance(), "Any other instance should be the same");
    }
}
