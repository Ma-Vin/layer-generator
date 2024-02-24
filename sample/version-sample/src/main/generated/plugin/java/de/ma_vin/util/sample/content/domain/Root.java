package de.ma_vin.util.sample.content.domain;

import de.ma_vin.util.layer.generator.annotations.model.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Generated domain class of Root
 */
@BaseDomain("de.ma_vin.util.sample.content.domain")
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
