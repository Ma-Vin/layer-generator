package com.github.ma_vin.util.layer_generator.sample.content.mapper;

import java.util.Map;

/**
 * Abstract basic mapper class which contains the common convertToMethod
 */
public abstract class AbstractMapper {

	/**
	 * Converts an {@code convertFrom} to an {@code T} object
	 *
	 * @param convertFrom           object which is to converted
	 * @param mappedObjects         map which contains already mapped objects. If an identification of {@code convertFrom} is contained, the found
	 *                              {@code M} will be returned
	 * @param objectCreator         functional interface which is called to create a new instance of {@code T} as result
	 * @param valueMapper           functional interface which is called to set the values of {@code convertFrom} at result
	 * @param singleReferenceMapper functional interface which is called to add the single references of {@code convertFrom} at result
	 * @param multiReferenceMapper  functional interface which is called to add the multi references of {@code convertFrom} at result
	 * @param identificationGetter  functional interface which is called to get the identification of {@code convertFrom}
	 * @param identificationSetter  functional interface which is called to set the identification at result
	 * @param <M>                   the type of the object at map
	 * @param <S>                   the type of the source object
	 * @param <T>                   the type of the target object
	 * @return an equivalent new created object or the found one from the given map
	 */
	@SuppressWarnings("java:S107")
	protected static <M, S, T> T convertTo(S convertFrom, Map<String, M> mappedObjects, ObjectCreator<T> objectCreator, ValueMapper<S, T> valueMapper
			, ReferenceMapper<S, T> singleReferenceMapper, ReferenceMapper<S, T> multiReferenceMapper, IdentificationGetter<S> identificationGetter
			, IdentificationSetter<S, T> identificationSetter) {
		if (convertFrom == null) {
			return null;
		}

		String identification = identificationGetter.getIdentification(convertFrom);
		if (!mappedObjects.isEmpty() && mappedObjects.containsKey(identification)) {
			return (T) mappedObjects.get(identification);
		}

		T result = objectCreator.create();

		identificationSetter.setIdentification(convertFrom, result);

		valueMapper.mapValues(convertFrom, result);
		singleReferenceMapper.mapReferences(convertFrom, result);
		multiReferenceMapper.mapReferences(convertFrom, result);

		mappedObjects.put(identification, (M) result);
		return result;
	}

	/**
	 * functional interface which is called to get the identification of {@code S}
	 *
	 * @param <S> the type of the source object
	 */
	@FunctionalInterface
	public interface IdentificationGetter<S> {

		String getIdentification(S convertFrom);

	}

	/**
	 * functional interface which is called to set the identification at {@code T} from {@code S}
	 *
	 * @param <S> the type of the source object
	 * @param <T> the type of the target object
	 */
	@FunctionalInterface
	public interface IdentificationSetter<S, T> {

		void setIdentification(S convertFrom, T convertTo);

	}

	/**
	 * functional interface which is called to create a new instance
	 *
	 * @param <T> the type of the created object
	 */
	@FunctionalInterface
	public interface ObjectCreator<T> {

		T create();

	}

	/**
	 * functional interface which is called to add references of {@code S} at {@code T}
	 *
	 * @param <S> the type of the source object
	 * @param <T> the type of the target object
	 */
	@FunctionalInterface
	public interface ReferenceMapper<S, T> {

		void mapReferences(S convertFrom, T convertTo);

	}

	/**
	 * functional interface which is called to set the values of {@code S} at {@code T}
	 *
	 * @param <S> the type of the source object
	 * @param <T> the type of the target object
	 */
	@FunctionalInterface
	public interface ValueMapper<S, T> {

		void mapValues(S convertFrom, T convertTo);

	}

}
