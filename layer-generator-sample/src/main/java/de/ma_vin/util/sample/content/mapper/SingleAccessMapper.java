package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.dao.DaoObjectFactory;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.dao.single.SingleRefOneParentDao;
import de.ma_vin.util.sample.content.dao.single.SingleRefTwoParentsDao;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.single.SingleRefOneParent;
import de.ma_vin.util.sample.content.domain.single.SingleRefTwoParents;
import java.util.HashMap;
import java.util.Map;

public class SingleAccessMapper {

	private SingleAccessMapper() {
	}

	/**
	 * singleton
	 */
	private static SingleAccessMapper instance;

	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDao singleRefOneParent) {
		return convertToSingleRefOneParent(singleRefOneParent, new HashMap<>());
	}

	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDao singleRefOneParent, Map<String, IIdentifiable> mappedObjects) {
		if (singleRefOneParent == null) {
			return null;
		}

		String identification = singleRefOneParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (SingleRefOneParent) mappedObjects.get(identification);
		}

		SingleRefOneParent result = DomainObjectFactory.createSingleRefOneParent();

		result.setIdentification(identification);

		result.setDescription(singleRefOneParent.getDescription());

		SingleAccessMapper.convertToSingleRefTwoParents(singleRefOneParent.getSingleRef(), result, mappedObjects);

		mappedObjects.put(identification, result);
		return result;
	}

	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDao singleRefOneParent, Root parent) {
		return convertToSingleRefOneParent(singleRefOneParent, parent, new HashMap<>());
	}

	public static SingleRefOneParent convertToSingleRefOneParent(SingleRefOneParentDao singleRefOneParent, Root parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefOneParent result = convertToSingleRefOneParent(singleRefOneParent, mappedObjects);
		if (result != null) {
			parent.setSingleRef(result);
		}
		return result;
	}

	public static SingleRefOneParentDao convertToSingleRefOneParentDao(SingleRefOneParent singleRefOneParent) {
		return convertToSingleRefOneParentDao(singleRefOneParent, new HashMap<>());
	}

	public static SingleRefOneParentDao convertToSingleRefOneParentDao(SingleRefOneParent singleRefOneParent, Map<String, IIdentifiableDao> mappedObjects) {
		if (singleRefOneParent == null) {
			return null;
		}

		String identification = singleRefOneParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (SingleRefOneParentDao) mappedObjects.get(identification);
		}

		SingleRefOneParentDao result = DaoObjectFactory.createSingleRefOneParentDao();

		result.setIdentification(identification);

		result.setDescription(singleRefOneParent.getDescription());

		SingleAccessMapper.convertToSingleRefTwoParentsDao(singleRefOneParent.getSingleRef(), result, mappedObjects);

		mappedObjects.put(identification, result);
		return result;
	}

	public static SingleRefOneParentDao convertToSingleRefOneParentDao(SingleRefOneParent singleRefOneParent, RootDao parent) {
		return convertToSingleRefOneParentDao(singleRefOneParent, parent, new HashMap<>());
	}

	public static SingleRefOneParentDao convertToSingleRefOneParentDao(SingleRefOneParent singleRefOneParent, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		SingleRefOneParentDao result = convertToSingleRefOneParentDao(singleRefOneParent, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.setSingleRef(result);
		}
		return result;
	}

	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDao singleRefTwoParents) {
		return convertToSingleRefTwoParents(singleRefTwoParents, new HashMap<>());
	}

	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDao singleRefTwoParents, Map<String, IIdentifiable> mappedObjects) {
		if (singleRefTwoParents == null) {
			return null;
		}

		String identification = singleRefTwoParents.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (SingleRefTwoParents) mappedObjects.get(identification);
		}

		SingleRefTwoParents result = DomainObjectFactory.createSingleRefTwoParents();

		result.setIdentification(identification);

		result.setDescription(singleRefTwoParents.getDescription());

		mappedObjects.put(identification, result);
		return result;
	}

	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDao singleRefTwoParents, Root parent) {
		return convertToSingleRefTwoParents(singleRefTwoParents, parent, new HashMap<>());
	}

	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDao singleRefTwoParents, Root parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefTwoParents result = convertToSingleRefTwoParents(singleRefTwoParents, mappedObjects);
		if (result != null) {
			parent.setAnotherSingleRef(result);
		}
		return result;
	}

	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDao singleRefTwoParents, SingleRefOneParent parent) {
		return convertToSingleRefTwoParents(singleRefTwoParents, parent, new HashMap<>());
	}

	public static SingleRefTwoParents convertToSingleRefTwoParents(SingleRefTwoParentsDao singleRefTwoParents, SingleRefOneParent parent, Map<String, IIdentifiable> mappedObjects) {
		SingleRefTwoParents result = convertToSingleRefTwoParents(singleRefTwoParents, mappedObjects);
		if (result != null) {
			parent.setSingleRef(result);
		}
		return result;
	}

	public static SingleRefTwoParentsDao convertToSingleRefTwoParentsDao(SingleRefTwoParents singleRefTwoParents) {
		return convertToSingleRefTwoParentsDao(singleRefTwoParents, new HashMap<>());
	}

	public static SingleRefTwoParentsDao convertToSingleRefTwoParentsDao(SingleRefTwoParents singleRefTwoParents, Map<String, IIdentifiableDao> mappedObjects) {
		if (singleRefTwoParents == null) {
			return null;
		}

		String identification = singleRefTwoParents.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (SingleRefTwoParentsDao) mappedObjects.get(identification);
		}

		SingleRefTwoParentsDao result = DaoObjectFactory.createSingleRefTwoParentsDao();

		result.setIdentification(identification);

		result.setDescription(singleRefTwoParents.getDescription());

		mappedObjects.put(identification, result);
		return result;
	}

	public static SingleRefTwoParentsDao convertToSingleRefTwoParentsDao(SingleRefTwoParents singleRefTwoParents, RootDao parent) {
		return convertToSingleRefTwoParentsDao(singleRefTwoParents, parent, new HashMap<>());
	}

	public static SingleRefTwoParentsDao convertToSingleRefTwoParentsDao(SingleRefTwoParents singleRefTwoParents, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		SingleRefTwoParentsDao result = convertToSingleRefTwoParentsDao(singleRefTwoParents, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.setAnotherSingleRef(result);
		}
		return result;
	}

	public static SingleRefTwoParentsDao convertToSingleRefTwoParentsDao(SingleRefTwoParents singleRefTwoParents, SingleRefOneParentDao parent) {
		return convertToSingleRefTwoParentsDao(singleRefTwoParents, parent, new HashMap<>());
	}

	public static SingleRefTwoParentsDao convertToSingleRefTwoParentsDao(SingleRefTwoParents singleRefTwoParents, SingleRefOneParentDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		SingleRefTwoParentsDao result = convertToSingleRefTwoParentsDao(singleRefTwoParents, mappedObjects);
		if (result != null) {
			result.setParentSingleRefOneParent(parent);
			parent.setSingleRef(result);
		}
		return result;
	}

	/**
	 * @return the singleton
	 */
	public static SingleAccessMapper getInstance() {
		if (instance == null) {
			instance = new SingleAccessMapper();
		}
		return instance;
	}

}
