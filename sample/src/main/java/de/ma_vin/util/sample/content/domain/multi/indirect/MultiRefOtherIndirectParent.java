package de.ma_vin.util.sample.content.domain.multi.indirect;

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
 * Generated domain class of MultiRefOtherIndirectParent
 */
@BaseDomain("de.ma_vin.util.sample.content.domain")
@Data
@EqualsAndHashCode(exclude = {"multiIndirectRef"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"multiIndirectRef"})
public class MultiRefOtherIndirectParent implements IIdentifiable {

	public static final String ID_PREFIX = "MI1";

	private String description;

	/**
	 * Identification of MultiRefOtherIndirectParent
	 */
	private String identification;

	@Setter(AccessLevel.PROTECTED)
	private Collection<MultiRefIndirectParent> multiIndirectRef = new HashSet<>();

	/**
	 * Adds a MultiRefIndirectParent to multiIndirectRef
	 * 
	 * @param multiRefIndirectParent MultiRefIndirectParent to add
	 */
	public boolean addMultiIndirectRef(MultiRefIndirectParent multiRefIndirectParent) {
		return multiIndirectRef.add(multiRefIndirectParent);
	}

	/**
	 * Removes a MultiRefIndirectParent from multiIndirectRef
	 * 
	 * @param multiRefIndirectParent MultiRefIndirectParent to remove
	 */
	public boolean removeMultiIndirectRef(MultiRefIndirectParent multiRefIndirectParent) {
		return multiIndirectRef.remove(multiRefIndirectParent);
	}

}
