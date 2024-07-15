package com.github.ma_vin.util.layer_generator.sample.entity.content.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of AbstractEntity
 */
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public abstract class AbstractEntityDto implements ITransportable {

	/**
	 * Identification of AbstractEntity
	 */
	private String identification;

	private String superName;

}
