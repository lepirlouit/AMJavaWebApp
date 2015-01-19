package be.pir.am.dao;

import javax.ejb.Stateless;

import be.pir.am.api.dao.CompetitionDao;
import be.pir.am.entities.CompetitionEntity;

@Stateless
public class CompetitionDaoImpl extends AbstractEntityDao<CompetitionEntity>
		implements CompetitionDao {

	public CompetitionDaoImpl() {
		super(CompetitionEntity.class);
	}

}
