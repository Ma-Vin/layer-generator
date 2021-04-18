package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseAccessMapper;
import de.ma_vin.util.sample.content.dao.DaoObjectFactory;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.dao.multi.indirect.MultiRefIndirectParentDao;
import de.ma_vin.util.sample.content.dao.multi.indirect.MultiRefOtherIndirectParentDao;
import de.ma_vin.util.sample.content.dao.multi.indirect.MultiRefOtherIndirectParentToMultiRefIndirectParentDao;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.multi.indirect.MultiRefIndirectParent;
import de.ma_vin.util.sample.content.domain.multi.indirect.MultiRefOtherIndirectParent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@BaseAccessMapper
public class MultiIndirectAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static MultiIndirectAccessMapper instance;

	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDao multiRefIndirectParent) {
		return convertToMultiRefIndirectParent(multiRefIndirectParent, new HashMap<>());
	}

	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDao multiRefIndirectParent, Map<String, IIdentifiable> mappedObjects) {
		if (multiRefIndirectParent == null) {
			return null;
		}

		String identification = multiRefIndirectParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (MultiRefIndirectParent) mappedObjects.get(identification);
		}

		MultiRefIndirectParent result = DomainObjectFactory.createMultiRefIndirectParent();

		result.setIdentification(identification);

		result.setDescription(multiRefIndirectParent.getDescription());

		mappedObjects.put(identification, result);
		return result;
	}

	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDao multiRefIndirectParent, MultiRefOtherIndirectParent parent) {
		return convertToMultiRefIndirectParent(multiRefIndirectParent, parent, new HashMap<>());
	}

	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDao multiRefIndirectParent, MultiRefOtherIndirectParent parent, Map<String, IIdentifiable> mappedObjects) {
		MultiRefIndirectParent result = convertToMultiRefIndirectParent(multiRefIndirectParent, mappedObjects);
		if (result != null) {
			parent.getMultiIndirectRefs().add(result);
		}
		return result;
	}

	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDao multiRefIndirectParent, Root parent) {
		return convertToMultiRefIndirectParent(multiRefIndirectParent, parent, new HashMap<>());
	}

	public static MultiRefIndirectParent convertToMultiRefIndirectParent(MultiRefIndirectParentDao multiRefIndirectParent, Root parent, Map<String, IIdentifiable> mappedObjects) {
		MultiRefIndirectParent result = convertToMultiRefIndirectParent(multiRefIndirectParent, mappedObjects);
		if (result != null) {
			parent.getMultiRefIndirectParents().add(result);
		}
		return result;
	}

	public static MultiRefIndirectParentDao convertToMultiRefIndirectParentDao(MultiRefIndirectParent multiRefIndirectParent) {
		return convertToMultiRefIndirectParentDao(multiRefIndirectParent, new HashMap<>());
	}

	public static MultiRefIndirectParentDao convertToMultiRefIndirectParentDao(MultiRefIndirectParent multiRefIndirectParent, Map<String, IIdentifiableDao> mappedObjects) {
		if (multiRefIndirectParent == null) {
			return null;
		}

		String identification = multiRefIndirectParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (MultiRefIndirectParentDao) mappedObjects.get(identification);
		}

		MultiRefIndirectParentDao result = DaoObjectFactory.createMultiRefIndirectParentDao();

		result.setIdentification(identification);

		result.setDescription(multiRefIndirectParent.getDescription());

		mappedObjects.put(identification, result);
		return result;
	}

	public static MultiRefIndirectParentDao convertToMultiRefIndirectParentDao(MultiRefIndirectParent multiRefIndirectParent, MultiRefOtherIndirectParentDao parent) {
		return convertToMultiRefIndirectParentDao(multiRefIndirectParent, parent, new HashMap<>());
	}

	public static MultiRefIndirectParentDao convertToMultiRefIndirectParentDao(MultiRefIndirectParent multiRefIndirectParent, MultiRefOtherIndirectParentDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		MultiRefIndirectParentDao result = convertToMultiRefIndirectParentDao(multiRefIndirectParent, mappedObjects);
		if (result != null) {
			MultiRefOtherIndirectParentToMultiRefIndirectParentDao connectionTable = DaoObjectFactory.createMultiRefOtherIndirectParentToMultiRefIndirectParentDao();
			connectionTable.setMultiRefIndirectParent(result);
			connectionTable.setMultiRefOtherIndirectParent(parent);
			parent.getMultiIndirectRefs().add(connectionTable);
		}
		return result;
	}

	public static MultiRefIndirectParentDao convertToMultiRefIndirectParentDao(MultiRefIndirectParent multiRefIndirectParent, RootDao parent) {
		return convertToMultiRefIndirectParentDao(multiRefIndirectParent, parent, new HashMap<>());
	}

	public static MultiRefIndirectParentDao convertToMultiRefIndirectParentDao(MultiRefIndirectParent multiRefIndirectParent, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		MultiRefIndirectParentDao result = convertToMultiRefIndirectParentDao(multiRefIndirectParent, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.getMultiRefIndirectParents().add(result);
		}
		return result;
	}

	public static MultiRefOtherIndirectParent convertToMultiRefOtherIndirectParent(MultiRefOtherIndirectParentDao multiRefOtherIndirectParent, boolean includeChildren) {
		return convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParent, includeChildren, new HashMap<>());
	}

	public static MultiRefOtherIndirectParent convertToMultiRefOtherIndirectParent(MultiRefOtherIndirectParentDao multiRefOtherIndirectParent, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		if (multiRefOtherIndirectParent == null) {
			return null;
		}

		String identification = multiRefOtherIndirectParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (MultiRefOtherIndirectParent) mappedObjects.get(identification);
		}

		MultiRefOtherIndirectParent result = DomainObjectFactory.createMultiRefOtherIndirectParent();

		result.setIdentification(identification);

		result.setDescription(multiRefOtherIndirectParent.getDescription());

		if (includeChildren) {
			multiRefOtherIndirectParent.getMultiIndirectRefs().forEach(arg ->
					MultiIndirectAccessMapper.convertToMultiRefIndirectParent(arg.getMultiRefIndirectParent(), result, mappedObjects)
			);
		}

		mappedObjects.put(identification, result);
		return result;
	}

	public static MultiRefOtherIndirectParent convertToMultiRefOtherIndirectParent(MultiRefOtherIndirectParentDao multiRefOtherIndirectParent, boolean includeChildren, Root parent) {
		return convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParent, includeChildren, parent, new HashMap<>());
	}

	public static MultiRefOtherIndirectParent convertToMultiRefOtherIndirectParent(MultiRefOtherIndirectParentDao multiRefOtherIndirectParent, boolean includeChildren, Root parent, Map<String, IIdentifiable> mappedObjects) {
		MultiRefOtherIndirectParent result = convertToMultiRefOtherIndirectParent(multiRefOtherIndirectParent, includeChildren, mappedObjects);
		if (result != null) {
			parent.getMultiRefIndirectOtherParents().add(result);
		}
		return result;
	}

	public static MultiRefOtherIndirectParentDao convertToMultiRefOtherIndirectParentDao(MultiRefOtherIndirectParent multiRefOtherIndirectParent, boolean includeChildren) {
		return convertToMultiRefOtherIndirectParentDao(multiRefOtherIndirectParent, includeChildren, new HashMap<>());
	}

	public static MultiRefOtherIndirectParentDao convertToMultiRefOtherIndirectParentDao(MultiRefOtherIndirectParent multiRefOtherIndirectParent, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {
		if (multiRefOtherIndirectParent == null) {
			return null;
		}

		String identification = multiRefOtherIndirectParent.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (MultiRefOtherIndirectParentDao) mappedObjects.get(identification);
		}

		MultiRefOtherIndirectParentDao result = DaoObjectFactory.createMultiRefOtherIndirectParentDao();

		result.setIdentification(identification);

		result.setDescription(multiRefOtherIndirectParent.getDescription());

		result.setMultiIndirectRefs(new ArrayList<>());
		if (includeChildren) {
			multiRefOtherIndirectParent.getMultiIndirectRefs().forEach(arg -> {
				MultiRefOtherIndirectParentToMultiRefIndirectParentDao connectionTable = new MultiRefOtherIndirectParentToMultiRefIndirectParentDao();
				connectionTable.setMultiRefOtherIndirectParent(result);
				connectionTable.setMultiRefIndirectParent(MultiIndirectAccessMapper.convertToMultiRefIndirectParentDao(arg, mappedObjects));
				result.getMultiIndirectRefs().add(connectionTable);
			});
		}

		mappedObjects.put(identification, result);
		return result;
	}

	public static MultiRefOtherIndirectParentDao convertToMultiRefOtherIndirectParentDao(MultiRefOtherIndirectParent multiRefOtherIndirectParent, boolean includeChildren, RootDao parent) {
		return convertToMultiRefOtherIndirectParentDao(multiRefOtherIndirectParent, includeChildren, parent, new HashMap<>());
	}

	public static MultiRefOtherIndirectParentDao convertToMultiRefOtherIndirectParentDao(MultiRefOtherIndirectParent multiRefOtherIndirectParent, boolean includeChildren, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		MultiRefOtherIndirectParentDao result = convertToMultiRefOtherIndirectParentDao(multiRefOtherIndirectParent, includeChildren, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.getMultiRefIndirectOtherParents().add(result);
		}
		return result;
	}

	/**
	 * @return the singleton
	 */
	public static MultiIndirectAccessMapper getInstance() {
		if (instance == null) {
			instance = AccessMapperFactory.createMultiIndirectAccessMapper();
		}
		return instance;
	}

}
