package de.ma_vin.util.sample.content.domain;

import de.ma_vin.util.layer.generator.annotations.model.BaseDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of RootExt
 */
@BaseDomain("de.ma_vin.util.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class RootExt implements IIdentifiable {

	public static final String ID_PREFIX = "RE";

	private String extendedInfo;

	/**
	 * Identification of RootExt
	 */
	private String identification;

	private Integer someInteger;

}
