package de.ma_vin.util.sample.content.dto.single.indirect;

import de.ma_vin.util.sample.content.dto.ITransportable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated dto class of SingleRefOtherIndirectParent
 */
@Data
@EqualsAndHashCode(exclude = {"singleIndirectRef"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"singleIndirectRef"})
public class SingleRefOtherIndirectParentDto implements ITransportable {

	private String description;

	/**
	 * Identification of SingleRefOtherIndirectParent
	 */
	private String identification;

	private SingleRefIndirectParentDto singleIndirectRef;

}
