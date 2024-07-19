package com.github.ma_vin.util.layer_generator.sample.reference.content.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import com.github.ma_vin.util.layer_generator.sample.given.AnyEnumType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of TargetEntityFilter
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.reference.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class TargetEntityFilter implements IIdentifiable {

	public static final String ID_PREFIX = "T4";

	private AnyEnumType enumFieldForFiltering;

	/**
	 * Identification of TargetEntityFilter
	 */
	private String identification;

}
