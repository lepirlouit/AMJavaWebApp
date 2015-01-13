package be.pir.am.api.dao;

import java.util.List;

import javax.ejb.Local;

import be.pir.am.entities.AthleteEntity;
import be.pir.am.entities.LicenceEntity;

@Local
public interface LicenceDao extends EntityDao<LicenceEntity> {
	List<AthleteEntity> findAthletesWithBib(String bib);
}
