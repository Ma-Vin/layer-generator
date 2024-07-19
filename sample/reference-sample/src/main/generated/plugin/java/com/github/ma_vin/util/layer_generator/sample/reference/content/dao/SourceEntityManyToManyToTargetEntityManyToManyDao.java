package com.github.ma_vin.util.layer_generator.sample.reference.content.dao;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@BaseDao("com.github.ma_vin.util.layer_generator.sample.reference.content.dao")
@Data
@Entity
@IdClass(SourceEntityManyToManyToTargetEntityManyToManyDao.SourceEntityManyToManyToTargetEntityManyToManyId.class)
@NoArgsConstructor
@SuppressWarnings("java:S1948")
@Table(name = "SourceEntityManyToManyToTargetEntityManyToManys")
public class SourceEntityManyToManyToTargetEntityManyToManyDao {

	@Id
	@JoinColumn(name = "SourceEntityManyToManyId")
	@ManyToOne(targetEntity = SourceEntityManyToManyDao.class)
	private SourceEntityManyToManyDao sourceEntityManyToMany;

	@Id
	@JoinColumn(name = "TargetEntityManyToManyId")
	@OneToOne(targetEntity = TargetEntityManyToManyDao.class)
	private TargetEntityManyToManyDao targetEntityManyToMany;

	@AllArgsConstructor
	@Data
	@NoArgsConstructor
	@SuppressWarnings("java:S1068")
	public static class SourceEntityManyToManyToTargetEntityManyToManyId implements Serializable {

		private Long sourceEntityManyToMany;

		private Long targetEntityManyToMany;

	}

}
