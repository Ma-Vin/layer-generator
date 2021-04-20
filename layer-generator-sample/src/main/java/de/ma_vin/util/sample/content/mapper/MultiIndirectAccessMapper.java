package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseAccessMapper;
import de.ma_vin.util.sample.content.dao.DaoObjectFactory;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.dao.multi.indirect.MultiRefIndirectParentDao;
import de.ma_vin.util.sample.content.dao.multi.indirect.MultiRefOtherIndirectParentDao;
import de.ma_vin.util.sample.content.dao.multi.indirect.MultiRefOtherIndirectParentToMultiRefIndirectParentDao;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.multi.indirect.MultiRefIndirectParent;
import de.ma_vin.util.sample.content.domain.multi.indirect.MultiRefOtherIndirectParent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@BaseAccessMapper
public class MultiIndirectAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static MultiIndirectAccessMapper instance;

	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDao multiRefIndirectParent) {
		return convertToMultiRefIndirectParent(multiRefIndirectParent, new HashMap<>());
	}

	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDao multiRefIndirectParent, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(multiRefIndirectParent, mappedObjects, DomainObjectFactory::createMultiRefIndirectParent, (dao, domain) -> getInstance().setMultiRefIndirectParentValues(dao, domain)
				, (dao, domain) -> getInstance().setMultiRefIndirectParentSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setMultiRefIndirectParentMultiReferences(dao, domain, mappedObjects));
	}

	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDao multiRefIndirectParent, MultiRefOtherIndirectParent parent) {
		return convertToMultiRefIndirectParent(multiRefIndirectParent, parent, new HashMap<>());
	}

	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDao multiRefIndirectParent, MultiRefOtherIndirectParent parent, Map<String, IIdentifiable> mappedObjects) {
		MultiRefIndirectParent result = convertToMultiRefIndirectParent(multiRefIndirectParent, mappedObjects);
		if (result != null) {
			parent.getMultiIndirectRefs().add(result);
		}
		return result;
	}

	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDao multiRefIndirectParent, Root parent) {
		return convertToMultiRefIndirectParent(multiRefIndirectParent, parent, new HashMap<>());
	}

	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDao multiRefIndirectParent, Root parent, Map<String, IIdentifiable> mappedObjects) {
		MultiRefIndirectParent result = convertToMultiRefIndirectParent(multiRefIndirectParent, mappedObjects);
		if (result != null) {
			parent.getMultiRefIndirectParents().add(result);
		}
		return result;
	}

	public static MultiRefIndirectParentDao convertToMultiRefIndirectParentDao(MultiRefIndirectParent multiRefIndirectParent) {
		return convertToMultiRefIndirectParentDao(multiRefIndirectParent, new HashMap<>());
	}

	public static MultiRefIndirectParentDao convertToMultiRefIndirectParentDao(MultiRefIndirectParent multiRefIndirectParent, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(multiRefIndirectParent, mappedObjects, DaoObjectFactory::createMultiRefIndirectParentDao, (domain, dao) -> getInstance().setMultiRefIndirectParentDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setMultiRefIndirectParentDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setMultiRefIndirectParentDaoMultiReferences(domain, dao, mappedObjects));
	}

	public static MultiRefIndirectParentDao convertToMultiRefIndirectParentDao(MultiRefIndirectParent multiRefIndirectParent, MultiRefOtherIndirectParentDao parent) {
		return convertToMultiRefIndirectParentDao(multiRefIndirectParent, parent, new HashMap<>());
	}

	public static MultiRefIndirectParentDao convertToMultiRefIndirectParentDao(MultiRefIndirectParent multiRefIndirectParent, MultiRefOtherIndirectParentDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		MultiRefIndirectParentDao result = convertToMultiRefIndirectParentDao(multiRefIndirectParent, mappedObjects);
		if (result != null) {
			MultiRefOtherIndirectParentToMultiRefIndirectParentDao connectionTable = DaoObjectFactory.createMultiRefOtherIndirectParentToMultiRefIndirectParentDao();
			connectionTable.setMultiRefIndirectParent(result);
			connectionTable.setMultiRefOtherIndirectParent(parent);
			parent.getMultiIndirectRefs().add(connectionTable);
		}
		return result;
	}

	public static MultiRefIndirectParentDao convertToMultiRefIndirectParentDao(MultiRefIndirectParent multiRefIndirectParent, RootDao parent) {
		return convertToMultiRefIndirectParentDao(multiRefIndirectParent, parent, new HashMap<>());
	}

	public static MultiRefIndirectParentDao convertToMultiRefIndirectParentDao(MultiRefIndirectParent multiRefIndirectParent, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		MultiRefIndirectParentDao result = convertToMultiRefIndirectParentDao(multiRefIndirectParent, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.getMultiRefIndirectParents().add(result);
		}
		return result;
	}

	public static MultiRefOtherIndirectParent convertToMultiRefOtherIndirectParent(MultiRefOtherIndirectParentDao multiRefOtherIndirectParent, boolean includeChildren) {
		return convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParent, includeChildren, new HashMap<>());
	}

	public static MultiRefOtherIndirectParent convertToMultiRefOtherIndirectParent(MultiRefOtherIndirectParentDao multiRefOtherIndirectParent, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(multiRefOtherIndirectParent, mappedObjects, DomainObjectFactory::createMultiRefOtherIndirectParent, (dao, domain) -> getInstance().setMultiRefOtherIndirectParentValues(dao, domain)
				, (dao, domain) -> getInstance().setMultiRefOtherIndirectParentSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setMultiRefOtherIndirectParentMultiReferences(dao, domain, includeChildren, mappedObjects));
	}

	public static MultiRefOtherIndirectParent convertToMultiRefOtherIndirectParent(MultiRefOtherIndirectParentDao multiRefOtherIndirectParent, boolean includeChildren, Root parent) {
		return convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParent, includeChildren, parent, new HashMap<>());
	}

	public static MultiRefOtherIndirectParent convertToMultiRefOtherIndirectParent(MultiRefOtherIndirectParentDao multiRefOtherIndirectParent, boolean includeChildren, Root parent, Map<String, IIdentifiable> mappedObjects) {
		MultiRefOtherIndirectParent result = convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParent, includeChildren, mappedObjects);
		if (result != null) {
			parent.getMultiRefIndirectOtherParents().add(result);
		}
		return result;
	}

	public static MultiRefOtherIndirectParentDao convertToMultiRefOtherIndirectParentDao(MultiRefOtherIndirectParent multiRefOtherIndirectParent, boolean includeChildren) {
		return convertToMultiRefOtherIndirectParentDao(multiRefOtherIndirectParent, includeChildren, new HashMap<>());
	}

	public static MultiRefOtherIndirectParentDao convertToMultiRefOtherIndirectParentDao(MultiRefOtherIndirectParent multiRefOtherIndirectParent, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(multiRefOtherIndirectParent, mappedObjects, DaoObjectFactory::createMultiRefOtherIndirectParentDao, (domain, dao) -> getInstance().setMultiRefOtherIndirectParentDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setMultiRefOtherIndirectParentDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setMultiRefOtherIndirectParentDaoMultiReferences(domain, dao, includeChildren, mappedObjects));
	}

	public static MultiRefOtherIndirectParentDao convertToMultiRefOtherIndirectParentDao(MultiRefOtherIndirectParent multiRefOtherIndirectParent, boolean includeChildren, RootDao parent) {
		return convertToMultiRefOtherIndirectParentDao(multiRefOtherIndirectParent, includeChildren, parent, new HashMap<>());
	}

	public static MultiRefOtherIndirectParentDao convertToMultiRefOtherIndirectParentDao(MultiRefOtherIndirectParent multiRefOtherIndirectParent, boolean includeChildren, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		MultiRefOtherIndirectParentDao result = convertToMultiRefOtherIndirectParentDao(multiRefOtherIndirectParent, includeChildren, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.getMultiRefIndirectOtherParents().add(result);
		}
		return result;
	}

	/**
	 * @return the singleton
	 */
	public static MultiIndirectAccessMapper getInstance() {
		if (instance == null) {
			instance = AccessMapperFactory.createMultiIndirectAccessMapper();
		}
		return instance;
	}

	@SuppressWarnings("java:S1186")
	protected void setMultiRefIndirectParentDaoMultiReferences(MultiRefIndirectParent domain, MultiRefIndirectParentDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setMultiRefIndirectParentDaoSingleReferences(MultiRefIndirectParent domain, MultiRefIndirectParentDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	protected void setMultiRefIndirectParentDaoValues(MultiRefIndirectParent domain, MultiRefIndirectParentDao dao) {
		dao.setDescription(domain.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setMultiRefIndirectParentMultiReferences(MultiRefIndirectParentDao dao, MultiRefIndirectParent domain, Map<String, IIdentifiable> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setMultiRefIndirectParentSingleReferences(MultiRefIndirectParentDao dao, MultiRefIndirectParent domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setMultiRefIndirectParentValues(MultiRefIndirectParentDao dao, MultiRefIndirectParent domain) {
		domain.setDescription(dao.getDescription());
	}

	protected void setMultiRefOtherIndirectParentDaoMultiReferences(MultiRefOtherIndirectParent domain, MultiRefOtherIndirectParentDao dao, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {
		dao.setMultiIndirectRefs(new ArrayList<>());
		if (includeChildren) {
			domain.getMultiIndirectRefs().forEach(arg -> {
				MultiRefOtherIndirectParentToMultiRefIndirectParentDao connectionTable = new MultiRefOtherIndirectParentToMultiRefIndirectParentDao();
				connectionTable.setMultiRefOtherIndirectParent(dao);
				connectionTable.setMultiRefIndirectParent(MultiIndirectAccessMapper.convertToMultiRefIndirectParentDao(arg, mappedObjects));
				dao.getMultiIndirectRefs().add(connectionTable);
			});
		}
	}

	@SuppressWarnings("java:S1186")
	protected void setMultiRefOtherIndirectParentDaoSingleReferences(MultiRefOtherIndirectParent domain, MultiRefOtherIndirectParentDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	protected void setMultiRefOtherIndirectParentDaoValues(MultiRefOtherIndirectParent domain, MultiRefOtherIndirectParentDao dao) {
		dao.setDescription(domain.getDescription());
	}

	protected void setMultiRefOtherIndirectParentMultiReferences(MultiRefOtherIndirectParentDao dao, MultiRefOtherIndirectParent domain, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		if (includeChildren) {
			dao.getMultiIndirectRefs().forEach(arg ->
					MultiIndirectAccessMapper.convertToMultiRefIndirectParent(arg.getMultiRefIndirectParent(), domain, mappedObjects)
			);
		}
	}

	@SuppressWarnings("java:S1186")
	protected void setMultiRefOtherIndirectParentSingleReferences(MultiRefOtherIndirectParentDao dao, MultiRefOtherIndirectParent domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setMultiRefOtherIndirectParentValues(MultiRefOtherIndirectParentDao dao, MultiRefOtherIndirectParent domain) {
		domain.setDescription(dao.getDescription());
	}

}
