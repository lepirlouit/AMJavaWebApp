package be.pir.am.entities;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class BaseEntity {
	@Temporal(TemporalType.TIMESTAMP)
	private Date since;

	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}
}
