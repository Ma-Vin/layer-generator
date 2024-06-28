package com.github.ma_vin.util.layer_generator.sample.content.domain.multi;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import java.util.Collection;
import java.util.HashSet;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Generated domain class of MultiRefOneParent
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
@Data
@EqualsAndHashCode(exclude = {"multiRef"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"multiRef"})
public class MultiRefOneParent implements IIdentifiable {

	public static final String ID_PREFIX = "M1";

	private String description;

	/**
	 * Identification of MultiRefOneParent
	 */
	private String identification;

	/**
	 * Instances with two parents
	 */
	@Setter(AccessLevel.PROTECTED)
	private Collection<MultiRefTwoParents> multiRef = new HashSet<>();

	/**
	 * Adds a MultiRefTwoParents to multiRef
	 * 
	 * @param multiRefTwoParents MultiRefTwoParents to add
	 */
	public boolean addMultiRef(MultiRefTwoParents multiRefTwoParents) {
		return multiRef.add(multiRefTwoParents);
	}

	/**
	 * Removes a MultiRefTwoParents from multiRef
	 * 
	 * @param multiRefTwoParents MultiRefTwoParents to remove
	 */
	public boolean removeMultiRef(MultiRefTwoParents multiRefTwoParents) {
		return multiRef.remove(multiRefTwoParents);
	}

}
