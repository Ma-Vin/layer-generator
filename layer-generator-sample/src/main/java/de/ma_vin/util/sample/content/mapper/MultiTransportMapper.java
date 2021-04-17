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
public class MultiTransportMapper {

	public MultiTransportMapper() {
	}

	/**
	 * singleton
	 */
	private static MultiTransportMapper instance;

	public static MultiRefOneParent convertToMultiRefOneParent(MultiRefOneParentDto multiRefOneParent) {
		return convertToMultiRefOneParent(multiRefOneParent, new HashMap<>());
	}

	public static MultiRefOneParent convertToMultiRefOneParent(MultiRefOneParentDto multiRefOneParent, Map<String, IIdentifiable> mappedObjects) {
		if (multiRefOneParent == null) {
			return null;
		}

		String identification = multiRefOneParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (MultiRefOneParent) mappedObjects.get(identification);
		}

		MultiRefOneParent result = DomainObjectFactory.createMultiRefOneParent();

		result.setIdentification(identification);

		result.setDescription(multiRefOneParent.getDescription());

		mappedObjects.put(identification, result);
		return result;
	}

	public static MultiRefOneParentDto convertToMultiRefOneParentDto(MultiRefOneParent multiRefOneParent) {
		return convertToMultiRefOneParentDto(multiRefOneParent, new HashMap<>());
	}

	public static MultiRefOneParentDto convertToMultiRefOneParentDto(MultiRefOneParent multiRefOneParent, Map<String, ITransportable> mappedObjects) {
		if (multiRefOneParent == null) {
			return null;
		}

		String identification = multiRefOneParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (MultiRefOneParentDto) mappedObjects.get(identification);
		}

		MultiRefOneParentDto result = DtoObjectFactory.createMultiRefOneParentDto();

		result.setIdentification(identification);

		result.setDescription(multiRefOneParent.getDescription());

		mappedObjects.put(identification, result);
		return result;
	}

	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDto multiRefTwoParents) {
		return convertToMultiRefTwoParents(multiRefTwoParents, new HashMap<>());
	}

	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDto multiRefTwoParents, Map<String, IIdentifiable> mappedObjects) {
		if (multiRefTwoParents == null) {
			return null;
		}

		String identification = multiRefTwoParents.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (MultiRefTwoParents) mappedObjects.get(identification);
		}

		MultiRefTwoParents result = DomainObjectFactory.createMultiRefTwoParents();

		result.setIdentification(identification);

		result.setDescription(multiRefTwoParents.getDescription());

		mappedObjects.put(identification, result);
		return result;
	}

	public static MultiRefTwoParentsDto convertToMultiRefTwoParentsDto(MultiRefTwoParents multiRefTwoParents) {
		return convertToMultiRefTwoParentsDto(multiRefTwoParents, new HashMap<>());
	}

	public static MultiRefTwoParentsDto convertToMultiRefTwoParentsDto(MultiRefTwoParents multiRefTwoParents, Map<String, ITransportable> mappedObjects) {
		if (multiRefTwoParents == null) {
			return null;
		}

		String identification = multiRefTwoParents.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (MultiRefTwoParentsDto) mappedObjects.get(identification);
		}

		MultiRefTwoParentsDto result = DtoObjectFactory.createMultiRefTwoParentsDto();

		result.setIdentification(identification);

		result.setDescription(multiRefTwoParents.getDescription());

		mappedObjects.put(identification, result);
		return result;
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

}
