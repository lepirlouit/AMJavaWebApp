package be.pir.am.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import be.pir.am.api.dao.AthleteDao;
import be.pir.am.entities.AthleteEntity;
import be.pir.am.entities.LicenceEntity;

@Stateless
public class AthleteDaoImpl extends AbstractEntityDao<AthleteEntity> implements
		AthleteDao {

	public AthleteDaoImpl() {
		super(AthleteEntity.class);
	}

	@Override
	public List<AthleteEntity> findAthleteByBibAndBirthdayMinMax(String bib, Date dateMin, Date dateMax) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<AthleteEntity> cq = cb.createQuery(AthleteEntity.class);
		Root<LicenceEntity> licence = cq.from(LicenceEntity.class);
		Join<Object, Object> athlete = licence.join("athlete");
		cq.select(licence.get("athlete"));
		cq.where(cb.between(athlete.get("birthdate"), dateMin, dateMax), cb.equal(licence.get("bib"), bib));
		return em.createQuery(cq).getResultList();
	}

}
