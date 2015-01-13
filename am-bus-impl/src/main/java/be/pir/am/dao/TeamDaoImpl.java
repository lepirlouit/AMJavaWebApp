package be.pir.am.dao;

import javax.ejb.Stateless;

import be.pir.am.api.dao.TeamDao;
import be.pir.am.entities.TeamEntity;

@Stateless
public class TeamDaoImpl extends AbstractEntityDao<TeamEntity> implements
		TeamDao {

	public TeamDaoImpl() {
		super(TeamEntity.class);
	}

}
