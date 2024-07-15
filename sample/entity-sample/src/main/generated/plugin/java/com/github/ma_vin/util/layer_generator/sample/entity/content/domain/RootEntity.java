package com.github.ma_vin.util.layer_generator.sample.entity.content.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of RootEntity
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.entity.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class RootEntity implements IIdentifiable {

	public static final String ID_PREFIX = "R";

	private String description;

	/**
	 * Identification of RootEntity
	 */
	private String identification;

	private String rootName;

}
