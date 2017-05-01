package be.pir.am.api.dao;

import be.pir.am.entities.CategoryEntity;
import be.pir.am.entities.CompetitionEntity;
import be.pir.am.entities.EventEntity;

import javax.ejb.Local;
import java.util.List;

@Local
public interface EventDao extends EntityDao<EventEntity> {
    List<EventEntity> findEventsByCategoryAndCompetition(CompetitionEntity competition, List<CategoryEntity> categories);

    List<EventEntity> findAllEventsInCompetition(CompetitionEntity competition);
}
