package com.github.ma_vin.util.layer_generator.sample.entity.content.dto;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated dto class of ExtendingEntity
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.entity.content.dto")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuppressWarnings({"java:S2160", "java:S1068"})
@ToString(callSuper = true)
public class ExtendingEntityDto extends AbstractEntityDto {

	private Integer addedField;

	/**
	 * Identification of ExtendingEntity
	 */
	private String identification;

}
