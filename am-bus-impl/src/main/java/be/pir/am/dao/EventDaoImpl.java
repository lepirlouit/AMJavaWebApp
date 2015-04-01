package be.pir.am.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import be.pir.am.api.dao.EventDao;
import be.pir.am.entities.CategoryEntity;
import be.pir.am.entities.CompetitionEntity;
import be.pir.am.entities.EventEntity;

@Stateless
public class EventDaoImpl extends AbstractEntityDao<EventEntity> implements EventDao {

	public EventDaoImpl() {
		super(EventEntity.class);
	}

	@Override
	public List<EventEntity> findEventsByCategoryAndCompetition(CompetitionEntity competition,
			List<CategoryEntity> categories) {

		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<EventEntity> cq = cb.createQuery(EventEntity.class);
		Root<EventEntity> event = cq.from(EventEntity.class);
		Join<Object, Object> category = event.join("categories");

		cq.where(cb.equal(event.get("competition"), competition), category.in(categories));
		return em.createQuery(cq).getResultList();
	}
}
