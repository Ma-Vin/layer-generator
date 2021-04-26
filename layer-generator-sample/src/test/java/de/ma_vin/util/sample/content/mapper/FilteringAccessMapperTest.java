package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.dao.filtering.*;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.filtering.*;
import de.ma_vin.util.sample.given.AnyEnumType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static de.ma_vin.util.sample.content.ObjectFactory.*;
import static org.junit.jupiter.api.Assertions.*;

public class FilteringAccessMapperTest {

    private RootDao rootDao;
    private SomeFilteringOwnerDao someFilteringOwnerDao;
    private FilteredDao filteredDao;
    private FilteredOnlyDaoFieldDao filteredOnlyDaoFieldDao;

    private Root root;
    private Filtered filtered;
    private SomeFilteringOwner someFilteringOwner;
    private FilteredOnlyDaoField filteredOnlyDaoField;

    Map<String, IIdentifiable> mappedObjects = new HashMap<>();
    Map<String, IIdentifiableDao> mappedDaoObjects = new HashMap<>();

    @BeforeEach
    public void setUp() {
        mappedObjects.clear();
        mappedDaoObjects.clear();

        initObjectFactory();
        filteredDao = createFilteredDao(getNextId(), AnyEnumType.ENUM_VALUE_A);
        filteredOnlyDaoFieldDao = createFilteredOnlyDaoFieldDao(getNextId(), AnyEnumType.ENUM_VALUE_B);
        someFilteringOwnerDao = createSomeFilteringOwnerDaoWithChildren(getNextId());
        rootDao = createRootDao(getNextId());

        initObjectFactory();
        filtered = createFiltered(getNextId(), AnyEnumType.ENUM_VALUE_C);
        filteredOnlyDaoField = createFilteredOnlyDaoField(getNextId());
        someFilteringOwner = createSomeFilteringOwnerWithChildren(getNextId());
        root = createRoot(getNextId());
    }

    @Test
    public void testConvertToSomeFilteringOwner() {
        SomeFilteringOwner result = FilteringAccessMapper.convertToSomeFilteringOwner(someFilteringOwnerDao, false);
        assertNotNull(result, "There should be any result");
        assertEquals(someFilteringOwnerDao.getIdentification(), result.getIdentification(), "Wrong identification");

        assertEquals(0, result.getFilterA().size(), "Wrong number of FilterA");
        assertEquals(0, result.getFilterB().size(), "Wrong number of FilterB");
        assertEquals(0, result.getFilterC().size(), "Wrong number of FilterC");
        assertEquals(0, result.getFilterDaoA().size(), "Wrong number of FilterDaoA");
        assertEquals(0, result.getFilterDaoB().size(), "Wrong number of FilterDaoB");
        assertEquals(0, result.getFilterDaoC().size(), "Wrong number of FilterDaoC");
    }

    @Test
    public void testConvertToSomeFilteringOwnerWithChildren() {
        SomeFilteringOwner result = FilteringAccessMapper.convertToSomeFilteringOwner(someFilteringOwnerDao, true);
        assertNotNull(result, "There should be any result");
        assertEquals(someFilteringOwnerDao.getIdentification(), result.getIdentification(), "Wrong identification");

        assertEquals(1, result.getFilterA().size(), "Wrong number of FilterA");
        result.getFilterA().forEach(f -> assertEquals(AnyEnumType.ENUM_VALUE_A, f.getSomeEnum(), "Wrong enum at filter"));
        assertEquals(1, result.getFilterB().size(), "Wrong number of FilterB");
        result.getFilterB().forEach(f -> assertEquals(AnyEnumType.ENUM_VALUE_B, f.getSomeEnum(), "Wrong enum at filter"));
        assertEquals(1, result.getFilterC().size(), "Wrong number of FilterC");
        result.getFilterC().forEach(f -> assertEquals(AnyEnumType.ENUM_VALUE_C, f.getSomeEnum(), "Wrong enum at filter"));
        assertEquals(1, result.getFilterDaoA().size(), "Wrong number of FilterDaoA");
        assertEquals(1, result.getFilterDaoB().size(), "Wrong number of FilterDaoB");
        assertEquals(1, result.getFilterDaoC().size(), "Wrong number of FilterDaoC");
    }

    @Test
    public void testConvertToSomeFilteringOwnerWithParent() {
        SomeFilteringOwner result = FilteringAccessMapper.convertToSomeFilteringOwner(someFilteringOwnerDao, false, root);
        assertNotNull(result, "There should be any result");
        assertEquals(someFilteringOwnerDao.getIdentification(), result.getIdentification(), "Wrong identification");

        assertEquals(root.getFiltering(), result, "Wrong ref at root");
    }

    @Test
    public void testConvertToSomeFilteringOwnerNull() {
        assertNull(FilteringAccessMapper.convertToSomeFilteringOwner(null, false), "The result should be null");
    }

    @Test
    public void testConvertToSomeFilteringOwnerAgain() {
        SomeFilteringOwner result = FilteringAccessMapper.convertToSomeFilteringOwner(someFilteringOwnerDao, false, mappedObjects);
        SomeFilteringOwner convertAgainResult = FilteringAccessMapper.convertToSomeFilteringOwner(someFilteringOwnerDao, false, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToFiltered() {
        Filtered result = FilteringAccessMapper.convertToFiltered(filteredDao);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredDao.getDescription(), result.getDescription(), "Wrong description");
        assertEquals(filteredDao.getSomeEnum(), result.getSomeEnum(), "Wrong some enum");
    }

    @Test
    public void testConvertToFilteredWithParent() {
        Filtered result = FilteringAccessMapper.convertToFiltered(filteredDao, someFilteringOwner);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredDao.getDescription(), result.getDescription(), "Wrong description");
        assertEquals(filteredDao.getSomeEnum(), result.getSomeEnum(), "Wrong some enum");

        assertTrue(someFilteringOwner.getFilterA().contains(result), "Wrong ref at someFilteringOwner");
        assertFalse(someFilteringOwner.getFilterB().contains(result), "An enum of type A should not be contained at FilterB");
        assertFalse(someFilteringOwner.getFilterC().contains(result), "An enum of type A should not be contained at FilterC");
    }

    @Test
    public void testConvertToFilteredNull() {
        assertNull(FilteringAccessMapper.convertToFiltered(null), "The result should be null");
    }

    @Test
    public void testConvertToFilteredAgain() {
        Filtered result = FilteringAccessMapper.convertToFiltered(filteredDao, mappedObjects);
        Filtered convertAgainResult = FilteringAccessMapper.convertToFiltered(filteredDao, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToFilteredOnlyDaoField() {
        FilteredOnlyDaoField result = FilteringAccessMapper.convertToFilteredOnlyDaoField(filteredOnlyDaoFieldDao);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredOnlyDaoFieldDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredOnlyDaoFieldDao.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldWithParent() {
        FilteredOnlyDaoField result = FilteringAccessMapper.convertToFilteredOnlyDaoField(filteredOnlyDaoFieldDao, someFilteringOwner);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredOnlyDaoFieldDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredOnlyDaoFieldDao.getDescription(), result.getDescription(), "Wrong description");

        assertFalse(someFilteringOwner.getFilterDaoA().contains(result), "An enum of type B should not be contained at FilterA");
        assertTrue(someFilteringOwner.getFilterDaoB().contains(result), "Wrong ref at someFilteringOwner");
        assertFalse(someFilteringOwner.getFilterDaoC().contains(result), "An enum of type B should not be contained at FilterC");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldNull() {
        assertNull(FilteringAccessMapper.convertToFilteredOnlyDaoField(null), "The result should be null");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldAgain() {
        FilteredOnlyDaoField result = FilteringAccessMapper.convertToFilteredOnlyDaoField(filteredOnlyDaoFieldDao, mappedObjects);
        FilteredOnlyDaoField convertAgainResult = FilteringAccessMapper.convertToFilteredOnlyDaoField(filteredOnlyDaoFieldDao, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToSomeFilteringOwnerDao() {
        SomeFilteringOwnerDao result = FilteringAccessMapper.convertToSomeFilteringOwnerDao(someFilteringOwner, false);
        assertNotNull(result, "There should be any result");
        assertEquals(someFilteringOwner.getIdentification(), result.getIdentification(), "Wrong identification");

        assertEquals(0, result.getAggFiltered().size(), "Wrong number of Filter");
        assertEquals(0, result.getAggFilteredOnlyDaoField().size(), "Wrong number of FilterDao");
    }

    @Test
    public void testConvertToSomeFilteringOwnerDaoWithChildren() {
        SomeFilteringOwnerDao result = FilteringAccessMapper.convertToSomeFilteringOwnerDao(someFilteringOwner, true);
        assertNotNull(result, "There should be any result");
        assertEquals(someFilteringOwner.getIdentification(), result.getIdentification(), "Wrong identification");

        assertEquals(3, result.getAggFiltered().size(), "Wrong number of Filter");
        assertEquals(3, result.getAggFilteredOnlyDaoField().size(), "Wrong number of FilterDao");
    }

    @Test
    public void testConvertToSomeFilteringOwnerDaoWithParent() {
        SomeFilteringOwnerDao result = FilteringAccessMapper.convertToSomeFilteringOwnerDao(someFilteringOwner, false, rootDao);
        assertNotNull(result, "There should be any result");
        assertEquals(someFilteringOwner.getIdentification(), result.getIdentification(), "Wrong identification");

        assertEquals(rootDao.getFiltering(), result, "Wrong ref at root");
    }

    @Test
    public void testConvertToSomeFilteringOwnerDaoNull() {
        assertNull(FilteringAccessMapper.convertToSomeFilteringOwnerDao(null, false), "The result should be null");
    }

    @Test
    public void testConvertToSomeFilteringOwnerDaoAgain() {
        SomeFilteringOwnerDao result = FilteringAccessMapper.convertToSomeFilteringOwnerDao(someFilteringOwner, false, mappedDaoObjects);
        SomeFilteringOwnerDao convertAgainResult = FilteringAccessMapper.convertToSomeFilteringOwnerDao(someFilteringOwner, false, mappedDaoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToFilteredDao() {
        FilteredDao result = FilteringAccessMapper.convertToFilteredDao(filtered);
        assertNotNull(result, "There should be any result");
        assertEquals(filtered.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filtered.getDescription(), result.getDescription(), "Wrong description");
        assertEquals(filtered.getSomeEnum(), result.getSomeEnum(), "Wrong some enum");
    }

    @Test
    public void testConvertToFilteredDaoWithParent() {
        FilteredDao result = FilteringAccessMapper.convertToFilteredDao(filtered, someFilteringOwnerDao);
        assertNotNull(result, "There should be any result");
        assertEquals(filtered.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filtered.getDescription(), result.getDescription(), "Wrong description");
        assertEquals(filtered.getSomeEnum(), result.getSomeEnum(), "Wrong some enum");

        assertTrue(someFilteringOwnerDao.getAggFiltered().contains(result), "Wrong ref at someFilteringOwner");
    }

    @Test
    public void testConvertToFilteredDaoNull() {
        assertNull(FilteringAccessMapper.convertToFilteredDao(null), "The result should be null");
    }

    @Test
    public void testConvertToFilteredDaAgain() {
        FilteredDao result = FilteringAccessMapper.convertToFilteredDao(filtered, mappedDaoObjects);
        FilteredDao convertAgainResult = FilteringAccessMapper.convertToFilteredDao(filtered, mappedDaoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldDao() {
        FilteredOnlyDaoFieldDao result = FilteringAccessMapper.convertToFilteredOnlyDaoFieldDao(filteredOnlyDaoField, AnyEnumType.ENUM_VALUE_A);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredOnlyDaoField.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredOnlyDaoField.getDescription(), result.getDescription(), "Wrong description");
        assertEquals(AnyEnumType.ENUM_VALUE_A, result.getSomeEnum(), "Wrong some enum");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldDaoWithParent() {
        FilteredOnlyDaoFieldDao result = FilteringAccessMapper.convertToFilteredOnlyDaoFieldDao(filteredOnlyDaoField, someFilteringOwnerDao, AnyEnumType.ENUM_VALUE_C);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredOnlyDaoField.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredOnlyDaoField.getDescription(), result.getDescription(), "Wrong description");
        assertEquals(AnyEnumType.ENUM_VALUE_C, result.getSomeEnum(), "Wrong some enum");

        assertTrue(someFilteringOwnerDao.getAggFilteredOnlyDaoField().contains(result), "An enum of type B should be contained at FilterDao");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldDaoNull() {
        assertNull(FilteringAccessMapper.convertToFilteredOnlyDaoFieldDao(null, AnyEnumType.ENUM_VALUE_B), "The result should be null");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldDaoAgain() {
        FilteredOnlyDaoFieldDao result = FilteringAccessMapper.convertToFilteredOnlyDaoFieldDao(filteredOnlyDaoField, AnyEnumType.ENUM_VALUE_A, mappedDaoObjects);
        FilteredOnlyDaoFieldDao convertAgainResult = FilteringAccessMapper.convertToFilteredOnlyDaoFieldDao(filteredOnlyDaoField, AnyEnumType.ENUM_VALUE_A, mappedDaoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testGetInstance() {
        FilteringAccessMapper firstInstance = FilteringAccessMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, FilteringAccessMapper.getInstance(), "Any other instance should be the same");
    }
}
