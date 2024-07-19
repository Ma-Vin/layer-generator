package com.github.ma_vin.util.layer_generator.sample.reference.content.dto;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated dto class of SourceEntityManyToOne
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.reference.content.dto")
@Data
@EqualsAndHashCode(exclude = {"manyToOneRef"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"manyToOneRef"})
public class SourceEntityManyToOneDto implements ITransportable {

	/**
	 * Identification of SourceEntityManyToOne
	 */
	private String identification;

	/**
	 * a m:1 reference
	 */
	private TargetEntityManyToManyDto manyToOneRef;

}
