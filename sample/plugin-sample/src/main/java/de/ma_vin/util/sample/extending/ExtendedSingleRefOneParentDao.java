package de.ma_vin.util.sample.extending;

import com.github.ma_vin.util.layer_generator.annotations.model.ExtendingDao;
import de.ma_vin.util.sample.content.dao.single.SingleRefOneParentDao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * SuppressWarnings("java:S2160"): equals is provided by EqualsAndHashCode
 */
@ExtendingDao
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuppressWarnings("java:S2160")
public class ExtendedSingleRefOneParentDao extends SingleRefOneParentDao {
    String addedString;
}
