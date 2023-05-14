package de.ma_vin.util.sample.extending;

import de.ma_vin.util.layer.generator.annotations.model.ExtendingDomain;
import de.ma_vin.util.sample.content.domain.multi.MultiRefTwoParents;
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
