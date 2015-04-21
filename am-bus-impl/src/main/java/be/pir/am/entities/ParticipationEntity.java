package be.pir.am.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "participations")
public class ParticipationEntity extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "round")
	private RoundEntity round;
	/** (c'est la serie)*/
	@Column
	private Integer heat = 0;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category", nullable = false)
	private CategoryEntity category;

	/**
	 * en général il y a toujours qu'un seul participant pour chaque
	 * participation, sauf pour un relais
	 */
	@ManyToMany
	@org.hibernate.annotations.IndexColumn(name = "seqno", base = 1)
	@JoinTable(name = "participants", joinColumns = {@JoinColumn(name = "participation", referencedColumnName = "ID")}, inverseJoinColumns = {@JoinColumn(name = "competitor", referencedColumnName = "ID")})
	private List<CompetitorEntity> competitors;

	public RoundEntity getRound() {
		return round;
	}

	public void setRound(RoundEntity round) {
		this.round = round;
	}

	public Integer getHeat() {
		return heat;
	}

	public void setHeat(Integer heat) {
		this.heat = heat;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity categoryEntity) {
		this.category = categoryEntity;
	}

	public List<CompetitorEntity> getCompetitors() {
		return competitors;
	}

	public void setCompetitors(List<CompetitorEntity> competitors) {
		this.competitors = competitors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((category == null) ? 0 : category.hashCode());
		result = (prime * result) + ((competitors == null) ? 0 : competitors.hashCode());
		result = (prime * result) + ((round == null) ? 0 : round.hashCode());
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		ParticipationEntity other = (ParticipationEntity) obj;
		if (category == null) {
			if (other.category != null) {
				return false;
			}
		} else if (!category.equals(other.category)) {
			return false;
		}
		if (competitors == null) {
			if (other.competitors != null) {
				return false;
			}
		} else if (!competitors.equals(other.competitors)) {
			return false;
		}
		if (round == null) {
			if (other.round != null) {
				return false;
			}
		} else if (!round.equals(other.round)) {
			return false;
		}
		return true;
	}

}
