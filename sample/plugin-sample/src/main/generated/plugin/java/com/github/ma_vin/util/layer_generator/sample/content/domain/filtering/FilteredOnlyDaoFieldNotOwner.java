package com.github.ma_vin.util.layer_generator.sample.content.domain.filtering;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of FilteredOnlyDaoFieldNotOwner
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
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
