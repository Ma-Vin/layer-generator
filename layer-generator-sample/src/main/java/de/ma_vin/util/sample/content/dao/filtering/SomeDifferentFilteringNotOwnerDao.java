package de.ma_vin.util.sample.content.dao.filtering;

import de.ma_vin.ape.utils.generators.IdGenerator;
import de.ma_vin.util.layer.generator.annotations.model.BaseDao;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.domain.filtering.SomeDifferentFilteringNotOwner;
import java.util.Collection;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of SomeDifferentFilteringNotOwner
 */
@BaseDao("de.ma_vin.util.sample.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"parentRoot", "aggFiltered"})
@Table(name = "SomeDifferentFilteringNotOwners")
@ToString(exclude = {"parentRoot", "aggFiltered"})
public class SomeDifferentFilteringNotOwnerDao implements IIdentifiableDao {

	@OneToMany(mappedBy = "filtered", targetEntity = SomeDifferentFilteringNotOwnerToFilteredDao.class)
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
