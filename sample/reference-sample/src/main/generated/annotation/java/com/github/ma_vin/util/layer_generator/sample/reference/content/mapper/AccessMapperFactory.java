package com.github.ma_vin.util.layer_generator.sample.reference.content.mapper;

public class AccessMapperFactory {

	private AccessMapperFactory() {
	}

	public static CommonAccessMapper createCommonAccessMapper() {
		return new CommonAccessMapper();
	}

}
