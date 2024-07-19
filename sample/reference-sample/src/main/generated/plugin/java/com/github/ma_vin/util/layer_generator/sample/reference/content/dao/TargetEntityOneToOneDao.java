package com.github.ma_vin.util.layer_generator.sample.reference.content.dao;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.TargetEntityOneToOne;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of TargetEntityOneToOne
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.reference.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"parentSourceEntityOneToOne"})
@Table(name = "TargetEntityOneToOnes")
@ToString(exclude = {"parentSourceEntityOneToOne"})
public class TargetEntityOneToOneDao implements IIdentifiableDao {

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@JoinColumn(name = "ParentSourceEntityOneToOneId", nullable = false)
	@OneToOne(targetEntity = SourceEntityOneToOneDao.class)
	private SourceEntityOneToOneDao parentSourceEntityOneToOne;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, TargetEntityOneToOne.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, TargetEntityOneToOne.ID_PREFIX);
	}

}
