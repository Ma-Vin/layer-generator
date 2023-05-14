package de.ma_vin.util.sample.content.dto;

public class DtoObjectFactory {

	private DtoObjectFactory() {
	}

	public static RootEntityDto createRootEntityDto() {
		return new RootEntityDto();
	}

	public static SubEntityDto createSubEntityDto() {
		return new SubEntityDto();
	}

}
