package com.github.ma_vin.util.layer_generator.sample.content.mapper;

import com.github.ma_vin.util.layer_generator.sample.extending.ExtendedCommonTransportMapper;

public class TransportMapperFactory {

	private TransportMapperFactory() {
	}

	public static CommonTransportMapper createCommonTransportMapper() {
		return new ExtendedCommonTransportMapper();
	}

	public static DomainDtoTransportMapper createDomainDtoTransportMapper() {
		return new DomainDtoTransportMapper();
	}

	public static DomainTransportMapper createDomainTransportMapper() {
		return new DomainTransportMapper();
	}

	public static FilteringTransportMapper createFilteringTransportMapper() {
		return new FilteringTransportMapper();
	}

	public static MultiIndirectTransportMapper createMultiIndirectTransportMapper() {
		return new MultiIndirectTransportMapper();
	}

	public static MultiTransportMapper createMultiTransportMapper() {
		return new MultiTransportMapper();
	}

	public static ParentTransportMapper createParentTransportMapper() {
		return new ParentTransportMapper();
	}

	public static SingleIndirectTransportMapper createSingleIndirectTransportMapper() {
		return new SingleIndirectTransportMapper();
	}

	public static SingleTransportMapper createSingleTransportMapper() {
		return new SingleTransportMapper();
	}

}
