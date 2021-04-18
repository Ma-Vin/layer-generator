package de.ma_vin.util.sample.content.mapper;

import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import java.util.Map;

public abstract class AbstractAccessMapper extends AbstractMapper {

	protected static <S extends IIdentifiable, T extends IIdentifiableDao> T convertToDao(S convertFrom, Map<String, IIdentifiableDao> mappedObjects, ObjectCreator<T> objectCreator, ValueMapper<S, T> valueMapper, ReferenceMapper<S, T> singleReferenceMapper, ReferenceMapper<S, T> multiReferenceMapper) {
		return convertTo(convertFrom, mappedObjects, objectCreator, valueMapper, singleReferenceMapper, multiReferenceMapper
				, S::getIdentification, (s, t) -> t.setIdentification(s.getIdentification()));
	}

	protected static <S extends IIdentifiableDao, T extends IIdentifiable> T convertToDomain(S convertFrom, Map<String, IIdentifiable> mappedObjects, ObjectCreator<T> objectCreator, ValueMapper<S, T> valueMapper, ReferenceMapper<S, T> singleReferenceMapper, ReferenceMapper<S, T> multiReferenceMapper) {
		return convertTo(convertFrom, mappedObjects, objectCreator, valueMapper, singleReferenceMapper, multiReferenceMapper
				, S::getIdentification, (s, t) -> t.setIdentification(s.getIdentification()));
	}

}
