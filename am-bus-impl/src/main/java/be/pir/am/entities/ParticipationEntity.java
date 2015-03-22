package be.pir.am.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "participations")
public class ParticipationEntity extends BaseEntity {
	@Id
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "round")
	private RoundEntity round;
	/** (c'est la serie)*/
	@Column
	private Integer heat = 0;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category")
	private CategoryEntity categoryEntity;

	/**
	 * en général il y a toujours qu'un seul participant pour chaque
	 * participation, sauf pour un relais
	 */
	@ManyToMany(mappedBy = "participations")
	private List<CompetitorEntity> competitors;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public CategoryEntity getCategoryEntity() {
		return categoryEntity;
	}

	public void setCategoryEntity(CategoryEntity categoryEntity) {
		this.categoryEntity = categoryEntity;
	}


}
