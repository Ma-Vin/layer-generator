package com.github.ma_vin.util.layer_generator.sample.reference.content.dao;

public interface IIdentifiableDao {

	/**
	 * @return the id of the dao
	 */
	Long getId();

	/**
	 * @return the calculated identification from id of the dao
	 */
	String getIdentification();

	/**
	 * @param id the id of the dao
	 */
	void setId(Long id);

	/**
	 * @param identification the identification where to determine the id from
	 */
	void setIdentification(String identification);

}
