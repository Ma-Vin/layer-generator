package de.ma_vin.util.sample.content.dao.filtering;

import de.ma_vin.util.layer.generator.annotations.model.BaseDao;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@BaseDao("de.ma_vin.util.sample.content.dao")
@Data
@Entity
@IdClass(SomeFilteringOwnerToFilteredNotOwnerDao.SomeFilteringOwnerToFilteredNotOwnerId.class)
@NoArgsConstructor
@SuppressWarnings("java:S1948")
@Table(name = "SomeFilteringOwnerToFilteredNotOwners")
public class SomeFilteringOwnerToFilteredNotOwnerDao {

	@Id
	@JoinColumn(name = "FilteredNotOwnerId")
	@OneToOne(targetEntity = FilteredNotOwnerDao.class)
	private FilteredNotOwnerDao filteredNotOwner;

	@Id
	@JoinColumn(name = "SomeFilteringOwnerId")
	@ManyToOne(targetEntity = SomeFilteringOwnerDao.class)
	private SomeFilteringOwnerDao someFilteringOwner;

	@AllArgsConstructor
	@Data
	@NoArgsConstructor
	@SuppressWarnings("java:S1068")
	public static class SomeFilteringOwnerToFilteredNotOwnerId implements Serializable {

		private Long filteredNotOwner;

		private Long someFilteringOwner;

	}

}
