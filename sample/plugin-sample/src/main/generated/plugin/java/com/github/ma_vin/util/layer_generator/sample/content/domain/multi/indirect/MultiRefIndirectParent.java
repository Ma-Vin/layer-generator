package com.github.ma_vin.util.layer_generator.sample.content.domain.multi.indirect;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of MultiRefIndirectParent
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class MultiRefIndirectParent implements IIdentifiable {

	public static final String ID_PREFIX = "MI2";

	private String description;

	/**
	 * Identification of MultiRefIndirectParent
	 */
	private String identification;

}
