package de.ma_vin.util.sample.content.mapper;

import static de.ma_vin.util.sample.content.ObjectFactory.*;
import static org.junit.jupiter.api.Assertions.*;

import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.dao.single.SingleRefOneParentDao;
import de.ma_vin.util.sample.content.dao.single.SingleRefTwoParentsDao;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.single.SingleRefOneParent;
import de.ma_vin.util.sample.content.domain.single.SingleRefTwoParents;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SingleAccessMapperTest {
    private RootDao rootDao;
    private SingleRefOneParentDao singleRefOneParentDao;
    private SingleRefTwoParentsDao singleRefTwoParentsDao;

    private Root root;
    private SingleRefOneParent singleRefOneParent;
    private SingleRefTwoParents singleRefTwoParents;

    Map<String, IIdentifiable> mappedObjects = new HashMap<>();
    Map<String, IIdentifiableDao> mappedDaoObjects = new HashMap<>();

    @BeforeEach
    public void setUp() {
        mappedObjects.clear();
        mappedDaoObjects.clear();

        initObjectFactory();
        singleRefOneParentDao = createSingleRefOneParentDaoWithChildren(getNextId());
        singleRefTwoParentsDao = createSingleRefTwoParentsDao(getNextId());
        rootDao = createRootDao(getNextId());

        initObjectFactory();
        singleRefOneParent = createSingleRefOneParentWithChildren(getNextId());
        singleRefTwoParents = createSingleRefTwoParents(getNextId());
        root = createRoot(getNextId());
    }

    @Test
    public void testConvertToSingleRefOneParent() {
        SingleRefOneParent result = SingleAccessMapper.convertToSingleRefOneParent(singleRefOneParentDao);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefOneParentDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefOneParentDao.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToSingleRefOneParentWithParent() {
        SingleRefOneParent result = SingleAccessMapper.convertToSingleRefOneParent(singleRefOneParentDao, root);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefOneParentDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefOneParentDao.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, root.getSingleRef(), "Wrong single ref at root");
    }

    @Test
    public void testConvertToSingleRefOneParentNull() {
        assertNull(SingleAccessMapper.convertToSingleRefOneParent(null), "The result should be null");
    }

    @Test
    public void testConvertToSingleRefOneParentAgain() {
        SingleRefOneParent result = SingleAccessMapper.convertToSingleRefOneParent(singleRefOneParentDao, mappedObjects);
        SingleRefOneParent convertAgainResult = SingleAccessMapper.convertToSingleRefOneParent(singleRefOneParentDao, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToSingleRefTwoParents() {
        SingleRefTwoParents result = SingleAccessMapper.convertToSingleRefTwoParents(singleRefTwoParentsDao);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefTwoParentsDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefTwoParentsDao.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToSingleRefTwoParentsWithRootParent() {
        SingleRefTwoParents result = SingleAccessMapper.convertToSingleRefTwoParents(singleRefTwoParentsDao, root);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefTwoParentsDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefTwoParentsDao.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, root.getAnotherSingleRef(), "Wrong another single ref at root");
    }

    @Test
    public void testConvertToSingleRefTwoParentsWithOtherParent() {
        SingleRefTwoParents result = SingleAccessMapper.convertToSingleRefTwoParents(singleRefTwoParentsDao, singleRefOneParent);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefTwoParentsDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefTwoParentsDao.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, singleRefOneParent.getSingleRef(), "Wrong single ref at root");
    }

    @Test
    public void testConvertToSingleRefTwoParentsNull() {
        assertNull(SingleAccessMapper.convertToSingleRefTwoParents(null), "The result should be null");
    }

    @Test
    public void testConvertToSingleRefTwoParentsAgain() {
        SingleRefTwoParents result = SingleAccessMapper.convertToSingleRefTwoParents(singleRefTwoParentsDao, mappedObjects);
        SingleRefTwoParents convertAgainResult = SingleAccessMapper.convertToSingleRefTwoParents(singleRefTwoParentsDao, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToSingleRefOneParentDao() {
        SingleRefOneParentDao result = SingleAccessMapper.convertToSingleRefOneParentDao(singleRefOneParent);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefOneParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefOneParent.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToSingleRefOneParentDaoWithParent() {
        SingleRefOneParentDao result = SingleAccessMapper.convertToSingleRefOneParentDao(singleRefOneParent, rootDao);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefOneParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefOneParent.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, rootDao.getSingleRef(), "Wrong single ref at root");
        assertEquals(rootDao, result.getParentRoot(), "Wrong parent");
    }

    @Test
    public void testConvertToSingleRefOneParentDaoNull() {
        assertNull(SingleAccessMapper.convertToSingleRefOneParentDao(null), "The result should be null");
    }

    @Test
    public void testConvertToSingleRefOneParentDaoAgain() {
        SingleRefOneParentDao result = SingleAccessMapper.convertToSingleRefOneParentDao(singleRefOneParent, mappedDaoObjects);
        SingleRefOneParentDao convertAgainResult = SingleAccessMapper.convertToSingleRefOneParentDao(singleRefOneParent, mappedDaoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToSingleRefTwoParentsDao() {
        SingleRefTwoParentsDao result = SingleAccessMapper.convertToSingleRefTwoParentsDao(singleRefTwoParents);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefTwoParentsDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefTwoParentsDao.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToSingleRefTwoParentsDaoWithRootParent() {
        SingleRefTwoParentsDao result = SingleAccessMapper.convertToSingleRefTwoParentsDao(singleRefTwoParents, rootDao);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefTwoParents.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefTwoParents.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, rootDao.getAnotherSingleRef(), "Wrong another single ref at root");
        assertEquals(rootDao, result.getParentRoot(), "Wrong parent");
    }

    @Test
    public void testConvertToSingleRefTwoParentsDaoWithOtherParent() {
        SingleRefTwoParentsDao result = SingleAccessMapper.convertToSingleRefTwoParentsDao(singleRefTwoParents, singleRefOneParentDao);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefTwoParents.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefTwoParents.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, singleRefOneParentDao.getSingleRef(), "Wrong single ref at root");
        assertEquals(singleRefOneParentDao, result.getParentSingleRefOneParent(), "Wrong parent");
    }

    @Test
    public void testConvertToSingleRefTwoParentsDaoNull() {
        assertNull(SingleAccessMapper.convertToSingleRefTwoParentsDao(null), "The result should be null");
    }

    @Test
    public void testConvertToSingleRefTwoParentsDaoAgain() {
        SingleRefTwoParentsDao result = SingleAccessMapper.convertToSingleRefTwoParentsDao(singleRefTwoParents, mappedDaoObjects);
        SingleRefTwoParentsDao convertAgainResult = SingleAccessMapper.convertToSingleRefTwoParentsDao(singleRefTwoParents, mappedDaoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testGetInstance() {
        SingleAccessMapper firstInstance = SingleAccessMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, SingleAccessMapper.getInstance(), "Any other instance should be the same");
    }
}
