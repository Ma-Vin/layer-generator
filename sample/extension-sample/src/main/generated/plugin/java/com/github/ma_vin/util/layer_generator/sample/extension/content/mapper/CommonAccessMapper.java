package com.github.ma_vin.util.layer_generator.sample.extension.content.mapper;

import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseAccessMapper;
import com.github.ma_vin.util.layer_generator.sample.extension.content.dao.DaoObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.extension.content.dao.IIdentifiableDao;
import com.github.ma_vin.util.layer_generator.sample.extension.content.dao.ToExtendEntityDao;
import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.DomainObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.ToExtendEntity;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class which provides methods to convert a data access to a domain object of sub package <i>null</i> and the other way around
 */
@BaseAccessMapper
public class CommonAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static CommonAccessMapper instance;

	/**
	 * Converts a(n) {@link ToExtendEntityDao} to a(n) {@link ToExtendEntity}
	 *
	 * @param toExtendEntity the source object which should be converted
	 * @return an equivalent new created {@link ToExtendEntity}
	 */
	public static ToExtendEntity convertToToExtendEntity(ToExtendEntityDao toExtendEntity) {
		return convertToToExtendEntity(toExtendEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link ToExtendEntityDao} to a(n) {@link ToExtendEntity}
	 *
	 * @param toExtendEntity the source object which should be converted
	 * @param mappedObjects  map which contains already mapped objects. If an identification of {@code toExtendEntity} is contained, the found
	 *                       {@link ToExtendEntity} will be returned
	 * @return an equivalent new created {@link ToExtendEntity} or the found one from the given map
	 */
	public static ToExtendEntity convertToToExtendEntity(ToExtendEntityDao toExtendEntity, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(toExtendEntity, mappedObjects, DomainObjectFactory::createToExtendEntity, (dao, domain) -> getInstance().setToExtendEntityValues(dao, domain)
				, (dao, domain) -> getInstance().setToExtendEntitySingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setToExtendEntityMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link ToExtendEntity} to a(n) {@link ToExtendEntityDao}
	 *
	 * @param toExtendEntity the source object which should be converted
	 * @return an equivalent new created {@link ToExtendEntityDao}
	 */
	public static ToExtendEntityDao convertToToExtendEntityDao(ToExtendEntity toExtendEntity) {
		return convertToToExtendEntityDao(toExtendEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link ToExtendEntity} to a(n) {@link ToExtendEntityDao}
	 *
	 * @param toExtendEntity the source object which should be converted
	 * @param mappedObjects  map which contains already mapped objects. If an identification of {@code toExtendEntity} is contained, the found
	 *                       {@link ToExtendEntityDao} will be returned
	 * @return an equivalent new created {@link ToExtendEntityDao} or the found one from the given map
	 */
	public static ToExtendEntityDao convertToToExtendEntityDao(ToExtendEntity toExtendEntity, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(toExtendEntity, mappedObjects, DaoObjectFactory::createToExtendEntityDao, (domain, dao) -> getInstance().setToExtendEntityDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setToExtendEntityDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setToExtendEntityDaoMultiReferences(domain, dao, mappedObjects));
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
	protected void setToExtendEntityDaoMultiReferences(ToExtendEntity domain, ToExtendEntityDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setToExtendEntityDaoSingleReferences(ToExtendEntity domain, ToExtendEntityDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	protected void setToExtendEntityDaoValues(ToExtendEntity domain, ToExtendEntityDao dao) {
		dao.setExistingAttribute(domain.getExistingAttribute());
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setToExtendEntityMultiReferences(ToExtendEntityDao dao, ToExtendEntity domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setToExtendEntitySingleReferences(ToExtendEntityDao dao, ToExtendEntity domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setToExtendEntityValues(ToExtendEntityDao dao, ToExtendEntity domain) {
		domain.setExistingAttribute(dao.getExistingAttribute());
	}

}
