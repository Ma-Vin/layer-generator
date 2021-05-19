package de.ma_vin.util.sample.content.dao.multi.indirect;

import de.ma_vin.util.layer.generator.annotations.model.BaseDao;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.dao.RootDao;
import de.ma_vin.util.sample.content.domain.multi.indirect.MultiRefIndirectSelfReference;
import de.ma_vin.util.sample.given.IdGenerator;
import java.util.Collection;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of MultiRefIndirectSelfReference
 */
@BaseDao("de.ma_vin.util.sample.content.dao")
@Data
@Entity
@EqualsAndHashCode(exclude = {"parentRoot", "selfRefs"})
@Table(name = "MultiRefIndirectSelfReferences")
@ToString(exclude = {"parentRoot", "selfRefs"})
public class MultiRefIndirectSelfReferenceDao implements IIdentifiableDao {

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@JoinColumn(name = "ParentRootId", nullable = false)
	@ManyToOne(targetEntity = RootDao.class)
	private RootDao parentRoot;

	@OneToMany(mappedBy = "multiRefIndirectSelfReference", targetEntity = MultiRefIndirectSelfReferenceToMultiRefIndirectSelfReferenceDao.class)
	private Collection<MultiRefIndirectSelfReferenceToMultiRefIndirectSelfReferenceDao> selfRefs;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, MultiRefIndirectSelfReference.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, MultiRefIndirectSelfReference.ID_PREFIX);
	}

}
