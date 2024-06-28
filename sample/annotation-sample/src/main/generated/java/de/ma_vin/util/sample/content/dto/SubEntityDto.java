package de.ma_vin.util.sample.content.dto;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of SubEntity
 */
@BaseDto("de.ma_vin.util.sample.content.dto")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class SubEntityDto implements ITransportable {

	private String description;

	/**
	 * Identification of SubEntity
	 */
	private String identification;

	private String subName;

}
