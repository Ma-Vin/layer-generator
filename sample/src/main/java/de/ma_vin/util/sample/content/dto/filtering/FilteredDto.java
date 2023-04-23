package de.ma_vin.util.sample.content.dto.filtering;

import de.ma_vin.util.layer.generator.annotations.model.BaseDto;
import de.ma_vin.util.sample.content.dto.ITransportable;
import de.ma_vin.util.sample.given.AnyEnumType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of Filtered
 */
@BaseDto("de.ma_vin.util.sample.content.dto")
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
