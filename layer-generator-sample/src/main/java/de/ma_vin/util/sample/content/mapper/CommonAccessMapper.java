package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.layer.generator.annotations.mapper.BaseAccessMapper;
import de.ma_vin.util.sample.content.dao.DaoObjectFactory;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.dao.RootExtDao;
import de.ma_vin.util.sample.content.domain.DomainObjectFactory;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.domain.RootExt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@BaseAccessMapper
public class CommonAccessMapper {

	public CommonAccessMapper() {
	}

	/**
	 * singleton
	 */
	private static CommonAccessMapper instance;

	public static Root convertToRoot(RootDao root, boolean includeChildren) {
		return convertToRoot(root, includeChildren, new HashMap<>());
	}

	public static Root convertToRoot(RootDao root, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		if (root == null) {
			return null;
		}

		String identification = root.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (Root) mappedObjects.get(identification);
		}

		Root result = DomainObjectFactory.createRoot();

		result.setIdentification(identification);

		result.setRootName(root.getRootName());
		result.setDescription(root.getDescription());

		SingleAccessMapper.convertToSingleRefOneParent(root.getSingleRef(), result, mappedObjects);
		SingleAccessMapper.convertToSingleRefTwoParents(root.getAnotherSingleRef(), result, mappedObjects);
		SingleIndirectAccessMapper.convertToSingleRefIndirectParent(root.getSingleRefIndirectParent(), result, mappedObjects);
		SingleIndirectAccessMapper.convertToSingleRefOtherIndirectParent(root.getSingleRefIndirectOtherParent(), result, mappedObjects);
		FilteringAccessMapper.convertToSomeFilteringOwner(root.getFiltering(), includeChildren, result, mappedObjects);
		CommonAccessMapper.convertToRootExt(root.getExt(), result, mappedObjects);

		if (includeChildren) {
			root.getMultiRefs().forEach(arg ->
					MultiAccessMapper.convertToMultiRefOneParent(arg, true, result, mappedObjects)
			);
			root.getAnotherMultiRefs().forEach(arg ->
					MultiAccessMapper.convertToMultiRefTwoParents(arg, result, mappedObjects)
			);
			root.getMultiRefIndirectParents().forEach(arg ->
					MultiIndirectAccessMapper.convertToMultiRefIndirectParent(arg, result, mappedObjects)
			);
			root.getMultiRefIndirectOtherParents().forEach(arg ->
					MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParent(arg, true, result, mappedObjects)
			);
			root.getExtendings().forEach(arg ->
					ParentAccessMapper.convertToExtendingClass(arg, result, mappedObjects)
			);
		}

		mappedObjects.put(identification, result);
		return result;
	}

	public static RootDao convertToRootDao(Root root, boolean includeChildren) {
		return convertToRootDao(root, includeChildren, new HashMap<>());
	}

	public static RootDao convertToRootDao(Root root, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {
		if (root == null) {
			return null;
		}

		String identification = root.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (RootDao) mappedObjects.get(identification);
		}

		RootDao result = DaoObjectFactory.createRootDao();

		result.setIdentification(identification);

		result.setRootName(root.getRootName());
		result.setDescription(root.getDescription());

		SingleAccessMapper.convertToSingleRefOneParentDao(root.getSingleRef(), result, mappedObjects);
		SingleAccessMapper.convertToSingleRefTwoParentsDao(root.getAnotherSingleRef(), result, mappedObjects);
		SingleIndirectAccessMapper.convertToSingleRefIndirectParentDao(root.getSingleRefIndirectParent(), result, mappedObjects);
		SingleIndirectAccessMapper.convertToSingleRefOtherIndirectParentDao(root.getSingleRefIndirectOtherParent(), result, mappedObjects);
		FilteringAccessMapper.convertToSomeFilteringOwnerDao(root.getFiltering(), includeChildren, result, mappedObjects);
		CommonAccessMapper.convertToRootExtDao(root.getExt(), result, mappedObjects);

		result.setMultiRefs(new ArrayList<>());
		result.setAnotherMultiRefs(new ArrayList<>());
		result.setMultiRefIndirectParents(new ArrayList<>());
		result.setMultiRefIndirectOtherParents(new ArrayList<>());
		result.setExtendings(new ArrayList<>());
		if (includeChildren) {
			root.getMultiRefs().forEach(arg ->
					MultiAccessMapper.convertToMultiRefOneParentDao(arg, true, result, mappedObjects)
			);
			root.getAnotherMultiRefs().forEach(arg ->
					MultiAccessMapper.convertToMultiRefTwoParentsDao(arg, result, mappedObjects)
			);
			root.getMultiRefIndirectParents().forEach(arg ->
					MultiIndirectAccessMapper.convertToMultiRefIndirectParentDao(arg, result, mappedObjects)
			);
			root.getMultiRefIndirectOtherParents().forEach(arg ->
					MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParentDao(arg, true, result, mappedObjects)
			);
			root.getExtendings().forEach(arg ->
					ParentAccessMapper.convertToExtendingClassDao(arg, result, mappedObjects)
			);
		}

		mappedObjects.put(identification, result);
		return result;
	}

	public static RootExt convertToRootExt(RootExtDao rootExt) {
		return convertToRootExt(rootExt, new HashMap<>());
	}

	public static RootExt convertToRootExt(RootExtDao rootExt, Map<String, IIdentifiable> mappedObjects) {
		if (rootExt == null) {
			return null;
		}

		String identification = rootExt.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (RootExt) mappedObjects.get(identification);
		}

		RootExt result = DomainObjectFactory.createRootExt();

		result.setIdentification(identification);

		result.setExtendedInfo(rootExt.getExtendedInfo());
		result.setSomeEnum(rootExt.getSomeEnum());
		result.setSomeInteger(rootExt.getSomeInteger());
		result.setSomeCustom(rootExt.getSomeCustom());
		result.setDaoAndDomain(rootExt.getDaoAndDomain());
		result.setTextWithDaoInfo(rootExt.getTextWithDaoInfo());
		result.setNumberWithDaoInfo(rootExt.getNumberWithDaoInfo());
		result.setDaoEnum(rootExt.getDaoEnum());
		result.setDaoEnumWithText(rootExt.getDaoEnumWithText());
		result.setSomeName(rootExt.getSomeName());

		mappedObjects.put(identification, result);
		return result;
	}

	public static RootExt convertToRootExt(RootExtDao rootExt, Root parent) {
		return convertToRootExt(rootExt, parent, new HashMap<>());
	}

	public static RootExt convertToRootExt(RootExtDao rootExt, Root parent, Map<String, IIdentifiable> mappedObjects) {
		RootExt result = convertToRootExt(rootExt, mappedObjects);
		if (result != null) {
			parent.setExt(result);
		}
		return result;
	}

	public static RootExtDao convertToRootExtDao(RootExt rootExt) {
		return convertToRootExtDao(rootExt, new HashMap<>());
	}

	public static RootExtDao convertToRootExtDao(RootExt rootExt, Map<String, IIdentifiableDao> mappedObjects) {
		if (rootExt == null) {
			return null;
		}

		String identification = rootExt.getIdentification();
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (RootExtDao) mappedObjects.get(identification);
		}

		RootExtDao result = DaoObjectFactory.createRootExtDao();

		result.setIdentification(identification);

		result.setExtendedInfo(rootExt.getExtendedInfo());
		result.setSomeEnum(rootExt.getSomeEnum());
		result.setSomeInteger(rootExt.getSomeInteger());
		result.setSomeCustom(rootExt.getSomeCustom());
		result.setDaoAndDomain(rootExt.getDaoAndDomain());
		result.setTextWithDaoInfo(rootExt.getTextWithDaoInfo());
		result.setNumberWithDaoInfo(rootExt.getNumberWithDaoInfo());
		result.setDaoEnum(rootExt.getDaoEnum());
		result.setDaoEnumWithText(rootExt.getDaoEnumWithText());
		result.setSomeName(rootExt.getSomeName());

		mappedObjects.put(identification, result);
		return result;
	}

	public static RootExtDao convertToRootExtDao(RootExt rootExt, RootDao parent) {
		return convertToRootExtDao(rootExt, parent, new HashMap<>());
	}

	public static RootExtDao convertToRootExtDao(RootExt rootExt, RootDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		RootExtDao result = convertToRootExtDao(rootExt, mappedObjects);
		if (result != null) {
			result.setParentRoot(parent);
			parent.setExt(result);
		}
		return result;
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

}
