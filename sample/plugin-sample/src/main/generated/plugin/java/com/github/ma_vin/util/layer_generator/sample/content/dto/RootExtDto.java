package com.github.ma_vin.util.layer_generator.sample.content.dto;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDto;
import com.github.ma_vin.util.layer_generator.sample.given.AnyEnumType;
import com.github.ma_vin.util.layer_generator.sample.given.CustomType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of RootExt
 */
@BaseDto("com.github.ma_vin.util.layer_generator.sample.content.dto")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class RootExtDto implements ITransportable {

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

	private String onlyDto;

	private CustomType someCustom;

	private AnyEnumType someEnum;

	private Integer someInteger;

	private String someName;

	private String textWithDaoInfo;

}
