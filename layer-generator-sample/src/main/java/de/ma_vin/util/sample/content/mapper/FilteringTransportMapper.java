package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseTransportMapper;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.filtering.Filtered;
import de.ma_vin.util.sample.content.domain.filtering.FilteredOnlyDaoField;
import de.ma_vin.util.sample.content.domain.filtering.SomeFilteringOwner;
import de.ma_vin.util.sample.content.dto.DtoObjectFactory;
import de.ma_vin.util.sample.content.dto.ITransportable;
import de.ma_vin.util.sample.content.dto.RootDto;
import de.ma_vin.util.sample.content.dto.filtering.FilteredDto;
import de.ma_vin.util.sample.content.dto.filtering.FilteredOnlyDaoFieldDto;
import de.ma_vin.util.sample.content.dto.filtering.SomeFilteringOwnerDto;
import java.util.HashMap;
import java.util.Map;

@BaseTransportMapper
public class FilteringTransportMapper {

	public FilteringTransportMapper() {
	}

	/**
	 * singleton
	 */
	private static FilteringTransportMapper instance;

	public static Filtered convertToFiltered(FilteredDto filtered) {
		return convertToFiltered(filtered, new HashMap<>());
	}

	public static Filtered convertToFiltered(FilteredDto filtered, Map<String, IIdentifiable> mappedObjects) {
		if (filtered == null) {
			return null;
		}

		String identification = filtered.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (Filtered) mappedObjects.get(identification);
		}

		Filtered result = DomainObjectFactory.createFiltered();

		result.setIdentification(identification);

		result.setDescription(filtered.getDescription());
		result.setSomeEnum(filtered.getSomeEnum());

		mappedObjects.put(identification, result);
		return result;
	}

	public static FilteredDto convertToFilteredDto(Filtered filtered) {
		return convertToFilteredDto(filtered, new HashMap<>());
	}

	public static FilteredDto convertToFilteredDto(Filtered filtered, Map<String, ITransportable> mappedObjects) {
		if (filtered == null) {
			return null;
		}

		String identification = filtered.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (FilteredDto) mappedObjects.get(identification);
		}

		FilteredDto result = DtoObjectFactory.createFilteredDto();

		result.setIdentification(identification);

		result.setDescription(filtered.getDescription());
		result.setSomeEnum(filtered.getSomeEnum());

		mappedObjects.put(identification, result);
		return result;
	}

	public static FilteredOnlyDaoField convertToFilteredOnlyDaoField(FilteredOnlyDaoFieldDto filteredOnlyDaoField) {
		return convertToFilteredOnlyDaoField(filteredOnlyDaoField, new HashMap<>());
	}

	public static FilteredOnlyDaoField convertToFilteredOnlyDaoField(FilteredOnlyDaoFieldDto filteredOnlyDaoField, Map<String, IIdentifiable> mappedObjects) {
		if (filteredOnlyDaoField == null) {
			return null;
		}

		String identification = filteredOnlyDaoField.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (FilteredOnlyDaoField) mappedObjects.get(identification);
		}

		FilteredOnlyDaoField result = DomainObjectFactory.createFilteredOnlyDaoField();

		result.setIdentification(identification);

		result.setDescription(filteredOnlyDaoField.getDescription());

		mappedObjects.put(identification, result);
		return result;
	}

	public static FilteredOnlyDaoFieldDto convertToFilteredOnlyDaoFieldDto(FilteredOnlyDaoField filteredOnlyDaoField) {
		return convertToFilteredOnlyDaoFieldDto(filteredOnlyDaoField, new HashMap<>());
	}

	public static FilteredOnlyDaoFieldDto convertToFilteredOnlyDaoFieldDto(FilteredOnlyDaoField filteredOnlyDaoField, Map<String, ITransportable> mappedObjects) {
		if (filteredOnlyDaoField == null) {
			return null;
		}

		String identification = filteredOnlyDaoField.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (FilteredOnlyDaoFieldDto) mappedObjects.get(identification);
		}

		FilteredOnlyDaoFieldDto result = DtoObjectFactory.createFilteredOnlyDaoFieldDto();

		result.setIdentification(identification);

		result.setDescription(filteredOnlyDaoField.getDescription());

		mappedObjects.put(identification, result);
		return result;
	}

	public static SomeFilteringOwner convertToSomeFilteringOwner(SomeFilteringOwnerDto someFilteringOwner) {
		return convertToSomeFilteringOwner(someFilteringOwner, new HashMap<>());
	}

	public static SomeFilteringOwner convertToSomeFilteringOwner(SomeFilteringOwnerDto someFilteringOwner, Map<String, IIdentifiable> mappedObjects) {
		if (someFilteringOwner == null) {
			return null;
		}

		String identification = someFilteringOwner.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (SomeFilteringOwner) mappedObjects.get(identification);
		}

		SomeFilteringOwner result = DomainObjectFactory.createSomeFilteringOwner();

		result.setIdentification(identification);

		mappedObjects.put(identification, result);
		return result;
	}

	public static SomeFilteringOwner convertToSomeFilteringOwner(SomeFilteringOwnerDto someFilteringOwner, Root parent) {
		return convertToSomeFilteringOwner(someFilteringOwner, parent, new HashMap<>());
	}

	public static SomeFilteringOwner convertToSomeFilteringOwner(SomeFilteringOwnerDto someFilteringOwner, Root parent, Map<String, IIdentifiable> mappedObjects) {
		SomeFilteringOwner result = convertToSomeFilteringOwner(someFilteringOwner, mappedObjects);
		if (result != null) {
			parent.setFiltering(result);
		}
		return result;
	}

	public static SomeFilteringOwnerDto convertToSomeFilteringOwnerDto(SomeFilteringOwner someFilteringOwner) {
		return convertToSomeFilteringOwnerDto(someFilteringOwner, new HashMap<>());
	}

	public static SomeFilteringOwnerDto convertToSomeFilteringOwnerDto(SomeFilteringOwner someFilteringOwner, Map<String, ITransportable> mappedObjects) {
		if (someFilteringOwner == null) {
			return null;
		}

		String identification = someFilteringOwner.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (SomeFilteringOwnerDto) mappedObjects.get(identification);
		}

		SomeFilteringOwnerDto result = DtoObjectFactory.createSomeFilteringOwnerDto();

		result.setIdentification(identification);

		mappedObjects.put(identification, result);
		return result;
	}

	public static SomeFilteringOwnerDto convertToSomeFilteringOwnerDto(SomeFilteringOwner someFilteringOwner, RootDto parent) {
		return convertToSomeFilteringOwnerDto(someFilteringOwner, parent, new HashMap<>());
	}

	public static SomeFilteringOwnerDto convertToSomeFilteringOwnerDto(SomeFilteringOwner someFilteringOwner, RootDto parent, Map<String, ITransportable> mappedObjects) {
		SomeFilteringOwnerDto result = convertToSomeFilteringOwnerDto(someFilteringOwner, mappedObjects);
		if (result != null) {
			parent.setFiltering(result);
		}
		return result;
	}

	/**
	 * @return the singleton
	 */
	public static FilteringTransportMapper getInstance() {
		if (instance == null) {
			instance = TransportMapperFactory.createFilteringTransportMapper();
		}
		return instance;
	}

}
