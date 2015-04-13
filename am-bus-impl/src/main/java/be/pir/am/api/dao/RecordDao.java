package be.pir.am.api.dao;

import javax.ejb.Local;

import be.pir.am.entities.AthleteEntity;
import be.pir.am.entities.EventTypeEntity;
import be.pir.am.entities.RecordEntity;

@Local
public interface RecordDao extends EntityDao<RecordEntity> {
	RecordEntity getBestRecordForAthleteAndEventType(AthleteEntity athelte, EventTypeEntity eventType);
}
