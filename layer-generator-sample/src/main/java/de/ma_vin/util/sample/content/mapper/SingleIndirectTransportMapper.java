package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseTransportMapper;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.single.indirect.SingleRefIndirectParent;
import de.ma_vin.util.sample.content.domain.single.indirect.SingleRefOtherIndirectParent;
import de.ma_vin.util.sample.content.dto.DtoObjectFactory;
import de.ma_vin.util.sample.content.dto.ITransportable;
import de.ma_vin.util.sample.content.dto.RootDto;
import de.ma_vin.util.sample.content.dto.single.indirect.SingleRefIndirectParentDto;
import de.ma_vin.util.sample.content.dto.single.indirect.SingleRefOtherIndirectParentDto;
import java.util.HashMap;
import java.util.Map;

@BaseTransportMapper
public class SingleIndirectTransportMapper extends AbstractTransportMapper {

	/**
	 * singleton
	 */
	private static SingleIndirectTransportMapper instance;

	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDto singleRefIndirectParent) {
		return convertToSingleRefIndirectParent(singleRefIndirectParent, new HashMap<>());
	}

	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDto singleRefIndirectParent, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(singleRefIndirectParent, mappedObjects, DomainObjectFactory::createSingleRefIndirectParent, (dto, domain) -> getInstance().setSingleRefIndirectParentValues(dto, domain)
				, (dto, domain) -> getInstance().setSingleRefIndirectParentSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDto singleRefIndirectParent, Root parent) {
		return convertToSingleRefIndirectParent(singleRefIndirectParent, parent, new HashMap<>());
	}

	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDto singleRefIndirectParent, Root parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefIndirectParent result = convertToSingleRefIndirectParent(singleRefIndirectParent, mappedObjects);
		if (result != null) {
			parent.setSingleRefIndirectParent(result);
		}
		return result;
	}

	public static SingleRefIndirectParentDto convertToSingleRefIndirectParentDto(SingleRefIndirectParent singleRefIndirectParent) {
		return convertToSingleRefIndirectParentDto(singleRefIndirectParent, new HashMap<>());
	}

	public static SingleRefIndirectParentDto convertToSingleRefIndirectParentDto(SingleRefIndirectParent singleRefIndirectParent, Map<String, ITransportable> mappedObjects) {
		return convertToDto(singleRefIndirectParent, mappedObjects, DtoObjectFactory::createSingleRefIndirectParentDto, (domain, dto) -> getInstance().setSingleRefIndirectParentDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setSingleRefIndirectParentDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	public static SingleRefIndirectParentDto convertToSingleRefIndirectParentDto(SingleRefIndirectParent singleRefIndirectParent, RootDto parent) {
		return convertToSingleRefIndirectParentDto(singleRefIndirectParent, parent, new HashMap<>());
	}

	public static SingleRefIndirectParentDto convertToSingleRefIndirectParentDto(SingleRefIndirectParent singleRefIndirectParent, RootDto parent, Map<String, ITransportable> mappedObjects) {
		SingleRefIndirectParentDto result = convertToSingleRefIndirectParentDto(singleRefIndirectParent, mappedObjects);
		if (result != null) {
			parent.setSingleRefIndirectParent(result);
		}
		return result;
	}

	public static SingleRefOtherIndirectParent convertToSingleRefOtherIndirectParent(SingleRefOtherIndirectParentDto singleRefOtherIndirectParent) {
		return convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParent, new HashMap<>());
	}

	public static SingleRefOtherIndirectParent convertToSingleRefOtherIndirectParent(SingleRefOtherIndirectParentDto singleRefOtherIndirectParent, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(singleRefOtherIndirectParent, mappedObjects, DomainObjectFactory::createSingleRefOtherIndirectParent, (dto, domain) -> getInstance().setSingleRefOtherIndirectParentValues(dto, domain)
				, (dto, domain) -> getInstance().setSingleRefOtherIndirectParentSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	public static SingleRefOtherIndirectParent convertToSingleRefOtherIndirectParent(SingleRefOtherIndirectParentDto singleRefOtherIndirectParent, Root parent) {
		return convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParent, parent, new HashMap<>());
	}

	public static SingleRefOtherIndirectParent convertToSingleRefOtherIndirectParent(SingleRefOtherIndirectParentDto singleRefOtherIndirectParent, Root parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefOtherIndirectParent result = convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParent, mappedObjects);
		if (result != null) {
			parent.setSingleRefIndirectOtherParent(result);
		}
		return result;
	}

	public static SingleRefOtherIndirectParentDto convertToSingleRefOtherIndirectParentDto(SingleRefOtherIndirectParent singleRefOtherIndirectParent) {
		return convertToSingleRefOtherIndirectParentDto(singleRefOtherIndirectParent, new HashMap<>());
	}

	public static SingleRefOtherIndirectParentDto convertToSingleRefOtherIndirectParentDto(SingleRefOtherIndirectParent singleRefOtherIndirectParent, Map<String, ITransportable> mappedObjects) {
		return convertToDto(singleRefOtherIndirectParent, mappedObjects, DtoObjectFactory::createSingleRefOtherIndirectParentDto, (domain, dto) -> getInstance().setSingleRefOtherIndirectParentDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setSingleRefOtherIndirectParentDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	public static SingleRefOtherIndirectParentDto convertToSingleRefOtherIndirectParentDto(SingleRefOtherIndirectParent singleRefOtherIndirectParent, RootDto parent) {
		return convertToSingleRefOtherIndirectParentDto(singleRefOtherIndirectParent, parent, new HashMap<>());
	}

	public static SingleRefOtherIndirectParentDto convertToSingleRefOtherIndirectParentDto(SingleRefOtherIndirectParent singleRefOtherIndirectParent, RootDto parent, Map<String, ITransportable> mappedObjects) {
		SingleRefOtherIndirectParentDto result = convertToSingleRefOtherIndirectParentDto(singleRefOtherIndirectParent, mappedObjects);
		if (result != null) {
			parent.setSingleRefIndirectOtherParent(result);
		}
		return result;
	}

	/**
	 * @return the singleton
	 */
	public static SingleIndirectTransportMapper getInstance() {
		if (instance == null) {
			instance = TransportMapperFactory.createSingleIndirectTransportMapper();
		}
		return instance;
	}

	@SuppressWarnings("java:S1186")
	protected void setSingleRefIndirectParentDtoSingleReferences(SingleRefIndirectParent domain, SingleRefIndirectParentDto dto, Map<String, ITransportable> mappedObjects) {
	}

	protected void setSingleRefIndirectParentDtoValues(SingleRefIndirectParent domain, SingleRefIndirectParentDto dto) {
		dto.setDescription(domain.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setSingleRefIndirectParentSingleReferences(SingleRefIndirectParentDto dto, SingleRefIndirectParent domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setSingleRefIndirectParentValues(SingleRefIndirectParentDto dto, SingleRefIndirectParent domain) {
		domain.setDescription(dto.getDescription());
	}

	protected void setSingleRefOtherIndirectParentDtoSingleReferences(SingleRefOtherIndirectParent domain, SingleRefOtherIndirectParentDto dto, Map<String, ITransportable> mappedObjects) {
		dto.setSingleIndirectRef(SingleIndirectTransportMapper.convertToSingleRefIndirectParentDto(domain.getSingleIndirectRef(), mappedObjects));
	}

	protected void setSingleRefOtherIndirectParentDtoValues(SingleRefOtherIndirectParent domain, SingleRefOtherIndirectParentDto dto) {
		dto.setDescription(domain.getDescription());
	}

	protected void setSingleRefOtherIndirectParentSingleReferences(SingleRefOtherIndirectParentDto dto, SingleRefOtherIndirectParent domain, Map<String, IIdentifiable> mappedObjects) {
		domain.setSingleIndirectRef(SingleIndirectTransportMapper.convertToSingleRefIndirectParent(dto.getSingleIndirectRef(), mappedObjects));
	}

	protected void setSingleRefOtherIndirectParentValues(SingleRefOtherIndirectParentDto dto, SingleRefOtherIndirectParent domain) {
		domain.setDescription(dto.getDescription());
	}

}
