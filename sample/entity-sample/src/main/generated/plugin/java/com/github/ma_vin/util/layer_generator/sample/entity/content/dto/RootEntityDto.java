package com.github.ma_vin.util.layer_generator.sample.entity.content.dto;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of RootEntity
 * <br>
 * The root entity of this example
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.entity.content.dto")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class RootEntityDto implements ITransportable {

	private double anyAttribute;

	/**
	 * Identification of RootEntity
	 */
	private String identification;

	private String rootName;

}
