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
 * Generated domain class of SourceEntityManyToMany
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.reference.content.domain")
@Data
@EqualsAndHashCode(exclude = {"manyToManyRef"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"manyToManyRef"})
public class SourceEntityManyToMany implements IIdentifiable {

	public static final String ID_PREFIX = "S3_1";

	/**
	 * Identification of SourceEntityManyToMany
	 */
	private String identification;

	/**
	 * a m:n reference
	 */
	@Setter(AccessLevel.PROTECTED)
	private Collection<TargetEntityManyToMany> manyToManyRef = new HashSet<>();

	/**
	 * Adds a TargetEntityManyToMany to manyToManyRef
	 * 
	 * @param targetEntityManyToMany TargetEntityManyToMany to add
	 */
	public boolean addManyToManyRef(TargetEntityManyToMany targetEntityManyToMany) {
		return manyToManyRef.add(targetEntityManyToMany);
	}

	/**
	 * Removes a TargetEntityManyToMany from manyToManyRef
	 * 
	 * @param targetEntityManyToMany TargetEntityManyToMany to remove
	 */
	public boolean removeManyToManyRef(TargetEntityManyToMany targetEntityManyToMany) {
		return manyToManyRef.remove(targetEntityManyToMany);
	}

}
