package de.ma_vin.util.sample.content.mapper;

import java.util.Map;

public abstract class AbstractMapper {

	@SuppressWarnings("java:S107")
	protected static <M, S, T> T convertTo(S convertFrom, Map<String, M> mappedObjects, ObjectCreator<T> objectCreator, ValueMapper<S, T> valueMapper, ReferenceMapper<S, T> singleReferenceMapper, ReferenceMapper<S, T> multiReferenceMapper, IdentificationGetter<S> identificationGetter, IdentificationSetter<S, T> identificationSetter) {
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

	@FunctionalInterface
	public interface IdentificationGetter<S> {

		String getIdentification(S convertFrom);

	}

	@FunctionalInterface
	public interface IdentificationSetter<S, T> {

		void setIdentification(S convertFrom, T convertTo);

	}

	@FunctionalInterface
	public interface ObjectCreator<T> {

		T create();

	}

	@FunctionalInterface
	public interface ReferenceMapper<S, T> {

		void mapReferences(S convertFrom, T convertTo);

	}

	@FunctionalInterface
	public interface ValueMapper<S, T> {

		void mapValues(S convertFrom, T convertTo);

	}

}
