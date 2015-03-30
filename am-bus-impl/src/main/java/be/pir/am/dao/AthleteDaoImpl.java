package be.pir.am.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import be.pir.am.api.dao.AthleteDao;
import be.pir.am.entities.AthleteEntity;
import be.pir.am.entities.LicenseEntity;

@Stateless
public class AthleteDaoImpl extends AbstractEntityDao<AthleteEntity> implements AthleteDao {

	public AthleteDaoImpl() {
		super(AthleteEntity.class);
	}

	@Override
	public List<LicenseEntity> findAthleteByBibGenderAndBirthdayMinMax(String bib, Character gender, Date dateMin,
			Date dateMax) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<LicenseEntity> cq = cb.createQuery(LicenseEntity.class);
		Root<LicenseEntity> license = cq.from(LicenseEntity.class);
		Join<Object, Object> athlete = license.join("athlete",JoinType.LEFT);
//		cq.select(license.get("athlete"));
		cq.where(cb.between(athlete.get("birthdate"), dateMin, dateMax), cb.equal(athlete.get("gender"), gender),
				cb.equal(license.get("bib"), bib));
		return em.createQuery(cq).getResultList();
	}

}
