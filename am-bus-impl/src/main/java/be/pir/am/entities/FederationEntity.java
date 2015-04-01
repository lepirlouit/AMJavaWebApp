package be.pir.am.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "federations")
public class FederationEntity extends BaseEntity {
	@Column(length = 16)
	private String code;
	@Column(length = 8)
	private String abbreviation;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country")
	private CountryEntity country;
	@Column(length = 256)
	private String name;
	@Column(length = 2)
	private String language;
	@Temporal(TemporalType.TIMESTAMP)
	private Date seasonstart;
	@Column(name = "referencedate_default")
	@Temporal(TemporalType.TIMESTAMP)
	private Date referencedateDefault;
	@Column(length = 256, name = "athletes_link")
	private String athletesLink;
	@Column(name = "athletes_current_version")
	private Integer athletesCurrentVersion;
	@Column(length = 256, name = "teams_link")
	private String teamsLink;
	@Column(name = "teams_current_version")
	private Integer teamsCurrentVersion;
	@Column(length = 256, name = "eventtypes_link")
	private String eventtypesLink;
	@Column(name = "eventtypes_current_version")
	private Integer eventtypesCurrentVersion;
	@Column(length = 256, name = "records_link")
	private String recordsLink;
	@Column(name = "records_current_version")
	private Integer recordsCurrentVersion;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "federation")
	private List<CategoryEntity> categories;

	public FederationEntity() {
	}

	public FederationEntity(Integer id) {
		super(id);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public CountryEntity getCountry() {
		return country;
	}

	public void setCountry(CountryEntity country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Date getSeasonstart() {
		return seasonstart;
	}

	public void setSeasonstart(Date seasonstart) {
		this.seasonstart = seasonstart;
	}

	public Date getReferencedateDefault() {
		return referencedateDefault;
	}

	public void setReferencedateDefault(Date referencedateDefault) {
		this.referencedateDefault = referencedateDefault;
	}

	public String getAthletesLink() {
		return athletesLink;
	}

	public void setAthletesLink(String athletesLink) {
		this.athletesLink = athletesLink;
	}

	public Integer getAthletesCurrentVersion() {
		return athletesCurrentVersion;
	}

	public void setAthletesCurrentVersion(Integer athletesCurrentVersion) {
		this.athletesCurrentVersion = athletesCurrentVersion;
	}

	public String getTeamsLink() {
		return teamsLink;
	}

	public void setTeamsLink(String teamsLink) {
		this.teamsLink = teamsLink;
	}

	public Integer getTeamsCurrentVersion() {
		return teamsCurrentVersion;
	}

	public void setTeamsCurrentVersion(Integer teamsCurrentVersion) {
		this.teamsCurrentVersion = teamsCurrentVersion;
	}

	public String getEventtypesLink() {
		return eventtypesLink;
	}

	public void setEventtypesLink(String eventtypesLink) {
		this.eventtypesLink = eventtypesLink;
	}

	public Integer getEventtypesCurrentVersion() {
		return eventtypesCurrentVersion;
	}

	public void setEventtypesCurrentVersion(Integer eventtypesCurrentVersion) {
		this.eventtypesCurrentVersion = eventtypesCurrentVersion;
	}

	public String getRecordsLink() {
		return recordsLink;
	}

	public void setRecordsLink(String recordsLink) {
		this.recordsLink = recordsLink;
	}

	public Integer getRecordsCurrentVersion() {
		return recordsCurrentVersion;
	}

	public void setRecordsCurrentVersion(Integer recordsCurrentVersion) {
		this.recordsCurrentVersion = recordsCurrentVersion;
	}

}
