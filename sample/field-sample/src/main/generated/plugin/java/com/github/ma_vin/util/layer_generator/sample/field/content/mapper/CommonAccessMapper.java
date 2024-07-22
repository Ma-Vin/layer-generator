package com.github.ma_vin.util.layer_generator.sample.field.content.mapper;

import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseAccessMapper;
import com.github.ma_vin.util.layer_generator.sample.field.content.dao.DaoObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.field.content.dao.FieldEntityDao;
import com.github.ma_vin.util.layer_generator.sample.field.content.dao.IIdentifiableDao;
import com.github.ma_vin.util.layer_generator.sample.field.content.domain.DomainObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.field.content.domain.FieldEntity;
import com.github.ma_vin.util.layer_generator.sample.field.content.domain.IIdentifiable;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class which provides methods to convert a data access to a domain object of sub package <i>null</i> and the other way around
 */
@BaseAccessMapper
public class CommonAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static CommonAccessMapper instance;

	/**
	 * Converts a(n) {@link FieldEntityDao} to a(n) {@link FieldEntity}
	 *
	 * @param fieldEntity the source object which should be converted
	 * @return an equivalent new created {@link FieldEntity}
	 */
	public static FieldEntity convertToFieldEntity(FieldEntityDao fieldEntity) {
		return convertToFieldEntity(fieldEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FieldEntityDao} to a(n) {@link FieldEntity}
	 *
	 * @param fieldEntity   the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code fieldEntity} is contained, the found
	 *                      {@link FieldEntity} will be returned
	 * @return an equivalent new created {@link FieldEntity} or the found one from the given map
	 */
	public static FieldEntity convertToFieldEntity(FieldEntityDao fieldEntity, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(fieldEntity, mappedObjects, DomainObjectFactory::createFieldEntity, (dao, domain) -> getInstance().setFieldEntityValues(dao, domain)
				, (dao, domain) -> getInstance().setFieldEntitySingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setFieldEntityMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link FieldEntity} to a(n) {@link FieldEntityDao}
	 *
	 * @param fieldEntity the source object which should be converted
	 * @return an equivalent new created {@link FieldEntityDao}
	 */
	public static FieldEntityDao convertToFieldEntityDao(FieldEntity fieldEntity) {
		return convertToFieldEntityDao(fieldEntity, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link FieldEntity} to a(n) {@link FieldEntityDao}
	 *
	 * @param fieldEntity   the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code fieldEntity} is contained, the found
	 *                      {@link FieldEntityDao} will be returned
	 * @return an equivalent new created {@link FieldEntityDao} or the found one from the given map
	 */
	public static FieldEntityDao convertToFieldEntityDao(FieldEntity fieldEntity, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(fieldEntity, mappedObjects, DaoObjectFactory::createFieldEntityDao, (domain, dao) -> getInstance().setFieldEntityDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setFieldEntityDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setFieldEntityDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * @return the singleton
	 */
	public static CommonAccessMapper getInstance() {
		if (instance == null) {
			instance = AccessMapperFactory.createCommonAccessMapper();
		}
		return instance;
	}

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setFieldEntityDaoMultiReferences(FieldEntity domain, FieldEntityDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setFieldEntityDaoSingleReferences(FieldEntity domain, FieldEntityDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	protected void setFieldEntityDaoValues(FieldEntity domain, FieldEntityDao dao) {
		dao.setSomeString(domain.getSomeString());
		dao.setSomeInteger(domain.getSomeInteger());
		dao.setSomeEnum(domain.getSomeEnum());
		dao.setSomeCustom(domain.getSomeCustom());
		dao.setDaoAndDomain(domain.getDaoAndDomain());
		dao.setTextWithDaoInfo(domain.getTextWithDaoInfo());
		dao.setNumberWithDaoInfo(domain.getNumberWithDaoInfo());
		dao.setDaoEnum(domain.getDaoEnum());
		dao.setDaoEnumWithText(domain.getDaoEnumWithText());
		dao.setSomeName(domain.getSomeName());
		dao.setDocument(domain.getDocument());
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setFieldEntityMultiReferences(FieldEntityDao dao, FieldEntity domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setFieldEntitySingleReferences(FieldEntityDao dao, FieldEntity domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setFieldEntityValues(FieldEntityDao dao, FieldEntity domain) {
		domain.setSomeString(dao.getSomeString());
		domain.setSomeInteger(dao.getSomeInteger());
		domain.setSomeEnum(dao.getSomeEnum());
		domain.setSomeCustom(dao.getSomeCustom());
		domain.setDaoAndDomain(dao.getDaoAndDomain());
		domain.setTextWithDaoInfo(dao.getTextWithDaoInfo());
		domain.setNumberWithDaoInfo(dao.getNumberWithDaoInfo());
		domain.setDaoEnum(dao.getDaoEnum());
		domain.setDaoEnumWithText(dao.getDaoEnumWithText());
		domain.setSomeName(dao.getSomeName());
		domain.setDocument(dao.getDocument());
	}

}
