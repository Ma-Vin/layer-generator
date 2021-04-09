package de.ma_vin.util.sample.content.dao;

import de.ma_vin.ape.utils.generators.IdGenerator;
import de.ma_vin.util.sample.content.dao.filtering.SomeFilteringOwnerDao;
import de.ma_vin.util.sample.content.dao.multi.MultiRefOneParentDao;
import de.ma_vin.util.sample.content.dao.multi.MultiRefTwoParentsDao;
import de.ma_vin.util.sample.content.dao.multi.indirect.MultiRefIndirectParentDao;
import de.ma_vin.util.sample.content.dao.multi.indirect.MultiRefOtherIndirectParentDao;
import de.ma_vin.util.sample.content.dao.parent.ExtendingClassDao;
import de.ma_vin.util.sample.content.dao.single.SingleRefOneParentDao;
import de.ma_vin.util.sample.content.dao.single.SingleRefTwoParentsDao;
import de.ma_vin.util.sample.content.dao.single.indirect.SingleRefIndirectParentDao;
import de.ma_vin.util.sample.content.dao.single.indirect.SingleRefOtherIndirectParentDao;
import de.ma_vin.util.sample.content.domain.Root;
import java.util.Collection;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Generated dao class of Root
 */
@Data
@Entity
@EqualsAndHashCode(exclude = {"singleRef", "anotherSingleRef", "multiRefs", "anotherMultiRefs", "singleRefIndirectParent", "singleRefIndirectOtherParent", "multiRefIndirectParents", "multiRefIndirectOtherParents", "extendings", "filtering", "ext"})
@Table(name = "Roots")
@ToString(exclude = {"singleRef", "anotherSingleRef", "multiRefs", "anotherMultiRefs", "singleRefIndirectParent", "singleRefIndirectOtherParent", "multiRefIndirectParents", "multiRefIndirectOtherParents", "extendings", "filtering", "ext"})
public class RootDao implements IIdentifiableDao {

	@OneToMany(mappedBy = "parentRoot", targetEntity = MultiRefTwoParentsDao.class)
	private Collection<MultiRefTwoParentsDao> anotherMultiRefs;

	@OneToOne(mappedBy = "parentRoot", targetEntity = SingleRefTwoParentsDao.class)
	private SingleRefTwoParentsDao anotherSingleRef;

	@Column
	private String description;

	@OneToOne(mappedBy = "parentRoot", targetEntity = RootExtDao.class)
	private RootExtDao ext;

	@OneToMany(mappedBy = "parentRoot", targetEntity = ExtendingClassDao.class)
	private Collection<ExtendingClassDao> extendings;

	@OneToOne(mappedBy = "parentRoot", targetEntity = SomeFilteringOwnerDao.class)
	private SomeFilteringOwnerDao filtering;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@OneToMany(mappedBy = "parentRoot", targetEntity = MultiRefOtherIndirectParentDao.class)
	private Collection<MultiRefOtherIndirectParentDao> multiRefIndirectOtherParents;

	@OneToMany(mappedBy = "parentRoot", targetEntity = MultiRefIndirectParentDao.class)
	private Collection<MultiRefIndirectParentDao> multiRefIndirectParents;

	@OneToMany(mappedBy = "parentRoot", targetEntity = MultiRefOneParentDao.class)
	private Collection<MultiRefOneParentDao> multiRefs;

	@Column
	private String rootName;

	@OneToOne(mappedBy = "parentRoot", targetEntity = SingleRefOneParentDao.class)
	private SingleRefOneParentDao singleRef;

	@OneToOne(mappedBy = "parentRoot", targetEntity = SingleRefOtherIndirectParentDao.class)
	private SingleRefOtherIndirectParentDao singleRefIndirectOtherParent;

	@OneToOne(mappedBy = "parentRoot", targetEntity = SingleRefIndirectParentDao.class)
	private SingleRefIndirectParentDao singleRefIndirectParent;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, Root.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, Root.ID_PREFIX);
	}

}
