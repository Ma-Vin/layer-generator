package de.ma_vin.util.sample.content.dao.domain.dao;

import de.ma_vin.ape.utils.generators.IdGenerator;
import de.ma_vin.util.layer.generator.annotations.model.BaseDao;
import de.ma_vin.util.sample.content.dao.IIdentifiableDao;
import de.ma_vin.util.sample.content.domain.domain.dao.DomainAndDao;
import javax.persistence.*;
import lombok.Data;

/**
 * Generated dao class of DomainAndDao
 */
@BaseDao("de.ma_vin.util.sample.content.dao")
@Data
@Entity
@Table(name = "DomainAndDaos")
public class DomainAndDaoDao implements IIdentifiableDao {

	@Column
	private String description;

	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Override
	public String getIdentification() {
		return IdGenerator.generateIdentification(id, DomainAndDao.ID_PREFIX);
	}

	@Override
	public void setIdentification(String identification) {
		id = IdGenerator.generateId(identification, DomainAndDao.ID_PREFIX);
	}

}
