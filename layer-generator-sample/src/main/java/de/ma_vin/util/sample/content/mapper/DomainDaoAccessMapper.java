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
public class DomainDaoAccessMapper {

	public DomainDaoAccessMapper() {
	}

	/**
	 * singleton
	 */
	private static DomainDaoAccessMapper instance;

	public static DomainAndDao convertToDomainAndDao(DomainAndDaoDao domainAndDao) {
		return convertToDomainAndDao(domainAndDao, new HashMap<>());
	}

	public static DomainAndDao convertToDomainAndDao(DomainAndDaoDao domainAndDao, Map<String, IIdentifiable> mappedObjects) {
		if (domainAndDao == null) {
			return null;
		}

		String identification = domainAndDao.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (DomainAndDao) mappedObjects.get(identification);
		}

		DomainAndDao result = DomainObjectFactory.createDomainAndDao();

		result.setIdentification(identification);

		result.setDescription(domainAndDao.getDescription());

		mappedObjects.put(identification, result);
		return result;
	}

	public static DomainAndDaoDao convertToDomainAndDaoDao(DomainAndDao domainAndDao) {
		return convertToDomainAndDaoDao(domainAndDao, new HashMap<>());
	}

	public static DomainAndDaoDao convertToDomainAndDaoDao(DomainAndDao domainAndDao, Map<String, IIdentifiableDao> mappedObjects) {
		if (domainAndDao == null) {
			return null;
		}

		String identification = domainAndDao.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (DomainAndDaoDao) mappedObjects.get(identification);
		}

		DomainAndDaoDao result = DaoObjectFactory.createDomainAndDaoDao();

		result.setIdentification(identification);

		result.setDescription(domainAndDao.getDescription());

		mappedObjects.put(identification, result);
		return result;
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

}
