package com.github.ma_vin.util.layer_generator.sample.content.dto;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated dto class of Root
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.content.dto")
@Data
@EqualsAndHashCode(exclude = {"singleRef", "anotherSingleRef", "ext"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"singleRef", "anotherSingleRef", "ext"})
public class RootDto implements ITransportable {

	private SingleRefTwoDto anotherSingleRef;

	private String description;

	private RootExtDto ext;

	/**
	 * Identification of Root
	 */
	private String identification;

	private String rootName;

	private SingleRefOneDto singleRef;

}
