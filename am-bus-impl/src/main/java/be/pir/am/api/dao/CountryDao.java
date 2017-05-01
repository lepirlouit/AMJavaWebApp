package be.pir.am.api.dao;

import be.pir.am.entities.CountryEntity;

import javax.ejb.Local;

@Local
public interface CountryDao extends EntityDao<CountryEntity> {
    CountryEntity getByIso3(String iso3);
}
