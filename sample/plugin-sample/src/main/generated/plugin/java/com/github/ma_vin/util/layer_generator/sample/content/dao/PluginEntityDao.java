package com.github.ma_vin.util.layer_generator.sample.content.dao;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.PluginEntity;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Generated dao class of PluginEntity
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.content.dao")
@Data
@Entity
@Table(name = "PluginEntitys")
public class PluginEntityDao implements IIdentifiableDao {

	@Column
	private short exampleAttribute;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, PluginEntity.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, PluginEntity.ID_PREFIX);
	}

}
