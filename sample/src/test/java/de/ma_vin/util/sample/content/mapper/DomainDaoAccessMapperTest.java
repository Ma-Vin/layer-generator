package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.dao.domain.dao.DomainAndDaoDao;
import de.ma_vin.util.sample.content.domain.domain.dao.DomainAndDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.ma_vin.util.sample.content.ObjectFactory.*;
import static de.ma_vin.util.sample.content.ObjectFactory.getNextId;
import static org.junit.jupiter.api.Assertions.*;

public class DomainDaoAccessMapperTest {

    private DomainAndDaoDao domainAndDaoDao;
    private DomainAndDao domainAndDao;

    @BeforeEach
    public void setUp() {
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
    public void testConvertToDomainAndDaoDao() {
        DomainAndDaoDao result = DomainDaoAccessMapper.convertToDomainAndDaoDao(domainAndDao);
        assertNotNull(result, "There should be any result");
        assertEquals(domainAndDao.getIdentification(), result.getIdentification(), "Wrong identification");
        assertEquals(domainAndDao.getDescription(), result.getDescription(), "Wrong description");
    }

    @Test
    public void testConvertToDomainAndDaoDaoNull() {
        assertNull(DomainDaoAccessMapper.convertToDomainAndDao(null), "The result should be null");
    }

    @Test
    public void testGetInstance() {
        DomainDaoAccessMapper firstInstance = DomainDaoAccessMapper.getInstance();
        assertNotNull(firstInstance, "An instance should be created");
        assertSame(firstInstance, DomainDaoAccessMapper.getInstance(), "Any other instance should be the same");
    }
}
