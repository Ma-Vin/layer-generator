package com.github.ma_vin.util.layer_generator.sample.reference.content.dto;

public class DtoObjectFactory {

	private DtoObjectFactory() {
	}

	public static SourceEntityFilterDto createSourceEntityFilterDto() {
		return new SourceEntityFilterDto();
	}

	public static SourceEntityFilterNotAtTargetDto createSourceEntityFilterNotAtTargetDto() {
		return new SourceEntityFilterNotAtTargetDto();
	}

	public static SourceEntityManyToManyDto createSourceEntityManyToManyDto() {
		return new SourceEntityManyToManyDto();
	}

	public static SourceEntityManyToOneDto createSourceEntityManyToOneDto() {
		return new SourceEntityManyToOneDto();
	}

	public static SourceEntityOneToManyDto createSourceEntityOneToManyDto() {
		return new SourceEntityOneToManyDto();
	}

	public static SourceEntityOneToOneDto createSourceEntityOneToOneDto() {
		return new SourceEntityOneToOneDto();
	}

	public static TargetEntityFilterDto createTargetEntityFilterDto() {
		return new TargetEntityFilterDto();
	}

	public static TargetEntityFilterNotAtTargetDto createTargetEntityFilterNotAtTargetDto() {
		return new TargetEntityFilterNotAtTargetDto();
	}

	public static TargetEntityManyToManyDto createTargetEntityManyToManyDto() {
		return new TargetEntityManyToManyDto();
	}

	public static TargetEntityOneToManyDto createTargetEntityOneToManyDto() {
		return new TargetEntityOneToManyDto();
	}

	public static TargetEntityOneToOneDto createTargetEntityOneToOneDto() {
		return new TargetEntityOneToOneDto();
	}

}
