package be.pir.am.dao;

import be.pir.am.api.dao.EventDao;
import be.pir.am.entities.CategoryEntity;
import be.pir.am.entities.CompetitionEntity;
import be.pir.am.entities.EventEntity;
import be.pir.am.entities.EventEntity_;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;

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
        event.fetch(EventEntity_.rounds, JoinType.LEFT);
        event.fetch(EventEntity_.eventType, JoinType.LEFT);

        cq.where(cb.equal(event.get("competition"), competition), category.in(categories));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<EventEntity> findAllEventsInCompetition(CompetitionEntity competition) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<EventEntity> cq = cb.createQuery(EventEntity.class);
        Root<EventEntity> event = cq.from(EventEntity.class);
        event.fetch(EventEntity_.rounds);
        cq.where(cb.equal(event.get(EventEntity_.competition), competition));
        return em.createQuery(cq).getResultList();
    }
}
