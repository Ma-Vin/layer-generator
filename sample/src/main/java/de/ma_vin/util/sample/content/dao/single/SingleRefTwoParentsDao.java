package de.ma_vin.util.sample.content.dao.single;

import de.ma_vin.ape.utils.generators.IdGenerator;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.domain.single.SingleRefTwoParents;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of SingleRefTwoParents
 */
@Data
@Entity
@EqualsAndHashCode(exclude = {"parentRoot", "parentSingleRefOneParent"})
@Table(name = "SingleRefTwoParentss")
@ToString(exclude = {"parentRoot", "parentSingleRefOneParent"})
public class SingleRefTwoParentsDao implements IIdentifiableDao {

	@Column
	private String description;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@JoinColumn(name = "RootId")
	@OneToOne(targetEntity = RootDao.class)
	private RootDao parentRoot;

	@JoinColumn(name = "SingleRefOneParentId")
	@OneToOne(targetEntity = SingleRefOneParentDao.class)
	private SingleRefOneParentDao parentSingleRefOneParent;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, SingleRefTwoParents.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, SingleRefTwoParents.ID_PREFIX);
	}

}
