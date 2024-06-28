package com.github.ma_vin.util.layer_generator.sample.content.dto.filtering;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.ITransportable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of FilteredOnlyDaoField
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.content.dto")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class FilteredOnlyDaoFieldDto implements ITransportable {

	private String descriptionOnlyDaoField;

	/**
	 * Identification of FilteredOnlyDaoField
	 */
	private String identification;

}
