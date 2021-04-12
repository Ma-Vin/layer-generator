package de.ma_vin.util.sample.content.dao.filtering;

import de.ma_vin.ape.utils.generators.IdGenerator;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.domain.filtering.Filtered;
import de.ma_vin.util.sample.given.AnyEnumType;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of Filtered
 */
@Data
@Entity
@EqualsAndHashCode(exclude = {"parentSomeFilteringOwner"})
@Table(name = "Filtereds")
@ToString(exclude = {"parentSomeFilteringOwner"})
public class FilteredDao implements IIdentifiableDao {

	@Column
	private String description;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@JoinColumn(name = "SomeFilteringOwnerId", nullable = false)
	@ManyToOne(targetEntity = SomeFilteringOwnerDao.class)
	private SomeFilteringOwnerDao parentSomeFilteringOwner;

	@Column
	@Enumerated(EnumType.STRING)
	private AnyEnumType someEnum;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, Filtered.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, Filtered.ID_PREFIX);
	}

}
