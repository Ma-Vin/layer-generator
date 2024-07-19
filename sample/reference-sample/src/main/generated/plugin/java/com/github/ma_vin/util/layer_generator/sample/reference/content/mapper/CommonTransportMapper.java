package com.github.ma_vin.util.layer_generator.sample.reference.content.mapper;

import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseTransportMapper;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.DomainObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.SourceEntityFilter;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.SourceEntityFilterNotAtTarget;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.SourceEntityManyToMany;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.SourceEntityManyToOne;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.SourceEntityOneToMany;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.SourceEntityOneToOne;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.TargetEntityFilter;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.TargetEntityFilterNotAtTarget;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.TargetEntityManyToMany;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.TargetEntityOneToMany;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.TargetEntityOneToOne;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dto.DtoObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dto.ITransportable;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dto.SourceEntityFilterDto;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dto.SourceEntityFilterNotAtTargetDto;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dto.SourceEntityManyToManyDto;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dto.SourceEntityManyToOneDto;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dto.SourceEntityOneToManyDto;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dto.SourceEntityOneToOneDto;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dto.TargetEntityFilterDto;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dto.TargetEntityFilterNotAtTargetDto;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dto.TargetEntityManyToManyDto;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dto.TargetEntityOneToManyDto;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dto.TargetEntityOneToOneDto;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class which provides methods to convert a data transport to a domain object of sub package <i>null<i> and the other way around
 */
@BaseTransportMapper
public class CommonTransportMapper extends AbstractTransportMapper {

	/**
	 * singleton
	 */
	private static CommonTransportMapper instance;

	/**
	 * Converts a(n) {@link SourceEntityFilterDto} to a(n) {@link SourceEntityFilter}
	 *
	 * @param sourceEntityFilter the source object which should be converted
	 * @return an equivalent new created {@link SourceEntityFilter}
	 */
	public static SourceEntityFilter convertToSourceEntityFilter(SourceEntityFilterDto sourceEntityFilter) {
		return convertToSourceEntityFilter(sourceEntityFilter, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityFilterDto} to a(n) {@link SourceEntityFilter}
	 *
	 * @param sourceEntityFilter the source object which should be converted
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code sourceEntityFilter} is contained, the found
	 *                           {@link SourceEntityFilter} will be returned
	 * @return an equivalent new created {@link SourceEntityFilter} or the found one from the given map
	 */
	public static SourceEntityFilter convertToSourceEntityFilter(SourceEntityFilterDto sourceEntityFilter, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(sourceEntityFilter, mappedObjects, DomainObjectFactory::createSourceEntityFilter, (dto, domain) -> getInstance().setSourceEntityFilterValues(dto, domain)
				, (dto, domain) -> getInstance().setSourceEntityFilterSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link SourceEntityFilter} to a(n) {@link SourceEntityFilterDto}
	 *
	 * @param sourceEntityFilter the source object which should be converted
	 * @return an equivalent new created {@link SourceEntityFilterDto}
	 */
	public static SourceEntityFilterDto convertToSourceEntityFilterDto(SourceEntityFilter sourceEntityFilter) {
		return convertToSourceEntityFilterDto(sourceEntityFilter, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityFilter} to a(n) {@link SourceEntityFilterDto}
	 *
	 * @param sourceEntityFilter the source object which should be converted
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code sourceEntityFilter} is contained, the found
	 *                           {@link SourceEntityFilterDto} will be returned
	 * @return an equivalent new created {@link SourceEntityFilterDto} or the found one from the given map
	 */
	public static SourceEntityFilterDto convertToSourceEntityFilterDto(SourceEntityFilter sourceEntityFilter, Map<String, ITransportable> mappedObjects) {
		return convertToDto(sourceEntityFilter, mappedObjects, DtoObjectFactory::createSourceEntityFilterDto, (domain, dto) -> getInstance().setSourceEntityFilterDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setSourceEntityFilterDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link SourceEntityFilterNotAtTargetDto} to a(n) {@link SourceEntityFilterNotAtTarget}
	 *
	 * @param sourceEntityFilterNotAtTarget the source object which should be converted
	 * @return an equivalent new created {@link SourceEntityFilterNotAtTarget}
	 */
	public static SourceEntityFilterNotAtTarget convertToSourceEntityFilterNotAtTarget(SourceEntityFilterNotAtTargetDto sourceEntityFilterNotAtTarget) {
		return convertToSourceEntityFilterNotAtTarget(sourceEntityFilterNotAtTarget, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityFilterNotAtTargetDto} to a(n) {@link SourceEntityFilterNotAtTarget}
	 *
	 * @param sourceEntityFilterNotAtTarget the source object which should be converted
	 * @param mappedObjects                 map which contains already mapped objects. If an identification of {@code sourceEntityFilterNotAtTarget} is
	 *                                      contained, the found {@link SourceEntityFilterNotAtTarget} will be returned
	 * @return an equivalent new created {@link SourceEntityFilterNotAtTarget} or the found one from the given map
	 */
	public static SourceEntityFilterNotAtTarget convertToSourceEntityFilterNotAtTarget(SourceEntityFilterNotAtTargetDto sourceEntityFilterNotAtTarget
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(sourceEntityFilterNotAtTarget, mappedObjects, DomainObjectFactory::createSourceEntityFilterNotAtTarget, (dto, domain) -> getInstance().setSourceEntityFilterNotAtTargetValues(dto, domain)
				, (dto, domain) -> getInstance().setSourceEntityFilterNotAtTargetSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link SourceEntityFilterNotAtTarget} to a(n) {@link SourceEntityFilterNotAtTargetDto}
	 *
	 * @param sourceEntityFilterNotAtTarget the source object which should be converted
	 * @return an equivalent new created {@link SourceEntityFilterNotAtTargetDto}
	 */
	public static SourceEntityFilterNotAtTargetDto convertToSourceEntityFilterNotAtTargetDto(SourceEntityFilterNotAtTarget sourceEntityFilterNotAtTarget) {
		return convertToSourceEntityFilterNotAtTargetDto(sourceEntityFilterNotAtTarget, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityFilterNotAtTarget} to a(n) {@link SourceEntityFilterNotAtTargetDto}
	 *
	 * @param sourceEntityFilterNotAtTarget the source object which should be converted
	 * @param mappedObjects                 map which contains already mapped objects. If an identification of {@code sourceEntityFilterNotAtTarget} is
	 *                                      contained, the found {@link SourceEntityFilterNotAtTargetDto} will be returned
	 * @return an equivalent new created {@link SourceEntityFilterNotAtTargetDto} or the found one from the given map
	 */
	public static SourceEntityFilterNotAtTargetDto convertToSourceEntityFilterNotAtTargetDto(SourceEntityFilterNotAtTarget sourceEntityFilterNotAtTarget
			, Map<String, ITransportable> mappedObjects) {
		return convertToDto(sourceEntityFilterNotAtTarget, mappedObjects, DtoObjectFactory::createSourceEntityFilterNotAtTargetDto, (domain, dto) -> getInstance().setSourceEntityFilterNotAtTargetDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setSourceEntityFilterNotAtTargetDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link SourceEntityManyToManyDto} to a(n) {@link SourceEntityManyToMany}
	 *
	 * @param sourceEntityManyToMany the source object which should be converted
	 * @return an equivalent new created {@link SourceEntityManyToMany}
	 */
	public static SourceEntityManyToMany convertToSourceEntityManyToMany(SourceEntityManyToManyDto sourceEntityManyToMany) {
		return convertToSourceEntityManyToMany(sourceEntityManyToMany, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityManyToManyDto} to a(n) {@link SourceEntityManyToMany}
	 *
	 * @param sourceEntityManyToMany the source object which should be converted
	 * @param mappedObjects          map which contains already mapped objects. If an identification of {@code sourceEntityManyToMany} is contained, the
	 *                               found {@link SourceEntityManyToMany} will be returned
	 * @return an equivalent new created {@link SourceEntityManyToMany} or the found one from the given map
	 */
	public static SourceEntityManyToMany convertToSourceEntityManyToMany(SourceEntityManyToManyDto sourceEntityManyToMany
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(sourceEntityManyToMany, mappedObjects, DomainObjectFactory::createSourceEntityManyToMany, (dto, domain) -> getInstance().setSourceEntityManyToManyValues(dto, domain)
				, (dto, domain) -> getInstance().setSourceEntityManyToManySingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link SourceEntityManyToMany} to a(n) {@link SourceEntityManyToManyDto}
	 *
	 * @param sourceEntityManyToMany the source object which should be converted
	 * @return an equivalent new created {@link SourceEntityManyToManyDto}
	 */
	public static SourceEntityManyToManyDto convertToSourceEntityManyToManyDto(SourceEntityManyToMany sourceEntityManyToMany) {
		return convertToSourceEntityManyToManyDto(sourceEntityManyToMany, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityManyToMany} to a(n) {@link SourceEntityManyToManyDto}
	 *
	 * @param sourceEntityManyToMany the source object which should be converted
	 * @param mappedObjects          map which contains already mapped objects. If an identification of {@code sourceEntityManyToMany} is contained, the
	 *                               found {@link SourceEntityManyToManyDto} will be returned
	 * @return an equivalent new created {@link SourceEntityManyToManyDto} or the found one from the given map
	 */
	public static SourceEntityManyToManyDto convertToSourceEntityManyToManyDto(SourceEntityManyToMany sourceEntityManyToMany
			, Map<String, ITransportable> mappedObjects) {
		return convertToDto(sourceEntityManyToMany, mappedObjects, DtoObjectFactory::createSourceEntityManyToManyDto, (domain, dto) -> getInstance().setSourceEntityManyToManyDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setSourceEntityManyToManyDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link SourceEntityManyToOneDto} to a(n) {@link SourceEntityManyToOne}
	 *
	 * @param sourceEntityManyToOne the source object which should be converted
	 * @return an equivalent new created {@link SourceEntityManyToOne}
	 */
	public static SourceEntityManyToOne convertToSourceEntityManyToOne(SourceEntityManyToOneDto sourceEntityManyToOne) {
		return convertToSourceEntityManyToOne(sourceEntityManyToOne, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityManyToOneDto} to a(n) {@link SourceEntityManyToOne}
	 *
	 * @param sourceEntityManyToOne the source object which should be converted
	 * @param mappedObjects         map which contains already mapped objects. If an identification of {@code sourceEntityManyToOne} is contained, the
	 *                              found {@link SourceEntityManyToOne} will be returned
	 * @return an equivalent new created {@link SourceEntityManyToOne} or the found one from the given map
	 */
	public static SourceEntityManyToOne convertToSourceEntityManyToOne(SourceEntityManyToOneDto sourceEntityManyToOne
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(sourceEntityManyToOne, mappedObjects, DomainObjectFactory::createSourceEntityManyToOne, (dto, domain) -> getInstance().setSourceEntityManyToOneValues(dto, domain)
				, (dto, domain) -> getInstance().setSourceEntityManyToOneSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link SourceEntityManyToOne} to a(n) {@link SourceEntityManyToOneDto}
	 *
	 * @param sourceEntityManyToOne the source object which should be converted
	 * @return an equivalent new created {@link SourceEntityManyToOneDto}
	 */
	public static SourceEntityManyToOneDto convertToSourceEntityManyToOneDto(SourceEntityManyToOne sourceEntityManyToOne) {
		return convertToSourceEntityManyToOneDto(sourceEntityManyToOne, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityManyToOne} to a(n) {@link SourceEntityManyToOneDto}
	 *
	 * @param sourceEntityManyToOne the source object which should be converted
	 * @param mappedObjects         map which contains already mapped objects. If an identification of {@code sourceEntityManyToOne} is contained, the
	 *                              found {@link SourceEntityManyToOneDto} will be returned
	 * @return an equivalent new created {@link SourceEntityManyToOneDto} or the found one from the given map
	 */
	public static SourceEntityManyToOneDto convertToSourceEntityManyToOneDto(SourceEntityManyToOne sourceEntityManyToOne
			, Map<String, ITransportable> mappedObjects) {
		return convertToDto(sourceEntityManyToOne, mappedObjects, DtoObjectFactory::createSourceEntityManyToOneDto, (domain, dto) -> getInstance().setSourceEntityManyToOneDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setSourceEntityManyToOneDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link SourceEntityOneToManyDto} to a(n) {@link SourceEntityOneToMany}
	 *
	 * @param sourceEntityOneToMany the source object which should be converted
	 * @return an equivalent new created {@link SourceEntityOneToMany}
	 */
	public static SourceEntityOneToMany convertToSourceEntityOneToMany(SourceEntityOneToManyDto sourceEntityOneToMany) {
		return convertToSourceEntityOneToMany(sourceEntityOneToMany, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityOneToManyDto} to a(n) {@link SourceEntityOneToMany}
	 *
	 * @param sourceEntityOneToMany the source object which should be converted
	 * @param mappedObjects         map which contains already mapped objects. If an identification of {@code sourceEntityOneToMany} is contained, the
	 *                              found {@link SourceEntityOneToMany} will be returned
	 * @return an equivalent new created {@link SourceEntityOneToMany} or the found one from the given map
	 */
	public static SourceEntityOneToMany convertToSourceEntityOneToMany(SourceEntityOneToManyDto sourceEntityOneToMany
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(sourceEntityOneToMany, mappedObjects, DomainObjectFactory::createSourceEntityOneToMany, (dto, domain) -> getInstance().setSourceEntityOneToManyValues(dto, domain)
				, (dto, domain) -> getInstance().setSourceEntityOneToManySingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link SourceEntityOneToMany} to a(n) {@link SourceEntityOneToManyDto}
	 *
	 * @param sourceEntityOneToMany the source object which should be converted
	 * @return an equivalent new created {@link SourceEntityOneToManyDto}
	 */
	public static SourceEntityOneToManyDto convertToSourceEntityOneToManyDto(SourceEntityOneToMany sourceEntityOneToMany) {
		return convertToSourceEntityOneToManyDto(sourceEntityOneToMany, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityOneToMany} to a(n) {@link SourceEntityOneToManyDto}
	 *
	 * @param sourceEntityOneToMany the source object which should be converted
	 * @param mappedObjects         map which contains already mapped objects. If an identification of {@code sourceEntityOneToMany} is contained, the
	 *                              found {@link SourceEntityOneToManyDto} will be returned
	 * @return an equivalent new created {@link SourceEntityOneToManyDto} or the found one from the given map
	 */
	public static SourceEntityOneToManyDto convertToSourceEntityOneToManyDto(SourceEntityOneToMany sourceEntityOneToMany
			, Map<String, ITransportable> mappedObjects) {
		return convertToDto(sourceEntityOneToMany, mappedObjects, DtoObjectFactory::createSourceEntityOneToManyDto, (domain, dto) -> getInstance().setSourceEntityOneToManyDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setSourceEntityOneToManyDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link SourceEntityOneToOneDto} to a(n) {@link SourceEntityOneToOne}
	 *
	 * @param sourceEntityOneToOne the source object which should be converted
	 * @return an equivalent new created {@link SourceEntityOneToOne}
	 */
	public static SourceEntityOneToOne convertToSourceEntityOneToOne(SourceEntityOneToOneDto sourceEntityOneToOne) {
		return convertToSourceEntityOneToOne(sourceEntityOneToOne, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityOneToOneDto} to a(n) {@link SourceEntityOneToOne}
	 *
	 * @param sourceEntityOneToOne the source object which should be converted
	 * @param mappedObjects        map which contains already mapped objects. If an identification of {@code sourceEntityOneToOne} is contained, the found
	 *                             {@link SourceEntityOneToOne} will be returned
	 * @return an equivalent new created {@link SourceEntityOneToOne} or the found one from the given map
	 */
	public static SourceEntityOneToOne convertToSourceEntityOneToOne(SourceEntityOneToOneDto sourceEntityOneToOne
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(sourceEntityOneToOne, mappedObjects, DomainObjectFactory::createSourceEntityOneToOne, (dto, domain) -> getInstance().setSourceEntityOneToOneValues(dto, domain)
				, (dto, domain) -> getInstance().setSourceEntityOneToOneSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link SourceEntityOneToOne} to a(n) {@link SourceEntityOneToOneDto}
	 *
	 * @param sourceEntityOneToOne the source object which should be converted
	 * @return an equivalent new created {@link SourceEntityOneToOneDto}
	 */
	public static SourceEntityOneToOneDto convertToSourceEntityOneToOneDto(SourceEntityOneToOne sourceEntityOneToOne) {
		return convertToSourceEntityOneToOneDto(sourceEntityOneToOne, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityOneToOne} to a(n) {@link SourceEntityOneToOneDto}
	 *
	 * @param sourceEntityOneToOne the source object which should be converted
	 * @param mappedObjects        map which contains already mapped objects. If an identification of {@code sourceEntityOneToOne} is contained, the found
	 *                             {@link SourceEntityOneToOneDto} will be returned
	 * @return an equivalent new created {@link SourceEntityOneToOneDto} or the found one from the given map
	 */
	public static SourceEntityOneToOneDto convertToSourceEntityOneToOneDto(SourceEntityOneToOne sourceEntityOneToOne
			, Map<String, ITransportable> mappedObjects) {
		return convertToDto(sourceEntityOneToOne, mappedObjects, DtoObjectFactory::createSourceEntityOneToOneDto, (domain, dto) -> getInstance().setSourceEntityOneToOneDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setSourceEntityOneToOneDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link TargetEntityFilterDto} to a(n) {@link TargetEntityFilter}
	 *
	 * @param targetEntityFilter the source object which should be converted
	 * @return an equivalent new created {@link TargetEntityFilter}
	 */
	public static TargetEntityFilter convertToTargetEntityFilter(TargetEntityFilterDto targetEntityFilter) {
		return convertToTargetEntityFilter(targetEntityFilter, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityFilterDto} to a(n) {@link TargetEntityFilter}
	 *
	 * @param targetEntityFilter the source object which should be converted
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code targetEntityFilter} is contained, the found
	 *                           {@link TargetEntityFilter} will be returned
	 * @return an equivalent new created {@link TargetEntityFilter} or the found one from the given map
	 */
	public static TargetEntityFilter convertToTargetEntityFilter(TargetEntityFilterDto targetEntityFilter, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(targetEntityFilter, mappedObjects, DomainObjectFactory::createTargetEntityFilter, (dto, domain) -> getInstance().setTargetEntityFilterValues(dto, domain)
				, (dto, domain) -> getInstance().setTargetEntityFilterSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link TargetEntityFilter} to a(n) {@link TargetEntityFilterDto}
	 *
	 * @param targetEntityFilter the source object which should be converted
	 * @return an equivalent new created {@link TargetEntityFilterDto}
	 */
	public static TargetEntityFilterDto convertToTargetEntityFilterDto(TargetEntityFilter targetEntityFilter) {
		return convertToTargetEntityFilterDto(targetEntityFilter, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityFilter} to a(n) {@link TargetEntityFilterDto}
	 *
	 * @param targetEntityFilter the source object which should be converted
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code targetEntityFilter} is contained, the found
	 *                           {@link TargetEntityFilterDto} will be returned
	 * @return an equivalent new created {@link TargetEntityFilterDto} or the found one from the given map
	 */
	public static TargetEntityFilterDto convertToTargetEntityFilterDto(TargetEntityFilter targetEntityFilter, Map<String, ITransportable> mappedObjects) {
		return convertToDto(targetEntityFilter, mappedObjects, DtoObjectFactory::createTargetEntityFilterDto, (domain, dto) -> getInstance().setTargetEntityFilterDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setTargetEntityFilterDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link TargetEntityFilterNotAtTargetDto} to a(n) {@link TargetEntityFilterNotAtTarget}
	 *
	 * @param targetEntityFilterNotAtTarget the source object which should be converted
	 * @return an equivalent new created {@link TargetEntityFilterNotAtTarget}
	 */
	public static TargetEntityFilterNotAtTarget convertToTargetEntityFilterNotAtTarget(TargetEntityFilterNotAtTargetDto targetEntityFilterNotAtTarget) {
		return convertToTargetEntityFilterNotAtTarget(targetEntityFilterNotAtTarget, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityFilterNotAtTargetDto} to a(n) {@link TargetEntityFilterNotAtTarget}
	 *
	 * @param targetEntityFilterNotAtTarget the source object which should be converted
	 * @param mappedObjects                 map which contains already mapped objects. If an identification of {@code targetEntityFilterNotAtTarget} is
	 *                                      contained, the found {@link TargetEntityFilterNotAtTarget} will be returned
	 * @return an equivalent new created {@link TargetEntityFilterNotAtTarget} or the found one from the given map
	 */
	public static TargetEntityFilterNotAtTarget convertToTargetEntityFilterNotAtTarget(TargetEntityFilterNotAtTargetDto targetEntityFilterNotAtTarget
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(targetEntityFilterNotAtTarget, mappedObjects, DomainObjectFactory::createTargetEntityFilterNotAtTarget, (dto, domain) -> getInstance().setTargetEntityFilterNotAtTargetValues(dto, domain)
				, (dto, domain) -> getInstance().setTargetEntityFilterNotAtTargetSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link TargetEntityFilterNotAtTarget} to a(n) {@link TargetEntityFilterNotAtTargetDto}
	 *
	 * @param targetEntityFilterNotAtTarget the source object which should be converted
	 * @return an equivalent new created {@link TargetEntityFilterNotAtTargetDto}
	 */
	public static TargetEntityFilterNotAtTargetDto convertToTargetEntityFilterNotAtTargetDto(TargetEntityFilterNotAtTarget targetEntityFilterNotAtTarget) {
		return convertToTargetEntityFilterNotAtTargetDto(targetEntityFilterNotAtTarget, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityFilterNotAtTarget} to a(n) {@link TargetEntityFilterNotAtTargetDto}
	 *
	 * @param targetEntityFilterNotAtTarget the source object which should be converted
	 * @param mappedObjects                 map which contains already mapped objects. If an identification of {@code targetEntityFilterNotAtTarget} is
	 *                                      contained, the found {@link TargetEntityFilterNotAtTargetDto} will be returned
	 * @return an equivalent new created {@link TargetEntityFilterNotAtTargetDto} or the found one from the given map
	 */
	public static TargetEntityFilterNotAtTargetDto convertToTargetEntityFilterNotAtTargetDto(TargetEntityFilterNotAtTarget targetEntityFilterNotAtTarget
			, Map<String, ITransportable> mappedObjects) {
		return convertToDto(targetEntityFilterNotAtTarget, mappedObjects, DtoObjectFactory::createTargetEntityFilterNotAtTargetDto, (domain, dto) -> getInstance().setTargetEntityFilterNotAtTargetDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setTargetEntityFilterNotAtTargetDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link TargetEntityManyToManyDto} to a(n) {@link TargetEntityManyToMany}
	 *
	 * @param targetEntityManyToMany the source object which should be converted
	 * @return an equivalent new created {@link TargetEntityManyToMany}
	 */
	public static TargetEntityManyToMany convertToTargetEntityManyToMany(TargetEntityManyToManyDto targetEntityManyToMany) {
		return convertToTargetEntityManyToMany(targetEntityManyToMany, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityManyToManyDto} to a(n) {@link TargetEntityManyToMany}
	 *
	 * @param targetEntityManyToMany the source object which should be converted
	 * @param mappedObjects          map which contains already mapped objects. If an identification of {@code targetEntityManyToMany} is contained, the
	 *                               found {@link TargetEntityManyToMany} will be returned
	 * @return an equivalent new created {@link TargetEntityManyToMany} or the found one from the given map
	 */
	public static TargetEntityManyToMany convertToTargetEntityManyToMany(TargetEntityManyToManyDto targetEntityManyToMany
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(targetEntityManyToMany, mappedObjects, DomainObjectFactory::createTargetEntityManyToMany, (dto, domain) -> getInstance().setTargetEntityManyToManyValues(dto, domain)
				, (dto, domain) -> getInstance().setTargetEntityManyToManySingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link TargetEntityManyToMany} to a(n) {@link TargetEntityManyToManyDto}
	 *
	 * @param targetEntityManyToMany the source object which should be converted
	 * @return an equivalent new created {@link TargetEntityManyToManyDto}
	 */
	public static TargetEntityManyToManyDto convertToTargetEntityManyToManyDto(TargetEntityManyToMany targetEntityManyToMany) {
		return convertToTargetEntityManyToManyDto(targetEntityManyToMany, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityManyToMany} to a(n) {@link TargetEntityManyToManyDto}
	 *
	 * @param targetEntityManyToMany the source object which should be converted
	 * @param mappedObjects          map which contains already mapped objects. If an identification of {@code targetEntityManyToMany} is contained, the
	 *                               found {@link TargetEntityManyToManyDto} will be returned
	 * @return an equivalent new created {@link TargetEntityManyToManyDto} or the found one from the given map
	 */
	public static TargetEntityManyToManyDto convertToTargetEntityManyToManyDto(TargetEntityManyToMany targetEntityManyToMany
			, Map<String, ITransportable> mappedObjects) {
		return convertToDto(targetEntityManyToMany, mappedObjects, DtoObjectFactory::createTargetEntityManyToManyDto, (domain, dto) -> getInstance().setTargetEntityManyToManyDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setTargetEntityManyToManyDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToManyDto} to a(n) {@link TargetEntityOneToMany}
	 *
	 * @param targetEntityOneToMany the source object which should be converted
	 * @return an equivalent new created {@link TargetEntityOneToMany}
	 */
	public static TargetEntityOneToMany convertToTargetEntityOneToMany(TargetEntityOneToManyDto targetEntityOneToMany) {
		return convertToTargetEntityOneToMany(targetEntityOneToMany, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToManyDto} to a(n) {@link TargetEntityOneToMany}
	 *
	 * @param targetEntityOneToMany the source object which should be converted
	 * @param mappedObjects         map which contains already mapped objects. If an identification of {@code targetEntityOneToMany} is contained, the
	 *                              found {@link TargetEntityOneToMany} will be returned
	 * @return an equivalent new created {@link TargetEntityOneToMany} or the found one from the given map
	 */
	public static TargetEntityOneToMany convertToTargetEntityOneToMany(TargetEntityOneToManyDto targetEntityOneToMany
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(targetEntityOneToMany, mappedObjects, DomainObjectFactory::createTargetEntityOneToMany, (dto, domain) -> getInstance().setTargetEntityOneToManyValues(dto, domain)
				, (dto, domain) -> getInstance().setTargetEntityOneToManySingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToMany} to a(n) {@link TargetEntityOneToManyDto}
	 *
	 * @param targetEntityOneToMany the source object which should be converted
	 * @return an equivalent new created {@link TargetEntityOneToManyDto}
	 */
	public static TargetEntityOneToManyDto convertToTargetEntityOneToManyDto(TargetEntityOneToMany targetEntityOneToMany) {
		return convertToTargetEntityOneToManyDto(targetEntityOneToMany, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToMany} to a(n) {@link TargetEntityOneToManyDto}
	 *
	 * @param targetEntityOneToMany the source object which should be converted
	 * @param mappedObjects         map which contains already mapped objects. If an identification of {@code targetEntityOneToMany} is contained, the
	 *                              found {@link TargetEntityOneToManyDto} will be returned
	 * @return an equivalent new created {@link TargetEntityOneToManyDto} or the found one from the given map
	 */
	public static TargetEntityOneToManyDto convertToTargetEntityOneToManyDto(TargetEntityOneToMany targetEntityOneToMany
			, Map<String, ITransportable> mappedObjects) {
		return convertToDto(targetEntityOneToMany, mappedObjects, DtoObjectFactory::createTargetEntityOneToManyDto, (domain, dto) -> getInstance().setTargetEntityOneToManyDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setTargetEntityOneToManyDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToOneDto} to a(n) {@link TargetEntityOneToOne}
	 *
	 * @param targetEntityOneToOne the source object which should be converted
	 * @return an equivalent new created {@link TargetEntityOneToOne}
	 */
	public static TargetEntityOneToOne convertToTargetEntityOneToOne(TargetEntityOneToOneDto targetEntityOneToOne) {
		return convertToTargetEntityOneToOne(targetEntityOneToOne, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToOneDto} to a(n) {@link TargetEntityOneToOne}
	 *
	 * @param targetEntityOneToOne the source object which should be converted
	 * @param mappedObjects        map which contains already mapped objects. If an identification of {@code targetEntityOneToOne} is contained, the found
	 *                             {@link TargetEntityOneToOne} will be returned
	 * @return an equivalent new created {@link TargetEntityOneToOne} or the found one from the given map
	 */
	public static TargetEntityOneToOne convertToTargetEntityOneToOne(TargetEntityOneToOneDto targetEntityOneToOne
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(targetEntityOneToOne, mappedObjects, DomainObjectFactory::createTargetEntityOneToOne, (dto, domain) -> getInstance().setTargetEntityOneToOneValues(dto, domain)
				, (dto, domain) -> getInstance().setTargetEntityOneToOneSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToOneDto} to a(n) {@link TargetEntityOneToOne} and sets the result to the corresponding reference property at
	 * the parent
	 *
	 * @param targetEntityOneToOne the source object which should be converted
	 * @param parent               the parent of converted result
	 * @return an equivalent new created {@link TargetEntityOneToOne}
	 */
	public static TargetEntityOneToOne convertToTargetEntityOneToOne(TargetEntityOneToOneDto targetEntityOneToOne, SourceEntityOneToOne parent) {
		return convertToTargetEntityOneToOne(targetEntityOneToOne, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToOneDto} to a(n) {@link TargetEntityOneToOne} and sets the result to the corresponding reference property at
	 * the parent
	 *
	 * @param targetEntityOneToOne the source object which should be converted
	 * @param parent               the parent of converted result
	 * @param mappedObjects        map which contains already mapped objects. If an identification of {@code targetEntityOneToOne} is contained, the found
	 *                             {@link TargetEntityOneToOne} will be returned
	 * @return an equivalent new created {@link TargetEntityOneToOne} or the found one from the given map
	 */
	public static TargetEntityOneToOne convertToTargetEntityOneToOne(TargetEntityOneToOneDto targetEntityOneToOne, SourceEntityOneToOne parent
			, Map<String, IIdentifiable> mappedObjects) {
		TargetEntityOneToOne result = convertToTargetEntityOneToOne(targetEntityOneToOne, mappedObjects);
		if (result != null) {
			parent.setOneToOneRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToOne} to a(n) {@link TargetEntityOneToOneDto}
	 *
	 * @param targetEntityOneToOne the source object which should be converted
	 * @return an equivalent new created {@link TargetEntityOneToOneDto}
	 */
	public static TargetEntityOneToOneDto convertToTargetEntityOneToOneDto(TargetEntityOneToOne targetEntityOneToOne) {
		return convertToTargetEntityOneToOneDto(targetEntityOneToOne, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToOne} to a(n) {@link TargetEntityOneToOneDto}
	 *
	 * @param targetEntityOneToOne the source object which should be converted
	 * @param mappedObjects        map which contains already mapped objects. If an identification of {@code targetEntityOneToOne} is contained, the found
	 *                             {@link TargetEntityOneToOneDto} will be returned
	 * @return an equivalent new created {@link TargetEntityOneToOneDto} or the found one from the given map
	 */
	public static TargetEntityOneToOneDto convertToTargetEntityOneToOneDto(TargetEntityOneToOne targetEntityOneToOne
			, Map<String, ITransportable> mappedObjects) {
		return convertToDto(targetEntityOneToOne, mappedObjects, DtoObjectFactory::createTargetEntityOneToOneDto, (domain, dto) -> getInstance().setTargetEntityOneToOneDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setTargetEntityOneToOneDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToOne} to a(n) {@link TargetEntityOneToOneDto} and sets the result to the corresponding reference property at
	 * the parent
	 *
	 * @param targetEntityOneToOne the source object which should be converted
	 * @param parent               the parent of converted result
	 * @return an equivalent new created {@link TargetEntityOneToOneDto}
	 */
	public static TargetEntityOneToOneDto convertToTargetEntityOneToOneDto(TargetEntityOneToOne targetEntityOneToOne, SourceEntityOneToOneDto parent) {
		return convertToTargetEntityOneToOneDto(targetEntityOneToOne, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToOne} to a(n) {@link TargetEntityOneToOneDto} and sets the result to the corresponding reference property at
	 * the parent
	 *
	 * @param targetEntityOneToOne the source object which should be converted
	 * @param parent               the parent of converted result
	 * @param mappedObjects        map which contains already mapped objects. If an identification of {@code targetEntityOneToOne} is contained, the found
	 *                             {@link TargetEntityOneToOneDto} will be returned
	 * @return an equivalent new created {@link TargetEntityOneToOneDto} or the found one from the given map
	 */
	public static TargetEntityOneToOneDto convertToTargetEntityOneToOneDto(TargetEntityOneToOne targetEntityOneToOne, SourceEntityOneToOneDto parent
			, Map<String, ITransportable> mappedObjects) {
		TargetEntityOneToOneDto result = convertToTargetEntityOneToOneDto(targetEntityOneToOne, mappedObjects);
		if (result != null) {
			parent.setOneToOneRef(result);
		}
		return result;
	}

	/**
	 * @return the singleton
	 */
	public static CommonTransportMapper getInstance() {
		if (instance == null) {
			instance = TransportMapperFactory.createCommonTransportMapper();
		}
		return instance;
	}

	/**
	 * Adds the references at {@code dto} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dto           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dto}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityFilterDtoSingleReferences(SourceEntityFilter domain, SourceEntityFilterDto dto
			, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityFilterDtoValues(SourceEntityFilter domain, SourceEntityFilterDto dto) {
	}

	/**
	 * Adds the references at {@code dto} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dto           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dto}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityFilterNotAtTargetDtoSingleReferences(SourceEntityFilterNotAtTarget domain, SourceEntityFilterNotAtTargetDto dto
			, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityFilterNotAtTargetDtoValues(SourceEntityFilterNotAtTarget domain, SourceEntityFilterNotAtTargetDto dto) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityFilterNotAtTargetSingleReferences(SourceEntityFilterNotAtTargetDto dto, SourceEntityFilterNotAtTarget domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityFilterNotAtTargetValues(SourceEntityFilterNotAtTargetDto dto, SourceEntityFilterNotAtTarget domain) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityFilterSingleReferences(SourceEntityFilterDto dto, SourceEntityFilter domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityFilterValues(SourceEntityFilterDto dto, SourceEntityFilter domain) {
	}

	/**
	 * Adds the references at {@code dto} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dto           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dto}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityManyToManyDtoSingleReferences(SourceEntityManyToMany domain, SourceEntityManyToManyDto dto
			, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityManyToManyDtoValues(SourceEntityManyToMany domain, SourceEntityManyToManyDto dto) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityManyToManySingleReferences(SourceEntityManyToManyDto dto, SourceEntityManyToMany domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityManyToManyValues(SourceEntityManyToManyDto dto, SourceEntityManyToMany domain) {
	}

	/**
	 * Adds the references at {@code dto} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dto           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dto}
	 */
	protected void setSourceEntityManyToOneDtoSingleReferences(SourceEntityManyToOne domain, SourceEntityManyToOneDto dto
			, Map<String, ITransportable> mappedObjects) {
		dto.setManyToOneRef(CommonTransportMapper.convertToTargetEntityManyToManyDto(domain.getManyToOneRef(), mappedObjects));
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityManyToOneDtoValues(SourceEntityManyToOne domain, SourceEntityManyToOneDto dto) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	protected void setSourceEntityManyToOneSingleReferences(SourceEntityManyToOneDto dto, SourceEntityManyToOne domain
			, Map<String, IIdentifiable> mappedObjects) {
		domain.setManyToOneRef(CommonTransportMapper.convertToTargetEntityManyToMany(dto.getManyToOneRef(), mappedObjects));
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityManyToOneValues(SourceEntityManyToOneDto dto, SourceEntityManyToOne domain) {
	}

	/**
	 * Adds the references at {@code dto} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dto           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dto}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityOneToManyDtoSingleReferences(SourceEntityOneToMany domain, SourceEntityOneToManyDto dto
			, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityOneToManyDtoValues(SourceEntityOneToMany domain, SourceEntityOneToManyDto dto) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityOneToManySingleReferences(SourceEntityOneToManyDto dto, SourceEntityOneToMany domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityOneToManyValues(SourceEntityOneToManyDto dto, SourceEntityOneToMany domain) {
	}

	/**
	 * Adds the references at {@code dto} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dto           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dto}
	 */
	protected void setSourceEntityOneToOneDtoSingleReferences(SourceEntityOneToOne domain, SourceEntityOneToOneDto dto
			, Map<String, ITransportable> mappedObjects) {
		CommonTransportMapper.convertToTargetEntityOneToOneDto(domain.getOneToOneRef(), dto, mappedObjects);
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityOneToOneDtoValues(SourceEntityOneToOne domain, SourceEntityOneToOneDto dto) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	protected void setSourceEntityOneToOneSingleReferences(SourceEntityOneToOneDto dto, SourceEntityOneToOne domain
			, Map<String, IIdentifiable> mappedObjects) {
		CommonTransportMapper.convertToTargetEntityOneToOne(dto.getOneToOneRef(), domain, mappedObjects);
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityOneToOneValues(SourceEntityOneToOneDto dto, SourceEntityOneToOne domain) {
	}

	/**
	 * Adds the references at {@code dto} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dto           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dto}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityFilterDtoSingleReferences(TargetEntityFilter domain, TargetEntityFilterDto dto
			, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setTargetEntityFilterDtoValues(TargetEntityFilter domain, TargetEntityFilterDto dto) {
		dto.setEnumFieldForFiltering(domain.getEnumFieldForFiltering());
	}

	/**
	 * Adds the references at {@code dto} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dto           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dto}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityFilterNotAtTargetDtoSingleReferences(TargetEntityFilterNotAtTarget domain, TargetEntityFilterNotAtTargetDto dto
			, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityFilterNotAtTargetDtoValues(TargetEntityFilterNotAtTarget domain, TargetEntityFilterNotAtTargetDto dto) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityFilterNotAtTargetSingleReferences(TargetEntityFilterNotAtTargetDto dto, TargetEntityFilterNotAtTarget domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityFilterNotAtTargetValues(TargetEntityFilterNotAtTargetDto dto, TargetEntityFilterNotAtTarget domain) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityFilterSingleReferences(TargetEntityFilterDto dto, TargetEntityFilter domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setTargetEntityFilterValues(TargetEntityFilterDto dto, TargetEntityFilter domain) {
		domain.setEnumFieldForFiltering(dto.getEnumFieldForFiltering());
	}

	/**
	 * Adds the references at {@code dto} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dto           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dto}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityManyToManyDtoSingleReferences(TargetEntityManyToMany domain, TargetEntityManyToManyDto dto
			, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityManyToManyDtoValues(TargetEntityManyToMany domain, TargetEntityManyToManyDto dto) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityManyToManySingleReferences(TargetEntityManyToManyDto dto, TargetEntityManyToMany domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityManyToManyValues(TargetEntityManyToManyDto dto, TargetEntityManyToMany domain) {
	}

	/**
	 * Adds the references at {@code dto} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dto           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dto}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityOneToManyDtoSingleReferences(TargetEntityOneToMany domain, TargetEntityOneToManyDto dto
			, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityOneToManyDtoValues(TargetEntityOneToMany domain, TargetEntityOneToManyDto dto) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityOneToManySingleReferences(TargetEntityOneToManyDto dto, TargetEntityOneToMany domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityOneToManyValues(TargetEntityOneToManyDto dto, TargetEntityOneToMany domain) {
	}

	/**
	 * Adds the references at {@code dto} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dto           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dto}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityOneToOneDtoSingleReferences(TargetEntityOneToOne domain, TargetEntityOneToOneDto dto
			, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityOneToOneDtoValues(TargetEntityOneToOne domain, TargetEntityOneToOneDto dto) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityOneToOneSingleReferences(TargetEntityOneToOneDto dto, TargetEntityOneToOne domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityOneToOneValues(TargetEntityOneToOneDto dto, TargetEntityOneToOne domain) {
	}

}
