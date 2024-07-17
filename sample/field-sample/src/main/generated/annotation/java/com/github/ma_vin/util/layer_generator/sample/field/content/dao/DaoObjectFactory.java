package com.github.ma_vin.util.layer_generator.sample.field.content.dao;

public class DaoObjectFactory {

	private DaoObjectFactory() {
	}

	public static FieldEntityDao createFieldEntityDao() {
		return new FieldEntityDao();
	}

}
