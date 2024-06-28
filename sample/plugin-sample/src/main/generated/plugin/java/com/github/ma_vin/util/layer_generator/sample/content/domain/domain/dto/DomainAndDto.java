package com.github.ma_vin.util.layer_generator.sample.content.domain.domain.dto;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import com.github.ma_vin.util.layer_generator.sample.content.domain.IIdentifiable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of DomainAndDto
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
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
