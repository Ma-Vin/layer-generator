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

/**
 * Generated class which provides methods to convert a data access to a domain object of sub package <i>null<i> and the other way around
 */
@BaseAccessMapper
public class CommonAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static CommonAccessMapper instance;

	/**
	 * Converts a(n) {@link RootDao} to a(n) {@link Root}
	 *
	 * @param root            the source object which should be converted
	 * @param includeChildren {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are not
	 *                        of type {@link java.util.Collection}
	 * @return an equivalent new created {@link Root}
	 */
	public static Root convertToRoot(RootDao root, boolean includeChildren) {
		return convertToRoot(root, includeChildren, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link RootDao} to a(n) {@link Root}
	 *
	 * @param root            the source object which should be converted
	 * @param includeChildren {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are not
	 *                        of type {@link java.util.Collection}
	 * @param mappedObjects   map which contains already mapped objects. If an identification of {@code root} is contained, the found {@link Root} will be
	 *                        returned
	 * @return an equivalent new created {@link Root} or the found one from the given map
	 */
	public static Root convertToRoot(RootDao root, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(root, mappedObjects, DomainObjectFactory::createRoot, (dao, domain) -> getInstance().setRootValues(dao, domain)
				, (dao, domain) -> getInstance().setRootSingleReferences(dao, domain, includeChildren, mappedObjects)
				, (dao, domain) -> getInstance().setRootMultiReferences(dao, domain, includeChildren, mappedObjects));
	}

	/**
	 * Converts a(n) {@link Root} to a(n) {@link RootDao}
	 *
	 * @param root            the source object which should be converted
	 * @param includeChildren {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are not
	 *                        of type {@link java.util.Collection}
	 * @return an equivalent new created {@link RootDao}
	 */
	public static RootDao convertToRootDao(Root root, boolean includeChildren) {
		return convertToRootDao(root, includeChildren, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link Root} to a(n) {@link RootDao}
	 *
	 * @param root            the source object which should be converted
	 * @param includeChildren {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are not
	 *                        of type {@link java.util.Collection}
	 * @param mappedObjects   map which contains already mapped objects. If an identification of {@code root} is contained, the found {@link RootDao} will
	 *                        be returned
	 * @return an equivalent new created {@link RootDao} or the found one from the given map
	 */
	public static RootDao convertToRootDao(Root root, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(root, mappedObjects, DaoObjectFactory::createRootDao, (domain, dao) -> getInstance().setRootDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setRootDaoSingleReferences(domain, dao, includeChildren, mappedObjects)
				, (domain, dao) -> getInstance().setRootDaoMultiReferences(domain, dao, includeChildren, mappedObjects));
	}

	/**
	 * Converts a(n) {@link RootExtDao} to a(n) {@link RootExt}
	 *
	 * @param rootExt the source object which should be converted
	 * @return an equivalent new created {@link RootExt}
	 */
	public static RootExt convertToRootExt(RootExtDao rootExt) {
		return convertToRootExt(rootExt, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link RootExtDao} to a(n) {@link RootExt}
	 *
	 * @param rootExt       the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code rootExt} is contained, the found {@link RootExt}
	 *                      will be returned
	 * @return an equivalent new created {@link RootExt} or the found one from the given map
	 */
	public static RootExt convertToRootExt(RootExtDao rootExt, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(rootExt, mappedObjects, DomainObjectFactory::createRootExt, (dao, domain) -> getInstance().setRootExtValues(dao, domain)
				, (dao, domain) -> getInstance().setRootExtSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setRootExtMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link RootExtDao} to a(n) {@link RootExt} and sets the result to the corresponding reference property at the parent
	 *
	 * @param rootExt the source object which should be converted
	 * @param parent  the parent of converted result
	 * @return an equivalent new created {@link RootExt}
	 */
	public static RootExt convertToRootExt(RootExtDao rootExt, Root parent) {
		return convertToRootExt(rootExt, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link RootExtDao} to a(n) {@link RootExt} and sets the result to the corresponding reference property at the parent
	 *
	 * @param rootExt       the source object which should be converted
	 * @param parent        the parent of converted result
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code rootExt} is contained, the found {@link RootExt}
	 *                      will be returned
	 * @return an equivalent new created {@link RootExt} or the found one from the given map
	 */
	public static RootExt convertToRootExt(RootExtDao rootExt, Root parent, Map<String, IIdentifiable> mappedObjects) {
		RootExt result = convertToRootExt(rootExt, mappedObjects);
		if (result != null) {
			parent.setExt(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link RootExt} to a(n) {@link RootExtDao}
	 *
	 * @param rootExt the source object which should be converted
	 * @return an equivalent new created {@link RootExtDao}
	 */
	public static RootExtDao convertToRootExtDao(RootExt rootExt) {
		return convertToRootExtDao(rootExt, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link RootExt} to a(n) {@link RootExtDao}
	 *
	 * @param rootExt       the source object which should be converted
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code rootExt} is contained, the found {@link RootExtDao}
	 *                      will be returned
	 * @return an equivalent new created {@link RootExtDao} or the found one from the given map
	 */
	public static RootExtDao convertToRootExtDao(RootExt rootExt, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(rootExt, mappedObjects, DaoObjectFactory::createRootExtDao, (domain, dao) -> getInstance().setRootExtDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setRootExtDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setRootExtDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * Converts a(n) {@link RootExt} to a(n) {@link RootExtDao} and sets the result to the corresponding reference property at the parent
	 *
	 * @param rootExt the source object which should be converted
	 * @param parent  the parent of converted result
	 * @return an equivalent new created {@link RootExtDao}
	 */
	public static RootExtDao convertToRootExtDao(RootExt rootExt, RootDao parent) {
		return convertToRootExtDao(rootExt, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link RootExt} to a(n) {@link RootExtDao} and sets the result to the corresponding reference property at the parent
	 *
	 * @param rootExt       the source object which should be converted
	 * @param parent        the parent of converted result
	 * @param mappedObjects map which contains already mapped objects. If an identification of {@code rootExt} is contained, the found {@link RootExtDao}
	 *                      will be returned
	 * @return an equivalent new created {@link RootExtDao} or the found one from the given map
	 */
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

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain          source of the given references
	 * @param dao             object where to add the references
	 * @param includeChildren {@code true} if references should be mapped. Otherwise {@code false}
	 * @param mappedObjects   map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	protected void setRootDaoMultiReferences(Root domain, RootDao dao, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {
		dao.setMultiRef(new ArrayList<>());
		dao.setAnotherMultiRef(new ArrayList<>());
		dao.setMultiRefIndirectParent(new ArrayList<>());
		dao.setMultiRefIndirectOtherParent(new ArrayList<>());
		dao.setExtending(new ArrayList<>());
		if (includeChildren) {
			domain.getMultiRef().forEach(arg ->
					MultiAccessMapper.convertToMultiRefOneParentDao(arg, true, dao, mappedObjects)
			);
			domain.getAnotherMultiRef().forEach(arg ->
					MultiAccessMapper.convertToMultiRefTwoParentsDao(arg, dao, mappedObjects)
			);
			domain.getMultiRefIndirectParent().forEach(arg ->
					MultiIndirectAccessMapper.convertToMultiRefIndirectParentDao(arg, dao, mappedObjects)
			);
			domain.getMultiRefIndirectOtherParent().forEach(arg ->
					MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParentDao(arg, true, dao, mappedObjects)
			);
			domain.getExtending().forEach(arg ->
					ParentAccessMapper.convertToExtendingClassDao(arg, dao, mappedObjects)
			);
		}
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain          source of the given references
	 * @param dao             object where to add the references
	 * @param includeChildren {@code true} if all references at sub entities of the single reference should also be mapped. {@code false} if only those
	 *                        references should be mapped which are not of type {@link java.util.Collection}
	 * @param mappedObjects   map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	protected void setRootDaoSingleReferences(Root domain, RootDao dao, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {
		SingleAccessMapper.convertToSingleRefOneParentDao(domain.getSingleRef(), dao, mappedObjects);
		SingleAccessMapper.convertToSingleRefTwoParentsDao(domain.getAnotherSingleRef(), dao, mappedObjects);
		SingleIndirectAccessMapper.convertToSingleRefIndirectParentDao(domain.getSingleRefIndirectParent(), dao, mappedObjects);
		SingleIndirectAccessMapper.convertToSingleRefOtherIndirectParentDao(domain.getSingleRefIndirectOtherParent(), dao, mappedObjects);
		FilteringAccessMapper.convertToSomeFilteringOwnerDao(domain.getFiltering(), includeChildren, dao, mappedObjects);
		FilteringAccessMapper.convertToSomeDifferentFilteringNotOwnerDao(domain.getNonOwnerFiltering(), includeChildren, dao, mappedObjects);
		CommonAccessMapper.convertToRootExtDao(domain.getExt(), dao, mappedObjects);
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	protected void setRootDaoValues(Root domain, RootDao dao) {
		dao.setRootName(domain.getRootName());
		dao.setDescription(domain.getDescription());
	}

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setRootExtDaoMultiReferences(RootExt domain, RootExtDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setRootExtDaoSingleReferences(RootExt domain, RootExtDao dao, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
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
	protected void setRootExtMultiReferences(RootExtDao dao, RootExt domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setRootExtSingleReferences(RootExtDao dao, RootExt domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
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
		domain.setDocument(dao.getDocument());
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao             source of the given references
	 * @param domain          object where to add the references
	 * @param includeChildren {@code true} if references should be mapped. Otherwise {@code false}
	 * @param mappedObjects   map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	protected void setRootMultiReferences(RootDao dao, Root domain, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		if (includeChildren) {
			dao.getMultiRef().forEach(arg ->
					MultiAccessMapper.convertToMultiRefOneParent(arg, true, domain, mappedObjects)
			);
			dao.getAnotherMultiRef().forEach(arg ->
					MultiAccessMapper.convertToMultiRefTwoParents(arg, domain, mappedObjects)
			);
			dao.getMultiRefIndirectParent().forEach(arg ->
					MultiIndirectAccessMapper.convertToMultiRefIndirectParent(arg, domain, mappedObjects)
			);
			dao.getMultiRefIndirectOtherParent().forEach(arg ->
					MultiIndirectAccessMapper.convertToMultiRefOtherIndirectParent(arg, true, domain, mappedObjects)
			);
			dao.getExtending().forEach(arg ->
					ParentAccessMapper.convertToExtendingClass(arg, domain, mappedObjects)
			);
		}
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao             source of the given references
	 * @param domain          object where to add the references
	 * @param includeChildren {@code true} if all references at sub entities of the single reference should also be mapped. {@code false} if only those
	 *                        references should be mapped which are not of type {@link java.util.Collection}
	 * @param mappedObjects   map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	protected void setRootSingleReferences(RootDao dao, Root domain, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		SingleAccessMapper.convertToSingleRefOneParent(dao.getSingleRef(), domain, mappedObjects);
		SingleAccessMapper.convertToSingleRefTwoParents(dao.getAnotherSingleRef(), domain, mappedObjects);
		SingleIndirectAccessMapper.convertToSingleRefIndirectParent(dao.getSingleRefIndirectParent(), domain, mappedObjects);
		SingleIndirectAccessMapper.convertToSingleRefOtherIndirectParent(dao.getSingleRefIndirectOtherParent(), domain, mappedObjects);
		FilteringAccessMapper.convertToSomeFilteringOwner(dao.getFiltering(), includeChildren, domain, mappedObjects);
		FilteringAccessMapper.convertToSomeDifferentFilteringNotOwner(dao.getNonOwnerFiltering(), includeChildren, domain, mappedObjects);
		CommonAccessMapper.convertToRootExt(dao.getExt(), domain, mappedObjects);
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setRootValues(RootDao dao, Root domain) {
		domain.setRootName(dao.getRootName());
		domain.setDescription(dao.getDescription());
	}

}
