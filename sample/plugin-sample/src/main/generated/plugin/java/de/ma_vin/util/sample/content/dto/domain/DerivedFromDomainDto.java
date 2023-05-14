package de.ma_vin.util.sample.content.dto.domain;

import de.ma_vin.util.layer.generator.annotations.model.BaseDto;
import de.ma_vin.util.sample.content.dto.ITransportable;
import de.ma_vin.util.sample.content.dto.single.SingleRefTwoParentsDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated dto class of DerivedFromDomain
 * <br>
 * Derived from SingleRefOneParent
 */
@BaseDto("de.ma_vin.util.sample.content.dto")
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
