package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseAccessMapper;
import de.ma_vin.util.sample.content.dao.DaoObjectFactory;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.dao.single.indirect.SingleRefIndirectParentDao;
import de.ma_vin.util.sample.content.dao.single.indirect.SingleRefOtherIndirectParentDao;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.single.indirect.SingleRefIndirectParent;
import de.ma_vin.util.sample.content.domain.single.indirect.SingleRefOtherIndirectParent;
import java.util.HashMap;
import java.util.Map;

@BaseAccessMapper
public class SingleIndirectAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static SingleIndirectAccessMapper instance;

	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDao singleRefIndirectParent) {
		return convertToSingleRefIndirectParent(singleRefIndirectParent, new HashMap<>());
	}

	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDao singleRefIndirectParent, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(singleRefIndirectParent, mappedObjects, DomainObjectFactory::createSingleRefIndirectParent, (dao, domain) -> getInstance().setSingleRefIndirectParentValues(dao, domain)
				, (dao, domain) -> getInstance().setSingleRefIndirectParentSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setSingleRefIndirectParentMultiReferences(dao, domain, mappedObjects));
	}

	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDao singleRefIndirectParent, Root parent) {
		return convertToSingleRefIndirectParent(singleRefIndirectParent, parent, new HashMap<>());
	}

	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDao singleRefIndirectParent, Root parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefIndirectParent result = convertToSingleRefIndirectParent(singleRefIndirectParent, mappedObjects);
		if (result != null) {
			parent.setSingleRefIndirectParent(result);
		}
		return result;
	}

	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDao singleRefIndirectParent, SingleRefOtherIndirectParent parent) {
		return convertToSingleRefIndirectParent(singleRefIndirectParent, parent, new HashMap<>());
	}

	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDao singleRefIndirectParent, SingleRefOtherIndirectParent parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefIndirectParent result = convertToSingleRefIndirectParent(singleRefIndirectParent, mappedObjects);
		if (result != null) {
			parent.setSingleIndirectRef(result);
		}
		return result;
	}

	public static SingleRefIndirectParentDao convertToSingleRefIndirectParentDao(SingleRefIndirectParent singleRefIndirectParent) {
		return convertToSingleRefIndirectParentDao(singleRefIndirectParent, new HashMap<>());
	}

	public static SingleRefIndirectParentDao convertToSingleRefIndirectParentDao(SingleRefIndirectParent singleRefIndirectParent, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(singleRefIndirectParent, mappedObjects, DaoObjectFactory::createSingleRefIndirectParentDao, (domain, dao) -> getInstance().setSingleRefIndirectParentDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setSingleRefIndirectParentDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setSingleRefIndirectParentDaoMultiReferences(domain, dao, mappedObjects));
	}

	public static SingleRefIndirectParentDao convertToSingleRefIndirectParentDao(SingleRefIndirectParent singleRefIndirectParent, RootDao parent) {
		return convertToSingleRefIndirectParentDao(singleRefIndirectParent, parent, new HashMap<>());
	}

	public static SingleRefIndirectParentDao convertToSingleRefIndirectParentDao(SingleRefIndirectParent singleRefIndirectParent, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		SingleRefIndirectParentDao result = convertToSingleRefIndirectParentDao(singleRefIndirectParent, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.setSingleRefIndirectParent(result);
		}
		return result;
	}

	public static SingleRefIndirectParentDao convertToSingleRefIndirectParentDao(SingleRefIndirectParent singleRefIndirectParent, SingleRefOtherIndirectParentDao parent) {
		return convertToSingleRefIndirectParentDao(singleRefIndirectParent, parent, new HashMap<>());
	}

	public static SingleRefIndirectParentDao convertToSingleRefIndirectParentDao(SingleRefIndirectParent singleRefIndirectParent, SingleRefOtherIndirectParentDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		SingleRefIndirectParentDao result = convertToSingleRefIndirectParentDao(singleRefIndirectParent, mappedObjects);
		if (result != null) {
			parent.setSingleIndirectRef(result);
		}
		return result;
	}

	public static SingleRefOtherIndirectParent convertToSingleRefOtherIndirectParent(SingleRefOtherIndirectParentDao singleRefOtherIndirectParent) {
		return convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParent, new HashMap<>());
	}

	public static SingleRefOtherIndirectParent convertToSingleRefOtherIndirectParent(SingleRefOtherIndirectParentDao singleRefOtherIndirectParent, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(singleRefOtherIndirectParent, mappedObjects, DomainObjectFactory::createSingleRefOtherIndirectParent, (dao, domain) -> getInstance().setSingleRefOtherIndirectParentValues(dao, domain)
				, (dao, domain) -> getInstance().setSingleRefOtherIndirectParentSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setSingleRefOtherIndirectParentMultiReferences(dao, domain, mappedObjects));
	}

	public static SingleRefOtherIndirectParent convertToSingleRefOtherIndirectParent(SingleRefOtherIndirectParentDao singleRefOtherIndirectParent, Root parent) {
		return convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParent, parent, new HashMap<>());
	}

	public static SingleRefOtherIndirectParent convertToSingleRefOtherIndirectParent(SingleRefOtherIndirectParentDao singleRefOtherIndirectParent, Root parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefOtherIndirectParent result = convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParent, mappedObjects);
		if (result != null) {
			parent.setSingleRefIndirectOtherParent(result);
		}
		return result;
	}

	public static SingleRefOtherIndirectParentDao convertToSingleRefOtherIndirectParentDao(SingleRefOtherIndirectParent singleRefOtherIndirectParent) {
		return convertToSingleRefOtherIndirectParentDao(singleRefOtherIndirectParent, new HashMap<>());
	}

	public static SingleRefOtherIndirectParentDao convertToSingleRefOtherIndirectParentDao(SingleRefOtherIndirectParent singleRefOtherIndirectParent, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(singleRefOtherIndirectParent, mappedObjects, DaoObjectFactory::createSingleRefOtherIndirectParentDao, (domain, dao) -> getInstance().setSingleRefOtherIndirectParentDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setSingleRefOtherIndirectParentDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setSingleRefOtherIndirectParentDaoMultiReferences(domain, dao, mappedObjects));
	}

	public static SingleRefOtherIndirectParentDao convertToSingleRefOtherIndirectParentDao(SingleRefOtherIndirectParent singleRefOtherIndirectParent, RootDao parent) {
		return convertToSingleRefOtherIndirectParentDao(singleRefOtherIndirectParent, parent, new HashMap<>());
	}

	public static SingleRefOtherIndirectParentDao convertToSingleRefOtherIndirectParentDao(SingleRefOtherIndirectParent singleRefOtherIndirectParent, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		SingleRefOtherIndirectParentDao result = convertToSingleRefOtherIndirectParentDao(singleRefOtherIndirectParent, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.setSingleRefIndirectOtherParent(result);
		}
		return result;
	}

	/**
	 * @return the singleton
	 */
	public static SingleIndirectAccessMapper getInstance() {
		if (instance == null) {
			instance = AccessMapperFactory.createSingleIndirectAccessMapper();
		}
		return instance;
	}

	@SuppressWarnings("java:S1186")
	protected void setSingleRefIndirectParentDaoMultiReferences(SingleRefIndirectParent domain, SingleRefIndirectParentDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setSingleRefIndirectParentDaoSingleReferences(SingleRefIndirectParent domain, SingleRefIndirectParentDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	protected void setSingleRefIndirectParentDaoValues(SingleRefIndirectParent domain, SingleRefIndirectParentDao dao) {
		dao.setDescription(domain.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setSingleRefIndirectParentMultiReferences(SingleRefIndirectParentDao dao, SingleRefIndirectParent domain, Map<String, IIdentifiable> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setSingleRefIndirectParentSingleReferences(SingleRefIndirectParentDao dao, SingleRefIndirectParent domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setSingleRefIndirectParentValues(SingleRefIndirectParentDao dao, SingleRefIndirectParent domain) {
		domain.setDescription(dao.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setSingleRefOtherIndirectParentDaoMultiReferences(SingleRefOtherIndirectParent domain, SingleRefOtherIndirectParentDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	protected void setSingleRefOtherIndirectParentDaoSingleReferences(SingleRefOtherIndirectParent domain, SingleRefOtherIndirectParentDao dao, Map<String, IIdentifiableDao> mappedObjects) {
		dao.setSingleIndirectRef(SingleIndirectAccessMapper.convertToSingleRefIndirectParentDao(domain.getSingleIndirectRef(), mappedObjects));
	}

	protected void setSingleRefOtherIndirectParentDaoValues(SingleRefOtherIndirectParent domain, SingleRefOtherIndirectParentDao dao) {
		dao.setDescription(domain.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setSingleRefOtherIndirectParentMultiReferences(SingleRefOtherIndirectParentDao dao, SingleRefOtherIndirectParent domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setSingleRefOtherIndirectParentSingleReferences(SingleRefOtherIndirectParentDao dao, SingleRefOtherIndirectParent domain, Map<String, IIdentifiable> mappedObjects) {
		domain.setSingleIndirectRef(SingleIndirectAccessMapper.convertToSingleRefIndirectParent(dao.getSingleIndirectRef(), mappedObjects));
	}

	protected void setSingleRefOtherIndirectParentValues(SingleRefOtherIndirectParentDao dao, SingleRefOtherIndirectParent domain) {
		domain.setDescription(dao.getDescription());
	}

}
