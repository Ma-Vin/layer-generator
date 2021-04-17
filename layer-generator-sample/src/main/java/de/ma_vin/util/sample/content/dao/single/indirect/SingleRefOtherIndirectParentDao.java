package de.ma_vin.util.sample.content.dao.single.indirect;

import de.ma_vin.ape.utils.generators.IdGenerator;
import de.ma_vin.util.layer.generator.annotations.model.BaseDao;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.domain.single.indirect.SingleRefOtherIndirectParent;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of SingleRefOtherIndirectParent
 */
@BaseDao("de.ma_vin.util.sample.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"parentRoot", "singleIndirectRef"})
@Table(name = "SingleRefOtherIndirectParents")
@ToString(exclude = {"parentRoot", "singleIndirectRef"})
public class SingleRefOtherIndirectParentDao implements IIdentifiableDao {

	@Column
	private String description;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@JoinColumn(name = "RootId", nullable = false)
	@OneToOne(targetEntity = RootDao.class)
	private RootDao parentRoot;

	@JoinColumn(name = "singleIndirectRefId")
	@ManyToOne(targetEntity = SingleRefIndirectParentDao.class)
	private SingleRefIndirectParentDao singleIndirectRef;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, SingleRefOtherIndirectParent.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, SingleRefOtherIndirectParent.ID_PREFIX);
	}

}
