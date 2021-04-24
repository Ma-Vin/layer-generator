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

/**
 * Generated class which provides methods to convert a data transport to a domain object of sub package <i>filtering<i> and the other way around
 */
@BaseTransportMapper
public class FilteringTransportMapper extends AbstractTransportMapper {

	/**
	 * singleton
	 */
	private static FilteringTransportMapper instance;

	/**
	 * Converts a(n) {@link FilteredDto} to a(n) {@link Filtered}
	 *
	 * @param filtered the source object which should be converted
	 * @return an equivalent new created {@link Filtered}
	 */
	public static Filtered convertToFiltered(FilteredDto filtered) {
		return convertToFiltered(filtered, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FilteredDto} to a(n) {@link Filtered}
	 *
	 * @param filtered      the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code filtered} is contained, the found {@link Filtered}
	 *                      will be returned
	 * @return an equivalent new created {@link Filtered} or the found one from the given map
	 */
	public static Filtered convertToFiltered(FilteredDto filtered, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(filtered, mappedObjects, DomainObjectFactory::createFiltered, (dto, domain) -> getInstance().setFilteredValues(dto, domain)
				, (dto, domain) -> getInstance().setFilteredSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link Filtered} to a(n) {@link FilteredDto}
	 *
	 * @param filtered the source object which should be converted
	 * @return an equivalent new created {@link FilteredDto}
	 */
	public static FilteredDto convertToFilteredDto(Filtered filtered) {
		return convertToFilteredDto(filtered, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link Filtered} to a(n) {@link FilteredDto}
	 *
	 * @param filtered      the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code filtered} is contained, the found
	 *                      {@link FilteredDto} will be returned
	 * @return an equivalent new created {@link FilteredDto} or the found one from the given map
	 */
	public static FilteredDto convertToFilteredDto(Filtered filtered, Map<String, ITransportable> mappedObjects) {
		return convertToDto(filtered, mappedObjects, DtoObjectFactory::createFilteredDto, (domain, dto) -> getInstance().setFilteredDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setFilteredDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link FilteredOnlyDaoFieldDto} to a(n) {@link FilteredOnlyDaoField}
	 *
	 * @param filteredOnlyDaoField the source object which should be converted
	 * @return an equivalent new created {@link FilteredOnlyDaoField}
	 */
	public static FilteredOnlyDaoField convertToFilteredOnlyDaoField(FilteredOnlyDaoFieldDto filteredOnlyDaoField) {
		return convertToFilteredOnlyDaoField(filteredOnlyDaoField, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FilteredOnlyDaoFieldDto} to a(n) {@link FilteredOnlyDaoField}
	 *
	 * @param filteredOnlyDaoField the source object which should be converted
	 * @param mappedObjects        map which contains already mapped objects. If an identification of {@code filteredOnlyDaoField} is contained, the found
	 *                             {@link FilteredOnlyDaoField} will be returned
	 * @return an equivalent new created {@link FilteredOnlyDaoField} or the found one from the given map
	 */
	public static FilteredOnlyDaoField convertToFilteredOnlyDaoField(FilteredOnlyDaoFieldDto filteredOnlyDaoField, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(filteredOnlyDaoField, mappedObjects, DomainObjectFactory::createFilteredOnlyDaoField, (dto, domain) -> getInstance().setFilteredOnlyDaoFieldValues(dto, domain)
				, (dto, domain) -> getInstance().setFilteredOnlyDaoFieldSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link FilteredOnlyDaoField} to a(n) {@link FilteredOnlyDaoFieldDto}
	 *
	 * @param filteredOnlyDaoField the source object which should be converted
	 * @return an equivalent new created {@link FilteredOnlyDaoFieldDto}
	 */
	public static FilteredOnlyDaoFieldDto convertToFilteredOnlyDaoFieldDto(FilteredOnlyDaoField filteredOnlyDaoField) {
		return convertToFilteredOnlyDaoFieldDto(filteredOnlyDaoField, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FilteredOnlyDaoField} to a(n) {@link FilteredOnlyDaoFieldDto}
	 *
	 * @param filteredOnlyDaoField the source object which should be converted
	 * @param mappedObjects        map which contains already mapped objects. If an identification of {@code filteredOnlyDaoField} is contained, the found
	 *                             {@link FilteredOnlyDaoFieldDto} will be returned
	 * @return an equivalent new created {@link FilteredOnlyDaoFieldDto} or the found one from the given map
	 */
	public static FilteredOnlyDaoFieldDto convertToFilteredOnlyDaoFieldDto(FilteredOnlyDaoField filteredOnlyDaoField, Map<String, ITransportable> mappedObjects) {
		return convertToDto(filteredOnlyDaoField, mappedObjects, DtoObjectFactory::createFilteredOnlyDaoFieldDto, (domain, dto) -> getInstance().setFilteredOnlyDaoFieldDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setFilteredOnlyDaoFieldDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link SomeFilteringOwnerDto} to a(n) {@link SomeFilteringOwner}
	 *
	 * @param someFilteringOwner the source object which should be converted
	 * @return an equivalent new created {@link SomeFilteringOwner}
	 */
	public static SomeFilteringOwner convertToSomeFilteringOwner(SomeFilteringOwnerDto someFilteringOwner) {
		return convertToSomeFilteringOwner(someFilteringOwner, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SomeFilteringOwnerDto} to a(n) {@link SomeFilteringOwner}
	 *
	 * @param someFilteringOwner the source object which should be converted
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code someFilteringOwner} is contained, the found
	 *                           {@link SomeFilteringOwner} will be returned
	 * @return an equivalent new created {@link SomeFilteringOwner} or the found one from the given map
	 */
	public static SomeFilteringOwner convertToSomeFilteringOwner(SomeFilteringOwnerDto someFilteringOwner, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(someFilteringOwner, mappedObjects, DomainObjectFactory::createSomeFilteringOwner, (dto, domain) -> getInstance().setSomeFilteringOwnerValues(dto, domain)
				, (dto, domain) -> getInstance().setSomeFilteringOwnerSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link SomeFilteringOwnerDto} to a(n) {@link SomeFilteringOwner} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param someFilteringOwner the source object which should be converted
	 * @param parent             the parent of converted result
	 * @return an equivalent new created {@link SomeFilteringOwner}
	 */
	public static SomeFilteringOwner convertToSomeFilteringOwner(SomeFilteringOwnerDto someFilteringOwner, Root parent) {
		return convertToSomeFilteringOwner(someFilteringOwner, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SomeFilteringOwnerDto} to a(n) {@link SomeFilteringOwner} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param someFilteringOwner the source object which should be converted
	 * @param parent             the parent of converted result
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code someFilteringOwner} is contained, the found
	 *                           {@link SomeFilteringOwner} will be returned
	 * @return an equivalent new created {@link SomeFilteringOwner} or the found one from the given map
	 */
	public static SomeFilteringOwner convertToSomeFilteringOwner(SomeFilteringOwnerDto someFilteringOwner, Root parent, Map<String, IIdentifiable> mappedObjects) {
		SomeFilteringOwner result = convertToSomeFilteringOwner(someFilteringOwner, mappedObjects);
		if (result != null) {
			parent.setFiltering(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SomeFilteringOwner} to a(n) {@link SomeFilteringOwnerDto}
	 *
	 * @param someFilteringOwner the source object which should be converted
	 * @return an equivalent new created {@link SomeFilteringOwnerDto}
	 */
	public static SomeFilteringOwnerDto convertToSomeFilteringOwnerDto(SomeFilteringOwner someFilteringOwner) {
		return convertToSomeFilteringOwnerDto(someFilteringOwner, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SomeFilteringOwner} to a(n) {@link SomeFilteringOwnerDto}
	 *
	 * @param someFilteringOwner the source object which should be converted
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code someFilteringOwner} is contained, the found
	 *                           {@link SomeFilteringOwnerDto} will be returned
	 * @return an equivalent new created {@link SomeFilteringOwnerDto} or the found one from the given map
	 */
	public static SomeFilteringOwnerDto convertToSomeFilteringOwnerDto(SomeFilteringOwner someFilteringOwner, Map<String, ITransportable> mappedObjects) {
		return convertToDto(someFilteringOwner, mappedObjects, DtoObjectFactory::createSomeFilteringOwnerDto, (domain, dto) -> getInstance().setSomeFilteringOwnerDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setSomeFilteringOwnerDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link SomeFilteringOwner} to a(n) {@link SomeFilteringOwnerDto} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param someFilteringOwner the source object which should be converted
	 * @param parent             the parent of converted result
	 * @return an equivalent new created {@link SomeFilteringOwnerDto}
	 */
	public static SomeFilteringOwnerDto convertToSomeFilteringOwnerDto(SomeFilteringOwner someFilteringOwner, RootDto parent) {
		return convertToSomeFilteringOwnerDto(someFilteringOwner, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SomeFilteringOwner} to a(n) {@link SomeFilteringOwnerDto} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param someFilteringOwner the source object which should be converted
	 * @param parent             the parent of converted result
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code someFilteringOwner} is contained, the found
	 *                           {@link SomeFilteringOwnerDto} will be returned
	 * @return an equivalent new created {@link SomeFilteringOwnerDto} or the found one from the given map
	 */
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

	@SuppressWarnings("java:S1186")
	protected void setFilteredDtoSingleReferences(Filtered domain, FilteredDto dto, Map<String, ITransportable> mappedObjects) {
	}

	protected void setFilteredDtoValues(Filtered domain, FilteredDto dto) {
		dto.setDescription(domain.getDescription());
		dto.setSomeEnum(domain.getSomeEnum());
	}

	@SuppressWarnings("java:S1186")
	protected void setFilteredOnlyDaoFieldDtoSingleReferences(FilteredOnlyDaoField domain, FilteredOnlyDaoFieldDto dto, Map<String, ITransportable> mappedObjects) {
	}

	protected void setFilteredOnlyDaoFieldDtoValues(FilteredOnlyDaoField domain, FilteredOnlyDaoFieldDto dto) {
		dto.setDescription(domain.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setFilteredOnlyDaoFieldSingleReferences(FilteredOnlyDaoFieldDto dto, FilteredOnlyDaoField domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setFilteredOnlyDaoFieldValues(FilteredOnlyDaoFieldDto dto, FilteredOnlyDaoField domain) {
		domain.setDescription(dto.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setFilteredSingleReferences(FilteredDto dto, Filtered domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setFilteredValues(FilteredDto dto, Filtered domain) {
		domain.setDescription(dto.getDescription());
		domain.setSomeEnum(dto.getSomeEnum());
	}

	@SuppressWarnings("java:S1186")
	protected void setSomeFilteringOwnerDtoSingleReferences(SomeFilteringOwner domain, SomeFilteringOwnerDto dto, Map<String, ITransportable> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setSomeFilteringOwnerDtoValues(SomeFilteringOwner domain, SomeFilteringOwnerDto dto) {
	}

	@SuppressWarnings("java:S1186")
	protected void setSomeFilteringOwnerSingleReferences(SomeFilteringOwnerDto dto, SomeFilteringOwner domain, Map<String, IIdentifiable> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setSomeFilteringOwnerValues(SomeFilteringOwnerDto dto, SomeFilteringOwner domain) {
	}

}
