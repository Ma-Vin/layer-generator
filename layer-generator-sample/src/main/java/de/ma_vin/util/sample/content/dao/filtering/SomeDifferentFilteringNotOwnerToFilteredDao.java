package de.ma_vin.util.sample.content.dao.filtering;

import de.ma_vin.util.layer.generator.annotations.model.BaseDao;
import de.ma_vin.util.sample.given.AnyEnumType;
import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@BaseDao("de.ma_vin.util.sample.content.dao")
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

		private Long filteredId;

		private Long someDifferentFilteringNotOwnerId;

	}

}
