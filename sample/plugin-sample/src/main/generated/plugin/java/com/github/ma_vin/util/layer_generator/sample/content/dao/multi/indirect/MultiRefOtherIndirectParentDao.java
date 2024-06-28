package com.github.ma_vin.util.layer_generator.sample.content.dao.multi.indirect;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.IIdentifiableDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.RootDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.multi.indirect.MultiRefOtherIndirectParent;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import jakarta.persistence.*;
import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of MultiRefOtherIndirectParent
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"parentRoot", "multiIndirectRef"})
@Table(name = "MultiRefOtherIndirectParents")
@ToString(exclude = {"parentRoot", "multiIndirectRef"})
public class MultiRefOtherIndirectParentDao implements IIdentifiableDao {

	@Column
	private String description;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@OneToMany(mappedBy = "multiRefOtherIndirectParent", targetEntity = MultiRefOtherIndirectParentToMultiRefIndirectParentDao.class)
	private Collection<MultiRefOtherIndirectParentToMultiRefIndirectParentDao> multiIndirectRef;

	@JoinColumn(name = "ParentRootId", nullable = false)
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
