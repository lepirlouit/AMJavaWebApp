package be.pir.am.api.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import be.pir.am.entities.AthleteEntity;

@Local
public interface AthleteDao extends EntityDao<AthleteEntity> {
	List<AthleteEntity> findAthleteByBibAndBirthdayMinMax(String bib, Date dateMin, Date dateMax);
}
