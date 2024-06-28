package com.github.ma_vin.util.layer_generator.sample.content.domain;

import com.github.ma_vin.util.layer_generator.sample.content.domain.domain.OnlyDomain;
import com.github.ma_vin.util.layer_generator.sample.content.domain.domain.dao.DomainAndDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.domain.dto.DomainAndDto;
import com.github.ma_vin.util.layer_generator.sample.content.domain.filtering.Filtered;
import com.github.ma_vin.util.layer_generator.sample.content.domain.filtering.FilteredNotOwner;
import com.github.ma_vin.util.layer_generator.sample.content.domain.filtering.FilteredOnlyDaoField;
import com.github.ma_vin.util.layer_generator.sample.content.domain.filtering.FilteredOnlyDaoFieldNotOwner;
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
import com.github.ma_vin.util.layer_generator.sample.extending.ExtendedMultiRefTwoParents;

public class DomainObjectFactory {

	private DomainObjectFactory() {
	}

	public static DomainAndDao createDomainAndDao() {
		return new DomainAndDao();
	}

	public static DomainAndDto createDomainAndDto() {
		return new DomainAndDto();
	}

	public static ExtendingClass createExtendingClass() {
		return new ExtendingClass();
	}

	public static Filtered createFiltered() {
		return new Filtered();
	}

	public static FilteredNotOwner createFilteredNotOwner() {
		return new FilteredNotOwner();
	}

	public static FilteredOnlyDaoField createFilteredOnlyDaoField() {
		return new FilteredOnlyDaoField();
	}

	public static FilteredOnlyDaoFieldNotOwner createFilteredOnlyDaoFieldNotOwner() {
		return new FilteredOnlyDaoFieldNotOwner();
	}

	public static MultiRefIndirectParent createMultiRefIndirectParent() {
		return new MultiRefIndirectParent();
	}

	public static MultiRefIndirectSelfReference createMultiRefIndirectSelfReference() {
		return new MultiRefIndirectSelfReference();
	}

	public static MultiRefOneParent createMultiRefOneParent() {
		return new MultiRefOneParent();
	}

	public static MultiRefOtherIndirectParent createMultiRefOtherIndirectParent() {
		return new MultiRefOtherIndirectParent();
	}

	public static MultiRefTwoParents createMultiRefTwoParents() {
		return new ExtendedMultiRefTwoParents();
	}

	public static OnlyDomain createOnlyDomain() {
		return new OnlyDomain();
	}

	public static Root createRoot() {
		return new Root();
	}

	public static RootExt createRootExt() {
		return new RootExt();
	}

	public static SingleRefIndirectParent createSingleRefIndirectParent() {
		return new SingleRefIndirectParent();
	}

	public static SingleRefOneParent createSingleRefOneParent() {
		return new SingleRefOneParent();
	}

	public static SingleRefOtherIndirectParent createSingleRefOtherIndirectParent() {
		return new SingleRefOtherIndirectParent();
	}

	public static SingleRefTwoParents createSingleRefTwoParents() {
		return new SingleRefTwoParents();
	}

	public static SomeDifferentFilteringNotOwner createSomeDifferentFilteringNotOwner() {
		return new SomeDifferentFilteringNotOwner();
	}

	public static SomeFilteringOwner createSomeFilteringOwner() {
		return new SomeFilteringOwner();
	}

}
