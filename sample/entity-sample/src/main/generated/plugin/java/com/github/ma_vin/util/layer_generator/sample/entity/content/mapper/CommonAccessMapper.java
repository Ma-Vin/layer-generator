package com.github.ma_vin.util.layer_generator.sample.entity.content.mapper;

import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseAccessMapper;
import com.github.ma_vin.util.layer_generator.sample.entity.content.dao.DaoObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.entity.content.dao.ExtendingEntityDao;
import com.github.ma_vin.util.layer_generator.sample.entity.content.dao.IIdentifiableDao;
import com.github.ma_vin.util.layer_generator.sample.entity.content.dao.RootEntityDao;
import com.github.ma_vin.util.layer_generator.sample.entity.content.domain.DomainObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.entity.content.domain.ExtendingEntity;
import com.github.ma_vin.util.layer_generator.sample.entity.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.entity.content.domain.RootEntity;
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
	 * Converts a(n) {@link ExtendingEntityDao} to a(n) {@link ExtendingEntity}
	 *
	 * @param extendingEntity the source object which should be converted
	 * @return an equivalent new created {@link ExtendingEntity}
	 */
	public static ExtendingEntity convertToExtendingEntity(ExtendingEntityDao extendingEntity) {
		return convertToExtendingEntity(extendingEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link ExtendingEntityDao} to a(n) {@link ExtendingEntity}
	 *
	 * @param extendingEntity the source object which should be converted
	 * @param mappedObjects   map which contains already mapped objects. If an identification of {@code extendingEntity} is contained, the found
	 *                        {@link ExtendingEntity} will be returned
	 * @return an equivalent new created {@link ExtendingEntity} or the found one from the given map
	 */
	public static ExtendingEntity convertToExtendingEntity(ExtendingEntityDao extendingEntity, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(extendingEntity, mappedObjects, DomainObjectFactory::createExtendingEntity, (dao, domain) -> getInstance().setExtendingEntityValues(dao, domain)
				, (dao, domain) -> getInstance().setExtendingEntitySingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setExtendingEntityMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link ExtendingEntity} to a(n) {@link ExtendingEntityDao}
	 *
	 * @param extendingEntity the source object which should be converted
	 * @return an equivalent new created {@link ExtendingEntityDao}
	 */
	public static ExtendingEntityDao convertToExtendingEntityDao(ExtendingEntity extendingEntity) {
		return convertToExtendingEntityDao(extendingEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link ExtendingEntity} to a(n) {@link ExtendingEntityDao}
	 *
	 * @param extendingEntity the source object which should be converted
	 * @param mappedObjects   map which contains already mapped objects. If an identification of {@code extendingEntity} is contained, the found
	 *                        {@link ExtendingEntityDao} will be returned
	 * @return an equivalent new created {@link ExtendingEntityDao} or the found one from the given map
	 */
	public static ExtendingEntityDao convertToExtendingEntityDao(ExtendingEntity extendingEntity, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(extendingEntity, mappedObjects, DaoObjectFactory::createExtendingEntityDao, (domain, dao) -> getInstance().setExtendingEntityDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setExtendingEntityDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setExtendingEntityDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * Converts a(n) {@link RootEntityDao} to a(n) {@link RootEntity}
	 *
	 * @param rootEntity the source object which should be converted
	 * @return an equivalent new created {@link RootEntity}
	 */
	public static RootEntity convertToRootEntity(RootEntityDao rootEntity) {
		return convertToRootEntity(rootEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link RootEntityDao} to a(n) {@link RootEntity}
	 *
	 * @param rootEntity    the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code rootEntity} is contained, the found
	 *                      {@link RootEntity} will be returned
	 * @return an equivalent new created {@link RootEntity} or the found one from the given map
	 */
	public static RootEntity convertToRootEntity(RootEntityDao rootEntity, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(rootEntity, mappedObjects, DomainObjectFactory::createRootEntity, (dao, domain) -> getInstance().setRootEntityValues(dao, domain)
				, (dao, domain) -> getInstance().setRootEntitySingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setRootEntityMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link RootEntity} to a(n) {@link RootEntityDao}
	 *
	 * @param rootEntity the source object which should be converted
	 * @return an equivalent new created {@link RootEntityDao}
	 */
	public static RootEntityDao convertToRootEntityDao(RootEntity rootEntity) {
		return convertToRootEntityDao(rootEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link RootEntity} to a(n) {@link RootEntityDao}
	 *
	 * @param rootEntity    the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code rootEntity} is contained, the found
	 *                      {@link RootEntityDao} will be returned
	 * @return an equivalent new created {@link RootEntityDao} or the found one from the given map
	 */
	public static RootEntityDao convertToRootEntityDao(RootEntity rootEntity, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(rootEntity, mappedObjects, DaoObjectFactory::createRootEntityDao, (domain, dao) -> getInstance().setRootEntityDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setRootEntityDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setRootEntityDaoMultiReferences(domain, dao, mappedObjects));
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
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setExtendingEntityDaoMultiReferences(ExtendingEntity domain, ExtendingEntityDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setExtendingEntityDaoSingleReferences(ExtendingEntity domain, ExtendingEntityDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	protected void setExtendingEntityDaoValues(ExtendingEntity domain, ExtendingEntityDao dao) {
		dao.setAddedField(domain.getAddedField());
		dao.setSuperName(domain.getSuperName());
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setExtendingEntityMultiReferences(ExtendingEntityDao dao, ExtendingEntity domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setExtendingEntitySingleReferences(ExtendingEntityDao dao, ExtendingEntity domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setExtendingEntityValues(ExtendingEntityDao dao, ExtendingEntity domain) {
		domain.setAddedField(dao.getAddedField());
		domain.setSuperName(dao.getSuperName());
	}

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setRootEntityDaoMultiReferences(RootEntity domain, RootEntityDao dao, Map<String, IIdentifiableDao> mappedObjects) {
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
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setRootEntityMultiReferences(RootEntityDao dao, RootEntity domain, Map<String, IIdentifiable> mappedObjects) {
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

}
