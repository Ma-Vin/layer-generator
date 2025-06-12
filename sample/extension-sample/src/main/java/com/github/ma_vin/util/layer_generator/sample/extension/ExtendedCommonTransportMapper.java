package com.github.ma_vin.util.layer_generator.sample.extension;

import com.github.ma_vin.util.layer_generator.annotations.mapper.ExtendingTransportMapper;
import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.ToExtendEntity;
import com.github.ma_vin.util.layer_generator.sample.extension.content.dto.ToExtendEntityDto;
import com.github.ma_vin.util.layer_generator.sample.extension.content.mapper.CommonTransportMapper;

@ExtendingTransportMapper
public class ExtendedCommonTransportMapper extends CommonTransportMapper {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setToExtendEntityDtoValues(ToExtendEntity domain, ToExtendEntityDto dto) {
        super.setToExtendEntityDtoValues(domain, dto);
        if (domain instanceof ExtendedEntity domainExtended && dto instanceof ExtendedEntityDto dtoExtended) {
            dtoExtended.setAddedToDtoBigDecimal(domainExtended.getAddedToDomainBigDecimal());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setToExtendEntityValues(ToExtendEntityDto dto, ToExtendEntity domain) {
        super.setToExtendEntityValues(dto, domain);
        if (dto instanceof ExtendedEntityDto dtoExtended && domain instanceof ExtendedEntity domainExtended) {
            domainExtended.setAddedToDomainBigDecimal(dtoExtended.getAddedToDtoBigDecimal());
        }
    }
}
