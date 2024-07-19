package com.github.ma_vin.util.layer_generator.sample.reference.content.dao;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.TargetEntityFilterNotAtTarget;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Generated dao class of TargetEntityFilterNotAtTarget
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.reference.content.dao")
@Data
@Entity
@Table(name = "TargetEntityFilterNotAtTargets")
public class TargetEntityFilterNotAtTargetDao implements IIdentifiableDao {

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, TargetEntityFilterNotAtTarget.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, TargetEntityFilterNotAtTarget.ID_PREFIX);
	}

}
