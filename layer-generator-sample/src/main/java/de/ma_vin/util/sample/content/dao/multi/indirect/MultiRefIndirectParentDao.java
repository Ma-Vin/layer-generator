package de.ma_vin.util.sample.content.dao.multi.indirect;

import de.ma_vin.util.layer.generator.annotations.model.BaseDao;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.domain.multi.indirect.MultiRefIndirectParent;
import de.ma_vin.util.sample.given.IdGenerator;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of MultiRefIndirectParent
 */
@BaseDao("de.ma_vin.util.sample.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"parentRoot"})
@Table(name = "MultiRefIndirectParents")
@ToString(exclude = {"parentRoot"})
public class MultiRefIndirectParentDao implements IIdentifiableDao {

	@Column
	private String description;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@JoinColumn(name = "ParentRootId", nullable = false)
	@ManyToOne(targetEntity = RootDao.class)
	private RootDao parentRoot;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, MultiRefIndirectParent.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, MultiRefIndirectParent.ID_PREFIX);
	}

}
