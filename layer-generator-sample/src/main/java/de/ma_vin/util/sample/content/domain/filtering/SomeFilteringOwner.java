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
 * Generated domain class of SomeFilteringOwner
 */
@BaseDomain("de.ma_vin.util.sample.content.domain")
@Data
@EqualsAndHashCode(exclude = {"filterAs", "filterBs", "filterCs", "filterDaoAs", "filterDaoBs", "filterDaoCs"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"filterAs", "filterBs", "filterCs", "filterDaoAs", "filterDaoBs", "filterDaoCs"})
public class SomeFilteringOwner implements IIdentifiable {

	public static final String ID_PREFIX = "F1";

	@Setter(AccessLevel.PROTECTED)
	private Collection<Filtered> filterAs = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<Filtered> filterBs = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<Filtered> filterCs = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<FilteredOnlyDaoField> filterDaoAs = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<FilteredOnlyDaoField> filterDaoBs = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<FilteredOnlyDaoField> filterDaoCs = new HashSet<>();

	/**
	 * Identification of SomeFilteringOwner
	 */
	private String identification;

	/**
	 * Adds a Filtered
	 * 
	 * @param filterA Filtered to add
	 */
	public boolean addFilterAs(Filtered filterA) {
		return filterAs.add(filterA);
	}

	/**
	 * Adds a Filtered
	 * 
	 * @param filterB Filtered to add
	 */
	public boolean addFilterBs(Filtered filterB) {
		return filterBs.add(filterB);
	}

	/**
	 * Adds a Filtered
	 * 
	 * @param filterC Filtered to add
	 */
	public boolean addFilterCs(Filtered filterC) {
		return filterCs.add(filterC);
	}

	/**
	 * Adds a FilteredOnlyDaoField
	 * 
	 * @param filterDaoA FilteredOnlyDaoField to add
	 */
	public boolean addFilterDaoAs(FilteredOnlyDaoField filterDaoA) {
		return filterDaoAs.add(filterDaoA);
	}

	/**
	 * Adds a FilteredOnlyDaoField
	 * 
	 * @param filterDaoB FilteredOnlyDaoField to add
	 */
	public boolean addFilterDaoBs(FilteredOnlyDaoField filterDaoB) {
		return filterDaoBs.add(filterDaoB);
	}

	/**
	 * Adds a FilteredOnlyDaoField
	 * 
	 * @param filterDaoC FilteredOnlyDaoField to add
	 */
	public boolean addFilterDaoCs(FilteredOnlyDaoField filterDaoC) {
		return filterDaoCs.add(filterDaoC);
	}

	/**
	 * Removes a Filtered
	 * 
	 * @param filterA Filtered to remove
	 */
	public boolean removeFilterAs(Filtered filterA) {
		return filterAs.remove(filterA);
	}

	/**
	 * Removes a Filtered
	 * 
	 * @param filterB Filtered to remove
	 */
	public boolean removeFilterBs(Filtered filterB) {
		return filterBs.remove(filterB);
	}

	/**
	 * Removes a Filtered
	 * 
	 * @param filterC Filtered to remove
	 */
	public boolean removeFilterCs(Filtered filterC) {
		return filterCs.remove(filterC);
	}

	/**
	 * Removes a FilteredOnlyDaoField
	 * 
	 * @param filterDaoA FilteredOnlyDaoField to remove
	 */
	public boolean removeFilterDaoAs(FilteredOnlyDaoField filterDaoA) {
		return filterDaoAs.remove(filterDaoA);
	}

	/**
	 * Removes a FilteredOnlyDaoField
	 * 
	 * @param filterDaoB FilteredOnlyDaoField to remove
	 */
	public boolean removeFilterDaoBs(FilteredOnlyDaoField filterDaoB) {
		return filterDaoBs.remove(filterDaoB);
	}

	/**
	 * Removes a FilteredOnlyDaoField
	 * 
	 * @param filterDaoC FilteredOnlyDaoField to remove
	 */
	public boolean removeFilterDaoCs(FilteredOnlyDaoField filterDaoC) {
		return filterDaoCs.remove(filterDaoC);
	}

}
