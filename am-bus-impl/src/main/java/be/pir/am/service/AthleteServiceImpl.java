package be.pir.am.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import be.pir.am.api.dao.AthleteDao;
import be.pir.am.api.dao.CategoryDao;
import be.pir.am.api.dao.CompetitorDao;
import be.pir.am.api.dao.EventDao;
import be.pir.am.api.dao.LicenseDao;
import be.pir.am.api.dao.ParticipationDao;
import be.pir.am.api.dao.RecordDao;
import be.pir.am.api.dao.TeamDao;
import be.pir.am.api.dto.AthleteDto;
import be.pir.am.api.dto.CategoryDto;
import be.pir.am.api.dto.CompetitionDto;
import be.pir.am.api.dto.EventDto;
import be.pir.am.api.dto.TeamDto;
import be.pir.am.api.service.AthleteService;
import be.pir.am.entities.AthleteEntity;
import be.pir.am.entities.CategoryEntity;
import be.pir.am.entities.CompetitionEntity;
import be.pir.am.entities.CompetitorEntity;
import be.pir.am.entities.EventEntity;
import be.pir.am.entities.FederationEntity;
import be.pir.am.entities.LicenseEntity;
import be.pir.am.entities.ParticipationEntity;
import be.pir.am.entities.RecordEntity;
import be.pir.am.entities.RoundEntity;
import be.pir.am.entities.TeamEntity;

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
	@EJB
	private CategoryDao categoryDao;
	@EJB
	private ParticipationDao participationDao;
	@EJB
	private TeamDao teamDao;
	@EJB
	private RecordDao recordDao;

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
		if (maximumAge == 0)
			maximumAge = 99;
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
		AthleteDto athDto = new AthleteDto(athlete.getFirstname(), athlete.getLastname());
		athDto.setBirthdate(athlete.getBirthdate());
		athDto.setLicenseId(license.getId());
		athDto.setId(license.getAthlete().getId());
		athDto.setBib(license.getBib());
		athDto.setTeam(license.getTeam().getName());
		athDto.setTeamShort(license.getTeam().getAbbreviation());
		athDto.setGender(athlete.getGender());
		return athDto;
	}

	@Override
	public List<EventDto> findEventsForAthlete(AthleteDto athlete, CompetitionDto competition) {
		SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
		CompetitorEntity competitor = competitorDao.findCompetitor(athlete, competition);
		AthleteEntity athleteEntity = athleteDao.findById(athlete.getId());
		LocalDate endOfYear = LocalDate.of(LocalDate.now().getYear(), 12, 31);
		LocalDate birthday = athleteEntity.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		Period p = Period.between(birthday, endOfYear);

		List<CategoryEntity> categories = categoryDao.findCategoriesByGenderFederationAndAge(athleteEntity.getGender(),
				new FederationEntity(competition.getFederationId()), (short) p.getYears());
		List<EventEntity> events = eventDao.findEventsByCategoryAndCompetition(
				new CompetitionEntity(competition.getId()), categories);
		Set<RoundEntity> rounds = new HashSet<>();
		List<EventDto> returnedList = new ArrayList<>();
		if (competitor != null) {
			for (ParticipationEntity participation : competitor.getParticipations()) {
				//participation can sometime be null because seqno starts from 1
				if (participation != null) {
					rounds.add(participation.getRound());
				}
			}
		}
		Collections.sort(events, new Comparator<EventEntity>() {

			@Override
			public int compare(EventEntity o1, EventEntity o2) {
				return o1.getRounds().get(0).getTimeScheduled().compareTo(o2.getRounds().get(0).getTimeScheduled());
			}
		});
		
		for (EventEntity event : events) {
			EventDto e = new EventDto();
			e.setId(event.getId());
			boolean needRecord = event.getEventType().getDistance() > 5;
			e.setNeedRecord(needRecord);
			RecordEntity record = null;
			if (needRecord) {
				record = recordDao.getBestRecordForAthleteAndEventType(athleteEntity, event.getEventType());
				if (record != null) {
					e.setRecord(record.getValue());
					e.setRecordId(record.getId());
				}
			}
			boolean contains = false;
			for (RoundEntity round : event.getRounds()) {
				e.setName(stf.format(round.getTimeScheduled()) + " - " + event.getName());
				if (rounds.contains(round)) {
					contains = true;
					break;
				}
			}
			e.setChecked(contains);
			returnedList.add(e);
		}
		return returnedList;
	}

	@Override
	public void subscribeAthleteToEvents(AthleteDto athlete, List<EventDto> events, CategoryDto category,
			CompetitionDto competition) {
		CompetitorEntity competitor = competitorDao.findCompetitorFetchParticipationsAndRounds(athlete, competition);
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
				ParticipationEntity participation = findParticipation(competitor, round);
				if (participation == null) {
					participation = new ParticipationEntity();
					participation.setCategoryEntity(new CategoryEntity(category.getId()));
					participation.setRound(round);
					participation.setCompetitors(Arrays.asList(competitor));
				}
				if (event.isChecked()) {
					if (!competitor.getParticipations().contains(participation)) {
						competitor.getParticipations().add(participation);
					}
					if (event.isNeedRecord()) {

						if ((event.getRecord() == null) || BigDecimal.ZERO.equals(event.getRecord())) {
							//remove all previous records

						} else {
							if ((event.getRecordId() == null)
									|| (event.getRecord().compareTo(recordDao.findById(event.getRecordId()).getValue()) != 0)) {
								//create new record
								RecordEntity newrecord = new RecordEntity();
								newrecord.setDate(new Date());
								newrecord.setAthlete(competitor.getAthlete());
								newrecord.setEventtype(eventEntity.getEventType());
								newrecord.setValue(event.getRecord());
								recordDao.save(newrecord);
							}
						}
					}
				} else {
					competitor.getParticipations().remove(participation);
					if (participation.getId() != null) {
						participationDao.delete(participation);
					}
				}
			}
		}
		if (competitor.getParticipations().size() == 0 && competitor.getId() != null) {
			competitorDao.delete(competitor);
		} else {
			//TODO send mail to competitor

			competitorDao.save(competitor);
		}
	}

	private ParticipationEntity findParticipation(CompetitorEntity competitor, RoundEntity round) {
		for (ParticipationEntity participation : competitor.getParticipations()) {
			if ((participation != null) && round.equals(participation.getRound())) {
				return participation;
			}
		}
		return null;
	}

	@Override
	public List<TeamDto> listAllTeams() {
		List<TeamDto> returnedList = new ArrayList<>();
		List<TeamEntity> allTeams = teamDao.findAll();
		for (TeamEntity team : allTeams) {
			returnedList.add(new TeamDto(team.getId(),team.getAbbreviation(), team.getName()));
		}
		
		return returnedList;
	}

}
