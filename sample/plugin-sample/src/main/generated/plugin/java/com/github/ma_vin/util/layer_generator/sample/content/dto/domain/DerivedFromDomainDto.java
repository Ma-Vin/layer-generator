package com.github.ma_vin.util.layer_generator.sample.content.dto.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.ITransportable;
import com.github.ma_vin.util.layer_generator.sample.content.dto.single.SingleRefTwoParentsDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated dto class of DerivedFromDomain
 * <br>
 * Derived from SingleRefOneParent
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.content.dto")
@Data
@EqualsAndHashCode(exclude = {"singleRef"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"singleRef"})
public class DerivedFromDomainDto implements ITransportable {

	private String description;

	/**
	 * Identification of DerivedFromDomain
	 */
	private String identification;

	/**
	 * Instance with two parents
	 */
	private SingleRefTwoParentsDto singleRef;

}
