package com.github.ma_vin.util.layer_generator.sample.content.dto.domain.dto;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import com.github.ma_vin.util.layer_generator.sample.content.dto.ITransportable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of DomainAndDto
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.content.dto")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class DomainAndDtoDto implements ITransportable {

	private String description;

	/**
	 * Identification of DomainAndDto
	 */
	private String identification;

}
