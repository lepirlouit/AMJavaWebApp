package be.pir.am.api.dto;

import java.math.BigDecimal;
import java.util.List;

public class EventDto extends BaseDto {
	private String name, abbreviation, hour;
	private Short minimumAge, maximumAge;
	private boolean checked;
	private boolean needRecord;
	private BigDecimal record;
	private Integer recordId;
	private List<AthleteDto> participants;
	private int number;

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

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isNeedRecord() {
		return needRecord;
	}

	public void setNeedRecord(boolean needRecord) {
		this.needRecord = needRecord;
	}

	public BigDecimal getRecord() {
		return record;
	}

	public void setRecord(BigDecimal record) {
		this.record = record;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public List<AthleteDto> getParticipants() {
		return participants;
	}

	public void setParticipants(List<AthleteDto> participants) {
		this.participants = participants;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	@Override
	public String toString() {
		return "EventDto [name=" + name + ", abbreviation=" + abbreviation + ", minimumAge=" + minimumAge
				+ ", maximumAge=" + maximumAge + ", checked=" + checked + ", needRecord=" + needRecord + ", record="
				+ record + ", recordId=" + recordId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + number;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventDto other = (EventDto) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (number != other.number)
			return false;
		return true;
	}

}
