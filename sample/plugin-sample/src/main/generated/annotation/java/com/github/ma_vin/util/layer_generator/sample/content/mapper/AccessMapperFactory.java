package com.github.ma_vin.util.layer_generator.sample.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.extending.ExtendedCommonAccessMapper;

public class AccessMapperFactory {

	private AccessMapperFactory() {
	}

	public static CommonAccessMapper createCommonAccessMapper() {
		return new ExtendedCommonAccessMapper();
	}

	public static DomainDaoAccessMapper createDomainDaoAccessMapper() {
		return new DomainDaoAccessMapper();
	}

	public static FilteringAccessMapper createFilteringAccessMapper() {
		return new FilteringAccessMapper();
	}

	public static MultiAccessMapper createMultiAccessMapper() {
		return new MultiAccessMapper();
	}

	public static MultiIndirectAccessMapper createMultiIndirectAccessMapper() {
		return new MultiIndirectAccessMapper();
	}

	public static ParentAccessMapper createParentAccessMapper() {
		return new ParentAccessMapper();
	}

	public static SingleAccessMapper createSingleAccessMapper() {
		return new SingleAccessMapper();
	}

	public static SingleIndirectAccessMapper createSingleIndirectAccessMapper() {
		return new SingleIndirectAccessMapper();
	}

}
