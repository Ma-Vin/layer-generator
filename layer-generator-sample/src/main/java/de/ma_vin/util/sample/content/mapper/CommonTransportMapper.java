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
public class CommonTransportMapper {

	public CommonTransportMapper() {
	}

	/**
	 * singleton
	 */
	private static CommonTransportMapper instance;

	public static Root convertToRoot(RootDto root) {
		return convertToRoot(root, new HashMap<>());
	}

	public static Root convertToRoot(RootDto root, Map<String, IIdentifiable> mappedObjects) {
		if (root == null) {
			return null;
		}

		String identification = root.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (Root) mappedObjects.get(identification);
		}

		Root result = DomainObjectFactory.createRoot();

		result.setIdentification(identification);

		result.setRootName(root.getRootName());
		result.setDescription(root.getDescription());

		SingleTransportMapper.convertToSingleRefOneParent(root.getSingleRef(), result, mappedObjects);
		SingleTransportMapper.convertToSingleRefTwoParents(root.getAnotherSingleRef(), result, mappedObjects);
		SingleIndirectTransportMapper.convertToSingleRefIndirectParent(root.getSingleRefIndirectParent(), result, mappedObjects);
		SingleIndirectTransportMapper.convertToSingleRefOtherIndirectParent(root.getSingleRefIndirectOtherParent(), result, mappedObjects);
		FilteringTransportMapper.convertToSomeFilteringOwner(root.getFiltering(), result, mappedObjects);
		CommonTransportMapper.convertToRootExt(root.getExt(), result, mappedObjects);

		mappedObjects.put(identification, result);
		return result;
	}

	public static RootDto convertToRootDto(Root root) {
		return convertToRootDto(root, new HashMap<>());
	}

	public static RootDto convertToRootDto(Root root, Map<String, ITransportable> mappedObjects) {
		if (root == null) {
			return null;
		}

		String identification = root.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (RootDto) mappedObjects.get(identification);
		}

		RootDto result = DtoObjectFactory.createRootDto();

		result.setIdentification(identification);

		result.setRootName(root.getRootName());
		result.setDescription(root.getDescription());

		SingleTransportMapper.convertToSingleRefOneParentDto(root.getSingleRef(), result, mappedObjects);
		SingleTransportMapper.convertToSingleRefTwoParentsDto(root.getAnotherSingleRef(), result, mappedObjects);
		SingleIndirectTransportMapper.convertToSingleRefIndirectParentDto(root.getSingleRefIndirectParent(), result, mappedObjects);
		SingleIndirectTransportMapper.convertToSingleRefOtherIndirectParentDto(root.getSingleRefIndirectOtherParent(), result, mappedObjects);
		FilteringTransportMapper.convertToSomeFilteringOwnerDto(root.getFiltering(), result, mappedObjects);
		CommonTransportMapper.convertToRootExtDto(root.getExt(), result, mappedObjects);

		mappedObjects.put(identification, result);
		return result;
	}

	public static RootExt convertToRootExt(RootExtDto rootExt) {
		return convertToRootExt(rootExt, new HashMap<>());
	}

	public static RootExt convertToRootExt(RootExtDto rootExt, Map<String, IIdentifiable> mappedObjects) {
		if (rootExt == null) {
			return null;
		}

		String identification = rootExt.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (RootExt) mappedObjects.get(identification);
		}

		RootExt result = DomainObjectFactory.createRootExt();

		result.setIdentification(identification);

		result.setExtendedInfo(rootExt.getExtendedInfo());
		result.setSomeEnum(rootExt.getSomeEnum());
		result.setSomeInteger(rootExt.getSomeInteger());
		result.setSomeCustom(rootExt.getSomeCustom());
		result.setDtoAndDomain(rootExt.getDtoAndDomain());
		result.setTextWithDaoInfo(rootExt.getTextWithDaoInfo());
		result.setNumberWithDaoInfo(rootExt.getNumberWithDaoInfo());
		result.setDaoEnum(rootExt.getDaoEnum());
		result.setDaoEnumWithText(rootExt.getDaoEnumWithText());
		result.setSomeName(rootExt.getSomeName());

		mappedObjects.put(identification, result);
		return result;
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
		if (rootExt == null) {
			return null;
		}

		String identification = rootExt.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (RootExtDto) mappedObjects.get(identification);
		}

		RootExtDto result = DtoObjectFactory.createRootExtDto();

		result.setIdentification(identification);

		result.setExtendedInfo(rootExt.getExtendedInfo());
		result.setSomeEnum(rootExt.getSomeEnum());
		result.setSomeInteger(rootExt.getSomeInteger());
		result.setSomeCustom(rootExt.getSomeCustom());
		result.setDtoAndDomain(rootExt.getDtoAndDomain());
		result.setTextWithDaoInfo(rootExt.getTextWithDaoInfo());
		result.setNumberWithDaoInfo(rootExt.getNumberWithDaoInfo());
		result.setDaoEnum(rootExt.getDaoEnum());
		result.setDaoEnumWithText(rootExt.getDaoEnumWithText());
		result.setSomeName(rootExt.getSomeName());

		mappedObjects.put(identification, result);
		return result;
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

}
