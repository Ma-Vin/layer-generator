package com.github.ma_vin.util.layer_generator.sample.content.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated domain class of Root
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
@Data
@EqualsAndHashCode(exclude = {"singleRef", "anotherSingleRef", "ext"})
@NoArgsConstructor
@SuppressWarnings("java:S1068")
@ToString(exclude = {"singleRef", "anotherSingleRef", "ext"})
public class Root implements IIdentifiable {

	public static final String ID_PREFIX = "R";

	private SingleRefTwo anotherSingleRef;

	private String description;

	private RootExt ext;

	/**
	 * Identification of Root
	 */
	private String identification;

	private String rootName;

	private SingleRefOne singleRef;

}
