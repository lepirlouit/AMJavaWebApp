package be.pir.am.dao;

import javax.ejb.Stateless;

import be.pir.am.api.dao.FederationDao;
import be.pir.am.entities.FederationEntity;

@Stateless
public class FederationDaoImpl extends AbstractEntityDao<FederationEntity>
		implements FederationDao {

	public FederationDaoImpl() {
		super(FederationEntity.class);
	}

}
