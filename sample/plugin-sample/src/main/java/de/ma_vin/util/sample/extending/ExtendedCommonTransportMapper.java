package de.ma_vin.util.sample.extending;

import de.ma_vin.util.layer.generator.annotations.mapper.ExtendingTransportMapper;
import de.ma_vin.util.sample.content.domain.Root;
import de.ma_vin.util.sample.content.dto.RootDto;
import de.ma_vin.util.sample.content.mapper.CommonTransportMapper;

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
