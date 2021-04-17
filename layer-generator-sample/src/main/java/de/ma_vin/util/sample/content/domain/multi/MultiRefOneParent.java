package de.ma_vin.util.sample.content.domain.multi;

import de.ma_vin.util.layer.generator.annotations.model.BaseDomain;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
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
@BaseDomain("de.ma_vin.util.sample.content.domain")
@Data
@EqualsAndHashCode(exclude = {"multiRefs"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"multiRefs"})
public class MultiRefOneParent implements IIdentifiable {

	public static final String ID_PREFIX = "M1";

	private String description;

	/**
	 * Identification of MultiRefOneParent
	 */
	private String identification;

	@Setter(AccessLevel.PROTECTED)
	private Collection<MultiRefTwoParents> multiRefs = new HashSet<>();

	/**
	 * Adds a MultiRefTwoParents
	 * 
	 * @param multiRef MultiRefTwoParents to add
	 */
	public boolean addMultiRefs(MultiRefTwoParents multiRef) {
		return multiRefs.add(multiRef);
	}

	/**
	 * Removes a MultiRefTwoParents
	 * 
	 * @param multiRef MultiRefTwoParents to remove
	 */
	public boolean removeMultiRefs(MultiRefTwoParents multiRef) {
		return multiRefs.remove(multiRef);
	}

}
