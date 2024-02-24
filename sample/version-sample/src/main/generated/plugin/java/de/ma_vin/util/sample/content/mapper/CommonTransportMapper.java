package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseTransportMapper;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.RootExt;
import de.ma_vin.util.sample.content.domain.SingleRefOne;
import de.ma_vin.util.sample.content.domain.SingleRefTwo;
import de.ma_vin.util.sample.content.dto.DtoObjectFactory;
import de.ma_vin.util.sample.content.dto.ITransportable;
import de.ma_vin.util.sample.content.dto.RootDto;
import de.ma_vin.util.sample.content.dto.RootExtDto;
import de.ma_vin.util.sample.content.dto.RootExtV1Dto;
import de.ma_vin.util.sample.content.dto.RootV1Dto;
import de.ma_vin.util.sample.content.dto.RootV2Dto;
import de.ma_vin.util.sample.content.dto.SingleRefOneDto;
import de.ma_vin.util.sample.content.dto.SingleRefTwoDto;
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
	 * Converts a(n) {@link RootV1Dto} to a(n) {@link Root}
	 *
	 * @param rootV1 the source object which should be converted
	 * @return an equivalent new created {@link Root}
	 */
	public static Root convertToRoot(RootV1Dto rootV1) {
		return convertToRoot(rootV1, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link RootV1Dto} to a(n) {@link Root}
	 *
	 * @param rootV1        the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code rootV1} is contained, the found {@link Root} will be
	 *                      returned
	 * @return an equivalent new created {@link Root} or the found one from the given map
	 */
	public static Root convertToRoot(RootV1Dto rootV1, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(rootV1, mappedObjects, DomainObjectFactory::createRoot, (dto, domain) -> getInstance().setRootValues(dto, domain)
				, (dto, domain) -> getInstance().setRootSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link RootV2Dto} to a(n) {@link Root}
	 *
	 * @param rootV2 the source object which should be converted
	 * @return an equivalent new created {@link Root}
	 */
	public static Root convertToRoot(RootV2Dto rootV2) {
		return convertToRoot(rootV2, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link RootV2Dto} to a(n) {@link Root}
	 *
	 * @param rootV2        the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code rootV2} is contained, the found {@link Root} will be
	 *                      returned
	 * @return an equivalent new created {@link Root} or the found one from the given map
	 */
	public static Root convertToRoot(RootV2Dto rootV2, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(rootV2, mappedObjects, DomainObjectFactory::createRoot, (dto, domain) -> getInstance().setRootValues(dto, domain)
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
	 * Converts a(n) {@link RootExtV1Dto} to a(n) {@link RootExt}
	 *
	 * @param rootExtV1 the source object which should be converted
	 * @return an equivalent new created {@link RootExt}
	 */
	public static RootExt convertToRootExt(RootExtV1Dto rootExtV1) {
		return convertToRootExt(rootExtV1, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link RootExtV1Dto} to a(n) {@link RootExt}
	 *
	 * @param rootExtV1     the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code rootExtV1} is contained, the found {@link RootExt}
	 *                      will be returned
	 * @return an equivalent new created {@link RootExt} or the found one from the given map
	 */
	public static RootExt convertToRootExt(RootExtV1Dto rootExtV1, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(rootExtV1, mappedObjects, DomainObjectFactory::createRootExt, (dto, domain) -> getInstance().setRootExtValues(dto, domain)
				, (dto, domain) -> getInstance().setRootExtSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
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
	 * Converts a(n) {@link RootExt} to a(n) {@link RootExtV1Dto}
	 *
	 * @param rootExt the source object which should be converted
	 * @return an equivalent new created {@link RootExtV1Dto}
	 */
	public static RootExtV1Dto convertToRootExtV1Dto(RootExt rootExt) {
		return convertToRootExtV1Dto(rootExt, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link RootExt} to a(n) {@link RootExtV1Dto}
	 *
	 * @param rootExt       the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code rootExt} is contained, the found
	 *                      {@link RootExtV1Dto} will be returned
	 * @return an equivalent new created {@link RootExtV1Dto} or the found one from the given map
	 */
	public static RootExtV1Dto convertToRootExtV1Dto(RootExt rootExt, Map<String, ITransportable> mappedObjects) {
		return convertToDto(rootExt, mappedObjects, DtoObjectFactory::createRootExtV1Dto, (domain, dto) -> getInstance().setRootExtV1DtoValues(domain, dto)
				, (domain, dto) -> getInstance().setRootExtV1DtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link Root} to a(n) {@link RootV1Dto}
	 *
	 * @param root the source object which should be converted
	 * @return an equivalent new created {@link RootV1Dto}
	 */
	public static RootV1Dto convertToRootV1Dto(Root root) {
		return convertToRootV1Dto(root, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link Root} to a(n) {@link RootV1Dto}
	 *
	 * @param root          the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code root} is contained, the found {@link RootV1Dto} will
	 *                      be returned
	 * @return an equivalent new created {@link RootV1Dto} or the found one from the given map
	 */
	public static RootV1Dto convertToRootV1Dto(Root root, Map<String, ITransportable> mappedObjects) {
		return convertToDto(root, mappedObjects, DtoObjectFactory::createRootV1Dto, (domain, dto) -> getInstance().setRootV1DtoValues(domain, dto)
				, (domain, dto) -> getInstance().setRootV1DtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link Root} to a(n) {@link RootV2Dto}
	 *
	 * @param root the source object which should be converted
	 * @return an equivalent new created {@link RootV2Dto}
	 */
	public static RootV2Dto convertToRootV2Dto(Root root) {
		return convertToRootV2Dto(root, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link Root} to a(n) {@link RootV2Dto}
	 *
	 * @param root          the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code root} is contained, the found {@link RootV2Dto} will
	 *                      be returned
	 * @return an equivalent new created {@link RootV2Dto} or the found one from the given map
	 */
	public static RootV2Dto convertToRootV2Dto(Root root, Map<String, ITransportable> mappedObjects) {
		return convertToDto(root, mappedObjects, DtoObjectFactory::createRootV2Dto, (domain, dto) -> getInstance().setRootV2DtoValues(domain, dto)
				, (domain, dto) -> getInstance().setRootV2DtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link SingleRefOneDto} to a(n) {@link SingleRefOne}
	 *
	 * @param singleRefOne the source object which should be converted
	 * @return an equivalent new created {@link SingleRefOne}
	 */
	public static SingleRefOne convertToSingleRefOne(SingleRefOneDto singleRefOne) {
		return convertToSingleRefOne(singleRefOne, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefOneDto} to a(n) {@link SingleRefOne}
	 *
	 * @param singleRefOne  the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code singleRefOne} is contained, the found
	 *                      {@link SingleRefOne} will be returned
	 * @return an equivalent new created {@link SingleRefOne} or the found one from the given map
	 */
	public static SingleRefOne convertToSingleRefOne(SingleRefOneDto singleRefOne, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(singleRefOne, mappedObjects, DomainObjectFactory::createSingleRefOne, (dto, domain) -> getInstance().setSingleRefOneValues(dto, domain)
				, (dto, domain) -> getInstance().setSingleRefOneSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link SingleRefOneDto} to a(n) {@link SingleRefOne} and sets the result to the corresponding reference property at the parent
	 *
	 * @param singleRefOne the source object which should be converted
	 * @param parent       the parent of converted result
	 * @return an equivalent new created {@link SingleRefOne}
	 */
	public static SingleRefOne convertToSingleRefOne(SingleRefOneDto singleRefOne, Root parent) {
		return convertToSingleRefOne(singleRefOne, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefOneDto} to a(n) {@link SingleRefOne} and sets the result to the corresponding reference property at the parent
	 *
	 * @param singleRefOne  the source object which should be converted
	 * @param parent        the parent of converted result
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code singleRefOne} is contained, the found
	 *                      {@link SingleRefOne} will be returned
	 * @return an equivalent new created {@link SingleRefOne} or the found one from the given map
	 */
	public static SingleRefOne convertToSingleRefOne(SingleRefOneDto singleRefOne, Root parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefOne result = convertToSingleRefOne(singleRefOne, mappedObjects);
		if (result != null) {
			parent.setSingleRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefOne} to a(n) {@link SingleRefOneDto}
	 *
	 * @param singleRefOne the source object which should be converted
	 * @return an equivalent new created {@link SingleRefOneDto}
	 */
	public static SingleRefOneDto convertToSingleRefOneDto(SingleRefOne singleRefOne) {
		return convertToSingleRefOneDto(singleRefOne, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefOne} to a(n) {@link SingleRefOneDto}
	 *
	 * @param singleRefOne  the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code singleRefOne} is contained, the found
	 *                      {@link SingleRefOneDto} will be returned
	 * @return an equivalent new created {@link SingleRefOneDto} or the found one from the given map
	 */
	public static SingleRefOneDto convertToSingleRefOneDto(SingleRefOne singleRefOne, Map<String, ITransportable> mappedObjects) {
		return convertToDto(singleRefOne, mappedObjects, DtoObjectFactory::createSingleRefOneDto, (domain, dto) -> getInstance().setSingleRefOneDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setSingleRefOneDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link SingleRefOne} to a(n) {@link SingleRefOneDto} and sets the result to the corresponding reference property at the parent
	 *
	 * @param singleRefOne the source object which should be converted
	 * @param parent       the parent of converted result
	 * @return an equivalent new created {@link SingleRefOneDto}
	 */
	public static SingleRefOneDto convertToSingleRefOneDto(SingleRefOne singleRefOne, RootDto parent) {
		return convertToSingleRefOneDto(singleRefOne, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefOne} to a(n) {@link SingleRefOneDto} and sets the result to the corresponding reference property at the parent
	 *
	 * @param singleRefOne  the source object which should be converted
	 * @param parent        the parent of converted result
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code singleRefOne} is contained, the found
	 *                      {@link SingleRefOneDto} will be returned
	 * @return an equivalent new created {@link SingleRefOneDto} or the found one from the given map
	 */
	public static SingleRefOneDto convertToSingleRefOneDto(SingleRefOne singleRefOne, RootDto parent, Map<String, ITransportable> mappedObjects) {
		SingleRefOneDto result = convertToSingleRefOneDto(singleRefOne, mappedObjects);
		if (result != null) {
			parent.setSingleRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefTwoDto} to a(n) {@link SingleRefTwo}
	 *
	 * @param singleRefTwo the source object which should be converted
	 * @return an equivalent new created {@link SingleRefTwo}
	 */
	public static SingleRefTwo convertToSingleRefTwo(SingleRefTwoDto singleRefTwo) {
		return convertToSingleRefTwo(singleRefTwo, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefTwoDto} to a(n) {@link SingleRefTwo}
	 *
	 * @param singleRefTwo  the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code singleRefTwo} is contained, the found
	 *                      {@link SingleRefTwo} will be returned
	 * @return an equivalent new created {@link SingleRefTwo} or the found one from the given map
	 */
	public static SingleRefTwo convertToSingleRefTwo(SingleRefTwoDto singleRefTwo, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(singleRefTwo, mappedObjects, DomainObjectFactory::createSingleRefTwo, (dto, domain) -> getInstance().setSingleRefTwoValues(dto, domain)
				, (dto, domain) -> getInstance().setSingleRefTwoSingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link SingleRefTwoDto} to a(n) {@link SingleRefTwo} and sets the result to the corresponding reference property at the parent
	 *
	 * @param singleRefTwo the source object which should be converted
	 * @param parent       the parent of converted result
	 * @return an equivalent new created {@link SingleRefTwo}
	 */
	public static SingleRefTwo convertToSingleRefTwo(SingleRefTwoDto singleRefTwo, Root parent) {
		return convertToSingleRefTwo(singleRefTwo, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefTwoDto} to a(n) {@link SingleRefTwo} and sets the result to the corresponding reference property at the parent
	 *
	 * @param singleRefTwo  the source object which should be converted
	 * @param parent        the parent of converted result
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code singleRefTwo} is contained, the found
	 *                      {@link SingleRefTwo} will be returned
	 * @return an equivalent new created {@link SingleRefTwo} or the found one from the given map
	 */
	public static SingleRefTwo convertToSingleRefTwo(SingleRefTwoDto singleRefTwo, Root parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefTwo result = convertToSingleRefTwo(singleRefTwo, mappedObjects);
		if (result != null) {
			parent.setAnotherSingleRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefTwo} to a(n) {@link SingleRefTwoDto}
	 *
	 * @param singleRefTwo the source object which should be converted
	 * @return an equivalent new created {@link SingleRefTwoDto}
	 */
	public static SingleRefTwoDto convertToSingleRefTwoDto(SingleRefTwo singleRefTwo) {
		return convertToSingleRefTwoDto(singleRefTwo, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefTwo} to a(n) {@link SingleRefTwoDto}
	 *
	 * @param singleRefTwo  the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code singleRefTwo} is contained, the found
	 *                      {@link SingleRefTwoDto} will be returned
	 * @return an equivalent new created {@link SingleRefTwoDto} or the found one from the given map
	 */
	public static SingleRefTwoDto convertToSingleRefTwoDto(SingleRefTwo singleRefTwo, Map<String, ITransportable> mappedObjects) {
		return convertToDto(singleRefTwo, mappedObjects, DtoObjectFactory::createSingleRefTwoDto, (domain, dto) -> getInstance().setSingleRefTwoDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setSingleRefTwoDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
	}

	/**
	 * Converts a(n) {@link SingleRefTwo} to a(n) {@link SingleRefTwoDto} and sets the result to the corresponding reference property at the parent
	 *
	 * @param singleRefTwo the source object which should be converted
	 * @param parent       the parent of converted result
	 * @return an equivalent new created {@link SingleRefTwoDto}
	 */
	public static SingleRefTwoDto convertToSingleRefTwoDto(SingleRefTwo singleRefTwo, RootDto parent) {
		return convertToSingleRefTwoDto(singleRefTwo, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefTwo} to a(n) {@link SingleRefTwoDto} and sets the result to the corresponding reference property at the parent
	 *
	 * @param singleRefTwo  the source object which should be converted
	 * @param parent        the parent of converted result
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code singleRefTwo} is contained, the found
	 *                      {@link SingleRefTwoDto} will be returned
	 * @return an equivalent new created {@link SingleRefTwoDto} or the found one from the given map
	 */
	public static SingleRefTwoDto convertToSingleRefTwoDto(SingleRefTwo singleRefTwo, RootDto parent, Map<String, ITransportable> mappedObjects) {
		SingleRefTwoDto result = convertToSingleRefTwoDto(singleRefTwo, mappedObjects);
		if (result != null) {
			parent.setAnotherSingleRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefTwo} to a(n) {@link SingleRefTwoDto} and sets the result to the corresponding reference property at the parent
	 *
	 * @param singleRefTwo the source object which should be converted
	 * @param parent       the parent of converted result
	 * @return an equivalent new created {@link SingleRefTwoDto}
	 */
	public static SingleRefTwoDto convertToSingleRefTwoDto(SingleRefTwo singleRefTwo, RootV1Dto parent) {
		return convertToSingleRefTwoDto(singleRefTwo, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefTwo} to a(n) {@link SingleRefTwoDto} and sets the result to the corresponding reference property at the parent
	 *
	 * @param singleRefTwo  the source object which should be converted
	 * @param parent        the parent of converted result
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code singleRefTwo} is contained, the found
	 *                      {@link SingleRefTwoDto} will be returned
	 * @return an equivalent new created {@link SingleRefTwoDto} or the found one from the given map
	 */
	public static SingleRefTwoDto convertToSingleRefTwoDto(SingleRefTwo singleRefTwo, RootV1Dto parent, Map<String, ITransportable> mappedObjects) {
		SingleRefTwoDto result = convertToSingleRefTwoDto(singleRefTwo, mappedObjects);
		if (result != null) {
			parent.setAnotherSingleRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link SingleRefTwo} to a(n) {@link SingleRefTwoDto} and sets the result to the corresponding reference property at the parent
	 *
	 * @param singleRefTwo the source object which should be converted
	 * @param parent       the parent of converted result
	 * @return an equivalent new created {@link SingleRefTwoDto}
	 */
	public static SingleRefTwoDto convertToSingleRefTwoDto(SingleRefTwo singleRefTwo, RootV2Dto parent) {
		return convertToSingleRefTwoDto(singleRefTwo, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SingleRefTwo} to a(n) {@link SingleRefTwoDto} and sets the result to the corresponding reference property at the parent
	 *
	 * @param singleRefTwo  the source object which should be converted
	 * @param parent        the parent of converted result
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code singleRefTwo} is contained, the found
	 *                      {@link SingleRefTwoDto} will be returned
	 * @return an equivalent new created {@link SingleRefTwoDto} or the found one from the given map
	 */
	public static SingleRefTwoDto convertToSingleRefTwoDto(SingleRefTwo singleRefTwo, RootV2Dto parent, Map<String, ITransportable> mappedObjects) {
		SingleRefTwoDto result = convertToSingleRefTwoDto(singleRefTwo, mappedObjects);
		if (result != null) {
			parent.setAnotherSingleRef(result);
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
		CommonTransportMapper.convertToSingleRefOneDto(domain.getSingleRef(), dto, mappedObjects);
		CommonTransportMapper.convertToSingleRefTwoDto(domain.getAnotherSingleRef(), dto, mappedObjects);
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
		dto.setSomeInteger(domain.getSomeInteger());
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
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setRootExtSingleReferences(RootExtV1Dto dto, RootExt domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dto} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dto           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dto}
	 */
	@SuppressWarnings("java:S1186")
	protected void setRootExtV1DtoSingleReferences(RootExt domain, RootExtV1Dto dto, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setRootExtV1DtoValues(RootExt domain, RootExtV1Dto dto) {
		dto.setExtendedInfo(domain.getExtendedInfo());
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setRootExtValues(RootExtDto dto, RootExt domain) {
		domain.setExtendedInfo(dto.getExtendedInfo());
		domain.setSomeInteger(dto.getSomeInteger());
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setRootExtValues(RootExtV1Dto dto, RootExt domain) {
		domain.setExtendedInfo(dto.getExtendedInfo());
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	protected void setRootSingleReferences(RootDto dto, Root domain, Map<String, IIdentifiable> mappedObjects) {
		CommonTransportMapper.convertToSingleRefOne(dto.getSingleRef(), domain, mappedObjects);
		CommonTransportMapper.convertToSingleRefTwo(dto.getAnotherSingleRef(), domain, mappedObjects);
		CommonTransportMapper.convertToRootExt(dto.getExt(), domain, mappedObjects);
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	protected void setRootSingleReferences(RootV1Dto dto, Root domain, Map<String, IIdentifiable> mappedObjects) {
		CommonTransportMapper.convertToSingleRefTwo(dto.getAnotherSingleRef(), domain, mappedObjects);
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	protected void setRootSingleReferences(RootV2Dto dto, Root domain, Map<String, IIdentifiable> mappedObjects) {
		CommonTransportMapper.convertToSingleRefTwo(dto.getAnotherSingleRef(), domain, mappedObjects);
	}

	/**
	 * Adds the references at {@code dto} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dto           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dto}
	 */
	protected void setRootV1DtoSingleReferences(Root domain, RootV1Dto dto, Map<String, ITransportable> mappedObjects) {
		CommonTransportMapper.convertToSingleRefTwoDto(domain.getAnotherSingleRef(), dto, mappedObjects);
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setRootV1DtoValues(Root domain, RootV1Dto dto) {
		dto.setRootName(domain.getRootName());
	}

	/**
	 * Adds the references at {@code dto} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dto           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dto}
	 */
	protected void setRootV2DtoSingleReferences(Root domain, RootV2Dto dto, Map<String, ITransportable> mappedObjects) {
		CommonTransportMapper.convertToSingleRefTwoDto(domain.getAnotherSingleRef(), dto, mappedObjects);
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setRootV2DtoValues(Root domain, RootV2Dto dto) {
		dto.setRootName(domain.getRootName());
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

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setRootValues(RootV1Dto dto, Root domain) {
		domain.setRootName(dto.getRootName());
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setRootValues(RootV2Dto dto, Root domain) {
		domain.setRootName(dto.getRootName());
	}

	/**
	 * Adds the references at {@code dto} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dto           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dto}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSingleRefOneDtoSingleReferences(SingleRefOne domain, SingleRefOneDto dto, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSingleRefOneDtoValues(SingleRefOne domain, SingleRefOneDto dto) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSingleRefOneSingleReferences(SingleRefOneDto dto, SingleRefOne domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSingleRefOneValues(SingleRefOneDto dto, SingleRefOne domain) {
	}

	/**
	 * Adds the references at {@code dto} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dto           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dto}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSingleRefTwoDtoSingleReferences(SingleRefTwo domain, SingleRefTwoDto dto, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSingleRefTwoDtoValues(SingleRefTwo domain, SingleRefTwoDto dto) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dto           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dto} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSingleRefTwoSingleReferences(SingleRefTwoDto dto, SingleRefTwo domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSingleRefTwoValues(SingleRefTwoDto dto, SingleRefTwo domain) {
	}

}
