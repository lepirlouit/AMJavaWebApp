package be.pir.am.dao;

import be.pir.am.api.dao.LicenseDao;
import be.pir.am.entities.LicenseEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class LicensenseDaoImpl extends AbstractEntityDao<LicenseEntity>
        implements LicenseDao {

    public LicensenseDaoImpl() {
        super(LicenseEntity.class);
    }

    @Override
    public List<LicenseEntity> findAthletesWithBib(String bib) {
        EntityManager em = this.getEntityManager();
        TypedQuery<LicenseEntity> query = em
                .createQuery(
                        "select l from LicenseEntity l inner join l.athlete a where l.bib like :bib",
                        LicenseEntity.class);
        query.setParameter("bib", bib);
        return query.getResultList();
    }

}
