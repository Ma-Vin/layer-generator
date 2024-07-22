package com.github.ma_vin.util.layer_generator.sample.extension.content.mapper;

import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseTransportMapper;
import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.CommonEntity;
import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.DomainObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.extension.content.dto.CommonEntityDto;
import com.github.ma_vin.util.layer_generator.sample.extension.content.dto.DtoObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.extension.content.dto.ITransportable;
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
	 * Converts a(n) {@link CommonEntityDto} to a(n) {@link CommonEntity}
	 *
	 * @param commonEntity the source object which should be converted
	 * @return an equivalent new created {@link CommonEntity}
	 */
	public static CommonEntity convertToCommonEntity(CommonEntityDto commonEntity) {
		return convertToCommonEntity(commonEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link CommonEntityDto} to a(n) {@link CommonEntity}
	 *
	 * @param commonEntity  the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code commonEntity} is contained, the found
	 *                      {@link CommonEntity} will be returned
	 * @return an equivalent new created {@link CommonEntity} or the found one from the given map
	 */
	public static CommonEntity convertToCommonEntity(CommonEntityDto commonEntity, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(commonEntity, mappedObjects, DomainObjectFactory::createCommonEntity, (dto, domain) -> getInstance().setCommonEntityValues(dto, domain)
				, (dto, domain) -> getInstance().setCommonEntitySingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link CommonEntity} to a(n) {@link CommonEntityDto}
	 *
	 * @param commonEntity the source object which should be converted
	 * @return an equivalent new created {@link CommonEntityDto}
	 */
	public static CommonEntityDto convertToCommonEntityDto(CommonEntity commonEntity) {
		return convertToCommonEntityDto(commonEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link CommonEntity} to a(n) {@link CommonEntityDto}
	 *
	 * @param commonEntity  the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code commonEntity} is contained, the found
	 *                      {@link CommonEntityDto} will be returned
	 * @return an equivalent new created {@link CommonEntityDto} or the found one from the given map
	 */
	public static CommonEntityDto convertToCommonEntityDto(CommonEntity commonEntity, Map<String, ITransportable> mappedObjects) {
		return convertToDto(commonEntity, mappedObjects, DtoObjectFactory::createCommonEntityDto, (domain, dto) -> getInstance().setCommonEntityDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setCommonEntityDtoSingleReferences(domain, dto, mappedObjects)
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
	protected void setCommonEntityDtoSingleReferences(CommonEntity domain, CommonEntityDto dto, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setCommonEntityDtoValues(CommonEntity domain, CommonEntityDto dto) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setCommonEntitySingleReferences(CommonEntityDto dto, CommonEntity domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setCommonEntityValues(CommonEntityDto dto, CommonEntity domain) {
	}

}
