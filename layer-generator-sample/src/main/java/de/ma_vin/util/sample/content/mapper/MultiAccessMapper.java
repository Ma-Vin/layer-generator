package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseAccessMapper;
import de.ma_vin.util.sample.content.dao.DaoObjectFactory;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.dao.multi.MultiRefOneParentDao;
import de.ma_vin.util.sample.content.dao.multi.MultiRefTwoParentsDao;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.multi.MultiRefOneParent;
import de.ma_vin.util.sample.content.domain.multi.MultiRefTwoParents;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@BaseAccessMapper
public class MultiAccessMapper {

	public MultiAccessMapper() {
	}

	/**
	 * singleton
	 */
	private static MultiAccessMapper instance;

	public static MultiRefOneParent convertToMultiRefOneParent(MultiRefOneParentDao multiRefOneParent, boolean includeChildren) {
		return convertToMultiRefOneParent(multiRefOneParent, includeChildren, new HashMap<>());
	}

	public static MultiRefOneParent convertToMultiRefOneParent(MultiRefOneParentDao multiRefOneParent, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		if (multiRefOneParent == null) {
			return null;
		}

		String identification = multiRefOneParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (MultiRefOneParent) mappedObjects.get(identification);
		}

		MultiRefOneParent result = DomainObjectFactory.createMultiRefOneParent();

		result.setIdentification(identification);

		result.setDescription(multiRefOneParent.getDescription());

		if (includeChildren) {
			multiRefOneParent.getMultiRefs().forEach(arg ->
					MultiAccessMapper.convertToMultiRefTwoParents(arg, result, mappedObjects)
			);
		}

		mappedObjects.put(identification, result);
		return result;
	}

	public static MultiRefOneParent convertToMultiRefOneParent(MultiRefOneParentDao multiRefOneParent, boolean includeChildren, Root parent) {
		return convertToMultiRefOneParent(multiRefOneParent, includeChildren, parent, new HashMap<>());
	}

	public static MultiRefOneParent convertToMultiRefOneParent(MultiRefOneParentDao multiRefOneParent, boolean includeChildren, Root parent, Map<String, IIdentifiable> mappedObjects) {
		MultiRefOneParent result = convertToMultiRefOneParent(multiRefOneParent, includeChildren, mappedObjects);
		if (result != null) {
			parent.getMultiRefs().add(result);
		}
		return result;
	}

	public static MultiRefOneParentDao convertToMultiRefOneParentDao(MultiRefOneParent multiRefOneParent, boolean includeChildren) {
		return convertToMultiRefOneParentDao(multiRefOneParent, includeChildren, new HashMap<>());
	}

	public static MultiRefOneParentDao convertToMultiRefOneParentDao(MultiRefOneParent multiRefOneParent, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {
		if (multiRefOneParent == null) {
			return null;
		}

		String identification = multiRefOneParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (MultiRefOneParentDao) mappedObjects.get(identification);
		}

		MultiRefOneParentDao result = DaoObjectFactory.createMultiRefOneParentDao();

		result.setIdentification(identification);

		result.setDescription(multiRefOneParent.getDescription());

		result.setMultiRefs(new ArrayList<>());
		if (includeChildren) {
			multiRefOneParent.getMultiRefs().forEach(arg ->
					MultiAccessMapper.convertToMultiRefTwoParentsDao(arg, result, mappedObjects)
			);
		}

		mappedObjects.put(identification, result);
		return result;
	}

	public static MultiRefOneParentDao convertToMultiRefOneParentDao(MultiRefOneParent multiRefOneParent, boolean includeChildren, RootDao parent) {
		return convertToMultiRefOneParentDao(multiRefOneParent, includeChildren, parent, new HashMap<>());
	}

	public static MultiRefOneParentDao convertToMultiRefOneParentDao(MultiRefOneParent multiRefOneParent, boolean includeChildren, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		MultiRefOneParentDao result = convertToMultiRefOneParentDao(multiRefOneParent, includeChildren, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.getMultiRefs().add(result);
		}
		return result;
	}

	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDao multiRefTwoParents) {
		return convertToMultiRefTwoParents(multiRefTwoParents, new HashMap<>());
	}

	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDao multiRefTwoParents, Map<String, IIdentifiable> mappedObjects) {
		if (multiRefTwoParents == null) {
			return null;
		}

		String identification = multiRefTwoParents.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (MultiRefTwoParents) mappedObjects.get(identification);
		}

		MultiRefTwoParents result = DomainObjectFactory.createMultiRefTwoParents();

		result.setIdentification(identification);

		result.setDescription(multiRefTwoParents.getDescription());

		mappedObjects.put(identification, result);
		return result;
	}

	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDao multiRefTwoParents, MultiRefOneParent parent) {
		return convertToMultiRefTwoParents(multiRefTwoParents, parent, new HashMap<>());
	}

	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDao multiRefTwoParents, MultiRefOneParent parent, Map<String, IIdentifiable> mappedObjects) {
		MultiRefTwoParents result = convertToMultiRefTwoParents(multiRefTwoParents, mappedObjects);
		if (result != null) {
			parent.getMultiRefs().add(result);
		}
		return result;
	}

	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDao multiRefTwoParents, Root parent) {
		return convertToMultiRefTwoParents(multiRefTwoParents, parent, new HashMap<>());
	}

	public static MultiRefTwoParents convertToMultiRefTwoParents(MultiRefTwoParentsDao multiRefTwoParents, Root parent, Map<String, IIdentifiable> mappedObjects) {
		MultiRefTwoParents result = convertToMultiRefTwoParents(multiRefTwoParents, mappedObjects);
		if (result != null) {
			parent.getAnotherMultiRefs().add(result);
		}
		return result;
	}

	public static MultiRefTwoParentsDao convertToMultiRefTwoParentsDao(MultiRefTwoParents multiRefTwoParents) {
		return convertToMultiRefTwoParentsDao(multiRefTwoParents, new HashMap<>());
	}

	public static MultiRefTwoParentsDao convertToMultiRefTwoParentsDao(MultiRefTwoParents multiRefTwoParents, Map<String, IIdentifiableDao> mappedObjects) {
		if (multiRefTwoParents == null) {
			return null;
		}

		String identification = multiRefTwoParents.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (MultiRefTwoParentsDao) mappedObjects.get(identification);
		}

		MultiRefTwoParentsDao result = DaoObjectFactory.createMultiRefTwoParentsDao();

		result.setIdentification(identification);

		result.setDescription(multiRefTwoParents.getDescription());

		mappedObjects.put(identification, result);
		return result;
	}

	public static MultiRefTwoParentsDao convertToMultiRefTwoParentsDao(MultiRefTwoParents multiRefTwoParents, MultiRefOneParentDao parent) {
		return convertToMultiRefTwoParentsDao(multiRefTwoParents, parent, new HashMap<>());
	}

	public static MultiRefTwoParentsDao convertToMultiRefTwoParentsDao(MultiRefTwoParents multiRefTwoParents, MultiRefOneParentDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		MultiRefTwoParentsDao result = convertToMultiRefTwoParentsDao(multiRefTwoParents, mappedObjects);
		if (result != null) {
			result.setParentMultiRefOneParent(parent);
			parent.getMultiRefs().add(result);
		}
		return result;
	}

	public static MultiRefTwoParentsDao convertToMultiRefTwoParentsDao(MultiRefTwoParents multiRefTwoParents, RootDao parent) {
		return convertToMultiRefTwoParentsDao(multiRefTwoParents, parent, new HashMap<>());
	}

	public static MultiRefTwoParentsDao convertToMultiRefTwoParentsDao(MultiRefTwoParents multiRefTwoParents, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		MultiRefTwoParentsDao result = convertToMultiRefTwoParentsDao(multiRefTwoParents, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.getAnotherMultiRefs().add(result);
		}
		return result;
	}

	/**
	 * @return the singleton
	 */
	public static MultiAccessMapper getInstance() {
		if (instance == null) {
			instance = AccessMapperFactory.createMultiAccessMapper();
		}
		return instance;
	}

}
