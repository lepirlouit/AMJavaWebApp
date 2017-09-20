package be.pir.am.dao;

import be.pir.am.api.dao.CountryDao;
import be.pir.am.entities.CountryEntity;
import be.pir.am.entities.CountryEntity_;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
public class CountryDaoImpl extends AbstractEntityDao<CountryEntity> implements
        CountryDao {

    public CountryDaoImpl() {
        super(CountryEntity.class);
    }

    @Override
    public CountryEntity getByIso3(String iso3) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<CountryEntity> cq = cb.createQuery(CountryEntity.class);
        Root<CountryEntity> event = cq.from(CountryEntity.class);
        cq.where(cb.equal(event.get(CountryEntity_.iso3), iso3));
        return em.createQuery(cq).getSingleResult();
    }
}
