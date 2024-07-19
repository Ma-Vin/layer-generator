package com.github.ma_vin.util.layer_generator.sample.reference.content.dao;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.SourceEntityManyToMany;
import jakarta.persistence.*;
import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of SourceEntityManyToMany
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.reference.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"manyToManyRef"})
@Table(name = "SourceEntityManyToManys")
@ToString(exclude = {"manyToManyRef"})
public class SourceEntityManyToManyDao implements IIdentifiableDao {

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	/**
	 * a m:n reference
	 */
	@OneToMany(mappedBy = "sourceEntityManyToMany", targetEntity = SourceEntityManyToManyToTargetEntityManyToManyDao.class)
	private Collection<SourceEntityManyToManyToTargetEntityManyToManyDao> manyToManyRef;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, SourceEntityManyToMany.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, SourceEntityManyToMany.ID_PREFIX);
	}

}
