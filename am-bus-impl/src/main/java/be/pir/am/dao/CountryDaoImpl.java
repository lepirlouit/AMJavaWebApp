package be.pir.am.dao;

import javax.ejb.Stateless;

import be.pir.am.api.dao.CountryDao;
import be.pir.am.entities.CountryEntity;

@Stateless
public class CountryDaoImpl extends AbstractEntityDao<CountryEntity> implements
		CountryDao {

	public CountryDaoImpl() {
		super(CountryEntity.class);
	}

}
