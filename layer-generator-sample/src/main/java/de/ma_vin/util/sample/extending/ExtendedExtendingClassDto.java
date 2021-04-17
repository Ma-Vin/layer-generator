package de.ma_vin.util.sample.extending;

import de.ma_vin.util.layer.generator.annotations.model.ExtendingDto;
import de.ma_vin.util.sample.content.dto.parent.ExtendingClassDto;
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
