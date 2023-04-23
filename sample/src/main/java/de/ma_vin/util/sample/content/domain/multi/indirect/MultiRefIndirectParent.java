package de.ma_vin.util.sample.content.domain.multi.indirect;

import de.ma_vin.util.layer.generator.annotations.model.BaseDomain;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of MultiRefIndirectParent
 */
@BaseDomain("de.ma_vin.util.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class MultiRefIndirectParent implements IIdentifiable {

	public static final String ID_PREFIX = "MI2";

	private String description;

	/**
	 * Identification of MultiRefIndirectParent
	 */
	private String identification;

}
