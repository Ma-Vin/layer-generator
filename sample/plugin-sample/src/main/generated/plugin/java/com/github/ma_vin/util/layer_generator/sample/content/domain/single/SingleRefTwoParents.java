package com.github.ma_vin.util.layer_generator.sample.content.domain.single;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of SingleRefTwoParents
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class SingleRefTwoParents implements IIdentifiable {

	public static final String ID_PREFIX = "S2";

	private String description;

	/**
	 * Identification of SingleRefTwoParents
	 */
	private String identification;

}
