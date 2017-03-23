package be.pir.am.api.dao;

import be.pir.am.entities.AthleteEntity;
import be.pir.am.entities.EventTypeEntity;
import be.pir.am.entities.RecordEntity;

import javax.ejb.Local;

@Local
public interface RecordDao extends EntityDao<RecordEntity> {
    RecordEntity getBestRecordForAthleteAndEventType(AthleteEntity athelte, EventTypeEntity eventType);
}
