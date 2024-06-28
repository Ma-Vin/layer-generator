package com.github.ma_vin.util.layer_generator.sample.content.mapper;

public class AccessMapperFactory {

	private AccessMapperFactory() {
	}

	public static CommonAccessMapper createCommonAccessMapper() {
		return new CommonAccessMapper();
	}

}
