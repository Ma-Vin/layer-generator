package de.ma_vin.util.sample.content.dto;

import de.ma_vin.util.sample.content.dto.domain.DerivedFromDomainDto;
import de.ma_vin.util.sample.content.dto.domain.dto.DomainAndDtoDto;
import de.ma_vin.util.sample.content.dto.dto.OnlyDtoDto;
import de.ma_vin.util.sample.content.dto.filtering.FilteredDto;
import de.ma_vin.util.sample.content.dto.filtering.FilteredOnlyDaoFieldDto;
import de.ma_vin.util.sample.content.dto.filtering.SomeDifferentFilteringNotOwnerDto;
import de.ma_vin.util.sample.content.dto.filtering.SomeFilteringOwnerDto;
import de.ma_vin.util.sample.content.dto.multi.MultiRefOneParentDto;
import de.ma_vin.util.sample.content.dto.multi.MultiRefTwoParentsDto;
import de.ma_vin.util.sample.content.dto.multi.indirect.MultiRefIndirectParentDto;
import de.ma_vin.util.sample.content.dto.multi.indirect.MultiRefIndirectSelfReferenceDto;
import de.ma_vin.util.sample.content.dto.multi.indirect.MultiRefOtherIndirectParentDto;
import de.ma_vin.util.sample.content.dto.parent.ExtendingClassDto;
import de.ma_vin.util.sample.content.dto.single.SingleRefOneParentDto;
import de.ma_vin.util.sample.content.dto.single.SingleRefTwoParentsDto;
import de.ma_vin.util.sample.content.dto.single.indirect.SingleRefIndirectParentDto;
import de.ma_vin.util.sample.content.dto.single.indirect.SingleRefOtherIndirectParentDto;
import de.ma_vin.util.sample.extending.ExtendedExtendingClassDto;

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
