package de.ma_vin.util.sample.content.dto.multi;

import de.ma_vin.util.layer.generator.annotations.model.BaseDto;
import de.ma_vin.util.sample.content.dto.ITransportable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of MultiRefOneParent
 */
@BaseDto("de.ma_vin.util.sample.content.dto")
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
