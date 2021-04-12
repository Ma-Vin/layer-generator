package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.parent.ExtendingClass;
import de.ma_vin.util.sample.content.dto.ITransportable;
import de.ma_vin.util.sample.content.dto.parent.ExtendingClassDto;
import java.util.HashMap;
import java.util.Map;

public class ParentTransportMapper {

	private ParentTransportMapper() {
	}

	/**
	 * singleton
	 */
	private static ParentTransportMapper instance;

	public static ExtendingClass convertToExtendingClass(ExtendingClassDto extendingClass) {
		return convertToExtendingClass(extendingClass, new HashMap<>());
	}

	public static ExtendingClass convertToExtendingClass(ExtendingClassDto extendingClass, Map<String, IIdentifiable> mappedObjects) {
		if (extendingClass == null) {
			return null;
		}

		String identification = extendingClass.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (ExtendingClass) mappedObjects.get(identification);
		}

		ExtendingClass result = new ExtendingClass();

		result.setIdentification(identification);

		result.setAdditionalDescription(extendingClass.getAdditionalDescription());
		result.setDescription(extendingClass.getDescription());

		mappedObjects.put(identification, result);
		return result;
	}

	public static ExtendingClassDto convertToExtendingClassDto(ExtendingClass extendingClass) {
		return convertToExtendingClassDto(extendingClass, new HashMap<>());
	}

	public static ExtendingClassDto convertToExtendingClassDto(ExtendingClass extendingClass, Map<String, ITransportable> mappedObjects) {
		if (extendingClass == null) {
			return null;
		}

		String identification = extendingClass.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (ExtendingClassDto) mappedObjects.get(identification);
		}

		ExtendingClassDto result = new ExtendingClassDto();

		result.setIdentification(identification);

		result.setAdditionalDescription(extendingClass.getAdditionalDescription());
		result.setDescription(extendingClass.getDescription());

		mappedObjects.put(identification, result);
		return result;
	}

	/**
	 * @return the singleton
	 */
	public static ParentTransportMapper getInstance() {
		if (instance == null) {
			instance = new ParentTransportMapper();
		}
		return instance;
	}

}
