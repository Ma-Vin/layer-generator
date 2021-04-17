package de.ma_vin.util.sample.content.dao.multi.indirect;

import de.ma_vin.ape.utils.generators.IdGenerator;
import de.ma_vin.util.layer.generator.annotations.model.BaseDao;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.domain.multi.indirect.MultiRefOtherIndirectParent;
import java.util.Collection;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of MultiRefOtherIndirectParent
 */
@BaseDao("de.ma_vin.util.sample.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"parentRoot", "multiIndirectRefs"})
@Table(name = "MultiRefOtherIndirectParents")
@ToString(exclude = {"parentRoot", "multiIndirectRefs"})
public class MultiRefOtherIndirectParentDao implements IIdentifiableDao {

	@Column
	private String description;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@OneToMany(mappedBy = "multiRefIndirectParent", targetEntity = MultiRefOtherIndirectParentToMultiRefIndirectParentDao.class)
	private Collection<MultiRefOtherIndirectParentToMultiRefIndirectParentDao> multiIndirectRefs;

	@JoinColumn(name = "RootId", nullable = false)
	@ManyToOne(targetEntity = RootDao.class)
	private RootDao parentRoot;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, MultiRefOtherIndirectParent.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, MultiRefOtherIndirectParent.ID_PREFIX);
	}

}