package com.github.ma_vin.util.layer_generator.sample.content.dto;

import com.github.ma_vin.util.layer_generator.sample.content.dto.domain.DerivedFromDomainDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.domain.dto.DomainAndDtoDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.dto.OnlyDtoDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.filtering.FilteredDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.filtering.FilteredOnlyDaoFieldDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.filtering.SomeDifferentFilteringNotOwnerDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.filtering.SomeFilteringOwnerDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.multi.MultiRefOneParentDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.multi.MultiRefTwoParentsDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.multi.indirect.MultiRefIndirectParentDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.multi.indirect.MultiRefIndirectSelfReferenceDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.multi.indirect.MultiRefOtherIndirectParentDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.parent.ExtendingClassDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.single.SingleRefOneParentDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.single.SingleRefTwoParentsDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.single.indirect.SingleRefIndirectParentDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.single.indirect.SingleRefOtherIndirectParentDto;
import com.github.ma_vin.util.layer_generator.sample.extending.ExtendedExtendingClassDto;

public class DtoObjectFactory {

	private DtoObjectFactory() {
	}

	public static DerivedFromDomainDto createDerivedFromDomainDto() {
		return new DerivedFromDomainDto();
	}

	public static DomainAndDtoDto createDomainAndDtoDto() {
		return new DomainAndDtoDto();
	}

	public static ExtendingClassDto createExtendingClassDto() {
		return new ExtendedExtendingClassDto();
	}

	public static FilteredDto createFilteredDto() {
		return new FilteredDto();
	}

	public static FilteredOnlyDaoFieldDto createFilteredOnlyDaoFieldDto() {
		return new FilteredOnlyDaoFieldDto();
	}

	public static MultiRefIndirectParentDto createMultiRefIndirectParentDto() {
		return new MultiRefIndirectParentDto();
	}

	public static MultiRefIndirectSelfReferenceDto createMultiRefIndirectSelfReferenceDto() {
		return new MultiRefIndirectSelfReferenceDto();
	}

	public static MultiRefOneParentDto createMultiRefOneParentDto() {
		return new MultiRefOneParentDto();
	}

	public static MultiRefOtherIndirectParentDto createMultiRefOtherIndirectParentDto() {
		return new MultiRefOtherIndirectParentDto();
	}

	public static MultiRefTwoParentsDto createMultiRefTwoParentsDto() {
		return new MultiRefTwoParentsDto();
	}

	public static OnlyDtoDto createOnlyDtoDto() {
		return new OnlyDtoDto();
	}

	public static RootDto createRootDto() {
		return new RootDto();
	}

	public static RootExtDto createRootExtDto() {
		return new RootExtDto();
	}

	public static SingleRefIndirectParentDto createSingleRefIndirectParentDto() {
		return new SingleRefIndirectParentDto();
	}

	public static SingleRefOneParentDto createSingleRefOneParentDto() {
		return new SingleRefOneParentDto();
	}

	public static SingleRefOtherIndirectParentDto createSingleRefOtherIndirectParentDto() {
		return new SingleRefOtherIndirectParentDto();
	}

	public static SingleRefTwoParentsDto createSingleRefTwoParentsDto() {
		return new SingleRefTwoParentsDto();
	}

	public static SomeDifferentFilteringNotOwnerDto createSomeDifferentFilteringNotOwnerDto() {
		return new SomeDifferentFilteringNotOwnerDto();
	}

	public static SomeFilteringOwnerDto createSomeFilteringOwnerDto() {
		return new SomeFilteringOwnerDto();
	}

}
