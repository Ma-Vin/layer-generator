package com.github.ma_vin.util.layer_generator.sample.reference.content.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated domain class of SourceEntityManyToOne
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.reference.content.domain")
@Data
@EqualsAndHashCode(exclude = {"manyToOneRef"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"manyToOneRef"})
public class SourceEntityManyToOne implements IIdentifiable {

	public static final String ID_PREFIX = "S3_2";

	/**
	 * Identification of SourceEntityManyToOne
	 */
	private String identification;

	/**
	 * a m:1 reference
	 */
	private TargetEntityManyToMany manyToOneRef;

}
