package com.github.ma_vin.util.layer_generator.sample.extension.content.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of ToExtendEntity
 * <br>
 * entity which will be extended
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.extension.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class ToExtendEntity implements IIdentifiable {

	public static final String ID_PREFIX = "R";

	private String existingAttribute;

	/**
	 * Identification of ToExtendEntity
	 */
	private String identification;

}
