package com.github.ma_vin.util.layer_generator.sample.reference.content.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import java.util.Collection;
import java.util.HashSet;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Generated domain class of SourceEntityFilterNotAtTarget
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.reference.content.domain")
@Data
@EqualsAndHashCode(exclude = {"oneToManyFilterA", "oneToManyFilterB"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"oneToManyFilterA", "oneToManyFilterB"})
public class SourceEntityFilterNotAtTarget implements IIdentifiable {

	public static final String ID_PREFIX = "S5";

	/**
	 * Identification of SourceEntityFilterNotAtTarget
	 */
	private String identification;

	/**
	 * a 1:n reference to entities with enum value A, but the property is stored at connection table
	 */
	@Setter(AccessLevel.PROTECTED)
	private Collection<TargetEntityFilterNotAtTarget> oneToManyFilterA = new HashSet<>();

	/**
	 * a 1:n reference to entities with enum value B, but the property is stored at connection table
	 */
	@Setter(AccessLevel.PROTECTED)
	private Collection<TargetEntityFilterNotAtTarget> oneToManyFilterB = new HashSet<>();

	/**
	 * Adds a TargetEntityFilterNotAtTarget to oneToManyFilterA
	 * 
	 * @param targetEntityFilterNotAtTarget TargetEntityFilterNotAtTarget to add
	 */
	public boolean addOneToManyFilterA(TargetEntityFilterNotAtTarget targetEntityFilterNotAtTarget) {
		return oneToManyFilterA.add(targetEntityFilterNotAtTarget);
	}

	/**
	 * Adds a TargetEntityFilterNotAtTarget to oneToManyFilterB
	 * 
	 * @param targetEntityFilterNotAtTarget TargetEntityFilterNotAtTarget to add
	 */
	public boolean addOneToManyFilterB(TargetEntityFilterNotAtTarget targetEntityFilterNotAtTarget) {
		return oneToManyFilterB.add(targetEntityFilterNotAtTarget);
	}

	/**
	 * Removes a TargetEntityFilterNotAtTarget from oneToManyFilterA
	 * 
	 * @param targetEntityFilterNotAtTarget TargetEntityFilterNotAtTarget to remove
	 */
	public boolean removeOneToManyFilterA(TargetEntityFilterNotAtTarget targetEntityFilterNotAtTarget) {
		return oneToManyFilterA.remove(targetEntityFilterNotAtTarget);
	}

	/**
	 * Removes a TargetEntityFilterNotAtTarget from oneToManyFilterB
	 * 
	 * @param targetEntityFilterNotAtTarget TargetEntityFilterNotAtTarget to remove
	 */
	public boolean removeOneToManyFilterB(TargetEntityFilterNotAtTarget targetEntityFilterNotAtTarget) {
		return oneToManyFilterB.remove(targetEntityFilterNotAtTarget);
	}

}
