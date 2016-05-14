package be.pir.am.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import be.pir.am.api.dao.TeamDao;
import be.pir.am.entities.EventEntity;
import be.pir.am.entities.EventEntity_;
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
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<TeamEntity> cq = cb.createQuery(TeamEntity.class);
		Root<TeamEntity> event = cq.from(TeamEntity.class);
		cq.where(cb.equal(event.get(TeamEntity_.federationNumber), federationNumber));
		return em.createQuery(cq).getSingleResult();
	}
}
