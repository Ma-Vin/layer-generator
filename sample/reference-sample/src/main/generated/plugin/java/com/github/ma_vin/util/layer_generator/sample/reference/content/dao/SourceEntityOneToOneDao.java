package com.github.ma_vin.util.layer_generator.sample.reference.content.dao;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.SourceEntityOneToOne;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of SourceEntityOneToOne
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.reference.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"oneToOneRef"})
@Table(name = "SourceEntityOneToOnes")
@ToString(exclude = {"oneToOneRef"})
public class SourceEntityOneToOneDao implements IIdentifiableDao {

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	/**
	 * a 1:1 reference
	 */
	@OneToOne(mappedBy = "parentSourceEntityOneToOne", targetEntity = TargetEntityOneToOneDao.class)
	private TargetEntityOneToOneDao oneToOneRef;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, SourceEntityOneToOne.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, SourceEntityOneToOne.ID_PREFIX);
	}

}
