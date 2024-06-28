package com.github.ma_vin.util.layer_generator.sample.content.dto;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.filtering.SomeDifferentFilteringNotOwnerDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.filtering.SomeFilteringOwnerDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.single.SingleRefOneParentDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.single.SingleRefTwoParentsDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.single.indirect.SingleRefIndirectParentDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.single.indirect.SingleRefOtherIndirectParentDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated dto class of Root
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.content.dto")
@Data
@EqualsAndHashCode(exclude = {"singleRef", "anotherSingleRef", "singleRefIndirectParent", "singleRefIndirectOtherParent", "filtering", "nonOwnerFiltering", "ext"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"singleRef", "anotherSingleRef", "singleRefIndirectParent", "singleRefIndirectOtherParent", "filtering", "nonOwnerFiltering", "ext"})
public class RootDto implements ITransportable {

	private SingleRefTwoParentsDto anotherSingleRef;

	private String description;

	private RootExtDto ext;

	private SomeFilteringOwnerDto filtering;

	/**
	 * Identification of Root
	 */
	private String identification;

	private SomeDifferentFilteringNotOwnerDto nonOwnerFiltering;

	private String rootName;

	private SingleRefOneParentDto singleRef;

	private SingleRefOtherIndirectParentDto singleRefIndirectOtherParent;

	private SingleRefIndirectParentDto singleRefIndirectParent;

}
