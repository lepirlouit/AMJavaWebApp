package be.pir.am.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "countries")
public class CountryEntity extends BaseEntity {
	private Short id;
	private String iso;
	private String name;
	private String iso3;
	private Integer numcode;
	private String area;
	private String iaafcode;
	@Basic(fetch = FetchType.LAZY)
	@Lob
	private byte[] flag;
	@Column(name = "ID_FOR_FEDERATION", length = 64)
	private String idForFederation;

	@Id
	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIso3() {
		return iso3;
	}

	public void setIso3(String iso3) {
		this.iso3 = iso3;
	}

	public Integer getNumcode() {
		return numcode;
	}

	public void setNumcode(Integer numcode) {
		this.numcode = numcode;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getIaafcode() {
		return iaafcode;
	}

	public void setIaafcode(String iaafcode) {
		this.iaafcode = iaafcode;
	}

	public byte[] getFlag() {
		return flag;
	}

	public void setFlag(byte[] flag) {
		this.flag = flag;
	}

	public String getIdForFederation() {
		return idForFederation;
	}

	public void setIdForFederation(String idForFederation) {
		this.idForFederation = idForFederation;
	}

}
