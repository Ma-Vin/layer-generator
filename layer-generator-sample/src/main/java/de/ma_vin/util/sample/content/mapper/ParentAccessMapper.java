package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseAccessMapper;
import de.ma_vin.util.sample.content.dao.DaoObjectFactory;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.dao.parent.ExtendingClassDao;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.parent.ExtendingClass;
import java.util.HashMap;
import java.util.Map;

@BaseAccessMapper
public class ParentAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static ParentAccessMapper instance;

	public static ExtendingClass convertToExtendingClass(ExtendingClassDao extendingClass) {
		return convertToExtendingClass(extendingClass, new HashMap<>());
	}

	public static ExtendingClass convertToExtendingClass(ExtendingClassDao extendingClass, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(extendingClass, mappedObjects, DomainObjectFactory::createExtendingClass, (dao, domain) -> getInstance().setExtendingClassValues(dao, domain)
				, (dao, domain) -> getInstance().setExtendingClassSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setExtendingClassMultiReferences(dao, domain, mappedObjects));
	}

	public static ExtendingClass convertToExtendingClass(ExtendingClassDao extendingClass, Root parent) {
		return convertToExtendingClass(extendingClass, parent, new HashMap<>());
	}

	public static ExtendingClass convertToExtendingClass(ExtendingClassDao extendingClass, Root parent, Map<String, IIdentifiable> mappedObjects) {
		ExtendingClass result = convertToExtendingClass(extendingClass, mappedObjects);
		if (result != null) {
			parent.getExtendings().add(result);
		}
		return result;
	}

	public static ExtendingClassDao convertToExtendingClassDao(ExtendingClass extendingClass) {
		return convertToExtendingClassDao(extendingClass, new HashMap<>());
	}

	public static ExtendingClassDao convertToExtendingClassDao(ExtendingClass extendingClass, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(extendingClass, mappedObjects, DaoObjectFactory::createExtendingClassDao, (domain, dao) -> getInstance().setExtendingClassDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setExtendingClassDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setExtendingClassDaoMultiReferences(domain, dao, mappedObjects));
	}

	public static ExtendingClassDao convertToExtendingClassDao(ExtendingClass extendingClass, RootDao parent) {
		return convertToExtendingClassDao(extendingClass, parent, new HashMap<>());
	}

	public static ExtendingClassDao convertToExtendingClassDao(ExtendingClass extendingClass, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		ExtendingClassDao result = convertToExtendingClassDao(extendingClass, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.getExtendings().add(result);
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

	@SuppressWarnings("java:S1186")
	protected void setExtendingClassDaoMultiReferences(ExtendingClass domain, ExtendingClassDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setExtendingClassDaoSingleReferences(ExtendingClass domain, ExtendingClassDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	protected void setExtendingClassDaoValues(ExtendingClass domain, ExtendingClassDao dao) {
		dao.setAdditionalDescription(domain.getAdditionalDescription());
		dao.setDescription(domain.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setExtendingClassMultiReferences(ExtendingClassDao dao, ExtendingClass domain, Map<String, IIdentifiable> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setExtendingClassSingleReferences(ExtendingClassDao dao, ExtendingClass domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setExtendingClassValues(ExtendingClassDao dao, ExtendingClass domain) {
		domain.setAdditionalDescription(dao.getAdditionalDescription());
		domain.setDescription(dao.getDescription());
	}

}
