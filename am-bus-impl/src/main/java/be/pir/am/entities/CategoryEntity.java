package be.pir.am.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity {
	@Column(length = 64)
	private String name;
	@Column(length = 16)
	private String abbreviation;
	private Short minimumAge;
	private Short maximumAge;
	private Character gender;
	@ManyToOne
	@JoinColumn(name = "federation", nullable = false)
	private FederationEntity federation;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public FederationEntity getFederation() {
		return federation;
	}

	public void setFederation(FederationEntity federation) {
		this.federation = federation;
	}

	public Short getMinimumAge() {
		return minimumAge;
	}

	public void setMinimumAge(Short minimumAge) {
		this.minimumAge = minimumAge;
	}

	public Short getMaximumAge() {
		return maximumAge;
	}

	public void setMaximumAge(Short maximumAge) {
		this.maximumAge = maximumAge;
	}

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}

}
