package com.github.ma_vin.util.layer_generator.sample.content.mapper;

import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseTransportMapper;
import com.github.ma_vin.util.layer_generator.sample.content.domain.DomainObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.content.domain.PluginEntity;
import com.github.ma_vin.util.layer_generator.sample.content.dto.DtoObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.content.dto.ITransportable;
import com.github.ma_vin.util.layer_generator.sample.content.dto.PluginEntityDto;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class which provides methods to convert a data transport to a domain object of sub package <i>null</i> and the other way around
 */
@BaseTransportMapper
public class CommonTransportMapper extends AbstractTransportMapper {

	/**
	 * singleton
	 */
	private static CommonTransportMapper instance;

	/**
	 * Converts a(n) {@link PluginEntityDto} to a(n) {@link PluginEntity}
	 *
	 * @param pluginEntity the source object which should be converted
	 * @return an equivalent new created {@link PluginEntity}
	 */
	public static PluginEntity convertToPluginEntity(PluginEntityDto pluginEntity) {
		return convertToPluginEntity(pluginEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link PluginEntityDto} to a(n) {@link PluginEntity}
	 *
	 * @param pluginEntity  the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code pluginEntity} is contained, the found
	 *                      {@link PluginEntity} will be returned
	 * @return an equivalent new created {@link PluginEntity} or the found one from the given map
	 */
	public static PluginEntity convertToPluginEntity(PluginEntityDto pluginEntity, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(pluginEntity, mappedObjects, DomainObjectFactory::createPluginEntity, (dto, domain) -> getInstance().setPluginEntityValues(dto, domain)
				, (dto, domain) -> getInstance().setPluginEntitySingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link PluginEntity} to a(n) {@link PluginEntityDto}
	 *
	 * @param pluginEntity the source object which should be converted
	 * @return an equivalent new created {@link PluginEntityDto}
	 */
	public static PluginEntityDto convertToPluginEntityDto(PluginEntity pluginEntity) {
		return convertToPluginEntityDto(pluginEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link PluginEntity} to a(n) {@link PluginEntityDto}
	 *
	 * @param pluginEntity  the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code pluginEntity} is contained, the found
	 *                      {@link PluginEntityDto} will be returned
	 * @return an equivalent new created {@link PluginEntityDto} or the found one from the given map
	 */
	public static PluginEntityDto convertToPluginEntityDto(PluginEntity pluginEntity, Map<String, ITransportable> mappedObjects) {
		return convertToDto(pluginEntity, mappedObjects, DtoObjectFactory::createPluginEntityDto, (domain, dto) -> getInstance().setPluginEntityDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setPluginEntityDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * @return the singleton
	 */
	public static CommonTransportMapper getInstance() {
		if (instance == null) {
			instance = TransportMapperFactory.createCommonTransportMapper();
		}
		return instance;
	}

	/**
	 * Adds the references at {@code dto} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dto           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dto}
	 */
	@SuppressWarnings("java:S1186")
	protected void setPluginEntityDtoSingleReferences(PluginEntity domain, PluginEntityDto dto, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setPluginEntityDtoValues(PluginEntity domain, PluginEntityDto dto) {
		dto.setExampleAttribute(domain.getExampleAttribute());
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setPluginEntitySingleReferences(PluginEntityDto dto, PluginEntity domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setPluginEntityValues(PluginEntityDto dto, PluginEntity domain) {
		domain.setExampleAttribute(dto.getExampleAttribute());
	}

}
