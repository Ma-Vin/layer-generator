package de.ma_vin.util.sample.content.domain.single;

import de.ma_vin.util.layer.generator.annotations.model.BaseDomain;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of SingleRefTwoParents
 */
@BaseDomain("de.ma_vin.util.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class SingleRefTwoParents implements IIdentifiable {

	public static final String ID_PREFIX = "S2";

	private String description;

	/**
	 * Identification of SingleRefTwoParents
	 */
	private String identification;

}
