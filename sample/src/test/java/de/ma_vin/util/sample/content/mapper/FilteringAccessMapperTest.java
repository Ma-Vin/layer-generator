package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.dao.filtering.*;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.filtering.*;
import de.ma_vin.util.sample.given.AnyEnumType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @BeforeEach
    public void setUp() {
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

        assertEquals(0, result.getFilterAs().size(), "Wrong number of FilterA");
        assertEquals(0, result.getFilterBs().size(), "Wrong number of FilterB");
        assertEquals(0, result.getFilterCs().size(), "Wrong number of FilterC");
        assertEquals(0, result.getFilterDaoAs().size(), "Wrong number of FilterDaoA");
        assertEquals(0, result.getFilterDaoBs().size(), "Wrong number of FilterDaoB");
        assertEquals(0, result.getFilterDaoCs().size(), "Wrong number of FilterDaoC");
    }

    @Test
    public void testConvertToSomeFilteringOwnerWithChildren() {
        SomeFilteringOwner result = FilteringAccessMapper.convertToSomeFilteringOwner(someFilteringOwnerDao, true);
        assertNotNull(result, "There should be any result");
        assertEquals(someFilteringOwnerDao.getIdentification(), result.getIdentification(), "Wrong identification");

        assertEquals(1, result.getFilterAs().size(), "Wrong number of FilterA");
        result.getFilterAs().forEach(f -> assertEquals(AnyEnumType.ENUM_VALUE_A, f.getSomeEnum(), "Wrong enum at filter"));
        assertEquals(1, result.getFilterBs().size(), "Wrong number of FilterB");
        result.getFilterBs().forEach(f -> assertEquals(AnyEnumType.ENUM_VALUE_B, f.getSomeEnum(), "Wrong enum at filter"));
        assertEquals(1, result.getFilterCs().size(), "Wrong number of FilterC");
        result.getFilterCs().forEach(f -> assertEquals(AnyEnumType.ENUM_VALUE_C, f.getSomeEnum(), "Wrong enum at filter"));
        assertEquals(1, result.getFilterDaoAs().size(), "Wrong number of FilterDaoA");
        assertEquals(1, result.getFilterDaoBs().size(), "Wrong number of FilterDaoB");
        assertEquals(1, result.getFilterDaoCs().size(), "Wrong number of FilterDaoC");
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

        assertTrue(someFilteringOwner.getFilterAs().contains(result), "Wrong ref at someFilteringOwner");
        assertFalse(someFilteringOwner.getFilterBs().contains(result), "An enum of type A should not be contained at FilterB");
        assertFalse(someFilteringOwner.getFilterCs().contains(result), "An enum of type A should not be contained at FilterC");
    }

    @Test
    public void testConvertToFilteredNull() {
        assertNull(FilteringAccessMapper.convertToFiltered(null), "The result should be null");
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

        assertFalse(someFilteringOwner.getFilterDaoAs().contains(result), "An enum of type B should not be contained at FilterA");
        assertTrue(someFilteringOwner.getFilterDaoBs().contains(result), "Wrong ref at someFilteringOwner");
        assertFalse(someFilteringOwner.getFilterDaoCs().contains(result), "An enum of type B should not be contained at FilterC");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldNull() {
        assertNull(FilteringAccessMapper.convertToFilteredOnlyDaoField(null), "The result should be null");
    }

    @Test
    public void testConvertToSomeFilteringOwnerDao() {
        SomeFilteringOwnerDao result = FilteringAccessMapper.convertToSomeFilteringOwnerDao(someFilteringOwner, false);
        assertNotNull(result, "There should be any result");
        assertEquals(someFilteringOwner.getIdentification(), result.getIdentification(), "Wrong identification");

        assertEquals(0, result.getAggFiltereds().size(), "Wrong number of Filter");
        assertEquals(0, result.getAggFilteredOnlyDaoFields().size(), "Wrong number of FilterDao");
    }

    @Test
    public void testConvertToSomeFilteringOwnerDaoWithChildren() {
        SomeFilteringOwnerDao result = FilteringAccessMapper.convertToSomeFilteringOwnerDao(someFilteringOwner, true);
        assertNotNull(result, "There should be any result");
        assertEquals(someFilteringOwner.getIdentification(), result.getIdentification(), "Wrong identification");

        assertEquals(3, result.getAggFiltereds().size(), "Wrong number of Filter");
        assertEquals(3, result.getAggFilteredOnlyDaoFields().size(), "Wrong number of FilterDao");
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

        assertTrue(someFilteringOwnerDao.getAggFiltereds().contains(result), "Wrong ref at someFilteringOwner");
    }

    @Test
    public void testConvertToFilteredDaoNull() {
        assertNull(FilteringAccessMapper.convertToFilteredDao(null), "The result should be null");
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

        assertTrue(someFilteringOwnerDao.getAggFilteredOnlyDaoFields().contains(result), "An enum of type B should be contained at FilterDao");
    }

    @Test
    public void testConvertToFilteredOnlyDaoFieldDaoNull() {
        assertNull(FilteringAccessMapper.convertToFilteredOnlyDaoFieldDao(null, AnyEnumType.ENUM_VALUE_B), "The result should be null");
    }

    @Test
    public void testGetInstance() {
        FilteringAccessMapper firstInstance = FilteringAccessMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, FilteringAccessMapper.getInstance(), "Any other instance should be the same");
    }
}
