package com.github.ma_vin.util.layer_generator.sample.extension.content.mapper;

public class TransportMapperFactory {

	private TransportMapperFactory() {
	}

	public static CommonTransportMapper createCommonTransportMapper() {
		return new CommonTransportMapper();
	}

	public static SubTransportMapper createSubTransportMapper() {
		return new SubTransportMapper();
	}

}
