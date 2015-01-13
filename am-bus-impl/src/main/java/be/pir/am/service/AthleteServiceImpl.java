package be.pir.am.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import be.pir.am.api.dao.AthleteDao;
import be.pir.am.api.dao.LicenceDao;
import be.pir.am.api.dto.AthleteDto;
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
		List<AthleteEntity> findAthletesWithBib = licenceDao
				.findAthletesWithBib(String.valueOf(bib));
		for (AthleteEntity athleteEntity : findAthletesWithBib) {
			returnedList.add(new AthleteDto(athleteEntity.getFirstname(),
					athleteEntity.getLastname()));
		}

		return returnedList;
	}

}
