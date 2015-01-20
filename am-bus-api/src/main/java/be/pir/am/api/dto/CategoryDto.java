package be.pir.am.api.dto;

public class CategoryDto extends BaseDto {
	private String name, abbreviation;
	private Short minimumAge, maximumAge;
	private Character gender;
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
