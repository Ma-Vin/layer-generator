package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseAccessMapper;
import de.ma_vin.util.sample.content.dao.DaoObjectFactory;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.dao.single.SingleRefOneParentDao;
import de.ma_vin.util.sample.content.dao.single.SingleRefTwoParentsDao;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.single.SingleRefOneParent;
import de.ma_vin.util.sample.content.domain.single.SingleRefTwoParents;
import java.util.HashMap;
import java.util.Map;

@BaseAccessMapper
public class SingleAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static SingleAccessMapper instance;

	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDao singleRefOneParent) {
		return convertToSingleRefOneParent(singleRefOneParent, new HashMap<>());
	}

	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDao singleRefOneParent, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(singleRefOneParent, mappedObjects, DomainObjectFactory::createSingleRefOneParent, (dao, domain) -> getInstance().setSingleRefOneParentValues(dao, domain)
				, (dao, domain) -> getInstance().setSingleRefOneParentSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setSingleRefOneParentMultiReferences(dao, domain, mappedObjects));
	}

	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDao singleRefOneParent, Root parent) {
		return convertToSingleRefOneParent(singleRefOneParent, parent, new HashMap<>());
	}

	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDao singleRefOneParent, Root parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefOneParent result = convertToSingleRefOneParent(singleRefOneParent, mappedObjects);
		if (result != null) {
			parent.setSingleRef(result);
		}
		return result;
	}

	public static SingleRefOneParentDao convertToSingleRefOneParentDao(SingleRefOneParent singleRefOneParent) {
		return convertToSingleRefOneParentDao(singleRefOneParent, new HashMap<>());
	}

	public static SingleRefOneParentDao convertToSingleRefOneParentDao(SingleRefOneParent singleRefOneParent, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(singleRefOneParent, mappedObjects, DaoObjectFactory::createSingleRefOneParentDao, (domain, dao) -> getInstance().setSingleRefOneParentDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setSingleRefOneParentDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setSingleRefOneParentDaoMultiReferences(domain, dao, mappedObjects));
	}

	public static SingleRefOneParentDao convertToSingleRefOneParentDao(SingleRefOneParent singleRefOneParent, RootDao parent) {
		return convertToSingleRefOneParentDao(singleRefOneParent, parent, new HashMap<>());
	}

	public static SingleRefOneParentDao convertToSingleRefOneParentDao(SingleRefOneParent singleRefOneParent, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		SingleRefOneParentDao result = convertToSingleRefOneParentDao(singleRefOneParent, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.setSingleRef(result);
		}
		return result;
	}

	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDao singleRefTwoParents) {
		return convertToSingleRefTwoParents(singleRefTwoParents, new HashMap<>());
	}

	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDao singleRefTwoParents, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(singleRefTwoParents, mappedObjects, DomainObjectFactory::createSingleRefTwoParents, (dao, domain) -> getInstance().setSingleRefTwoParentsValues(dao, domain)
				, (dao, domain) -> getInstance().setSingleRefTwoParentsSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setSingleRefTwoParentsMultiReferences(dao, domain, mappedObjects));
	}

	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDao singleRefTwoParents, Root parent) {
		return convertToSingleRefTwoParents(singleRefTwoParents, parent, new HashMap<>());
	}

	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDao singleRefTwoParents, Root parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefTwoParents result = convertToSingleRefTwoParents(singleRefTwoParents, mappedObjects);
		if (result != null) {
			parent.setAnotherSingleRef(result);
		}
		return result;
	}

	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDao singleRefTwoParents, SingleRefOneParent parent) {
		return convertToSingleRefTwoParents(singleRefTwoParents, parent, new HashMap<>());
	}

	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDao singleRefTwoParents, SingleRefOneParent parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefTwoParents result = convertToSingleRefTwoParents(singleRefTwoParents, mappedObjects);
		if (result != null) {
			parent.setSingleRef(result);
		}
		return result;
	}

	public static SingleRefTwoParentsDao convertToSingleRefTwoParentsDao(SingleRefTwoParents singleRefTwoParents) {
		return convertToSingleRefTwoParentsDao(singleRefTwoParents, new HashMap<>());
	}

	public static SingleRefTwoParentsDao convertToSingleRefTwoParentsDao(SingleRefTwoParents singleRefTwoParents, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(singleRefTwoParents, mappedObjects, DaoObjectFactory::createSingleRefTwoParentsDao, (domain, dao) -> getInstance().setSingleRefTwoParentsDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setSingleRefTwoParentsDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setSingleRefTwoParentsDaoMultiReferences(domain, dao, mappedObjects));
	}

	public static SingleRefTwoParentsDao convertToSingleRefTwoParentsDao(SingleRefTwoParents singleRefTwoParents, RootDao parent) {
		return convertToSingleRefTwoParentsDao(singleRefTwoParents, parent, new HashMap<>());
	}

	public static SingleRefTwoParentsDao convertToSingleRefTwoParentsDao(SingleRefTwoParents singleRefTwoParents, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		SingleRefTwoParentsDao result = convertToSingleRefTwoParentsDao(singleRefTwoParents, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.setAnotherSingleRef(result);
		}
		return result;
	}

	public static SingleRefTwoParentsDao convertToSingleRefTwoParentsDao(SingleRefTwoParents singleRefTwoParents, SingleRefOneParentDao parent) {
		return convertToSingleRefTwoParentsDao(singleRefTwoParents, parent, new HashMap<>());
	}

	public static SingleRefTwoParentsDao convertToSingleRefTwoParentsDao(SingleRefTwoParents singleRefTwoParents, SingleRefOneParentDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		SingleRefTwoParentsDao result = convertToSingleRefTwoParentsDao(singleRefTwoParents, mappedObjects);
		if (result != null) {
			result.setParentSingleRefOneParent(parent);
			parent.setSingleRef(result);
		}
		return result;
	}

	/**
	 * @return the singleton
	 */
	public static SingleAccessMapper getInstance() {
		if (instance == null) {
			instance = AccessMapperFactory.createSingleAccessMapper();
		}
		return instance;
	}

	@SuppressWarnings("java:S1186")
	protected void setSingleRefOneParentDaoMultiReferences(SingleRefOneParent domain, SingleRefOneParentDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	protected void setSingleRefOneParentDaoSingleReferences(SingleRefOneParent domain, SingleRefOneParentDao dao, Map<String, IIdentifiableDao> mappedObjects) {
		SingleAccessMapper.convertToSingleRefTwoParentsDao(domain.getSingleRef(), dao, mappedObjects);
	}

	protected void setSingleRefOneParentDaoValues(SingleRefOneParent domain, SingleRefOneParentDao dao) {
		dao.setDescription(domain.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setSingleRefOneParentMultiReferences(SingleRefOneParentDao dao, SingleRefOneParent domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setSingleRefOneParentSingleReferences(SingleRefOneParentDao dao, SingleRefOneParent domain, Map<String, IIdentifiable> mappedObjects) {
		SingleAccessMapper.convertToSingleRefTwoParents(dao.getSingleRef(), domain, mappedObjects);
	}

	protected void setSingleRefOneParentValues(SingleRefOneParentDao dao, SingleRefOneParent domain) {
		domain.setDescription(dao.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setSingleRefTwoParentsDaoMultiReferences(SingleRefTwoParents domain, SingleRefTwoParentsDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setSingleRefTwoParentsDaoSingleReferences(SingleRefTwoParents domain, SingleRefTwoParentsDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	protected void setSingleRefTwoParentsDaoValues(SingleRefTwoParents domain, SingleRefTwoParentsDao dao) {
		dao.setDescription(domain.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setSingleRefTwoParentsMultiReferences(SingleRefTwoParentsDao dao, SingleRefTwoParents domain, Map<String, IIdentifiable> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setSingleRefTwoParentsSingleReferences(SingleRefTwoParentsDao dao, SingleRefTwoParents domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setSingleRefTwoParentsValues(SingleRefTwoParentsDao dao, SingleRefTwoParents domain) {
		domain.setDescription(dao.getDescription());
	}

}
