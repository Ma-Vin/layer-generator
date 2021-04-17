package de.ma_vin.util.sample.content.domain.domain;

import de.ma_vin.util.layer.generator.annotations.model.BaseDomain;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of OnlyDomain
 */
@BaseDomain("de.ma_vin.util.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class OnlyDomain implements IIdentifiable {

	public static final String ID_PREFIX = "D2";

	private String description;

	/**
	 * Identification of OnlyDomain
	 */
	private String identification;

}
