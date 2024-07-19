package com.github.ma_vin.util.layer_generator.sample.extension.content.dto.sub;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import com.github.ma_vin.util.layer_generator.sample.extension.content.dto.ITransportable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of SubEntity
 * <br>
 * entity at sub package of basePackage.type
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.extension.content.dto")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class SubEntityDto implements ITransportable {

	/**
	 * Identification of SubEntity
	 */
	private String identification;

}
