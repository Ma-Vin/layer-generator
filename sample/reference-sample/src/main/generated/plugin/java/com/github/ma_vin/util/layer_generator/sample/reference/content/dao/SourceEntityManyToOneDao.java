package com.github.ma_vin.util.layer_generator.sample.reference.content.dao;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.SourceEntityManyToOne;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of SourceEntityManyToOne
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.reference.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"manyToOneRef"})
@Table(name = "SourceEntityManyToOnes")
@ToString(exclude = {"manyToOneRef"})
public class SourceEntityManyToOneDao implements IIdentifiableDao {

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	/**
	 * a m:1 reference
	 */
	@JoinColumn(name = "manyToOneRefId")
	@ManyToOne(targetEntity = TargetEntityManyToManyDao.class)
	private TargetEntityManyToManyDao manyToOneRef;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, SourceEntityManyToOne.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, SourceEntityManyToOne.ID_PREFIX);
	}

}
