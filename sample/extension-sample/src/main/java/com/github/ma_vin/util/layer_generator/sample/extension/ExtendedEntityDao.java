package com.github.ma_vin.util.layer_generator.sample.extension;

import com.github.ma_vin.util.layer_generator.annotations.model.ExtendingDao;
import com.github.ma_vin.util.layer_generator.sample.extension.content.dao.ToExtendEntityDao;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@ExtendingDao
@Data
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("java:S1068")
public class ExtendedEntityDao extends ToExtendEntityDao {
    private BigDecimal addedToDaoBigDecimal;
}
