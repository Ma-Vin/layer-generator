package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseTransportMapper;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.single.SingleRefOneParent;
import de.ma_vin.util.sample.content.domain.single.SingleRefTwoParents;
import de.ma_vin.util.sample.content.dto.DtoObjectFactory;
import de.ma_vin.util.sample.content.dto.ITransportable;
import de.ma_vin.util.sample.content.dto.RootDto;
import de.ma_vin.util.sample.content.dto.domain.DerivedFromDomainDto;
import de.ma_vin.util.sample.content.dto.single.SingleRefOneParentDto;
import de.ma_vin.util.sample.content.dto.single.SingleRefTwoParentsDto;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class which provides methods to convert a data transport to a domain object of sub package <i>single<i> and the other way around
 */
@BaseTransportMapper
public class SingleTransportMapper extends AbstractTransportMapper {

	/**
	 * singleton
	 */
	private static SingleTransportMapper instance;

	/**
	 * Converts a(n) {@link SingleRefOneParentDto} to a(n) {@link SingleRefOneParent}
	 *
	 * @param singleRefOneParent the source object which should be converted
	 * @return an equivalent new created {@link SingleRefOneParent}
	 */
	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDto singleRefOneParent) {
		return convertToSingleRefOneParent(singleRefOneParent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefOneParentDto} to a(n) {@link SingleRefOneParent}
	 *
	 * @param singleRefOneParent the source object which should be converted
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code singleRefOneParent} is contained, the found
	 *                           {@link SingleRefOneParent} will be returned
	 * @return an equivalent new created {@link SingleRefOneParent} or the found one from the given map
	 */
	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDto singleRefOneParent, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(singleRefOneParent, mappedObjects, DomainObjectFactory::createSingleRefOneParent, (dto, domain) -> getInstance().setSingleRefOneParentValues(dto, domain)
				, (dto, domain) -> getInstance().setSingleRefOneParentSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link SingleRefOneParentDto} to a(n) {@link SingleRefOneParent} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefOneParent the source object which should be converted
	 * @param parent             the parent of converted result
	 * @return an equivalent new created {@link SingleRefOneParent}
	 */
	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDto singleRefOneParent, Root parent) {
		return convertToSingleRefOneParent(singleRefOneParent, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefOneParentDto} to a(n) {@link SingleRefOneParent} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefOneParent the source object which should be converted
	 * @param parent             the parent of converted result
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code singleRefOneParent} is contained, the found
	 *                           {@link SingleRefOneParent} will be returned
	 * @return an equivalent new created {@link SingleRefOneParent} or the found one from the given map
	 */
	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDto singleRefOneParent, Root parent
			, Map<String, IIdentifiable> mappedObjects) {
		SingleRefOneParent result = convertToSingleRefOneParent(singleRefOneParent, mappedObjects);
		if (result != null) {
			parent.setSingleRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefOneParent} to a(n) {@link SingleRefOneParentDto}
	 *
	 * @param singleRefOneParent the source object which should be converted
	 * @return an equivalent new created {@link SingleRefOneParentDto}
	 */
	public static SingleRefOneParentDto convertToSingleRefOneParentDto(SingleRefOneParent singleRefOneParent) {
		return convertToSingleRefOneParentDto(singleRefOneParent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefOneParent} to a(n) {@link SingleRefOneParentDto}
	 *
	 * @param singleRefOneParent the source object which should be converted
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code singleRefOneParent} is contained, the found
	 *                           {@link SingleRefOneParentDto} will be returned
	 * @return an equivalent new created {@link SingleRefOneParentDto} or the found one from the given map
	 */
	public static SingleRefOneParentDto convertToSingleRefOneParentDto(SingleRefOneParent singleRefOneParent, Map<String, ITransportable> mappedObjects) {
		return convertToDto(singleRefOneParent, mappedObjects, DtoObjectFactory::createSingleRefOneParentDto, (domain, dto) -> getInstance().setSingleRefOneParentDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setSingleRefOneParentDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link SingleRefOneParent} to a(n) {@link SingleRefOneParentDto} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefOneParent the source object which should be converted
	 * @param parent             the parent of converted result
	 * @return an equivalent new created {@link SingleRefOneParentDto}
	 */
	public static SingleRefOneParentDto convertToSingleRefOneParentDto(SingleRefOneParent singleRefOneParent, RootDto parent) {
		return convertToSingleRefOneParentDto(singleRefOneParent, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefOneParent} to a(n) {@link SingleRefOneParentDto} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefOneParent the source object which should be converted
	 * @param parent             the parent of converted result
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code singleRefOneParent} is contained, the found
	 *                           {@link SingleRefOneParentDto} will be returned
	 * @return an equivalent new created {@link SingleRefOneParentDto} or the found one from the given map
	 */
	public static SingleRefOneParentDto convertToSingleRefOneParentDto(SingleRefOneParent singleRefOneParent, RootDto parent
			, Map<String, ITransportable> mappedObjects) {
		SingleRefOneParentDto result = convertToSingleRefOneParentDto(singleRefOneParent, mappedObjects);
		if (result != null) {
			parent.setSingleRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParentsDto} to a(n) {@link SingleRefTwoParents}
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @return an equivalent new created {@link SingleRefTwoParents}
	 */
	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDto singleRefTwoParents) {
		return convertToSingleRefTwoParents(singleRefTwoParents, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParentsDto} to a(n) {@link SingleRefTwoParents}
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param mappedObjects       map which contains already mapped objects. If an identification of {@code singleRefTwoParents} is contained, the found
	 *                            {@link SingleRefTwoParents} will be returned
	 * @return an equivalent new created {@link SingleRefTwoParents} or the found one from the given map
	 */
	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDto singleRefTwoParents, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(singleRefTwoParents, mappedObjects, DomainObjectFactory::createSingleRefTwoParents, (dto, domain) -> getInstance().setSingleRefTwoParentsValues(dto, domain)
				, (dto, domain) -> getInstance().setSingleRefTwoParentsSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParentsDto} to a(n) {@link SingleRefTwoParents} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param parent              the parent of converted result
	 * @return an equivalent new created {@link SingleRefTwoParents}
	 */
	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDto singleRefTwoParents, Root parent) {
		return convertToSingleRefTwoParents(singleRefTwoParents, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParentsDto} to a(n) {@link SingleRefTwoParents} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param parent              the parent of converted result
	 * @param mappedObjects       map which contains already mapped objects. If an identification of {@code singleRefTwoParents} is contained, the found
	 *                            {@link SingleRefTwoParents} will be returned
	 * @return an equivalent new created {@link SingleRefTwoParents} or the found one from the given map
	 */
	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDto singleRefTwoParents, Root parent
			, Map<String, IIdentifiable> mappedObjects) {
		SingleRefTwoParents result = convertToSingleRefTwoParents(singleRefTwoParents, mappedObjects);
		if (result != null) {
			parent.setAnotherSingleRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParentsDto} to a(n) {@link SingleRefTwoParents} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param parent              the parent of converted result
	 * @return an equivalent new created {@link SingleRefTwoParents}
	 */
	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDto singleRefTwoParents, SingleRefOneParent parent) {
		return convertToSingleRefTwoParents(singleRefTwoParents, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParentsDto} to a(n) {@link SingleRefTwoParents} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param parent              the parent of converted result
	 * @param mappedObjects       map which contains already mapped objects. If an identification of {@code singleRefTwoParents} is contained, the found
	 *                            {@link SingleRefTwoParents} will be returned
	 * @return an equivalent new created {@link SingleRefTwoParents} or the found one from the given map
	 */
	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDto singleRefTwoParents, SingleRefOneParent parent
			, Map<String, IIdentifiable> mappedObjects) {
		SingleRefTwoParents result = convertToSingleRefTwoParents(singleRefTwoParents, mappedObjects);
		if (result != null) {
			parent.setSingleRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParents} to a(n) {@link SingleRefTwoParentsDto}
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @return an equivalent new created {@link SingleRefTwoParentsDto}
	 */
	public static SingleRefTwoParentsDto convertToSingleRefTwoParentsDto(SingleRefTwoParents singleRefTwoParents) {
		return convertToSingleRefTwoParentsDto(singleRefTwoParents, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParents} to a(n) {@link SingleRefTwoParentsDto}
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param mappedObjects       map which contains already mapped objects. If an identification of {@code singleRefTwoParents} is contained, the found
	 *                            {@link SingleRefTwoParentsDto} will be returned
	 * @return an equivalent new created {@link SingleRefTwoParentsDto} or the found one from the given map
	 */
	public static SingleRefTwoParentsDto convertToSingleRefTwoParentsDto(SingleRefTwoParents singleRefTwoParents
			, Map<String, ITransportable> mappedObjects) {
		return convertToDto(singleRefTwoParents, mappedObjects, DtoObjectFactory::createSingleRefTwoParentsDto, (domain, dto) -> getInstance().setSingleRefTwoParentsDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setSingleRefTwoParentsDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParents} to a(n) {@link SingleRefTwoParentsDto} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param parent              the parent of converted result
	 * @return an equivalent new created {@link SingleRefTwoParentsDto}
	 */
	public static SingleRefTwoParentsDto convertToSingleRefTwoParentsDto(SingleRefTwoParents singleRefTwoParents, DerivedFromDomainDto parent) {
		return convertToSingleRefTwoParentsDto(singleRefTwoParents, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParents} to a(n) {@link SingleRefTwoParentsDto} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param parent              the parent of converted result
	 * @param mappedObjects       map which contains already mapped objects. If an identification of {@code singleRefTwoParents} is contained, the found
	 *                            {@link SingleRefTwoParentsDto} will be returned
	 * @return an equivalent new created {@link SingleRefTwoParentsDto} or the found one from the given map
	 */
	public static SingleRefTwoParentsDto convertToSingleRefTwoParentsDto(SingleRefTwoParents singleRefTwoParents, DerivedFromDomainDto parent
			, Map<String, ITransportable> mappedObjects) {
		SingleRefTwoParentsDto result = convertToSingleRefTwoParentsDto(singleRefTwoParents, mappedObjects);
		if (result != null) {
			parent.setSingleRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParents} to a(n) {@link SingleRefTwoParentsDto} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param parent              the parent of converted result
	 * @return an equivalent new created {@link SingleRefTwoParentsDto}
	 */
	public static SingleRefTwoParentsDto convertToSingleRefTwoParentsDto(SingleRefTwoParents singleRefTwoParents, RootDto parent) {
		return convertToSingleRefTwoParentsDto(singleRefTwoParents, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParents} to a(n) {@link SingleRefTwoParentsDto} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param parent              the parent of converted result
	 * @param mappedObjects       map which contains already mapped objects. If an identification of {@code singleRefTwoParents} is contained, the found
	 *                            {@link SingleRefTwoParentsDto} will be returned
	 * @return an equivalent new created {@link SingleRefTwoParentsDto} or the found one from the given map
	 */
	public static SingleRefTwoParentsDto convertToSingleRefTwoParentsDto(SingleRefTwoParents singleRefTwoParents, RootDto parent
			, Map<String, ITransportable> mappedObjects) {
		SingleRefTwoParentsDto result = convertToSingleRefTwoParentsDto(singleRefTwoParents, mappedObjects);
		if (result != null) {
			parent.setAnotherSingleRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParents} to a(n) {@link SingleRefTwoParentsDto} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param parent              the parent of converted result
	 * @return an equivalent new created {@link SingleRefTwoParentsDto}
	 */
	public static SingleRefTwoParentsDto convertToSingleRefTwoParentsDto(SingleRefTwoParents singleRefTwoParents, SingleRefOneParentDto parent) {
		return convertToSingleRefTwoParentsDto(singleRefTwoParents, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefTwoParents} to a(n) {@link SingleRefTwoParentsDto} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param singleRefTwoParents the source object which should be converted
	 * @param parent              the parent of converted result
	 * @param mappedObjects       map which contains already mapped objects. If an identification of {@code singleRefTwoParents} is contained, the found
	 *                            {@link SingleRefTwoParentsDto} will be returned
	 * @return an equivalent new created {@link SingleRefTwoParentsDto} or the found one from the given map
	 */
	public static SingleRefTwoParentsDto convertToSingleRefTwoParentsDto(SingleRefTwoParents singleRefTwoParents, SingleRefOneParentDto parent
			, Map<String, ITransportable> mappedObjects) {
		SingleRefTwoParentsDto result = convertToSingleRefTwoParentsDto(singleRefTwoParents, mappedObjects);
		if (result != null) {
			parent.setSingleRef(result);
		}
		return result;
	}

	/**
	 * @return the singleton
	 */
	public static SingleTransportMapper getInstance() {
		if (instance == null) {
			instance = TransportMapperFactory.createSingleTransportMapper();
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
	protected void setSingleRefOneParentDtoSingleReferences(SingleRefOneParent domain, SingleRefOneParentDto dto
			, Map<String, ITransportable> mappedObjects) {
		SingleTransportMapper.convertToSingleRefTwoParentsDto(domain.getSingleRef(), dto, mappedObjects);
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setSingleRefOneParentDtoValues(SingleRefOneParent domain, SingleRefOneParentDto dto) {
		dto.setDescription(domain.getDescription());
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	protected void setSingleRefOneParentSingleReferences(SingleRefOneParentDto dto, SingleRefOneParent domain, Map<String, IIdentifiable> mappedObjects) {
		SingleTransportMapper.convertToSingleRefTwoParents(dto.getSingleRef(), domain, mappedObjects);
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setSingleRefOneParentValues(SingleRefOneParentDto dto, SingleRefOneParent domain) {
		domain.setDescription(dto.getDescription());
	}

	/**
	 * Adds the references at {@code dto} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dto           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dto}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSingleRefTwoParentsDtoSingleReferences(SingleRefTwoParents domain, SingleRefTwoParentsDto dto
			, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setSingleRefTwoParentsDtoValues(SingleRefTwoParents domain, SingleRefTwoParentsDto dto) {
		dto.setDescription(domain.getDescription());
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSingleRefTwoParentsSingleReferences(SingleRefTwoParentsDto dto, SingleRefTwoParents domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setSingleRefTwoParentsValues(SingleRefTwoParentsDto dto, SingleRefTwoParents domain) {
		domain.setDescription(dto.getDescription());
	}

}
