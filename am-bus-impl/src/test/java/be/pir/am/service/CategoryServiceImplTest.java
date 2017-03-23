package be.pir.am.service;

import be.pir.am.api.dto.CategoryDto;
import be.pir.am.entities.*;
import com.bm.testsuite.BaseSessionBeanFixture;
import junit.framework.Assert;

import java.util.List;

public class CategoryServiceImplTest extends BaseSessionBeanFixture<CategoryServiceImpl> {
    private static final Class<?>[] usedBeans = {AthleteEntity.class, CountryEntity.class, LicenseEntity.class,
            TeamEntity.class, FederationEntity.class, CategoryEntity.class, CompetitionEntity.class,
            CompetitorEntity.class, EventEntity.class, ParticipationEntity.class, RoundEntity.class};

    /**
     * Constructor.
     */
    public CategoryServiceImplTest() {
        super(CategoryServiceImpl.class, usedBeans);
    }

    /**
     * Test the dpendency injection.
     *
     * @author Daniel Wiese
     * @since 08.11.2005
     */
    public void testDependencyInjectionWithMethodInvokation() {
        final CategoryServiceImpl toTest = this.getBeanToTest();
        List<CategoryDto> findAthletesByBib = toTest.getCategoriesForLbfa();
        Assert.assertEquals(6, findAthletesByBib.size());
    }

}
