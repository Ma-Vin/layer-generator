package com.github.ma_vin.util.layer_generator.sample.entity.content.dto;

public class DtoObjectFactory {

	private DtoObjectFactory() {
	}

	public static DerivedEntityDto createDerivedEntityDto() {
		return new DerivedEntityDto();
	}

	public static ExtendingEntityDto createExtendingEntityDto() {
		return new ExtendingEntityDto();
	}

	public static RootEntityDto createRootEntityDto() {
		return new RootEntityDto();
	}

}
