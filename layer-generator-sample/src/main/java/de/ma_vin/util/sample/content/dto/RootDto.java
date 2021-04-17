package de.ma_vin.util.sample.content.dto;

import de.ma_vin.util.layer.generator.annotations.model.BaseDto;
import de.ma_vin.util.sample.content.dto.filtering.SomeFilteringOwnerDto;
import de.ma_vin.util.sample.content.dto.single.SingleRefOneParentDto;
import de.ma_vin.util.sample.content.dto.single.SingleRefTwoParentsDto;
import de.ma_vin.util.sample.content.dto.single.indirect.SingleRefIndirectParentDto;
import de.ma_vin.util.sample.content.dto.single.indirect.SingleRefOtherIndirectParentDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated dto class of Root
 */
@BaseDto("de.ma_vin.util.sample.content.dto")
@Data
@EqualsAndHashCode(exclude = {"singleRef", "anotherSingleRef", "singleRefIndirectParent", "singleRefIndirectOtherParent", "filtering", "ext"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"singleRef", "anotherSingleRef", "singleRefIndirectParent", "singleRefIndirectOtherParent", "filtering", "ext"})
public class RootDto implements ITransportable {

	private SingleRefTwoParentsDto anotherSingleRef;

	private String description;

	private RootExtDto ext;

	private SomeFilteringOwnerDto filtering;

	/**
	 * Identification of Root
	 */
	private String identification;

	private String rootName;

	private SingleRefOneParentDto singleRef;

	private SingleRefOtherIndirectParentDto singleRefIndirectOtherParent;

	private SingleRefIndirectParentDto singleRefIndirectParent;

}
