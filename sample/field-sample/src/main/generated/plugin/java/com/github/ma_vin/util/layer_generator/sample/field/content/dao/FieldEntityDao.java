package com.github.ma_vin.util.layer_generator.sample.field.content.dao;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.field.content.domain.FieldEntity;
import com.github.ma_vin.util.layer_generator.sample.given.AnyEnumType;
import com.github.ma_vin.util.layer_generator.sample.given.CustomType;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Generated dao class of FieldEntity
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.field.content.dao")
@Data
@Entity
@Table(name = "FieldEntitys")
public class FieldEntityDao implements IIdentifiableDao {

	@Column
	private String daoAndDomain;

	@Column
	private AnyEnumType daoEnum;

	@Column
	@Enumerated(EnumType.STRING)
	private AnyEnumType daoEnumWithText;

	/**
	 * short text
	 */
	@Column(columnDefinition = "BLOB")
	@Lob
	private String document;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column(precision = 7, scale = 3)
	private double numberWithDaoInfo;

	@Column
	private String onlyDao;

	@Column
	private CustomType someCustom;

	@Column
	@Enumerated(EnumType.STRING)
	private AnyEnumType someEnum;

	@Column
	private Integer someInteger;

	@Column(name = "anyOtherName")
	private String someName;

	@Column
	private String someString;

	@Column(length = 128, nullable = false)
	private String textWithDaoInfo;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, FieldEntity.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, FieldEntity.ID_PREFIX);
	}

}
