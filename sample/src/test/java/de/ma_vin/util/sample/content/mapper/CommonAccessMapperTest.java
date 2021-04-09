package de.ma_vin.util.sample.content.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static de.ma_vin.util.sample.content.ObjectFactory.*;

import de.ma_vin.util.sample.content.dao.*;
import de.ma_vin.util.sample.content.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommonAccessMapperTest {

    private RootDao rootDao;
    private RootExtDao rootExtDao;

    private Root root;
    private RootExt rootExt;

    @BeforeEach
    public void setUp() {
        initObjectFactory();
        rootDao = createRootDaoWithChildren(getNextId());
        rootExtDao = createRootExtDao(getNextId());

        initObjectFactory();
        root = createRootWithChildren(getNextId());
        rootExt = createRootExt(getNextId());
    }

    @Test
    public void testConvertToRoot() {
        Root result = CommonAccessMapper.convertToRoot(rootDao, false);

        assertNotNull(result, "There should be any result");
        assertEquals(rootDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(rootDao.getDescription(), result.getDescription(), "Wrong description");

        assertNotNull(result.getSingleRef(), "There should be any SingleRef");
        assertEquals(rootDao.getSingleRef().getIdentification(), result.getSingleRef().getIdentification(), "Wrong identification at SingleRef");
        assertNotNull(result.getAnotherSingleRef(), "There should be any AnotherSingleRef");
        assertEquals(rootDao.getAnotherSingleRef().getIdentification(), result.getAnotherSingleRef().getIdentification(), "Wrong identification at AnotherSingleRef");
        assertNotNull(result.getSingleRefIndirectParent(), "There should be any SingleRefIndirectParent");
        assertEquals(rootDao.getSingleRefIndirectParent().getIdentification(), result.getSingleRefIndirectParent().getIdentification(), "Wrong identification at SingleRefIndirectParent");
        assertNotNull(result.getSingleRefIndirectOtherParent(), "There should be any SingleRefIndirectOtherParent");
        assertEquals(rootDao.getSingleRefIndirectOtherParent().getIdentification(), result.getSingleRefIndirectOtherParent().getIdentification(), "Wrong identification at SingleRefIndirectOtherParent");
        assertNotNull(result.getFiltering(), "There should be any Filtering");
        assertEquals(rootDao.getFiltering().getIdentification(), result.getFiltering().getIdentification(), "Wrong identification at Filtering");
        assertNotNull(result.getExt(), "There should be any RootExt");
        assertEquals(rootDao.getExt().getIdentification(), result.getExt().getIdentification(), "Wrong identification at RootExt");

        assertNotNull(result.getMultiRefs(), "There should be any MultiRefs list");
        assertEquals(0, result.getMultiRefs().size());
        assertNotNull(result.getAnotherMultiRefs(), "There should be any AnotherMultiRefs list");
        assertEquals(0, result.getAnotherMultiRefs().size());
        assertNotNull(result.getMultiRefIndirectParents(), "There should be any MultiRefIndirectParents list");
        assertEquals(0, result.getMultiRefIndirectParents().size());
        assertNotNull(result.getMultiRefIndirectOtherParents(), "There should be any MultiRefIndirectOtherParents list");
        assertEquals(0, result.getMultiRefIndirectOtherParents().size());
        assertNotNull(result.getExtendings(), "There should be any extending list");
        assertEquals(0, result.getExtendings().size());
    }

    @Test
    public void testConvertToRootWithChildren() {
        Root result = CommonAccessMapper.convertToRoot(rootDao, true);

        assertNotNull(result, "There should be any result");
        assertEquals(rootDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(rootDao.getDescription(), result.getDescription(), "Wrong description");

        assertNotNull(result.getSingleRef(), "There should be any SingleRef");
        assertEquals(rootDao.getSingleRef().getIdentification(), result.getSingleRef().getIdentification(), "Wrong identification at SingleRef");
        assertNotNull(result.getAnotherSingleRef(), "There should be any AnotherSingleRef");
        assertEquals(rootDao.getAnotherSingleRef().getIdentification(), result.getAnotherSingleRef().getIdentification(), "Wrong identification at AnotherSingleRef");
        assertNotNull(result.getSingleRefIndirectParent(), "There should be any SingleRefIndirectParent");
        assertEquals(rootDao.getSingleRefIndirectParent().getIdentification(), result.getSingleRefIndirectParent().getIdentification(), "Wrong identification at SingleRefIndirectParent");
        assertNotNull(result.getSingleRefIndirectOtherParent(), "There should be any SingleRefIndirectOtherParent");
        assertEquals(rootDao.getSingleRefIndirectOtherParent().getIdentification(), result.getSingleRefIndirectOtherParent().getIdentification(), "Wrong identification at SingleRefIndirectOtherParent");
        assertNotNull(result.getFiltering(), "There should be any Filtering");
        assertEquals(rootDao.getFiltering().getIdentification(), result.getFiltering().getIdentification(), "Wrong identification at Filtering");
        assertNotNull(result.getExt(), "There should be any RootExt");
        assertEquals(rootDao.getExt().getIdentification(), result.getExt().getIdentification(), "Wrong identification at RootExt");

        assertNotNull(result.getMultiRefs(), "There should be any MultiRefs list");
        assertEquals(rootDao.getMultiRefs().size(), result.getMultiRefs().size());
        assertNotNull(result.getAnotherMultiRefs(), "There should be any AnotherMultiRefs list");
        assertEquals(rootDao.getAnotherMultiRefs().size(), result.getAnotherMultiRefs().size());
        assertNotNull(result.getMultiRefIndirectParents(), "There should be any MultiRefIndirectParents list");
        assertEquals(rootDao.getMultiRefIndirectParents().size(), result.getMultiRefIndirectParents().size());
        assertNotNull(result.getMultiRefIndirectOtherParents(), "There should be any MultiRefIndirectOtherParents list");
        assertEquals(rootDao.getMultiRefIndirectOtherParents().size(), result.getMultiRefIndirectOtherParents().size());
        assertNotNull(result.getExtendings(), "There should be any extending list");
        assertEquals(rootDao.getExtendings().size(), result.getExtendings().size());
    }

    @Test
    public void testConvertToRootNull() {
        assertNull(CommonAccessMapper.convertToRoot(null, false), "The result should be null");
    }

    @Test
    public void testCreateRootExt() {
        RootExt result = CommonAccessMapper.convertToRootExt(rootExtDao);

        assertNotNull(result, "There should be any result");
        assertEquals(rootExtDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(rootExtDao.getExtendedInfo(), result.getExtendedInfo(), "Wrong ExtendedInfo");
        assertEquals(rootExtDao.getSomeInteger(), result.getSomeInteger(), "Wrong SomeInteger");
        assertEquals(rootExtDao.getSomeCustom(), result.getSomeCustom(), "Wrong SomeCustom");
        assertEquals(rootExtDao.getDaoAndDomain(), result.getDaoAndDomain(), "Wrong DaoAndDomain");
        assertEquals(rootExtDao.getTextWithDaoInfo(), result.getTextWithDaoInfo(), "Wrong TextWithDaoInfo");
        assertEquals(rootExtDao.getNumberWithDaoInfo(), result.getNumberWithDaoInfo(), "Wrong NumberWithDaoInfo");
        assertEquals(rootExtDao.getDaoEnum(), result.getDaoEnum(), "Wrong DaoEnum");
        assertEquals(rootExtDao.getDaoEnumWithText(), result.getDaoEnumWithText(), "Wrong tDaoEnumWithText");
        assertEquals(rootExtDao.getExtendedInfo(), result.getExtendedInfo(), "Wrong ExtendedInfo");
    }

    @Test
    public void testCreateRootExtWithParent() {
        RootExt result = CommonAccessMapper.convertToRootExt(rootExtDao, root);

        assertNotNull(result, "There should be any result");
        assertEquals(rootExtDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(rootExtDao.getExtendedInfo(), result.getExtendedInfo(), "Wrong ExtendedInfo");
        assertEquals(rootExtDao.getSomeInteger(), result.getSomeInteger(), "Wrong SomeInteger");
        assertEquals(rootExtDao.getSomeCustom(), result.getSomeCustom(), "Wrong SomeCustom");
        assertEquals(rootExtDao.getDaoAndDomain(), result.getDaoAndDomain(), "Wrong DaoAndDomain");
        assertEquals(rootExtDao.getTextWithDaoInfo(), result.getTextWithDaoInfo(), "Wrong TextWithDaoInfo");
        assertEquals(rootExtDao.getNumberWithDaoInfo(), result.getNumberWithDaoInfo(), "Wrong NumberWithDaoInfo");
        assertEquals(rootExtDao.getDaoEnum(), result.getDaoEnum(), "Wrong DaoEnum");
        assertEquals(rootExtDao.getDaoEnumWithText(), result.getDaoEnumWithText(), "Wrong tDaoEnumWithText");
        assertEquals(rootExtDao.getExtendedInfo(), result.getExtendedInfo(), "Wrong ExtendedInfo");

        assertEquals(root.getExt(), result, "Wrong ext at root");
    }

    @Test
    public void testCreateRootExtNull() {
        assertNull(CommonAccessMapper.convertToRootExt(null), "The result should be null");
    }

    @Test
    public void testConvertToRootDao() {
        RootDao result = CommonAccessMapper.convertToRootDao(root, false);

        assertNotNull(result, "There should be any result");
        assertEquals(root.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(root.getDescription(), result.getDescription(), "Wrong description");

        assertNotNull(result.getSingleRef(), "There should be any SingleRef");
        assertEquals(root.getSingleRef().getIdentification(), result.getSingleRef().getIdentification(), "Wrong identification at SingleRef");
        assertEquals(result, result.getSingleRef().getParentRoot(), "Wrong parent at SingleRef");
        assertNotNull(result.getAnotherSingleRef(), "There should be any AnotherSingleRef");
        assertEquals(root.getAnotherSingleRef().getIdentification(), result.getAnotherSingleRef().getIdentification(), "Wrong identification at AnotherSingleRef");
        assertEquals(result, result.getAnotherSingleRef().getParentRoot(), "Wrong parent at AnotherSingleRef");
        assertNotNull(result.getSingleRefIndirectParent(), "There should be any SingleRefIndirectParent");
        assertEquals(root.getSingleRefIndirectParent().getIdentification(), result.getSingleRefIndirectParent().getIdentification(), "Wrong identification at SingleRefIndirectParent");
        assertEquals(result, result.getSingleRefIndirectParent().getParentRoot(), "Wrong parent at SingleRefIndirectParent");
        assertNotNull(result.getSingleRefIndirectOtherParent(), "There should be any SingleRefIndirectOtherParent");
        assertEquals(root.getSingleRefIndirectOtherParent().getIdentification(), result.getSingleRefIndirectOtherParent().getIdentification(), "Wrong identification at SingleRefIndirectOtherParent");
        assertEquals(result, result.getSingleRefIndirectOtherParent().getParentRoot(), "Wrong parent at SingleRefIndirectOtherParent");
        assertNotNull(result.getFiltering(), "There should be any Filtering");
        assertEquals(root.getFiltering().getIdentification(), result.getFiltering().getIdentification(), "Wrong identification at Filtering");
        assertEquals(result, result.getFiltering().getParentRoot(), "Wrong parent at Filtering");
        assertNotNull(result.getExt(), "There should be any RootExt");
        assertEquals(root.getExt().getIdentification(), result.getExt().getIdentification(), "Wrong identification at RootExt");
        assertEquals(result, result.getExt().getParentRoot(), "Wrong parent at RootExt");

        assertNotNull(result.getMultiRefs(), "There should be any MultiRefs list");
        assertEquals(0, result.getMultiRefs().size());
        assertNotNull(result.getAnotherMultiRefs(), "There should be any AnotherMultiRefs list");
        assertEquals(0, result.getAnotherMultiRefs().size());
        assertNotNull(result.getMultiRefIndirectParents(), "There should be any MultiRefIndirectParents list");
        assertEquals(0, result.getMultiRefIndirectParents().size());
        assertNotNull(result.getMultiRefIndirectOtherParents(), "There should be any MultiRefIndirectOtherParents list");
        assertEquals(0, result.getMultiRefIndirectOtherParents().size());
        assertNotNull(result.getExtendings(), "There should be any extending list");
        assertEquals(0, result.getExtendings().size());
    }

    @Test
    public void testConvertToRootWithChildrenDao() {
        RootDao result = CommonAccessMapper.convertToRootDao(root, true);

        assertNotNull(result, "There should be any result");
        assertEquals(root.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(root.getDescription(), result.getDescription(), "Wrong description");

        assertNotNull(result.getSingleRef(), "There should be any SingleRef");
        assertEquals(root.getSingleRef().getIdentification(), result.getSingleRef().getIdentification(), "Wrong identification at SingleRef");
        assertNotNull(result.getAnotherSingleRef(), "There should be any AnotherSingleRef");
        assertEquals(root.getAnotherSingleRef().getIdentification(), result.getAnotherSingleRef().getIdentification(), "Wrong identification at AnotherSingleRef");
        assertNotNull(result.getSingleRefIndirectParent(), "There should be any SingleRefIndirectParent");
        assertEquals(root.getSingleRefIndirectParent().getIdentification(), result.getSingleRefIndirectParent().getIdentification(), "Wrong identification at SingleRefIndirectParent");
        assertNotNull(result.getSingleRefIndirectOtherParent(), "There should be any SingleRefIndirectOtherParent");
        assertEquals(root.getSingleRefIndirectOtherParent().getIdentification(), result.getSingleRefIndirectOtherParent().getIdentification(), "Wrong identification at SingleRefIndirectOtherParent");
        assertNotNull(result.getFiltering(), "There should be any Filtering");
        assertEquals(root.getFiltering().getIdentification(), result.getFiltering().getIdentification(), "Wrong identification at Filtering");
        assertNotNull(result.getExt(), "There should be any RootExt");
        assertEquals(root.getExt().getIdentification(), result.getExt().getIdentification(), "Wrong identification at RootExt");

        assertNotNull(result.getMultiRefs(), "There should be any MultiRefs list");
        assertEquals(root.getMultiRefs().size(), result.getMultiRefs().size());
        result.getMultiRefs().forEach(o -> assertEquals(result, o.getParentRoot(), "Wrong parent at " + o.toString()));
        assertNotNull(result.getAnotherMultiRefs(), "There should be any AnotherMultiRefs list");
        assertEquals(root.getAnotherMultiRefs().size(), result.getAnotherMultiRefs().size());
        result.getAnotherMultiRefs().forEach(o -> assertEquals(result, o.getParentRoot(), "Wrong parent at " + o.toString()));
        assertNotNull(result.getMultiRefIndirectParents(), "There should be any MultiRefIndirectParents list");
        assertEquals(root.getMultiRefIndirectParents().size(), result.getMultiRefIndirectParents().size());
        result.getMultiRefIndirectParents().forEach(o -> assertEquals(result, o.getParentRoot(), "Wrong parent at " + o.toString()));
        assertNotNull(result.getMultiRefIndirectOtherParents(), "There should be any MultiRefIndirectOtherParents list");
        assertEquals(root.getMultiRefIndirectOtherParents().size(), result.getMultiRefIndirectOtherParents().size());
        result.getMultiRefIndirectOtherParents().forEach(o -> assertEquals(result, o.getParentRoot(), "Wrong parent at " + o.toString()));
        assertNotNull(result.getExtendings(), "There should be any extending list");
        assertEquals(root.getExtendings().size(), result.getExtendings().size());
        result.getExtendings().forEach(o -> assertEquals(result, o.getParentRoot(), "Wrong parent at " + o.toString()));
    }

    @Test
    public void testConvertToRootDaoNull() {
        assertNull(CommonAccessMapper.convertToRootDao(null, false), "The result should be null");
    }

    @Test
    public void testCreateRootExtDao() {
        RootExtDao result = CommonAccessMapper.convertToRootExtDao(rootExt);

        assertNotNull(result, "There should be any result");
        assertEquals(rootExt.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(rootExt.getExtendedInfo(), result.getExtendedInfo(), "Wrong ExtendedInfo");
        assertEquals(rootExt.getSomeInteger(), result.getSomeInteger(), "Wrong SomeInteger");
        assertEquals(rootExt.getSomeCustom(), result.getSomeCustom(), "Wrong SomeCustom");
        assertEquals(rootExt.getDaoAndDomain(), result.getDaoAndDomain(), "Wrong DaoAndDomain");
        assertEquals(rootExt.getTextWithDaoInfo(), result.getTextWithDaoInfo(), "Wrong TextWithDaoInfo");
        assertEquals(rootExt.getNumberWithDaoInfo(), result.getNumberWithDaoInfo(), "Wrong NumberWithDaoInfo(");
        assertEquals(rootExt.getDaoEnum(), result.getDaoEnum(), "Wrong DaoEnum");
        assertEquals(rootExt.getDaoEnumWithText(), result.getDaoEnumWithText(), "Wrong DaoEnumWithText");
        assertEquals(rootExt.getExtendedInfo(), result.getExtendedInfo(), "Wrong ExtendedInfo");
    }

    @Test
    public void testCreateRootExtDaoWithParent() {
        RootExtDao result = CommonAccessMapper.convertToRootExtDao(rootExt, rootDao);

        assertNotNull(result, "There should be any result");
        assertEquals(rootExt.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(rootExt.getExtendedInfo(), result.getExtendedInfo(), "Wrong ExtendedInfo");
        assertEquals(rootExt.getSomeInteger(), result.getSomeInteger(), "Wrong SomeInteger");
        assertEquals(rootExt.getSomeCustom(), result.getSomeCustom(), "Wrong SomeCustom");
        assertEquals(rootExt.getDaoAndDomain(), result.getDaoAndDomain(), "Wrong DaoAndDomain");
        assertEquals(rootExt.getTextWithDaoInfo(), result.getTextWithDaoInfo(), "Wrong TextWithDaoInfo");
        assertEquals(rootExt.getNumberWithDaoInfo(), result.getNumberWithDaoInfo(), "Wrong NumberWithDaoInfo(");
        assertEquals(rootExt.getDaoEnum(), result.getDaoEnum(), "Wrong DaoEnum");
        assertEquals(rootExt.getDaoEnumWithText(), result.getDaoEnumWithText(), "Wrong DaoEnumWithText");
        assertEquals(rootExt.getExtendedInfo(), result.getExtendedInfo(), "Wrong ExtendedInfo");

        assertEquals(rootDao.getExt(), result, "Wrong ext at root");
        assertEquals(rootDao, result.getParentRoot(), "Wrong parent");
    }

    @Test
    public void testCreateRootDaoExtNull() {
        assertNull(CommonAccessMapper.convertToRootExtDao(null), "The result should be null");
    }

    @Test
    public void testGetInstance() {
        CommonAccessMapper firstInstance = CommonAccessMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, CommonAccessMapper.getInstance(), "Any other instance should be the same");
    }
}
