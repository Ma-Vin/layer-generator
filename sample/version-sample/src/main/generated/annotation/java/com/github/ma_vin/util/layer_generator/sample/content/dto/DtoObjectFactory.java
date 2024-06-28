package com.github.ma_vin.util.layer_generator.sample.content.dto;

public class DtoObjectFactory {

	private DtoObjectFactory() {
	}

	public static RootDto createRootDto() {
		return new RootDto();
	}

	public static RootExtDto createRootExtDto() {
		return new RootExtDto();
	}

	public static RootExtV1Dto createRootExtV1Dto() {
		return new RootExtV1Dto();
	}

	public static RootV1Dto createRootV1Dto() {
		return new RootV1Dto();
	}

	public static RootV2Dto createRootV2Dto() {
		return new RootV2Dto();
	}

	public static SingleRefOneDto createSingleRefOneDto() {
		return new SingleRefOneDto();
	}

	public static SingleRefTwoDto createSingleRefTwoDto() {
		return new SingleRefTwoDto();
	}

}
