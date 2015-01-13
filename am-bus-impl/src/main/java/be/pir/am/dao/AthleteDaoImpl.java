package be.pir.am.dao;

import javax.ejb.Stateless;

import be.pir.am.api.dao.AthleteDao;
import be.pir.am.entities.AthleteEntity;

@Stateless
public class AthleteDaoImpl extends AbstractEntityDao<AthleteEntity> implements
		AthleteDao {

	public AthleteDaoImpl() {
		super(AthleteEntity.class);
	}

}
