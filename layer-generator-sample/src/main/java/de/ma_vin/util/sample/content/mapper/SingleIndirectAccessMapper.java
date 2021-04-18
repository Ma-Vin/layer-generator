package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseAccessMapper;
import de.ma_vin.util.sample.content.dao.DaoObjectFactory;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.dao.single.indirect.SingleRefIndirectParentDao;
import de.ma_vin.util.sample.content.dao.single.indirect.SingleRefOtherIndirectParentDao;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.single.indirect.SingleRefIndirectParent;
import de.ma_vin.util.sample.content.domain.single.indirect.SingleRefOtherIndirectParent;
import java.util.HashMap;
import java.util.Map;

@BaseAccessMapper
public class SingleIndirectAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static SingleIndirectAccessMapper instance;

	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDao singleRefIndirectParent) {
		return convertToSingleRefIndirectParent(singleRefIndirectParent, new HashMap<>());
	}

	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDao singleRefIndirectParent, Map<String, IIdentifiable> mappedObjects) {
		if (singleRefIndirectParent == null) {
			return null;
		}

		String identification = singleRefIndirectParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (SingleRefIndirectParent) mappedObjects.get(identification);
		}

		SingleRefIndirectParent result = DomainObjectFactory.createSingleRefIndirectParent();

		result.setIdentification(identification);

		result.setDescription(singleRefIndirectParent.getDescription());

		mappedObjects.put(identification, result);
		return result;
	}

	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDao singleRefIndirectParent, Root parent) {
		return convertToSingleRefIndirectParent(singleRefIndirectParent, parent, new HashMap<>());
	}

	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDao singleRefIndirectParent, Root parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefIndirectParent result = convertToSingleRefIndirectParent(singleRefIndirectParent, mappedObjects);
		if (result != null) {
			parent.setSingleRefIndirectParent(result);
		}
		return result;
	}

	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDao singleRefIndirectParent, SingleRefOtherIndirectParent parent) {
		return convertToSingleRefIndirectParent(singleRefIndirectParent, parent, new HashMap<>());
	}

	public static SingleRefIndirectParent convertToSingleRefIndirectParent(SingleRefIndirectParentDao singleRefIndirectParent, SingleRefOtherIndirectParent parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefIndirectParent result = convertToSingleRefIndirectParent(singleRefIndirectParent, mappedObjects);
		if (result != null) {
			parent.setSingleIndirectRef(result);
		}
		return result;
	}

	public static SingleRefIndirectParentDao convertToSingleRefIndirectParentDao(SingleRefIndirectParent singleRefIndirectParent) {
		return convertToSingleRefIndirectParentDao(singleRefIndirectParent, new HashMap<>());
	}

	public static SingleRefIndirectParentDao convertToSingleRefIndirectParentDao(SingleRefIndirectParent singleRefIndirectParent, Map<String, IIdentifiableDao> mappedObjects) {
		if (singleRefIndirectParent == null) {
			return null;
		}

		String identification = singleRefIndirectParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (SingleRefIndirectParentDao) mappedObjects.get(identification);
		}

		SingleRefIndirectParentDao result = DaoObjectFactory.createSingleRefIndirectParentDao();

		result.setIdentification(identification);

		result.setDescription(singleRefIndirectParent.getDescription());

		mappedObjects.put(identification, result);
		return result;
	}

	public static SingleRefIndirectParentDao convertToSingleRefIndirectParentDao(SingleRefIndirectParent singleRefIndirectParent, RootDao parent) {
		return convertToSingleRefIndirectParentDao(singleRefIndirectParent, parent, new HashMap<>());
	}

	public static SingleRefIndirectParentDao convertToSingleRefIndirectParentDao(SingleRefIndirectParent singleRefIndirectParent, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		SingleRefIndirectParentDao result = convertToSingleRefIndirectParentDao(singleRefIndirectParent, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.setSingleRefIndirectParent(result);
		}
		return result;
	}

	public static SingleRefIndirectParentDao convertToSingleRefIndirectParentDao(SingleRefIndirectParent singleRefIndirectParent, SingleRefOtherIndirectParentDao parent) {
		return convertToSingleRefIndirectParentDao(singleRefIndirectParent, parent, new HashMap<>());
	}

	public static SingleRefIndirectParentDao convertToSingleRefIndirectParentDao(SingleRefIndirectParent singleRefIndirectParent, SingleRefOtherIndirectParentDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		SingleRefIndirectParentDao result = convertToSingleRefIndirectParentDao(singleRefIndirectParent, mappedObjects);
		if (result != null) {
			parent.setSingleIndirectRef(result);
		}
		return result;
	}

	public static SingleRefOtherIndirectParent convertToSingleRefOtherIndirectParent(SingleRefOtherIndirectParentDao singleRefOtherIndirectParent) {
		return convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParent, new HashMap<>());
	}

	public static SingleRefOtherIndirectParent convertToSingleRefOtherIndirectParent(SingleRefOtherIndirectParentDao singleRefOtherIndirectParent, Map<String, IIdentifiable> mappedObjects) {
		if (singleRefOtherIndirectParent == null) {
			return null;
		}

		String identification = singleRefOtherIndirectParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (SingleRefOtherIndirectParent) mappedObjects.get(identification);
		}

		SingleRefOtherIndirectParent result = DomainObjectFactory.createSingleRefOtherIndirectParent();

		result.setIdentification(identification);

		result.setDescription(singleRefOtherIndirectParent.getDescription());

		result.setSingleIndirectRef(SingleIndirectAccessMapper.convertToSingleRefIndirectParent(singleRefOtherIndirectParent.getSingleIndirectRef(), mappedObjects));

		mappedObjects.put(identification, result);
		return result;
	}

	public static SingleRefOtherIndirectParent convertToSingleRefOtherIndirectParent(SingleRefOtherIndirectParentDao singleRefOtherIndirectParent, Root parent) {
		return convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParent, parent, new HashMap<>());
	}

	public static SingleRefOtherIndirectParent convertToSingleRefOtherIndirectParent(SingleRefOtherIndirectParentDao singleRefOtherIndirectParent, Root parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefOtherIndirectParent result = convertToSingleRefOtherIndirectParent(singleRefOtherIndirectParent, mappedObjects);
		if (result != null) {
			parent.setSingleRefIndirectOtherParent(result);
		}
		return result;
	}

	public static SingleRefOtherIndirectParentDao convertToSingleRefOtherIndirectParentDao(SingleRefOtherIndirectParent singleRefOtherIndirectParent) {
		return convertToSingleRefOtherIndirectParentDao(singleRefOtherIndirectParent, new HashMap<>());
	}

	public static SingleRefOtherIndirectParentDao convertToSingleRefOtherIndirectParentDao(SingleRefOtherIndirectParent singleRefOtherIndirectParent, Map<String, IIdentifiableDao> mappedObjects) {
		if (singleRefOtherIndirectParent == null) {
			return null;
		}

		String identification = singleRefOtherIndirectParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (SingleRefOtherIndirectParentDao) mappedObjects.get(identification);
		}

		SingleRefOtherIndirectParentDao result = DaoObjectFactory.createSingleRefOtherIndirectParentDao();

		result.setIdentification(identification);

		result.setDescription(singleRefOtherIndirectParent.getDescription());

		result.setSingleIndirectRef(SingleIndirectAccessMapper.convertToSingleRefIndirectParentDao(singleRefOtherIndirectParent.getSingleIndirectRef(), mappedObjects));

		mappedObjects.put(identification, result);
		return result;
	}

	public static SingleRefOtherIndirectParentDao convertToSingleRefOtherIndirectParentDao(SingleRefOtherIndirectParent singleRefOtherIndirectParent, RootDao parent) {
		return convertToSingleRefOtherIndirectParentDao(singleRefOtherIndirectParent, parent, new HashMap<>());
	}

	public static SingleRefOtherIndirectParentDao convertToSingleRefOtherIndirectParentDao(SingleRefOtherIndirectParent singleRefOtherIndirectParent, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		SingleRefOtherIndirectParentDao result = convertToSingleRefOtherIndirectParentDao(singleRefOtherIndirectParent, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.setSingleRefIndirectOtherParent(result);
		}
		return result;
	}

	/**
	 * @return the singleton
	 */
	public static SingleIndirectAccessMapper getInstance() {
		if (instance == null) {
			instance = AccessMapperFactory.createSingleIndirectAccessMapper();
		}
		return instance;
	}

}
