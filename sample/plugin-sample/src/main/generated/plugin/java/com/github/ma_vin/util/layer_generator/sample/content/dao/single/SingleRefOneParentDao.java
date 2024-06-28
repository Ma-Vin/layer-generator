package com.github.ma_vin.util.layer_generator.sample.content.dao.single;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.IIdentifiableDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.RootDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.single.SingleRefOneParent;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of SingleRefOneParent
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"parentRoot", "singleRef"})
@Table(name = "SingleRefOneParents")
@ToString(exclude = {"parentRoot", "singleRef"})
public class SingleRefOneParentDao implements IIdentifiableDao {

	@Column
	private String description;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@JoinColumn(name = "ParentRootId", nullable = false)
	@OneToOne(targetEntity = RootDao.class)
	private RootDao parentRoot;

	/**
	 * Instance with two parents
	 */
	@OneToOne(mappedBy = "parentSingleRefOneParent", targetEntity = SingleRefTwoParentsDao.class)
	private SingleRefTwoParentsDao singleRef;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, SingleRefOneParent.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, SingleRefOneParent.ID_PREFIX);
	}

}
