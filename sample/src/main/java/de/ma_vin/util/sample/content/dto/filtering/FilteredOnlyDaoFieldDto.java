package de.ma_vin.util.sample.content.dto.filtering;

import de.ma_vin.util.layer.generator.annotations.model.BaseDto;
import de.ma_vin.util.sample.content.dto.ITransportable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of FilteredOnlyDaoField
 */
@BaseDto("de.ma_vin.util.sample.content.dto")
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
