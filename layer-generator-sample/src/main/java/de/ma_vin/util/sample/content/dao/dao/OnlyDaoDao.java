package de.ma_vin.util.sample.content.dao.dao;

import de.ma_vin.util.layer.generator.annotations.model.BaseDao;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.given.IdGenerator;
import javax.persistence.*;
import lombok.Data;

/**
 * Generated dao class of OnlyDao
 */
@BaseDao("de.ma_vin.util.sample.content.dao")
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
