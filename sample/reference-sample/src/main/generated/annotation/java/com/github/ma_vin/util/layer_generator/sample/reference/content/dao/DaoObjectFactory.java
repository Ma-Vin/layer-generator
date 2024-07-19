package com.github.ma_vin.util.layer_generator.sample.reference.content.dao;

public class DaoObjectFactory {

	private DaoObjectFactory() {
	}

	public static SourceEntityFilterDao createSourceEntityFilterDao() {
		return new SourceEntityFilterDao();
	}

	public static SourceEntityFilterNotAtTargetDao createSourceEntityFilterNotAtTargetDao() {
		return new SourceEntityFilterNotAtTargetDao();
	}

	public static SourceEntityFilterNotAtTargetToTargetEntityFilterNotAtTargetDao createSourceEntityFilterNotAtTargetToTargetEntityFilterNotAtTargetDao() {
		return new SourceEntityFilterNotAtTargetToTargetEntityFilterNotAtTargetDao();
	}

	public static SourceEntityManyToManyDao createSourceEntityManyToManyDao() {
		return new SourceEntityManyToManyDao();
	}

	public static SourceEntityManyToManyToTargetEntityManyToManyDao createSourceEntityManyToManyToTargetEntityManyToManyDao() {
		return new SourceEntityManyToManyToTargetEntityManyToManyDao();
	}

	public static SourceEntityManyToOneDao createSourceEntityManyToOneDao() {
		return new SourceEntityManyToOneDao();
	}

	public static SourceEntityOneToManyDao createSourceEntityOneToManyDao() {
		return new SourceEntityOneToManyDao();
	}

	public static SourceEntityOneToOneDao createSourceEntityOneToOneDao() {
		return new SourceEntityOneToOneDao();
	}

	public static TargetEntityFilterDao createTargetEntityFilterDao() {
		return new TargetEntityFilterDao();
	}

	public static TargetEntityFilterNotAtTargetDao createTargetEntityFilterNotAtTargetDao() {
		return new TargetEntityFilterNotAtTargetDao();
	}

	public static TargetEntityManyToManyDao createTargetEntityManyToManyDao() {
		return new TargetEntityManyToManyDao();
	}

	public static TargetEntityOneToManyDao createTargetEntityOneToManyDao() {
		return new TargetEntityOneToManyDao();
	}

	public static TargetEntityOneToOneDao createTargetEntityOneToOneDao() {
		return new TargetEntityOneToOneDao();
	}

}
