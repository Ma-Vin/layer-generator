package com.github.ma_vin.util.layer_generator.sample.content.dao.filtering;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.IIdentifiableDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.RootDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.filtering.SomeFilteringOwner;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import jakarta.persistence.*;
import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of SomeFilteringOwner
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"parentRoot", "aggFilteredOnlyDaoFieldNotOwner", "aggFiltered", "aggFilteredOnlyDaoField", "aggFilteredNotOwner"})
@Table(name = "SomeFilteringOwners")
@ToString(exclude = {"parentRoot", "aggFilteredOnlyDaoFieldNotOwner", "aggFiltered", "aggFilteredOnlyDaoField", "aggFilteredNotOwner"})
public class SomeFilteringOwnerDao implements IIdentifiableDao {

	@OneToMany(mappedBy = "parentSomeFilteringOwner", targetEntity = FilteredDao.class)
	private Collection<FilteredDao> aggFiltered;

	@OneToMany(mappedBy = "someFilteringOwner", targetEntity = SomeFilteringOwnerToFilteredNotOwnerDao.class)
	private Collection<SomeFilteringOwnerToFilteredNotOwnerDao> aggFilteredNotOwner;

	@OneToMany(mappedBy = "parentSomeFilteringOwner", targetEntity = FilteredOnlyDaoFieldDao.class)
	private Collection<FilteredOnlyDaoFieldDao> aggFilteredOnlyDaoField;

	@OneToMany(mappedBy = "someFilteringOwner", targetEntity = SomeFilteringOwnerToFilteredOnlyDaoFieldNotOwnerDao.class)
	private Collection<SomeFilteringOwnerToFilteredOnlyDaoFieldNotOwnerDao> aggFilteredOnlyDaoFieldNotOwner;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@JoinColumn(name = "ParentRootId", nullable = false)
	@OneToOne(targetEntity = RootDao.class)
	private RootDao parentRoot;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, SomeFilteringOwner.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, SomeFilteringOwner.ID_PREFIX);
	}

}
