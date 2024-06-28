package com.github.ma_vin.util.layer_generator.sample.content.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of RootExt
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class RootExt implements IIdentifiable {

	public static final String ID_PREFIX = "RE";

	private String extendedInfo;

	/**
	 * Identification of RootExt
	 */
	private String identification;

	private Integer someInteger;

}
