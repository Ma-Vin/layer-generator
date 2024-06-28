package com.github.ma_vin.util.layer_generator.sample.content.dao;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.SubEntity;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of SubEntity
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"parentRootEntity"})
@Table(name = "SubEntitys")
@ToString(exclude = {"parentRootEntity"})
public class SubEntityDao implements IIdentifiableDao {

	@Column
	private String description;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@JoinColumn(name = "ParentRootEntityId", nullable = false)
	@ManyToOne(targetEntity = RootEntityDao.class)
	private RootEntityDao parentRootEntity;

	@Column
	private String subName;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, SubEntity.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, SubEntity.ID_PREFIX);
	}

}
