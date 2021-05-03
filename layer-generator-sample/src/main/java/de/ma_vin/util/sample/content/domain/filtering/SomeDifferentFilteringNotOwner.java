package de.ma_vin.util.sample.content.domain.filtering;

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
 * Generated domain class of SomeDifferentFilteringNotOwner
 */
@BaseDomain("de.ma_vin.util.sample.content.domain")
@Data
@EqualsAndHashCode(exclude = {"filterA", "filterB", "filterC"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"filterA", "filterB", "filterC"})
public class SomeDifferentFilteringNotOwner implements IIdentifiable {

	public static final String ID_PREFIX = "F6";

	@Setter(AccessLevel.PROTECTED)
	private Collection<Filtered> filterA = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<Filtered> filterB = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<Filtered> filterC = new HashSet<>();

	/**
	 * Identification of SomeDifferentFilteringNotOwner
	 */
	private String identification;

	/**
	 * Adds a Filtered to filterA
	 * 
	 * @param filtered Filtered to add
	 */
	public boolean addFilterA(Filtered filtered) {
		return filterA.add(filtered);
	}

	/**
	 * Adds a Filtered to filterB
	 * 
	 * @param filtered Filtered to add
	 */
	public boolean addFilterB(Filtered filtered) {
		return filterB.add(filtered);
	}

	/**
	 * Adds a Filtered to filterC
	 * 
	 * @param filtered Filtered to add
	 */
	public boolean addFilterC(Filtered filtered) {
		return filterC.add(filtered);
	}

	/**
	 * Removes a Filtered from filterA
	 * 
	 * @param filtered Filtered to remove
	 */
	public boolean removeFilterA(Filtered filtered) {
		return filterA.remove(filtered);
	}

	/**
	 * Removes a Filtered from filterB
	 * 
	 * @param filtered Filtered to remove
	 */
	public boolean removeFilterB(Filtered filtered) {
		return filterB.remove(filtered);
	}

	/**
	 * Removes a Filtered from filterC
	 * 
	 * @param filtered Filtered to remove
	 */
	public boolean removeFilterC(Filtered filtered) {
		return filterC.remove(filtered);
	}

}
