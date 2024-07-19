package com.github.ma_vin.util.layer_generator.sample.reference.content.dao;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.given.AnyEnumType;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@BaseDao("com.github.ma_vin.util.layer_generator.sample.reference.content.dao")
@Data
@Entity
@IdClass(SourceEntityFilterNotAtTargetToTargetEntityFilterNotAtTargetDao.SourceEntityFilterNotAtTargetToTargetEntityFilterNotAtTargetId.class)
@NoArgsConstructor
@SuppressWarnings("java:S1948")
@Table(name = "SourceEntityFilterNotAtTargetToTargetEntityFilterNotAtTargets")
public class SourceEntityFilterNotAtTargetToTargetEntityFilterNotAtTargetDao {

	private AnyEnumType filterAnyEnumType;

	@Id
	@JoinColumn(name = "SourceEntityFilterNotAtTargetId")
	@ManyToOne(targetEntity = SourceEntityFilterNotAtTargetDao.class)
	private SourceEntityFilterNotAtTargetDao sourceEntityFilterNotAtTarget;

	@Id
	@JoinColumn(name = "TargetEntityFilterNotAtTargetId")
	@OneToOne(targetEntity = TargetEntityFilterNotAtTargetDao.class)
	private TargetEntityFilterNotAtTargetDao targetEntityFilterNotAtTarget;

	@AllArgsConstructor
	@Data
	@NoArgsConstructor
	@SuppressWarnings("java:S1068")
	public static class SourceEntityFilterNotAtTargetToTargetEntityFilterNotAtTargetId implements Serializable {

		private Long sourceEntityFilterNotAtTarget;

		private Long targetEntityFilterNotAtTarget;

	}

}
