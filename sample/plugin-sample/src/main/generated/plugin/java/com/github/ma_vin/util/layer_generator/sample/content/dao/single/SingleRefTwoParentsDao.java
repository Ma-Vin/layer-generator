package com.github.ma_vin.util.layer_generator.sample.content.dao.single;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.IIdentifiableDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.RootDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.single.SingleRefTwoParents;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of SingleRefTwoParents
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.content.dao")
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

	@JoinColumn(name = "ParentRootId")
	@OneToOne(targetEntity = RootDao.class)
	private RootDao parentRoot;

	@JoinColumn(name = "ParentSingleRefOneParentId")
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
