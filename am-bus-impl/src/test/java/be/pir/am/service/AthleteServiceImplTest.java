package be.pir.am.service;

import be.pir.am.api.dto.AthleteDto;
import be.pir.am.api.dto.CategoryDto;
import be.pir.am.api.dto.CompetitionDto;
import be.pir.am.api.dto.EventDto;
import be.pir.am.entities.*;
import com.bm.testsuite.BaseSessionBeanFixture;
import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;

import java.util.Collections;
import java.util.List;

public class AthleteServiceImplTest extends BaseSessionBeanFixture<AthleteServiceImpl> {
    private static final Class<?>[] usedBeans = {AthleteEntity.class, CountryEntity.class, LicenseEntity.class,
            TeamEntity.class, FederationEntity.class, CategoryEntity.class, CompetitionEntity.class,
            CompetitorEntity.class, EventEntity.class, ParticipationEntity.class, RoundEntity.class,
            EventTypeEntity.class};

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
        LocalDate res = LocalDate.fromDateFields(athlete.getBirthdate());
        LocalDate now = new LocalDate(
                LocalDateTime.fromDateFields(competition.getStartDate())
                        .getYear(), 12, 31);
        long years = Period.fieldDifference(res, now).getYears();

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
        athlete.setBirthdate(new LocalDate(1989,11,15).toDate());
        athlete.setGender('F');

        CompetitionDto competition = new CompetitionDto();
        competition.setId(1);
        competition.setFederationId(10);
        EventDto event = new EventDto();
        event.setId(21);
        CategoryDto category = new CategoryDto();
        category.setId(58);

        final AthleteServiceImpl toTest = this.getBeanToTest();

        toTest.subscribeAthleteToEvents(athlete, Collections.singletonList(event), category, competition);

    }

    public void testFindAllCompetitors() {
        CompetitionDto competition = new CompetitionDto();
        competition.setId(71);
        final AthleteServiceImpl toTest = this.getBeanToTest();
        List<EventDto> allParticipations = toTest.getAllParticipations(competition);
        for (EventDto eventDto : allParticipations) {
            System.out.println(eventDto);
            for (AthleteDto athleteDto : eventDto.getParticipants()) {
                System.out.println(athleteDto);
            }
        }
    }

}
