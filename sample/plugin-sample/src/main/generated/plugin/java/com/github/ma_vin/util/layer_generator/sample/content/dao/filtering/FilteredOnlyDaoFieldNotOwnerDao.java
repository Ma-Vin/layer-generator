package com.github.ma_vin.util.layer_generator.sample.content.dao.filtering;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.IIdentifiableDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.filtering.FilteredOnlyDaoFieldNotOwner;
import com.github.ma_vin.util.layer_generator.sample.given.AnyEnumType;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Generated dao class of FilteredOnlyDaoFieldNotOwner
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.content.dao")
@Data
@Entity
@Table(name = "FilteredOnlyDaoFieldNotOwners")
public class FilteredOnlyDaoFieldNotOwnerDao implements IIdentifiableDao {

	@Column
	private String descriptionOnlyDaoFieldNotOwner;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column
	@Enumerated(EnumType.STRING)
	private AnyEnumType someEnumOnlyDaoFieldNotOwner;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, FilteredOnlyDaoFieldNotOwner.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, FilteredOnlyDaoFieldNotOwner.ID_PREFIX);
	}

}
