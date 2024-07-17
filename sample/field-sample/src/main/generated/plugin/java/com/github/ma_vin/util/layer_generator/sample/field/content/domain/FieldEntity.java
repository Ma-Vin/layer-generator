package com.github.ma_vin.util.layer_generator.sample.field.content.domain;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDomain;
import com.github.ma_vin.util.layer_generator.sample.given.AnyEnumType;
import com.github.ma_vin.util.layer_generator.sample.given.CustomType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated domain class of FieldEntity
 */
@BaseDomain("com.github.ma_vin.util.layer_generator.sample.field.content.domain")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class FieldEntity implements IIdentifiable {

	public static final String ID_PREFIX = "R";

	private String daoAndDomain;

	private AnyEnumType daoEnum;

	private AnyEnumType daoEnumWithText;

	/**
	 * short text
	 */
	private String document;

	private String dtoAndDomain;

	/**
	 * Identification of FieldEntity
	 */
	private String identification;

	private double numberWithDaoInfo;

	private String onlyDomain;

	private CustomType someCustom;

	private AnyEnumType someEnum;

	private Integer someInteger;

	private String someName;

	private String someString;

	private String textWithDaoInfo;

}
