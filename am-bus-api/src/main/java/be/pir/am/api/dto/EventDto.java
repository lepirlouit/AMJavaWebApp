package be.pir.am.api.dto;

import java.math.BigDecimal;

public class EventDto extends BaseDto {
	private String name, abbreviation;
	private Short minimumAge, maximumAge;
	private boolean checked;
	private boolean needRecord;
	private BigDecimal record;
	private Integer recordId;

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

	@Override
	public String toString() {
		return "EventDto [name=" + name + ", abbreviation=" + abbreviation + ", minimumAge=" + minimumAge
				+ ", maximumAge=" + maximumAge + ", checked=" + checked + ", needRecord=" + needRecord + ", record="
				+ record + ", recordId=" + recordId + "]";
	}

}
