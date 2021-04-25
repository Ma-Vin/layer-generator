package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseTransportMapper;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.single.indirect.SingleRefIndirectParent;
import de.ma_vin.util.sample.content.domain.single.indirect.SingleRefOtherIndirectParent;
import de.ma_vin.util.sample.content.dto.DtoObjectFactory;
import de.ma_vin.util.sample.content.dto.ITransportable;
import de.ma_vin.util.sample.content.dto.RootDto;
import de.ma_vin.util.sample.content.dto.single.indirect.SingleRefIndirectParentDto;
import de.ma_vin.util.sample.content.dto.single.indirect.SingleRefOtherIndirectParentDto;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class which provides methods to convert a data transport to a domain object of sub package <i>single.indirect<i> and the other way around
 */
@BaseTransportMapper
public class SingleIndirectTransportMapper extends AbstractTransportMapper {

	/**
	 * singleton
	 */
	private static SingleIndirectTransportMapper instance;

	/**
	 * Converts a(n) {@link SingleRefIndirectParentDto} to a(n) {@link SingleRefIndirectParent}
	 *
	 * @param singleRefIndirectParent the source object which should be converted
	 * @return an equivalent new created {@link SingleRefIndirectParent}
	 */
	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDto singleRefIndirectParent) {
		return convertToSingleRefIndirectParent(singleRefIndirectParent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefIndirectParentDto} to a(n) {@link SingleRefIndirectParent}
	 *
	 * @param singleRefIndirectParent the source object which should be converted
	 * @param mappedObjects           map which contains already mapped objects. If an identification of {@code singleRefIndirectParent} is contained, the
	 *                                found {@link SingleRefIndirectParent} will be returned
	 * @return an equivalent new created {@link SingleRefIndirectParent} or the found one from the given map
	 */
	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDto singleRefIndirectParent
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(singleRefIndirectParent, mappedObjects, DomainObjectFactory::createSingleRefIndirectParent, (dto, domain) -> getInstance().setSingleRefIndirectParentValues(dto, domain)
				, (dto, domain) -> getInstance().setSingleRefIndirectParentSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link SingleRefIndirectParentDto} to a(n) {@link SingleRefIndirectParent} and sets the result to the corresponding reference
	 * property at the parent
	 *
	 * @param singleRefIndirectParent the source object which should be converted
	 * @param parent                  the parent of converted result
	 * @return an equivalent new created {@link SingleRefIndirectParent}
	 */
	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDto singleRefIndirectParent, Root parent) {
		return convertToSingleRefIndirectParent(singleRefIndirectParent, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefIndirectParentDto} to a(n) {@link SingleRefIndirectParent} and sets the result to the corresponding reference
	 * property at the parent
	 *
	 * @param singleRefIndirectParent the source object which should be converted
	 * @param parent                  the parent of converted result
	 * @param mappedObjects           map which contains already mapped objects. If an identification of {@code singleRefIndirectParent} is contained, the
	 *                                found {@link SingleRefIndirectParent} will be returned
	 * @return an equivalent new created {@link SingleRefIndirectParent} or the found one from the given map
	 */
	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDto singleRefIndirectParent, Root parent
			, Map<String, IIdentifiable> mappedObjects) {
		SingleRefIndirectParent result = convertToSingleRefIndirectParent(singleRefIndirectParent, mappedObjects);
		if (result != null) {
			parent.setSingleRefIndirectParent(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefIndirectParent} to a(n) {@link SingleRefIndirectParentDto}
	 *
	 * @param singleRefIndirectParent the source object which should be converted
	 * @return an equivalent new created {@link SingleRefIndirectParentDto}
	 */
	public static SingleRefIndirectParentDto convertToSingleRefIndirectParentDto(SingleRefIndirectParent singleRefIndirectParent) {
		return convertToSingleRefIndirectParentDto(singleRefIndirectParent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefIndirectParent} to a(n) {@link SingleRefIndirectParentDto}
	 *
	 * @param singleRefIndirectParent the source object which should be converted
	 * @param mappedObjects           map which contains already mapped objects. If an identification of {@code singleRefIndirectParent} is contained, the
	 *                                found {@link SingleRefIndirectParentDto} will be returned
	 * @return an equivalent new created {@link SingleRefIndirectParentDto} or the found one from the given map
	 */
	public static SingleRefIndirectParentDto convertToSingleRefIndirectParentDto(SingleRefIndirectParent singleRefIndirectParent
			, Map<String, ITransportable> mappedObjects) {
		return convertToDto(singleRefIndirectParent, mappedObjects, DtoObjectFactory::createSingleRefIndirectParentDto, (domain, dto) -> getInstance().setSingleRefIndirectParentDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setSingleRefIndirectParentDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link SingleRefIndirectParent} to a(n) {@link SingleRefIndirectParentDto} and sets the result to the corresponding reference
	 * property at the parent
	 *
	 * @param singleRefIndirectParent the source object which should be converted
	 * @param parent                  the parent of converted result
	 * @return an equivalent new created {@link SingleRefIndirectParentDto}
	 */
	public static SingleRefIndirectParentDto convertToSingleRefIndirectParentDto(SingleRefIndirectParent singleRefIndirectParent, RootDto parent) {
		return convertToSingleRefIndirectParentDto(singleRefIndirectParent, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefIndirectParent} to a(n) {@link SingleRefIndirectParentDto} and sets the result to the corresponding reference
	 * property at the parent
	 *
	 * @param singleRefIndirectParent the source object which should be converted
	 * @param parent                  the parent of converted result
	 * @param mappedObjects           map which contains already mapped objects. If an identification of {@code singleRefIndirectParent} is contained, the
	 *                                found {@link SingleRefIndirectParentDto} will be returned
	 * @return an equivalent new created {@link SingleRefIndirectParentDto} or the found one from the given map
	 */
	public static SingleRefIndirectParentDto convertToSingleRefIndirectParentDto(SingleRefIndirectParent singleRefIndirectParent, RootDto parent
			, Map<String, ITransportable> mappedObjects) {
		SingleRefIndirectParentDto result = convertToSingleRefIndirectParentDto(singleRefIndirectParent, mappedObjects);
		if (result != null) {
			parent.setSingleRefIndirectParent(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefOtherIndirectParentDto} to a(n) {@link SingleRefOtherIndirectParent}
	 *
	 * @param singleRefOtherIndirectParent the source object which should be converted
	 * @return an equivalent new created {@link SingleRefOtherIndirectParent}
	 */
	public static SingleRefOtherIndirectParent convertToSingleRefOtherIndirectParent(SingleRefOtherIndirectParentDto singleRefOtherIndirectParent) {
		return convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefOtherIndirectParentDto} to a(n) {@link SingleRefOtherIndirectParent}
	 *
	 * @param singleRefOtherIndirectParent the source object which should be converted
	 * @param mappedObjects                map which contains already mapped objects. If an identification of {@code singleRefOtherIndirectParent} is
	 *                                     contained, the found {@link SingleRefOtherIndirectParent} will be returned
	 * @return an equivalent new created {@link SingleRefOtherIndirectParent} or the found one from the given map
	 */
	public static SingleRefOtherIndirectParent convertToSingleRefOtherIndirectParent(SingleRefOtherIndirectParentDto singleRefOtherIndirectParent
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(singleRefOtherIndirectParent, mappedObjects, DomainObjectFactory::createSingleRefOtherIndirectParent, (dto, domain) -> getInstance().setSingleRefOtherIndirectParentValues(dto, domain)
				, (dto, domain) -> getInstance().setSingleRefOtherIndirectParentSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link SingleRefOtherIndirectParentDto} to a(n) {@link SingleRefOtherIndirectParent} and sets the result to the corresponding
	 * reference property at the parent
	 *
	 * @param singleRefOtherIndirectParent the source object which should be converted
	 * @param parent                       the parent of converted result
	 * @return an equivalent new created {@link SingleRefOtherIndirectParent}
	 */
	public static SingleRefOtherIndirectParent convertToSingleRefOtherIndirectParent(SingleRefOtherIndirectParentDto singleRefOtherIndirectParent
			, Root parent) {
		return convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParent, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefOtherIndirectParentDto} to a(n) {@link SingleRefOtherIndirectParent} and sets the result to the corresponding
	 * reference property at the parent
	 *
	 * @param singleRefOtherIndirectParent the source object which should be converted
	 * @param parent                       the parent of converted result
	 * @param mappedObjects                map which contains already mapped objects. If an identification of {@code singleRefOtherIndirectParent} is
	 *                                     contained, the found {@link SingleRefOtherIndirectParent} will be returned
	 * @return an equivalent new created {@link SingleRefOtherIndirectParent} or the found one from the given map
	 */
	public static SingleRefOtherIndirectParent convertToSingleRefOtherIndirectParent(SingleRefOtherIndirectParentDto singleRefOtherIndirectParent
			, Root parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefOtherIndirectParent result = convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParent, mappedObjects);
		if (result != null) {
			parent.setSingleRefIndirectOtherParent(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefOtherIndirectParent} to a(n) {@link SingleRefOtherIndirectParentDto}
	 *
	 * @param singleRefOtherIndirectParent the source object which should be converted
	 * @return an equivalent new created {@link SingleRefOtherIndirectParentDto}
	 */
	public static SingleRefOtherIndirectParentDto convertToSingleRefOtherIndirectParentDto(SingleRefOtherIndirectParent singleRefOtherIndirectParent) {
		return convertToSingleRefOtherIndirectParentDto(singleRefOtherIndirectParent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefOtherIndirectParent} to a(n) {@link SingleRefOtherIndirectParentDto}
	 *
	 * @param singleRefOtherIndirectParent the source object which should be converted
	 * @param mappedObjects                map which contains already mapped objects. If an identification of {@code singleRefOtherIndirectParent} is
	 *                                     contained, the found {@link SingleRefOtherIndirectParentDto} will be returned
	 * @return an equivalent new created {@link SingleRefOtherIndirectParentDto} or the found one from the given map
	 */
	public static SingleRefOtherIndirectParentDto convertToSingleRefOtherIndirectParentDto(SingleRefOtherIndirectParent singleRefOtherIndirectParent
			, Map<String, ITransportable> mappedObjects) {
		return convertToDto(singleRefOtherIndirectParent, mappedObjects, DtoObjectFactory::createSingleRefOtherIndirectParentDto, (domain, dto) -> getInstance().setSingleRefOtherIndirectParentDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setSingleRefOtherIndirectParentDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link SingleRefOtherIndirectParent} to a(n) {@link SingleRefOtherIndirectParentDto} and sets the result to the corresponding
	 * reference property at the parent
	 *
	 * @param singleRefOtherIndirectParent the source object which should be converted
	 * @param parent                       the parent of converted result
	 * @return an equivalent new created {@link SingleRefOtherIndirectParentDto}
	 */
	public static SingleRefOtherIndirectParentDto convertToSingleRefOtherIndirectParentDto(SingleRefOtherIndirectParent singleRefOtherIndirectParent
			, RootDto parent) {
		return convertToSingleRefOtherIndirectParentDto(singleRefOtherIndirectParent, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefOtherIndirectParent} to a(n) {@link SingleRefOtherIndirectParentDto} and sets the result to the corresponding
	 * reference property at the parent
	 *
	 * @param singleRefOtherIndirectParent the source object which should be converted
	 * @param parent                       the parent of converted result
	 * @param mappedObjects                map which contains already mapped objects. If an identification of {@code singleRefOtherIndirectParent} is
	 *                                     contained, the found {@link SingleRefOtherIndirectParentDto} will be returned
	 * @return an equivalent new created {@link SingleRefOtherIndirectParentDto} or the found one from the given map
	 */
	public static SingleRefOtherIndirectParentDto convertToSingleRefOtherIndirectParentDto(SingleRefOtherIndirectParent singleRefOtherIndirectParent
			, RootDto parent, Map<String, ITransportable> mappedObjects) {
		SingleRefOtherIndirectParentDto result = convertToSingleRefOtherIndirectParentDto(singleRefOtherIndirectParent, mappedObjects);
		if (result != null) {
			parent.setSingleRefIndirectOtherParent(result);
		}
		return result;
	}

	/**
	 * @return the singleton
	 */
	public static SingleIndirectTransportMapper getInstance() {
		if (instance == null) {
			instance = TransportMapperFactory.createSingleIndirectTransportMapper();
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
	protected void setSingleRefIndirectParentDtoSingleReferences(SingleRefIndirectParent domain, SingleRefIndirectParentDto dto
			, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setSingleRefIndirectParentDtoValues(SingleRefIndirectParent domain, SingleRefIndirectParentDto dto) {
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
	protected void setSingleRefIndirectParentSingleReferences(SingleRefIndirectParentDto dto, SingleRefIndirectParent domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setSingleRefIndirectParentValues(SingleRefIndirectParentDto dto, SingleRefIndirectParent domain) {
		domain.setDescription(dto.getDescription());
	}

	/**
	 * Adds the references at {@code dto} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dto           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dto}
	 */
	protected void setSingleRefOtherIndirectParentDtoSingleReferences(SingleRefOtherIndirectParent domain, SingleRefOtherIndirectParentDto dto
			, Map<String, ITransportable> mappedObjects) {
		dto.setSingleIndirectRef(SingleIndirectTransportMapper.convertToSingleRefIndirectParentDto(domain.getSingleIndirectRef(), mappedObjects));
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setSingleRefOtherIndirectParentDtoValues(SingleRefOtherIndirectParent domain, SingleRefOtherIndirectParentDto dto) {
		dto.setDescription(domain.getDescription());
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	protected void setSingleRefOtherIndirectParentSingleReferences(SingleRefOtherIndirectParentDto dto, SingleRefOtherIndirectParent domain
			, Map<String, IIdentifiable> mappedObjects) {
		domain.setSingleIndirectRef(SingleIndirectTransportMapper.convertToSingleRefIndirectParent(dto.getSingleIndirectRef(), mappedObjects));
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setSingleRefOtherIndirectParentValues(SingleRefOtherIndirectParentDto dto, SingleRefOtherIndirectParent domain) {
		domain.setDescription(dto.getDescription());
	}

}
