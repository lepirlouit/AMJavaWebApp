package be.pir.am.service;

import be.pir.am.entities.LicenseEntity;

import java.util.Map;

/**
 * Created by Benoit on 14-05-16.
 */
public class UpdateContext {
    private Map<String, LicenseEntity> licensesMap;
    private int numberOfLinesProcessedCounter;

    public Map<String, LicenseEntity> getLicensesMap() {
        return licensesMap;
    }

    public void setLicensesMap(Map<String, LicenseEntity> licensesMap) {
        this.licensesMap = licensesMap;
    }

    public int getNumberOfLinesProcessedCounter() {
        return numberOfLinesProcessedCounter;
    }

    public void incrementNumberOfLinesProcessedCounter() {
        this.numberOfLinesProcessedCounter++;
    }
}
