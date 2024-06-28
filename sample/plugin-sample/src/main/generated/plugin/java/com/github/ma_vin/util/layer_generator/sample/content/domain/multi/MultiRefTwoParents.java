package com.github.ma_vin.util.layer_generator.sample.content.domain.multi;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of MultiRefTwoParents
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class MultiRefTwoParents implements IIdentifiable {

	public static final String ID_PREFIX = "M2";

	private String description;

	/**
	 * Identification of MultiRefTwoParents
	 */
	private String identification;

}
