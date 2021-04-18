package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseTransportMapper;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.multi.indirect.MultiRefIndirectParent;
import de.ma_vin.util.sample.content.domain.multi.indirect.MultiRefOtherIndirectParent;
import de.ma_vin.util.sample.content.dto.DtoObjectFactory;
import de.ma_vin.util.sample.content.dto.ITransportable;
import de.ma_vin.util.sample.content.dto.multi.indirect.MultiRefIndirectParentDto;
import de.ma_vin.util.sample.content.dto.multi.indirect.MultiRefOtherIndirectParentDto;
import java.util.HashMap;
import java.util.Map;

@BaseTransportMapper
public class MultiIndirectTransportMapper extends AbstractTransportMapper {

	/**
	 * singleton
	 */
	private static MultiIndirectTransportMapper instance;

	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDto multiRefIndirectParent) {
		return convertToMultiRefIndirectParent(multiRefIndirectParent, new HashMap<>());
	}

	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDto multiRefIndirectParent, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(multiRefIndirectParent, mappedObjects, DomainObjectFactory::createMultiRefIndirectParent, (dto, domain) -> getInstance().setMultiRefIndirectParentValues(dto, domain)
				, (dto, domain) -> getInstance().setMultiRefIndirectParentSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	public static MultiRefIndirectParentDto convertToMultiRefIndirectParentDto(MultiRefIndirectParent multiRefIndirectParent) {
		return convertToMultiRefIndirectParentDto(multiRefIndirectParent, new HashMap<>());
	}

	public static MultiRefIndirectParentDto convertToMultiRefIndirectParentDto(MultiRefIndirectParent multiRefIndirectParent, Map<String, ITransportable> mappedObjects) {
		return convertToDto(multiRefIndirectParent, mappedObjects, DtoObjectFactory::createMultiRefIndirectParentDto, (domain, dto) -> getInstance().setMultiRefIndirectParentDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setMultiRefIndirectParentDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	public static MultiRefOtherIndirectParent convertToMultiRefOtherIndirectParent(MultiRefOtherIndirectParentDto multiRefOtherIndirectParent) {
		return convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParent, new HashMap<>());
	}

	public static MultiRefOtherIndirectParent convertToMultiRefOtherIndirectParent(MultiRefOtherIndirectParentDto multiRefOtherIndirectParent, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(multiRefOtherIndirectParent, mappedObjects, DomainObjectFactory::createMultiRefOtherIndirectParent, (dto, domain) -> getInstance().setMultiRefOtherIndirectParentValues(dto, domain)
				, (dto, domain) -> getInstance().setMultiRefOtherIndirectParentSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	public static MultiRefOtherIndirectParentDto convertToMultiRefOtherIndirectParentDto(MultiRefOtherIndirectParent multiRefOtherIndirectParent) {
		return convertToMultiRefOtherIndirectParentDto(multiRefOtherIndirectParent, new HashMap<>());
	}

	public static MultiRefOtherIndirectParentDto convertToMultiRefOtherIndirectParentDto(MultiRefOtherIndirectParent multiRefOtherIndirectParent, Map<String, ITransportable> mappedObjects) {
		return convertToDto(multiRefOtherIndirectParent, mappedObjects, DtoObjectFactory::createMultiRefOtherIndirectParentDto, (domain, dto) -> getInstance().setMultiRefOtherIndirectParentDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setMultiRefOtherIndirectParentDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * @return the singleton
	 */
	public static MultiIndirectTransportMapper getInstance() {
		if (instance == null) {
			instance = TransportMapperFactory.createMultiIndirectTransportMapper();
		}
		return instance;
	}

	@SuppressWarnings("java:S1186")
	protected void setMultiRefIndirectParentDtoSingleReferences(MultiRefIndirectParent domain, MultiRefIndirectParentDto dto, Map<String, ITransportable> mappedObjects) {
	}

	protected void setMultiRefIndirectParentDtoValues(MultiRefIndirectParent domain, MultiRefIndirectParentDto dto) {
		dto.setDescription(domain.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setMultiRefIndirectParentSingleReferences(MultiRefIndirectParentDto dto, MultiRefIndirectParent domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setMultiRefIndirectParentValues(MultiRefIndirectParentDto dto, MultiRefIndirectParent domain) {
		domain.setDescription(dto.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setMultiRefOtherIndirectParentDtoSingleReferences(MultiRefOtherIndirectParent domain, MultiRefOtherIndirectParentDto dto, Map<String, ITransportable> mappedObjects) {
	}

	protected void setMultiRefOtherIndirectParentDtoValues(MultiRefOtherIndirectParent domain, MultiRefOtherIndirectParentDto dto) {
		dto.setDescription(domain.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setMultiRefOtherIndirectParentSingleReferences(MultiRefOtherIndirectParentDto dto, MultiRefOtherIndirectParent domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setMultiRefOtherIndirectParentValues(MultiRefOtherIndirectParentDto dto, MultiRefOtherIndirectParent domain) {
		domain.setDescription(dto.getDescription());
	}

}
