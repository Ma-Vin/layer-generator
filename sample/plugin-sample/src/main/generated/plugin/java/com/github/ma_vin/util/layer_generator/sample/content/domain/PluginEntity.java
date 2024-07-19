package com.github.ma_vin.util.layer_generator.sample.content.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of PluginEntity
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class PluginEntity implements IIdentifiable {

	public static final String ID_PREFIX = "P";

	private short exampleAttribute;

	/**
	 * Identification of PluginEntity
	 */
	private String identification;

}
