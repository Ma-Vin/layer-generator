package com.github.ma_vin.util.layer_generator.sample.entity.content.dao;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.entity.content.domain.RootEntity;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Generated dao class of RootEntity
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.entity.content.dao")
@Data
@Entity
@Table(name = "RootEntitys")
public class RootEntityDao implements IIdentifiableDao {

	@Column
	private String description;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column
	private String rootName;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, RootEntity.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, RootEntity.ID_PREFIX);
	}

}
