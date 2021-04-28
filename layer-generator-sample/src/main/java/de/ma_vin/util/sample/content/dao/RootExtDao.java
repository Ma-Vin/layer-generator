package de.ma_vin.util.sample.content.dao;

import de.ma_vin.ape.utils.generators.IdGenerator;
import de.ma_vin.util.layer.generator.annotations.model.BaseDao;
import de.ma_vin.util.sample.content.domain.RootExt;
import de.ma_vin.util.sample.given.AnyEnumType;
import de.ma_vin.util.sample.given.CustomType;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of RootExt
 */
@BaseDao("de.ma_vin.util.sample.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"parentRoot"})
@Table(indexes = {@Index(columnList = "someInteger, anyOtherName DESC", name = "SomeIndex")}, name = "RootExtensions")
@ToString(exclude = {"parentRoot"})
public class RootExtDao implements IIdentifiableDao {

	@Column
	private String daoAndDomain;

	@Column
	private AnyEnumType daoEnum;

	@Column
	@Enumerated(EnumType.STRING)
	private AnyEnumType daoEnumWithText;

	/**
	 * short text
	 * <br>
	 * long text
	 */
	@Column(columnDefinition = "BLOB")
	@Lob
	private String document;

	@Column
	private String extendedInfo;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column(precision = 7, scale = 3)
	private double numberWithDaoInfo;

	@Column
	private String onlyDao;

	@JoinColumn(name = "ParentRootId", nullable = false)
	@OneToOne(targetEntity = RootDao.class)
	private RootDao parentRoot;

	@Column
	private CustomType someCustom;

	@Column
	@Enumerated(EnumType.STRING)
	private AnyEnumType someEnum;

	@Column
	private Integer someInteger;

	@Column(name = "anyOtherName")
	private String someName;

	@Column(length = 128, nullable = false)
	private String textWithDaoInfo;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, RootExt.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, RootExt.ID_PREFIX);
	}

}
