package be.pir.am.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import be.pir.am.api.dao.CompetitorDao;
import be.pir.am.api.dto.AthleteDto;
import be.pir.am.api.dto.CompetitionDto;
import be.pir.am.entities.AthleteEntity;
import be.pir.am.entities.CompetitionEntity;
import be.pir.am.entities.CompetitorEntity;

@Stateless
public class CompetitorDaoImpl extends AbstractEntityDao<CompetitorEntity> implements CompetitorDao {

	public CompetitorDaoImpl() {
		super(CompetitorEntity.class);
	}

	@Override
	public CompetitorEntity findCompetitor(AthleteDto athlete, CompetitionDto competition) {
		CompetitionEntity competitionEntity = new CompetitionEntity(competition.getId());
		AthleteEntity athleteEntity = new AthleteEntity(athlete.getId());
		
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<CompetitorEntity> cq = cb.createQuery(CompetitorEntity.class);
		Root<CompetitorEntity> competitor = cq.from(CompetitorEntity.class);

		cq.where(cb.equal(competitor.get("competition"), competitionEntity), cb.equal(competitor.get("athlete"), athleteEntity));
		try{
			return em.createQuery(cq).getSingleResult();
		}catch(NoResultException e){
			return null;
		}
	}

}
