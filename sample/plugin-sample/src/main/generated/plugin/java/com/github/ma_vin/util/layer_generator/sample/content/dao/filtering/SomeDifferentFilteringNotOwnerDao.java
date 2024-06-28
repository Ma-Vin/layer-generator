package com.github.ma_vin.util.layer_generator.sample.content.dao.filtering;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.IIdentifiableDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.RootDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.filtering.SomeDifferentFilteringNotOwner;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import jakarta.persistence.*;
import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of SomeDifferentFilteringNotOwner
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"parentRoot", "aggFiltered"})
@Table(name = "SomeDifferentFilteringNotOwners")
@ToString(exclude = {"parentRoot", "aggFiltered"})
public class SomeDifferentFilteringNotOwnerDao implements IIdentifiableDao {

	@OneToMany(mappedBy = "someDifferentFilteringNotOwner", targetEntity = SomeDifferentFilteringNotOwnerToFilteredDao.class)
	private Collection<SomeDifferentFilteringNotOwnerToFilteredDao> aggFiltered;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@JoinColumn(name = "ParentRootId", nullable = false)
	@OneToOne(targetEntity = RootDao.class)
	private RootDao parentRoot;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, SomeDifferentFilteringNotOwner.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, SomeDifferentFilteringNotOwner.ID_PREFIX);
	}

}
