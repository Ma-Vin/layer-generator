package com.github.ma_vin.util.layer_generator.sample.entity.content.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated domain class of ExtendingEntity
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.entity.content.domain")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuppressWarnings({"java:S2160", "java:S1068"})
@ToString(callSuper = true)
public class ExtendingEntity extends AbstractEntity {

	public static final String ID_PREFIX = "E";

	private Integer addedField;

	/**
	 * Identification of ExtendingEntity
	 */
	private String identification;

}
