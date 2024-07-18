package com.github.ma_vin.util.layer_generator.sample.entity.content.dao;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.entity.content.domain.IndexEntity;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Generated dao class of IndexEntity
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.entity.content.dao")
@Data
@Entity
@Table(indexes = {@Index(columnList = "primaryKeyPartOne, primaryKeyPartTwo DESC", name = "ThePrimaryKey", unique = true), @Index(columnList = "indexPart DESC", name = "AnIndex")}, name = "IndexEntitys")
public class IndexEntityDao implements IIdentifiableDao {

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column
	private Integer indexPart;

	@Column
	private Integer primaryKeyPartOne;

	@Column
	private Integer primaryKeyPartTwo;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, IndexEntity.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, IndexEntity.ID_PREFIX);
	}

}
