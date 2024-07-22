package com.github.ma_vin.util.layer_generator.sample.field.content.mapper;

import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseTransportMapper;
import com.github.ma_vin.util.layer_generator.sample.field.content.domain.DomainObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.field.content.domain.FieldEntity;
import com.github.ma_vin.util.layer_generator.sample.field.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.field.content.dto.DtoObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.field.content.dto.FieldEntityDto;
import com.github.ma_vin.util.layer_generator.sample.field.content.dto.ITransportable;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class which provides methods to convert a data transport to a domain object of sub package <i>null</i> and the other way around
 */
@BaseTransportMapper
public class CommonTransportMapper extends AbstractTransportMapper {

	/**
	 * singleton
	 */
	private static CommonTransportMapper instance;

	/**
	 * Converts a(n) {@link FieldEntityDto} to a(n) {@link FieldEntity}
	 *
	 * @param fieldEntity the source object which should be converted
	 * @return an equivalent new created {@link FieldEntity}
	 */
	public static FieldEntity convertToFieldEntity(FieldEntityDto fieldEntity) {
		return convertToFieldEntity(fieldEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FieldEntityDto} to a(n) {@link FieldEntity}
	 *
	 * @param fieldEntity   the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code fieldEntity} is contained, the found
	 *                      {@link FieldEntity} will be returned
	 * @return an equivalent new created {@link FieldEntity} or the found one from the given map
	 */
	public static FieldEntity convertToFieldEntity(FieldEntityDto fieldEntity, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(fieldEntity, mappedObjects, DomainObjectFactory::createFieldEntity, (dto, domain) -> getInstance().setFieldEntityValues(dto, domain)
				, (dto, domain) -> getInstance().setFieldEntitySingleReferences(dto, domain, mappedObjects)
				, (dto, domain) -> {
		});
	}

	/**
	 * Converts a(n) {@link FieldEntity} to a(n) {@link FieldEntityDto}
	 *
	 * @param fieldEntity the source object which should be converted
	 * @return an equivalent new created {@link FieldEntityDto}
	 */
	public static FieldEntityDto convertToFieldEntityDto(FieldEntity fieldEntity) {
		return convertToFieldEntityDto(fieldEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FieldEntity} to a(n) {@link FieldEntityDto}
	 *
	 * @param fieldEntity   the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code fieldEntity} is contained, the found
	 *                      {@link FieldEntityDto} will be returned
	 * @return an equivalent new created {@link FieldEntityDto} or the found one from the given map
	 */
	public static FieldEntityDto convertToFieldEntityDto(FieldEntity fieldEntity, Map<String, ITransportable> mappedObjects) {
		return convertToDto(fieldEntity, mappedObjects, DtoObjectFactory::createFieldEntityDto, (domain, dto) -> getInstance().setFieldEntityDtoValues(domain, dto)
				, (domain, dto) -> getInstance().setFieldEntityDtoSingleReferences(domain, dto, mappedObjects)
				, (domain, dto) -> {
		});
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
	protected void setFieldEntityDtoSingleReferences(FieldEntity domain, FieldEntityDto dto, Map<String, ITransportable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dto} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dto    object where to set the values
	 */
	protected void setFieldEntityDtoValues(FieldEntity domain, FieldEntityDto dto) {
		dto.setSomeString(domain.getSomeString());
		dto.setSomeInteger(domain.getSomeInteger());
		dto.setSomeEnum(domain.getSomeEnum());
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
	protected void setFieldEntitySingleReferences(FieldEntityDto dto, FieldEntity domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dto} to {@code domain} which are not of reference type
	 *
	 * @param dto    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setFieldEntityValues(FieldEntityDto dto, FieldEntity domain) {
		domain.setSomeString(dto.getSomeString());
		domain.setSomeInteger(dto.getSomeInteger());
		domain.setSomeEnum(dto.getSomeEnum());
		domain.setSomeCustom(dto.getSomeCustom());
		domain.setDtoAndDomain(dto.getDtoAndDomain());
		domain.setTextWithDaoInfo(dto.getTextWithDaoInfo());
		domain.setNumberWithDaoInfo(dto.getNumberWithDaoInfo());
		domain.setDaoEnum(dto.getDaoEnum());
		domain.setDaoEnumWithText(dto.getDaoEnumWithText());
		domain.setSomeName(dto.getSomeName());
		domain.setDocument(dto.getDocument());
	}

}
