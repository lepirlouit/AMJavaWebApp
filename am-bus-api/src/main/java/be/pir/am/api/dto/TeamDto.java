package be.pir.am.api.dto;

public class TeamDto extends BaseDto {
	String shortName,name;

	public TeamDto(Integer id, String abbreviation, String name2) {
		setId(id);
		setShortName(abbreviation);
		setName(name2);
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
