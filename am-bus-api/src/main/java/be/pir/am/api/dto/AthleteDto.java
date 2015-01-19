package be.pir.am.api.dto;

public class AthleteDto extends BaseDto {
	private String firstName;
	private String lastName;

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

}
