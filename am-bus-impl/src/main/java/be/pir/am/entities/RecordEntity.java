package be.pir.am.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "records")
public class RecordEntity extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "athlete")
	private AthleteEntity athlete;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eventtype")
	private EventTypeEntity eventtype;
	private Date date;
	private Double value;

	public AthleteEntity getAthlete() {
		return athlete;
	}

	public void setAthlete(AthleteEntity athlete) {
		this.athlete = athlete;
	}

	public EventTypeEntity getEventtype() {
		return eventtype;
	}

	public void setEventtype(EventTypeEntity eventtype) {
		this.eventtype = eventtype;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
