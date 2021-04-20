package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseAccessMapper;
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

@BaseAccessMapper
@Log4j2
public class FilteringAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static FilteringAccessMapper instance;

	public static Filtered convertToFiltered(FilteredDao filtered) {
		return convertToFiltered(filtered, new HashMap<>());
	}

	public static Filtered convertToFiltered(FilteredDao filtered, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(filtered, mappedObjects, DomainObjectFactory::createFiltered, (dao, domain) -> getInstance().setFilteredValues(dao, domain)
				, (dao, domain) -> getInstance().setFilteredSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setFilteredMultiReferences(dao, domain, mappedObjects));
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
		return convertToDao(filtered, mappedObjects, DaoObjectFactory::createFilteredDao, (domain, dao) -> getInstance().setFilteredDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setFilteredDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setFilteredDaoMultiReferences(domain, dao, mappedObjects));
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
		return convertToDomain(filteredOnlyDaoField, mappedObjects, DomainObjectFactory::createFilteredOnlyDaoField, (dao, domain) -> getInstance().setFilteredOnlyDaoFieldValues(dao, domain)
				, (dao, domain) -> getInstance().setFilteredOnlyDaoFieldSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setFilteredOnlyDaoFieldMultiReferences(dao, domain, mappedObjects));
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
		return convertToDao(filteredOnlyDaoField, mappedObjects, DaoObjectFactory::createFilteredOnlyDaoFieldDao, (domain, dao) -> getInstance().setFilteredOnlyDaoFieldDaoValues(domain, dao, someEnum)
				, (domain, dao) -> getInstance().setFilteredOnlyDaoFieldDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setFilteredOnlyDaoFieldDaoMultiReferences(domain, dao, mappedObjects));
	}

	public static SomeFilteringOwner convertToSomeFilteringOwner(SomeFilteringOwnerDao someFilteringOwner, boolean includeChildren) {
		return convertToSomeFilteringOwner(someFilteringOwner, includeChildren, new HashMap<>());
	}

	public static SomeFilteringOwner convertToSomeFilteringOwner(SomeFilteringOwnerDao someFilteringOwner, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(someFilteringOwner, mappedObjects, DomainObjectFactory::createSomeFilteringOwner, (dao, domain) -> getInstance().setSomeFilteringOwnerValues(dao, domain)
				, (dao, domain) -> getInstance().setSomeFilteringOwnerSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setSomeFilteringOwnerMultiReferences(dao, domain, includeChildren, mappedObjects));
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
		return convertToDao(someFilteringOwner, mappedObjects, DaoObjectFactory::createSomeFilteringOwnerDao, (domain, dao) -> getInstance().setSomeFilteringOwnerDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setSomeFilteringOwnerDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setSomeFilteringOwnerDaoMultiReferences(domain, dao, includeChildren, mappedObjects));
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
			instance = AccessMapperFactory.createFilteringAccessMapper();
		}
		return instance;
	}

	@SuppressWarnings("java:S1186")
	protected void setFilteredDaoMultiReferences(Filtered domain, FilteredDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setFilteredDaoSingleReferences(Filtered domain, FilteredDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	protected void setFilteredDaoValues(Filtered domain, FilteredDao dao) {
		dao.setDescription(domain.getDescription());
		dao.setSomeEnum(domain.getSomeEnum());
	}

	@SuppressWarnings("java:S1186")
	protected void setFilteredMultiReferences(FilteredDao dao, Filtered domain, Map<String, IIdentifiable> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setFilteredOnlyDaoFieldDaoMultiReferences(FilteredOnlyDaoField domain, FilteredOnlyDaoFieldDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setFilteredOnlyDaoFieldDaoSingleReferences(FilteredOnlyDaoField domain, FilteredOnlyDaoFieldDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	protected void setFilteredOnlyDaoFieldDaoValues(FilteredOnlyDaoField domain, FilteredOnlyDaoFieldDao dao, AnyEnumType someEnum) {
		dao.setDescription(domain.getDescription());
		dao.setSomeEnum(someEnum);
	}

	@SuppressWarnings("java:S1186")
	protected void setFilteredOnlyDaoFieldMultiReferences(FilteredOnlyDaoFieldDao dao, FilteredOnlyDaoField domain, Map<String, IIdentifiable> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setFilteredOnlyDaoFieldSingleReferences(FilteredOnlyDaoFieldDao dao, FilteredOnlyDaoField domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setFilteredOnlyDaoFieldValues(FilteredOnlyDaoFieldDao dao, FilteredOnlyDaoField domain) {
		domain.setDescription(dao.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setFilteredSingleReferences(FilteredDao dao, Filtered domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setFilteredValues(FilteredDao dao, Filtered domain) {
		domain.setDescription(dao.getDescription());
		domain.setSomeEnum(dao.getSomeEnum());
	}

	protected void setSomeFilteringOwnerDaoMultiReferences(SomeFilteringOwner domain, SomeFilteringOwnerDao dao, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {
		dao.setAggFiltereds(new ArrayList<>());
		dao.setAggFilteredOnlyDaoFields(new ArrayList<>());
		if (includeChildren) {
			domain.getFilterAs().forEach(arg ->
					FilteringAccessMapper.convertToFilteredDao(arg, dao, mappedObjects)
			);
			domain.getFilterBs().forEach(arg ->
					FilteringAccessMapper.convertToFilteredDao(arg, dao, mappedObjects)
			);
			domain.getFilterCs().forEach(arg ->
					FilteringAccessMapper.convertToFilteredDao(arg, dao, mappedObjects)
			);
			domain.getFilterDaoAs().forEach(arg ->
					FilteringAccessMapper.convertToFilteredOnlyDaoFieldDao(arg, dao, AnyEnumType.ENUM_VALUE_A, mappedObjects)
			);
			domain.getFilterDaoBs().forEach(arg ->
					FilteringAccessMapper.convertToFilteredOnlyDaoFieldDao(arg, dao, AnyEnumType.ENUM_VALUE_B, mappedObjects)
			);
			domain.getFilterDaoCs().forEach(arg ->
					FilteringAccessMapper.convertToFilteredOnlyDaoFieldDao(arg, dao, AnyEnumType.ENUM_VALUE_C, mappedObjects)
			);
		}
	}

	@SuppressWarnings("java:S1186")
	protected void setSomeFilteringOwnerDaoSingleReferences(SomeFilteringOwner domain, SomeFilteringOwnerDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setSomeFilteringOwnerDaoValues(SomeFilteringOwner domain, SomeFilteringOwnerDao dao) {
	}

	protected void setSomeFilteringOwnerMultiReferences(SomeFilteringOwnerDao dao, SomeFilteringOwner domain, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		if (includeChildren) {
			dao.getAggFiltereds().forEach(arg ->
					FilteringAccessMapper.convertToFiltered(arg, domain, mappedObjects)
			);
			dao.getAggFilteredOnlyDaoFields().forEach(arg ->
					FilteringAccessMapper.convertToFilteredOnlyDaoField(arg, domain, mappedObjects)
			);
		}
	}

	@SuppressWarnings("java:S1186")
	protected void setSomeFilteringOwnerSingleReferences(SomeFilteringOwnerDao dao, SomeFilteringOwner domain, Map<String, IIdentifiable> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setSomeFilteringOwnerValues(SomeFilteringOwnerDao dao, SomeFilteringOwner domain) {
	}

}
