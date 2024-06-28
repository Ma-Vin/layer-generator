package com.github.ma_vin.util.layer_generator.sample.content.dto.filtering;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.ITransportable;
import com.github.ma_vin.util.layer_generator.sample.given.AnyEnumType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of Filtered
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.content.dto")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class FilteredDto implements ITransportable {

	private String description;

	/**
	 * Identification of Filtered
	 */
	private String identification;

	private AnyEnumType someEnum;

}
