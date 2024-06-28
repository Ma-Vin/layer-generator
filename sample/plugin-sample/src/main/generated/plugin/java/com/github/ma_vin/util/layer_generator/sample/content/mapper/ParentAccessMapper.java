package com.github.ma_vin.util.layer_generator.sample.content.mapper;

import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseAccessMapper;
import com.github.ma_vin.util.layer_generator.sample.content.dao.DaoObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.content.dao.IIdentifiableDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.RootDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.parent.ExtendingClassDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.DomainObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.content.domain.Root;
import com.github.ma_vin.util.layer_generator.sample.content.domain.parent.ExtendingClass;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class which provides methods to convert a data access to a domain object of sub package <i>parent<i> and the other way around
 */
@BaseAccessMapper
public class ParentAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static ParentAccessMapper instance;

	/**
	 * Converts a(n) {@link ExtendingClassDao} to a(n) {@link ExtendingClass}
	 *
	 * @param extendingClass the source object which should be converted
	 * @return an equivalent new created {@link ExtendingClass}
	 */
	public static ExtendingClass convertToExtendingClass(ExtendingClassDao extendingClass) {
		return convertToExtendingClass(extendingClass, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link ExtendingClassDao} to a(n) {@link ExtendingClass}
	 *
	 * @param extendingClass the source object which should be converted
	 * @param mappedObjects  map which contains already mapped objects. If an identification of {@code extendingClass} is contained, the found
	 *                       {@link ExtendingClass} will be returned
	 * @return an equivalent new created {@link ExtendingClass} or the found one from the given map
	 */
	public static ExtendingClass convertToExtendingClass(ExtendingClassDao extendingClass, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(extendingClass, mappedObjects, DomainObjectFactory::createExtendingClass, (dao, domain) -> getInstance().setExtendingClassValues(dao, domain)
				, (dao, domain) -> getInstance().setExtendingClassSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setExtendingClassMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link ExtendingClassDao} to a(n) {@link ExtendingClass} and sets the result to the corresponding reference property at the parent
	 *
	 * @param extendingClass the source object which should be converted
	 * @param parent         the parent of converted result
	 * @return an equivalent new created {@link ExtendingClass}
	 */
	public static ExtendingClass convertToExtendingClass(ExtendingClassDao extendingClass, Root parent) {
		return convertToExtendingClass(extendingClass, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link ExtendingClassDao} to a(n) {@link ExtendingClass} and sets the result to the corresponding reference property at the parent
	 *
	 * @param extendingClass the source object which should be converted
	 * @param parent         the parent of converted result
	 * @param mappedObjects  map which contains already mapped objects. If an identification of {@code extendingClass} is contained, the found
	 *                       {@link ExtendingClass} will be returned
	 * @return an equivalent new created {@link ExtendingClass} or the found one from the given map
	 */
	public static ExtendingClass convertToExtendingClass(ExtendingClassDao extendingClass, Root parent, Map<String, IIdentifiable> mappedObjects) {
		ExtendingClass result = convertToExtendingClass(extendingClass, mappedObjects);
		if (result != null) {
			parent.getExtending().add(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link ExtendingClass} to a(n) {@link ExtendingClassDao}
	 *
	 * @param extendingClass the source object which should be converted
	 * @return an equivalent new created {@link ExtendingClassDao}
	 */
	public static ExtendingClassDao convertToExtendingClassDao(ExtendingClass extendingClass) {
		return convertToExtendingClassDao(extendingClass, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link ExtendingClass} to a(n) {@link ExtendingClassDao}
	 *
	 * @param extendingClass the source object which should be converted
	 * @param mappedObjects  map which contains already mapped objects. If an identification of {@code extendingClass} is contained, the found
	 *                       {@link ExtendingClassDao} will be returned
	 * @return an equivalent new created {@link ExtendingClassDao} or the found one from the given map
	 */
	public static ExtendingClassDao convertToExtendingClassDao(ExtendingClass extendingClass, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(extendingClass, mappedObjects, DaoObjectFactory::createExtendingClassDao, (domain, dao) -> getInstance().setExtendingClassDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setExtendingClassDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setExtendingClassDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * Converts a(n) {@link ExtendingClass} to a(n) {@link ExtendingClassDao} and sets the result to the corresponding reference property at the parent
	 *
	 * @param extendingClass the source object which should be converted
	 * @param parent         the parent of converted result
	 * @return an equivalent new created {@link ExtendingClassDao}
	 */
	public static ExtendingClassDao convertToExtendingClassDao(ExtendingClass extendingClass, RootDao parent) {
		return convertToExtendingClassDao(extendingClass, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link ExtendingClass} to a(n) {@link ExtendingClassDao} and sets the result to the corresponding reference property at the parent
	 *
	 * @param extendingClass the source object which should be converted
	 * @param parent         the parent of converted result
	 * @param mappedObjects  map which contains already mapped objects. If an identification of {@code extendingClass} is contained, the found
	 *                       {@link ExtendingClassDao} will be returned
	 * @return an equivalent new created {@link ExtendingClassDao} or the found one from the given map
	 */
	public static ExtendingClassDao convertToExtendingClassDao(ExtendingClass extendingClass, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		ExtendingClassDao result = convertToExtendingClassDao(extendingClass, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.getExtending().add(result);
		}
		return result;
	}

	/**
	 * @return the singleton
	 */
	public static ParentAccessMapper getInstance() {
		if (instance == null) {
			instance = AccessMapperFactory.createParentAccessMapper();
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
	protected void setExtendingClassDaoMultiReferences(ExtendingClass domain, ExtendingClassDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setExtendingClassDaoSingleReferences(ExtendingClass domain, ExtendingClassDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	protected void setExtendingClassDaoValues(ExtendingClass domain, ExtendingClassDao dao) {
		dao.setAdditionalDescription(domain.getAdditionalDescription());
		dao.setDescription(domain.getDescription());
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setExtendingClassMultiReferences(ExtendingClassDao dao, ExtendingClass domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setExtendingClassSingleReferences(ExtendingClassDao dao, ExtendingClass domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setExtendingClassValues(ExtendingClassDao dao, ExtendingClass domain) {
		domain.setAdditionalDescription(dao.getAdditionalDescription());
		domain.setDescription(dao.getDescription());
	}

}
