package com.github.ma_vin.util.layer_generator.sample.entity.content.mapper;

import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseTransportMapper;
import com.github.ma_vin.util.layer_generator.sample.entity.content.domain.DomainObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.entity.content.domain.ExtendingEntity;
import com.github.ma_vin.util.layer_generator.sample.entity.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.entity.content.domain.RootEntity;
import com.github.ma_vin.util.layer_generator.sample.entity.content.dto.DerivedEntityDto;
import com.github.ma_vin.util.layer_generator.sample.entity.content.dto.DtoObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.entity.content.dto.ExtendingEntityDto;
import com.github.ma_vin.util.layer_generator.sample.entity.content.dto.ITransportable;
import com.github.ma_vin.util.layer_generator.sample.entity.content.dto.RootEntityDto;
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
	 * Converts a(n) {@link RootEntity} to a(n) {@link DerivedEntityDto}
	 *
	 * @param rootEntity the source object which should be converted
	 * @return an equivalent new created {@link DerivedEntityDto}
	 */
	public static DerivedEntityDto convertToDerivedEntityDto(RootEntity rootEntity) {
		return convertToDerivedEntityDto(rootEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link RootEntity} to a(n) {@link DerivedEntityDto}
	 *
	 * @param rootEntity    the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code derivedEntity} is contained, the found
	 *                      {@link DerivedEntityDto} will be returned
	 * @return an equivalent new created {@link DerivedEntityDto} or the found one from the given map
	 */
	public static DerivedEntityDto convertToDerivedEntityDto(RootEntity rootEntity, Map<String, ITransportable> mappedObjects) {
		return convertToDto(rootEntity, mappedObjects, DtoObjectFactory::createDerivedEntityDto, (domain, dto) -> getInstance().setDerivedEntityDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setDerivedEntityDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link ExtendingEntityDto} to a(n) {@link ExtendingEntity}
	 *
	 * @param extendingEntity the source object which should be converted
	 * @return an equivalent new created {@link ExtendingEntity}
	 */
	public static ExtendingEntity convertToExtendingEntity(ExtendingEntityDto extendingEntity) {
		return convertToExtendingEntity(extendingEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link ExtendingEntityDto} to a(n) {@link ExtendingEntity}
	 *
	 * @param extendingEntity the source object which should be converted
	 * @param mappedObjects   map which contains already mapped objects. If an identification of {@code extendingEntity} is contained, the found
	 *                        {@link ExtendingEntity} will be returned
	 * @return an equivalent new created {@link ExtendingEntity} or the found one from the given map
	 */
	public static ExtendingEntity convertToExtendingEntity(ExtendingEntityDto extendingEntity, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(extendingEntity, mappedObjects, DomainObjectFactory::createExtendingEntity, (dto, domain) -> getInstance().setExtendingEntityValues(dto, domain)
				, (dto, domain) -> getInstance().setExtendingEntitySingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link ExtendingEntity} to a(n) {@link ExtendingEntityDto}
	 *
	 * @param extendingEntity the source object which should be converted
	 * @return an equivalent new created {@link ExtendingEntityDto}
	 */
	public static ExtendingEntityDto convertToExtendingEntityDto(ExtendingEntity extendingEntity) {
		return convertToExtendingEntityDto(extendingEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link ExtendingEntity} to a(n) {@link ExtendingEntityDto}
	 *
	 * @param extendingEntity the source object which should be converted
	 * @param mappedObjects   map which contains already mapped objects. If an identification of {@code extendingEntity} is contained, the found
	 *                        {@link ExtendingEntityDto} will be returned
	 * @return an equivalent new created {@link ExtendingEntityDto} or the found one from the given map
	 */
	public static ExtendingEntityDto convertToExtendingEntityDto(ExtendingEntity extendingEntity, Map<String, ITransportable> mappedObjects) {
		return convertToDto(extendingEntity, mappedObjects, DtoObjectFactory::createExtendingEntityDto, (domain, dto) -> getInstance().setExtendingEntityDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setExtendingEntityDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

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
	protected void setDerivedEntityDtoSingleReferences(RootEntity domain, DerivedEntityDto dto, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setDerivedEntityDtoValues(RootEntity domain, DerivedEntityDto dto) {
		dto.setRootName(domain.getRootName());
	}

	/**
	 * Adds the references at {@code dto} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dto           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dto}
	 */
	@SuppressWarnings("java:S1186")
	protected void setExtendingEntityDtoSingleReferences(ExtendingEntity domain, ExtendingEntityDto dto, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setExtendingEntityDtoValues(ExtendingEntity domain, ExtendingEntityDto dto) {
		dto.setAddedField(domain.getAddedField());
		dto.setSuperName(domain.getSuperName());
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setExtendingEntitySingleReferences(ExtendingEntityDto dto, ExtendingEntity domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setExtendingEntityValues(ExtendingEntityDto dto, ExtendingEntity domain) {
		domain.setAddedField(dto.getAddedField());
		domain.setSuperName(dto.getSuperName());
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
		dto.setAnyAttribute(domain.getAnyAttribute());
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
		domain.setAnyAttribute(dto.getAnyAttribute());
	}

}
