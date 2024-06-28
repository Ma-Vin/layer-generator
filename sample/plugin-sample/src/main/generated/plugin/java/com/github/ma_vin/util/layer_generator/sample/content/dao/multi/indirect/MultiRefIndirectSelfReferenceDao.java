package com.github.ma_vin.util.layer_generator.sample.content.dao.multi.indirect;

import com.github.ma_vin.util.layer_generator.annotations.model.BaseDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.IIdentifiableDao;
import com.github.ma_vin.util.layer_generator.sample.content.dao.RootDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.multi.indirect.MultiRefIndirectSelfReference;
import com.github.ma_vin.util.layer_generator.sample.given.IdGenerator;
import jakarta.persistence.*;
import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of MultiRefIndirectSelfReference
 */
@BaseDao("com.github.ma_vin.util.layer_generator.sample.content.dao")
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
