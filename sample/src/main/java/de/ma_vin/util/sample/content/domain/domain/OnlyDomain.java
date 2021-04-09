package de.ma_vin.util.sample.content.domain.domain;

import de.ma_vin.util.sample.content.domain.IIdentifiable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of OnlyDomain
 */
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
