package de.ma_vin.util.sample.content.domain.domain.dao;

import de.ma_vin.util.sample.content.domain.IIdentifiable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of DomainAndDao
 */
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
