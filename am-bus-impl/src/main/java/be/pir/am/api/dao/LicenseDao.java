package be.pir.am.api.dao;

import be.pir.am.entities.LicenseEntity;

import javax.ejb.Local;
import java.util.List;

@Local
public interface LicenseDao extends EntityDao<LicenseEntity> {
    List<LicenseEntity> findAthletesWithBib(String bib);
}
