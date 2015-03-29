package be.pir.am.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "teams")
public class TeamEntity extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "federation")
	private FederationEntity federation;
	@Column(length = 64)
	private String abbreviation;
	@Column(length = 256)
	private String name;
	@Column(length = 16)
	private String federationNumber;
	@Column(length = 64)
	private String place;

	public FederationEntity getFederation() {
		return federation;
	}

	public void setFederation(FederationEntity federation) {
		this.federation = federation;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFederationNumber() {
		return federationNumber;
	}

	public void setFederationNumber(String federationNumber) {
		this.federationNumber = federationNumber;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

}
