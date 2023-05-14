package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseAccessMapper;
import de.ma_vin.util.sample.content.dao.DaoObjectFactory;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootEntityDao;
import de.ma_vin.util.sample.content.dao.SubEntityDao;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.RootEntity;
import de.ma_vin.util.sample.content.domain.SubEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class which provides methods to convert a data access to a domain object of sub package <i>null<i> and the other way around
 */
@BaseAccessMapper
public class CommonAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static CommonAccessMapper instance;

	/**
	 * Converts a(n) {@link RootEntityDao} to a(n) {@link RootEntity}
	 *
	 * @param rootEntity      the source object which should be converted
	 * @param includeChildren {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are not
	 *                        of type {@link java.util.Collection}
	 * @return an equivalent new created {@link RootEntity}
	 */
	public static RootEntity convertToRootEntity(RootEntityDao rootEntity, boolean includeChildren) {
		return convertToRootEntity(rootEntity, includeChildren, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link RootEntityDao} to a(n) {@link RootEntity}
	 *
	 * @param rootEntity      the source object which should be converted
	 * @param includeChildren {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are not
	 *                        of type {@link java.util.Collection}
	 * @param mappedObjects   map which contains already mapped objects. If an identification of {@code rootEntity} is contained, the found
	 *                        {@link RootEntity} will be returned
	 * @return an equivalent new created {@link RootEntity} or the found one from the given map
	 */
	public static RootEntity convertToRootEntity(RootEntityDao rootEntity, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(rootEntity, mappedObjects, DomainObjectFactory::createRootEntity, (dao, domain) -> getInstance().setRootEntityValues(dao, domain)
				, (dao, domain) -> getInstance().setRootEntitySingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setRootEntityMultiReferences(dao, domain, includeChildren, mappedObjects));
	}

	/**
	 * Converts a(n) {@link RootEntity} to a(n) {@link RootEntityDao}
	 *
	 * @param rootEntity      the source object which should be converted
	 * @param includeChildren {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are not
	 *                        of type {@link java.util.Collection}
	 * @return an equivalent new created {@link RootEntityDao}
	 */
	public static RootEntityDao convertToRootEntityDao(RootEntity rootEntity, boolean includeChildren) {
		return convertToRootEntityDao(rootEntity, includeChildren, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link RootEntity} to a(n) {@link RootEntityDao}
	 *
	 * @param rootEntity      the source object which should be converted
	 * @param includeChildren {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are not
	 *                        of type {@link java.util.Collection}
	 * @param mappedObjects   map which contains already mapped objects. If an identification of {@code rootEntity} is contained, the found
	 *                        {@link RootEntityDao} will be returned
	 * @return an equivalent new created {@link RootEntityDao} or the found one from the given map
	 */
	public static RootEntityDao convertToRootEntityDao(RootEntity rootEntity, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(rootEntity, mappedObjects, DaoObjectFactory::createRootEntityDao, (domain, dao) -> getInstance().setRootEntityDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setRootEntityDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setRootEntityDaoMultiReferences(domain, dao, includeChildren, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SubEntityDao} to a(n) {@link SubEntity}
	 *
	 * @param subEntity the source object which should be converted
	 * @return an equivalent new created {@link SubEntity}
	 */
	public static SubEntity convertToSubEntity(SubEntityDao subEntity) {
		return convertToSubEntity(subEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SubEntityDao} to a(n) {@link SubEntity}
	 *
	 * @param subEntity     the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code subEntity} is contained, the found {@link SubEntity}
	 *                      will be returned
	 * @return an equivalent new created {@link SubEntity} or the found one from the given map
	 */
	public static SubEntity convertToSubEntity(SubEntityDao subEntity, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(subEntity, mappedObjects, DomainObjectFactory::createSubEntity, (dao, domain) -> getInstance().setSubEntityValues(dao, domain)
				, (dao, domain) -> getInstance().setSubEntitySingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setSubEntityMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SubEntityDao} to a(n) {@link SubEntity} and sets the result to the corresponding reference property at the parent
	 *
	 * @param subEntity the source object which should be converted
	 * @param parent    the parent of converted result
	 * @return an equivalent new created {@link SubEntity}
	 */
	public static SubEntity convertToSubEntity(SubEntityDao subEntity, RootEntity parent) {
		return convertToSubEntity(subEntity, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SubEntityDao} to a(n) {@link SubEntity} and sets the result to the corresponding reference property at the parent
	 *
	 * @param subEntity     the source object which should be converted
	 * @param parent        the parent of converted result
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code subEntity} is contained, the found {@link SubEntity}
	 *                      will be returned
	 * @return an equivalent new created {@link SubEntity} or the found one from the given map
	 */
	public static SubEntity convertToSubEntity(SubEntityDao subEntity, RootEntity parent, Map<String, IIdentifiable> mappedObjects) {
		SubEntity result = convertToSubEntity(subEntity, mappedObjects);
		if (result != null) {
			parent.getSubEntities().add(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SubEntity} to a(n) {@link SubEntityDao}
	 *
	 * @param subEntity the source object which should be converted
	 * @return an equivalent new created {@link SubEntityDao}
	 */
	public static SubEntityDao convertToSubEntityDao(SubEntity subEntity) {
		return convertToSubEntityDao(subEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SubEntity} to a(n) {@link SubEntityDao}
	 *
	 * @param subEntity     the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code subEntity} is contained, the found
	 *                      {@link SubEntityDao} will be returned
	 * @return an equivalent new created {@link SubEntityDao} or the found one from the given map
	 */
	public static SubEntityDao convertToSubEntityDao(SubEntity subEntity, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(subEntity, mappedObjects, DaoObjectFactory::createSubEntityDao, (domain, dao) -> getInstance().setSubEntityDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setSubEntityDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setSubEntityDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SubEntity} to a(n) {@link SubEntityDao} and sets the result to the corresponding reference property at the parent
	 *
	 * @param subEntity the source object which should be converted
	 * @param parent    the parent of converted result
	 * @return an equivalent new created {@link SubEntityDao}
	 */
	public static SubEntityDao convertToSubEntityDao(SubEntity subEntity, RootEntityDao parent) {
		return convertToSubEntityDao(subEntity, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SubEntity} to a(n) {@link SubEntityDao} and sets the result to the corresponding reference property at the parent
	 *
	 * @param subEntity     the source object which should be converted
	 * @param parent        the parent of converted result
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code subEntity} is contained, the found
	 *                      {@link SubEntityDao} will be returned
	 * @return an equivalent new created {@link SubEntityDao} or the found one from the given map
	 */
	public static SubEntityDao convertToSubEntityDao(SubEntity subEntity, RootEntityDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		SubEntityDao result = convertToSubEntityDao(subEntity, mappedObjects);
		if (result != null) {
			result.setParentRootEntity(parent);
			parent.getSubEntities().add(result);
		}
		return result;
	}

	/**
	 * @return the singleton
	 */
	public static CommonAccessMapper getInstance() {
		if (instance == null) {
			instance = AccessMapperFactory.createCommonAccessMapper();
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
	protected void setRootEntityDaoMultiReferences(RootEntity domain, RootEntityDao dao, boolean includeChildren
			, Map<String, IIdentifiableDao> mappedObjects) {
		dao.setSubEntities(new ArrayList<>());
		if (includeChildren) {
			domain.getSubEntities().forEach(arg ->
					CommonAccessMapper.convertToSubEntityDao(arg, dao, mappedObjects)
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
	protected void setRootEntityDaoSingleReferences(RootEntity domain, RootEntityDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	protected void setRootEntityDaoValues(RootEntity domain, RootEntityDao dao) {
		dao.setRootName(domain.getRootName());
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
	protected void setRootEntityMultiReferences(RootEntityDao dao, RootEntity domain, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		if (includeChildren) {
			dao.getSubEntities().forEach(arg ->
					CommonAccessMapper.convertToSubEntity(arg, domain, mappedObjects)
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
	protected void setRootEntitySingleReferences(RootEntityDao dao, RootEntity domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setRootEntityValues(RootEntityDao dao, RootEntity domain) {
		domain.setRootName(dao.getRootName());
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
	protected void setSubEntityDaoMultiReferences(SubEntity domain, SubEntityDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSubEntityDaoSingleReferences(SubEntity domain, SubEntityDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	protected void setSubEntityDaoValues(SubEntity domain, SubEntityDao dao) {
		dao.setSubName(domain.getSubName());
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
	protected void setSubEntityMultiReferences(SubEntityDao dao, SubEntity domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSubEntitySingleReferences(SubEntityDao dao, SubEntity domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setSubEntityValues(SubEntityDao dao, SubEntity domain) {
		domain.setSubName(dao.getSubName());
		domain.setDescription(dao.getDescription());
	}

}
