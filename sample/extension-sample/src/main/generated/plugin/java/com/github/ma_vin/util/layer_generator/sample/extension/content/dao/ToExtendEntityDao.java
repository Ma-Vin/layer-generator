package com.github.ma_vin.util.layer_generator.sample.extension.content.dao;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.ToExtendEntity;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Generated dao class of ToExtendEntity
 * <br>
 * entity which will be extended
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.extension.content.dao")
@Data
@Entity
@Table(name = "ToExtendEntitys")
public class ToExtendEntityDao implements IIdentifiableDao {

	@Column
	private String existingAttribute;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, ToExtendEntity.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, ToExtendEntity.ID_PREFIX);
	}

}
