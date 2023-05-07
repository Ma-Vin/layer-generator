package de.ma_vin.util.sample.content.domain.filtering;

import de.ma_vin.util.layer.generator.annotations.model.BaseDomain;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of FilteredOnlyDaoFieldNotOwner
 */
@BaseDomain("de.ma_vin.util.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class FilteredOnlyDaoFieldNotOwner implements IIdentifiable {

	public static final String ID_PREFIX = "F5";

	private String descriptionOnlyDaoFieldNotOwner;

	/**
	 * Identification of FilteredOnlyDaoFieldNotOwner
	 */
	private String identification;

}
