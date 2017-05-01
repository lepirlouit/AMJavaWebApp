package be.pir.am.dao;

import be.pir.am.api.dao.FederationDao;
import be.pir.am.entities.FederationEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
public class FederationDaoImpl extends AbstractEntityDao<FederationEntity>
        implements FederationDao {

    public FederationDaoImpl() {
        super(FederationEntity.class);
    }

    @Override
    public FederationEntity getByCode(String code) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<FederationEntity> cq = cb.createQuery(FederationEntity.class);
        Root<FederationEntity> federation = cq.from(FederationEntity.class);

        cq.where(cb.equal(federation.get("code"), code));
        return em.createQuery(cq).getSingleResult();
    }

}
