package com.github.ma_vin.util.layer_generator.sample.content.domain.domain.dao;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of DomainAndDao
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class DomainAndDao implements IIdentifiable {

	public static final String ID_PREFIX = "DD1";

	private String description;

	/**
	 * Identification of DomainAndDao
	 */
	private String identification;

}
