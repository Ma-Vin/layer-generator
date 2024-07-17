package com.github.ma_vin.util.layer_generator.sample.field.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.field.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.field.content.dto.ITransportable;
import java.util.Map;

/**
 * Generated abstract class which provides generic methods to convert a data transport to a domain object and the other way around
 */
public abstract class AbstractTransportMapper extends AbstractMapper {

	/**
	 * Converts an {@link ITransportable} to an {@link IIdentifiable} object
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
	protected static <S extends ITransportable, T extends IIdentifiable> T convertToDomain(S convertFrom, Map<String, IIdentifiable> mappedObjects
			, ObjectCreator<T> objectCreator, ValueMapper<S, T> valueMapper, ReferenceMapper<S, T> singleReferenceMapper
			, ReferenceMapper<S, T> multiReferenceMapper) {
		return convertTo(convertFrom, mappedObjects, objectCreator, valueMapper, singleReferenceMapper, multiReferenceMapper
				, S::getIdentification, (s, t) -> t.setIdentification(s.getIdentification()));
	}

	/**
	 * Converts an {@link IIdentifiable} to an {@link ITransportable} object
	 *
	 * @param convertFrom           object which is to converted
	 * @param mappedObjects         map which contains already mapped objects. If an identification of {@code convertFrom} is contained, the found
	 *                              {@link ITransportable} will be returned
	 * @param objectCreator         functional interface which is called to create a new instance of {@link ITransportable} as result
	 * @param valueMapper           functional interface which is called to set the values of {@code convertFrom} at result
	 * @param singleReferenceMapper functional interface which is called to add the single references of {@code convertFrom} at result
	 * @param multiReferenceMapper  functional interface which is called to add the multi references of {@code convertFrom} at result
	 * @param <S>                   the type of the source object
	 * @param <T>                   the type of the target object
	 * @return an equivalent new created object or the found one from the given map
	 */
	protected static <S extends IIdentifiable, T extends ITransportable> T convertToDto(S convertFrom, Map<String, ITransportable> mappedObjects
			, ObjectCreator<T> objectCreator, ValueMapper<S, T> valueMapper, ReferenceMapper<S, T> singleReferenceMapper
			, ReferenceMapper<S, T> multiReferenceMapper) {
		return convertTo(convertFrom, mappedObjects, objectCreator, valueMapper, singleReferenceMapper, multiReferenceMapper
				, S::getIdentification, (s, t) -> t.setIdentification(s.getIdentification()));
	}

}
