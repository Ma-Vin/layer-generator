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
 * Generated domain class of SourceEntityOneToMany
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.reference.content.domain")
@Data
@EqualsAndHashCode(exclude = {"oneToManyRef"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"oneToManyRef"})
public class SourceEntityOneToMany implements IIdentifiable {

	public static final String ID_PREFIX = "S2";

	/**
	 * Identification of SourceEntityOneToMany
	 */
	private String identification;

	/**
	 * a 1:n reference
	 */
	@Setter(AccessLevel.PROTECTED)
	private Collection<TargetEntityOneToMany> oneToManyRef = new HashSet<>();

	/**
	 * Adds a TargetEntityOneToMany to oneToManyRef
	 * 
	 * @param targetEntityOneToMany TargetEntityOneToMany to add
	 */
	public boolean addOneToManyRef(TargetEntityOneToMany targetEntityOneToMany) {
		return oneToManyRef.add(targetEntityOneToMany);
	}

	/**
	 * Removes a TargetEntityOneToMany from oneToManyRef
	 * 
	 * @param targetEntityOneToMany TargetEntityOneToMany to remove
	 */
	public boolean removeOneToManyRef(TargetEntityOneToMany targetEntityOneToMany) {
		return oneToManyRef.remove(targetEntityOneToMany);
	}

}
