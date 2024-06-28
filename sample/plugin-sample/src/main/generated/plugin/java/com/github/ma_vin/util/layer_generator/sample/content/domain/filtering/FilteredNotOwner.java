package com.github.ma_vin.util.layer_generator.sample.content.domain.filtering;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.given.AnyEnumType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of FilteredNotOwner
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
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
