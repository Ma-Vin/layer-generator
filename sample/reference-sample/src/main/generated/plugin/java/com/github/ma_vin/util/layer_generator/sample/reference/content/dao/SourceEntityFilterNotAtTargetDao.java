package com.github.ma_vin.util.layer_generator.sample.reference.content.dao;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.SourceEntityFilterNotAtTarget;
import jakarta.persistence.*;
import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of SourceEntityFilterNotAtTarget
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.reference.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"aggTargetEntityFilterNotAtTarget"})
@Table(name = "SourceEntityFilterNotAtTargets")
@ToString(exclude = {"aggTargetEntityFilterNotAtTarget"})
public class SourceEntityFilterNotAtTargetDao implements IIdentifiableDao {

	/**
	 * a 1:n reference to entities with enum value A, but the property is stored at connection table
	 */
	@OneToMany(mappedBy = "sourceEntityFilterNotAtTarget", targetEntity = SourceEntityFilterNotAtTargetToTargetEntityFilterNotAtTargetDao.class)
	private Collection<SourceEntityFilterNotAtTargetToTargetEntityFilterNotAtTargetDao> aggTargetEntityFilterNotAtTarget;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, SourceEntityFilterNotAtTarget.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, SourceEntityFilterNotAtTarget.ID_PREFIX);
	}

}
