package de.ma_vin.util.sample.content.domain.parent;

import de.ma_vin.util.sample.content.domain.IIdentifiable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of SuperClass
 */
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public abstract class SuperClass implements IIdentifiable {

	public static final String ID_PREFIX = "SC1";

	private String description;

	/**
	 * Identification of SuperClass
	 */
	private String identification;

}
