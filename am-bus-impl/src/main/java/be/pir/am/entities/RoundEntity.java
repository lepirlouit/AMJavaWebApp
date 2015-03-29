package be.pir.am.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "rounds")
public class RoundEntity extends BaseEntity {
	@Column(columnDefinition = "TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeScheduled;

	public Date getTimeScheduled() {
		return timeScheduled;
	}

	public void setTimeScheduled(Date timeScheduled) {
		this.timeScheduled = timeScheduled;
	}

}
