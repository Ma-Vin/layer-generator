package com.github.ma_vin.util.layer_generator.sample.entity.content.dao;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.entity.content.domain.ExtendingEntity;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of ExtendingEntity
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.entity.content.dao")
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("java:S2160")
@Table(name = "ExtendingEntitys")
@ToString(callSuper = true)
public class ExtendingEntityDao extends AbstractEntityDao {

	@Column
	private Integer addedField;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, ExtendingEntity.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, ExtendingEntity.ID_PREFIX);
	}

}
