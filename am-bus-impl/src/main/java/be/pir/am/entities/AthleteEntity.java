package be.pir.am.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "athletes")
public class AthleteEntity extends BaseEntity {
	private String firstname;
	private String lastname;
	private String middlename;
	private Character gender;
	@Column(columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date birthdate;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nationality")
	private CountryEntity nationality;

	public AthleteEntity() {
	}

	public AthleteEntity(Integer id) {
		super(id);
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public CountryEntity getNationality() {
		return nationality;
	}

	public void setNationality(CountryEntity nationality) {
		this.nationality = nationality;
	}

}
