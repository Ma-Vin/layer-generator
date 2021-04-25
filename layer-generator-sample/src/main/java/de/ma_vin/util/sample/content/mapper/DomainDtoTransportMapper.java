package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseTransportMapper;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.domain.dto.DomainAndDto;
import de.ma_vin.util.sample.content.dto.DtoObjectFactory;
import de.ma_vin.util.sample.content.dto.ITransportable;
import de.ma_vin.util.sample.content.dto.domain.dto.DomainAndDtoDto;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class which provides methods to convert a data transport to a domain object of sub package <i>domain.dto<i> and the other way around
 */
@BaseTransportMapper
public class DomainDtoTransportMapper extends AbstractTransportMapper {

	/**
	 * singleton
	 */
	private static DomainDtoTransportMapper instance;

	/**
	 * Converts a(n) {@link DomainAndDtoDto} to a(n) {@link DomainAndDto}
	 *
	 * @param domainAndDto the source object which should be converted
	 * @return an equivalent new created {@link DomainAndDto}
	 */
	public static DomainAndDto convertToDomainAndDto(DomainAndDtoDto domainAndDto) {
		return convertToDomainAndDto(domainAndDto, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link DomainAndDtoDto} to a(n) {@link DomainAndDto}
	 *
	 * @param domainAndDto  the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code domainAndDto} is contained, the found
	 *                      {@link DomainAndDto} will be returned
	 * @return an equivalent new created {@link DomainAndDto} or the found one from the given map
	 */
	public static DomainAndDto convertToDomainAndDto(DomainAndDtoDto domainAndDto, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(domainAndDto, mappedObjects, DomainObjectFactory::createDomainAndDto, (dto, domain) -> getInstance().setDomainAndDtoValues(dto, domain)
				, (dto, domain) -> getInstance().setDomainAndDtoSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link DomainAndDto} to a(n) {@link DomainAndDtoDto}
	 *
	 * @param domainAndDto the source object which should be converted
	 * @return an equivalent new created {@link DomainAndDtoDto}
	 */
	public static DomainAndDtoDto convertToDomainAndDtoDto(DomainAndDto domainAndDto) {
		return convertToDomainAndDtoDto(domainAndDto, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link DomainAndDto} to a(n) {@link DomainAndDtoDto}
	 *
	 * @param domainAndDto  the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code domainAndDto} is contained, the found
	 *                      {@link DomainAndDtoDto} will be returned
	 * @return an equivalent new created {@link DomainAndDtoDto} or the found one from the given map
	 */
	public static DomainAndDtoDto convertToDomainAndDtoDto(DomainAndDto domainAndDto, Map<String, ITransportable> mappedObjects) {
		return convertToDto(domainAndDto, mappedObjects, DtoObjectFactory::createDomainAndDtoDto, (domain, dto) -> getInstance().setDomainAndDtoDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setDomainAndDtoDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * @return the singleton
	 */
	public static DomainDtoTransportMapper getInstance() {
		if (instance == null) {
			instance = TransportMapperFactory.createDomainDtoTransportMapper();
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
	protected void setDomainAndDtoDtoSingleReferences(DomainAndDto domain, DomainAndDtoDto dto, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setDomainAndDtoDtoValues(DomainAndDto domain, DomainAndDtoDto dto) {
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
	protected void setDomainAndDtoSingleReferences(DomainAndDtoDto dto, DomainAndDto domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setDomainAndDtoValues(DomainAndDtoDto dto, DomainAndDto domain) {
		domain.setDescription(dto.getDescription());
	}

}
