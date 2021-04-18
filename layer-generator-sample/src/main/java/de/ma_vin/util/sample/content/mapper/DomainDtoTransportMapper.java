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

@BaseTransportMapper
public class DomainDtoTransportMapper extends AbstractTransportMapper {

	/**
	 * singleton
	 */
	private static DomainDtoTransportMapper instance;

	public static DomainAndDto convertToDomainAndDto(DomainAndDtoDto domainAndDto) {
		return convertToDomainAndDto(domainAndDto, new HashMap<>());
	}

	public static DomainAndDto convertToDomainAndDto(DomainAndDtoDto domainAndDto, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(domainAndDto, mappedObjects, DomainObjectFactory::createDomainAndDto, (dto, domain) -> getInstance().setDomainAndDtoValues(dto, domain)
				, (dto, domain) -> getInstance().setDomainAndDtoSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	public static DomainAndDtoDto convertToDomainAndDtoDto(DomainAndDto domainAndDto) {
		return convertToDomainAndDtoDto(domainAndDto, new HashMap<>());
	}

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

	@SuppressWarnings("java:S1186")
	protected void setDomainAndDtoDtoSingleReferences(DomainAndDto domain, DomainAndDtoDto dto, Map<String, ITransportable> mappedObjects) {
	}

	protected void setDomainAndDtoDtoValues(DomainAndDto domain, DomainAndDtoDto dto) {
		dto.setDescription(domain.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setDomainAndDtoSingleReferences(DomainAndDtoDto dto, DomainAndDto domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setDomainAndDtoValues(DomainAndDtoDto dto, DomainAndDto domain) {
		domain.setDescription(dto.getDescription());
	}

}
