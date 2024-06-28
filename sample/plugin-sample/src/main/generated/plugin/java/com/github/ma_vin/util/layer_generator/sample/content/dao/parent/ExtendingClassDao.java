package com.github.ma_vin.util.layer_generator.sample.content.dao.parent;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.RootDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.parent.ExtendingClass;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of ExtendingClass
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.content.dao")
@Data
@Entity
@EqualsAndHashCode(callSuper = true, exclude = {"parentRoot"})
@SuppressWarnings("java:S2160")
@Table(name = "ExtendingClasss")
@ToString(callSuper = true, exclude = {"parentRoot"})
public class ExtendingClassDao extends SuperClassDao {

	@Column
	private String additionalDescription;

	@JoinColumn(name = "ParentRootId", nullable = false)
	@ManyToOne(targetEntity = RootDao.class)
	private RootDao parentRoot;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, ExtendingClass.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, ExtendingClass.ID_PREFIX);
	}

}
