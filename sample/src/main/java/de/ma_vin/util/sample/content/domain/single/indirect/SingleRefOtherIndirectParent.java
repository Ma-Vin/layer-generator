package de.ma_vin.util.sample.content.domain.single.indirect;

import de.ma_vin.util.sample.content.domain.IIdentifiable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated domain class of SingleRefOtherIndirectParent
 */
@Data
@EqualsAndHashCode(exclude = {"singleIndirectRef"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"singleIndirectRef"})
public class SingleRefOtherIndirectParent implements IIdentifiable {

	public static final String ID_PREFIX = "SI1";

	private String description;

	/**
	 * Identification of SingleRefOtherIndirectParent
	 */
	private String identification;

	private SingleRefIndirectParent singleIndirectRef;

}
