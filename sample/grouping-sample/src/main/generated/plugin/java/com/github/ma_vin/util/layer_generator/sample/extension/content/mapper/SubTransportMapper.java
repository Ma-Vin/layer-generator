package com.github.ma_vin.util.layer_generator.sample.extension.content.mapper;

import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseTransportMapper;
import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.DomainObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.sub.SubEntity;
import com.github.ma_vin.util.layer_generator.sample.extension.content.dto.DtoObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.extension.content.dto.ITransportable;
import com.github.ma_vin.util.layer_generator.sample.extension.content.dto.sub.SubEntityDto;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class which provides methods to convert a data transport to a domain object of sub package <i>sub<i> and the other way around
 */
@BaseTransportMapper
public class SubTransportMapper extends AbstractTransportMapper {

	/**
	 * singleton
	 */
	private static SubTransportMapper instance;

	/**
	 * Converts a(n) {@link SubEntityDto} to a(n) {@link SubEntity}
	 *
	 * @param subEntity the source object which should be converted
	 * @return an equivalent new created {@link SubEntity}
	 */
	public static SubEntity convertToSubEntity(SubEntityDto subEntity) {
		return convertToSubEntity(subEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SubEntityDto} to a(n) {@link SubEntity}
	 *
	 * @param subEntity     the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code subEntity} is contained, the found {@link SubEntity}
	 *                      will be returned
	 * @return an equivalent new created {@link SubEntity} or the found one from the given map
	 */
	public static SubEntity convertToSubEntity(SubEntityDto subEntity, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(subEntity, mappedObjects, DomainObjectFactory::createSubEntity, (dto, domain) -> getInstance().setSubEntityValues(dto, domain)
				, (dto, domain) -> getInstance().setSubEntitySingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link SubEntity} to a(n) {@link SubEntityDto}
	 *
	 * @param subEntity the source object which should be converted
	 * @return an equivalent new created {@link SubEntityDto}
	 */
	public static SubEntityDto convertToSubEntityDto(SubEntity subEntity) {
		return convertToSubEntityDto(subEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SubEntity} to a(n) {@link SubEntityDto}
	 *
	 * @param subEntity     the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code subEntity} is contained, the found
	 *                      {@link SubEntityDto} will be returned
	 * @return an equivalent new created {@link SubEntityDto} or the found one from the given map
	 */
	public static SubEntityDto convertToSubEntityDto(SubEntity subEntity, Map<String, ITransportable> mappedObjects) {
		return convertToDto(subEntity, mappedObjects, DtoObjectFactory::createSubEntityDto, (domain, dto) -> getInstance().setSubEntityDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setSubEntityDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * @return the singleton
	 */
	public static SubTransportMapper getInstance() {
		if (instance == null) {
			instance = TransportMapperFactory.createSubTransportMapper();
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
	protected void setSubEntityDtoSingleReferences(SubEntity domain, SubEntityDto dto, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSubEntityDtoValues(SubEntity domain, SubEntityDto dto) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSubEntitySingleReferences(SubEntityDto dto, SubEntity domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSubEntityValues(SubEntityDto dto, SubEntity domain) {
	}

}
