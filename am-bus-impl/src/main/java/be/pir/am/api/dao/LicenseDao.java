package be.pir.am.api.dao;

import java.util.List;

import javax.ejb.Local;

import be.pir.am.entities.LicenseEntity;

@Local
public interface LicenseDao extends EntityDao<LicenseEntity> {
	List<LicenseEntity> findAthletesWithBib(String bib);
}
