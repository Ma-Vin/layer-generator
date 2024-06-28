package com.github.ma_vin.util.layer_generator.sample.content.mapper;

import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseAccessMapper;
import com.github.ma_vin.util.layer_generator.sample.content.dao.DaoObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.content.dao.IIdentifiableDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.RootDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.single.indirect.SingleRefIndirectParentDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.single.indirect.SingleRefOtherIndirectParentDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.DomainObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.content.domain.Root;
import com.github.ma_vin.util.layer_generator.sample.content.domain.single.indirect.SingleRefIndirectParent;
import com.github.ma_vin.util.layer_generator.sample.content.domain.single.indirect.SingleRefOtherIndirectParent;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class which provides methods to convert a data access to a domain object of sub package <i>single.indirect<i> and the other way around
 */
@BaseAccessMapper
public class SingleIndirectAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static SingleIndirectAccessMapper instance;

	/**
	 * Converts a(n) {@link SingleRefIndirectParentDao} to a(n) {@link SingleRefIndirectParent}
	 *
	 * @param singleRefIndirectParent the source object which should be converted
	 * @return an equivalent new created {@link SingleRefIndirectParent}
	 */
	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDao singleRefIndirectParent) {
		return convertToSingleRefIndirectParent(singleRefIndirectParent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefIndirectParentDao} to a(n) {@link SingleRefIndirectParent}
	 *
	 * @param singleRefIndirectParent the source object which should be converted
	 * @param mappedObjects           map which contains already mapped objects. If an identification of {@code singleRefIndirectParent} is contained, the
	 *                                found {@link SingleRefIndirectParent} will be returned
	 * @return an equivalent new created {@link SingleRefIndirectParent} or the found one from the given map
	 */
	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDao singleRefIndirectParent
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(singleRefIndirectParent, mappedObjects, DomainObjectFactory::createSingleRefIndirectParent, (dao, domain) -> getInstance().setSingleRefIndirectParentValues(dao, domain)
				, (dao, domain) -> getInstance().setSingleRefIndirectParentSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setSingleRefIndirectParentMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SingleRefIndirectParentDao} to a(n) {@link SingleRefIndirectParent} and sets the result to the corresponding reference
	 * property at the parent
	 *
	 * @param singleRefIndirectParent the source object which should be converted
	 * @param parent                  the parent of converted result
	 * @return an equivalent new created {@link SingleRefIndirectParent}
	 */
	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDao singleRefIndirectParent, Root parent) {
		return convertToSingleRefIndirectParent(singleRefIndirectParent, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefIndirectParentDao} to a(n) {@link SingleRefIndirectParent} and sets the result to the corresponding reference
	 * property at the parent
	 *
	 * @param singleRefIndirectParent the source object which should be converted
	 * @param parent                  the parent of converted result
	 * @param mappedObjects           map which contains already mapped objects. If an identification of {@code singleRefIndirectParent} is contained, the
	 *                                found {@link SingleRefIndirectParent} will be returned
	 * @return an equivalent new created {@link SingleRefIndirectParent} or the found one from the given map
	 */
	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDao singleRefIndirectParent, Root parent
			, Map<String, IIdentifiable> mappedObjects) {
		SingleRefIndirectParent result = convertToSingleRefIndirectParent(singleRefIndirectParent, mappedObjects);
		if (result != null) {
			parent.setSingleRefIndirectParent(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefIndirectParentDao} to a(n) {@link SingleRefIndirectParent} and sets the result to the corresponding reference
	 * property at the parent
	 *
	 * @param singleRefIndirectParent the source object which should be converted
	 * @param parent                  the parent of converted result
	 * @return an equivalent new created {@link SingleRefIndirectParent}
	 */
	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDao singleRefIndirectParent
			, SingleRefOtherIndirectParent parent) {
		return convertToSingleRefIndirectParent(singleRefIndirectParent, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefIndirectParentDao} to a(n) {@link SingleRefIndirectParent} and sets the result to the corresponding reference
	 * property at the parent
	 *
	 * @param singleRefIndirectParent the source object which should be converted
	 * @param parent                  the parent of converted result
	 * @param mappedObjects           map which contains already mapped objects. If an identification of {@code singleRefIndirectParent} is contained, the
	 *                                found {@link SingleRefIndirectParent} will be returned
	 * @return an equivalent new created {@link SingleRefIndirectParent} or the found one from the given map
	 */
	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDao singleRefIndirectParent
			, SingleRefOtherIndirectParent parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefIndirectParent result = convertToSingleRefIndirectParent(singleRefIndirectParent, mappedObjects);
		if (result != null) {
			parent.setSingleIndirectRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefIndirectParent} to a(n) {@link SingleRefIndirectParentDao}
	 *
	 * @param singleRefIndirectParent the source object which should be converted
	 * @return an equivalent new created {@link SingleRefIndirectParentDao}
	 */
	public static SingleRefIndirectParentDao convertToSingleRefIndirectParentDao(SingleRefIndirectParent singleRefIndirectParent) {
		return convertToSingleRefIndirectParentDao(singleRefIndirectParent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefIndirectParent} to a(n) {@link SingleRefIndirectParentDao}
	 *
	 * @param singleRefIndirectParent the source object which should be converted
	 * @param mappedObjects           map which contains already mapped objects. If an identification of {@code singleRefIndirectParent} is contained, the
	 *                                found {@link SingleRefIndirectParentDao} will be returned
	 * @return an equivalent new created {@link SingleRefIndirectParentDao} or the found one from the given map
	 */
	public static SingleRefIndirectParentDao convertToSingleRefIndirectParentDao(SingleRefIndirectParent singleRefIndirectParent
			, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(singleRefIndirectParent, mappedObjects, DaoObjectFactory::createSingleRefIndirectParentDao, (domain, dao) -> getInstance().setSingleRefIndirectParentDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setSingleRefIndirectParentDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setSingleRefIndirectParentDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SingleRefIndirectParent} to a(n) {@link SingleRefIndirectParentDao} and sets the result to the corresponding reference
	 * property at the parent
	 *
	 * @param singleRefIndirectParent the source object which should be converted
	 * @param parent                  the parent of converted result
	 * @return an equivalent new created {@link SingleRefIndirectParentDao}
	 */
	public static SingleRefIndirectParentDao convertToSingleRefIndirectParentDao(SingleRefIndirectParent singleRefIndirectParent, RootDao parent) {
		return convertToSingleRefIndirectParentDao(singleRefIndirectParent, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefIndirectParent} to a(n) {@link SingleRefIndirectParentDao} and sets the result to the corresponding reference
	 * property at the parent
	 *
	 * @param singleRefIndirectParent the source object which should be converted
	 * @param parent                  the parent of converted result
	 * @param mappedObjects           map which contains already mapped objects. If an identification of {@code singleRefIndirectParent} is contained, the
	 *                                found {@link SingleRefIndirectParentDao} will be returned
	 * @return an equivalent new created {@link SingleRefIndirectParentDao} or the found one from the given map
	 */
	public static SingleRefIndirectParentDao convertToSingleRefIndirectParentDao(SingleRefIndirectParent singleRefIndirectParent, RootDao parent
			, Map<String, IIdentifiableDao> mappedObjects) {
		SingleRefIndirectParentDao result = convertToSingleRefIndirectParentDao(singleRefIndirectParent, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.setSingleRefIndirectParent(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefIndirectParent} to a(n) {@link SingleRefIndirectParentDao} and sets the result to the corresponding reference
	 * property at the parent
	 *
	 * @param singleRefIndirectParent the source object which should be converted
	 * @param parent                  the parent of converted result
	 * @return an equivalent new created {@link SingleRefIndirectParentDao}
	 */
	public static SingleRefIndirectParentDao convertToSingleRefIndirectParentDao(SingleRefIndirectParent singleRefIndirectParent
			, SingleRefOtherIndirectParentDao parent) {
		return convertToSingleRefIndirectParentDao(singleRefIndirectParent, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefIndirectParent} to a(n) {@link SingleRefIndirectParentDao} and sets the result to the corresponding reference
	 * property at the parent
	 *
	 * @param singleRefIndirectParent the source object which should be converted
	 * @param parent                  the parent of converted result
	 * @param mappedObjects           map which contains already mapped objects. If an identification of {@code singleRefIndirectParent} is contained, the
	 *                                found {@link SingleRefIndirectParentDao} will be returned
	 * @return an equivalent new created {@link SingleRefIndirectParentDao} or the found one from the given map
	 */
	public static SingleRefIndirectParentDao convertToSingleRefIndirectParentDao(SingleRefIndirectParent singleRefIndirectParent
			, SingleRefOtherIndirectParentDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		SingleRefIndirectParentDao result = convertToSingleRefIndirectParentDao(singleRefIndirectParent, mappedObjects);
		if (result != null) {
			parent.setSingleIndirectRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefOtherIndirectParentDao} to a(n) {@link SingleRefOtherIndirectParent}
	 *
	 * @param singleRefOtherIndirectParent the source object which should be converted
	 * @return an equivalent new created {@link SingleRefOtherIndirectParent}
	 */
	public static SingleRefOtherIndirectParent convertToSingleRefOtherIndirectParent(SingleRefOtherIndirectParentDao singleRefOtherIndirectParent) {
		return convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefOtherIndirectParentDao} to a(n) {@link SingleRefOtherIndirectParent}
	 *
	 * @param singleRefOtherIndirectParent the source object which should be converted
	 * @param mappedObjects                map which contains already mapped objects. If an identification of {@code singleRefOtherIndirectParent} is
	 *                                     contained, the found {@link SingleRefOtherIndirectParent} will be returned
	 * @return an equivalent new created {@link SingleRefOtherIndirectParent} or the found one from the given map
	 */
	public static SingleRefOtherIndirectParent convertToSingleRefOtherIndirectParent(SingleRefOtherIndirectParentDao singleRefOtherIndirectParent
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(singleRefOtherIndirectParent, mappedObjects, DomainObjectFactory::createSingleRefOtherIndirectParent, (dao, domain) -> getInstance().setSingleRefOtherIndirectParentValues(dao, domain)
				, (dao, domain) -> getInstance().setSingleRefOtherIndirectParentSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setSingleRefOtherIndirectParentMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SingleRefOtherIndirectParentDao} to a(n) {@link SingleRefOtherIndirectParent} and sets the result to the corresponding
	 * reference property at the parent
	 *
	 * @param singleRefOtherIndirectParent the source object which should be converted
	 * @param parent                       the parent of converted result
	 * @return an equivalent new created {@link SingleRefOtherIndirectParent}
	 */
	public static SingleRefOtherIndirectParent convertToSingleRefOtherIndirectParent(SingleRefOtherIndirectParentDao singleRefOtherIndirectParent
			, Root parent) {
		return convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParent, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefOtherIndirectParentDao} to a(n) {@link SingleRefOtherIndirectParent} and sets the result to the corresponding
	 * reference property at the parent
	 *
	 * @param singleRefOtherIndirectParent the source object which should be converted
	 * @param parent                       the parent of converted result
	 * @param mappedObjects                map which contains already mapped objects. If an identification of {@code singleRefOtherIndirectParent} is
	 *                                     contained, the found {@link SingleRefOtherIndirectParent} will be returned
	 * @return an equivalent new created {@link SingleRefOtherIndirectParent} or the found one from the given map
	 */
	public static SingleRefOtherIndirectParent convertToSingleRefOtherIndirectParent(SingleRefOtherIndirectParentDao singleRefOtherIndirectParent
			, Root parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefOtherIndirectParent result = convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParent, mappedObjects);
		if (result != null) {
			parent.setSingleRefIndirectOtherParent(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefOtherIndirectParent} to a(n) {@link SingleRefOtherIndirectParentDao}
	 *
	 * @param singleRefOtherIndirectParent the source object which should be converted
	 * @return an equivalent new created {@link SingleRefOtherIndirectParentDao}
	 */
	public static SingleRefOtherIndirectParentDao convertToSingleRefOtherIndirectParentDao(SingleRefOtherIndirectParent singleRefOtherIndirectParent) {
		return convertToSingleRefOtherIndirectParentDao(singleRefOtherIndirectParent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefOtherIndirectParent} to a(n) {@link SingleRefOtherIndirectParentDao}
	 *
	 * @param singleRefOtherIndirectParent the source object which should be converted
	 * @param mappedObjects                map which contains already mapped objects. If an identification of {@code singleRefOtherIndirectParent} is
	 *                                     contained, the found {@link SingleRefOtherIndirectParentDao} will be returned
	 * @return an equivalent new created {@link SingleRefOtherIndirectParentDao} or the found one from the given map
	 */
	public static SingleRefOtherIndirectParentDao convertToSingleRefOtherIndirectParentDao(SingleRefOtherIndirectParent singleRefOtherIndirectParent
			, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(singleRefOtherIndirectParent, mappedObjects, DaoObjectFactory::createSingleRefOtherIndirectParentDao, (domain, dao) -> getInstance().setSingleRefOtherIndirectParentDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setSingleRefOtherIndirectParentDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setSingleRefOtherIndirectParentDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SingleRefOtherIndirectParent} to a(n) {@link SingleRefOtherIndirectParentDao} and sets the result to the corresponding
	 * reference property at the parent
	 *
	 * @param singleRefOtherIndirectParent the source object which should be converted
	 * @param parent                       the parent of converted result
	 * @return an equivalent new created {@link SingleRefOtherIndirectParentDao}
	 */
	public static SingleRefOtherIndirectParentDao convertToSingleRefOtherIndirectParentDao(SingleRefOtherIndirectParent singleRefOtherIndirectParent
			, RootDao parent) {
		return convertToSingleRefOtherIndirectParentDao(singleRefOtherIndirectParent, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefOtherIndirectParent} to a(n) {@link SingleRefOtherIndirectParentDao} and sets the result to the corresponding
	 * reference property at the parent
	 *
	 * @param singleRefOtherIndirectParent the source object which should be converted
	 * @param parent                       the parent of converted result
	 * @param mappedObjects                map which contains already mapped objects. If an identification of {@code singleRefOtherIndirectParent} is
	 *                                     contained, the found {@link SingleRefOtherIndirectParentDao} will be returned
	 * @return an equivalent new created {@link SingleRefOtherIndirectParentDao} or the found one from the given map
	 */
	public static SingleRefOtherIndirectParentDao convertToSingleRefOtherIndirectParentDao(SingleRefOtherIndirectParent singleRefOtherIndirectParent
			, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
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

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSingleRefIndirectParentDaoMultiReferences(SingleRefIndirectParent domain, SingleRefIndirectParentDao dao
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
	protected void setSingleRefIndirectParentDaoSingleReferences(SingleRefIndirectParent domain, SingleRefIndirectParentDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	protected void setSingleRefIndirectParentDaoValues(SingleRefIndirectParent domain, SingleRefIndirectParentDao dao) {
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
	protected void setSingleRefIndirectParentMultiReferences(SingleRefIndirectParentDao dao, SingleRefIndirectParent domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSingleRefIndirectParentSingleReferences(SingleRefIndirectParentDao dao, SingleRefIndirectParent domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setSingleRefIndirectParentValues(SingleRefIndirectParentDao dao, SingleRefIndirectParent domain) {
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
	protected void setSingleRefOtherIndirectParentDaoMultiReferences(SingleRefOtherIndirectParent domain, SingleRefOtherIndirectParentDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	protected void setSingleRefOtherIndirectParentDaoSingleReferences(SingleRefOtherIndirectParent domain, SingleRefOtherIndirectParentDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
		dao.setSingleIndirectRef(SingleIndirectAccessMapper.convertToSingleRefIndirectParentDao(domain.getSingleIndirectRef(), mappedObjects));
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	protected void setSingleRefOtherIndirectParentDaoValues(SingleRefOtherIndirectParent domain, SingleRefOtherIndirectParentDao dao) {
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
	protected void setSingleRefOtherIndirectParentMultiReferences(SingleRefOtherIndirectParentDao dao, SingleRefOtherIndirectParent domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	protected void setSingleRefOtherIndirectParentSingleReferences(SingleRefOtherIndirectParentDao dao, SingleRefOtherIndirectParent domain
			, Map<String, IIdentifiable> mappedObjects) {
		domain.setSingleIndirectRef(SingleIndirectAccessMapper.convertToSingleRefIndirectParent(dao.getSingleIndirectRef(), mappedObjects));
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setSingleRefOtherIndirectParentValues(SingleRefOtherIndirectParentDao dao, SingleRefOtherIndirectParent domain) {
		domain.setDescription(dao.getDescription());
	}

}
