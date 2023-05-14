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

/**
 * Generated class which provides methods to convert a data access to a domain object of sub package <i>single<i> and the other way around
 */
@BaseAccessMapper
public class SingleAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static SingleAccessMapper instance;

	/**
	 * Converts a(n) {@link SingleRefOneParentDao} to a(n) {@link SingleRefOneParent}
	 *
	 * @param singleRefOneParent the source object which should be converted
	 * @return an equivalent new created {@link SingleRefOneParent}
	 */
	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDao singleRefOneParent) {
		return convertToSingleRefOneParent(singleRefOneParent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefOneParentDao} to a(n) {@link SingleRefOneParent}
	 *
	 * @param singleRefOneParent the source object which should be converted
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code singleRefOneParent} is contained, the found
	 *                           {@link SingleRefOneParent} will be returned
	 * @return an equivalent new created {@link SingleRefOneParent} or the found one from the given map
	 */
	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDao singleRefOneParent, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(singleRefOneParent, mappedObjects, DomainObjectFactory::createSingleRefOneParent, (dao, domain) -> getInstance().setSingleRefOneParentValues(dao, domain)
				, (dao, domain) -> getInstance().setSingleRefOneParentSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setSingleRefOneParentMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SingleRefOneParentDao} to a(n) {@link SingleRefOneParent} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefOneParent the source object which should be converted
	 * @param parent             the parent of converted result
	 * @return an equivalent new created {@link SingleRefOneParent}
	 */
	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDao singleRefOneParent, Root parent) {
		return convertToSingleRefOneParent(singleRefOneParent, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefOneParentDao} to a(n) {@link SingleRefOneParent} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefOneParent the source object which should be converted
	 * @param parent             the parent of converted result
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code singleRefOneParent} is contained, the found
	 *                           {@link SingleRefOneParent} will be returned
	 * @return an equivalent new created {@link SingleRefOneParent} or the found one from the given map
	 */
	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDao singleRefOneParent, Root parent
			, Map<String, IIdentifiable> mappedObjects) {
		SingleRefOneParent result = convertToSingleRefOneParent(singleRefOneParent, mappedObjects);
		if (result != null) {
			parent.setSingleRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefOneParent} to a(n) {@link SingleRefOneParentDao}
	 *
	 * @param singleRefOneParent the source object which should be converted
	 * @return an equivalent new created {@link SingleRefOneParentDao}
	 */
	public static SingleRefOneParentDao convertToSingleRefOneParentDao(SingleRefOneParent singleRefOneParent) {
		return convertToSingleRefOneParentDao(singleRefOneParent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefOneParent} to a(n) {@link SingleRefOneParentDao}
	 *
	 * @param singleRefOneParent the source object which should be converted
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code singleRefOneParent} is contained, the found
	 *                           {@link SingleRefOneParentDao} will be returned
	 * @return an equivalent new created {@link SingleRefOneParentDao} or the found one from the given map
	 */
	public static SingleRefOneParentDao convertToSingleRefOneParentDao(SingleRefOneParent singleRefOneParent, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(singleRefOneParent, mappedObjects, DaoObjectFactory::createSingleRefOneParentDao, (domain, dao) -> getInstance().setSingleRefOneParentDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setSingleRefOneParentDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setSingleRefOneParentDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SingleRefOneParent} to a(n) {@link SingleRefOneParentDao} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefOneParent the source object which should be converted
	 * @param parent             the parent of converted result
	 * @return an equivalent new created {@link SingleRefOneParentDao}
	 */
	public static SingleRefOneParentDao convertToSingleRefOneParentDao(SingleRefOneParent singleRefOneParent, RootDao parent) {
		return convertToSingleRefOneParentDao(singleRefOneParent, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefOneParent} to a(n) {@link SingleRefOneParentDao} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefOneParent the source object which should be converted
	 * @param parent             the parent of converted result
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code singleRefOneParent} is contained, the found
	 *                           {@link SingleRefOneParentDao} will be returned
	 * @return an equivalent new created {@link SingleRefOneParentDao} or the found one from the given map
	 */
	public static SingleRefOneParentDao convertToSingleRefOneParentDao(SingleRefOneParent singleRefOneParent, RootDao parent
			, Map<String, IIdentifiableDao> mappedObjects) {
		SingleRefOneParentDao result = convertToSingleRefOneParentDao(singleRefOneParent, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.setSingleRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParentsDao} to a(n) {@link SingleRefTwoParents}
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @return an equivalent new created {@link SingleRefTwoParents}
	 */
	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDao singleRefTwoParents) {
		return convertToSingleRefTwoParents(singleRefTwoParents, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParentsDao} to a(n) {@link SingleRefTwoParents}
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param mappedObjects       map which contains already mapped objects. If an identification of {@code singleRefTwoParents} is contained, the found
	 *                            {@link SingleRefTwoParents} will be returned
	 * @return an equivalent new created {@link SingleRefTwoParents} or the found one from the given map
	 */
	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDao singleRefTwoParents, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(singleRefTwoParents, mappedObjects, DomainObjectFactory::createSingleRefTwoParents, (dao, domain) -> getInstance().setSingleRefTwoParentsValues(dao, domain)
				, (dao, domain) -> getInstance().setSingleRefTwoParentsSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setSingleRefTwoParentsMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParentsDao} to a(n) {@link SingleRefTwoParents} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param parent              the parent of converted result
	 * @return an equivalent new created {@link SingleRefTwoParents}
	 */
	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDao singleRefTwoParents, Root parent) {
		return convertToSingleRefTwoParents(singleRefTwoParents, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParentsDao} to a(n) {@link SingleRefTwoParents} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param parent              the parent of converted result
	 * @param mappedObjects       map which contains already mapped objects. If an identification of {@code singleRefTwoParents} is contained, the found
	 *                            {@link SingleRefTwoParents} will be returned
	 * @return an equivalent new created {@link SingleRefTwoParents} or the found one from the given map
	 */
	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDao singleRefTwoParents, Root parent
			, Map<String, IIdentifiable> mappedObjects) {
		SingleRefTwoParents result = convertToSingleRefTwoParents(singleRefTwoParents, mappedObjects);
		if (result != null) {
			parent.setAnotherSingleRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParentsDao} to a(n) {@link SingleRefTwoParents} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param parent              the parent of converted result
	 * @return an equivalent new created {@link SingleRefTwoParents}
	 */
	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDao singleRefTwoParents, SingleRefOneParent parent) {
		return convertToSingleRefTwoParents(singleRefTwoParents, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParentsDao} to a(n) {@link SingleRefTwoParents} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param parent              the parent of converted result
	 * @param mappedObjects       map which contains already mapped objects. If an identification of {@code singleRefTwoParents} is contained, the found
	 *                            {@link SingleRefTwoParents} will be returned
	 * @return an equivalent new created {@link SingleRefTwoParents} or the found one from the given map
	 */
	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDao singleRefTwoParents, SingleRefOneParent parent
			, Map<String, IIdentifiable> mappedObjects) {
		SingleRefTwoParents result = convertToSingleRefTwoParents(singleRefTwoParents, mappedObjects);
		if (result != null) {
			parent.setSingleRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParents} to a(n) {@link SingleRefTwoParentsDao}
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @return an equivalent new created {@link SingleRefTwoParentsDao}
	 */
	public static SingleRefTwoParentsDao convertToSingleRefTwoParentsDao(SingleRefTwoParents singleRefTwoParents) {
		return convertToSingleRefTwoParentsDao(singleRefTwoParents, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParents} to a(n) {@link SingleRefTwoParentsDao}
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param mappedObjects       map which contains already mapped objects. If an identification of {@code singleRefTwoParents} is contained, the found
	 *                            {@link SingleRefTwoParentsDao} will be returned
	 * @return an equivalent new created {@link SingleRefTwoParentsDao} or the found one from the given map
	 */
	public static SingleRefTwoParentsDao convertToSingleRefTwoParentsDao(SingleRefTwoParents singleRefTwoParents
			, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(singleRefTwoParents, mappedObjects, DaoObjectFactory::createSingleRefTwoParentsDao, (domain, dao) -> getInstance().setSingleRefTwoParentsDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setSingleRefTwoParentsDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setSingleRefTwoParentsDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParents} to a(n) {@link SingleRefTwoParentsDao} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param parent              the parent of converted result
	 * @return an equivalent new created {@link SingleRefTwoParentsDao}
	 */
	public static SingleRefTwoParentsDao convertToSingleRefTwoParentsDao(SingleRefTwoParents singleRefTwoParents, RootDao parent) {
		return convertToSingleRefTwoParentsDao(singleRefTwoParents, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParents} to a(n) {@link SingleRefTwoParentsDao} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param parent              the parent of converted result
	 * @param mappedObjects       map which contains already mapped objects. If an identification of {@code singleRefTwoParents} is contained, the found
	 *                            {@link SingleRefTwoParentsDao} will be returned
	 * @return an equivalent new created {@link SingleRefTwoParentsDao} or the found one from the given map
	 */
	public static SingleRefTwoParentsDao convertToSingleRefTwoParentsDao(SingleRefTwoParents singleRefTwoParents, RootDao parent
			, Map<String, IIdentifiableDao> mappedObjects) {
		SingleRefTwoParentsDao result = convertToSingleRefTwoParentsDao(singleRefTwoParents, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.setAnotherSingleRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParents} to a(n) {@link SingleRefTwoParentsDao} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param parent              the parent of converted result
	 * @return an equivalent new created {@link SingleRefTwoParentsDao}
	 */
	public static SingleRefTwoParentsDao convertToSingleRefTwoParentsDao(SingleRefTwoParents singleRefTwoParents, SingleRefOneParentDao parent) {
		return convertToSingleRefTwoParentsDao(singleRefTwoParents, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParents} to a(n) {@link SingleRefTwoParentsDao} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param parent              the parent of converted result
	 * @param mappedObjects       map which contains already mapped objects. If an identification of {@code singleRefTwoParents} is contained, the found
	 *                            {@link SingleRefTwoParentsDao} will be returned
	 * @return an equivalent new created {@link SingleRefTwoParentsDao} or the found one from the given map
	 */
	public static SingleRefTwoParentsDao convertToSingleRefTwoParentsDao(SingleRefTwoParents singleRefTwoParents, SingleRefOneParentDao parent
			, Map<String, IIdentifiableDao> mappedObjects) {
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

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSingleRefOneParentDaoMultiReferences(SingleRefOneParent domain, SingleRefOneParentDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	protected void setSingleRefOneParentDaoSingleReferences(SingleRefOneParent domain, SingleRefOneParentDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
		SingleAccessMapper.convertToSingleRefTwoParentsDao(domain.getSingleRef(), dao, mappedObjects);
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	protected void setSingleRefOneParentDaoValues(SingleRefOneParent domain, SingleRefOneParentDao dao) {
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
	protected void setSingleRefOneParentMultiReferences(SingleRefOneParentDao dao, SingleRefOneParent domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	protected void setSingleRefOneParentSingleReferences(SingleRefOneParentDao dao, SingleRefOneParent domain, Map<String, IIdentifiable> mappedObjects) {
		SingleAccessMapper.convertToSingleRefTwoParents(dao.getSingleRef(), domain, mappedObjects);
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setSingleRefOneParentValues(SingleRefOneParentDao dao, SingleRefOneParent domain) {
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
	protected void setSingleRefTwoParentsDaoMultiReferences(SingleRefTwoParents domain, SingleRefTwoParentsDao dao
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
	protected void setSingleRefTwoParentsDaoSingleReferences(SingleRefTwoParents domain, SingleRefTwoParentsDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	protected void setSingleRefTwoParentsDaoValues(SingleRefTwoParents domain, SingleRefTwoParentsDao dao) {
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
	protected void setSingleRefTwoParentsMultiReferences(SingleRefTwoParentsDao dao, SingleRefTwoParents domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSingleRefTwoParentsSingleReferences(SingleRefTwoParentsDao dao, SingleRefTwoParents domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setSingleRefTwoParentsValues(SingleRefTwoParentsDao dao, SingleRefTwoParents domain) {
		domain.setDescription(dao.getDescription());
	}

}
