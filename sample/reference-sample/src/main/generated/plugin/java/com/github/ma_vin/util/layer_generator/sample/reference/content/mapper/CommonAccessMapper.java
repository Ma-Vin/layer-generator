package com.github.ma_vin.util.layer_generator.sample.reference.content.mapper;

import com.github.ma_vin.util.layer_generator.annotations.mapper.BaseAccessMapper;
import com.github.ma_vin.util.layer_generator.sample.given.AnyEnumType;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dao.DaoObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dao.IIdentifiableDao;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dao.SourceEntityFilterDao;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dao.SourceEntityFilterNotAtTargetDao;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dao.SourceEntityFilterNotAtTargetToTargetEntityFilterNotAtTargetDao;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dao.SourceEntityManyToManyDao;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dao.SourceEntityManyToManyToTargetEntityManyToManyDao;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dao.SourceEntityManyToOneDao;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dao.SourceEntityOneToManyDao;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dao.SourceEntityOneToOneDao;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dao.TargetEntityFilterDao;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dao.TargetEntityFilterNotAtTargetDao;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dao.TargetEntityManyToManyDao;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dao.TargetEntityOneToManyDao;
import com.github.ma_vin.util.layer_generator.sample.reference.content.dao.TargetEntityOneToOneDao;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.DomainObjectFactory;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.SourceEntityFilter;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.SourceEntityFilterNotAtTarget;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.SourceEntityManyToMany;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.SourceEntityManyToOne;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.SourceEntityOneToMany;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.SourceEntityOneToOne;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.TargetEntityFilter;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.TargetEntityFilterNotAtTarget;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.TargetEntityManyToMany;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.TargetEntityOneToMany;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.TargetEntityOneToOne;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;

/**
 * Generated class which provides methods to convert a data access to a domain object of sub package <i>null<i> and the other way around
 */
@BaseAccessMapper
@Log4j2
public class CommonAccessMapper extends AbstractAccessMapper {

	/**
	 * singleton
	 */
	private static CommonAccessMapper instance;

	/**
	 * Converts a(n) {@link SourceEntityFilterDao} to a(n) {@link SourceEntityFilter}
	 *
	 * @param sourceEntityFilter the source object which should be converted
	 * @param includeChildren    {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are
	 *                           not of type {@link java.util.Collection}
	 * @return an equivalent new created {@link SourceEntityFilter}
	 */
	public static SourceEntityFilter convertToSourceEntityFilter(SourceEntityFilterDao sourceEntityFilter, boolean includeChildren) {
		return convertToSourceEntityFilter(sourceEntityFilter, includeChildren, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityFilterDao} to a(n) {@link SourceEntityFilter}
	 *
	 * @param sourceEntityFilter the source object which should be converted
	 * @param includeChildren    {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are
	 *                           not of type {@link java.util.Collection}
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code sourceEntityFilter} is contained, the found
	 *                           {@link SourceEntityFilter} will be returned
	 * @return an equivalent new created {@link SourceEntityFilter} or the found one from the given map
	 */
	public static SourceEntityFilter convertToSourceEntityFilter(SourceEntityFilterDao sourceEntityFilter, boolean includeChildren
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(sourceEntityFilter, mappedObjects, DomainObjectFactory::createSourceEntityFilter, (dao, domain) -> getInstance().setSourceEntityFilterValues(dao, domain)
				, (dao, domain) -> getInstance().setSourceEntityFilterSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setSourceEntityFilterMultiReferences(dao, domain, includeChildren, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SourceEntityFilter} to a(n) {@link SourceEntityFilterDao}
	 *
	 * @param sourceEntityFilter the source object which should be converted
	 * @param includeChildren    {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are
	 *                           not of type {@link java.util.Collection}
	 * @return an equivalent new created {@link SourceEntityFilterDao}
	 */
	public static SourceEntityFilterDao convertToSourceEntityFilterDao(SourceEntityFilter sourceEntityFilter, boolean includeChildren) {
		return convertToSourceEntityFilterDao(sourceEntityFilter, includeChildren, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityFilter} to a(n) {@link SourceEntityFilterDao}
	 *
	 * @param sourceEntityFilter the source object which should be converted
	 * @param includeChildren    {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which are
	 *                           not of type {@link java.util.Collection}
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code sourceEntityFilter} is contained, the found
	 *                           {@link SourceEntityFilterDao} will be returned
	 * @return an equivalent new created {@link SourceEntityFilterDao} or the found one from the given map
	 */
	public static SourceEntityFilterDao convertToSourceEntityFilterDao(SourceEntityFilter sourceEntityFilter, boolean includeChildren
			, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(sourceEntityFilter, mappedObjects, DaoObjectFactory::createSourceEntityFilterDao, (domain, dao) -> getInstance().setSourceEntityFilterDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setSourceEntityFilterDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setSourceEntityFilterDaoMultiReferences(domain, dao, includeChildren, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SourceEntityFilterNotAtTargetDao} to a(n) {@link SourceEntityFilterNotAtTarget}
	 *
	 * @param sourceEntityFilterNotAtTarget the source object which should be converted
	 * @param includeChildren               {@code true} if all references should also be mapped. {@code false} if only those references should be mapped
	 *                                      which are not of type {@link java.util.Collection}
	 * @return an equivalent new created {@link SourceEntityFilterNotAtTarget}
	 */
	public static SourceEntityFilterNotAtTarget convertToSourceEntityFilterNotAtTarget(SourceEntityFilterNotAtTargetDao sourceEntityFilterNotAtTarget
			, boolean includeChildren) {
		return convertToSourceEntityFilterNotAtTarget(sourceEntityFilterNotAtTarget, includeChildren, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityFilterNotAtTargetDao} to a(n) {@link SourceEntityFilterNotAtTarget}
	 *
	 * @param sourceEntityFilterNotAtTarget the source object which should be converted
	 * @param includeChildren               {@code true} if all references should also be mapped. {@code false} if only those references should be mapped
	 *                                      which are not of type {@link java.util.Collection}
	 * @param mappedObjects                 map which contains already mapped objects. If an identification of {@code sourceEntityFilterNotAtTarget} is
	 *                                      contained, the found {@link SourceEntityFilterNotAtTarget} will be returned
	 * @return an equivalent new created {@link SourceEntityFilterNotAtTarget} or the found one from the given map
	 */
	public static SourceEntityFilterNotAtTarget convertToSourceEntityFilterNotAtTarget(SourceEntityFilterNotAtTargetDao sourceEntityFilterNotAtTarget
			, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(sourceEntityFilterNotAtTarget, mappedObjects, DomainObjectFactory::createSourceEntityFilterNotAtTarget, (dao, domain) -> getInstance().setSourceEntityFilterNotAtTargetValues(dao, domain)
				, (dao, domain) -> getInstance().setSourceEntityFilterNotAtTargetSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setSourceEntityFilterNotAtTargetMultiReferences(dao, domain, includeChildren, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SourceEntityFilterNotAtTarget} to a(n) {@link SourceEntityFilterNotAtTargetDao}
	 *
	 * @param sourceEntityFilterNotAtTarget the source object which should be converted
	 * @param includeChildren               {@code true} if all references should also be mapped. {@code false} if only those references should be mapped
	 *                                      which are not of type {@link java.util.Collection}
	 * @return an equivalent new created {@link SourceEntityFilterNotAtTargetDao}
	 */
	public static SourceEntityFilterNotAtTargetDao convertToSourceEntityFilterNotAtTargetDao(SourceEntityFilterNotAtTarget sourceEntityFilterNotAtTarget
			, boolean includeChildren) {
		return convertToSourceEntityFilterNotAtTargetDao(sourceEntityFilterNotAtTarget, includeChildren, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityFilterNotAtTarget} to a(n) {@link SourceEntityFilterNotAtTargetDao}
	 *
	 * @param sourceEntityFilterNotAtTarget the source object which should be converted
	 * @param includeChildren               {@code true} if all references should also be mapped. {@code false} if only those references should be mapped
	 *                                      which are not of type {@link java.util.Collection}
	 * @param mappedObjects                 map which contains already mapped objects. If an identification of {@code sourceEntityFilterNotAtTarget} is
	 *                                      contained, the found {@link SourceEntityFilterNotAtTargetDao} will be returned
	 * @return an equivalent new created {@link SourceEntityFilterNotAtTargetDao} or the found one from the given map
	 */
	public static SourceEntityFilterNotAtTargetDao convertToSourceEntityFilterNotAtTargetDao(SourceEntityFilterNotAtTarget sourceEntityFilterNotAtTarget
			, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(sourceEntityFilterNotAtTarget, mappedObjects, DaoObjectFactory::createSourceEntityFilterNotAtTargetDao, (domain, dao) -> getInstance().setSourceEntityFilterNotAtTargetDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setSourceEntityFilterNotAtTargetDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setSourceEntityFilterNotAtTargetDaoMultiReferences(domain, dao, includeChildren, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SourceEntityManyToManyDao} to a(n) {@link SourceEntityManyToMany}
	 *
	 * @param sourceEntityManyToMany the source object which should be converted
	 * @param includeChildren        {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which
	 *                               are not of type {@link java.util.Collection}
	 * @return an equivalent new created {@link SourceEntityManyToMany}
	 */
	public static SourceEntityManyToMany convertToSourceEntityManyToMany(SourceEntityManyToManyDao sourceEntityManyToMany, boolean includeChildren) {
		return convertToSourceEntityManyToMany(sourceEntityManyToMany, includeChildren, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityManyToManyDao} to a(n) {@link SourceEntityManyToMany}
	 *
	 * @param sourceEntityManyToMany the source object which should be converted
	 * @param includeChildren        {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which
	 *                               are not of type {@link java.util.Collection}
	 * @param mappedObjects          map which contains already mapped objects. If an identification of {@code sourceEntityManyToMany} is contained, the
	 *                               found {@link SourceEntityManyToMany} will be returned
	 * @return an equivalent new created {@link SourceEntityManyToMany} or the found one from the given map
	 */
	public static SourceEntityManyToMany convertToSourceEntityManyToMany(SourceEntityManyToManyDao sourceEntityManyToMany, boolean includeChildren
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(sourceEntityManyToMany, mappedObjects, DomainObjectFactory::createSourceEntityManyToMany, (dao, domain) -> getInstance().setSourceEntityManyToManyValues(dao, domain)
				, (dao, domain) -> getInstance().setSourceEntityManyToManySingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setSourceEntityManyToManyMultiReferences(dao, domain, includeChildren, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SourceEntityManyToMany} to a(n) {@link SourceEntityManyToManyDao}
	 *
	 * @param sourceEntityManyToMany the source object which should be converted
	 * @param includeChildren        {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which
	 *                               are not of type {@link java.util.Collection}
	 * @return an equivalent new created {@link SourceEntityManyToManyDao}
	 */
	public static SourceEntityManyToManyDao convertToSourceEntityManyToManyDao(SourceEntityManyToMany sourceEntityManyToMany, boolean includeChildren) {
		return convertToSourceEntityManyToManyDao(sourceEntityManyToMany, includeChildren, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityManyToMany} to a(n) {@link SourceEntityManyToManyDao}
	 *
	 * @param sourceEntityManyToMany the source object which should be converted
	 * @param includeChildren        {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which
	 *                               are not of type {@link java.util.Collection}
	 * @param mappedObjects          map which contains already mapped objects. If an identification of {@code sourceEntityManyToMany} is contained, the
	 *                               found {@link SourceEntityManyToManyDao} will be returned
	 * @return an equivalent new created {@link SourceEntityManyToManyDao} or the found one from the given map
	 */
	public static SourceEntityManyToManyDao convertToSourceEntityManyToManyDao(SourceEntityManyToMany sourceEntityManyToMany, boolean includeChildren
			, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(sourceEntityManyToMany, mappedObjects, DaoObjectFactory::createSourceEntityManyToManyDao, (domain, dao) -> getInstance().setSourceEntityManyToManyDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setSourceEntityManyToManyDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setSourceEntityManyToManyDaoMultiReferences(domain, dao, includeChildren, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SourceEntityManyToOneDao} to a(n) {@link SourceEntityManyToOne}
	 *
	 * @param sourceEntityManyToOne the source object which should be converted
	 * @return an equivalent new created {@link SourceEntityManyToOne}
	 */
	public static SourceEntityManyToOne convertToSourceEntityManyToOne(SourceEntityManyToOneDao sourceEntityManyToOne) {
		return convertToSourceEntityManyToOne(sourceEntityManyToOne, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityManyToOneDao} to a(n) {@link SourceEntityManyToOne}
	 *
	 * @param sourceEntityManyToOne the source object which should be converted
	 * @param mappedObjects         map which contains already mapped objects. If an identification of {@code sourceEntityManyToOne} is contained, the
	 *                              found {@link SourceEntityManyToOne} will be returned
	 * @return an equivalent new created {@link SourceEntityManyToOne} or the found one from the given map
	 */
	public static SourceEntityManyToOne convertToSourceEntityManyToOne(SourceEntityManyToOneDao sourceEntityManyToOne
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(sourceEntityManyToOne, mappedObjects, DomainObjectFactory::createSourceEntityManyToOne, (dao, domain) -> getInstance().setSourceEntityManyToOneValues(dao, domain)
				, (dao, domain) -> getInstance().setSourceEntityManyToOneSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setSourceEntityManyToOneMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SourceEntityManyToOne} to a(n) {@link SourceEntityManyToOneDao}
	 *
	 * @param sourceEntityManyToOne the source object which should be converted
	 * @return an equivalent new created {@link SourceEntityManyToOneDao}
	 */
	public static SourceEntityManyToOneDao convertToSourceEntityManyToOneDao(SourceEntityManyToOne sourceEntityManyToOne) {
		return convertToSourceEntityManyToOneDao(sourceEntityManyToOne, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityManyToOne} to a(n) {@link SourceEntityManyToOneDao}
	 *
	 * @param sourceEntityManyToOne the source object which should be converted
	 * @param mappedObjects         map which contains already mapped objects. If an identification of {@code sourceEntityManyToOne} is contained, the
	 *                              found {@link SourceEntityManyToOneDao} will be returned
	 * @return an equivalent new created {@link SourceEntityManyToOneDao} or the found one from the given map
	 */
	public static SourceEntityManyToOneDao convertToSourceEntityManyToOneDao(SourceEntityManyToOne sourceEntityManyToOne
			, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(sourceEntityManyToOne, mappedObjects, DaoObjectFactory::createSourceEntityManyToOneDao, (domain, dao) -> getInstance().setSourceEntityManyToOneDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setSourceEntityManyToOneDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setSourceEntityManyToOneDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SourceEntityOneToManyDao} to a(n) {@link SourceEntityOneToMany}
	 *
	 * @param sourceEntityOneToMany the source object which should be converted
	 * @param includeChildren       {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which
	 *                              are not of type {@link java.util.Collection}
	 * @return an equivalent new created {@link SourceEntityOneToMany}
	 */
	public static SourceEntityOneToMany convertToSourceEntityOneToMany(SourceEntityOneToManyDao sourceEntityOneToMany, boolean includeChildren) {
		return convertToSourceEntityOneToMany(sourceEntityOneToMany, includeChildren, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityOneToManyDao} to a(n) {@link SourceEntityOneToMany}
	 *
	 * @param sourceEntityOneToMany the source object which should be converted
	 * @param includeChildren       {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which
	 *                              are not of type {@link java.util.Collection}
	 * @param mappedObjects         map which contains already mapped objects. If an identification of {@code sourceEntityOneToMany} is contained, the
	 *                              found {@link SourceEntityOneToMany} will be returned
	 * @return an equivalent new created {@link SourceEntityOneToMany} or the found one from the given map
	 */
	public static SourceEntityOneToMany convertToSourceEntityOneToMany(SourceEntityOneToManyDao sourceEntityOneToMany, boolean includeChildren
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(sourceEntityOneToMany, mappedObjects, DomainObjectFactory::createSourceEntityOneToMany, (dao, domain) -> getInstance().setSourceEntityOneToManyValues(dao, domain)
				, (dao, domain) -> getInstance().setSourceEntityOneToManySingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setSourceEntityOneToManyMultiReferences(dao, domain, includeChildren, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SourceEntityOneToMany} to a(n) {@link SourceEntityOneToManyDao}
	 *
	 * @param sourceEntityOneToMany the source object which should be converted
	 * @param includeChildren       {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which
	 *                              are not of type {@link java.util.Collection}
	 * @return an equivalent new created {@link SourceEntityOneToManyDao}
	 */
	public static SourceEntityOneToManyDao convertToSourceEntityOneToManyDao(SourceEntityOneToMany sourceEntityOneToMany, boolean includeChildren) {
		return convertToSourceEntityOneToManyDao(sourceEntityOneToMany, includeChildren, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityOneToMany} to a(n) {@link SourceEntityOneToManyDao}
	 *
	 * @param sourceEntityOneToMany the source object which should be converted
	 * @param includeChildren       {@code true} if all references should also be mapped. {@code false} if only those references should be mapped which
	 *                              are not of type {@link java.util.Collection}
	 * @param mappedObjects         map which contains already mapped objects. If an identification of {@code sourceEntityOneToMany} is contained, the
	 *                              found {@link SourceEntityOneToManyDao} will be returned
	 * @return an equivalent new created {@link SourceEntityOneToManyDao} or the found one from the given map
	 */
	public static SourceEntityOneToManyDao convertToSourceEntityOneToManyDao(SourceEntityOneToMany sourceEntityOneToMany, boolean includeChildren
			, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(sourceEntityOneToMany, mappedObjects, DaoObjectFactory::createSourceEntityOneToManyDao, (domain, dao) -> getInstance().setSourceEntityOneToManyDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setSourceEntityOneToManyDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setSourceEntityOneToManyDaoMultiReferences(domain, dao, includeChildren, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SourceEntityOneToOneDao} to a(n) {@link SourceEntityOneToOne}
	 *
	 * @param sourceEntityOneToOne the source object which should be converted
	 * @return an equivalent new created {@link SourceEntityOneToOne}
	 */
	public static SourceEntityOneToOne convertToSourceEntityOneToOne(SourceEntityOneToOneDao sourceEntityOneToOne) {
		return convertToSourceEntityOneToOne(sourceEntityOneToOne, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityOneToOneDao} to a(n) {@link SourceEntityOneToOne}
	 *
	 * @param sourceEntityOneToOne the source object which should be converted
	 * @param mappedObjects        map which contains already mapped objects. If an identification of {@code sourceEntityOneToOne} is contained, the found
	 *                             {@link SourceEntityOneToOne} will be returned
	 * @return an equivalent new created {@link SourceEntityOneToOne} or the found one from the given map
	 */
	public static SourceEntityOneToOne convertToSourceEntityOneToOne(SourceEntityOneToOneDao sourceEntityOneToOne
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(sourceEntityOneToOne, mappedObjects, DomainObjectFactory::createSourceEntityOneToOne, (dao, domain) -> getInstance().setSourceEntityOneToOneValues(dao, domain)
				, (dao, domain) -> getInstance().setSourceEntityOneToOneSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setSourceEntityOneToOneMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link SourceEntityOneToOne} to a(n) {@link SourceEntityOneToOneDao}
	 *
	 * @param sourceEntityOneToOne the source object which should be converted
	 * @return an equivalent new created {@link SourceEntityOneToOneDao}
	 */
	public static SourceEntityOneToOneDao convertToSourceEntityOneToOneDao(SourceEntityOneToOne sourceEntityOneToOne) {
		return convertToSourceEntityOneToOneDao(sourceEntityOneToOne, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link SourceEntityOneToOne} to a(n) {@link SourceEntityOneToOneDao}
	 *
	 * @param sourceEntityOneToOne the source object which should be converted
	 * @param mappedObjects        map which contains already mapped objects. If an identification of {@code sourceEntityOneToOne} is contained, the found
	 *                             {@link SourceEntityOneToOneDao} will be returned
	 * @return an equivalent new created {@link SourceEntityOneToOneDao} or the found one from the given map
	 */
	public static SourceEntityOneToOneDao convertToSourceEntityOneToOneDao(SourceEntityOneToOne sourceEntityOneToOne
			, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(sourceEntityOneToOne, mappedObjects, DaoObjectFactory::createSourceEntityOneToOneDao, (domain, dao) -> getInstance().setSourceEntityOneToOneDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setSourceEntityOneToOneDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setSourceEntityOneToOneDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * Converts a(n) {@link TargetEntityFilterDao} to a(n) {@link TargetEntityFilter}
	 *
	 * @param targetEntityFilter the source object which should be converted
	 * @return an equivalent new created {@link TargetEntityFilter}
	 */
	public static TargetEntityFilter convertToTargetEntityFilter(TargetEntityFilterDao targetEntityFilter) {
		return convertToTargetEntityFilter(targetEntityFilter, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityFilterDao} to a(n) {@link TargetEntityFilter}
	 *
	 * @param targetEntityFilter the source object which should be converted
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code targetEntityFilter} is contained, the found
	 *                           {@link TargetEntityFilter} will be returned
	 * @return an equivalent new created {@link TargetEntityFilter} or the found one from the given map
	 */
	public static TargetEntityFilter convertToTargetEntityFilter(TargetEntityFilterDao targetEntityFilter, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(targetEntityFilter, mappedObjects, DomainObjectFactory::createTargetEntityFilter, (dao, domain) -> getInstance().setTargetEntityFilterValues(dao, domain)
				, (dao, domain) -> getInstance().setTargetEntityFilterSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setTargetEntityFilterMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link TargetEntityFilterDao} to a(n) {@link TargetEntityFilter} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param targetEntityFilter the source object which should be converted
	 * @param parent             the parent of converted result
	 * @return an equivalent new created {@link TargetEntityFilter}
	 */
	public static TargetEntityFilter convertToTargetEntityFilter(TargetEntityFilterDao targetEntityFilter, SourceEntityFilter parent) {
		return convertToTargetEntityFilter(targetEntityFilter, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityFilterDao} to a(n) {@link TargetEntityFilter} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param targetEntityFilter the source object which should be converted
	 * @param parent             the parent of converted result
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code targetEntityFilter} is contained, the found
	 *                           {@link TargetEntityFilter} will be returned
	 * @return an equivalent new created {@link TargetEntityFilter} or the found one from the given map
	 */
	public static TargetEntityFilter convertToTargetEntityFilter(TargetEntityFilterDao targetEntityFilter, SourceEntityFilter parent
			, Map<String, IIdentifiable> mappedObjects) {
		TargetEntityFilter result = convertToTargetEntityFilter(targetEntityFilter, mappedObjects);
		if (result != null) {
			switch (targetEntityFilter.getEnumFieldForFiltering()) {
				case ENUM_VALUE_A:
					parent.addOneToManyFilterA(result);
					break;
				case ENUM_VALUE_B:
					parent.addOneToManyFilterB(result);
					break;
				default:
					log.error("There is not any mapping rule for targetEntityFilter of type {}", targetEntityFilter.getEnumFieldForFiltering());
			}
		}
		return result;
	}

	/**
	 * Converts a(n) {@link TargetEntityFilter} to a(n) {@link TargetEntityFilterDao}
	 *
	 * @param targetEntityFilter the source object which should be converted
	 * @return an equivalent new created {@link TargetEntityFilterDao}
	 */
	public static TargetEntityFilterDao convertToTargetEntityFilterDao(TargetEntityFilter targetEntityFilter) {
		return convertToTargetEntityFilterDao(targetEntityFilter, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityFilter} to a(n) {@link TargetEntityFilterDao}
	 *
	 * @param targetEntityFilter the source object which should be converted
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code targetEntityFilter} is contained, the found
	 *                           {@link TargetEntityFilterDao} will be returned
	 * @return an equivalent new created {@link TargetEntityFilterDao} or the found one from the given map
	 */
	public static TargetEntityFilterDao convertToTargetEntityFilterDao(TargetEntityFilter targetEntityFilter, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(targetEntityFilter, mappedObjects, DaoObjectFactory::createTargetEntityFilterDao, (domain, dao) -> getInstance().setTargetEntityFilterDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setTargetEntityFilterDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setTargetEntityFilterDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * Converts a(n) {@link TargetEntityFilter} to a(n) {@link TargetEntityFilterDao} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param targetEntityFilter the source object which should be converted
	 * @param parent             the parent of converted result
	 * @return an equivalent new created {@link TargetEntityFilterDao}
	 */
	public static TargetEntityFilterDao convertToTargetEntityFilterDao(TargetEntityFilter targetEntityFilter, SourceEntityFilterDao parent) {
		return convertToTargetEntityFilterDao(targetEntityFilter, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityFilter} to a(n) {@link TargetEntityFilterDao} and sets the result to the corresponding reference property at the
	 * parent
	 *
	 * @param targetEntityFilter the source object which should be converted
	 * @param parent             the parent of converted result
	 * @param mappedObjects      map which contains already mapped objects. If an identification of {@code targetEntityFilter} is contained, the found
	 *                           {@link TargetEntityFilterDao} will be returned
	 * @return an equivalent new created {@link TargetEntityFilterDao} or the found one from the given map
	 */
	public static TargetEntityFilterDao convertToTargetEntityFilterDao(TargetEntityFilter targetEntityFilter, SourceEntityFilterDao parent
			, Map<String, IIdentifiableDao> mappedObjects) {
		TargetEntityFilterDao result = convertToTargetEntityFilterDao(targetEntityFilter, mappedObjects);
		if (result != null) {
			result.setParentSourceEntityFilter(parent);
			parent.getAggTargetEntityFilter().add(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link TargetEntityFilterNotAtTargetDao} to a(n) {@link TargetEntityFilterNotAtTarget}
	 *
	 * @param targetEntityFilterNotAtTarget the source object which should be converted
	 * @return an equivalent new created {@link TargetEntityFilterNotAtTarget}
	 */
	public static TargetEntityFilterNotAtTarget convertToTargetEntityFilterNotAtTarget(TargetEntityFilterNotAtTargetDao targetEntityFilterNotAtTarget) {
		return convertToTargetEntityFilterNotAtTarget(targetEntityFilterNotAtTarget, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityFilterNotAtTargetDao} to a(n) {@link TargetEntityFilterNotAtTarget}
	 *
	 * @param targetEntityFilterNotAtTarget the source object which should be converted
	 * @param mappedObjects                 map which contains already mapped objects. If an identification of {@code targetEntityFilterNotAtTarget} is
	 *                                      contained, the found {@link TargetEntityFilterNotAtTarget} will be returned
	 * @return an equivalent new created {@link TargetEntityFilterNotAtTarget} or the found one from the given map
	 */
	public static TargetEntityFilterNotAtTarget convertToTargetEntityFilterNotAtTarget(TargetEntityFilterNotAtTargetDao targetEntityFilterNotAtTarget
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(targetEntityFilterNotAtTarget, mappedObjects, DomainObjectFactory::createTargetEntityFilterNotAtTarget, (dao, domain) -> getInstance().setTargetEntityFilterNotAtTargetValues(dao, domain)
				, (dao, domain) -> getInstance().setTargetEntityFilterNotAtTargetSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setTargetEntityFilterNotAtTargetMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link TargetEntityFilterNotAtTargetDao} to a(n) {@link TargetEntityFilterNotAtTarget} and sets the result to the corresponding
	 * reference property at the parent
	 *
	 * @param targetEntityFilterNotAtTarget the source object which should be converted
	 * @param parent                        the parent of converted result
	 * @param filterAnyEnumType             value to map between domain multiple {@link java.util.Collection}s and dao aggregated
	 *                                      {@link java.util.Collection}
	 * @return an equivalent new created {@link TargetEntityFilterNotAtTarget}
	 */
	public static TargetEntityFilterNotAtTarget convertToTargetEntityFilterNotAtTarget(TargetEntityFilterNotAtTargetDao targetEntityFilterNotAtTarget
			, SourceEntityFilterNotAtTarget parent, AnyEnumType filterAnyEnumType) {
		return convertToTargetEntityFilterNotAtTarget(targetEntityFilterNotAtTarget, parent, filterAnyEnumType, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityFilterNotAtTargetDao} to a(n) {@link TargetEntityFilterNotAtTarget} and sets the result to the corresponding
	 * reference property at the parent
	 *
	 * @param targetEntityFilterNotAtTarget the source object which should be converted
	 * @param parent                        the parent of converted result
	 * @param filterAnyEnumType             value to map between domain multiple {@link java.util.Collection}s and dao aggregated
	 *                                      {@link java.util.Collection}
	 * @param mappedObjects                 map which contains already mapped objects. If an identification of {@code targetEntityFilterNotAtTarget} is
	 *                                      contained, the found {@link TargetEntityFilterNotAtTarget} will be returned
	 * @return an equivalent new created {@link TargetEntityFilterNotAtTarget} or the found one from the given map
	 */
	public static TargetEntityFilterNotAtTarget convertToTargetEntityFilterNotAtTarget(TargetEntityFilterNotAtTargetDao targetEntityFilterNotAtTarget
			, SourceEntityFilterNotAtTarget parent, AnyEnumType filterAnyEnumType, Map<String, IIdentifiable> mappedObjects) {
		TargetEntityFilterNotAtTarget result = convertToTargetEntityFilterNotAtTarget(targetEntityFilterNotAtTarget, mappedObjects);
		if (result != null) {
			switch (filterAnyEnumType) {
				case ENUM_VALUE_A:
					parent.addOneToManyFilterA(result);
					break;
				case ENUM_VALUE_B:
					parent.addOneToManyFilterB(result);
					break;
				default:
					log.error("There is not any mapping rule for targetEntityFilterNotAtTarget of type {}", filterAnyEnumType);
			}
		}
		return result;
	}

	/**
	 * Converts a(n) {@link TargetEntityFilterNotAtTarget} to a(n) {@link TargetEntityFilterNotAtTargetDao}
	 *
	 * @param targetEntityFilterNotAtTarget the source object which should be converted
	 * @return an equivalent new created {@link TargetEntityFilterNotAtTargetDao}
	 */
	public static TargetEntityFilterNotAtTargetDao convertToTargetEntityFilterNotAtTargetDao(TargetEntityFilterNotAtTarget targetEntityFilterNotAtTarget) {
		return convertToTargetEntityFilterNotAtTargetDao(targetEntityFilterNotAtTarget, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityFilterNotAtTarget} to a(n) {@link TargetEntityFilterNotAtTargetDao}
	 *
	 * @param targetEntityFilterNotAtTarget the source object which should be converted
	 * @param mappedObjects                 map which contains already mapped objects. If an identification of {@code targetEntityFilterNotAtTarget} is
	 *                                      contained, the found {@link TargetEntityFilterNotAtTargetDao} will be returned
	 * @return an equivalent new created {@link TargetEntityFilterNotAtTargetDao} or the found one from the given map
	 */
	public static TargetEntityFilterNotAtTargetDao convertToTargetEntityFilterNotAtTargetDao(TargetEntityFilterNotAtTarget targetEntityFilterNotAtTarget
			, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(targetEntityFilterNotAtTarget, mappedObjects, DaoObjectFactory::createTargetEntityFilterNotAtTargetDao, (domain, dao) -> getInstance().setTargetEntityFilterNotAtTargetDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setTargetEntityFilterNotAtTargetDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setTargetEntityFilterNotAtTargetDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * Converts a(n) {@link TargetEntityFilterNotAtTarget} to a(n) {@link TargetEntityFilterNotAtTargetDao} and sets the result to the corresponding
	 * reference property at the parent
	 *
	 * @param targetEntityFilterNotAtTarget the source object which should be converted
	 * @param parent                        the parent of converted result
	 * @param filterAnyEnumType             value to map between domain multiple {@link java.util.Collection}s and dao aggregated
	 *                                      {@link java.util.Collection}
	 * @return an equivalent new created {@link TargetEntityFilterNotAtTargetDao}
	 */
	public static TargetEntityFilterNotAtTargetDao convertToTargetEntityFilterNotAtTargetDao(TargetEntityFilterNotAtTarget targetEntityFilterNotAtTarget
			, SourceEntityFilterNotAtTargetDao parent, AnyEnumType filterAnyEnumType) {
		return convertToTargetEntityFilterNotAtTargetDao(targetEntityFilterNotAtTarget, parent, filterAnyEnumType, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityFilterNotAtTarget} to a(n) {@link TargetEntityFilterNotAtTargetDao} and sets the result to the corresponding
	 * reference property at the parent
	 *
	 * @param targetEntityFilterNotAtTarget the source object which should be converted
	 * @param parent                        the parent of converted result
	 * @param filterAnyEnumType             value to map between domain multiple {@link java.util.Collection}s and dao aggregated
	 *                                      {@link java.util.Collection}
	 * @param mappedObjects                 map which contains already mapped objects. If an identification of {@code targetEntityFilterNotAtTarget} is
	 *                                      contained, the found {@link TargetEntityFilterNotAtTargetDao} will be returned
	 * @return an equivalent new created {@link TargetEntityFilterNotAtTargetDao} or the found one from the given map
	 */
	public static TargetEntityFilterNotAtTargetDao convertToTargetEntityFilterNotAtTargetDao(TargetEntityFilterNotAtTarget targetEntityFilterNotAtTarget
			, SourceEntityFilterNotAtTargetDao parent, AnyEnumType filterAnyEnumType, Map<String, IIdentifiableDao> mappedObjects) {
		TargetEntityFilterNotAtTargetDao result = convertToTargetEntityFilterNotAtTargetDao(targetEntityFilterNotAtTarget, mappedObjects);
		if (result != null) {
			SourceEntityFilterNotAtTargetToTargetEntityFilterNotAtTargetDao connectionTable = DaoObjectFactory.createSourceEntityFilterNotAtTargetToTargetEntityFilterNotAtTargetDao();
			connectionTable.setTargetEntityFilterNotAtTarget(result);
			connectionTable.setSourceEntityFilterNotAtTarget(parent);
			connectionTable.setFilterAnyEnumType(filterAnyEnumType);
			parent.getAggTargetEntityFilterNotAtTarget().add(connectionTable);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link TargetEntityManyToManyDao} to a(n) {@link TargetEntityManyToMany}
	 *
	 * @param targetEntityManyToMany the source object which should be converted
	 * @return an equivalent new created {@link TargetEntityManyToMany}
	 */
	public static TargetEntityManyToMany convertToTargetEntityManyToMany(TargetEntityManyToManyDao targetEntityManyToMany) {
		return convertToTargetEntityManyToMany(targetEntityManyToMany, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityManyToManyDao} to a(n) {@link TargetEntityManyToMany}
	 *
	 * @param targetEntityManyToMany the source object which should be converted
	 * @param mappedObjects          map which contains already mapped objects. If an identification of {@code targetEntityManyToMany} is contained, the
	 *                               found {@link TargetEntityManyToMany} will be returned
	 * @return an equivalent new created {@link TargetEntityManyToMany} or the found one from the given map
	 */
	public static TargetEntityManyToMany convertToTargetEntityManyToMany(TargetEntityManyToManyDao targetEntityManyToMany
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(targetEntityManyToMany, mappedObjects, DomainObjectFactory::createTargetEntityManyToMany, (dao, domain) -> getInstance().setTargetEntityManyToManyValues(dao, domain)
				, (dao, domain) -> getInstance().setTargetEntityManyToManySingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setTargetEntityManyToManyMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link TargetEntityManyToManyDao} to a(n) {@link TargetEntityManyToMany} and sets the result to the corresponding reference property
	 * at the parent
	 *
	 * @param targetEntityManyToMany the source object which should be converted
	 * @param parent                 the parent of converted result
	 * @return an equivalent new created {@link TargetEntityManyToMany}
	 */
	public static TargetEntityManyToMany convertToTargetEntityManyToMany(TargetEntityManyToManyDao targetEntityManyToMany, SourceEntityManyToMany parent) {
		return convertToTargetEntityManyToMany(targetEntityManyToMany, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityManyToManyDao} to a(n) {@link TargetEntityManyToMany} and sets the result to the corresponding reference property
	 * at the parent
	 *
	 * @param targetEntityManyToMany the source object which should be converted
	 * @param parent                 the parent of converted result
	 * @param mappedObjects          map which contains already mapped objects. If an identification of {@code targetEntityManyToMany} is contained, the
	 *                               found {@link TargetEntityManyToMany} will be returned
	 * @return an equivalent new created {@link TargetEntityManyToMany} or the found one from the given map
	 */
	public static TargetEntityManyToMany convertToTargetEntityManyToMany(TargetEntityManyToManyDao targetEntityManyToMany, SourceEntityManyToMany parent
			, Map<String, IIdentifiable> mappedObjects) {
		TargetEntityManyToMany result = convertToTargetEntityManyToMany(targetEntityManyToMany, mappedObjects);
		if (result != null) {
			parent.getManyToManyRef().add(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link TargetEntityManyToManyDao} to a(n) {@link TargetEntityManyToMany} and sets the result to the corresponding reference property
	 * at the parent
	 *
	 * @param targetEntityManyToMany the source object which should be converted
	 * @param parent                 the parent of converted result
	 * @return an equivalent new created {@link TargetEntityManyToMany}
	 */
	public static TargetEntityManyToMany convertToTargetEntityManyToMany(TargetEntityManyToManyDao targetEntityManyToMany, SourceEntityManyToOne parent) {
		return convertToTargetEntityManyToMany(targetEntityManyToMany, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityManyToManyDao} to a(n) {@link TargetEntityManyToMany} and sets the result to the corresponding reference property
	 * at the parent
	 *
	 * @param targetEntityManyToMany the source object which should be converted
	 * @param parent                 the parent of converted result
	 * @param mappedObjects          map which contains already mapped objects. If an identification of {@code targetEntityManyToMany} is contained, the
	 *                               found {@link TargetEntityManyToMany} will be returned
	 * @return an equivalent new created {@link TargetEntityManyToMany} or the found one from the given map
	 */
	public static TargetEntityManyToMany convertToTargetEntityManyToMany(TargetEntityManyToManyDao targetEntityManyToMany, SourceEntityManyToOne parent
			, Map<String, IIdentifiable> mappedObjects) {
		TargetEntityManyToMany result = convertToTargetEntityManyToMany(targetEntityManyToMany, mappedObjects);
		if (result != null) {
			parent.setManyToOneRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link TargetEntityManyToMany} to a(n) {@link TargetEntityManyToManyDao}
	 *
	 * @param targetEntityManyToMany the source object which should be converted
	 * @return an equivalent new created {@link TargetEntityManyToManyDao}
	 */
	public static TargetEntityManyToManyDao convertToTargetEntityManyToManyDao(TargetEntityManyToMany targetEntityManyToMany) {
		return convertToTargetEntityManyToManyDao(targetEntityManyToMany, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityManyToMany} to a(n) {@link TargetEntityManyToManyDao}
	 *
	 * @param targetEntityManyToMany the source object which should be converted
	 * @param mappedObjects          map which contains already mapped objects. If an identification of {@code targetEntityManyToMany} is contained, the
	 *                               found {@link TargetEntityManyToManyDao} will be returned
	 * @return an equivalent new created {@link TargetEntityManyToManyDao} or the found one from the given map
	 */
	public static TargetEntityManyToManyDao convertToTargetEntityManyToManyDao(TargetEntityManyToMany targetEntityManyToMany
			, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(targetEntityManyToMany, mappedObjects, DaoObjectFactory::createTargetEntityManyToManyDao, (domain, dao) -> getInstance().setTargetEntityManyToManyDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setTargetEntityManyToManyDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setTargetEntityManyToManyDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * Converts a(n) {@link TargetEntityManyToMany} to a(n) {@link TargetEntityManyToManyDao} and sets the result to the corresponding reference property
	 * at the parent
	 *
	 * @param targetEntityManyToMany the source object which should be converted
	 * @param parent                 the parent of converted result
	 * @return an equivalent new created {@link TargetEntityManyToManyDao}
	 */
	public static TargetEntityManyToManyDao convertToTargetEntityManyToManyDao(TargetEntityManyToMany targetEntityManyToMany
			, SourceEntityManyToManyDao parent) {
		return convertToTargetEntityManyToManyDao(targetEntityManyToMany, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityManyToMany} to a(n) {@link TargetEntityManyToManyDao} and sets the result to the corresponding reference property
	 * at the parent
	 *
	 * @param targetEntityManyToMany the source object which should be converted
	 * @param parent                 the parent of converted result
	 * @param mappedObjects          map which contains already mapped objects. If an identification of {@code targetEntityManyToMany} is contained, the
	 *                               found {@link TargetEntityManyToManyDao} will be returned
	 * @return an equivalent new created {@link TargetEntityManyToManyDao} or the found one from the given map
	 */
	public static TargetEntityManyToManyDao convertToTargetEntityManyToManyDao(TargetEntityManyToMany targetEntityManyToMany
			, SourceEntityManyToManyDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		TargetEntityManyToManyDao result = convertToTargetEntityManyToManyDao(targetEntityManyToMany, mappedObjects);
		if (result != null) {
			SourceEntityManyToManyToTargetEntityManyToManyDao connectionTable = DaoObjectFactory.createSourceEntityManyToManyToTargetEntityManyToManyDao();
			connectionTable.setTargetEntityManyToMany(result);
			connectionTable.setSourceEntityManyToMany(parent);
			parent.getManyToManyRef().add(connectionTable);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link TargetEntityManyToMany} to a(n) {@link TargetEntityManyToManyDao} and sets the result to the corresponding reference property
	 * at the parent
	 *
	 * @param targetEntityManyToMany the source object which should be converted
	 * @param parent                 the parent of converted result
	 * @return an equivalent new created {@link TargetEntityManyToManyDao}
	 */
	public static TargetEntityManyToManyDao convertToTargetEntityManyToManyDao(TargetEntityManyToMany targetEntityManyToMany
			, SourceEntityManyToOneDao parent) {
		return convertToTargetEntityManyToManyDao(targetEntityManyToMany, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityManyToMany} to a(n) {@link TargetEntityManyToManyDao} and sets the result to the corresponding reference property
	 * at the parent
	 *
	 * @param targetEntityManyToMany the source object which should be converted
	 * @param parent                 the parent of converted result
	 * @param mappedObjects          map which contains already mapped objects. If an identification of {@code targetEntityManyToMany} is contained, the
	 *                               found {@link TargetEntityManyToManyDao} will be returned
	 * @return an equivalent new created {@link TargetEntityManyToManyDao} or the found one from the given map
	 */
	public static TargetEntityManyToManyDao convertToTargetEntityManyToManyDao(TargetEntityManyToMany targetEntityManyToMany
			, SourceEntityManyToOneDao parent, Map<String, IIdentifiableDao> mappedObjects) {
		TargetEntityManyToManyDao result = convertToTargetEntityManyToManyDao(targetEntityManyToMany, mappedObjects);
		if (result != null) {
			parent.setManyToOneRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToManyDao} to a(n) {@link TargetEntityOneToMany}
	 *
	 * @param targetEntityOneToMany the source object which should be converted
	 * @return an equivalent new created {@link TargetEntityOneToMany}
	 */
	public static TargetEntityOneToMany convertToTargetEntityOneToMany(TargetEntityOneToManyDao targetEntityOneToMany) {
		return convertToTargetEntityOneToMany(targetEntityOneToMany, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToManyDao} to a(n) {@link TargetEntityOneToMany}
	 *
	 * @param targetEntityOneToMany the source object which should be converted
	 * @param mappedObjects         map which contains already mapped objects. If an identification of {@code targetEntityOneToMany} is contained, the
	 *                              found {@link TargetEntityOneToMany} will be returned
	 * @return an equivalent new created {@link TargetEntityOneToMany} or the found one from the given map
	 */
	public static TargetEntityOneToMany convertToTargetEntityOneToMany(TargetEntityOneToManyDao targetEntityOneToMany
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(targetEntityOneToMany, mappedObjects, DomainObjectFactory::createTargetEntityOneToMany, (dao, domain) -> getInstance().setTargetEntityOneToManyValues(dao, domain)
				, (dao, domain) -> getInstance().setTargetEntityOneToManySingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setTargetEntityOneToManyMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToManyDao} to a(n) {@link TargetEntityOneToMany} and sets the result to the corresponding reference property at
	 * the parent
	 *
	 * @param targetEntityOneToMany the source object which should be converted
	 * @param parent                the parent of converted result
	 * @return an equivalent new created {@link TargetEntityOneToMany}
	 */
	public static TargetEntityOneToMany convertToTargetEntityOneToMany(TargetEntityOneToManyDao targetEntityOneToMany, SourceEntityOneToMany parent) {
		return convertToTargetEntityOneToMany(targetEntityOneToMany, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToManyDao} to a(n) {@link TargetEntityOneToMany} and sets the result to the corresponding reference property at
	 * the parent
	 *
	 * @param targetEntityOneToMany the source object which should be converted
	 * @param parent                the parent of converted result
	 * @param mappedObjects         map which contains already mapped objects. If an identification of {@code targetEntityOneToMany} is contained, the
	 *                              found {@link TargetEntityOneToMany} will be returned
	 * @return an equivalent new created {@link TargetEntityOneToMany} or the found one from the given map
	 */
	public static TargetEntityOneToMany convertToTargetEntityOneToMany(TargetEntityOneToManyDao targetEntityOneToMany, SourceEntityOneToMany parent
			, Map<String, IIdentifiable> mappedObjects) {
		TargetEntityOneToMany result = convertToTargetEntityOneToMany(targetEntityOneToMany, mappedObjects);
		if (result != null) {
			parent.getOneToManyRef().add(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToMany} to a(n) {@link TargetEntityOneToManyDao}
	 *
	 * @param targetEntityOneToMany the source object which should be converted
	 * @return an equivalent new created {@link TargetEntityOneToManyDao}
	 */
	public static TargetEntityOneToManyDao convertToTargetEntityOneToManyDao(TargetEntityOneToMany targetEntityOneToMany) {
		return convertToTargetEntityOneToManyDao(targetEntityOneToMany, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToMany} to a(n) {@link TargetEntityOneToManyDao}
	 *
	 * @param targetEntityOneToMany the source object which should be converted
	 * @param mappedObjects         map which contains already mapped objects. If an identification of {@code targetEntityOneToMany} is contained, the
	 *                              found {@link TargetEntityOneToManyDao} will be returned
	 * @return an equivalent new created {@link TargetEntityOneToManyDao} or the found one from the given map
	 */
	public static TargetEntityOneToManyDao convertToTargetEntityOneToManyDao(TargetEntityOneToMany targetEntityOneToMany
			, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(targetEntityOneToMany, mappedObjects, DaoObjectFactory::createTargetEntityOneToManyDao, (domain, dao) -> getInstance().setTargetEntityOneToManyDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setTargetEntityOneToManyDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setTargetEntityOneToManyDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToMany} to a(n) {@link TargetEntityOneToManyDao} and sets the result to the corresponding reference property at
	 * the parent
	 *
	 * @param targetEntityOneToMany the source object which should be converted
	 * @param parent                the parent of converted result
	 * @return an equivalent new created {@link TargetEntityOneToManyDao}
	 */
	public static TargetEntityOneToManyDao convertToTargetEntityOneToManyDao(TargetEntityOneToMany targetEntityOneToMany, SourceEntityOneToManyDao parent) {
		return convertToTargetEntityOneToManyDao(targetEntityOneToMany, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToMany} to a(n) {@link TargetEntityOneToManyDao} and sets the result to the corresponding reference property at
	 * the parent
	 *
	 * @param targetEntityOneToMany the source object which should be converted
	 * @param parent                the parent of converted result
	 * @param mappedObjects         map which contains already mapped objects. If an identification of {@code targetEntityOneToMany} is contained, the
	 *                              found {@link TargetEntityOneToManyDao} will be returned
	 * @return an equivalent new created {@link TargetEntityOneToManyDao} or the found one from the given map
	 */
	public static TargetEntityOneToManyDao convertToTargetEntityOneToManyDao(TargetEntityOneToMany targetEntityOneToMany, SourceEntityOneToManyDao parent
			, Map<String, IIdentifiableDao> mappedObjects) {
		TargetEntityOneToManyDao result = convertToTargetEntityOneToManyDao(targetEntityOneToMany, mappedObjects);
		if (result != null) {
			result.setParentSourceEntityOneToMany(parent);
			parent.getOneToManyRef().add(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToOneDao} to a(n) {@link TargetEntityOneToOne}
	 *
	 * @param targetEntityOneToOne the source object which should be converted
	 * @return an equivalent new created {@link TargetEntityOneToOne}
	 */
	public static TargetEntityOneToOne convertToTargetEntityOneToOne(TargetEntityOneToOneDao targetEntityOneToOne) {
		return convertToTargetEntityOneToOne(targetEntityOneToOne, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToOneDao} to a(n) {@link TargetEntityOneToOne}
	 *
	 * @param targetEntityOneToOne the source object which should be converted
	 * @param mappedObjects        map which contains already mapped objects. If an identification of {@code targetEntityOneToOne} is contained, the found
	 *                             {@link TargetEntityOneToOne} will be returned
	 * @return an equivalent new created {@link TargetEntityOneToOne} or the found one from the given map
	 */
	public static TargetEntityOneToOne convertToTargetEntityOneToOne(TargetEntityOneToOneDao targetEntityOneToOne
			, Map<String, IIdentifiable> mappedObjects) {
		return convertToDomain(targetEntityOneToOne, mappedObjects, DomainObjectFactory::createTargetEntityOneToOne, (dao, domain) -> getInstance().setTargetEntityOneToOneValues(dao, domain)
				, (dao, domain) -> getInstance().setTargetEntityOneToOneSingleReferences(dao, domain, mappedObjects)
				, (dao, domain) -> getInstance().setTargetEntityOneToOneMultiReferences(dao, domain, mappedObjects));
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToOneDao} to a(n) {@link TargetEntityOneToOne} and sets the result to the corresponding reference property at
	 * the parent
	 *
	 * @param targetEntityOneToOne the source object which should be converted
	 * @param parent               the parent of converted result
	 * @return an equivalent new created {@link TargetEntityOneToOne}
	 */
	public static TargetEntityOneToOne convertToTargetEntityOneToOne(TargetEntityOneToOneDao targetEntityOneToOne, SourceEntityOneToOne parent) {
		return convertToTargetEntityOneToOne(targetEntityOneToOne, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToOneDao} to a(n) {@link TargetEntityOneToOne} and sets the result to the corresponding reference property at
	 * the parent
	 *
	 * @param targetEntityOneToOne the source object which should be converted
	 * @param parent               the parent of converted result
	 * @param mappedObjects        map which contains already mapped objects. If an identification of {@code targetEntityOneToOne} is contained, the found
	 *                             {@link TargetEntityOneToOne} will be returned
	 * @return an equivalent new created {@link TargetEntityOneToOne} or the found one from the given map
	 */
	public static TargetEntityOneToOne convertToTargetEntityOneToOne(TargetEntityOneToOneDao targetEntityOneToOne, SourceEntityOneToOne parent
			, Map<String, IIdentifiable> mappedObjects) {
		TargetEntityOneToOne result = convertToTargetEntityOneToOne(targetEntityOneToOne, mappedObjects);
		if (result != null) {
			parent.setOneToOneRef(result);
		}
		return result;
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToOne} to a(n) {@link TargetEntityOneToOneDao}
	 *
	 * @param targetEntityOneToOne the source object which should be converted
	 * @return an equivalent new created {@link TargetEntityOneToOneDao}
	 */
	public static TargetEntityOneToOneDao convertToTargetEntityOneToOneDao(TargetEntityOneToOne targetEntityOneToOne) {
		return convertToTargetEntityOneToOneDao(targetEntityOneToOne, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToOne} to a(n) {@link TargetEntityOneToOneDao}
	 *
	 * @param targetEntityOneToOne the source object which should be converted
	 * @param mappedObjects        map which contains already mapped objects. If an identification of {@code targetEntityOneToOne} is contained, the found
	 *                             {@link TargetEntityOneToOneDao} will be returned
	 * @return an equivalent new created {@link TargetEntityOneToOneDao} or the found one from the given map
	 */
	public static TargetEntityOneToOneDao convertToTargetEntityOneToOneDao(TargetEntityOneToOne targetEntityOneToOne
			, Map<String, IIdentifiableDao> mappedObjects) {
		return convertToDao(targetEntityOneToOne, mappedObjects, DaoObjectFactory::createTargetEntityOneToOneDao, (domain, dao) -> getInstance().setTargetEntityOneToOneDaoValues(domain, dao)
				, (domain, dao) -> getInstance().setTargetEntityOneToOneDaoSingleReferences(domain, dao, mappedObjects)
				, (domain, dao) -> getInstance().setTargetEntityOneToOneDaoMultiReferences(domain, dao, mappedObjects));
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToOne} to a(n) {@link TargetEntityOneToOneDao} and sets the result to the corresponding reference property at
	 * the parent
	 *
	 * @param targetEntityOneToOne the source object which should be converted
	 * @param parent               the parent of converted result
	 * @return an equivalent new created {@link TargetEntityOneToOneDao}
	 */
	public static TargetEntityOneToOneDao convertToTargetEntityOneToOneDao(TargetEntityOneToOne targetEntityOneToOne, SourceEntityOneToOneDao parent) {
		return convertToTargetEntityOneToOneDao(targetEntityOneToOne, parent, new HashMap<>());
	}

	/**
	 * Converts a(n) {@link TargetEntityOneToOne} to a(n) {@link TargetEntityOneToOneDao} and sets the result to the corresponding reference property at
	 * the parent
	 *
	 * @param targetEntityOneToOne the source object which should be converted
	 * @param parent               the parent of converted result
	 * @param mappedObjects        map which contains already mapped objects. If an identification of {@code targetEntityOneToOne} is contained, the found
	 *                             {@link TargetEntityOneToOneDao} will be returned
	 * @return an equivalent new created {@link TargetEntityOneToOneDao} or the found one from the given map
	 */
	public static TargetEntityOneToOneDao convertToTargetEntityOneToOneDao(TargetEntityOneToOne targetEntityOneToOne, SourceEntityOneToOneDao parent
			, Map<String, IIdentifiableDao> mappedObjects) {
		TargetEntityOneToOneDao result = convertToTargetEntityOneToOneDao(targetEntityOneToOne, mappedObjects);
		if (result != null) {
			result.setParentSourceEntityOneToOne(parent);
			parent.setOneToOneRef(result);
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
	protected void setSourceEntityFilterDaoMultiReferences(SourceEntityFilter domain, SourceEntityFilterDao dao, boolean includeChildren
			, Map<String, IIdentifiableDao> mappedObjects) {
		dao.setAggTargetEntityFilter(new ArrayList<>());
		if (includeChildren) {
			domain.getOneToManyFilterA().forEach(arg ->
					CommonAccessMapper.convertToTargetEntityFilterDao(arg, dao, mappedObjects)
			);
			domain.getOneToManyFilterB().forEach(arg ->
					CommonAccessMapper.convertToTargetEntityFilterDao(arg, dao, mappedObjects)
			);
		}
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityFilterDaoSingleReferences(SourceEntityFilter domain, SourceEntityFilterDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityFilterDaoValues(SourceEntityFilter domain, SourceEntityFilterDao dao) {
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao             source of the given references
	 * @param domain          object where to add the references
	 * @param includeChildren {@code true} if references should be mapped. Otherwise {@code false}
	 * @param mappedObjects   map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	protected void setSourceEntityFilterMultiReferences(SourceEntityFilterDao dao, SourceEntityFilter domain, boolean includeChildren
			, Map<String, IIdentifiable> mappedObjects) {
		if (includeChildren) {
			dao.getAggTargetEntityFilter().forEach(arg ->
					CommonAccessMapper.convertToTargetEntityFilter(arg, domain, mappedObjects)
			);
		}
	}

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain          source of the given references
	 * @param dao             object where to add the references
	 * @param includeChildren {@code true} if references should be mapped. Otherwise {@code false}
	 * @param mappedObjects   map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	protected void setSourceEntityFilterNotAtTargetDaoMultiReferences(SourceEntityFilterNotAtTarget domain, SourceEntityFilterNotAtTargetDao dao
			, boolean includeChildren, Map<String, IIdentifiableDao> mappedObjects) {
		dao.setAggTargetEntityFilterNotAtTarget(new ArrayList<>());
		if (includeChildren) {
			domain.getOneToManyFilterA().forEach(arg ->
					CommonAccessMapper.convertToTargetEntityFilterNotAtTargetDao(arg, dao, AnyEnumType.ENUM_VALUE_A, mappedObjects)
			);
			domain.getOneToManyFilterB().forEach(arg ->
					CommonAccessMapper.convertToTargetEntityFilterNotAtTargetDao(arg, dao, AnyEnumType.ENUM_VALUE_B, mappedObjects)
			);
		}
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityFilterNotAtTargetDaoSingleReferences(SourceEntityFilterNotAtTarget domain, SourceEntityFilterNotAtTargetDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityFilterNotAtTargetDaoValues(SourceEntityFilterNotAtTarget domain, SourceEntityFilterNotAtTargetDao dao) {
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao             source of the given references
	 * @param domain          object where to add the references
	 * @param includeChildren {@code true} if references should be mapped. Otherwise {@code false}
	 * @param mappedObjects   map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	protected void setSourceEntityFilterNotAtTargetMultiReferences(SourceEntityFilterNotAtTargetDao dao, SourceEntityFilterNotAtTarget domain
			, boolean includeChildren, Map<String, IIdentifiable> mappedObjects) {
		if (includeChildren) {
			dao.getAggTargetEntityFilterNotAtTarget().forEach(arg ->
					CommonAccessMapper.convertToTargetEntityFilterNotAtTarget(arg.getTargetEntityFilterNotAtTarget(), domain, arg.getFilterAnyEnumType(), mappedObjects)
			);
		}
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityFilterNotAtTargetSingleReferences(SourceEntityFilterNotAtTargetDao dao, SourceEntityFilterNotAtTarget domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityFilterNotAtTargetValues(SourceEntityFilterNotAtTargetDao dao, SourceEntityFilterNotAtTarget domain) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityFilterSingleReferences(SourceEntityFilterDao dao, SourceEntityFilter domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityFilterValues(SourceEntityFilterDao dao, SourceEntityFilter domain) {
	}

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain          source of the given references
	 * @param dao             object where to add the references
	 * @param includeChildren {@code true} if references should be mapped. Otherwise {@code false}
	 * @param mappedObjects   map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	protected void setSourceEntityManyToManyDaoMultiReferences(SourceEntityManyToMany domain, SourceEntityManyToManyDao dao, boolean includeChildren
			, Map<String, IIdentifiableDao> mappedObjects) {
		dao.setManyToManyRef(new ArrayList<>());
		if (includeChildren) {
			domain.getManyToManyRef().forEach(arg ->
					CommonAccessMapper.convertToTargetEntityManyToManyDao(arg, dao, mappedObjects)
			);
		}
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityManyToManyDaoSingleReferences(SourceEntityManyToMany domain, SourceEntityManyToManyDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityManyToManyDaoValues(SourceEntityManyToMany domain, SourceEntityManyToManyDao dao) {
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao             source of the given references
	 * @param domain          object where to add the references
	 * @param includeChildren {@code true} if references should be mapped. Otherwise {@code false}
	 * @param mappedObjects   map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	protected void setSourceEntityManyToManyMultiReferences(SourceEntityManyToManyDao dao, SourceEntityManyToMany domain, boolean includeChildren
			, Map<String, IIdentifiable> mappedObjects) {
		if (includeChildren) {
			dao.getManyToManyRef().forEach(arg ->
					CommonAccessMapper.convertToTargetEntityManyToMany(arg.getTargetEntityManyToMany(), domain, mappedObjects)
			);
		}
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityManyToManySingleReferences(SourceEntityManyToManyDao dao, SourceEntityManyToMany domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityManyToManyValues(SourceEntityManyToManyDao dao, SourceEntityManyToMany domain) {
	}

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityManyToOneDaoMultiReferences(SourceEntityManyToOne domain, SourceEntityManyToOneDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	protected void setSourceEntityManyToOneDaoSingleReferences(SourceEntityManyToOne domain, SourceEntityManyToOneDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
		dao.setManyToOneRef(CommonAccessMapper.convertToTargetEntityManyToManyDao(domain.getManyToOneRef(), mappedObjects));
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityManyToOneDaoValues(SourceEntityManyToOne domain, SourceEntityManyToOneDao dao) {
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityManyToOneMultiReferences(SourceEntityManyToOneDao dao, SourceEntityManyToOne domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	protected void setSourceEntityManyToOneSingleReferences(SourceEntityManyToOneDao dao, SourceEntityManyToOne domain
			, Map<String, IIdentifiable> mappedObjects) {
		domain.setManyToOneRef(CommonAccessMapper.convertToTargetEntityManyToMany(dao.getManyToOneRef(), mappedObjects));
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityManyToOneValues(SourceEntityManyToOneDao dao, SourceEntityManyToOne domain) {
	}

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain          source of the given references
	 * @param dao             object where to add the references
	 * @param includeChildren {@code true} if references should be mapped. Otherwise {@code false}
	 * @param mappedObjects   map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	protected void setSourceEntityOneToManyDaoMultiReferences(SourceEntityOneToMany domain, SourceEntityOneToManyDao dao, boolean includeChildren
			, Map<String, IIdentifiableDao> mappedObjects) {
		dao.setOneToManyRef(new ArrayList<>());
		if (includeChildren) {
			domain.getOneToManyRef().forEach(arg ->
					CommonAccessMapper.convertToTargetEntityOneToManyDao(arg, dao, mappedObjects)
			);
		}
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityOneToManyDaoSingleReferences(SourceEntityOneToMany domain, SourceEntityOneToManyDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityOneToManyDaoValues(SourceEntityOneToMany domain, SourceEntityOneToManyDao dao) {
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao             source of the given references
	 * @param domain          object where to add the references
	 * @param includeChildren {@code true} if references should be mapped. Otherwise {@code false}
	 * @param mappedObjects   map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	protected void setSourceEntityOneToManyMultiReferences(SourceEntityOneToManyDao dao, SourceEntityOneToMany domain, boolean includeChildren
			, Map<String, IIdentifiable> mappedObjects) {
		if (includeChildren) {
			dao.getOneToManyRef().forEach(arg ->
					CommonAccessMapper.convertToTargetEntityOneToMany(arg, domain, mappedObjects)
			);
		}
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityOneToManySingleReferences(SourceEntityOneToManyDao dao, SourceEntityOneToMany domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityOneToManyValues(SourceEntityOneToManyDao dao, SourceEntityOneToMany domain) {
	}

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityOneToOneDaoMultiReferences(SourceEntityOneToOne domain, SourceEntityOneToOneDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	protected void setSourceEntityOneToOneDaoSingleReferences(SourceEntityOneToOne domain, SourceEntityOneToOneDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
		CommonAccessMapper.convertToTargetEntityOneToOneDao(domain.getOneToOneRef(), dao, mappedObjects);
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityOneToOneDaoValues(SourceEntityOneToOne domain, SourceEntityOneToOneDao dao) {
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityOneToOneMultiReferences(SourceEntityOneToOneDao dao, SourceEntityOneToOne domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	protected void setSourceEntityOneToOneSingleReferences(SourceEntityOneToOneDao dao, SourceEntityOneToOne domain
			, Map<String, IIdentifiable> mappedObjects) {
		CommonAccessMapper.convertToTargetEntityOneToOne(dao.getOneToOneRef(), domain, mappedObjects);
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setSourceEntityOneToOneValues(SourceEntityOneToOneDao dao, SourceEntityOneToOne domain) {
	}

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityFilterDaoMultiReferences(TargetEntityFilter domain, TargetEntityFilterDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityFilterDaoSingleReferences(TargetEntityFilter domain, TargetEntityFilterDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	protected void setTargetEntityFilterDaoValues(TargetEntityFilter domain, TargetEntityFilterDao dao) {
		dao.setEnumFieldForFiltering(domain.getEnumFieldForFiltering());
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityFilterMultiReferences(TargetEntityFilterDao dao, TargetEntityFilter domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityFilterNotAtTargetDaoMultiReferences(TargetEntityFilterNotAtTarget domain, TargetEntityFilterNotAtTargetDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityFilterNotAtTargetDaoSingleReferences(TargetEntityFilterNotAtTarget domain, TargetEntityFilterNotAtTargetDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityFilterNotAtTargetDaoValues(TargetEntityFilterNotAtTarget domain, TargetEntityFilterNotAtTargetDao dao) {
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityFilterNotAtTargetMultiReferences(TargetEntityFilterNotAtTargetDao dao, TargetEntityFilterNotAtTarget domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityFilterNotAtTargetSingleReferences(TargetEntityFilterNotAtTargetDao dao, TargetEntityFilterNotAtTarget domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityFilterNotAtTargetValues(TargetEntityFilterNotAtTargetDao dao, TargetEntityFilterNotAtTarget domain) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityFilterSingleReferences(TargetEntityFilterDao dao, TargetEntityFilter domain, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	protected void setTargetEntityFilterValues(TargetEntityFilterDao dao, TargetEntityFilter domain) {
		domain.setEnumFieldForFiltering(dao.getEnumFieldForFiltering());
	}

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityManyToManyDaoMultiReferences(TargetEntityManyToMany domain, TargetEntityManyToManyDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityManyToManyDaoSingleReferences(TargetEntityManyToMany domain, TargetEntityManyToManyDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityManyToManyDaoValues(TargetEntityManyToMany domain, TargetEntityManyToManyDao dao) {
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityManyToManyMultiReferences(TargetEntityManyToManyDao dao, TargetEntityManyToMany domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityManyToManySingleReferences(TargetEntityManyToManyDao dao, TargetEntityManyToMany domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityManyToManyValues(TargetEntityManyToManyDao dao, TargetEntityManyToMany domain) {
	}

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityOneToManyDaoMultiReferences(TargetEntityOneToMany domain, TargetEntityOneToManyDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityOneToManyDaoSingleReferences(TargetEntityOneToMany domain, TargetEntityOneToManyDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityOneToManyDaoValues(TargetEntityOneToMany domain, TargetEntityOneToManyDao dao) {
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityOneToManyMultiReferences(TargetEntityOneToManyDao dao, TargetEntityOneToMany domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityOneToManySingleReferences(TargetEntityOneToManyDao dao, TargetEntityOneToMany domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityOneToManyValues(TargetEntityOneToManyDao dao, TargetEntityOneToMany domain) {
	}

	/**
	 * Adds the references at {@code dao} which are of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityOneToOneDaoMultiReferences(TargetEntityOneToOne domain, TargetEntityOneToOneDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Adds the references at {@code dao} which are not of type {@link java.util.Collection}
	 *
	 * @param domain        source of the given references
	 * @param dao           object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code domain} to {@code dao}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityOneToOneDaoSingleReferences(TargetEntityOneToOne domain, TargetEntityOneToOneDao dao
			, Map<String, IIdentifiableDao> mappedObjects) {
	}

	/**
	 * Takes over values from {@code domain} to {@code dao} which are not of reference type
	 *
	 * @param domain source of the given values
	 * @param dao    object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityOneToOneDaoValues(TargetEntityOneToOne domain, TargetEntityOneToOneDao dao) {
	}

	/**
	 * Adds the references at {@code domain} which are of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityOneToOneMultiReferences(TargetEntityOneToOneDao dao, TargetEntityOneToOne domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Adds the references at {@code domain} which are not of type {@link java.util.Collection}
	 *
	 * @param dao           source of the given references
	 * @param domain        object where to add the references
	 * @param mappedObjects map which contains already mapped objects. It will be used while mapping sub entities of {@code dao} to {@code domain}
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityOneToOneSingleReferences(TargetEntityOneToOneDao dao, TargetEntityOneToOne domain
			, Map<String, IIdentifiable> mappedObjects) {
	}

	/**
	 * Takes over values from {@code dao} to {@code domain} which are not of reference type
	 *
	 * @param dao    source of the given values
	 * @param domain object where to set the values
	 */
	@SuppressWarnings("java:S1186")
	protected void setTargetEntityOneToOneValues(TargetEntityOneToOneDao dao, TargetEntityOneToOne domain) {
	}

}
