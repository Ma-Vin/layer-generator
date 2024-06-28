package com.github.ma_vin.util.layer_generator.sample.content.dto;

public class DtoObjectFactory {

	private DtoObjectFactory() {
	}

	public static RootEntityDto createRootEntityDto() {
		return new RootEntityDto();
	}

	public static SubEntityDto createSubEntityDto() {
		return new SubEntityDto();
	}

}
