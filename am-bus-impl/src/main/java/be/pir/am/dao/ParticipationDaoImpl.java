package be.pir.am.dao;

import be.pir.am.api.dao.ParticipationDao;
import be.pir.am.entities.ParticipationEntity;

import javax.ejb.Stateless;

@Stateless
public class ParticipationDaoImpl extends AbstractEntityDao<ParticipationEntity> implements ParticipationDao {

    public ParticipationDaoImpl() {
        super(ParticipationEntity.class);
    }

}
