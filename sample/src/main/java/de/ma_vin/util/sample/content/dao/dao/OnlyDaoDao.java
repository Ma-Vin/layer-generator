package de.ma_vin.util.sample.content.dao.dao;

import de.ma_vin.ape.utils.generators.IdGenerator;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import javax.persistence.*;
import lombok.Data;

/**
 * Generated dao class of OnlyDao
 */
@Data
@Entity
@Table(name = "OnlyDaos")
public class OnlyDaoDao implements IIdentifiableDao {

	@Column
	private String description;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, "");
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, "");
	}

}
