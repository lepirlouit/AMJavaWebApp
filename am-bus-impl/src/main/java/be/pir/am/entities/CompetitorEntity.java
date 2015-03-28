package be.pir.am.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
@Table(name = "competitors")
public class CompetitorEntity extends BaseEntity {
	@Id
	private Integer id;
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "athlete")
	private AthleteEntity athlete;
	@Null
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "license")
	private LicenseEntity license;
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "competition")
	private CompetitionEntity competition;
	@Column(length = 6)
	private String bib;
	@Column(length = 256)
	private String displayname;
	
    @ManyToMany(cascade=CascadeType.ALL)
    @OrderColumn(name = "seqno", columnDefinition = "smallint")
	@JoinTable(name = "participants", 
		joinColumns = {
			@JoinColumn(name = "competitor", referencedColumnName = "ID")
		},
		inverseJoinColumns = {
			@JoinColumn(name = "participation", referencedColumnName = "ID")
		})
    private List<ParticipationEntity> participations;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
