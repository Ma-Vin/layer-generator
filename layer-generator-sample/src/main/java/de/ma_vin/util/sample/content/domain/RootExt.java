package de.ma_vin.util.sample.content.domain;

import de.ma_vin.util.layer.generator.annotations.model.BaseDomain;
import de.ma_vin.util.sample.given.AnyEnumType;
import de.ma_vin.util.sample.given.CustomType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of RootExt
 */
@BaseDomain("de.ma_vin.util.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class RootExt implements IIdentifiable {

	public static final String ID_PREFIX = "RE";

	private String daoAndDomain;

	private AnyEnumType daoEnum;

	private AnyEnumType daoEnumWithText;

	private String dtoAndDomain;

	private String extendedInfo;

	/**
	 * Identification of RootExt
	 */
	private String identification;

	private double numberWithDaoInfo;

	private String onlyDomain;

	private CustomType someCustom;

	private AnyEnumType someEnum;

	private Integer someInteger;

	private String someName;

	private String textWithDaoInfo;

}
