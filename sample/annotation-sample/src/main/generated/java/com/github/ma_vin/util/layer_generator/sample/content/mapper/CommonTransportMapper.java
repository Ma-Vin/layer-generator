package com.github.ma_vin.util.layer_generator.sample.content.mapper;

import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseTransportMapper;
import com.github.ma_vin.util.layer_generator.sample.content.domain.DomainObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.content.domain.RootEntity;
import com.github.ma_vin.util.layer_generator.sample.content.domain.SubEntity;
import com.github.ma_vin.util.layer_generator.sample.content.dto.DtoObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.content.dto.ITransportable;
import com.github.ma_vin.util.layer_generator.sample.content.dto.RootEntityDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.SubEntityDto;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class which provides methods to convert a data transport to a domain object of sub package <i>null<i> and the other way around
 */
@BaseTransportMapper
public class CommonTransportMapper extends AbstractTransportMapper {

	/**
	 * singleton
	 */
	private static CommonTransportMapper instance;

	/**
	 * Converts a(n) {@link RootEntityDto} to a(n) {@link RootEntity}
	 *
	 * @param rootEntity the source object which should be converted
	 * @return an equivalent new created {@link RootEntity}
	 */
	public static RootEntity convertToRootEntity(RootEntityDto rootEntity) {
		return convertToRootEntity(rootEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link RootEntityDto} to a(n) {@link RootEntity}
	 *
	 * @param rootEntity    the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code rootEntity} is contained, the found
	 *                      {@link RootEntity} will be returned
	 * @return an equivalent new created {@link RootEntity} or the found one from the given map
	 */
	public static RootEntity convertToRootEntity(RootEntityDto rootEntity, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(rootEntity, mappedObjects, DomainObjectFactory::createRootEntity, (dto, domain) -> getInstance().setRootEntityValues(dto, domain)
				, (dto, domain) -> getInstance().setRootEntitySingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link RootEntity} to a(n) {@link RootEntityDto}
	 *
	 * @param rootEntity the source object which should be converted
	 * @return an equivalent new created {@link RootEntityDto}
	 */
	public static RootEntityDto convertToRootEntityDto(RootEntity rootEntity) {
		return convertToRootEntityDto(rootEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link RootEntity} to a(n) {@link RootEntityDto}
	 *
	 * @param rootEntity    the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code rootEntity} is contained, the found
	 *                      {@link RootEntityDto} will be returned
	 * @return an equivalent new created {@link RootEntityDto} or the found one from the given map
	 */
	public static RootEntityDto convertToRootEntityDto(RootEntity rootEntity, Map<String, ITransportable> mappedObjects) {
		return convertToDto(rootEntity, mappedObjects, DtoObjectFactory::createRootEntityDto, (domain, dto) -> getInstance().setRootEntityDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setRootEntityDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

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
	protected void setRootEntityDtoSingleReferences(RootEntity domain, RootEntityDto dto, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setRootEntityDtoValues(RootEntity domain, RootEntityDto dto) {
		dto.setRootName(domain.getRootName());
		dto.setDescription(domain.getDescription());
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setRootEntitySingleReferences(RootEntityDto dto, RootEntity domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setRootEntityValues(RootEntityDto dto, RootEntity domain) {
		domain.setRootName(dto.getRootName());
		domain.setDescription(dto.getDescription());
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
	protected void setSubEntityDtoValues(SubEntity domain, SubEntityDto dto) {
		dto.setSubName(domain.getSubName());
		dto.setDescription(domain.getDescription());
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
	protected void setSubEntityValues(SubEntityDto dto, SubEntity domain) {
		domain.setSubName(dto.getSubName());
		domain.setDescription(dto.getDescription());
	}

}
