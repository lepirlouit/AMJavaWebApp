package be.pir.am.dao;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import be.pir.am.entities.AthleteEntity;
import be.pir.am.entities.CategoryEntity;
import be.pir.am.entities.CompetitionEntity;
import be.pir.am.entities.CompetitorEntity;
import be.pir.am.entities.CountryEntity;
import be.pir.am.entities.EventEntity;
import be.pir.am.entities.EventTypeEntity;
import be.pir.am.entities.FederationEntity;
import be.pir.am.entities.LicenseEntity;
import be.pir.am.entities.ParticipationEntity;
import be.pir.am.entities.RoundEntity;
import be.pir.am.entities.TeamEntity;

import com.bm.testsuite.BaseSessionBeanFixture;

public class EventDaoImplTest extends BaseSessionBeanFixture<EventDaoImpl> {
	private static final Class<?>[] usedBeans = { AthleteEntity.class, CountryEntity.class, LicenseEntity.class,
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
