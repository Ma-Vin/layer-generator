package de.ma_vin.util.sample.content.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static de.ma_vin.util.sample.content.ObjectFactory.*;

import de.ma_vin.util.sample.content.dao.*;
import de.ma_vin.util.sample.content.domain.*;
import de.ma_vin.util.sample.extending.ExtendedCommonAccessMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class CommonAccessMapperTest {

    private RootDao rootDao;
    private RootExtDao rootExtDao;

    private Root root;
    private RootExt rootExt;

    Map<String, IIdentifiable> mappedObjects = new HashMap<>();
    Map<String, IIdentifiableDao> mappedDaoObjects = new HashMap<>();

    @BeforeEach
    public void setUp() {
        mappedObjects.clear();
        mappedDaoObjects.clear();

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
        assertNotNull(result.getNonOwnerFiltering(), "There should be any Non Owner Filtering");
        assertEquals(rootDao.getNonOwnerFiltering().getIdentification(), result.getNonOwnerFiltering().getIdentification(), "Wrong identification at Non Owner Filtering");
        assertNotNull(result.getExt(), "There should be any RootExt");
        assertEquals(rootDao.getExt().getIdentification(), result.getExt().getIdentification(), "Wrong identification at RootExt");

        assertNotNull(result.getMultiRef(), "There should be any MultiRefs list");
        assertEquals(0, result.getMultiRef().size());
        assertNotNull(result.getAnotherMultiRef(), "There should be any AnotherMultiRefs list");
        assertEquals(0, result.getAnotherMultiRef().size());
        assertNotNull(result.getMultiRefIndirectParent(), "There should be any MultiRefIndirectParents list");
        assertEquals(0, result.getMultiRefIndirectParent().size());
        assertNotNull(result.getMultiRefIndirectOtherParent(), "There should be any MultiRefIndirectOtherParents list");
        assertEquals(0, result.getMultiRefIndirectOtherParent().size());
        assertNotNull(result.getMultiRefIndirectSelfReference(), "There should be any MultiRefIndirectSelfReference list");
        assertEquals(0, result.getMultiRefIndirectSelfReference().size());
        assertNotNull(result.getExtending(), "There should be any extending list");
        assertEquals(0, result.getExtending().size());
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
        assertNotNull(result.getNonOwnerFiltering(), "There should be any Non Owner Filtering");
        assertEquals(rootDao.getNonOwnerFiltering().getIdentification(), result.getNonOwnerFiltering().getIdentification(), "Wrong identification at Non Owner Filtering");
        assertNotNull(result.getExt(), "There should be any RootExt");
        assertEquals(rootDao.getExt().getIdentification(), result.getExt().getIdentification(), "Wrong identification at RootExt");

        assertNotNull(result.getMultiRef(), "There should be any MultiRefs list");
        assertEquals(rootDao.getMultiRef().size(), result.getMultiRef().size());
        assertNotNull(result.getAnotherMultiRef(), "There should be any AnotherMultiRefs list");
        assertEquals(rootDao.getAnotherMultiRef().size(), result.getAnotherMultiRef().size());
        assertNotNull(result.getMultiRefIndirectParent(), "There should be any MultiRefIndirectParents list");
        assertEquals(rootDao.getMultiRefIndirectParent().size(), result.getMultiRefIndirectParent().size());
        assertNotNull(result.getMultiRefIndirectOtherParent(), "There should be any MultiRefIndirectOtherParents list");
        assertEquals(rootDao.getMultiRefIndirectOtherParent().size(), result.getMultiRefIndirectOtherParent().size());
        assertNotNull(result.getMultiRefIndirectSelfReference(), "There should be any MultiRefIndirectSelfReference list");
        assertEquals(rootDao.getMultiRefIndirectSelfReference().size(), result.getMultiRefIndirectSelfReference().size());
        assertNotNull(result.getExtending(), "There should be any extending list");
        assertEquals(rootDao.getExtending().size(), result.getExtending().size());
    }

    @Test
    public void testConvertToRootNull() {
        assertNull(CommonAccessMapper.convertToRoot(null, false), "The result should be null");
    }

    @Test
    public void testConvertToRootAgain() {
        Root result = CommonAccessMapper.convertToRoot(rootDao, false, mappedObjects);
        Root convertAgainResult = CommonAccessMapper.convertToRoot(rootDao, false, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
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
        assertEquals(rootExtDao.getSomeName() + ExtendedCommonAccessMapper.ADDITIONAL_POSTFIX, result.getSomeName(), "Wrong modified SomeName");
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
        assertEquals(rootExtDao.getSomeName() + ExtendedCommonAccessMapper.ADDITIONAL_POSTFIX, result.getSomeName(), "Wrong modified SomeName");

        assertEquals(root.getExt(), result, "Wrong ext at root");
    }

    @Test
    public void testCreateRootExtNull() {
        assertNull(CommonAccessMapper.convertToRootExt(null), "The result should be null");
    }

    @Test
    public void testCreateRootExtAgain() {
        RootExt result = CommonAccessMapper.convertToRootExt(rootExtDao, mappedObjects);
        RootExt convertAgainResult = CommonAccessMapper.convertToRootExt(rootExtDao, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
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
        assertNotNull(result.getNonOwnerFiltering(), "There should be any Non Owner Filtering");
        assertEquals(root.getNonOwnerFiltering().getIdentification(), result.getNonOwnerFiltering().getIdentification(), "Wrong identification at Non Owner Filtering");
        assertEquals(result, result.getNonOwnerFiltering().getParentRoot(), "Wrong parent at Non Owner Filtering");
        assertNotNull(result.getExt(), "There should be any RootExt");
        assertEquals(root.getExt().getIdentification(), result.getExt().getIdentification(), "Wrong identification at RootExt");
        assertEquals(result, result.getExt().getParentRoot(), "Wrong parent at RootExt");

        assertNotNull(result.getMultiRef(), "There should be any MultiRefs list");
        assertEquals(0, result.getMultiRef().size());
        assertNotNull(result.getAnotherMultiRef(), "There should be any AnotherMultiRefs list");
        assertEquals(0, result.getAnotherMultiRef().size());
        assertNotNull(result.getMultiRefIndirectParent(), "There should be any MultiRefIndirectParents list");
        assertEquals(0, result.getMultiRefIndirectParent().size());
        assertNotNull(result.getMultiRefIndirectOtherParent(), "There should be any MultiRefIndirectOtherParents list");
        assertEquals(0, result.getMultiRefIndirectOtherParent().size());
        assertNotNull(result.getMultiRefIndirectSelfReference(), "There should be any MultiRefIndirectSelfReference list");
        assertEquals(0, result.getMultiRefIndirectSelfReference().size());
        assertNotNull(result.getExtending(), "There should be any extending list");
        assertEquals(0, result.getExtending().size());
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
        assertNotNull(result.getNonOwnerFiltering(), "There should be any Non Owner Filtering");
        assertEquals(root.getNonOwnerFiltering().getIdentification(), result.getNonOwnerFiltering().getIdentification(), "Wrong identification at Non Owner Filtering");
        assertEquals(result, result.getNonOwnerFiltering().getParentRoot(), "Wrong parent at Non Owner Filtering");
        assertNotNull(result.getExt(), "There should be any RootExt");
        assertEquals(root.getExt().getIdentification(), result.getExt().getIdentification(), "Wrong identification at RootExt");

        assertNotNull(result.getMultiRef(), "There should be any MultiRefs list");
        assertEquals(root.getMultiRef().size(), result.getMultiRef().size());
        result.getMultiRef().forEach(o -> assertEquals(result, o.getParentRoot(), "Wrong parent at " + o.toString()));
        assertNotNull(result.getAnotherMultiRef(), "There should be any AnotherMultiRefs list");
        assertEquals(root.getAnotherMultiRef().size(), result.getAnotherMultiRef().size());
        result.getAnotherMultiRef().forEach(o -> assertEquals(result, o.getParentRoot(), "Wrong parent at " + o.toString()));
        assertNotNull(result.getMultiRefIndirectParent(), "There should be any MultiRefIndirectParents list");
        assertEquals(root.getMultiRefIndirectParent().size(), result.getMultiRefIndirectParent().size());
        result.getMultiRefIndirectParent().forEach(o -> assertEquals(result, o.getParentRoot(), "Wrong parent at " + o.toString()));
        assertNotNull(result.getMultiRefIndirectOtherParent(), "There should be any MultiRefIndirectOtherParents list");
        assertEquals(root.getMultiRefIndirectOtherParent().size(), result.getMultiRefIndirectOtherParent().size());
        result.getMultiRefIndirectOtherParent().forEach(o -> assertEquals(result, o.getParentRoot(), "Wrong parent at " + o.toString()));
        assertNotNull(result.getMultiRefIndirectSelfReference(), "There should be any MultiRefIndirectOtherParents list");
        assertEquals(root.getMultiRefIndirectSelfReference().size(), result.getMultiRefIndirectSelfReference().size());
        result.getMultiRefIndirectSelfReference().forEach(o -> assertEquals(result, o.getParentRoot(), "Wrong parent at " + o.toString()));
        assertNotNull(result.getExtending(), "There should be any extending list");
        assertEquals(root.getExtending().size(), result.getExtending().size());
        result.getExtending().forEach(o -> assertEquals(result, o.getParentRoot(), "Wrong parent at " + o.toString()));
    }

    @Test
    public void testConvertToRootDaoNull() {
        assertNull(CommonAccessMapper.convertToRootDao(null, false), "The result should be null");
    }

    @Test
    public void testConvertToRootDaoAgain() {
        RootDao result = CommonAccessMapper.convertToRootDao(root, false, mappedDaoObjects);
        RootDao convertAgainResult = CommonAccessMapper.convertToRootDao(root, false, mappedDaoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
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
        assertEquals(rootExt.getSomeName() + ExtendedCommonAccessMapper.ADDITIONAL_POSTFIX, result.getSomeName(), "Wrong modified SomeName");
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
        assertEquals(rootExt.getSomeName() + ExtendedCommonAccessMapper.ADDITIONAL_POSTFIX, result.getSomeName(), "Wrong modified SomeName");

        assertEquals(rootDao.getExt(), result, "Wrong ext at root");
        assertEquals(rootDao, result.getParentRoot(), "Wrong parent");
    }

    @Test
    public void testCreateRootDaoExtNull() {
        assertNull(CommonAccessMapper.convertToRootExtDao(null), "The result should be null");
    }

    @Test
    public void testCreateRootExtDaoAgain() {
        RootExtDao result = CommonAccessMapper.convertToRootExtDao(rootExt, mappedDaoObjects);
        RootExtDao convertAgainResult = CommonAccessMapper.convertToRootExtDao(rootExt, mappedDaoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testGetInstance() {
        CommonAccessMapper firstInstance = CommonAccessMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertTrue(firstInstance instanceof ExtendedCommonAccessMapper, "There should be an extended instance");
        assertSame(firstInstance, CommonAccessMapper.getInstance(), "Any other instance should be the same");
    }
}
