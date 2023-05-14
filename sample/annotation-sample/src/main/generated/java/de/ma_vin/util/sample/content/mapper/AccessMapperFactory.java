package de.ma_vin.util.sample.content.mapper;

public class AccessMapperFactory {

	private AccessMapperFactory() {
	}

	public static CommonAccessMapper createCommonAccessMapper() {
		return new CommonAccessMapper();
	}

}
