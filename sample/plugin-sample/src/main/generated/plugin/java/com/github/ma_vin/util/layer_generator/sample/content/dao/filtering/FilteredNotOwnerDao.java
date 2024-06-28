package com.github.ma_vin.util.layer_generator.sample.content.dao.filtering;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.IIdentifiableDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.filtering.FilteredNotOwner;
import com.github.ma_vin.util.layer_generator.sample.given.AnyEnumType;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Generated dao class of FilteredNotOwner
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.content.dao")
@Data
@Entity
@Table(name = "FilteredNotOwners")
public class FilteredNotOwnerDao implements IIdentifiableDao {

	@Column
	private String descriptionNotOwner;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column
	@Enumerated(EnumType.STRING)
	private AnyEnumType someEnumNotOwner;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, FilteredNotOwner.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, FilteredNotOwner.ID_PREFIX);
	}

}
