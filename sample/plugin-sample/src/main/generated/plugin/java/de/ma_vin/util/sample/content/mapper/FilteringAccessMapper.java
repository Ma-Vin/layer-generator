package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseAccessMapper;
import de.ma_vin.util.sample.content.dao.DaoObjectFactory;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.dao.filtering.FilteredDao;
import de.ma_vin.util.sample.content.dao.filtering.FilteredNotOwnerDao;
import de.ma_vin.util.sample.content.dao.filtering.FilteredOnlyDaoFieldDao;
import de.ma_vin.util.sample.content.dao.filtering.FilteredOnlyDaoFieldNotOwnerDao;
import de.ma_vin.util.sample.content.dao.filtering.SomeDifferentFilteringNotOwnerDao;
import de.ma_vin.util.sample.content.dao.filtering.SomeDifferentFilteringNotOwnerToFilteredDao;
import de.ma_vin.util.sample.content.dao.filtering.SomeFilteringOwnerDao;
import de.ma_vin.util.sample.content.dao.filtering.SomeFilteringOwnerToFilteredNotOwnerDao;
import de.ma_vin.util.sample.content.dao.filtering.SomeFilteringOwnerToFilteredOnlyDaoFieldNotOwnerDao;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.filtering.Filtered;
import de.ma_vin.util.sample.content.domain.filtering.FilteredNotOwner;
import de.ma_vin.util.sample.content.domain.filtering.FilteredOnlyDaoField;
import de.ma_vin.util.sample.content.domain.filtering.FilteredOnlyDaoFieldNotOwner;
import de.ma_vin.util.sample.content.domain.filtering.SomeDifferentFilteringNotOwner;
import de.ma_vin.util.sample.content.domain.filtering.SomeFilteringOwner;
import de.ma_vin.util.sample.given.AnyEnumType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;

/**
 * Generated class which provides methods to convert a data access to a domain object of sub package <i>filtering<i> and the other way around
 */
@BaseAccessMapper
@Log4j2
public class FilteringAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static FilteringAccessMapper instance;

	/**
	 * Converts a(n) {@link FilteredDao} to a(n) {@link Filtered}
	 *
	 * @param filtered the source object which should be converted
	 * @return an equivalent new created {@link Filtered}
	 */
	public static Filtered convertToFiltered(FilteredDao filtered) {
		return convertToFiltered(filtered, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FilteredDao} to a(n) {@link Filtered}
	 *
	 * @param filtered      the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code filtered} is contained, the found {@link Filtered}
	 *                      will be returned
	 * @return an equivalent new created {@link Filtered} or the found one from the given map
	 */
	public static Filtered convertToFiltered(FilteredDao filtered, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(filtered, mappedObjects, DomainObjectFactory::createFiltered, (dao, domain) -> getInstance().setFilteredValues(dao, domain)
				, (dao, domain) -> getInstance().setFilteredSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setFilteredMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link FilteredDao} to a(n) {@link Filtered} and sets the result to the corresponding reference property at the parent
	 *
	 * @param filtered          the source object which should be converted
	 * @param parent            the parent of converted result
	 * @param filterAnyEnumType value to map between domain multiple {@link java.util.Collection}s and dao aggregated {@link java.util.Collection}
	 * @return an equivalent new created {@link Filtered}
	 */
	public static Filtered convertToFiltered(FilteredDao filtered, SomeDifferentFilteringNotOwner parent, AnyEnumType filterAnyEnumType) {
		return convertToFiltered(filtered, parent, filterAnyEnumType, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FilteredDao} to a(n) {@link Filtered} and sets the result to the corresponding reference property at the parent
	 *
	 * @param filtered          the source object which should be converted
	 * @param parent            the parent of converted result
	 * @param filterAnyEnumType value to map between domain multiple {@link java.util.Collection}s and dao aggregated {@link java.util.Collection}
	 * @param mappedObjects     map which contains already mapped objects. If an identification of {@code filtered} is contained, the found
	 *                          {@link Filtered} will be returned
	 * @return an equivalent new created {@link Filtered} or the found one from the given map
	 */
	public static Filtered convertToFiltered(FilteredDao filtered, SomeDifferentFilteringNotOwner parent, AnyEnumType filterAnyEnumType
			, Map<String, IIdentifiable> mappedObjects) {
		Filtered result = convertToFiltered(filtered, mappedObjects);
		if (result != null) {
			switch (filterAnyEnumType) {
				case ENUM_VALUE_A:
					parent.addFilterA(result);
					break;
				case ENUM_VALUE_B:
					parent.addFilterB(result);
					break;
				case ENUM_VALUE_C:
					parent.addFilterC(result);
					break;
				default:
					log.error("There is not any mapping rule for filtered of type {}", filterAnyEnumType);
			}
		}
		return result;
	}

	/**
	 * Converts a(n) {@link FilteredDao} to a(n) {@link Filtered} and sets the result to the corresponding reference property at the parent
	 *
	 * @param filtered the source object which should be converted
	 * @param parent   the parent of converted result
	 * @return an equivalent new created {@link Filtered}
	 */
	public static Filtered convertToFiltered(FilteredDao filtered, SomeFilteringOwner parent) {
		return convertToFiltered(filtered, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FilteredDao} to a(n) {@link Filtered} and sets the result to the corresponding reference property at the parent
	 *
	 * @param filtered      the source object which should be converted
	 * @param parent        the parent of converted result
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code filtered} is contained, the found {@link Filtered}
	 *                      will be returned
	 * @return an equivalent new created {@link Filtered} or the found one from the given map
	 */
	public static Filtered convertToFiltered(FilteredDao filtered, SomeFilteringOwner parent, Map<String, IIdentifiable> mappedObjects) {
		Filtered result = convertToFiltered(filtered, mappedObjects);
		if (result != null) {
			switch (filtered.getSomeEnum()) {
				case ENUM_VALUE_A:
					parent.addFilterA(result);
					break;
				case ENUM_VALUE_B:
					parent.addFilterB(result);
					break;
				case ENUM_VALUE_C:
					parent.addFilterC(result);
					break;
				default:
					log.error("There is not any mapping rule for filtered of type {}", filtered.getSomeEnum());
			}
		}
		return result;
	}

	/**
	 * Converts a(n) {@link Filtered} to a(n) {@link FilteredDao}
	 *
	 * @param filtered the source object which should be converted
	 * @return an equivalent new created {@link FilteredDao}
	 */
	public static FilteredDao convertToFilteredDao(Filtered filtered) {
		return convertToFilteredDao(filtered, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link Filtered} to a(n) {@link FilteredDao}
	 *
	 * @param filtered      the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code filtered} is contained, the found
	 *                      {@link FilteredDao} will be returned
	 * @return an equivalent new created {@link FilteredDao} or the found one from the given map
	 */
	public static FilteredDao convertToFilteredDao(Filtered filtered, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(filtered, mappedObjects, DaoObjectFactory::createFilteredDao, (domain, dao) -> getInstance().setFilteredDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setFilteredDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setFilteredDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * Converts a(n) {@link Filtered} to a(n) {@link FilteredDao} and sets the result to the corresponding reference property at the parent
	 *
	 * @param filtered          the source object which should be converted
	 * @param parent            the parent of converted result
	 * @param filterAnyEnumType value to map between domain multiple {@link java.util.Collection}s and dao aggregated {@link java.util.Collection}
	 * @return an equivalent new created {@link FilteredDao}
	 */
	public static FilteredDao convertToFilteredDao(Filtered filtered, SomeDifferentFilteringNotOwnerDao parent, AnyEnumType filterAnyEnumType) {
		return convertToFilteredDao(filtered, parent, filterAnyEnumType, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link Filtered} to a(n) {@link FilteredDao} and sets the result to the corresponding reference property at the parent
	 *
	 * @param filtered          the source object which should be converted
	 * @param parent            the parent of converted result
	 * @param filterAnyEnumType value to map between domain multiple {@link java.util.Collection}s and dao aggregated {@link java.util.Collection}
	 * @param mappedObjects     map which contains already mapped objects. If an identification of {@code filtered} is contained, the found
	 *                          {@link FilteredDao} will be returned
	 * @return an equivalent new created {@link FilteredDao} or the found one from the given map
	 */
	public static FilteredDao convertToFilteredDao(Filtered filtered, SomeDifferentFilteringNotOwnerDao parent, AnyEnumType filterAnyEnumType
			, Map<String, IIdentifiableDao> mappedObjects) {
		FilteredDao result = convertToFilteredDao(filtered, mappedObjects);
		if (result != null) {
			SomeDifferentFilteringNotOwnerToFilteredDao connectionTable = DaoObjectFactory.createSomeDifferentFilteringNotOwnerToFilteredDao();
			connectionTable.setFiltered(result);
			connectionTable.setSomeDifferentFilteringNotOwner(parent);
			connectionTable.setFilterAnyEnumType(filterAnyEnumType);
			parent.getAggFiltered().add(connectionTable);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link Filtered} to a(n) {@link FilteredDao} and sets the result to the corresponding reference property at the parent
	 *
	 * @param filtered the source object which should be converted
	 * @param parent   the parent of converted result
	 * @return an equivalent new created {@link FilteredDao}
	 */
	public static FilteredDao convertToFilteredDao(Filtered filtered, SomeFilteringOwnerDao parent) {
		return convertToFilteredDao(filtered, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link Filtered} to a(n) {@link FilteredDao} and sets the result to the corresponding reference property at the parent
	 *
	 * @param filtered      the source object which should be converted
	 * @param parent        the parent of converted result
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code filtered} is contained, the found
	 *                      {@link FilteredDao} will be returned
	 * @return an equivalent new created {@link FilteredDao} or the found one from the given map
	 */
	public static FilteredDao convertToFilteredDao(Filtered filtered, SomeFilteringOwnerDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		FilteredDao result = convertToFilteredDao(filtered, mappedObjects);
		if (result != null) {
			result.setParentSomeFilteringOwner(parent);
			parent.getAggFiltered().add(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link FilteredNotOwnerDao} to a(n) {@link FilteredNotOwner}
	 *
	 * @param filteredNotOwner the source object which should be converted
	 * @return an equivalent new created {@link FilteredNotOwner}
	 */
	public static FilteredNotOwner convertToFilteredNotOwner(FilteredNotOwnerDao filteredNotOwner) {
		return convertToFilteredNotOwner(filteredNotOwner, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FilteredNotOwnerDao} to a(n) {@link FilteredNotOwner}
	 *
	 * @param filteredNotOwner the source object which should be converted
	 * @param mappedObjects    map which contains already mapped objects. If an identification of {@code filteredNotOwner} is contained, the found
	 *                         {@link FilteredNotOwner} will be returned
	 * @return an equivalent new created {@link FilteredNotOwner} or the found one from the given map
	 */
	public static FilteredNotOwner convertToFilteredNotOwner(FilteredNotOwnerDao filteredNotOwner, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(filteredNotOwner, mappedObjects, DomainObjectFactory::createFilteredNotOwner, (dao, domain) -> getInstance().setFilteredNotOwnerValues(dao, domain)
				, (dao, domain) -> getInstance().setFilteredNotOwnerSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setFilteredNotOwnerMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link FilteredNotOwnerDao} to a(n) {@link FilteredNotOwner} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param filteredNotOwner the source object which should be converted
	 * @param parent           the parent of converted result
	 * @return an equivalent new created {@link FilteredNotOwner}
	 */
	public static FilteredNotOwner convertToFilteredNotOwner(FilteredNotOwnerDao filteredNotOwner, SomeFilteringOwner parent) {
		return convertToFilteredNotOwner(filteredNotOwner, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FilteredNotOwnerDao} to a(n) {@link FilteredNotOwner} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param filteredNotOwner the source object which should be converted
	 * @param parent           the parent of converted result
	 * @param mappedObjects    map which contains already mapped objects. If an identification of {@code filteredNotOwner} is contained, the found
	 *                         {@link FilteredNotOwner} will be returned
	 * @return an equivalent new created {@link FilteredNotOwner} or the found one from the given map
	 */
	public static FilteredNotOwner convertToFilteredNotOwner(FilteredNotOwnerDao filteredNotOwner, SomeFilteringOwner parent
			, Map<String, IIdentifiable> mappedObjects) {
		FilteredNotOwner result = convertToFilteredNotOwner(filteredNotOwner, mappedObjects);
		if (result != null) {
			switch (filteredNotOwner.getSomeEnumNotOwner()) {
				case ENUM_VALUE_A:
					parent.addFilterNotOwnerA(result);
					break;
				case ENUM_VALUE_B:
					parent.addFilterNotOwnerB(result);
					break;
				case ENUM_VALUE_C:
					parent.addFilterNotOwnerC(result);
					break;
				default:
					log.error("There is not any mapping rule for filteredNotOwner of type {}", filteredNotOwner.getSomeEnumNotOwner());
			}
		}
		return result;
	}

	/**
	 * Converts a(n) {@link FilteredNotOwner} to a(n) {@link FilteredNotOwnerDao}
	 *
	 * @param filteredNotOwner the source object which should be converted
	 * @return an equivalent new created {@link FilteredNotOwnerDao}
	 */
	public static FilteredNotOwnerDao convertToFilteredNotOwnerDao(FilteredNotOwner filteredNotOwner) {
		return convertToFilteredNotOwnerDao(filteredNotOwner, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FilteredNotOwner} to a(n) {@link FilteredNotOwnerDao}
	 *
	 * @param filteredNotOwner the source object which should be converted
	 * @param mappedObjects    map which contains already mapped objects. If an identification of {@code filteredNotOwner} is contained, the found
	 *                         {@link FilteredNotOwnerDao} will be returned
	 * @return an equivalent new created {@link FilteredNotOwnerDao} or the found one from the given map
	 */
	public static FilteredNotOwnerDao convertToFilteredNotOwnerDao(FilteredNotOwner filteredNotOwner, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(filteredNotOwner, mappedObjects, DaoObjectFactory::createFilteredNotOwnerDao, (domain, dao) -> getInstance().setFilteredNotOwnerDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setFilteredNotOwnerDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setFilteredNotOwnerDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * Converts a(n) {@link FilteredNotOwner} to a(n) {@link FilteredNotOwnerDao} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param filteredNotOwner the source object which should be converted
	 * @param parent           the parent of converted result
	 * @return an equivalent new created {@link FilteredNotOwnerDao}
	 */
	public static FilteredNotOwnerDao convertToFilteredNotOwnerDao(FilteredNotOwner filteredNotOwner, SomeFilteringOwnerDao parent) {
		return convertToFilteredNotOwnerDao(filteredNotOwner, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FilteredNotOwner} to a(n) {@link FilteredNotOwnerDao} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param filteredNotOwner the source object which should be converted
	 * @param parent           the parent of converted result
	 * @param mappedObjects    map which contains already mapped objects. If an identification of {@code filteredNotOwner} is contained, the found
	 *                         {@link FilteredNotOwnerDao} will be returned
	 * @return an equivalent new created {@link FilteredNotOwnerDao} or the found one from the given map
	 */
	public static FilteredNotOwnerDao convertToFilteredNotOwnerDao(FilteredNotOwner filteredNotOwner, SomeFilteringOwnerDao parent
			, Map<String, IIdentifiableDao> mappedObjects) {
		FilteredNotOwnerDao result = convertToFilteredNotOwnerDao(filteredNotOwner, mappedObjects);
		if (result != null) {
			SomeFilteringOwnerToFilteredNotOwnerDao connectionTable = DaoObjectFactory.createSomeFilteringOwnerToFilteredNotOwnerDao();
			connectionTable.setFilteredNotOwner(result);
			connectionTable.setSomeFilteringOwner(parent);
			parent.getAggFilteredNotOwner().add(connectionTable);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link FilteredOnlyDaoFieldDao} to a(n) {@link FilteredOnlyDaoField}
	 *
	 * @param filteredOnlyDaoField the source object which should be converted
	 * @return an equivalent new created {@link FilteredOnlyDaoField}
	 */
	public static FilteredOnlyDaoField convertToFilteredOnlyDaoField(FilteredOnlyDaoFieldDao filteredOnlyDaoField) {
		return convertToFilteredOnlyDaoField(filteredOnlyDaoField, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FilteredOnlyDaoFieldDao} to a(n) {@link FilteredOnlyDaoField}
	 *
	 * @param filteredOnlyDaoField the source object which should be converted
	 * @param mappedObjects        map which contains already mapped objects. If an identification of {@code filteredOnlyDaoField} is contained, the found
	 *                             {@link FilteredOnlyDaoField} will be returned
	 * @return an equivalent new created {@link FilteredOnlyDaoField} or the found one from the given map
	 */
	public static FilteredOnlyDaoField convertToFilteredOnlyDaoField(FilteredOnlyDaoFieldDao filteredOnlyDaoField
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(filteredOnlyDaoField, mappedObjects, DomainObjectFactory::createFilteredOnlyDaoField, (dao, domain) -> getInstance().setFilteredOnlyDaoFieldValues(dao, domain)
				, (dao, domain) -> getInstance().setFilteredOnlyDaoFieldSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setFilteredOnlyDaoFieldMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link FilteredOnlyDaoFieldDao} to a(n) {@link FilteredOnlyDaoField} and sets the result to the corresponding reference property at
	 * the parent
	 *
	 * @param filteredOnlyDaoField the source object which should be converted
	 * @param parent               the parent of converted result
	 * @return an equivalent new created {@link FilteredOnlyDaoField}
	 */
	public static FilteredOnlyDaoField convertToFilteredOnlyDaoField(FilteredOnlyDaoFieldDao filteredOnlyDaoField, SomeFilteringOwner parent) {
		return convertToFilteredOnlyDaoField(filteredOnlyDaoField, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FilteredOnlyDaoFieldDao} to a(n) {@link FilteredOnlyDaoField} and sets the result to the corresponding reference property at
	 * the parent
	 *
	 * @param filteredOnlyDaoField the source object which should be converted
	 * @param parent               the parent of converted result
	 * @param mappedObjects        map which contains already mapped objects. If an identification of {@code filteredOnlyDaoField} is contained, the found
	 *                             {@link FilteredOnlyDaoField} will be returned
	 * @return an equivalent new created {@link FilteredOnlyDaoField} or the found one from the given map
	 */
	public static FilteredOnlyDaoField convertToFilteredOnlyDaoField(FilteredOnlyDaoFieldDao filteredOnlyDaoField, SomeFilteringOwner parent
			, Map<String, IIdentifiable> mappedObjects) {
		FilteredOnlyDaoField result = convertToFilteredOnlyDaoField(filteredOnlyDaoField, mappedObjects);
		if (result != null) {
			switch (filteredOnlyDaoField.getSomeEnumOnlyDaoField()) {
				case ENUM_VALUE_A:
					parent.addFilterDaoA(result);
					break;
				case ENUM_VALUE_B:
					parent.addFilterDaoB(result);
					break;
				case ENUM_VALUE_C:
					parent.addFilterDaoC(result);
					break;
				default:
					log.error("There is not any mapping rule for filteredOnlyDaoField of type {}", filteredOnlyDaoField.getSomeEnumOnlyDaoField());
			}
		}
		return result;
	}

	/**
	 * Converts a(n) {@link FilteredOnlyDaoField} to a(n) {@link FilteredOnlyDaoFieldDao}
	 *
	 * @param filteredOnlyDaoField the source object which should be converted
	 * @param someEnumOnlyDaoField value to map between domain multiple {@link java.util.Collection}s and dao aggregated {@link java.util.Collection}
	 * @return an equivalent new created {@link FilteredOnlyDaoFieldDao}
	 */
	public static FilteredOnlyDaoFieldDao convertToFilteredOnlyDaoFieldDao(FilteredOnlyDaoField filteredOnlyDaoField, AnyEnumType someEnumOnlyDaoField) {
		return convertToFilteredOnlyDaoFieldDao(filteredOnlyDaoField, someEnumOnlyDaoField, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FilteredOnlyDaoField} to a(n) {@link FilteredOnlyDaoFieldDao} and sets the result to the corresponding reference property at
	 * the parent
	 *
	 * @param filteredOnlyDaoField the source object which should be converted
	 * @param parent               the parent of converted result
	 * @param someEnumOnlyDaoField value to map between domain multiple {@link java.util.Collection}s and dao aggregated {@link java.util.Collection}
	 * @return an equivalent new created {@link FilteredOnlyDaoFieldDao}
	 */
	public static FilteredOnlyDaoFieldDao convertToFilteredOnlyDaoFieldDao(FilteredOnlyDaoField filteredOnlyDaoField, SomeFilteringOwnerDao parent
			, AnyEnumType someEnumOnlyDaoField) {
		return convertToFilteredOnlyDaoFieldDao(filteredOnlyDaoField, parent, someEnumOnlyDaoField, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FilteredOnlyDaoField} to a(n) {@link FilteredOnlyDaoFieldDao} and sets the result to the corresponding reference property at
	 * the parent
	 *
	 * @param filteredOnlyDaoField the source object which should be converted
	 * @param parent               the parent of converted result
	 * @param someEnumOnlyDaoField value to map between domain multiple {@link java.util.Collection}s and dao aggregated {@link java.util.Collection}
	 * @param mappedObjects        map which contains already mapped objects. If an identification of {@code filteredOnlyDaoField} is contained, the found
	 *                             {@link FilteredOnlyDaoFieldDao} will be returned
	 * @return an equivalent new created {@link FilteredOnlyDaoFieldDao} or the found one from the given map
	 */
	public static FilteredOnlyDaoFieldDao convertToFilteredOnlyDaoFieldDao(FilteredOnlyDaoField filteredOnlyDaoField, SomeFilteringOwnerDao parent
			, AnyEnumType someEnumOnlyDaoField, Map<String, IIdentifiableDao> mappedObjects) {
		FilteredOnlyDaoFieldDao result = convertToFilteredOnlyDaoFieldDao(filteredOnlyDaoField, someEnumOnlyDaoField, mappedObjects);
		if (result != null) {
			result.setParentSomeFilteringOwner(parent);
			parent.getAggFilteredOnlyDaoField().add(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link FilteredOnlyDaoField} to a(n) {@link FilteredOnlyDaoFieldDao}
	 *
	 * @param filteredOnlyDaoField the source object which should be converted
	 * @param someEnumOnlyDaoField value to map between domain multiple {@link java.util.Collection}s and dao aggregated {@link java.util.Collection}
	 * @param mappedObjects        map which contains already mapped objects. If an identification of {@code filteredOnlyDaoField} is contained, the found
	 *                             {@link FilteredOnlyDaoFieldDao} will be returned
	 * @return an equivalent new created {@link FilteredOnlyDaoFieldDao} or the found one from the given map
	 */
	public static FilteredOnlyDaoFieldDao convertToFilteredOnlyDaoFieldDao(FilteredOnlyDaoField filteredOnlyDaoField, AnyEnumType someEnumOnlyDaoField
			, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(filteredOnlyDaoField, mappedObjects, DaoObjectFactory::createFilteredOnlyDaoFieldDao, (domain, dao) -> getInstance().setFilteredOnlyDaoFieldDaoValues(domain, dao, someEnumOnlyDaoField)
				, (domain, dao) -> getInstance().setFilteredOnlyDaoFieldDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setFilteredOnlyDaoFieldDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * Converts a(n) {@link FilteredOnlyDaoFieldNotOwnerDao} to a(n) {@link FilteredOnlyDaoFieldNotOwner}
	 *
	 * @param filteredOnlyDaoFieldNotOwner the source object which should be converted
	 * @return an equivalent new created {@link FilteredOnlyDaoFieldNotOwner}
	 */
	public static FilteredOnlyDaoFieldNotOwner convertToFilteredOnlyDaoFieldNotOwner(FilteredOnlyDaoFieldNotOwnerDao filteredOnlyDaoFieldNotOwner) {
		return convertToFilteredOnlyDaoFieldNotOwner(filteredOnlyDaoFieldNotOwner, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FilteredOnlyDaoFieldNotOwnerDao} to a(n) {@link FilteredOnlyDaoFieldNotOwner}
	 *
	 * @param filteredOnlyDaoFieldNotOwner the source object which should be converted
	 * @param mappedObjects                map which contains already mapped objects. If an identification of {@code filteredOnlyDaoFieldNotOwner} is
	 *                                     contained, the found {@link FilteredOnlyDaoFieldNotOwner} will be returned
	 * @return an equivalent new created {@link FilteredOnlyDaoFieldNotOwner} or the found one from the given map
	 */
	public static FilteredOnlyDaoFieldNotOwner convertToFilteredOnlyDaoFieldNotOwner(FilteredOnlyDaoFieldNotOwnerDao filteredOnlyDaoFieldNotOwner
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(filteredOnlyDaoFieldNotOwner, mappedObjects, DomainObjectFactory::createFilteredOnlyDaoFieldNotOwner, (dao, domain) -> getInstance().setFilteredOnlyDaoFieldNotOwnerValues(dao, domain)
				, (dao, domain) -> getInstance().setFilteredOnlyDaoFieldNotOwnerSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setFilteredOnlyDaoFieldNotOwnerMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link FilteredOnlyDaoFieldNotOwnerDao} to a(n) {@link FilteredOnlyDaoFieldNotOwner} and sets the result to the corresponding
	 * reference property at the parent
	 *
	 * @param filteredOnlyDaoFieldNotOwner the source object which should be converted
	 * @param parent                       the parent of converted result
	 * @return an equivalent new created {@link FilteredOnlyDaoFieldNotOwner}
	 */
	public static FilteredOnlyDaoFieldNotOwner convertToFilteredOnlyDaoFieldNotOwner(FilteredOnlyDaoFieldNotOwnerDao filteredOnlyDaoFieldNotOwner
			, SomeFilteringOwner parent) {
		return convertToFilteredOnlyDaoFieldNotOwner(filteredOnlyDaoFieldNotOwner, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FilteredOnlyDaoFieldNotOwnerDao} to a(n) {@link FilteredOnlyDaoFieldNotOwner} and sets the result to the corresponding
	 * reference property at the parent
	 *
	 * @param filteredOnlyDaoFieldNotOwner the source object which should be converted
	 * @param parent                       the parent of converted result
	 * @param mappedObjects                map which contains already mapped objects. If an identification of {@code filteredOnlyDaoFieldNotOwner} is
	 *                                     contained, the found {@link FilteredOnlyDaoFieldNotOwner} will be returned
	 * @return an equivalent new created {@link FilteredOnlyDaoFieldNotOwner} or the found one from the given map
	 */
	public static FilteredOnlyDaoFieldNotOwner convertToFilteredOnlyDaoFieldNotOwner(FilteredOnlyDaoFieldNotOwnerDao filteredOnlyDaoFieldNotOwner
			, SomeFilteringOwner parent, Map<String, IIdentifiable> mappedObjects) {
		FilteredOnlyDaoFieldNotOwner result = convertToFilteredOnlyDaoFieldNotOwner(filteredOnlyDaoFieldNotOwner, mappedObjects);
		if (result != null) {
			switch (filteredOnlyDaoFieldNotOwner.getSomeEnumOnlyDaoFieldNotOwner()) {
				case ENUM_VALUE_A:
					parent.addFilterDaoNotOwnerA(result);
					break;
				case ENUM_VALUE_B:
					parent.addFilterDaoNotOwnerB(result);
					break;
				case ENUM_VALUE_C:
					parent.addFilterDaoNotOwnerC(result);
					break;
				default:
					log.error("There is not any mapping rule for filteredOnlyDaoFieldNotOwner of type {}", filteredOnlyDaoFieldNotOwner.getSomeEnumOnlyDaoFieldNotOwner());
			}
		}
		return result;
	}

	/**
	 * Converts a(n) {@link FilteredOnlyDaoFieldNotOwner} to a(n) {@link FilteredOnlyDaoFieldNotOwnerDao}
	 *
	 * @param filteredOnlyDaoFieldNotOwner the source object which should be converted
	 * @param someEnumOnlyDaoFieldNotOwner value to map between domain multiple {@link java.util.Collection}s and dao aggregated
	 *                                     {@link java.util.Collection}
	 * @return an equivalent new created {@link FilteredOnlyDaoFieldNotOwnerDao}
	 */
	public static FilteredOnlyDaoFieldNotOwnerDao convertToFilteredOnlyDaoFieldNotOwnerDao(FilteredOnlyDaoFieldNotOwner filteredOnlyDaoFieldNotOwner
			, AnyEnumType someEnumOnlyDaoFieldNotOwner) {
		return convertToFilteredOnlyDaoFieldNotOwnerDao(filteredOnlyDaoFieldNotOwner, someEnumOnlyDaoFieldNotOwner, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FilteredOnlyDaoFieldNotOwner} to a(n) {@link FilteredOnlyDaoFieldNotOwnerDao} and sets the result to the corresponding
	 * reference property at the parent
	 *
	 * @param filteredOnlyDaoFieldNotOwner the source object which should be converted
	 * @param parent                       the parent of converted result
	 * @param someEnumOnlyDaoFieldNotOwner value to map between domain multiple {@link java.util.Collection}s and dao aggregated
	 *                                     {@link java.util.Collection}
	 * @return an equivalent new created {@link FilteredOnlyDaoFieldNotOwnerDao}
	 */
	public static FilteredOnlyDaoFieldNotOwnerDao convertToFilteredOnlyDaoFieldNotOwnerDao(FilteredOnlyDaoFieldNotOwner filteredOnlyDaoFieldNotOwner
			, SomeFilteringOwnerDao parent, AnyEnumType someEnumOnlyDaoFieldNotOwner) {
		return convertToFilteredOnlyDaoFieldNotOwnerDao(filteredOnlyDaoFieldNotOwner, parent, someEnumOnlyDaoFieldNotOwner, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FilteredOnlyDaoFieldNotOwner} to a(n) {@link FilteredOnlyDaoFieldNotOwnerDao} and sets the result to the corresponding
	 * reference property at the parent
	 *
	 * @param filteredOnlyDaoFieldNotOwner the source object which should be converted
	 * @param parent                       the parent of converted result
	 * @param someEnumOnlyDaoFieldNotOwner value to map between domain multiple {@link java.util.Collection}s and dao aggregated
	 *                                     {@link java.util.Collection}
	 * @param mappedObjects                map which contains already mapped objects. If an identification of {@code filteredOnlyDaoFieldNotOwner} is
	 *                                     contained, the found {@link FilteredOnlyDaoFieldNotOwnerDao} will be returned
	 * @return an equivalent new created {@link FilteredOnlyDaoFieldNotOwnerDao} or the found one from the given map
	 */
	public static FilteredOnlyDaoFieldNotOwnerDao convertToFilteredOnlyDaoFieldNotOwnerDao(FilteredOnlyDaoFieldNotOwner filteredOnlyDaoFieldNotOwner
			, SomeFilteringOwnerDao parent, AnyEnumType someEnumOnlyDaoFieldNotOwner, Map<String, IIdentifiableDao> mappedObjects) {
		FilteredOnlyDaoFieldNotOwnerDao result = convertToFilteredOnlyDaoFieldNotOwnerDao(filteredOnlyDaoFieldNotOwner, someEnumOnlyDaoFieldNotOwner, mappedObjects);
		if (result != null) {
			SomeFilteringOwnerToFilteredOnlyDaoFieldNotOwnerDao connectionTable = DaoObjectFactory.createSomeFilteringOwnerToFilteredOnlyDaoFieldNotOwnerDao();
			connectionTable.setFilteredOnlyDaoFieldNotOwner(result);
			connectionTable.setSomeFilteringOwner(parent);
			parent.getAggFilteredOnlyDaoFieldNotOwner().add(connectionTable);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link FilteredOnlyDaoFieldNotOwner} to a(n) {@link FilteredOnlyDaoFieldNotOwnerDao}
	 *
	 * @param filteredOnlyDaoFieldNotOwner the source object which should be converted
	 * @param someEnumOnlyDaoFieldNotOwner value to map between domain multiple {@link java.util.Collection}s and dao aggregated
	 *                                     {@link java.util.Collection}
	 * @param mappedObjects                map which contains already mapped objects. If an identification of {@code filteredOnlyDaoFieldNotOwner} is
	 *                                     contained, the found {@link FilteredOnlyDaoFieldNotOwnerDao} will be returned
	 * @return an equivalent new created {@link FilteredOnlyDaoFieldNotOwnerDao} or the found one from the given map
	 */
	public static FilteredOnlyDaoFieldNotOwnerDao convertToFilteredOnlyDaoFieldNotOwnerDao(FilteredOnlyDaoFieldNotOwner filteredOnlyDaoFieldNotOwner
			, AnyEnumType someEnumOnlyDaoFieldNotOwner, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(filteredOnlyDaoFieldNotOwner, mappedObjects, DaoObjectFactory::createFilteredOnlyDaoFieldNotOwnerDao, (domain, dao) -> getInstance().setFilteredOnlyDaoFieldNotOwnerDaoValues(domain, dao, someEnumOnlyDaoFieldNotOwner)
				, (domain, dao) -> getInstance().setFilteredOnlyDaoFieldNotOwnerDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setFilteredOnlyDaoFieldNotOwnerDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SomeDifferentFilteringNotOwnerDao} to a(n) {@link SomeDifferentFilteringNotOwner}
	 *
	 * @param someDifferentFilteringNotOwner the source object which should be converted
	 * @param includeChildren                {@code true} if all references should also be mapped. {@code false} if only those references should be mapped
	 *                                       which are not of type {@link java.util.Collection}
	 * @return an equivalent new created {@link SomeDifferentFilteringNotOwner}
	 */
	public static SomeDifferentFilteringNotOwner convertToSomeDifferentFilteringNotOwner(SomeDifferentFilteringNotOwnerDao someDifferentFilteringNotOwner
			, boolean includeChildren) {
		return convertToSomeDifferentFilteringNotOwner(someDifferentFilteringNotOwner, includeChildren, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SomeDifferentFilteringNotOwnerDao} to a(n) {@link SomeDifferentFilteringNotOwner}
	 *
	 * @param someDifferentFilteringNotOwner the source object which should be converted
	 * @param includeChildren                {@code true} if all references should also be mapped. {@code false} if only those references should be mapped
	 *                                       which are not of type {@link java.util.Collection}
	 * @param mappedObjects                  map which contains already mapped objects. If an identification of {@code someDifferentFilteringNotOwner} is
	 *                                       contained, the found {@link SomeDifferentFilteringNotOwner} will be returned
	 * @return an equivalent new created {@link SomeDifferentFilteringNotOwner} or the found one from the given map
	 */
	public static SomeDifferentFilteringNotOwner convertToSomeDifferentFilteringNotOwner(SomeDifferentFilteringNotOwnerDao someDifferentFilteringNotOwner
			, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(someDifferentFilteringNotOwner, mappedObjects, DomainObjectFactory::createSomeDifferentFilteringNotOwner, (dao, domain) -> getInstance().setSomeDifferentFilteringNotOwnerValues(dao, domain)
				, (dao, domain) -> getInstance().setSomeDifferentFilteringNotOwnerSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setSomeDifferentFilteringNotOwnerMultiReferences(dao, domain, includeChildren, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SomeDifferentFilteringNotOwnerDao} to a(n) {@link SomeDifferentFilteringNotOwner} and sets the result to the corresponding
	 * reference property at the parent
	 *
	 * @param someDifferentFilteringNotOwner the source object which should be converted
	 * @param includeChildren                {@code true} if all references should also be mapped. {@code false} if only those references should be mapped
	 *                                       which are not of type {@link java.util.Collection}
	 * @param parent                         the parent of converted result
	 * @return an equivalent new created {@link SomeDifferentFilteringNotOwner}
	 */
	public static SomeDifferentFilteringNotOwner convertToSomeDifferentFilteringNotOwner(SomeDifferentFilteringNotOwnerDao someDifferentFilteringNotOwner
			, boolean includeChildren, Root parent) {
		return convertToSomeDifferentFilteringNotOwner(someDifferentFilteringNotOwner, includeChildren, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SomeDifferentFilteringNotOwnerDao} to a(n) {@link SomeDifferentFilteringNotOwner} and sets the result to the corresponding
	 * reference property at the parent
	 *
	 * @param someDifferentFilteringNotOwner the source object which should be converted
	 * @param includeChildren                {@code true} if all references should also be mapped. {@code false} if only those references should be mapped
	 *                                       which are not of type {@link java.util.Collection}
	 * @param parent                         the parent of converted result
	 * @param mappedObjects                  map which contains already mapped objects. If an identification of {@code someDifferentFilteringNotOwner} is
	 *                                       contained, the found {@link SomeDifferentFilteringNotOwner} will be returned
	 * @return an equivalent new created {@link SomeDifferentFilteringNotOwner} or the found one from the given map
	 */
	public static SomeDifferentFilteringNotOwner convertToSomeDifferentFilteringNotOwner(SomeDifferentFilteringNotOwnerDao someDifferentFilteringNotOwner
			, boolean includeChildren, Root parent, Map<String, IIdentifiable> mappedObjects) {
		SomeDifferentFilteringNotOwner result = convertToSomeDifferentFilteringNotOwner(someDifferentFilteringNotOwner, includeChildren, mappedObjects);
		if (result != null) {
			parent.setNonOwnerFiltering(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SomeDifferentFilteringNotOwner} to a(n) {@link SomeDifferentFilteringNotOwnerDao}
	 *
	 * @param someDifferentFilteringNotOwner the source object which should be converted
	 * @param includeChildren                {@code true} if all references should also be mapped. {@code false} if only those references should be mapped
	 *                                       which are not of type {@link java.util.Collection}
	 * @return an equivalent new created {@link SomeDifferentFilteringNotOwnerDao}
	 */
	public static SomeDifferentFilteringNotOwnerDao convertToSomeDifferentFilteringNotOwnerDao(SomeDifferentFilteringNotOwner someDifferentFilteringNotOwner
			, boolean includeChildren) {
		return convertToSomeDifferentFilteringNotOwnerDao(someDifferentFilteringNotOwner, includeChildren, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SomeDifferentFilteringNotOwner} to a(n) {@link SomeDifferentFilteringNotOwnerDao}
	 *
	 * @param someDifferentFilteringNotOwner the source object which should be converted
	 * @param includeChildren                {@code true} if all references should also be mapped. {@code false} if only those references should be mapped
	 *                                       which are not of type {@link java.util.Collection}
	 * @param mappedObjects                  map which contains already mapped objects. If an identification of {@code someDifferentFilteringNotOwner} is
	 *                                       contained, the found {@link SomeDifferentFilteringNotOwnerDao} will be returned
	 * @return an equivalent new created {@link SomeDifferentFilteringNotOwnerDao} or the found one from the given map
	 */
	public static SomeDifferentFilteringNotOwnerDao convertToSomeDifferentFilteringNotOwnerDao(SomeDifferentFilteringNotOwner someDifferentFilteringNotOwner
			, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(someDifferentFilteringNotOwner, mappedObjects, DaoObjectFactory::createSomeDifferentFilteringNotOwnerDao, (domain, dao) -> getInstance().setSomeDifferentFilteringNotOwnerDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setSomeDifferentFilteringNotOwnerDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setSomeDifferentFilteringNotOwnerDaoMultiReferences(domain, dao, includeChildren, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SomeDifferentFilteringNotOwner} to a(n) {@link SomeDifferentFilteringNotOwnerDao} and sets the result to the corresponding
	 * reference property at the parent
	 *
	 * @param someDifferentFilteringNotOwner the source object which should be converted
	 * @param includeChildren                {@code true} if all references should also be mapped. {@code false} if only those references should be mapped
	 *                                       which are not of type {@link java.util.Collection}
	 * @param parent                         the parent of converted result
	 * @return an equivalent new created {@link SomeDifferentFilteringNotOwnerDao}
	 */
	public static SomeDifferentFilteringNotOwnerDao convertToSomeDifferentFilteringNotOwnerDao(SomeDifferentFilteringNotOwner someDifferentFilteringNotOwner
			, boolean includeChildren, RootDao parent) {
		return convertToSomeDifferentFilteringNotOwnerDao(someDifferentFilteringNotOwner, includeChildren, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SomeDifferentFilteringNotOwner} to a(n) {@link SomeDifferentFilteringNotOwnerDao} and sets the result to the corresponding
	 * reference property at the parent
	 *
	 * @param someDifferentFilteringNotOwner the source object which should be converted
	 * @param includeChildren                {@code true} if all references should also be mapped. {@code false} if only those references should be mapped
	 *                                       which are not of type {@link java.util.Collection}
	 * @param parent                         the parent of converted result
	 * @param mappedObjects                  map which contains already mapped objects. If an identification of {@code someDifferentFilteringNotOwner} is
	 *                                       contained, the found {@link SomeDifferentFilteringNotOwnerDao} will be returned
	 * @return an equivalent new created {@link SomeDifferentFilteringNotOwnerDao} or the found one from the given map
	 */
	public static SomeDifferentFilteringNotOwnerDao convertToSomeDifferentFilteringNotOwnerDao(SomeDifferentFilteringNotOwner someDifferentFilteringNotOwner
			, boolean includeChildren, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		SomeDifferentFilteringNotOwnerDao result = convertToSomeDifferentFilteringNotOwnerDao(someDifferentFilteringNotOwner, includeChildren, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.setNonOwnerFiltering(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SomeFilteringOwnerDao} to a(n) {@link SomeFilteringOwner}
	 *
	 * @param someFilteringOwner the source object which should be converted
	 * @param includeChildren    {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are
	 *                           not of type {@link java.util.Collection}
	 * @return an equivalent new created {@link SomeFilteringOwner}
	 */
	public static SomeFilteringOwner convertToSomeFilteringOwner(SomeFilteringOwnerDao someFilteringOwner, boolean includeChildren) {
		return convertToSomeFilteringOwner(someFilteringOwner, includeChildren, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SomeFilteringOwnerDao} to a(n) {@link SomeFilteringOwner}
	 *
	 * @param someFilteringOwner the source object which should be converted
	 * @param includeChildren    {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are
	 *                           not of type {@link java.util.Collection}
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code someFilteringOwner} is contained, the found
	 *                           {@link SomeFilteringOwner} will be returned
	 * @return an equivalent new created {@link SomeFilteringOwner} or the found one from the given map
	 */
	public static SomeFilteringOwner convertToSomeFilteringOwner(SomeFilteringOwnerDao someFilteringOwner, boolean includeChildren
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(someFilteringOwner, mappedObjects, DomainObjectFactory::createSomeFilteringOwner, (dao, domain) -> getInstance().setSomeFilteringOwnerValues(dao, domain)
				, (dao, domain) -> getInstance().setSomeFilteringOwnerSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setSomeFilteringOwnerMultiReferences(dao, domain, includeChildren, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SomeFilteringOwnerDao} to a(n) {@link SomeFilteringOwner} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param someFilteringOwner the source object which should be converted
	 * @param includeChildren    {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are
	 *                           not of type {@link java.util.Collection}
	 * @param parent             the parent of converted result
	 * @return an equivalent new created {@link SomeFilteringOwner}
	 */
	public static SomeFilteringOwner convertToSomeFilteringOwner(SomeFilteringOwnerDao someFilteringOwner, boolean includeChildren, Root parent) {
		return convertToSomeFilteringOwner(someFilteringOwner, includeChildren, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SomeFilteringOwnerDao} to a(n) {@link SomeFilteringOwner} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param someFilteringOwner the source object which should be converted
	 * @param includeChildren    {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are
	 *                           not of type {@link java.util.Collection}
	 * @param parent             the parent of converted result
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code someFilteringOwner} is contained, the found
	 *                           {@link SomeFilteringOwner} will be returned
	 * @return an equivalent new created {@link SomeFilteringOwner} or the found one from the given map
	 */
	public static SomeFilteringOwner convertToSomeFilteringOwner(SomeFilteringOwnerDao someFilteringOwner, boolean includeChildren, Root parent
			, Map<String, IIdentifiable> mappedObjects) {
		SomeFilteringOwner result = convertToSomeFilteringOwner(someFilteringOwner, includeChildren, mappedObjects);
		if (result != null) {
			parent.setFiltering(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SomeFilteringOwner} to a(n) {@link SomeFilteringOwnerDao}
	 *
	 * @param someFilteringOwner the source object which should be converted
	 * @param includeChildren    {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are
	 *                           not of type {@link java.util.Collection}
	 * @return an equivalent new created {@link SomeFilteringOwnerDao}
	 */
	public static SomeFilteringOwnerDao convertToSomeFilteringOwnerDao(SomeFilteringOwner someFilteringOwner, boolean includeChildren) {
		return convertToSomeFilteringOwnerDao(someFilteringOwner, includeChildren, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SomeFilteringOwner} to a(n) {@link SomeFilteringOwnerDao}
	 *
	 * @param someFilteringOwner the source object which should be converted
	 * @param includeChildren    {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are
	 *                           not of type {@link java.util.Collection}
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code someFilteringOwner} is contained, the found
	 *                           {@link SomeFilteringOwnerDao} will be returned
	 * @return an equivalent new created {@link SomeFilteringOwnerDao} or the found one from the given map
	 */
	public static SomeFilteringOwnerDao convertToSomeFilteringOwnerDao(SomeFilteringOwner someFilteringOwner, boolean includeChildren
			, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(someFilteringOwner, mappedObjects, DaoObjectFactory::createSomeFilteringOwnerDao, (domain, dao) -> getInstance().setSomeFilteringOwnerDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setSomeFilteringOwnerDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setSomeFilteringOwnerDaoMultiReferences(domain, dao, includeChildren, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SomeFilteringOwner} to a(n) {@link SomeFilteringOwnerDao} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param someFilteringOwner the source object which should be converted
	 * @param includeChildren    {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are
	 *                           not of type {@link java.util.Collection}
	 * @param parent             the parent of converted result
	 * @return an equivalent new created {@link SomeFilteringOwnerDao}
	 */
	public static SomeFilteringOwnerDao convertToSomeFilteringOwnerDao(SomeFilteringOwner someFilteringOwner, boolean includeChildren, RootDao parent) {
		return convertToSomeFilteringOwnerDao(someFilteringOwner, includeChildren, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SomeFilteringOwner} to a(n) {@link SomeFilteringOwnerDao} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param someFilteringOwner the source object which should be converted
	 * @param includeChildren    {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are
	 *                           not of type {@link java.util.Collection}
	 * @param parent             the parent of converted result
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code someFilteringOwner} is contained, the found
	 *                           {@link SomeFilteringOwnerDao} will be returned
	 * @return an equivalent new created {@link SomeFilteringOwnerDao} or the found one from the given map
	 */
	public static SomeFilteringOwnerDao convertToSomeFilteringOwnerDao(SomeFilteringOwner someFilteringOwner, boolean includeChildren, RootDao parent
			, Map<String, IIdentifiableDao> mappedObjects) {
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
			instance = AccessMapperFactory.createFilteringAccessMapper();
		}
		return instance;
	}

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setFilteredDaoMultiReferences(Filtered domain, FilteredDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setFilteredDaoSingleReferences(Filtered domain, FilteredDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	protected void setFilteredDaoValues(Filtered domain, FilteredDao dao) {
		dao.setDescription(domain.getDescription());
		dao.setSomeEnum(domain.getSomeEnum());
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setFilteredMultiReferences(FilteredDao dao, Filtered domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setFilteredNotOwnerDaoMultiReferences(FilteredNotOwner domain, FilteredNotOwnerDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setFilteredNotOwnerDaoSingleReferences(FilteredNotOwner domain, FilteredNotOwnerDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	protected void setFilteredNotOwnerDaoValues(FilteredNotOwner domain, FilteredNotOwnerDao dao) {
		dao.setDescriptionNotOwner(domain.getDescriptionNotOwner());
		dao.setSomeEnumNotOwner(domain.getSomeEnumNotOwner());
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setFilteredNotOwnerMultiReferences(FilteredNotOwnerDao dao, FilteredNotOwner domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setFilteredNotOwnerSingleReferences(FilteredNotOwnerDao dao, FilteredNotOwner domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setFilteredNotOwnerValues(FilteredNotOwnerDao dao, FilteredNotOwner domain) {
		domain.setDescriptionNotOwner(dao.getDescriptionNotOwner());
		domain.setSomeEnumNotOwner(dao.getSomeEnumNotOwner());
	}

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setFilteredOnlyDaoFieldDaoMultiReferences(FilteredOnlyDaoField domain, FilteredOnlyDaoFieldDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setFilteredOnlyDaoFieldDaoSingleReferences(FilteredOnlyDaoField domain, FilteredOnlyDaoFieldDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain               source of the given values
	 * @param dao                  object where to set the values
	 * @param someEnumOnlyDaoField value to map between domain multiple {@link java.util.Collection}s and dao aggregated {@link java.util.Collection}
	 */
	protected void setFilteredOnlyDaoFieldDaoValues(FilteredOnlyDaoField domain, FilteredOnlyDaoFieldDao dao, AnyEnumType someEnumOnlyDaoField) {
		dao.setDescriptionOnlyDaoField(domain.getDescriptionOnlyDaoField());
		dao.setSomeEnumOnlyDaoField(someEnumOnlyDaoField);
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setFilteredOnlyDaoFieldMultiReferences(FilteredOnlyDaoFieldDao dao, FilteredOnlyDaoField domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setFilteredOnlyDaoFieldNotOwnerDaoMultiReferences(FilteredOnlyDaoFieldNotOwner domain, FilteredOnlyDaoFieldNotOwnerDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setFilteredOnlyDaoFieldNotOwnerDaoSingleReferences(FilteredOnlyDaoFieldNotOwner domain, FilteredOnlyDaoFieldNotOwnerDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain                       source of the given values
	 * @param dao                          object where to set the values
	 * @param someEnumOnlyDaoFieldNotOwner value to map between domain multiple {@link java.util.Collection}s and dao aggregated
	 *                                     {@link java.util.Collection}
	 */
	protected void setFilteredOnlyDaoFieldNotOwnerDaoValues(FilteredOnlyDaoFieldNotOwner domain, FilteredOnlyDaoFieldNotOwnerDao dao
			, AnyEnumType someEnumOnlyDaoFieldNotOwner) {
		dao.setDescriptionOnlyDaoFieldNotOwner(domain.getDescriptionOnlyDaoFieldNotOwner());
		dao.setSomeEnumOnlyDaoFieldNotOwner(someEnumOnlyDaoFieldNotOwner);
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setFilteredOnlyDaoFieldNotOwnerMultiReferences(FilteredOnlyDaoFieldNotOwnerDao dao, FilteredOnlyDaoFieldNotOwner domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setFilteredOnlyDaoFieldNotOwnerSingleReferences(FilteredOnlyDaoFieldNotOwnerDao dao, FilteredOnlyDaoFieldNotOwner domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setFilteredOnlyDaoFieldNotOwnerValues(FilteredOnlyDaoFieldNotOwnerDao dao, FilteredOnlyDaoFieldNotOwner domain) {
		domain.setDescriptionOnlyDaoFieldNotOwner(dao.getDescriptionOnlyDaoFieldNotOwner());
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setFilteredOnlyDaoFieldSingleReferences(FilteredOnlyDaoFieldDao dao, FilteredOnlyDaoField domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setFilteredOnlyDaoFieldValues(FilteredOnlyDaoFieldDao dao, FilteredOnlyDaoField domain) {
		domain.setDescriptionOnlyDaoField(dao.getDescriptionOnlyDaoField());
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setFilteredSingleReferences(FilteredDao dao, Filtered domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setFilteredValues(FilteredDao dao, Filtered domain) {
		domain.setDescription(dao.getDescription());
		domain.setSomeEnum(dao.getSomeEnum());
	}

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain          source of the given references
	 * @param dao             object where to add the references
	 * @param includeChildren {@code true} if references should be mapped. Otherwise {@code false}
	 * @param mappedObjects   map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	protected void setSomeDifferentFilteringNotOwnerDaoMultiReferences(SomeDifferentFilteringNotOwner domain, SomeDifferentFilteringNotOwnerDao dao
			, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {
		dao.setAggFiltered(new ArrayList<>());
		if (includeChildren) {
			domain.getFilterA().forEach(arg ->
					FilteringAccessMapper.convertToFilteredDao(arg, dao, AnyEnumType.ENUM_VALUE_A, mappedObjects)
			);
			domain.getFilterB().forEach(arg ->
					FilteringAccessMapper.convertToFilteredDao(arg, dao, AnyEnumType.ENUM_VALUE_B, mappedObjects)
			);
			domain.getFilterC().forEach(arg ->
					FilteringAccessMapper.convertToFilteredDao(arg, dao, AnyEnumType.ENUM_VALUE_C, mappedObjects)
			);
		}
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSomeDifferentFilteringNotOwnerDaoSingleReferences(SomeDifferentFilteringNotOwner domain, SomeDifferentFilteringNotOwnerDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSomeDifferentFilteringNotOwnerDaoValues(SomeDifferentFilteringNotOwner domain, SomeDifferentFilteringNotOwnerDao dao) {
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao             source of the given references
	 * @param domain          object where to add the references
	 * @param includeChildren {@code true} if references should be mapped. Otherwise {@code false}
	 * @param mappedObjects   map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	protected void setSomeDifferentFilteringNotOwnerMultiReferences(SomeDifferentFilteringNotOwnerDao dao, SomeDifferentFilteringNotOwner domain
			, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		if (includeChildren) {
			dao.getAggFiltered().forEach(arg ->
					FilteringAccessMapper.convertToFiltered(arg.getFiltered(), domain, arg.getFilterAnyEnumType(), mappedObjects)
			);
		}
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSomeDifferentFilteringNotOwnerSingleReferences(SomeDifferentFilteringNotOwnerDao dao, SomeDifferentFilteringNotOwner domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSomeDifferentFilteringNotOwnerValues(SomeDifferentFilteringNotOwnerDao dao, SomeDifferentFilteringNotOwner domain) {
	}

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain          source of the given references
	 * @param dao             object where to add the references
	 * @param includeChildren {@code true} if references should be mapped. Otherwise {@code false}
	 * @param mappedObjects   map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	protected void setSomeFilteringOwnerDaoMultiReferences(SomeFilteringOwner domain, SomeFilteringOwnerDao dao, boolean includeChildren
			, Map<String, IIdentifiableDao> mappedObjects) {
		dao.setAggFilteredOnlyDaoFieldNotOwner(new ArrayList<>());
		dao.setAggFiltered(new ArrayList<>());
		dao.setAggFilteredOnlyDaoField(new ArrayList<>());
		dao.setAggFilteredNotOwner(new ArrayList<>());
		if (includeChildren) {
			domain.getFilterA().forEach(arg ->
					FilteringAccessMapper.convertToFilteredDao(arg, dao, mappedObjects)
			);
			domain.getFilterB().forEach(arg ->
					FilteringAccessMapper.convertToFilteredDao(arg, dao, mappedObjects)
			);
			domain.getFilterC().forEach(arg ->
					FilteringAccessMapper.convertToFilteredDao(arg, dao, mappedObjects)
			);
			domain.getFilterDaoA().forEach(arg ->
					FilteringAccessMapper.convertToFilteredOnlyDaoFieldDao(arg, dao, AnyEnumType.ENUM_VALUE_A, mappedObjects)
			);
			domain.getFilterDaoB().forEach(arg ->
					FilteringAccessMapper.convertToFilteredOnlyDaoFieldDao(arg, dao, AnyEnumType.ENUM_VALUE_B, mappedObjects)
			);
			domain.getFilterDaoC().forEach(arg ->
					FilteringAccessMapper.convertToFilteredOnlyDaoFieldDao(arg, dao, AnyEnumType.ENUM_VALUE_C, mappedObjects)
			);
			domain.getFilterNotOwnerA().forEach(arg ->
					FilteringAccessMapper.convertToFilteredNotOwnerDao(arg, dao, mappedObjects)
			);
			domain.getFilterNotOwnerB().forEach(arg ->
					FilteringAccessMapper.convertToFilteredNotOwnerDao(arg, dao, mappedObjects)
			);
			domain.getFilterNotOwnerC().forEach(arg ->
					FilteringAccessMapper.convertToFilteredNotOwnerDao(arg, dao, mappedObjects)
			);
			domain.getFilterDaoNotOwnerA().forEach(arg ->
					FilteringAccessMapper.convertToFilteredOnlyDaoFieldNotOwnerDao(arg, dao, AnyEnumType.ENUM_VALUE_A, mappedObjects)
			);
			domain.getFilterDaoNotOwnerB().forEach(arg ->
					FilteringAccessMapper.convertToFilteredOnlyDaoFieldNotOwnerDao(arg, dao, AnyEnumType.ENUM_VALUE_B, mappedObjects)
			);
			domain.getFilterDaoNotOwnerC().forEach(arg ->
					FilteringAccessMapper.convertToFilteredOnlyDaoFieldNotOwnerDao(arg, dao, AnyEnumType.ENUM_VALUE_C, mappedObjects)
			);
		}
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSomeFilteringOwnerDaoSingleReferences(SomeFilteringOwner domain, SomeFilteringOwnerDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSomeFilteringOwnerDaoValues(SomeFilteringOwner domain, SomeFilteringOwnerDao dao) {
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao             source of the given references
	 * @param domain          object where to add the references
	 * @param includeChildren {@code true} if references should be mapped. Otherwise {@code false}
	 * @param mappedObjects   map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	protected void setSomeFilteringOwnerMultiReferences(SomeFilteringOwnerDao dao, SomeFilteringOwner domain, boolean includeChildren
			, Map<String, IIdentifiable> mappedObjects) {
		if (includeChildren) {
			dao.getAggFilteredOnlyDaoFieldNotOwner().forEach(arg ->
					FilteringAccessMapper.convertToFilteredOnlyDaoFieldNotOwner(arg.getFilteredOnlyDaoFieldNotOwner(), domain, mappedObjects)
			);
			dao.getAggFiltered().forEach(arg ->
					FilteringAccessMapper.convertToFiltered(arg, domain, mappedObjects)
			);
			dao.getAggFilteredOnlyDaoField().forEach(arg ->
					FilteringAccessMapper.convertToFilteredOnlyDaoField(arg, domain, mappedObjects)
			);
			dao.getAggFilteredNotOwner().forEach(arg ->
					FilteringAccessMapper.convertToFilteredNotOwner(arg.getFilteredNotOwner(), domain, mappedObjects)
			);
		}
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSomeFilteringOwnerSingleReferences(SomeFilteringOwnerDao dao, SomeFilteringOwner domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSomeFilteringOwnerValues(SomeFilteringOwnerDao dao, SomeFilteringOwner domain) {
	}

}
