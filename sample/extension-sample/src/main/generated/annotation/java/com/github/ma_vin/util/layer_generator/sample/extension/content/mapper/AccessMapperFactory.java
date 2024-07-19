package com.github.ma_vin.util.layer_generator.sample.extension.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.extension.ExtendedCommonAccessMapper;

public class AccessMapperFactory {

	private AccessMapperFactory() {
	}

	public static CommonAccessMapper createCommonAccessMapper() {
		return new ExtendedCommonAccessMapper();
	}

}
