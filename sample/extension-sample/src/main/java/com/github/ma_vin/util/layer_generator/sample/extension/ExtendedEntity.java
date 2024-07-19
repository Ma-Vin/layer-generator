package com.github.ma_vin.util.layer_generator.sample.extension;

import com.github.ma_vin.util.layer_generator.annotations.model.ExtendingDomain;
import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.ToExtendEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@ExtendingDomain
@Data
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("java:S1068")
public class ExtendedEntity extends ToExtendEntity {
    private BigDecimal addedToDomainBigDecimal;
}
