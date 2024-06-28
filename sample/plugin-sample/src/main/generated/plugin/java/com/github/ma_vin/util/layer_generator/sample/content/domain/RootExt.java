package com.github.ma_vin.util.layer_generator.sample.content.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import com.github.ma_vin.util.layer_generator.sample.given.AnyEnumType;
import com.github.ma_vin.util.layer_generator.sample.given.CustomType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of RootExt
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class RootExt implements IIdentifiable {

	public static final String ID_PREFIX = "RE";

	private String daoAndDomain;

	private AnyEnumType daoEnum;

	private AnyEnumType daoEnumWithText;

	/**
	 * short text
	 * <br>
	 * long text
	 */
	private String document;

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
