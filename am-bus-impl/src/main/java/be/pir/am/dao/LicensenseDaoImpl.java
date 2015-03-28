package be.pir.am.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import be.pir.am.api.dao.LicenseDao;
import be.pir.am.entities.AthleteEntity;
import be.pir.am.entities.LicenseEntity;

@Stateless
public class LicensenseDaoImpl extends AbstractEntityDao<LicenseEntity>
		implements LicenseDao {

	public LicensenseDaoImpl() {
		super(LicenseEntity.class);
	}

	@Override
	public List<AthleteEntity> findAthletesWithBib(String bib) {
		EntityManager em = this.getEntityManager();
		TypedQuery<AthleteEntity> query = em
				.createQuery(
						"select distinct a from LicenseEntity l inner join l.athlete a where l.bib like :bib",
						AthleteEntity.class);
		query.setParameter("bib", bib);
		return query.getResultList();
	}

}
