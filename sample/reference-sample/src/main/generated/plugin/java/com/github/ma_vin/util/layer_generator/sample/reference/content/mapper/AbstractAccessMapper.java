package com.github.ma_vin.util.layer_generator.sample.reference.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.reference.content.dao.IIdentifiableDao;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.IIdentifiable;
import java.util.Map;

/**
 * Generated abstract class which provides generic methods to convert a data access to a domain object and the other way around
 */
public abstract class AbstractAccessMapper extends AbstractMapper {

	/**
	 * Converts an {@link IIdentifiable} to an {@link IIdentifiableDao} object
	 *
	 * @param convertFrom           object which is to converted
	 * @param mappedObjects         map which contains already mapped objects. If an identification of {@code convertFrom} is contained, the found
	 *                              {@link IIdentifiableDao} will be returned
	 * @param objectCreator         functional interface which is called to create a new instance of {@link IIdentifiableDao} as result
	 * @param valueMapper           functional interface which is called to set the values of {@code convertFrom} at result
	 * @param singleReferenceMapper functional interface which is called to add the single references of {@code convertFrom} at result
	 * @param multiReferenceMapper  functional interface which is called to add the multi references of {@code convertFrom} at result
	 * @param <S>                   the type of the source object
	 * @param <T>                   the type of the target object
	 * @return an equivalent new created object or the found one from the given map
	 */
	protected static <S extends IIdentifiable, T extends IIdentifiableDao> T convertToDao(S convertFrom, Map<String, IIdentifiableDao> mappedObjects
			, ObjectCreator<T> objectCreator, ValueMapper<S, T> valueMapper, ReferenceMapper<S, T> singleReferenceMapper
			, ReferenceMapper<S, T> multiReferenceMapper) {
		return convertTo(convertFrom, mappedObjects, objectCreator, valueMapper, singleReferenceMapper, multiReferenceMapper
				, S::getIdentification, (s, t) -> t.setIdentification(s.getIdentification()));
	}

	/**
	 * Converts an {@link IIdentifiableDao} to an {@link IIdentifiable} object
	 *
	 * @param convertFrom           object which is to converted
	 * @param mappedObjects         map which contains already mapped objects. If an identification of {@code convertFrom} is contained, the found
	 *                              {@link IIdentifiable} will be returned
	 * @param objectCreator         functional interface which is called to create a new instance of {@link IIdentifiable} as result
	 * @param valueMapper           functional interface which is called to set the values of {@code convertFrom} at result
	 * @param singleReferenceMapper functional interface which is called to add the single references of {@code convertFrom} at result
	 * @param multiReferenceMapper  functional interface which is called to add the multi references of {@code convertFrom} at result
	 * @param <S>                   the type of the source object
	 * @param <T>                   the type of the target object
	 * @return an equivalent new created object or the found one from the given map
	 */
	protected static <S extends IIdentifiableDao, T extends IIdentifiable> T convertToDomain(S convertFrom, Map<String, IIdentifiable> mappedObjects
			, ObjectCreator<T> objectCreator, ValueMapper<S, T> valueMapper, ReferenceMapper<S, T> singleReferenceMapper
			, ReferenceMapper<S, T> multiReferenceMapper) {
		return convertTo(convertFrom, mappedObjects, objectCreator, valueMapper, singleReferenceMapper, multiReferenceMapper
				, S::getIdentification, (s, t) -> t.setIdentification(s.getIdentification()));
	}

}
