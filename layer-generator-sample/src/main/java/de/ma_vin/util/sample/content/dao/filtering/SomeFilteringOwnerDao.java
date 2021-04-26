package de.ma_vin.util.sample.content.dao.filtering;

import de.ma_vin.ape.utils.generators.IdGenerator;
import de.ma_vin.util.layer.generator.annotations.model.BaseDao;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.domain.filtering.SomeFilteringOwner;
import java.util.Collection;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of SomeFilteringOwner
 */
@BaseDao("de.ma_vin.util.sample.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"parentRoot", "aggFiltered", "aggFilteredOnlyDaoField"})
@Table(name = "SomeFilteringOwners")
@ToString(exclude = {"parentRoot", "aggFiltered", "aggFilteredOnlyDaoField"})
public class SomeFilteringOwnerDao implements IIdentifiableDao {

	@OneToMany(mappedBy = "parentSomeFilteringOwner", targetEntity = FilteredDao.class)
	private Collection<FilteredDao> aggFiltered;

	@OneToMany(mappedBy = "parentSomeFilteringOwner", targetEntity = FilteredOnlyDaoFieldDao.class)
	private Collection<FilteredOnlyDaoFieldDao> aggFilteredOnlyDaoField;

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
