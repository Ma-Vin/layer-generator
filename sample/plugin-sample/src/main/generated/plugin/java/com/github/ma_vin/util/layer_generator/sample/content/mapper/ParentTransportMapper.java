package com.github.ma_vin.util.layer_generator.sample.content.mapper;

import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseTransportMapper;
import com.github.ma_vin.util.layer_generator.sample.content.domain.DomainObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.content.domain.parent.ExtendingClass;
import com.github.ma_vin.util.layer_generator.sample.content.dto.DtoObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.content.dto.ITransportable;
import com.github.ma_vin.util.layer_generator.sample.content.dto.parent.ExtendingClassDto;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class which provides methods to convert a data transport to a domain object of sub package <i>parent<i> and the other way around
 */
@BaseTransportMapper
public class ParentTransportMapper extends AbstractTransportMapper {

	/**
	 * singleton
	 */
	private static ParentTransportMapper instance;

	/**
	 * Converts a(n) {@link ExtendingClassDto} to a(n) {@link ExtendingClass}
	 *
	 * @param extendingClass the source object which should be converted
	 * @return an equivalent new created {@link ExtendingClass}
	 */
	public static ExtendingClass convertToExtendingClass(ExtendingClassDto extendingClass) {
		return convertToExtendingClass(extendingClass, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link ExtendingClassDto} to a(n) {@link ExtendingClass}
	 *
	 * @param extendingClass the source object which should be converted
	 * @param mappedObjects  map which contains already mapped objects. If an identification of {@code extendingClass} is contained, the found
	 *                       {@link ExtendingClass} will be returned
	 * @return an equivalent new created {@link ExtendingClass} or the found one from the given map
	 */
	public static ExtendingClass convertToExtendingClass(ExtendingClassDto extendingClass, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(extendingClass, mappedObjects, DomainObjectFactory::createExtendingClass, (dto, domain) -> getInstance().setExtendingClassValues(dto, domain)
				, (dto, domain) -> getInstance().setExtendingClassSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link ExtendingClass} to a(n) {@link ExtendingClassDto}
	 *
	 * @param extendingClass the source object which should be converted
	 * @return an equivalent new created {@link ExtendingClassDto}
	 */
	public static ExtendingClassDto convertToExtendingClassDto(ExtendingClass extendingClass) {
		return convertToExtendingClassDto(extendingClass, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link ExtendingClass} to a(n) {@link ExtendingClassDto}
	 *
	 * @param extendingClass the source object which should be converted
	 * @param mappedObjects  map which contains already mapped objects. If an identification of {@code extendingClass} is contained, the found
	 *                       {@link ExtendingClassDto} will be returned
	 * @return an equivalent new created {@link ExtendingClassDto} or the found one from the given map
	 */
	public static ExtendingClassDto convertToExtendingClassDto(ExtendingClass extendingClass, Map<String, ITransportable> mappedObjects) {
		return convertToDto(extendingClass, mappedObjects, DtoObjectFactory::createExtendingClassDto, (domain, dto) -> getInstance().setExtendingClassDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setExtendingClassDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * @return the singleton
	 */
	public static ParentTransportMapper getInstance() {
		if (instance == null) {
			instance = TransportMapperFactory.createParentTransportMapper();
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
	protected void setExtendingClassDtoSingleReferences(ExtendingClass domain, ExtendingClassDto dto, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setExtendingClassDtoValues(ExtendingClass domain, ExtendingClassDto dto) {
		dto.setAdditionalDescription(domain.getAdditionalDescription());
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
	protected void setExtendingClassSingleReferences(ExtendingClassDto dto, ExtendingClass domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setExtendingClassValues(ExtendingClassDto dto, ExtendingClass domain) {
		domain.setAdditionalDescription(dto.getAdditionalDescription());
		domain.setDescription(dto.getDescription());
	}

}
