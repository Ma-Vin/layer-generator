package com.github.ma_vin.util.layer_generator.sample.reference.content.dao;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.TargetEntityManyToMany;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Generated dao class of TargetEntityManyToMany
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.reference.content.dao")
@Data
@Entity
@Table(name = "TargetEntityManyToManys")
public class TargetEntityManyToManyDao implements IIdentifiableDao {

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, TargetEntityManyToMany.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, TargetEntityManyToMany.ID_PREFIX);
	}

}
