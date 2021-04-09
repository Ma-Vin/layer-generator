package de.ma_vin.util.sample.content.dto.parent;

import de.ma_vin.util.sample.content.dto.ITransportable;
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
