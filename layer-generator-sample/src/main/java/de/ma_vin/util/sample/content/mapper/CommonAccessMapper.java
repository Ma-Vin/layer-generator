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
public class CommonAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static CommonAccessMapper instance;

	public static Root convertToRoot(RootDao root, boolean includeChildren) {
		return convertToRoot(root, includeChildren, new HashMap<>());
	}

	public static Root convertToRoot(RootDao root, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(root, mappedObjects, DomainObjectFactory::createRoot, (dao, domain) -> getInstance().setRootValues(dao, domain)
				, (dao, domain) -> getInstance().setRootSingleReferences(dao, domain, includeChildren, mappedObjects)
				, (dao, domain) -> getInstance().setRootMultiReferences(dao, domain, includeChildren, mappedObjects));
	}

	public static RootDao convertToRootDao(Root root, boolean includeChildren) {
		return convertToRootDao(root, includeChildren, new HashMap<>());
	}

	public static RootDao convertToRootDao(Root root, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(root, mappedObjects, DaoObjectFactory::createRootDao, (domain, dao) -> getInstance().setRootDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setRootDaoSingleReferences(domain, dao, includeChildren, mappedObjects)
				, (domain, dao) -> getInstance().setRootDaoMultiReferences(domain, dao, includeChildren, mappedObjects));
	}

	public static RootExt convertToRootExt(RootExtDao rootExt) {
		return convertToRootExt(rootExt, new HashMap<>());
	}

	public static RootExt convertToRootExt(RootExtDao rootExt, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(rootExt, mappedObjects, DomainObjectFactory::createRootExt, (dao, domain) -> getInstance().setRootExtValues(dao, domain)
				, (dao, domain) -> getInstance().setRootExtSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setRootExtMultiReferences(dao, domain, mappedObjects));
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
		return convertToDao(rootExt, mappedObjects, DaoObjectFactory::createRootExtDao, (domain, dao) -> getInstance().setRootExtDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setRootExtDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setRootExtDaoMultiReferences(domain, dao, mappedObjects));
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

	protected void setRootDaoMultiReferences(Root domain, RootDao dao, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {
		dao.setMultiRefs(new ArrayList<>());
		dao.setAnotherMultiRefs(new ArrayList<>());
		dao.setMultiRefIndirectParents(new ArrayList<>());
		dao.setMultiRefIndirectOtherParents(new ArrayList<>());
		dao.setExtendings(new ArrayList<>());
		if (includeChildren) {
			domain.getMultiRefs().forEach(arg ->
					MultiAccessMapper.convertToMultiRefOneParentDao(arg, true, dao, mappedObjects)
			);
			domain.getAnotherMultiRefs().forEach(arg ->
					MultiAccessMapper.convertToMultiRefTwoParentsDao(arg, dao, mappedObjects)
			);
			domain.getMultiRefIndirectParents().forEach(arg ->
					MultiIndirectAccessMapper.convertToMultiRefIndirectParentDao(arg, dao, mappedObjects)
			);
			domain.getMultiRefIndirectOtherParents().forEach(arg ->
					MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParentDao(arg, true, dao, mappedObjects)
			);
			domain.getExtendings().forEach(arg ->
					ParentAccessMapper.convertToExtendingClassDao(arg, dao, mappedObjects)
			);
		}
	}

	protected void setRootDaoSingleReferences(Root domain, RootDao dao, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {
		SingleAccessMapper.convertToSingleRefOneParentDao(domain.getSingleRef(), dao, mappedObjects);
		SingleAccessMapper.convertToSingleRefTwoParentsDao(domain.getAnotherSingleRef(), dao, mappedObjects);
		SingleIndirectAccessMapper.convertToSingleRefIndirectParentDao(domain.getSingleRefIndirectParent(), dao, mappedObjects);
		SingleIndirectAccessMapper.convertToSingleRefOtherIndirectParentDao(domain.getSingleRefIndirectOtherParent(), dao, mappedObjects);
		FilteringAccessMapper.convertToSomeFilteringOwnerDao(domain.getFiltering(), includeChildren, dao, mappedObjects);
		CommonAccessMapper.convertToRootExtDao(domain.getExt(), dao, mappedObjects);
	}

	protected void setRootDaoValues(Root domain, RootDao dao) {
		dao.setRootName(domain.getRootName());
		dao.setDescription(domain.getDescription());
	}

	@SuppressWarnings("java:S1186")
	protected void setRootExtDaoMultiReferences(RootExt domain, RootExtDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setRootExtDaoSingleReferences(RootExt domain, RootExtDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	protected void setRootExtDaoValues(RootExt domain, RootExtDao dao) {
		dao.setExtendedInfo(domain.getExtendedInfo());
		dao.setSomeEnum(domain.getSomeEnum());
		dao.setSomeInteger(domain.getSomeInteger());
		dao.setSomeCustom(domain.getSomeCustom());
		dao.setDaoAndDomain(domain.getDaoAndDomain());
		dao.setTextWithDaoInfo(domain.getTextWithDaoInfo());
		dao.setNumberWithDaoInfo(domain.getNumberWithDaoInfo());
		dao.setDaoEnum(domain.getDaoEnum());
		dao.setDaoEnumWithText(domain.getDaoEnumWithText());
		dao.setSomeName(domain.getSomeName());
	}

	@SuppressWarnings("java:S1186")
	protected void setRootExtMultiReferences(RootExtDao dao, RootExt domain, Map<String, IIdentifiable> mappedObjects) {
	}

	@SuppressWarnings("java:S1186")
	protected void setRootExtSingleReferences(RootExtDao dao, RootExt domain, Map<String, IIdentifiable> mappedObjects) {
	}

	protected void setRootExtValues(RootExtDao dao, RootExt domain) {
		domain.setExtendedInfo(dao.getExtendedInfo());
		domain.setSomeEnum(dao.getSomeEnum());
		domain.setSomeInteger(dao.getSomeInteger());
		domain.setSomeCustom(dao.getSomeCustom());
		domain.setDaoAndDomain(dao.getDaoAndDomain());
		domain.setTextWithDaoInfo(dao.getTextWithDaoInfo());
		domain.setNumberWithDaoInfo(dao.getNumberWithDaoInfo());
		domain.setDaoEnum(dao.getDaoEnum());
		domain.setDaoEnumWithText(dao.getDaoEnumWithText());
		domain.setSomeName(dao.getSomeName());
	}

	protected void setRootMultiReferences(RootDao dao, Root domain, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		if (includeChildren) {
			dao.getMultiRefs().forEach(arg ->
					MultiAccessMapper.convertToMultiRefOneParent(arg, true, domain, mappedObjects)
			);
			dao.getAnotherMultiRefs().forEach(arg ->
					MultiAccessMapper.convertToMultiRefTwoParents(arg, domain, mappedObjects)
			);
			dao.getMultiRefIndirectParents().forEach(arg ->
					MultiIndirectAccessMapper.convertToMultiRefIndirectParent(arg, domain, mappedObjects)
			);
			dao.getMultiRefIndirectOtherParents().forEach(arg ->
					MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParent(arg, true, domain, mappedObjects)
			);
			dao.getExtendings().forEach(arg ->
					ParentAccessMapper.convertToExtendingClass(arg, domain, mappedObjects)
			);
		}
	}

	protected void setRootSingleReferences(RootDao dao, Root domain, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		SingleAccessMapper.convertToSingleRefOneParent(dao.getSingleRef(), domain, mappedObjects);
		SingleAccessMapper.convertToSingleRefTwoParents(dao.getAnotherSingleRef(), domain, mappedObjects);
		SingleIndirectAccessMapper.convertToSingleRefIndirectParent(dao.getSingleRefIndirectParent(), domain, mappedObjects);
		SingleIndirectAccessMapper.convertToSingleRefOtherIndirectParent(dao.getSingleRefIndirectOtherParent(), domain, mappedObjects);
		FilteringAccessMapper.convertToSomeFilteringOwner(dao.getFiltering(), includeChildren, domain, mappedObjects);
		CommonAccessMapper.convertToRootExt(dao.getExt(), domain, mappedObjects);
	}

	protected void setRootValues(RootDao dao, Root domain) {
		domain.setRootName(dao.getRootName());
		domain.setDescription(dao.getDescription());
	}

}
