package de.ma_vin.util.sample.content.dao.filtering;

import de.ma_vin.util.layer.generator.annotations.model.BaseDao;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.domain.filtering.FilteredOnlyDaoFieldNotOwner;
import de.ma_vin.util.sample.given.AnyEnumType;
import de.ma_vin.util.sample.given.IdGenerator;
import javax.persistence.*;
import lombok.Data;

/**
 * Generated dao class of FilteredOnlyDaoFieldNotOwner
 */
@BaseDao("de.ma_vin.util.sample.content.dao")
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
