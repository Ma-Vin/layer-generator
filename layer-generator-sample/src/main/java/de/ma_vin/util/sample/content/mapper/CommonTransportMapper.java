package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseTransportMapper;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.RootExt;
import de.ma_vin.util.sample.content.dto.DtoObjectFactory;
import de.ma_vin.util.sample.content.dto.ITransportable;
import de.ma_vin.util.sample.content.dto.RootDto;
import de.ma_vin.util.sample.content.dto.RootExtDto;
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
	 * Converts a(n) {@link RootDto} to a(n) {@link Root}
	 *
	 * @param root the source object which should be converted
	 * @return an equivalent new created {@link Root}
	 */
	public static Root convertToRoot(RootDto root) {
		return convertToRoot(root, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link RootDto} to a(n) {@link Root}
	 *
	 * @param root          the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code root} is contained, the found {@link Root} will be
	 *                      returned
	 * @return an equivalent new created {@link Root} or the found one from the given map
	 */
	public static Root convertToRoot(RootDto root, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(root, mappedObjects, DomainObjectFactory::createRoot, (dto, domain) -> getInstance().setRootValues(dto, domain)
				, (dto, domain) -> getInstance().setRootSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link Root} to a(n) {@link RootDto}
	 *
	 * @param root the source object which should be converted
	 * @return an equivalent new created {@link RootDto}
	 */
	public static RootDto convertToRootDto(Root root) {
		return convertToRootDto(root, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link Root} to a(n) {@link RootDto}
	 *
	 * @param root          the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code root} is contained, the found {@link RootDto} will
	 *                      be returned
	 * @return an equivalent new created {@link RootDto} or the found one from the given map
	 */
	public static RootDto convertToRootDto(Root root, Map<String, ITransportable> mappedObjects) {
		return convertToDto(root, mappedObjects, DtoObjectFactory::createRootDto, (domain, dto) -> getInstance().setRootDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setRootDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link RootExtDto} to a(n) {@link RootExt}
	 *
	 * @param rootExt the source object which should be converted
	 * @return an equivalent new created {@link RootExt}
	 */
	public static RootExt convertToRootExt(RootExtDto rootExt) {
		return convertToRootExt(rootExt, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link RootExtDto} to a(n) {@link RootExt}
	 *
	 * @param rootExt       the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code rootExt} is contained, the found {@link RootExt}
	 *                      will be returned
	 * @return an equivalent new created {@link RootExt} or the found one from the given map
	 */
	public static RootExt convertToRootExt(RootExtDto rootExt, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(rootExt, mappedObjects, DomainObjectFactory::createRootExt, (dto, domain) -> getInstance().setRootExtValues(dto, domain)
				, (dto, domain) -> getInstance().setRootExtSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link RootExtDto} to a(n) {@link RootExt} and sets the result to the corresponding reference property at the parent
	 *
	 * @param rootExt the source object which should be converted
	 * @param parent  the parent of converted result
	 * @return an equivalent new created {@link RootExt}
	 */
	public static RootExt convertToRootExt(RootExtDto rootExt, Root parent) {
		return convertToRootExt(rootExt, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link RootExtDto} to a(n) {@link RootExt} and sets the result to the corresponding reference property at the parent
	 *
	 * @param rootExt       the source object which should be converted
	 * @param parent        the parent of converted result
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code rootExt} is contained, the found {@link RootExt}
	 *                      will be returned
	 * @return an equivalent new created {@link RootExt} or the found one from the given map
	 */
	public static RootExt convertToRootExt(RootExtDto rootExt, Root parent, Map<String, IIdentifiable> mappedObjects) {
		RootExt result = convertToRootExt(rootExt, mappedObjects);
		if (result != null) {
			parent.setExt(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link RootExt} to a(n) {@link RootExtDto}
	 *
	 * @param rootExt the source object which should be converted
	 * @return an equivalent new created {@link RootExtDto}
	 */
	public static RootExtDto convertToRootExtDto(RootExt rootExt) {
		return convertToRootExtDto(rootExt, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link RootExt} to a(n) {@link RootExtDto}
	 *
	 * @param rootExt       the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code rootExt} is contained, the found {@link RootExtDto}
	 *                      will be returned
	 * @return an equivalent new created {@link RootExtDto} or the found one from the given map
	 */
	public static RootExtDto convertToRootExtDto(RootExt rootExt, Map<String, ITransportable> mappedObjects) {
		return convertToDto(rootExt, mappedObjects, DtoObjectFactory::createRootExtDto, (domain, dto) -> getInstance().setRootExtDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setRootExtDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link RootExt} to a(n) {@link RootExtDto} and sets the result to the corresponding reference property at the parent
	 *
	 * @param rootExt the source object which should be converted
	 * @param parent  the parent of converted result
	 * @return an equivalent new created {@link RootExtDto}
	 */
	public static RootExtDto convertToRootExtDto(RootExt rootExt, RootDto parent) {
		return convertToRootExtDto(rootExt, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link RootExt} to a(n) {@link RootExtDto} and sets the result to the corresponding reference property at the parent
	 *
	 * @param rootExt       the source object which should be converted
	 * @param parent        the parent of converted result
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code rootExt} is contained, the found {@link RootExtDto}
	 *                      will be returned
	 * @return an equivalent new created {@link RootExtDto} or the found one from the given map
	 */
	public static RootExtDto convertToRootExtDto(RootExt rootExt, RootDto parent, Map<String, ITransportable> mappedObjects) {
		RootExtDto result = convertToRootExtDto(rootExt, mappedObjects);
		if (result != null) {
			parent.setExt(result);
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
	protected void setRootDtoSingleReferences(Root domain, RootDto dto, Map<String, ITransportable> mappedObjects) {
		SingleTransportMapper.convertToSingleRefOneParentDto(domain.getSingleRef(), dto, mappedObjects);
		SingleTransportMapper.convertToSingleRefTwoParentsDto(domain.getAnotherSingleRef(), dto, mappedObjects);
		SingleIndirectTransportMapper.convertToSingleRefIndirectParentDto(domain.getSingleRefIndirectParent(), dto, mappedObjects);
		SingleIndirectTransportMapper.convertToSingleRefOtherIndirectParentDto(domain.getSingleRefIndirectOtherParent(), dto, mappedObjects);
		FilteringTransportMapper.convertToSomeFilteringOwnerDto(domain.getFiltering(), dto, mappedObjects);
		FilteringTransportMapper.convertToSomeDifferentFilteringNotOwnerDto(domain.getNonOwnerFiltering(), dto, mappedObjects);
		CommonTransportMapper.convertToRootExtDto(domain.getExt(), dto, mappedObjects);
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setRootDtoValues(Root domain, RootDto dto) {
		dto.setRootName(domain.getRootName());
		dto.setDescription(domain.getDescription());
	}

	/**
	 * Adds the references at {@code dto} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dto           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dto}
	 */
	@SuppressWarnings("java:S1186")
	protected void setRootExtDtoSingleReferences(RootExt domain, RootExtDto dto, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setRootExtDtoValues(RootExt domain, RootExtDto dto) {
		dto.setExtendedInfo(domain.getExtendedInfo());
		dto.setSomeEnum(domain.getSomeEnum());
		dto.setSomeInteger(domain.getSomeInteger());
		dto.setSomeCustom(domain.getSomeCustom());
		dto.setDtoAndDomain(domain.getDtoAndDomain());
		dto.setTextWithDaoInfo(domain.getTextWithDaoInfo());
		dto.setNumberWithDaoInfo(domain.getNumberWithDaoInfo());
		dto.setDaoEnum(domain.getDaoEnum());
		dto.setDaoEnumWithText(domain.getDaoEnumWithText());
		dto.setSomeName(domain.getSomeName());
		dto.setDocument(domain.getDocument());
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setRootExtSingleReferences(RootExtDto dto, RootExt domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setRootExtValues(RootExtDto dto, RootExt domain) {
		domain.setExtendedInfo(dto.getExtendedInfo());
		domain.setSomeEnum(dto.getSomeEnum());
		domain.setSomeInteger(dto.getSomeInteger());
		domain.setSomeCustom(dto.getSomeCustom());
		domain.setDtoAndDomain(dto.getDtoAndDomain());
		domain.setTextWithDaoInfo(dto.getTextWithDaoInfo());
		domain.setNumberWithDaoInfo(dto.getNumberWithDaoInfo());
		domain.setDaoEnum(dto.getDaoEnum());
		domain.setDaoEnumWithText(dto.getDaoEnumWithText());
		domain.setSomeName(dto.getSomeName());
		domain.setDocument(dto.getDocument());
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	protected void setRootSingleReferences(RootDto dto, Root domain, Map<String, IIdentifiable> mappedObjects) {
		SingleTransportMapper.convertToSingleRefOneParent(dto.getSingleRef(), domain, mappedObjects);
		SingleTransportMapper.convertToSingleRefTwoParents(dto.getAnotherSingleRef(), domain, mappedObjects);
		SingleIndirectTransportMapper.convertToSingleRefIndirectParent(dto.getSingleRefIndirectParent(), domain, mappedObjects);
		SingleIndirectTransportMapper.convertToSingleRefOtherIndirectParent(dto.getSingleRefIndirectOtherParent(), domain, mappedObjects);
		FilteringTransportMapper.convertToSomeFilteringOwner(dto.getFiltering(), domain, mappedObjects);
		FilteringTransportMapper.convertToSomeDifferentFilteringNotOwner(dto.getNonOwnerFiltering(), domain, mappedObjects);
		CommonTransportMapper.convertToRootExt(dto.getExt(), domain, mappedObjects);
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setRootValues(RootDto dto, Root domain) {
		domain.setRootName(dto.getRootName());
		domain.setDescription(dto.getDescription());
	}

}
