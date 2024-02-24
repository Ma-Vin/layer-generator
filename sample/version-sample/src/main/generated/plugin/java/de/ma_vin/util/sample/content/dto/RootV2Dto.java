package de.ma_vin.util.sample.content.dto;

import de.ma_vin.util.layer.generator.annotations.model.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated dto class of RootV2
 */
@BaseDto("de.ma_vin.util.sample.content.dto")
@Data
@EqualsAndHashCode(exclude = {"extNew", "anotherSingleRef"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"extNew", "anotherSingleRef"})
public class RootV2Dto implements ITransportable {

	private SingleRefTwoDto anotherSingleRef;

	private RootExtV1Dto extNew;

	/**
	 * Identification of RootV2
	 */
	private String identification;

	private String rootName;

}
