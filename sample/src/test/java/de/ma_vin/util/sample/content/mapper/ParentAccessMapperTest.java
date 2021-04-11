package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.dao.parent.ExtendingClassDao;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.parent.ExtendingClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static de.ma_vin.util.sample.content.ObjectFactory.*;
import static de.ma_vin.util.sample.content.ObjectFactory.getNextId;
import static org.junit.jupiter.api.Assertions.*;

public class ParentAccessMapperTest {
    private RootDao rootDao;
    private ExtendingClassDao extendingClassDao;

    private Root root;
    private ExtendingClass extendingClass;

    Map<String, IIdentifiable> mappedObjects = new HashMap<>();
    Map<String, IIdentifiableDao> mappedDaoObjects = new HashMap<>();

    @BeforeEach
    public void setUp() {
        mappedObjects.clear();
        mappedDaoObjects.clear();

        initObjectFactory();
        extendingClassDao = createExtendingClassDao(getNextId());
        rootDao = createRootDao(getNextId());

        initObjectFactory();
        extendingClass = createExtendingClass(getNextId());
        root = createRoot(getNextId());
    }

    @Test
    public void testConvertToExtendingClass() {
        ExtendingClass result = ParentAccessMapper.convertToExtendingClass(extendingClassDao);
        assertNotNull(result, "There should be any result");
        assertEquals(extendingClassDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(extendingClassDao.getDescription(), result.getDescription(), "Wrong description");
        assertEquals(extendingClassDao.getAdditionalDescription(), result.getAdditionalDescription(), "Wrong additional description");
    }

    @Test
    public void testConvertToExtendingClassWithParent() {
        ExtendingClass result = ParentAccessMapper.convertToExtendingClass(extendingClassDao, root);
        assertNotNull(result, "There should be any result");
        assertEquals(extendingClassDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(extendingClassDao.getDescription(), result.getDescription(), "Wrong description");
        assertEquals(extendingClassDao.getAdditionalDescription(), result.getAdditionalDescription(), "Wrong additional description");

        assertTrue(root.getExtendings().contains(result), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefOneParentNull() {
        assertNull(ParentAccessMapper.convertToExtendingClass(null), "The result should be null");
    }

    @Test
    public void testConvertToMultiRefOneParentAgain() {
        ExtendingClass result = ParentAccessMapper.convertToExtendingClass(extendingClassDao, mappedObjects);
        ExtendingClass convertAgainResult = ParentAccessMapper.convertToExtendingClass(extendingClassDao, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToExtendingClassDao() {
        ExtendingClassDao result = ParentAccessMapper.convertToExtendingClassDao(extendingClass);
        assertNotNull(result, "There should be any result");
        assertEquals(extendingClass.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(extendingClass.getDescription(), result.getDescription(), "Wrong description");
        assertEquals(extendingClass.getAdditionalDescription(), result.getAdditionalDescription(), "Wrong additional description");
    }

    @Test
    public void testConvertToExtendingClassDaoWithParent() {
        ExtendingClassDao result = ParentAccessMapper.convertToExtendingClassDao(extendingClass, rootDao);
        assertNotNull(result, "There should be any result");
        assertEquals(extendingClass.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(extendingClass.getDescription(), result.getDescription(), "Wrong description");
        assertEquals(extendingClass.getAdditionalDescription(), result.getAdditionalDescription(), "Wrong additional description");

        assertTrue(rootDao.getExtendings().contains(result), "Wrong multi ref at root");
    }

    @Test
    public void testConvertToMultiRefOneParentDaoNull() {
        assertNull(ParentAccessMapper.convertToExtendingClassDao(null), "The result should be null");
    }

    @Test
    public void testConvertToMultiRefOneParentDaoAgain() {
        ExtendingClassDao result =  ParentAccessMapper.convertToExtendingClassDao(extendingClass, mappedDaoObjects);
        ExtendingClassDao convertAgainResult =  ParentAccessMapper.convertToExtendingClassDao(extendingClass, mappedDaoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testGetInstance() {
        ParentAccessMapper firstInstance = ParentAccessMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, ParentAccessMapper.getInstance(), "Any other instance should be the same");
    }
}
