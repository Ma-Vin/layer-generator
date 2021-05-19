package de.ma_vin.util.sample.content.dao.multi.indirect;

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
@IdClass(MultiRefIndirectSelfReferenceToMultiRefIndirectSelfReferenceDao.MultiRefIndirectSelfReferenceToMultiRefIndirectSelfReferenceId.class)
@NoArgsConstructor
@SuppressWarnings("java:S1948")
@Table(name = "MultiRefIndirectSelfReferenceToMultiRefIndirectSelfReferences")
public class MultiRefIndirectSelfReferenceToMultiRefIndirectSelfReferenceDao {

	@Id
	@JoinColumn(name = "MultiRefIndirectSelfReferenceId")
	@ManyToOne(targetEntity = MultiRefIndirectSelfReferenceDao.class)
	private MultiRefIndirectSelfReferenceDao multiRefIndirectSelfReference;

	@Id
	@JoinColumn(name = "SubMultiRefIndirectSelfReferenceId")
	@OneToOne(targetEntity = MultiRefIndirectSelfReferenceDao.class)
	private MultiRefIndirectSelfReferenceDao subMultiRefIndirectSelfReference;

	@AllArgsConstructor
	@Data
	@NoArgsConstructor
	@SuppressWarnings("java:S1068")
	public static class MultiRefIndirectSelfReferenceToMultiRefIndirectSelfReferenceId implements Serializable {

		private Long multiRefIndirectSelfReference;

		private Long subMultiRefIndirectSelfReference;

	}

}
