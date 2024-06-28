package com.github.ma_vin.util.layer_generator.sample.content.dto.multi.indirect;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.ITransportable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of MultiRefOtherIndirectParent
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.content.dto")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class MultiRefOtherIndirectParentDto implements ITransportable {

	private String description;

	/**
	 * Identification of MultiRefOtherIndirectParent
	 */
	private String identification;

}
