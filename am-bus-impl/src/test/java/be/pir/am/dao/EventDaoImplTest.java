package be.pir.am.dao;

import be.pir.am.entities.*;
import com.bm.testsuite.BaseSessionBeanFixture;
import junit.framework.Assert;

import java.util.Arrays;
import java.util.List;

public class EventDaoImplTest extends BaseSessionBeanFixture<EventDaoImpl> {
    private static final Class<?>[] usedBeans = {AthleteEntity.class, CountryEntity.class, LicenseEntity.class,
            TeamEntity.class, FederationEntity.class, CategoryEntity.class, CompetitionEntity.class,
            CompetitorEntity.class, EventEntity.class, ParticipationEntity.class, RoundEntity.class,
            EventTypeEntity.class};

    /**
     * Constructor.
     */
    public EventDaoImplTest() {
        super(EventDaoImpl.class, usedBeans);
    }

    public void testFindEventsByCategoryAndCompetition() {
        final EventDaoImpl toTest = this.getBeanToTest();
        CompetitionEntity competition = new CompetitionEntity();
        competition.setId(71);

        CategoryEntity category = new CategoryEntity();
        category.setId(58);

        List<EventEntity> findAthletesByBib = toTest.findEventsByCategoryAndCompetition(competition,
                Arrays.asList(category));
        for (EventEntity eventEntity : findAthletesByBib) {
            System.out.println(eventEntity.getName());
        }
        Assert.assertEquals(1, findAthletesByBib.size());
    }

}
