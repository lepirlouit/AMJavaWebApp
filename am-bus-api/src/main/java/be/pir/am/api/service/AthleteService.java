package be.pir.am.api.service;

import java.util.List;

import javax.ejb.Local;

import be.pir.am.api.dto.AthleteDto;
import be.pir.am.api.dto.CategoryDto;

@Local
public interface AthleteService {
	List<AthleteDto> findAthletesByBib(int bib);
	List<AthleteDto> findAthletesByBibAndCategory(int bib, CategoryDto category);
}
