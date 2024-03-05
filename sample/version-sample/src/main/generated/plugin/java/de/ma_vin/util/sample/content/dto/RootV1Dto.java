package de.ma_vin.util.sample.content.dto;

import de.ma_vin.util.layer.generator.annotations.model.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated dto class of RootV1
 */
@BaseDto("de.ma_vin.util.sample.content.dto")
@Data
@EqualsAndHashCode(exclude = {"extNew", "anotherSingleRef"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"extNew", "anotherSingleRef"})
public class RootV1Dto implements ITransportable {

	private String addedField;

	private SingleRefTwoDto anotherSingleRef;

	private RootExtV1Dto extNew;

	/**
	 * Identification of RootV1
	 */
	private String identification;

	private String rootName;

}
