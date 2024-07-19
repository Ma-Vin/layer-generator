package com.github.ma_vin.util.layer_generator.sample.extension.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.extension.ExtendedCommonTransportMapper;

public class TransportMapperFactory {

	private TransportMapperFactory() {
	}

	public static CommonTransportMapper createCommonTransportMapper() {
		return new ExtendedCommonTransportMapper();
	}

}
