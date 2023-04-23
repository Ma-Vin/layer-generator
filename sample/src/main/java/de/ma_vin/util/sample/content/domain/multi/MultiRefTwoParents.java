package de.ma_vin.util.sample.content.domain.multi;

import de.ma_vin.util.layer.generator.annotations.model.BaseDomain;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of MultiRefTwoParents
 */
@BaseDomain("de.ma_vin.util.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class MultiRefTwoParents implements IIdentifiable {

	public static final String ID_PREFIX = "M2";

	private String description;

	/**
	 * Identification of MultiRefTwoParents
	 */
	private String identification;

}
