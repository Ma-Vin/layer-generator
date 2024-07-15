package com.github.ma_vin.util.layer_generator.sample.entity.content.dto;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of DerivedEntity
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.entity.content.dto")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class DerivedEntityDto implements ITransportable {

	/**
	 * Identification of DerivedEntity
	 */
	private String identification;

	private String rootName;

}
