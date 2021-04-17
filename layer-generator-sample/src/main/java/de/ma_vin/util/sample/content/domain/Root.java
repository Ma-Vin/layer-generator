package de.ma_vin.util.sample.content.domain;

import de.ma_vin.util.layer.generator.annotations.model.BaseDomain;
import de.ma_vin.util.sample.content.domain.filtering.SomeFilteringOwner;
import de.ma_vin.util.sample.content.domain.multi.MultiRefOneParent;
import de.ma_vin.util.sample.content.domain.multi.MultiRefTwoParents;
import de.ma_vin.util.sample.content.domain.multi.indirect.MultiRefIndirectParent;
import de.ma_vin.util.sample.content.domain.multi.indirect.MultiRefOtherIndirectParent;
import de.ma_vin.util.sample.content.domain.parent.ExtendingClass;
import de.ma_vin.util.sample.content.domain.single.SingleRefOneParent;
import de.ma_vin.util.sample.content.domain.single.SingleRefTwoParents;
import de.ma_vin.util.sample.content.domain.single.indirect.SingleRefIndirectParent;
import de.ma_vin.util.sample.content.domain.single.indirect.SingleRefOtherIndirectParent;
import java.util.Collection;
import java.util.HashSet;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Generated domain class of Root
 */
@BaseDomain("de.ma_vin.util.sample.content.domain")
@Data
@EqualsAndHashCode(exclude = {"singleRef", "anotherSingleRef", "multiRefs", "anotherMultiRefs", "singleRefIndirectParent", "singleRefIndirectOtherParent", "multiRefIndirectParents", "multiRefIndirectOtherParents", "extendings", "filtering", "ext"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"singleRef", "anotherSingleRef", "multiRefs", "anotherMultiRefs", "singleRefIndirectParent", "singleRefIndirectOtherParent", "multiRefIndirectParents", "multiRefIndirectOtherParents", "extendings", "filtering", "ext"})
public class Root implements IIdentifiable {

	public static final String ID_PREFIX = "R";

	@Setter(AccessLevel.PROTECTED)
	private Collection<MultiRefTwoParents> anotherMultiRefs = new HashSet<>();

	private SingleRefTwoParents anotherSingleRef;

	private String description;

	private RootExt ext;

	@Setter(AccessLevel.PROTECTED)
	private Collection<ExtendingClass> extendings = new HashSet<>();

	private SomeFilteringOwner filtering;

	/**
	 * Identification of Root
	 */
	private String identification;

	@Setter(AccessLevel.PROTECTED)
	private Collection<MultiRefOtherIndirectParent> multiRefIndirectOtherParents = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<MultiRefIndirectParent> multiRefIndirectParents = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<MultiRefOneParent> multiRefs = new HashSet<>();

	private String rootName;

	private SingleRefOneParent singleRef;

	private SingleRefOtherIndirectParent singleRefIndirectOtherParent;

	private SingleRefIndirectParent singleRefIndirectParent;

	/**
	 * Adds a MultiRefTwoParents
	 * 
	 * @param anotherMultiRef MultiRefTwoParents to add
	 */
	public boolean addAnotherMultiRefs(MultiRefTwoParents anotherMultiRef) {
		return anotherMultiRefs.add(anotherMultiRef);
	}

	/**
	 * Adds an ExtendingClass
	 * 
	 * @param extending ExtendingClass to add
	 */
	public boolean addExtendings(ExtendingClass extending) {
		return extendings.add(extending);
	}

	/**
	 * Adds a MultiRefOtherIndirectParent
	 * 
	 * @param multiRefIndirectOtherParent MultiRefOtherIndirectParent to add
	 */
	public boolean addMultiRefIndirectOtherParents(MultiRefOtherIndirectParent multiRefIndirectOtherParent) {
		return multiRefIndirectOtherParents.add(multiRefIndirectOtherParent);
	}

	/**
	 * Adds a MultiRefIndirectParent
	 * 
	 * @param multiRefIndirectParent MultiRefIndirectParent to add
	 */
	public boolean addMultiRefIndirectParents(MultiRefIndirectParent multiRefIndirectParent) {
		return multiRefIndirectParents.add(multiRefIndirectParent);
	}

	/**
	 * Adds a MultiRefOneParent
	 * 
	 * @param multiRef MultiRefOneParent to add
	 */
	public boolean addMultiRefs(MultiRefOneParent multiRef) {
		return multiRefs.add(multiRef);
	}

	/**
	 * Removes a MultiRefTwoParents
	 * 
	 * @param anotherMultiRef MultiRefTwoParents to remove
	 */
	public boolean removeAnotherMultiRefs(MultiRefTwoParents anotherMultiRef) {
		return anotherMultiRefs.remove(anotherMultiRef);
	}

	/**
	 * Removes an ExtendingClass
	 * 
	 * @param extending ExtendingClass to remove
	 */
	public boolean removeExtendings(ExtendingClass extending) {
		return extendings.remove(extending);
	}

	/**
	 * Removes a MultiRefOtherIndirectParent
	 * 
	 * @param multiRefIndirectOtherParent MultiRefOtherIndirectParent to remove
	 */
	public boolean removeMultiRefIndirectOtherParents(MultiRefOtherIndirectParent multiRefIndirectOtherParent) {
		return multiRefIndirectOtherParents.remove(multiRefIndirectOtherParent);
	}

	/**
	 * Removes a MultiRefIndirectParent
	 * 
	 * @param multiRefIndirectParent MultiRefIndirectParent to remove
	 */
	public boolean removeMultiRefIndirectParents(MultiRefIndirectParent multiRefIndirectParent) {
		return multiRefIndirectParents.remove(multiRefIndirectParent);
	}

	/**
	 * Removes a MultiRefOneParent
	 * 
	 * @param multiRef MultiRefOneParent to remove
	 */
	public boolean removeMultiRefs(MultiRefOneParent multiRef) {
		return multiRefs.remove(multiRef);
	}

}
