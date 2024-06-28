package com.github.ma_vin.util.layer_generator.sample.extending;

import com.github.ma_vin.util.layer_generator.annotations.mapper.ExtendingTransportMapper;
import com.github.ma_vin.util.layer_generator.sample.content.domain.Root;
import com.github.ma_vin.util.layer_generator.sample.content.dto.RootDto;
import com.github.ma_vin.util.layer_generator.sample.content.mapper.CommonTransportMapper;

@ExtendingTransportMapper
public class ExtendedCommonTransportMapper extends CommonTransportMapper {
    public static final String ADDITIONAL_POSTFIX = "Postfix";

    @Override
    protected void setRootDtoValues(Root domain, RootDto dto) {
        super.setRootDtoValues(domain, dto);
        dto.setDescription(dto.getDescription() + ADDITIONAL_POSTFIX);
    }

    @Override
    protected void setRootValues(RootDto dto, Root domain) {
        super.setRootValues(dto, domain);
        domain.setDescription(domain.getDescription() + ADDITIONAL_POSTFIX);
    }
}
