package de.ma_vin.util.sample.content.dao.multi;

import de.ma_vin.util.layer.generator.annotations.model.BaseDao;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.domain.multi.MultiRefOneParent;
import de.ma_vin.util.sample.given.IdGenerator;
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
@EqualsAndHashCode(exclude = {"parentRoot", "multiRef"})
@Table(name = "MultiRefOneParents")
@ToString(exclude = {"parentRoot", "multiRef"})
public class MultiRefOneParentDao implements IIdentifiableDao {

	@Column
	private String description;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	/**
	 * Instances with two parents
	 */
	@OneToMany(mappedBy = "parentMultiRefOneParent", targetEntity = MultiRefTwoParentsDao.class)
	private Collection<MultiRefTwoParentsDao> multiRef;

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
