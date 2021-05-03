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
@EqualsAndHashCode(exclude = {"filterA", "filterB", "filterC", "filterDaoA", "filterDaoB", "filterDaoC", "filterNotOwnerA", "filterNotOwnerB", "filterNotOwnerC", "filterDaoNotOwnerA", "filterDaoNotOwnerB", "filterDaoNotOwnerC"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"filterA", "filterB", "filterC", "filterDaoA", "filterDaoB", "filterDaoC", "filterNotOwnerA", "filterNotOwnerB", "filterNotOwnerC", "filterDaoNotOwnerA", "filterDaoNotOwnerB", "filterDaoNotOwnerC"})
public class SomeFilteringOwner implements IIdentifiable {

	public static final String ID_PREFIX = "F1";

	@Setter(AccessLevel.PROTECTED)
	private Collection<Filtered> filterA = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<Filtered> filterB = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<Filtered> filterC = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<FilteredOnlyDaoField> filterDaoA = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<FilteredOnlyDaoField> filterDaoB = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<FilteredOnlyDaoField> filterDaoC = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<FilteredOnlyDaoFieldNotOwner> filterDaoNotOwnerA = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<FilteredOnlyDaoFieldNotOwner> filterDaoNotOwnerB = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<FilteredOnlyDaoFieldNotOwner> filterDaoNotOwnerC = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<FilteredNotOwner> filterNotOwnerA = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<FilteredNotOwner> filterNotOwnerB = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<FilteredNotOwner> filterNotOwnerC = new HashSet<>();

	/**
	 * Identification of SomeFilteringOwner
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
	 * Adds a FilteredOnlyDaoField to filterDaoA
	 * 
	 * @param filteredOnlyDaoField FilteredOnlyDaoField to add
	 */
	public boolean addFilterDaoA(FilteredOnlyDaoField filteredOnlyDaoField) {
		return filterDaoA.add(filteredOnlyDaoField);
	}

	/**
	 * Adds a FilteredOnlyDaoField to filterDaoB
	 * 
	 * @param filteredOnlyDaoField FilteredOnlyDaoField to add
	 */
	public boolean addFilterDaoB(FilteredOnlyDaoField filteredOnlyDaoField) {
		return filterDaoB.add(filteredOnlyDaoField);
	}

	/**
	 * Adds a FilteredOnlyDaoField to filterDaoC
	 * 
	 * @param filteredOnlyDaoField FilteredOnlyDaoField to add
	 */
	public boolean addFilterDaoC(FilteredOnlyDaoField filteredOnlyDaoField) {
		return filterDaoC.add(filteredOnlyDaoField);
	}

	/**
	 * Adds a FilteredOnlyDaoFieldNotOwner to filterDaoNotOwnerA
	 * 
	 * @param filteredOnlyDaoFieldNotOwner FilteredOnlyDaoFieldNotOwner to add
	 */
	public boolean addFilterDaoNotOwnerA(FilteredOnlyDaoFieldNotOwner filteredOnlyDaoFieldNotOwner) {
		return filterDaoNotOwnerA.add(filteredOnlyDaoFieldNotOwner);
	}

	/**
	 * Adds a FilteredOnlyDaoFieldNotOwner to filterDaoNotOwnerB
	 * 
	 * @param filteredOnlyDaoFieldNotOwner FilteredOnlyDaoFieldNotOwner to add
	 */
	public boolean addFilterDaoNotOwnerB(FilteredOnlyDaoFieldNotOwner filteredOnlyDaoFieldNotOwner) {
		return filterDaoNotOwnerB.add(filteredOnlyDaoFieldNotOwner);
	}

	/**
	 * Adds a FilteredOnlyDaoFieldNotOwner to filterDaoNotOwnerC
	 * 
	 * @param filteredOnlyDaoFieldNotOwner FilteredOnlyDaoFieldNotOwner to add
	 */
	public boolean addFilterDaoNotOwnerC(FilteredOnlyDaoFieldNotOwner filteredOnlyDaoFieldNotOwner) {
		return filterDaoNotOwnerC.add(filteredOnlyDaoFieldNotOwner);
	}

	/**
	 * Adds a FilteredNotOwner to filterNotOwnerA
	 * 
	 * @param filteredNotOwner FilteredNotOwner to add
	 */
	public boolean addFilterNotOwnerA(FilteredNotOwner filteredNotOwner) {
		return filterNotOwnerA.add(filteredNotOwner);
	}

	/**
	 * Adds a FilteredNotOwner to filterNotOwnerB
	 * 
	 * @param filteredNotOwner FilteredNotOwner to add
	 */
	public boolean addFilterNotOwnerB(FilteredNotOwner filteredNotOwner) {
		return filterNotOwnerB.add(filteredNotOwner);
	}

	/**
	 * Adds a FilteredNotOwner to filterNotOwnerC
	 * 
	 * @param filteredNotOwner FilteredNotOwner to add
	 */
	public boolean addFilterNotOwnerC(FilteredNotOwner filteredNotOwner) {
		return filterNotOwnerC.add(filteredNotOwner);
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

	/**
	 * Removes a FilteredOnlyDaoField from filterDaoA
	 * 
	 * @param filteredOnlyDaoField FilteredOnlyDaoField to remove
	 */
	public boolean removeFilterDaoA(FilteredOnlyDaoField filteredOnlyDaoField) {
		return filterDaoA.remove(filteredOnlyDaoField);
	}

	/**
	 * Removes a FilteredOnlyDaoField from filterDaoB
	 * 
	 * @param filteredOnlyDaoField FilteredOnlyDaoField to remove
	 */
	public boolean removeFilterDaoB(FilteredOnlyDaoField filteredOnlyDaoField) {
		return filterDaoB.remove(filteredOnlyDaoField);
	}

	/**
	 * Removes a FilteredOnlyDaoField from filterDaoC
	 * 
	 * @param filteredOnlyDaoField FilteredOnlyDaoField to remove
	 */
	public boolean removeFilterDaoC(FilteredOnlyDaoField filteredOnlyDaoField) {
		return filterDaoC.remove(filteredOnlyDaoField);
	}

	/**
	 * Removes a FilteredOnlyDaoFieldNotOwner from filterDaoNotOwnerA
	 * 
	 * @param filteredOnlyDaoFieldNotOwner FilteredOnlyDaoFieldNotOwner to remove
	 */
	public boolean removeFilterDaoNotOwnerA(FilteredOnlyDaoFieldNotOwner filteredOnlyDaoFieldNotOwner) {
		return filterDaoNotOwnerA.remove(filteredOnlyDaoFieldNotOwner);
	}

	/**
	 * Removes a FilteredOnlyDaoFieldNotOwner from filterDaoNotOwnerB
	 * 
	 * @param filteredOnlyDaoFieldNotOwner FilteredOnlyDaoFieldNotOwner to remove
	 */
	public boolean removeFilterDaoNotOwnerB(FilteredOnlyDaoFieldNotOwner filteredOnlyDaoFieldNotOwner) {
		return filterDaoNotOwnerB.remove(filteredOnlyDaoFieldNotOwner);
	}

	/**
	 * Removes a FilteredOnlyDaoFieldNotOwner from filterDaoNotOwnerC
	 * 
	 * @param filteredOnlyDaoFieldNotOwner FilteredOnlyDaoFieldNotOwner to remove
	 */
	public boolean removeFilterDaoNotOwnerC(FilteredOnlyDaoFieldNotOwner filteredOnlyDaoFieldNotOwner) {
		return filterDaoNotOwnerC.remove(filteredOnlyDaoFieldNotOwner);
	}

	/**
	 * Removes a FilteredNotOwner from filterNotOwnerA
	 * 
	 * @param filteredNotOwner FilteredNotOwner to remove
	 */
	public boolean removeFilterNotOwnerA(FilteredNotOwner filteredNotOwner) {
		return filterNotOwnerA.remove(filteredNotOwner);
	}

	/**
	 * Removes a FilteredNotOwner from filterNotOwnerB
	 * 
	 * @param filteredNotOwner FilteredNotOwner to remove
	 */
	public boolean removeFilterNotOwnerB(FilteredNotOwner filteredNotOwner) {
		return filterNotOwnerB.remove(filteredNotOwner);
	}

	/**
	 * Removes a FilteredNotOwner from filterNotOwnerC
	 * 
	 * @param filteredNotOwner FilteredNotOwner to remove
	 */
	public boolean removeFilterNotOwnerC(FilteredNotOwner filteredNotOwner) {
		return filterNotOwnerC.remove(filteredNotOwner);
	}

}
