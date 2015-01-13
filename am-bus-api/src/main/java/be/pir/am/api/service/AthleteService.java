package be.pir.am.api.service;

import java.util.List;

import javax.ejb.Local;

import be.pir.am.api.dto.AthleteDto;

@Local
public interface AthleteService {
	List<AthleteDto> findAthletesByBib(int bib);
}
