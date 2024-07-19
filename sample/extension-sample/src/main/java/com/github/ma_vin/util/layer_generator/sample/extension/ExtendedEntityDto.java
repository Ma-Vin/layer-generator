package com.github.ma_vin.util.layer_generator.sample.extension;

import com.github.ma_vin.util.layer_generator.annotations.model.ExtendingDto;
import com.github.ma_vin.util.layer_generator.sample.extension.content.dto.ToExtendEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@ExtendingDto
@Data
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("java:S1068")
public class ExtendedEntityDto extends ToExtendEntityDto {
    private BigDecimal addedToDtoBigDecimal;
}
