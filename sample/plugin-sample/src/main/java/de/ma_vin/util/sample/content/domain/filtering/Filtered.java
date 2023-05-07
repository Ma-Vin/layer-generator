package de.ma_vin.util.sample.content.domain.filtering;

import de.ma_vin.util.layer.generator.annotations.model.BaseDomain;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.given.AnyEnumType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of Filtered
 */
@BaseDomain("de.ma_vin.util.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class Filtered implements IIdentifiable {

	public static final String ID_PREFIX = "F2";

	private String description;

	/**
	 * Identification of Filtered
	 */
	private String identification;

	private AnyEnumType someEnum;

}
