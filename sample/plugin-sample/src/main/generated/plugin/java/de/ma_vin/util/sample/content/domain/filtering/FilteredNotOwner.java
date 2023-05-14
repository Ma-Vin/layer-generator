package de.ma_vin.util.sample.content.domain.filtering;

import de.ma_vin.util.layer.generator.annotations.model.BaseDomain;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import de.ma_vin.util.sample.given.AnyEnumType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of FilteredNotOwner
 */
@BaseDomain("de.ma_vin.util.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class FilteredNotOwner implements IIdentifiable {

	public static final String ID_PREFIX = "F3";

	private String descriptionNotOwner;

	/**
	 * Identification of FilteredNotOwner
	 */
	private String identification;

	private AnyEnumType someEnumNotOwner;

}
