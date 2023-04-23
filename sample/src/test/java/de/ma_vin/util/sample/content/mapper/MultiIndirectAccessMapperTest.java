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
    private MultiRefIndirectSelfReferenceDao multiRefIndirectSelfReferenceDao;
    private MultiRefIndirectSelfReferenceDao subMultiRefIndirectSelfReferenceDao;

    private Root root;
    private MultiRefIndirectParent multiRefIndirectParent;
    private MultiRefOtherIndirectParent multiRefOtherIndirectParent;
    private MultiRefIndirectSelfReference multiRefIndirectSelfReference;
    private MultiRefIndirectSelfReference subMultiRefIndirectSelfReference;

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
        multiRefIndirectSelfReferenceDao = createMultiRefIndirectSelfReferenceDao(getNextId());
        subMultiRefIndirectSelfReferenceDao = createMultiRefIndirectSelfReferenceDao(getNextId());
        rootDao = createRootDao(getNextId());

        initObjectFactory();
        multiRefIndirectParent = createMultiRefIndirectParent(getNextId());
        addToCreatedMap(multiRefIndirectParent);
        multiRefOtherIndirectParent = createMultiRefOtherIndirectParentWithChildren(getNextId(), multiRefIndirectParent.getIdentification());
        multiRefIndirectSelfReference = createMultiRefIndirectSelfReference(getNextId());
        subMultiRefIndirectSelfReference = createMultiRefIndirectSelfReference(getNextId());
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

        assertTrue(root.getMultiRefIndirectParent().contains(result), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefIndirectParentWithOtherParent() {
        MultiRefIndirectParent result = MultiIndirectAccessMapper.convertToMultiRefIndirectParent(multiRefIndirectParentDao, multiRefOtherIndirectParent);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefIndirectParentDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefIndirectParentDao.getDescription(), result.getDescription(), "Wrong description");

        assertTrue(multiRefOtherIndirectParent.getMultiIndirectRef().contains(result), "Wrong multi ref at root");
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

        assertEquals(0, result.getMultiIndirectRef().size(), "Wrong number of MultiRefs");
    }

    @Test
    public void testConvertToMultiRefOtherIndirectParentWithChildren() {
        MultiRefOtherIndirectParent result = MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParentDao, true);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOtherIndirectParentDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOtherIndirectParentDao.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(multiRefOtherIndirectParentDao.getMultiIndirectRef().size(), result.getMultiIndirectRef().size(), "Wrong number of MultiRefs");
        assertTrue(result.getMultiIndirectRef().stream().anyMatch(o -> o.getIdentification().equals(multiRefIndirectParentDao.getIdentification())), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefOtherIndirectParentWithParent() {
        MultiRefOtherIndirectParent result = MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParentDao, false, root);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOtherIndirectParentDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOtherIndirectParentDao.getDescription(), result.getDescription(), "Wrong description");

        assertTrue(root.getMultiRefIndirectOtherParent().contains(result), "Wrong multi ref at root");
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
    public void testConvertToMultiRefIndirectSelfReference() {
        MultiRefIndirectSelfReference result = MultiIndirectAccessMapper.convertToMultiRefIndirectSelfReference(multiRefIndirectSelfReferenceDao, false);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefIndirectSelfReferenceDao.getIdentification(), result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToMultiRefIndirectSelfReferenceWithChildren() {
        MultiRefIndirectSelfReferenceToMultiRefIndirectSelfReferenceDao con = new MultiRefIndirectSelfReferenceToMultiRefIndirectSelfReferenceDao();
        con.setSubMultiRefIndirectSelfReference(subMultiRefIndirectSelfReferenceDao);
        con.setMultiRefIndirectSelfReference(multiRefIndirectSelfReferenceDao);
        multiRefIndirectSelfReferenceDao.getSelfRefs().add(con);
        MultiRefIndirectSelfReference result = MultiIndirectAccessMapper.convertToMultiRefIndirectSelfReference(multiRefIndirectSelfReferenceDao, true);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefIndirectSelfReferenceDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(1, result.getSelfRefs().size(), "Wrong number of children");
    }

    @Test
    public void testConvertToMultiRefIndirectSelfReferenceWithParent() {
        MultiRefIndirectSelfReference result = MultiIndirectAccessMapper.convertToMultiRefIndirectSelfReference(multiRefIndirectSelfReferenceDao, false, root);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefIndirectSelfReferenceDao.getIdentification(), result.getIdentification(), "Wrong identification");

        assertTrue(root.getMultiRefIndirectSelfReference().contains(result), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefIndirectSelfReferenceWithOtherParent() {
        MultiRefIndirectSelfReference result = MultiIndirectAccessMapper.convertToMultiRefIndirectSelfReference(subMultiRefIndirectSelfReferenceDao, false, multiRefIndirectSelfReference);
        assertNotNull(result, "There should be any result");
        assertEquals(subMultiRefIndirectSelfReferenceDao.getIdentification(), result.getIdentification(), "Wrong identification");

        assertTrue(multiRefIndirectSelfReference.getSelfRefs().contains(result), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefIndirectSelfReferenceNull() {
        assertNull(MultiIndirectAccessMapper.convertToMultiRefIndirectSelfReference(null, false), "The result should be null");
    }

    @Test
    public void testConvertToMultiRefIndirectSelfReferenceAgain() {
        MultiRefIndirectSelfReference result = MultiIndirectAccessMapper.convertToMultiRefIndirectSelfReference(multiRefIndirectSelfReferenceDao, false, mappedObjects);
        MultiRefIndirectSelfReference convertAgainResult = MultiIndirectAccessMapper.convertToMultiRefIndirectSelfReference(multiRefIndirectSelfReferenceDao, false, mappedObjects);
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

        assertTrue(rootDao.getMultiRefIndirectParent().contains(result), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefIndirectParentDaoWithOtherParent() {
        MultiRefIndirectParentDao result = MultiIndirectAccessMapper.convertToMultiRefIndirectParentDao(multiRefIndirectParent, multiRefOtherIndirectParentDao);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefIndirectParent.getDescription(), result.getDescription(), "Wrong description");

        assertTrue(multiRefOtherIndirectParentDao.getMultiIndirectRef().stream().anyMatch(o -> o.getMultiRefIndirectParent().getIdentification().equals(result.getIdentification())), "Wrong multi ref at root");
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

        assertEquals(0, result.getMultiIndirectRef().size(), "Wrong number of MultiRefs");
    }

    @Test
    public void testConvertToMultiRefOtherIndirectParentDaoWithChildren() {
        MultiRefOtherIndirectParentDao result = MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParentDao(multiRefOtherIndirectParent, true);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOtherIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOtherIndirectParent.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(multiRefOtherIndirectParent.getMultiIndirectRef().size(), result.getMultiIndirectRef().size(), "Wrong number of MultiRefs");
        assertTrue(result.getMultiIndirectRef().stream().anyMatch(o -> o.getMultiRefIndirectParent().getIdentification().equals(multiRefIndirectParent.getIdentification())), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefOtherIndirectParentDaoWithParent() {
        MultiRefOtherIndirectParentDao result = MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParentDao(multiRefOtherIndirectParent, false, rootDao);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefOtherIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(multiRefOtherIndirectParent.getDescription(), result.getDescription(), "Wrong description");

        assertTrue(rootDao.getMultiRefIndirectOtherParent().contains(result), "Wrong multi ref at root");
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
    public void testConvertToMultiRefIndirectSelfReferenceDao() {
        MultiRefIndirectSelfReferenceDao result = MultiIndirectAccessMapper.convertToMultiRefIndirectSelfReferenceDao(multiRefIndirectSelfReference, false);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefIndirectSelfReference.getIdentification(), result.getIdentification(), "Wrong identification");
    }

    @Test
    public void testConvertToMultiRefIndirectSelfReferenceDaoWithChildren() {
        multiRefIndirectSelfReference.addSelfRefs(subMultiRefIndirectSelfReference);
        MultiRefIndirectSelfReferenceDao result = MultiIndirectAccessMapper.convertToMultiRefIndirectSelfReferenceDao(multiRefIndirectSelfReference, true);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefIndirectSelfReference.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(1, result.getSelfRefs().size(), "Wrong number of children");
    }

    @Test
    public void testConvertToMultiRefIndirectSelfReferenceDaoWithParent() {
        MultiRefIndirectSelfReferenceDao result = MultiIndirectAccessMapper.convertToMultiRefIndirectSelfReferenceDao(multiRefIndirectSelfReference, false, rootDao);
        assertNotNull(result, "There should be any result");
        assertEquals(multiRefIndirectSelfReference.getIdentification(), result.getIdentification(), "Wrong identification");

        assertTrue(rootDao.getMultiRefIndirectSelfReference().contains(result), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefIndirectSelfReferenceDaoWithOtherParent() {
        MultiRefIndirectSelfReferenceDao result = MultiIndirectAccessMapper.convertToMultiRefIndirectSelfReferenceDao(subMultiRefIndirectSelfReference, false, multiRefIndirectSelfReferenceDao);
        assertNotNull(result, "There should be any result");
        assertEquals(subMultiRefIndirectSelfReference.getIdentification(), result.getIdentification(), "Wrong identification");

        assertTrue(multiRefIndirectSelfReferenceDao.getSelfRefs().stream().anyMatch(o -> o.getSubMultiRefIndirectSelfReference().getIdentification().equals(result.getIdentification())), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefIndirectSelfReferenceDaoNull() {
        assertNull(MultiIndirectAccessMapper.convertToMultiRefIndirectSelfReferenceDao(null, false), "The result should be null");
    }

    @Test
    public void testConvertToMultiRefIndirectSelfReferenceDaoAgain() {
        MultiRefIndirectSelfReferenceDao result = MultiIndirectAccessMapper.convertToMultiRefIndirectSelfReferenceDao(multiRefIndirectSelfReference, false, mappedDaoObjects);
        MultiRefIndirectSelfReferenceDao convertAgainResult = MultiIndirectAccessMapper.convertToMultiRefIndirectSelfReferenceDao(multiRefIndirectSelfReference, false, mappedDaoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testGetInstance() {
        MultiIndirectAccessMapper firstInstance = MultiIndirectAccessMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, MultiIndirectAccessMapper.getInstance(), "Any other instance should be the same");
    }
}
