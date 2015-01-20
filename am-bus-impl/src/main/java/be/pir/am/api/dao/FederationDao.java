package be.pir.am.api.dao;

import javax.ejb.Local;

import be.pir.am.entities.FederationEntity;

@Local
public interface FederationDao extends EntityDao<FederationEntity> {
	FederationEntity getByCode(String code);
}
