package de.ma_vin.util.sample.content.domain;

import de.ma_vin.util.layer.generator.annotations.model.BaseDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of SingleRefTwo
 */
@BaseDomain("de.ma_vin.util.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class SingleRefTwo implements IIdentifiable {

	public static final String ID_PREFIX = "SR2";

	/**
	 * Identification of SingleRefTwo
	 */
	private String identification;

}
