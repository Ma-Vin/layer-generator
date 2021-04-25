package de.ma_vin.util.sample.content.dao.multi;

import de.ma_vin.ape.utils.generators.IdGenerator;
import de.ma_vin.util.layer.generator.annotations.model.BaseDao;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.domain.multi.MultiRefOneParent;
import java.util.Collection;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of MultiRefOneParent
 */
@BaseDao("de.ma_vin.util.sample.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"parentRoot", "multiRefs"})
@Table(name = "MultiRefOneParents")
@ToString(exclude = {"parentRoot", "multiRefs"})
public class MultiRefOneParentDao implements IIdentifiableDao {

	@Column
	private String description;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@OneToMany(mappedBy = "parentMultiRefOneParent", targetEntity = MultiRefTwoParentsDao.class)
	private Collection<MultiRefTwoParentsDao> multiRefs;

	@JoinColumn(name = "ParentRootId", nullable = false)
	@ManyToOne(targetEntity = RootDao.class)
	private RootDao parentRoot;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, MultiRefOneParent.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, MultiRefOneParent.ID_PREFIX);
	}

}
