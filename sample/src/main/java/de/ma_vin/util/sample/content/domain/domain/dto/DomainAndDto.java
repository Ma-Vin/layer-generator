package de.ma_vin.util.sample.content.domain.domain.dto;

import de.ma_vin.util.sample.content.domain.IIdentifiable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of DomainAndDto
 */
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class DomainAndDto implements IIdentifiable {

	public static final String ID_PREFIX = "DD2";

	private String description;

	/**
	 * Identification of DomainAndDto
	 */
	private String identification;

}
