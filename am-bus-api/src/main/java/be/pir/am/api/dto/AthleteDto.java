package be.pir.am.api.dto;

import java.util.Date;

public class AthleteDto extends BaseDto {
	private String firstName, lastName, team, teamShort, category;
	private Date birthdate;
	private String bib;
	private int licenseId;
	private Character gender;

	public AthleteDto() {
	}

	public AthleteDto(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getTeamShort() {
		return teamShort;
	}

	public void setTeamShort(String teamShort) {
		this.teamShort = teamShort;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getBib() {
		return bib;
	}

	public void setBib(String bib) {
		this.bib = bib;
	}

	public int getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(int licenseId) {
		this.licenseId = licenseId;
	}

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "AthleteDto [firstName=" + firstName + ", lastName=" + lastName + ", team=" + team + ", teamShort="
				+ teamShort + ", birthdate=" + birthdate + ", bib=" + bib + ", licenseId=" + licenseId + ", gender="
				+ gender + "]";
	}

}
