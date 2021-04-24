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

/**
 * Generated class which provides methods to convert a data access to a domain object of sub package <i>multi.indirect<i> and the other way around
 */
@BaseAccessMapper
public class MultiIndirectAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static MultiIndirectAccessMapper instance;

	/**
	 * Converts a(n) {@link MultiRefIndirectParentDao} to a(n) {@link MultiRefIndirectParent}
	 *
	 * @param multiRefIndirectParent the source object which should be converted
	 * @return an equivalent new created {@link MultiRefIndirectParent}
	 */
	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDao multiRefIndirectParent) {
		return convertToMultiRefIndirectParent(multiRefIndirectParent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefIndirectParentDao} to a(n) {@link MultiRefIndirectParent}
	 *
	 * @param multiRefIndirectParent the source object which should be converted
	 * @param mappedObjects          map which contains already mapped objects. If an identification of {@code multiRefIndirectParent} is contained, the
	 *                               found {@link MultiRefIndirectParent} will be returned
	 * @return an equivalent new created {@link MultiRefIndirectParent} or the found one from the given map
	 */
	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDao multiRefIndirectParent, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(multiRefIndirectParent, mappedObjects, DomainObjectFactory::createMultiRefIndirectParent, (dao, domain) -> getInstance().setMultiRefIndirectParentValues(dao, domain)
				, (dao, domain) -> getInstance().setMultiRefIndirectParentSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setMultiRefIndirectParentMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link MultiRefIndirectParentDao} to a(n) {@link MultiRefIndirectParent} and sets the result to the corresponding reference property
	 * at the parent
	 *
	 * @param multiRefIndirectParent the source object which should be converted
	 * @param parent                 the parent of converted result
	 * @return an equivalent new created {@link MultiRefIndirectParent}
	 */
	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDao multiRefIndirectParent, MultiRefOtherIndirectParent parent) {
		return convertToMultiRefIndirectParent(multiRefIndirectParent, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefIndirectParentDao} to a(n) {@link MultiRefIndirectParent} and sets the result to the corresponding reference property
	 * at the parent
	 *
	 * @param multiRefIndirectParent the source object which should be converted
	 * @param parent                 the parent of converted result
	 * @param mappedObjects          map which contains already mapped objects. If an identification of {@code multiRefIndirectParent} is contained, the
	 *                               found {@link MultiRefIndirectParent} will be returned
	 * @return an equivalent new created {@link MultiRefIndirectParent} or the found one from the given map
	 */
	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDao multiRefIndirectParent, MultiRefOtherIndirectParent parent, Map<String, IIdentifiable> mappedObjects) {
		MultiRefIndirectParent result = convertToMultiRefIndirectParent(multiRefIndirectParent, mappedObjects);
		if (result != null) {
			parent.getMultiIndirectRefs().add(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link MultiRefIndirectParentDao} to a(n) {@link MultiRefIndirectParent} and sets the result to the corresponding reference property
	 * at the parent
	 *
	 * @param multiRefIndirectParent the source object which should be converted
	 * @param parent                 the parent of converted result
	 * @return an equivalent new created {@link MultiRefIndirectParent}
	 */
	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDao multiRefIndirectParent, Root parent) {
		return convertToMultiRefIndirectParent(multiRefIndirectParent, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefIndirectParentDao} to a(n) {@link MultiRefIndirectParent} and sets the result to the corresponding reference property
	 * at the parent
	 *
	 * @param multiRefIndirectParent the source object which should be converted
	 * @param parent                 the parent of converted result
	 * @param mappedObjects          map which contains already mapped objects. If an identification of {@code multiRefIndirectParent} is contained, the
	 *                               found {@link MultiRefIndirectParent} will be returned
	 * @return an equivalent new created {@link MultiRefIndirectParent} or the found one from the given map
	 */
	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDao multiRefIndirectParent, Root parent, Map<String, IIdentifiable> mappedObjects) {
		MultiRefIndirectParent result = convertToMultiRefIndirectParent(multiRefIndirectParent, mappedObjects);
		if (result != null) {
			parent.getMultiRefIndirectParents().add(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link MultiRefIndirectParent} to a(n) {@link MultiRefIndirectParentDao}
	 *
	 * @param multiRefIndirectParent the source object which should be converted
	 * @return an equivalent new created {@link MultiRefIndirectParentDao}
	 */
	public static MultiRefIndirectParentDao convertToMultiRefIndirectParentDao(MultiRefIndirectParent multiRefIndirectParent) {
		return convertToMultiRefIndirectParentDao(multiRefIndirectParent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefIndirectParent} to a(n) {@link MultiRefIndirectParentDao}
	 *
	 * @param multiRefIndirectParent the source object which should be converted
	 * @param mappedObjects          map which contains already mapped objects. If an identification of {@code multiRefIndirectParent} is contained, the
	 *                               found {@link MultiRefIndirectParentDao} will be returned
	 * @return an equivalent new created {@link MultiRefIndirectParentDao} or the found one from the given map
	 */
	public static MultiRefIndirectParentDao convertToMultiRefIndirectParentDao(MultiRefIndirectParent multiRefIndirectParent, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(multiRefIndirectParent, mappedObjects, DaoObjectFactory::createMultiRefIndirectParentDao, (domain, dao) -> getInstance().setMultiRefIndirectParentDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setMultiRefIndirectParentDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setMultiRefIndirectParentDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * Converts a(n) {@link MultiRefIndirectParent} to a(n) {@link MultiRefIndirectParentDao} and sets the result to the corresponding reference property
	 * at the parent
	 *
	 * @param multiRefIndirectParent the source object which should be converted
	 * @param parent                 the parent of converted result
	 * @return an equivalent new created {@link MultiRefIndirectParentDao}
	 */
	public static MultiRefIndirectParentDao convertToMultiRefIndirectParentDao(MultiRefIndirectParent multiRefIndirectParent, MultiRefOtherIndirectParentDao parent) {
		return convertToMultiRefIndirectParentDao(multiRefIndirectParent, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefIndirectParent} to a(n) {@link MultiRefIndirectParentDao} and sets the result to the corresponding reference property
	 * at the parent
	 *
	 * @param multiRefIndirectParent the source object which should be converted
	 * @param parent                 the parent of converted result
	 * @param mappedObjects          map which contains already mapped objects. If an identification of {@code multiRefIndirectParent} is contained, the
	 *                               found {@link MultiRefIndirectParentDao} will be returned
	 * @return an equivalent new created {@link MultiRefIndirectParentDao} or the found one from the given map
	 */
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

	/**
	 * Converts a(n) {@link MultiRefIndirectParent} to a(n) {@link MultiRefIndirectParentDao} and sets the result to the corresponding reference property
	 * at the parent
	 *
	 * @param multiRefIndirectParent the source object which should be converted
	 * @param parent                 the parent of converted result
	 * @return an equivalent new created {@link MultiRefIndirectParentDao}
	 */
	public static MultiRefIndirectParentDao convertToMultiRefIndirectParentDao(MultiRefIndirectParent multiRefIndirectParent, RootDao parent) {
		return convertToMultiRefIndirectParentDao(multiRefIndirectParent, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefIndirectParent} to a(n) {@link MultiRefIndirectParentDao} and sets the result to the corresponding reference property
	 * at the parent
	 *
	 * @param multiRefIndirectParent the source object which should be converted
	 * @param parent                 the parent of converted result
	 * @param mappedObjects          map which contains already mapped objects. If an identification of {@code multiRefIndirectParent} is contained, the
	 *                               found {@link MultiRefIndirectParentDao} will be returned
	 * @return an equivalent new created {@link MultiRefIndirectParentDao} or the found one from the given map
	 */
	public static MultiRefIndirectParentDao convertToMultiRefIndirectParentDao(MultiRefIndirectParent multiRefIndirectParent, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		MultiRefIndirectParentDao result = convertToMultiRefIndirectParentDao(multiRefIndirectParent, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.getMultiRefIndirectParents().add(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link MultiRefOtherIndirectParentDao} to a(n) {@link MultiRefOtherIndirectParent}
	 *
	 * @param multiRefOtherIndirectParent the source object which should be converted
	 * @param includeChildren             {@code true} if all references should also be mapped. {@code false} if only those references should be mapped
	 *                                    which are not of type {@link java.util.Collection}
	 * @return an equivalent new created {@link MultiRefOtherIndirectParent}
	 */
	public static MultiRefOtherIndirectParent convertToMultiRefOtherIndirectParent(MultiRefOtherIndirectParentDao multiRefOtherIndirectParent, boolean includeChildren) {
		return convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParent, includeChildren, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefOtherIndirectParentDao} to a(n) {@link MultiRefOtherIndirectParent}
	 *
	 * @param multiRefOtherIndirectParent the source object which should be converted
	 * @param includeChildren             {@code true} if all references should also be mapped. {@code false} if only those references should be mapped
	 *                                    which are not of type {@link java.util.Collection}
	 * @param mappedObjects               map which contains already mapped objects. If an identification of {@code multiRefOtherIndirectParent} is
	 *                                    contained, the found {@link MultiRefOtherIndirectParent} will be returned
	 * @return an equivalent new created {@link MultiRefOtherIndirectParent} or the found one from the given map
	 */
	public static MultiRefOtherIndirectParent convertToMultiRefOtherIndirectParent(MultiRefOtherIndirectParentDao multiRefOtherIndirectParent, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(multiRefOtherIndirectParent, mappedObjects, DomainObjectFactory::createMultiRefOtherIndirectParent, (dao, domain) -> getInstance().setMultiRefOtherIndirectParentValues(dao, domain)
				, (dao, domain) -> getInstance().setMultiRefOtherIndirectParentSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setMultiRefOtherIndirectParentMultiReferences(dao, domain, includeChildren, mappedObjects));
	}

	/**
	 * Converts a(n) {@link MultiRefOtherIndirectParentDao} to a(n) {@link MultiRefOtherIndirectParent} and sets the result to the corresponding reference
	 * property at the parent
	 *
	 * @param multiRefOtherIndirectParent the source object which should be converted
	 * @param includeChildren             {@code true} if all references should also be mapped. {@code false} if only those references should be mapped
	 *                                    which are not of type {@link java.util.Collection}
	 * @param parent                      the parent of converted result
	 * @return an equivalent new created {@link MultiRefOtherIndirectParent}
	 */
	public static MultiRefOtherIndirectParent convertToMultiRefOtherIndirectParent(MultiRefOtherIndirectParentDao multiRefOtherIndirectParent, boolean includeChildren, Root parent) {
		return convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParent, includeChildren, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefOtherIndirectParentDao} to a(n) {@link MultiRefOtherIndirectParent} and sets the result to the corresponding reference
	 * property at the parent
	 *
	 * @param multiRefOtherIndirectParent the source object which should be converted
	 * @param includeChildren             {@code true} if all references should also be mapped. {@code false} if only those references should be mapped
	 *                                    which are not of type {@link java.util.Collection}
	 * @param parent                      the parent of converted result
	 * @param mappedObjects               map which contains already mapped objects. If an identification of {@code multiRefOtherIndirectParent} is
	 *                                    contained, the found {@link MultiRefOtherIndirectParent} will be returned
	 * @return an equivalent new created {@link MultiRefOtherIndirectParent} or the found one from the given map
	 */
	public static MultiRefOtherIndirectParent convertToMultiRefOtherIndirectParent(MultiRefOtherIndirectParentDao multiRefOtherIndirectParent, boolean includeChildren, Root parent, Map<String, IIdentifiable> mappedObjects) {
		MultiRefOtherIndirectParent result = convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParent, includeChildren, mappedObjects);
		if (result != null) {
			parent.getMultiRefIndirectOtherParents().add(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link MultiRefOtherIndirectParent} to a(n) {@link MultiRefOtherIndirectParentDao}
	 *
	 * @param multiRefOtherIndirectParent the source object which should be converted
	 * @param includeChildren             {@code true} if all references should also be mapped. {@code false} if only those references should be mapped
	 *                                    which are not of type {@link java.util.Collection}
	 * @return an equivalent new created {@link MultiRefOtherIndirectParentDao}
	 */
	public static MultiRefOtherIndirectParentDao convertToMultiRefOtherIndirectParentDao(MultiRefOtherIndirectParent multiRefOtherIndirectParent, boolean includeChildren) {
		return convertToMultiRefOtherIndirectParentDao(multiRefOtherIndirectParent, includeChildren, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefOtherIndirectParent} to a(n) {@link MultiRefOtherIndirectParentDao}
	 *
	 * @param multiRefOtherIndirectParent the source object which should be converted
	 * @param includeChildren             {@code true} if all references should also be mapped. {@code false} if only those references should be mapped
	 *                                    which are not of type {@link java.util.Collection}
	 * @param mappedObjects               map which contains already mapped objects. If an identification of {@code multiRefOtherIndirectParent} is
	 *                                    contained, the found {@link MultiRefOtherIndirectParentDao} will be returned
	 * @return an equivalent new created {@link MultiRefOtherIndirectParentDao} or the found one from the given map
	 */
	public static MultiRefOtherIndirectParentDao convertToMultiRefOtherIndirectParentDao(MultiRefOtherIndirectParent multiRefOtherIndirectParent, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(multiRefOtherIndirectParent, mappedObjects, DaoObjectFactory::createMultiRefOtherIndirectParentDao, (domain, dao) -> getInstance().setMultiRefOtherIndirectParentDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setMultiRefOtherIndirectParentDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setMultiRefOtherIndirectParentDaoMultiReferences(domain, dao, includeChildren, mappedObjects));
	}

	/**
	 * Converts a(n) {@link MultiRefOtherIndirectParent} to a(n) {@link MultiRefOtherIndirectParentDao} and sets the result to the corresponding reference
	 * property at the parent
	 *
	 * @param multiRefOtherIndirectParent the source object which should be converted
	 * @param includeChildren             {@code true} if all references should also be mapped. {@code false} if only those references should be mapped
	 *                                    which are not of type {@link java.util.Collection}
	 * @param parent                      the parent of converted result
	 * @return an equivalent new created {@link MultiRefOtherIndirectParentDao}
	 */
	public static MultiRefOtherIndirectParentDao convertToMultiRefOtherIndirectParentDao(MultiRefOtherIndirectParent multiRefOtherIndirectParent, boolean includeChildren, RootDao parent) {
		return convertToMultiRefOtherIndirectParentDao(multiRefOtherIndirectParent, includeChildren, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefOtherIndirectParent} to a(n) {@link MultiRefOtherIndirectParentDao} and sets the result to the corresponding reference
	 * property at the parent
	 *
	 * @param multiRefOtherIndirectParent the source object which should be converted
	 * @param includeChildren             {@code true} if all references should also be mapped. {@code false} if only those references should be mapped
	 *                                    which are not of type {@link java.util.Collection}
	 * @param parent                      the parent of converted result
	 * @param mappedObjects               map which contains already mapped objects. If an identification of {@code multiRefOtherIndirectParent} is
	 *                                    contained, the found {@link MultiRefOtherIndirectParentDao} will be returned
	 * @return an equivalent new created {@link MultiRefOtherIndirectParentDao} or the found one from the given map
	 */
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
