package com.github.ma_vin.util.layer_generator.sample.content.dto;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of RootExt
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.content.dto")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class RootExtDto implements ITransportable {

	private String extendedInfo;

	/**
	 * Identification of RootExt
	 */
	private String identification;

	private Integer someInteger;

}
