package be.pir.am.service;

import be.pir.am.api.dao.*;
import be.pir.am.api.dto.*;
import be.pir.am.api.service.AthleteService;
import be.pir.am.entities.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;

import java.util.*;

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
    private CompetitionDao competitionDao;
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
        Short minimumAge;
        Short maximumAge;
        Character gender;
        if (category == null) {
            minimumAge = 0;
            maximumAge = 100;
            gender = null;
        } else {
            minimumAge = category.getMinimumAge();
            maximumAge = category.getMaximumAge();
            gender = category.getGender();
        }
        if (maximumAge == 0) {
            maximumAge = 99;
        }
        Date dateMin = new LocalDate(LocalDate.now().getYear() - maximumAge, 1, 1).toDate();
        Date dateMax = new LocalDate(LocalDate.now().getYear() - minimumAge, 12, 31).toDate();

        List<LicenseEntity> results = athleteDao.findAthleteByBibGenderAndBirthdayMinMax(String.valueOf(bib),
                gender, dateMin, dateMax);
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

    private AthleteDto createAthleteDto(CompetitorEntity competitor) {
        AthleteEntity athlete = competitor.getAthlete();
        AthleteDto athDto = new AthleteDto(athlete.getFirstname(), athlete.getLastname());
        athDto.setBirthdate(athlete.getBirthdate());
        athDto.setLicenseId(competitor.getId());
        athDto.setId(competitor.getAthlete().getId());
        athDto.setBib(competitor.getBib());
        if (competitor.getLicense() != null) {
            athDto.setTeam(competitor.getLicense().getTeam().getName());
            athDto.setTeamShort(competitor.getLicense().getTeam().getAbbreviation());
        }
        athDto.setGender(athlete.getGender());
        return athDto;
    }

    @Override
    public List<EventDto> findEventsForAthlete(AthleteDto athlete, CompetitionDto competition) {
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
        CompetitorEntity competitor = null;
        if (athlete.getId() != null) {
            competitor = competitorDao.findCompetitor(athlete, competition);
        }
        List<CategoryEntity> categories = getCategoryForAthlete(competition, athlete.getGender(),
                athlete.getBirthdate());
        if (categories.isEmpty()) {
            return Collections.emptyList();
        }
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
            RecordEntity record;
            if (needRecord && (athlete.getId() != null)) {
                record = recordDao.getBestRecordForAthleteAndEventType(new AthleteEntity(athlete.getId()),
                        event.getEventType());
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

    private List<CategoryEntity> getCategoryForAthlete(CompetitionDto competition, Character gender, Date birthDate) {
        LocalDate endOfYear = new LocalDate(LocalDate.now().getYear(), 12, 31);
        //TODO : in place of currentDate, take the end of year of the competition date
        LocalDate birthday = LocalDate.fromDateFields(birthDate);

        Period p = Period.fieldDifference(birthday, endOfYear);

        return categoryDao.findCategoriesByGenderFederationAndAge(gender,
                new FederationEntity(competition.getFederationId()), (short) p.getYears());
    }

    @Override
    public void subscribeAthleteToEvents(AthleteDto athlete, List<EventDto> events, CategoryDto category,
                                         CompetitionDto competition) {
        CompetitorEntity competitor = athlete.getId() == null ? null : competitorDao
                .findCompetitorFetchParticipationsAndRounds(athlete, competition);
        if (competitor == null) {
            competitor = new CompetitorEntity();
            if (athlete.getId() != null) {
                competitor.setAthlete(athleteDao.findById(athlete.getId()));
            } else {
                AthleteEntity athleteEntity = new AthleteEntity();
                athleteEntity.setFirstname(athlete.getFirstName());
                athleteEntity.setLastname(athlete.getLastName());
                athleteEntity.setGender(athlete.getGender());
                athleteEntity.setBirthdate(athlete.getBirthdate());
                athleteEntity.setNationality(new CountryEntity(2032));
                athleteDao.save(athleteEntity);
                athlete.setId(athleteEntity.getId());
                competitor.setAthlete(athleteEntity);
            }
            competitor.setParticipations(new ArrayList<ParticipationEntity>());
            competitor.setCompetition(new CompetitionEntity(competition.getId()));
            competitor.setBib(athlete.getBib());
            if (athlete.getLicenseId() != 0)
                competitor.setLicense(new LicenseEntity(athlete.getLicenseId()));
            competitor.setDisplayname(athlete.getLastName() + ", " + athlete.getFirstName());
        }
        for (EventDto event : events) {
            EventEntity eventEntity = eventDao.findById(event.getId());
            for (RoundEntity round : eventEntity.getRounds()) {
                ParticipationEntity participation = findParticipation(competitor, round);
                if (participation == null) {
                    participation = new ParticipationEntity();
                    participation.setCategory(getCategoryForAthlete(competition, athlete.getGender(),
                            athlete.getBirthdate()).get(0));
                    participation.setRound(round);
                    participation.setCompetitors(Collections.singletonList(competitor));
                }
                if (event.isChecked()) {
                    if (!competitor.getParticipations().contains(participation)) {
                        competitor.getParticipations().add(participation);
                    }
                    if (event.isNeedRecord()) {

                        if ((event.getRecord() == null) || BigDecimal.ZERO.equals(event.getRecord())) {
                            //TODO : remove all previous records

                        } else {
                            if ((event.getRecordId() == null)
                                    || (event.getRecord().compareTo(recordDao.findById(event.getRecordId()).getValue()) != 0)) {
                                //create new record
                                RecordEntity newrecord = new RecordEntity();
                                newrecord.setDate(new Date());
                                newrecord.setAthlete(competitor.getAthlete());
                                newrecord.setEventtype(eventEntity.getEventType());
                                newrecord.setValue(event.getRecord());
                                newrecord.setSeasonflag(Boolean.TRUE);
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
        if ((competitor.getParticipations().size() == 0) && (competitor.getId() != null)) {
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
            returnedList.add(new TeamDto(team.getId(), team.getAbbreviation(), team.getName()));
        }

        return returnedList;
    }

    @Override
    public List<EventDto> getAllParticipations(CompetitionDto competition) {
        CompetitionEntity competitionEntity = new CompetitionEntity(competition.getId());
        List<EventEntity> findAllEventsInCompetition = eventDao.findAllEventsInCompetition(competitionEntity);
        Map<EventDto, List<AthleteDto>> events = new HashMap<>();
        for (EventEntity eventEntity : findAllEventsInCompetition) {
            EventDto event = createEventDto(eventEntity);
            List<AthleteDto> participantsInMap = new ArrayList<>();
            event.setParticipants(participantsInMap);
            events.put(event, participantsInMap);
            event.setCategory(CategoryServiceImpl.createCategoryDto(getMasterCategoryForEvent(eventEntity)));
        }
        List<CompetitorEntity> allCompetitors = competitorDao
                .getAllCompetitorsFetchParticipationsRoundsCategoriesEvents(competitionEntity);
        for (CompetitorEntity competitor : allCompetitors) {
            AthleteDto athlete = createAthleteDto(competitor);
            for (ParticipationEntity participation : competitor.getParticipations()) {
                athlete.setCategory(participation.getCategory().getName());
                EventDto event = createEventDto(participation.getRound().getEvent());
                List<AthleteDto> participantsInMap = events.get(event);

                participantsInMap.add(athlete);
            }
        }

        ArrayList<EventDto> eventsList = new ArrayList<>(events.keySet());
        //order each participantsList (in events) (by name)
		Comparator<AthleteDto> eventsComparator = new Comparator<AthleteDto>() {

			@Override
			public int compare(AthleteDto o1, AthleteDto o2) {
				return o1.getLastName().compareTo(o2.getLastName());
			}
		};
        for (EventDto eventDto : eventsList) {
            Collections.sort(eventDto.getParticipants(),eventsComparator);
        }
        //order EventsList by EventNumber
		Collections.sort(eventsList, new Comparator<EventDto>() {

			@Override
			public int compare(EventDto o1, EventDto o2) {
				return o1.getNumber() - o2.getNumber();
			}
		});
        return eventsList;
    }

    private CategoryEntity getMasterCategoryForEvent(EventEntity eventEntity) {
        //if contains TC then TC otherwise the first
        for (CategoryEntity categoryEntity : eventEntity.getCategories()) {
            if (categoryEntity.getAbbreviation().startsWith("TC")) {
                return categoryEntity;
            }
        }
        return eventEntity.getCategories().iterator().next();
    }

    private EventDto createEventDto(EventEntity event) {
        EventDto eventDto = new EventDto();
        eventDto.setName(event.getName());
        eventDto.setNumber(event.getNumber());
        eventDto.setHour(new SimpleDateFormat("HH:mm").format(new LocalDateTime(event.getRounds().get(0).getTimeScheduled()).toDate()));
        eventDto.setAbbreviation(event.getAbbreviation());
        return eventDto;
    }

    @Override
    public CompetitionDto getCompetitionWithId(Number id) {
        return createCompetitionDto(competitionDao.findById(id));
    }

    private CompetitionDto createCompetitionDto(CompetitionEntity entity) {
        CompetitionDto competitionDto = new CompetitionDto();
        competitionDto.setId(entity.getId());
        competitionDto.setName(entity.getName());
        competitionDto.setStartDate(entity.getStartDate());
        competitionDto.setFederationId(entity.getFederation().getId());
        return competitionDto;
    }
}
