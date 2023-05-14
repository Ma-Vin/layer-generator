package de.ma_vin.util.sample.content.dto.multi.indirect;

import de.ma_vin.util.layer.generator.annotations.model.BaseDto;
import de.ma_vin.util.sample.content.dto.ITransportable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of MultiRefOtherIndirectParent
 */
@BaseDto("de.ma_vin.util.sample.content.dto")
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
