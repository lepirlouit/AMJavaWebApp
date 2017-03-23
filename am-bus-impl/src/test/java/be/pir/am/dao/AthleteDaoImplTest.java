package be.pir.am.dao;

import be.pir.am.entities.*;
import com.bm.testsuite.BaseSessionBeanFixture;
import junit.framework.Assert;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class AthleteDaoImplTest extends BaseSessionBeanFixture<AthleteDaoImpl> {
    private static final Class<?>[] usedBeans = {AthleteEntity.class, CountryEntity.class, LicenseEntity.class,
            TeamEntity.class, FederationEntity.class, CategoryEntity.class, CompetitionEntity.class,
            CompetitorEntity.class, EventEntity.class, ParticipationEntity.class, RoundEntity.class,
            EventTypeEntity.class, RecordEntity.class};

    /**
     * Constructor.
     */
    public AthleteDaoImplTest() {
        super(AthleteDaoImpl.class, usedBeans);
    }

    public void testFindAthleteByBibAndBirthdayMinMax() {
        final AthleteDaoImpl toTest = this.getBeanToTest();

        Date min = Date.from(LocalDate.of(1989, 11, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date max = Date.from(LocalDate.of(1989, 11, 30).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        List<LicenseEntity> findAthletesByBib = toTest.findAthleteByBibGenderAndBirthdayMinMax("927", 'F', min, max);
        for (LicenseEntity athlete : findAthletesByBib) {
            System.out.println(athlete.getAthlete().getLastname());
        }
        Assert.assertEquals(1, findAthletesByBib.size());
    }
}
