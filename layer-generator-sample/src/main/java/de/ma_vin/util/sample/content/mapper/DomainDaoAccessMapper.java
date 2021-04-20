package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseAccessMapper;
import de.ma_vin.util.sample.content.dao.DaoObjectFactory;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.domain.dao.DomainAndDaoDao;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.domain.dao.DomainAndDao;
import java.util.HashMap;
import java.util.Map;

@BaseAccessMapper
public class DomainDaoAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static DomainDaoAccessMapper instance;

	public static DomainAndDao convertToDomainAndDao(DomainAndDaoDao domainAndDao) {
		return convertToDomainAndDao(domainAndDao, new HashMap<>());
	}

	public static DomainAndDao convertToDomainAndDao(DomainAndDaoDao domainAndDao, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(domainAndDao, mappedObjects, DomainObjectFactory::createDomainAndDao, (dao, domain) -> getInstance().setDomainAndDaoValues(dao, domain)
				, (dao, domain) -> getInstance().setDomainAndDaoSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setDomainAndDaoMultiReferences(dao, domain, mappedObjects));
	}

	public static DomainAndDaoDao convertToDomainAndDaoDao(DomainAndDao domainAndDao) {
		return convertToDomainAndDaoDao(domainAndDao, new HashMap<>());
	}

	public static DomainAndDaoDao convertToDomainAndDaoDao(DomainAndDao domainAndDao, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(domainAndDao, mappedObjects, DaoObjectFactory::createDomainAndDaoDao, (domain, dao) -> getInstance().setDomainAndDaoDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setDomainAndDaoDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setDomainAndDaoDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * @return the singleton
	 */
	public static DomainDaoAccessMapper getInstance() {
		if (instance == null) {
			instance = AccessMapperFactory.createDomainDaoAccessMapper();
		}
		return instance;
	}

	@SuppressWarnings("java:S1186")
	protected void setDomainAndDaoDaoMultiReferences(DomainAndDao domain, DomainAndDaoDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setDomainAndDaoDaoSingleReferences(DomainAndDao domain, DomainAndDaoDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	protected void setDomainAndDaoDaoValues(DomainAndDao domain, DomainAndDaoDao dao) {
		dao.setDescription(domain.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setDomainAndDaoMultiReferences(DomainAndDaoDao dao, DomainAndDao domain, Map<String, IIdentifiable> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setDomainAndDaoSingleReferences(DomainAndDaoDao dao, DomainAndDao domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setDomainAndDaoValues(DomainAndDaoDao dao, DomainAndDao domain) {
		domain.setDescription(dao.getDescription());
	}

}
