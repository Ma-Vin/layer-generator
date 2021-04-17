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
public class MultiIndirectTransportMapper {

	public MultiIndirectTransportMapper() {
	}

	/**
	 * singleton
	 */
	private static MultiIndirectTransportMapper instance;

	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDto multiRefIndirectParent) {
		return convertToMultiRefIndirectParent(multiRefIndirectParent, new HashMap<>());
	}

	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDto multiRefIndirectParent, Map<String, IIdentifiable> mappedObjects) {
		if (multiRefIndirectParent == null) {
			return null;
		}

		String identification = multiRefIndirectParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (MultiRefIndirectParent) mappedObjects.get(identification);
		}

		MultiRefIndirectParent result = DomainObjectFactory.createMultiRefIndirectParent();

		result.setIdentification(identification);

		result.setDescription(multiRefIndirectParent.getDescription());

		mappedObjects.put(identification, result);
		return result;
	}

	public static MultiRefIndirectParentDto convertToMultiRefIndirectParentDto(MultiRefIndirectParent multiRefIndirectParent) {
		return convertToMultiRefIndirectParentDto(multiRefIndirectParent, new HashMap<>());
	}

	public static MultiRefIndirectParentDto convertToMultiRefIndirectParentDto(MultiRefIndirectParent multiRefIndirectParent, Map<String, ITransportable> mappedObjects) {
		if (multiRefIndirectParent == null) {
			return null;
		}

		String identification = multiRefIndirectParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (MultiRefIndirectParentDto) mappedObjects.get(identification);
		}

		MultiRefIndirectParentDto result = DtoObjectFactory.createMultiRefIndirectParentDto();

		result.setIdentification(identification);

		result.setDescription(multiRefIndirectParent.getDescription());

		mappedObjects.put(identification, result);
		return result;
	}

	public static MultiRefOtherIndirectParent convertToMultiRefOtherIndirectParent(MultiRefOtherIndirectParentDto multiRefOtherIndirectParent) {
		return convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParent, new HashMap<>());
	}

	public static MultiRefOtherIndirectParent convertToMultiRefOtherIndirectParent(MultiRefOtherIndirectParentDto multiRefOtherIndirectParent, Map<String, IIdentifiable> mappedObjects) {
		if (multiRefOtherIndirectParent == null) {
			return null;
		}

		String identification = multiRefOtherIndirectParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (MultiRefOtherIndirectParent) mappedObjects.get(identification);
		}

		MultiRefOtherIndirectParent result = DomainObjectFactory.createMultiRefOtherIndirectParent();

		result.setIdentification(identification);

		result.setDescription(multiRefOtherIndirectParent.getDescription());

		mappedObjects.put(identification, result);
		return result;
	}

	public static MultiRefOtherIndirectParentDto convertToMultiRefOtherIndirectParentDto(MultiRefOtherIndirectParent multiRefOtherIndirectParent) {
		return convertToMultiRefOtherIndirectParentDto(multiRefOtherIndirectParent, new HashMap<>());
	}

	public static MultiRefOtherIndirectParentDto convertToMultiRefOtherIndirectParentDto(MultiRefOtherIndirectParent multiRefOtherIndirectParent, Map<String, ITransportable> mappedObjects) {
		if (multiRefOtherIndirectParent == null) {
			return null;
		}

		String identification = multiRefOtherIndirectParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (MultiRefOtherIndirectParentDto) mappedObjects.get(identification);
		}

		MultiRefOtherIndirectParentDto result = DtoObjectFactory.createMultiRefOtherIndirectParentDto();

		result.setIdentification(identification);

		result.setDescription(multiRefOtherIndirectParent.getDescription());

		mappedObjects.put(identification, result);
		return result;
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

}
