package com.github.ma_vin.util.layer_generator.sample.reference.content.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of TargetEntityOneToOne
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.reference.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class TargetEntityOneToOne implements IIdentifiable {

	public static final String ID_PREFIX = "T1";

	/**
	 * Identification of TargetEntityOneToOne
	 */
	private String identification;

}
