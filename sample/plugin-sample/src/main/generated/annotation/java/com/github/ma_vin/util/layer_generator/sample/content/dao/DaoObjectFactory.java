package com.github.ma_vin.util.layer_generator.sample.content.dao;

import com.github.ma_vin.util.layer_generator.sample.content.dao.dao.OnlyDaoDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.domain.dao.DomainAndDaoDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.filtering.FilteredDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.filtering.FilteredNotOwnerDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.filtering.FilteredOnlyDaoFieldDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.filtering.FilteredOnlyDaoFieldNotOwnerDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.filtering.SomeDifferentFilteringNotOwnerDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.filtering.SomeDifferentFilteringNotOwnerToFilteredDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.filtering.SomeFilteringOwnerDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.filtering.SomeFilteringOwnerToFilteredNotOwnerDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.filtering.SomeFilteringOwnerToFilteredOnlyDaoFieldNotOwnerDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.multi.MultiRefOneParentDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.multi.MultiRefTwoParentsDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.multi.indirect.MultiRefIndirectParentDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.multi.indirect.MultiRefIndirectSelfReferenceDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.multi.indirect.MultiRefIndirectSelfReferenceToMultiRefIndirectSelfReferenceDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.multi.indirect.MultiRefOtherIndirectParentDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.multi.indirect.MultiRefOtherIndirectParentToMultiRefIndirectParentDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.parent.ExtendingClassDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.single.SingleRefOneParentDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.single.SingleRefTwoParentsDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.single.indirect.SingleRefIndirectParentDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.single.indirect.SingleRefOtherIndirectParentDao;
import com.github.ma_vin.util.layer_generator.sample.extending.ExtendedSingleRefOneParentDao;

public class DaoObjectFactory {

	private DaoObjectFactory() {
	}

	public static DomainAndDaoDao createDomainAndDaoDao() {
		return new DomainAndDaoDao();
	}

	public static ExtendingClassDao createExtendingClassDao() {
		return new ExtendingClassDao();
	}

	public static FilteredDao createFilteredDao() {
		return new FilteredDao();
	}

	public static FilteredNotOwnerDao createFilteredNotOwnerDao() {
		return new FilteredNotOwnerDao();
	}

	public static FilteredOnlyDaoFieldDao createFilteredOnlyDaoFieldDao() {
		return new FilteredOnlyDaoFieldDao();
	}

	public static FilteredOnlyDaoFieldNotOwnerDao createFilteredOnlyDaoFieldNotOwnerDao() {
		return new FilteredOnlyDaoFieldNotOwnerDao();
	}

	public static MultiRefIndirectParentDao createMultiRefIndirectParentDao() {
		return new MultiRefIndirectParentDao();
	}

	public static MultiRefIndirectSelfReferenceDao createMultiRefIndirectSelfReferenceDao() {
		return new MultiRefIndirectSelfReferenceDao();
	}

	public static MultiRefIndirectSelfReferenceToMultiRefIndirectSelfReferenceDao createMultiRefIndirectSelfReferenceToMultiRefIndirectSelfReferenceDao() {
		return new MultiRefIndirectSelfReferenceToMultiRefIndirectSelfReferenceDao();
	}

	public static MultiRefOneParentDao createMultiRefOneParentDao() {
		return new MultiRefOneParentDao();
	}

	public static MultiRefOtherIndirectParentDao createMultiRefOtherIndirectParentDao() {
		return new MultiRefOtherIndirectParentDao();
	}

	public static MultiRefOtherIndirectParentToMultiRefIndirectParentDao createMultiRefOtherIndirectParentToMultiRefIndirectParentDao() {
		return new MultiRefOtherIndirectParentToMultiRefIndirectParentDao();
	}

	public static MultiRefTwoParentsDao createMultiRefTwoParentsDao() {
		return new MultiRefTwoParentsDao();
	}

	public static OnlyDaoDao createOnlyDaoDao() {
		return new OnlyDaoDao();
	}

	public static RootDao createRootDao() {
		return new RootDao();
	}

	public static RootExtDao createRootExtDao() {
		return new RootExtDao();
	}

	public static SingleRefIndirectParentDao createSingleRefIndirectParentDao() {
		return new SingleRefIndirectParentDao();
	}

	public static SingleRefOneParentDao createSingleRefOneParentDao() {
		return new ExtendedSingleRefOneParentDao();
	}

	public static SingleRefOtherIndirectParentDao createSingleRefOtherIndirectParentDao() {
		return new SingleRefOtherIndirectParentDao();
	}

	public static SingleRefTwoParentsDao createSingleRefTwoParentsDao() {
		return new SingleRefTwoParentsDao();
	}

	public static SomeDifferentFilteringNotOwnerDao createSomeDifferentFilteringNotOwnerDao() {
		return new SomeDifferentFilteringNotOwnerDao();
	}

	public static SomeDifferentFilteringNotOwnerToFilteredDao createSomeDifferentFilteringNotOwnerToFilteredDao() {
		return new SomeDifferentFilteringNotOwnerToFilteredDao();
	}

	public static SomeFilteringOwnerDao createSomeFilteringOwnerDao() {
		return new SomeFilteringOwnerDao();
	}

	public static SomeFilteringOwnerToFilteredNotOwnerDao createSomeFilteringOwnerToFilteredNotOwnerDao() {
		return new SomeFilteringOwnerToFilteredNotOwnerDao();
	}

	public static SomeFilteringOwnerToFilteredOnlyDaoFieldNotOwnerDao createSomeFilteringOwnerToFilteredOnlyDaoFieldNotOwnerDao() {
		return new SomeFilteringOwnerToFilteredOnlyDaoFieldNotOwnerDao();
	}

}
