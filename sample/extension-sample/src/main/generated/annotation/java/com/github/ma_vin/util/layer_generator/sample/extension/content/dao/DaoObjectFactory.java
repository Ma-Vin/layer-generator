package com.github.ma_vin.util.layer_generator.sample.extension.content.dao;

import com.github.ma_vin.util.layer_generator.sample.extension.ExtendedEntityDao;

public class DaoObjectFactory {

	private DaoObjectFactory() {
	}

	public static ToExtendEntityDao createToExtendEntityDao() {
		return new ExtendedEntityDao();
	}

}
