package de.ma_vin.util.sample.content.dao;

import de.ma_vin.ape.utils.generators.IdGenerator;
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
@Data
@Entity
@EqualsAndHashCode(exclude = {"parentRoot"})
@Table(name = "RootExts")
@ToString(exclude = {"parentRoot"})
public class RootExtDao implements IIdentifiableDao {

	@Column
	private String daoAndDomain;

	@Column
	private AnyEnumType daoEnum;

	@Column
	@Enumerated(EnumType.STRING)
	private AnyEnumType daoEnumWithText;

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

	@JoinColumn(name = "RootId", nullable = false)
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
