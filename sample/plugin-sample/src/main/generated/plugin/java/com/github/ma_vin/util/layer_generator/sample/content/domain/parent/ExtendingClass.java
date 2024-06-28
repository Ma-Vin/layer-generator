package com.github.ma_vin.util.layer_generator.sample.content.domain.parent;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated domain class of ExtendingClass
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuppressWarnings({"java:S2160", "java:S1068"})
@ToString(callSuper = true)
public class ExtendingClass extends SuperClass {

	public static final String ID_PREFIX = "SC2";

	private String additionalDescription;

	/**
	 * Identification of ExtendingClass
	 */
	private String identification;

}
