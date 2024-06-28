package com.github.ma_vin.util.layer_generator.sample.content.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import com.github.ma_vin.util.layer_generator.sample.content.domain.filtering.SomeDifferentFilteringNotOwner;
import com.github.ma_vin.util.layer_generator.sample.content.domain.filtering.SomeFilteringOwner;
import com.github.ma_vin.util.layer_generator.sample.content.domain.multi.MultiRefOneParent;
import com.github.ma_vin.util.layer_generator.sample.content.domain.multi.MultiRefTwoParents;
import com.github.ma_vin.util.layer_generator.sample.content.domain.multi.indirect.MultiRefIndirectParent;
import com.github.ma_vin.util.layer_generator.sample.content.domain.multi.indirect.MultiRefIndirectSelfReference;
import com.github.ma_vin.util.layer_generator.sample.content.domain.multi.indirect.MultiRefOtherIndirectParent;
import com.github.ma_vin.util.layer_generator.sample.content.domain.parent.ExtendingClass;
import com.github.ma_vin.util.layer_generator.sample.content.domain.single.SingleRefOneParent;
import com.github.ma_vin.util.layer_generator.sample.content.domain.single.SingleRefTwoParents;
import com.github.ma_vin.util.layer_generator.sample.content.domain.single.indirect.SingleRefIndirectParent;
import com.github.ma_vin.util.layer_generator.sample.content.domain.single.indirect.SingleRefOtherIndirectParent;
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
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
@Data
@EqualsAndHashCode(exclude = {"singleRef", "anotherSingleRef", "multiRef", "anotherMultiRef", "singleRefIndirectParent", "singleRefIndirectOtherParent", "multiRefIndirectParent", "multiRefIndirectOtherParent", "multiRefIndirectSelfReference", "extending", "filtering", "nonOwnerFiltering", "ext"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"singleRef", "anotherSingleRef", "multiRef", "anotherMultiRef", "singleRefIndirectParent", "singleRefIndirectOtherParent", "multiRefIndirectParent", "multiRefIndirectOtherParent", "multiRefIndirectSelfReference", "extending", "filtering", "nonOwnerFiltering", "ext"})
public class Root implements IIdentifiable {

	public static final String ID_PREFIX = "R";

	@Setter(AccessLevel.PROTECTED)
	private Collection<MultiRefTwoParents> anotherMultiRef = new HashSet<>();

	private SingleRefTwoParents anotherSingleRef;

	private String description;

	private RootExt ext;

	@Setter(AccessLevel.PROTECTED)
	private Collection<ExtendingClass> extending = new HashSet<>();

	private SomeFilteringOwner filtering;

	/**
	 * Identification of Root
	 */
	private String identification;

	@Setter(AccessLevel.PROTECTED)
	private Collection<MultiRefOneParent> multiRef = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<MultiRefOtherIndirectParent> multiRefIndirectOtherParent = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<MultiRefIndirectParent> multiRefIndirectParent = new HashSet<>();

	@Setter(AccessLevel.PROTECTED)
	private Collection<MultiRefIndirectSelfReference> multiRefIndirectSelfReference = new HashSet<>();

	private SomeDifferentFilteringNotOwner nonOwnerFiltering;

	private String rootName;

	private SingleRefOneParent singleRef;

	private SingleRefOtherIndirectParent singleRefIndirectOtherParent;

	private SingleRefIndirectParent singleRefIndirectParent;

	/**
	 * Adds a MultiRefTwoParents to anotherMultiRef
	 * 
	 * @param multiRefTwoParents MultiRefTwoParents to add
	 */
	public boolean addAnotherMultiRef(MultiRefTwoParents multiRefTwoParents) {
		return anotherMultiRef.add(multiRefTwoParents);
	}

	/**
	 * Adds an ExtendingClass to extending
	 * 
	 * @param extendingClass ExtendingClass to add
	 */
	public boolean addExtending(ExtendingClass extendingClass) {
		return extending.add(extendingClass);
	}

	/**
	 * Adds a MultiRefOneParent to multiRef
	 * 
	 * @param multiRefOneParent MultiRefOneParent to add
	 */
	public boolean addMultiRef(MultiRefOneParent multiRefOneParent) {
		return multiRef.add(multiRefOneParent);
	}

	/**
	 * Adds a MultiRefOtherIndirectParent to multiRefIndirectOtherParent
	 * 
	 * @param multiRefOtherIndirectParent MultiRefOtherIndirectParent to add
	 */
	public boolean addMultiRefIndirectOtherParent(MultiRefOtherIndirectParent multiRefOtherIndirectParent) {
		return multiRefIndirectOtherParent.add(multiRefOtherIndirectParent);
	}

	/**
	 * Adds a MultiRefIndirectParent to multiRefIndirectParent
	 * 
	 * @param multiRefIndirectParent MultiRefIndirectParent to add
	 */
	public boolean addMultiRefIndirectParent(MultiRefIndirectParent multiRefIndirectParent) {
		return this.multiRefIndirectParent.add(multiRefIndirectParent);
	}

	/**
	 * Adds a MultiRefIndirectSelfReference to multiRefIndirectSelfReference
	 * 
	 * @param multiRefIndirectSelfReference MultiRefIndirectSelfReference to add
	 */
	public boolean addMultiRefIndirectSelfReference(MultiRefIndirectSelfReference multiRefIndirectSelfReference) {
		return this.multiRefIndirectSelfReference.add(multiRefIndirectSelfReference);
	}

	/**
	 * Removes a MultiRefTwoParents from anotherMultiRef
	 * 
	 * @param multiRefTwoParents MultiRefTwoParents to remove
	 */
	public boolean removeAnotherMultiRef(MultiRefTwoParents multiRefTwoParents) {
		return anotherMultiRef.remove(multiRefTwoParents);
	}

	/**
	 * Removes an ExtendingClass from extending
	 * 
	 * @param extendingClass ExtendingClass to remove
	 */
	public boolean removeExtending(ExtendingClass extendingClass) {
		return extending.remove(extendingClass);
	}

	/**
	 * Removes a MultiRefOneParent from multiRef
	 * 
	 * @param multiRefOneParent MultiRefOneParent to remove
	 */
	public boolean removeMultiRef(MultiRefOneParent multiRefOneParent) {
		return multiRef.remove(multiRefOneParent);
	}

	/**
	 * Removes a MultiRefOtherIndirectParent from multiRefIndirectOtherParent
	 * 
	 * @param multiRefOtherIndirectParent MultiRefOtherIndirectParent to remove
	 */
	public boolean removeMultiRefIndirectOtherParent(MultiRefOtherIndirectParent multiRefOtherIndirectParent) {
		return multiRefIndirectOtherParent.remove(multiRefOtherIndirectParent);
	}

	/**
	 * Removes a MultiRefIndirectParent from multiRefIndirectParent
	 * 
	 * @param multiRefIndirectParent MultiRefIndirectParent to remove
	 */
	public boolean removeMultiRefIndirectParent(MultiRefIndirectParent multiRefIndirectParent) {
		return this.multiRefIndirectParent.remove(multiRefIndirectParent);
	}

	/**
	 * Removes a MultiRefIndirectSelfReference from multiRefIndirectSelfReference
	 * 
	 * @param multiRefIndirectSelfReference MultiRefIndirectSelfReference to remove
	 */
	public boolean removeMultiRefIndirectSelfReference(MultiRefIndirectSelfReference multiRefIndirectSelfReference) {
		return this.multiRefIndirectSelfReference.remove(multiRefIndirectSelfReference);
	}

}
