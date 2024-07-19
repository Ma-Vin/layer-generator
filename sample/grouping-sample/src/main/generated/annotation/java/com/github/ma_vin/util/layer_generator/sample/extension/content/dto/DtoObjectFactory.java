package com.github.ma_vin.util.layer_generator.sample.extension.content.dto;

import com.github.ma_vin.util.layer_generator.sample.extension.content.dto.sub.SubEntityDto;

public class DtoObjectFactory {

	private DtoObjectFactory() {
	}

	public static CommonEntityDto createCommonEntityDto() {
		return new CommonEntityDto();
	}

	public static SubEntityDto createSubEntityDto() {
		return new SubEntityDto();
	}

}
