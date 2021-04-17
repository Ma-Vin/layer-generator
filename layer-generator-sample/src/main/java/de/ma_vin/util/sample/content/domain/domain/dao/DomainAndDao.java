package de.ma_vin.util.sample.content.domain.domain.dao;

import de.ma_vin.util.layer.generator.annotations.model.BaseDomain;
import de.ma_vin.util.sample.content.domain.IIdentifiable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of DomainAndDao
 */
@BaseDomain("de.ma_vin.util.sample.content.domain")
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
