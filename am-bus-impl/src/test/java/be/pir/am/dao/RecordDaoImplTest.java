package be.pir.am.dao;

import be.pir.am.entities.*;
import com.bm.testsuite.BaseSessionBeanFixture;

public class RecordDaoImplTest extends BaseSessionBeanFixture<RecordDaoImpl> {
    private static final Class<?>[] usedBeans = {AthleteEntity.class, CountryEntity.class, LicenseEntity.class,
            TeamEntity.class, FederationEntity.class, CategoryEntity.class, CompetitionEntity.class,
            CompetitorEntity.class, EventEntity.class, ParticipationEntity.class, RoundEntity.class,
            EventTypeEntity.class, RecordEntity.class};

    /**
     * Constructor.
     */
    public RecordDaoImplTest() {
        super(RecordDaoImpl.class, usedBeans);
    }

    public void testfindRecords() {
        final RecordDaoImpl toTest = this.getBeanToTest();
        AthleteEntity athlete = new AthleteEntity(858810);
        EventTypeEntity eventType = new EventTypeEntity(139);

        RecordEntity bestRecord = toTest.getBestRecordForAthleteAndEventType(athlete, eventType);
        System.out.println(bestRecord.getValue());
    }
}
