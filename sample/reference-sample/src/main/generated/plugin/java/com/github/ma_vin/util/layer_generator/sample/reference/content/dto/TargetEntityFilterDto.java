package com.github.ma_vin.util.layer_generator.sample.reference.content.dto;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import com.github.ma_vin.util.layer_generator.sample.given.AnyEnumType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of TargetEntityFilter
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.reference.content.dto")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class TargetEntityFilterDto implements ITransportable {

	private AnyEnumType enumFieldForFiltering;

	/**
	 * Identification of TargetEntityFilter
	 */
	private String identification;

}
