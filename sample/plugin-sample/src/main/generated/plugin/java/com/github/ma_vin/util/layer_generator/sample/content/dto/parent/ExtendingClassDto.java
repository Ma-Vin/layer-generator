package com.github.ma_vin.util.layer_generator.sample.content.dto.parent;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated dto class of ExtendingClass
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.content.dto")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuppressWarnings({"java:S2160", "java:S1068"})
@ToString(callSuper = true)
public class ExtendingClassDto extends SuperClassDto {

	private String additionalDescription;

	/**
	 * Identification of ExtendingClass
	 */
	private String identification;

}
