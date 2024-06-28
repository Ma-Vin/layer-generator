package com.github.ma_vin.util.layer_generator.sample.content.domain.single.indirect;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of SingleRefIndirectParent
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class SingleRefIndirectParent implements IIdentifiable {

	public static final String ID_PREFIX = "SI2";

	private String description;

	/**
	 * Identification of SingleRefIndirectParent
	 */
	private String identification;

}
