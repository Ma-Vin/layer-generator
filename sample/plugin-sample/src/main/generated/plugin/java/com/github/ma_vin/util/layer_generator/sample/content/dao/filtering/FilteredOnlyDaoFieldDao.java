package com.github.ma_vin.util.layer_generator.sample.content.dao.filtering;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.IIdentifiableDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.filtering.FilteredOnlyDaoField;
import com.github.ma_vin.util.layer_generator.sample.given.AnyEnumType;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of FilteredOnlyDaoField
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"parentSomeFilteringOwner"})
@Table(name = "FilteredOnlyDaoFields")
@ToString(exclude = {"parentSomeFilteringOwner"})
public class FilteredOnlyDaoFieldDao implements IIdentifiableDao {

	@Column
	private String descriptionOnlyDaoField;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@JoinColumn(name = "ParentSomeFilteringOwnerId", nullable = false)
	@ManyToOne(targetEntity = SomeFilteringOwnerDao.class)
	private SomeFilteringOwnerDao parentSomeFilteringOwner;

	@Column
	@Enumerated(EnumType.STRING)
	private AnyEnumType someEnumOnlyDaoField;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, FilteredOnlyDaoField.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, FilteredOnlyDaoField.ID_PREFIX);
	}

}
