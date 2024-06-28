package com.github.ma_vin.util.layer_generator.sample.content.dto.single;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.ITransportable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of SingleRefTwoParents
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.content.dto")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class SingleRefTwoParentsDto implements ITransportable {

	private String description;

	/**
	 * Identification of SingleRefTwoParents
	 */
	private String identification;

}
