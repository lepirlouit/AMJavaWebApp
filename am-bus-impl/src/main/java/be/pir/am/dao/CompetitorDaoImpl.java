package be.pir.am.dao;

import be.pir.am.api.dao.CompetitorDao;
import be.pir.am.api.dto.AthleteDto;
import be.pir.am.api.dto.CompetitionDto;
import be.pir.am.entities.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.util.List;

@Stateless
public class CompetitorDaoImpl extends AbstractEntityDao<CompetitorEntity> implements CompetitorDao {

    public CompetitorDaoImpl() {
        super(CompetitorEntity.class);
    }

    @Override
    public CompetitorEntity findCompetitor(AthleteDto athlete, CompetitionDto competition) {
        return getFindCompitorRequest(athlete, competition, false);
    }

    private CompetitorEntity getFindCompitorRequest(AthleteDto athlete, CompetitionDto competition, boolean fetch) {
        CompetitionEntity competitionEntity = new CompetitionEntity(competition.getId());
        AthleteEntity athleteEntity = new AthleteEntity(athlete.getId());

        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<CompetitorEntity> cq = cb.createQuery(CompetitorEntity.class);
        Root<CompetitorEntity> competitor = cq.from(CompetitorEntity.class);
        Fetch<CompetitorEntity, ParticipationEntity> participations = competitor.fetch(
                CompetitorEntity_.participations, JoinType.LEFT);
        participations.fetch(ParticipationEntity_.round, JoinType.LEFT);
        cq.where(cb.equal(competitor.get(CompetitorEntity_.competition), competitionEntity),
                cb.equal(competitor.get(CompetitorEntity_.athlete), athleteEntity));
        try {
            return em.createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public CompetitorEntity findCompetitorFetchParticipationsAndRounds(AthleteDto athlete, CompetitionDto competition) {
        return getFindCompitorRequest(athlete, competition, true);
    }

    @Override
    public List<CompetitorEntity> getAllCompetitorsFetchParticipationsRoundsCategoriesEvents(
            CompetitionEntity competition) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CompetitorEntity> cq = cb.createQuery(CompetitorEntity.class);
        Root<CompetitorEntity> competitor = cq.from(CompetitorEntity.class);
        Fetch<CompetitorEntity, LicenseEntity> license = competitor.fetch(CompetitorEntity_.license, JoinType.LEFT);
        license.fetch(LicenseEntity_.team, JoinType.LEFT);
        Fetch<CompetitorEntity, ParticipationEntity> participation = competitor.fetch(CompetitorEntity_.participations);
        participation.fetch(ParticipationEntity_.category);
        Fetch<ParticipationEntity, RoundEntity> round = participation.fetch(ParticipationEntity_.round);
        round.fetch(RoundEntity_.event);

        return em.createQuery(cq.distinct(true)).getResultList();
    }
}
