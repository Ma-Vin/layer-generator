package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.domain.dao.DomainAndDaoDao;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.domain.dao.DomainAndDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static de.ma_vin.util.sample.content.ObjectFactory.*;
import static de.ma_vin.util.sample.content.ObjectFactory.getNextId;
import static org.junit.jupiter.api.Assertions.*;

public class DomainDaoAccessMapperTest {

    private DomainAndDaoDao domainAndDaoDao;
    private DomainAndDao domainAndDao;

    Map<String, IIdentifiable> mappedObjects = new HashMap<>();
    Map<String, IIdentifiableDao> mappedDaoObjects = new HashMap<>();

    @BeforeEach
    public void setUp() {
        mappedObjects.clear();
        mappedDaoObjects.clear();

        initObjectFactory();
        domainAndDaoDao = createDomainAndDaoDao(getNextId());

        initObjectFactory();
        domainAndDao = createDomainAndDao(getNextId());
    }

    @Test
    public void testConvertToDomainAndDao() {
        DomainAndDao result = DomainDaoAccessMapper.convertToDomainAndDao(domainAndDaoDao);
        assertNotNull(result, "There should be any result");
        assertEquals(domainAndDaoDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(domainAndDaoDao.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToDomainAndDaoNull() {
        assertNull(DomainDaoAccessMapper.convertToDomainAndDao(null), "The result should be null");
    }

    @Test
    public void testConvertToDomainAndDaoAgain() {
        DomainAndDao result = DomainDaoAccessMapper.convertToDomainAndDao(domainAndDaoDao, mappedObjects);
        DomainAndDao convertAgainResult = DomainDaoAccessMapper.convertToDomainAndDao(domainAndDaoDao, mappedObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testConvertToDomainAndDaoDao() {
        DomainAndDaoDao result = DomainDaoAccessMapper.convertToDomainAndDaoDao(domainAndDao);
        assertNotNull(result, "There should be any result");
        assertEquals(domainAndDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(domainAndDao.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToDomainAndDaoDaoNull() {
        assertNull(DomainDaoAccessMapper.convertToDomainAndDaoDao(null), "The result should be null");
    }

    @Test
    public void testConvertToDomainAndDaoDaoAgain() {
        DomainAndDaoDao result = DomainDaoAccessMapper.convertToDomainAndDaoDao(domainAndDao, mappedDaoObjects);
        DomainAndDaoDao convertAgainResult = DomainDaoAccessMapper.convertToDomainAndDaoDao(domainAndDao, mappedDaoObjects);
        assertSame(result, convertAgainResult, "Converting again with map should return the same object");
    }

    @Test
    public void testGetInstance() {
        DomainDaoAccessMapper firstInstance = DomainDaoAccessMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, DomainDaoAccessMapper.getInstance(), "Any other instance should be the same");
    }
}
