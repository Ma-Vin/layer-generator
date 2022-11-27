package de.ma_vin.util.sample.content.dao.parent;

import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Generated dao class of SuperClass
 */
@Data
@MappedSuperclass
public abstract class SuperClassDao implements IIdentifiableDao {

	@Column
	private String description;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	protected Long id;

}
