package de.ma_vin.util.sample.content.dto;

import de.ma_vin.util.layer.generator.annotations.model.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of RootExtV1
 */
@BaseDto("de.ma_vin.util.sample.content.dto")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class RootExtV1Dto implements ITransportable {

	private String extendedInfo;

	/**
	 * Identification of RootExtV1
	 */
	private String identification;

}
