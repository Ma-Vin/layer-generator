package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.dao.parent.ExtendingClassDao;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.parent.ExtendingClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.ma_vin.util.sample.content.ObjectFactory.*;
import static de.ma_vin.util.sample.content.ObjectFactory.getNextId;
import static org.junit.jupiter.api.Assertions.*;

public class ParentAccessMapperTest {
    private RootDao rootDao;
    private ExtendingClassDao extendingClassDao;

    private Root root;
    private ExtendingClass extendingClass;

    @BeforeEach
    public void setUp() {
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
    public void testConvertToExtendingClassDao() {
        ExtendingClassDao result = ParentAccessMapper.convertToExtendingClassDao(extendingClass);
        assertNotNull(result, "There should be any result");
        assertEquals(extendingClass.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(extendingClass.getDescription(), result.getDescription(), "Wrong description");
        assertEquals(extendingClass.getAdditionalDescription(), result.getAdditionalDescription(), "Wrong additional description");
    }

    @Test
    public void testConvertToExtendingClassWDaoithParent() {
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
    public void testGetInstance() {
        ParentAccessMapper firstInstance = ParentAccessMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, ParentAccessMapper.getInstance(), "Any other instance should be the same");
    }
}
