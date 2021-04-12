package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.dao.multi.indirect.*;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.multi.indirect.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static de.ma_vin.util.sample.content.ObjectFactory.*;
import static de.ma_vin.util.sample.content.ObjectFactory.getNextId;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MultiIndirectAccessMapperTest {
    private RootDao rootDao;
    private MultiRefIndirectParentDao multiRefIndirectParentDao;
    private MultiRefOtherIndirectParentDao multiRefOtherIndirectParentDao;

    private Root root;
    private MultiRefIndirectParent multiRefIndirectParent;
    private MultiRefOtherIndirectParent multiRefOtherIndirectParent;

    Map<String, IIdentifiable> mappedObjects = new HashMap<>();
    Map<String, IIdentifiableDao> mappedDaoObjects = new HashMap<>();

    @BeforeEach
    public void setUp() {
        mappedObjects.clear();
        mappedDaoObjects.clear();

        initObjectFactory();
        multiRefIndirectParentDao = createMultiRefIndirectParentDao(getNextId());
        addToCreatedMap(multiRefIndirectParentDao);
        multiRefOtherIndirectParentDao = createMultiRefOtherIndirectParentDaoWithChildren(getNextId(), multiRefIndirectParentDao.getIdentification());
        rootDao = createRootDao(getNextId());

        initObjectFactory();
        multiRefIndirectParent = createMultiRefIndirectParent(getNextId());
        addToCreatedMap(multiRefIndirectParent);
        multiRefOtherIndirectParent = createMultiRefOtherIndirectParentWithChildren(getNextId(), multiRefIndirectParent.getIdentification());
        root = createRoot(getNextId());
    }

    @Test
    public void testConvertToMultiRefIndirectParent() {
        MultiRefIndirectParent result = MultiIndirectAccessMapper.convertToMultiRefIndirectParent(multiRefIndirectParentDao);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefIndirectParentDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefIndirectParentDao.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToMultiRefIndirectParentWithParent() {
        MultiRefIndirectParent result = MultiIndirectAccessMapper.convertToMultiRefIndirectParent(multiRefIndirectParentDao, root);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefIndirectParentDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefIndirectParentDao.getDescription(), result.getDescription(), "Wrong description");

        assertTrue(root.getMultiRefIndirectParents().contains(result), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefIndirectParentWithOtherParent() {
        MultiRefIndirectParent result = MultiIndirectAccessMapper.convertToMultiRefIndirectParent(multiRefIndirectParentDao, multiRefOtherIndirectParent);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefIndirectParentDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefIndirectParentDao.getDescription(), result.getDescription(), "Wrong description");

        assertTrue(multiRefOtherIndirectParent.getMultiIndirectRefs().contains(result), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefIndirectParentNull() {
        assertNull(MultiIndirectAccessMapper.convertToMultiRefIndirectParent(null), "The result should be null");
    }

    @Test
    public void testConvertToMultiRefIndirectParentAgain() {
        MultiRefIndirectParent result = MultiIndirectAccessMapper.convertToMultiRefIndirectParent(multiRefIndirectParentDao, mappedObjects);
        MultiRefIndirectParent convertAgainResult = MultiIndirectAccessMapper.convertToMultiRefIndirectParent(multiRefIndirectParentDao, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToMultiRefOtherIndirectParent() {
        MultiRefOtherIndirectParent result = MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParentDao, false);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOtherIndirectParentDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOtherIndirectParentDao.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(0, result.getMultiIndirectRefs().size(), "Wrong number of MultiRefs");
    }

    @Test
    public void testConvertToMultiRefOtherIndirectParentWithChildren() {
        MultiRefOtherIndirectParent result = MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParentDao, true);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOtherIndirectParentDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOtherIndirectParentDao.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(multiRefOtherIndirectParentDao.getMultiIndirectRefs().size(), result.getMultiIndirectRefs().size(), "Wrong number of MultiRefs");
        assertTrue(result.getMultiIndirectRefs().stream().anyMatch(o -> o.getIdentification().equals(multiRefIndirectParentDao.getIdentification())), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefOtherIndirectParentWithParent() {
        MultiRefOtherIndirectParent result = MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParentDao, false, root);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOtherIndirectParentDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOtherIndirectParentDao.getDescription(), result.getDescription(), "Wrong description");

        assertTrue(root.getMultiRefIndirectOtherParents().contains(result), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefOtherIndirectParentNull() {
        assertNull(MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParent(null, false), "The result should be null");
    }

    @Test
    public void testConvertToMultiRefOtherIndirectParentAgain() {
        MultiRefOtherIndirectParent result = MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParentDao, false, mappedObjects);
        MultiRefOtherIndirectParent convertAgainResult = MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParentDao, false, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToMultiRefIndirectParentDao() {
        MultiRefIndirectParentDao result = MultiIndirectAccessMapper.convertToMultiRefIndirectParentDao(multiRefIndirectParent);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefIndirectParent.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToMultiRefIndirectParentDaoWithParent() {
        MultiRefIndirectParentDao result = MultiIndirectAccessMapper.convertToMultiRefIndirectParentDao(multiRefIndirectParent, rootDao);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefIndirectParent.getDescription(), result.getDescription(), "Wrong description");

        assertTrue(rootDao.getMultiRefIndirectParents().contains(result), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefIndirectParentDaoWithOtherParent() {
        MultiRefIndirectParentDao result = MultiIndirectAccessMapper.convertToMultiRefIndirectParentDao(multiRefIndirectParent, multiRefOtherIndirectParentDao);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefIndirectParent.getDescription(), result.getDescription(), "Wrong description");

        assertTrue(multiRefOtherIndirectParentDao.getMultiIndirectRefs().stream().anyMatch(o -> o.getMultiRefIndirectParent().getIdentification().equals(result.getIdentification())), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefIndirectParentDaoNull() {
        assertNull(MultiIndirectAccessMapper.convertToMultiRefIndirectParentDao(null), "The result should be null");
    }

    @Test
    public void testConvertToMultiRefIndirectParentDaoAgain() {
        MultiRefIndirectParentDao result = MultiIndirectAccessMapper.convertToMultiRefIndirectParentDao(multiRefIndirectParent, mappedDaoObjects);
        MultiRefIndirectParentDao convertAgainResult = MultiIndirectAccessMapper.convertToMultiRefIndirectParentDao(multiRefIndirectParent, mappedDaoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToMultiRefOtherIndirectParentDao() {
        MultiRefOtherIndirectParentDao result = MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParentDao(multiRefOtherIndirectParent, false);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOtherIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOtherIndirectParent.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(0, result.getMultiIndirectRefs().size(), "Wrong number of MultiRefs");
    }

    @Test
    public void testConvertToMultiRefOtherIndirectParentDaoWithChildren() {
        MultiRefOtherIndirectParentDao result = MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParentDao(multiRefOtherIndirectParent, true);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOtherIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOtherIndirectParent.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(multiRefOtherIndirectParent.getMultiIndirectRefs().size(), result.getMultiIndirectRefs().size(), "Wrong number of MultiRefs");
        assertTrue(result.getMultiIndirectRefs().stream().anyMatch(o -> o.getMultiRefIndirectParent().getIdentification().equals(multiRefIndirectParent.getIdentification())), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefOtherIndirectParentDaoWithParent() {
        MultiRefOtherIndirectParentDao result = MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParentDao(multiRefOtherIndirectParent, false, rootDao);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOtherIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOtherIndirectParent.getDescription(), result.getDescription(), "Wrong description");

        assertTrue(rootDao.getMultiRefIndirectOtherParents().contains(result), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefOtherIndirectParentDaoNull() {
        assertNull(MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParentDao(null, false), "The result should be null");
    }

    @Test
    public void testConvertToMultiRefOtherIndirectParentDaoAgain() {
        MultiRefOtherIndirectParentDao result = MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParentDao(multiRefOtherIndirectParent, false, mappedDaoObjects);
        MultiRefOtherIndirectParentDao convertAgainResult = MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParentDao(multiRefOtherIndirectParent, false, mappedDaoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testGetInstance() {
        MultiIndirectAccessMapper firstInstance = MultiIndirectAccessMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, MultiIndirectAccessMapper.getInstance(), "Any other instance should be the same");
    }
}
