package com.github.ma_vin.util.layer_generator.sample.reference.content.dao;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.given.AnyEnumType;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import com.github.ma_vin.util.layer_generator.sample.reference.content.domain.TargetEntityFilter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of TargetEntityFilter
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.reference.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"parentSourceEntityFilter"})
@Table(name = "TargetEntityFilters")
@ToString(exclude = {"parentSourceEntityFilter"})
public class TargetEntityFilterDao implements IIdentifiableDao {

	@Column
	@Enumerated(EnumType.STRING)
	private AnyEnumType enumFieldForFiltering;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@JoinColumn(name = "ParentSourceEntityFilterId", nullable = false)
	@ManyToOne(targetEntity = SourceEntityFilterDao.class)
	private SourceEntityFilterDao parentSourceEntityFilter;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, TargetEntityFilter.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, TargetEntityFilter.ID_PREFIX);
	}

}
