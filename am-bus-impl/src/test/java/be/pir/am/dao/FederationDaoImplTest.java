package be.pir.am.dao;

import be.pir.am.entities.*;
import com.bm.testsuite.BaseSessionBeanFixture;
import junit.framework.Assert;

public class FederationDaoImplTest extends BaseSessionBeanFixture<FederationDaoImpl> {
    private static final Class<?>[] usedBeans = {AthleteEntity.class, CountryEntity.class, LicenseEntity.class,
            TeamEntity.class, FederationEntity.class, CategoryEntity.class, CompetitionEntity.class,
            CompetitorEntity.class, EventEntity.class, ParticipationEntity.class, RoundEntity.class};

    /**
     * Constructor.
     */
    public FederationDaoImplTest() {
        super(FederationDaoImpl.class, usedBeans);
    }

    public void testMaxOneCategoryForEachAge() {
        final FederationDaoImpl toTest = this.getBeanToTest();

        FederationEntity federation = toTest.getByCode("BEL_LBFA");
        Assert.assertNotNull(federation);
    }
}
