package de.ma_vin.util.sample.content.domain.single.indirect;

import de.ma_vin.util.layer.generator.annotations.model.BaseDomain;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of SingleRefIndirectParent
 */
@BaseDomain("de.ma_vin.util.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class SingleRefIndirectParent implements IIdentifiable {

	public static final String ID_PREFIX = "SI2";

	private String description;

	/**
	 * Identification of SingleRefIndirectParent
	 */
	private String identification;

}
