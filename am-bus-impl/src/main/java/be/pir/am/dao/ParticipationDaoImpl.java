package be.pir.am.dao;

import javax.ejb.Stateless;

import be.pir.am.api.dao.ParticipationDao;
import be.pir.am.entities.ParticipationEntity;

@Stateless
public class ParticipationDaoImpl extends AbstractEntityDao<ParticipationEntity> implements ParticipationDao {

	public ParticipationDaoImpl() {
		super(ParticipationEntity.class);
	}

}
