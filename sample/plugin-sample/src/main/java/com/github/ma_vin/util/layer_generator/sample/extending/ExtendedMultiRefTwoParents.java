package com.github.ma_vin.util.layer_generator.sample.extending;

import com.github.ma_vin.util.layer_generator.annotations.model.ExtendingDomain;
import com.github.ma_vin.util.layer_generator.sample.content.domain.multi.MultiRefTwoParents;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * SuppressWarnings("java:S2160"): equals is provided by EqualsAndHashCode
 */
@ExtendingDomain
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuppressWarnings("java:S2160")
public class ExtendedMultiRefTwoParents extends MultiRefTwoParents {
    Boolean addedBoolean;
}
