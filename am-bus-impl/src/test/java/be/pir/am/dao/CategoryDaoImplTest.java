package be.pir.am.dao;

import be.pir.am.entities.*;
import com.bm.testsuite.BaseSessionBeanFixture;
import junit.framework.Assert;

import java.util.List;

public class CategoryDaoImplTest extends BaseSessionBeanFixture<CategoryDaoImpl> {
    private static final Class<?>[] usedBeans = {AthleteEntity.class, CountryEntity.class, LicenseEntity.class,
            TeamEntity.class, FederationEntity.class, CategoryEntity.class, CompetitionEntity.class,
            CompetitorEntity.class, EventEntity.class, ParticipationEntity.class, RoundEntity.class, EventTypeEntity.class};

    /**
     * Constructor.
     */
    public CategoryDaoImplTest() {
        super(CategoryDaoImpl.class, usedBeans);
    }

    public void testMaxOneCategoryForEachAge() {
        final CategoryDaoImpl toTest = this.getBeanToTest();
        FederationEntity federation = new FederationEntity();
        federation.setId(10);
        for (short i = 6; i < 100; i++) {
            List<CategoryEntity> findAthletesByBib = toTest.findCategoriesByGenderFederationAndAge('W', federation, i);
            Assert.assertEquals("error for W age : " + i, 1, findAthletesByBib.size());
        }
        for (short i = 6; i < 100; i++) {
            List<CategoryEntity> findAthletesByBib = toTest.findCategoriesByGenderFederationAndAge('M', federation, i);
            Assert.assertEquals("error for M age : " + i, 1, 1, findAthletesByBib.size());
        }
    }

    public void testFindAllCategoriesForDisplay() {
        final CategoryDaoImpl toTest = this.getBeanToTest();
        FederationEntity federation = new FederationEntity();
        federation.setId(10);
        List<CategoryEntity> categories = toTest.findAllCategoriesForDisplay(federation);
        Assert.assertEquals(18, categories.size());
    }
}
