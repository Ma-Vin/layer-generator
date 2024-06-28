package com.github.ma_vin.util.layer_generator.sample.content.dao.multi;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.IIdentifiableDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.RootDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.multi.MultiRefTwoParents;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of MultiRefTwoParents
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.content.dao")
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

	@JoinColumn(name = "ParentMultiRefOneParentId")
	@ManyToOne(targetEntity = MultiRefOneParentDao.class)
	private MultiRefOneParentDao parentMultiRefOneParent;

	@JoinColumn(name = "ParentRootId")
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
