package com.github.ma_vin.util.layer_generator.sample.entity.content.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of AbstractEntity
 */
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public abstract class AbstractEntity implements IIdentifiable {

	public static final String ID_PREFIX = "A";

	/**
	 * Identification of AbstractEntity
	 */
	private String identification;

	private String superName;

}
