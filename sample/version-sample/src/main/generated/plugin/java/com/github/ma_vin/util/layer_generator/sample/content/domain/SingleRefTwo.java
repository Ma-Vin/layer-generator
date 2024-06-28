package com.github.ma_vin.util.layer_generator.sample.content.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of SingleRefTwo
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class SingleRefTwo implements IIdentifiable {

	public static final String ID_PREFIX = "SR2";

	/**
	 * Identification of SingleRefTwo
	 */
	private String identification;

}
