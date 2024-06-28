package com.github.ma_vin.util.layer_generator.sample.content.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of SingleRefOne
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class SingleRefOne implements IIdentifiable {

	public static final String ID_PREFIX = "SR1";

	/**
	 * Identification of SingleRefOne
	 */
	private String identification;

}
