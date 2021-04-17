package de.ma_vin.util.sample.content.mapper;

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

public class SingleIndirectTransportMapper {

	private SingleIndirectTransportMapper() {
	}

	/**
	 * singleton
	 */
	private static SingleIndirectTransportMapper instance;

	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDto singleRefIndirectParent) {
		return convertToSingleRefIndirectParent(singleRefIndirectParent, new HashMap<>());
	}

	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDto singleRefIndirectParent, Map<String, IIdentifiable> mappedObjects) {
		if (singleRefIndirectParent == null) {
			return null;
		}

		String identification = singleRefIndirectParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (SingleRefIndirectParent) mappedObjects.get(identification);
		}

		SingleRefIndirectParent result = DomainObjectFactory.createSingleRefIndirectParent();

		result.setIdentification(identification);

		result.setDescription(singleRefIndirectParent.getDescription());

		mappedObjects.put(identification, result);
		return result;
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
		if (singleRefIndirectParent == null) {
			return null;
		}

		String identification = singleRefIndirectParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (SingleRefIndirectParentDto) mappedObjects.get(identification);
		}

		SingleRefIndirectParentDto result = DtoObjectFactory.createSingleRefIndirectParentDto();

		result.setIdentification(identification);

		result.setDescription(singleRefIndirectParent.getDescription());

		mappedObjects.put(identification, result);
		return result;
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
		if (singleRefOtherIndirectParent == null) {
			return null;
		}

		String identification = singleRefOtherIndirectParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (SingleRefOtherIndirectParent) mappedObjects.get(identification);
		}

		SingleRefOtherIndirectParent result = DomainObjectFactory.createSingleRefOtherIndirectParent();

		result.setIdentification(identification);

		result.setDescription(singleRefOtherIndirectParent.getDescription());

		result.setSingleIndirectRef(SingleIndirectTransportMapper.convertToSingleRefIndirectParent(singleRefOtherIndirectParent.getSingleIndirectRef(), mappedObjects));

		mappedObjects.put(identification, result);
		return result;
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
		if (singleRefOtherIndirectParent == null) {
			return null;
		}

		String identification = singleRefOtherIndirectParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (SingleRefOtherIndirectParentDto) mappedObjects.get(identification);
		}

		SingleRefOtherIndirectParentDto result = DtoObjectFactory.createSingleRefOtherIndirectParentDto();

		result.setIdentification(identification);

		result.setDescription(singleRefOtherIndirectParent.getDescription());

		result.setSingleIndirectRef(SingleIndirectTransportMapper.convertToSingleRefIndirectParentDto(singleRefOtherIndirectParent.getSingleIndirectRef(), mappedObjects));

		mappedObjects.put(identification, result);
		return result;
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
			instance = new SingleIndirectTransportMapper();
		}
		return instance;
	}

}
