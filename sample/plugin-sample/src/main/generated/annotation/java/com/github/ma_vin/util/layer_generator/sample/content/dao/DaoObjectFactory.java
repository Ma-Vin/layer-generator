package com.github.ma_vin.util.layer_generator.sample.content.dao;

public class DaoObjectFactory {

	private DaoObjectFactory() {
	}

	public static PluginEntityDao createPluginEntityDao() {
		return new PluginEntityDao();
	}

}
