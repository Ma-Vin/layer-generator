package de.ma_vin.util.sample.content.domain.multi.indirect;

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
 * Generated domain class of MultiRefOtherIndirectParent
 */
@Data
@EqualsAndHashCode(exclude = {"multiIndirectRefs"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"multiIndirectRefs"})
public class MultiRefOtherIndirectParent implements IIdentifiable {

	public static final String ID_PREFIX = "MI1";

	private String description;

	/**
	 * Identification of MultiRefOtherIndirectParent
	 */
	private String identification;

	@Setter(AccessLevel.PROTECTED)
	private Collection<MultiRefIndirectParent> multiIndirectRefs = new HashSet<>();

	/**
	 * Adds a MultiRefIndirectParent
	 * 
	 * @param multiIndirectRef MultiRefIndirectParent to add
	 */
	public boolean addMultiIndirectRefs(MultiRefIndirectParent multiIndirectRef) {
		return multiIndirectRefs.add(multiIndirectRef);
	}

	/**
	 * Removes a MultiRefIndirectParent
	 * 
	 * @param multiIndirectRef MultiRefIndirectParent to remove
	 */
	public boolean removeMultiIndirectRefs(MultiRefIndirectParent multiIndirectRef) {
		return multiIndirectRefs.remove(multiIndirectRef);
	}

}
