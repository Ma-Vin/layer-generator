package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseTransportMapper;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.multi.MultiRefOneParent;
import de.ma_vin.util.sample.content.domain.multi.MultiRefTwoParents;
import de.ma_vin.util.sample.content.dto.DtoObjectFactory;
import de.ma_vin.util.sample.content.dto.ITransportable;
import de.ma_vin.util.sample.content.dto.multi.MultiRefOneParentDto;
import de.ma_vin.util.sample.content.dto.multi.MultiRefTwoParentsDto;
import java.util.HashMap;
import java.util.Map;

@BaseTransportMapper
public class MultiTransportMapper extends AbstractTransportMapper {

	/**
	 * singleton
	 */
	private static MultiTransportMapper instance;

	public static MultiRefOneParent convertToMultiRefOneParent(MultiRefOneParentDto multiRefOneParent) {
		return convertToMultiRefOneParent(multiRefOneParent, new HashMap<>());
	}

	public static MultiRefOneParent convertToMultiRefOneParent(MultiRefOneParentDto multiRefOneParent, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(multiRefOneParent, mappedObjects, DomainObjectFactory::createMultiRefOneParent, (dto, domain) -> getInstance().setMultiRefOneParentValues(dto, domain)
				, (dto, domain) -> getInstance().setMultiRefOneParentSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	public static MultiRefOneParentDto convertToMultiRefOneParentDto(MultiRefOneParent multiRefOneParent) {
		return convertToMultiRefOneParentDto(multiRefOneParent, new HashMap<>());
	}

	public static MultiRefOneParentDto convertToMultiRefOneParentDto(MultiRefOneParent multiRefOneParent, Map<String, ITransportable> mappedObjects) {
		return convertToDto(multiRefOneParent, mappedObjects, DtoObjectFactory::createMultiRefOneParentDto, (domain, dto) -> getInstance().setMultiRefOneParentDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setMultiRefOneParentDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDto multiRefTwoParents) {
		return convertToMultiRefTwoParents(multiRefTwoParents, new HashMap<>());
	}

	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDto multiRefTwoParents, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(multiRefTwoParents, mappedObjects, DomainObjectFactory::createMultiRefTwoParents, (dto, domain) -> getInstance().setMultiRefTwoParentsValues(dto, domain)
				, (dto, domain) -> getInstance().setMultiRefTwoParentsSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	public static MultiRefTwoParentsDto convertToMultiRefTwoParentsDto(MultiRefTwoParents multiRefTwoParents) {
		return convertToMultiRefTwoParentsDto(multiRefTwoParents, new HashMap<>());
	}

	public static MultiRefTwoParentsDto convertToMultiRefTwoParentsDto(MultiRefTwoParents multiRefTwoParents, Map<String, ITransportable> mappedObjects) {
		return convertToDto(multiRefTwoParents, mappedObjects, DtoObjectFactory::createMultiRefTwoParentsDto, (domain, dto) -> getInstance().setMultiRefTwoParentsDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setMultiRefTwoParentsDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * @return the singleton
	 */
	public static MultiTransportMapper getInstance() {
		if (instance == null) {
			instance = TransportMapperFactory.createMultiTransportMapper();
		}
		return instance;
	}

	@SuppressWarnings("java:S1186")
	protected void setMultiRefOneParentDtoSingleReferences(MultiRefOneParent domain, MultiRefOneParentDto dto, Map<String, ITransportable> mappedObjects) {
	}

	protected void setMultiRefOneParentDtoValues(MultiRefOneParent domain, MultiRefOneParentDto dto) {
		dto.setDescription(domain.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setMultiRefOneParentSingleReferences(MultiRefOneParentDto dto, MultiRefOneParent domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setMultiRefOneParentValues(MultiRefOneParentDto dto, MultiRefOneParent domain) {
		domain.setDescription(dto.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setMultiRefTwoParentsDtoSingleReferences(MultiRefTwoParents domain, MultiRefTwoParentsDto dto, Map<String, ITransportable> mappedObjects) {
	}

	protected void setMultiRefTwoParentsDtoValues(MultiRefTwoParents domain, MultiRefTwoParentsDto dto) {
		dto.setDescription(domain.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setMultiRefTwoParentsSingleReferences(MultiRefTwoParentsDto dto, MultiRefTwoParents domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setMultiRefTwoParentsValues(MultiRefTwoParentsDto dto, MultiRefTwoParents domain) {
		domain.setDescription(dto.getDescription());
	}

}
