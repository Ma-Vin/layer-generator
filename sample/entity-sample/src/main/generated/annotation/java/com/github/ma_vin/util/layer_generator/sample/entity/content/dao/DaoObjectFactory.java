package com.github.ma_vin.util.layer_generator.sample.entity.content.dao;

public class DaoObjectFactory {

	private DaoObjectFactory() {
	}

	public static ExtendingEntityDao createExtendingEntityDao() {
		return new ExtendingEntityDao();
	}

	public static IndexEntityDao createIndexEntityDao() {
		return new IndexEntityDao();
	}

	public static RootEntityDao createRootEntityDao() {
		return new RootEntityDao();
	}

}
