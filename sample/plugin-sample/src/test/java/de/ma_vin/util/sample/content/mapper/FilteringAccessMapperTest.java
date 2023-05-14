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
    private FilteredNotOwnerDao filteredNotOwnerDao;
    private FilteredOnlyDaoFieldDao filteredOnlyDaoFieldDao;
    private FilteredOnlyDaoFieldNotOwnerDao filteredOnlyDaoFieldNotOwnerDao;
    private SomeDifferentFilteringNotOwnerDao someDifferentFilteringNotOwnerDao;

    private Root root;
    private Filtered filtered;
    private FilteredNotOwner filteredNotOwner;
    private SomeFilteringOwner someFilteringOwner;
    private FilteredOnlyDaoFieldNotOwner filteredOnlyDaoFieldNotOwner;
    private FilteredOnlyDaoField filteredOnlyDaoField;
    private SomeDifferentFilteringNotOwner someDifferentFilteringNotOwner;

    Map<String, IIdentifiable> mappedObjects = new HashMap<>();
    Map<String, IIdentifiableDao> mappedDaoObjects = new HashMap<>();

    @BeforeEach
    public void setUp() {
        mappedObjects.clear();
        mappedDaoObjects.clear();

        initObjectFactory();
        filteredDao = createFilteredDao(getNextId(), AnyEnumType.ENUM_VALUE_A);
        filteredNotOwnerDao = createFilteredNotOwnerDao(getNextId(), AnyEnumType.ENUM_VALUE_A);
        filteredOnlyDaoFieldDao = createFilteredOnlyDaoFieldDao(getNextId(), AnyEnumType.ENUM_VALUE_B);
        filteredOnlyDaoFieldNotOwnerDao = createFilteredOnlyDaoFieldNotOwnerDao(getNextId(), AnyEnumType.ENUM_VALUE_B);
        someFilteringOwnerDao = createSomeFilteringOwnerDaoWithChildren(getNextId());
        someDifferentFilteringNotOwnerDao = createSomeDifferentFilteringNotOwnerDaoWithChildren(getNextId());
        rootDao = createRootDao(getNextId());

        initObjectFactory();
        filtered = createFiltered(getNextId(), AnyEnumType.ENUM_VALUE_C);
        filteredNotOwner = createFilteredNotOwner(getNextId(), AnyEnumType.ENUM_VALUE_C);
        filteredOnlyDaoField = createFilteredOnlyDaoField(getNextId());
        filteredOnlyDaoFieldNotOwner = createFilteredOnlyDaoFieldNotOwner(getNextId());
        someFilteringOwner = createSomeFilteringOwnerWithChildren(getNextId());
        someDifferentFilteringNotOwner = createSomeDifferentFilteringNotOwnerWithChildren(getNextId());
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

        assertEquals(0, result.getFilterNotOwnerA().size(), "Wrong number of FilterNotOwnerA");
        assertEquals(0, result.getFilterNotOwnerB().size(), "Wrong number of FilterNotOwnerB");
        assertEquals(0, result.getFilterNotOwnerC().size(), "Wrong number of FilterNotOwnerC");

        assertEquals(0, result.getFilterDaoA().size(), "Wrong number of FilterDaoA");
        assertEquals(0, result.getFilterDaoB().size(), "Wrong number of FilterDaoB");
        assertEquals(0, result.getFilterDaoC().size(), "Wrong number of FilterDaoC");

        assertEquals(0, result.getFilterDaoNotOwnerA().size(), "Wrong number of DaoFilterNotOwnerA");
        assertEquals(0, result.getFilterDaoNotOwnerB().size(), "Wrong number of DaoFilterNotOwnerB");
        assertEquals(0, result.getFilterDaoNotOwnerC().size(), "Wrong number of DaoFilterNotOwnerC");
    }

    @Test
    public void testConvertToSomeFilteringOwnerWithChildren() {
        SomeFilteringOwner result = FilteringAccessMapper.convertToSomeFilteringOwner(someFilteringOwnerDao, true);
        assertNotNull(result, "There should be any result");
        assertEquals(someFilteringOwnerDao.getIdentification(), result.getIdentification(), "Wrong identification");

        assertEquals(1, result.getFilterA().size(), "Wrong number of FilterA");
        result.getFilterA().forEach(f -> assertEquals(AnyEnumType.ENUM_VALUE_A, f.getSomeEnum(), "Wrong enum at FilterA"));
        assertEquals(1, result.getFilterB().size(), "Wrong number of FilterB");
        result.getFilterB().forEach(f -> assertEquals(AnyEnumType.ENUM_VALUE_B, f.getSomeEnum(), "Wrong enum at FilterB"));
        assertEquals(1, result.getFilterC().size(), "Wrong number of FilterC");
        result.getFilterC().forEach(f -> assertEquals(AnyEnumType.ENUM_VALUE_C, f.getSomeEnum(), "Wrong enum at FilterC"));

        assertEquals(1, result.getFilterNotOwnerA().size(), "Wrong number of FilterNotOwnerA");
        result.getFilterNotOwnerA().forEach(f -> assertEquals(AnyEnumType.ENUM_VALUE_A, f.getSomeEnumNotOwner(), "Wrong enum at FilterNotOwnerA"));
        assertEquals(1, result.getFilterNotOwnerB().size(), "Wrong number of FilterNotOwnerB");
        result.getFilterNotOwnerB().forEach(f -> assertEquals(AnyEnumType.ENUM_VALUE_B, f.getSomeEnumNotOwner(), "Wrong enum at FilterNotOwnerB"));
        assertEquals(1, result.getFilterNotOwnerB().size(), "Wrong number of FilterNotOwnerC");
        result.getFilterNotOwnerC().forEach(f -> assertEquals(AnyEnumType.ENUM_VALUE_C, f.getSomeEnumNotOwner(), "Wrong enum at FilterNotOwnerC"));

        assertEquals(1, result.getFilterDaoA().size(), "Wrong number of FilterDaoA");
        assertEquals(1, result.getFilterDaoB().size(), "Wrong number of FilterDaoB");
        assertEquals(1, result.getFilterDaoC().size(), "Wrong number of FilterDaoC");

        assertEquals(1, result.getFilterDaoNotOwnerA().size(), "Wrong number of FilterDaoNotOwnerA");
        assertEquals(1, result.getFilterDaoNotOwnerB().size(), "Wrong number of FilterDaoNotOwnerB");
        assertEquals(1, result.getFilterDaoNotOwnerC().size(), "Wrong number of FilterDaoNotOwnerC");
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
    public void testConvertToSomeDifferentFilteringOwner() {
        SomeDifferentFilteringNotOwner result = FilteringAccessMapper.convertToSomeDifferentFilteringNotOwner(someDifferentFilteringNotOwnerDao, false);
        assertNotNull(result, "There should be any result");
        assertEquals(someDifferentFilteringNotOwnerDao.getIdentification(), result.getIdentification(), "Wrong identification");

        assertEquals(0, result.getFilterA().size(), "Wrong number of FilterA");
        assertEquals(0, result.getFilterB().size(), "Wrong number of FilterB");
        assertEquals(0, result.getFilterC().size(), "Wrong number of FilterC");
    }

    @Test
    public void testConvertToSomeDifferentFilteringOwnerWithChildren() {
        SomeDifferentFilteringNotOwner result = FilteringAccessMapper.convertToSomeDifferentFilteringNotOwner(someDifferentFilteringNotOwnerDao, true);
        assertNotNull(result, "There should be any result");
        assertEquals(someDifferentFilteringNotOwnerDao.getIdentification(), result.getIdentification(), "Wrong identification");

        assertEquals(1, result.getFilterA().size(), "Wrong number of FilterA");
        result.getFilterA().forEach(f -> assertEquals(AnyEnumType.ENUM_VALUE_B, f.getSomeEnum(), "Wrong enum at FilterA"));
        assertEquals(1, result.getFilterB().size(), "Wrong number of FilterB");
        result.getFilterB().forEach(f -> assertEquals(AnyEnumType.ENUM_VALUE_C, f.getSomeEnum(), "Wrong enum at FilterB"));
        assertEquals(1, result.getFilterC().size(), "Wrong number of FilterC");
        result.getFilterC().forEach(f -> assertEquals(AnyEnumType.ENUM_VALUE_A, f.getSomeEnum(), "Wrong enum at FilterC"));
    }

    @Test
    public void testConvertToSomeDifferentFilteringOwnerWithParent() {
        SomeDifferentFilteringNotOwner result = FilteringAccessMapper.convertToSomeDifferentFilteringNotOwner(someDifferentFilteringNotOwnerDao, false, root);
        assertNotNull(result, "There should be any result");
        assertEquals(someDifferentFilteringNotOwnerDao.getIdentification(), result.getIdentification(), "Wrong identification");

        assertEquals(root.getNonOwnerFiltering(), result, "Wrong ref at root");
    }

    @Test
    public void testConvertToSomeDifferentFilteringOwnerNull() {
        assertNull(FilteringAccessMapper.convertToSomeDifferentFilteringNotOwner(null, false), "The result should be null");
    }

    @Test
    public void testConvertToSomeDifferentFilteringOwnerAgain() {
        SomeDifferentFilteringNotOwner result = FilteringAccessMapper.convertToSomeDifferentFilteringNotOwner(someDifferentFilteringNotOwnerDao, false, mappedObjects);
        SomeDifferentFilteringNotOwner convertAgainResult = FilteringAccessMapper.convertToSomeDifferentFilteringNotOwner(someDifferentFilteringNotOwnerDao, false, mappedObjects);
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
    public void testConvertToFilteredWithDifferentParent() {
        Filtered result = FilteringAccessMapper.convertToFiltered(filteredDao, someDifferentFilteringNotOwner, AnyEnumType.ENUM_VALUE_A);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredDao.getDescription(), result.getDescription(), "Wrong description");
        assertEquals(filteredDao.getSomeEnum(), result.getSomeEnum(), "Wrong some enum");

        assertTrue(someDifferentFilteringNotOwner.getFilterA().contains(result), "Wrong ref at someFilteringOwner");
        assertFalse(someDifferentFilteringNotOwner.getFilterB().contains(result), "An enum of type A should not be contained at FilterB");
        assertFalse(someDifferentFilteringNotOwner.getFilterC().contains(result), "An enum of type A should not be contained at FilterC");
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
    public void testConvertToFilteredNotOwner() {
        FilteredNotOwner result = FilteringAccessMapper.convertToFilteredNotOwner(filteredNotOwnerDao);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredNotOwnerDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredNotOwnerDao.getDescriptionNotOwner(), result.getDescriptionNotOwner(), "Wrong description");
        assertEquals(filteredNotOwnerDao.getSomeEnumNotOwner(), result.getSomeEnumNotOwner(), "Wrong some enum");
    }

    @Test
    public void testConvertToFilteredNotOwnerWithParent() {
        FilteredNotOwner result = FilteringAccessMapper.convertToFilteredNotOwner(filteredNotOwnerDao, someFilteringOwner);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredNotOwnerDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredNotOwnerDao.getDescriptionNotOwner(), result.getDescriptionNotOwner(), "Wrong description");
        assertEquals(filteredNotOwnerDao.getSomeEnumNotOwner(), result.getSomeEnumNotOwner(), "Wrong some enum");

        assertTrue(someFilteringOwner.getFilterNotOwnerA().contains(result), "Wrong ref at someFilteringOwner");
        assertFalse(someFilteringOwner.getFilterNotOwnerB().contains(result), "An enum of type A should not be contained at FilterB");
        assertFalse(someFilteringOwner.getFilterNotOwnerC().contains(result), "An enum of type A should not be contained at FilterC");
    }

    @Test
    public void testConvertToFilteredNotOwnerNull() {
        assertNull(FilteringAccessMapper.convertToFilteredNotOwner(null), "The result should be null");
    }

    @Test
    public void testConvertToFilteredNotOwnerAgain() {
        FilteredNotOwner result = FilteringAccessMapper.convertToFilteredNotOwner(filteredNotOwnerDao, mappedObjects);
        FilteredNotOwner convertAgainResult = FilteringAccessMapper.convertToFilteredNotOwner(filteredNotOwnerDao, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToFilteredOnlyDaoField() {
        FilteredOnlyDaoField result = FilteringAccessMapper.convertToFilteredOnlyDaoField(filteredOnlyDaoFieldDao);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredOnlyDaoFieldDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredOnlyDaoFieldDao.getDescriptionOnlyDaoField(), result.getDescriptionOnlyDaoField(), "Wrong description");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldWithParent() {
        FilteredOnlyDaoField result = FilteringAccessMapper.convertToFilteredOnlyDaoField(filteredOnlyDaoFieldDao, someFilteringOwner);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredOnlyDaoFieldDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredOnlyDaoFieldDao.getDescriptionOnlyDaoField(), result.getDescriptionOnlyDaoField(), "Wrong description");

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
    public void testConvertToFilteredOnlyDaoFieldNotOwner() {
        FilteredOnlyDaoFieldNotOwner result = FilteringAccessMapper.convertToFilteredOnlyDaoFieldNotOwner(filteredOnlyDaoFieldNotOwnerDao);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredOnlyDaoFieldNotOwnerDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredOnlyDaoFieldNotOwnerDao.getDescriptionOnlyDaoFieldNotOwner(), result.getDescriptionOnlyDaoFieldNotOwner(), "Wrong description");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldNotOwnerWithParent() {
        FilteredOnlyDaoFieldNotOwner result = FilteringAccessMapper.convertToFilteredOnlyDaoFieldNotOwner(filteredOnlyDaoFieldNotOwnerDao, someFilteringOwner);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredOnlyDaoFieldNotOwnerDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredOnlyDaoFieldNotOwnerDao.getDescriptionOnlyDaoFieldNotOwner(), result.getDescriptionOnlyDaoFieldNotOwner(), "Wrong description");

        assertFalse(someFilteringOwner.getFilterDaoNotOwnerA().contains(result), "An enum of type B should not be contained at FilterA");
        assertTrue(someFilteringOwner.getFilterDaoNotOwnerB().contains(result), "Wrong ref at someFilteringOwner");
        assertFalse(someFilteringOwner.getFilterDaoNotOwnerC().contains(result), "An enum of type B should not be contained at FilterC");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldNotOwnerNull() {
        assertNull(FilteringAccessMapper.convertToFilteredOnlyDaoFieldNotOwner(null), "The result should be null");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldNotOwnerAgain() {
        FilteredOnlyDaoFieldNotOwner result = FilteringAccessMapper.convertToFilteredOnlyDaoFieldNotOwner(filteredOnlyDaoFieldNotOwnerDao, mappedObjects);
        FilteredOnlyDaoFieldNotOwner convertAgainResult = FilteringAccessMapper.convertToFilteredOnlyDaoFieldNotOwner(filteredOnlyDaoFieldNotOwnerDao, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToSomeFilteringOwnerDao() {
        SomeFilteringOwnerDao result = FilteringAccessMapper.convertToSomeFilteringOwnerDao(someFilteringOwner, false);
        assertNotNull(result, "There should be any result");
        assertEquals(someFilteringOwner.getIdentification(), result.getIdentification(), "Wrong identification");

        assertEquals(0, result.getAggFiltered().size(), "Wrong number of Filter");
        assertEquals(0, result.getAggFilteredNotOwner().size(), "Wrong number of FilterNotOwner");
        assertEquals(0, result.getAggFilteredOnlyDaoField().size(), "Wrong number of FilterDao");
        assertEquals(0, result.getAggFilteredOnlyDaoFieldNotOwner().size(), "Wrong number of FilterOnlyDaoFieldNotOwner");
    }

    @Test
    public void testConvertToSomeFilteringOwnerDaoWithChildren() {
        SomeFilteringOwnerDao result = FilteringAccessMapper.convertToSomeFilteringOwnerDao(someFilteringOwner, true);
        assertNotNull(result, "There should be any result");
        assertEquals(someFilteringOwner.getIdentification(), result.getIdentification(), "Wrong identification");

        assertEquals(3, result.getAggFiltered().size(), "Wrong number of Filter");
        assertEquals(3, result.getAggFilteredNotOwner().size(), "Wrong number of FilterNotOwner");
        assertEquals(3, result.getAggFilteredOnlyDaoField().size(), "Wrong number of FilterDao");
        assertEquals(3, result.getAggFilteredOnlyDaoFieldNotOwner().size(), "Wrong number of FilterOnlyDaoFieldNotOwner");
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
    public void testConvertToSomeDifferentFilteringOwnerDao() {
        SomeDifferentFilteringNotOwnerDao result = FilteringAccessMapper.convertToSomeDifferentFilteringNotOwnerDao(someDifferentFilteringNotOwner, false);
        assertNotNull(result, "There should be any result");
        assertEquals(someDifferentFilteringNotOwner.getIdentification(), result.getIdentification(), "Wrong identification");

        assertEquals(0, result.getAggFiltered().size(), "Wrong number of Filter");
    }

    @Test
    public void testConvertToSomeDifferentFilteringOwnerDaoWithChildren() {
        SomeDifferentFilteringNotOwnerDao result = FilteringAccessMapper.convertToSomeDifferentFilteringNotOwnerDao(someDifferentFilteringNotOwner, true);
        assertNotNull(result, "There should be any result");
        assertEquals(someDifferentFilteringNotOwner.getIdentification(), result.getIdentification(), "Wrong identification");

        assertEquals(3, result.getAggFiltered().size(), "Wrong number of Filter");
    }

    @Test
    public void testConvertToSomeDifferentFilteringOwnerDaoWithParent() {
        SomeDifferentFilteringNotOwnerDao result = FilteringAccessMapper.convertToSomeDifferentFilteringNotOwnerDao(someDifferentFilteringNotOwner, false, rootDao);
        assertNotNull(result, "There should be any result");
        assertEquals(someDifferentFilteringNotOwner.getIdentification(), result.getIdentification(), "Wrong identification");

        assertEquals(rootDao.getNonOwnerFiltering(), result, "Wrong ref at root");
    }

    @Test
    public void testConvertToSomeDifferentFilteringOwnerDaoNull() {
        assertNull(FilteringAccessMapper.convertToSomeDifferentFilteringNotOwnerDao(null, false), "The result should be null");
    }

    @Test
    public void testConvertToSomeDifferentFilteringOwnerDaoAgain() {
        SomeDifferentFilteringNotOwnerDao result = FilteringAccessMapper.convertToSomeDifferentFilteringNotOwnerDao(someDifferentFilteringNotOwner, false, mappedDaoObjects);
        SomeDifferentFilteringNotOwnerDao convertAgainResult = FilteringAccessMapper.convertToSomeDifferentFilteringNotOwnerDao(someDifferentFilteringNotOwner, false, mappedDaoObjects);
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
    public void testConvertToFilteredDaoWithDifferentParent() {
        FilteredDao result = FilteringAccessMapper.convertToFilteredDao(filtered, someDifferentFilteringNotOwnerDao, AnyEnumType.ENUM_VALUE_A);
        assertNotNull(result, "There should be any result");
        assertEquals(filtered.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filtered.getDescription(), result.getDescription(), "Wrong description");
        assertEquals(filtered.getSomeEnum(), result.getSomeEnum(), "Wrong some enum");

        assertTrue(someDifferentFilteringNotOwnerDao.getAggFiltered()
                .stream()
                .anyMatch(c -> AnyEnumType.ENUM_VALUE_A.equals(c.getFilterAnyEnumType()) && c.getFiltered().equals(result)), "Wrong ref at someFilteringOwner");
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
    public void testConvertToFilteredNotOwnerDao() {
        FilteredNotOwnerDao result = FilteringAccessMapper.convertToFilteredNotOwnerDao(filteredNotOwner);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredNotOwner.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredNotOwner.getDescriptionNotOwner(), result.getDescriptionNotOwner(), "Wrong description");
        assertEquals(filteredNotOwner.getSomeEnumNotOwner(), result.getSomeEnumNotOwner(), "Wrong some enum");
    }

    @Test
    public void testConvertToFilteredNotOwnerDaoWithParent() {
        FilteredNotOwnerDao result = FilteringAccessMapper.convertToFilteredNotOwnerDao(filteredNotOwner, someFilteringOwnerDao);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredNotOwner.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredNotOwner.getDescriptionNotOwner(), result.getDescriptionNotOwner(), "Wrong description");
        assertEquals(filteredNotOwner.getSomeEnumNotOwner(), result.getSomeEnumNotOwner(), "Wrong some enum");

        assertTrue(someFilteringOwnerDao.getAggFilteredNotOwner().stream().anyMatch(c -> c.getFilteredNotOwner().equals(result)), "Wrong ref at someFilteringOwner");
    }

    @Test
    public void testConvertToFilteredNotOwnerDaoNull() {
        assertNull(FilteringAccessMapper.convertToFilteredNotOwnerDao(null), "The result should be null");
    }

    @Test
    public void testConvertToFilteredNotOwnerDaoAgain() {
        FilteredNotOwnerDao result = FilteringAccessMapper.convertToFilteredNotOwnerDao(filteredNotOwner, mappedDaoObjects);
        FilteredNotOwnerDao convertAgainResult = FilteringAccessMapper.convertToFilteredNotOwnerDao(filteredNotOwner, mappedDaoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldDao() {
        FilteredOnlyDaoFieldDao result = FilteringAccessMapper.convertToFilteredOnlyDaoFieldDao(filteredOnlyDaoField, AnyEnumType.ENUM_VALUE_A);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredOnlyDaoField.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredOnlyDaoField.getDescriptionOnlyDaoField(), result.getDescriptionOnlyDaoField(), "Wrong description");
        assertEquals(AnyEnumType.ENUM_VALUE_A, result.getSomeEnumOnlyDaoField(), "Wrong some enum");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldDaoWithParent() {
        FilteredOnlyDaoFieldDao result = FilteringAccessMapper.convertToFilteredOnlyDaoFieldDao(filteredOnlyDaoField, someFilteringOwnerDao, AnyEnumType.ENUM_VALUE_C);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredOnlyDaoField.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredOnlyDaoField.getDescriptionOnlyDaoField(), result.getDescriptionOnlyDaoField(), "Wrong description");
        assertEquals(AnyEnumType.ENUM_VALUE_C, result.getSomeEnumOnlyDaoField(), "Wrong some enum");

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
    public void testConvertToFilteredOnlyDaoFieldNotOwnerDao() {
        FilteredOnlyDaoFieldNotOwnerDao result = FilteringAccessMapper.convertToFilteredOnlyDaoFieldNotOwnerDao(filteredOnlyDaoFieldNotOwner, AnyEnumType.ENUM_VALUE_A);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredOnlyDaoFieldNotOwner.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredOnlyDaoFieldNotOwner.getDescriptionOnlyDaoFieldNotOwner(), result.getDescriptionOnlyDaoFieldNotOwner(), "Wrong description");
        assertEquals(AnyEnumType.ENUM_VALUE_A, result.getSomeEnumOnlyDaoFieldNotOwner(), "Wrong some enum");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldDaoNotOwnerWithParent() {
        FilteredOnlyDaoFieldNotOwnerDao result = FilteringAccessMapper.convertToFilteredOnlyDaoFieldNotOwnerDao(filteredOnlyDaoFieldNotOwner, someFilteringOwnerDao, AnyEnumType.ENUM_VALUE_C);
        assertNotNull(result, "There should be any result");
        assertEquals(filteredOnlyDaoFieldNotOwner.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(filteredOnlyDaoFieldNotOwner.getDescriptionOnlyDaoFieldNotOwner(), result.getDescriptionOnlyDaoFieldNotOwner(), "Wrong description");
        assertEquals(AnyEnumType.ENUM_VALUE_C, result.getSomeEnumOnlyDaoFieldNotOwner(), "Wrong some enum");

        assertTrue(someFilteringOwnerDao.getAggFilteredOnlyDaoFieldNotOwner().stream().anyMatch(c -> c.getFilteredOnlyDaoFieldNotOwner().equals(result)), "An enum of type C should be contained at FilterDao");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldDaoNotOwnerNull() {
        assertNull(FilteringAccessMapper.convertToFilteredOnlyDaoFieldNotOwnerDao(null, AnyEnumType.ENUM_VALUE_B), "The result should be null");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldDaoNotOwnerAgain() {
        FilteredOnlyDaoFieldNotOwnerDao result = FilteringAccessMapper.convertToFilteredOnlyDaoFieldNotOwnerDao(filteredOnlyDaoFieldNotOwner, AnyEnumType.ENUM_VALUE_A, mappedDaoObjects);
        FilteredOnlyDaoFieldNotOwnerDao convertAgainResult = FilteringAccessMapper.convertToFilteredOnlyDaoFieldNotOwnerDao(filteredOnlyDaoFieldNotOwner, AnyEnumType.ENUM_VALUE_A, mappedDaoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testGetInstance() {
        FilteringAccessMapper firstInstance = FilteringAccessMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, FilteringAccessMapper.getInstance(), "Any other instance should be the same");
    }
}
