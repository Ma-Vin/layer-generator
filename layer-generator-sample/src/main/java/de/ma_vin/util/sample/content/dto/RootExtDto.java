package de.ma_vin.util.sample.content.dto;

import de.ma_vin.util.layer.generator.annotations.model.BaseDto;
import de.ma_vin.util.sample.given.AnyEnumType;
import de.ma_vin.util.sample.given.CustomType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generated dto class of RootExt
 */
@BaseDto("de.ma_vin.util.sample.content.dto")
@Data
@NoArgsConstructor
@SuppressWarnings("java:S1068")
public class RootExtDto implements ITransportable {

	private AnyEnumType daoEnum;

	private AnyEnumType daoEnumWithText;

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
