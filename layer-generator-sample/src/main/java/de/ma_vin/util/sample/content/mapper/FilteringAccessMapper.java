package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.dao.DaoObjectFactory;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.dao.filtering.FilteredDao;
import de.ma_vin.util.sample.content.dao.filtering.FilteredOnlyDaoFieldDao;
import de.ma_vin.util.sample.content.dao.filtering.SomeFilteringOwnerDao;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.filtering.Filtered;
import de.ma_vin.util.sample.content.domain.filtering.FilteredOnlyDaoField;
import de.ma_vin.util.sample.content.domain.filtering.SomeFilteringOwner;
import de.ma_vin.util.sample.given.AnyEnumType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class FilteringAccessMapper {

	private FilteringAccessMapper() {
	}

	/**
	 * singleton
	 */
	private static FilteringAccessMapper instance;

	public static Filtered convertToFiltered(FilteredDao filtered) {
		return convertToFiltered(filtered, new HashMap<>());
	}

	public static Filtered convertToFiltered(FilteredDao filtered, Map<String, IIdentifiable> mappedObjects) {
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

	public static Filtered convertToFiltered(FilteredDao filtered, SomeFilteringOwner parent) {
		return convertToFiltered(filtered, parent, new HashMap<>());
	}

	public static Filtered convertToFiltered(FilteredDao filtered, SomeFilteringOwner parent, Map<String, IIdentifiable> mappedObjects) {
		Filtered result = convertToFiltered(filtered, mappedObjects);
		if (result != null) {
			switch (filtered.getSomeEnum()) {
				case ENUM_VALUE_A:
					parent.addFilterAs(result);
					break;
				case ENUM_VALUE_B:
					parent.addFilterBs(result);
					break;
				case ENUM_VALUE_C:
					parent.addFilterCs(result);
					break;
				default:
					log.error("There is not any mapping rule for dummy of type {}", filtered.getSomeEnum());
			}
		}
		return result;
	}

	public static FilteredDao convertToFilteredDao(Filtered filtered) {
		return convertToFilteredDao(filtered, new HashMap<>());
	}

	public static FilteredDao convertToFilteredDao(Filtered filtered, Map<String, IIdentifiableDao> mappedObjects) {
		if (filtered == null) {
			return null;
		}

		String identification = filtered.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (FilteredDao) mappedObjects.get(identification);
		}

		FilteredDao result = DaoObjectFactory.createFilteredDao();

		result.setIdentification(identification);

		result.setDescription(filtered.getDescription());
		result.setSomeEnum(filtered.getSomeEnum());

		mappedObjects.put(identification, result);
		return result;
	}

	public static FilteredDao convertToFilteredDao(Filtered filtered, SomeFilteringOwnerDao parent) {
		return convertToFilteredDao(filtered, parent, new HashMap<>());
	}

	public static FilteredDao convertToFilteredDao(Filtered filtered, SomeFilteringOwnerDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		FilteredDao result = convertToFilteredDao(filtered, mappedObjects);
		if (result != null) {
			result.setParentSomeFilteringOwner(parent);
			parent.getAggFiltereds().add(result);
		}
		return result;
	}

	public static FilteredOnlyDaoField convertToFilteredOnlyDaoField(FilteredOnlyDaoFieldDao filteredOnlyDaoField) {
		return convertToFilteredOnlyDaoField(filteredOnlyDaoField, new HashMap<>());
	}

	public static FilteredOnlyDaoField convertToFilteredOnlyDaoField(FilteredOnlyDaoFieldDao filteredOnlyDaoField, Map<String, IIdentifiable> mappedObjects) {
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

	public static FilteredOnlyDaoField convertToFilteredOnlyDaoField(FilteredOnlyDaoFieldDao filteredOnlyDaoField, SomeFilteringOwner parent) {
		return convertToFilteredOnlyDaoField(filteredOnlyDaoField, parent, new HashMap<>());
	}

	public static FilteredOnlyDaoField convertToFilteredOnlyDaoField(FilteredOnlyDaoFieldDao filteredOnlyDaoField, SomeFilteringOwner parent, Map<String, IIdentifiable> mappedObjects) {
		FilteredOnlyDaoField result = convertToFilteredOnlyDaoField(filteredOnlyDaoField, mappedObjects);
		if (result != null) {
			switch (filteredOnlyDaoField.getSomeEnum()) {
				case ENUM_VALUE_A:
					parent.addFilterDaoAs(result);
					break;
				case ENUM_VALUE_B:
					parent.addFilterDaoBs(result);
					break;
				case ENUM_VALUE_C:
					parent.addFilterDaoCs(result);
					break;
				default:
					log.error("There is not any mapping rule for dummy of type {}", filteredOnlyDaoField.getSomeEnum());
			}
		}
		return result;
	}

	public static FilteredOnlyDaoFieldDao convertToFilteredOnlyDaoFieldDao(FilteredOnlyDaoField filteredOnlyDaoField, AnyEnumType someEnum) {
		return convertToFilteredOnlyDaoFieldDao(filteredOnlyDaoField, someEnum, new HashMap<>());
	}

	public static FilteredOnlyDaoFieldDao convertToFilteredOnlyDaoFieldDao(FilteredOnlyDaoField filteredOnlyDaoField, SomeFilteringOwnerDao parent, AnyEnumType someEnum) {
		return convertToFilteredOnlyDaoFieldDao(filteredOnlyDaoField, parent, someEnum, new HashMap<>());
	}

	public static FilteredOnlyDaoFieldDao convertToFilteredOnlyDaoFieldDao(FilteredOnlyDaoField filteredOnlyDaoField, SomeFilteringOwnerDao parent, AnyEnumType someEnum, Map<String, IIdentifiableDao> mappedObjects) {
		FilteredOnlyDaoFieldDao result = convertToFilteredOnlyDaoFieldDao(filteredOnlyDaoField, someEnum, mappedObjects);
		if (result != null) {
			result.setParentSomeFilteringOwner(parent);
			parent.getAggFilteredOnlyDaoFields().add(result);
		}
		return result;
	}

	public static FilteredOnlyDaoFieldDao convertToFilteredOnlyDaoFieldDao(FilteredOnlyDaoField filteredOnlyDaoField, AnyEnumType someEnum, Map<String, IIdentifiableDao> mappedObjects) {
		if (filteredOnlyDaoField == null) {
			return null;
		}

		String identification = filteredOnlyDaoField.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (FilteredOnlyDaoFieldDao) mappedObjects.get(identification);
		}

		FilteredOnlyDaoFieldDao result = DaoObjectFactory.createFilteredOnlyDaoFieldDao();

		result.setIdentification(identification);

		result.setDescription(filteredOnlyDaoField.getDescription());

		result.setSomeEnum(someEnum);

		mappedObjects.put(identification, result);
		return result;
	}

	public static SomeFilteringOwner convertToSomeFilteringOwner(SomeFilteringOwnerDao someFilteringOwner, boolean includeChildren) {
		return convertToSomeFilteringOwner(someFilteringOwner, includeChildren, new HashMap<>());
	}

	public static SomeFilteringOwner convertToSomeFilteringOwner(SomeFilteringOwnerDao someFilteringOwner, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		if (someFilteringOwner == null) {
			return null;
		}

		String identification = someFilteringOwner.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (SomeFilteringOwner) mappedObjects.get(identification);
		}

		SomeFilteringOwner result = DomainObjectFactory.createSomeFilteringOwner();

		result.setIdentification(identification);

		if (includeChildren) {
			someFilteringOwner.getAggFiltereds().forEach(arg ->
					FilteringAccessMapper.convertToFiltered(arg, result, mappedObjects)
			);
			someFilteringOwner.getAggFilteredOnlyDaoFields().forEach(arg ->
					FilteringAccessMapper.convertToFilteredOnlyDaoField(arg, result, mappedObjects)
			);
		}

		mappedObjects.put(identification, result);
		return result;
	}

	public static SomeFilteringOwner convertToSomeFilteringOwner(SomeFilteringOwnerDao someFilteringOwner, boolean includeChildren, Root parent) {
		return convertToSomeFilteringOwner(someFilteringOwner, includeChildren, parent, new HashMap<>());
	}

	public static SomeFilteringOwner convertToSomeFilteringOwner(SomeFilteringOwnerDao someFilteringOwner, boolean includeChildren, Root parent, Map<String, IIdentifiable> mappedObjects) {
		SomeFilteringOwner result = convertToSomeFilteringOwner(someFilteringOwner, includeChildren, mappedObjects);
		if (result != null) {
			parent.setFiltering(result);
		}
		return result;
	}

	public static SomeFilteringOwnerDao convertToSomeFilteringOwnerDao(SomeFilteringOwner someFilteringOwner, boolean includeChildren) {
		return convertToSomeFilteringOwnerDao(someFilteringOwner, includeChildren, new HashMap<>());
	}

	public static SomeFilteringOwnerDao convertToSomeFilteringOwnerDao(SomeFilteringOwner someFilteringOwner, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {
		if (someFilteringOwner == null) {
			return null;
		}

		String identification = someFilteringOwner.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (SomeFilteringOwnerDao) mappedObjects.get(identification);
		}

		SomeFilteringOwnerDao result = DaoObjectFactory.createSomeFilteringOwnerDao();

		result.setIdentification(identification);

		result.setAggFiltereds(new ArrayList<>());
		result.setAggFilteredOnlyDaoFields(new ArrayList<>());
		if (includeChildren) {
			someFilteringOwner.getFilterAs().forEach(arg ->
					FilteringAccessMapper.convertToFilteredDao(arg, result, mappedObjects)
			);
			someFilteringOwner.getFilterBs().forEach(arg ->
					FilteringAccessMapper.convertToFilteredDao(arg, result, mappedObjects)
			);
			someFilteringOwner.getFilterCs().forEach(arg ->
					FilteringAccessMapper.convertToFilteredDao(arg, result, mappedObjects)
			);
			someFilteringOwner.getFilterDaoAs().forEach(arg ->
					FilteringAccessMapper.convertToFilteredOnlyDaoFieldDao(arg, result, AnyEnumType.ENUM_VALUE_A, mappedObjects)
			);
			someFilteringOwner.getFilterDaoBs().forEach(arg ->
					FilteringAccessMapper.convertToFilteredOnlyDaoFieldDao(arg, result, AnyEnumType.ENUM_VALUE_B, mappedObjects)
			);
			someFilteringOwner.getFilterDaoCs().forEach(arg ->
					FilteringAccessMapper.convertToFilteredOnlyDaoFieldDao(arg, result, AnyEnumType.ENUM_VALUE_C, mappedObjects)
			);
		}

		mappedObjects.put(identification, result);
		return result;
	}

	public static SomeFilteringOwnerDao convertToSomeFilteringOwnerDao(SomeFilteringOwner someFilteringOwner, boolean includeChildren, RootDao parent) {
		return convertToSomeFilteringOwnerDao(someFilteringOwner, includeChildren, parent, new HashMap<>());
	}

	public static SomeFilteringOwnerDao convertToSomeFilteringOwnerDao(SomeFilteringOwner someFilteringOwner, boolean includeChildren, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		SomeFilteringOwnerDao result = convertToSomeFilteringOwnerDao(someFilteringOwner, includeChildren, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.setFiltering(result);
		}
		return result;
	}

	/**
	 * @return the singleton
	 */
	public static FilteringAccessMapper getInstance() {
		if (instance == null) {
			instance = new FilteringAccessMapper();
		}
		return instance;
	}

}
