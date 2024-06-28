package com.github.ma_vin.util.layer_generator.sample.content.dto.single.indirect;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.ITransportable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated dto class of SingleRefOtherIndirectParent
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.content.dto")
@Data
@EqualsAndHashCode(exclude = {"singleIndirectRef"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"singleIndirectRef"})
public class SingleRefOtherIndirectParentDto implements ITransportable {

	private String description;

	/**
	 * Identification of SingleRefOtherIndirectParent
	 */
	private String identification;

	private SingleRefIndirectParentDto singleIndirectRef;

}
