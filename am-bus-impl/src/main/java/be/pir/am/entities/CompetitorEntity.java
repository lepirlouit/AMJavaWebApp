package be.pir.am.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "competitors")
public class CompetitorEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "athlete", nullable = false)
    private AthleteEntity athlete;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "license")
    private LicenseEntity license;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition")
    private CompetitionEntity competition;
    @Column(length = 6)
    private String bib;
    @Column(length = 256)
    private String displayname;

    @ManyToMany(mappedBy = "competitors", cascade = CascadeType.ALL)
    private List<ParticipationEntity> participations;

    public AthleteEntity getAthlete() {
        return athlete;
    }

    public void setAthlete(AthleteEntity athlete) {
        this.athlete = athlete;
    }

    public LicenseEntity getLicense() {
        return license;
    }

    public void setLicense(LicenseEntity license) {
        this.license = license;
    }

    public CompetitionEntity getCompetition() {
        return competition;
    }

    public void setCompetition(CompetitionEntity competition) {
        this.competition = competition;
    }

    public String getBib() {
        return bib;
    }

    public void setBib(String bib) {
        this.bib = bib;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public List<ParticipationEntity> getParticipations() {
        return participations;
    }

    public void setParticipations(List<ParticipationEntity> participations) {
        this.participations = participations;
    }

}
