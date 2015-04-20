package be.pir.am.api.dao;

import java.util.List;

import javax.ejb.Local;

import be.pir.am.api.dto.AthleteDto;
import be.pir.am.api.dto.CompetitionDto;
import be.pir.am.entities.CompetitionEntity;
import be.pir.am.entities.CompetitorEntity;

@Local
public interface CompetitorDao  extends EntityDao<CompetitorEntity> {
	CompetitorEntity findCompetitor(AthleteDto athlete, CompetitionDto competition);

	CompetitorEntity findCompetitorFetchParticipationsAndRounds(AthleteDto athlete, CompetitionDto competition);
	List<CompetitorEntity> getAllCompetitorsFetchParticipationsRoundsCategoriesEvents(CompetitionEntity competition);
}