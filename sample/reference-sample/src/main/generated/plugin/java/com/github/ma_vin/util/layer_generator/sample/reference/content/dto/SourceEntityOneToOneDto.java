package com.github.ma_vin.util.layer_generator.sample.reference.content.dto;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated dto class of SourceEntityOneToOne
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.reference.content.dto")
@Data
@EqualsAndHashCode(exclude = {"oneToOneRef"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"oneToOneRef"})
public class SourceEntityOneToOneDto implements ITransportable {

	/**
	 * Identification of SourceEntityOneToOne
	 */
	private String identification;

	/**
	 * a 1:1 reference
	 */
	private TargetEntityOneToOneDto oneToOneRef;

}
