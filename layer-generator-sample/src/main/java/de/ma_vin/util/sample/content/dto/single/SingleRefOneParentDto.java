package de.ma_vin.util.sample.content.dto.single;

import de.ma_vin.util.layer.generator.annotations.model.BaseDto;
import de.ma_vin.util.sample.content.dto.ITransportable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated dto class of SingleRefOneParent
 */
@BaseDto("de.ma_vin.util.sample.content.dto")
@Data
@EqualsAndHashCode(exclude = {"singleRef"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"singleRef"})
public class SingleRefOneParentDto implements ITransportable {

	private String description;

	/**
	 * Identification of SingleRefOneParent
	 */
	private String identification;

	/**
	 * Instance with two parents
	 */
	private SingleRefTwoParentsDto singleRef;

}
