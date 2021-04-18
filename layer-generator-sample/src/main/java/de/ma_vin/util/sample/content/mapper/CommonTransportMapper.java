package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseTransportMapper;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.RootExt;
import de.ma_vin.util.sample.content.dto.DtoObjectFactory;
import de.ma_vin.util.sample.content.dto.ITransportable;
import de.ma_vin.util.sample.content.dto.RootDto;
import de.ma_vin.util.sample.content.dto.RootExtDto;
import java.util.HashMap;
import java.util.Map;

@BaseTransportMapper
public class CommonTransportMapper extends AbstractTransportMapper {

	/**
	 * singleton
	 */
	private static CommonTransportMapper instance;

	public static Root convertToRoot(RootDto root) {
		return convertToRoot(root, new HashMap<>());
	}

	public static Root convertToRoot(RootDto root, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(root, mappedObjects, DomainObjectFactory::createRoot, (dto, domain) -> getInstance().setRootValues(dto, domain)
				, (dto, domain) -> getInstance().setRootSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	public static RootDto convertToRootDto(Root root) {
		return convertToRootDto(root, new HashMap<>());
	}

	public static RootDto convertToRootDto(Root root, Map<String, ITransportable> mappedObjects) {
		return convertToDto(root, mappedObjects, DtoObjectFactory::createRootDto, (domain, dto) -> getInstance().setRootDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setRootDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	public static RootExt convertToRootExt(RootExtDto rootExt) {
		return convertToRootExt(rootExt, new HashMap<>());
	}

	public static RootExt convertToRootExt(RootExtDto rootExt, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(rootExt, mappedObjects, DomainObjectFactory::createRootExt, (dto, domain) -> getInstance().setRootExtValues(dto, domain)
				, (dto, domain) -> getInstance().setRootExtSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	public static RootExt convertToRootExt(RootExtDto rootExt, Root parent) {
		return convertToRootExt(rootExt, parent, new HashMap<>());
	}

	public static RootExt convertToRootExt(RootExtDto rootExt, Root parent, Map<String, IIdentifiable> mappedObjects) {
		RootExt result = convertToRootExt(rootExt, mappedObjects);
		if (result != null) {
			parent.setExt(result);
		}
		return result;
	}

	public static RootExtDto convertToRootExtDto(RootExt rootExt) {
		return convertToRootExtDto(rootExt, new HashMap<>());
	}

	public static RootExtDto convertToRootExtDto(RootExt rootExt, Map<String, ITransportable> mappedObjects) {
		return convertToDto(rootExt, mappedObjects, DtoObjectFactory::createRootExtDto, (domain, dto) -> getInstance().setRootExtDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setRootExtDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	public static RootExtDto convertToRootExtDto(RootExt rootExt, RootDto parent) {
		return convertToRootExtDto(rootExt, parent, new HashMap<>());
	}

	public static RootExtDto convertToRootExtDto(RootExt rootExt, RootDto parent, Map<String, ITransportable> mappedObjects) {
		RootExtDto result = convertToRootExtDto(rootExt, mappedObjects);
		if (result != null) {
			parent.setExt(result);
		}
		return result;
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

	protected void setRootDtoSingleReferences(Root domain, RootDto dto, Map<String, ITransportable> mappedObjects) {
		SingleTransportMapper.convertToSingleRefOneParentDto(domain.getSingleRef(), dto, mappedObjects);
		SingleTransportMapper.convertToSingleRefTwoParentsDto(domain.getAnotherSingleRef(), dto, mappedObjects);
		SingleIndirectTransportMapper.convertToSingleRefIndirectParentDto(domain.getSingleRefIndirectParent(), dto, mappedObjects);
		SingleIndirectTransportMapper.convertToSingleRefOtherIndirectParentDto(domain.getSingleRefIndirectOtherParent(), dto, mappedObjects);
		FilteringTransportMapper.convertToSomeFilteringOwnerDto(domain.getFiltering(), dto, mappedObjects);
		CommonTransportMapper.convertToRootExtDto(domain.getExt(), dto, mappedObjects);
	}

	protected void setRootDtoValues(Root domain, RootDto dto) {
		dto.setRootName(domain.getRootName());
		dto.setDescription(domain.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setRootExtDtoSingleReferences(RootExt domain, RootExtDto dto, Map<String, ITransportable> mappedObjects) {
	}

	protected void setRootExtDtoValues(RootExt domain, RootExtDto dto) {
		dto.setExtendedInfo(domain.getExtendedInfo());
		dto.setSomeEnum(domain.getSomeEnum());
		dto.setSomeInteger(domain.getSomeInteger());
		dto.setSomeCustom(domain.getSomeCustom());
		dto.setDtoAndDomain(domain.getDtoAndDomain());
		dto.setTextWithDaoInfo(domain.getTextWithDaoInfo());
		dto.setNumberWithDaoInfo(domain.getNumberWithDaoInfo());
		dto.setDaoEnum(domain.getDaoEnum());
		dto.setDaoEnumWithText(domain.getDaoEnumWithText());
		dto.setSomeName(domain.getSomeName());
	}

	@SuppressWarnings("java:S1186")
	protected void setRootExtSingleReferences(RootExtDto dto, RootExt domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setRootExtValues(RootExtDto dto, RootExt domain) {
		domain.setExtendedInfo(dto.getExtendedInfo());
		domain.setSomeEnum(dto.getSomeEnum());
		domain.setSomeInteger(dto.getSomeInteger());
		domain.setSomeCustom(dto.getSomeCustom());
		domain.setDtoAndDomain(dto.getDtoAndDomain());
		domain.setTextWithDaoInfo(dto.getTextWithDaoInfo());
		domain.setNumberWithDaoInfo(dto.getNumberWithDaoInfo());
		domain.setDaoEnum(dto.getDaoEnum());
		domain.setDaoEnumWithText(dto.getDaoEnumWithText());
		domain.setSomeName(dto.getSomeName());
	}

	protected void setRootSingleReferences(RootDto dto, Root domain, Map<String, IIdentifiable> mappedObjects) {
		SingleTransportMapper.convertToSingleRefOneParent(dto.getSingleRef(), domain, mappedObjects);
		SingleTransportMapper.convertToSingleRefTwoParents(dto.getAnotherSingleRef(), domain, mappedObjects);
		SingleIndirectTransportMapper.convertToSingleRefIndirectParent(dto.getSingleRefIndirectParent(), domain, mappedObjects);
		SingleIndirectTransportMapper.convertToSingleRefOtherIndirectParent(dto.getSingleRefIndirectOtherParent(), domain, mappedObjects);
		FilteringTransportMapper.convertToSomeFilteringOwner(dto.getFiltering(), domain, mappedObjects);
		CommonTransportMapper.convertToRootExt(dto.getExt(), domain, mappedObjects);
	}

	protected void setRootValues(RootDto dto, Root domain) {
		domain.setRootName(dto.getRootName());
		domain.setDescription(dto.getDescription());
	}

}
