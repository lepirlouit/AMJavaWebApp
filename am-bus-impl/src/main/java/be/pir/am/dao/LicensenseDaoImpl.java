package be.pir.am.dao;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import be.pir.am.api.dao.LicenseDao;
import be.pir.am.entities.LicenseEntity;
import be.pir.am.entities.LicenseEntity_;

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
		try{
			return query.getResultList();
		}catch(NoResultException e){
			return Collections.emptyList();
		}
	}

	@Override
	public List<LicenseEntity> findAllWithAthletesFetch() {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<LicenseEntity> cq = cb.createQuery(LicenseEntity.class);
		Root<LicenseEntity> licence = cq.from(LicenseEntity.class);
		/*Fetch<LicenseEntity, AthleteEntity> athlete = */licence.fetch(
				LicenseEntity_.athlete, JoinType.LEFT);
		try{
			return em.createQuery(cq).getResultList();
		}catch(NoResultException e){
			return Collections.emptyList();
		}
	}
}
