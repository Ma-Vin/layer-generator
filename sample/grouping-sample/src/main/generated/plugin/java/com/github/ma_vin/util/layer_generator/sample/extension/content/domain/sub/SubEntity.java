package com.github.ma_vin.util.layer_generator.sample.extension.content.domain.sub;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.IIdentifiable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of SubEntity
 * <br>
 * entity at sub package of basePackage.type
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.extension.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class SubEntity implements IIdentifiable {

	public static final String ID_PREFIX = "S";

	/**
	 * Identification of SubEntity
	 */
	private String identification;

}
