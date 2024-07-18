package com.github.ma_vin.util.layer_generator.sample.entity.content.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of IndexEntity
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.entity.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class IndexEntity implements IIdentifiable {

	public static final String ID_PREFIX = "I";

	/**
	 * Identification of IndexEntity
	 */
	private String identification;

	private Integer indexPart;

	private Integer primaryKeyPartOne;

	private Integer primaryKeyPartTwo;

}
