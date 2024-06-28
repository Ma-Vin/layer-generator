package com.github.ma_vin.util.layer_generator.sample.content.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of SubEntity
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class SubEntity implements IIdentifiable {

	public static final String ID_PREFIX = "S";

	private String description;

	/**
	 * Identification of SubEntity
	 */
	private String identification;

	private String subName;

}
