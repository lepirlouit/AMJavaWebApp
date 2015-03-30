package be.pir.am.api.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import be.pir.am.entities.AthleteEntity;
import be.pir.am.entities.LicenseEntity;

@Local
public interface AthleteDao extends EntityDao<AthleteEntity> {
	List<LicenseEntity> findAthleteByBibGenderAndBirthdayMinMax(String bib, Character gender, Date dateMin, Date dateMax);
}
