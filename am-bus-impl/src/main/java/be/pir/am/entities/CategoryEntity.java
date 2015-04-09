package be.pir.am.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity {
	@Column(length = 64)
	private String name;
	@Column(length = 16)
	private String abbreviation;
	private Short minimumAge;
	private Short maximumAge;
	private Character gender;
	@ManyToOne
	@JoinColumn(name = "federation", nullable = false)
	private FederationEntity federation;

	public CategoryEntity() {
	}

	public CategoryEntity(Integer id) {
		super(id);
	}

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

	public FederationEntity getFederation() {
		return federation;
	}

	public void setFederation(FederationEntity federation) {
		this.federation = federation;
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

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((abbreviation == null) ? 0 : abbreviation.hashCode());
		result = (prime * result) + ((federation == null) ? 0 : federation.hashCode());
		result = (prime * result) + ((gender == null) ? 0 : gender.hashCode());
		result = (prime * result) + ((maximumAge == null) ? 0 : maximumAge.hashCode());
		result = (prime * result) + ((minimumAge == null) ? 0 : minimumAge.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		CategoryEntity other = (CategoryEntity) obj;
		if (abbreviation == null) {
			if (other.abbreviation != null) {
				return false;
			}
		} else if (!abbreviation.equals(other.abbreviation)) {
			return false;
		}
		if (federation == null) {
			if (other.federation != null) {
				return false;
			}
		} else if (!federation.equals(other.federation)) {
			return false;
		}
		if (gender == null) {
			if (other.gender != null) {
				return false;
			}
		} else if (!gender.equals(other.gender)) {
			return false;
		}
		if (maximumAge == null) {
			if (other.maximumAge != null) {
				return false;
			}
		} else if (!maximumAge.equals(other.maximumAge)) {
			return false;
		}
		if (minimumAge == null) {
			if (other.minimumAge != null) {
				return false;
			}
		} else if (!minimumAge.equals(other.minimumAge)) {
			return false;
		}
		return true;
	}

}
