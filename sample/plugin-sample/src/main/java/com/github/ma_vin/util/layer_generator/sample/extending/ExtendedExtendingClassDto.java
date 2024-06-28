package com.github.ma_vin.util.layer_generator.sample.extending;

import com.github.ma_vin.util.layer_generator.annotations.model.ExtendingDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.parent.ExtendingClassDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * SuppressWarnings("java:S2160"): equals is provided by EqualsAndHashCode
 */
@ExtendingDto
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuppressWarnings("java:S2160")
public class ExtendedExtendingClassDto extends ExtendingClassDto {
    BigDecimal addedBigDecimal;
}
