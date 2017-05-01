package be.pir.am.api.dto;

import java.io.Serializable;
import java.util.Date;

public class BaseDto implements Serializable{
	private Integer id;
	private Date since;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getSince() {
		return since;
	}
	public void setSince(Date since) {
		this.since = since;
	}

}
