package com.github.ma_vin.util.layer_generator.sample.entity.content.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of RootEntity
 * <br>
 * The root entity of this example
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.entity.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class RootEntity implements IIdentifiable {

	public static final String ID_PREFIX = "R";

	private double anyAttribute;

	/**
	 * Identification of RootEntity
	 */
	private String identification;

	private String rootName;

}
