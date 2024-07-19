package com.github.ma_vin.util.layer_generator.sample.reference.content.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated domain class of SourceEntityOneToOne
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.reference.content.domain")
@Data
@EqualsAndHashCode(exclude = {"oneToOneRef"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"oneToOneRef"})
public class SourceEntityOneToOne implements IIdentifiable {

	public static final String ID_PREFIX = "S1";

	/**
	 * Identification of SourceEntityOneToOne
	 */
	private String identification;

	/**
	 * a 1:1 reference
	 */
	private TargetEntityOneToOne oneToOneRef;

}
