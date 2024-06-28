package com.github.ma_vin.util.layer_generator.sample.content.mapper;

import static com.github.ma_vin.util.layer_generator.sample.content.ObjectFactory.*;
import static org.junit.jupiter.api.Assertions.*;

import com.github.ma_vin.util.layer_generator.sample.content.dao.IIdentifiableDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.RootDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.single.indirect.SingleRefIndirectParentDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.single.indirect.SingleRefOtherIndirectParentDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.content.domain.Root;
import com.github.ma_vin.util.layer_generator.sample.content.domain.single.indirect.SingleRefIndirectParent;
import com.github.ma_vin.util.layer_generator.sample.content.domain.single.indirect.SingleRefOtherIndirectParent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SingleIndirectAccessMapperTest {

    private RootDao rootDao;
    private SingleRefIndirectParentDao singleRefIndirectParentDao;
    private SingleRefOtherIndirectParentDao singleRefOtherIndirectParentDao;

    private Root root;
    private SingleRefIndirectParent singleRefIndirectParent;
    private SingleRefOtherIndirectParent singleRefOtherIndirectParent;

    Map<String, IIdentifiable> mappedObjects = new HashMap<>();
    Map<String, IIdentifiableDao> mappedDaoObjects = new HashMap<>();

    @BeforeEach
    public void setUp() {
        mappedObjects.clear();
        mappedDaoObjects.clear();

        initObjectFactory();
        singleRefIndirectParentDao = createSingleRefIndirectParentDao(getNextId());
        singleRefOtherIndirectParentDao = createSingleRefOtherIndirectParentDao(getNextId());
        rootDao = createRootDao(getNextId());

        initObjectFactory();
        singleRefIndirectParent = createSingleRefIndirectParent(getNextId());
        singleRefOtherIndirectParent = createSingleRefOtherIndirectParent(getNextId());
        root = createRoot(getNextId());
    }

    @Test
    public void testConvertToSingleRefIndirectParent() {
        SingleRefIndirectParent result = SingleIndirectAccessMapper.convertToSingleRefIndirectParent(singleRefIndirectParentDao);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefIndirectParentDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefIndirectParentDao.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToSingleRefIndirectParentWithParent() {
        SingleRefIndirectParent result = SingleIndirectAccessMapper.convertToSingleRefIndirectParent(singleRefIndirectParentDao, root);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefIndirectParentDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefIndirectParentDao.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, root.getSingleRefIndirectParent(), "Wrong single ref indirect at root");
    }

    @Test
    public void testConvertToSingleRefIndirectParentWithOtherParent() {
        SingleRefIndirectParent result = SingleIndirectAccessMapper.convertToSingleRefIndirectParent(singleRefIndirectParentDao, singleRefOtherIndirectParent);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefIndirectParentDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefIndirectParentDao.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, singleRefOtherIndirectParent.getSingleIndirectRef(), "Wrong single ref indirect at root");
    }

    @Test
    public void testConvertToSingleRefIndirectParentNull() {
        assertNull(SingleIndirectAccessMapper.convertToSingleRefIndirectParent(null), "The result should be null");
    }

    @Test
    public void testConvertToSingleRefIndirectParentAgain() {
        SingleRefIndirectParent result = SingleIndirectAccessMapper.convertToSingleRefIndirectParent(singleRefIndirectParentDao, mappedObjects);
        SingleRefIndirectParent convertAgainResult = SingleIndirectAccessMapper.convertToSingleRefIndirectParent(singleRefIndirectParentDao, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToSingleRefOtherIndirectParent() {
        SingleRefOtherIndirectParent result = SingleIndirectAccessMapper.convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParentDao);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefOtherIndirectParentDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefOtherIndirectParentDao.getDescription(), result.getDescription(), "Wrong description");
        assertNull(result.getSingleIndirectRef(), "There should be any reference");
    }

    @Test
    public void testConvertToSingleRefOtherIndirectParentWithChildren() {
        addToCreatedMap(singleRefIndirectParentDao);
        singleRefOtherIndirectParentDao = createSingleRefOtherIndirectParentDaoWithChildren(getNextId(), singleRefIndirectParentDao.getIdentification());

        SingleRefOtherIndirectParent result = SingleIndirectAccessMapper.convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParentDao);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefOtherIndirectParentDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefOtherIndirectParentDao.getDescription(), result.getDescription(), "Wrong description");
        assertNotNull(result.getSingleIndirectRef(), "There should be some reference");
        assertEquals(singleRefIndirectParentDao.getIdentification(), result.getSingleIndirectRef().getIdentification(), "Wrong identification at reference");
    }

    @Test
    public void testConvertToSingleRefOtherIndirectParentWithParent() {
        SingleRefOtherIndirectParent result = SingleIndirectAccessMapper.convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParentDao, root);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefOtherIndirectParentDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefOtherIndirectParentDao.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, root.getSingleRefIndirectOtherParent(), "Wrong single ref indirect at root");
    }

    @Test
    public void testConvertToSingleRefOtherIndirectParentNull() {
        assertNull(SingleIndirectAccessMapper.convertToSingleRefOtherIndirectParent(null), "The result should be null");
    }

    @Test
    public void testConvertToSingleRefOtherIndirectParentAgain() {
        SingleRefOtherIndirectParent result = SingleIndirectAccessMapper.convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParentDao, mappedObjects);
        SingleRefOtherIndirectParent convertAgainResult = SingleIndirectAccessMapper.convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParentDao, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToSingleRefIndirectParentDao() {
        SingleRefIndirectParentDao result = SingleIndirectAccessMapper.convertToSingleRefIndirectParentDao(singleRefIndirectParent);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefIndirectParent.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToSingleRefIndirectParentDaoWithParent() {
        SingleRefIndirectParentDao result = SingleIndirectAccessMapper.convertToSingleRefIndirectParentDao(singleRefIndirectParent, rootDao);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefIndirectParent.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, rootDao.getSingleRefIndirectParent(), "Wrong single ref indirect at root");
    }

    @Test
    public void testConvertToSingleRefIndirectParentDaoWithOtherParent() {
        SingleRefIndirectParentDao result = SingleIndirectAccessMapper.convertToSingleRefIndirectParentDao(singleRefIndirectParent, singleRefOtherIndirectParentDao);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefIndirectParent.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, singleRefOtherIndirectParentDao.getSingleIndirectRef(), "Wrong single ref indirect at root");
    }

    @Test
    public void testConvertToSingleRefIndirectParentDaoNull() {
        assertNull(SingleIndirectAccessMapper.convertToSingleRefIndirectParentDao(null), "The result should be null");
    }

    @Test
    public void testConvertToSingleRefIndirectParentDaoAgain() {
        SingleRefIndirectParentDao result = SingleIndirectAccessMapper.convertToSingleRefIndirectParentDao(singleRefIndirectParent, mappedDaoObjects);
        SingleRefIndirectParentDao convertAgainResult = SingleIndirectAccessMapper.convertToSingleRefIndirectParentDao(singleRefIndirectParent, mappedDaoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToSingleRefOtherIndirectParentDao() {
        SingleRefOtherIndirectParentDao result = SingleIndirectAccessMapper.convertToSingleRefOtherIndirectParentDao(singleRefOtherIndirectParent);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefOtherIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefOtherIndirectParent.getDescription(), result.getDescription(), "Wrong description");
        assertNull(result.getSingleIndirectRef(), "There should be any reference");
    }

    @Test
    public void testConvertToSingleRefOtherIndirectParentDaoWithChildren() {
        addToCreatedMap(singleRefIndirectParent);
        singleRefOtherIndirectParent = createSingleRefOtherIndirectParentWithChildren(getNextId(), singleRefIndirectParentDao.getIdentification());

        SingleRefOtherIndirectParentDao result = SingleIndirectAccessMapper.convertToSingleRefOtherIndirectParentDao(singleRefOtherIndirectParent);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefOtherIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefOtherIndirectParent.getDescription(), result.getDescription(), "Wrong description");
        assertNotNull(result.getSingleIndirectRef(), "There should be some reference");
        assertEquals(singleRefIndirectParent.getIdentification(), result.getSingleIndirectRef().getIdentification(), "Wrong identification at reference");
    }

    @Test
    public void testConvertToSingleRefOtherIndirectParentDaoWithParent() {
        SingleRefOtherIndirectParentDao result = SingleIndirectAccessMapper.convertToSingleRefOtherIndirectParentDao(singleRefOtherIndirectParent, rootDao);
        assertNotNull(result, "There should be any result");
        assertEquals(singleRefOtherIndirectParent.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(singleRefOtherIndirectParent.getDescription(), result.getDescription(), "Wrong description");

        assertEquals(result, rootDao.getSingleRefIndirectOtherParent(), "Wrong single ref indirect at root");
    }

    @Test
    public void testConvertToSingleRefOtherIndirectParentDaoNull() {
        assertNull(SingleIndirectAccessMapper.convertToSingleRefOtherIndirectParentDao(null), "The result should be null");
    }

    @Test
    public void testConvertToSingleRefOtherIndirectParentDaoAgain() {
        SingleRefOtherIndirectParentDao result = SingleIndirectAccessMapper.convertToSingleRefOtherIndirectParentDao(singleRefOtherIndirectParent, mappedDaoObjects);
        SingleRefOtherIndirectParentDao convertAgainResult = SingleIndirectAccessMapper.convertToSingleRefOtherIndirectParentDao(singleRefOtherIndirectParent, mappedDaoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testGetInstance() {
        SingleIndirectAccessMapper firstInstance = SingleIndirectAccessMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, SingleIndirectAccessMapper.getInstance(), "Any other instance should be the same");
    }
}
