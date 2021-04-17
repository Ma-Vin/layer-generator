package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.domain.dto.DomainAndDto;
import de.ma_vin.util.sample.content.dto.DtoObjectFactory;
import de.ma_vin.util.sample.content.dto.ITransportable;
import de.ma_vin.util.sample.content.dto.domain.dto.DomainAndDtoDto;
import java.util.HashMap;
import java.util.Map;

public class DomainDtoTransportMapper {

	private DomainDtoTransportMapper() {
	}

	/**
	 * singleton
	 */
	private static DomainDtoTransportMapper instance;

	public static DomainAndDto convertToDomainAndDto(DomainAndDtoDto domainAndDto) {
		return convertToDomainAndDto(domainAndDto, new HashMap<>());
	}

	public static DomainAndDto convertToDomainAndDto(DomainAndDtoDto domainAndDto, Map<String, IIdentifiable> mappedObjects) {
		if (domainAndDto == null) {
			return null;
		}

		String identification = domainAndDto.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (DomainAndDto) mappedObjects.get(identification);
		}

		DomainAndDto result = DomainObjectFactory.createDomainAndDto();

		result.setIdentification(identification);

		result.setDescription(domainAndDto.getDescription());

		mappedObjects.put(identification, result);
		return result;
	}

	public static DomainAndDtoDto convertToDomainAndDtoDto(DomainAndDto domainAndDto) {
		return convertToDomainAndDtoDto(domainAndDto, new HashMap<>());
	}

	public static DomainAndDtoDto convertToDomainAndDtoDto(DomainAndDto domainAndDto, Map<String, ITransportable> mappedObjects) {
		if (domainAndDto == null) {
			return null;
		}

		String identification = domainAndDto.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (DomainAndDtoDto) mappedObjects.get(identification);
		}

		DomainAndDtoDto result = DtoObjectFactory.createDomainAndDtoDto();

		result.setIdentification(identification);

		result.setDescription(domainAndDto.getDescription());

		mappedObjects.put(identification, result);
		return result;
	}

	/**
	 * @return the singleton
	 */
	public static DomainDtoTransportMapper getInstance() {
		if (instance == null) {
			instance = new DomainDtoTransportMapper();
		}
		return instance;
	}

}
