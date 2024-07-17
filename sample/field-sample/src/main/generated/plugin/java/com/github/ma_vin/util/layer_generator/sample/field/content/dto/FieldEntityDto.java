package com.github.ma_vin.util.layer_generator.sample.field.content.dto;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import com.github.ma_vin.util.layer_generator.sample.given.AnyEnumType;
import com.github.ma_vin.util.layer_generator.sample.given.CustomType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of FieldEntity
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.field.content.dto")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class FieldEntityDto implements ITransportable {

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

	private String onlyDto;

	private CustomType someCustom;

	private AnyEnumType someEnum;

	private Integer someInteger;

	private String someName;

	private String someString;

	private String textWithDaoInfo;

}
