package de.ma_vin.util.sample.content.dao.filtering;

import de.ma_vin.ape.utils.generators.IdGenerator;
import de.ma_vin.util.layer.generator.annotations.model.BaseDao;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.domain.filtering.FilteredNotOwner;
import de.ma_vin.util.sample.given.AnyEnumType;
import javax.persistence.*;
import lombok.Data;

/**
 * Generated dao class of FilteredNotOwner
 */
@BaseDao("de.ma_vin.util.sample.content.dao")
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
