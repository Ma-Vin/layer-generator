package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseAccessMapper;
import de.ma_vin.util.sample.content.dao.DaoObjectFactory;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.dao.multi.MultiRefOneParentDao;
import de.ma_vin.util.sample.content.dao.multi.MultiRefTwoParentsDao;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.multi.MultiRefOneParent;
import de.ma_vin.util.sample.content.domain.multi.MultiRefTwoParents;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@BaseAccessMapper
public class MultiAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static MultiAccessMapper instance;

	public static MultiRefOneParent convertToMultiRefOneParent(MultiRefOneParentDao multiRefOneParent, boolean includeChildren) {
		return convertToMultiRefOneParent(multiRefOneParent, includeChildren, new HashMap<>());
	}

	public static MultiRefOneParent convertToMultiRefOneParent(MultiRefOneParentDao multiRefOneParent, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(multiRefOneParent, mappedObjects, DomainObjectFactory::createMultiRefOneParent, (dao, domain) -> getInstance().setMultiRefOneParentValues(dao, domain)
				, (dao, domain) -> getInstance().setMultiRefOneParentSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setMultiRefOneParentMultiReferences(dao, domain, includeChildren, mappedObjects));
	}

	public static MultiRefOneParent convertToMultiRefOneParent(MultiRefOneParentDao multiRefOneParent, boolean includeChildren, Root parent) {
		return convertToMultiRefOneParent(multiRefOneParent, includeChildren, parent, new HashMap<>());
	}

	public static MultiRefOneParent convertToMultiRefOneParent(MultiRefOneParentDao multiRefOneParent, boolean includeChildren, Root parent, Map<String, IIdentifiable> mappedObjects) {
		MultiRefOneParent result = convertToMultiRefOneParent(multiRefOneParent, includeChildren, mappedObjects);
		if (result != null) {
			parent.getMultiRefs().add(result);
		}
		return result;
	}

	public static MultiRefOneParentDao convertToMultiRefOneParentDao(MultiRefOneParent multiRefOneParent, boolean includeChildren) {
		return convertToMultiRefOneParentDao(multiRefOneParent, includeChildren, new HashMap<>());
	}

	public static MultiRefOneParentDao convertToMultiRefOneParentDao(MultiRefOneParent multiRefOneParent, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(multiRefOneParent, mappedObjects, DaoObjectFactory::createMultiRefOneParentDao, (domain, dao) -> getInstance().setMultiRefOneParentDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setMultiRefOneParentDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setMultiRefOneParentDaoMultiReferences(domain, dao, includeChildren, mappedObjects));
	}

	public static MultiRefOneParentDao convertToMultiRefOneParentDao(MultiRefOneParent multiRefOneParent, boolean includeChildren, RootDao parent) {
		return convertToMultiRefOneParentDao(multiRefOneParent, includeChildren, parent, new HashMap<>());
	}

	public static MultiRefOneParentDao convertToMultiRefOneParentDao(MultiRefOneParent multiRefOneParent, boolean includeChildren, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		MultiRefOneParentDao result = convertToMultiRefOneParentDao(multiRefOneParent, includeChildren, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.getMultiRefs().add(result);
		}
		return result;
	}

	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDao multiRefTwoParents) {
		return convertToMultiRefTwoParents(multiRefTwoParents, new HashMap<>());
	}

	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDao multiRefTwoParents, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(multiRefTwoParents, mappedObjects, DomainObjectFactory::createMultiRefTwoParents, (dao, domain) -> getInstance().setMultiRefTwoParentsValues(dao, domain)
				, (dao, domain) -> getInstance().setMultiRefTwoParentsSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setMultiRefTwoParentsMultiReferences(dao, domain, mappedObjects));
	}

	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDao multiRefTwoParents, MultiRefOneParent parent) {
		return convertToMultiRefTwoParents(multiRefTwoParents, parent, new HashMap<>());
	}

	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDao multiRefTwoParents, MultiRefOneParent parent, Map<String, IIdentifiable> mappedObjects) {
		MultiRefTwoParents result = convertToMultiRefTwoParents(multiRefTwoParents, mappedObjects);
		if (result != null) {
			parent.getMultiRefs().add(result);
		}
		return result;
	}

	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDao multiRefTwoParents, Root parent) {
		return convertToMultiRefTwoParents(multiRefTwoParents, parent, new HashMap<>());
	}

	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDao multiRefTwoParents, Root parent, Map<String, IIdentifiable> mappedObjects) {
		MultiRefTwoParents result = convertToMultiRefTwoParents(multiRefTwoParents, mappedObjects);
		if (result != null) {
			parent.getAnotherMultiRefs().add(result);
		}
		return result;
	}

	public static MultiRefTwoParentsDao convertToMultiRefTwoParentsDao(MultiRefTwoParents multiRefTwoParents) {
		return convertToMultiRefTwoParentsDao(multiRefTwoParents, new HashMap<>());
	}

	public static MultiRefTwoParentsDao convertToMultiRefTwoParentsDao(MultiRefTwoParents multiRefTwoParents, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(multiRefTwoParents, mappedObjects, DaoObjectFactory::createMultiRefTwoParentsDao, (domain, dao) -> getInstance().setMultiRefTwoParentsDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setMultiRefTwoParentsDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setMultiRefTwoParentsDaoMultiReferences(domain, dao, mappedObjects));
	}

	public static MultiRefTwoParentsDao convertToMultiRefTwoParentsDao(MultiRefTwoParents multiRefTwoParents, MultiRefOneParentDao parent) {
		return convertToMultiRefTwoParentsDao(multiRefTwoParents, parent, new HashMap<>());
	}

	public static MultiRefTwoParentsDao convertToMultiRefTwoParentsDao(MultiRefTwoParents multiRefTwoParents, MultiRefOneParentDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		MultiRefTwoParentsDao result = convertToMultiRefTwoParentsDao(multiRefTwoParents, mappedObjects);
		if (result != null) {
			result.setParentMultiRefOneParent(parent);
			parent.getMultiRefs().add(result);
		}
		return result;
	}

	public static MultiRefTwoParentsDao convertToMultiRefTwoParentsDao(MultiRefTwoParents multiRefTwoParents, RootDao parent) {
		return convertToMultiRefTwoParentsDao(multiRefTwoParents, parent, new HashMap<>());
	}

	public static MultiRefTwoParentsDao convertToMultiRefTwoParentsDao(MultiRefTwoParents multiRefTwoParents, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		MultiRefTwoParentsDao result = convertToMultiRefTwoParentsDao(multiRefTwoParents, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.getAnotherMultiRefs().add(result);
		}
		return result;
	}

	/**
	 * @return the singleton
	 */
	public static MultiAccessMapper getInstance() {
		if (instance == null) {
			instance = AccessMapperFactory.createMultiAccessMapper();
		}
		return instance;
	}

	protected void setMultiRefOneParentDaoMultiReferences(MultiRefOneParent domain, MultiRefOneParentDao dao, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {
		dao.setMultiRefs(new ArrayList<>());
		if (includeChildren) {
			domain.getMultiRefs().forEach(arg ->
					MultiAccessMapper.convertToMultiRefTwoParentsDao(arg, dao, mappedObjects)
			);
		}
	}

	@SuppressWarnings("java:S1186")
	protected void setMultiRefOneParentDaoSingleReferences(MultiRefOneParent domain, MultiRefOneParentDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	protected void setMultiRefOneParentDaoValues(MultiRefOneParent domain, MultiRefOneParentDao dao) {
		dao.setDescription(domain.getDescription());
	}

	protected void setMultiRefOneParentMultiReferences(MultiRefOneParentDao dao, MultiRefOneParent domain, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		if (includeChildren) {
			dao.getMultiRefs().forEach(arg ->
					MultiAccessMapper.convertToMultiRefTwoParents(arg, domain, mappedObjects)
			);
		}
	}

	@SuppressWarnings("java:S1186")
	protected void setMultiRefOneParentSingleReferences(MultiRefOneParentDao dao, MultiRefOneParent domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setMultiRefOneParentValues(MultiRefOneParentDao dao, MultiRefOneParent domain) {
		domain.setDescription(dao.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setMultiRefTwoParentsDaoMultiReferences(MultiRefTwoParents domain, MultiRefTwoParentsDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setMultiRefTwoParentsDaoSingleReferences(MultiRefTwoParents domain, MultiRefTwoParentsDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	protected void setMultiRefTwoParentsDaoValues(MultiRefTwoParents domain, MultiRefTwoParentsDao dao) {
		dao.setDescription(domain.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setMultiRefTwoParentsMultiReferences(MultiRefTwoParentsDao dao, MultiRefTwoParents domain, Map<String, IIdentifiable> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setMultiRefTwoParentsSingleReferences(MultiRefTwoParentsDao dao, MultiRefTwoParents domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setMultiRefTwoParentsValues(MultiRefTwoParentsDao dao, MultiRefTwoParents domain) {
		domain.setDescription(dao.getDescription());
	}

}
