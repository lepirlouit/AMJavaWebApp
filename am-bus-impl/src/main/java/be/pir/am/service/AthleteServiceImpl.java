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
import be.pir.am.api.dao.LicenceDao;
import be.pir.am.api.dto.AthleteDto;
import be.pir.am.api.dto.CategoryDto;
import be.pir.am.api.dto.CompetitionDto;
import be.pir.am.api.dto.EventDto;
import be.pir.am.api.service.AthleteService;
import be.pir.am.entities.AthleteEntity;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AthleteServiceImpl implements AthleteService {
	@EJB
	private AthleteDao athleteDao;
	@EJB
	private LicenceDao licenceDao;

	@Override
	public List<AthleteDto> findAthletesByBib(int bib) {

		List<AthleteDto> returnedList = new ArrayList<>();
		List<AthleteEntity> findAthletesWithBib = licenceDao.findAthletesWithBib(String.valueOf(bib));
		for (AthleteEntity athleteEntity : findAthletesWithBib) {
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

		List<AthleteEntity> results = athleteDao.findAthleteByBibGenderAndBirthdayMinMax(String.valueOf(bib),
				category.getGender(), dateMin, dateMax);
		for (AthleteEntity athleteEntity : results) {
			returnedList.add(createAthleteDto(athleteEntity));
		}
		return returnedList;
	}

	private AthleteDto createAthleteDto(AthleteEntity athE) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		AthleteDto athDto = new AthleteDto(athE.getFirstname(), athE.getLastname());
		athDto.setBirthdate(sdf.format(athE.getBirthdate()));
		// TODO : setTeam (from Licence)
		return athDto;
	}

	@Override
	public List<EventDto> findEventsForAthlete(AthleteDto athlete, CompetitionDto competition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void subscribeAthleteToEvents(AthleteDto competitor, List<EventDto> events) {
		// TODO Auto-generated method stub

	}

}
