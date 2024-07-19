package com.github.ma_vin.util.layer_generator.sample.reference.content.dao;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.SourceEntityFilter;
import jakarta.persistence.*;
import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of SourceEntityFilter
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.reference.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"aggTargetEntityFilter"})
@Table(name = "SourceEntityFilters")
@ToString(exclude = {"aggTargetEntityFilter"})
public class SourceEntityFilterDao implements IIdentifiableDao {

	/**
	 * a 1:n reference to entities with enum value A
	 */
	@OneToMany(mappedBy = "parentSourceEntityFilter", targetEntity = TargetEntityFilterDao.class)
	private Collection<TargetEntityFilterDao> aggTargetEntityFilter;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, SourceEntityFilter.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, SourceEntityFilter.ID_PREFIX);
	}

}
