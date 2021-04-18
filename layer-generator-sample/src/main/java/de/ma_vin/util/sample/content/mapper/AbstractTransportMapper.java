package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.content.dto.ITransportable;
import java.util.Map;

public abstract class AbstractTransportMapper extends AbstractMapper {

	protected static <S extends ITransportable, T extends IIdentifiable> T convertToDomain(S convertFrom, Map<String, IIdentifiable> mappedObjects, ObjectCreator<T> objectCreator, ValueMapper<S, T> valueMapper, ReferenceMapper<S, T> singleReferenceMapper, ReferenceMapper<S, T> multiReferenceMapper) {
		return convertTo(convertFrom, mappedObjects, objectCreator, valueMapper, singleReferenceMapper, multiReferenceMapper
				, S::getIdentification, (s, t) -> t.setIdentification(s.getIdentification()));
	}

	protected static <S extends IIdentifiable, T extends ITransportable> T convertToDto(S convertFrom, Map<String, ITransportable> mappedObjects, ObjectCreator<T> objectCreator, ValueMapper<S, T> valueMapper, ReferenceMapper<S, T> singleReferenceMapper, ReferenceMapper<S, T> multiReferenceMapper) {
		return convertTo(convertFrom, mappedObjects, objectCreator, valueMapper, singleReferenceMapper, multiReferenceMapper
				, S::getIdentification, (s, t) -> t.setIdentification(s.getIdentification()));
	}

}
