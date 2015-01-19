package be.pir.am.dao;

import java.util.List;

import junit.framework.Assert;
import be.pir.am.entities.AthleteEntity;
import be.pir.am.entities.CategoryEntity;
import be.pir.am.entities.CompetitionEntity;
import be.pir.am.entities.CompetitorEntity;
import be.pir.am.entities.CountryEntity;
import be.pir.am.entities.EventEntity;
import be.pir.am.entities.FederationEntity;
import be.pir.am.entities.LicenceEntity;
import be.pir.am.entities.ParticipationEntity;
import be.pir.am.entities.RoundEntity;
import be.pir.am.entities.TeamEntity;

import com.bm.testsuite.BaseSessionBeanFixture;

public class CategoryDaoImplTest extends BaseSessionBeanFixture<CategoryDaoImpl> {
	private static final Class<?>[] usedBeans = { AthleteEntity.class, CountryEntity.class, LicenceEntity.class,
			TeamEntity.class, FederationEntity.class, CategoryEntity.class, CompetitionEntity.class,
			CompetitorEntity.class, EventEntity.class, ParticipationEntity.class, RoundEntity.class };

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
			Assert.assertEquals(1, findAthletesByBib.size());
		}
		for (short i = 6; i < 100; i++) {
			List<CategoryEntity> findAthletesByBib = toTest.findCategoriesByGenderFederationAndAge('M', federation, i);
			Assert.assertEquals(1, findAthletesByBib.size());
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
