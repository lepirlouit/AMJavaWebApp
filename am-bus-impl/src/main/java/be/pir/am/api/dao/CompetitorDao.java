package be.pir.am.api.dao;

import be.pir.am.api.dto.AthleteDto;
import be.pir.am.entities.CompetitionEntity;
import be.pir.am.entities.CompetitorEntity;

public interface CompetitorDao {
	CompetitorEntity findCompetitor(AthleteDto athlete, CompetitionEntity competition);
}
