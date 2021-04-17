package de.ma_vin.util.sample.content.mapper;

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

public class SingleTransportMapper {

	private SingleTransportMapper() {
	}

	/**
	 * singleton
	 */
	private static SingleTransportMapper instance;

	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDto singleRefOneParent) {
		return convertToSingleRefOneParent(singleRefOneParent, new HashMap<>());
	}

	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDto singleRefOneParent, Map<String, IIdentifiable> mappedObjects) {
		if (singleRefOneParent == null) {
			return null;
		}

		String identification = singleRefOneParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (SingleRefOneParent) mappedObjects.get(identification);
		}

		SingleRefOneParent result = DomainObjectFactory.createSingleRefOneParent();

		result.setIdentification(identification);

		result.setDescription(singleRefOneParent.getDescription());

		SingleTransportMapper.convertToSingleRefTwoParents(singleRefOneParent.getSingleRef(), result, mappedObjects);

		mappedObjects.put(identification, result);
		return result;
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
		if (singleRefOneParent == null) {
			return null;
		}

		String identification = singleRefOneParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (SingleRefOneParentDto) mappedObjects.get(identification);
		}

		SingleRefOneParentDto result = DtoObjectFactory.createSingleRefOneParentDto();

		result.setIdentification(identification);

		result.setDescription(singleRefOneParent.getDescription());

		SingleTransportMapper.convertToSingleRefTwoParentsDto(singleRefOneParent.getSingleRef(), result, mappedObjects);

		mappedObjects.put(identification, result);
		return result;
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
		if (singleRefTwoParents == null) {
			return null;
		}

		String identification = singleRefTwoParents.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (SingleRefTwoParents) mappedObjects.get(identification);
		}

		SingleRefTwoParents result = DomainObjectFactory.createSingleRefTwoParents();

		result.setIdentification(identification);

		result.setDescription(singleRefTwoParents.getDescription());

		mappedObjects.put(identification, result);
		return result;
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
		if (singleRefTwoParents == null) {
			return null;
		}

		String identification = singleRefTwoParents.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (SingleRefTwoParentsDto) mappedObjects.get(identification);
		}

		SingleRefTwoParentsDto result = DtoObjectFactory.createSingleRefTwoParentsDto();

		result.setIdentification(identification);

		result.setDescription(singleRefTwoParents.getDescription());

		mappedObjects.put(identification, result);
		return result;
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
			instance = new SingleTransportMapper();
		}
		return instance;
	}

}
