package be.pir.am.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "eventtypes")
public class EventTypeEntity extends BaseEntity {
	private Double distance;


	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}


}
