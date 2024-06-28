package com.github.ma_vin.util.layer_generator.sample.content.mapper;

import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseTransportMapper;
import com.github.ma_vin.util.layer_generator.sample.content.domain.DomainObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.content.domain.multi.MultiRefOneParent;
import com.github.ma_vin.util.layer_generator.sample.content.domain.multi.MultiRefTwoParents;
import com.github.ma_vin.util.layer_generator.sample.content.dto.DtoObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.content.dto.ITransportable;
import com.github.ma_vin.util.layer_generator.sample.content.dto.multi.MultiRefOneParentDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.multi.MultiRefTwoParentsDto;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class which provides methods to convert a data transport to a domain object of sub package <i>multi<i> and the other way around
 */
@BaseTransportMapper
public class MultiTransportMapper extends AbstractTransportMapper {

	/**
	 * singleton
	 */
	private static MultiTransportMapper instance;

	/**
	 * Converts a(n) {@link MultiRefOneParentDto} to a(n) {@link MultiRefOneParent}
	 *
	 * @param multiRefOneParent the source object which should be converted
	 * @return an equivalent new created {@link MultiRefOneParent}
	 */
	public static MultiRefOneParent convertToMultiRefOneParent(MultiRefOneParentDto multiRefOneParent) {
		return convertToMultiRefOneParent(multiRefOneParent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefOneParentDto} to a(n) {@link MultiRefOneParent}
	 *
	 * @param multiRefOneParent the source object which should be converted
	 * @param mappedObjects     map which contains already mapped objects. If an identification of {@code multiRefOneParent} is contained, the found
	 *                          {@link MultiRefOneParent} will be returned
	 * @return an equivalent new created {@link MultiRefOneParent} or the found one from the given map
	 */
	public static MultiRefOneParent convertToMultiRefOneParent(MultiRefOneParentDto multiRefOneParent, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(multiRefOneParent, mappedObjects, DomainObjectFactory::createMultiRefOneParent, (dto, domain) -> getInstance().setMultiRefOneParentValues(dto, domain)
				, (dto, domain) -> getInstance().setMultiRefOneParentSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link MultiRefOneParent} to a(n) {@link MultiRefOneParentDto}
	 *
	 * @param multiRefOneParent the source object which should be converted
	 * @return an equivalent new created {@link MultiRefOneParentDto}
	 */
	public static MultiRefOneParentDto convertToMultiRefOneParentDto(MultiRefOneParent multiRefOneParent) {
		return convertToMultiRefOneParentDto(multiRefOneParent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefOneParent} to a(n) {@link MultiRefOneParentDto}
	 *
	 * @param multiRefOneParent the source object which should be converted
	 * @param mappedObjects     map which contains already mapped objects. If an identification of {@code multiRefOneParent} is contained, the found
	 *                          {@link MultiRefOneParentDto} will be returned
	 * @return an equivalent new created {@link MultiRefOneParentDto} or the found one from the given map
	 */
	public static MultiRefOneParentDto convertToMultiRefOneParentDto(MultiRefOneParent multiRefOneParent, Map<String, ITransportable> mappedObjects) {
		return convertToDto(multiRefOneParent, mappedObjects, DtoObjectFactory::createMultiRefOneParentDto, (domain, dto) -> getInstance().setMultiRefOneParentDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setMultiRefOneParentDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link MultiRefTwoParentsDto} to a(n) {@link MultiRefTwoParents}
	 *
	 * @param multiRefTwoParents the source object which should be converted
	 * @return an equivalent new created {@link MultiRefTwoParents}
	 */
	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDto multiRefTwoParents) {
		return convertToMultiRefTwoParents(multiRefTwoParents, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefTwoParentsDto} to a(n) {@link MultiRefTwoParents}
	 *
	 * @param multiRefTwoParents the source object which should be converted
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code multiRefTwoParents} is contained, the found
	 *                           {@link MultiRefTwoParents} will be returned
	 * @return an equivalent new created {@link MultiRefTwoParents} or the found one from the given map
	 */
	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDto multiRefTwoParents, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(multiRefTwoParents, mappedObjects, DomainObjectFactory::createMultiRefTwoParents, (dto, domain) -> getInstance().setMultiRefTwoParentsValues(dto, domain)
				, (dto, domain) -> getInstance().setMultiRefTwoParentsSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link MultiRefTwoParents} to a(n) {@link MultiRefTwoParentsDto}
	 *
	 * @param multiRefTwoParents the source object which should be converted
	 * @return an equivalent new created {@link MultiRefTwoParentsDto}
	 */
	public static MultiRefTwoParentsDto convertToMultiRefTwoParentsDto(MultiRefTwoParents multiRefTwoParents) {
		return convertToMultiRefTwoParentsDto(multiRefTwoParents, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link MultiRefTwoParents} to a(n) {@link MultiRefTwoParentsDto}
	 *
	 * @param multiRefTwoParents the source object which should be converted
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code multiRefTwoParents} is contained, the found
	 *                           {@link MultiRefTwoParentsDto} will be returned
	 * @return an equivalent new created {@link MultiRefTwoParentsDto} or the found one from the given map
	 */
	public static MultiRefTwoParentsDto convertToMultiRefTwoParentsDto(MultiRefTwoParents multiRefTwoParents, Map<String, ITransportable> mappedObjects) {
		return convertToDto(multiRefTwoParents, mappedObjects, DtoObjectFactory::createMultiRefTwoParentsDto, (domain, dto) -> getInstance().setMultiRefTwoParentsDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setMultiRefTwoParentsDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * @return the singleton
	 */
	public static MultiTransportMapper getInstance() {
		if (instance == null) {
			instance = TransportMapperFactory.createMultiTransportMapper();
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
	protected void setMultiRefOneParentDtoSingleReferences(MultiRefOneParent domain, MultiRefOneParentDto dto, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setMultiRefOneParentDtoValues(MultiRefOneParent domain, MultiRefOneParentDto dto) {
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
	protected void setMultiRefOneParentSingleReferences(MultiRefOneParentDto dto, MultiRefOneParent domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setMultiRefOneParentValues(MultiRefOneParentDto dto, MultiRefOneParent domain) {
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
	protected void setMultiRefTwoParentsDtoSingleReferences(MultiRefTwoParents domain, MultiRefTwoParentsDto dto
			, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setMultiRefTwoParentsDtoValues(MultiRefTwoParents domain, MultiRefTwoParentsDto dto) {
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
	protected void setMultiRefTwoParentsSingleReferences(MultiRefTwoParentsDto dto, MultiRefTwoParents domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setMultiRefTwoParentsValues(MultiRefTwoParentsDto dto, MultiRefTwoParents domain) {
		domain.setDescription(dto.getDescription());
	}

}
