package com.github.ma_vin.util.layer_generator.sample.content.dao.filtering;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.given.AnyEnumType;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@BaseDao("com.github.ma_vin.util.layer_generator.sample.content.dao")
@Data
@Entity
@IdClass(SomeDifferentFilteringNotOwnerToFilteredDao.SomeDifferentFilteringNotOwnerToFilteredId.class)
@NoArgsConstructor
@SuppressWarnings("java:S1948")
@Table(name = "SomeDifferentFilteringNotOwnerToFiltereds")
public class SomeDifferentFilteringNotOwnerToFilteredDao {

	private AnyEnumType filterAnyEnumType;

	@Id
	@JoinColumn(name = "FilteredId")
	@OneToOne(targetEntity = FilteredDao.class)
	private FilteredDao filtered;

	@Id
	@JoinColumn(name = "SomeDifferentFilteringNotOwnerId")
	@ManyToOne(targetEntity = SomeDifferentFilteringNotOwnerDao.class)
	private SomeDifferentFilteringNotOwnerDao someDifferentFilteringNotOwner;

	@AllArgsConstructor
	@Data
	@NoArgsConstructor
	@SuppressWarnings("java:S1068")
	public static class SomeDifferentFilteringNotOwnerToFilteredId implements Serializable {

		private Long filtered;

		private Long someDifferentFilteringNotOwner;

	}

}
