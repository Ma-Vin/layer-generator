package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseTransportMapper;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.single.SingleRefOneParent;
import de.ma_vin.util.sample.content.domain.single.SingleRefTwoParents;
import de.ma_vin.util.sample.content.dto.DtoObjectFactory;
import de.ma_vin.util.sample.content.dto.ITransportable;
import de.ma_vin.util.sample.content.dto.RootDto;
import de.ma_vin.util.sample.content.dto.single.SingleRefOneParentDto;
import de.ma_vin.util.sample.content.dto.single.SingleRefTwoParentsDto;
import java.util.HashMap;
import java.util.Map;

@BaseTransportMapper
public class SingleTransportMapper extends AbstractTransportMapper {

	/**
	 * singleton
	 */
	private static SingleTransportMapper instance;

	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDto singleRefOneParent) {
		return convertToSingleRefOneParent(singleRefOneParent, new HashMap<>());
	}

	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDto singleRefOneParent, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(singleRefOneParent, mappedObjects, DomainObjectFactory::createSingleRefOneParent, (dto, domain) -> getInstance().setSingleRefOneParentValues(dto, domain)
				, (dto, domain) -> getInstance().setSingleRefOneParentSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDto singleRefOneParent, Root parent) {
		return convertToSingleRefOneParent(singleRefOneParent, parent, new HashMap<>());
	}

	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDto singleRefOneParent, Root parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefOneParent result = convertToSingleRefOneParent(singleRefOneParent, mappedObjects);
		if (result != null) {
			parent.setSingleRef(result);
		}
		return result;
	}

	public static SingleRefOneParentDto convertToSingleRefOneParentDto(SingleRefOneParent singleRefOneParent) {
		return convertToSingleRefOneParentDto(singleRefOneParent, new HashMap<>());
	}

	public static SingleRefOneParentDto convertToSingleRefOneParentDto(SingleRefOneParent singleRefOneParent, Map<String, ITransportable> mappedObjects) {
		return convertToDto(singleRefOneParent, mappedObjects, DtoObjectFactory::createSingleRefOneParentDto, (domain, dto) -> getInstance().setSingleRefOneParentDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setSingleRefOneParentDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	public static SingleRefOneParentDto convertToSingleRefOneParentDto(SingleRefOneParent singleRefOneParent, RootDto parent) {
		return convertToSingleRefOneParentDto(singleRefOneParent, parent, new HashMap<>());
	}

	public static SingleRefOneParentDto convertToSingleRefOneParentDto(SingleRefOneParent singleRefOneParent, RootDto parent, Map<String, ITransportable> mappedObjects) {
		SingleRefOneParentDto result = convertToSingleRefOneParentDto(singleRefOneParent, mappedObjects);
		if (result != null) {
			parent.setSingleRef(result);
		}
		return result;
	}

	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDto singleRefTwoParents) {
		return convertToSingleRefTwoParents(singleRefTwoParents, new HashMap<>());
	}

	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDto singleRefTwoParents, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(singleRefTwoParents, mappedObjects, DomainObjectFactory::createSingleRefTwoParents, (dto, domain) -> getInstance().setSingleRefTwoParentsValues(dto, domain)
				, (dto, domain) -> getInstance().setSingleRefTwoParentsSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDto singleRefTwoParents, Root parent) {
		return convertToSingleRefTwoParents(singleRefTwoParents, parent, new HashMap<>());
	}

	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDto singleRefTwoParents, Root parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefTwoParents result = convertToSingleRefTwoParents(singleRefTwoParents, mappedObjects);
		if (result != null) {
			parent.setAnotherSingleRef(result);
		}
		return result;
	}

	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDto singleRefTwoParents, SingleRefOneParent parent) {
		return convertToSingleRefTwoParents(singleRefTwoParents, parent, new HashMap<>());
	}

	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDto singleRefTwoParents, SingleRefOneParent parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefTwoParents result = convertToSingleRefTwoParents(singleRefTwoParents, mappedObjects);
		if (result != null) {
			parent.setSingleRef(result);
		}
		return result;
	}

	public static SingleRefTwoParentsDto convertToSingleRefTwoParentsDto(SingleRefTwoParents singleRefTwoParents) {
		return convertToSingleRefTwoParentsDto(singleRefTwoParents, new HashMap<>());
	}

	public static SingleRefTwoParentsDto convertToSingleRefTwoParentsDto(SingleRefTwoParents singleRefTwoParents, Map<String, ITransportable> mappedObjects) {
		return convertToDto(singleRefTwoParents, mappedObjects, DtoObjectFactory::createSingleRefTwoParentsDto, (domain, dto) -> getInstance().setSingleRefTwoParentsDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setSingleRefTwoParentsDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	public static SingleRefTwoParentsDto convertToSingleRefTwoParentsDto(SingleRefTwoParents singleRefTwoParents, RootDto parent) {
		return convertToSingleRefTwoParentsDto(singleRefTwoParents, parent, new HashMap<>());
	}

	public static SingleRefTwoParentsDto convertToSingleRefTwoParentsDto(SingleRefTwoParents singleRefTwoParents, RootDto parent, Map<String, ITransportable> mappedObjects) {
		SingleRefTwoParentsDto result = convertToSingleRefTwoParentsDto(singleRefTwoParents, mappedObjects);
		if (result != null) {
			parent.setAnotherSingleRef(result);
		}
		return result;
	}

	public static SingleRefTwoParentsDto convertToSingleRefTwoParentsDto(SingleRefTwoParents singleRefTwoParents, SingleRefOneParentDto parent) {
		return convertToSingleRefTwoParentsDto(singleRefTwoParents, parent, new HashMap<>());
	}

	public static SingleRefTwoParentsDto convertToSingleRefTwoParentsDto(SingleRefTwoParents singleRefTwoParents, SingleRefOneParentDto parent, Map<String, ITransportable> mappedObjects) {
		SingleRefTwoParentsDto result = convertToSingleRefTwoParentsDto(singleRefTwoParents, mappedObjects);
		if (result != null) {
			parent.setSingleRef(result);
		}
		return result;
	}

	/**
	 * @return the singleton
	 */
	public static SingleTransportMapper getInstance() {
		if (instance == null) {
			instance = TransportMapperFactory.createSingleTransportMapper();
		}
		return instance;
	}

	protected void setSingleRefOneParentDtoSingleReferences(SingleRefOneParent domain, SingleRefOneParentDto dto, Map<String, ITransportable> mappedObjects) {
		SingleTransportMapper.convertToSingleRefTwoParentsDto(domain.getSingleRef(), dto, mappedObjects);
	}

	protected void setSingleRefOneParentDtoValues(SingleRefOneParent domain, SingleRefOneParentDto dto) {
		dto.setDescription(domain.getDescription());
	}

	protected void setSingleRefOneParentSingleReferences(SingleRefOneParentDto dto, SingleRefOneParent domain, Map<String, IIdentifiable> mappedObjects) {
		SingleTransportMapper.convertToSingleRefTwoParents(dto.getSingleRef(), domain, mappedObjects);
	}

	protected void setSingleRefOneParentValues(SingleRefOneParentDto dto, SingleRefOneParent domain) {
		domain.setDescription(dto.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setSingleRefTwoParentsDtoSingleReferences(SingleRefTwoParents domain, SingleRefTwoParentsDto dto, Map<String, ITransportable> mappedObjects) {
	}

	protected void setSingleRefTwoParentsDtoValues(SingleRefTwoParents domain, SingleRefTwoParentsDto dto) {
		dto.setDescription(domain.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setSingleRefTwoParentsSingleReferences(SingleRefTwoParentsDto dto, SingleRefTwoParents domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setSingleRefTwoParentsValues(SingleRefTwoParentsDto dto, SingleRefTwoParents domain) {
		domain.setDescription(dto.getDescription());
	}

}
