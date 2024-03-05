package de.ma_vin.util.sample.content.domain;

import de.ma_vin.util.layer.generator.annotations.model.BaseDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of SingleRefOne
 */
@BaseDomain("de.ma_vin.util.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class SingleRefOne implements IIdentifiable {

	public static final String ID_PREFIX = "SR1";

	/**
	 * Identification of SingleRefOne
	 */
	private String identification;

}
