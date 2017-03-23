package be.pir.am.dao;

import be.pir.am.api.dto.AthleteDto;
import be.pir.am.api.dto.CompetitionDto;
import be.pir.am.entities.*;
import com.bm.testsuite.BaseSessionBeanFixture;
import org.junit.Assert;

import java.util.List;

public class CompetitorDaoImplTest extends BaseSessionBeanFixture<CompetitorDaoImpl> {
    private static final Class<?>[] usedBeans = {AthleteEntity.class, CountryEntity.class, LicenseEntity.class,
            TeamEntity.class, FederationEntity.class, CategoryEntity.class, CompetitionEntity.class,
            CompetitorEntity.class, EventEntity.class, ParticipationEntity.class, RoundEntity.class,
            EventTypeEntity.class};

    /**
     * Constructor.
     */
    public CompetitorDaoImplTest() {
        super(CompetitorDaoImpl.class, usedBeans);
    }

    public void testFindNullCompetitor() {
        final CompetitorDaoImpl toTest = this.getBeanToTest();
        AthleteDto athlete = new AthleteDto();
        athlete.setId(404);
        CompetitionDto competition = new CompetitionDto();
        competition.setId(404);
        CompetitorEntity competitor = toTest.findCompetitor(athlete, competition);
        Assert.assertNull(competitor);
    }

    public void testFindCompetitor() {
        final CompetitorDaoImpl toTest = this.getBeanToTest();
        AthleteDto athlete = new AthleteDto();
        athlete.setId(1073164);
        CompetitionDto competition = new CompetitionDto();
        competition.setId(71);
        CompetitorEntity competitor = toTest.findCompetitor(athlete, competition);
        Assert.assertNotNull(competitor);
    }

    public void testFindAllParticipations() {
        final CompetitorDaoImpl toTest = this.getBeanToTest();
        CompetitionEntity competition = new CompetitionEntity(71);

        List<CompetitorEntity> allParticipations = toTest
                .getAllCompetitorsFetchParticipationsRoundsCategoriesEvents(competition);

        for (CompetitorEntity competitorEntity : allParticipations) {
            System.out.println(competitorEntity.getDisplayname());
        }
        Assert.assertEquals(2, allParticipations.size());
    }
}
