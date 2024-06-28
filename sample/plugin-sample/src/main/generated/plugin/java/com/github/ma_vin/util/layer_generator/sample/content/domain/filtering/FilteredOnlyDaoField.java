package com.github.ma_vin.util.layer_generator.sample.content.domain.filtering;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of FilteredOnlyDaoField
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class FilteredOnlyDaoField implements IIdentifiable {

	public static final String ID_PREFIX = "F4";

	private String descriptionOnlyDaoField;

	/**
	 * Identification of FilteredOnlyDaoField
	 */
	private String identification;

}
