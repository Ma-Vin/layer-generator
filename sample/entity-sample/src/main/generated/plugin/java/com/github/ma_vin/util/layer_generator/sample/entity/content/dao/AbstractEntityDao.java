package com.github.ma_vin.util.layer_generator.sample.entity.content.dao;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Generated dao class of AbstractEntity
 */
@Data
@MappedSuperclass
public abstract class AbstractEntityDao implements IIdentifiableDao {

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	protected Long id;

	@Column
	private String superName;

}
