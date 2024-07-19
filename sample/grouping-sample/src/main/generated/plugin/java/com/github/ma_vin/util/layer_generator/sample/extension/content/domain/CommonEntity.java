package com.github.ma_vin.util.layer_generator.sample.extension.content.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of CommonEntity
 * <br>
 * entity at default basePackage
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.extension.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class CommonEntity implements IIdentifiable {

	public static final String ID_PREFIX = "C";

	/**
	 * Identification of CommonEntity
	 */
	private String identification;

}
