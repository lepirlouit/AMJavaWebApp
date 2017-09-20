package be.pir.am.dao;

import be.pir.am.api.dao.RecordDao;
import be.pir.am.entities.AthleteEntity;
import be.pir.am.entities.EventTypeEntity;
import be.pir.am.entities.RecordEntity;
import be.pir.am.entities.RecordEntity_;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.math.BigDecimal;

@Stateless
public class RecordDaoImpl extends AbstractEntityDao<RecordEntity> implements RecordDao {

    public RecordDaoImpl() {
        super(RecordEntity.class);
    }

    @Override
    public RecordEntity getBestRecordForAthleteAndEventType(AthleteEntity athlete, EventTypeEntity eventType) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<RecordEntity> cq = cb.createQuery(RecordEntity.class);
        Subquery<BigDecimal> subquery = cq.subquery(BigDecimal.class);
        Root<RecordEntity> subrecord = subquery.from(RecordEntity.class);
        subquery.select(cb.min(subrecord.get(RecordEntity_.value))).where(cb.equal(subrecord.get("athlete"), athlete),
                cb.equal(subrecord.get("eventtype"), eventType));

        Root<RecordEntity> record = cq.from(RecordEntity.class);
        cq.where(cb.equal(record.get("athlete"), athlete), cb.equal(record.get("eventtype"), eventType),
                cb.equal(record.get("value"), subquery));
        try {
            return em.createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }
}
