package be.pir.am.api.service;

import java.util.List;

import javax.ejb.Local;

import be.pir.am.api.dto.AthleteDto;
import be.pir.am.api.dto.CategoryDto;
import be.pir.am.api.dto.CompetitionDto;
import be.pir.am.api.dto.EventDto;
import be.pir.am.api.dto.TeamDto;

@Local
public interface AthleteService {
	List<AthleteDto> findAthletesByBib(int bib);

	List<AthleteDto> findAthletesByBibAndCategory(int bib, CategoryDto category);

	List<EventDto> findEventsForAthlete(AthleteDto athlete, CompetitionDto competition);

	/**
	 * First find the competitor for this competition, if null, create a new one
	 * create a participation to all the rounds of the events,
	 * 
	 * 
	 * 
	 * @param competitor
	 * @param events
	 */
	void subscribeAthleteToEvents(AthleteDto competitor, List<EventDto> events, CategoryDto category, CompetitionDto competition);
	
	List<TeamDto> listAllTeams();
}
