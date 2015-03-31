package be.pir.am.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import be.pir.am.api.dao.AthleteDao;
import be.pir.am.api.dao.CompetitorDao;
import be.pir.am.api.dao.EventDao;
import be.pir.am.api.dao.LicenseDao;
import be.pir.am.api.dto.AthleteDto;
import be.pir.am.api.dto.CategoryDto;
import be.pir.am.api.dto.CompetitionDto;
import be.pir.am.api.dto.EventDto;
import be.pir.am.api.service.AthleteService;
import be.pir.am.entities.AthleteEntity;
import be.pir.am.entities.CategoryEntity;
import be.pir.am.entities.CompetitionEntity;
import be.pir.am.entities.CompetitorEntity;
import be.pir.am.entities.EventEntity;
import be.pir.am.entities.LicenseEntity;
import be.pir.am.entities.ParticipationEntity;
import be.pir.am.entities.RoundEntity;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AthleteServiceImpl implements AthleteService {
	@EJB
	private AthleteDao athleteDao;
	@EJB
	private LicenseDao licenseDao;
	@EJB
	private EventDao eventDao;
	@EJB
	private CompetitorDao competitorDao;

	@Override
	public List<AthleteDto> findAthletesByBib(int bib) {

		List<AthleteDto> returnedList = new ArrayList<>();
		List<LicenseEntity> findAthletesWithBib = licenseDao.findAthletesWithBib(String.valueOf(bib));
		for (LicenseEntity athleteEntity : findAthletesWithBib) {
			returnedList.add(createAthleteDto(athleteEntity));
		}

		return returnedList;
	}

	@Override
	public List<AthleteDto> findAthletesByBibAndCategory(int bib, CategoryDto category) {
		List<AthleteDto> returnedList = new ArrayList<>();
		Short minimumAge = category.getMinimumAge();
		Short maximumAge = category.getMaximumAge();
		Date dateMin = Date.from(LocalDate.of(LocalDate.now().getYear() - maximumAge, 1, 1).atStartOfDay()
				.atZone(ZoneId.systemDefault()).toInstant());
		Date dateMax = Date.from(LocalDate.of(LocalDate.now().getYear() - minimumAge, 1, 1).atStartOfDay()
				.atZone(ZoneId.systemDefault()).toInstant());

		List<LicenseEntity> results = athleteDao.findAthleteByBibGenderAndBirthdayMinMax(String.valueOf(bib),
				category.getGender(), dateMin, dateMax);
		for (LicenseEntity athleteEntity : results) {
			returnedList.add(createAthleteDto(athleteEntity));
		}
		return returnedList;
	}

	private AthleteDto createAthleteDto(LicenseEntity license) {
		AthleteEntity athlete = license.getAthlete();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		AthleteDto athDto = new AthleteDto(athlete.getFirstname(), athlete.getLastname());
		athDto.setBirthdate(sdf.format(athlete.getBirthdate()));
		athDto.setLicenseId(license.getId());
		athDto.setId(license.getAthlete().getId());
		athDto.setBib(license.getBib());
		athDto.setTeam(license.getTeam().getName());
		athDto.setTeamShort(license.getTeam().getAbbreviation());

		return athDto;
	}

	@Override
	public List<EventDto> findEventsForAthlete(AthleteDto athlete, CompetitionDto competition) {
		CompetitorEntity competitor = competitorDao.findCompetitor(athlete, competition);
		for (ParticipationEntity participation : competitor.getParticipations()) {
			//			participation.getRound().get
		}
		return null;
	}

	@Override
	public void subscribeAthleteToEvents(AthleteDto athlete, List<EventDto> events, CategoryDto category,
			CompetitionDto competition) {
		CompetitorEntity competitor = competitorDao.findCompetitor(athlete, competition);
		if (competitor == null) {
			competitor = new CompetitorEntity();
			competitor.setAthlete(new AthleteEntity(athlete.getId()));
			competitor.setParticipations(new ArrayList<>());
			competitor.setCompetition(new CompetitionEntity(competition.getId()));
			competitor.setBib(athlete.getBib());
			competitor.setLicense(new LicenseEntity(athlete.getLicenseId()));
			competitor.setDisplayname(athlete.getLastName() + ", " + athlete.getFirstName());
		}
		for (EventDto event : events) {
			EventEntity eventEntity = eventDao.findById(event.getId());
			for (RoundEntity round : eventEntity.getRounds()) {
				ParticipationEntity participation = new ParticipationEntity();
				participation.setCategoryEntity(new CategoryEntity(category.getId()));
				participation.setRound(round);
				competitor.getParticipations().add(participation);
			}
		}
		competitorDao.save(competitor);
	}

}
