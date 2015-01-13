package be.pir.am.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import be.pir.am.api.dao.LicenceDao;
import be.pir.am.entities.AthleteEntity;
import be.pir.am.entities.LicenceEntity;

@Stateless
public class LicencenceDaoImpl extends AbstractEntityDao<LicenceEntity>
		implements LicenceDao {

	public LicencenceDaoImpl() {
		super(LicenceEntity.class);
	}

	@Override
	public List<AthleteEntity> findAthletesWithBib(String bib) {
		EntityManager em = this.getEntityManager();
		TypedQuery<AthleteEntity> query = em
				.createQuery(
						"select distinct a from LicenceEntity l inner join l.athlete a where l.bib like :bib",
						AthleteEntity.class);
		query.setParameter("bib", bib);
		return query.getResultList();
	}

}
