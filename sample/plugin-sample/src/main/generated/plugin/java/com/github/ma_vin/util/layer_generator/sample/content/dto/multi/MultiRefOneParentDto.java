package com.github.ma_vin.util.layer_generator.sample.content.dto.multi;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.ITransportable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of MultiRefOneParent
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.content.dto")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class MultiRefOneParentDto implements ITransportable {

	private String description;

	/**
	 * Identification of MultiRefOneParent
	 */
	private String identification;

}
