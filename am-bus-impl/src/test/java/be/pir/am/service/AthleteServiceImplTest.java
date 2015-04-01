package be.pir.am.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import be.pir.am.api.dto.AthleteDto;
import be.pir.am.api.dto.CategoryDto;
import be.pir.am.api.dto.CompetitionDto;
import be.pir.am.api.dto.EventDto;
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

public class AthleteServiceImplTest extends BaseSessionBeanFixture<AthleteServiceImpl> {
	private static final Class<?>[] usedBeans = { AthleteEntity.class, CountryEntity.class, LicenseEntity.class,
			TeamEntity.class, FederationEntity.class, CategoryEntity.class, CompetitionEntity.class,
			CompetitorEntity.class, EventEntity.class, ParticipationEntity.class, RoundEntity.class };

	/**
	 * Constructor.
	 */
	public AthleteServiceImplTest() {
		super(AthleteServiceImpl.class, usedBeans);
	}

	/**
	 * Test the dpendency injection.
	 * 
	 * @author Daniel Wiese
	 * @since 08.11.2005
	 */
	public void testDependencyInjectionWithMethodInvokation() {
		final AthleteServiceImpl toTest = this.getBeanToTest();
		List<AthleteDto> findAthletesByBib = toTest.findAthletesByBib(500);
		Assert.assertEquals(12, findAthletesByBib.size());
	}

	public void testFindEvents() {
		CompetitionEntity competition = getEntityManager().find(CompetitionEntity.class, 71);
		Assert.assertNotNull(competition);
		AthleteEntity athlete = getEntityManager().find(AthleteEntity.class, 1026601);
		Assert.assertNotNull(athlete);
		LocalDate res = LocalDateTime.ofInstant(Instant.ofEpochMilli(athlete.getBirthdate().getTime()),
				ZoneId.systemDefault()).toLocalDate();
		LocalDate now = LocalDate.of(
				LocalDateTime
						.ofInstant(Instant.ofEpochMilli(competition.getStartDate().getTime()), ZoneId.systemDefault())
						.toLocalDate().getYear(), 12, 31);
		long years = ChronoUnit.YEARS.between(res, now);

		//get category by Gender, federation, age
		//get competition events by category

		System.out.println(years);
		System.out.println(athlete.getBirthdate());
		System.out.println(competition.getStartDate());
		System.out.println(competition.getFederation().getName());
		System.out.println(competition.getEvents().size());
	}

	public void testFindAthletesByBibAndCategory() {
		final AthleteServiceImpl toTest = this.getBeanToTest();
		CategoryDto category = new CategoryDto();
		category.setMinimumAge((short) 20);
		category.setMaximumAge((short) 35);
		category.setGender('W');

		List<AthleteDto> findAthletesByBib = toTest.findAthletesByBibAndCategory(927, category);
		for (AthleteDto athleteDto : findAthletesByBib) {
			System.out.println(athleteDto.getLastName());
		}
		Assert.assertEquals(2, findAthletesByBib.size());
	}

	public void testSubscribeAthleteToEvents() {
		AthleteDto athlete = new AthleteDto();
		athlete.setId(1026657);//sarah
		athlete.setBib("927");
		athlete.setLicenseId(1026657);
		athlete.setFirstName("UnitTest");
		athlete.setLastName("Ejb3Unit");

		CompetitionDto competition = new CompetitionDto();
		competition.setId(71);
		EventDto event = new EventDto();
		event.setId(3);
		CategoryDto category = new CategoryDto();
		category.setId(58);

		final AthleteServiceImpl toTest = this.getBeanToTest();

		toTest.subscribeAthleteToEvents(athlete, Arrays.asList(event), category, competition);

	}

}
