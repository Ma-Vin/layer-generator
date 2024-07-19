package com.github.ma_vin.util.layer_generator.sample.extension.content.dto;

import com.github.ma_vin.util.layer_generator.sample.extension.ExtendedEntityDto;

public class DtoObjectFactory {

	private DtoObjectFactory() {
	}

	public static ToExtendEntityDto createToExtendEntityDto() {
		return new ExtendedEntityDto();
	}

}
