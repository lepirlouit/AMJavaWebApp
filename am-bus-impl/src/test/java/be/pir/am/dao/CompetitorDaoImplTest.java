package be.pir.am.dao;

import org.junit.Assert;

import be.pir.am.api.dto.AthleteDto;
import be.pir.am.api.dto.CompetitionDto;
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

public class CompetitorDaoImplTest extends BaseSessionBeanFixture<CompetitorDaoImpl> {
	private static final Class<?>[] usedBeans = { AthleteEntity.class, CountryEntity.class, LicenseEntity.class,
		TeamEntity.class, FederationEntity.class, CategoryEntity.class, CompetitionEntity.class,
		CompetitorEntity.class, EventEntity.class, ParticipationEntity.class, RoundEntity.class };
	
	/**
	 * Constructor.
	 */
	public CompetitorDaoImplTest() {
		super(CompetitorDaoImpl.class, usedBeans);
	}
	
	public void testFindNullCompetitor(){
		final CompetitorDaoImpl toTest = this.getBeanToTest();
		AthleteDto athlete = new AthleteDto();
		athlete.setId(404);
		CompetitionDto competition = new CompetitionDto();
		competition.setId(404);
		CompetitorEntity competitor = toTest.findCompetitor(athlete, competition);
		Assert.assertNull(competitor);
	}
	
	public void testFindCompetitor(){
		final CompetitorDaoImpl toTest = this.getBeanToTest();
		AthleteDto athlete = new AthleteDto();
		athlete.setId(1073164);
		CompetitionDto competition = new CompetitionDto();
		competition.setId(71);
		CompetitorEntity competitor = toTest.findCompetitor(athlete, competition);
		Assert.assertNotNull(competitor);
	}
}
