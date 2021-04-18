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
		if (extendingClass == null) {
			return null;
		}

		String identification = extendingClass.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (ExtendingClass) mappedObjects.get(identification);
		}

		ExtendingClass result = DomainObjectFactory.createExtendingClass();

		result.setIdentification(identification);

		result.setAdditionalDescription(extendingClass.getAdditionalDescription());
		result.setDescription(extendingClass.getDescription());

		mappedObjects.put(identification, result);
		return result;
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
		if (extendingClass == null) {
			return null;
		}

		String identification = extendingClass.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (ExtendingClassDao) mappedObjects.get(identification);
		}

		ExtendingClassDao result = DaoObjectFactory.createExtendingClassDao();

		result.setIdentification(identification);

		result.setAdditionalDescription(extendingClass.getAdditionalDescription());
		result.setDescription(extendingClass.getDescription());

		mappedObjects.put(identification, result);
		return result;
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

}
