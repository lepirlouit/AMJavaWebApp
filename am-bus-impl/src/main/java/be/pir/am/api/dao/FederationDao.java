package be.pir.am.api.dao;

import be.pir.am.entities.FederationEntity;

import javax.ejb.Local;

@Local
public interface FederationDao extends EntityDao<FederationEntity> {
    FederationEntity getByCode(String code);
}
