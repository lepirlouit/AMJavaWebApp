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
@Table(name = "rounds")
public class RoundEntity extends BaseEntity {
	@Column(columnDefinition = "TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeScheduled;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event", nullable = false)
	private EventEntity event;

	private String name;

	public Date getTimeScheduled() {
		return timeScheduled;
	}

	public void setTimeScheduled(Date timeScheduled) {
		this.timeScheduled = timeScheduled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EventEntity getEvent() {
		return event;
	}

	public void setEvent(EventEntity event) {
		this.event = event;
	}

}
