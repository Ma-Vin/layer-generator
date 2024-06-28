package com.github.ma_vin.util.layer_generator.sample.content.domain.single;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated domain class of SingleRefOneParent
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
@Data
@EqualsAndHashCode(exclude = {"singleRef"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"singleRef"})
public class SingleRefOneParent implements IIdentifiable {

	public static final String ID_PREFIX = "S1";

	private String description;

	/**
	 * Identification of SingleRefOneParent
	 */
	private String identification;

	/**
	 * Instance with two parents
	 */
	private SingleRefTwoParents singleRef;

}
