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
 * Generated domain class of SourceEntityFilter
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.reference.content.domain")
@Data
@EqualsAndHashCode(exclude = {"oneToManyFilterA", "oneToManyFilterB"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"oneToManyFilterA", "oneToManyFilterB"})
public class SourceEntityFilter implements IIdentifiable {

	public static final String ID_PREFIX = "S4";

	/**
	 * Identification of SourceEntityFilter
	 */
	private String identification;

	/**
	 * a 1:n reference to entities with enum value A
	 */
	@Setter(AccessLevel.PROTECTED)
	private Collection<TargetEntityFilter> oneToManyFilterA = new HashSet<>();

	/**
	 * a 1:n reference to entities with enum value B
	 */
	@Setter(AccessLevel.PROTECTED)
	private Collection<TargetEntityFilter> oneToManyFilterB = new HashSet<>();

	/**
	 * Adds a TargetEntityFilter to oneToManyFilterA
	 * 
	 * @param targetEntityFilter TargetEntityFilter to add
	 */
	public boolean addOneToManyFilterA(TargetEntityFilter targetEntityFilter) {
		return oneToManyFilterA.add(targetEntityFilter);
	}

	/**
	 * Adds a TargetEntityFilter to oneToManyFilterB
	 * 
	 * @param targetEntityFilter TargetEntityFilter to add
	 */
	public boolean addOneToManyFilterB(TargetEntityFilter targetEntityFilter) {
		return oneToManyFilterB.add(targetEntityFilter);
	}

	/**
	 * Removes a TargetEntityFilter from oneToManyFilterA
	 * 
	 * @param targetEntityFilter TargetEntityFilter to remove
	 */
	public boolean removeOneToManyFilterA(TargetEntityFilter targetEntityFilter) {
		return oneToManyFilterA.remove(targetEntityFilter);
	}

	/**
	 * Removes a TargetEntityFilter from oneToManyFilterB
	 * 
	 * @param targetEntityFilter TargetEntityFilter to remove
	 */
	public boolean removeOneToManyFilterB(TargetEntityFilter targetEntityFilter) {
		return oneToManyFilterB.remove(targetEntityFilter);
	}

}
