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

/**
 * Generated class which provides methods to convert a data access to a domain object of sub package <i>domain.dao<i> and the other way around
 */
@BaseAccessMapper
public class DomainDaoAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static DomainDaoAccessMapper instance;

	/**
	 * Converts a(n) {@link DomainAndDaoDao} to a(n) {@link DomainAndDao}
	 *
	 * @param domainAndDao the source object which should be converted
	 * @return an equivalent new created {@link DomainAndDao}
	 */
	public static DomainAndDao convertToDomainAndDao(DomainAndDaoDao domainAndDao) {
		return convertToDomainAndDao(domainAndDao, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link DomainAndDaoDao} to a(n) {@link DomainAndDao}
	 *
	 * @param domainAndDao  the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code domainAndDao} is contained, the found
	 *                      {@link DomainAndDao} will be returned
	 * @return an equivalent new created {@link DomainAndDao} or the found one from the given map
	 */
	public static DomainAndDao convertToDomainAndDao(DomainAndDaoDao domainAndDao, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(domainAndDao, mappedObjects, DomainObjectFactory::createDomainAndDao, (dao, domain) -> getInstance().setDomainAndDaoValues(dao, domain)
				, (dao, domain) -> getInstance().setDomainAndDaoSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setDomainAndDaoMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link DomainAndDao} to a(n) {@link DomainAndDaoDao}
	 *
	 * @param domainAndDao the source object which should be converted
	 * @return an equivalent new created {@link DomainAndDaoDao}
	 */
	public static DomainAndDaoDao convertToDomainAndDaoDao(DomainAndDao domainAndDao) {
		return convertToDomainAndDaoDao(domainAndDao, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link DomainAndDao} to a(n) {@link DomainAndDaoDao}
	 *
	 * @param domainAndDao  the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code domainAndDao} is contained, the found
	 *                      {@link DomainAndDaoDao} will be returned
	 * @return an equivalent new created {@link DomainAndDaoDao} or the found one from the given map
	 */
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
