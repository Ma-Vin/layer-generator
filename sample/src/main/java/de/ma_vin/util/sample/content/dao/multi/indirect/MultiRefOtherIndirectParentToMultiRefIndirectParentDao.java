package de.ma_vin.util.sample.content.dao.multi.indirect;

import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Entity
@IdClass(MultiRefOtherIndirectParentToMultiRefIndirectParentDao.MultiRefOtherIndirectParentToMultiRefIndirectParentId.class)
@NoArgsConstructor
@SuppressWarnings("java:S1948")
@Table(name = "MultiRefOtherIndirectParentToMultiRefIndirectParents")
public class MultiRefOtherIndirectParentToMultiRefIndirectParentDao {

	@Id
	@JoinColumn(name = "MultiRefIndirectParentId")
	@OneToOne(targetEntity = MultiRefIndirectParentDao.class)
	private MultiRefIndirectParentDao multiRefIndirectParent;

	@Id
	@JoinColumn(name = "MultiRefOtherIndirectParentId")
	@ManyToOne(targetEntity = MultiRefOtherIndirectParentDao.class)
	private MultiRefOtherIndirectParentDao multiRefOtherIndirectParent;

	@AllArgsConstructor
	@Data
	@NoArgsConstructor
	@SuppressWarnings("java:S1068")
	public static class MultiRefOtherIndirectParentToMultiRefIndirectParentId implements Serializable {

		private Long multiRefIndirectParentId;

		private Long multiRefOtherIndirectParentId;

	}

}
