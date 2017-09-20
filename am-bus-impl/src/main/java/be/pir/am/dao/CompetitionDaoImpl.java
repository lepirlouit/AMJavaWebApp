package be.pir.am.dao;

import be.pir.am.api.dao.CompetitionDao;
import be.pir.am.entities.CompetitionEntity;

import javax.ejb.Stateless;

@Stateless
public class CompetitionDaoImpl extends AbstractEntityDao<CompetitionEntity>
        implements CompetitionDao {

    public CompetitionDaoImpl() {
        super(CompetitionEntity.class);
    }

}
