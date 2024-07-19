package com.github.ma_vin.util.layer_generator.sample.reference.content.dao;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.TargetEntityOneToMany;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of TargetEntityOneToMany
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.reference.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"parentSourceEntityOneToMany"})
@Table(name = "TargetEntityOneToManys")
@ToString(exclude = {"parentSourceEntityOneToMany"})
public class TargetEntityOneToManyDao implements IIdentifiableDao {

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@JoinColumn(name = "ParentSourceEntityOneToManyId", nullable = false)
	@ManyToOne(targetEntity = SourceEntityOneToManyDao.class)
	private SourceEntityOneToManyDao parentSourceEntityOneToMany;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, TargetEntityOneToMany.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, TargetEntityOneToMany.ID_PREFIX);
	}

}
