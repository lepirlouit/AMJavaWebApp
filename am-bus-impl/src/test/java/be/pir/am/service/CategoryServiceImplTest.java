package be.pir.am.service;

import java.util.List;

import junit.framework.Assert;
import be.pir.am.api.dto.CategoryDto;
import be.pir.am.entities.AthleteEntity;
import be.pir.am.entities.CategoryEntity;
import be.pir.am.entities.CompetitionEntity;
import be.pir.am.entities.CompetitorEntity;
import be.pir.am.entities.CountryEntity;
import be.pir.am.entities.EventEntity;
import be.pir.am.entities.FederationEntity;
import be.pir.am.entities.LicenseEntity;
import be.pir.am.entities.ParticipationEntity;
import be.pir.am.entities.RoundEntity;
import be.pir.am.entities.TeamEntity;

import com.bm.testsuite.BaseSessionBeanFixture;

public class CategoryServiceImplTest extends BaseSessionBeanFixture<CategoryServiceImpl> {
	private static final Class<?>[] usedBeans = { AthleteEntity.class, CountryEntity.class, LicenseEntity.class,
			TeamEntity.class, FederationEntity.class, CategoryEntity.class, CompetitionEntity.class,
			CompetitorEntity.class, EventEntity.class, ParticipationEntity.class, RoundEntity.class };

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
