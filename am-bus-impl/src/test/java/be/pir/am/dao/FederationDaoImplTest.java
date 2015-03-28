package be.pir.am.dao;

import junit.framework.Assert;
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

public class FederationDaoImplTest extends BaseSessionBeanFixture<FederationDaoImpl> {
	private static final Class<?>[] usedBeans = { AthleteEntity.class, CountryEntity.class, LicenseEntity.class,
			TeamEntity.class, FederationEntity.class, CategoryEntity.class, CompetitionEntity.class,
			CompetitorEntity.class, EventEntity.class, ParticipationEntity.class, RoundEntity.class };

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
