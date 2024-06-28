package com.github.ma_vin.util.layer_generator.sample.content.domain.filtering;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import com.github.ma_vin.util.layer_generator.sample.given.AnyEnumType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of Filtered
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class Filtered implements IIdentifiable {

	public static final String ID_PREFIX = "F2";

	private String description;

	/**
	 * Identification of Filtered
	 */
	private String identification;

	private AnyEnumType someEnum;

}
