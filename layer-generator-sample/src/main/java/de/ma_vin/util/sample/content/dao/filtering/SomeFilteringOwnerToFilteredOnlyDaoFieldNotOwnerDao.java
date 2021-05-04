package de.ma_vin.util.sample.content.dao.filtering;

import de.ma_vin.util.layer.generator.annotations.model.BaseDao;
import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@BaseDao("de.ma_vin.util.sample.content.dao")
@Data
@Entity
@IdClass(SomeFilteringOwnerToFilteredOnlyDaoFieldNotOwnerDao.SomeFilteringOwnerToFilteredOnlyDaoFieldNotOwnerId.class)
@NoArgsConstructor
@SuppressWarnings("java:S1948")
@Table(name = "SomeFilteringOwnerToFilteredOnlyDaoFieldNotOwners")
public class SomeFilteringOwnerToFilteredOnlyDaoFieldNotOwnerDao {

	@Id
	@JoinColumn(name = "FilteredOnlyDaoFieldNotOwnerId")
	@OneToOne(targetEntity = FilteredOnlyDaoFieldNotOwnerDao.class)
	private FilteredOnlyDaoFieldNotOwnerDao filteredOnlyDaoFieldNotOwner;

	@Id
	@JoinColumn(name = "SomeFilteringOwnerId")
	@ManyToOne(targetEntity = SomeFilteringOwnerDao.class)
	private SomeFilteringOwnerDao someFilteringOwner;

	@AllArgsConstructor
	@Data
	@NoArgsConstructor
	@SuppressWarnings("java:S1068")
	public static class SomeFilteringOwnerToFilteredOnlyDaoFieldNotOwnerId implements Serializable {

		private Long filteredOnlyDaoFieldNotOwner;

		private Long someFilteringOwner;

	}

}
