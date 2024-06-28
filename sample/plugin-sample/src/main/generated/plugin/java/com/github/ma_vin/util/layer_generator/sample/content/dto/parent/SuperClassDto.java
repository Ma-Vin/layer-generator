package com.github.ma_vin.util.layer_generator.sample.content.dto.parent;

import com.github.ma_vin.util.layer_generator.sample.content.dto.ITransportable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of SuperClass
 */
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public abstract class SuperClassDto implements ITransportable {

	private String description;

	/**
	 * Identification of SuperClass
	 */
	private String identification;

}
