package com.github.ma_vin.util.layer_generator.sample.content.dto.single;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.ITransportable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated dto class of SingleRefOneParent
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.content.dto")
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
