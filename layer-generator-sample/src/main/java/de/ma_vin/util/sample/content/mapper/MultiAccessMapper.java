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

/**
 * Generated class which provides methods to convert a data access to a domain object of sub package <i>multi<i> and the other way around
 */
@BaseAccessMapper
public class MultiAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static MultiAccessMapper instance;

	/**
	 * Converts a(n) {@link MultiRefOneParentDao} to a(n) {@link MultiRefOneParent}
	 *
	 * @param multiRefOneParent the source object which should be converted
	 * @param includeChildren   {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are
	 *                          not of type {@link java.util.Collection}
	 * @return an equivalent new created {@link MultiRefOneParent}
	 */
	public static MultiRefOneParent convertToMultiRefOneParent(MultiRefOneParentDao multiRefOneParent, boolean includeChildren) {
		return convertToMultiRefOneParent(multiRefOneParent, includeChildren, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefOneParentDao} to a(n) {@link MultiRefOneParent}
	 *
	 * @param multiRefOneParent the source object which should be converted
	 * @param includeChildren   {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are
	 *                          not of type {@link java.util.Collection}
	 * @param mappedObjects     map which contains already mapped objects. If an identification of {@code multiRefOneParent} is contained, the found
	 *                          {@link MultiRefOneParent} will be returned
	 * @return an equivalent new created {@link MultiRefOneParent} or the found one from the given map
	 */
	public static MultiRefOneParent convertToMultiRefOneParent(MultiRefOneParentDao multiRefOneParent, boolean includeChildren
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(multiRefOneParent, mappedObjects, DomainObjectFactory::createMultiRefOneParent, (dao, domain) -> getInstance().setMultiRefOneParentValues(dao, domain)
				, (dao, domain) -> getInstance().setMultiRefOneParentSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setMultiRefOneParentMultiReferences(dao, domain, includeChildren, mappedObjects));
	}

	/**
	 * Converts a(n) {@link MultiRefOneParentDao} to a(n) {@link MultiRefOneParent} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param multiRefOneParent the source object which should be converted
	 * @param includeChildren   {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are
	 *                          not of type {@link java.util.Collection}
	 * @param parent            the parent of converted result
	 * @return an equivalent new created {@link MultiRefOneParent}
	 */
	public static MultiRefOneParent convertToMultiRefOneParent(MultiRefOneParentDao multiRefOneParent, boolean includeChildren, Root parent) {
		return convertToMultiRefOneParent(multiRefOneParent, includeChildren, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefOneParentDao} to a(n) {@link MultiRefOneParent} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param multiRefOneParent the source object which should be converted
	 * @param includeChildren   {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are
	 *                          not of type {@link java.util.Collection}
	 * @param parent            the parent of converted result
	 * @param mappedObjects     map which contains already mapped objects. If an identification of {@code multiRefOneParent} is contained, the found
	 *                          {@link MultiRefOneParent} will be returned
	 * @return an equivalent new created {@link MultiRefOneParent} or the found one from the given map
	 */
	public static MultiRefOneParent convertToMultiRefOneParent(MultiRefOneParentDao multiRefOneParent, boolean includeChildren, Root parent
			, Map<String, IIdentifiable> mappedObjects) {
		MultiRefOneParent result = convertToMultiRefOneParent(multiRefOneParent, includeChildren, mappedObjects);
		if (result != null) {
			parent.getMultiRef().add(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link MultiRefOneParent} to a(n) {@link MultiRefOneParentDao}
	 *
	 * @param multiRefOneParent the source object which should be converted
	 * @param includeChildren   {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are
	 *                          not of type {@link java.util.Collection}
	 * @return an equivalent new created {@link MultiRefOneParentDao}
	 */
	public static MultiRefOneParentDao convertToMultiRefOneParentDao(MultiRefOneParent multiRefOneParent, boolean includeChildren) {
		return convertToMultiRefOneParentDao(multiRefOneParent, includeChildren, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefOneParent} to a(n) {@link MultiRefOneParentDao}
	 *
	 * @param multiRefOneParent the source object which should be converted
	 * @param includeChildren   {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are
	 *                          not of type {@link java.util.Collection}
	 * @param mappedObjects     map which contains already mapped objects. If an identification of {@code multiRefOneParent} is contained, the found
	 *                          {@link MultiRefOneParentDao} will be returned
	 * @return an equivalent new created {@link MultiRefOneParentDao} or the found one from the given map
	 */
	public static MultiRefOneParentDao convertToMultiRefOneParentDao(MultiRefOneParent multiRefOneParent, boolean includeChildren
			, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(multiRefOneParent, mappedObjects, DaoObjectFactory::createMultiRefOneParentDao, (domain, dao) -> getInstance().setMultiRefOneParentDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setMultiRefOneParentDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setMultiRefOneParentDaoMultiReferences(domain, dao, includeChildren, mappedObjects));
	}

	/**
	 * Converts a(n) {@link MultiRefOneParent} to a(n) {@link MultiRefOneParentDao} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param multiRefOneParent the source object which should be converted
	 * @param includeChildren   {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are
	 *                          not of type {@link java.util.Collection}
	 * @param parent            the parent of converted result
	 * @return an equivalent new created {@link MultiRefOneParentDao}
	 */
	public static MultiRefOneParentDao convertToMultiRefOneParentDao(MultiRefOneParent multiRefOneParent, boolean includeChildren, RootDao parent) {
		return convertToMultiRefOneParentDao(multiRefOneParent, includeChildren, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefOneParent} to a(n) {@link MultiRefOneParentDao} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param multiRefOneParent the source object which should be converted
	 * @param includeChildren   {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are
	 *                          not of type {@link java.util.Collection}
	 * @param parent            the parent of converted result
	 * @param mappedObjects     map which contains already mapped objects. If an identification of {@code multiRefOneParent} is contained, the found
	 *                          {@link MultiRefOneParentDao} will be returned
	 * @return an equivalent new created {@link MultiRefOneParentDao} or the found one from the given map
	 */
	public static MultiRefOneParentDao convertToMultiRefOneParentDao(MultiRefOneParent multiRefOneParent, boolean includeChildren, RootDao parent
			, Map<String, IIdentifiableDao> mappedObjects) {
		MultiRefOneParentDao result = convertToMultiRefOneParentDao(multiRefOneParent, includeChildren, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.getMultiRef().add(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link MultiRefTwoParentsDao} to a(n) {@link MultiRefTwoParents}
	 *
	 * @param multiRefTwoParents the source object which should be converted
	 * @return an equivalent new created {@link MultiRefTwoParents}
	 */
	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDao multiRefTwoParents) {
		return convertToMultiRefTwoParents(multiRefTwoParents, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefTwoParentsDao} to a(n) {@link MultiRefTwoParents}
	 *
	 * @param multiRefTwoParents the source object which should be converted
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code multiRefTwoParents} is contained, the found
	 *                           {@link MultiRefTwoParents} will be returned
	 * @return an equivalent new created {@link MultiRefTwoParents} or the found one from the given map
	 */
	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDao multiRefTwoParents, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(multiRefTwoParents, mappedObjects, DomainObjectFactory::createMultiRefTwoParents, (dao, domain) -> getInstance().setMultiRefTwoParentsValues(dao, domain)
				, (dao, domain) -> getInstance().setMultiRefTwoParentsSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setMultiRefTwoParentsMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link MultiRefTwoParentsDao} to a(n) {@link MultiRefTwoParents} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param multiRefTwoParents the source object which should be converted
	 * @param parent             the parent of converted result
	 * @return an equivalent new created {@link MultiRefTwoParents}
	 */
	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDao multiRefTwoParents, MultiRefOneParent parent) {
		return convertToMultiRefTwoParents(multiRefTwoParents, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefTwoParentsDao} to a(n) {@link MultiRefTwoParents} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param multiRefTwoParents the source object which should be converted
	 * @param parent             the parent of converted result
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code multiRefTwoParents} is contained, the found
	 *                           {@link MultiRefTwoParents} will be returned
	 * @return an equivalent new created {@link MultiRefTwoParents} or the found one from the given map
	 */
	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDao multiRefTwoParents, MultiRefOneParent parent
			, Map<String, IIdentifiable> mappedObjects) {
		MultiRefTwoParents result = convertToMultiRefTwoParents(multiRefTwoParents, mappedObjects);
		if (result != null) {
			parent.getMultiRef().add(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link MultiRefTwoParentsDao} to a(n) {@link MultiRefTwoParents} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param multiRefTwoParents the source object which should be converted
	 * @param parent             the parent of converted result
	 * @return an equivalent new created {@link MultiRefTwoParents}
	 */
	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDao multiRefTwoParents, Root parent) {
		return convertToMultiRefTwoParents(multiRefTwoParents, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefTwoParentsDao} to a(n) {@link MultiRefTwoParents} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param multiRefTwoParents the source object which should be converted
	 * @param parent             the parent of converted result
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code multiRefTwoParents} is contained, the found
	 *                           {@link MultiRefTwoParents} will be returned
	 * @return an equivalent new created {@link MultiRefTwoParents} or the found one from the given map
	 */
	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDao multiRefTwoParents, Root parent
			, Map<String, IIdentifiable> mappedObjects) {
		MultiRefTwoParents result = convertToMultiRefTwoParents(multiRefTwoParents, mappedObjects);
		if (result != null) {
			parent.getAnotherMultiRef().add(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link MultiRefTwoParents} to a(n) {@link MultiRefTwoParentsDao}
	 *
	 * @param multiRefTwoParents the source object which should be converted
	 * @return an equivalent new created {@link MultiRefTwoParentsDao}
	 */
	public static MultiRefTwoParentsDao convertToMultiRefTwoParentsDao(MultiRefTwoParents multiRefTwoParents) {
		return convertToMultiRefTwoParentsDao(multiRefTwoParents, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefTwoParents} to a(n) {@link MultiRefTwoParentsDao}
	 *
	 * @param multiRefTwoParents the source object which should be converted
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code multiRefTwoParents} is contained, the found
	 *                           {@link MultiRefTwoParentsDao} will be returned
	 * @return an equivalent new created {@link MultiRefTwoParentsDao} or the found one from the given map
	 */
	public static MultiRefTwoParentsDao convertToMultiRefTwoParentsDao(MultiRefTwoParents multiRefTwoParents, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(multiRefTwoParents, mappedObjects, DaoObjectFactory::createMultiRefTwoParentsDao, (domain, dao) -> getInstance().setMultiRefTwoParentsDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setMultiRefTwoParentsDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setMultiRefTwoParentsDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * Converts a(n) {@link MultiRefTwoParents} to a(n) {@link MultiRefTwoParentsDao} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param multiRefTwoParents the source object which should be converted
	 * @param parent             the parent of converted result
	 * @return an equivalent new created {@link MultiRefTwoParentsDao}
	 */
	public static MultiRefTwoParentsDao convertToMultiRefTwoParentsDao(MultiRefTwoParents multiRefTwoParents, MultiRefOneParentDao parent) {
		return convertToMultiRefTwoParentsDao(multiRefTwoParents, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefTwoParents} to a(n) {@link MultiRefTwoParentsDao} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param multiRefTwoParents the source object which should be converted
	 * @param parent             the parent of converted result
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code multiRefTwoParents} is contained, the found
	 *                           {@link MultiRefTwoParentsDao} will be returned
	 * @return an equivalent new created {@link MultiRefTwoParentsDao} or the found one from the given map
	 */
	public static MultiRefTwoParentsDao convertToMultiRefTwoParentsDao(MultiRefTwoParents multiRefTwoParents, MultiRefOneParentDao parent
			, Map<String, IIdentifiableDao> mappedObjects) {
		MultiRefTwoParentsDao result = convertToMultiRefTwoParentsDao(multiRefTwoParents, mappedObjects);
		if (result != null) {
			result.setParentMultiRefOneParent(parent);
			parent.getMultiRef().add(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link MultiRefTwoParents} to a(n) {@link MultiRefTwoParentsDao} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param multiRefTwoParents the source object which should be converted
	 * @param parent             the parent of converted result
	 * @return an equivalent new created {@link MultiRefTwoParentsDao}
	 */
	public static MultiRefTwoParentsDao convertToMultiRefTwoParentsDao(MultiRefTwoParents multiRefTwoParents, RootDao parent) {
		return convertToMultiRefTwoParentsDao(multiRefTwoParents, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefTwoParents} to a(n) {@link MultiRefTwoParentsDao} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param multiRefTwoParents the source object which should be converted
	 * @param parent             the parent of converted result
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code multiRefTwoParents} is contained, the found
	 *                           {@link MultiRefTwoParentsDao} will be returned
	 * @return an equivalent new created {@link MultiRefTwoParentsDao} or the found one from the given map
	 */
	public static MultiRefTwoParentsDao convertToMultiRefTwoParentsDao(MultiRefTwoParents multiRefTwoParents, RootDao parent
			, Map<String, IIdentifiableDao> mappedObjects) {
		MultiRefTwoParentsDao result = convertToMultiRefTwoParentsDao(multiRefTwoParents, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.getAnotherMultiRef().add(result);
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

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain          source of the given references
	 * @param dao             object where to add the references
	 * @param includeChildren {@code true} if references should be mapped. Otherwise {@code false}
	 * @param mappedObjects   map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	protected void setMultiRefOneParentDaoMultiReferences(MultiRefOneParent domain, MultiRefOneParentDao dao, boolean includeChildren
			, Map<String, IIdentifiableDao> mappedObjects) {
		dao.setMultiRef(new ArrayList<>());
		if (includeChildren) {
			domain.getMultiRef().forEach(arg ->
					MultiAccessMapper.convertToMultiRefTwoParentsDao(arg, dao, mappedObjects)
			);
		}
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setMultiRefOneParentDaoSingleReferences(MultiRefOneParent domain, MultiRefOneParentDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	protected void setMultiRefOneParentDaoValues(MultiRefOneParent domain, MultiRefOneParentDao dao) {
		dao.setDescription(domain.getDescription());
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao             source of the given references
	 * @param domain          object where to add the references
	 * @param includeChildren {@code true} if references should be mapped. Otherwise {@code false}
	 * @param mappedObjects   map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	protected void setMultiRefOneParentMultiReferences(MultiRefOneParentDao dao, MultiRefOneParent domain, boolean includeChildren
			, Map<String, IIdentifiable> mappedObjects) {
		if (includeChildren) {
			dao.getMultiRef().forEach(arg ->
					MultiAccessMapper.convertToMultiRefTwoParents(arg, domain, mappedObjects)
			);
		}
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setMultiRefOneParentSingleReferences(MultiRefOneParentDao dao, MultiRefOneParent domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setMultiRefOneParentValues(MultiRefOneParentDao dao, MultiRefOneParent domain) {
		domain.setDescription(dao.getDescription());
	}

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setMultiRefTwoParentsDaoMultiReferences(MultiRefTwoParents domain, MultiRefTwoParentsDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setMultiRefTwoParentsDaoSingleReferences(MultiRefTwoParents domain, MultiRefTwoParentsDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	protected void setMultiRefTwoParentsDaoValues(MultiRefTwoParents domain, MultiRefTwoParentsDao dao) {
		dao.setDescription(domain.getDescription());
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setMultiRefTwoParentsMultiReferences(MultiRefTwoParentsDao dao, MultiRefTwoParents domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setMultiRefTwoParentsSingleReferences(MultiRefTwoParentsDao dao, MultiRefTwoParents domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setMultiRefTwoParentsValues(MultiRefTwoParentsDao dao, MultiRefTwoParents domain) {
		domain.setDescription(dao.getDescription());
	}

}
