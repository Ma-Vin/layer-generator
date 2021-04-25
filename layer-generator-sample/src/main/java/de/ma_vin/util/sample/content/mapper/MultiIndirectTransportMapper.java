package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseTransportMapper;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.multi.indirect.MultiRefIndirectParent;
import de.ma_vin.util.sample.content.domain.multi.indirect.MultiRefOtherIndirectParent;
import de.ma_vin.util.sample.content.dto.DtoObjectFactory;
import de.ma_vin.util.sample.content.dto.ITransportable;
import de.ma_vin.util.sample.content.dto.multi.indirect.MultiRefIndirectParentDto;
import de.ma_vin.util.sample.content.dto.multi.indirect.MultiRefOtherIndirectParentDto;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class which provides methods to convert a data transport to a domain object of sub package <i>multi.indirect<i> and the other way around
 */
@BaseTransportMapper
public class MultiIndirectTransportMapper extends AbstractTransportMapper {

	/**
	 * singleton
	 */
	private static MultiIndirectTransportMapper instance;

	/**
	 * Converts a(n) {@link MultiRefIndirectParentDto} to a(n) {@link MultiRefIndirectParent}
	 *
	 * @param multiRefIndirectParent the source object which should be converted
	 * @return an equivalent new created {@link MultiRefIndirectParent}
	 */
	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDto multiRefIndirectParent) {
		return convertToMultiRefIndirectParent(multiRefIndirectParent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefIndirectParentDto} to a(n) {@link MultiRefIndirectParent}
	 *
	 * @param multiRefIndirectParent the source object which should be converted
	 * @param mappedObjects          map which contains already mapped objects. If an identification of {@code multiRefIndirectParent} is contained, the
	 *                               found {@link MultiRefIndirectParent} will be returned
	 * @return an equivalent new created {@link MultiRefIndirectParent} or the found one from the given map
	 */
	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDto multiRefIndirectParent
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(multiRefIndirectParent, mappedObjects, DomainObjectFactory::createMultiRefIndirectParent, (dto, domain) -> getInstance().setMultiRefIndirectParentValues(dto, domain)
				, (dto, domain) -> getInstance().setMultiRefIndirectParentSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link MultiRefIndirectParent} to a(n) {@link MultiRefIndirectParentDto}
	 *
	 * @param multiRefIndirectParent the source object which should be converted
	 * @return an equivalent new created {@link MultiRefIndirectParentDto}
	 */
	public static MultiRefIndirectParentDto convertToMultiRefIndirectParentDto(MultiRefIndirectParent multiRefIndirectParent) {
		return convertToMultiRefIndirectParentDto(multiRefIndirectParent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefIndirectParent} to a(n) {@link MultiRefIndirectParentDto}
	 *
	 * @param multiRefIndirectParent the source object which should be converted
	 * @param mappedObjects          map which contains already mapped objects. If an identification of {@code multiRefIndirectParent} is contained, the
	 *                               found {@link MultiRefIndirectParentDto} will be returned
	 * @return an equivalent new created {@link MultiRefIndirectParentDto} or the found one from the given map
	 */
	public static MultiRefIndirectParentDto convertToMultiRefIndirectParentDto(MultiRefIndirectParent multiRefIndirectParent
			, Map<String, ITransportable> mappedObjects) {
		return convertToDto(multiRefIndirectParent, mappedObjects, DtoObjectFactory::createMultiRefIndirectParentDto, (domain, dto) -> getInstance().setMultiRefIndirectParentDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setMultiRefIndirectParentDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link MultiRefOtherIndirectParentDto} to a(n) {@link MultiRefOtherIndirectParent}
	 *
	 * @param multiRefOtherIndirectParent the source object which should be converted
	 * @return an equivalent new created {@link MultiRefOtherIndirectParent}
	 */
	public static MultiRefOtherIndirectParent convertToMultiRefOtherIndirectParent(MultiRefOtherIndirectParentDto multiRefOtherIndirectParent) {
		return convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefOtherIndirectParentDto} to a(n) {@link MultiRefOtherIndirectParent}
	 *
	 * @param multiRefOtherIndirectParent the source object which should be converted
	 * @param mappedObjects               map which contains already mapped objects. If an identification of {@code multiRefOtherIndirectParent} is
	 *                                    contained, the found {@link MultiRefOtherIndirectParent} will be returned
	 * @return an equivalent new created {@link MultiRefOtherIndirectParent} or the found one from the given map
	 */
	public static MultiRefOtherIndirectParent convertToMultiRefOtherIndirectParent(MultiRefOtherIndirectParentDto multiRefOtherIndirectParent
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(multiRefOtherIndirectParent, mappedObjects, DomainObjectFactory::createMultiRefOtherIndirectParent, (dto, domain) -> getInstance().setMultiRefOtherIndirectParentValues(dto, domain)
				, (dto, domain) -> getInstance().setMultiRefOtherIndirectParentSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link MultiRefOtherIndirectParent} to a(n) {@link MultiRefOtherIndirectParentDto}
	 *
	 * @param multiRefOtherIndirectParent the source object which should be converted
	 * @return an equivalent new created {@link MultiRefOtherIndirectParentDto}
	 */
	public static MultiRefOtherIndirectParentDto convertToMultiRefOtherIndirectParentDto(MultiRefOtherIndirectParent multiRefOtherIndirectParent) {
		return convertToMultiRefOtherIndirectParentDto(multiRefOtherIndirectParent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefOtherIndirectParent} to a(n) {@link MultiRefOtherIndirectParentDto}
	 *
	 * @param multiRefOtherIndirectParent the source object which should be converted
	 * @param mappedObjects               map which contains already mapped objects. If an identification of {@code multiRefOtherIndirectParent} is
	 *                                    contained, the found {@link MultiRefOtherIndirectParentDto} will be returned
	 * @return an equivalent new created {@link MultiRefOtherIndirectParentDto} or the found one from the given map
	 */
	public static MultiRefOtherIndirectParentDto convertToMultiRefOtherIndirectParentDto(MultiRefOtherIndirectParent multiRefOtherIndirectParent
			, Map<String, ITransportable> mappedObjects) {
		return convertToDto(multiRefOtherIndirectParent, mappedObjects, DtoObjectFactory::createMultiRefOtherIndirectParentDto, (domain, dto) -> getInstance().setMultiRefOtherIndirectParentDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setMultiRefOtherIndirectParentDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * @return the singleton
	 */
	public static MultiIndirectTransportMapper getInstance() {
		if (instance == null) {
			instance = TransportMapperFactory.createMultiIndirectTransportMapper();
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
	protected void setMultiRefIndirectParentDtoSingleReferences(MultiRefIndirectParent domain, MultiRefIndirectParentDto dto
			, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setMultiRefIndirectParentDtoValues(MultiRefIndirectParent domain, MultiRefIndirectParentDto dto) {
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
	protected void setMultiRefIndirectParentSingleReferences(MultiRefIndirectParentDto dto, MultiRefIndirectParent domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setMultiRefIndirectParentValues(MultiRefIndirectParentDto dto, MultiRefIndirectParent domain) {
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
	protected void setMultiRefOtherIndirectParentDtoSingleReferences(MultiRefOtherIndirectParent domain, MultiRefOtherIndirectParentDto dto
			, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setMultiRefOtherIndirectParentDtoValues(MultiRefOtherIndirectParent domain, MultiRefOtherIndirectParentDto dto) {
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
	protected void setMultiRefOtherIndirectParentSingleReferences(MultiRefOtherIndirectParentDto dto, MultiRefOtherIndirectParent domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setMultiRefOtherIndirectParentValues(MultiRefOtherIndirectParentDto dto, MultiRefOtherIndirectParent domain) {
		domain.setDescription(dto.getDescription());
	}

}
