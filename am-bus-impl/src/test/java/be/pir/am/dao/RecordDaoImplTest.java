package be.pir.am.dao;

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
import be.pir.am.entities.RecordEntity;
import be.pir.am.entities.RoundEntity;
import be.pir.am.entities.TeamEntity;

import com.bm.testsuite.BaseSessionBeanFixture;

public class RecordDaoImplTest extends BaseSessionBeanFixture<RecordDaoImpl> {
	private static final Class<?>[] usedBeans = { AthleteEntity.class, CountryEntity.class, LicenseEntity.class,
			TeamEntity.class, FederationEntity.class, CategoryEntity.class, CompetitionEntity.class,
			CompetitorEntity.class, EventEntity.class, ParticipationEntity.class, RoundEntity.class,
			EventTypeEntity.class, RecordEntity.class };

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
