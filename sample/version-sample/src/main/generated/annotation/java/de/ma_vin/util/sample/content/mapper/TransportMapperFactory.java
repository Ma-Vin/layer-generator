package de.ma_vin.util.sample.content.mapper;

public class TransportMapperFactory {

	private TransportMapperFactory() {
	}

	public static CommonTransportMapper createCommonTransportMapper() {
		return new CommonTransportMapper();
	}

}
