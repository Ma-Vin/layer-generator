package com.github.ma_vin.util.layer_generator.sample.extension.content.mapper;

import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseTransportMapper;
import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.DomainObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.ToExtendEntity;
import com.github.ma_vin.util.layer_generator.sample.extension.content.dto.DtoObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.extension.content.dto.ITransportable;
import com.github.ma_vin.util.layer_generator.sample.extension.content.dto.ToExtendEntityDto;
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
	 * Converts a(n) {@link ToExtendEntityDto} to a(n) {@link ToExtendEntity}
	 *
	 * @param toExtendEntity the source object which should be converted
	 * @return an equivalent new created {@link ToExtendEntity}
	 */
	public static ToExtendEntity convertToToExtendEntity(ToExtendEntityDto toExtendEntity) {
		return convertToToExtendEntity(toExtendEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link ToExtendEntityDto} to a(n) {@link ToExtendEntity}
	 *
	 * @param toExtendEntity the source object which should be converted
	 * @param mappedObjects  map which contains already mapped objects. If an identification of {@code toExtendEntity} is contained, the found
	 *                       {@link ToExtendEntity} will be returned
	 * @return an equivalent new created {@link ToExtendEntity} or the found one from the given map
	 */
	public static ToExtendEntity convertToToExtendEntity(ToExtendEntityDto toExtendEntity, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(toExtendEntity, mappedObjects, DomainObjectFactory::createToExtendEntity, (dto, domain) -> getInstance().setToExtendEntityValues(dto, domain)
				, (dto, domain) -> getInstance().setToExtendEntitySingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link ToExtendEntity} to a(n) {@link ToExtendEntityDto}
	 *
	 * @param toExtendEntity the source object which should be converted
	 * @return an equivalent new created {@link ToExtendEntityDto}
	 */
	public static ToExtendEntityDto convertToToExtendEntityDto(ToExtendEntity toExtendEntity) {
		return convertToToExtendEntityDto(toExtendEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link ToExtendEntity} to a(n) {@link ToExtendEntityDto}
	 *
	 * @param toExtendEntity the source object which should be converted
	 * @param mappedObjects  map which contains already mapped objects. If an identification of {@code toExtendEntity} is contained, the found
	 *                       {@link ToExtendEntityDto} will be returned
	 * @return an equivalent new created {@link ToExtendEntityDto} or the found one from the given map
	 */
	public static ToExtendEntityDto convertToToExtendEntityDto(ToExtendEntity toExtendEntity, Map<String, ITransportable> mappedObjects) {
		return convertToDto(toExtendEntity, mappedObjects, DtoObjectFactory::createToExtendEntityDto, (domain, dto) -> getInstance().setToExtendEntityDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setToExtendEntityDtoSingleReferences(domain, dto, mappedObjects)
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
	protected void setToExtendEntityDtoSingleReferences(ToExtendEntity domain, ToExtendEntityDto dto, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setToExtendEntityDtoValues(ToExtendEntity domain, ToExtendEntityDto dto) {
		dto.setExistingAttribute(domain.getExistingAttribute());
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setToExtendEntitySingleReferences(ToExtendEntityDto dto, ToExtendEntity domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setToExtendEntityValues(ToExtendEntityDto dto, ToExtendEntity domain) {
		domain.setExistingAttribute(dto.getExistingAttribute());
	}

}
