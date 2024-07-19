package com.github.ma_vin.util.layer_generator.sample.reference.content.dao;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.SourceEntityOneToMany;
import jakarta.persistence.*;
import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of SourceEntityOneToMany
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.reference.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"oneToManyRef"})
@Table(name = "SourceEntityOneToManys")
@ToString(exclude = {"oneToManyRef"})
public class SourceEntityOneToManyDao implements IIdentifiableDao {

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	/**
	 * a 1:n reference
	 */
	@OneToMany(mappedBy = "parentSourceEntityOneToMany", targetEntity = TargetEntityOneToManyDao.class)
	private Collection<TargetEntityOneToManyDao> oneToManyRef;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, SourceEntityOneToMany.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, SourceEntityOneToMany.ID_PREFIX);
	}

}
