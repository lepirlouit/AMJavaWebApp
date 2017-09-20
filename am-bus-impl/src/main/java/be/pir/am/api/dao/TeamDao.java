package be.pir.am.api.dao;

import be.pir.am.entities.TeamEntity;

import javax.ejb.Local;

@Local
public interface TeamDao extends EntityDao<TeamEntity> {

    TeamEntity getByFederationNumber(String federationNumber);

}
