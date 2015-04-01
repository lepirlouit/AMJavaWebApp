package be.pir.am.api.dao;

import java.util.List;

import javax.ejb.Local;

import be.pir.am.entities.CategoryEntity;
import be.pir.am.entities.CompetitionEntity;
import be.pir.am.entities.EventEntity;

@Local
public interface EventDao extends EntityDao<EventEntity> {
	List<EventEntity> findEventsByCategoryAndCompetition(CompetitionEntity competition, List<CategoryEntity> categories);
}
