package com.github.ma_vin.util.layer_generator.sample.extension.content.dto;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of ToExtendEntity
 * <br>
 * entity which will be extended
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.extension.content.dto")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class ToExtendEntityDto implements ITransportable {

	private String existingAttribute;

	/**
	 * Identification of ToExtendEntity
	 */
	private String identification;

}
