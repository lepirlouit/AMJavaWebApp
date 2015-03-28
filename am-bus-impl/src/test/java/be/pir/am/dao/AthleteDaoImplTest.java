package be.pir.am.dao;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

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

public class AthleteDaoImplTest extends BaseSessionBeanFixture<AthleteDaoImpl> {
	private static final Class<?>[] usedBeans = { AthleteEntity.class, CountryEntity.class, LicenseEntity.class,
			TeamEntity.class, FederationEntity.class, CategoryEntity.class, CompetitionEntity.class,
			CompetitorEntity.class, EventEntity.class, ParticipationEntity.class, RoundEntity.class };

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

		List<AthleteEntity> findAthletesByBib = toTest.findAthleteByBibGenderAndBirthdayMinMax("1997", 'W', min, max);
		for (AthleteEntity athlete : findAthletesByBib) {
			System.out.println(athlete.getLastname());
		}
		Assert.assertEquals(1, findAthletesByBib.size());
	}
}
