package com.github.ma_vin.util.layer_generator.sample.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.content.dao.IIdentifiableDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.RootDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.multi.MultiRefOneParentDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.multi.MultiRefTwoParentsDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.content.domain.Root;
import com.github.ma_vin.util.layer_generator.sample.content.domain.multi.MultiRefOneParent;
import com.github.ma_vin.util.layer_generator.sample.content.domain.multi.MultiRefTwoParents;
import com.github.ma_vin.util.layer_generator.sample.extending.ExtendedMultiRefTwoParents;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.github.ma_vin.util.layer_generator.sample.content.ObjectFactory.*;
import static com.github.ma_vin.util.layer_generator.sample.content.ObjectFactory.getNextId;
import static org.junit.jupiter.api.Assertions.*;

public class MultiAccessMapperTest {
    private RootDao rootDao;
    private MultiRefOneParentDao multiRefOneParentDao;
    private MultiRefTwoParentsDao multiRefTwoParentsDao;

    private Root root;
    private MultiRefOneParent multiRefOneParent;
    private MultiRefTwoParents multiRefTwoParents;

    Map<String, IIdentifiable> mappedObjects = new HashMap<>();
    Map<String, IIdentifiableDao> mappedDaoObjects = new HashMap<>();

    @BeforeEach
    public void setUp() {
        mappedObjects.clear();
        mappedDaoObjects.clear();

        initObjectFactory();
        multiRefOneParentDao = createMultiRefOneParentDaoWithChildren(getNextId());
        multiRefTwoParentsDao = createMultiRefTwoParentsDao(getNextId());
        rootDao = createRootDao(getNextId());

        initObjectFactory();
        multiRefOneParent = createMultiRefOneParentWithChildren(getNextId());
        multiRefTwoParents = createMultiRefTwoParents(getNextId());
        root = createRoot(getNextId());
    }

    @Test
    public void testConvertToMultiRefOneParent() {
        MultiRefOneParent result = MultiAccessMapper.convertToMultiRefOneParent(multiRefOneParentDao, false);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOneParentDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOneParentDao.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(0, result.getMultiRef().size(), "Wrong number of MultiRefs");
    }

    @Test
    public void testConvertToMultiRefOneParentWithChildren() {
        MultiRefOneParent result = MultiAccessMapper.convertToMultiRefOneParent(multiRefOneParentDao, true);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOneParentDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOneParentDao.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(multiRefOneParentDao.getMultiRef().size(), result.getMultiRef().size(), "Wrong number of MultiRefs");
    }

    @Test
    public void testConvertToMultiRefOneParentWithParent() {
        MultiRefOneParent result = MultiAccessMapper.convertToMultiRefOneParent(multiRefOneParentDao, false, root);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOneParentDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOneParentDao.getDescription(), result.getDescription(), "Wrong description");

        assertTrue(root.getMultiRef().contains(result), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefOneParentNull() {
        assertNull(MultiAccessMapper.convertToMultiRefOneParent(null, false), "The result should be null");
    }

    @Test
    public void testConvertToMultiRefOneParentAgain() {
        MultiRefOneParent result = MultiAccessMapper.convertToMultiRefOneParent(multiRefOneParentDao, false, mappedObjects);
        MultiRefOneParent convertAgainResult = MultiAccessMapper.convertToMultiRefOneParent(multiRefOneParentDao, false, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToMultiRefTwoParent() {
        MultiRefTwoParents result = MultiAccessMapper.convertToMultiRefTwoParents(multiRefTwoParentsDao);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefTwoParentsDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefTwoParentsDao.getDescription(), result.getDescription(), "Wrong description");
        assertTrue(result instanceof ExtendedMultiRefTwoParents, "The result should be an extended one");
    }

    @Test
    public void testConvertToMultiRefTwoParentsWithRootParent() {
        MultiRefTwoParents result = MultiAccessMapper.convertToMultiRefTwoParents(multiRefTwoParentsDao, root);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefTwoParentsDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefTwoParentsDao.getDescription(), result.getDescription(), "Wrong description");

        assertTrue(root.getAnotherMultiRef().contains(result), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefTwoParentsWithOtherParent() {
        MultiRefTwoParents result = MultiAccessMapper.convertToMultiRefTwoParents(multiRefTwoParentsDao, multiRefOneParent);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefTwoParentsDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefTwoParentsDao.getDescription(), result.getDescription(), "Wrong description");

        assertTrue(multiRefOneParent.getMultiRef().contains(result), "Wrong multi ref at other parent");
    }

    @Test
    public void testConvertToMultiRefTwoParentsNull() {
        assertNull(MultiAccessMapper.convertToMultiRefTwoParents(null), "The result should be null");
    }

    @Test
    public void testConvertToMultiRefTwoParentsAgain() {
        MultiRefTwoParents result = MultiAccessMapper.convertToMultiRefTwoParents(multiRefTwoParentsDao, mappedObjects);
        MultiRefTwoParents convertAgainResult = MultiAccessMapper.convertToMultiRefTwoParents(multiRefTwoParentsDao, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToMultiRefOneParentDao() {
        MultiRefOneParentDao result = MultiAccessMapper.convertToMultiRefOneParentDao(multiRefOneParent, false);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOneParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOneParent.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(0, result.getMultiRef().size(), "Wrong number of MultiRefs");
    }

    @Test
    public void testConvertToMultiRefOneParentDaoWithChildren() {
        MultiRefOneParentDao result = MultiAccessMapper.convertToMultiRefOneParentDao(multiRefOneParent, true);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOneParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOneParent.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(multiRefOneParent.getMultiRef().size(), result.getMultiRef().size(), "Wrong number of MultiRefs");
    }

    @Test
    public void testConvertToMultiRefOneParentDaoWithParent() {
        MultiRefOneParentDao result = MultiAccessMapper.convertToMultiRefOneParentDao(multiRefOneParent, false, rootDao);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOneParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOneParent.getDescription(), result.getDescription(), "Wrong description");

        assertTrue(rootDao.getMultiRef().contains(result), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefOneParentDaoNull() {
        assertNull(MultiAccessMapper.convertToMultiRefOneParentDao(null, false), "The result should be null");
    }

    @Test
    public void testConvertToMultiRefOneParentDaoAgain() {
        MultiRefOneParentDao result = MultiAccessMapper.convertToMultiRefOneParentDao(multiRefOneParent, false, mappedDaoObjects);
        MultiRefOneParentDao convertAgainResult = MultiAccessMapper.convertToMultiRefOneParentDao(multiRefOneParent, false, mappedDaoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToMultiRefTwoParentsDao() {
        MultiRefTwoParentsDao result = MultiAccessMapper.convertToMultiRefTwoParentsDao(multiRefTwoParents);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefTwoParents.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefTwoParents.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToMultiRefTwoParentsDaoWithRootParent() {
        MultiRefTwoParentsDao result = MultiAccessMapper.convertToMultiRefTwoParentsDao(multiRefTwoParents, rootDao);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefTwoParents.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefTwoParents.getDescription(), result.getDescription(), "Wrong description");

        assertTrue(rootDao.getAnotherMultiRef().contains(result), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefTwoParentsDaoWithOtherParent() {
        MultiRefTwoParentsDao result = MultiAccessMapper.convertToMultiRefTwoParentsDao(multiRefTwoParents, multiRefOneParentDao);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefTwoParents.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefTwoParents.getDescription(), result.getDescription(), "Wrong description");

        assertTrue(multiRefOneParentDao.getMultiRef().contains(result), "Wrong multi ref at other parent");
    }

    @Test
    public void testConvertToMultiRefTwoParentsDaoNull() {
        assertNull(MultiAccessMapper.convertToMultiRefTwoParentsDao(null), "The result should be null");
    }

    @Test
    public void testConvertToMultiRefTwoParentsDaoAgain() {
        MultiRefTwoParentsDao result = MultiAccessMapper.convertToMultiRefTwoParentsDao(multiRefTwoParents, mappedDaoObjects);
        MultiRefTwoParentsDao convertAgainResult = MultiAccessMapper.convertToMultiRefTwoParentsDao(multiRefTwoParents, mappedDaoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testGetInstance() {
        MultiAccessMapper firstInstance = MultiAccessMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, MultiAccessMapper.getInstance(), "Any other instance should be the same");
    }
}
