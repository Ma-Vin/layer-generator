package de.ma_vin.util.sample.content.dao.multi;

import de.ma_vin.ape.utils.generators.IdGenerator;
import de.ma_vin.util.layer.generator.annotations.model.BaseDao;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.domain.multi.MultiRefTwoParents;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of MultiRefTwoParents
 */
@BaseDao("de.ma_vin.util.sample.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"parentRoot", "parentMultiRefOneParent"})
@Table(name = "MultiRefTwoParentss")
@ToString(exclude = {"parentRoot", "parentMultiRefOneParent"})
public class MultiRefTwoParentsDao implements IIdentifiableDao {

	@Column
	private String description;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@JoinColumn(name = "MultiRefOneParentId")
	@ManyToOne(targetEntity = MultiRefOneParentDao.class)
	private MultiRefOneParentDao parentMultiRefOneParent;

	@JoinColumn(name = "RootId")
	@ManyToOne(targetEntity = RootDao.class)
	private RootDao parentRoot;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, MultiRefTwoParents.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, MultiRefTwoParents.ID_PREFIX);
	}

}
