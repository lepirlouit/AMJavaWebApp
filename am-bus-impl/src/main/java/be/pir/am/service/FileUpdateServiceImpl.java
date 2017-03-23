package be.pir.am.service;

import be.pir.am.api.dao.CountryDao;
import be.pir.am.api.dao.LicenseDao;
import be.pir.am.api.dao.TeamDao;
import be.pir.am.entities.AthleteEntity;
import be.pir.am.entities.LicenseEntity;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class FileUpdateServiceImpl {
    private static final Logger LOGGER = Logger.getLogger(FileUpdateServiceImpl.class);
    private static final String SEPARATOR = "\t";
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @EJB
    private LicenseDao licenseDao;
    @EJB
    private CountryDao countryDao;
    @EJB
    private TeamDao teamDao;

    public boolean updateFromWebBasicAuth(String url, String login, String password) {
        try {
            URL website = new URL(url);
            File file = File.createTempFile("athletes", ".csv");
            String loginPassword = login + ":" + password;
            String encoded = new sun.misc.BASE64Encoder().encode(loginPassword.getBytes());
            URLConnection conn = website.openConnection();
            conn.setRequestProperty("Authorization", "Basic " + encoded);


            Files.copy(conn.getInputStream(), file.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            boolean returnedValue = updateAthletes(file);
            file.delete();
            return returnedValue;
        } catch (IOException e) {
            LOGGER.error("error downloading file", e);
            return false;
        }
    }

    public boolean updateAthletes(File file) {


        //read first line and validate
        //read file in memory
        //load all athlethes and licenses from database from licenses left join fetch athletes
        //transform to map (based on field with a star (Map<String, Choice> result =

        UpdateContext uc = new UpdateContext();
        List<LicenseEntity> licenses = new ArrayList<>();//TODO : get all licenses
        uc.setLicensesMap(licenses.stream().collect(
                Collectors.toMap(LicenseEntity::getLicensenumber, Function.identity()))); //licenses.licensenumber

        //TODO, do the same for : db_countries.iso3 and db_teams.federationnumber
        try (Stream<String> recordLines = recreate(Files.lines(file.toPath(), StandardCharsets.UTF_8).skip(1)
                .parallel())) {
            recordLines.map(line -> line.trim())
                    .filter(line -> !line.isEmpty())
                    .forEach(line -> processLine(line, uc));
        } catch (IOException e) {
            LOGGER.error("error in reading files", e);
        }

        return true;
    }

	/*
     * [0] db_licenses.licensenumber*
	 * [1] db_licenses.bib
	 * [2] db_licenses.id_for_federation
	 * [3] db_athletes.firstname
	 * [4] db_athletes.lastname
	 * [5] db_athletes.gender
	 * [6] db_athletes.birthdate
	 * [7] db_countries.iso3
	 * [8] db_teams.federationnumber
	 */

    /**
     * validate line
     * get license in map
     * if null, create new and persist
     * else set other fields : db_licenses.bib	db_licenses.id_for_federation	db_athletes.firstname	db_athletes.lastname	db_athletes.gender	db_athletes.birthdate	db_countries.iso3	db_teams.federationnumber
     */
    private void processLine(String line, UpdateContext context) {
        String[] split = line.split(SEPARATOR);
        if (split.length != 9) {
            LOGGER.error("Bad number of fields in line : " + line);
            return;
        }
        LicenseEntity license = context.getLicensesMap().get(split[0]);
        if (license == null) {
            license = new LicenseEntity();
            license.setAthlete(new AthleteEntity());
            //TODO : perhaps athlete already exists with other license.
        }

        license.setBib(split[1]);
        license.setId_for_federation(split[2]);
        license.getAthlete().setFirstname(split[3]);
        license.getAthlete().setLastname(split[4]);
        license.getAthlete().setGender(split[5].charAt(0));
        try {
            license.getAthlete().setBirthdate(new SimpleDateFormat(DATE_PATTERN).parse(split[6]));
        } catch (ParseException e) {
            LOGGER.error("Bad date format for line : " + line, e);
            return;
        }
        license.getAthlete().setNationality(countryDao.getByIso3(split[7]));
        license.setTeam(teamDao.getByFederationNumber(split[8]));

        if (license.getId() == null) {
            licenseDao.save(license);
        }
        context.incrementNumberOfLinesProcessedCounter();
    }

    //this method is to avoid bug in jre (fixed in Java8u60) : http://stackoverflow.com/questions/28259636/is-this-a-bug-in-files-lines-or-am-i-misunderstanding-something-about-paralle
    private static <T> Stream<T> recreate(Stream<T> stream) {
        return StreamSupport.stream(stream.spliterator(), stream.isParallel()).onClose(stream::close);
    }

}
