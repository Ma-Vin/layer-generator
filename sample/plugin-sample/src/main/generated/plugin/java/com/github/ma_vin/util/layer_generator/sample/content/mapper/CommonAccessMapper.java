package com.github.ma_vin.util.layer_generator.sample.content.mapper;

import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseAccessMapper;
import com.github.ma_vin.util.layer_generator.sample.content.dao.DaoObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.content.dao.IIdentifiableDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.PluginEntityDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.DomainObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.content.domain.PluginEntity;
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
	 * Converts a(n) {@link PluginEntityDao} to a(n) {@link PluginEntity}
	 *
	 * @param pluginEntity the source object which should be converted
	 * @return an equivalent new created {@link PluginEntity}
	 */
	public static PluginEntity convertToPluginEntity(PluginEntityDao pluginEntity) {
		return convertToPluginEntity(pluginEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link PluginEntityDao} to a(n) {@link PluginEntity}
	 *
	 * @param pluginEntity  the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code pluginEntity} is contained, the found
	 *                      {@link PluginEntity} will be returned
	 * @return an equivalent new created {@link PluginEntity} or the found one from the given map
	 */
	public static PluginEntity convertToPluginEntity(PluginEntityDao pluginEntity, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(pluginEntity, mappedObjects, DomainObjectFactory::createPluginEntity, (dao, domain) -> getInstance().setPluginEntityValues(dao, domain)
				, (dao, domain) -> getInstance().setPluginEntitySingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setPluginEntityMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link PluginEntity} to a(n) {@link PluginEntityDao}
	 *
	 * @param pluginEntity the source object which should be converted
	 * @return an equivalent new created {@link PluginEntityDao}
	 */
	public static PluginEntityDao convertToPluginEntityDao(PluginEntity pluginEntity) {
		return convertToPluginEntityDao(pluginEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link PluginEntity} to a(n) {@link PluginEntityDao}
	 *
	 * @param pluginEntity  the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code pluginEntity} is contained, the found
	 *                      {@link PluginEntityDao} will be returned
	 * @return an equivalent new created {@link PluginEntityDao} or the found one from the given map
	 */
	public static PluginEntityDao convertToPluginEntityDao(PluginEntity pluginEntity, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(pluginEntity, mappedObjects, DaoObjectFactory::createPluginEntityDao, (domain, dao) -> getInstance().setPluginEntityDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setPluginEntityDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setPluginEntityDaoMultiReferences(domain, dao, mappedObjects));
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
	protected void setPluginEntityDaoMultiReferences(PluginEntity domain, PluginEntityDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setPluginEntityDaoSingleReferences(PluginEntity domain, PluginEntityDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	protected void setPluginEntityDaoValues(PluginEntity domain, PluginEntityDao dao) {
		dao.setExampleAttribute(domain.getExampleAttribute());
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setPluginEntityMultiReferences(PluginEntityDao dao, PluginEntity domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setPluginEntitySingleReferences(PluginEntityDao dao, PluginEntity domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setPluginEntityValues(PluginEntityDao dao, PluginEntity domain) {
		domain.setExampleAttribute(dao.getExampleAttribute());
	}

}
