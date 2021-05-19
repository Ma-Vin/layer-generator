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
 * Generated domain class of MultiRefIndirectSelfReference
 */
@BaseDomain("de.ma_vin.util.sample.content.domain")
@Data
@EqualsAndHashCode(exclude = {"selfRefs"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"selfRefs"})
public class MultiRefIndirectSelfReference implements IIdentifiable {

	public static final String ID_PREFIX = "MI2";

	/**
	 * Identification of MultiRefIndirectSelfReference
	 */
	private String identification;

	@Setter(AccessLevel.PROTECTED)
	private Collection<MultiRefIndirectSelfReference> selfRefs = new HashSet<>();

	/**
	 * Adds a MultiRefIndirectSelfReference to selfRefs
	 * 
	 * @param multiRefIndirectSelfReference MultiRefIndirectSelfReference to add
	 */
	public boolean addSelfRefs(MultiRefIndirectSelfReference multiRefIndirectSelfReference) {
		return selfRefs.add(multiRefIndirectSelfReference);
	}

	/**
	 * Removes a MultiRefIndirectSelfReference from selfRefs
	 * 
	 * @param multiRefIndirectSelfReference MultiRefIndirectSelfReference to remove
	 */
	public boolean removeSelfRefs(MultiRefIndirectSelfReference multiRefIndirectSelfReference) {
		return selfRefs.remove(multiRefIndirectSelfReference);
	}

}
