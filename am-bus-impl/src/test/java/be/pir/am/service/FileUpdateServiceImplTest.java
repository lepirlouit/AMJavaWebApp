package be.pir.am.service;

import be.pir.am.api.dao.CountryDao;
import be.pir.am.api.dao.LicenseDao;
import be.pir.am.api.dao.TeamDao;
import be.pir.am.entities.LicenseEntity;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Benoit on 14-05-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class FileUpdateServiceImplTest {
    @Mock
    private LicenseDao licenseDao;
    @Mock
    private CountryDao countryDao;
    @Mock
    private TeamDao teamDao;

    @InjectMocks
    private FileUpdateServiceImpl beanToTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updateFromTheNet() {
        Properties lbfaProperties = new Properties();
        boolean returnedValue = false;
        try {
            lbfaProperties.load(this.getClass().getResource("lbfa.properties").openStream());
            returnedValue = beanToTest.updateFromWebBasicAuth(lbfaProperties.getProperty("athlete.file.url"), lbfaProperties.getProperty("athlete.file.user"), lbfaProperties.getProperty("athlete.file.password"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(returnedValue);
    }

    @Test
    public void testShortFile() {
        File inputCsvFile = new File(this.getClass().getResource("ath-testfile-short.csv").getFile());
        Assert.assertTrue(inputCsvFile.isFile());


        boolean returnedValue = beanToTest.updateAthletes(inputCsvFile);
        Mockito.verify(countryDao, Mockito.times(3)).getByIso3("BEL");
        Mockito.verify(teamDao, Mockito.times(1)).getByFederationNumber("31");
        Mockito.verify(teamDao, Mockito.times(2)).getByFederationNumber("2");
        Mockito.verify(licenseDao, Mockito.times(3)).save(Mockito.any(LicenseEntity.class));
        Assert.assertEquals(true, returnedValue);
        Mockito.verifyNoMoreInteractions(licenseDao, countryDao, teamDao);
    }

    @Test
    public void testDependencyInjectionWithMethodInvokation() {
        File inputCsvFile = new File(this.getClass().getResource("ath-testfile1.csv").getFile());
        Assert.assertTrue(inputCsvFile.isFile());


        boolean returnedValue = beanToTest.updateAthletes(inputCsvFile);
        Assert.assertEquals(true, returnedValue);
    }

    @Test
    public void testFullFile() {
        File inputCsvFile = new File(this.getClass().getResource("full_ath_file.csv").getFile());
        Assert.assertTrue(inputCsvFile.isFile());


        boolean returnedValue = beanToTest.updateAthletes(inputCsvFile);

        Mockito.verify(countryDao, Mockito.times(33463)).getByIso3("BEL");
        Mockito.verify(teamDao, Mockito.times(33463)).getByFederationNumber(Mockito.anyString());
        Mockito.verify(licenseDao, Mockito.times(33463)).save(Mockito.any(LicenseEntity.class));
        Assert.assertEquals(true, returnedValue);
        Mockito.verifyNoMoreInteractions(licenseDao, countryDao, teamDao);
    }
}
