package com.github.ma_vin.util.layer_generator.sample.content.mapper;

import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseTransportMapper;
import com.github.ma_vin.util.layer_generator.sample.content.domain.DomainObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.content.domain.single.SingleRefOneParent;
import com.github.ma_vin.util.layer_generator.sample.content.dto.DtoObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.content.dto.ITransportable;
import com.github.ma_vin.util.layer_generator.sample.content.dto.domain.DerivedFromDomainDto;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class which provides methods to convert a data transport to a domain object of sub package <i>domain<i> and the other way around
 */
@BaseTransportMapper
public class DomainTransportMapper extends AbstractTransportMapper {

	/**
	 * singleton
	 */
	private static DomainTransportMapper instance;

	/**
	 * Converts a(n) {@link SingleRefOneParent} to a(n) {@link DerivedFromDomainDto}
	 *
	 * @param singleRefOneParent the source object which should be converted
	 * @return an equivalent new created {@link DerivedFromDomainDto}
	 */
	public static DerivedFromDomainDto convertToDerivedFromDomainDto(SingleRefOneParent singleRefOneParent) {
		return convertToDerivedFromDomainDto(singleRefOneParent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefOneParent} to a(n) {@link DerivedFromDomainDto}
	 *
	 * @param singleRefOneParent the source object which should be converted
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code derivedFromDomain} is contained, the found
	 *                           {@link DerivedFromDomainDto} will be returned
	 * @return an equivalent new created {@link DerivedFromDomainDto} or the found one from the given map
	 */
	public static DerivedFromDomainDto convertToDerivedFromDomainDto(SingleRefOneParent singleRefOneParent, Map<String, ITransportable> mappedObjects) {
		return convertToDto(singleRefOneParent, mappedObjects, DtoObjectFactory::createDerivedFromDomainDto, (domain, dto) -> getInstance().setDerivedFromDomainDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setDerivedFromDomainDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * @return the singleton
	 */
	public static DomainTransportMapper getInstance() {
		if (instance == null) {
			instance = TransportMapperFactory.createDomainTransportMapper();
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
	protected void setDerivedFromDomainDtoSingleReferences(SingleRefOneParent domain, DerivedFromDomainDto dto, Map<String, ITransportable> mappedObjects) {
		SingleTransportMapper.convertToSingleRefTwoParentsDto(domain.getSingleRef(), dto, mappedObjects);
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setDerivedFromDomainDtoValues(SingleRefOneParent domain, DerivedFromDomainDto dto) {
		dto.setDescription(domain.getDescription());
	}

}
