package be.pir.am.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import be.pir.am.api.dao.TeamDao;
import be.pir.am.entities.TeamEntity;
import be.pir.am.entities.TeamEntity_;

@Stateless
public class TeamDaoImpl extends AbstractEntityDao<TeamEntity> implements
		TeamDao {

	public TeamDaoImpl() {
		super(TeamEntity.class);
	}

	@Override
	public TeamEntity getByFederationNumber(String federationNumber) {
		try {
			EntityManager em = getEntityManager();
			CriteriaBuilder cb = em.getCriteriaBuilder();

			CriteriaQuery<TeamEntity> cq = cb.createQuery(TeamEntity.class);
			Root<TeamEntity> team = cq.from(TeamEntity.class);
			cq.where(cb.equal(team.get(TeamEntity_.federationNumber), federationNumber));
			return em.createQuery(cq).getSingleResult();
		}catch (NoResultException nre){
			return null;
		}
	}
}
